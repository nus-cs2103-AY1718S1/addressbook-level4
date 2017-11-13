// @@author ahmadalkaff
package seedu.address.model.person;

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
 * A list of emails that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Phone#equals(Object)
 */
public class EmailList implements Iterable<Email> {

    private final ObservableList<Email> internalList = FXCollections.observableArrayList();

    /**
     * Constructs an empty EmailList
     */
    public EmailList() {

    }

    /**
     * Creates a EmailList using given emails.
     * Enforces no nulls.
     */
    public EmailList(Set<Email> emails) {
        requireAllNonNull(emails);
        internalList.addAll(emails);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all emails in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Email> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Adds an email to the list.
     *
     * @throws PhoneList.DuplicatePhoneException if the email to add is a duplicate of an existing Phone in the list.
     */
    public void add(Email toAdd) throws EmailList.DuplicateEmailException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new EmailList.DuplicateEmailException();
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
     * Returns true if the list contains an equivalent email as the given argument.
     */
    public boolean contains(Email toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof EmailList // instanceof handles nulls
                && this.internalList.equals(((EmailList) other).internalList));
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateEmailException extends DuplicateDataException {
        protected DuplicateEmailException() {
            super("Operation would result in duplicate mods");
        }
    }
}
