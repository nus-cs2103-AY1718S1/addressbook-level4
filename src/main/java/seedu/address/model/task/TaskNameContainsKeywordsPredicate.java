//@@author ShaocongDong
package seedu.address.model.task;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyTask}'s {@code Name} matches any of the keywords given.
 */
public class TaskNameContainsKeywordsPredicate implements Predicate<ReadOnlyTask> {
    private final List<Name> keywords;

    public TaskNameContainsKeywordsPredicate(List<Name> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyTask task) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getName().toString(), keyword.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskNameContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TaskNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
