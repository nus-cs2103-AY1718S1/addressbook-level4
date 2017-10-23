package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * A list of tags that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Tag#equals(Object)
 */
public class UniqueTagList implements Iterable<Tag> {

    private final ObservableList<Tag> internalList = FXCollections.observableArrayList();
    private Tag pinTag;

    /**
     * Constructs empty TagList.
     */
    public UniqueTagList() {
        pinTag = createPinTag();
    }

    /**
     * Creates a UniqueTagList using given tags.
     * Enforces no nulls.
     */
    public UniqueTagList(Set<Tag> tags) {
        requireAllNonNull(tags);
        internalList.addAll(tags);
        pinTag = createPinTag();

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Creates a pin tag
     * @return a Pin Tag to be used to add or remove person to be pinned in the address book
     */
    private Tag createPinTag() {
        try {
            return new Tag("Pinned");
        } catch (IllegalValueException ive) {
            return null; //Will not reach here
        }

    }
    /**
     * Returns all tags in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Tag> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Tags in this list with those in the argument tag list.
     */
    public void setTags(Set<Tag> tags) {
        requireAllNonNull(tags);
        internalList.setAll(tags);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every tag in the argument list exists in this object.
     */
    public void mergeFrom(UniqueTagList from) {
        final Set<Tag> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(tag -> !alreadyInside.contains(tag))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Tag as the given argument.
     */
    public boolean contains(Tag toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Tag to the list.
     *
     * @throws DuplicateTagException if the Tag to add is a duplicate of an existing Tag in the list.
     */
    public void add(Tag toAdd) throws DuplicateTagException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTagException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Adds a pin tag to the tag list
     */
    public void addPinTag() {
        internalList.add(pinTag);
    }

    /**
     * Removes a pin tag from the tag list
     * @throws IllegalValueException
     */
    public void removePinTag() throws IllegalValueException {
        if (contains(pinTag)) {
            internalList.remove(pinTag);
        } else {
            throw new IllegalValueException("Unable to find tag");
        }
    }


    @Override
    public Iterator<Tag> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Tag> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    /**
     * Searches the tag list to find Pinned Tag. Can always be found as the person in pinned already
     *
     * @param pinnedPerson
     * @return true is pin tag exists, false if no pin tag
     */
    public static boolean containsPinTag(ReadOnlyPerson pinnedPerson) {
        for (Tag tag : pinnedPerson.getTags()) {
            if ("Pinned".equals(tag.tagName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueTagList // instanceof handles nulls
                        && this.internalList.equals(((UniqueTagList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueTagList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTagException extends DuplicateDataException {
        protected DuplicateTagException() {
            super("Operation would result in duplicate tags");
        }
    }

}
