package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code email} matches the keyword given.
 */
public class EmailContainsKeywordPredicate implements Predicate<ReadOnlyPerson> {

    private final String keyword;
    public EmailContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return StringUtil.containsWordIgnoreCase(person.getEmail().toString(), keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailContainsKeywordPredicate // instanceof handles nulls
                && this.keyword.equals(((EmailContainsKeywordPredicate) other).keyword)); // state check
    }

}
