package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.parcel.ReadOnlyParcel;
import seedu.address.model.parcel.exceptions.DuplicateParcelException;
import seedu.address.model.parcel.exceptions.ParcelNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyParcel> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given parcel. */
    void deletePerson(ReadOnlyParcel target) throws ParcelNotFoundException;

    /** Adds the given parcel */
    void addPerson(ReadOnlyParcel person) throws DuplicateParcelException;

    /**
     * Replaces the given parcel {@code target} with {@code editedPerson}.
     *
     * @throws DuplicateParcelException if updating the parcel's details causes the parcel to be equivalent to
     *      another existing parcel in the list.
     * @throws ParcelNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(ReadOnlyParcel target, ReadOnlyParcel editedPerson)
            throws DuplicateParcelException, ParcelNotFoundException;

    /** Returns an unmodifiable view of the filtered parcel list */
    ObservableList<ReadOnlyParcel> getFilteredPersonList();

    /**
     * Updates the filter of the filtered parcel list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyParcel> predicate);

}
