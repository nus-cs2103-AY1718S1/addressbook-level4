/* @@author Syabil */
package seedu.address.model.meeting;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyMeeting}'s {@code Meeting} matches any of the keywords given.
 */
public class MeetingContainsKeywordsPredicate implements Predicate<ReadOnlyMeeting> {
    private final List<String> keywords;

    public MeetingContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyMeeting meeting) {
        return (keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(meeting.getName().fullName, keyword))
                || keywords.stream()
                        .anyMatch(keyword ->
                                StringUtil.containsWordIgnoreCase(meeting.getPersonsMeet()
                                        .get(0).getName().fullName, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MeetingContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((MeetingContainsKeywordsPredicate) other).keywords)); // state check
    }
}
