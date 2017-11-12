package seedu.address.commons.exceptions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.Avatar;
import seedu.address.model.reminder.exceptions.ReminderNotFoundException;

//@@author yunpengn
public class InvalidFilePathExceptionTest {
    private ExpectedException thrown = ExpectedException.none();

    @Test
    public void createException_getMessage_checkCorrectness() throws Exception {
        thrown.expect(ReminderNotFoundException.class);
        Exception exception = new InvalidFilePathException(Avatar.INVALID_PATH_MESSAGE);
        assertEquals(Avatar.INVALID_PATH_MESSAGE, exception.getMessage());
    }

    @Test
    public void createException_emptyMessage_checkCorrectness() throws Exception {
        thrown.expect(ReminderNotFoundException.class);
        Exception exception = new InvalidFilePathException();
        assertEquals(null, exception.getMessage());
    }
}
