package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.MeetingCardHandle;
import guitests.guihandles.MeetingListPanelHandle;
import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.meeting.ReadOnlyMeeting;
import seedu.address.model.person.ReadOnlyPerson;


/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(PersonCardHandle expectedCard, PersonCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }
    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPerson(ReadOnlyPerson expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPerson.getEmail().value, actualCard.getEmail());
        assertEquals(expectedPerson.getAddress().value, actualCard.getAddress());
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }



    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(MeetingCardHandle expectedCard, MeetingCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getNameMeeting(), actualCard.getNameMeeting());
        assertEquals(expectedCard.getPlace(), actualCard.getPlace());
        assertEquals(expectedCard.getPersonToMeet(), actualCard.getPersonToMeet());
        assertEquals(expectedCard.getPhoneNum(), actualCard.getPhoneNum());
        assertEquals(expectedCard.getDateTime(), actualCard.getDateTime());
    }
    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedMeeting}.
     */
    public static void assertCardDisplaysMeeting(ReadOnlyMeeting expectedMeeting, MeetingCardHandle actualCard) {
        assertEquals(expectedMeeting.getName().fullName, actualCard.getNameMeeting());
        assertEquals(expectedMeeting.getPersonPhone().phone, actualCard.getPhoneNum());
        assertEquals(expectedMeeting.getPlace().value, actualCard.getPlace());
        assertEquals(expectedMeeting.getDate().value, actualCard.getDateTime());
        assertEquals(expectedMeeting.getPersonName().fullName, actualCard.getPersonToMeet());
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, ReadOnlyPerson... persons) {
        for (int i = 0; i < persons.length; i++) {
            assertCardDisplaysPerson(persons[i], personListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, List<ReadOnlyPerson> persons) {
        assertListMatching(personListPanelHandle, persons.toArray(new ReadOnlyPerson[0]));
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(MeetingListPanelHandle meetingListPanelHandle, ReadOnlyMeeting... meetings) {
        for (int i = 0; i < meetings.length; i++) {
            assertCardDisplaysMeeting(meetings[i], meetingListPanelHandle.getMeetingCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(MeetingListPanelHandle meetingListPanelHandle,
                                          List<ReadOnlyMeeting> meetings) {
        assertListMatching(meetingListPanelHandle, meetings.toArray(new ReadOnlyMeeting[0]));
    }

    /**
     * Asserts the size of the list in {@code personListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PersonListPanelHandle personListPanelHandle, int size) {
        int numberOfPeople = personListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
