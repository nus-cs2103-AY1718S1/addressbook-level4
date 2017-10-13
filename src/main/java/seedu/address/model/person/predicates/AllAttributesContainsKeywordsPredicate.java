package seedu.address.model.person.predicates;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.ReadOnlyPerson;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class AllAttributesContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public AllAttributesContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream().anyMatch(keyword -> person.getEmail().value.contains(keyword)) ||
                keywords.stream().anyMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword)) ||
                keywords.stream().anyMatch(keyword -> person.getPhone().value.contains(keyword)) ||
                keywords.stream().anyMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AllAttributesContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((AllAttributesContainsKeywordsPredicate) other).keywords)); // state check
    }

}
