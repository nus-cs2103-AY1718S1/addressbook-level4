package seedu.address.model.event;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

import seedu.address.commons.util.DateTimeUtil;

/**
 * Represents an Event's start time and duration in the address book.
 */
public class EventTime {


    private final LocalDateTime eventStartTime;
    private final Duration eventDuration;
    private final LocalDateTime eventEndTime;
    private Boolean isUpcoming;

    public EventTime(LocalDateTime eventStartTime, Duration eventDuration) {
        this.eventStartTime = eventStartTime;
        this.eventDuration = eventDuration;
        this.eventEndTime = eventStartTime.plus(eventDuration);

        isUpcoming = LocalDateTime.now().isBefore(this.eventStartTime);
    }

    /**
     * Check the value of isUpcoming by checking current time against start time
     *
     * @return boolean value of isUpcoming
     */
    public boolean isUpcoming() {
        if (LocalDateTime.now().isBefore(eventStartTime)) {
            isUpcoming = Boolean.TRUE;
            return isUpcoming;
        } else {
            isUpcoming = Boolean.FALSE;
            return isUpcoming;
        }
    }

    public LocalDateTime getStart() {
        return eventStartTime;
    }

    public LocalDateTime getEnd() {
        return eventEndTime;
    }

    @Override
    public String toString() {
        return DateTimeUtil.parseLocalDateTimeToString(eventStartTime);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventTime // instanceof handles nulls
                && this.eventStartTime.isEqual(((EventTime) other).eventStartTime)
                && this.eventDuration.equals(((EventTime) other).eventDuration)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventStartTime, eventDuration);
    }

}
