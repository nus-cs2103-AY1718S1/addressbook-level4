package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author Jeremy
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

        assertTrue("".equals(emptyRemark.toString()));
        assertFalse(emptyRemark.toString().equals(0));
        assertFalse(emptyRemark.toString() == null);
        assertFalse("".equals(emptyRemark));

        assertTrue(filledRemark.toString().equals("Filled Remark"));
        assertFalse(filledRemark.toString().equals(0));
        assertFalse(filledRemark.toString() == null);
        assertFalse("".equals(filledRemark));

        int filledRemarkHash = filledRemarkCopy.hashCode();
        int filledRemarkCopyHash = filledRemark.hashCode();
        assertEquals(filledRemarkCopyHash, filledRemarkHash);
    }
}
