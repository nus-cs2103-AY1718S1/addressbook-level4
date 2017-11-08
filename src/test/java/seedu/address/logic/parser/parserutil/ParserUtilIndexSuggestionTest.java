package seedu.address.logic.parser.parserutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.ParserUtil.isParseableInt;
import static seedu.address.logic.parser.ParserUtil.parseFirstInt;
import static seedu.address.logic.parser.ParserUtil.parseRemoveFirstInt;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ParserUtilIndexSuggestionTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void tryParseFirstIntAssertFalse() {
        assertFalse(isParseableInt("one two three")); // characters
        assertFalse(isParseableInt("~!@# $%^&*()_+")); // symbols
        assertFalse(isParseableInt("0")); // zero
        assertFalse(isParseableInt("00")); // zero
        assertFalse(isParseableInt("2147483649")); // large numbers
    }

    @Test
    public void tryParseFirstIntAssertTrue() {
        assertTrue(isParseableInt("abc 1")); // word then number
        assertTrue(isParseableInt("del1")); // word then number, without spacing
        assertTrue(isParseableInt("add n/ 8 to Rolodex")); // characters then number, then characters
        assertTrue(isParseableInt("-1, acba")); // negative numbers
    }

    @Test
    public void parseFirstIntReturnsNonZeroPositiveInteger() throws Exception {
        assertEquals(parseFirstInt("abc 1"), 1); // word then number
        assertEquals(parseFirstInt("del1"), 1); // word then number, without spacing
        assertEquals(parseFirstInt("add n/ 8 to Rolodex"), 8); // characters then number, then characters
        assertEquals(parseFirstInt("-1, acba"), 1); // negative numbers
        assertEquals(parseFirstInt("-7"), 7); // negative numbers
    }

    @Test
    public void parseFirstIntCharactersOnlyThrowsNumberFormatException() {
        thrown.expect(NumberFormatException.class);
        parseFirstInt("one two three");
    }

    @Test
    public void parseFirstIntSymbolsOnlyThrowsNumberFormatException() {
        thrown.expect(NumberFormatException.class);
        parseFirstInt("~!@# $%^&*()_+");
    }

    @Test
    public void parseFirstIntZeroThrowsNumberFormatException() {
        thrown.expect(NumberFormatException.class);
        parseFirstInt("0");
    }

    @Test
    public void parseFirstIntLargeNumbersThrowsNumberFormatException() {
        thrown.expect(NumberFormatException.class);
        parseFirstInt("2147483649");
    }

    @Test
    public void parseRemoveFirstIntRemovesFirstInt() {
        assertEquals("delete second2", parseRemoveFirstInt("delete1second2"));
        assertEquals("de", parseRemoveFirstInt("de2"));
        assertEquals("2 3 4 5 6 7", parseRemoveFirstInt("1 2 3 4 5 6 7"));
        assertEquals("", parseRemoveFirstInt("1234567"));
        assertEquals("nothing here", parseRemoveFirstInt("nothing here"));
    }
}
