package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.PredicateUtil;


/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class CountryContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {

    private final List<String> keywords;

    public CountryContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }


    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> PredicateUtil.containsAttributeIgnoreCase(person.getCountry().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CountryContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((CountryContainsKeywordsPredicate) other).keywords)); // state check
    }

}
