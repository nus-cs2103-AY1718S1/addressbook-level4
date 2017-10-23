package seedu.address.model.customfields;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * A list of fields that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see CustomField#equals(Object)
 */
public class CustomFieldsList implements Iterable<CustomField> {
    private final ObservableList<CustomField> internalList = FXCollections.observableArrayList();

    public CustomFieldsList(Set<CustomField> fields) {
        requireAllNonNull(fields);
        internalList.addAll(fields);
    }

    public CustomFieldsList() {
    }

    /**
     * Adds a field to the list.
     *
     * @param toAdd
     */
    public void add(CustomField toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CustomFieldsList // instanceof handles nulls
                && this.internalList.equals(((CustomFieldsList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    public Set<CustomField> toSet() {
        return new HashSet<>(internalList);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<CustomField> iterator() {
        return null;
    }
}
