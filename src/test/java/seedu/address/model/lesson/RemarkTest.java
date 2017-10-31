package seedu.address.model.lesson;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.module.Remark;

//@@author junming403
public class RemarkTest {

    @Test
    public void isValidRemark() {
        // invalid remark
        assertFalse(Remark.isValidRemark("")); // empty string
        assertFalse(Remark.isValidRemark(getLongString()));

        // valid phone numbers
        assertTrue(Remark.isValidRemark("it is a valid remark")); // Must follow this format
    }

    /**
     * Get a String that is longger than 150 characters.
     */
    private String getLongString() {
        String ouput = "";
        for (int i = 0; i < 1000; i++) {
            ouput += "abc";
        }
        return ouput;
    }

}
