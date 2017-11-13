package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<ReadOnlyPerson> PREDICATE_MATCHING_NO_PERSONS = unused -> false;
    private static final Predicate<ReadOnlyTask> PREDICATE_MATCHING_NO_TASKS = unused -> false;

    /**
     * Updates {@code model}'s filtered person list to display only {@code toDisplay}.
     */
    public static void setFilteredPersonList(Model model, List<ReadOnlyPerson> toDisplay) {
        Optional<Predicate<ReadOnlyPerson>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatchingPersons).reduce(Predicate::or);
        model.updateFilteredPersonList(predicate.orElse(PREDICATE_MATCHING_NO_PERSONS));
    }

    /**
     * @see ModelHelper#setFilteredPersonList(Model, List)
     */
    public static void setFilteredPersonList(Model model, ReadOnlyPerson... toDisplay) {
        setFilteredPersonList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code ReadOnlyPerson} equals to {@code other}.
     */
    private static Predicate<ReadOnlyPerson> getPredicateMatchingPersons(ReadOnlyPerson other) {
        return person -> person.equals(other);
    }

    // @@author ahmadalkaff
    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredTaskList(Model model, List<ReadOnlyTask> toDisplay) {
        Optional<Predicate<ReadOnlyTask>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatchingTasks).reduce(Predicate::or);
        model.updateFilteredTaskList(predicate.orElse(PREDICATE_MATCHING_NO_TASKS));
    }

    /**
     * @see ModelHelper#setFilteredTaskList(Model, List)
     */
    public static void setFilteredTaskList(Model model, ReadOnlyTask... toDisplay) {
        setFilteredTaskList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code ReadOnlyTask} equals to {@code other}.
     */
    private static Predicate<ReadOnlyTask> getPredicateMatchingTasks(ReadOnlyTask other) {
        return task -> task.equals(other);
    }
    // @@author

}
