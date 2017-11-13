package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.place.Place;
import seedu.address.model.place.ReadOnlyPlace;
import seedu.address.model.place.exceptions.DuplicatePlaceException;
import seedu.address.model.place.exceptions.PlaceNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<ReadOnlyPlace> filteredPlaces;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPlaces = new FilteredList<>(this.addressBook.getPlaceList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    //@@author aungmyin23
    @Override
    public void sortPlaces() {
        addressBook.sortPlaces();
    }
    //@@author

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    @Override
    public synchronized void deletePlace(ReadOnlyPlace target) throws PlaceNotFoundException {
        addressBook.removePlace(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPlace(ReadOnlyPlace place) throws DuplicatePlaceException {
        addressBook.addPlace(place);
        updateFilteredPlaceList(PREDICATE_SHOW_ALL_PLACES);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePlace(ReadOnlyPlace target, ReadOnlyPlace editedPlace)
            throws DuplicatePlaceException, PlaceNotFoundException {
        requireAllNonNull(target, editedPlace);

        addressBook.updatePlace(target, editedPlace);
        indicateAddressBookChanged();
    }

    //@@author Chng-Zhi-Xuan
    /**
     * Adds given tag to target place
     * @param place
     * @param newTag
     * @throws DuplicatePlaceException
     * @throws PlaceNotFoundException
     * @throws UniqueTagList.DuplicateTagException
     */
    public void addTag(ReadOnlyPlace place, Tag newTag) throws DuplicatePlaceException, PlaceNotFoundException,
                                                                    UniqueTagList.DuplicateTagException {
        Predicate oldPredicate = filteredPlaces.getPredicate();
        filteredPlaces.setPredicate(PREDICATE_SHOW_ALL_PLACES);

        Place updatedPlace = new Place(place);

        Set<Tag> currentTags = updatedPlace.getTags();
        Set<Tag> updatedTags = new HashSet<Tag>();

        updatedTags.addAll(currentTags);

        if (updatedTags.contains(newTag)) {
            throw new UniqueTagList.DuplicateTagException();
        } else {
            updatedTags.add(newTag);
            updatedPlace.setTags(updatedTags);
            addressBook.updatePlace(place, updatedPlace);
            indicateAddressBookChanged();
        }

        filteredPlaces.setPredicate(oldPredicate);
    }

    /**
     * Removes given Tag from all places in address book
     * @param tag
     * @throws PlaceNotFoundException
     * @throws DuplicatePlaceException
     */

    public void removeAllTags(Tag tag) throws PlaceNotFoundException, DuplicatePlaceException {
        Predicate oldPredicate = filteredPlaces.getPredicate();
        filteredPlaces.setPredicate(PREDICATE_SHOW_ALL_PLACES);

        for (int i = 0; i < addressBook.getPlaceList().size(); i++) {
            ReadOnlyPlace currentPlace = addressBook.getPlaceList().get(i);
            Place updatedPlace = new Place(currentPlace);

            Set<Tag> currentTags = updatedPlace.getTags();
            Set<Tag> updatedTags = new HashSet<Tag>();

            Iterator<Tag> iter = currentTags.iterator();

            while (iter.hasNext()) {
                Tag currTag = iter.next();
                if (!currTag.tagName.equals(tag.tagName)) {
                    updatedTags.add(currTag);
                }
            }

            updatedPlace.setTags(updatedTags);
            indicateAddressBookChanged();

            addressBook.updatePlace(currentPlace, updatedPlace);
        }

        filteredPlaces.setPredicate(oldPredicate);
    }
    //@@author

    //=========== Filtered Place List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyPlace} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyPlace> getFilteredPlaceList() {
        return FXCollections.unmodifiableObservableList(filteredPlaces);
    }

    @Override
    public void updateFilteredPlaceList(Predicate<ReadOnlyPlace> predicate) {
        requireNonNull(predicate);
        filteredPlaces.setPredicate(predicate);
    }

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
                && filteredPlaces.equals(other.filteredPlaces);
    }

}
