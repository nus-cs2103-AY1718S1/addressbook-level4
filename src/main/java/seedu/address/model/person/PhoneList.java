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
 * A list of phones that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Phone#equals(Object)
 */
public class PhoneList implements Iterable<Phone> {

    private final ObservableList<Phone> internalList = FXCollections.observableArrayList();

    /**
     * Constructs an empty PhoneList
     */
    public PhoneList() {

    }

    /**
     * Creates a PhoneList using given phones.
     * Enforces no nulls.
     */
    public PhoneList(Set<Phone> phones) {
        requireAllNonNull(phones);
        internalList.addAll(phones);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all phones in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Phone> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Adds a phone to the list.
     *
     * @throws PhoneList.DuplicatePhoneException if the phone to add is a duplicate of an existing phone in the list.
     */
    public void add(Phone toAdd) throws PhoneList.DuplicatePhoneException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new PhoneList.DuplicatePhoneException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Phone> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns true if the list contains an equivalent phone as the given argument.
     */
    public boolean contains(Phone toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof PhoneList // instanceof handles nulls
                && this.internalList.equals(((PhoneList) other).internalList));
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicatePhoneException extends DuplicateDataException {
        protected DuplicatePhoneException() {
            super("Operation would result in duplicate mods");
        }
    }
}
