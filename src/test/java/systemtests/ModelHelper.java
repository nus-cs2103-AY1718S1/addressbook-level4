package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.parcel.ReadOnlyParcel;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<ReadOnlyParcel> PREDICATE_MATCHING_NO_PARCELS = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<ReadOnlyParcel> toDisplay) {
        Optional<Predicate<ReadOnlyParcel>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredParcelList(predicate.orElse(PREDICATE_MATCHING_NO_PARCELS));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, ReadOnlyParcel... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code ReadOnlyParcel} equals to {@code other}.
     */
    private static Predicate<ReadOnlyParcel> getPredicateMatching(ReadOnlyParcel other) {
        return parcel -> parcel.equals(other);
    }
}
