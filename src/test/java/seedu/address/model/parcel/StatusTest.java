package seedu.address.model.parcel;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;

public class StatusTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testToStringTest() {
        Status delivered = Status.COMPLETED;
        assertEquals("COMPLETED", delivered.toString());

        Status delivering = Status.DELIVERING;
        assertEquals("DELIVERING", delivering.toString());

        Status pending = Status.PENDING;
        assertEquals("PENDING", pending.toString());
    }

    @Test
    public void getStatusInstanceTest() throws IllegalValueException {
        // all uppercase
        Status pending = Status.getInstance("PENDING");
        assertEquals(Status.PENDING, pending);

        // all lowercase
        Status delivering = Status.getInstance("delivering");
        assertEquals(Status.DELIVERING, delivering);

        // mix of uppercase and lowercase characters
        Status delivered = Status.getInstance("dEliVERed");
        assertEquals(Status.COMPLETED, delivered);

        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(Status.MESSAGE_STATUS_CONSTRAINTS);
        Status.getInstance("asd1237fa&(&"); // weird characters
        Status.getInstance("JUMPING"); // not one of the possible values
    }

    @Test
    public void isValidStatusTest() {
        assertFalse(Status.isValidStatus("INVALID"));

        // uppercase letters
        assertTrue(Status.isValidStatus("PENDING"));
        assertTrue(Status.isValidStatus("DELIVERING"));

        // lower case letters
        assertFalse(Status.isValidStatus("pending"));
        assertFalse(Status.isValidStatus("delivered"));

        // mix of upper and lower case
        assertFalse(Status.isValidStatus("DelIVEred"));

        // random symbols
        assertFalse(Status.isValidStatus("$!@HBJ123"));
    }

}
