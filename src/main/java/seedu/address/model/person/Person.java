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
    private ObjectProperty<Birthday> birthday;
    private ObjectProperty<Email> email;
    private ObjectProperty<Address> address;
    private ObjectProperty<Score> score;

    private ObjectProperty<UniqueTagList> tags;
    private ObjectProperty<Avatar> avatarPic;

    /**
     * Using default profile picture.
     */
    public Person(Name name, Phone phone, Birthday birthday, Email email, Address address, Score score, Set<Tag> tags) {
        this(name, phone, birthday, email, address, score, tags, new Avatar());
    }

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Birthday birthday, Email email, Address address, Score score, Set<Tag> tags,
                  Avatar avatar) {
        requireAllNonNull(name, phone, birthday, email, address, score, tags, avatar);

        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        //@@author siri99
        this.birthday = new SimpleObjectProperty<>(birthday);
        //@@author siri99
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.score = new SimpleObjectProperty<>(score);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        this.avatarPic = new SimpleObjectProperty<>(avatar);

    }

    /**
     * Creates a copy of the given ReadOnlyPerson, which is nice.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getBirthday(), source.getEmail(),
                source.getAddress(), source.getScore(), source.getTags(), source.getAvatarPic());
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

    //@@author siri99
    public void setBirthday(Birthday birthday)  {
        this.birthday.set(requireNonNull(birthday));
    }

    @Override
    public ObjectProperty<Birthday> birthdayProperty()  {
        return birthday;
    }

    @Override
    public Birthday getBirthday() {
        return birthday.get();
    }
    //@@author siri99

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
    //@@author Henning
    public void setScore(Score score) {
        this.score.set(requireNonNull(score));
    }

    @Override
    public ObjectProperty<Score> scoreProperty() {
        return score;
    }

    @Override
    public Score getScore() {
        return score.get();
    }

    @Override
    public Avatar getAvatarPic() {
        return avatarPic.get();
    }

    public void setAvatarPic(Avatar avatar) {
        this.avatarPic.set(requireNonNull(avatar));
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
        return Objects.hash(name, phone, birthday, email, address, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
