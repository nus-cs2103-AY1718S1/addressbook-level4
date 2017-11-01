package seedu.address.model.property;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
//@@author junyango
public class DateTimeTest {
    @BeforeClass
    public static void setUp() {
        PropertyManager.initializePropertyManager();
    }

    @Test
    public void isValidTime() {
        // invalid time
        assertFalse(DateTime.isValidTime("")); // empty string
        assertFalse(DateTime.isValidTime("251215 08:30")); // wrong year format
        assertFalse(DateTime.isValidTime("25122015 08:30am")); // included am
        assertFalse(DateTime.isValidTime("25122015 08:30pm")); // included pm

        // valid time
        assertTrue(DateTime.isValidTime("25122015 08:30"));
        assertTrue(DateTime.isValidTime("14122016 13:30"));
        assertTrue(DateTime.isValidTime("09121924 23:30"));

    }
}
