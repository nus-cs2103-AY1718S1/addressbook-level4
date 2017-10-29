package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

//@@author Ernest
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Bloodtype} matches any of the keywords given.
 */
public class BloodtypeContainsKeywordPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keyword;

    public BloodtypeContainsKeywordPredicate(List<String> keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keyword.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getBloodType().type, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BloodtypeContainsKeywordPredicate // instanceof handles nulls
                && this.keyword.equals(((BloodtypeContainsKeywordPredicate) other).keyword)); // state check
    }

}
