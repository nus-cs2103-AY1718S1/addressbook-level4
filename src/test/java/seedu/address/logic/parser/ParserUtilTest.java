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
import seedu.address.model.commandidentifier.CommandIdentifier;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.email.Email;
import seedu.address.model.schedule.Activity;
import seedu.address.model.schedule.ScheduleDate;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_COMMAND_IDENTIFIER = "evolve";
    private static final String INVALID_SCHEDULE_DATE_1 = "29-02-2017";
    private static final String INVALID_SCHEDULE_DATE_2 = "2017-02-02";
    private static final String INVALID_ACTIVITY = " ";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123, Main Street, #05-05, ML 404";
    private static final String VALID_EMAIL_1 = "rachel@example.com";
    private static final String VALID_EMAIL_2 = "rachel@gmail.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_COMMAND_IDENTIFIER_1 = "";
    private static final String VALID_COMMAND_IDENTIFIER_2 = "delete";
    private static final String VALID_COMMAND_IDENTIFIER_3 = "d";
    private static final String VALID_SCHEDULE_DATE_1 = "01-01-1997";
    private static final String VALID_SCHEDULE_DATE_2 = "01-01";
    private static final String VALID_ACTIVITY = "Team meeting";

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
        ParserUtil.parseEmails(null);
    }

    @Test
    public void parseEmail_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseEmails(Arrays.asList(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseEmails(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseEmail_collectionWithValidEmails_returnsEmailSet() throws Exception {
        Set<Email> actualEmailSet = ParserUtil.parseEmails(Arrays.asList(VALID_EMAIL_1, VALID_EMAIL_2));
        Set<Email> expectedEmailSet = new HashSet<>(Arrays.asList(new Email(VALID_EMAIL_1), new Email(VALID_EMAIL_2)));

        assertEquals(expectedEmailSet, actualEmailSet);
    }

    @Test
    public void parseScheduleDate_invalidValue_throwIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseScheduleDate(Optional.of(INVALID_SCHEDULE_DATE_1));
        ParserUtil.parseScheduleDate(Optional.of(INVALID_SCHEDULE_DATE_2));
    }

    @Test
    public void parseScheduleDate_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseScheduleDate(Optional.empty()).isPresent());
    }

    @Test
    public void parseScheduleDate_validValue_returnsScheduleDate() throws Exception {
        ScheduleDate expectedScheduleDate1 = new ScheduleDate(VALID_SCHEDULE_DATE_1);
        ScheduleDate expectedScheduleDate2 = new ScheduleDate(VALID_SCHEDULE_DATE_2);
        Optional<ScheduleDate> actualScheduleDate1 = ParserUtil.parseScheduleDate(Optional.of(VALID_SCHEDULE_DATE_1));
        Optional<ScheduleDate> actualScheduleDate2 = ParserUtil.parseScheduleDate(Optional.of(VALID_SCHEDULE_DATE_2));

        assertEquals(expectedScheduleDate1, actualScheduleDate1.get());
        assertEquals(expectedScheduleDate2, actualScheduleDate2.get());
    }

    @Test
    public void parseActivity_invalidValue_throwIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseActivity(Optional.of(INVALID_ACTIVITY));
    }

    @Test
    public void parseActivity_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseActivity(Optional.empty()).isPresent());
    }

    @Test
    public void parseActivity_validValue_returnsScheduleDate() throws Exception {
        Activity expectedActivity = new Activity(VALID_ACTIVITY);
        Optional<Activity> actualActivity = ParserUtil.parseActivity(Optional.of(VALID_ACTIVITY));

        assertEquals(expectedActivity, actualActivity.get());
    }

    @Test
    public void parseCommandIdentifier_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseCommandIdentifier(null);
    }

    @Test
    public void parseCommandIdentifier_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseCommandIdentifier(INVALID_COMMAND_IDENTIFIER);
    }

    @Test
    public void parseCommandIdentifier_emptyString_returnsCommandIdentifier() throws Exception {
        CommandIdentifier expectedCommandIdentifier = new CommandIdentifier(VALID_COMMAND_IDENTIFIER_1);
        CommandIdentifier actualCommandIdentifier = ParserUtil.parseCommandIdentifier(VALID_COMMAND_IDENTIFIER_1);

        assertEquals(expectedCommandIdentifier, actualCommandIdentifier);
    }

    @Test
    public void parseCommandIdentifier_commandWord_returnsCommandIdentifier() throws Exception {
        CommandIdentifier expectedCommandIdentifier = new CommandIdentifier(VALID_COMMAND_IDENTIFIER_2);
        CommandIdentifier actualCommandIdentifier = ParserUtil.parseCommandIdentifier(VALID_COMMAND_IDENTIFIER_2);

        assertEquals(expectedCommandIdentifier, actualCommandIdentifier);
    }

    @Test
    public void parseCommandIdentifier_commandAlias_returnsCommandIdentifier() throws Exception {
        CommandIdentifier expectedCommandIdentifier = new CommandIdentifier(VALID_COMMAND_IDENTIFIER_3);
        CommandIdentifier actualCommandIdentifier = ParserUtil.parseCommandIdentifier(VALID_COMMAND_IDENTIFIER_3);

        assertEquals(expectedCommandIdentifier, actualCommandIdentifier);
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
}
