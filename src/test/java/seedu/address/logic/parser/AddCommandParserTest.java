package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DEBT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DEBT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.HANDPHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.HANDPHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.HOME_PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.HOME_PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INTEREST_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INTEREST_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEBT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HANDPHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HOME_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INTEREST_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_OFFICE_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POSTAL_CODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.OFFICE_PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.OFFICE_PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.POSTAL_CODE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.POSTAL_CODE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEBT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEBT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HANDPHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HANDPHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOME_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOME_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTEREST_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTEREST_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OFFICE_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OFFICE_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTAL_CODE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTAL_CODE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.AddCommandParser.MESSAGE_INVALID_DEBT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Debt;
import seedu.address.model.person.Email;
import seedu.address.model.person.Interest;
import seedu.address.model.person.Name;
import seedu.address.model.person.OfficePhone;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PostalCode;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withHandphone(VALID_HANDPHONE_BOB)
                .withHomePhone(VALID_HOME_PHONE_BOB).withOfficePhone(VALID_OFFICE_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withPostalCode(VALID_POSTAL_CODE_BOB)
                .withDebt(VALID_DEBT_BOB).withTotalDebt(VALID_DEBT_BOB).withInterest(VALID_INTEREST_BOB)
                .withDeadline(VALID_DEADLINE_BOB).withTags(VALID_TAG_FRIEND).build();

        // multiple names - last name accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + NAME_DESC_BOB + HANDPHONE_DESC_BOB
                + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + DEBT_DESC_BOB + INTEREST_DESC_BOB
                + DEADLINE_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple handphones - last handphone accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + HANDPHONE_DESC_AMY
                + HANDPHONE_DESC_BOB + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + POSTAL_CODE_DESC_BOB + DEBT_DESC_BOB + INTEREST_DESC_BOB + DEADLINE_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple home phones - last home phone accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + HOME_PHONE_DESC_AMY
                + HANDPHONE_DESC_BOB + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + POSTAL_CODE_DESC_BOB + DEBT_DESC_BOB + INTEREST_DESC_BOB + DEADLINE_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple home phones - last home phone accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + OFFICE_PHONE_DESC_AMY
                + HANDPHONE_DESC_BOB + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + POSTAL_CODE_DESC_BOB + DEBT_DESC_BOB + INTEREST_DESC_BOB + DEADLINE_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + HANDPHONE_DESC_BOB
                        + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB + EMAIL_DESC_AMY
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + DEBT_DESC_BOB + INTEREST_DESC_BOB
                + DEADLINE_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + HANDPHONE_DESC_BOB
                        + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + DEBT_DESC_BOB + INTEREST_DESC_BOB
                + DEADLINE_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple postal codes - last postal code accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + HANDPHONE_DESC_BOB
                        + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_AMY + POSTAL_CODE_DESC_BOB + DEBT_DESC_BOB + INTEREST_DESC_BOB
                + DEADLINE_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple debts - last debt accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + HANDPHONE_DESC_BOB
                        + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + DEBT_DESC_AMY + DEBT_DESC_BOB + INTEREST_DESC_BOB
                + DEADLINE_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple deadlines - last deadline accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + HANDPHONE_DESC_BOB
                        + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + DEBT_DESC_BOB + INTEREST_DESC_BOB + DEADLINE_DESC_AMY
                + DEADLINE_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple interests - last interest accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + HANDPHONE_DESC_BOB
                        + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + DEBT_DESC_BOB + INTEREST_DESC_AMY
                        + INTEREST_DESC_BOB + DEADLINE_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder().withName(VALID_NAME_BOB)
                .withHandphone(VALID_HANDPHONE_BOB).withHomePhone(VALID_HOME_PHONE_BOB)
                .withOfficePhone(VALID_OFFICE_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withPostalCode(VALID_POSTAL_CODE_BOB).withDebt(VALID_DEBT_BOB).withTotalDebt(VALID_DEBT_BOB)
                .withInterest(VALID_INTEREST_BOB).withDeadline(VALID_DEADLINE_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + HANDPHONE_DESC_BOB
                + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB
                + DEBT_DESC_BOB + INTEREST_DESC_BOB + DEADLINE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withHandphone(VALID_HANDPHONE_AMY)
                .withHomePhone(VALID_HOME_PHONE_AMY).withOfficePhone(VALID_OFFICE_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withPostalCode(VALID_POSTAL_CODE_AMY)
                .withDebt(VALID_DEBT_AMY).withTotalDebt(VALID_DEBT_AMY).withInterest(VALID_INTEREST_AMY)
                .withDeadline(VALID_DEADLINE_AMY).withTags().build();
        // zero tags
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + HANDPHONE_DESC_AMY
                + HOME_PHONE_DESC_AMY + OFFICE_PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + POSTAL_CODE_DESC_AMY
                + DEBT_DESC_AMY + INTEREST_DESC_AMY + DEADLINE_DESC_AMY, new AddCommand(expectedPerson));
        Person expectedPerson2 = new PersonBuilder().withName(VALID_NAME_AMY).withHandphone(VALID_HANDPHONE_AMY)
                .withHomePhone(VALID_HOME_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withPostalCode(VALID_POSTAL_CODE_AMY).withDebt(VALID_DEBT_AMY).withTotalDebt(VALID_DEBT_AMY)
                .withInterest(VALID_INTEREST_AMY).withDeadline(VALID_DEADLINE_AMY).withTags(VALID_TAG_FRIEND)
                .withOfficePhone(OfficePhone.NO_OFFICE_PHONE_SET).build();
        // no office phone
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + HANDPHONE_DESC_AMY
                + HOME_PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + POSTAL_CODE_DESC_AMY
                + DEBT_DESC_AMY + INTEREST_DESC_AMY + DEADLINE_DESC_AMY + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson2));
        Person expectedPerson3 = new PersonBuilder().withName(VALID_NAME_AMY).withHandphone(VALID_HANDPHONE_AMY)
                .withHomePhone(VALID_HOME_PHONE_AMY).withOfficePhone(VALID_OFFICE_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withPostalCode(VALID_POSTAL_CODE_AMY)
                .withDebt(VALID_DEBT_AMY).withTotalDebt(VALID_DEBT_AMY).withInterest(VALID_INTEREST_AMY)
                .withDeadline(Deadline.NO_DEADLINE_SET).withTags(VALID_TAG_FRIEND).build();
        // no deadline
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + HANDPHONE_DESC_AMY
                + HOME_PHONE_DESC_AMY + OFFICE_PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + POSTAL_CODE_DESC_AMY
                + DEBT_DESC_AMY + INTEREST_DESC_AMY + TAG_DESC_FRIEND, new AddCommand(expectedPerson3));
        Person expectedPerson4 = new PersonBuilder().withName(VALID_NAME_AMY).withHandphone(VALID_HANDPHONE_AMY)
                .withHomePhone(VALID_HOME_PHONE_AMY).withOfficePhone(VALID_OFFICE_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withPostalCode(VALID_POSTAL_CODE_AMY)
                .withDebt(VALID_DEBT_AMY).withTotalDebt(VALID_DEBT_AMY).withInterest(Interest.NO_INTEREST_SET)
                .withDeadline(VALID_DEADLINE_AMY).withTags(VALID_TAG_FRIEND).build();
        // no interest
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + HANDPHONE_DESC_AMY
                + HOME_PHONE_DESC_AMY + OFFICE_PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + POSTAL_CODE_DESC_AMY
                + DEBT_DESC_AMY + DEADLINE_DESC_AMY + TAG_DESC_FRIEND, new AddCommand(expectedPerson4));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + HANDPHONE_DESC_BOB
                + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB
                + DEBT_DESC_BOB + INTEREST_DESC_BOB + DEADLINE_DESC_BOB, expectedMessage);

        // missing handphone prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + VALID_HANDPHONE_BOB
                + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + POSTAL_CODE_DESC_BOB + DEBT_DESC_BOB + INTEREST_DESC_BOB + DEADLINE_DESC_BOB, expectedMessage);

        // missing home phone prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + HANDPHONE_DESC_BOB
                + VALID_HOME_PHONE_BOB + OFFICE_PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + POSTAL_CODE_DESC_BOB + DEBT_DESC_BOB + INTEREST_DESC_BOB + DEADLINE_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + HANDPHONE_DESC_BOB
                + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB
                + VALID_EMAIL_BOB + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + DEBT_DESC_BOB
                + INTEREST_DESC_BOB + DEADLINE_DESC_BOB, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + HANDPHONE_DESC_BOB
                + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB
                + EMAIL_DESC_BOB + VALID_ADDRESS_BOB + POSTAL_CODE_DESC_BOB  + DEBT_DESC_BOB
                + INTEREST_DESC_BOB + DEADLINE_DESC_BOB, expectedMessage);

        // missing postal code prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + HANDPHONE_DESC_BOB
                + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + VALID_POSTAL_CODE_BOB + DEBT_DESC_BOB + INTEREST_DESC_BOB
                + DEADLINE_DESC_BOB, expectedMessage);

        // missing debt prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + HANDPHONE_DESC_BOB
                + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + VALID_DEBT_BOB
                + INTEREST_DESC_BOB + DEADLINE_DESC_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + VALID_HANDPHONE_BOB
                + VALID_HOME_PHONE_BOB + VALID_OFFICE_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB
                + VALID_POSTAL_CODE_BOB + VALID_DEBT_BOB + VALID_INTEREST_BOB + VALID_DEADLINE_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + HANDPHONE_DESC_BOB
                + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + DEBT_DESC_BOB + INTEREST_DESC_BOB + DEADLINE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid handphone
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + INVALID_HANDPHONE_DESC
                + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + DEBT_DESC_BOB + INTEREST_DESC_BOB
                + DEADLINE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid home phone
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + HANDPHONE_DESC_BOB
                + INVALID_HOME_PHONE_DESC + OFFICE_PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + DEBT_DESC_BOB + INTEREST_DESC_BOB
                + DEADLINE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid office phone
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + HANDPHONE_DESC_BOB
                + HOME_PHONE_DESC_BOB + INVALID_OFFICE_PHONE_DESC + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + DEBT_DESC_BOB + INTEREST_DESC_BOB
                + DEADLINE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + HANDPHONE_DESC_BOB
                + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB + INVALID_EMAIL_DESC
                + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + DEBT_DESC_BOB + INTEREST_DESC_BOB + DEADLINE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + HANDPHONE_DESC_BOB
                + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC + POSTAL_CODE_DESC_BOB + DEBT_DESC_BOB + INTEREST_DESC_BOB
                + DEADLINE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid postal code
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + HANDPHONE_DESC_BOB
                + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_POSTAL_CODE_DESC + DEBT_DESC_BOB + INTEREST_DESC_BOB + DEADLINE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                PostalCode.MESSAGE_POSTAL_CODE_CONSTRAINTS);

        // invalid debt - non-digit characters
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + HANDPHONE_DESC_BOB
                + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + INVALID_DEBT_DESC + INTEREST_DESC_BOB
                + DEADLINE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Debt.MESSAGE_DEBT_CONSTRAINTS);

        // invalid debt - debt is zero
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + HANDPHONE_DESC_BOB
                + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + " d/0" + INTEREST_DESC_BOB
                + DEADLINE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                MESSAGE_INVALID_DEBT);

        // invalid interest
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + HANDPHONE_DESC_BOB
                + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + DEBT_DESC_BOB + INVALID_INTEREST_DESC
                + DEADLINE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Interest.MESSAGE_INTEREST_CONSTRAINTS);

        // invalid deadline
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + HANDPHONE_DESC_BOB
                + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + DEBT_DESC_BOB + INTEREST_DESC_BOB + INVALID_DEADLINE_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Deadline.MESSAGE_DEADLINE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + HANDPHONE_DESC_BOB
                + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + DEBT_DESC_BOB + INTEREST_DESC_BOB + DEADLINE_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + HANDPHONE_DESC_BOB
                + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB
                + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC + POSTAL_CODE_DESC_BOB + DEBT_DESC_BOB + INTEREST_DESC_BOB
                + DEADLINE_DESC_BOB, Name.MESSAGE_NAME_CONSTRAINTS);
    }
}
