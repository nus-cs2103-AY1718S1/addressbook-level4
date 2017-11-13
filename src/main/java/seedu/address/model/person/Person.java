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
    private ObjectProperty<HomeNumber> homeNumber;
    private ObjectProperty<Email> email;
    private ObjectProperty<SchEmail> schEmail;
    private ObjectProperty<Website> website;
    private ObjectProperty<Address> address;
    private ObjectProperty<Birthday> birthday;
    private ObjectProperty<Boolean> favourite;
    private ObjectProperty<UniqueTagList> tags;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, HomeNumber homeNumber, Email email, SchEmail schEmail,
                  Website website, Address address,
                  Birthday birthday, Boolean favourite, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, birthday, tags);

        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.homeNumber = new SimpleObjectProperty<>(homeNumber);
        this.email = new SimpleObjectProperty<>(email);
        this.schEmail = new SimpleObjectProperty<>(schEmail);
        this.website = new SimpleObjectProperty<>(website);
        this.address = new SimpleObjectProperty<>(address);
        this.birthday = new SimpleObjectProperty<>(birthday);
        this.favourite = new SimpleObjectProperty<>(favourite);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {

        this(source.getName(), source.getPhone(), source.getHomeNumber(), source.getEmail(), source.getSchEmail(),
                source.getWebsite(), source.getAddress(),
                source.getBirthday(), source.getFavourite(), source.getTags());
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

    //@@author chernghann
    public void setHomeNumber(HomeNumber homeNumber) {
        this.homeNumber.set(requireNonNull(homeNumber));
    }

    @Override
    public ObjectProperty<HomeNumber> homeNumberProperty() {
        return homeNumber;
    }

    @Override
    public HomeNumber getHomeNumber() {
        return homeNumber.get();
    }
    //@@author

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

    //@@author chernghann
    public void setSchEmail(SchEmail schEmail) {
        this.schEmail.set(requireNonNull(schEmail));
    }

    @Override
    public ObjectProperty<SchEmail> schEmailProperty() {
        return schEmail;
    }

    @Override
    public SchEmail getSchEmail() {
        return schEmail.get();
    }

    //@@author DarrenCzen

    public void setWebsite(Website website) {
        this.website.set(requireNonNull(website));
    }
    //@@author

    @Override
    public ObjectProperty<Website> websiteProperty() {
        return website;
    }

    @Override
    public Website getWebsite() {
        return website.get();
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

    //@@author archthegit

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

    //@@author itsdickson
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

        return Objects.hash(name, phone, email, schEmail, website,
                address, birthday, favourite, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
