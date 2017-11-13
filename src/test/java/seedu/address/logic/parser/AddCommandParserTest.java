package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DEFAULT_UNDEFINED;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.AVATAR_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.AVATAR_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DEFAULT_UNDEFINED;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AVATAR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NOK_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NOK_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NOK_NAME_DEFAULT_UNDEFINED;
import static seedu.address.logic.commands.CommandTestUtil.NOK_NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NOK_NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NOK_PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NOK_PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVATAR_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVATAR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOK_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOK_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOK_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOK_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Avatar;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.NokName;
import seedu.address.model.person.NokPhone;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withAvatar(VALID_AVATAR_BOB)
                           .withNokName(VALID_NOK_NAME_BOB).withNokPhone(VALID_NOK_PHONE_BOB)
                           .withTags(VALID_TAG_FRIEND).build();

        // multiple names - last name accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + AVATAR_DESC_BOB + NOK_NAME_DESC_BOB + NOK_PHONE_DESC_BOB
                        + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + AVATAR_DESC_BOB + NOK_NAME_DESC_BOB + NOK_PHONE_DESC_BOB
                        + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + AVATAR_DESC_BOB + NOK_NAME_DESC_BOB + NOK_PHONE_DESC_BOB
                        + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + AVATAR_DESC_BOB + NOK_NAME_DESC_BOB + NOK_PHONE_DESC_BOB
                        + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple avatars - last avatar accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + AVATAR_DESC_AMY + AVATAR_DESC_BOB + NOK_NAME_DESC_BOB + NOK_PHONE_DESC_BOB
                        + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple NOK names - last NOK name accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + AVATAR_DESC_BOB + NOK_NAME_DESC_AMY + NOK_NAME_DESC_BOB
                        + NOK_PHONE_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple NOK phones - last NOK phone accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + AVATAR_DESC_BOB + NOK_NAME_DESC_BOB + NOK_PHONE_DESC_AMY
                        + NOK_PHONE_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withAvatar(VALID_AVATAR_BOB)
                .withNokName(VALID_NOK_NAME_BOB).withNokPhone(VALID_NOK_PHONE_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + AVATAR_DESC_BOB + NOK_NAME_DESC_BOB + NOK_PHONE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withAvatar(VALID_AVATAR_AMY)
                .withNokName(VALID_NOK_NAME_AMY).withNokPhone(VALID_NOK_PHONE_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + AVATAR_DESC_AMY + NOK_NAME_DESC_AMY + NOK_PHONE_DESC_AMY,
                new AddCommand(expectedPerson));

        // without address
        Person expectedPerson3 = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(DEFAULT_UNDEFINED).withAvatar(VALID_AVATAR_AMY)
                .withNokName(VALID_NOK_NAME_AMY).withNokPhone(VALID_NOK_PHONE_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                        + EMAIL_DESC_AMY + ADDRESS_DEFAULT_UNDEFINED + AVATAR_DESC_AMY + NOK_NAME_DESC_AMY
                        + NOK_PHONE_DESC_AMY + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson3));

        // without avatar
        Person expectedPerson4 = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withNokName(VALID_NOK_NAME_AMY).withNokPhone(VALID_NOK_PHONE_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                        + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + AVATAR_DESC_AMY + NOK_NAME_DESC_AMY
                        + NOK_PHONE_DESC_AMY + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson4));

        // without NOK Name
        Person expectedPerson5 = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withAvatar(VALID_AVATAR_AMY)
                .withNokName(DEFAULT_UNDEFINED).withNokPhone(VALID_NOK_PHONE_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                        + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + AVATAR_DESC_AMY + NOK_NAME_DEFAULT_UNDEFINED
                        + NOK_PHONE_DESC_AMY + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson5));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + AVATAR_DESC_BOB + NOK_NAME_DESC_BOB
                + NOK_PHONE_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + VALID_PHONE_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + AVATAR_DESC_BOB + NOK_NAME_DESC_BOB
                + NOK_PHONE_DESC_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + VALID_PHONE_BOB
                + VALID_EMAIL_BOB + VALID_ADDRESS_BOB + VALID_AVATAR_BOB + NOK_NAME_DESC_BOB
                + NOK_PHONE_DESC_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + AVATAR_DESC_BOB + NOK_NAME_DESC_BOB + NOK_PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + AVATAR_DESC_BOB + NOK_NAME_DESC_BOB + NOK_PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                + ADDRESS_DESC_BOB + AVATAR_DESC_BOB + NOK_NAME_DESC_BOB + NOK_PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC + AVATAR_DESC_BOB + NOK_NAME_DESC_BOB + NOK_PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid avatar
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_AVATAR_DESC + NOK_NAME_DESC_BOB + NOK_PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, Avatar.MESSAGE_AVATAR_CONSTRAINTS);

        // invalid NOK name
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + AVATAR_DESC_BOB + INVALID_NOK_NAME_DESC + NOK_PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, NokName.MESSAGE_NOK_NAME_CONSTRAINTS);

        // invalid NOK phone
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + AVATAR_DESC_BOB + NOK_NAME_DESC_BOB + INVALID_NOK_PHONE_DESC + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, NokPhone.MESSAGE_NOK_PHONE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + AVATAR_DESC_BOB + NOK_NAME_DESC_BOB + NOK_PHONE_DESC_BOB + INVALID_TAG_DESC
                + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC + AVATAR_DESC_BOB + NOK_NAME_DESC_BOB + NOK_PHONE_DESC_BOB,
                Name.MESSAGE_NAME_CONSTRAINTS);
    }
}
