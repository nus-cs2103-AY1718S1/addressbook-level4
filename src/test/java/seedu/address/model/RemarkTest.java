package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.person.Remark;

public class RemarkTest {

    @Test
    public void equals() {
        Remark remark = new Remark("Hello");

        // Return true if same object
        assertTrue(remark.equals(remark));

        // Returns true if remarks have the same value
        Remark remarkSameValue = new Remark(remark.value);
        assertTrue(remark.equals(remarkSameValue));

        // Returns false if different type
        assertFalse(remark.equals(1));

        // Returns false if null
        assertFalse(remark.equals(null));

        // Returns false if different person
        Remark differentRemark = new Remark("Hey");
        assertFalse(remark.equals(differentRemark));
    }
}
