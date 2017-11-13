package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import javafx.collections.ObservableList;
import seedu.address.commons.core.PossibleDays;
import seedu.address.commons.core.PossibleTimes;
import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueTagList tags;

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
    }


    /**
     * Given a time span, add it to the person's free time slot so that this time span will  be considered when
     * arranging a meeting.
     */
    public void addScheduleToPerson(Integer index, TreeSet<Integer> timeSpan) throws PersonNotFoundException {
        persons.addSchedule(index, timeSpan);
    }

    /* Given a time span, add it to the person's busy time slot so that this time span will not be considered when
       arranging a meeting.
     */
    public void clearScheduleForPerson(Integer index, TreeSet<Integer> timeSpan) throws PersonNotFoundException {
        persons.clearSchedule(index, timeSpan);
    }

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
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.setPerson(target, editedPerson);
    }

    /**
     * Generating Meeting Time based on the list of Index.
     */
    public TreeSet<Integer> generateMeetingTime(Index[] listOfIndex) {
        TreeSet<Integer> satisfiedTimeSet = new TreeSet<>();
        for (int i = 0; i < PossibleDays.DAYS.length; i++) {
            for (int k = 0; k < PossibleTimes.TIMES.length; k++) {
                satisfiedTimeSet.add(PossibleDays.DAYS[i] * PossibleDays.DAY_COEFFICIENT + PossibleTimes.TIMES[k]);
            }
        }

        for (int j = 0; j < listOfIndex.length; j++) {
            Iterator<Integer> iterator = satisfiedTimeSet.iterator();
            Schedule currentSchedule = getPersonList().get(listOfIndex[j].getZeroBased()).getSchedule();
            while (iterator.hasNext()) {
                Integer timeNumber = iterator.next();
                if (!currentSchedule.containsTimeNumber(timeNumber)) {
                    iterator.remove();
                }
            }
        }
        return satisfiedTimeSet;
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

    /**
     * Remove tag from persson
     * Returns true if tag is successfully removed
     */
    public boolean removeTag(String str) {
        if (persons.removeTag(str)) {
            tags.removeTag(str);
            resetData(this);
            return true;
        } else {
            return false;
        }
    }

    public void sort() {
        persons.sort();
    }
    //@@author hj2304
    /**
     * Checks Meeting Time based on the list of Index.
     */
    public boolean checkMeetingTime(Index[] listOfIndex, int day, int start, int end) {
        boolean res = true;
        TreeSet<Integer> satisfiedTimeSet = new TreeSet<>();
        boolean started = false;
        boolean ended = false;
        for (int k = 0; k < PossibleTimes.TIMES.length; k++) {
            if (PossibleTimes.TIMES[k] == start) {
                started = true;
            }
            if (PossibleTimes.TIMES[k] == end) {
                ended = true;
            }
            if (started && !ended) {
                satisfiedTimeSet.add(day * PossibleDays.DAY_COEFFICIENT + PossibleTimes.TIMES[k]);
            }
            if (ended) {
                break;
            }
        }
        if (!started || !ended) {
            res = false;
        }
        for (int j = 0; j < listOfIndex.length; j++) {
            if (!res) {
                break;
            }
            Iterator<Integer> iterator = satisfiedTimeSet.iterator();
            Schedule currentSchedule = getPersonList().get(listOfIndex[j].getZeroBased()).getSchedule();
            while (iterator.hasNext()) {
                Integer timeNumber = iterator.next();
                if (!currentSchedule.containsTimeNumber(timeNumber)) {
                    res = false;
                    break;
                }
            }
        }
        return res;
    }

    public void addEventToPerson(Integer index, Tag event) {
        persons.addEventTag(index, event);
    }
    //@@author
}
