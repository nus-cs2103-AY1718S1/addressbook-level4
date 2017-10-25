package seedu.address.ui;

import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class DetailedPersonCard extends UiPart<Region> {

    private static final String FXML = "DetailedPersonListCard.fxml";
    private static String[] colors = {"red", "yellow", "blue", "orange", "brown", "green", "pink", "black"};
    private static HashMap<String, String> tagColors = new HashMap<>();
    private static HashMap<String, String> webLinkColors = new HashMap<>();

    static {
        webLinkColors.put("facebook", "#3b5998");
        webLinkColors.put("twitter", "#00aced");
        webLinkColors.put("linkedin", "#0077b5");
        webLinkColors.put("instagram", "#8a3ab9");
        webLinkColors.put("others", "grey");
    }

    private static Random random = new Random();

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private ReadOnlyPerson selectedPerson;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane webLinks;
    @FXML
    private Label remark;
    @FXML
    private FlowPane tags;

    public DetailedPersonCard() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    private static String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, colors[random.nextInt(colors.length)]);
        }
        return tagColors.get(tagValue);
    }

    private static String getColorForWeblinks(String webLinkTag) {
        return webLinkColors.get(webLinkTag);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        remark.textProperty().bind(Bindings.convert(person.remarkProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
        person.webLinkProperty().addListener((observable, oldValue, newValue) -> {
            webLinks.getChildren().clear();
            initWebLinks(person);
        });
    }

    /**
     * Initialize person's tag and add appropriate background color to it
     *
     * @param person name
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    /**
     * Initialize person's webLink and add appropriate background color to it
     *
     * @param person name
     */
    private void initWebLinks(ReadOnlyPerson person) {
        person.getWebLinks().forEach(webLink -> {
            Label webLinkLabel = new Label(webLink.webLinkTag);
            webLinkLabel.setStyle("-fx-background-color: " + getColorForWeblinks(webLink.toStringWebLinkTag()));
            webLinks.getChildren().add(webLinkLabel);
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DetailedPersonCard)) {
            return false;
        }

        // state check
        DetailedPersonCard card = (DetailedPersonCard) other;
        return id.getText().equals(card.id.getText())
                && selectedPerson.equals(card.selectedPerson);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        selectedPerson = event.getNewSelection().person;
        bindListeners(selectedPerson);
    }
}
