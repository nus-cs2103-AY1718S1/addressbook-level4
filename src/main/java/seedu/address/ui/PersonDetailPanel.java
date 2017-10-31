package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ClearPersonDetailPanelRequestEvent;
import seedu.address.commons.events.ui.PersonEditedEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.util.Avatar;

/**
 * The Person Detail Panel of the App.
 */
public class PersonDetailPanel extends UiPart<Region> {

    public static final String PERSON_PHONE_ICON = "☎  ";
    public static final String PERSON_ADDRESS_ICON = "\uD83C\uDFE0  ";
    public static final String PERSON_EMAIL_ICON = "\uD83D\uDCE7  ";

    private static final String FXML = "PersonDetailPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private Circle avatar; // TODO: Implement support for uploading picture from local directory
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
    private Label remark;
    @FXML
    private FlowPane tags;

    public PersonDetailPanel() {
        super(FXML);
        showEmptyPanel();
        registerAsAnEventHandler(this);
    }

    /**
     * Displays an empty panel
     */
    private void showEmptyPanel() {
        name.setText("");
        phone.setText("");
        email.setText("");
        address.setText("");
        remark.setText("");
        initial.setText("");
        tags.getChildren().clear();
        avatar.setFill(Color.TRANSPARENT);
    }

    /**
     * Shows the details of the person on the panel
     */
    private void showPersonDetails(ReadOnlyPerson person) {

        initial.setText(Avatar.getInitial(person.getName().fullName));
        avatar.setFill(Paint.valueOf(Avatar.getColor(person.getName().fullName)));

        setTextFields(person);
        setTags(person);
    }

    private void setTextFields(ReadOnlyPerson person) {
        name.setText(person.getName().toString());
        phone.setText(PERSON_PHONE_ICON + person.getPhone().toString());
        address.setText(PERSON_ADDRESS_ICON + person.getAddress().toString());
        email.setText(PERSON_EMAIL_ICON + person.getEmail().toString());
        remark.setText(person.getRemark().toString());
    }

    private void setTags(ReadOnlyPerson person) {
        tags.getChildren().clear();
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Shows person details on the panel when a person is selected
     */
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showPersonDetails(event.getNewSelection().person);
    }

    /**
     * Updates the panel when the details of the selected person is changed
     */
    @Subscribe
    private void handlePersonDetailsChangedEvent(PersonEditedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showPersonDetails(event.editedPerson);
    }

    @Subscribe
    private void handlePersonListClearedEvent(ClearPersonDetailPanelRequestEvent event) {
        showEmptyPanel();
    }
}
