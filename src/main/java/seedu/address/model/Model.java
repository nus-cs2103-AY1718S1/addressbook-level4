package seedu.address.model;

import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToTabRequestEvent;
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
     * Sets the active list in the model.
     *
     * @param event the {@link JumpToTabRequestEvent} contains the index of the selected Tab. The selected tab will
     *              provide information on the active list to select. If the value of
     *              {@link JumpToTabRequestEvent#targetIndex} is zero, then {@link ModelManager#completedParcels}
     *              is set as the new active list. Otherwise, the {@link ModelManager#uncompletedParcels} is set as
     *              the active list {@link ModelManager#activeParcels}
     */
    void setActiveList(JumpToTabRequestEvent event);
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
     * Adds all unique {@link Parcel}s stored in {@code parcels} to the {@link AddressBook} and sort the parcel list.
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
     * Method to force the model to select a card without using the select command.
     */
    void forceSelect(Index target);


    /**
     * Method to force the model to select a card without using the select command.
     */
    void forceSelectParcel(ReadOnlyParcel target);

    /**
     * Method to set tabIndex attribute in Model.
     */
    void setTabIndex(Index index);

    /**
     * Method to get tabIndex attribute in Model.
     */
    Index getTabIndex();

    /**
     * Method to encapsulate all the sub methods to be executed when AddCommand is executed.
     * @param parcel the parcel to add
     * @throws DuplicateParcelException if parcel is already inside the list of parcels, reject the input
     */
    void addParcelCommand(ReadOnlyParcel parcel) throws DuplicateParcelException;

    /**
     * Method to encapsulate all the sub methods to be executed when EditCommand is executed.
     * @param parcelToEdit the parcel to edit
     * @param editedParcel the edited parcel to replace the parcel to edit.
     * @throws DuplicateParcelException if editedParcel already exists unless the parcelToEdit is the same entity.
     * @throws ParcelNotFoundException if parcelToEdit cannot be found in the list
     */
    void editParcelCommand(ReadOnlyParcel parcelToEdit, ReadOnlyParcel editedParcel)
            throws DuplicateParcelException, ParcelNotFoundException;

    /**
     * Method to retrieve flag that represents whether the current tab selected is all parcels.
     */
    boolean getActiveIsAllBool();

    /**
     * Method to forcefully raise the event to switch tabs to all parcels.
     */
    void uiJumpToTabAll();

    /**
     * Method to forcefully rasie the event to switch tabs to completed parcels.
     */
    void uiJumpToTabCompleted();
    //@@author
}


