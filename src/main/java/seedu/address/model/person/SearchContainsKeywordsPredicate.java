package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.StringUtil;

//@@author RonakLakhotia
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} and {@code DateOFbirth} matches the keywords given.
 */

public class SearchContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {

    private static final Logger logger = LogsCenter.getLogger(SearchContainsKeywordsPredicate.class);
    private final List<String> keywords;

    public SearchContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }


    @Override
    public boolean test (ReadOnlyPerson person) {

        if (keywords.size() <= 1) {
            logger.warning("The number of arguements entered do not match the command format");
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
