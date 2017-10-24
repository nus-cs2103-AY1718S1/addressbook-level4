package seedu.room.logic.parser;

import static seedu.room.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.room.logic.commands.CommandTestUtil.DEFAULT_NOT_SET;
import static seedu.room.logic.commands.CommandTestUtil.EMAIL_DEFAULT_UNSET;
import static seedu.room.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.room.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.room.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.room.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.room.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.room.logic.commands.CommandTestUtil.INVALID_ROOM_DESC;
import static seedu.room.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.room.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.room.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.room.logic.commands.CommandTestUtil.PHONE_DEFAULT_UNSET;
import static seedu.room.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.room.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.room.logic.commands.CommandTestUtil.ROOM_DEFAULT_UNSET;
import static seedu.room.logic.commands.CommandTestUtil.ROOM_DESC_AMY;
import static seedu.room.logic.commands.CommandTestUtil.ROOM_DESC_BOB;
import static seedu.room.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.room.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.room.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.room.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.room.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.room.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.room.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.room.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.room.logic.commands.CommandTestUtil.VALID_ROOM_AMY;
import static seedu.room.logic.commands.CommandTestUtil.VALID_ROOM_BOB;
import static seedu.room.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.room.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.room.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.room.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.room.logic.commands.AddCommand;
import seedu.room.model.person.Email;
import seedu.room.model.person.Name;
import seedu.room.model.person.Person;
import seedu.room.model.person.Phone;
import seedu.room.model.person.Room;
import seedu.room.model.tag.Tag;
import seedu.room.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withRoom(VALID_ROOM_BOB).withTags(VALID_TAG_FRIEND).build();

        // multiple names - last name accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ROOM_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ROOM_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
                + EMAIL_DESC_BOB + ROOM_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple rooms - last room accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ROOM_DESC_AMY + ROOM_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withRoom(VALID_ROOM_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ROOM_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withRoom(VALID_ROOM_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ROOM_DESC_AMY, new AddCommand(expectedPerson));

        // without phone
        Person expectedPerson2 = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(DEFAULT_NOT_SET)
                .withEmail(VALID_EMAIL_AMY).withRoom(VALID_ROOM_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DEFAULT_UNSET
                        + EMAIL_DESC_AMY + ROOM_DESC_AMY + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson2));

        // without email
        Person expectedPerson3 = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(DEFAULT_NOT_SET).withRoom(VALID_ROOM_AMY).withTags(VALID_TAG_HUSBAND,
                VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                        + EMAIL_DEFAULT_UNSET + ROOM_DESC_AMY + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson3));

        // without room
        Person expectedPerson4 = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withRoom(DEFAULT_NOT_SET)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                        + EMAIL_DESC_AMY + ROOM_DEFAULT_UNSET + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson4));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ROOM_DESC_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + VALID_PHONE_BOB
                + VALID_EMAIL_BOB + VALID_ROOM_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ROOM_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + ROOM_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                        + ROOM_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid room
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + INVALID_ROOM_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Room.MESSAGE_ROOM_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ROOM_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ROOM_DESC, Name.MESSAGE_NAME_CONSTRAINTS);
    }
}
