package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MEETING_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MEETING_DESC_DEC;
import static seedu.address.logic.commands.CommandTestUtil.MEETING_DESC_NOV;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NO_ADDRESS_SET;
import static seedu.address.logic.commands.CommandTestUtil.NO_EMAIL_SET;
import static seedu.address.logic.commands.CommandTestUtil.NO_PHONE_SET;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETING_DEC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETING_NOV;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.meeting.Meeting;
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
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND)
                .withMeetings(VALID_MEETING_DEC).build();

        // multiple names - last name accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND
                + MEETING_DESC_DEC, new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND
                + MEETING_DESC_DEC, new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND
                + MEETING_DESC_DEC, new AddCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + TAG_DESC_FRIEND
                + MEETING_DESC_DEC, new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).withMeetings(VALID_MEETING_DEC).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + MEETING_DESC_DEC,
                new AddCommand(expectedPersonMultipleTags));

        // multiple meetings - all accepted
        Person expectedPersonMultipleMeetings = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_FRIEND).withMeetings(VALID_MEETING_DEC, VALID_MEETING_NOV).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + MEETING_DESC_DEC + MEETING_DESC_NOV,
                new AddCommand(expectedPersonMultipleMeetings));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags().withMeetings().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY, new AddCommand(expectedPerson));

        // no phone number
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(NO_PHONE_SET)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY, new AddCommand(expectedPerson));

        // no email address
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(NO_EMAIL_SET).withAddress(VALID_ADDRESS_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + ADDRESS_DESC_AMY, new AddCommand(expectedPerson));

        // no phone number
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(NO_ADDRESS_SET).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY, new AddCommand(expectedPerson));

        // only has name
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(NO_PHONE_SET)
                .withEmail(NO_EMAIL_SET).withAddress(NO_ADDRESS_SET).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY, new AddCommand(expectedPerson));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + VALID_PHONE_BOB
                + VALID_EMAIL_BOB + VALID_ADDRESS_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                        + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + INVALID_ADDRESS_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // invalid meeting
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + INVALID_MEETING_DESC, Meeting.MESSAGE_TIME_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC, Name.MESSAGE_NAME_CONSTRAINTS);
    }

}
