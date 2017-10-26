package seedu.address.model.event;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code ReadOnlyEvent}'s {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class EventContainsKeywordPredicate implements Predicate<ReadOnlyEvent> {
    private final List<String> keywords;

    public EventContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyEvent event) {
        Boolean isSelected = false;
        if (!event.getParticipants().isEmpty()) {
            isSelected = keywords.stream().anyMatch(keyword ->
                    event.getEventName().fullEventName.equalsIgnoreCase(keyword));
        }
        return isSelected;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventContainsKeywordPredicate // instanceof handles nulls
                && this.keywords.equals(((EventContainsKeywordPredicate) other).keywords)); // state check
    }
}
