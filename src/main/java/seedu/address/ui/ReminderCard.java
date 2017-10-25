package seedu.address.ui;

import java.util.HashMap;
import java.util.Random;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.reminder.ReadOnlyReminder;

/**
 * An UI component that displays information of a {@code Reminder}.
 */
public class ReminderCard extends UiPart<Region> {

    private static final String FXML = "ReminderListCard.fxml";

    private static String[] colors = { "red", "gold", "blue", "purple", "orange", "brown",
        "green", "magenta", "black", "grey" };
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
    private static Random random = new Random();

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyReminder reminder;

    @javafx.fxml.FXML
    private HBox remindercardPane;
    @FXML
    private Label task;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private Label datentime;
    @FXML
    private Label message;
    @FXML
    private FlowPane tags;

    public ReminderCard(ReadOnlyReminder reminder, int displayedIndex) {
        super(FXML);
        this.reminder = reminder;
        id.setText(displayedIndex + ". ");
        initTags(reminder);
        bindListeners(reminder);
    }

    private static String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, colors[random.nextInt(colors.length)]);
        }

        return tagColors.get(tagValue);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyReminder reminder) {
        task.textProperty().bind(Bindings.convert(reminder.taskProperty()));
        priority.textProperty().bind(Bindings.convert(reminder.priorityProperty()));
        datentime.textProperty().bind(Bindings.convert(reminder.dateProperty()));
        message.textProperty().bind(Bindings.convert(reminder.messageProperty()));
        reminder.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(reminder);
        });
    }

    /**
     * @param reminder
     */
    private void initTags(ReadOnlyReminder reminder) {
        reminder.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        ReminderCard card = (ReminderCard) other;
        return id.getText().equals(card.id.getText())
                && reminder.equals(card.reminder);
    }
}

