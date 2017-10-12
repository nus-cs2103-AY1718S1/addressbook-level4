package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class PersonContainsSpecifiedKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public PersonContainsSpecifiedKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsSpecifiedKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonContainsSpecifiedKeywordsPredicate) other).keywords)); // state check
    }

}
