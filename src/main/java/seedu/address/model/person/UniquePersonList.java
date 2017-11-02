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
    private int attribute;

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
        sort();
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if the replacement is equivalent to another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    public void  setPerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
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
        sort();
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
        sort();
        return personFoundAndDeleted;
    }
    //@@author A0143832J
    /**
     * Favorites the equivalent person in the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public void favorite(ReadOnlyPerson toFavorite) throws PersonNotFoundException {
        requireNonNull(toFavorite);
        int index = internalList.indexOf(toFavorite);

        if (index == -1) {
            throw new PersonNotFoundException();
        }

        Person newPerson = new Person(toFavorite);
        newPerson.setFavorite(new Favorite(!toFavorite.getFavorite().favorite));

        internalList.set(index, newPerson);
        sort();
    }
    //@@author

    public void setPersons(UniquePersonList replacement) {
        this.internalList.setAll(replacement.internalList);
        sort();
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

    //@@author A0143832J
    /**
     * sort the list in default sorting order: Favorite > non-Favorite; then alphabetical order
     */
    public void sort() {
        internalList.sort((ReadOnlyPerson p1, ReadOnlyPerson p2) -> {
            if (!p1.getFavorite().equals(p2.getFavorite())) {
                return p2.getFavorite().getValue() - p1.getFavorite().getValue();
            } else {
                switch (attribute) {
                case 1: return p1.getPhone().value.compareTo(p2.getPhone().value);
                case 2: return p1.getEmail().value.compareTo(p2.getEmail().value);
                case 3: return p1.getAddress().value.compareTo(p2.getAddress().value);
                default: return p1.getName().fullName.compareTo(p2.getName().fullName);
                }
            }
        });
    }
    //@@author

    //@@author majunting
    /**
     * sort the list by a user specified attribute
     */
    public void sortPersonBy(int attribute) {
        this.attribute = attribute;
        internalList.sort((ReadOnlyPerson p1, ReadOnlyPerson p2) -> {
            switch (attribute) {
            case 1: return p1.getPhone().value.compareTo(p2.getPhone().value);
            case 2: return p1.getEmail().value.compareTo(p2.getEmail().value);
            case 3: return p1.getAddress().value.compareTo(p2.getAddress().value);
            default: return p1.getName().fullName.compareTo(p2.getName().fullName);
            }
        });
    }
    //@@author
}
