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
import seedu.address.model.person.Address;
import seedu.address.model.person.EmailAddress;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "87123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_EMAIL_MESSAGE = "hello";
    private static final String VALID_EMAIL_SUBJECT = "subject header";
    private static final String VALID_EMAIL_LOGIN = "adam@gmail.com:password";

    private static final String NOT_FILLED = "-";
    private static final String EMPTY_STRING = "";

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
    public void parsePhone_unfilledPhone_returnsUnfilledPhone() throws Exception {
        Phone expectedPhone = new Phone(NOT_FILLED);
        Optional<Phone> actualPhone = ParserUtil.parsePhone(Optional.of("-"));

        assertEquals(expectedPhone, actualPhone.get());
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
    public void parseAddress_unfilledAddress_returnsUnfilledAddress() throws Exception {
        Address expectedAddress = new Address(NOT_FILLED);
        Optional<Address> actualAddress = ParserUtil.parseAddress(Optional.of("-"));

        assertEquals(expectedAddress, actualAddress.get());
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
        EmailAddress expectedEmail = new EmailAddress(VALID_EMAIL);
        Optional<EmailAddress> actualEmail = ParserUtil.parseEmail(Optional.of(VALID_EMAIL));

        assertEquals(expectedEmail, actualEmail.get());
    }

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

    //@@author awarenessxz
    @Test
    public void parseEmailDraft_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseEmailMessage(null);
        ParserUtil.parseEmailSubject(null);
        ParserUtil.parseLoginDetails(null);
    }

    //@@author awarenessxz
    @Test
    public void parseEmailDraft_emptyString_returnsEmptyString() throws Exception {
        assertTrue(ParserUtil.parseEmailMessage(Optional.of(EMPTY_STRING)).trim().isEmpty());
        assertTrue(ParserUtil.parseEmailSubject(Optional.of(EMPTY_STRING)).trim().isEmpty());
        assertTrue(ParserUtil.parseLoginDetails(Optional.of(EMPTY_STRING)).trim().isEmpty());
    }

    //@@author awarenessxz
    @Test
    public void parseEmailDraft_validArgs_returnsMessage() throws Exception {
        //Expected Email Message
        String expectedMessage = VALID_EMAIL_MESSAGE;
        String message = ParserUtil.parseEmailMessage(Optional.of(VALID_EMAIL_MESSAGE));

        //Expected Email Subject
        String expectedSubject = VALID_EMAIL_SUBJECT;
        String subject = ParserUtil.parseEmailSubject(Optional.of(VALID_EMAIL_SUBJECT));

        //Expected Email Login Details
        String expectedLoginDetails = VALID_EMAIL_LOGIN;
        String loginDetails = ParserUtil.parseEmailSubject(Optional.of(VALID_EMAIL_LOGIN));

        //Verifies if all argument are parsed correctly.
        assertEquals(expectedMessage, message);
        assertEquals(expectedSubject, subject);
        assertEquals(expectedLoginDetails, loginDetails);
    }

    //@@author awarenessxz
    @Test
    public void parseSortOrder() {
        int result;

        try {
            //assertEqual -1 if String empty
            result = ParserUtil.parseSortOrder(Optional.of(""));
            assertEquals(-1, result);

            //assertEqual 0 if String name
            result = ParserUtil.parseSortOrder(Optional.of("name"));
            assertEquals(0, result);

            //assertEqual 1 if String tag
            result = ParserUtil.parseSortOrder(Optional.of("tag"));
            assertEquals(1, result);

            //assertEqual 2 if String email
            result = ParserUtil.parseSortOrder(Optional.of("email"));
            assertEquals(2, result);

            //assertEqual 3 if String address
            result = ParserUtil.parseSortOrder(Optional.of("address"));
            assertEquals(3, result);

            //assertEqual -1 if String invalid
            result = ParserUtil.parseSortOrder(Optional.of("phone"));
            assertEquals(-1, result);

        } catch (IllegalValueException e) {
            assert false : "Should not hit this at all";
        }
    }
}
