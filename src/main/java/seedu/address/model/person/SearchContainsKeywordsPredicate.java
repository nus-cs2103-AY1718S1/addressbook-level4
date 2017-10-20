package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */

public class SearchContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {

    private final List<String> keywords;

    public SearchContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test (ReadOnlyPerson person) {

        if (keywords.size() <= 1) {
            return false;
        }


        return StringUtil.containsWordIgnoreCase(person.getName().fullName, keywords.get(0))
               && StringUtil.containsWordIgnoreCase(person.getDateOfBirth().date, keywords.get(1));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((SearchContainsKeywordsPredicate) other).keywords)); // state check
    }
}
