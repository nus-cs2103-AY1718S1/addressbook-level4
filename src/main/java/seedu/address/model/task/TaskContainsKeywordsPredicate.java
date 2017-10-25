package seedu.address.model.task;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyTask}'s {@code Name} and/or {@code Deadline} matches any of the keywords given.
 */
public class TaskContainsKeywordsPredicate implements Predicate<ReadOnlyTask> {
    private final List<String> keywords;

    public TaskContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyTask task) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getDescription().taskDescription, keyword)
                || StringUtil.containsWordIgnoreCase(task.getDeadline().date, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.task
                .TaskContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((seedu.address.model.task.TaskContainsKeywordsPredicate) other)
                .keywords)); // state check
    }
}
