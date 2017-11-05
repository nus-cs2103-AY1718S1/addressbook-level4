package seedu.address.model.person.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.parser.ArgumentWildcardMatcher;
import seedu.address.model.person.ReadOnlyPerson;

//@@author newalter
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Address} matches any of the keywords given.
 */
public class AddressContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;


    public AddressContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = ArgumentWildcardMatcher.processKeywords(keywords);
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        for (String address : person.getAddress().value.split("\\s+")) {
            for (String keyword : keywords) {
                if (address.toLowerCase().matches(keyword)) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((AddressContainsKeywordsPredicate) other).keywords)); // state check
    }

}
