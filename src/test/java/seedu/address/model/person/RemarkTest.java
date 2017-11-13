package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RemarkTest {

    @Test
    public void equals() {
        Remark remark = new Remark("Hello");

        // same object -> true
        assertTrue(remark.equals(remark));

        // same values -> true
        Remark remarkCopy = new Remark(remark.value);
        assertTrue(remark.equals(remarkCopy));

        // different types -> false
        assertFalse(remark.equals(1));

        // null -> false
        assertFalse(remark == null);

        // different remark -> false
        Remark differentRemark = new Remark("Bye");
        assertFalse(remark.equals(differentRemark));
    }

    @Test
    public void compareTo() {
        Remark remark = new Remark("Hey");

        // same object -> true
        assertTrue(remark.compareTo(remark) == 0);

        // same values -> true
        Remark remarkCopy = new Remark(remark.value);
        assertTrue(remark.compareTo(remarkCopy) == 0);

        // different remark -> false
        Remark differentRemark = new Remark("Bye");
        assertFalse(remark.compareTo(differentRemark) == 0);
    }
}
