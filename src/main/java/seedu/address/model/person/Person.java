package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Person implements ReadOnlyPerson {

    private ObjectProperty<Name> name;
    private ObjectProperty<Phone> phone;
    private ObjectProperty<EmailAddress> emailAddress;
    private ObjectProperty<Address> address;
    private ObjectProperty<Photo> photo;

    private ObjectProperty<UniqueTagList> tags;
    private ObjectProperty<Birthdate> birthdate;
    private ObjectProperty<NusModules> nusModules;
    private ObjectProperty<UserId> id;

    /**
     * Every field must be present and not null.
     */

    public Person(Name name, Phone phone, EmailAddress email, Address address, Photo photo, Set<Tag> tags,
                  Birthdate birthdate, UserId id) {
        requireAllNonNull(name, phone, email, address, tags, birthdate, id);

        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.emailAddress = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.photo = new SimpleObjectProperty<>(photo);
        this.birthdate = new SimpleObjectProperty<>(birthdate);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        this.id = new SimpleObjectProperty<>(id);

    }

    /**
     * Constructor which includes nusModule
     */

    public Person(Name name, Phone phone, EmailAddress email, Address address, Photo photo, Set<Tag> tags,
                  Birthdate birthdate, NusModules nusModules, UserId id) {
        requireAllNonNull(name, phone, email, address, tags, birthdate, id);

        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.emailAddress = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.photo = new SimpleObjectProperty<>(photo);
        this.birthdate = new SimpleObjectProperty<>(birthdate);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        this.nusModules = new SimpleObjectProperty<>(nusModules);
        this.id = new SimpleObjectProperty<>(id);

    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmailAddress(), source.getAddress(),
                source.getPhoto(), source.getTags(), source.getBirthdate(), source.getNusModules(), source.getUserId());
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

    public void setEmailAddress(EmailAddress emailAddress) {
        this.emailAddress.set(requireNonNull(emailAddress));
    }

    @Override
    public ObjectProperty<EmailAddress> emailAddressProperty() {
        return emailAddress;
    }

    @Override
    public EmailAddress getEmailAddress() {
        return emailAddress.get();
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

    //@@author hengyu95
    @Override
    public ObjectProperty<Birthdate> birthdateProperty() {
        return birthdate;
    }

    @Override
    public Birthdate getBirthdate() {
        return birthdate.get();
    }

    public void setBirthdate(Birthdate birthdate) {
        this.birthdate.set(requireNonNull(birthdate));
    }

    @Override
    public ObjectProperty<UserId> userIdProperty() {
        return id;
    }

    @Override
    public UserId getUserId() {
        return id.get();
    }

    public void setUserId(UserId id) {
        this.id.set(requireNonNull(id));
    }
    //@@author


    public void setPhoto(Photo photo) {
        this.photo.set(photo);
    }

    @Override
    public ObjectProperty<Photo> photoProperty() {
        return photo;
    }

    @Override
    public Photo getPhoto() {
        return photo.get();
    }

    //@@author ritchielq
    @Override
    public ObjectProperty<NusModules> nusModulesProperty() {
        return nusModules;
    }

    @Override
    public NusModules getNusModules() {
        if (nusModules == null) {
            return new NusModules();
        }
        return nusModules.get();
    }

    public void setNusModules(NusModules nusModules) {
        if (this.nusModules == null) {
            this.nusModules = new SimpleObjectProperty<>(nusModules);
        } else {
            this.nusModules.set(nusModules);
        }
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

    //@@author awarenessxz
    /**
     * Returns true if all tags in {@code List<String> tags} are found in this {@code tags}
     */
    @Override
    public boolean containsTags(List<String> tags) {
        for (Tag t : this.tags.get().toSet()) {
            boolean found = tags.stream().anyMatch(tag ->
                    StringUtil.containsNonFullWordIgnoreCase(t.tagName, tag));
            if (found) {
                return true;
            }
        }
        return false;
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyPerson) other))
                || (other instanceof ReadOnlyPerson && this.isSameEmail((ReadOnlyPerson) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, emailAddress, address, photo, tags, birthdate);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
