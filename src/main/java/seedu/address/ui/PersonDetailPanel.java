package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.util.Avatar;

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
    private Circle avatar;

    @FXML
    private Label initial;

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
        initial.setText("");
        avatar.setFill(Color.TRANSPARENT);

        //avatarImage = new Image(getClass().getResourceAsStream("/images/avatarGray.png"));
        //avatar.fitWidthProperty().bind(personDetailPanel.widthProperty());
    }

    /**
     * Shows the details of the person on the panel
     */
    private void showPersonDetails(ReadOnlyPerson person) {

        initial.setText(Avatar.getInitial(person.getName().fullName));
        avatar.setFill(Paint.valueOf(Avatar.getColor(person.getName().fullName)));

        name.setText(person.getName().toString());
        phone.setText(person.getPhone().toString());
        address.setText(person.getAddress().toString());
        email.setText(person.getEmail().toString());

        tags.getChildren().clear();
        initTags(person);
    }

    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag ->
            tags.getChildren().add(new Label(tag.tagName)));
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
