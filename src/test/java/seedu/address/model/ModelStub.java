package seedu.address.model;

import static org.junit.Assert.fail;

import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToTabRequestEvent;
import seedu.address.model.parcel.ReadOnlyParcel;
import seedu.address.model.parcel.exceptions.DuplicateParcelException;
import seedu.address.model.parcel.exceptions.ParcelNotFoundException;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void addParcel(ReadOnlyParcel parcel) throws DuplicateParcelException {
        fail("This method should not be called.");
    }

    @Override
    public void addAllParcels(List<ReadOnlyParcel> parcels, List<ReadOnlyParcel> uniqueParcels,
                              List<ReadOnlyParcel> duplicateParcels) {
        fail("This method should not be called.");
    }

    //@@author kennard123661
    @Override
    public void setActiveList(JumpToTabRequestEvent event) {
        fail("This method should not be called.");
    }
    //@@author

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        fail("This method should not be called.");
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public void deleteParcel(ReadOnlyParcel target) throws ParcelNotFoundException {
        fail("This method should not be called.");
    }

    @Override
    public void updateParcel(ReadOnlyParcel target, ReadOnlyParcel editedParcel)
            throws DuplicateParcelException {
        fail("This method should not be called.");
    }

    @Override
    public ObservableList<ReadOnlyParcel> getFilteredParcelList() {
        fail("This method should not be called.");
        return null;
    }

    //@@author kennard123661
    @Override
    public ObservableList<ReadOnlyParcel> getCompletedParcelList() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public ObservableList<ReadOnlyParcel> getActiveList() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public ObservableList<ReadOnlyParcel> getUncompletedParcelList() {
        fail("This method should not be called.");
        return null;
    }
    //@@author

    @Override
    public void updateFilteredParcelList(Predicate<ReadOnlyParcel> predicate) {
        fail("This method should not be called.");
    }

    @Override
    public void maintainSorted() {
        fail("This method should not be called.");
    }

    @Override
    public void forceSelect(Index target) {
        fail("This method should not be called.");
    }

    @Override
    public void forceSelectParcel(ReadOnlyParcel target) {
        fail("This method should not be called.");
    }

    @Override
    public void setTabIndex(Index index) {
        fail("This method should not be called.");
    }

    @Override
    public Index getTabIndex() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public void addParcelCommand(ReadOnlyParcel parcel) throws DuplicateParcelException {
        fail("This method should not be called.");
    }

    @Override
    public void editParcelCommand(ReadOnlyParcel parcelToEdit, ReadOnlyParcel editedParcel)
            throws DuplicateParcelException, ParcelNotFoundException {
        fail("This method should not be called.");
    }

    @Override
    public boolean getActiveIsAllBool() {
        fail("This method should not be called.");
        return false;
    }

    @Override
    public void uiJumpToTabAll() {
        fail("This method should not be called.");
    }

    @Override
    public void uiJumpToTabCompleted() {
        fail("This method should not be called.");
    }

}
