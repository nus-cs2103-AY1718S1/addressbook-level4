package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code phone} matches the keyword given.
 */
public class PhoneContainsKeywordPredicate implements Predicate<ReadOnlyPerson> {

    private final String keyword;
    public PhoneContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneContainsKeywordPredicate // instanceof handles nulls
                && this.keyword.equals(((PhoneContainsKeywordPredicate) other).keyword)); // state check
    }

}
