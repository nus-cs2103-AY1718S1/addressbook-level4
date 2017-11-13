package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author April0616
public class RemarkTest {

    @Test
    public void equals() {
        Remark likeCoffeeRemark = new Remark("Likes to drink coffee.");
        Remark capFiveRemark = new Remark("Got CAP 5.0.");

        // same object -> returns true
        assertTrue(likeCoffeeRemark.equals(likeCoffeeRemark));

        // same value -> returns true
        Remark copy = new Remark(capFiveRemark.value);
        assertTrue(capFiveRemark.equals(copy));

        // different types -> returns false
        assertFalse(capFiveRemark.equals(1));

        // null -> returns false
        assertFalse(capFiveRemark == null);

        // different objects -> returns false
        assertFalse(likeCoffeeRemark.equals(capFiveRemark));
    }
}
