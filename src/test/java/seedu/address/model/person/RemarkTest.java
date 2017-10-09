package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RemarkTest {

    @Test
    public void testRemark() {
        Remark emptyRemark = new Remark("");
        Remark emptyRemarkCopy = new Remark(emptyRemark.toString());
        Remark filledRemark = new Remark("Filled Remark");
        Remark filledRemarkCopy = new Remark(filledRemark.toString());

        assertTrue(emptyRemark.equals(emptyRemarkCopy));
        assertTrue(filledRemark.equals(filledRemarkCopy));
        assertFalse(emptyRemark.equals(filledRemark));
        assertFalse(emptyRemarkCopy.equals(filledRemarkCopy));

        assertTrue(emptyRemark.toString().equals(""));
        assertFalse(emptyRemark.toString().equals(0));
        assertFalse(emptyRemark.toString().equals(null));
        assertFalse(emptyRemark.equals(""));

        assertTrue(filledRemark.toString().equals("Filled Remark"));
        assertFalse(filledRemark.toString().equals(0));
        assertFalse(filledRemark.toString().equals(null));
        assertFalse(filledRemark.equals(""));

    }
}
