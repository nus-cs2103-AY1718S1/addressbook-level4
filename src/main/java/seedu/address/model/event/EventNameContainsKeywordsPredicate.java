package seedu.address.model.event;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

// @@author HouDenghao
/**
 * Tests that a {@code ReadOnlyEvent}'s {@code Name} matches any of the keywords given.
 */
public class EventNameContainsKeywordsPredicate implements Predicate<ReadOnlyEvent> {
    private final List<String> keywords;

    public EventNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyEvent event) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(event.getEventName().fullEventName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventNameContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((EventNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
