package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireNotAllNull;

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
    private ObjectProperty<Birthday> birthday;
    private ObjectProperty<Address> address;
    private ObjectProperty<ProfilePage> profile;
    private ObjectProperty<UniqueTagList> tags;
    private ObjectProperty<Favorite> favorite;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Birthday birthday, Address address,
                  ProfilePage profile, Favorite favorite, Set<Tag> tags) {
        requireNotAllNull(name, phone, email, birthday, address, tags);
        //if(name!=null)
        this.name = new SimpleObjectProperty<>(name);
        //if(phone!=null)
        this.phone = new SimpleObjectProperty<>(phone);
        //if(email!=null)
        this.email = new SimpleObjectProperty<>(email);
        //if(birthday!=null)
        this.birthday = new SimpleObjectProperty<>(birthday);
        //if(address!=null)
        this.address = new SimpleObjectProperty<>(address);
        //if(profile!=null)
        this.profile = new SimpleObjectProperty<>(profile);
        //if(profile!=null)
        this.favorite = new SimpleObjectProperty<>(favorite);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));

    }

    public Person(Name name) {
        requireNonNull(name);
        this.name = new SimpleObjectProperty<>(name);
    }


    //@@author yanji1221
    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getBirthday(), source.getAddress(),
                source.getProfilePage(), source.getFavorite(), source.getTags());
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

    //@@author hxy0229
    @Override
    public Favorite getFavorite() {
        return favorite.get();
    }

    public void setFavorite(Favorite favorite) {
        this.favorite.set(requireNonNull(favorite));
    }

    @Override
    public ObjectProperty<Favorite> favoriteProperty() {
        return favorite;
    }
    //@@author

    @Override
    public Email getEmail() {
        return email.get();
    }
    //@@author yanji1221
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

    //@@author quangtdn
    public void setProfilePage(ProfilePage profile) {
        this.profile.set(requireNonNull(profile));
    }

    @Override
    public ObjectProperty<ProfilePage> profilepageProperty() {
        return profile;
    }

    @Override
    public ProfilePage getProfilePage() {
        return profile.get();
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
    public void setTags(Set<Tag> replacementTag) {
        tags.set(new UniqueTagList(replacementTag));
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
        return Objects.hash(name, phone, email, birthday, address, profile,  tags);
    }


    @Override
    public String toString() {
        return getAsText();
    }

}
