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
     * Updates {@code model}'s filtered list to display only {@code toDisplayPerson}.
     */
    public static void setFilteredList(Model model, List<ReadOnlyPerson> toDisplayPerson) {
        Optional<Predicate<ReadOnlyPerson>> predicatePerson =
                toDisplayPerson.stream().map(ModelHelper::getPredicatePersonMatching).reduce(Predicate::or);
        model.updateFilteredPersonList(predicatePerson.orElse(PREDICATE_MATCHING_NO_PERSONS));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, ReadOnlyPerson... toDisplayPerson) {
        setFilteredList(model, Arrays.asList(toDisplayPerson));
    }


    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplayTask}.
     */
    public static void setFilteredTaskList(Model model, List<ReadOnlyTask> toDisplayTask) {
        Optional<Predicate<ReadOnlyTask>> predicateTask =
                toDisplayTask.stream().map(ModelHelper::getPredicateTaskMatching).reduce(Predicate::or);
        model.updateFilteredTaskList(predicateTask.orElse(PREDICATE_MATCHING_NO_TASKS));
    }


    /**
     * @see ModelHelper#setFilteredTaskList(Model, List)
     */
    public static void setFilteredTaskList(Model model, ReadOnlyTask... toDisplayTask) {
        setFilteredTaskList(model, Arrays.asList(toDisplayTask));
    };

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
