package seedu.address.model.meeting;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyMeeting}'s {@code Meeting} matches the whole word given.
 */
public class MeetingContainsFullWordPredicate implements Predicate<ReadOnlyMeeting> {
    private final List<String> keywords;

    public MeetingContainsFullWordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyMeeting meeting) {
        return (keywords.stream()
                .anyMatch(keyword ->
                        StringUtil.containsFullWordIgnoreCase(meeting.getPersonName().fullName, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MeetingContainsFullWordPredicate // instanceof handles nulls
                && this.keywords.equals(((MeetingContainsFullWordPredicate) other).keywords)); // state check
    }
}
