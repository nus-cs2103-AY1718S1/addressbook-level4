package seedu.address.logic.parser.parserutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.ParserUtil.isParsableName;
import static seedu.address.logic.parser.ParserUtil.parseName;
import static seedu.address.logic.parser.ParserUtil.parseRemainingName;

import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;

public class ParserUtilNameSuggestionTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void isParsableNameAssertFalse() {
        assertFalse(isParsableName("")); // Blank
        assertFalse(isParsableName("        ")); // Whitespace
        assertFalse(isParsableName("~!@# $%^&*()_+ ")); // symbols
    }

    @Test
    public void isParsableNameAssertTrue() {
        assertTrue(isParsableName("one two three")); // Characters
        assertTrue(isParsableName("2147483649")); // Numbers
        assertTrue(isParsableName("PeterJack_1190@example.com ")); // emails
        assertTrue(isParsableName("Adam Brown classmates 11111111 adam@gmail.com 972, Pansy Street, #08-12, 093173"));
        assertTrue(isParsableName("Bernice Yu 99272758 Blk 30 Lorong 3 Serangoon Gardens, #07-18"));
        assertTrue(isParsableName("&$Davis lives at a/Blk 436 Serangoon Gardens Street"));
        assertTrue(isParsableName("8314568937234 Welcome to Rolodex 45 Aljunied Street 85, #11-31"));
        assertTrue(isParsableName("Lecter Goh 8314568937234 45 Aljunied Street 85, #11-31"));
    }

    @Test
    public void parseRemainingNameWhiteSpaceCharactersOnlyReturnsRemainingNameTrimmed() throws IllegalValueException {
        assertName("one two three", " one two three");
    }

    @Test
    public void parseRemainingNamePrefixCharactersOnlyReturnsRemainingNameWithPrefix() throws IllegalValueException {
        assertName("one two three", "n/one two three");
    }

    /**
     * Attempts to parse a name String and assert the expected value and validity of the name.
     */
    public void assertName(String expected, String input) throws IllegalValueException {
        assertEquals(expected, parseRemainingName(input));
        Optional possibleName = parseName(Optional.of(parseRemainingName(input)));
        assertTrue(possibleName.isPresent() && possibleName.get() instanceof Name);
    }

    @Test
    public void parseNameBlankStringThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseRemainingName("");
    }

    @Test
    public void parseNameWhitespaceStringThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseRemainingName("           ");
    }

    @Test
    public void parseNameSymbolsOnlyThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseRemainingName("~!@# $%^&*()_+ ");
    }
}
