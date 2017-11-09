package seedu.address.logic.parser.parserutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.ParserUtil.isParsableIndex;
import static seedu.address.logic.parser.ParserUtil.parseFirstIndex;
import static seedu.address.logic.parser.ParserUtil.parseRemoveFirstIndex;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ParserUtilIndexSuggestionTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void isParsableFirstIntAssertFalse() {
        assertFalse(isParsableIndex("one two three")); // characters
        assertFalse(isParsableIndex("~!@# $%^&*()_+")); // symbols
        assertFalse(isParsableIndex("0")); // zero
        assertFalse(isParsableIndex("00")); // zero
        assertFalse(isParsableIndex("2147483649")); // large numbers
    }

    @Test
    public void isParsableFirstIntAssertTrue() {
        assertTrue(isParsableIndex("abc 1")); // word then number
        assertTrue(isParsableIndex("del1")); // word then number, without spacing
        assertTrue(isParsableIndex("add n/ 8 to Rolodex")); // characters then number, then characters
        assertTrue(isParsableIndex("-1, acba")); // negative numbers
    }

    @Test
    public void parseFirstIntReturnsNonZeroPositiveInteger() throws Exception {
        assertEquals(parseFirstIndex("abc 1"), 1); // word then number
        assertEquals(parseFirstIndex("del1"), 1); // word then number, without spacing
        assertEquals(parseFirstIndex("add n/ 8 to Rolodex"), 8); // characters then number, then characters
        assertEquals(parseFirstIndex("-1, acba"), 1); // negative numbers
        assertEquals(parseFirstIndex("-7"), 7); // negative numbers
    }

    @Test
    public void parseFirstIntCharactersOnlyThrowsNumberFormatException() {
        thrown.expect(NumberFormatException.class);
        parseFirstIndex("one two three");
    }

    @Test
    public void parseFirstIntSymbolsOnlyThrowsNumberFormatException() {
        thrown.expect(NumberFormatException.class);
        parseFirstIndex("~!@# $%^&*()_+");
    }

    @Test
    public void parseFirstIntZeroThrowsNumberFormatException() {
        thrown.expect(NumberFormatException.class);
        parseFirstIndex("0");
    }

    @Test
    public void parseFirstIntLargeNumbersThrowsNumberFormatException() {
        thrown.expect(NumberFormatException.class);
        parseFirstIndex("2147483649");
    }

    @Test
    public void parseRemoveFirstIntRemovesFirstInt() {
        assertEquals("delete second2", parseRemoveFirstIndex("delete1second2"));
        assertEquals("de", parseRemoveFirstIndex("de2"));
        assertEquals("2 3 4 5 6 7", parseRemoveFirstIndex("1 2 3 4 5 6 7"));
        assertEquals("", parseRemoveFirstIndex("1234567"));
        assertEquals("nothing here", parseRemoveFirstIndex("nothing here"));
    }
}
