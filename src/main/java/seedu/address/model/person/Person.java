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
    private ObjectProperty<Nickname> nickname;
    private ObjectProperty<Birthday> birthday;
    private ObjectProperty<DisplayPicture> displayPicture;
    private ObjectProperty<PopularityCounter> popularityCounter;


    private ObjectProperty<UniqueTagList> tags;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Birthday birthday,
                  Nickname nickname, DisplayPicture displayPicture, PopularityCounter popularityCounter,
                  Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, birthday, tags);

        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.nickname = new SimpleObjectProperty<>(nickname);
        this.birthday = new SimpleObjectProperty<>(birthday);
        this.displayPicture = new SimpleObjectProperty<>(displayPicture);
        this.popularityCounter = new SimpleObjectProperty<>(popularityCounter);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(),
             source.getBirthday(), source.getNickname(), source.getDisplayPicture(), source.getPopularityCounter(),
                source.getTags());
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

    public void setNickname(Nickname nickname) {
        this.nickname.set(requireNonNull(nickname));
    }

    @Override
    public ObjectProperty<Nickname> nicknameProperty() {
        return nickname;
    }

    @Override
    public Nickname getNickname() {
        return nickname.get();
    }

    //@@author justinpoh
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

    public void setDisplayPicture(DisplayPicture displayPicture) {
        this.displayPicture.set(requireNonNull(displayPicture));
    }

    @Override
    public ObjectProperty<DisplayPicture> displayPictureProperty() {
        return displayPicture;
    }

    @Override
    public DisplayPicture getDisplayPicture() {
        return displayPicture.get();
    }

    public void setPopularityCounter(PopularityCounter popularityCounter) {
        this.popularityCounter.set(requireNonNull(popularityCounter));
    }

    @Override
    public ObjectProperty<PopularityCounter> popularityCounterProperty() {
        return popularityCounter;
    }

    @Override
    public PopularityCounter getPopularityCounter() {
        return popularityCounter.get();
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
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyPerson) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, birthday, nickname, displayPicture, popularityCounter, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
