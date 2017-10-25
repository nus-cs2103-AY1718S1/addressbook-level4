package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.group.Group;
import seedu.address.model.group.ReadOnlyGroup;
import seedu.address.model.group.UniqueGroupList;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.NoPersonsException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.schedule.ReadOnlySchedule;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.UniqueScheduleList;
import seedu.address.model.schedule.exceptions.DuplicateScheduleException;
import seedu.address.model.schedule.exceptions.ScheduleNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;


/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueTagList tags;
    private final UniqueGroupList groups;
    private final UniqueScheduleList schedules;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        tags = new UniqueTagList();
        groups = new UniqueGroupList();
        schedules = new UniqueScheduleList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons, Tags and Groups in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setPersons(List<? extends ReadOnlyPerson> persons) throws DuplicatePersonException {
        this.persons.setPersons(persons);
    }

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    public void setGroups(List<? extends ReadOnlyGroup> groups) throws DuplicateGroupException {
        this.groups.setGroups(groups);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        try {
            setPersons(newData.getPersonList());
        } catch (DuplicatePersonException e) {
            assert false : "AddressBooks should not have duplicate persons";
        }

        setTags(new HashSet<>(newData.getTagList()));
        syncMasterTagListWith(persons);

        try {
            setGroups(newData.getGroupList());
        } catch (DuplicateGroupException e) {
            assert false : "AddressBooks should not have duplicate groups";
        }
        syncMasterGroupListWith(persons);
    }

    //// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(ReadOnlyPerson p) throws DuplicatePersonException {
        Person newPerson = new Person(p);
        syncMasterTagListWith(newPerson);
        syncMasterGroupListWith(newPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.add(newPerson);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedReadOnlyPerson}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedReadOnlyPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncMasterTagListWith(Person)
     */
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedReadOnlyPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedReadOnlyPerson);

        Person editedPerson = new Person(editedReadOnlyPerson);
        syncMasterTagListWith(editedPerson);
        syncMasterGroupListWith(editedPerson);
        // TODO: the tags master list and the groups master list will be updated even though the below line fails.
        // This can cause the tags master list and group master list to have additional
        // tags or groups that are not tagged to any person
        // in the person list.
        persons.setPerson(target, editedPerson);
    }

    /**
     * Sorts persons in address book.
     */

    public void sortPerson(Comparator<ReadOnlyPerson> sortComparator, boolean isReverseOrder)
            throws NoPersonsException {
        persons.sort(sortComparator, isReverseOrder);
    }

    /**
     * Ensures that every tag in this person:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(Person person) {
        final UniqueTagList personTags = new UniqueTagList(person.getTags());
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        person.setTags(correctTagReferences);
    }

    /**
     * Ensures that every tag in these persons:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     *  @see #syncMasterTagListWith(Person)
     */
    private void syncMasterTagListWith(UniquePersonList persons) {
        persons.forEach(this::syncMasterTagListWith);
    }

    /**
     * Ensures that every group in this person:
     *  - exists in the master list {@link #groups}
     *  - points to a Tag object in the master list
     */
    private void syncMasterGroupListWith(Person person) {
        final UniqueGroupList personGroups = new UniqueGroupList(person.getGroups());
        groups.mergeFrom(personGroups);

        // Create map with values = group object references in the master list
        // used for checking person group references
        final Map<Group, Group> masterGroupObjects = new HashMap<>();
        groups.forEach(group -> masterGroupObjects.put(group, group));

        // Rebuild the list of person groups to point to the relevant groups in the master group list.
        final Set<Group> correctGroupReferences = new HashSet<>();
        personGroups.forEach(group -> correctGroupReferences.add(masterGroupObjects.get(group)));
        person.setGroups(correctGroupReferences);
    }

    /**
     * Ensures that every group in these persons:
     *  - exists in the master list {@link #groups}
     *  - points to a Group object in the master list
     *  @see #syncMasterGroupListWith(Person)
     */
    private void syncMasterGroupListWith(UniquePersonList persons) {
        persons.forEach(this::syncMasterGroupListWith);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removePerson(ReadOnlyPerson key) throws PersonNotFoundException {
        if (persons.remove(key)) {
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// group-level operations

    /**
     * Adds a group to the address book.
     *
     * @throws DuplicateGroupException if an equivalent group already exists.
     */

    public void addGroup(ReadOnlyGroup g) throws DuplicateGroupException {
        Group newGroup = new Group(g);
        groups.add(newGroup);
    }

    /**
     * Adds a person to a group in the address book.
     *
     * @throws GroupNotFoundException if group does not exist.
     * @throws PersonNotFoundException if person does not exist.
     * @throws DuplicatePersonException if an equivalent person already exists.
     *
     */

    public void addPersonToGroup(Index targetGroup, ReadOnlyPerson toAdd)
            throws GroupNotFoundException, PersonNotFoundException, DuplicatePersonException {
        groups.addPersonToGroup(targetGroup, toAdd);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws GroupNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeGroup(ReadOnlyGroup key) throws GroupNotFoundException {
        if (groups.remove(key)) {
            return true;
        } else {
            throw new GroupNotFoundException();
        }
    }

    //// schedule-level operations

    /**
     * Adds a schedule to the address book.
     *
     * @throws DuplicateScheduleException if an equivalent schedule already exists.
     */

    public void addSchedule(ReadOnlySchedule s) throws DuplicateScheduleException {
        Schedule newSchedule = new Schedule(s);
        schedules.add(newSchedule);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws ScheduleNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeSchedule(ReadOnlySchedule key) throws ScheduleNotFoundException {
        if (schedules.remove(key)) {
            return true;
        } else {
            throw new ScheduleNotFoundException();
        }
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, "
                + groups.asObservableList().size() + " groups, "
                + tags.asObservableList().size() +  " tags";

        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyPerson> getPersonList() {
        return persons.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public ObservableList<ReadOnlyGroup> getGroupList() {
        return groups.asObservableList();
    }

    @Override
    public ObservableList<ReadOnlySchedule> getScheduleList() {
        return schedules.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags))
                && this.groups.equalsOrderInsensitive(((AddressBook) other).groups);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags, groups);
    }
}
