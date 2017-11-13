package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.DateParseException;


public class DateAddedTest {

    @Test(expected = DateParseException.class)
    public void testInvalidDate() throws IllegalValueException {
        new DateAdded("Gibberish");
    }

    @Test
    public void testEqual() throws IllegalValueException {
        DateAdded d1 = new DateAdded("01/01/2000 11:11:11");
        DateAdded d2 = new DateAdded("01/01/2000 11:11:11");
        DateAdded d3 = new DateAdded("01/01/2000 22:22:22");

        assertTrue(d1.equals(d1));
        assertTrue(d1.equals(d2));
        assertFalse(d1.equals(d3));

    }
}
