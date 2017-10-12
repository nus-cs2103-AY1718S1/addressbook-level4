package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.parcel.ReadOnlyParcel;
import seedu.address.model.tag.Tag;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the parcels list.
     * This list will not contain any duplicate parcels.
     */
    ObservableList<ReadOnlyParcel> getParcelList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
