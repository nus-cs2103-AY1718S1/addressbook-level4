package seedu.address.model.place;

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
 * Represents a Place in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Place implements ReadOnlyPlace, Comparable<Place> {

    private ObjectProperty<Name> name;
    private ObjectProperty<Phone> phone;
    private ObjectProperty<Website> website;
    private ObjectProperty<Address> address;
    private ObjectProperty<PostalCode> postalcode;

    private ObjectProperty<UniqueTagList> tags;

    /**
     * Every field must be present and not null.
     */
    public Place(Name name, Phone phone, Website website, Address address, PostalCode postalcode, Set<Tag> tags) {
        requireAllNonNull(name, phone, website, address, postalcode, tags);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.website = new SimpleObjectProperty<>(website);
        this.address = new SimpleObjectProperty<>(address);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        this.postalcode = new SimpleObjectProperty<>(postalcode);
    }

    /**
     * Creates a copy of the given ReadOnlyPlace.
     */
    public Place(ReadOnlyPlace source) {
        this(source.getName(), source.getPhone(), source.getWebsite(), source.getAddress(), source.getPostalCode(),
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

    public void setWebsite(Website website) {
        this.website.set(requireNonNull(website));
    }

    @Override
    public ObjectProperty<Website> websiteProperty() {
        return website;
    }

    @Override
    public Website getWebsite() {
        return website.get();
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

    public void setPostalcode(PostalCode postalcode) {
        this.postalcode.set(requireNonNull(postalcode));
    }

    @Override
    public ObjectProperty<PostalCode> postalcodeProperty() {
        return postalcode;
    }

    @Override
    public PostalCode getPostalCode() {
        return postalcode.get();
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
     * Replaces this place's tags with the tags in the argument tag set.
     */
    public void setTags(Set<Tag> replacement) {
        tags.set(new UniqueTagList(replacement));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPlace // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyPlace) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, website, address, postalcode, tags);
    }

    //@@author aungmyin23
    @Override
    public int compareTo(Place otherPlace) {
        int toUpdate = this.name.toString().toUpperCase().compareTo((otherPlace.name.toString().toUpperCase()));
        return toUpdate;
    }
    //@@author

    @Override
    public String toString() {
        return getAsText();
    }

}
