package seedu.address.model.task;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyTask}'s details matches any of the keywords given.
 */
public class TaskHasKeywordsPredicate implements Predicate<ReadOnlyTask> {

    private final List<String> keywords;

    public TaskHasKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyTask task) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getHeader().header, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskHasKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TaskHasKeywordsPredicate) other).keywords)); // state check
    }

}

