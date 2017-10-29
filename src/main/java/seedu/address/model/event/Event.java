package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.property.Address;
import seedu.address.model.property.DateTime;
import seedu.address.model.property.Name;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.UniqueReminderList;

/**
 * Represents an Event in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Event implements ReadOnlyEvent {

    private ObjectProperty<Name> name;
    private ObjectProperty<DateTime> time;
    private ObjectProperty<Address> venue;
    private ObjectProperty<UniqueReminderList> reminders;

    /**
     * Every field must be present and not null.
     */
    public Event(Name name, DateTime time, Address venue) {
        requireAllNonNull(name, time, venue);
        this.name = new SimpleObjectProperty<>(name);
        this.time = new SimpleObjectProperty<>(time);
        this.venue = new SimpleObjectProperty<>(venue);

        Set<Reminder> reminders = new HashSet<>();

        // ensuring every instance of Event has an empty list of reminders in it.
        this.reminders = new SimpleObjectProperty<>();
    }

    /**
     * Creates a copy of the given ReadOnlyEvent.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getName(), source.getTime(), source.getVenue());
    }

    public void setName(Name name) {
        this.name.set(requireNonNull(name));
    }

    @Override
    public ObjectProperty<Name> nameProperty() {
        return name;
    }

    @Override
    public Name getName() {
        return name.get();
    }

    public void setDateTime(DateTime time) {
        this.time.set(requireNonNull(time));
    }

    @Override
    public DateTime getTime() {
        return time.get();
    }
    @Override
    public ObjectProperty<DateTime> timeProperty() {
        return time;
    }

    public void setVenue(Address venue) {
        this.venue.set(requireNonNull(venue));
    }

    @Override
    public ObjectProperty<Address> venueProperty() {
        return venue;
    }

    @Override
    public Address getVenue() {
        return venue.get();
    }

    @Override
    public ObjectProperty<UniqueReminderList> reminderProperty() {
        return reminders;
    }
    /**
     * Returns an immutable property set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Reminder> getReminder() {
        return Collections.unmodifiableSet(reminders.get().toSet());
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

