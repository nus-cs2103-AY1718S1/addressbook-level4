package seedu.address.model.meeting;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NameMeetingTest {
    @Test
    public void isValidName() {
        // invalid NameMeeting
        assertFalse(NameMeeting.isValidName("")); // empty string
        assertFalse(NameMeeting.isValidName(" ")); // spaces only

        // valid NameMeeting
        assertTrue(NameMeeting.isValidName("project meeting")); // alphabets only
        assertTrue(NameMeeting.isValidName("12345")); // numbers only
        assertTrue(NameMeeting.isValidName("peter's 23rd birthday party")); // with punctuation
        assertTrue(NameMeeting.isValidName("Clementi Mall")); // with capital letters
        assertTrue(NameMeeting.isValidName("Raffles Town Club Room 13 ")); // long names
    }
}
