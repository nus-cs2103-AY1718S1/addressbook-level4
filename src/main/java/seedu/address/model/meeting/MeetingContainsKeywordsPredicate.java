/* @@author Syabil */
package seedu.address.model.meeting;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Tests that a {@code ReadOnlyMeeting}'s {@code Meeting} matches any of the keywords given.
 */
public class MeetingContainsKeywordsPredicate implements Predicate<ReadOnlyMeeting> {
    private final List<String> keywords;

    public MeetingContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }


    //@@author kyngyi
    /**
     * Tests if a {@code ReadOnlyMeeting}'s {@code List<ReadOnlyPerson>} contains any persons with name
     * matching any of the keywords given.
     */
    private boolean personListContainsKeyword(List<String> keywords, List<ReadOnlyPerson> target) {
        for (int indexKeyword = 0; indexKeyword < keywords.size(); indexKeyword++) {
            for (int indexTarget = 0; indexTarget < target.size(); indexTarget++) {
                if (StringUtil.containsWordIgnoreCase(target.get(indexTarget).getName().fullName, (
                        keywords.get(indexKeyword)))) {
                    return true;
                }
            }
        }
        return false;
    }
    //@@author

    //@@author Syabil
    @Override
    public boolean test(ReadOnlyMeeting meeting) {
        for (int index = 0; index < meeting.getPersonsMeet().size(); index++) {
            if (keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(meeting.getName().fullName, keyword))
                    || personListContainsKeyword(keywords, meeting.getPersonsMeet())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MeetingContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((MeetingContainsKeywordsPredicate) other).keywords)); // state check
    }
}
