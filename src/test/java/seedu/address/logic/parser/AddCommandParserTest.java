package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.FIELD_DESC_COMPANY;
import static seedu.address.logic.commands.CommandTestUtil.FIELD_DESC_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_DESC_SAVING;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FIELD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GROUP_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FIELD_COMPANY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FIELD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_SAVING;
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
import seedu.address.model.customfields.CustomField;
import seedu.address.model.group.Group;
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
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withGroup(VALID_GROUP_SAVING).withTags(VALID_TAG_FRIEND)
                .withFields(VALID_FIELD_SCHOOL).build();

        // multiple names - last name accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + GROUP_DESC_SAVING + TAG_DESC_FRIEND + FIELD_DESC_SCHOOL,
                new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + FIELD_DESC_SCHOOL + GROUP_DESC_SAVING,
                new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + GROUP_DESC_SAVING + TAG_DESC_FRIEND + FIELD_DESC_SCHOOL,
                new AddCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + GROUP_DESC_SAVING + TAG_DESC_FRIEND + FIELD_DESC_SCHOOL,
                new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withGroup(VALID_GROUP_SAVING)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).withFields(VALID_FIELD_SCHOOL).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + GROUP_DESC_SAVING
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + FIELD_DESC_SCHOOL,
                new AddCommand(expectedPersonMultipleTags));

        //@@author syy94
        // multiple fields - all accepted
        Person expectedPersonMultipleFields = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withGroup(VALID_GROUP_SAVING)
                .withTags(VALID_TAG_FRIEND).withFields(VALID_FIELD_SCHOOL, VALID_FIELD_COMPANY).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + GROUP_DESC_SAVING
                        + TAG_DESC_FRIEND + FIELD_DESC_SCHOOL + FIELD_DESC_COMPANY,
                new AddCommand(expectedPersonMultipleFields));
        //@@author
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags and zero fields
        Person expectedPersonZeroTags = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withGroup(VALID_GROUP_SAVING)
                .withTags().withFields().build();

        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                        + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + GROUP_DESC_SAVING,
                new AddCommand(expectedPersonZeroTags));


        // missing phone prefix
        Person expectedPersonMissingPhone = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_EMPTY)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withGroup(VALID_GROUP_SAVING).withTags(VALID_TAG_FRIEND)
                .withFields(VALID_FIELD_SCHOOL).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + GROUP_DESC_SAVING + TAG_DESC_FRIEND
                + FIELD_DESC_SCHOOL, new AddCommand(expectedPersonMissingPhone));

        // missing email prefix
        Person expectedPersonMissingEmail = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMPTY).withAddress(VALID_ADDRESS_BOB).withGroup(VALID_GROUP_SAVING)
                .withTags(VALID_TAG_FRIEND).withFields(VALID_FIELD_SCHOOL).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + GROUP_DESC_SAVING + TAG_DESC_FRIEND
                + FIELD_DESC_SCHOOL, new AddCommand(expectedPersonMissingEmail));

        // missing address prefix
        Person expectedPersonMissingAddress = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_EMPTY)
                .withGroup(VALID_GROUP_SAVING).withTags(VALID_TAG_FRIEND)
                .withFields(VALID_FIELD_SCHOOL).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + GROUP_DESC_SAVING + TAG_DESC_FRIEND
                + FIELD_DESC_SCHOOL, new AddCommand(expectedPersonMissingAddress));

        // all prefixes missing
        Person expectedPersonMissingAllExceptName = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_EMPTY)
                .withEmail(VALID_EMPTY).withAddress(VALID_EMPTY)
                .withGroup(VALID_GROUP_SAVING).withTags().withFields().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + GROUP_DESC_SAVING,
                new AddCommand(expectedPersonMissingAllExceptName));


    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedNameMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + GROUP_DESC_SAVING, expectedNameMessage);

        String expectedGroupMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing group prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB, expectedGroupMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + GROUP_DESC_SAVING + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + GROUP_DESC_SAVING + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                + ADDRESS_DESC_BOB + GROUP_DESC_SAVING + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + GROUP_DESC_SAVING + INVALID_TAG_DESC
                + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        //@@author kengying
        // invalid field
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + GROUP_DESC_SAVING + INVALID_FIELD_DESC
                + VALID_TAG_FRIEND, CustomField.MESSAGE_FIELD_CONSTRAINTS);

        // invalid group
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_GROUP_DESC + VALID_TAG_FRIEND, Group.MESSAGE_GROUP_CONSTRAINTS);
        //@@author

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                + VALID_ADDRESS_BOB + GROUP_DESC_SAVING, Name.MESSAGE_NAME_CONSTRAINTS);
    }
}
