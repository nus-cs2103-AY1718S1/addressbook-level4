package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.awt.Color;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of tags that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Tag#equals(Object)
 */
public class UniqueTagList implements Iterable<Tag> {

    private static Random random = new Random(123);
    private static boolean tagColorOn;
    private final ObservableList<Tag> internalList = FXCollections.observableArrayList();
    /**
     * Constructs empty TagList.
     */
    public UniqueTagList() {}

    /**
     * Creates a UniqueTagList using given tags.
     * Enforces no nulls.
     *
     * Assign each tag with a unique color
     */
    public UniqueTagList(Set<Tag> tags) {
        requireAllNonNull(tags);
        internalList.addAll(tags);
        assert CollectionUtil.elementsAreUnique(internalList);
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
        if (!tagColorOn) {
            for (Tag tag : tags) {
                tag.setTagColor("#dcdcdc");
                tagColorOn = false;
            }
        } else {
            for (Tag tag : tags) {
                if (tag.getTagColor() == null || tag.getTagColor().equals("#dcdcdc")) {
                    float r = random.nextFloat();
                    float g = random.nextFloat();
                    float b = random.nextFloat();
                    Color randomColor = new Color(r, g, b);
                    tag.setTagColor(convertColorToHexadecimal(randomColor));
                }
                tagColorOn = true;
            }
        }
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

    public void setTagsColorOn() {
        tagColorOn = true;
    }

    public void setTagsColorOff() {
        tagColorOn = false;
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTagException extends DuplicateDataException {
        protected DuplicateTagException() {
            super("Operation would result in duplicate tags");
        }
    }

    /**
     * Converts a color to hexadecimal string
     *
     */
    private static String convertColorToHexadecimal(Color color) {
        String hex = Integer.toHexString(color.getRGB() & 0xffffff);
        if (hex.length() < 6) {
            if (hex.length() == 5) {
                hex = "0" + hex;
            }
            if (hex.length() == 4) {
                hex = "00" + hex;
            }
            if (hex.length() == 3) {
                hex = "000" + hex;
            }
        }
        hex = "#" + hex;
        return hex;
    }

}
