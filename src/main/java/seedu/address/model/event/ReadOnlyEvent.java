package seedu.address.model.event;

import java.util.Set;

import javafx.beans.property.ObjectProperty;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;

// @@author HuWanqing
/**
 * Represents an interface for event in the address book.
 */
public interface ReadOnlyEvent {
    ObjectProperty<EventName> eventNameProperty();
    EventName getEventName();
    ObjectProperty<EventDescription> descriptionProperty();
    EventDescription getDescription();
    ObjectProperty<EventTime> timeProperty();
    EventTime getEventTime();
    ObjectProperty<ParticipantList> participantProperty();
    Set<Person> getParticipants();
    ObjectProperty<String> daysProperty();

    /**
     * returns true if both event has the same state
     */
    default boolean eventIsSameStateAs(ReadOnlyEvent other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getEventName().equals(this.getEventName()) // state checks here onwards
                && other.getEventTime().equals(this.getEventTime()));
    }

    /**
     * Formats the event as text, showing all event details.
     */
    default String getEventAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getEventName())
                .append(" Description: ")
                .append(getDescription())
                .append(" Time: ")
                .append(getEventTime())
                .append(" Participants: ");
        for (ReadOnlyPerson person : getParticipants()) {
            builder.append(person.getName())
                    .append(" ");
        }
        return builder.toString();
    }
}
