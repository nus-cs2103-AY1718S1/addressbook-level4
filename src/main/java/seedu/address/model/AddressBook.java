package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.UniqueScheduleList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueScheduleList schedules;
    private final UniqueTagList tags;
    private final UniqueScheduleList schedulesToRemind;

    private Logger logger = LogsCenter.getLogger(AddressBook.class);
    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        schedules = new UniqueScheduleList();
        tags = new UniqueTagList();
        schedulesToRemind = new UniqueScheduleList();
    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the Persons, Schedules and Tags in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setPersons(List<? extends ReadOnlyPerson> persons) throws DuplicatePersonException {
        this.persons.setPersons(persons);
    }

    //@@author CT15
    public void setSchedules(Set<Schedule> schedules) {
        this.schedules.setSchedules(schedules);
        this.schedules.sort();
    }

    //@@author 17navasaw
    public void setSchedulesToRemind() {
        ObservableList<Schedule> scheduleList = this.schedules.asObservableList();
        Set<Schedule> schedulesToRemind = new HashSet<>();

        for (Schedule schedule: scheduleList) {
            if (Schedule.doesScheduleNeedReminder(schedule)) {
                schedulesToRemind.add(schedule);
            }
        }

        this.schedulesToRemind.setSchedules(schedulesToRemind);
    }

    //@@author
    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
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

        setSchedules(new HashSet<>(newData.getScheduleList()));
        syncMasterScheduleListWith(persons);
        setTags(new HashSet<>(newData.getTagList()));
        syncMasterTagListWith(persons);
    }

    //// person-level operations

    /**
     * Adds a person to the address book.
     * Checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     * Checks the new person's schedules and updates {@link #schedules} with any new schedules found,
     * and updates the Schedule objects in the person to point to those in {@link #schedules}
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(ReadOnlyPerson p) throws DuplicatePersonException {
        Person newPerson = new Person(p);
        syncMasterScheduleListWith(newPerson);
        syncMasterTagListWith(newPerson);
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
     *                                  another existing person in the list.
     * @throws PersonNotFoundException  if {@code target} could not be found in the list.
     * @see #syncMasterTagListWith(Person)
     */
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedReadOnlyPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedReadOnlyPerson);

        Person editedPerson = new Person(editedReadOnlyPerson);
        syncMasterScheduleListWith(editedPerson);
        syncMasterTagListWith(editedPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.setPerson(target, editedPerson);
    }

    //@@author CT15
    /**
     * Ensures that every schedule in this person:
     * - exists in the master list {@link #schedules}
     * - points to a Schedule object in the master list
     */
    private void syncMasterScheduleListWith(Person person) {
        final UniqueScheduleList personSchedules = new UniqueScheduleList(person.getSchedules());

        schedules.mergeFrom(personSchedules);
        schedules.sort();
        setSchedulesToRemind();

        //Testing
        Iterator<Schedule> iterator2 = schedules.iterator();
        while (iterator2.hasNext()) {
            logger.info("Schedules after: " + iterator2.next().toString() + "\n");
        }
        Iterator<Schedule> iterator3 = schedulesToRemind.iterator();
        while (iterator3.hasNext()) {
            logger.info("Schedules to Remind: " + iterator3.next().toString() + "\n");
        }

        // Create map with values = schedule object references in the master list
        // used for checking person schedule references
        final Map<Schedule, Schedule> masterScheduleObjects = new HashMap<>();
        personSchedules.forEach(schedule -> masterScheduleObjects.put(schedule, schedule));

        // Rebuild the list of person schedules to point to the relevant schedules in the master schedule list.
        final List<Schedule> correctScheduleReferences = new ArrayList<>();
        personSchedules.forEach(schedule -> correctScheduleReferences.add(masterScheduleObjects.get(schedule)));
        person.setSchedules(correctScheduleReferences);
    }

    /**
     * Ensures that every schedule in these persons:
     * - exists in the master list {@link #schedules}
     * - points to a Schedule object in the master list
     *
     * @see #syncMasterScheduleListWith(Person)
     */
    private void syncMasterScheduleListWith(UniquePersonList persons) {
        persons.forEach(this::syncMasterScheduleListWith);
    }

    //@@author
    /**
     * Ensures that every tag in this person:
     * - exists in the master list {@link #tags}
     * - points to a Tag object in the master list
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
     * - exists in the master list {@link #tags}
     * - points to a Tag object in the master list
     *
     * @see #syncMasterTagListWith(Person)
     */
    private void syncMasterTagListWith(UniquePersonList persons) {
        persons.forEach(this::syncMasterTagListWith);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     *
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

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() + " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyPerson> getPersonList() {
        return persons.asObservableList();
    }

    //@@author CT15
    @Override
    public ObservableList<Schedule> getScheduleList() {
        return schedules.asObservableList();
    }

    //@@author 17navasaw
    @Override
    public ObservableList<Schedule> getScheduleToRemindList() {
        return schedulesToRemind.asObservableList();
    }

    //@@author
    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }
}
