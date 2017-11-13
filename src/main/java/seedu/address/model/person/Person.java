package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
    private ObjectProperty<Remark> remark;
    //@@author zengfengw
    private ObjectProperty<Birthday> birthday;
    //@@author
    private ObjectProperty<Age> age;
    private ObjectProperty<Photo> photo;
    private ObjectProperty<UniqueTagList> tags;

    /**
     * Every field must be present and not null.
     */

    public Person(Name name, Phone phone, Email email, Address address, Remark remark,
                  Birthday birthday, Age age, Photo photo, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, remark, birthday, age, photo, tags);

        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.remark = new SimpleObjectProperty<>(remark);
        //@@author zengfengw
        this.birthday = new SimpleObjectProperty<>(birthday);
        this.age = new SimpleObjectProperty<>(age);
        //@@author
        this.photo = new SimpleObjectProperty<>(photo);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {

        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getRemark(),
             source.getBirthday(), source.getAge(), source.getPhoto(), source.getTags());
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

    //@@author zengfengw
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
    //@@author

    //@@author Affalen
    public void setRemark(Remark remark) {
        this.remark.set(requireNonNull(remark));
    }
    //@@author
    //@@author wishingmaid
    @Override
    public ObjectProperty<Photo> photoProperty() {
        return photo;
    }
    public Photo getPhoto() {
        return photo.get();
    }
    public void setPhoto(Photo photo) {
        this.photo.set(requireNonNull(photo));
    }
    //@@author wishingmaid
    //@@author Affalen
    @Override
    public ObjectProperty<Remark> remarkProperty() {
        return remark;
    }
    //@@author

    //@@author Affalen
    @Override
    public Remark getRemark() {
        return remark.get();
    }
    //@@author

    //@@author zengfengw
    //@Override
    public void setAge(Age age) {
        this.age.set(requireNonNull(age));
    }

    @Override
    public ObjectProperty<Age> ageProperty() {
        return age;
    }

    @Override
    public Age getAge() {
        return age.get();
    }
    //@@author

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

    /**
     * Updates this person with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyPerson replacement) {
        requireNonNull(replacement);

        this.setName(replacement.getName());
        this.setPhone(replacement.getPhone());
        this.setEmail(replacement.getEmail());
        this.setAddress(replacement.getAddress());
        //@@author zengfengw
        this.setBirthday(replacement.getBirthday());
        this.setAge(replacement.getAge());
        //@@author
        this.setTags(replacement.getTags());
    }

    /**
     * Comparator function for sort for age, in case of null field.
     */
    //@@author Estois
    public int compareAge(ReadOnlyPerson o1) {
        if (this.getAge().toString().equals("") && o1.getAge().toString().equals("")) {
            return 0;
        }

        if (this.getAge().toString().equals("")) {
            return 1;
        }

        if (o1.getAge().toString().equals("")) {
            return -1;
        }

        return this.getAge().toString().compareToIgnoreCase(o1.getAge().toString());
    }
    //@@author
}
