package seedu.address.model.reminder;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
//@@author RonakLakhotia
/**
 * Tests that a {@code ReadOnlyReminder}'s {@code Name} matches any of the keywords given.
 */
public class DetailsContainsKeywordsPredicate implements Predicate<ReadOnlyReminder> {
    private final List<String> keywords;

    public DetailsContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyReminder reminder) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(reminder.getDetails().details, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DetailsContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((DetailsContainsKeywordsPredicate) other).keywords)); // state check
    }

}
