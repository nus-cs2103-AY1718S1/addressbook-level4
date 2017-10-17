package seedu.address.model.meeting;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.meeting.MeetingContainsKeywordsPredicate;
import seedu.address.model.meeting.ReadOnlyMeeting;

public class MeetingContainsKeywordsPredicate implements Predicate<ReadOnlyMeeting> {
    private final List<String> keywords;

    public MeetingContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyMeeting meeting) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(meeting.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MeetingContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((MeetingContainsKeywordsPredicate) other).keywords)); // state check
    }
}
