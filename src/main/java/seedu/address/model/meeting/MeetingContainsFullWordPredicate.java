package seedu.address.model.meeting;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.ReadOnlyPerson;

//@@author kyngyi
/**
 * Tests that a {@code ReadOnlyMeeting}'s {@code Meeting} matches the whole word given.
 */
public class MeetingContainsFullWordPredicate implements Predicate<ReadOnlyMeeting> {
    private final List<String> keywords;

    public MeetingContainsFullWordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Tests if a {@code ReadOnlyMeeting}'s {@code List<ReadOnlyPerson>} contains any persons with name
     * matching any of the keywords given.
     */
    private boolean personListContainsFullWord(String phrase, List<ReadOnlyPerson> target) {
        for (int indexTarget = 0; indexTarget < target.size(); indexTarget++) {
            if (StringUtil.containsFullWordIgnoreCase(target.get(indexTarget).getName().fullName, phrase)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean test(ReadOnlyMeeting meeting) {
        return (personListContainsFullWord(keywords.get(0), meeting.getPersonsMeet()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MeetingContainsFullWordPredicate // instanceof handles nulls
                && this.keywords.equals(((MeetingContainsFullWordPredicate) other).keywords)); // state check
    }
}
