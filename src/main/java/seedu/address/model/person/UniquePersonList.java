package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
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

    /**
     * Sorts the list of people by Score, from highest to lowest.
     */
    public void sortPersonsByScore() {

        Comparator<ReadOnlyPerson> personComparator = new Comparator<ReadOnlyPerson>() {

            public int compare(ReadOnlyPerson person1, ReadOnlyPerson person2) {

                String personName1 = person1.getScore().toString();
                String personName2 = person2.getScore().toString();

                return personName2.compareTo(personName1);
            }

        };

        FXCollections.sort(internalList, personComparator);
    }

    //@@author siri99
    /**
     *Sorts the list alphabetically by name
     */
    public void sortPersonsByName() {

        Comparator<ReadOnlyPerson> personComparator = new Comparator<ReadOnlyPerson>() {

            public int compare(ReadOnlyPerson person1, ReadOnlyPerson person2) {

                String personName1 = person1.getName().toString().toLowerCase();
                String personName2 = person2.getName().toString().toLowerCase();

                return personName1.compareTo(personName2);
            }

        };

        FXCollections.sort(internalList, personComparator);
    }

    /**
     * Sorts the list of people in order of birthdays: Jan to Dec.
     */
    public void sortPersonsByBirthday() {

        Comparator<ReadOnlyPerson> personComparator = new Comparator<ReadOnlyPerson>() {

            public int compare(ReadOnlyPerson person1, ReadOnlyPerson person2) {

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
                String firstBirthday = person1.getBirthday().toString();
                String secondBirthday = person2.getBirthday().toString();

                /* if birthday is not listed, assigns them the last date of the year so that
                 *they are sorted to the bottom of the list */
                if (firstBirthday.equals("No Birthday Listed")) {
                    firstBirthday = "31/12/1900";
                }
                if (secondBirthday.equals("No Birthday Listed")) {
                    secondBirthday = "31/12/1900";
                }

                // Split into 3 parts - day, month and year
                String[] day1 = firstBirthday.split("/");
                String[] day2 = secondBirthday.split("/");

                String date1 = day1[1] + "/" + day1[0];
                String date2 = day2[1] + "/" + day2[0];

                Date birthday1 = null;
                Date birthday2 = null;
                sdf.setLenient(false);
                try {
                    birthday1 = sdf.parse(date1);
                    birthday2 = sdf.parse(date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return birthday1.compareTo(birthday2);

            }

        };

        FXCollections.sort(internalList, personComparator);
    }

    //@@author siri99

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
