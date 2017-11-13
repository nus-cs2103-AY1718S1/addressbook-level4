package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.property.Address;
import seedu.address.model.property.DateTime;
import seedu.address.model.property.Name;
import seedu.address.model.property.Property;
import seedu.address.model.property.UniquePropertyMap;
import seedu.address.model.property.exceptions.DuplicatePropertyException;
import seedu.address.model.property.exceptions.PropertyNotFoundException;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.UniqueReminderList;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;

//@@junyang junyango

/**
 * Represents an Event in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Event implements ReadOnlyEvent {

    private ObjectProperty<Name> name;
    private ObjectProperty<DateTime> time;
    private ObjectProperty<Address> address;
    private ObjectProperty<UniqueReminderList> reminders;
    private ObjectProperty<UniquePropertyMap> properties;

    public Event(Name name, DateTime time, Address address) {
        this(name, time, address, new ArrayList<>());
    }

    /**
     * Every field must be present and not null.
     */
    public Event(Name name, DateTime time, Address address, List<Reminder> reminders) {
        requireAllNonNull(name, time, address);
        this.name = new SimpleObjectProperty<>(name);
        this.time = new SimpleObjectProperty<>(time);
        this.address = new SimpleObjectProperty<>(address);

        Set<Property> properties = new HashSet<>();
        properties.add(time);
        properties.add(name);
        properties.add(address);
        this.properties = new SimpleObjectProperty<>();
        try {
            setProperties(properties);
        } catch (DuplicatePropertyException e) {
            e.printStackTrace();
            System.err.println("This should never happen");
        }
        try {
            this.reminders = new SimpleObjectProperty<>(new UniqueReminderList(reminders));
        } catch (DuplicateReminderException e) {
            e.printStackTrace();
            System.err.println("This should never happen");
        }
    }

    public Event(Set<Property> properties, ArrayList<Reminder> reminders) throws DuplicateReminderException,
            DuplicatePropertyException {
        requireAllNonNull(properties, reminders);
        this.properties = new SimpleObjectProperty<>();
        setProperties(properties);

        try {
            this.name = new SimpleObjectProperty<>(new Name(getProperty("n")));
            this.time = new SimpleObjectProperty<>(new DateTime(getProperty("dt")));
            this.address = new SimpleObjectProperty<>(new Address(getProperty("a")));
        } catch (IllegalValueException | PropertyNotFoundException e) {
            // TODO: Better error handling
            e.printStackTrace();
            System.err.println("This should never happen.");
        }
        this.reminders = new SimpleObjectProperty<>((new UniqueReminderList(reminders)));
    }

    /**
     * Creates a copy of the given ReadOnlyEvent.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getName(), source.getTime(), source.getAddress(), source.getReminders());
        try {
            setProperties(source.getProperties());
            setReminders(source.getReminders());
        } catch (DuplicatePropertyException | DuplicateReminderException e) {
            // TODO: Better error handling
            e.printStackTrace();
            System.err.println("This should never happen.");
        }
    }

    public void setName(Name name) {
        requireNonNull(name);
        setProperty(name);
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
        requireNonNull(time);
        setProperty(time);
        this.time.set(time);
    }

    @Override
    public DateTime getTime() {
        return time.get();
    }
    @Override
    public ObjectProperty<DateTime> timeProperty() {
        return time;
    }

    public void setAddress(Address address) {
        requireNonNull(address);
        setProperty(address);
        this.address.set(address);
    }

    @Override
    public ObjectProperty<Address> addressProperty() {
        return address;
    }

    @Override
    public Address getAddress() {
        return address.get();
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
    public void addReminder(ReadOnlyReminder r) throws DuplicateReminderException {
        reminders.get().add(r);
    }
    @Override
    public ArrayList<Reminder> getReminders() {
        return reminders.get().toList();
    }

    /**
     * Replaces this event's reminders with the reminders in the argument tag set.
     */
    public void setReminders(List<? extends ReadOnlyReminder> replacement) throws DuplicateReminderException {
        reminders.set(new UniqueReminderList(replacement));
    }

    @Override
    public ObjectProperty<UniquePropertyMap> properties() {
        return properties;
    }

    /**
     * Returns an immutable property set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Property> getProperties() {
        return Collections.unmodifiableSet(properties.get().toSet());
    }

    /**
     * Replaces this event's properties with the properties in the argument tag set.
     */
    public void setProperties(Set<Property> replacement) throws DuplicatePropertyException {
        properties.set(new UniquePropertyMap(replacement));
    }

    private String getProperty(String shortName) throws PropertyNotFoundException {
        return properties.get().getPropertyValue(shortName);
    }
    /**
     * Updates the value of the property if there already exists a property with the same shortName, otherwise
     * adds a new property.
     */
    public void setProperty(Property toSet) {
        properties.get().addOrUpdate(toSet);
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
        return Objects.hash(name, time, address, reminders);
    }

    @Override
    public String toString() {
        return getAsText();
    }
}

