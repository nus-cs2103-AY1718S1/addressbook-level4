package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.UniqueMeetingList;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueTagList tags;
    private final UniqueMeetingList meetings;

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
        meetings = new UniqueMeetingList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons and Tags in the {@code toBeCopied}
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

    public void setMeetings(Set<Meeting> meetings) {
        this.meetings.setMeetings(meetings);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        Set<Meeting> meetingList = new HashSet<>();
        try {
            setPersons(newData.getPersonList());
            for (Person person : persons) {
                setMeetingWithPersonDetails(person);
                meetingList.addAll(person.getMeetings());
            }
        } catch (DuplicatePersonException e) {
            assert false : "AddressBooks should not have duplicate persons";
        }
        setTags(new HashSet<>(newData.getTagList()));
        setMeetings(meetingList);
        sortMeeting();
        syncMasterTagListWith(persons);
        syncMasterMeetingListWith(persons);
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
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        syncMasterMeetingListWith(newPerson);
        persons.add(newPerson);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedReadOnlyPerson}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedReadOnlyPerson}.
     * {@code AddressBook}'s meeting list will be updated with the meetings of {@code editedReadOnlyPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncMasterTagListWith(Person)
     * @see #syncMasterMeetingListWith(Person)
     */
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedReadOnlyPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedReadOnlyPerson);

        Person editedPerson = new Person(editedReadOnlyPerson);
        syncMasterTagListWith(editedPerson);
        syncMasterMeetingListWith(editedPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.setPerson(target, editedPerson);
    }

    //@@author LimYangSheng
    /**
     * Updates {@code person} meetings to have reference to itself.
     */
    private void setMeetingWithPersonDetails(ReadOnlyPerson person) {
        for (Meeting meeting : person.getMeetings()) {
            meeting.setPerson(person);
        }
    }

    /**
     * Finds the meetings in meeting list with {@code Person} that equals {@code target} and replaces it with
     * {@code editedReadOnlyPerson}
     */
    public void updateMeetings(ReadOnlyPerson target, ReadOnlyPerson editedReadOnlyPerson) {
        requireNonNull(editedReadOnlyPerson);
        meetings.updateMeetings(target, editedReadOnlyPerson);
    }

    //@@author
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

    //@@author alexanderleegs
    /**
     * Ensures that every meeting in this person:
     *  - exists in the master list {@link #meetings}
     *  - points to a Meeting object in the master list
     */
    private void syncMasterMeetingListWith(Person person) {
        final UniqueMeetingList personMeetings = new UniqueMeetingList(person.getMeetings());
        meetings.mergeFrom(personMeetings);

        final Map<Meeting, Meeting> masterMeetingObjectReferences = new HashMap<>();
        meetings.forEach(meeting -> masterMeetingObjectReferences.put(meeting, meeting));

        final Set<Meeting> correctMeetingReferences = new HashSet<>();
        personMeetings.forEach(meeting -> correctMeetingReferences.add(masterMeetingObjectReferences.get(meeting)));
        person.setMeetings(correctMeetingReferences);
    }

    /**
     * Ensures that every meeting in these persons:
     *  - exists in the master list {@link #meetings}
     *  - points to a Meeting object in the master list
     *  @see #syncMasterMeetingListWith(Person)
     */
    private void syncMasterMeetingListWith(UniquePersonList persons) {
        persons.forEach(this::syncMasterMeetingListWith);
    }
    //@@author

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removePerson(ReadOnlyPerson key) throws PersonNotFoundException {
        if (persons.contains(key)) {
            persons.remove(key);
            Set<Meeting> meetingsToRemove = key.getMeetings();
            for (Meeting meeting : meetingsToRemove) {
                meetings.remove(meeting);
            }
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }

    //@@author alexanderleegs
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    /**
     * Sorts contacts by {@code field}.
     */
    public void sort(String field) {
        persons.sort(field);
        sortMeeting();
    }

    //@@author
    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    public void sortMeeting() {
        meetings.sortMeeting();
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() +  " tags";
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

    //@@author alexanderleegs
    @Override
    public ObservableList<Meeting> getMeetingList() {
        return meetings.asObservableList();
    }

    //@@author
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags)
                && this.meetings.equalsOrderInsensitive(((AddressBook) other).meetings));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }
}
