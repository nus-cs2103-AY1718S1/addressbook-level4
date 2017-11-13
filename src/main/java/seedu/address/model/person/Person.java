package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.List;
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
    private ObjectProperty<Birthday> birthday;
    private ObjectProperty<Remark> remark;
    private ObjectProperty<Favorite> favorite;
    private ObjectProperty<Major> major;
    private ObjectProperty<Facebook> facebook;

    private ObjectProperty<UniqueTagList> tags;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address,
                  Birthday birthday, Remark remark, Major major, Facebook facebook, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, birthday, tags);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.birthday = new SimpleObjectProperty<>(birthday);
        this.remark = new SimpleObjectProperty<>(remark);
        this.major = new SimpleObjectProperty<>(major);
        this.facebook = new SimpleObjectProperty<>(facebook);
        this.favorite = new SimpleObjectProperty<>(new Favorite());
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));

    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getBirthday(),
                source.getRemark(), source.getMajor(), source.getFacebook(), source.getTags());
        this.setFavorite(source.getFavorite());
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

    //@@author heiseish
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

    public void setFavorite(Favorite favorite) {
        this.favorite.set(requireNonNull(favorite));
    }

    @Override
    public ObjectProperty<Favorite> favoriteProperty() {
        return favorite;
    }

    @Override
    public Favorite getFavorite() {
        return favorite.get();
    }

    public void setMajor(Major major) {
        this.major.set(requireNonNull(major));
    }

    @Override
    public ObjectProperty<Major> majorProperty() {
        return major;
    }

    @Override
    public Major getMajor() {
        return major.get();
    }

    public void setFacebook(Facebook facebook) {
        this.facebook.set(requireNonNull(facebook));
    }

    @Override
    public ObjectProperty<Facebook> facebookProperty() {
        return facebook;
    }

    @Override
    public Facebook getFacebook() {
        return facebook.get();
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

    /**
     * Returns an immutable List of tagName
     */
    @Override
    public List<String> getTagsString() {
        return tags.get().toStringList();
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

    /**
     * Updates this person with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyPerson replacement) {
        requireNonNull(replacement);
        this.setName(replacement.getName());
        this.setPhone(replacement.getPhone());
        this.setEmail(replacement.getEmail());
        this.setAddress(replacement.getAddress());
        this.setBirthday(replacement.getBirthday());
        this.setRemark(replacement.getRemark());
        this.setFavorite(replacement.getFavorite());
        this.setMajor(replacement.getMajor());
        this.setFacebook(replacement.getFacebook());
        this.setTags(replacement.getTags());
    }

}
