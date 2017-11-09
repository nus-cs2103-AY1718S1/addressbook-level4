package seedu.address.model.parcel;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.DATE_TODAY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_TOMORROW;
import static seedu.address.logic.commands.CommandTestUtil.DATE_YESTERDAY;

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

    @Test
    public void getUpdatedInstanceTest() throws IllegalValueException {
        DeliveryDate tomorrow = new DeliveryDate(DATE_TOMORROW);
        DeliveryDate today = new DeliveryDate(DATE_TODAY);
        DeliveryDate yesterday = new DeliveryDate(DATE_YESTERDAY);

        // Ensure that Status is not updated for COMPLETED and DELIVERING even if date changes.
        assertEquals(Status.COMPLETED, Status.getUpdatedInstance(Status.COMPLETED, yesterday));
        assertEquals(Status.COMPLETED, Status.getUpdatedInstance(Status.COMPLETED, today));
        assertEquals(Status.COMPLETED, Status.getUpdatedInstance(Status.COMPLETED, tomorrow));
        assertEquals(Status.DELIVERING, Status.getUpdatedInstance(Status.DELIVERING, yesterday));
        assertEquals(Status.DELIVERING, Status.getUpdatedInstance(Status.DELIVERING, today));
        assertEquals(Status.DELIVERING, Status.getUpdatedInstance(Status.DELIVERING, tomorrow));

        // PENDING/OVERDUE status becomes OVERDUE status if delivery date was yesterday
        assertEquals(Status.OVERDUE, Status.getUpdatedInstance(Status.PENDING, yesterday));
        assertEquals(Status.OVERDUE, Status.getUpdatedInstance(Status.OVERDUE, yesterday));

        // PENDING/OVERDUE status becomes PENDING status if delivery date is today or tomorrow
        assertEquals(Status.PENDING, Status.getUpdatedInstance(Status.PENDING, today));
        assertEquals(Status.PENDING, Status.getUpdatedInstance(Status.PENDING, tomorrow));
        assertEquals(Status.PENDING, Status.getUpdatedInstance(Status.OVERDUE, today));
        assertEquals(Status.PENDING, Status.getUpdatedInstance(Status.OVERDUE, tomorrow));
    }
}
