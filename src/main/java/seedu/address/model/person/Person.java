package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.customField.CustomField;
import seedu.address.model.customField.UniqueCustomFieldList;
import seedu.address.model.person.phone.Phone;
import seedu.address.model.person.phone.UniquePhoneList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Person implements ReadOnlyPerson, Comparable<Person> {

    private ObjectProperty<Name> name;
    private ObjectProperty<Phone> primaryPhone;
    private ObjectProperty<UniquePhoneList> uniquePhoneList;
    private ObjectProperty<Email> email;
    private ObjectProperty<Address> address;
    private ObjectProperty<Photo> photo;

    private ObjectProperty<UniqueTagList> tags;
    private ObjectProperty<UniqueCustomFieldList> customFields;


    /**
     * Every field must be present and not null except Custom Field and Photo.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = new SimpleObjectProperty<>(name);
        this.primaryPhone = new SimpleObjectProperty<>(phone);
        this.uniquePhoneList = new SimpleObjectProperty<>(new UniquePhoneList());
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);

        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        this.customFields = new SimpleObjectProperty<>(new UniqueCustomFieldList());
    }

    public Person(Name name, Phone phone, UniquePhoneList uniquePhoneList,
                  Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, uniquePhoneList, email, address, tags);
        this.name = new SimpleObjectProperty<>(name);
        this.primaryPhone = new SimpleObjectProperty<>(phone);
        this.uniquePhoneList = new SimpleObjectProperty<>(uniquePhoneList);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.photo = new SimpleObjectProperty<>(new Photo());

        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        this.customFields = new SimpleObjectProperty<>(new UniqueCustomFieldList());
    }

    /**
     * Every field must be present and not null except Photo.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Set<CustomField> customFields) {
        requireAllNonNull(name, phone, email, address, tags, customFields);
        this.name = new SimpleObjectProperty<>(name);
        this.primaryPhone = new SimpleObjectProperty<>(phone);
        this.uniquePhoneList = new SimpleObjectProperty<>(new UniquePhoneList(phone));
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        // protect internal custom fields from changes in the arg list
        this.customFields = new SimpleObjectProperty<>(new UniqueCustomFieldList(customFields));
    }

    public Person(Name name, Phone phone, UniquePhoneList uniquePhoneList,
                  Email email, Address address, Set<Tag> tags, Set<CustomField> customFields) {
        requireAllNonNull(name, uniquePhoneList, email, address, tags, customFields);
        this.name = new SimpleObjectProperty<>(name);
        this.primaryPhone = new SimpleObjectProperty<>(phone);
        this.uniquePhoneList = new SimpleObjectProperty<>(uniquePhoneList);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.photo = new SimpleObjectProperty<>(new Photo());
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        // protect internal custom fields from changes in the arg list
        this.customFields = new SimpleObjectProperty<>(new UniqueCustomFieldList(customFields));
    }

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Photo photo,
                  Set<Tag> tags, Set<CustomField> customFields) {
        requireAllNonNull(name, phone, email, address, tags, customFields);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.photo = new SimpleObjectProperty<>(photo);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        // protect internal custom fields from changes in the arg list
        this.customFields = new SimpleObjectProperty<>(new UniqueCustomFieldList(customFields));
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getPhoneList(), source.getEmail(), source.getAddress(),
                source.getTags(), source.getCustomFields());
    }

    public void setPhone(Phone phone) {
        this.primaryPhone.set(requireNonNull(phone));
    }

    @Override
    public ObjectProperty<Phone> phoneProperty() {
        return primaryPhone;
    }

    @Override
    public Phone getPhone() {
        return primaryPhone.get();
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

    @Override
    public UniquePhoneList getPhoneList() {
        return uniquePhoneList.get();
    }

    public ObjectProperty<UniquePhoneList> phoneListProperty() {
        return uniquePhoneList;
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

    public void setPhoto(Photo photo) {
        this.photo.set(requireNonNull(photo));
    }

    @Override
    public ObjectProperty<Photo> photoProperty() {
        return photo;
    }

    @Override
    public Photo getPhoto() {
        return photo.get();
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
     * Returns an immutable custom field set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<CustomField> getCustomFields() {
        return Collections.unmodifiableSet(customFields.get().toSet());
    }

    /**
     * Returns the list of custom fields of the person
     *
     * @return customFields.get()
     */
    @Override
    public UniqueCustomFieldList getCustomFieldList() {
        return customFields.get();
    }

    public ObjectProperty<UniqueCustomFieldList> customFieldProperty() {
        return customFields;
    }

    /**
     * Replaces this person's custom fields with the custom fields in the argument custom fields set.
     */
    public void setCustomFields(Set<CustomField> replacement) {
        customFields.set(new UniqueCustomFieldList(replacement));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyPerson) other));
    }

    @Override
    public int compareTo(Person other) {
        return this.getName().toString().compareToIgnoreCase(other.getName().toString());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, primaryPhone, email, address, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
