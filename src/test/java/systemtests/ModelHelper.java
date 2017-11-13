package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.place.ReadOnlyPlace;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<ReadOnlyPlace> PREDICATE_MATCHING_NO_PLACES = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<ReadOnlyPlace> toDisplay) {
        Optional<Predicate<ReadOnlyPlace>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredPlaceList(predicate.orElse(PREDICATE_MATCHING_NO_PLACES));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, ReadOnlyPlace... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code ReadOnlyPlace} equals to {@code other}.
     */
    private static Predicate<ReadOnlyPlace> getPredicateMatching(ReadOnlyPlace other) {
        return place -> place.equals(other);
    }
}
