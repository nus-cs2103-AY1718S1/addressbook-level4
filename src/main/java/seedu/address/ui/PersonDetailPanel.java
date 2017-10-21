package seedu.address.ui;

import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.util.ColorsUtil;

/**
 * The Person Detail Panel of the App.
 */
public class PersonDetailPanel extends UiPart<Region> {
    private static final String FXML = "PersonDetailPanel.fxml";
    private static String[] colors = ColorsUtil.getTagColors();
    private static HashMap<String, String> tagColors = new HashMap<> ();
    private static Random random = new Random();

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Image avatarImage;

    @FXML
    private StackPane personDetailPanel;

    @FXML
    private HBox cardPane;

    @FXML
    private ImageView avatar;

    @FXML
    private Label name;

    @FXML
    private Label phone;

    @FXML
    private Label address;

    @FXML
    private Label email;

    @FXML
    private FlowPane tags;

    public PersonDetailPanel() {
        super(FXML);
        initialise();
        registerAsAnEventHandler(this);
    }

    /**
     * Initialize the panel with empty fields
     */
    private void initialise() {
        name.setText("");
        phone.setText("");
        email.setText("");
        address.setText("");
        avatarImage = new Image(getClass().getResourceAsStream("/images/avatarGray.png"));
        avatar.fitWidthProperty().bind(personDetailPanel.widthProperty());
    }

    /**
     * Shows the details of the person on the panel
     */
    private void showPersonDetails(ReadOnlyPerson person) {

        avatar.setImage(avatarImage);
        name.setText(person.getName().toString());
        phone.setText(person.getPhone().toString());
        address.setText(person.getAddress().toString());
        email.setText(person.getEmail().toString());
        tags.getChildren().clear();
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getTagColor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    private String getTagColor(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, colors[random.nextInt(colors.length)]);
        }
        return tagColors.get(tagValue);
    }

    /**
     * Shows person details on the panel when a person is selected
     */
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showPersonDetails(event.getNewSelection().person);
    }
}
