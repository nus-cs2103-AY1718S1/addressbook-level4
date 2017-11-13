package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

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
    private ObjectProperty<Boolean> favourite;
    private ObjectProperty<Remark> remark;
    private ObjectProperty<UniqueTagList> tags;
    private ObjectProperty<Avatar> avatar;
    private int privacyLevel = 2;

    /**
     * Every field must be present and not null.
     */

    public Person(Name name, Phone phone, Email email, Address address,
                  Boolean favourite, Remark remark, Avatar avatar, Set<Tag> tags) {
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.favourite = new SimpleObjectProperty<>(favourite);
        this.remark = new SimpleObjectProperty<>(remark);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        this.avatar = new SimpleObjectProperty<>(avatar);
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getFavourite(),
             source.getRemark(), source.getAvatar(), source.getTags());
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
    //@@author wangyiming1019
    public void setFavourite(Boolean favourite) {
        this.favourite.set(requireNonNull(favourite));
    }

    @Override
    public ObjectProperty<Boolean> favouriteProperty() {
        return favourite;
    }

    @Override
    public Boolean getFavourite() {
        return favourite.get();
    }
    //@@author

    //@@author charlesgoh
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

    public void setAvatar(Avatar avatar) {
        this.avatar.set(requireNonNull(avatar));
    }

    @Override
    public ObjectProperty<Avatar> avatarProperty() {
        return avatar;
    }

    @Override
    public Avatar getAvatar() {
        return avatar.get();
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
        return Objects.hash(name, phone, email, address, favourite, remark, tags, avatar);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    //@@author jeffreygohkw
    @Override
    public void setPrivacyLevel(int level) {
        this.getName().setPrivacyLevel(level);
        this.getPhone().setPrivacyLevel(level);
        this.getEmail().setPrivacyLevel(level);
        this.getAddress().setPrivacyLevel(level);
        this.getRemark().setPrivacyLevel(level);
        this.privacyLevel = level;
    }

    public int getPrivacyLevel() {
        return this.privacyLevel;
    }

    /**
     * Returns true if the Person has at least one private field and false otherwise
     */
    @Override
    public boolean hasPrivateField() {
        return (this.getName().getIsPrivate() || this.getPhone().getIsPrivate() || this.getAddress().getIsPrivate()
                || this.getEmail().getIsPrivate() || this.getRemark().getIsPrivate());
    }
}
