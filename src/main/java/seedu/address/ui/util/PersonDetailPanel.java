package seedu.address.ui.util;

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
import seedu.address.ui.PersonListPanel;
import seedu.address.ui.UiPart;

/**
 * The Person Detail Panel of the App.
 */
public class PersonDetailPanel extends UiPart<Region> {
    private static final String FXML = "PersonDetailPanel.fxml";

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

    public PersonDetailPanel(PersonListPanel personListPanel) {
        super(FXML);
        initialise();
        registerAsAnEventHandler(this);
    }

    /**
     * Initialize the panel with empty fields
     */
    private void initialise() {
        name.setText("");
        avatarImage = new Image(getClass().getResourceAsStream("/images/avatarGray.png"));
        avatar.fitWidthProperty().bind(personDetailPanel.widthProperty());
    }

    /**
     * Shows the details of the person on the panel
     */
    private void showPersonDetails(ReadOnlyPerson person) {

        avatar.setImage(avatarImage);
        name.setText(person.getName().toString());
        phone.setText(person.getName().toString());
        address.setText(person.getName().toString());
        email.setText(person.getName().toString());
        tags.getChildren();
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
