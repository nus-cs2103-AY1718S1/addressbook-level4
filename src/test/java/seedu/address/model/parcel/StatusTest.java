package seedu.address.model.parcel;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author kennard123661
public class StatusTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testToStringTest() {
        Status completed = Status.COMPLETED;
        assertEquals("COMPLETED", completed.toString());

        Status delivering = Status.DELIVERING;
        assertEquals("DELIVERING", delivering.toString());

        Status pending = Status.PENDING;
        assertEquals("PENDING", pending.toString());

        Status overdue = Status.OVERDUE;
        assertEquals("OVERDUE", overdue.toString());
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
        Status completed = Status.getInstance("cOmPleTeD");
        assertEquals(Status.COMPLETED, completed);

        // mix of uppercase and lowercase characters
        Status overdue = Status.getInstance("overDUE");
        assertEquals(Status.OVERDUE, overdue);

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
        assertTrue(Status.isValidStatus("OVERDUE"));
        assertTrue(Status.isValidStatus("COMPLETED"));

        // lower case letters
        assertFalse(Status.isValidStatus("pending"));
        assertFalse(Status.isValidStatus("completed"));

        // mix of upper and lower case
        assertFalse(Status.isValidStatus("ComPleTed"));

        // random symbols
        assertFalse(Status.isValidStatus("$!@HBJ123"));
    }

}
