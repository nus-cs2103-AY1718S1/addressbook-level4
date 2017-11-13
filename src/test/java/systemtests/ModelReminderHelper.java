//@@author inGall
package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.reminder.ReadOnlyReminder;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelReminderHelper {
    private static final Predicate<ReadOnlyReminder> PREDICATE_MATCHING_NO_PRIORITY = unused -> false;

    /**
     * Updates {@code model}'s filtered reminder list to display only {@code toDisplay}.
     */
    public static void setFilteredReminderList(Model model, List<ReadOnlyReminder> toDisplay) {
        Optional<Predicate<ReadOnlyReminder>> predicate =
                toDisplay.stream().map(ModelReminderHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredReminderList(predicate.orElse(PREDICATE_MATCHING_NO_PRIORITY));
    }

    /**
     * @see ModelReminderHelper#setFilteredReminderList(Model, List)
     */
    public static void setFilteredReminderList(Model model, ReadOnlyReminder... toDisplay) {
        setFilteredReminderList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code ReadOnlyReminder} equals to {@code other}.
     */
    private static Predicate<ReadOnlyReminder> getPredicateMatching(ReadOnlyReminder other) {
        return reminder -> reminder.equals(other);
    }
}
