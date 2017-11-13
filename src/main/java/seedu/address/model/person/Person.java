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
    private ObjectProperty<Avatar> avatar;
    private ObjectProperty<Comment> comment;
    private ObjectProperty<Appoint> appoint;

    private ObjectProperty<UniqueTagList> tags;

    /**
     * Every field must be present and not null.
     * A default avatar image is stored
     */
    public Person(Name name, Phone phone, Email email, Address address, Comment comment, Appoint appoint,
                  Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        // Use default avatar image
        this.avatar = new SimpleObjectProperty<>(new Avatar());
        this.comment = new SimpleObjectProperty<>(comment);
        this.appoint = new SimpleObjectProperty<>(appoint);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
    }
    //@@author vsudhakar
    public Person(Name name, Phone phone, Email email, Address address, Comment comment, Appoint appoint, Avatar avatar,
                  Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        // Use default avatar image
        this.avatar = new SimpleObjectProperty<>(avatar);
        this.comment = new SimpleObjectProperty<>(comment);
        this.appoint = new SimpleObjectProperty<>(appoint);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
    }
    //@@author


    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getComment(),
                source.getAppoint(), source.getAvatar(), source.getTags());
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

    @Override
    public ObjectProperty<Avatar> avatarProperty() {
        return avatar;
    }

    //@@author vsudhakar
    @Override
    public Avatar getAvatar() {
        return avatar.get();
    }

    public void setAvatar(Avatar avatar) {
        this.avatar.set(requireNonNull(avatar));
    }
    //@@author

    //@@author risashindo7
    public void setComment(Comment comment) {
        this.comment.set(requireNonNull(comment));
    }

    @Override
    public ObjectProperty<Comment> commentProperty() {
        return comment;
    }

    @Override
    public Comment getComment() {
        return comment.get();
    }


    public void setAppoint(Appoint appoint) {
        this.appoint.set(requireNonNull(appoint));
    }

    @Override
    public ObjectProperty<Appoint> appointProperty() {
        return appoint;
    }

    @Override
    public Appoint getAppoint() {
        return appoint.get();
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
    public boolean containTags(List<String> tagsList) {
        int matchTagsCount = 0;
        int numberOfKeywords = tagsList.size();
        for (Tag t : this.tags.get().toSet()) {
            boolean exist = tagsList.stream().anyMatch(tag -> t.tagName.equals(tag));
            if (exist) {
                matchTagsCount++;
            }
        }

        return matchTagsCount == numberOfKeywords;
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
        return Objects.hash(name, phone, email, address, comment, appoint, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
