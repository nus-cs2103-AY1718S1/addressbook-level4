package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.parser.ArgumentWildcardMatcher;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    //@@author newalter
    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = ArgumentWildcardMatcher.processKeywords(keywords);
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        for (String name : person.getName().fullName.split("\\s+")) {
            for (String keyword : keywords) {
                if (name.toLowerCase().matches(keyword)) {
                    return true;
                }
            }
        }
        return false;
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
