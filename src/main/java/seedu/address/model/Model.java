package seedu.address.model;

import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.parcel.Parcel;
import seedu.address.model.parcel.ReadOnlyParcel;
import seedu.address.model.parcel.Status;
import seedu.address.model.parcel.UniqueParcelList;
import seedu.address.model.parcel.exceptions.DuplicateParcelException;
import seedu.address.model.parcel.exceptions.ParcelNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagInternalErrorException;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<ReadOnlyParcel> PREDICATE_SHOW_ALL_PARCELS = unused -> true;

    //@@author kennard123661
    /**
     * Updates {@code filteredUncompletedParcels} and {@code filteredCompletedParcels} list and updates the
     * {@code activeFilteredList} to either of the previous sub lists based on its current reference.
     */
    void updateSubLists();

    /**
     * Sets the active list in the model.
     *
     * @param isCompleted if true, the active list will be set to the list of {@link Parcel}s with {@link Status} that
     *                    is COMPLETED. Otherwise, it will be set the list of parcels with {@link Status} that is not
     *                    COMPLETED.
     */
    void setActiveList(boolean isCompleted);
    //@@author

    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyAddressBook newData);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Deletes the given parcel.
     */
    void deleteParcel(ReadOnlyParcel target) throws ParcelNotFoundException;

    //@@author fustilio
    /**
     * Deletes the given tag from every parcel.
     */
    void deleteTag(Tag target) throws TagNotFoundException, TagInternalErrorException;
    //@@author

    /**
     * Adds the given parcel
     */
    void addParcel(ReadOnlyParcel parcel) throws DuplicateParcelException;

    //@@author kennard123661
    /**
     * Adds all unique {@link Parcel}s stored in {@param parcels} to the {@link AddressBook}
     *
     * @param parcels the list of parcels to add into the {@link AddressBook}.
     * @param uniqueParcels the list of unique parcels stored in {@param parcels} that will not create duplicate parcels
     *                      if added into the {@link UniqueParcelList} in the {@link AddressBook}
     * @param duplicateParcels the list of parcels stored in {@param parcels} that will create duplicate parcels in the
     *                         if added into the {@link UniqueParcelList} in the {@link AddressBook}
     */
    void addAllParcels(List<ReadOnlyParcel> parcels, List<ReadOnlyParcel> uniqueParcels, List<ReadOnlyParcel>
            duplicateParcels);
    //@@author

    /**
     * Replaces the given parcel {@code target} with {@code editedParcel}.
     *
     * @throws DuplicateParcelException if updating the parcel's details causes the parcel to be equivalent to
     *                                  another existing parcel in the list.
     * @throws ParcelNotFoundException  if {@code target} could not be found in the list.
     */
    void updateParcel(ReadOnlyParcel target, ReadOnlyParcel editedParcel)
            throws DuplicateParcelException, ParcelNotFoundException;

    /**
     * Returns an unmodifiable view of the filtered parcel list
     */
    ObservableList<ReadOnlyParcel> getFilteredParcelList();

    //@@author kennard123661
    /**
     * Returns an unmodifiable view of the filtered list of {@link Parcel} from that have {@link Status}
     * that is COMPLETED.
     */
    ObservableList<ReadOnlyParcel> getCompletedParcelList();

    /**
     * Returns an unmodifiable view of the filtered list of {@link Parcel} from that have {@link Status}
     * that is not COMPLETED.
     */
    ObservableList<ReadOnlyParcel> getUncompletedParcelList();

    /**
     * Returns an unmodifiable view of the current active list.
     */
    ObservableList<ReadOnlyParcel> getActiveList();
    //@@author

    /**
     * Updates the filter of the filtered parcel list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredParcelList(Predicate<ReadOnlyParcel> predicate);

    //@@author fustilio
    /**
     * Method to sort the lists of addresses by delivery date with the earliest date in front
     */
    void maintainSorted();

    /**
     * Method to check if there is a parcel selected.
     */
    boolean hasSelected();

    /**
     * Method to toggle whether or not a parcel has been selected
     */
    void select();

    /**
     * Method to toggle whether or not a parcel has been selected
     */
    void unselect();

    /**
     * Method to set the prevIndex attribute to the specified target.
     */
    void setPrevIndex(Index target);

    /**
     * Method to retrieve Index of last selected Parcel Card.
     */
    Index getPrevIndex();

    /**
     * Method to force the model to select a card without using the select command.
     */
    void forceSelect(Index target);

    /**
     * Method to reselect a parcel card if there is a card selected.
     */
    void reselect(ReadOnlyParcel parcel);
    //@@author
}


