package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class RemarkTest {

    @Test
    public void equals() throws IllegalValueException {
        Remark someRemark = new Remark("This is a remark.");

        //same object, return true
        assertTrue(someRemark.equals(someRemark));

        //same value, returns true
        Remark copiedSomeRemark = new Remark(someRemark.value);
        assertTrue(someRemark.equals(copiedSomeRemark));

        //different types, return false
        assertFalse(someRemark.equals(678));

        //null, return false
        assertFalse(someRemark.equals(null));

        //different person, return false
        Remark differentRemark = new Remark("This is not a remark");
        assertFalse(someRemark.equals(differentRemark));
    }
}
