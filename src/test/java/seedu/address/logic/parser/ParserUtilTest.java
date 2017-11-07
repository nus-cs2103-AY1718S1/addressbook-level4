package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.parser.ParserUtil.parseEmail;
import static seedu.address.logic.parser.ParserUtil.parseFirstEmail;
import static seedu.address.logic.parser.ParserUtil.parseFirstFilePath;
import static seedu.address.logic.parser.ParserUtil.parseFirstInt;
import static seedu.address.logic.parser.ParserUtil.parseFirstPhone;
import static seedu.address.logic.parser.ParserUtil.parsePhone;
import static seedu.address.logic.parser.ParserUtil.parseRemoveFirstEmail;
import static seedu.address.logic.parser.ParserUtil.parseRemoveFirstInt;
import static seedu.address.logic.parser.ParserUtil.parseRemoveFirstPhone;
import static seedu.address.logic.parser.ParserUtil.tryParseEmail;
import static seedu.address.logic.parser.ParserUtil.tryParseFilePath;
import static seedu.address.logic.parser.ParserUtil.tryParseInt;
import static seedu.address.logic.parser.ParserUtil.tryParsePhone;
import static seedu.address.storage.util.RolodexStorageUtil.isValidRolodexStorageExtension;
import static seedu.address.storage.util.RolodexStorageUtil.isValidRolodexStorageFilepath;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "1234567";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_REMARK = "Likes to eat.";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndexInvalidInputThrowsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndexOutOfRangeInputThrowsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndexValidInputSuccess() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseNameNullThrowsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseName(null);
    }

    @Test
    public void parseNameInvalidValueThrowsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseName(Optional.of(INVALID_NAME));
    }

    @Test
    public void parseNameOptionalEmptyReturnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseName(Optional.empty()).isPresent());
    }

    @Test
    public void parseNameValidValueReturnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        Optional<Name> actualName = ParserUtil.parseName(Optional.of(VALID_NAME));

        assertEquals(expectedName, actualName.get());
    }

    @Test
    public void parsePhoneNullThrowsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        parsePhone(null);
    }

    @Test
    public void parsePhoneInvalidValueThrowsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        parsePhone(Optional.of(INVALID_PHONE));
    }

    @Test
    public void parsePhoneOptionalEmptyReturnsOptionalEmpty() throws Exception {
        assertFalse(parsePhone(Optional.empty()).isPresent());
    }

    @Test
    public void parsePhoneValidValueReturnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        Optional<Phone> actualPhone = parsePhone(Optional.of(VALID_PHONE));

        assertEquals(expectedPhone, actualPhone.get());
    }

    @Test
    public void parseAddressNullThrowsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseAddress(null);
    }

    @Test
    public void parseAddressInvalidValueThrowsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseAddress(Optional.of(INVALID_ADDRESS));
    }

    @Test
    public void parseAddressOptionalEmptyReturnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseAddress(Optional.empty()).isPresent());
    }

    @Test
    public void parseAddressValidValueReturnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        Optional<Address> actualAddress = ParserUtil.parseAddress(Optional.of(VALID_ADDRESS));

        assertEquals(expectedAddress, actualAddress.get());
    }

    @Test
    public void parseEmailNullThrowsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        parseEmail(null);
    }

    @Test
    public void parseEmailInvalidValueThrowsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        parseEmail(Optional.of(INVALID_EMAIL));
    }

    @Test
    public void parseEmailOptionalEmptyReturnsOptionalEmpty() throws Exception {
        assertFalse(parseEmail(Optional.empty()).isPresent());
    }

    @Test
    public void parseEmailValidValueReturnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        Optional<Email> actualEmail = parseEmail(Optional.of(VALID_EMAIL));

        assertEquals(expectedEmail, actualEmail.get());
    }

    @Test
    public void parseRemarkNullThrowsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseRemark(null);
    }

    @Test
    public void parseRemarkOptionalEmptyReturnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseRemark(Optional.empty()).isPresent());
    }

    @Test
    public void parseRemarkValidValueReturnsRemark() throws Exception {
        Remark expectedRemark = new Remark(VALID_REMARK);
        Optional<Remark> actualRemark = ParserUtil.parseRemark(Optional.of(VALID_REMARK));

        assertEquals(expectedRemark, actualRemark.get());
    }



    @Test
    public void parseTagsNullThrowsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTags(null);
    }

    @Test
    public void parseTagsCollectionWithInvalidTagsThrowsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG));
    }

    @Test
    public void parseTagsEmptyCollectionReturnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTagsCollectionWithValidTagsReturnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    // ========================================= IntelliParser tests =========================================

    @Test
    public void tryParseFirstIntAssertFalse() {
        assertFalse(tryParseInt("one two three")); // characters
        assertFalse(tryParseInt("~!@# $%^&*()_+")); // symbols
        assertFalse(tryParseInt("0")); // zero
        assertFalse(tryParseInt("00")); // zero
        assertFalse(tryParseInt("2147483649")); // large numbers
    }

    @Test
    public void tryParseFirstIntAssertTrue() {
        assertTrue(tryParseInt("abc 1")); // word then number
        assertTrue(tryParseInt("del1")); // word then number, without spacing
        assertTrue(tryParseInt("add n/ 8 to Rolodex")); // characters then number, then characters
        assertTrue(tryParseInt("-1, acba")); // negative numbers
    }

    @Test
    public void parseFirstIntReturnsNonZeroPositiveInteger() throws Exception {
        assertEquals(parseFirstInt("abc 1"), 1); ; // word then number
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

    @Test
    public void tryParseFirstFilePathAssertFalse() {
        assertFalse(tryParseFilePath("one two three")); // characters
        assertFalse(tryParseFilePath("~!@# $%^&*()_+")); // symbols
        assertFalse(tryParseFilePath("2147483649")); // numbers
        assertFalse(tryParseFilePath("////")); // empty directory
        assertFalse(tryParseFilePath("C:/")); // empty directory, with absolute prefix
        assertFalse(tryParseFilePath("C:////")); // empty directory, with absolute prefix
        assertFalse(tryParseFilePath("var////")); // empty directory, with relative prefix
        assertFalse(tryParseFilePath("var\\\\\\\\")); // empty directory, backslashes
    }

    @Test
    public void tryParseFirstFilePathAssertTrue() {
        assertTrue(tryParseFilePath("data/default.rldx"));
        assertTrue(tryParseFilePath("Prefix data/default"));
        assertTrue(tryParseFilePath("C:/Users/Downloads/my.rldx postfix"));
        assertTrue(tryParseFilePath("Some prefix C:/abc.rldx"));
        assertTrue(tryParseFilePath("pref  214/748/36/49 postfix"));
        assertTrue(tryParseFilePath("data////r"));
        assertTrue(tryParseFilePath("C:////g postfix"));
        assertTrue(tryParseFilePath("prefix var////f"));
        assertTrue(tryParseFilePath("prefix var\\a\\b\\c\\"));
    }

    @Test
    public void parseFirstFilePathReturnsFilePathWithExtension() throws IllegalArgumentException {
        String[] input = {
            " data/default.rldx",
            "Prefix data/default",
            "C:/Users/Downloads/my.rldx rolodex",
            "Some prefix C:/abc.rldx",
            "pref  214/748/36/49 postfix",
            "data////r",
            "C:////g",
            "C:////g/",
            "var////f",
            "var\\a\\b\\c\\"
        };
        String[] expected = {
            "data/default.rldx",
            "Prefix data/default.rldx",
            "C:/Users/Downloads/my rolodex.rldx",
            "Some prefix C:/abc.rldx",
            "pref  214/748/36/49 postfix.rldx",
            "data////r.rldx",
            "C:////g.rldx",
            "C:////g.rldx",
            "var////f.rldx",
            "var/a/b/c.rldx"
        };
        for (int i = 0; i < input.length; i++) {
            assertEquals(expected[i], parseFirstFilePath(input[i]));
            String possibleFilepath = parseFirstFilePath(input[i]);
            assertTrue(isValidRolodexStorageFilepath(possibleFilepath));
            assertTrue(isValidRolodexStorageExtension(possibleFilepath));
        }
    }

    @Test
    public void parseFirstFilePathCharactersOnlyThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstFilePath("one two three");
    }

    @Test
    public void parseFirstFilePathSymbolsOnlyThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstFilePath("~!@# $%^&*()_+");
    }

    @Test
    public void parseFirstFilePathNumbersOnlyThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstFilePath("2147483649");
    }

    @Test
    public void parseFirstFilePathEmptyDirectoryThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstFilePath("////");
    }

    @Test
    public void parseFirstFilePathEmptyDirectoryWithAbsolutePrefixThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstFilePath("C:/");
    }

    @Test
    public void parseFirstFilePathEmptyDirectoryWithAbsolutePrefixLongThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstFilePath("C:////");
    }

    @Test
    public void parseFirstFilePathEmptyDirectoryWithRelativePrefixThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstFilePath("var////");
    }

    @Test
    public void parseFirstFilePathEmptyDirectoryWithRelativePrefixBackslashesThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstFilePath("var\\\\\\\\");
    }

    @Test
    public void tryParseFirstPhoneAssertFalse() {
        assertFalse(tryParsePhone("aloha")); // characters only
        assertFalse(tryParsePhone("+(&%*$^&*")); // symbols only
        assertFalse(tryParsePhone("123")); // short numbers
        assertFalse(tryParsePhone("+123")); // short numbers, with +
        assertFalse(tryParsePhone("add me p/+onetwo"));
        assertFalse(tryParsePhone("fasdfe+624"));
        assertFalse(tryParsePhone("015431asd")); // exactly 6 numbers
        assertFalse(tryParsePhone("+015431words")); // exactly 6 numbers, with +
        assertFalse(tryParsePhone("asda 0154361")); // starts with 0
        assertFalse(tryParsePhone("3rfsdf +0154361")); // starts with 0, with +
    }

    @Test
    public void tryParseFirstPhoneAssertTrue() {
        assertTrue(tryParsePhone("prefix9154361postfix")); // exactly 7 numbers
        assertTrue(tryParsePhone("prefix 93121534"));
        assertTrue(tryParsePhone("prefix 124293842033123postfix")); // long phone numbers
        assertTrue(tryParsePhone("prefix +9154361asd")); // exactly 7 numbers, with +
        assertTrue(tryParsePhone("prefix+93121534postfix")); // with +
        assertTrue(tryParsePhone(" prefix+124293842033123asd")); // long phone numbers, with +
        assertTrue(tryParsePhone(" 123+124293842033123asd")); // short numbers then long phone numbers, with +
    }

    @Test
    public void parseFirstPhoneReturnsPhone() throws IllegalValueException {
        String[] input = {
            "prefix9154361postfix",
            "123+124293842033123asd",
            "prefix9154361postfix",
            "prefix 93121534",
            "prefix 124293842033123postfix",
            "prefix +9154361asd",
            "prefix+93121534postfix",
            " prefix+124293842033123asd",
            "firstPhone: +123456789 secondPhone: +987654321"
        };
        String[] expected = {
            "9154361",
            "+124293842033123",
            "9154361",
            "93121534",
            "124293842033123",
            "+9154361",
            "+93121534",
            "+124293842033123",
            "+123456789"
        };
        for (int i = 0; i < input.length; i++) {
            assertEquals(expected[i], parseFirstPhone(input[i]));
            Optional possiblePhone = parsePhone(Optional.of(parseFirstPhone(input[i])));
            assertTrue(possiblePhone.isPresent() && possiblePhone.get() instanceof Phone);
        }
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

    @Test
    public void tryParseFirstEmailAssertFalse() {
        assertFalse(tryParseEmail("aloha")); // characters only
        assertFalse(tryParseEmail("+(&%*$^&*")); // symbols only
        assertFalse(tryParseEmail("123")); // numbers
        assertFalse(tryParseEmail("prefix/abc+@gm.com")); // invalid characters before @
        assertFalse(tryParseEmail("add me e/abc+@gmab.com")); // invalid characters
        assertFalse(tryParseEmail("@email")); // missing local part
        assertFalse(tryParseEmail("peterjackexample.com")); // missing '@' symbol
        assertFalse(tryParseEmail("peterjack@")); // missing domain name
    }

    @Test
    public void tryParseFirstEmailAssertTrue() {
        assertTrue(tryParseEmail("e/PeterJack_1190@example.com/postfix"));
        assertTrue(tryParseEmail("addemail/a@b/postfix")); // minimal
        assertTrue(tryParseEmail("test@localhost/postfix")); // alphabets only
        assertTrue(tryParseEmail("123@145/postfix")); // numeric local part and domain name
        assertTrue(tryParseEmail("e/a1@example1.com")); // mixture of alphanumeric and dot characters
        assertTrue(tryParseEmail("_user_@_e_x_a_m_p_l_e_.com_")); // underscores
        assertTrue(tryParseEmail("add peter_jack@very_very_very_long_example.com something")); // long domain name
        assertTrue(tryParseEmail("email if.you.dream.it_you.can.do.it@example.com")); // long local part
    }

    @Test
    public void parseFirstEmailReturnsEmail() throws IllegalValueException {
        String[] input = {
            "e/PeterJack_1190@example.com/postfix",
            "addemail/a@b/postfix",
            "test@localhost/postfix",
            "123@145/postfix",
            "e/a1@example1.com",
            "_user_@_e_x_a_m_p_l_e_.com_",
            "add peter_jack@very_very_very_long_example.com something",
            "email if.you.dream.it_you.can.do.it@example.com"
        };
        String[] expected = {
            "PeterJack_1190@example.com",
            "a@b",
            "test@localhost",
            "123@145",
            "a1@example1.com",
            "_user_@_e_x_a_m_p_l_e_.com_",
            "peter_jack@very_very_very_long_example.com",
            "if.you.dream.it_you.can.do.it@example.com"
        };
        for (int i = 0; i < input.length; i++) {
            assertEquals(expected[i], parseFirstEmail(input[i]));
            Optional possibleEmail = parseEmail(Optional.of(parseFirstEmail(input[i])));
            assertTrue(possibleEmail.isPresent() && possibleEmail.get() instanceof Email);
        }
    }

    @Test
    public void parseFirstEmailCharactersOnlyThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstEmail("aloha");
    }

    @Test
    public void parseFirstEmailSymbolsOnlyThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstEmail("+(&%*$^&*");
    }

    @Test
    public void parseFirstEmailNumbersThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstEmail("123");
    }

    @Test
    public void parseFirstEmailInvalidCharactersBeforeAtThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstEmail("prefix/abc+@gm.com");
    }

    @Test
    public void parseFirstEmailInvalidCharactersThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstEmail("add me e/abc+@gmab.com");
    }

    @Test
    public void parseFirstEmailMissingLocalThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstEmail("@email");
    }

    @Test
    public void parseFirstEmailMissingAtThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstEmail("peterjackexample.com");
    }

    @Test
    public void parseFirstEmailMissingDomainThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstEmail("peterjack@");
    }

    @Test
    public void parseRemoveFirstEmailRemovesFirstEmail() {
        assertEquals("delete second def@ghi", parseRemoveFirstEmail("delete abc@def.com second def@ghi"));
        assertEquals("de", parseRemoveFirstEmail("de abcdef@g"));
        assertEquals("", parseRemoveFirstEmail("ab@de"));
        assertEquals("Not_an_email+@abn.de", parseRemoveFirstEmail("Not_an_email+@abn.de"));
        assertEquals("", parseRemoveFirstEmail("a@abn.de"));
        assertEquals("nothing here", parseRemoveFirstEmail("nothing here"));
    }
}
