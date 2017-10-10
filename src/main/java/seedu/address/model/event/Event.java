package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Represents an Event in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Event implements ReadOnlyEvent {

    private ObjectProperty<EventName> name;
    private ObjectProperty<EventTime> time;
    private ObjectProperty<EventVenue> venue;

    /**
     * Every field must be present and not null.
     */
    public Event(EventName name, EventTime time, EventVenue venue) {
        requireAllNonNull(name, time, venue);
        this.name = new SimpleObjectProperty<>(name);
        this.time = new SimpleObjectProperty<>(time);
        this.venue = new SimpleObjectProperty<>(venue);
    }

    /**
     * Creates a copy of the given ReadOnlyEvent.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getName(), source.getTime(), source.getVenue());
    }

    public void setName(EventName name) {
        this.name.set(requireNonNull(name));
    }

    @Override
    public ObjectProperty<EventName> nameProperty() {
        return name;
    }

    @Override
    public EventName getName() {
        return name.get();
    }

    public void setTime(EventTime time) {
        this.time.set(requireNonNull(time));
    }

    @Override
    public EventTime getTime() {
        return time.get();
    }

    @Override
    public ObjectProperty<EventTime> timeProperty() {
        return time;
    }

    public void setVenue(EventVenue venue) {
        this.venue.set(requireNonNull(venue));
    }

    @Override
    public ObjectProperty<EventVenue> venueProperty() {
        return venue;
    }

    @Override
    public EventVenue getVenue() {
        return venue.get();
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyEvent // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyEvent) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, time, venue);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}

