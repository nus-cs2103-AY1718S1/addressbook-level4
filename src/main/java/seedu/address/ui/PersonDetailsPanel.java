package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.PersonEditedEvent;
import seedu.address.commons.events.ui.PasswordAcceptedEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.util.ColorUtil;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.storage.SecurityManager;
//@@author syy94

/**
 * Panel containing the details of the selected person.
 */
public class PersonDetailsPanel extends UiPart<Region> {
    private static final String FXML = "PersonDetailsPanel.fxml";
    private static final String MESSAGE_ENTER_PASS = "Please enter the password";
    private static final String MESSAGE_SELECT_PERSON = "Select a person to view their details.";
    private static final String LOG_IGNORED = "Currently not showing edited Person. Panel not updated";
    private static final String LOG_CHANGED = "Panel updated with new Person";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private ReadOnlyPerson person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label group;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private VBox fields;

    public PersonDetailsPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
        setStartMessage();
    }

    private void setStartMessage() {
        if (SecurityManager.passExists()) {
            group.setText(MESSAGE_ENTER_PASS);
        } else {
            group.setText(MESSAGE_SELECT_PERSON);
        }
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        group.textProperty().bind(Bindings.convert(person.groupProperty()));
        group.setStyle("-fx-background-color: " + ColorUtil.getUniqueHsbColorForObject(person.getGroup()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));

        person.fieldsListProperty().addListener((observable, oldValue, newValue) -> regenerateFields(person));

        person.tagProperty().addListener((observable, oldValue, newValue) -> regenerateTags(person));
    }

    /**
     * Creates and add the tags belonging to the person
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            final Label label = new Label(tag.tagName);
            label.setStyle("-fx-background-color: " + ColorUtil.getUniqueHsbColorForObject(tag));
            tags.getChildren().add(label);
        });
    }

    /**
     * Creates and add the tags belonging to the person
     */
    private void initFields(ReadOnlyPerson person) {
        person.getFields().forEach(field -> {
            final Label label = new Label(field.key + ": " + field.value);
            label.getStyleClass().add("cell_small_label");
            fields.getChildren().add(label);
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonDetailsPanel)) {
            return false;
        }

        // state check
        PersonDetailsPanel panel = (PersonDetailsPanel) other;
        return id.getText().equals(panel.id.getText())
                && person.equals(panel.person);
    }

    private void setPerson(ReadOnlyPerson person) {
        this.person = person;
        bindListeners(person);
        regenerateTags(person);
        regenerateFields(person);
    }

    private void regenerateTags(ReadOnlyPerson person) {
        tags.getChildren().clear();
        initTags(person);
    }

    private void regenerateFields(ReadOnlyPerson person) {
        fields.getChildren().clear();
        initFields(person);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setPerson(event.getNewSelection().person);
    }

    @Subscribe
    private void handlePersonEditedEvent(PersonEditedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.getOldPerson().equals(person)) {
            setPerson(event.getNewPerson());
            logger.info(LOG_CHANGED);
        } else {
            logger.info(LOG_IGNORED);
        }
    }

    @Subscribe
    private void handlePasswordAcceptedEvent(PasswordAcceptedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        group.setText(MESSAGE_SELECT_PERSON);
    }
}
