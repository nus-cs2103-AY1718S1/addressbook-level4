//@@author heiseish
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MajorTest {
    @Test
    public void isEqualMajor() {

        // equal Major objects
        assertTrue(new Major("Chemical Engineering").equals(new Major("Chemical Engineering")));
        assertTrue(new Major("Computer Science").equals(new Major("Computer Science")));

        //unequal Major objects
        assertFalse(new Major("Chemical Engineering").equals(new Major("Computer Science")));
        assertFalse(new Major("Computer Science").equals(new Major("")));
    }
}
