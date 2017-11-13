package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.NoPersonsException;
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
     * Constructs empty UniquePersonList.
     */
    public UniquePersonList() {}


    /**
     * Creates a UniquePersonList using given persons.
     * Enforces no nulls.
     */
    public UniquePersonList(Set<Person> persons) {
        requireNonNull(persons);
        internalList.addAll(persons);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

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
     * Sorts persons in address book by field and in order specified.
     * @param sortComparator
     * @param isReverseOrder
     * @throws NoPersonsException
     */

    public void sort(Comparator sortComparator, Boolean isReverseOrder) throws NoPersonsException {
        requireNonNull(sortComparator);
        requireNonNull(isReverseOrder);

        if (internalList.size() < 1) {
            throw new NoPersonsException();
        }

        Collections.sort(internalList, sortComparator);

        if (isReverseOrder) {
            Collections.reverse(internalList);
        }
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
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
     * Replaces the person {@code target} in the list with {@code favouritePerson}.
     * @throws DuplicatePersonException if the replacement is equivalent to another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    public void setFavourite(ReadOnlyPerson target, ReadOnlyPerson favouritePerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(favouritePerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.equals(favouritePerson) && internalList.contains(favouritePerson)) {
            throw new DuplicatePersonException();
        }

        int targetIndex;

        /** Main favourite (fadd) logic
         * If person is marked as favourite, remove it from its current position
         * Attempt to insert at the head of the list
         * If the person at the top is favourite
         * Find new position where:
         * It's lexicographically smaller than the person's name preceding it
         * i.e If the edited person's name is Ben, it should come after Alex, if Alex is marked as favourite
         * Insert at the new position
         */

        if (favouritePerson.getFavourite().getStatus()) {
            targetIndex = 0;
            ReadOnlyPerson currentPerson;
            for (int i = 0; i < internalList.size(); i++) {
                currentPerson = internalList.get(i);
                if (currentPerson.getFavourite().getStatus()) {
                    if (currentPerson.getName().fullName.compareTo(favouritePerson.getName().fullName) < 0) {
                        targetIndex++;
                    }
                }
            }

            internalList.remove(index);
            internalList.add(targetIndex, new Person(favouritePerson));


        } else {

            /** Main favourite (fremove) logic
             * If person is unmarked as favourite, insert person at new position
             * Find new position where:
             * New position should be after all the favourites
             * Insert at the new position
             */

            targetIndex = index;
            for (int i = index + 1; i < internalList.size(); i++) {
                if (internalList.get(i).getFavourite().getStatus()) {
                    targetIndex++;
                }
            }

            /** If there is no change in position, do not remove person
             *  Continue with normal edit logic
             */
            if (targetIndex != index) {
                internalList.remove(index);
                internalList.add(targetIndex, new Person(favouritePerson));
            } else {
                internalList.set(index, new Person(favouritePerson));
            }
        }
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

    /**
     * Returns all persons in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Person> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }
}
