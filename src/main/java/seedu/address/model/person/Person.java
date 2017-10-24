package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.group.Group;
import seedu.address.model.group.UniqueGroupList;
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
    /**
     *  A Person will have an empty group list by default
     */
    private ObjectProperty<UniqueGroupList> groups = new SimpleObjectProperty<>(new UniqueGroupList());
    /**
     *  A Person will not be marked as favourite by default
     */
    private ObjectProperty<Favourite> favourite = new SimpleObjectProperty<>(new Favourite(false));

    /**
     * Every field must be present and not null.
     * Stamdard Constructor
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
    }

    /**
     * Every field must be present and not null.
     * Constructor for Favourite feature
     */
    public Person(Name name, Phone phone, Email email, Address address, Favourite favourite, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.favourite = new SimpleObjectProperty<>(favourite);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
    }

    /**
     * Every field must be present and not null.
     * Constructor for Group feature
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Set<Group> groups) {
        requireAllNonNull(name, phone, email, address, tags, groups);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        // protect internal groups from changes in the arg list
        this.groups = new SimpleObjectProperty<>(new UniqueGroupList(groups));
    }

    /**
     * Every field must be present and not null.
     * Constructor for XMLAdaptedPerson
     */
    public Person(Name name, Phone phone, Email email, Address address, Favourite favourite,
                  Set<Tag> tags, Set<Group> groups) {
        requireAllNonNull(name, phone, email, address, tags, groups);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.favourite = new SimpleObjectProperty<>(favourite);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        // protect internal groups from changes in the arg list
        this.groups = new SimpleObjectProperty<>(new UniqueGroupList(groups));
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getFavourite(),
                source.getTags());
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

    public void setFavourite() {
        Favourite fav = new Favourite(false);
        this.favourite.set(fav);
    }

    public ObjectProperty<Favourite> favouriteProperty() {
        return favourite;
    }


    public Favourite getFavourite() {
        return favourite.get();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.get().toSet());
    }

    @Override
    public Set<Group> getGroups() {
        return groups.get().toSet();
    }

    @Override
    public ObjectProperty<UniqueTagList> tagProperty() {
        return tags;
    }

    @Override
    public ObjectProperty<UniqueGroupList> groupProperty() {
        return groups;
    }

    /**
     * Replaces this person's tags with the tags in the argument tag set.
     */
    public void setTags(Set<Tag> replacement) {
        tags.set(new UniqueTagList(replacement));
    }

    /**
     * Replaces this person's groups with the groups in the argument group set.
     */
    public void setGroups(Set<Group> replacement) {
        groups.set(new UniqueGroupList(replacement));
    }

    /**
     * Replaces this person's groups with the groups in the argument group set.
     */

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyPerson) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, groups);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
