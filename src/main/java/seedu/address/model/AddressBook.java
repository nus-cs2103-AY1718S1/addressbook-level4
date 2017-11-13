package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.model.place.Place;
import seedu.address.model.place.ReadOnlyPlace;
import seedu.address.model.place.UniquePlaceList;
import seedu.address.model.place.exceptions.DuplicatePlaceException;
import seedu.address.model.place.exceptions.PlaceNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePlaceList places;
    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        places = new UniquePlaceList();
        tags = new UniqueTagList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Places and Tags in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations
    //@@author aungmyin23
    public void sortPlaces() {
        places.sort();
    }
    //@@author
    public void setPlaces(List<? extends ReadOnlyPlace> places) throws DuplicatePlaceException {
        this.places.setPlaces(places);
    }

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        try {
            setPlaces(newData.getPlaceList());
        } catch (DuplicatePlaceException e) {
            assert false : "AddressBooks should not have duplicate places";
        }

        setTags(new HashSet<>(newData.getTagList()));
        syncMasterTagListWith(places);
    }

    //// place-level operations

    /**
     * Adds a place to the address book.
     * Also checks the new place's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the place to point to those in {@link #tags}.
     *
     * @throws DuplicatePlaceException if an equivalent place already exists.
     */
    public void addPlace(ReadOnlyPlace p) throws DuplicatePlaceException {
        Place newPlace = new Place(p);
        syncMasterTagListWith(newPlace);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any place
        // in the place list.
        places.add(newPlace);
    }

    /**
     * Replaces the given place {@code target} in the list with {@code editedReadOnlyPlace}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedReadOnlyPlace}.
     *
     * @throws DuplicatePlaceException if updating the place's details causes the place to be equivalent to
     *      another existing place in the list.
     * @throws PlaceNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncMasterTagListWith(Place)
     */
    public void updatePlace(ReadOnlyPlace target, ReadOnlyPlace editedReadOnlyPlace)
            throws DuplicatePlaceException, PlaceNotFoundException {
        requireNonNull(editedReadOnlyPlace);

        Place editedPlace = new Place(editedReadOnlyPlace);
        syncMasterTagListWith(editedPlace);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any place
        // in the place list.
        places.setPlace(target, editedPlace);
    }

    /**
     * Ensures that every tag in this place:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(Place place) {
        final UniqueTagList placeTags = new UniqueTagList(place.getTags());
        tags.mergeFrom(placeTags);

        // Create map with values = tag object references in the master list
        // used for checking place tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of place tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        placeTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        place.setTags(correctTagReferences);
    }

    /**
     * Ensures that every tag in these places:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     *  @see #syncMasterTagListWith(Place)
     */
    private void syncMasterTagListWith(UniquePlaceList places) {
        places.forEach(this::syncMasterTagListWith);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws PlaceNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removePlace(ReadOnlyPlace key) throws PlaceNotFoundException {
        if (places.remove(key)) {
            return true;
        } else {
            throw new PlaceNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return places.asObservableList().size() + " places, " + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyPlace> getPlaceList() {
        return places.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.places.equals(((AddressBook) other).places)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(places, tags);
    }
}
