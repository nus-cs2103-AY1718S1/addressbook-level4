package seedu.address.model.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;



public class EventNameTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void testInvalidInput() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        EventName e1 = new EventName("");
    }

    @Test
    public void testEventNameCreationSuccess() throws IllegalValueException {
        EventName e = new EventName("Event 1");
        EventName e1 = new EventName("Event 1");
        EventName e2 = new EventName("Event 3");

        assertEquals("Event 1", e.toString());
        assertEquals("Event 1".hashCode(), e.hashCode());
        assertTrue(e.equals(e1));
        assertTrue(EventName.isValidName(e1.toString()));
        assertFalse(e.equals(e2));

    }

}
