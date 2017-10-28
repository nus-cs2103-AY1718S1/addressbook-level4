package seedu.address.ui;

import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.Logic;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Info Panel of the App that displays full information of a {@code Person}.
 */
public class DetailsPanel extends UiPart<Region> {

    private static final String FXML = "DetailsPanel.fxml";
    private static final String PREFIX_ADDRESS_FIELD = "Address: ";
    private static final String PREFIX_PHONE_FIELD = "Phone: ";
    private static final String PREFIX_HOME_PHONE_FIELD = "Home Phone: ";
    private static final String PREFIX_EMAIL_FIELD = "Email: ";
    private static final String PREFIX_SCHOOL_EMAIL_FIELD = "School Email: ";
    private static final String PREFIX_BIRTHDAY_FIELD = "Birthday: ";
    private static final String PREFIX_WEBSITE_FIELD = "Website: ";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Logic logic;

    @FXML
    private Pane pane;
    @FXML
    private Label name;
    @FXML
    private Text phoneField;
    @FXML
    private Label phone;
    @FXML
    private Text addressField;
    @FXML
    private Label address;
    @FXML
    private Text emailField;
    @FXML
    private Label email;
    @FXML
    private Text schoolEmailField;
    @FXML
    private Label schoolEmail;
    @FXML
    private Text birthdayField;
    @FXML
    private Label birthday;
    @FXML
    private Text websiteField;
    @FXML
    private Label website;
    @FXML
    private Text homePhoneField;
    @FXML
    private Label homePhone;
    @FXML
    private FlowPane tags;

    public DetailsPanel() {
        super(FXML);
        this.logic = logic;
        loadBlankPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Loads the full info of the person
     * @param person the selected person to display the full info of.
     */
    public void loadPersonInfo(ReadOnlyPerson person) {
        phoneField.setText(PREFIX_PHONE_FIELD);
        addressField.setText(PREFIX_ADDRESS_FIELD);
        emailField.setText(PREFIX_EMAIL_FIELD);
        schoolEmailField.setText(PREFIX_SCHOOL_EMAIL_FIELD);
        birthdayField.setText(PREFIX_BIRTHDAY_FIELD);
        websiteField.setText(PREFIX_WEBSITE_FIELD);
        homePhoneField.setText(PREFIX_HOME_PHONE_FIELD);
        bindListeners(person);
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
        schoolEmail.textProperty().bind(Bindings.convert(person.schEmailProperty()));
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
        website.textProperty().bind(Bindings.convert(person.websiteProperty()));
        homePhone.textProperty().bind(Bindings.convert(person.homeNumberProperty()));
        tags.getChildren().clear();
        initTags(person);
    }

    /**
     * Initializes and styles tags belonging to each person.
     * @param person
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-font-size:" + "15px");
            tags.getChildren().add(tagLabel);
        });
        logger.finest("All tags for " + person.getName().toString() + " initialized in info");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DetailsPanel)) {
            return false;
        }

        DetailsPanel detailsPanel = (DetailsPanel) other;
        return name.getText().equals(detailsPanel.name.getText())
                && phone.getText().equals(detailsPanel.phone.getText())
                && address.getText().equals(detailsPanel.address.getText())
                && email.getText().equals(detailsPanel.email.getText())
                && schoolEmail.getText().equals(detailsPanel.schoolEmail.getText())
                && birthday.getText().equals(detailsPanel.birthday.getText())
                && website.getText().equals(detailsPanel.website.getText())
                && homePhone.getText().equals(detailsPanel.homePhone.getText())
                && tags.getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .map(Label::getText)
                .collect(Collectors.toList())
                .equals(detailsPanel.tags.getChildrenUnmodifiable()
                        .stream()
                        .map(Label.class::cast)
                        .map(Label::getText)
                        .collect(Collectors.toList()));
    }
    /**
     * Sets all info fields to not display anything when the app is just started.
     */
    private void loadBlankPage() {
        Label label;
        Text text;
        for (Node node: pane.getChildren()) {
            if (node instanceof Label) {
                label = (Label) node;
                label.setText("");
            } else if (node instanceof Text) {
                text = (Text) node;
                text.setText("");
            } else if (node instanceof TextFlow) {
                for (Node subNode: ((TextFlow) node).getChildren()) {
                    if (subNode instanceof Text) {
                        text = (Text) subNode;
                        text.setText("");
                    }
                    if (subNode instanceof Label) {
                        label = (Label) subNode;
                        label.setText("");
                    }
                }
            }
        }
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonInfo(event.getNewSelection().person);
    }
}


