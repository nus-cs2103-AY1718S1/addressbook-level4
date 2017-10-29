package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

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
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Person#equals(Object)
 * @see CollectionUtil#elementsAreUnique(java.util.Collection)
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

    //@@author Alim95
    /**
     * Sorts the list in order.
     */
    public void sort(String toSort) {
        switch (toSort) {
        case "name":
            internalList.sort((p1, p2) -> p1.getName().toString().compareToIgnoreCase(p2.getName().toString()));
            break;
        case "phone":
            internalList.sort((p1, p2) -> p1.getPhone().toString().compareToIgnoreCase(p2.getPhone().toString()));
            break;
        case "email":
            internalList.sort((p1, p2) -> p1.getEmail().toString().compareToIgnoreCase(p2.getEmail().toString()));
            break;
        case "address":
            internalList.sort((p1, p2) -> p1.getAddress().toString().compareToIgnoreCase(p2.getAddress().toString()));
            break;
        default:
            break;
        }
    }

    //@@author
    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if the replacement is equivalent to another existing person in the list.
     * @throws PersonNotFoundException  if {@code target} could not be found in the list.
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

    /**
     * Hides the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public boolean hide(ReadOnlyPerson toHide) throws PersonNotFoundException {
        requireNonNull(toHide);
        final int indexToHide = internalList.indexOf(toHide);
        final boolean personFoundAndHidden = internalList.get(indexToHide).setPrivate(true);
        if (!personFoundAndHidden) {
            throw new PersonNotFoundException();
        }
        return personFoundAndHidden;
    }

    //@@author Alim95
    /**
     * Pins the equivalent person in the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public boolean pin(ReadOnlyPerson toPin) throws PersonNotFoundException {
        requireNonNull(toPin);
        final int indexToPin = internalList.indexOf(toPin);
        final boolean personFoundAndPinned = internalList.get(indexToPin).setPinned(true);
        if (!personFoundAndPinned) {
            throw new PersonNotFoundException();
        }
        return personFoundAndPinned;
    }

    /**
     * Unpins the equivalent person in the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public boolean unpin(ReadOnlyPerson toUnpin) throws PersonNotFoundException {
        requireNonNull(toUnpin);
        final int indexToPin = internalList.indexOf(toUnpin);
        final boolean personFoundAndUnpinned = internalList.get(indexToPin).setPinned(false);
        if (!personFoundAndUnpinned) {
            throw new PersonNotFoundException();
        }
        return personFoundAndUnpinned;
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
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyPerson> asObservableList() {
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
