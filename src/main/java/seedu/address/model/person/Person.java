package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.event.Event;
import seedu.address.model.event.UniqueEventList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Person implements ReadOnlyPerson {

    private ObjectProperty<Name> name;
    private ObjectProperty<Phone> phone;
    private ObjectProperty<Email> email;
    private ObjectProperty<Address> address;
    private ObjectProperty<UniqueTagList> tags;
    private ObjectProperty<DateAdded> dateAdded;
    private ObjectProperty<UniqueEventList> events;
    private ObjectProperty<Birthday> birthday;

    /**
     * /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Set<Event> events,
                  DateAdded dateAdded) {
        requireAllNonNull(name, phone, email, address, tags, dateAdded);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.birthday = new SimpleObjectProperty<>(new Birthday());
        this.dateAdded = new SimpleObjectProperty<>(dateAdded);
        // protect internal tags & events from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        this.events = new SimpleObjectProperty<>(new UniqueEventList(events));
    }

    public Person(Name name, Birthday birthday, Phone phone, Email email, Address address, Set<Tag> tags,
                  Set<Event> events, DateAdded dateAdded) {
        requireAllNonNull(name, phone, email, address, tags, dateAdded);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.birthday = new SimpleObjectProperty<>(birthday);
        this.dateAdded = new SimpleObjectProperty<>(dateAdded);
        // protect internal tags & events from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        this.events = new SimpleObjectProperty<>(new UniqueEventList(events));
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getBirthday(), source.getPhone(), source.getEmail(), source.getAddress(),
                source.getTags(), source.getEvents(), source.getDateAdded());
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

    public void setBirthday(Birthday birthday) {
        this.birthday.set(requireNonNull(birthday));
    }

    @Override
    public ObjectProperty<Birthday> birthdayProperty() {
        return birthday;
    }

    @Override
    public Birthday getBirthday() {
        return birthday.get();
    }

    public void setPhone(Phone phone) {
        this.phone.set(requireNonNull(phone));
    }

    @Override
    public ObjectProperty<Phone> phoneProperty() {
        return phone;
    }

    @Override
    public Phone getPhone() {
        return phone.get();
    }

    public void setEmail(Email email) {
        this.email.set(requireNonNull(email));
    }

    @Override
    public ObjectProperty<Email> emailProperty() {
        return email;
    }

    @Override
    public Email getEmail() {
        return email.get();
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

    @Override
    public DateAdded getDateAdded() {
        return dateAdded.get();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.get().toSet());
    }

    public ObjectProperty<UniqueTagList> tagProperty() {
        return tags;
    }

    /**
     * Replaces this person's tags with the tags in the argument tag set.
     */
    public void setTags(Set<Tag> replacement) {
        tags.set(new UniqueTagList(replacement));
    }

    /**
     * Returns an immutable event set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Event> getEvents() {
        return Collections.unmodifiableSet(events.get().toSet());
    }

    public ObjectProperty<UniqueEventList> eventProperty() {
        return events;
    }

    /**
     * Replaces this person's events with the events in the argument events set.
     */
    public void setEvents(Set<Event> replacement) {
        events.set(new UniqueEventList(replacement));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyPerson) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, events, dateAdded);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
