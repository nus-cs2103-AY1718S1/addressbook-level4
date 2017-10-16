package seedu.address.model.event;

import javafx.beans.property.ObjectProperty;

/**
 * A read-only immutable interface for an Event in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */

public interface ReadOnlyEvent {

    ObjectProperty<EventName> nameProperty();
    EventName getName();
    ObjectProperty<EventTime> timeProperty();
    EventTime getTime();
    ObjectProperty<EventVenue> venueProperty();
    EventVenue getVenue();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyEvent other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getTime().equals(this.getTime())
                && other.getVenue().equals(this.getVenue()));
    }

    /**
     * Formats the event as text, showing all event details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Event: ")
                .append(getName())
                .append(" | ")
                .append(" Date/Time: ")
                .append(getTime())
                .append(" | ")
                .append(" Venue: ")
                .append(getVenue());
        return builder.toString();
    }

}

