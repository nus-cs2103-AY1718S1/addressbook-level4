package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelNoSelectionEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.Logic;
import seedu.address.model.person.ReadOnlyPerson;

//@@author teclu
/**
 * The Person Panel of the App, which displays the contact details of a selected person.
 */
public class PersonPanel extends UiPart<Region> {
    private static final String FXML = "PersonPanel.fxml";

    private final Logic logic;
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private PersonCard selectedPersonCard;
    private ReadOnlyPerson selectedPerson;
    private boolean isBlankPage = true;

    @FXML
    private VBox panel;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label birthday;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView avatar;

    public PersonPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        registerAsAnEventHandler(this);
        loadBlankPersonPage();
    }

    /**
     * Loads a blank page when no contact is selected.
     */
    private void loadBlankPersonPage() {
        name.setText("No contact selected");
        phone.setText("Phone: -");
        address.setText("Address: -");
        email.setText("Email: -");
        birthday.setText("Birthday: -");
        avatar.setImage(null);
        tags.getChildren().clear();
    }

    /**
     * Loads the selected person's contact details.
     */
    private void loadPersonPage() {
        name.setText(selectedPerson.getName().fullName);
        phone.setText("Phone: " + selectedPerson.getPhone().toString());
        address.setText("Address: " + selectedPerson.getAddress().toString());
        email.setText("Email: " + selectedPerson.getEmail().toString());
        birthday.setText("Birthday: " + selectedPerson.getBirthday().toString());
        avatar.setImage(SwingFXUtils.toFXImage(selectedPerson.getAvatar().getImage(), null));
        selectedPerson.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + tag.tagColour);
            tags.getChildren().add(tagLabel);
        });
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        loadBlankPersonPage();
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        selectedPersonCard = event.getNewSelection();
        selectedPerson = selectedPersonCard.person;
        isBlankPage = false;
        loadPersonPage();
    }

    @Subscribe
    private void handlePersonPanelNoSelectionEvent(PersonPanelNoSelectionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        isBlankPage = true;
        loadBlankPersonPage();
    }

    /**
     * Clicking the Avatar will trigger a GUI option of uploading pictures.
     * Note: Implementation is not complete; may or may not use this.
     */
    @FXML
    private void avatarPrompt() {
        if (!isBlankPage) {
            new AvatarWindow(selectedPersonCard, logic);
        }
    }
}
