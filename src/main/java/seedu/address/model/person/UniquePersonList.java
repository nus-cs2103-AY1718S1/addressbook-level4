package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
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
 * @see CollectionUtil# elementsAreUnique(Collection)
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

    //sorting methods

    /***
     * sort addressbook persons by number of times they were searched for
     * @author Sri-vatsa
     */
    public void sortBySearchCount () {
        internalList.sort(new SearchCountComparator());
    }

    /**
     * Custom Comparator class to compare two ReadOnlyPerson Objects by their search Count
     * @author Sri-vatsa
     */
    public class SearchCountComparator implements Comparator<ReadOnlyPerson> {

        /**
         * @author Sri-vatsa
         * Basis of comparison between ReadOnlyPerson
         * Compares two persons by Search Count
         *
         * @param o1 is an instance of ReadOnlyPerson
         * @param o2 is another instance of ReadOnlyPerson
         * @return Result of Comparison
         */
        public int compare (ReadOnlyPerson o1, ReadOnlyPerson o2) {

            int personASearchCount = Integer.parseInt(o1.getSearchData().getSearchCount());
            int personBSearchCount = Integer.parseInt(o2.getSearchData().getSearchCount());

            if (personASearchCount > personBSearchCount) {
                return -1;
            } else if (personASearchCount < personBSearchCount) {
                return 1;
            } else {
                return 0;
            }

        }

    }

    /***
     * @author Sri-vatsa
     * sort address book persons in alphabetical order
     */
    public void sortLexicographically () {
        internalList.sort(new LexicographicComparator());
    }

    /**
     * @author Sri-vatsa
     * Custom Comparator class to compare two ReadOnlyPerson Objects lexicographically
     */
    public class LexicographicComparator implements Comparator<ReadOnlyPerson> {

        /**
         * @author Sri-vatsa
         * Basis of comparison between ReadOnlyPerson
         * Compares two persons lexicographically
         *
         * @param o1 is an instance of ReadOnlyPerson
         * @param o2 is another instance of ReadOnlyPerson
         * @return Result of Comparison
         */
        public int compare (ReadOnlyPerson o1, ReadOnlyPerson o2) {

            String personAFullName = o1.getName().fullName;
            String personBFullName = o2.getName().fullName;

            return personAFullName.compareTo(personBFullName);
        }

    }


}
