package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.relationship.Relationship;
import seedu.address.model.relationship.UniqueRelList;
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
    private ObjectProperty<Company> company;
    private ObjectProperty<Position> position;
    private ObjectProperty<Status> status;
    private ObjectProperty<Priority> priority;
    private ObjectProperty<Note> note;
    private ObjectProperty<Photo> photo;

    private ObjectProperty<UniqueTagList> tags;
    private ObjectProperty<UniqueRelList> relation;

    /**
     * Constructor without optional fields. Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Set<Relationship> relation) {
        requireAllNonNull(name, phone, email, address, tags, relation);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        //if Person is called without Company, Position or Status
        // parameters, initialize them to "NIL".
        //if Person is called without Priority, initialize it to L. Note is initialized to "NIL" in all cases
        //as it is meant to be added after creating the person.
        //if Person is called without Photo, initialize it to the default photo.
        try {
            this.company = new SimpleObjectProperty<>(new Company("NIL"));
            this.position = new SimpleObjectProperty<>(new Position("NIL"));
            this.status = new SimpleObjectProperty<>(new Status("NIL"));
            this.priority = new SimpleObjectProperty<>(new Priority("L"));
            this.note = new SimpleObjectProperty<>(new Note("NIL"));
            this.photo = new SimpleObjectProperty<>(new Photo("src/main/resources/images/default.jpg"));;
        } catch (Exception e) {
            e.printStackTrace();
        }

        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        // protect internal connections from changes in the arg list
        this.relation = new SimpleObjectProperty<>(new UniqueRelList(relation));
    }

    /**
     * Constructor including all optional fields except Note. Every field must be present and not null.
     *
     * @param name
     * @param phone
     * @param email
     * @param address
     * @param company
     * @param position
     * @param status
     * @param priority
     * @param photo
     * @param tags
     * @param relation
     */
    public Person(Name name, Phone phone, Email email, Address address, Company company, Position position,
                  Status status, Priority priority, Photo photo, Set<Tag>
                          tags, Set<Relationship> relation) {
        requireAllNonNull(name, phone, email, address, company, position, status, priority, photo, tags, relation);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.company = new SimpleObjectProperty<>(company);
        this.position = new SimpleObjectProperty<>(position);
        this.status = new SimpleObjectProperty<>(status);
        this.priority = new SimpleObjectProperty<>(priority);
        this.photo = new SimpleObjectProperty<>(photo);
        //Note is initialized to "NIL" as it is meant to be added after creating the person.
        try {
            this.note = new SimpleObjectProperty<>(new Note("NIL"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));

        // protect internal connections from changes in the arg list
        this.relation = new SimpleObjectProperty<>(new UniqueRelList(relation));
    }

    /**
     * Constructor including all fields. Every field must be present and not null.
     *
     * @param name
     * @param phone
     * @param email
     * @param address
     * @param company
     * @param position
     * @param status
     * @param priority
     * @param note
     * @param tags
     * @param relation
     */
    public Person(Name name, Phone phone, Email email, Address address, Company company, Position position,
                  Status status, Priority priority, Note note, Photo
                          photo, Set<Tag> tags, Set<Relationship> relation) {
        requireAllNonNull(name, phone, email, address, company, position, status, priority, note, photo, tags, relations);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.company = new SimpleObjectProperty<>(company);
        this.position = new SimpleObjectProperty<>(position);
        this.status = new SimpleObjectProperty<>(status);
        this.priority = new SimpleObjectProperty<>(priority);
        this.note = new SimpleObjectProperty<>(note);
        this.photo = new SimpleObjectProperty<>(photo);

        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        // protect internal connections from changes in the arg list
        this.relation = new SimpleObjectProperty<>(new UniqueRelList(relation));
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(),
                source.getCompany(), source.getPosition(), source.getStatus(), source.getPriority(),
                source.getNote(), source.getPhoto(), source.getTags(), source.getRelation());
    }

    @Override
    public ObjectProperty<Name> nameProperty() {
        return name;
    }

    @Override
    public Name getName() {
        return name.get();
    }

    public void setName(Name name) {
        this.name.set(requireNonNull(name));
    }

    @Override
    public ObjectProperty<Phone> phoneProperty() {
        return phone;
    }

    @Override
    public Phone getPhone() {
        return phone.get();
    }

    public void setPhone(Phone phone) {
        this.phone.set(requireNonNull(phone));
    }

    @Override
    public ObjectProperty<Email> emailProperty() {
        return email;
    }

    @Override
    public Email getEmail() {
        return email.get();
    }

    public void setEmail(Email email) {
        this.email.set(requireNonNull(email));
    }

    @Override
    public ObjectProperty<Address> addressProperty() {
        return address;
    }

    @Override
    public Address getAddress() {
        return address.get();
    }

    public void setAddress(Address address) {
        this.address.set(requireNonNull(address));
    }

    @Override
    public Company getCompany() {
        return company.get();
    }

    public void setCompany(Company company) {
        this.company.set(requireNonNull(company));
    }

    @Override
    public ObjectProperty<Company> companyProperty() {
        return company;
    }

    @Override
    public Position getPosition() {
        return position.get();
    }

    public void setPosition(Position position) {
        this.position.set(position);
    }

    public ObjectProperty<Position> positionProperty() {
        return position;
    }

    @Override
    public Status getStatus() {
        return status.get();
    }

    public void setStatus(Status status) {
        this.status.set(status);
    }

    @Override
    public ObjectProperty<Status> statusProperty() {
        return status;
    }

    @Override
    public Priority getPriority() {
        return priority.get();
    }

    public void setPriority(Priority priority) {
        this.priority.set(priority);
    }

    @Override
    public ObjectProperty<Priority> priorityProperty() {
        return priority;
    }

    @Override
    public Note getNote() {
        return note.get();
    }

    public void setNote(Note note) {
        this.note.set(note);
    }

    @Override
    public ObjectProperty<Note> noteProperty() {
        return note;
    }

    public void setNote(Note note) {
        this.note.set(note);
    }

    @Override
    public Photo getPhoto() {
        return photo.get();
    }

    @Override
    public ObjectProperty<Photo> photoProperty() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo.set(photo);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.get().toSet());
    }

    /**
     * Replaces this person's tags with the tags in the argument tag set.
     */
    public void setTags(Set<Tag> replacement) {
        tags.set(new UniqueTagList(replacement));
    }

    public ObjectProperty<UniqueTagList> tagProperty() {
        return tags;
    }

    /**
     * Returns an immutable relationship set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Relationship> getRelation() {
        return Collections.unmodifiableSet(relation.get().toSet());
    }

    /**
     * Replaces this person's relationship with the tags in the argument tag set.
     */
    public void setRel(Set<Relationship> replacement) {
        relation.set(new UniqueRelList(replacement));
    }

    public ObjectProperty<UniqueRelList> relProperty() {
        return relation;
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyPerson) other));
    }

    /**
     * Removes a tag from this person's list of tags if the list contains the tag.
     *
     * @param toRemove Tag to be removed
     */
    public void removeTag(Tag toRemove) {
        tags.get().remove(toRemove);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
