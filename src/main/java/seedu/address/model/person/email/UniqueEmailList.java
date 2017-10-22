package seedu.address.model.person.email;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of emails that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Email#equals(Object)
 */
public class UniqueEmailList {

    private final ObservableList<Email> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty EmailList.
     */
    public UniqueEmailList() {}

    /**
     * Creates a UniqueEmailList using given tags.
     * Enforces no nulls.
     */
    public UniqueEmailList(Set<Email> emails) {
        requireAllNonNull(emails);
        internalList.addAll(emails);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all Emails in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Email> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Email> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueEmailList // instanceof handles nulls
                && this.internalList.equals(((UniqueEmailList) other).internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

}

