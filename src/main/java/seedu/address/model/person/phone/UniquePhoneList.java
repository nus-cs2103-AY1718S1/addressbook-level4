package seedu.address.model.person.phone;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.exceptions.DuplicatePhoneException;
import seedu.address.model.person.exceptions.PhoneNotFoundException;



//@@author eeching
/**
 * A list of phones that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Phone#equals(Object)
 */
public class UniquePhoneList implements Iterable<Phone> {

    private final ObservableList<Phone> internalList = FXCollections.observableArrayList();

    /**
     * Constructs phoneList with a number.
     */

    public UniquePhoneList() {}

    public UniquePhoneList(Phone phone) {

        requireNonNull(phone);
        internalList.add(phone);
    }

    public UniquePhoneList(Set<Phone> phones) {
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
     * Returns true if the list contains an equivalent phone as the given argument.
     */
    public boolean contains(Phone toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a person to the list.
     *
     * @throws DuplicatePhoneException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Phone toAdd) throws DuplicatePhoneException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePhoneException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws PhoneNotFoundException if no such person could be found in the list.
     */
    public boolean remove(Phone toRemove) throws PhoneNotFoundException {
        requireNonNull(toRemove);
        final boolean phoneFoundAndDeleted = internalList.remove(toRemove);
        if (!phoneFoundAndDeleted) {
            throw new PhoneNotFoundException();
        }
        return phoneFoundAndDeleted;
    }


    public int getSize() {
        return internalList.size();
    }

    public String getAllPhone() {

        if (internalList.size() > 1) {
            String rest = "The additional phone number(s) are/is \n";
            int index = 1;
            for (Phone phone: internalList) {
                rest = rest + index + "/ " + phone.number + "\n";
                index++;
            }
            return rest;
        } else {
            return "";
        }
    }


    @Override
    public Iterator<Phone> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Phone> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.person.phone.UniquePhoneList // instanceof handles nulls
                && this.internalList.equals(((seedu.address.model.person.phone.UniquePhoneList) other).internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

}
//@@author
