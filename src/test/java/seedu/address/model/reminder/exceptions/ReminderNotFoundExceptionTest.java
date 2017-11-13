package seedu.address.model.reminder.exceptions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

//@@author yunpengn
public class ReminderNotFoundExceptionTest {
    @Test
    public void createException_toString_checkCorrectness() throws Exception {
        Exception exception = new ReminderNotFoundException("Some message here");
        assertEquals("Some message here", exception.toString());
    }
}
