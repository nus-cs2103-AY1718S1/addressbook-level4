package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches the keyword given.
 */
public class NameContainsKeywordPredicate implements Predicate<ReadOnlyPerson> {
    private final String keyword;

    public NameContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordPredicate // instanceof handles nulls
                && this.keyword.equals(((NameContainsKeywordPredicate) other).keyword)); // state check
    }

}
