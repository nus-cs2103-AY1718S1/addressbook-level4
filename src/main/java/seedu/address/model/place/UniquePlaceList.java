package seedu.address.model.place;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.place.exceptions.DuplicatePlaceException;
import seedu.address.model.place.exceptions.PlaceNotFoundException;

/**
 * A list of places that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Place#equals(Object)
 * @see CollectionUtil#elementsAreUnique
 */
public class UniquePlaceList implements Iterable<Place> {

    private final ObservableList<Place> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyPlace> mappedList = EasyBind.map(internalList, (place) -> place);

    /**
     * Returns true if the list contains an equivalent place as the given argument.
     */
    public boolean contains(ReadOnlyPlace toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a place to the list.
     *
     * @throws DuplicatePlaceException if the place to add is a duplicate of an existing place in the list.
     */
    public void add(ReadOnlyPlace toAdd) throws DuplicatePlaceException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePlaceException();
        }
        internalList.add(new Place(toAdd));
    }

    /**
     * Replaces the place {@code target} in the list with {@code editedPlace}.
     *
     * @throws DuplicatePlaceException if the replacement is equivalent to another existing place in the list.
     * @throws PlaceNotFoundException if {@code target} could not be found in the list.
     */
    public void setPlace(ReadOnlyPlace target, ReadOnlyPlace editedPlace)
            throws DuplicatePlaceException, PlaceNotFoundException {
        requireNonNull(editedPlace);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PlaceNotFoundException();
        }

        if (!target.equals(editedPlace) && internalList.contains(editedPlace)) {
            throw new DuplicatePlaceException();
        }

        internalList.set(index, new Place(editedPlace));
    }

    /**
     * Removes the equivalent place from the list.
     *
     * @throws PlaceNotFoundException if no such place could be found in the list.
     */
    public boolean remove(ReadOnlyPlace toRemove) throws PlaceNotFoundException {
        requireNonNull(toRemove);
        final boolean placeFoundAndDeleted = internalList.remove(toRemove);
        if (!placeFoundAndDeleted) {
            throw new PlaceNotFoundException();
        }
        return placeFoundAndDeleted;
    }

    public void setPlaces(UniquePlaceList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setPlaces(List<? extends ReadOnlyPlace> places) throws DuplicatePlaceException {
        final UniquePlaceList replacement = new UniquePlaceList();
        for (final ReadOnlyPlace place : places) {
            replacement.add(new Place(place));
        }
        setPlaces(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyPlace> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    @Override
    public Iterator<Place> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePlaceList // instanceof handles nulls
                        && this.internalList.equals(((UniquePlaceList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
