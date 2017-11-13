package seedu.room.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.room.commons.core.Messages;
import seedu.room.commons.util.CollectionUtil;
import seedu.room.logic.commands.exceptions.CommandException;
import seedu.room.model.person.exceptions.DuplicatePersonException;
import seedu.room.model.person.exceptions.NoneHighlightedException;
import seedu.room.model.person.exceptions.PersonNotFoundException;
import seedu.room.model.person.exceptions.TagNotFoundException;
import seedu.room.model.tag.Tag;


/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniquePersonList implements Iterable<Person> {

    private final ObservableList<Person> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyPerson> mappedList = EasyBind.map(internalList, (person) -> person);
    private String currentlySortedBy = "name";

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(ReadOnlyPerson toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a person to the list.
     *
     * @throws DuplicatePersonException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(ReadOnlyPerson toAdd) throws DuplicatePersonException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(new Person(toAdd));
        this.sortBy(currentlySortedBy);
    }

    //@@author shitian007
    /**
     * Updates the highlight status of the persons with the specified tag
     */
    public void updateHighlightStatus(String highlightTag) throws TagNotFoundException {
        resetHighlightStatusHelper();
        boolean tagFound = false;
        for (Person person : this.internalList) {
            for (Tag t : person.getTags()) {
                if (t.getTagName().equals(highlightTag)) {
                    tagFound = true;
                    person.setHighlightStatus(true);
                }
            }
        }
        if (!tagFound) {
            throw new TagNotFoundException("No Such Tag Exists");
        }
    }

    /**
     * Removes highlighting of everyone
     */
    public void resetHighlightStatus() throws NoneHighlightedException {
        boolean highlightReset = resetHighlightStatusHelper();
        if (!highlightReset) {
            throw new NoneHighlightedException("No Residents Highlighted");
        }
    }

    /**
     * @return true if at least one resident's highlight status has been reset
     */
    public boolean resetHighlightStatusHelper() {
        boolean highlightReset = false;
        for (Person person : this.internalList) {
            if (person.getHighlightStatus()) {
                person.setHighlightStatus(false);
                highlightReset = true;
            }
        }
        return highlightReset;
    }

    //@@author

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if the replacement is equivalent to another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    public void setPerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.equals(editedPerson) && internalList.contains(editedPerson)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, new Person(editedPerson));
        this.sortBy(currentlySortedBy);
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public boolean remove(ReadOnlyPerson toRemove) throws PersonNotFoundException {
        requireNonNull(toRemove);
        final boolean personFoundAndDeleted = internalList.remove(toRemove);
        if (!personFoundAndDeleted) {
            throw new PersonNotFoundException();
        }
        this.sortBy(currentlySortedBy);
        return personFoundAndDeleted;
    }

    //@@author Haozhe321
    /**
     * Removes the persons who have the tag supplied
     *
     * @throws CommandException if no one has this tag
     */
    public void removeByTag(Tag tag) throws CommandException {
        Iterator<Person> itr = this.iterator();
        int numRemoved = 0;
        while (itr.hasNext()) {
            Person p = itr.next();
            if (p.getTags().contains(tag)) {
                itr.remove();
                numRemoved++;
            }
        }
        if (numRemoved == 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_TAG_FOUND);
        }
    }
    //@@author

    public void setPersons(UniquePersonList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setPersons(List<? extends ReadOnlyPerson> persons) throws DuplicatePersonException {
        final UniquePersonList replacement = new UniquePersonList();
        for (final ReadOnlyPerson person : persons) {
            replacement.add(new Person(person));
        }
        setPersons(replacement);
        sortBy(currentlySortedBy);
    }

    //@@author Haozhe321
    public ObservableList<Person> getInternalList() {
        return internalList;
    }
    //@@author

    /**
     * Sorts the Person's List by sorting criteria
     */
    public void sortBy(String sortCriteria) {
        this.currentlySortedBy = sortCriteria;
        for (Person p : internalList) {
            p.setComparator(sortCriteria);
        }
        FXCollections.sort(internalList);
    }

    /**
     * Swaps the rooms of the two persons passed as arguments
     */
    public void swapRooms(ReadOnlyPerson person1, ReadOnlyPerson person2)
        throws PersonNotFoundException {
        requireNonNull(person1);
        requireNonNull(person2);

        final boolean person1FoundAndDeleted = internalList.remove(person1);
        final boolean person2FoundAndDeleted = internalList.remove(person2);
        if (!person1FoundAndDeleted || !person2FoundAndDeleted) {
            throw new PersonNotFoundException();
        }

        Person newPerson1 = new Person(person1.getName(), person1.getPhone(), person1.getEmail(), person2.getRoom(),
                person1.getTimestamp(), person1.getTags());
        Person newPerson2 = new Person(person2.getName(), person2.getPhone(), person2.getEmail(), person1.getRoom(),
                person2.getTimestamp(), person2.getTags());

        internalList.add(new Person(newPerson1));
        internalList.add(new Person(newPerson2));
        this.sortBy(currentlySortedBy);

    }

    public String getCurrentlySortedBy() {
        return this.currentlySortedBy;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyPerson> asObservableList() {
        sortBy(currentlySortedBy);
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                        && this.internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
