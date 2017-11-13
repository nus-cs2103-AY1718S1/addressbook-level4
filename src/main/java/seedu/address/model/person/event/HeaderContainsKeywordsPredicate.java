package seedu.address.model.person.event;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

//@@author royceljh
/**
 * Tests that a {@code ReadOnlyEvent}'s {@code Header} matches any of the keywords given.
 */
public class HeaderContainsKeywordsPredicate implements Predicate<ReadOnlyEvent> {
    private final List<String> keywords;

    public HeaderContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyEvent event) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(event.getHeader().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HeaderContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((HeaderContainsKeywordsPredicate) other).keywords)); // state check
    }

}
