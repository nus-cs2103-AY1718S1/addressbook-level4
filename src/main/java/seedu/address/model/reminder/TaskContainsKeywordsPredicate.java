package seedu.address.model.reminder;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class TaskContainsKeywordsPredicate implements Predicate<ReadOnlyReminder> {
    private final List<String> keywords;

    public TaskContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyReminder reminder) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(reminder.getTask().taskName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TaskContainsKeywordsPredicate) other).keywords)); // state check
    }

}
