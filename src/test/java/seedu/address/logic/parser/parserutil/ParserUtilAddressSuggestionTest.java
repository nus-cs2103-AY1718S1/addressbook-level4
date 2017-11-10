package seedu.address.logic.parser.parserutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.ParserUtil.isParsableAddressTillEnd;
import static seedu.address.logic.parser.ParserUtil.parseAddress;
import static seedu.address.logic.parser.ParserUtil.parseAddressTillEnd;
import static seedu.address.logic.parser.ParserUtil.parseRemoveAddressTillEnd;

import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;

public class ParserUtilAddressSuggestionTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void isParsableAddressTillEndAssertFalse() {
        assertFalse(isParsableAddressTillEnd("one two three")); // No block or characters
        assertFalse(isParsableAddressTillEnd("~!@# $%^&*()_+ ")); // symbols
        assertFalse(isParsableAddressTillEnd("0")); // zero
        assertFalse(isParsableAddressTillEnd("00")); // zero
        assertFalse(isParsableAddressTillEnd("2147483649")); // large numbers
        assertFalse(isParsableAddressTillEnd("PeterJack_1190@example.com ")); // emails with numbers
        assertFalse(isParsableAddressTillEnd("123@456.789 ")); // emails with numbers only
    }

    @Test
    public void isParsableAddressTillEndAssertTrue() {
        // add command
        assertTrue(isParsableAddressTillEnd(
                "add Adam Brown classmates 11111111 adam@gmail.com 972, Pansy Street, #08-12, 093173"));
        // edit command
        assertTrue(isParsableAddressTillEnd(
                "edit Bernice Yu 99272758 Blk 30 Lorong 3 Serangoon Gardens, #07-18"));
        // remark command
        assertTrue(isParsableAddressTillEnd(
                "rmk Davis lives at a/Blk 436 Serangoon Gardens Street 26, #16-43 p/12358576 this is a utility"));
        // mixed data
        assertTrue(isParsableAddressTillEnd("8314568937234 Welcome to Rolodex 45 Aljunied Street 85, #11-31"));
        // mixed data with name in front
        assertTrue(isParsableAddressTillEnd("Lecter Goh 8314568937234 45 Aljunied Street 85, #11-31"));
    }

    @Test
    public void parseAddressTillEndAddCommandReturnsAddress() throws IllegalValueException {
        assertAddressTillEnd("972, Pansy Street, #08-12, 093173",
                "add Adam Brown classmates 11111111 adam@gmail.com 972, Pansy Street, #08-12, 093173");
    }

    @Test
    public void parseAddressTillEndEditCommandReturnsAddress() throws IllegalValueException {
        assertAddressTillEnd("Blk 30 Lorong 3 Serangoon Gardens, #07-18",
                "edit Bernice Yu 99272758 Blk 30 Lorong 3 Serangoon Gardens, #07-18");
    }

    @Test
    public void parseAddressTillEndModifyCommandWithPostFixReturnsAddressWithPostfix() throws IllegalValueException {
        assertAddressTillEnd("Blk 436 Serangoon Gardens Street 26, #16-43 p/12358576 this is a utility",
                "rmk Davis lives at a/Blk 436 Serangoon Gardens Street 26, #16-43 p/12358576 this is a utility");
    }

    @Test
    public void parseAddressTillEndPhoneRemarkAddressReturnsAddress() throws IllegalValueException {
        assertAddressTillEnd("45 Aljunied Street 85, #11-31",
                "8314568937234 Welcome to Rolodex 45 Aljunied Street 85, #11-31");
    }

    @Test
    public void parseAddressTillEndNamePhoneAddressReturnsAddress() throws IllegalValueException {
        assertAddressTillEnd("45 Aljunied Street 85, #11-31",
                "Lecter Goh 8314568937234 45 Aljunied Street 85, #11-31");
    }

    @Test
    public void parseAddressTillEndNamePhoneRemarkAddressReturnsAddress() throws IllegalValueException {
        assertAddressTillEnd("45 Aljunied Street 85, #11-31",
                "Lecter Goh 8314568937234 Welcome to Rolodex 45 Aljunied Street 85, #11-31");
    }

    @Test
    public void parseAddressTillEndIntegerStartWithWhitespaceReturnsTrimmedAddress() throws IllegalValueException {
        assertAddressTillEnd("123", "123 ");
    }

    /**
     * Attempts to parse a String containing an address to the end of the String
     * and assert the expected value and validity of the address.
     */
    public void assertAddressTillEnd(String expected, String input) throws IllegalValueException {
        assertEquals(expected, parseAddressTillEnd(input));
        Optional possibleAddress = parseAddress(Optional.of(parseAddressTillEnd(input)));
        assertTrue(possibleAddress.isPresent() && possibleAddress.get() instanceof Address);
    }

    @Test
    public void parseAddressTillEndNoBlockNumberThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseAddressTillEnd("one two three Madison Avenue");
    }

    @Test
    public void parseAddressTillEndSymbolsOnlyThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseAddressTillEnd("~!@# $%^&*()_+");
    }

    @Test
    public void parseAddressTillEndIntegersOnlyThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseAddressTillEnd("123");
    }

    @Test
    public void parseAddressTillEndLargeIntegersWithWhitespaceThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseAddressTillEnd("1233426256 ");
    }

    @Test
    public void parseAddressTillEndIntegersInEmailThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseAddressTillEnd("PeterJack_1190@example.com ");
    }

    @Test
    public void parseAddressTillEndIntegersInEmailOnlyThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseAddressTillEnd("123@456.789 ");
    }

    @Test
    public void parseRemoveAddressTillEndRemovesAddressTillEnd() {
        assertEquals("add Adam Brown classmates 11111111 adam@gmail.com",
                parseRemoveAddressTillEnd(
                        "add Adam Brown classmates 11111111 adam@gmail.com 972, Pansy Street, #08-12, 093173"));

        assertEquals("edit Bernice Yu 99272758",
                parseRemoveAddressTillEnd(
                        "edit Bernice Yu 99272758 Blk 30 Lorong 3 Serangoon Gardens, #07-18"));

        assertEquals("rmk Davis lives at",
                parseRemoveAddressTillEnd(
                        "rmk Davis lives at a/Blk 436 Serangoon Gardens Street 26, #16-43 p/12358576 this is a"
                                + " utility"));

        assertEquals("8314568937234 Welcome to Rolodex",
                parseRemoveAddressTillEnd(
                        "8314568937234 Welcome to Rolodex 45 Aljunied Street 85, #11-31"));

        assertEquals("Lecter Goh 8314568937234",
                parseRemoveAddressTillEnd(
                        "Lecter Goh 8314568937234 45 Aljunied Street 85, #11-31"));

        assertEquals("Lecter Goh 8314568937234 Welcome to Rolodex",
                parseRemoveAddressTillEnd(
                        "Lecter Goh 8314568937234 Welcome to Rolodex 45 Aljunied Street 85, #11-31"));

        assertEquals("", parseRemoveAddressTillEnd("123 "));
    }
}
