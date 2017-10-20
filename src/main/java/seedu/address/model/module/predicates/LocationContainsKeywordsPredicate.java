package seedu.address.model.module.predicates;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.module.ReadOnlyLesson;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Phone Number} matches any of the keywords given.
 */
public class LocationContainsKeywordsPredicate implements Predicate<ReadOnlyLesson> {
    private final List<String> keywords;

    public LocationContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyLesson lesson) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(lesson.getLocation().value, keyword));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LocationContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((LocationContainsKeywordsPredicate) other).keywords)); // state check
    }

}
