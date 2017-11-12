package seedu.address.commons.exceptions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.model.person.Avatar;

//@@author yunpengn
public class InvalidFilePathExceptionTest {
    @Test
    public void createException_getMessage_checkCorrectness() throws Exception {
        Exception exception = new InvalidFilePathException(Avatar.INVALID_PATH_MESSAGE);
        assertEquals(Avatar.INVALID_PATH_MESSAGE, exception.getMessage());
    }

    @Test
    public void createException_emptyMessage_checkCorrectness() throws Exception {
        Exception exception = new InvalidFilePathException();
        assertEquals(null, exception.getMessage());
    }
}
