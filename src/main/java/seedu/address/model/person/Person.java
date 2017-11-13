package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.group.Group;
import seedu.address.model.group.UniqueGroupList;
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
    private ObjectProperty<Email> email;
    private ObjectProperty<Address> address;
    private ObjectProperty<UniqueTagList> tags;

    /**
     *  A Person will have an empty group list by default
     */
    private ObjectProperty<UniqueGroupList> groups = new SimpleObjectProperty<>(new UniqueGroupList());

    /**
     *  A Person will not be marked as favourite by default
     */
    private ObjectProperty<Favourite> favourite = new SimpleObjectProperty<>(new Favourite(false));
    /**
     *  A Person will have a generic profile picture by default. will search in /resources by default?
     */
    private ObjectProperty<ProfPic> profPic = new SimpleObjectProperty<>(new ProfPic("maleIcon.png"));

    /**
    *  A Person will have an empty schedule list by default
    */
    private ObjectProperty<UniqueScheduleList> schedule = new SimpleObjectProperty<>(new UniqueScheduleList());

    /**
     * Every field must be present and not null.
     * Standard Constructor
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
    }

    //@@author nassy93
    /**
     * Every field must be present and not null.
     * Constructor for Favourite feature
     */
    public Person(Name name, Phone phone, Email email, Address address, Favourite favourite,
                  ProfPic profPic, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.favourite = new SimpleObjectProperty<>(favourite);
        this.profPic = new SimpleObjectProperty<>(profPic);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
    }

    //@@author cjianhui
    /**
     * Every field must be present and not null.
     * Constructor for Schedule feature
     */
    public Person(Name name, Phone phone, Email email, Address address, Favourite favourite,
                  Set<Tag> tags, Set<Schedule> schedule) {
        requireAllNonNull(name, phone, email, address, favourite, tags, schedule);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.favourite = new SimpleObjectProperty<>(favourite);
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        this.schedule = new SimpleObjectProperty<>(new UniqueScheduleList(schedule));
    }

    //@@author cjianhui
    /**
     * Every field must be present and not null.
     * Constructor for Group feature
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Set<Group> groups) {
        requireAllNonNull(name, phone, email, address, tags, groups);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        // protect internal groups from changes in the arg list
        this.groups = new SimpleObjectProperty<>(new UniqueGroupList(groups));
    }

    //@@author
    /**
     * Every field must be present and not null.
     * Constructor for XMLAdaptedPerson
     */
    public Person(Name name, Phone phone, Email email, Address address, Favourite favourite,
                  ProfPic profPic, Set<Tag> tags, Set<Group> groups, Set<Schedule> schedule) {
        requireAllNonNull(name, phone, email, address, tags, groups, schedule);

        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.favourite = new SimpleObjectProperty<>(favourite);
        this.profPic = new SimpleObjectProperty<>(profPic);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        // protect internal groups from changes in the arg list
        this.groups = new SimpleObjectProperty<>(new UniqueGroupList(groups));
        this.schedule = new SimpleObjectProperty<>(new UniqueScheduleList(schedule));
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getFavourite(),
                source.getProfPic(), source.getTags(), source.getGroups(), source.getSchedule());
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

    public void setFavourite(Favourite fav) {
        this.favourite.set(fav);
    }

    @Override
    public ObjectProperty<Favourite> favouriteProperty() {
        return favourite;
    }

    @Override
    public Favourite getFavourite() {
        return favourite.get();
    }

    public void setProfPic(ProfPic profPic) {
        this.profPic.set(profPic);
    }

    @Override
    public ObjectProperty<ProfPic> profPicProperty() {
        return profPic;
    }

    @Override
    public ProfPic getProfPic() {
        return profPic.get();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.get().toSet());
    }

    @Override
    public Set<Group> getGroups() {
        return groups.get().toSet();
    }

    @Override
    public Set<Schedule> getSchedule() {
        return Collections.unmodifiableSet(schedule.get().toSet());
    }

    @Override
    public ObjectProperty<UniqueTagList> tagProperty() {
        return tags;
    }

    @Override
    public ObjectProperty<UniqueGroupList> groupProperty() {
        return groups;
    }

    @Override
    public ObjectProperty<UniqueScheduleList> scheduleProperty() {
        return schedule;
    }
    /**
     * Replaces this person's tags with the tags in the argument tag set.
     */
    public void setTags(Set<Tag> replacement) {
        tags.set(new UniqueTagList(replacement));
    }

    /**
     * Replaces this person's groups with the groups in the argument group set.
     */
    public void setGroups(Set<Group> replacement) {
        groups.set(new UniqueGroupList(replacement));
    }

    /**
     * Replaces this person's schedule with the schedule in the argument schedule set.
     */
    public void setSchedule(Set<Schedule> replacement) {
        schedule.set(new UniqueScheduleList(replacement)); }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyPerson) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, groups);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
