package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.place.ReadOnlyPlace;
import seedu.address.model.place.exceptions.DuplicatePlaceException;
import seedu.address.model.place.exceptions.PlaceNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyPlace> PREDICATE_SHOW_ALL_PLACES = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given place. */
    void deletePlace(ReadOnlyPlace target) throws PlaceNotFoundException;

    /** Adds the given place */
    void addPlace(ReadOnlyPlace place) throws DuplicatePlaceException;

    //@@author Chng-Zhi-Xuan
    /**
     * Adds tag to target place in address book.
     *
     * @throws PlaceNotFoundException if place cannot be found in the list.
     *
     * @throws DuplicatePlaceException if after removal, the place's details causes the place to be
     *      equivalent to another existing place in the list.
     *
     * @throws UniqueTagList.DuplicateTagException if added tag is a duplicate.
     */
    void addTag(ReadOnlyPlace place, Tag tag) throws DuplicatePlaceException, PlaceNotFoundException,
                                                    UniqueTagList.DuplicateTagException;

    /**
     * Removes given tag from all places in address book.
     *
     * @throws PlaceNotFoundException if place cannot be found in the list.
     *
     * @throws DuplicatePlaceException if after removal, the place's details causes the place to be
     *      equivalent to another existing place in the list.
     */
    void removeAllTags(Tag tagName) throws PlaceNotFoundException, DuplicatePlaceException;
    //@@author Chng-Zhi-Xuan

    /**
     * Replaces the given place {@code target} with {@code editedPlace}.
     *
     * @throws DuplicatePlaceException if updating the place's details causes the place to be equivalent to
     *      another existing place in the list.
     * @throws PlaceNotFoundException if {@code target} could not be found in the list.
     */
    void updatePlace(ReadOnlyPlace target, ReadOnlyPlace editedPlace)
            throws DuplicatePlaceException, PlaceNotFoundException;

    /** Returns an unmodifiable view of the filtered place list */
    ObservableList<ReadOnlyPlace> getFilteredPlaceList();

    /**
     * Updates the filter of the filtered place list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPlaceList(Predicate<ReadOnlyPlace> predicate);

}
