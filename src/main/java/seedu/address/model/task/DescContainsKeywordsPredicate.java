package seedu.address.model.task;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

//@@author zhangshuoyang
/**
 * Tests that a {@code ReadOnlyTask}'s {@code Name} matches any of the keywords given.
 */
public class DescContainsKeywordsPredicate implements Predicate<ReadOnlyTask> {
    private final List<String> keywords;

    public DescContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyTask task) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getDescription().descriptionName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DescContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((DescContainsKeywordsPredicate) other).keywords)); // state check
    }
}
