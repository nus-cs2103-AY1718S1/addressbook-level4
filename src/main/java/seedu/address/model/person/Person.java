package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Comparator;
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
public class Person implements ReadOnlyPerson, Comparator<Person> {

    private ObjectProperty<Name> name;
    private ObjectProperty<Phone> phone;
    private ObjectProperty<Email> email;
    private ObjectProperty<Address> address;
    private ObjectProperty<Birthday> birthday;
    private ObjectProperty<UserName> twitterName;
    private ObjectProperty<UserName> instagramName;
    private ObjectProperty<DisplayPic> displayPic;

    private ObjectProperty<UniqueTagList> tags;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Birthday birthday, UserName twitterName,
                  UserName instagramName, DisplayPic displayPic, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, birthday, tags);

        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.birthday = new SimpleObjectProperty<>(birthday);
        this.twitterName = new SimpleObjectProperty<>(twitterName);
        this.instagramName = new SimpleObjectProperty<>(instagramName);
        this.displayPic = new SimpleObjectProperty<>(displayPic);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getBirthday(),
                source.getTwitterName(), source.getInstagramName(), source.getDisplayPic(), source.getTags());
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

    public String getLowerCaseNameToString() {
        return name.get().toString().toLowerCase();
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
    //@@author conantteo
    @Override
    public ObjectProperty<Birthday> birthdayProperty() {
        return birthday;
    }

    @Override
    public Birthday getBirthday() {
        return birthday.get();
    }

    public void setBirthday(Birthday birthday) {
        this.birthday.set(requireNonNull(birthday));
    }
    //@@author
    public void setDisplayPic(DisplayPic displayPic) {
        this.displayPic.set(requireNonNull(displayPic));
    }

    @Override
    public ObjectProperty<DisplayPic> displayPicProperty() {
        return displayPic;
    }

    @Override
    public DisplayPic getDisplayPic() {
        return displayPic.get();
    }

    //@@author danielbrzn
    @Override
    public ObjectProperty<UserName> twitterNameProperty() {
        return twitterName;
    }

    @Override
    public UserName getTwitterName() {
        return twitterName.get();
    }

    public void setTwitterName(UserName twitterName) {
        this.twitterName.set(requireNonNull(twitterName));
    }

    @Override
    public ObjectProperty<UserName> instagramNameProperty() {
        return instagramName;
    }

    @Override
    public UserName getInstagramName() {
        return instagramName.get();
    }

    public void setInstagramName(UserName instagramName) {
        this.instagramName.set(requireNonNull(instagramName));
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
        return Objects.hash(name, phone, email, address, birthday, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public int compare(Person p1, Person p2) {
        return p1.name.toString().compareTo(p2.name.toString());
    }

}
