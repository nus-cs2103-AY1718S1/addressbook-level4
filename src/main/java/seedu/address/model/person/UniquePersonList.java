package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.ParserUtil.EMPTY_STRING;
import static seedu.address.logic.parser.ParserUtil.INDEX_ZERO;
import static seedu.address.logic.parser.ParserUtil.SORTADD_ARGS;
import static seedu.address.logic.parser.ParserUtil.SORTBIRTHDAY_ARGS;
import static seedu.address.logic.parser.ParserUtil.SORTEMAIL_ARGS;
import static seedu.address.logic.parser.ParserUtil.SORTFAVOURITE_ARGS;
import static seedu.address.logic.parser.ParserUtil.SORTNAME_ARGS;
import static seedu.address.logic.parser.ParserUtil.SORTNUMTIMESSEARCHED_ARGS;
import static seedu.address.logic.parser.ParserUtil.SORTNUM_ARGS;
import static seedu.address.logic.parser.ParserUtil.SORTREMARK_ARGS;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#equals(Object)
 */
public class UniquePersonList implements Iterable<Person> {

    public static final String EMPTY_LIST = "EMPTY";
    private final ObservableList<Person> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyPerson> mappedList = EasyBind.map(internalList, (person) -> person);

    private Map<String, Comparator<Person>> comparatorMap;

    //@@author justintkj
    /**
     * Installs the comparator for the different sortType into comparatorMap
     */
    public UniquePersonList () {
        comparatorMap = new HashMap<String, Comparator<Person>>();
        updateComparatorMapWithArg(SORTNAME_ARGS, Comparator.comparing(Person::getName));
        updateComparatorMapWithArg(SORTNUM_ARGS, Comparator.comparing(Person::getPhone));
        updateComparatorMapWithArg(SORTADD_ARGS, Comparator.comparing(Person::getAddress));
        updateComparatorMapWithArg(SORTEMAIL_ARGS, Comparator.comparing(Person::getEmail));
        updateComparatorMapWithArg(SORTREMARK_ARGS, Comparator.comparing(Person::getRemark));
        updateComparatorMapWithArg(SORTBIRTHDAY_ARGS, Comparator.comparing(Person::getBirthday));
        updateComparatorMapWithArg(SORTFAVOURITE_ARGS, Comparator.comparing(Person::getFavourite));
        updateComparatorMapWithArg(SORTNUMTIMESSEARCHED_ARGS, Comparator.comparing(Person::getNumTimesSearched));
    }

    /**
     * Connects argument used with it's relevant comparator
     *
     * @param arrayOfValidArg array of valid arguments
     * @param comparatorUsed comparator used for the array of valid arguments
     */
    private void updateComparatorMapWithArg(String[] arrayOfValidArg, Comparator comparatorUsed) {
        for (String arg:arrayOfValidArg) {
            comparatorMap.put(arg, comparatorUsed);
        }
    }

    //@@author

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(ReadOnlyPerson toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }
    //@@author justintkj
    /**
     * Sorts the internalList as declared by the arguments
     */
    public String sort (String sortType) {
        if (internalList.size() == INDEX_ZERO) {
            return EMPTY_LIST;
        }
        Collections.sort(internalList, comparatorMap.get(sortType));
        return findFullNameSort(sortType);
    }

    /**
     * Finds the full name output from all the valid args
     * @param sortType the argument user used
     * @return full name sort type
     */
    public String findFullNameSort (String sortType) {
        return findFullSortType(sortType, SORTNAME_ARGS) + findFullSortType(sortType, SORTNUM_ARGS)
                + findFullSortType(sortType, SORTADD_ARGS) + findFullSortType(sortType, SORTEMAIL_ARGS)
                + findFullSortType(sortType, SORTREMARK_ARGS) + findFullSortType(sortType, SORTBIRTHDAY_ARGS)
                + findFullSortType(sortType, SORTFAVOURITE_ARGS)
                + findFullSortType(sortType, SORTNUMTIMESSEARCHED_ARGS);
    }
    /**
     * Returns index zero of array if sortType is inside the array
     *
     * @param sortType the argument user used
     * @param sortParam list of all argument that has the same meaning
     * @return index zero of array
     */
    public String findFullSortType (String sortType, String[] sortParam) {
        for (String arg: sortParam) {
            if (arg.trim().toLowerCase().equals(sortType.trim().toLowerCase())) {
                return sortParam[INDEX_ZERO];
            }
        }
        return EMPTY_STRING;
    }
    //@@author

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
}
