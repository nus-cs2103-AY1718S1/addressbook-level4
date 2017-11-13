package seedu.address.model.insurance;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;

//@@author OscarWang114
/**
 * A list of insurances that enforces uniqueness between its elements and does not allow nulls.
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
     * Adds an insurance to the list.
     *
     */
    public void add(ReadOnlyInsurance toAdd) {
        requireNonNull(toAdd);
        internalList.add(new LifeInsurance(toAdd));
        sortInsurances();
    }

    //@@author Juxarius

    /**
     * sort insurance in descending order according to premium,
     * change the sign in the return statement to make it ascending
     */
    public void sortInsurances() {
        internalList.sort((insurance1, insurance2) -> {
            if (insurance1.getPremium().equals(insurance2.getPremium())) {
                return 0;
            } else {
                return insurance1.getPremium().toDouble() < insurance2.getPremium().toDouble() ? 1 : -1;
            }
        });
    }
    //@@author

    //@@author OscarWang114
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
