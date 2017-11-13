package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
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
    private ObjectProperty<ArrayList<Remark>> remark;
    private ObjectProperty<FavouriteStatus> favouriteStatus;

    private ObjectProperty<UniqueTagList> tags;
    private ObjectProperty<Link> link;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address,
                  ArrayList<Remark> remark, FavouriteStatus favouriteStatus, Set<Tag> tags, Link link) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.remark = new SimpleObjectProperty<>(remark);
        this.favouriteStatus = new SimpleObjectProperty<>(favouriteStatus);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        this.link = new SimpleObjectProperty<>(link);
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(),
                source.getRemark(), source.getFavouriteStatus(), source.getTags(), source.getLink());
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
    //@@author limyongsong
    public void setRemark(ArrayList<Remark> remark) {
        this.remark.set(requireNonNull(remark));
    }

    @Override
    public ObjectProperty<ArrayList<Remark>> remarkProperty() {
        return remark;
    }

    @Override
    public ArrayList<Remark> getRemark() {
        ArrayList<Remark> readOnlyRemarkList = new ArrayList<>(remark.get());
        return readOnlyRemarkList;
    }
    //@@author
    public void setFavouriteStatus(FavouriteStatus favouriteStatus) {
        this.favouriteStatus.set(requireNonNull(favouriteStatus));
    }

    @Override
    public ObjectProperty<FavouriteStatus> favouriteStatusProperty() {
        return favouriteStatus;
    }

    @Override
    public FavouriteStatus getFavouriteStatus() {
        return favouriteStatus.get();
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
    //@@author limyongsong
    public void setLink(Link link) {
        this.link.set(requireNonNull(link));
    }

    @Override
    public ObjectProperty<Link> linkProperty() {
        return link;
    }

    @Override
    public Link getLink() {
        return link.get();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyPerson) other));
    }
    //@@author
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
