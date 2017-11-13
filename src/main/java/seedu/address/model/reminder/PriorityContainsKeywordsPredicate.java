//@@author inGall
package seedu.address.model.reminder;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class PriorityContainsKeywordsPredicate implements Predicate<ReadOnlyReminder> {
    private final List<String> keywords;

    public PriorityContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyReminder reminder) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(reminder.getPriority().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PriorityContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PriorityContainsKeywordsPredicate) other).keywords)); // state check
    }

}
