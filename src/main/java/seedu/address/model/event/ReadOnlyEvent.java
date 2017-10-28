//@@author A0162268B
package seedu.address.model.event;

import java.time.Duration;
import java.time.LocalTime;

import javafx.beans.property.ObjectProperty;
import seedu.address.model.event.timeslot.Date;
import seedu.address.model.event.timeslot.Timeslot;
import seedu.address.model.event.timeslot.Timing;

/**
 * A read-only immutable interface for an Event in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyEvent {
    ObjectProperty<Title> titleProperty();

    Title getTitle();

    ObjectProperty<Date> dateProperty();

    Date getDate();

    ObjectProperty<Timeslot> timeslotProperty();

    Timeslot getTimeslot();

    ObjectProperty<Timing> timingProperty();

    Timing getTiming();

    ObjectProperty<Description> descriptionProperty();

    Description getDescription();

    boolean happensBefore(Timeslot slot);

    Duration getDuration();

    LocalTime getStartTime();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyEvent other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTitle().equals(this.getTitle()) // state checks here onwards
                && other.getTimeslot().equals(this.getTimeslot())
                && other.getDescription().equals(this.getDescription()));
    }

    /**
     * Formats the event as text, showing all its details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Timeslot: ")
                .append(getTimeslot())
                .append(" Description: ")
                .append(getDescription());
        return builder.toString();
    }
}
