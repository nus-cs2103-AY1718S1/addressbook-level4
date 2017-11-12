package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.model.parcel.Parcel;
import seedu.address.model.parcel.ReadOnlyParcel;
import seedu.address.model.parcel.UniqueParcelList;
import seedu.address.model.parcel.exceptions.DuplicateParcelException;
import seedu.address.model.parcel.exceptions.ParcelNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueParcelList parcels;
    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        parcels = new UniqueParcelList();
        tags = new UniqueTagList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Parcels and Tags in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setParcels(List<? extends ReadOnlyParcel> parcels) throws DuplicateParcelException {
        this.parcels.setParcels(parcels);
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
            setParcels(newData.getParcelList());
        } catch (DuplicateParcelException e) {
            assert false : "AddressBooks should not have duplicate parcels";
        }

        setTags(new HashSet<>(newData.getTagList()));
        syncMasterTagListWith(parcels);
    }

    //// parcel-level operations

    /**
     * Adds a parcel to the address book.
     * Also checks the new parcel's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the parcel to point to those in {@link #tags}.
     *
     * @throws DuplicateParcelException if an equivalent parcel already exists.
     */
    public void addParcel(ReadOnlyParcel p) throws DuplicateParcelException {
        Parcel newParcel = new Parcel(p);
        syncMasterTagListWith(newParcel);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any parcel
        // in the parcel list.
        parcels.add(newParcel);
    }

    /**
     * Replaces the given parcel {@code target} in the list with {@code editedReadOnlyParcel}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedReadOnlyParcel}.
     *
     * @throws DuplicateParcelException if updating the parcel's details causes the parcel to be equivalent to
     *      another existing parcel in the list.
     * @throws ParcelNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncMasterTagListWith(Parcel)
     */
    public void updateParcel(ReadOnlyParcel target, ReadOnlyParcel editedReadOnlyParcel)
            throws DuplicateParcelException, ParcelNotFoundException {
        requireNonNull(editedReadOnlyParcel);

        Parcel editedParcel = new Parcel(editedReadOnlyParcel);
        syncMasterTagListWith(editedParcel);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any parcel
        // in the parcel list.
        parcels.setParcel(target, editedParcel);
    }

    /**
     * Ensures that every tag in this parcel:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(Parcel parcel) {
        final UniqueTagList parcelTags = new UniqueTagList(parcel.getTags());
        tags.mergeFrom(parcelTags);

        // Create map with values = tag object references in the master list
        // used for checking parcel tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of parcel tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        parcelTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        parcel.setTags(correctTagReferences);
    }

    /**
     * Ensures that every tag in these parcels:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     *  @see #syncMasterTagListWith(Parcel)
     */
    private void syncMasterTagListWith(UniqueParcelList parcels) {
        parcels.forEach(this::syncMasterTagListWith);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws ParcelNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeParcel(ReadOnlyParcel key) throws ParcelNotFoundException {
        if (parcels.remove(key)) {
            return true;
        } else {
            throw new ParcelNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return parcels.asObservableList().size() + " parcels, " + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyParcel> getParcelList() {
        return parcels.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.parcels.equals(((AddressBook) other).parcels)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(parcels, tags);
    }

    //@@author fustilio
    /**
     * Function that sorts the lists of parcels
     */
    public void sort() {
        try {
            this.setParcels(parcels.getSortedList());
        } catch (DuplicateParcelException e) {
            e.printStackTrace();
        }
    }
    //@@author
}
