package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalMeetings.SHOPPING;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysMeeting;

import org.junit.Test;

import guitests.guihandles.MeetingCardHandle;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.ReadOnlyMeeting;
import seedu.address.testutil.MeethingBuilder;

public class MeetingCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Meeting meeting = new MeetingBuilder().build();
        MeetingCard meetingCard = new MeetingCard(meeting, 1);
        uiPartRule.setUiPart(meetingCard);
        assertCardDisplay(meetingCard, meeting, 1);

        // changes made to Meeting reflects on card
        guiRobot.interact(() -> {
            meeting.setName(SHOPPING.getName());
            meeting.setPlace(SHOPPING.getPlace());
            meeting.setDateTime(SHOPPING.getDate());
            meeting.setPhoneNum(SHOPPING.getPersonPhone());
            meeting.setPersonName(SHOPPING.getPersonName());
        });
        assertCardDisplay(meetingCard, meeting, 2);
    }

    @Test
    public void equals() {
        Meeting meeting = new MeetingBuilder().build();
        MeetingCard meetingCard = new MeetingCard(meeting, 0);

        // same meeting, same index -> returns true
        MeetingCard copy = new MeetingCard(meeting, 0);
        assertTrue(meetingCard.equals(copy));

        // same object -> returns true
        assertTrue(meetingCard.equals(meetingCard));

        // null -> returns false
        assertFalse(meetingCard.equals(null));

        // different types -> returns false
        assertFalse(meetingCard.equals(0));

        // different meeting, same index -> returns false
        Meeting differentMeeting = new MeetingBuilder().withName("differentName").build();
        assertFalse(meetingCard.equals(new MeetingCard(differentMeeting, 0)));

        // same Meeting, different index -> returns false
        assertFalse(meetingCard.equals(new MeetingCard(meeting, 1)));
    }

    /**
     * Asserts that {@code meetingCard} displays the details of {@code expectedMeeting} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(MeetingCard meetingCard, ReadOnlyMeeting expectedMeeting, int expectedId) {
        guiRobot.pauseForHuman();

        MeetingCardHandle meetingCardHandle = new MeetingCardHandle(meetingCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", meetingCardHandle.getId());

        // verify meeting details are displayed correctly
        assertCardDisplaysMeeting(expectedMeeting, meetingCardHandle);
    }
}
