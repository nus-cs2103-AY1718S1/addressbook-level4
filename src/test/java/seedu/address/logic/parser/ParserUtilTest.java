package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Homepage;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+6512A34";
    private static final String INVALID_ADDRESS = "%$";
    private static final String INVALID_EMAIL = "//@$example.com";
    private static final String INVALID_TAG = "#$@friend";
    private static final String INVALID_HOMEPAGE = "%#google.ga";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_HOMEPAGE = "http://www.google.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

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
        ParserUtil.parseName(null);
    }

    @Test
    public void parseName_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseName(Optional.of(INVALID_NAME));
    }

    @Test
    public void parseName_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseName(Optional.empty()).isPresent());
    }

    @Test
    public void parseName_validValue_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        Optional<Name> actualName = ParserUtil.parseName(Optional.of(VALID_NAME));

        assertEquals(expectedName, actualName.get());
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parsePhone(null);
    }

    @Test
    public void parsePhone_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parsePhone(Optional.of(INVALID_PHONE));
    }

    @Test
    public void parsePhone_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parsePhone(Optional.empty()).isPresent());
    }

    @Test
    public void parsePhone_validValue_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        Optional<Phone> actualPhone = ParserUtil.parsePhone(Optional.of(VALID_PHONE));

        assertEquals(expectedPhone, actualPhone.get());
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseAddress(null);
    }

    @Test
    public void parseAddress_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseAddress(Optional.of(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseAddress(Optional.empty()).isPresent());
    }

    @Test
    public void parseAddress_validValue_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        Optional<Address> actualAddress = ParserUtil.parseAddress(Optional.of(VALID_ADDRESS));

        assertEquals(expectedAddress, actualAddress.get());
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseEmail(null);
    }

    @Test
    public void parseEmail_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseEmail(Optional.of(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseEmail(Optional.empty()).isPresent());
    }

    @Test
    public void parseEmail_validValue_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        Optional<Email> actualEmail = ParserUtil.parseEmail(Optional.of(VALID_EMAIL));

        assertEquals(expectedEmail, actualEmail.get());
    }

    @Test
    public void parseTags_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTags(null);
    }

    //@@author yewshengkai
    @Test
    public void parseTag_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTag(null);
    }
    //@@author

    @Test
    public void parseTags_collectionWithInvalidTags_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    //@@author yewshengkai
    @Test
    public void parseAllDetail_withValidDetails() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        ArrayList<String> addressList = ParserUtil.parseAllDetail(
                Arrays.asList(VALID_ADDRESS), FindCommand.COMMAND_WORD_ADDRESS);
        assertEquals(expectedAddress.value, addressList.toString().replaceAll(
                "['\\[\\],']", ""));
        ParserUtil.parseAllDetail(addressList, FindCommand.COMMAND_WORD_ADDRESS);

        Email expectedEmail = new Email(VALID_EMAIL);
        ArrayList<String> emailList = ParserUtil.parseAllDetail(Arrays.asList(
                VALID_EMAIL), FindCommand.COMMAND_WORD_EMAIL);
        assertEquals(expectedEmail.value, emailList.toString().replaceAll(
                "['\\[\\]']", ""));
        ParserUtil.parseAllDetail(emailList, FindCommand.COMMAND_WORD_EMAIL);

        //@@author karrui
        Homepage expectedHomepage = new Homepage(VALID_HOMEPAGE);
        ArrayList<String> homepageList = ParserUtil.parseAllDetail(Arrays.asList(
                VALID_HOMEPAGE), FindCommand.COMMAND_WORD_HOMEPAGE);
        assertEquals(expectedHomepage.value, homepageList.toString().replaceAll(
                "['\\[\\]']", ""));

        //@@author yewshengkai
        ParserUtil.parseAllDetail(homepageList, FindCommand.COMMAND_WORD_HOMEPAGE);
        Phone expectedPhone = new Phone(VALID_PHONE);
        ArrayList<String> phoneList = ParserUtil.parseAllDetail(Arrays.asList(
                VALID_PHONE), FindCommand.COMMAND_WORD_PHONE);
        assertEquals(expectedPhone.value, phoneList.toString().replaceAll(
                "['\\[\\]']", ""));
        ParserUtil.parseAllDetail(phoneList, FindCommand.COMMAND_WORD_PHONE);

        Tag expectedTag = new Tag(VALID_TAG_1);
        ArrayList<String> tagList = ParserUtil.parseAllDetail(Arrays.asList(
                VALID_TAG_1), FindCommand.COMMAND_WORD_TAG);
        assertEquals(expectedTag.tagName, tagList.toString().replaceAll(
                "['\\[\\]']", ""));
        ParserUtil.parseAllDetail(tagList, FindCommand.COMMAND_WORD_TAG);
    }

    //@@author yewshengkai
    @Test
    public void parseAllDetail_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        Address expectedAddress = new Address(INVALID_ADDRESS);
        ArrayList<String> invalidAddress = ParserUtil.parseAllDetail(
                Arrays.asList(INVALID_ADDRESS), FindCommand.COMMAND_WORD_ADDRESS);
        assertEquals(expectedAddress.value, invalidAddress.toString().replaceAll(
                "['\\[\\],']", ""));
        ParserUtil.parseAllDetail(invalidAddress, FindCommand.COMMAND_WORD_ADDRESS);
        assertFalse(INVALID_ADDRESS, Address.isValidAddress(INVALID_ADDRESS));

        Email expectedEmail = new Email(INVALID_EMAIL);
        ArrayList<String> invalidEmail = ParserUtil.parseAllDetail(
                Arrays.asList(INVALID_EMAIL), FindCommand.COMMAND_WORD_EMAIL);
        assertEquals(expectedEmail.value, invalidEmail.toString());
        ParserUtil.parseAllDetail(invalidEmail, FindCommand.COMMAND_WORD_EMAIL);

        Homepage expectedHomepage = new Homepage(INVALID_HOMEPAGE);
        ArrayList<String> invalidHomepage = ParserUtil.parseAllDetail(
                Arrays.asList(INVALID_HOMEPAGE), FindCommand.COMMAND_WORD_HOMEPAGE);
        assertEquals(expectedHomepage.value, invalidHomepage.toString());
        ParserUtil.parseAllDetail(invalidHomepage, FindCommand.COMMAND_WORD_HOMEPAGE);

        Phone expectedPhone = new Phone(INVALID_PHONE);
        ArrayList<String> invalidPhone = ParserUtil.parseAllDetail(
                Arrays.asList(INVALID_PHONE), FindCommand.COMMAND_WORD_PHONE);
        assertEquals(expectedPhone.value, invalidPhone.toString());
        ParserUtil.parseAllDetail(invalidPhone, FindCommand.COMMAND_WORD_PHONE);

        Tag expectedTag = new Tag(INVALID_TAG);
        ArrayList<String> invalidTag = ParserUtil.parseAllDetail(
                Arrays.asList(INVALID_TAG), FindCommand.COMMAND_WORD_TAG);
        assertEquals(expectedTag.tagName, invalidTag.toString());
        ParserUtil.parseAllDetail(invalidTag, FindCommand.COMMAND_WORD_TAG);
    }
    //@@author

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }
}

