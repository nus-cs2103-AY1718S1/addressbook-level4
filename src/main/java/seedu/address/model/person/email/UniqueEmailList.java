package seedu.address.model.person.email;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.Tag;

/**
 * A list of emails that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Tag#equals(Object)
 */
public class UniqueEmailList implements Iterable<Email> {

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
     * Replaces the Emails in this list with those in the argument tag list.
     */
    public void setEmails(Set<Email> emails) {
        requireAllNonNull(emails);
        internalList.setAll(emails);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every email in the argument list exists in this object.
     */
    public void mergeFrom(UniqueEmailList from) {
        final Set<Email> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(email -> !alreadyInside.contains(email))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Email as the given argument.
     */
    public boolean contains(Email toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Email to the list.
     *
     * @throws DuplicateEmailException if the Email to add is a duplicate of an existing Tag in the list.
     */
    public void add(Email toAdd) throws DuplicateEmailException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateEmailException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Email> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
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

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueEmailList other) {
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
    public static class DuplicateEmailException extends DuplicateDataException {
        protected DuplicateEmailException() {
            super("Operation would result in duplicate emails");
        }
    }

}

