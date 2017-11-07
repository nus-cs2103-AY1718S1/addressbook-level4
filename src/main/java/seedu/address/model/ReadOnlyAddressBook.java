package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.place.ReadOnlyPlace;
import seedu.address.model.tag.Tag;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the places list.
     * This list will not contain any duplicate places.
     */
    ObservableList<ReadOnlyPlace> getPlaceList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
