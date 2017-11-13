package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

//@@author bokwoon95
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsSubstringsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public NameContainsSubstringsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsSubstringIgnoreCase(person.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsSubstringsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsSubstringsPredicate) other).keywords)); // state check
    }

}
