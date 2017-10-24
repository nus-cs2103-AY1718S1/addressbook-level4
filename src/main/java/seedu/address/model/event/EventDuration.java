package seedu.address.model.event;

import java.time.Duration;

import seedu.address.commons.util.EventOutputUtil;


/**
 * This Object only serves as a placeholder for UI purposes.
 *
 * @see EventTime for actual manipulation of event time
 * <p>
 * Justification:
 * DateTime and Duration are to be tightly-coupled,
 * therefore both values are placed in EventTime.java
 * <p>
 * The purpose of this class is to store the duration
 * value, which is to be outputted via its toString method
 * from its ObjectProperty binding at EventCard.java
 * @see seedu.address.ui.EventCard#bindListeners(Event)
 */
public class EventDuration {

    private Duration eventDuration;

    public EventDuration(Duration eventDuration) {
        this.eventDuration = eventDuration;
    }


    public Duration getDuration() {
        return eventDuration;
    }

    @Override
    public String toString() {
        return EventOutputUtil.toStringDuration(eventDuration);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventDuration // instanceof handles nulls
                && this.eventDuration.equals(((EventDuration) other).getDuration())); // state check
    }

    @Override
    public int hashCode() {
        return eventDuration.hashCode();
    }
}
