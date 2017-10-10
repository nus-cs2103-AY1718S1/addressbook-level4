//@@author Giang
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RemarkTest {
    @Test
    public void isEqualRemarks() {

        // equal Remark objects
        assertTrue(new Remark("haha").equals(new Remark("haha")));
        assertTrue(new Remark("hmpq").equals(new Remark("hmpq")));

        //unequal Remark objects
        assertFalse(new Remark("haha").equals(new Remark("bobo")));
        assertFalse(new Remark("owing money").equals(new Remark("new friend")));
    }
}
