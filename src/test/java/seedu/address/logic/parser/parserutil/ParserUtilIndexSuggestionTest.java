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
    public void isParsableFirstIndexAssertFalse() {
        assertFalse(isParsableIndex("one two three", 4)); // characters
        assertFalse(isParsableIndex("~!@# $%^&*()_+", Integer.MAX_VALUE)); // symbols
        assertFalse(isParsableIndex("0", Integer.MAX_VALUE)); // zero
        assertFalse(isParsableIndex("00", Integer.MAX_VALUE)); // zero
        assertFalse(isParsableIndex("51", 50)); // large numbers > rolodex size
        assertFalse(isParsableIndex("2147483649", Integer.MAX_VALUE)); // large numbers > 2^31
    }

    @Test
    public void isParsableFirstIndexAssertTrue() {
        assertTrue(isParsableIndex("abc 1", 1)); // word then number
        assertTrue(isParsableIndex("del1", 1)); // word then number, without spacing
        assertTrue(isParsableIndex("add n/ 8 to Rolodex", 8)); // characters then number, then characters
        assertTrue(isParsableIndex("-1, acba", 1)); // negative numbers
    }

    @Test
    public void parseFirstIndexReturnsNonZeroPositiveInteger() throws Exception {
        assertEquals(parseFirstIndex("abc 1", 1), 1); // word then number
        assertEquals(parseFirstIndex("del1", 1), 1); // word then number, without spacing
        assertEquals(parseFirstIndex("add n/ 8 to Rolodex", 8), 8); // characters then number, then characters
        assertEquals(parseFirstIndex("-1, acba", 1), 1); // negative numbers
        assertEquals(parseFirstIndex("-7", 7), 7); // negative numbers
    }

    @Test
    public void parseFirstIndexCharactersOnlyThrowsNumberFormatException() {
        thrown.expect(NumberFormatException.class);
        parseFirstIndex("one two three", 4);
    }

    @Test
    public void parseFirstIndexSymbolsOnlyThrowsNumberFormatException() {
        thrown.expect(NumberFormatException.class);
        parseFirstIndex("~!@# $%^&*()_+", Integer.MAX_VALUE);
    }

    @Test
    public void parseFirstIndexExceedLimitsThrowsNumberFormatException() {
        thrown.expect(NumberFormatException.class);
        parseFirstIndex("51", 50);
    }

    @Test
    public void parseFirstIndexZeroThrowsNumberFormatException() {
        thrown.expect(NumberFormatException.class);
        parseFirstIndex("0", Integer.MAX_VALUE);
    }

    @Test
    public void parseFirstIndexLargeNumbersThrowsNumberFormatException() {
        thrown.expect(NumberFormatException.class);
        parseFirstIndex("2147483649", Integer.MAX_VALUE);
    }

    @Test
    public void parseRemoveFirstIndexRemovesFirstIndex() {
        assertEquals("delete second2", parseRemoveFirstIndex("delete1second2", 1));
        assertEquals("delete second2", parseRemoveFirstIndex("delete1 second2", 1));
        assertEquals("de", parseRemoveFirstIndex("de2", 2));
        assertEquals("2 3 4 5 6 7", parseRemoveFirstIndex("1 2 3 4 5 6 7", 1));
        assertEquals("", parseRemoveFirstIndex("1234567", 1234567));
        assertEquals("nothing here", parseRemoveFirstIndex("nothing here", Integer.MAX_VALUE));
    }
}
