//@@author cqhchan
package seedu.address.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
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

    public static final int TIMER_DELAY = 0; // in milliseconds
    public static final int TIMER_PERIOD = 5000000; // in milliseconds

    public static final int GREEN_WARNING_DAYS_LEFT = 7;
    public static final int YELLOW_WARNING_DAYS_LEFT = 3;
    public static final int ORANGE_WARNING_DAYS_LEFT = 0;

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
    @FXML
    private Label daysCountdown;

    public ReminderCard(ReadOnlyReminder reminder, int displayedIndex) {
        super(FXML);
        this.reminder = reminder;
        id.setText(displayedIndex + ". ");
        initTags(reminder);
        bindListeners(reminder);
        initCountdown(reminder);
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
    //@@author

    //@@author duyson98
    /**
     * @param reminder
     */
    private void initCountdown(ReadOnlyReminder reminder) {
        // Calculates the day difference between the reminder's date and the current date
        // Todo: Minus 1 day in day difference if the current time passes the reminder's time
        final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDate deadline = LocalDate.parse(reminder.getDate().toString(), dateFormatter);
        LocalDate currentTime = LocalDate.now();
        int daysBetween = (int) ChronoUnit.DAYS.between(currentTime, deadline);

        setDaysCountdownBasedOnDays(daysBetween);
        if (daysBetween >= ORANGE_WARNING_DAYS_LEFT) { // Only start the countdown if the deadline is not overdue
            startDaysCountdown(deadline);
        }
    }

    /**
     * Starts the countdown.
     */
    private void startDaysCountdown(LocalDate date) {
        final Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                LocalDate currentDate = LocalDate.now();
                int newDaysBetween = (int) ChronoUnit.DAYS.between(currentDate, date);
                Platform.runLater(() -> setDaysCountdownBasedOnDays(newDaysBetween));
            }
        };
        timer.scheduleAtFixedRate(task, TIMER_DELAY, TIMER_PERIOD);
    }

    private void setDaysCountdownBasedOnDays(int days) {
        setDaysCountdownContentBasedOnDays(days);
        setDaysCountdownColorBasedOnDays(days);
    }

    private void setDaysCountdownContentBasedOnDays(int days) {
        if (days > ORANGE_WARNING_DAYS_LEFT) {
            daysCountdown.setText(days + " day(s)" + " left");
        } else if (days == ORANGE_WARNING_DAYS_LEFT) {
            daysCountdown.setText("today");
        } else {
            daysCountdown.setText("overdue");
        }
    }

    private void setDaysCountdownColorBasedOnDays(int days) {
        if (days >= GREEN_WARNING_DAYS_LEFT) {
            daysCountdown.setStyle("-fx-text-fill: " + "greenyellow");
        } else if (days >= YELLOW_WARNING_DAYS_LEFT) {
            daysCountdown.setStyle("-fx-text-fill: " + "yellow");
        } else if (days >= ORANGE_WARNING_DAYS_LEFT) {
            daysCountdown.setStyle("-fx-text-fill: " + "orange");
        } else {
            daysCountdown.setStyle("-fx-text-fill: " + "red");
        }
    }
    //@@author

    //@@author cqhchan
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReminderCard)) {
            return false;
        }

        // state check
        ReminderCard card = (ReminderCard) other;
        return id.getText().equals(card.id.getText())
                && reminder.equals(card.reminder);
    }
    //@@author
}
