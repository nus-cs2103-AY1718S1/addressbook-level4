package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toCollection;

import java.util.Iterator;
import java.util.List;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

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

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(ReadOnlyPerson toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Returns {@code ObservableList} of all objects in list.
     */
    public ObservableList<Person> getInternalList() {
        return internalList;
    }

    /**
     * Returns index of {@code ReadOnlyPerson} in list.
     */
    public int getIndexOf(ReadOnlyPerson key) {
        return internalList.indexOf(key);
    }

    /**
     * Returns {@code ReadOnlyPerson} in list from given {@param index}.
     */
    public ReadOnlyPerson getReadOnlyPerson(int index) {
        return mappedList.get(index);
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
    }

    /**
     * Adds a person to the list in a specific position.
     *
     * @throws DuplicatePersonException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(int index, ReadOnlyPerson toAdd) throws DuplicatePersonException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(index, new Person(toAdd));
    }

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
        return personFoundAndDeleted;
    }

    public void setPersons(UniquePersonList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setPersons(List<? extends ReadOnlyPerson> persons) throws DuplicatePersonException {
        final UniquePersonList replacement = new UniquePersonList();
        for (final ReadOnlyPerson person : persons) {
            replacement.add(new Person(person));
        }
        setPersons(replacement);
    }

    //@@author khooroko
    /**
     * Sorts the unique person list by the specified order.
     * @param order to sort the list by.
     */
    public void sortBy(String order) throws IllegalArgumentException {
        switch (order) {
        case "name":
            internalList.sort((Person p1, Person p2) -> p1.getName().compareTo(p2.getName()));
            break;
        case "debt":
            internalList.sort((Person p1, Person p2) -> p2.getDebt().compareTo(p1.getDebt()));
            break;
        case "cluster":
            internalList.sort((Person p1, Person p2) -> p1.getCluster().compareTo(p2.getCluster()));
            break;
        case "deadline":
            internalList.sort((Person p1, Person p2) -> p1.getDeadline().compareTo(p2.getDeadline()));
            break;
        default:
            throw new IllegalArgumentException("Invalid sort ordering");
        }
    }

    //@@author
    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyPerson> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyPerson> asObservableBlacklist() {
        return FXCollections.unmodifiableObservableList(mappedList.stream().filter(person -> person.isBlacklisted()).collect(toCollection(FXCollections::observableArrayList)));
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyPerson> asObservableWhitelist() {
        return FXCollections.unmodifiableObservableList(mappedList.stream().filter(person -> person.isWhitelisted()).collect(toCollection(FXCollections::observableArrayList)));
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
