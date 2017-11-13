package seedu.address.model.reminder.exceptions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.rules.ExpectedException;

//@@author junyango

public class DuplicateReminderExceptionTest {
    private ExpectedException thrown = ExpectedException.none();

    @Test
    public void createException_getMessage_checkCorrectness() throws Exception {
        thrown.expect(ReminderNotFoundException.class);
        Exception exception = new DuplicateReminderException();
        assertEquals(DuplicateReminderException.MESSAGE, exception.getMessage());
    }
}
