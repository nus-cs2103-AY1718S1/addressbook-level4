package seedu.address.model.person;

import static seedu.address.commons.util.StringUtil.containsWordIgnoreCase;

import java.util.function.Predicate;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code remark} contains the keyword given.
 */

//@@author nicholaschuayunzhi
public class RemarkContainsKeywordPredicate implements Predicate<ReadOnlyPerson> {

    private final String keyword;
    public RemarkContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return containsWordIgnoreCase(person.getRemark().value, keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemarkContainsKeywordPredicate // instanceof handles nulls
                && this.keyword.equals(((RemarkContainsKeywordPredicate) other).keyword)); // state check
    }

    @Override
    public int hashCode() {
        return keyword.hashCode();
    }
}
