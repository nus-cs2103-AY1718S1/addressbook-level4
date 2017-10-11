package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.property.exceptions.DuplicatePropertyException;

/**
 * A list of properties that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Property#equals(Object)
 */
public class UniquePropertyList implements Iterable<Property> {
    private final ObservableList<Property> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty PropertyList.
     */
    public UniquePropertyList() {}

    /**
     * Creates a UniquePropertyList using given properties.
     * Enforces no nulls.
     */
    public UniquePropertyList(Set<Property> properties) {
        requireAllNonNull(properties);
        internalList.addAll(properties);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all properties in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Property> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces all the properties in this list with those in the argument property set.
     */
    public void setTags(Set<Property> tags) {
        requireAllNonNull(tags);
        internalList.setAll(tags);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every property in the argument list exists in this object.
     */
    public void mergeFrom(UniquePropertyList from) {
        final Set<Property> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(tag -> !alreadyInside.contains(tag))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Property as the given argument.
     */
    public boolean contains(Property toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a property to the list.
     *
     * @throws DuplicatePropertyException if the given property already exists in this list (or there exists a
     * property that is equal to the one in the argument).
     */
    public void add(Property toAdd) throws DuplicatePropertyException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePropertyException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Property> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Property> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniquePropertyList // instanceof handles nulls
                && this.internalList.equals(((UniquePropertyList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniquePropertyList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }
}
