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
import seedu.address.model.mod.Mod;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_MOD = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE_1 = "123456";
    private static final String VALID_PHONE_2 = "987654";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL_1 = "rachel@example.com";
    private static final String VALID_EMAIL_2 = "rwalker@example.com";
    private static final String VALID_MOD_1 = "friend";
    private static final String VALID_MOD_2 = "neighbour";

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
    public void parsePhones_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parsePhones(null);
    }

    @Test
    public void parsePhones_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parsePhones(Arrays.asList(INVALID_PHONE, VALID_PHONE_1));
    }

    @Test
    public void parsePhones_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parsePhones(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parsePhones_collectionWithValidPhones_returnsPhoneSet() throws Exception {
        Set<Phone> actualPhoneSet = ParserUtil.parsePhones(Arrays.asList(VALID_PHONE_1, VALID_PHONE_2));
        Set<Phone> expectedPhoneSet = new HashSet<>(Arrays.asList(new Phone(VALID_PHONE_1), new Phone(VALID_PHONE_2)));

        assertEquals(expectedPhoneSet, actualPhoneSet);
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
    public void parseEmails_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseEmails(null);
    }

    @Test
    public void parseEmails_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseEmails(Arrays.asList(INVALID_EMAIL, VALID_EMAIL_1));
    }

    @Test
    public void parseEmails_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseEmails(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseEmails_collectionWithValidEmails_returnsEmailSet() throws Exception {
        Set<Email> actualEmailSet = ParserUtil.parseEmails(Arrays.asList(VALID_EMAIL_1, VALID_EMAIL_2));
        Set<Email> expectedEmailSet = new HashSet<>(Arrays.asList(new Email(VALID_EMAIL_1), new Email(VALID_EMAIL_2)));

        assertEquals(expectedEmailSet, actualEmailSet);
    }

    @Test
    public void parseTags_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseMods(null);
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseMods(Arrays.asList(VALID_MOD_1, INVALID_MOD));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseMods(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Mod> actualTagSet = ParserUtil.parseMods(Arrays.asList(VALID_MOD_1, VALID_MOD_2));
        Set<Mod> expectedTagSet = new HashSet<Mod>(Arrays.asList(new Mod(VALID_MOD_1), new Mod(VALID_MOD_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }
}
