package seedu.address.model.tag;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.tag.exceptions.TagNotFoundException;

public class TagNotFoundExceptionTest {
    private ExpectedException thrown = ExpectedException.none();

    @Test
    public void createException_toString_checkCorrectness() throws Exception {
        thrown.expect(TagNotFoundException.class);
        Exception exception = new TagNotFoundException("Some message here");
        assertEquals("Some message here", exception.toString());
    }
}
