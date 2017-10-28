package seedu.address.model.module.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.module.ReadOnlyLesson;

/**
 * Tests that a {@code ReadOnlyLesson}'s {@code Code} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<ReadOnlyLesson> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyLesson lesson) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(lesson.getCode().fullCodeName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
