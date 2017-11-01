package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Person implements ReadOnlyPerson {

    private static final String DEFAULT_NAME = "Full Name";
    private static final String DEFAULT_OCCUPATION = "Google, Software engineer";
    private static final String DEFAULT_PHONE = "123456";
    private static final String DEFAULT_EMAIL = "fullname@gmail.com";
    private static final String DEFAULT_ADDRESS = "Singapore";
    private static final String DEFAULT_REMARK = "funny";
    private static final String DEFAULT_WEBSITE = "https://www.google.com";
    private static final String DEFAULT_TAG = "me";

    private ObjectProperty<Name> name;
    private ObjectProperty<Occupation> occupation;
    private ObjectProperty<Phone> phone;
    private ObjectProperty<Email> email;
    private ObjectProperty<Address> address;
    private ObjectProperty<Remark> remark;
    private ObjectProperty<Website> website;
    private ObjectProperty<UniqueTagList> tags;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Occupation occupation, Phone phone, Email email, Address address, Remark remark,
                  Website website, Set<Tag> tags) {
        requireAllNonNull(name, occupation, phone, email, address, remark, tags);
        this.name = new SimpleObjectProperty<>(name);
        this.occupation = new SimpleObjectProperty<>(occupation);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.remark = new SimpleObjectProperty<>(remark);
        this.website = new SimpleObjectProperty<>(website);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
    }

    public Person() {
        try {
            this.name = new SimpleObjectProperty<>(new Name(DEFAULT_NAME));
            this.occupation = new SimpleObjectProperty<>(new Occupation(DEFAULT_OCCUPATION));
            this.phone = new SimpleObjectProperty<>(new Phone(DEFAULT_PHONE));
            this.email = new SimpleObjectProperty<>(new Email(DEFAULT_EMAIL));
            this.address = new SimpleObjectProperty<>(new Address(DEFAULT_ADDRESS));
            this.remark = new SimpleObjectProperty<>(new Remark(DEFAULT_REMARK));
            this.website = new SimpleObjectProperty<>(new Website(DEFAULT_WEBSITE));
            List<Tag> tagList = new ArrayList<>();
            tagList.add(new Tag(DEFAULT_TAG));
            this.tags = new SimpleObjectProperty<>(new UniqueTagList(new HashSet<>(tagList)));
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getOccupation(), source.getPhone(), source.getEmail(), source.getAddress(),
                source.getRemark(), source.getWebsite(), source.getTags());
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

    //@@author JavynThun
    public void setOccupation(Occupation occupation) {
        this.occupation.set(requireNonNull(occupation));
    }

    @Override
    public ObjectProperty<Occupation> occupationProperty() {
        return occupation;
    }

    @Override
    public Occupation getOccupation() {
        return occupation.get();
    }
    //@@author

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

    //@@author
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
        return Objects.hash(name, occupation, phone, email, address, website, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public void resetData(ReadOnlyPerson replacement) {
        this.setOccupation(replacement.getOccupation());
        this.setPhone(replacement.getPhone());
        this.setEmail(replacement.getEmail());
        this.setAddress(replacement.getAddress());
        this.setRemark(replacement.getRemark());
        this.setWebsite(replacement.getWebsite());
        this.setTags(replacement.getTags());
    }

}
