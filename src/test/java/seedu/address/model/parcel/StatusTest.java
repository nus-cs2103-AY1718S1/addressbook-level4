package seedu.address.model.parcel;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author kennard123661
public class StatusTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toStringTest() {
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
    public void getInstanceTest_success() throws IllegalValueException {
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
    }

    @Test
    public void getInstanceTest_failure() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(Status.MESSAGE_STATUS_CONSTRAINTS);

        Status.getInstance("asd1237fa&(&"); // weird characters
        Status.getInstance("JUMPING"); // not one of the possible values
    }
}
