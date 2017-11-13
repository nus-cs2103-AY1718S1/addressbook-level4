package seedu.address.ui;

import static javafx.scene.paint.Color.TRANSPARENT;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
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
    private FlowPane tagsWithBorder;
    @FXML
    private ImageView iconPhone;
    @FXML
    private ImageView iconEmail;
    @FXML
    private ImageView iconAddress;

    public PersonDetailPanel() {
        super(FXML);
        showEmptyPanel();
        registerAsAnEventHandler(this);
    }

    /**
     * Displays an empty panel.
     */
    private void showEmptyPanel() {
        name.setText("");
        phone.setText("");
        email.setText("");
        address.setText("");
        remark.setText("");
        initial.setText("");
        tagsWithBorder.getChildren().clear();
        avatar.setFill(TRANSPARENT);
        hideIcons();
    }

    /**
     * Shows the details of the person on the panel.
     */
    private void showPersonDetails(ReadOnlyPerson person) {
        setAvatar(person);
        setTextFields(person);
        setTags(person);
        showIcons();
    }

    private void setAvatar(ReadOnlyPerson person) {
        initial.setText(Avatar.getInitial(person.getName().fullName));
        avatar.setFill(Paint.valueOf(Avatar.getColor(person.getName().fullName)));
    }

    private void setTextFields(ReadOnlyPerson person) {
        name.setText(person.getName().toString());
        phone.setText(person.getPhone().toString());
        address.setText(person.getAddress().toString());
        email.setText(person.getEmail().toString());
        setRemark(person);
    }

    private void setTags(ReadOnlyPerson person) {
        tagsWithBorder.getChildren().clear();
        person.getTags().forEach(tag -> tagsWithBorder.getChildren().add(new Label(tag.tagName)));
    }

    private void setRemark(ReadOnlyPerson person) {
        remark.setText(person.getRemark().toString());

        if (person.getRemark().isEmpty()) {
            remark.setManaged(false);
        } else {
            remark.setManaged(true);
        }
    }

    private void hideIcons() {
        iconPhone.setVisible(false);
        iconEmail.setVisible(false);
        iconAddress.setVisible(false);
    }

    private void showIcons() {
        iconPhone.setVisible(true);
        iconEmail.setVisible(true);
        iconAddress.setVisible(true);
    }

    /**
     * Shows person details on the panel when a person is selected.
     */
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showPersonDetails(event.getNewSelection().person);
    }

    /**
     * Updates the panel when the details of the selected person is changed.
     */
    @Subscribe
    private void handlePersonDetailsChangedEvent(PersonEditedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showPersonDetails(event.editedPerson);
    }

    @Subscribe
    private void handlePersonListClearedEvent(ClearPersonDetailPanelRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showEmptyPanel();
    }
}
