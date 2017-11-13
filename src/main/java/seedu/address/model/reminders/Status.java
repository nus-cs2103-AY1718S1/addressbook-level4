package seedu.address.model.reminders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

//@@author justinpoh
/**
 * Represents a Reminder's status in the program.
 * Guarantees: immutable.
 */
public class Status {

    private static final String STATUS_FORMAT_SINGLE_MESSAGE = "Status: %1$s day left.";
    private static final String STATUS_FORMAT_MESSAGE = "Status: %1$s days left.";
    private static final String STATUS_TODAY_MESSAGE = "Event happening today!";
    private static final String STATUS_OVERDUE = "Event has passed.";

    private static final int THREE_DAYS = 3;
    private static final int ONE_DAY = 1;
    private static final int ZERO_DAY = 0;
    private static final int ZERO = 0;

    private final LocalDate currentDate;
    private final LocalTime currentTime;

    private final LocalDate dateOfReminder;
    private final LocalTime timeOfReminder;

    private final String status;

    /**
     * Initialize the status for this reminder.
     */
    public Status(Reminder reminder) {
        currentDate = LocalDate.now();
        currentTime = LocalTime.now();
        this.dateOfReminder = reminder.getDate().toLocalDate();
        this.timeOfReminder = reminder.getTime().toLocalTime();
        this.status = getStatus();
    }

    /**
     * This constructor is used for testing purposes only.
     * This is because the values of {@code LocalDate.now()} and {@code LocalTime.now()} is dependent on the date and
     * time the tests are conducted and might lead to tests failing depending on the date and time the tests
     * are conducted.
     */
    private Status(Reminder reminder, LocalDate defaultDate, LocalTime defaultTime) {
        this.currentDate = defaultDate;
        this.currentTime = defaultTime;
        this.dateOfReminder = reminder.getDate().toLocalDate();
        this.timeOfReminder = reminder.getTime().toLocalTime();
        this.status = getStatus();
    }

    /**
     * Return a Status instance that is used for testing, initialized with {@code reminder}, {@code defaultDate}
     * and {@code defaultTime}.
     */
    public static Status getStatusTestInstance(Reminder reminder, LocalDate defaultDate, LocalTime defaultTime) {
        Status testInstance = new Status(reminder, defaultDate, defaultTime);
        return testInstance;
    }

    /**
     * Returns true if the event has already past.
     */
    public boolean hasEventPassed() {
        final long daysUntilEvent = getDaysUntilEvent();
        final long minutesUntilEvent = getMinutesUntilEvent();
        if (daysUntilEvent > ZERO_DAY) {
            return false;
        } else if (daysUntilEvent == ZERO_DAY && minutesUntilEvent >= ZERO) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if the event is happening today (with respect to the date and time this object is created).
     */
    public boolean isEventToday() {
        final long daysUntilEvent = getDaysUntilEvent();
        final long minutesUntilEvent = getMinutesUntilEvent();

        return daysUntilEvent == 0 && minutesUntilEvent >= 0;
    }

    /**
     * Returns true if the event is happening within three days
     * (with respect to the date and time this object is created.)
     */
    public boolean isEventWithinThreeDays() {
        final long daysUntilEvent = getDaysUntilEvent();
        final long minutesUntilEvent = getMinutesUntilEvent();
        if (daysUntilEvent < ZERO_DAY || daysUntilEvent > THREE_DAYS) {
            return false;
        }
        if (daysUntilEvent == 0 && minutesUntilEvent < 0) {
            return false;
        }
        return true;
    }

    /**
     * Returns the correct status depending on the given {@code reminder} to this Status object
     * @see Status(Reminder)
     */
    private String getStatus() {
        if (hasEventPassed()) {
            return STATUS_OVERDUE;
        } else if (isEventToday()) {
            return STATUS_TODAY_MESSAGE;
        } else {
            final long daysUntilEvent = getDaysUntilEvent();
            if (daysUntilEvent == ONE_DAY) {
                return String.format(STATUS_FORMAT_SINGLE_MESSAGE, ONE_DAY);
            }

            return String.format(STATUS_FORMAT_MESSAGE, getDaysUntilEvent());
        }
    }

    /**
     * Returns the number of days left until {@code reminderDate} (with respect to the date this object is created).
     */
    private long getDaysUntilEvent() {
        return currentDate.until(dateOfReminder, ChronoUnit.DAYS);
    }

    /**
     * Returns the difference in time between the current time and {@code reminderTime} in minutes
     * (with respect to the time this object is created).
     * Note that this method only returns the difference in time, and does not take into consideration the difference
     * in days.
     */
    private long getMinutesUntilEvent() {
        return currentTime.until(timeOfReminder, ChronoUnit.MINUTES);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, dateOfReminder, timeOfReminder);
    }

    @Override
    public String toString() {
        return status;
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof Status
                && this.status.equals(((Status) other).status));
    }
}
