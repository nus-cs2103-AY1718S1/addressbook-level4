package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.HOON;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.logic.commands.AddMultipleCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddMultipleCommandParserTest {
    private AddMultipleCommandParser parser = new AddMultipleCommandParser();

    @Test
    public void parse_fileNotExist_failure() {
        final String FILE_NOT_EXIST = "./src/test/data/AddMultipleCommandSystemTest/doesNotExist.txt";
        String expectedMessage = String.format(AddMultipleCommand.MESSAGE_INVALID_FILE, FILE_NOT_EXIST);
        assertParseFailure(parser, FILE_NOT_EXIST, expectedMessage);
    }

    @Test
    public void parse_emptyInput_failure() {
        final String EMPTY_INPUT = "";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMultipleCommand.MESSAGE_USAGE);
        assertParseFailure(parser, EMPTY_INPUT, expectedMessage);
    }

    @Test
    public void parse_compulsoryFieldsPresent_success() {
        final String VALID_PERSONS_FILEPATH = "./src/test/data/AddMultipleCommandSystemTest/validPersons.txt";
        ArrayList<ReadOnlyPerson> expectedPersonList = new ArrayList<>();

        expectedPersonList.add(ALICE);
        expectedPersonList.add(BENSON);
        expectedPersonList.add(CARL);
        expectedPersonList.add(HOON);
        expectedPersonList.add(AMY);
        expectedPersonList.add(BOB);

        assertParseSuccess(parser, VALID_PERSONS_FILEPATH, new AddMultipleCommand(expectedPersonList));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        final String MISSING_OPTIONAL_FILE_PATH = "./src/test/data/AddMultipleCommandSystemTest/validPersons_missingOptionalFields.txt";
        ArrayList<ReadOnlyPerson> expectedPersonList = new ArrayList<>();

        ReadOnlyPerson AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags().build();
        ReadOnlyPerson BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags().build();

        expectedPersonList.add(AMY);
        expectedPersonList.add(BOB);

        assertParseSuccess(parser, MISSING_OPTIONAL_FILE_PATH, new AddMultipleCommand(expectedPersonList));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        final String MISSING_FIELD_NAME_FILEPATH = "./src/test/data/AddMultipleCommandSystemTest/missingPrefix_name.txt";
        final String MISSING_FIELD_PHONE_FILEPATH = "./src/test/data/AddMultipleCommandSystemTest/missingPrefix_phone.txt";
        final String MISSING_FIELD_EMAIL_FILEPATH = "./src/test/data/AddMultipleCommandSystemTest/missingPrefix_email.txt";
        final String MISSING_FIELD_ADDRESS_FILEPATH = "./src/test/data/AddMultipleCommandSystemTest/missingPrefix_address.txt";
        final String MISSING_FIELD_COMPULSORY_FILEPATH = "./src/test/data/AddMultipleCommandSystemTest/missingPrefix_allCompulsory.txt";
        String expectedMessage = String.format(MESSAGE_INVALID_PERSON_FORMAT, AddMultipleCommand.MESSAGE_PERSON_FORMAT);

        // missing name prefix
        assertParseFailure(parser, MISSING_FIELD_NAME_FILEPATH, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, MISSING_FIELD_PHONE_FILEPATH, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, MISSING_FIELD_EMAIL_FILEPATH, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, MISSING_FIELD_ADDRESS_FILEPATH, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, MISSING_FIELD_COMPULSORY_FILEPATH, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        final String INVALID_FIELD_NAME_FILEPATH = "./src/test/data/AddMultipleCommandSystemTest/invalidNameFormat.txt";
        final String INVALID_FIELD_PHONE_FILEPATH = "./src/test/data/AddMultipleCommandSystemTest/invalidPhoneFormat.txt";
        final String INVALID_FIELD_EMAIL_FILEPATH = "./src/test/data/AddMultipleCommandSystemTest/invalidEmailFormat.txt";
        final String INVALID_FIELD_ADDRESS_FILEPATH = "./src/test/data/AddMultipleCommandSystemTest/invalidAddressFormat.txt";
        final String INVALID_FIELD_TAG_FILEPATH = "./src/test/data/AddMultipleCommandSystemTest/invalidTagFormat.txt";
        final String INVALID_FIELD_NAME_PHONE_FILEPATH = "./src/test/data/AddMultipleCommandSystemTest/invalidFormatNamePhone.txt";

        // invalid name
        assertParseFailure(parser, INVALID_FIELD_NAME_FILEPATH, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, INVALID_FIELD_PHONE_FILEPATH, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, INVALID_FIELD_EMAIL_FILEPATH, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, INVALID_FIELD_ADDRESS_FILEPATH, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, INVALID_FIELD_TAG_FILEPATH, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_FIELD_NAME_PHONE_FILEPATH, Name.MESSAGE_NAME_CONSTRAINTS);
    }
}
