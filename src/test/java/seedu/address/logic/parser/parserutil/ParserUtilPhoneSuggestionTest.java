package seedu.address.logic.parser.parserutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.ParserUtil.isParsablePhone;
import static seedu.address.logic.parser.ParserUtil.parseFirstPhone;
import static seedu.address.logic.parser.ParserUtil.parsePhone;
import static seedu.address.logic.parser.ParserUtil.parseRemoveFirstPhone;

import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Phone;

public class ParserUtilPhoneSuggestionTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void isParsableFirstPhoneAssertFalse() {
        assertFalse(isParsablePhone("aloha")); // characters only
        assertFalse(isParsablePhone("+(&%*$^&*")); // symbols only
        assertFalse(isParsablePhone("123")); // short numbers
        assertFalse(isParsablePhone("+123")); // short numbers, with +
        assertFalse(isParsablePhone("add me p/+onetwo"));
        assertFalse(isParsablePhone("fasdfe+624"));
        assertFalse(isParsablePhone("015431asd")); // exactly 6 numbers
        assertFalse(isParsablePhone("+015431words")); // exactly 6 numbers, with +
        assertFalse(isParsablePhone("asda 0154361")); // starts with 0
        assertFalse(isParsablePhone("3rfsdf +0154361")); // starts with 0, with +
    }

    @Test
    public void isParsableFirstPhoneAssertTrue() {
        assertTrue(isParsablePhone("prefix9154361postfix")); // exactly 7 numbers
        assertTrue(isParsablePhone("prefix 93121534"));
        assertTrue(isParsablePhone("p/124293842033123postfix")); // long phone numbers
        assertTrue(isParsablePhone("prefix +9154361asd")); // exactly 7 numbers, with +
        assertTrue(isParsablePhone("prefix+93121534postfix")); // with +
        assertTrue(isParsablePhone(" prefix+124293842033123asd")); // long phone numbers, with +
        assertTrue(isParsablePhone(" 123+124293842033123asd")); // short numbers then long phone numbers, with +
    }

    @Test
    public void parseFirstPhoneStringPrefixReturnsPhone() throws IllegalValueException {
        assertFirstPhone("93121534", "prefix 93121534");
    }

    @Test
    public void parseFirstPhoneStringPrefixStringPostfixReturnsPhone() throws IllegalValueException {
        assertFirstPhone("9154361", "prefix9154361postfix");
    }

    @Test
    public void parseFirstPhoneStringSpacedPrefixStringPostfixReturnsPhone() throws IllegalValueException {
        assertFirstPhone("124293842033123", "prefix 124293842033123postfix");
    }

    @Test
    public void parseFirstPhoneStringPlusPrefixStringPostfixReturnsPhoneWithPlus() throws IllegalValueException {
        assertFirstPhone("+93121534", "prefix+93121534postfix");
    }

    @Test
    public void parseFirstPhoneStringSpacePlusPrefixStringPostfixReturnsPhoneWithPlus() throws IllegalValueException {
        assertFirstPhone("+9154361", "prefix +9154361asd");
    }

    @Test
    public void parseFirstPhoneSpaceStringPlusPrefixStringPostfixReturnsPhoneWithPlus() throws IllegalValueException {
        assertFirstPhone("+124293842033123", "prefix+124293842033123asd");
    }

    @Test
    public void parseFirstPhoneShortNumberPrefixStringPostfixReturnsPhoneWithPlus() throws IllegalValueException {
        assertFirstPhone("+124293842033123", "123+124293842033123asd");
    }

    @Test
    public void parseFirstPhoneTwoPhonesWithPlusReturnsFirstPhoneWithPlus() throws IllegalValueException {
        assertFirstPhone("+123456789", "firstPhone: +123456789 secondPhone: +987654321");
    }

    /**
     * Attempts to parse a phone String and assert the expected value and validity of the phone.
     */
    public void assertFirstPhone(String expected, String input) throws IllegalValueException {
        assertEquals(expected, parseFirstPhone(input));
        Optional possiblePhone = parsePhone(Optional.of(parseFirstPhone(input)));
        assertTrue(possiblePhone.isPresent() && possiblePhone.get() instanceof Phone);
    }

    @Test
    public void parseFirstPhoneCharactersOnlyThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstPhone("aloha");
    }

    @Test
    public void parseFirstPhoneSymbolsOnlyThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstPhone("+(&%*$^&*");
    }

    @Test
    public void parseFirstPhoneShortNumbersThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstPhone("123");
    }

    @Test
    public void parseFirstPhoneShortNumbersWithPlusThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstPhone("+123");
    }

    @Test
    public void parseFirstPhoneSixNumbersThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstPhone("015431asd");
    }

    @Test
    public void parseFirstPhoneSixNumbersWithPlusThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstPhone("+015431words");
    }

    @Test
    public void parseFirstPhoneStartsWithZeroThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstPhone("asda 0154361");
    }

    @Test
    public void parseFirstPhoneStartsWithZeroWithPlusThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstPhone("3rfsdf +0154361");
    }

    @Test
    public void parseRemoveFirstPhoneRemovesFirstPhone() {
        assertEquals("delete second+20154361", parseRemoveFirstPhone("delete +90154361second+20154361"));
        assertEquals("de", parseRemoveFirstPhone("de10154361"));
        assertEquals("35015431 3 35015431 5 7", parseRemoveFirstPhone("35015431 35015431 3 35015431 5 7"));
        assertEquals("", parseRemoveFirstPhone("1234567"));
        assertEquals("", parseRemoveFirstPhone("+1234567"));
        assertEquals("nothing here", parseRemoveFirstPhone("nothing here"));
    }
}
