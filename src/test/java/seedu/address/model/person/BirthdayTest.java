package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BirthdayTest {

    //@@author frozventus
    @Test
    public void isValidBirthday() throws Exception {
        // blank Birthday
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only

        // missing parts
        assertFalse(Birthday.isValidBirthday("02-1995")); // missing day
        assertFalse(Birthday.isValidBirthday("21-1992")); // missing month
        assertFalse(Birthday.isValidBirthday("01-01")); // missing year

        // valid Birthday
        assertTrue(Birthday.isValidBirthday("~")); // there is no input for birthday field
        assertTrue(Birthday.isValidBirthday("02-03-1995"));
        assertTrue(Birthday.isValidBirthday("12-12-1999"));
        assertTrue(Birthday.isValidBirthday("05-07-2005"));
    }
}
