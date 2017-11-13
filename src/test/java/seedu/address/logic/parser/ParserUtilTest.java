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
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.event.Desc;
import seedu.address.model.person.event.EventDate;
import seedu.address.model.person.event.Header;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String INVALID_HEADER = " ";
    private static final String INVALID_DESC = " ";
    private static final String INVALID_EVENT_DATE = "2017-23-20";

    private static final String VALID_HEADER = "Outing";
    private static final String VALID_DESC = "dinner with friends";
    private static final String VALID_EVENT_DATE = "2017-11-20";

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
    //@@author royceljh
    @Test
    public void parseHeader_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseHeader(null);
    }

    @Test
    public void parseHeader_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseHeader(Optional.of(INVALID_HEADER));
    }

    @Test
    public void parseHeader_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseHeader(Optional.empty()).isPresent());
    }

    @Test
    public void parseHeader_validValue_returnsHeader() throws Exception {
        Header expectedHeader = new Header(VALID_HEADER);
        Optional<Header> actualHeader = ParserUtil.parseHeader(Optional.of(VALID_HEADER));

        assertEquals(expectedHeader, actualHeader.get());
    }

    @Test
    public void parseDesc_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseDesc(null);
    }

    @Test
    public void parseDesc_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseDesc(Optional.of(INVALID_DESC));
    }

    @Test
    public void parseDesc_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseDesc(Optional.empty()).isPresent());
    }

    @Test
    public void parseDesc_validValue_returnsDesc() throws Exception {
        Desc expectedDesc = new Desc(VALID_DESC);
        Optional<Desc> actualDesc = ParserUtil.parseDesc(Optional.of(VALID_DESC));

        assertEquals(expectedDesc, actualDesc.get());
    }

    @Test
    public void parseEventDate_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseEventDate(null);
    }

    @Test
    public void parseEventDate_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseEventDate(Optional.of(INVALID_EVENT_DATE));
    }

    @Test
    public void parseEventDate_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseEventDate(Optional.empty()).isPresent());
    }

    @Test
    public void parseEventDate_validValue_returnsEventDate() throws Exception {
        EventDate expectedEventDate = new EventDate(VALID_EVENT_DATE);
        Optional<EventDate> actualEventDate = ParserUtil.parseEventDate(Optional.of(VALID_EVENT_DATE));

        assertEquals(expectedEventDate, actualEventDate.get());
    }
}
