package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Parameters} matches any of the keywords given.
 */
public class PersonContainsAllKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public PersonContainsAllKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .allMatch(keyword -> StringUtil.containsStringIgnoreCase(person.getAsText(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsAllKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonContainsAllKeywordsPredicate) other).keywords)); // state check
    }

}
