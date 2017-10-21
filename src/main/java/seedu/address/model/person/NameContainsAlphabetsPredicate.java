package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsAlphabetsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public NameContainsAlphabetsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsAlphabetIgnoreCase(person.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsAlphabetsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsAlphabetsPredicate) other).keywords)); // state check
    }

}
