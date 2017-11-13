package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.UniqueMeetingList;
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
    private ObjectProperty<Note> note;
    private ObjectProperty<UniqueTagList> tags;
    private ObjectProperty<UniqueMeetingList> meetings;
    private ObjectProperty<LastUpdated> lastUpdated;
    private ObjectProperty<Id> id;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Note note, Id id,
                  LastUpdated lastUpdated, Set<Tag> tags, Set<Meeting> meetings) {
        requireAllNonNull(name, phone, email, address, tags, meetings);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.note = new SimpleObjectProperty<>(note);
        this.id = new SimpleObjectProperty<>(id);
        this.lastUpdated = new SimpleObjectProperty<>(lastUpdated);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        // protect internal meetings from changes in the arg list
        this.meetings = new SimpleObjectProperty<>(new UniqueMeetingList(meetings));
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getNote(),
                source.getId(), source.getLastUpdated(), source.getTags(), source.getMeetings());
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

    public void setNote(Note note) {
        this.note.set(requireNonNull(note));
    }

    @Override
    public ObjectProperty<Note> noteProperty() {
        return note;
    }

    @Override
    public Note getNote() {
        return note.get();
    };

    @Override
    public ObjectProperty<LastUpdated> lastUpdatedProperty() {
        return lastUpdated;
    }

    @Override
    public LastUpdated getLastUpdated() {
        return lastUpdated.get();
    }

    public void setLastUpdated (LastUpdated lastUpdated) {
        this.lastUpdated = new SimpleObjectProperty<>(lastUpdated);
    }

    @Override
    public ObjectProperty<Id> idProperty() {
        return id;
    }

    @Override
    public Id getId() {
        return id.get();
    }

    public void setId (Id id) {
        this.id.set(requireNonNull(id));
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

    //@@author alexanderleegs
    /**
     * Returns an immutable meeting set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Meeting> getMeetings() {
        return Collections.unmodifiableSet(meetings.get().toSet());
    }

    public ObjectProperty<UniqueMeetingList> meetingProperty() {
        return meetings;
    }

    /**
     * Replaces this person's meetings with the meetings in the argument meeting set.
     */
    public void setMeetings(Set<Meeting> replacement) {
        meetings.set(new UniqueMeetingList(replacement));
    }

    //@@author
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyPerson) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name.getValue(), phone.getValue(), email.getValue(), address.getValue());
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
