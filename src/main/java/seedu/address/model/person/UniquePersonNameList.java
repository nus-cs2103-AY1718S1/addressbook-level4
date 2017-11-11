//@@author 17navasaw
package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of person names that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Name#equals(Object)
 */
public class UniquePersonNameList {
    private final ObservableList<Name> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty PersonNameList.
     */
    public UniquePersonNameList() {}

    /**
     * Creates a UniquePersonNameList using given names.
     * Enforces no nulls.
     */
    public UniquePersonNameList(Set<Name> personNames) {
        requireAllNonNull(personNames);
        internalList.addAll(personNames);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all Names in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Name> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Name> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniquePersonNameList // instanceof handles nulls
                && this.internalList.equals(((UniquePersonNameList) other).internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }
}
