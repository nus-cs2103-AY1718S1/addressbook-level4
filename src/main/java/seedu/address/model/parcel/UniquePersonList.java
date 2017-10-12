package seedu.address.model.parcel;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.parcel.exceptions.DuplicateParcelException;
import seedu.address.model.parcel.exceptions.ParcelNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Parcel#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniquePersonList implements Iterable<Parcel> {

    private final ObservableList<Parcel> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyParcel> mappedList = EasyBind.map(internalList, (person) -> person);

    /**
     * Returns true if the list contains an equivalent parcel as the given argument.
     */
    public boolean contains(ReadOnlyParcel toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a parcel to the list.
     *
     * @throws DuplicateParcelException if the parcel to add is a duplicate of an existing parcel in the list.
     */
    public void add(ReadOnlyParcel toAdd) throws DuplicateParcelException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateParcelException();
        }
        internalList.add(new Parcel(toAdd));
    }

    /**
     * Replaces the parcel {@code target} in the list with {@code editedPerson}.
     *
     * @throws DuplicateParcelException if the replacement is equivalent to another existing parcel in the list.
     * @throws ParcelNotFoundException if {@code target} could not be found in the list.
     */
    public void setPerson(ReadOnlyParcel target, ReadOnlyParcel editedPerson)
            throws DuplicateParcelException, ParcelNotFoundException {
        requireNonNull(editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ParcelNotFoundException();
        }

        if (!target.equals(editedPerson) && internalList.contains(editedPerson)) {
            throw new DuplicateParcelException();
        }

        internalList.set(index, new Parcel(editedPerson));
    }

    /**
     * Removes the equivalent parcel from the list.
     *
     * @throws ParcelNotFoundException if no such parcel could be found in the list.
     */
    public boolean remove(ReadOnlyParcel toRemove) throws ParcelNotFoundException {
        requireNonNull(toRemove);
        final boolean personFoundAndDeleted = internalList.remove(toRemove);
        if (!personFoundAndDeleted) {
            throw new ParcelNotFoundException();
        }
        return personFoundAndDeleted;
    }

    public void setPersons(UniquePersonList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setPersons(List<? extends ReadOnlyParcel> persons) throws DuplicateParcelException {
        final UniquePersonList replacement = new UniquePersonList();
        for (final ReadOnlyParcel person : persons) {
            replacement.add(new Parcel(person));
        }
        setPersons(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyParcel> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    @Override
    public Iterator<Parcel> iterator() {
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
