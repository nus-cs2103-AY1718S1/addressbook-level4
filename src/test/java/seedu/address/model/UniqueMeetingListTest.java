package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalMeetings.M1;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//@@author liuhang0213
public class UniqueMeetingListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueMeetingList meetingList = new UniqueMeetingList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), meetingList.getMeetingList());
    }

    @Test
    public void setMeetings_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        meetingList.setMeetings(null);
    }

    @Test
    public void setMeetings_withDuplicateMeetings_throwsAssertionError() {
        List<ReadOnlyMeeting> newMeetings = Arrays.asList(new Meeting(M1), new Meeting(M1));
        MeetingListStub newData = new MeetingListStub(newMeetings);

        thrown.expect(AssertionError.class);
        meetingList.setMeetings(newData.getMeetingList());
    }

    /**
     * A stub UniqueMeetingList which meetings can violate interface constraints.
     */
    private static class MeetingListStub implements ReadOnlyMeetingList {
        private final ObservableList<ReadOnlyMeeting> meetings = FXCollections.observableArrayList();

        MeetingListStub (Collection<ReadOnlyMeeting> meetings) {
            this.meetings.setAll(meetings);
        }

        @Override
        public ObservableList<ReadOnlyMeeting> getMeetingList() {
            return meetings;
        }

        @Override
        public ReadOnlyMeeting getUpcomingMeeting() {
            return meetings.sorted().get(0);
        }
    }
}
