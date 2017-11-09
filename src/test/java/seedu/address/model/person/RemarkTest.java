package seedu.address.model.person;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

//@@author seankwekjk
public class RemarkTest {

    @Test
    public void construct() {
        Remark test = new Remark("hello ");
        String empty = null;
        assertEquals("hello", test.getRemarkText());
        test = new Remark(empty);
        assertEquals("", test.getRemarkText());
    }

    @Test
    public void toStringTest() {
        Remark test = new Remark("hello");
        assertEquals("[hello]", test.toString());
    }
}
