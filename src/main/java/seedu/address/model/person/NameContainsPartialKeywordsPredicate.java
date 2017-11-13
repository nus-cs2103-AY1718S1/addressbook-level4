package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

//@@author RSJunior37
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given partially.
 */
public class NameContainsPartialKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public NameContainsPartialKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> person.getName().fullName.toLowerCase().contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsPartialKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsPartialKeywordsPredicate) other).keywords)); // state check
    }

}
