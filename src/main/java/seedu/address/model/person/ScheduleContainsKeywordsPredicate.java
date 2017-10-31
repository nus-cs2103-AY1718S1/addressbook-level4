package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.PredicateUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tags} matches any of the keywords given.
 */
public class ScheduleContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public ScheduleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> PredicateUtil.containsScheduleIgnoreCase(person.getSchedules(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((ScheduleContainsKeywordsPredicate) other).keywords)); // state check
    }

}
