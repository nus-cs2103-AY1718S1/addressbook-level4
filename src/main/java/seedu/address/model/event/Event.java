package seedu.address.model.event;

//@@author chernghann
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.person.Address;

/**
 * Represents a Event in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Event implements ReadOnlyEvent {

    private ObjectProperty<EventName> name;
    private ObjectProperty<Date> date;
    private ObjectProperty<Address> address;

    /**
     * Every field must be present and not null.
     */
    public Event(EventName name, Date date, Address address) {
        requireAllNonNull(name, date, address);
        this.name = new SimpleObjectProperty<>(name);
        this.date = new SimpleObjectProperty<>(date);
        this.address = new SimpleObjectProperty<>(address);
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getName(), source.getDate(), source.getAddress());
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

    public void setAddress(Address address) {
        this.address.set(requireNonNull(address));
    }

    @Override
    public ObjectProperty<Address> addressProperty() {
        return address;
    }

    @Override
    public Address getAddress() {
        return address.get();
    }

    public void setDate(Date date) {
        this.date.set(requireNonNull(date));
    }

    @Override
    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    @Override
    public Date getDate() {
        return date.get();
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
        return Objects.hash(name, date, address);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
