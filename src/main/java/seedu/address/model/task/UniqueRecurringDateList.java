package seedu.address.model.task;

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
 * A list of recurring dates that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see RecurringDate#equals(Object)
 */
public class UniqueRecurringDateList implements Iterable<RecurringDate> {

    private final ObservableList<RecurringDate> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty RecurringDateList.
     */
    public UniqueRecurringDateList() {}

    /**
     * Creates a UniqueRecurringDateList using given tags.
     * Enforces no nulls.
     */
    public UniqueRecurringDateList(Set<RecurringDate> tags) {
        requireAllNonNull(tags);
        internalList.addAll(tags);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all recurring dates in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<RecurringDate> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Recurring Dates in this list with those in the argument recurring date list.
     */
    public void setTags(Set<RecurringDate> recurringDates) {
        requireAllNonNull(recurringDates);
        internalList.setAll(recurringDates);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every recurring date in the argument list exists in this object.
     */
    public void mergeFrom(UniqueRecurringDateList from) {
        final Set<RecurringDate> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(recurringDate -> !alreadyInside.contains(recurringDate))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Recurring Date as the given argument.
     */
    public boolean contains(RecurringDate toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Recurring Date to the list.
     *
     * @throws DuplicateTagException if the Recurring Date to add is a duplicate of an existing Recurring Date
     * in the list.
     */
    //*TODO: FIX EXCEPTION ?? DUPLICATE RECURRING DATES SHOULD BE FINE
    public void add(RecurringDate toAdd) throws DuplicateTagException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTagException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<RecurringDate> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<RecurringDate> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueRecurringDateList // instanceof handles nulls
                && this.internalList.equals(((UniqueRecurringDateList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueRecurringDateList other) {
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
