package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDATE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NOT_FILLED;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHOTO_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHOTO_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHOTO_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHOTO_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIENDS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIENDS)
                .withBirthdate(VALID_BIRTHDATE_BOB).build();

        // multiple names - last name accepted - using command word
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_FRIEND + BIRTHDATE_DESC_BOB,
                new AddCommand(expectedPerson));

        // multiple names - last name accepted - using command alias
        assertParseSuccess(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_FRIEND + BIRTHDATE_DESC_BOB,
                new AddCommand(expectedPerson));

        // multiple phones - last phone accepted - using command word
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_FRIEND + BIRTHDATE_DESC_BOB,
                new AddCommand(expectedPerson));

        // multiple phones - last phone accepted - using command alias
        assertParseSuccess(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_FRIEND + BIRTHDATE_DESC_BOB,
                new AddCommand(expectedPerson));

        // multiple emails - last email accepted - using command word
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_FRIEND + BIRTHDATE_DESC_BOB,

                new AddCommand(expectedPerson));
        // multiple emails - last email accepted - using command alias
        assertParseSuccess(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_FRIEND + BIRTHDATE_DESC_BOB,
                new AddCommand(expectedPerson));

        // multiple addresses - last address accepted - using command word
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_FRIEND + BIRTHDATE_DESC_BOB,
                new AddCommand(expectedPerson));

        // multiple addresses - last address accepted - using command alias
        assertParseSuccess(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_FRIEND + BIRTHDATE_DESC_BOB,
                new AddCommand(expectedPerson));

        // multiple tags - all accepted - using command word
        Person expectedPersonMultipleTags = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withPhoto(VALID_PHOTO_BOB)
                .withTags(VALID_TAG_FRIENDS, VALID_TAG_HUSBAND).withBirthdate(VALID_BIRTHDATE_BOB).build();

        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND
                        + BIRTHDATE_DESC_BOB,

                new AddCommand(expectedPersonMultipleTags));

        // multiple tags - all accepted - using command alias
        assertParseSuccess(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND
                        + BIRTHDATE_DESC_BOB,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags - using command word
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withPhoto(VALID_PHOTO_AMY)
                .withBirthdate(VALID_BIRTHDATE_AMY).build();

        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + PHOTO_DESC_AMY + BIRTHDATE_DESC_AMY, new AddCommand(expectedPerson));

        // zero tags - using command alias
        assertParseSuccess(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + PHOTO_DESC_AMY + BIRTHDATE_DESC_AMY, new AddCommand(expectedPerson));

        // missing name prefix - using command word
        expectedPerson = new PersonBuilder().withName(NOT_FILLED).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withPhoto(VALID_PHOTO_AMY)
                .withBirthdate(VALID_BIRTHDATE_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + VALID_NAME_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + PHOTO_DESC_AMY + BIRTHDATE_DESC_AMY, new AddCommand(expectedPerson));

        // missing name prefix - using command alias
        expectedPerson = new PersonBuilder().withName(NOT_FILLED).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withPhoto(VALID_PHOTO_AMY)
                .withBirthdate(VALID_BIRTHDATE_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_ALIAS + VALID_NAME_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + PHOTO_DESC_AMY + BIRTHDATE_DESC_AMY, new AddCommand(expectedPerson));

        // missing phone - using command word
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(NOT_FILLED)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withPhoto(VALID_PHOTO_AMY).withTags()
                .withBirthdate(VALID_BIRTHDATE_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + PHOTO_DESC_AMY + BIRTHDATE_DESC_AMY, new AddCommand(expectedPerson));

        // missing phone - using command alias
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(NOT_FILLED)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withPhoto(VALID_PHOTO_AMY)
                .withBirthdate(VALID_BIRTHDATE_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + PHOTO_DESC_AMY + BIRTHDATE_DESC_AMY, new AddCommand(expectedPerson));

        // missing name - using command word
        expectedPerson = new PersonBuilder().withName(NOT_FILLED).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withPhoto(VALID_PHOTO_BOB)
                .withBirthdate(VALID_BIRTHDATE_BOB).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + BIRTHDATE_DESC_BOB, new AddCommand(expectedPerson));

        // missing name - using command alias
        expectedPerson = new PersonBuilder().withName(NOT_FILLED).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withPhoto(VALID_PHOTO_BOB)
                .withBirthdate(VALID_BIRTHDATE_BOB).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_ALIAS + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + BIRTHDATE_DESC_BOB, new AddCommand(expectedPerson));

        // missing address - using command word
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(NOT_FILLED).withPhoto(VALID_PHOTO_AMY)
                .withBirthdate(VALID_BIRTHDATE_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + PHOTO_DESC_AMY + BIRTHDATE_DESC_AMY, new AddCommand(expectedPerson));

        // missing address - using command alias
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(NOT_FILLED).withPhoto(VALID_PHOTO_AMY)
                .withBirthdate(VALID_BIRTHDATE_AMY).withTags().build();;
        assertParseSuccess(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + PHOTO_DESC_AMY + BIRTHDATE_DESC_AMY, new AddCommand(expectedPerson));

        // missing 2 fields (phone and address) - using command word
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(NOT_FILLED)
                .withEmail(VALID_EMAIL_AMY).withAddress(NOT_FILLED).withPhoto(VALID_PHOTO_AMY)
                .withBirthdate(VALID_BIRTHDATE_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY + PHOTO_DESC_AMY
                + BIRTHDATE_DESC_AMY, new AddCommand(expectedPerson));

        // missing 2 fields (phone and address) - using command alias
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(NOT_FILLED)
                .withEmail(VALID_EMAIL_AMY).withAddress(NOT_FILLED).withPhoto(VALID_PHOTO_AMY)
                .withBirthdate(VALID_BIRTHDATE_AMY).withTags().build();;
        assertParseSuccess(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_AMY + EMAIL_DESC_AMY + PHOTO_DESC_AMY
                        + BIRTHDATE_DESC_AMY,
                new AddCommand(expectedPerson));
    }


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing email prefix - using command word
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + VALID_EMAIL_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB, expectedMessage);

        // missing email prefix - using command alias
        assertParseFailure(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + PHONE_DESC_BOB
                + VALID_EMAIL_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB, expectedMessage);

        // all prefixes missing - using command word
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + VALID_PHONE_BOB
                + VALID_EMAIL_BOB + VALID_ADDRESS_BOB + VALID_PHOTO_BOB, expectedMessage);

        // all prefixes missing - using command alias
        assertParseFailure(parser, AddCommand.COMMAND_ALIAS + VALID_NAME_BOB + VALID_PHONE_BOB
                + VALID_EMAIL_BOB + VALID_ADDRESS_BOB + VALID_PHOTO_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name - using command word
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid name - using command alias
        assertParseFailure(parser, AddCommand.COMMAND_ALIAS + INVALID_NAME_DESC + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone - using command word
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + INVALID_PHONE_DESC
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid phone - using command alias
        assertParseFailure(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + INVALID_PHONE_DESC
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email - using command word
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                        + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB + PHOTO_DESC_BOB  + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid email - using command alias
        assertParseFailure(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + PHONE_DESC_BOB
                        + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address - using command word
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + INVALID_ADDRESS_DESC + PHOTO_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid address - using command alias
        assertParseFailure(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + INVALID_ADDRESS_DESC + PHOTO_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid tag - using command word
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIENDS,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        // invalid tag - using command alias
        assertParseFailure(parser, AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + PHOTO_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIENDS,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported - using command word
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC + PHOTO_DESC_BOB, Name.MESSAGE_NAME_CONSTRAINTS);

        // two invalid values, only first invalid value reported - using command alias
        assertParseFailure(parser, AddCommand.COMMAND_ALIAS + INVALID_NAME_DESC + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC + PHOTO_DESC_BOB , Name.MESSAGE_NAME_CONSTRAINTS);
    }

}
