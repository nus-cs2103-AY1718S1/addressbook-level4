package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDAY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDAY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BIRTHDAY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODULE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_DESC_GER1000;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_GER1000;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.module.Module;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB)
                .withPhones(VALID_PHONE_AMY, VALID_PHONE_BOB).withBirthday(VALID_BIRTHDAY_BOB)
                .withEmails(VALID_EMAIL_AMY, VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withModules(VALID_MODULE_CS2101).build();

        // multiple names - last name accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_AMY
                + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + MODULE_DESC_CS2101, new AddCommand(expectedPerson));

        // multiple phones - multiple phones accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_AMY
                + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + MODULE_DESC_CS2101, new AddCommand(expectedPerson));

        // multiple emails - multiple emails accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + BIRTHDAY_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_AMY + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + MODULE_DESC_CS2101, new AddCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + BIRTHDAY_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB
                        + MODULE_DESC_CS2101, new AddCommand(expectedPerson));

        //@@author viviantan95
        // multiple birthdays - last birthday accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + BIRTHDAY_DESC_AMY + BIRTHDAY_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + MODULE_DESC_CS2101, new AddCommand(expectedPerson));
        //@@author

        // multiple mods - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder().withName(VALID_NAME_BOB)
                .withPhones(VALID_PHONE_AMY, VALID_PHONE_BOB).withBirthday(VALID_BIRTHDAY_BOB)
                .withEmails(VALID_EMAIL_AMY, VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withModules(VALID_MODULE_CS2101, VALID_MODULE_GER1000).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + BIRTHDAY_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + MODULE_DESC_GER1000
                        + MODULE_DESC_CS2101, new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero mods
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhones(VALID_PHONE_AMY)
                .withBirthday(VALID_BIRTHDAY_AMY).withEmails(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withModules().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + PHONE_DESC_BOB
                + BIRTHDAY_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + VALID_PHONE_BOB
                + BIRTHDAY_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + BIRTHDAY_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + BIRTHDAY_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB, expectedMessage);

        //@@author viviantan95
        // missing birthday prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + VALID_BIRTHDAY_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB, expectedMessage);
        //@@author

        // all prefixes missing
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + VALID_PHONE_BOB
                + VALID_BIRTHDAY_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB
                + BIRTHDAY_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + MODULE_DESC_GER1000
                + MODULE_DESC_CS2101, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + INVALID_PHONE_DESC
                + BIRTHDAY_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + MODULE_DESC_GER1000
                + MODULE_DESC_CS2101, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB + MODULE_DESC_GER1000
                + MODULE_DESC_CS2101, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC + MODULE_DESC_GER1000
                + MODULE_DESC_CS2101, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        //@@author viviantan95
        // invalid birthday
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + INVALID_BIRTHDAY_DESC + MODULE_DESC_GER1000
                        + MODULE_DESC_CS2101, Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);
        //@@author

        // invalid module
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + INVALID_MODULE_DESC
                + VALID_MODULE_CS2101, Module.MESSAGE_MODULE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC, Name.MESSAGE_NAME_CONSTRAINTS);
    }
}
