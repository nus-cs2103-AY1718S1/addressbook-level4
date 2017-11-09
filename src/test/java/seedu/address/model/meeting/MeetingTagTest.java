package seedu.address.model.meeting;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MeetingTagTest {
    @Test
    public void isValidTagName() {
        // invalid MeetingTag
        assertFalse(MeetingTag.isValidTagName("")); // empty string
        assertFalse(MeetingTag.isValidTagName(" ")); // spaces only
        assertFalse(MeetingTag.isValidTagName("3")); //invalid number
        assertFalse(MeetingTag.isValidTagName("NAME")); // String

        // valid MeetingTag
        assertTrue(MeetingTag.isValidTagName("0"));
        assertTrue(MeetingTag.isValidTagName("1"));
        assertTrue(MeetingTag.isValidTagName("2"));
    }
}
