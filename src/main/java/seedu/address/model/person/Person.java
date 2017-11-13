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
    private ObjectProperty<Avatar> avatar;
    private ObjectProperty<NokName> nokName;
    private ObjectProperty<NokPhone> nokPhone;

    private ObjectProperty<UniqueTagList> tags;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Avatar avatar, NokName nokName,
                  NokPhone nokPhone, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, nokName, nokPhone, tags);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.avatar = new SimpleObjectProperty<>(avatar);
        this.nokName = new SimpleObjectProperty<>(nokName);
        this.nokPhone = new SimpleObjectProperty<>(nokPhone);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(),  source.getAvatar(),
                source.getNokName(), source.getNokPhone(), source.getTags());
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

    //@@author yuheng222
    public void setAvatar(Avatar avatar) {
        this.avatar.set(avatar);
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

    public void setNokName(NokName nokName) {
        this.nokName.set(requireNonNull(nokName));
    }

    @Override
    public ObjectProperty<NokName> nokNameProperty() {
        return nokName;
    }

    @Override
    public NokName getNokName() {
        return nokName.get();
    }

    public void setNokPhone(NokPhone nokPhone) {
        this.nokPhone.set(requireNonNull(nokPhone));
    }

    @Override
    public ObjectProperty<NokPhone> nokPhoneProperty() {
        return nokPhone;
    }

    @Override
    public NokPhone getNokPhone() {
        return nokPhone.get();
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
     * Removes a tag from this person's tag list.
     */
    public boolean removeTags(Tag tag) {
        Set<Tag> tagList = tags.get().toSet();
        if (tagList != null) {
            if (tagList.remove(tag)) {
                setTags(tagList);
                return true;
            }
            ;
        }
        return false;
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
        return Objects.hash(name, phone, email, address, avatar, nokName, nokPhone, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
