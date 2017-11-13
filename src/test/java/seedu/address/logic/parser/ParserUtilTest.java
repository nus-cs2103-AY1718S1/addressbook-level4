package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
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
import seedu.address.model.alias.Keyword;
import seedu.address.model.alias.Representation;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_BIRTHDAY = "1901/01/01";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_KEYWORD = "a";
    private static final String INVALID_REPRESENTATION = "";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_BIRTHDAY = "01/01/1901";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_KEYWORD = "wfm";
    private static final String VALID_REPRESENTATION = "Works For Me";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseName(null, "test");
    }

    @Test
    public void parseName_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseName(Optional.of(INVALID_NAME), "test");
    }

    @Test
    public void parseName_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseName(Optional.empty(), "test").isPresent());
    }

    @Test
    public void parseName_validValue_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        Optional<Name> actualName = ParserUtil.parseName(Optional.of(VALID_NAME), "test");

        assertEquals(expectedName, actualName.get());
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parsePhone(null, "test");
    }

    @Test
    public void parsePhone_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parsePhone(Optional.of(INVALID_PHONE), "test");
    }

    @Test
    public void parsePhone_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parsePhone(Optional.empty(), "test").isPresent());
    }

    @Test
    public void parsePhone_validValue_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        Optional<Phone> actualPhone = ParserUtil.parsePhone(Optional.of(VALID_PHONE), "test");

        assertEquals(expectedPhone, actualPhone.get());
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseAddress(null, "test");
    }

    @Test
    public void parseAddress_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseAddress(Optional.of(INVALID_ADDRESS), "test");
    }

    @Test
    public void parseAddress_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseAddress(Optional.empty(), "test").isPresent());
    }

    @Test
    public void parseAddress_validValue_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        Optional<Address> actualAddress = ParserUtil.parseAddress(Optional.of(VALID_ADDRESS), "test");

        assertEquals(expectedAddress, actualAddress.get());
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseEmail(null, "test");
    }

    @Test
    public void parseEmail_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseEmail(Optional.of(INVALID_EMAIL), "test");
    }

    @Test
    public void parseEmail_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseEmail(Optional.empty(), "test").isPresent());
    }

    @Test
    public void parseEmail_validValue_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        Optional<Email> actualEmail = ParserUtil.parseEmail(Optional.of(VALID_EMAIL), "test");

        assertEquals(expectedEmail, actualEmail.get());
    }

    //@@author wynkheng
    @Test
    public void parseBirthday_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseBirthday(null, "test");
    }

    @Test
    public void parseBirthday_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseBirthday(Optional.of(INVALID_BIRTHDAY), "test");
    }

    @Test
    public void parseBirthday_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseBirthday(Optional.empty(), "test").isPresent());
    }

    @Test
    public void parseBirthday_validValue_returnsBirthday() throws Exception {
        Birthday expectedBirthday = new Birthday(VALID_BIRTHDAY);
        Optional<Birthday> actualBirthday = ParserUtil.parseBirthday(Optional.of(VALID_BIRTHDAY), "test");

        assertEquals(expectedBirthday, actualBirthday.get());
    }
    //@@author

    @Test
    public void parseTags_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTags(null);
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseKeyword_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseKeyword(null);
    }

    @Test
    public void parseKeyword_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseKeyword(Optional.of(INVALID_KEYWORD));
    }

    @Test
    public void parseKeyword_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseKeyword(Optional.empty()).isPresent());
    }

    @Test
    public void parseKeyword_validValue_returnsKeyword() throws Exception {
        Keyword expectedKeyword = new Keyword(VALID_KEYWORD);
        Optional<Keyword> actualKeyword = ParserUtil.parseKeyword(Optional.of(VALID_KEYWORD));

        assertEquals(expectedKeyword, actualKeyword.get());
    }

    @Test
    public void parseRepresentation_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseRepresentation(null);
    }

    @Test
    public void parseRepresentation_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseRepresentation(Optional.of(INVALID_REPRESENTATION));
    }

    @Test
    public void parseRepresentation_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseRepresentation(Optional.empty()).isPresent());
    }

    @Test
    public void parseRepresentation_validValue_returnsRepresentation() throws Exception {
        Representation expectedRepresentation = new Representation(VALID_REPRESENTATION);
        Optional<Representation> actualRepresentation =
                ParserUtil.parseRepresentation(Optional.of(VALID_REPRESENTATION));

        assertEquals(expectedRepresentation, actualRepresentation.get());
    }
}
