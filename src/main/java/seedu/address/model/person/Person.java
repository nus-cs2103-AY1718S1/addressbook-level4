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
public class Person implements ReadOnlyPerson, Comparable<Person> {

    private ObjectProperty<Name> name;
    private ObjectProperty<Phone> phone;
    private ObjectProperty<Email> email;
    private ObjectProperty<Address> address;
    private ObjectProperty<Remark> remark;
    private ObjectProperty<Birthday> birthday;
    private ObjectProperty<UniqueTagList> tags;
    private ObjectProperty<ProfilePicture> image;
    private ObjectProperty<Favourite> favourite;
    private ObjectProperty<NumTimesSearched> numTimesSearched;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Remark remark, Birthday birthday,
                  Set<Tag> tags, ProfilePicture image, Favourite favourite, NumTimesSearched numTimesSearched) {
        requireAllNonNull(name, phone, email, address, remark, birthday, tags);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.remark = new SimpleObjectProperty<>(remark);
        this.birthday = new SimpleObjectProperty<>(birthday);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        this.image = new SimpleObjectProperty<>(image);
        this.favourite = new SimpleObjectProperty<>(favourite);
        this.numTimesSearched = new SimpleObjectProperty<>(numTimesSearched);
    }

    public Person(Name name, Phone phone, Email email, Address address, Remark remark, Birthday birthday,
                  Set<Tag> tags, ProfilePicture image, Favourite favourite) {
        this(name, phone, email, address, remark, birthday, tags, image, favourite, new NumTimesSearched());
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getRemark(),
                source.getBirthday(), source.getTags(),
                source.getPicture(), source.getFavourite(), source.getNumTimesSearched());
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

    //@@author liliwei25
    @Override
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

    @Override
    public ObjectProperty<Birthday> birthdayProperty() {
        return birthday;
    }

    @Override
    public void setBirthday(Birthday birthday) {
        this.birthday.set(requireNonNull(birthday));
    }

    @Override
    public Birthday getBirthday() {
        return birthday.get();
    }

    @Override
    public int getDay() {
        return birthday.get().getDay();
    }

    @Override
    public int getMonth() {
        return birthday.get().getMonth();
    }

    @Override
    public Favourite getFavourite() {
        return favourite.get();
    }

    public void setFavourite(Favourite favourite) {
        this.favourite.set(requireNonNull(favourite));
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

    @Override
    public ObjectProperty<ProfilePicture> imageProperty() {
        return image;
    }

    @Override
    public ProfilePicture getPicture() {
        return image.get();
    }

    @Override
    public void setImage(String img) {
        image.set(new ProfilePicture(img));
    }

    //@@author thehelpfulbees

    @Override
    public NumTimesSearched getNumTimesSearched() {
        return numTimesSearched.get();
    }

    @Override
    public void incrementNumTimesSearched() {
        this.numTimesSearched.get().incrementValue();
    }

    //@@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyPerson) other));
    }

    @Override
    public int compareTo(Person otherPerson) {
        return this.name.toString().toUpperCase().compareTo(otherPerson.name.toString().toUpperCase());
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
