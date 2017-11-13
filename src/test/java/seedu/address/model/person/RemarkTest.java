package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author hanselblack
public class RemarkTest {

    @Test
    public void equals() {
        Remark remark = new Remark("Hello");

        //same object -> returns true
        assertTrue(remark.equals(remark));

        //same values -> returns true
        Remark remarkCopy = new Remark(remark.value);
        assertTrue(remark.equals(remarkCopy));

        //different types -> return false
        assertFalse(remark.equals(1));

        //null -> returns false
        assertFalse(remark.equals(null));

        //different person -> returns false
        Remark differentRemark = new Remark("Bye");
        assertFalse(remark.equals(differentRemark));
    }

    @Test
    public void hashCodeTest() throws IllegalValueException {
        Remark remarkStub = new Remark("remark stub");
        assertEquals("remark stub".hashCode(), remarkStub.hashCode());

        Remark remarkStub2 = new Remark("remark stub 2");
        assertEquals("remark stub 2".hashCode(), remarkStub2.hashCode());
    }
}
