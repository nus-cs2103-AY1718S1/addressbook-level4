//@@author erik0704
package seedu.address.model.event;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Event}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsEventPredicate implements Predicate<Event> {
    private final List<String> keywords;

    public NameContainsKeywordsEventPredicate(List<String> keywords) {
        this.keywords = keywords;
    }


    @Override
    public boolean test(Event event) {
        return keywords.stream().anyMatch(keyword
            -> StringUtil.containsWordIgnoreCase(event.getName().fullName, keyword)) || keywords.stream()
            .anyMatch(keyword -> event.getName().fullName.toLowerCase().contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsEventPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsKeywordsEventPredicate) other).keywords)); // state check
    }
}
