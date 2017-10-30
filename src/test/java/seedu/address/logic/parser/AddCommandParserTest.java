package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDAY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDAY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.HOME_NUM_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.HOME_NUM_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BIRTHDAY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HOME_NUM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SCH_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WEBSITE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SCH_EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SCH_EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOME_NUM_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOME_NUM_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCH_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCH_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEBSITE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEBSITE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.WEBSITE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.WEBSITE_DESC_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.HomeNumber;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.SchEmail;
import seedu.address.model.person.Website;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withHomeNumber(VALID_HOME_NUM_BOB).withEmail(VALID_EMAIL_BOB).withSchEmail(VALID_SCH_EMAIL_BOB)
                .withWebsite(VALID_WEBSITE_BOB).withAddress(VALID_ADDRESS_BOB).withBirthday(VALID_BIRTHDAY_BOB)
                .withTags(VALID_TAG_FRIEND).build();

        // multiple names - last name accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                + HOME_NUM_DESC_BOB + EMAIL_DESC_BOB + SCH_EMAIL_DESC_BOB + WEBSITE_DESC_BOB
                + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + HOME_NUM_DESC_BOB + EMAIL_DESC_BOB + SCH_EMAIL_DESC_BOB + WEBSITE_DESC_BOB
                + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple home number - last home number accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + HOME_NUM_DESC_AMY + HOME_NUM_DESC_BOB + EMAIL_DESC_BOB + SCH_EMAIL_DESC_BOB + WEBSITE_DESC_BOB
                + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
                + HOME_NUM_DESC_BOB + EMAIL_DESC_BOB + SCH_EMAIL_DESC_BOB + WEBSITE_DESC_BOB
                + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple school emails - last school email accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + HOME_NUM_DESC_BOB + EMAIL_DESC_BOB + SCH_EMAIL_DESC_AMY + SCH_EMAIL_DESC_BOB
                + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + HOME_NUM_DESC_BOB + EMAIL_DESC_BOB + SCH_EMAIL_DESC_BOB
                + WEBSITE_DESC_BOB + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withHomeNumber(VALID_HOME_NUM_BOB).withEmail(VALID_EMAIL_BOB).withSchEmail(VALID_SCH_EMAIL_BOB)
                .withWebsite(VALID_WEBSITE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                        + HOME_NUM_DESC_BOB + EMAIL_DESC_BOB + SCH_EMAIL_DESC_BOB
                        + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withHomeNumber(VALID_HOME_NUM_AMY).withEmail(VALID_EMAIL_AMY).withSchEmail(VALID_SCH_EMAIL_AMY)
                .withWebsite(VALID_WEBSITE_AMY).withAddress(VALID_ADDRESS_AMY)
                .withBirthday(VALID_BIRTHDAY_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + HOME_NUM_DESC_AMY + EMAIL_DESC_AMY + SCH_EMAIL_DESC_AMY
                + WEBSITE_DESC_AMY + ADDRESS_DESC_AMY + BIRTHDAY_DESC_AMY, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + PHONE_DESC_BOB
                + HOME_NUM_DESC_BOB + EMAIL_DESC_BOB + SCH_EMAIL_DESC_BOB + WEBSITE_DESC_BOB
                + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + VALID_PHONE_BOB
                + HOME_NUM_DESC_BOB + EMAIL_DESC_BOB + SCH_EMAIL_DESC_BOB + WEBSITE_DESC_BOB
                + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + HOME_NUM_DESC_BOB + VALID_EMAIL_BOB + SCH_EMAIL_DESC_BOB
                + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB, expectedMessage);

        // creates a person with no school email
        Person expectedPersonWithNoSchEmail = new PersonBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withSchEmail(SchEmail.SCH_EMAIL_TEMPORARY)
                .withHomeNumber(VALID_HOME_NUM_BOB).withEmail(VALID_EMAIL_BOB)
                .withWebsite(VALID_WEBSITE_BOB).withAddress(VALID_ADDRESS_BOB).withBirthday(VALID_BIRTHDAY_BOB)
                .build();

        // missing schEmail prefix
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + HOME_NUM_DESC_BOB + EMAIL_DESC_BOB
                + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB,
                new AddCommand(expectedPersonWithNoSchEmail));

        // creates a person with no homeNumber
        Person expectedPersonWithNoHomeNumber = new PersonBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withSchEmail(VALID_SCH_EMAIL_BOB)
                .withHomeNumber(HomeNumber.HOME_NUMBER_TEMPORARY).withEmail(VALID_EMAIL_BOB)
                .withWebsite(VALID_WEBSITE_BOB).withAddress(VALID_ADDRESS_BOB).withBirthday(VALID_BIRTHDAY_BOB)
                .build();

        // missing homeNumber prefix
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + SCH_EMAIL_DESC_BOB
                + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB,
                new AddCommand(expectedPersonWithNoHomeNumber));

        // creates a person with no birthday
        Person expectedPersonWithNoBirthday = new PersonBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withSchEmail(VALID_SCH_EMAIL_BOB)
                .withHomeNumber(VALID_HOME_NUM_BOB).withEmail(VALID_EMAIL_BOB)
                .withWebsite(VALID_WEBSITE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withBirthday(Birthday.BIRTHDAY_TEMPORARY)
                .build();

        // missing birthday prefix
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB
                        + PHONE_DESC_BOB + HOME_NUM_DESC_BOB
                        + EMAIL_DESC_BOB + SCH_EMAIL_DESC_BOB
                        + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB,
                new AddCommand(expectedPersonWithNoBirthday));

        // creates a person with no website
        Person expectedPersonWithNoWebsite = new PersonBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withSchEmail(VALID_SCH_EMAIL_BOB)
                .withHomeNumber(VALID_HOME_NUM_BOB).withEmail(VALID_EMAIL_BOB)
                .withWebsite(Website.WEBSITE_TEMPORARY).withAddress(VALID_ADDRESS_BOB)
                .withBirthday(VALID_BIRTHDAY_BOB)
                .build();

        // missing website prefix
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + HOME_NUM_DESC_BOB + EMAIL_DESC_BOB + SCH_EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB,
                new AddCommand(expectedPersonWithNoWebsite));

        // creates a person with no address
        Person expectedPersonWithNoAddress = new PersonBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withSchEmail(VALID_SCH_EMAIL_BOB)
                .withHomeNumber(VALID_HOME_NUM_BOB).withEmail(VALID_EMAIL_BOB)
                .withWebsite(VALID_WEBSITE_BOB).withAddress(Address.ADDRESS_TEMPORARY)
                .withBirthday(VALID_BIRTHDAY_BOB)
                .build();

        // missing address prefix
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + HOME_NUM_DESC_BOB + EMAIL_DESC_BOB + SCH_EMAIL_DESC_BOB
                + WEBSITE_DESC_BOB + BIRTHDAY_DESC_BOB,
                new AddCommand(expectedPersonWithNoAddress));

        // all prefixes missing
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + VALID_PHONE_BOB
                + VALID_EMAIL_BOB + VALID_SCH_EMAIL_BOB + VALID_WEBSITE_BOB
                + VALID_ADDRESS_BOB + VALID_BIRTHDAY_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB
                + HOME_NUM_DESC_BOB + EMAIL_DESC_BOB + SCH_EMAIL_DESC_BOB
                + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + INVALID_PHONE_DESC
                + HOME_NUM_DESC_BOB + EMAIL_DESC_BOB + SCH_EMAIL_DESC_BOB
                + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid home number
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + INVALID_HOME_NUM_DESC + EMAIL_DESC_BOB + SCH_EMAIL_DESC_BOB
                + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, HomeNumber.MESSAGE_HOME_NUMBER_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + HOME_NUM_DESC_BOB + INVALID_EMAIL_DESC + SCH_EMAIL_DESC_BOB
                + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid school email
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + HOME_NUM_DESC_BOB + EMAIL_DESC_BOB + INVALID_SCH_EMAIL_DESC
                + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, SchEmail.MESSAGE_SCH_EMAIL_CONSTRAINTS);

        // invalid website
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + HOME_NUM_DESC_BOB + EMAIL_DESC_BOB + SCH_EMAIL_DESC_BOB
                + INVALID_WEBSITE_DESC + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Website.MESSAGE_WEBSITE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + HOME_NUM_DESC_BOB + EMAIL_DESC_BOB + SCH_EMAIL_DESC_BOB
                + WEBSITE_DESC_BOB + INVALID_ADDRESS_DESC + BIRTHDAY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        //invalid birthday
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                        + HOME_NUM_DESC_BOB + EMAIL_DESC_BOB + SCH_EMAIL_DESC_BOB + WEBSITE_DESC_BOB
                        + ADDRESS_DESC_BOB + INVALID_BIRTHDAY_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + HOME_NUM_DESC_BOB + EMAIL_DESC_BOB + SCH_EMAIL_DESC_BOB + WEBSITE_DESC_BOB
                + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB
                + HOME_NUM_DESC_BOB + EMAIL_DESC_BOB + SCH_EMAIL_DESC_BOB
                + WEBSITE_DESC_BOB + INVALID_ADDRESS_DESC + BIRTHDAY_DESC_BOB, Name.MESSAGE_NAME_CONSTRAINTS);
    }
}
