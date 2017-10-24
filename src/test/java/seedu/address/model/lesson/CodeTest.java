package seedu.address.model.lesson;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.module.Code;

public class CodeTest {
    @Test
    public void isValidCode() {
        // invalid name
        assertFalse(Code.isValidCode("")); // empty string
        assertFalse(Code.isValidCode(" ")); // spaces only
        assertFalse(Code.isValidCode("C2101")); // contains only 1 letter
        assertFalse(Code.isValidCode("CS")); // no digit
        assertFalse(Code.isValidCode("CS*")); // contains non-alphanumeric characters
        assertFalse(Code.isValidCode("CS221")); // contains only 3 digits
        assertFalse(Code.isValidCode("2101")); // contains only digit
        assertFalse(Code.isValidCode("2101")); // contains only digit
        assertFalse(Code.isValidCode("GEQR221")); // contains 4 letters

        // valid name
        assertTrue(Code.isValidCode("CS2101")); // 2 letters and 4 digits
        assertTrue(Code.isValidCode("cs2101")); // all small letter for

        assertTrue(Code.isValidCode("MA1101R")); // 2 letters, 4 digits and 1 letter
        assertTrue(Code.isValidCode("ma1101R")); // last letter is capital
        assertTrue(Code.isValidCode("ma1101r")); // all small letter
        assertTrue(Code.isValidCode("Ma1101r")); // first letter is capital

        assertTrue(Code.isValidCode("GEQ1000")); // 3 letters and 4 digits
        assertTrue(Code.isValidCode("geq1000")); // all small letter for
    }
}
