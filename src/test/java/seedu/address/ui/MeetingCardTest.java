package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysMeeting;

import org.junit.Test;

import guitests.guihandles.MeetingCardHandle;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

//@@author LimYangSheng
public class MeetingCardTest extends GuiUnitTest {
    @Test
    public void display() {
        String meetingName = "dummy";
        String meetingTime = "dummy";
        Person person = new PersonBuilder().withMeeting("dinner", "2017-12-30 18:00").build();
        for (Meeting meeting : person.getMeetings()) {
            meetingName = meeting.meetingName;
            meetingTime = meeting.value;
        }
        try {
            Meeting expectedMeeting = new Meeting(person, meetingName, meetingTime);
            MeetingCard meetingCard = new MeetingCard(expectedMeeting, 1);
            uiPartRule.setUiPart(meetingCard);
            assertMeetingDisplay(meetingCard, expectedMeeting, 1);

            // changes made to Meeting reflects on card
            guiRobot.interact(() -> {
                person.setName(ALICE.getName());
                person.setAddress(ALICE.getAddress());
                person.setEmail(ALICE.getEmail());
                person.setPhone(ALICE.getPhone());
                person.setTags(ALICE.getTags());
                person.setNote(ALICE.getNote());
                person.setMeetings(ALICE.getMeetings());
                expectedMeeting.setPerson(person);
            });
            assertMeetingDisplay(meetingCard, expectedMeeting, 1);

        } catch (IllegalValueException e) {
            throw new AssertionError("Time format should be correct");
        }
    }

    @Test
    public void equals() {
        String meetingName = "dummy";
        String meetingTime = "dummy";
        Person person = new PersonBuilder().withMeeting("dinner", "2017-12-30 18:00").build();
        for (Meeting meeting : person.getMeetings()) {
            meetingName = meeting.meetingName;
            meetingTime = meeting.value;
        }
        try {
            Meeting expectedMeeting = new Meeting(person, meetingName, meetingTime);
            MeetingCard meetingCard = new MeetingCard(expectedMeeting, 0);

            // same meeting, same index -> returns true
            MeetingCard copy = new MeetingCard(expectedMeeting, 0);
            assertTrue(meetingCard.equals(copy));

            // same object -> returns true
            assertTrue(meetingCard.equals(meetingCard));

            // null -> returns false
            assertFalse(meetingCard.equals(null));

            // different types -> returns false
            assertFalse(meetingCard.equals(0));

            // different meeting, same index -> returns false
            Person differentPerson = new PersonBuilder().withMeeting("differentName", "2018-01-01 00:00").build();
            for (Meeting meeting : differentPerson.getMeetings()) {
                meetingName = meeting.meetingName;
                meetingTime = meeting.value;
            }
            Meeting differentMeeting = new Meeting(differentPerson, meetingName, meetingTime);
            assertFalse(meetingCard.equals(new MeetingCard(differentMeeting, 0)));

            // same meeting, different index -> returns false
            assertFalse(meetingCard.equals(new MeetingCard(expectedMeeting, 1)));
        } catch (IllegalValueException e) {
            throw new AssertionError("Time format should be correct");
        }
    }

    /**
     * Asserts that {@code meetingCard} displays the details of {@code expectedMeeting} correctly and matches
     * {@code expectedId}.
     */
    private void assertMeetingDisplay(MeetingCard meetingCard, Meeting expectedMeeting, int expectedId) {
        guiRobot.pauseForHuman();

        MeetingCardHandle meetingCardHandle = new MeetingCardHandle(meetingCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", meetingCardHandle.getId());

        // verify meeting details are displayed correctly
        assertCardDisplaysMeeting(expectedMeeting, meetingCardHandle);
    }
}
