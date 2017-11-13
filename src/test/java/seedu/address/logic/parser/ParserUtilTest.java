package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.InternalId;
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
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    //@@author Sri-vatsa
    private static final String INVALID_DATE = "22/12/198";
    private static final String INVALID_TIME = "+5";
    private static final String INVALID_LOCATION = " ";
    private static final String INVALID_NOTES = " ";
    private static final String INVALID_PERSON = "#1";
    private static final String INVALID_FORMAT_ACCESSCODE = "Gibberish";

    private static final String VALID_DATE = "27/11/2020";
    private static final String VALID_TIME = "1800";
    private static final String VALID_LOCATION = "UTown";
    private static final String VALID_NOTES = "Meeting";
    private static final String VALID_PERSON = "1";
    private static final String VALID_FORMAT_ACCESSCODE = "0/1g23j765kl985";

    //@@author
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
    //@@author Sri-vatsa
    @Test
    public void parseLocation_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseLocation(null);
    }

    @Test
    public void parseLocation_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseLocation(Optional.of(INVALID_LOCATION));
    }

    @Test
    public void parseLocation_validValue_returnsLocation() throws Exception {

        Optional<String> actualLocation = ParserUtil.parseLocation(Optional.of(VALID_LOCATION));

        assertEquals(VALID_LOCATION, actualLocation.get());
    }

    @Test
    public void parseNotes_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseNotes(null);
    }

    @Test
    public void parseNotes_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseNotes(Optional.of(INVALID_NOTES));
    }

    @Test
    public void parseNotes_validValue_returnsEmail() throws Exception {

        Optional<String> actualNotes = ParserUtil.parseLocation(Optional.of(VALID_NOTES));

        assertEquals(VALID_NOTES, actualNotes.get());
    }

    @Test
    public void parseDate_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseDate(null);
    }

    @Test
    public void parseDate_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseDate(Optional.empty()).isPresent());
    }

    @Test
    public void parseDate_validValue_returnsDate() throws Exception {

        Optional<String> actualDate = ParserUtil.parseDate(Optional.of(VALID_DATE));

        assertEquals(VALID_DATE, actualDate.get());
    }
    @Test
    public void parseTime_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTime(null);
    }

    @Test
    public void parseTime_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseTime(Optional.empty()).isPresent());
    }

    @Test
    public void parseTime_validValue_returnsTime() throws Exception {

        Optional<String> actualTime = ParserUtil.parseTime(Optional.of(VALID_TIME));

        assertEquals(VALID_TIME, actualTime.get());
    }

    @Test
    public void parseDateTime_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseDateTime(null, null);
    }

    @Test
    public void parseDateTime_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseDateTime(INVALID_DATE, INVALID_TIME);
    }

    @Test
    public void parseDateTime_validValue_returnsDateTime() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm");
        String dateTime = VALID_DATE + " " + VALID_TIME;
        LocalDateTime localDateTimeExpected = LocalDateTime.parse(dateTime, formatter);

        LocalDateTime localDateTimeActual = ParserUtil.parseDateTime(VALID_DATE, VALID_TIME);

        assertEquals(localDateTimeExpected, localDateTimeActual);
    }

    @Test
    public void parseIds_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseIds(null);
    }

    @Test
    public void parseIds_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ArrayList<String> ids = new ArrayList<>();
        ids.add(INVALID_PERSON);
        ParserUtil.parseIds(ids);
    }

    @Test
    public void parseIds_validValue_returnsIds() throws Exception {
        //expected data
        ArrayList<InternalId> idsExpected = new ArrayList<>();
        idsExpected.add(new InternalId(Integer.parseInt(VALID_PERSON)));

        //actual data
        ArrayList<String> idsActual = new ArrayList<>();
        idsActual.add(VALID_PERSON);

        ArrayList<InternalId> actualIds = ParserUtil.parseIds(idsActual);

        assertEquals(idsExpected, actualIds);
    }

    @Test
    public void parseAccessCode_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseAccessCode(null);
    }

    @Test
    public void parseAccessCode_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseAccessCode(INVALID_FORMAT_ACCESSCODE);
    }

    @Test
    public void parseAccessCode_validValue_returnsAccessCode() throws Exception {

        String actualAccessCode = ParserUtil.parseAccessCode(VALID_FORMAT_ACCESSCODE);

        assertEquals(VALID_FORMAT_ACCESSCODE, actualAccessCode);
    }

}
