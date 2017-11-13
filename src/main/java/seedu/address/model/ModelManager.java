package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.ui.ParcelListPanel.INDEX_FIRST_TAB;
import static seedu.address.ui.ParcelListPanel.INDEX_SECOND_TAB;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.JumpToTabRequestEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.parcel.ReadOnlyParcel;
import seedu.address.model.parcel.Status;
import seedu.address.model.parcel.exceptions.DuplicateParcelException;
import seedu.address.model.parcel.exceptions.ParcelNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private static final Predicate<ReadOnlyParcel> deliveredPredicate = p -> p.getStatus().equals(Status.COMPLETED);
    private static final Index TAB_ALL_PARCELS = INDEX_FIRST_TAB;
    private static final Index TAB_COMPLETED_PARCELS = INDEX_SECOND_TAB;

    private Index tabIndex;
    private final AddressBook addressBook;

    private final FilteredList<ReadOnlyParcel> filteredParcels;
    private final FilteredList<ReadOnlyParcel> completedParcels;
    private final FilteredList<ReadOnlyParcel> uncompletedParcels;
    private FilteredList<ReadOnlyParcel> activeParcels; // references the current selected list

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.tabIndex = INDEX_FIRST_TAB;
        filteredParcels = new FilteredList<>(this.addressBook.getParcelList());
        completedParcels = filteredParcels.filtered(deliveredPredicate);
        uncompletedParcels = filteredParcels.filtered(deliveredPredicate.negate());
        activeParcels = uncompletedParcels;
        ModelListener modelListener = new ModelListener(this);
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //@@author kennard123661
    @Subscribe @Override
    public void setActiveList(JumpToTabRequestEvent event) {
        boolean isCompleted = event.targetIndex == INDEX_SECOND_TAB.getZeroBased();
        activeParcels = isCompleted ? completedParcels : uncompletedParcels;
        logger.info("Active list now set to "
                + (isCompleted ? "completed parcels list." : "uncompleted parcels list."));
    }
    //@@author

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    @Override
    public synchronized void deleteParcel(ReadOnlyParcel target) throws ParcelNotFoundException {
        addressBook.removeParcel(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addParcel(ReadOnlyParcel parcel) throws DuplicateParcelException {
        addressBook.addParcel(parcel);
        updateFilteredParcelList(PREDICATE_SHOW_ALL_PARCELS);
        indicateAddressBookChanged();
    }

    //@@author kennard123661
    @Override
    public synchronized void addAllParcels(List<ReadOnlyParcel> parcels, List<ReadOnlyParcel> uniqueParcels,
                                           List<ReadOnlyParcel> duplicateParcels) {

        assert parcels != null : "parcels should not be null";
        assert uniqueParcels != null : "uniqueParcels should not be null";
        assert duplicateParcels != null : "duplicateParcels should not be null";

        for (ReadOnlyParcel parcel : parcels) {
            try {
                // throws duplicate parcel exception if parcel is non-unique
                addressBook.addParcel(parcel);

                uniqueParcels.add(parcel);
            } catch (DuplicateParcelException ive) {
                duplicateParcels.add(parcel);
            }
        }

        maintainSorted();
        updateFilteredParcelList(PREDICATE_SHOW_ALL_PARCELS);
        indicateAddressBookChanged();
    }
    //@@author

    @Override
    public void updateParcel(ReadOnlyParcel target, ReadOnlyParcel editedParcel)
            throws DuplicateParcelException, ParcelNotFoundException {
        requireAllNonNull(target, editedParcel);

        addressBook.updateParcel(target, editedParcel);
        updateFilteredParcelList(PREDICATE_SHOW_ALL_PARCELS);
        indicateAddressBookChanged();
    }


    //=========== Filtered Parcel List Accessors =============================================================
    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyParcel} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyParcel> getFilteredParcelList() {
        return FXCollections.unmodifiableObservableList(filteredParcels);
    }

    //@@author kennard123661
    /**
     * Returns an unmodifiable view of the list of {@link ReadOnlyParcel} with {@link Status} that is COMPLETED,
     * backed by the internal list of {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyParcel> getCompletedParcelList() {
        return FXCollections.unmodifiableObservableList(completedParcels);
    }

    /**
     * Returns an unmodifiable view of the list of {@link ReadOnlyParcel} with {@link Status} that is not COMPLETED,
     * backed by the internal list of {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyParcel> getUncompletedParcelList() {
        return FXCollections.unmodifiableObservableList(uncompletedParcels);
    }

    /**
     * Returns an unmodifiable view of the list of {@link ReadOnlyParcel} in the {@code activeParcels}
     */
    @Override
    public ObservableList<ReadOnlyParcel> getActiveList() {
        return FXCollections.unmodifiableObservableList(activeParcels);
    }
    //@@author

    @Override
    public void updateFilteredParcelList(Predicate<ReadOnlyParcel> predicate) {
        requireNonNull(predicate);
        filteredParcels.setPredicate(predicate);
    }

    //@@author fustilio
    @Override
    public void maintainSorted() {
        addressBook.sort();
    }

    @Override
    public void forceSelect(Index target) {
        EventsCenter.getInstance().post(new JumpToListRequestEvent(target));
    }

    @Override
    public void forceSelectParcel(ReadOnlyParcel target) {
        forceSelect(Index.fromZeroBased(findIndex(target)));
    }

    @Override
    public void setTabIndex(Index index) {
        this.tabIndex = index;
    }

    @Override
    public Index getTabIndex() {
        return this.tabIndex;
    }

    @Override
    public void addParcelCommand(ReadOnlyParcel toAdd) throws DuplicateParcelException {
        this.addParcel(toAdd);
        this.maintainSorted();
        this.handleTabChange(toAdd);
        this.forceSelectParcel(toAdd);
        indicateAddressBookChanged();
    }

    @Override
    public void editParcelCommand(ReadOnlyParcel parcelToEdit, ReadOnlyParcel editedParcel)
            throws DuplicateParcelException, ParcelNotFoundException {
        this.updateParcel(parcelToEdit, editedParcel);
        this.maintainSorted();
        this.handleTabChange(editedParcel);
        this.forceSelectParcel(editedParcel);
        indicateAddressBookChanged();
    }

    @Override
    public boolean getActiveIsAllBool() {
        return tabIndex.equals(TAB_ALL_PARCELS);
    }

    /**
     * Method to internally change the active list to the correct tab according to the changed parcel.
     * @param targetParcel
     */
    private void handleTabChange(ReadOnlyParcel targetParcel) {
        try {
            if (targetParcel.getStatus().equals(Status.getInstance("COMPLETED"))) {
                if (this.getTabIndex().equals(TAB_ALL_PARCELS)) {
                    uiJumpToTabCompleted();
                }
            } else {
                if (this.getTabIndex().equals(TAB_COMPLETED_PARCELS)) {
                    uiJumpToTabAll();
                }
            }
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void uiJumpToTabAll() {
        EventsCenter.getInstance().post(new JumpToTabRequestEvent(TAB_ALL_PARCELS));
    }

    @Override
    public void uiJumpToTabCompleted() {
        EventsCenter.getInstance().post(new JumpToTabRequestEvent(TAB_COMPLETED_PARCELS));
    }

    /**
     * Method to retrieve the index of a given parcel in the active list.
     */
    private int findIndex(ReadOnlyParcel target) {
        return getActiveList().indexOf(target);
    }
    //@@author

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && filteredParcels.equals(other.filteredParcels)
                && completedParcels.equals(other.completedParcels)
                && uncompletedParcels.equals(other.uncompletedParcels);
    }

    public static Predicate<ReadOnlyParcel> getDeliveredPredicate() {
        return deliveredPredicate;
    }

}
