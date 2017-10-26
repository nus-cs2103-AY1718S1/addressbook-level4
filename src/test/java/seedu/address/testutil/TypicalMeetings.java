package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Meeting;
import seedu.address.model.UniqueMeetingList;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalMeetings {

    public static final Meeting M1= new MeetingBuilder().build();

    private TypicalMeetings() {} // prevents instantiation


    /**
     * Returns a {@code UniqueMeetingList} with all the typical meetings
     */
    public static UniqueMeetingList getTypicalMeetingList() {
        try {
            UniqueMeetingList meetings = new UniqueMeetingList();
            for (Meeting m : getTypicalMeetings()) {
                meetings.add(m);
            }
            return meetings;
        } catch (UniqueMeetingList.DuplicateMeetingException e) {
            throw new AssertionError("sample data cannot contain duplicate meetings", e);
        }
    }

    public static List<Meeting> getTypicalMeetings() {
        return new ArrayList<>(Arrays.asList(M1));
    }


}
