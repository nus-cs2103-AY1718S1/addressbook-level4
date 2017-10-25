package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.reminder.ReadOnlyReminder;

/**
 * The Display Panel of the App.
 */
public class DisplayPanel  extends UiPart<Region> {


    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String FXML = "ReminderDisplay.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyReminder reminder;


    @FXML
    private Label task;
    @FXML
    private Label priority;
    @FXML
    private Label datentime;
    @FXML
    private TextArea message;
    @FXML
    private FlowPane tags;

    public DisplayPanel(ReadOnlyReminder reminder) {
        super(FXML);
        this.reminder = reminder;
        initTags(reminder);
        setDisplay(reminder);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void setDisplay(ReadOnlyReminder reminder) {
        task.textProperty().bind(Bindings.convert(reminder.taskProperty()));
        priority.textProperty().bind(Bindings.convert(reminder.priorityProperty()));
        datentime.textProperty().bind(Bindings.convert(reminder.dateProperty()));
        message.textProperty().bind(Bindings.convert(reminder.messageProperty()));
        reminder.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            reminder.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        });
    }

    private void initTags(ReadOnlyReminder reminder) {
        reminder.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

}
