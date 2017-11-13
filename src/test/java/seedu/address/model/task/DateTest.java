package seedu.address.model.task;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DateTest {
    @Test
    public void isValid() throws Exception {
        // invalid date
        assertFalse(Date.isValidDate(""));  // empty string
        assertFalse(Date.isValidDate("01.01.2017"));    // invalid format
        assertFalse(Date.isValidDate("01-01-2017"));    // invalid format
        assertFalse(Date.isValidDate("01012017"));    // invalid format
        assertFalse(Date.isValidDate("1/01/2017"));    // invalid format
        assertFalse(Date.isValidDate("01/1/2017"));    // invalid format
        assertFalse(Date.isValidDate("01/01-2017"));    // invalid format
        assertFalse(Date.isValidDate("Jan 01 2017"));    // invalid format
        assertFalse(Date.isValidDate("2017/01/01"));    // invalid format
        assertFalse(Date.isValidDate("41/01/2017"));    // invalid value
        assertFalse(Date.isValidDate("21/21/2017"));    // invalid format

        // valid date
        assertTrue(Date.isValidDate("01/01/2017"));
        assertTrue(Date.isValidDate("16/09/2017"));
    }
}
