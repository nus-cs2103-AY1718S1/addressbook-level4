package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
//@@author RonakLakhotia
public class RemarkTest {
    @Test
    public void isValidRemark() {
        // invalid remark
        assertFalse(Remark.isValidRemark(" ")); // spaces only
        assertFalse(Remark.isValidRemark("/LEC/1")); // missing module title
        assertFalse(Remark.isValidRemark("CS2102//1")); // missing module type
        assertFalse(Remark.isValidRemark("CS2102/LEC/")); // missing module number

        // valid remark
        assertTrue(Remark.isValidRemark("")); // empty string
        assertTrue(Remark.isValidRemark("CS2102/LEC/1"));
    }
}
