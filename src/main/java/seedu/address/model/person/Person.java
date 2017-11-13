package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.schedule.Schedule;
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
    private ObjectProperty<Mrt> mrt;

    private ObjectProperty<UniqueTagList> tags;

    private ObjectProperty<Schedule> schedule;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Mrt mrt, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.mrt = new SimpleObjectProperty<>(mrt);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        initiateSchedule();
    }

    //@@author YuchenHe98
    public Person(Name name, Phone phone, Email email, Address address, Mrt mrt, Set<Tag> tags, Schedule schedule) {
        requireAllNonNull(name, phone, email, address, tags, schedule);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.mrt = new SimpleObjectProperty<>(mrt);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        this.schedule = new SimpleObjectProperty<>(schedule);
    }
    //@@author

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(),
                source.getMrt(), source.getTags());
        setSchedule(source.getSchedule());
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

    public void setMrt(Mrt mrt) {
        this.mrt.set(requireNonNull(mrt));
    }

    @Override
    public ObjectProperty<Mrt> mrtProperty() {
        return mrt;
    }

    @Override
    public Mrt getMrt() {
        return mrt.get();
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

    //@@author hj2304
    /**
     * adds an eventTag to the person
     * @param event
     */
    public void addEventTag(Tag event) {
        Set<Tag> tags = getTags();
        Set<Tag> tagSet = new HashSet<Tag>();
        for (Tag t : tags) {
            tagSet.add(t);
        }
        tagSet.add(event);
        setTags(tagSet);
    }

    //@@author YuchenHe98
    @Override
    public Schedule getSchedule() {
        return schedule.get();
    }

    //@@author YuchenHe98
    public ObjectProperty<Schedule> scheduleProperty() {
        return schedule;
    }

    //@@author YuchenHe98
    /**
     * Create an empty schedule object
     */
    public void initiateSchedule() {
        Schedule schedule = new Schedule();
        this.schedule = new SimpleObjectProperty<>(schedule);
    }

    //@@author YuchenHe98
    /**
     * Set the person's schedule based on a given schedule.
     */
    public void setSchedule(Schedule schedule) {
        this.schedule.set(schedule);
    }

    //@@author YuchenHe98
    /**
     * Add a time span to a person's schedule to indicate that he is free at this time.
     */
    public void addSpanToSchedule(TreeSet<Integer> span) {
        for (Integer startTime : span) {
            getSchedule().addTime(startTime);
        }
    }

    //@@author YuchenHe98
    /**
     *Clear a time span to a person's schedule to indicate that he is busy at this time.
     */
    public void clearSpanForSchedule(TreeSet<Integer> span) {
        for (Integer startTime : span) {
            getSchedule().clearTime(startTime);
        }
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
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
}
