package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import seedu.address.model.person.email.Email;
import seedu.address.model.person.email.UniqueEmailList;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.UniqueScheduleList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Person implements ReadOnlyPerson {

    private ObjectProperty<Name> name;
    private ObjectProperty<Phone> phone;
    private ObjectProperty<Country> country;
    private ObjectProperty<UniqueEmailList> emails;
    private ObjectProperty<Address> address;
    private ObjectProperty<UniqueScheduleList> schedules;
    private ObjectProperty<UniqueTagList> tags;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Country country, Set<Email> emails, Address address, Set<Schedule> schedules,
                  Set<Tag> tags) {
        requireAllNonNull(name, phone, country, emails, address, schedules, tags);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.country = new SimpleObjectProperty<>(country);
        this.emails = new SimpleObjectProperty<>(new UniqueEmailList(emails));
        this.address = new SimpleObjectProperty<>(address);
        this.schedules = new SimpleObjectProperty<>(new UniqueScheduleList(schedules));
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getCountry(), source.getEmails(), source.getAddress(),
                source.getSchedules(), source.getTags());
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

    public void setCountry(Country country) {
        this.country.set(requireNonNull(country));
    }

    @Override
    public ObjectProperty<Country> countryProperty() {
        return country;
    }

    @Override
    public Country getCountry() {
        return country.get();
    }

    /**
    * Replaces this person's emails with the emails in the argument tag set.
    */
    public void setEmails(Set<Email> replacement) {
        emails.set(new UniqueEmailList(replacement));
    }

    @Override
    public ObjectProperty<UniqueEmailList> emailProperty() {
        return emails;
    }

    /**
     * Returns an immutable email set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Email> getEmails() {
        return Collections.unmodifiableSet(emails.get().toSet());
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

    /**
     * Replaces this person's schedules with the schedules in the argument schedule set.
     */
    public void setSchedules(Set<Schedule> replacement) {
        schedules.set(new UniqueScheduleList(replacement));
    }

    @Override
    public ObjectProperty<UniqueScheduleList> scheduleProperty() {
        return schedules;
    }

    /**
     * Returns an immutable schedule set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Schedule> getSchedules() {
        return Collections.unmodifiableSet(schedules.get().toSet());
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
        return Objects.hash(name, phone, emails, address, schedules, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
