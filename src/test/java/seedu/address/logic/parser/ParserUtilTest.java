package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Avatar;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;
import seedu.address.model.task.Priority;
import seedu.address.model.task.TaskAddress;
import seedu.address.model.task.TaskName;

public class ParserUtilTest {
    private static final boolean PRIVATE_FIELD = true;

    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_REMARK = " ";
    private static final String INVALID_AVATAR = " ";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_TASK_NAME = " ";
    private static final String INVALID_DEADLINE = "The distant, distant past";
    private static final String INVALID_PRIORITY = "99";
    private static final String INVALID_TASK_ADDRESS = " ";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_REMARK = "Thinks that P=NP";
    private static final String VALID_AVATAR = "valid_file.png";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_TASK_NAME = "Launcher";
    private static final String VALID_DESCRIPTION = "An arbitrary decision";
    private static final String VALID_DEADLINE = "2 weeks later";
    private static final String VALID_PRIORITY = "3";
    private static final String VALID_TASK_ADDRESS = "NUS";

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
        Name expectedPrivateName = new Name(VALID_NAME, PRIVATE_FIELD);
        Optional<Name> actualPrivateName = ParserUtil.parseName(Optional.of(VALID_NAME));

        assertEquals(expectedName, actualName.get());
        assertEquals(expectedPrivateName, actualPrivateName.get());
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
        Phone expectedPrivatePhone = new Phone(VALID_PHONE, PRIVATE_FIELD);
        Optional<Phone> actualPrivatePhone = ParserUtil.parsePhone(Optional.of(VALID_PHONE));

        assertEquals(expectedPhone, actualPhone.get());
        assertEquals(expectedPrivatePhone, actualPrivatePhone.get());
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
        Address expectedPrivateAddress = new Address(VALID_ADDRESS, PRIVATE_FIELD);
        Optional<Address> actualPrivateAddress = ParserUtil.parseAddress(Optional.of(VALID_ADDRESS));

        assertEquals(expectedAddress, actualAddress.get());
        assertEquals(expectedPrivateAddress, actualPrivateAddress.get());
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
        Email expectedPrivateEmail = new Email(VALID_EMAIL, PRIVATE_FIELD);
        Optional<Email> actualPrivateEmail = ParserUtil.parseEmail(Optional.of(VALID_EMAIL));

        assertEquals(expectedEmail, actualEmail.get());
        assertEquals(expectedPrivateEmail, actualPrivateEmail.get());
    }

    //@@author Esilocke
    @Test
    public void parseRemark_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseRemark(null);
    }

    @Test
    public void parseRemark_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseRemark(Optional.of(INVALID_REMARK));
    }

    @Test
    public void parseRemark_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseRemark(Optional.empty()).isPresent());
    }

    @Test
    public void parseRemark_validValue_returnsRemark() throws Exception {
        Remark expectedRemark = new Remark(VALID_REMARK);
        Optional<Remark> actualRemark = ParserUtil.parseRemark(Optional.of(VALID_REMARK));
        Remark expectedPrivateRemark = new Remark(VALID_REMARK, PRIVATE_FIELD);
        Optional<Remark> actualPrivateRemark = ParserUtil.parseRemark(Optional.of(VALID_REMARK));

        assertEquals(expectedRemark, actualRemark.get());
        assertEquals(expectedPrivateRemark, actualPrivateRemark.get());
    }

    @Test
    public void parseAvatar_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseAvatar(null);
    }

    @Test
    public void parseAvatar_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseAvatar(Optional.of(INVALID_AVATAR));
    }

    @Test
    public void parseAvatar_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseAvatar(Optional.empty()).isPresent());
    }

    @Test
    public void parseAvatar_validValue_returnsAvatar() throws Exception {
        Avatar expectedAvatar = new Avatar(VALID_AVATAR);
        Optional<Avatar> actualAvatar = ParserUtil.parseAvatar(Optional.of(VALID_AVATAR));

        assertEquals(expectedAvatar, actualAvatar.get());
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
        Set<Tag> expectedTagSet = new HashSet<>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseTaskName_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTaskName(null);
    }

    @Test
    public void parseTaskName_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTaskName(Optional.of(INVALID_TASK_NAME));
    }

    @Test
    public void parseTaskName_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseTaskName(Optional.empty()).isPresent());
    }

    @Test
    public void parseTaskName_validValue_returnsTaskName() throws Exception {
        TaskName expectedTaskName = new TaskName(VALID_TASK_NAME);
        Optional<TaskName> actualTaskName = ParserUtil.parseTaskName(Optional.of(VALID_TASK_NAME));

        assertEquals(expectedTaskName, actualTaskName.get());
    }

    @Test
    public void parseDescription_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseDescription(null);
    }

    @Test
    public void parseDescription_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseDescription(Optional.empty()).isPresent());
    }

    @Test
    public void parseDescription_validValue_returnsDescription() throws Exception {
        Description expectedDescription = new Description(VALID_DESCRIPTION);
        Optional<Description> actualDescription = ParserUtil.parseDescription(Optional.of(VALID_DESCRIPTION));

        assertEquals(expectedDescription, actualDescription.get());
    }

    @Test
    public void parseDeadline_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseDeadline(null);
    }

    @Test
    public void parseDeadline_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseDeadline(Optional.of(INVALID_DEADLINE));
    }

    @Test
    public void parseDeadline_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseDeadline(Optional.empty()).isPresent());
    }

    @Test
    public void parseDeadline_validValue_returnsDeadline() throws Exception {
        Deadline expectedDeadline = new Deadline(VALID_DEADLINE);
        Optional<Deadline> actualDeadline = ParserUtil.parseDeadline(Optional.of(VALID_DEADLINE));

        assertEquals(expectedDeadline, actualDeadline.get());
    }

    @Test
    public void parsePriority_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parsePriority(null);
    }

    @Test
    public void parsePriority_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parsePriority(Optional.of(INVALID_PRIORITY));
    }

    @Test
    public void parsePriority_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parsePriority(Optional.empty()).isPresent());
    }

    @Test
    public void parsePriority_validValue_returnsPriority() throws Exception {
        Priority expectedPriority = new Priority(VALID_PRIORITY);
        Optional<Priority> actualPriority = ParserUtil.parsePriority(Optional.of(VALID_PRIORITY));

        assertEquals(expectedPriority, actualPriority.get());
    }

    @Test
    public void parseTaskAddress_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTaskAddress(null);
    }

    @Test
    public void parseTaskAddress_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTaskAddress(Optional.of(INVALID_TASK_ADDRESS));
    }

    @Test
    public void parseTaskAddress_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseTaskAddress(Optional.empty()).isPresent());
    }

    @Test
    public void parseTaskAddress_validValue_returnsTaskAddress() throws Exception {
        TaskAddress expectedTaskAddress = new TaskAddress(VALID_TASK_ADDRESS);
        Optional<TaskAddress> actualTaskAddress = ParserUtil.parseTaskAddress(Optional.of(VALID_TASK_ADDRESS));

        assertEquals(expectedTaskAddress, actualTaskAddress.get());
    }

    @Test
    public void parseIndexes_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseIndexes(null);
    }

    @Test
    public void parseIndexes_invalidValue_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIndexes("1 2 a b c");
    }

    @Test
    public void parseIndexes_validValue_returnsIndexes() throws Exception {
        ArrayList<Index> expectedIndexes = new ArrayList<>(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON,
                INDEX_THIRD_PERSON));

        assertEquals(expectedIndexes, ParserUtil.parseIndexes("1 2 3"));
        assertEquals(expectedIndexes, ParserUtil.parseIndexes("     1   2    3   "));
    }
}
