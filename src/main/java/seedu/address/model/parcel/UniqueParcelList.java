package seedu.address.model.parcel;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.parcel.exceptions.DuplicateParcelException;
import seedu.address.model.parcel.exceptions.ParcelNotFoundException;

/**
 * A list of parcels that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Parcel#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueParcelList implements Iterable<Parcel> {

    private final ObservableList<Parcel> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyParcel> mappedList = EasyBind.map(internalList, (parcel) -> parcel);

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
     * Replaces the parcel {@code target} in the list with {@code editedParcel}.
     *
     * @throws DuplicateParcelException if the replacement is equivalent to another existing parcel in the list.
     * @throws ParcelNotFoundException if {@code target} could not be found in the list.
     */
    public void setParcel(ReadOnlyParcel target, ReadOnlyParcel editedParcel)
            throws DuplicateParcelException, ParcelNotFoundException {
        requireNonNull(editedParcel);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ParcelNotFoundException();
        }

        if (!target.equals(editedParcel) && internalList.contains(editedParcel)) {
            throw new DuplicateParcelException();
        }

        internalList.set(index, new Parcel(editedParcel));
    }

    /**
     * Removes the equivalent parcel from the list.
     *
     * @throws ParcelNotFoundException if no such parcel could be found in the list.
     */
    public boolean remove(ReadOnlyParcel toRemove) throws ParcelNotFoundException {
        requireNonNull(toRemove);
        final boolean parcelFoundAndDeleted = internalList.remove(toRemove);
        if (!parcelFoundAndDeleted) {
            throw new ParcelNotFoundException();
        }
        return parcelFoundAndDeleted;
    }

    public void setParcels(UniqueParcelList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setParcels(List<? extends ReadOnlyParcel> parcels) throws DuplicateParcelException {
        final UniqueParcelList replacement = new UniqueParcelList();
        for (final ReadOnlyParcel parcel : parcels) {
            replacement.add(new Parcel(parcel));
        }
        setParcels(replacement);
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
                || (other instanceof UniqueParcelList // instanceof handles nulls
                        && this.internalList.equals(((UniqueParcelList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Sorts the internal list and returns it.
     */
    public ObservableList<Parcel> getSortedList() {
        try {
            setParcels(internalList.sorted());
        } catch (DuplicateParcelException e) {
            e.printStackTrace();
        }
        return internalList;
    }
}
