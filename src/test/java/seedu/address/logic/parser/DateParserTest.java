package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalDate;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

//@author Juxarius
public class DateParserTest {
    private final DateParser parser = new DateParser();

    @Test
    public void dateParserTest() {
        // Testing individual validity tests
        try {
            assertEquals(11, parser.getMonth("nOvemBer"));
            assertEquals(12, parser.getMonth("Dec"));
            assertEquals("Mar", parser.getValidMonth("3"));
            assertEquals("Dec", parser.getValidMonth("12"));
            assertEquals("03", parser.getValidDay("3"));
            assertEquals("1994", parser.getValidYear("94"));
            assertEquals("1000", parser.getValidYear("1000"));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Validity tests fail.", ive);
        }

        LocalDate expectedDate = LocalDate.now().withDayOfMonth(11).withMonth(10).withYear(1994);
        try {
            // Accepted dates strings
            assertEquals(expectedDate, parser.parse("11.10/1994"));
            assertEquals(expectedDate, parser.parse("11-10 94"));
            assertEquals(expectedDate, parser.parse("11 oct 1994"));
            assertEquals(expectedDate, parser.parse("11 october 94"));
            assertEquals(expectedDate, parser.parse("11 october 1994dksajasd"));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Correctly formatted argument was unable to be parsed.", ive);
        }
        assertParseFailure(parser, "1994 11 10");
        assertParseFailure(parser, "11101994");
        assertParseFailure(parser, "11 10 94kjds");
    }

    public static void assertParseFailure(DateParser parser, String input) {
        try {
            parser.parse(input);
            fail("The expected parse failure was not thrown.");
        } catch (IllegalValueException ive) {
            assert(true);
        }
    }



}
