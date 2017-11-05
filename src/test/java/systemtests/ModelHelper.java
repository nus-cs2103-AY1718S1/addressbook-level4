package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.module.ReadOnlyLesson;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<ReadOnlyLesson> PREDICATE_MATCHING_NO_LESSONS = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<ReadOnlyLesson> toDisplay) {
        Optional<Predicate<ReadOnlyLesson>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredLessonList(predicate.orElse(PREDICATE_MATCHING_NO_LESSONS));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, ReadOnlyLesson... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code ReadOnlyLesson} equals to {@code other}.
     */
    private static Predicate<ReadOnlyLesson> getPredicateMatching(ReadOnlyLesson other) {
        return lesson -> lesson.equals(other);
    }
}
