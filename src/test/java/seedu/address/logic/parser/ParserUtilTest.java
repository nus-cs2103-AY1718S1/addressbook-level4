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
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Debt;
import seedu.address.model.person.Email;
import seedu.address.model.person.Handphone;
import seedu.address.model.person.HomePhone;
import seedu.address.model.person.Interest;
import seedu.address.model.person.Name;
import seedu.address.model.person.OfficePhone;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PostalCode;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_POSTAL_CODE = "00111";
    private static final String INVALID_DEBT = "1234a";
    private static final String INVALID_INTEREST = "one";
    private static final String INVALID_DEADLINE = "0-0-2017";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "61123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_POSTAL_CODE = "321123";
    private static final String VALID_DEBT = "1234";
    private static final String VALID_INTEREST = "1";
    private static final String VALID_DEADLINE = "11-11-2020";
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
    public void parseHandphone_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseHandphone(null);
    }

    @Test
    public void parseHandphone_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseHandphone(Optional.of(INVALID_PHONE));
    }

    @Test
    public void parseHandphone_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseHandphone(Optional.empty()).isPresent());
    }

    @Test
    public void parseHandphone_validValue_returnsPhone() throws Exception {
        Phone expectedPhone = new Handphone(VALID_PHONE);
        Optional<Handphone> actualPhone = ParserUtil.parseHandphone(Optional.of(VALID_PHONE));

        assertEquals(expectedPhone, actualPhone.get());
    }

    @Test
    public void parseHomePhone_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseHomePhone(null);
    }

    @Test
    public void parseHomePhone_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseHomePhone(Optional.of(INVALID_PHONE));
    }

    @Test
    public void parseHomePhone_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseHomePhone(Optional.empty()).isPresent());
    }

    @Test
    public void parseHomePhone_validValue_returnsPhone() throws Exception {
        Phone expectedPhone = new HomePhone(VALID_PHONE);
        Optional<HomePhone> actualPhone = ParserUtil.parseHomePhone(Optional.of(VALID_PHONE));

        assertEquals(expectedPhone, actualPhone.get());
    }

    @Test
    public void parseOfficePhone_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseOfficePhone(null);
    }

    @Test
    public void parseOfficePhone_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseOfficePhone(Optional.of(INVALID_PHONE));
    }

    @Test
    public void parseOfficePhone_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseOfficePhoneForEdit(Optional.empty()).isPresent());
    }

    @Test
    public void parseOfficePhone_validValue_returnsPhone() throws Exception {
        Phone expectedPhone = new OfficePhone(VALID_PHONE);
        Optional<OfficePhone> actualPhone = ParserUtil.parseOfficePhone(Optional.of(VALID_PHONE));

        assertEquals(expectedPhone, actualPhone.get());
    }

    @Test
    public void parseDebt_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseDebt(null);
    }

    @Test
    public void parseDebt_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseDebt(Optional.of(INVALID_DEBT));
    }

    @Test
    public void parseDebt_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseDebt(Optional.empty()).isPresent());
    }

    @Test
    public void parseDebt_validValue_returnsDebt() throws Exception {
        Debt expectedDebt = new Debt(VALID_DEBT);
        Optional<Debt> actualDebt = ParserUtil.parseDebt(Optional.of(VALID_DEBT));

        assertEquals(expectedDebt, actualDebt.get());
    }

    @Test
    public void parseInterest_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseInterest(null);
    }

    @Test
    public void parseInterest_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseInterest(Optional.of(INVALID_INTEREST));
    }

    @Test
    public void parseInterestForEdit_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseInterestForEdit(Optional.empty()).isPresent());
    }

    @Test
    public void parseInterest_validValue_returnsInterest() throws Exception {
        Interest expectedInterest = new Interest(VALID_INTEREST);
        Optional<Interest> actualInterest = ParserUtil.parseInterest(Optional.of(VALID_INTEREST));

        assertEquals(expectedInterest, actualInterest.get());
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
    public void parsePostalCode_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parsePostalCode(null);
    }

    @Test
    public void parsePostalCode_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parsePostalCode(Optional.of(INVALID_POSTAL_CODE));
    }

    @Test
    public void parsePostalCode_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parsePostalCode(Optional.empty()).isPresent());
    }

    @Test
    public void parsePostalCode_validValue_returnsPostalCode() throws Exception {
        PostalCode expectedPostalCode = new PostalCode(VALID_POSTAL_CODE);
        Optional<PostalCode> actualPostalCode = ParserUtil.parsePostalCode(Optional.of(VALID_POSTAL_CODE));

        assertEquals(expectedPostalCode, actualPostalCode.get());
    }

    @Test
    public void parseDeadlineForEdit_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseDeadlineForEdit(null);
    }

    @Test
    public void parseDeadline_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseDeadline(Optional.of(INVALID_DEADLINE));
    }

    @Test
    public void parseDeadlineForEdit_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseDeadlineForEdit(Optional.empty()).isPresent());
    }

    @Test
    public void parseDeadline_validValue_returnsDeadLine() throws Exception {
        Deadline expectedDeadline = new Deadline(VALID_DEADLINE);
        Optional<Deadline> actualDeadline = ParserUtil.parseDeadline(Optional.of(VALID_DEADLINE));

        assertEquals(expectedDeadline, actualDeadline.get());
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
