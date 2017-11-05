package seedu.address.model.reminder;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.reminder.exceptions.ReminderNotFoundException;

public class ReminderNotFoundExceptionTest {
    private ExpectedException thrown = ExpectedException.none();

    @Test
    public void createException_toString_checkCorrectness() throws Exception {
        thrown.expect(ReminderNotFoundException.class);
        Exception exception = new ReminderNotFoundException("Some message here");
        assertEquals("Some message here", exception.toString());
    }
}
