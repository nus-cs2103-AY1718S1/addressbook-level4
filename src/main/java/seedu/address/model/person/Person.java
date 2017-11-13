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
    private ObjectProperty<DateOfBirth> date;
    private ObjectProperty<Remark> remark;
    private ObjectProperty<UniqueTagList> tags;
    private ObjectProperty<FileImage> image;
    private ObjectProperty<FacebookUsername> username;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, DateOfBirth dateOfBirth,
                  Remark remark, FileImage image, FacebookUsername username, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.date = new SimpleObjectProperty<>(dateOfBirth);
        this.remark = new SimpleObjectProperty<>(remark);
        this.image = new SimpleObjectProperty<>(image);
        this.username = new SimpleObjectProperty<>(username);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<> (new UniqueTagList(tags));
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getDateOfBirth(),
                source.getRemark(), source.getImage(), source.getUsername(), source.getTags());
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
    public FacebookUsername getUsername() {
        return username.get();
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


    public void setRemark(Remark remark) {
        this.remark.set(requireNonNull(remark));
    }

    @Override
    public ObjectProperty<Remark> remarkProperty() {
        return remark;
    }

    @Override
    public Remark getRemark() {
        return remark.get();
    }
    //@@author RonakLakhotia
    @Override
    public FileImage getImage() {
        return image.get();
    }

    public ObjectProperty<FileImage> imageProperty() {
        return image;
    }

    public void setDateOfBirth(DateOfBirth date) {
        this.date.set(requireNonNull(date));
    }

    public void setImage(FileImage image) {
        this.image.set(requireNonNull(image));
    }
    @Override
    public ObjectProperty<DateOfBirth> dateOfBirthProperty() {
        return date;
    }

    @Override
    public  DateOfBirth getDateOfBirth() {
        return date.get();
    }

    public void setUsername(FacebookUsername username) {
        this.username.set(requireNonNull(username));
    }
    @Override
    public ObjectProperty<FacebookUsername> usernameProperty() {
        return username;
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
        return Objects.hash(name, phone, email, address, date, remark, image, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
