package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<ReadOnlyPerson> PREDICATE_MATCHING_NO_PERSONS = unused -> false;
    private static final Predicate<ReadOnlyEvent> PREDICATE_MATCHING_NO_EVENTS = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<ReadOnlyPerson> toDisplay) {
        Optional<Predicate<ReadOnlyPerson>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredPersonList(predicate.orElse(PREDICATE_MATCHING_NO_PERSONS));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, ReadOnlyPerson... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code ReadOnlyPerson} equals to {@code other}.
     */
    private static Predicate<ReadOnlyPerson> getPredicateMatching(ReadOnlyPerson other) {
        return person -> person.equals(other);
    }

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredEventList(Model model, List<ReadOnlyEvent> toDisplay) {
        Optional<Predicate<ReadOnlyEvent>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateEventMatching).reduce(Predicate::or);
        model.updateFilteredEventList(predicate.orElse(PREDICATE_MATCHING_NO_EVENTS));
    }

    /**
     * @see ModelHelper#setFilteredEventList(Model, List)
     */
    public static void setFilteredEventList(Model model, ReadOnlyEvent... toDisplay) {
        setFilteredEventList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code ReadOnlyEvent} equals to {@code other}.
     */
    private static Predicate<ReadOnlyEvent> getPredicateEventMatching(ReadOnlyEvent other) {
        return event -> event.equals(other);
    }
}
