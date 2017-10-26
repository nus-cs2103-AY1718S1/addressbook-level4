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
     * Updates {@code model}'s filtered list to display only {@code personsToDisplay}.
     */
    public static void setFilteredPersonsList(Model model, List<ReadOnlyPerson> personsToDisplay) {
        Optional<Predicate<ReadOnlyPerson>> predicatePersons =
                personsToDisplay.stream().map(ModelHelper::getPredicatePersonMatching).reduce(Predicate::or);
        model.updateFilteredPersonList(predicatePersons.orElse(PREDICATE_MATCHING_NO_PERSONS));
    }

    /**
     * @see ModelHelper#setFilteredPersonsList(Model, List)
     */
    public static void setFilteredPersonsList(Model model, ReadOnlyPerson... personsToDisplay) {
        setFilteredPersonsList(model, Arrays.asList(personsToDisplay));
    }

    /**
     * Updates {@code model}'s filtered list to display only {@code tasksToDisplay}.
     */
    public static void setFilteredTasksList(Model model, List<ReadOnlyTask> tasksToDisplay) {
        Optional<Predicate<ReadOnlyTask>> predicateTasks =
                tasksToDisplay.stream().map(ModelHelper::getPredicateTaskMatching).reduce(Predicate::or);
        model.updateFilteredTaskList(predicateTasks.orElse(PREDICATE_MATCHING_NO_TASKS));
    }

    /**
     * @see ModelHelper#setFilteredPersonsList(Model, List)
     */
    public static void setFilteredTasksList(Model model, ReadOnlyTask... tasksToDisplay) {
        setFilteredTasksList(model, Arrays.asList(tasksToDisplay));
    }

    /**
     * Update {@code model}'s filtered task list to display only {@code tasksToDisplay}.
     */
    public static void setFilteredTaskList(Model model, List<ReadOnlyTask> tasksToDisplay) {
        Optional<Predicate<ReadOnlyTask>> predicate =
            tasksToDisplay.stream().map(ModelHelper::getPredicateTaskMatching).reduce(Predicate::or);
        model.updateFilteredTaskList(predicate.orElse(PREDICATE_MATCHING_NO_TASKS));
    }

    /**
     * @see ModelHelper#setFilteredTaskList(Model, List)
     */
    public static void setFilteredTaskList(Model model, ReadOnlyTask... toDisplay) {
        setFilteredTaskList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code ReadOnlyPerson} equals to {@code other}.
     */
    private static Predicate<ReadOnlyPerson> getPredicatePersonMatching(ReadOnlyPerson other) {
        return person -> person.equals(other);
    }

    /**
     * Returns a predicate that evaluates to true if this {@code ReadOnlyTask} equals to {@code other}.
     */
    private static Predicate<ReadOnlyTask> getPredicateTaskMatching(ReadOnlyTask other) {
        return task -> task.equals(other);
    }
}
