package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.property.Address;
import seedu.address.model.property.Email;
import seedu.address.model.property.Name;
import seedu.address.model.property.Phone;
import seedu.address.model.property.Property;
import seedu.address.model.property.UniquePropertyMap;
import seedu.address.model.property.exceptions.DuplicatePropertyException;
import seedu.address.model.property.exceptions.PropertyNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Person implements ReadOnlyPerson {
    private ObjectProperty<Name> name;
    private ObjectProperty<Phone> phone;
    private ObjectProperty<Email> email;
    private ObjectProperty<Address> address;
    private ObjectProperty<UniquePropertyMap> properties;
    private ObjectProperty<UniqueTagList> tags;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);

        Set<Property> properties = new HashSet<>();
        properties.add(name);
        properties.add(phone);
        properties.add(email);
        properties.add(address);

        this.properties = new SimpleObjectProperty<>();
        try {
            setProperties(properties);
        } catch (DuplicatePropertyException e) {
            // TODO: Better error handling
            e.printStackTrace();
            System.err.println("This should never happen.");
        }

        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
    }

    public Person(Set<Property> properties, Set<Tag> tags) throws DuplicatePropertyException {
        requireAllNonNull(properties, tags);
        this.properties = new SimpleObjectProperty<>();
        setProperties(properties);

        try {
            this.name = new SimpleObjectProperty<>(new Name(getProperty("n")));
            this.phone = new SimpleObjectProperty<>(new Phone(getProperty("p")));
            this.email = new SimpleObjectProperty<>(new Email(getProperty("e")));
            this.address = new SimpleObjectProperty<>(new Address(getProperty("a")));
        } catch (IllegalValueException | PropertyNotFoundException e) {
            // TODO: Better error handling
            e.printStackTrace();
            System.err.println("This should never happen.");
        }

        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getTags());
        try {
            setProperties(source.getProperties());
        } catch (DuplicatePropertyException e) {
            // TODO: Better error handling
            e.printStackTrace();
            System.err.println("This should never happen.");
        }
    }

    public void setName(Name name) {
        requireNonNull(name);
        setProperty(name);
        this.name.set(name);
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
        requireNonNull(phone);
        setProperty(phone);
        this.phone.set(phone);
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
        requireNonNull(email);
        setProperty(email);
        this.email.set(email);
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
     * Replaces this person's properties with the properties in the argument tag set.
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

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.get().toSet());
    }

    /**
     * Returns the set of tags joined into a string
     * @return
     */
    public String joinTagsToString() {
        Set<Tag> tags = getTags();
        StringBuilder sb = new StringBuilder();
        for (Tag t : tags) {
            sb.append(t.getStringTagName());
            sb.append(" ");
        }
        return sb.toString();
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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyPerson) other));
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
