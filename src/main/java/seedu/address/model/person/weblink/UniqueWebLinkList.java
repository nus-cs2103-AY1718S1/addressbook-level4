package seedu.address.model.person.weblink;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
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
 * @see WebLink#equals(Object)
 */

public class UniqueWebLinkList implements Iterable<WebLink> {

    private final ObservableList<WebLink> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty WebLinkList.
     */
    public UniqueWebLinkList() {}

    /**
     * Creates a UniqueTagList using given webLinks.
     * Enforces no nulls.
     */
    public UniqueWebLinkList(Set<WebLink> webLinks) {
        requireAllNonNull(webLinks);
        internalList.addAll(webLinks);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all tags in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<WebLink> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the WebLinks in this list with those in the argument WebLinks list.
     */
    public void setWebLinks(Set<WebLink> webLinks) {
        requireAllNonNull(webLinks);
        internalList.setAll(webLinks);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every webLink in the argument list exists in this object.
     */
    public void mergeFrom(UniqueWebLinkList from) {
        final Set<WebLink> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(webLinks -> !alreadyInside.contains(webLinks))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent weblink as the given argument.
     */
    public boolean contains(WebLink toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a webLink to the list.
     *
     * @throws DuplicateWebLinkException if the webLink to add is a duplicate of an existing Tag in the list.
     */
    public void add(WebLink toAdd) throws DuplicateWebLinkException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateWebLinkException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<WebLink> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<WebLink> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueWebLinkList // instanceof handles nulls
                && this.internalList.equals(((UniqueWebLinkList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueWebLinkList other) {
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
    public static class DuplicateWebLinkException extends DuplicateDataException {
        protected DuplicateWebLinkException() {
            super("Operation would result in duplicate web Links");
        }
    }

}
