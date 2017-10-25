package seedu.address.model.insurance;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see LifeInsurance#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueLifeInsuranceList implements Iterable<LifeInsurance> {

    private final ObservableList<LifeInsurance> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyInsurance> mappedList = EasyBind.map(internalList, (insurance) -> insurance);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(ReadOnlyInsurance toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a person to the list.
     *
     * @throws DuplicatePersonException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(ReadOnlyInsurance toAdd) throws DuplicatePersonException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(new LifeInsurance(toAdd));
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if the replacement is equivalent to another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    public void setInsurance(ReadOnlyInsurance target, ReadOnlyInsurance editedInsurance)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedInsurance);

        int index = internalList.indexOf(target);
        if (index == -1) {
            //TODO: WRONG exception
            throw new PersonNotFoundException();
        }

        if (!target.equals(editedInsurance) && internalList.contains(editedInsurance)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, new LifeInsurance(editedInsurance));
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public boolean remove(ReadOnlyInsurance toRemove) throws PersonNotFoundException {
        requireNonNull(toRemove);
        final boolean personFoundAndDeleted = internalList.remove(toRemove);
        if (!personFoundAndDeleted) {
            throw new PersonNotFoundException();
        }
        return personFoundAndDeleted;
    }

    public void setLifeInsurances(UniqueLifeInsuranceList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setLifeInsurances(List<? extends ReadOnlyInsurance> insurances) throws DuplicatePersonException {
        final UniqueLifeInsuranceList replacement = new UniqueLifeInsuranceList();
        for (final ReadOnlyInsurance insurance : insurances) {
            replacement.add(new LifeInsurance(insurance));
        }
        setLifeInsurances(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyInsurance> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    @Override
    public Iterator<LifeInsurance> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueLifeInsuranceList // instanceof handles nulls
                && this.internalList.equals(((UniqueLifeInsuranceList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
