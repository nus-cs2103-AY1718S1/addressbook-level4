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
import seedu.address.model.person.Grades;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "student/972 parent/97997";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_GRADES = "-123";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_REMARK = null;

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "student/97272031 parent/97979797";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_GRADES = "123.0";
    private static final String VALID_REMARK = "Top 25 percentile";
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

    //@@author lincredibleJC
    @Test
    public void parseGrades_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseGrades(null);
    }

    @Test
    public void parseGrades_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseGrades(Optional.of(INVALID_GRADES));
    }

    @Test
    public void parseGrades_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseGrades(Optional.empty()).isPresent());
    }

    @Test
    public void parseGrades_validValue_returnsGrade() throws Exception {
        Grades expectedGrades = new Grades(VALID_GRADES);
        Optional<Grades> actualGrades = ParserUtil.parseGrades(Optional.of(VALID_GRADES));

        assertEquals(expectedGrades, actualGrades.get());
    }
    //@@author

    @Test
    public void parseRemark_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseEmail(null);
    }

    //@@author nahtanojmil
    @Test
    public void parseRemark_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseRemark(Optional.of(INVALID_REMARK));
    }

    @Test
    public void parseRemark_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseRemark(Optional.empty()).isPresent());
    }

    @Test
    public void parseRemark_validValue_returnsEmail() throws Exception {
        Remark expectedRemark = new Remark(VALID_REMARK);
        Optional<Remark> actualRemark = ParserUtil.parseRemark(Optional.of(VALID_REMARK));

        assertEquals(expectedRemark, actualRemark.get());
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
    //@@author limcel
    @Test
    public void parseSchedule_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseSchedule(null);
    }

    @Test
    public void parseSchedule_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseSchedule(Optional.empty()).isPresent());
    }
    //@@author
}
