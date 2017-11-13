package seedu.address.model.event;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.event.exceptions.EventNotFoundException;

//@@author junyango

public class EventNotFoundExceptionTest {
    private ExpectedException thrown = ExpectedException.none();

    @Test
    public void createException_toString_checkCorrectness() throws Exception {
        thrown.expect(EventNotFoundException.class);
        Exception exception = new EventNotFoundException("Some message here");
        assertEquals("Some message here", exception.toString());
    }
}
