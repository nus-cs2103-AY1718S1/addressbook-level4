package seedu.address.model.customField;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of customField that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see CustomField#equals(Object)
 */
public class UniqueCustomFieldList implements Iterable<CustomField> {

    public final ObservableList<CustomField> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty CustomFieldList.
     */
    public UniqueCustomFieldList() {}

    /**
     * Creates a UniqueCustomFieldList using given customFields.
     * Enforces no nulls.
     */
    public UniqueCustomFieldList(Set<CustomField> customFields) {
        requireAllNonNull(customFields);
        internalList.addAll(customFields);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all customFields in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<CustomField> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the CustomFields in this list with those in the argument customField list.
     */
    public void setCustomFields(Set<CustomField> customFields) {
        requireAllNonNull(customFields);
        internalList.setAll(customFields);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every customField in the argument list exists in this object.
     */
    public void mergeFrom(UniqueCustomFieldList from) {
        final Set<CustomField> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(customField -> !alreadyInside.contains(customField))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent CustomField as the given argument.
     */
    public boolean contains(CustomField toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a CustomField to the list.
     */
    public void add(CustomField toAdd) {
        requireNonNull(toAdd);

        for (CustomField cf : internalList) {
            if (cf.customFieldName.equals(toAdd.customFieldName)) {
                if (toAdd.getCustomFieldValue().equals("")) {
                    remove(toAdd.customFieldName);
                    return;
                }
                cf.setCustomFieldValue(toAdd.getCustomFieldValue());
                return;
            }
        }

        if (toAdd.getCustomFieldValue().equals("")) {
            return;
        }

        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     *  Remove a CustomField from the list according to the custom field name
     *
     * @param customFieldName
     */
    public void remove(String customFieldName) {
        for (CustomField cf : internalList) {
            if (cf.customFieldName.equals(customFieldName)) {
                internalList.remove(cf);
                break;
            }
        }
    }

    @Override
    public Iterator<CustomField> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<CustomField> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueCustomFieldList // instanceof handles nulls
                && this.internalList.equals(((UniqueCustomFieldList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueCustomFieldList other) {
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
