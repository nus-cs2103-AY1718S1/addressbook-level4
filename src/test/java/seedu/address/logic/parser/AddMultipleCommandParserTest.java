package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BENSON;
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
import seedu.address.ui.GuiUnitTest;

//@author KhorSL
public class AddMultipleCommandParserTest extends GuiUnitTest {
    private AddMultipleCommandParser parser = new AddMultipleCommandParser();

    @Test
    public void parse_fileNotExist_failure() {
        final String fileNotExist = "./src/test/data/AddMultipleCommandSystemTest/doesNotExist.txt";
        String expectedMessage = String.format(AddMultipleCommand.MESSAGE_INVALID_FILE, fileNotExist);
        assertParseFailure(parser, fileNotExist, expectedMessage);
    }

    @Test
    public void parse_emptyInput_failure() {
        final String emptyInput = "";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMultipleCommand.MESSAGE_USAGE);
        assertParseFailure(parser, emptyInput, expectedMessage);
    }

    @Test
    public void parse_compulsoryFieldsPresent_success() {
        final String validPersonsFilepath = "./src/test/data/AddMultipleCommandSystemTest/validPersons.txt";
        ArrayList<ReadOnlyPerson> expectedPersonList = new ArrayList<>();

        expectedPersonList.add(ALICE);
        expectedPersonList.add(BENSON);
        expectedPersonList.add(CARL);
        expectedPersonList.add(HOON);
        expectedPersonList.add(AMY);
        expectedPersonList.add(BOB);

        assertParseSuccess(parser, validPersonsFilepath, new AddMultipleCommand(expectedPersonList));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        final String missingOptionalFilePath =
                "./src/test/data/AddMultipleCommandSystemTest/validPersons_missingOptionalFields.txt";
        ArrayList<ReadOnlyPerson> expectedPersonList = new ArrayList<>();

        ReadOnlyPerson amy = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags().build();
        ReadOnlyPerson bob = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags().build();

        expectedPersonList.add(amy);
        expectedPersonList.add(bob);

        assertParseSuccess(parser, missingOptionalFilePath, new AddMultipleCommand(expectedPersonList));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        final String missingFieldNameFilepath =
                "./src/test/data/AddMultipleCommandSystemTest/missingPrefix_name.txt";
        final String missingFieldPhoneFilepath =
                "./src/test/data/AddMultipleCommandSystemTest/missingPrefix_phone.txt";
        final String missingFieldEmailFilepath =
                "./src/test/data/AddMultipleCommandSystemTest/missingPrefix_email.txt";
        final String missingFieldAddressFilepath =
                "./src/test/data/AddMultipleCommandSystemTest/missingPrefix_address.txt";
        final String missingFieldCompulsoryFilepath =
                "./src/test/data/AddMultipleCommandSystemTest/missingPrefix_allCompulsory.txt";
        String expectedMessage = String.format(MESSAGE_INVALID_PERSON_FORMAT, AddMultipleCommand.MESSAGE_PERSON_FORMAT);

        // missing name prefix
        assertParseFailure(parser, missingFieldNameFilepath, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, missingFieldPhoneFilepath, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, missingFieldEmailFilepath, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, missingFieldAddressFilepath, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, missingFieldCompulsoryFilepath, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        final String invalidFieldNameFilepath = "./src/test/data/AddMultipleCommandSystemTest/invalidNameFormat.txt";
        final String invalidFieldPhoneFilepath = "./src/test/data/AddMultipleCommandSystemTest/invalidPhoneFormat.txt";
        final String invalidFieldEmailFilepath = "./src/test/data/AddMultipleCommandSystemTest/invalidEmailFormat.txt";
        final String invalidFieldAddressFilepath =
                "./src/test/data/AddMultipleCommandSystemTest/invalidAddressFormat.txt";
        final String invalidFieldTagFilepath = "./src/test/data/AddMultipleCommandSystemTest/invalidTagFormat.txt";
        final String invalidFieldNamePhoneFilepath =
                "./src/test/data/AddMultipleCommandSystemTest/invalidFormatNamePhone.txt";

        // invalid name
        assertParseFailure(parser, invalidFieldNameFilepath, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, invalidFieldPhoneFilepath, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, invalidFieldEmailFilepath, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, invalidFieldAddressFilepath, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, invalidFieldTagFilepath, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, invalidFieldNamePhoneFilepath, Name.MESSAGE_NAME_CONSTRAINTS);
    }
}
