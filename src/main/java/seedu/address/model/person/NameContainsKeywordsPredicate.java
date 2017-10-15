package seedu.address.model.person;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.HashMap;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} or {@code Tag} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final HashMap<String, List<String>> keywords;

    public NameContainsKeywordsPredicate(HashMap<String, List<String>> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        boolean result = false;
        if (keywords.containsKey(PREFIX_NAME.toString()) && !keywords.get(PREFIX_NAME.toString()).isEmpty()) {
            result = keywords.get(PREFIX_NAME.toString()).stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
            
        }

        if (keywords.containsKey(PREFIX_TAG.toString()) && !keywords.get(PREFIX_TAG.toString()).isEmpty()) {
            result = result || person.containTags(keywords.get(PREFIX_TAG.toString()));
        }
        
        return result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
