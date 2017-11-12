package seedu.room.model;

import static java.util.Objects.requireNonNull;
import static seedu.room.logic.commands.RemoveTagCommand.MESSAGE_REMOVE_TAG_NOT_EXIST;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.room.commons.exceptions.IllegalValueException;
import seedu.room.logic.commands.exceptions.AlreadySortedException;
import seedu.room.logic.commands.exceptions.CommandException;
import seedu.room.model.person.Person;
import seedu.room.model.person.ReadOnlyPerson;
import seedu.room.model.person.UniquePersonList;
import seedu.room.model.person.exceptions.DuplicatePersonException;
import seedu.room.model.person.exceptions.NoneHighlightedException;
import seedu.room.model.person.exceptions.PersonNotFoundException;
import seedu.room.model.person.exceptions.TagNotFoundException;
import seedu.room.model.tag.Tag;
import seedu.room.model.tag.UniqueTagList;

/**
 * Wraps all data at the room-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class ResidentBook implements ReadOnlyResidentBook {

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

    public ResidentBook() {}

    /**
     * Creates an ResidentBook using the Persons and Tags in the {@code toBeCopied}
     */
    public ResidentBook(ReadOnlyResidentBook toBeCopied) {
        this();
        resetData(toBeCopied);
        deleteTemporary();
    }

    //// list overwrite operations

    public void setPersons(List<? extends ReadOnlyPerson> persons) throws DuplicatePersonException {
        this.persons.setPersons(persons);
    }

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * Resets the existing data of this {@code ResidentBook} with {@code newData}.
     */
    public void resetData(ReadOnlyResidentBook newData) {
        requireNonNull(newData);
        try {
            setPersons(newData.getPersonList());
        } catch (DuplicatePersonException e) {
            assert false : "ResidentBooks should not have duplicate persons";
        }

        setTags(new HashSet<>(newData.getTagList()));
        syncMasterTagListWith(persons);
    }

    //@@author Haozhe321
    /**
     * delete temporary persons on start up of the app
     */
    public void deleteTemporary() {
        UniquePersonList personsList = this.getUniquePersonList();

        Iterator<Person> itr = personsList.iterator(); //iterator to iterate through the persons list
        while (itr.hasNext()) {
            Person person = itr.next();
            LocalDateTime personExpiry = person.getTimestamp().getExpiryTime();
            LocalDateTime current = LocalDateTime.now();
            if (personExpiry != null) { //if this is a temporary contact
                if (current.compareTo(personExpiry) == 1) { //if current time is past the time of expiry
                    itr.remove();
                }
            }
        }
    }
    //@@author

    //// person-level operations

    /**
     * Adds a person to the resident book.
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
     * {@code ResidentBook}'s tag list will be updated with the tags of {@code editedReadOnlyPerson}.
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
        editedPerson.getPicture().setPictureUrl(editedReadOnlyPerson.getPicture().getPictureUrl());

        syncMasterTagListWith(editedPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.setPerson(target, editedPerson);
    }

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
     * Removes {@code key} from this {@code ResidentBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code ResidentBook}.
     */
    public boolean removePerson(ReadOnlyPerson key) throws PersonNotFoundException {
        if (persons.remove(key)) {
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }

    //@@author Haozhe321
    public void removeByTag(Tag tag) throws IllegalValueException, CommandException {
        persons.removeByTag(tag);
    }
    //@@author

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    /**
     * Removes {@code tag} from this {@code ResidentBook}.
     */
    public void removeTag(Tag t) throws TagNotFoundException {
        boolean isExist = false;
        Iterator<Tag> itr = tags.iterator();  // list is a Set<String>!
        while (itr.hasNext()) {
            if (itr.next().equals(t)) {
                isExist = true;
                itr.remove();
            }
        }

        if (!isExist) {
            throw new TagNotFoundException(MESSAGE_REMOVE_TAG_NOT_EXIST);
        }

    }

    //@@author shitian007
    /**
     * Updates highlight status of person with specified tag
     */
    public void updateHighlightStatus(String highlightTag) throws TagNotFoundException {
        try {
            if (!this.tags.contains(new Tag(highlightTag))) {
                throw new TagNotFoundException("No such Tag Exists");
            } else {
                persons.updateHighlightStatus(highlightTag);
            }
        } catch (IllegalValueException e) {
            throw new TagNotFoundException("No such Tag Exists");
        }
    }

    /**
     * Removes highlight status of all persons
     */
    public void resetHighlightStatus() throws NoneHighlightedException {
        persons.resetHighlightStatus();
    }
    //@@author

    //// sort resident book
    //@@author sushinoya
    /**
     * Sorts the UniquePersonList, persons.
     * @throws AlreadySortedException if the list is already sorted by given criteria.
     */
    public void sortBy(String sortCriteria) throws AlreadySortedException {

        if (persons.getCurrentlySortedBy().equals(sortCriteria)) {
            throw new AlreadySortedException("List already sorted by: " + sortCriteria);
        } else {
            persons.sortBy(sortCriteria);
        }
    }

    /**
     * Swaps the rooms between two residents.
     * @throws PersonNotFoundException if the persons specified are not found in the list.
     */
    public void swapRooms(ReadOnlyPerson person1, ReadOnlyPerson person2)
        throws PersonNotFoundException {
        persons.swapRooms(person1, person2);
    }
    //@@author

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

    public UniquePersonList getUniquePersonList() {
        return persons;
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ResidentBook // instanceof handles nulls
                && this.persons.equals(((ResidentBook) other).persons)
                && this.tags.equalsOrderInsensitive(((ResidentBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }

}
