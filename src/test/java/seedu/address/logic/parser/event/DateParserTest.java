//@@author A0162268B
package seedu.address.logic.parser.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class DateParserTest {

    private static final String invalidDay = "9/05/1999";
    private static final String invalidMonth = "09/5/1999";
    private static final String invalidYear = "09/05/199";
    private static final String validDate = "09/05/1999";
    private DateParser parser = new DateParser();

    @Test
    public void parseInvalidDay() throws ParseException {
        boolean thrown = false;

        try {
            parser.parse(invalidDay);
        } catch (ParseException e) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    @Test
    public void parseInvalidMonth() throws ParseException {
        boolean thrown = false;

        try {
            parser.parse(invalidMonth);
        } catch (ParseException e) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    @Test
    public void parseInvalidYear() throws ParseException {
        boolean thrown = false;

        try {
            parser.parse(invalidYear);
        } catch (ParseException e) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    @Test
    public void parseValidDate() throws ParseException {
        boolean thrown = false;

        try {
            parser.parse(validDate);
        } catch (ParseException e) {
            thrown = true;
        }

        assertFalse(thrown);
    }

}
