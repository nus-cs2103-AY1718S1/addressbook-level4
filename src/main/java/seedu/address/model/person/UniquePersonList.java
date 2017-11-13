package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
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
    private final ObservableList<Person> internalCopy = FXCollections.observableArrayList();
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
    //@@author wangyiming1019
    /**
     * Favourites the equivalent person in the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public void favouritePerson(ReadOnlyPerson toFavourite) throws PersonNotFoundException {
        requireNonNull(toFavourite);
        int index = internalList.indexOf(toFavourite);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        internalList.get(index).setFavourite(true);
    }

    /**
     * Unfavourites the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public void unfavouritePerson(ReadOnlyPerson toUnfavourite) throws PersonNotFoundException {
        requireNonNull(toUnfavourite);
        int index = internalList.indexOf(toUnfavourite);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        internalList.get(index).setFavourite(false);
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

    //@@author Esilocke
    /**
     * Returns an array list of {@code Index} corresponding to the {@code ReadOnlyPerson} specified
     */
    public ArrayList<Index> extractIndexes(ArrayList<ReadOnlyPerson> persons) {
        ArrayList<Index> indexes = new ArrayList<>();
        for (ReadOnlyPerson p : persons) {
            assert(internalList.contains(p));
            int position = internalList.indexOf(p);
            indexes.add(Index.fromZeroBased(position));
        }
        return indexes;
    }

    /**
     * Returns an array containing:
     * Index - The old index of each person in the internalList before sorting
     * Value - The new index of each person after a sort operation
     */
    public Index[] getMappings() {
        Index[] mappings = new Index[internalCopy.size()];
        int count = 0;
        for (Person p : internalCopy) {
            assert(internalList.contains(p));
            int index =  internalList.indexOf(p);
            mappings[count] = Index.fromZeroBased(index);
            count++;
        }
        return mappings;
    }
    //@@author

    /**
     * Sorts person list by all persons by any field in ascending or descending order
     * @param field
     * @param order
     */
    //@@author charlesgoh
    public void sortBy(String field, String order) {
        //sortyBy first chooses the right comparator
        System.out.println(internalList.size());
        internalCopy.clear();
        for (Person p : internalList) {
            internalCopy.add(p);
        }
        System.out.println(internalCopy.size());
        Comparator<Person> comparator = null;

        /**
         * Comparators for the various fields available for sorting
         */
        Comparator<Person> personNameComparator = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getName().value.compareTo(o2.getName().value);
            }
        };

        Comparator<Person> personPhoneComparator = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getPhone().value.compareTo(o2.getPhone().value);
            }
        };

        Comparator<Person> personEmailComparator = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getEmail().value.compareTo(o2.getEmail().value);
            }
        };

        Comparator<Person> personAddressComparator = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getAddress().value.compareTo(o2.getAddress().value);
            }
        };

        switch (field) {
        case "name":
            comparator = personNameComparator;
            break;

        case "phone":
            comparator = personPhoneComparator;
            break;

        case "email":
            comparator = personEmailComparator;
            break;

        case "address":
            comparator = personAddressComparator;
            break;

        default:
            throw new AssertionError("Invalid field parameter entered...\n");
        }

        //sortBy then chooses the right ordering
        switch (order) {
        case "asc":
            Collections.sort(internalList, comparator);
            break;

        case "desc":
            Collections.sort(internalList, Collections.reverseOrder(comparator));
            break;

        default:
            throw new AssertionError("Invalid field parameter entered...\n");
        }
    }
    //@@author

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
