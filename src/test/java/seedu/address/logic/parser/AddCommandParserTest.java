package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_ADDEDITCOMMANDREMARK_INVALID;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.FORMCLASS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.FORMCLASS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GRADES_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GRADES_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FORMCLASS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GRADES_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POSTALCODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.POSTALCODE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.POSTALCODE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FORMCLASS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FORMCLASS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADES_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADES_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTALCODE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTALCODE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.FormClass;
import seedu.address.model.person.Grades;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PostalCode;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withFormClass(VALID_FORMCLASS_BOB)
                .withGrades(VALID_GRADES_BOB).withPostalCode(VALID_POSTALCODE_BOB).withTags(VALID_TAG_FRIEND).build();

        // multiple names - last name accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + FORMCLASS_DESC_BOB + GRADES_DESC_BOB + POSTALCODE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + FORMCLASS_DESC_BOB + GRADES_DESC_BOB + POSTALCODE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + FORMCLASS_DESC_BOB + GRADES_DESC_BOB + TAG_DESC_FRIEND
                + POSTALCODE_DESC_AMY + POSTALCODE_DESC_BOB, new AddCommand(expectedPerson));


        // multiple addresses - last address accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + FORMCLASS_DESC_BOB + GRADES_DESC_BOB + TAG_DESC_FRIEND
                + POSTALCODE_DESC_BOB, new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withFormClass(VALID_FORMCLASS_BOB)
                .withGrades(VALID_GRADES_BOB).withPostalCode(VALID_POSTALCODE_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + FORMCLASS_DESC_BOB + GRADES_DESC_BOB + POSTALCODE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedPersonMultipleTags));
    }
    //@@author Lenaldnwj
    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withGrades(VALID_GRADES_AMY)
                .withFormClass(VALID_FORMCLASS_AMY).withPostalCode(VALID_POSTALCODE_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + FORMCLASS_DESC_AMY + GRADES_DESC_AMY + POSTALCODE_DESC_AMY,
                new AddCommand(expectedPerson));

        // no email
        Person expectedPersonWithNoEmail = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withAddress(VALID_ADDRESS_AMY).withGrades(VALID_GRADES_AMY).withEmail("(Email not recorded)")
                .withFormClass(VALID_FORMCLASS_AMY).withPostalCode(VALID_POSTALCODE_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                        + ADDRESS_DESC_AMY + FORMCLASS_DESC_AMY + GRADES_DESC_AMY
                        + POSTALCODE_DESC_AMY + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonWithNoEmail));

        // no address
        Person expectedPersonWithNoAddress = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withAddress("(Address not recorded)").withGrades(VALID_GRADES_AMY).withEmail(VALID_EMAIL_AMY)
                .withFormClass(VALID_FORMCLASS_AMY).withPostalCode(VALID_POSTALCODE_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                        + FORMCLASS_DESC_AMY + GRADES_DESC_AMY + EMAIL_DESC_AMY
                        + POSTALCODE_DESC_AMY
                        + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonWithNoAddress));

        // no postal code
        Person expectedPersonWithNoPostalCode = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withAddress(VALID_ADDRESS_AMY).withGrades(VALID_GRADES_AMY).withEmail(VALID_EMAIL_AMY)
                .withFormClass(VALID_FORMCLASS_AMY).withPostalCode("(Postal code not recorded)")
                .withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                        + ADDRESS_DESC_AMY + FORMCLASS_DESC_AMY + GRADES_DESC_AMY + EMAIL_DESC_AMY
                        + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonWithNoPostalCode));

        // no address, no postal code
        Person expectedPersonWithNoAddressNoPostalCode = new PersonBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withAddress("(Address not recorded)").withGrades(VALID_GRADES_AMY).withEmail(VALID_EMAIL_AMY)
                .withFormClass(VALID_FORMCLASS_AMY).withPostalCode("(Postal code not recorded)")
                .withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                        + FORMCLASS_DESC_AMY + GRADES_DESC_AMY + EMAIL_DESC_AMY
                        + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonWithNoAddressNoPostalCode));

        // no email, no address, no postal code
        Person expectedPersonWithNoEmailNoAddressNoPostalCode = new PersonBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withAddress("(Address not recorded)").withGrades(VALID_GRADES_AMY).withEmail("(Email not recorded)")
                .withFormClass(VALID_FORMCLASS_AMY).withPostalCode("(Postal code not recorded)")
                .withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                        + FORMCLASS_DESC_AMY + GRADES_DESC_AMY
                        + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonWithNoEmailNoAddressNoPostalCode));

        // no email, no address, no postal code, no tag, meaning don't have all the optional inputs
        Person expectedPersonWithNoOptionalInputs = new PersonBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withAddress("(Address not recorded)").withGrades(VALID_GRADES_AMY).withEmail("(Email not recorded)")
                .withFormClass(VALID_FORMCLASS_AMY).withPostalCode("(Postal code not recorded)")
                .withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                        + FORMCLASS_DESC_AMY + GRADES_DESC_AMY,
                new AddCommand(expectedPersonWithNoOptionalInputs));
    }
    //@@author
    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + VALID_PHONE_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + VALID_EMAIL_BOB + ADDRESS_DESC_BOB, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + VALID_ADDRESS_BOB, expectedMessage);

        // missing formClass prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + VALID_FORMCLASS_BOB, expectedMessage);

        // missing grades prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + VALID_GRADES_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + VALID_PHONE_BOB
                + VALID_EMAIL_BOB + VALID_ADDRESS_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + FORMCLASS_DESC_BOB + GRADES_DESC_BOB + POSTALCODE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + INVALID_PHONE_DESC
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + FORMCLASS_DESC_BOB + GRADES_DESC_BOB + POSTALCODE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB + FORMCLASS_DESC_BOB + GRADES_DESC_BOB + POSTALCODE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC + FORMCLASS_DESC_BOB + GRADES_DESC_BOB + POSTALCODE_DESC_BOB + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid formClass
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_FORMCLASS_DESC + GRADES_DESC_BOB + POSTALCODE_DESC_BOB + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, FormClass.MESSAGE_FORMCLASS_CONSTRAINTS);

        // invalid grades
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + FORMCLASS_DESC_BOB + INVALID_GRADES_DESC + POSTALCODE_DESC_BOB + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, Grades.MESSAGE_GRADES_CONSTRAINTS);

        // invalid postalCode
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + FORMCLASS_DESC_BOB + GRADES_DESC_BOB + INVALID_POSTALCODE_DESC + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, PostalCode.MESSAGE_POSTALCODE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + FORMCLASS_DESC_BOB + GRADES_DESC_BOB + POSTALCODE_DESC_BOB + INVALID_TAG_DESC
                + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC + FORMCLASS_DESC_BOB + GRADES_DESC_BOB + POSTALCODE_DESC_BOB,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid field remarks
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + FORMCLASS_DESC_BOB + GRADES_DESC_BOB + POSTALCODE_DESC_BOB + REMARK_DESC_BOB,
                MESSAGE_ADDEDITCOMMANDREMARK_INVALID);
    }
    //@@author Lenaldnwj
    @Test
    public void optionalInput() {

        // When address, postal code, and email not entered by user
        assertEquals(AddCommandParser.optionalInput("add n/John Doe p/student/97272031 "
                + "parent/97979797 f/12S23 g/123.0 "
                + "t/friends t/owesMoney"), "add n/John Doe p/student/97272031 parent/97979797 f/12S23 g/123.0 "
                + "t/friends t/owesMoney a/ (Address not recorded) "
                + "e/ (Email not recorded) c/ (Postal code not recorded)");

        // When address and postal code not entered by user
        assertEquals(AddCommandParser.optionalInput("add n/John Doe p/student/97272031 "
                + "parent/97979797 e/johnd@example.com f/12S23 g/123.0 "
                + "t/friends t/owesMoney"), "add n/John Doe p/student/97272031 parent/97979797 e/johnd@example.com "
                + "f/12S23 g/123.0 "
                + "t/friends t/owesMoney a/ (Address not recorded) "
                + "c/ (Postal code not recorded)");

        // When email not entered by user
        assertEquals(AddCommandParser.optionalInput("add n/John Doe p/student/97272031 "
                + "parent/97979797 a/311, Clementi Ave 2, #02-25 f/12S23 g/123.0 "
                + "t/friends t/owesMoney"), "add n/John Doe p/student/97272031 "
                + "parent/97979797 a/311, Clementi Ave 2, #02-25 "
                + "f/12S23 g/123.0 t/friends t/owesMoney "
                + "e/ (Email not recorded) c/ (Postal code not recorded)");

        // When postal code not entered by user
        assertEquals(AddCommandParser.optionalInput("add n/John Doe p/student/97272031 "
                + "parent/97979797 e/johnd@example.com a/311, Clementi Ave 2, #02-25 f/12S23 g/123.0 "
                + "t/friends t/owesMoney"), "add n/John Doe p/student/97272031 parent/97979797 e/johnd@example.com "
                + "a/311, Clementi Ave 2, #02-25 f/12S23 g/123.0 "
                + "t/friends t/owesMoney "
                + "c/ (Postal code not recorded)");

        // When student number not entered by user
        assertEquals(AddCommandParser.optionalInput("add n/John Doe p/"
                + "parent/97979797 e/johnd@example.com a/311, Clementi Ave 2, #02-25 f/12S23 g/123.0 c/673349 "
                + "t/friends t/owesMoney"), "add n/John Doe p/parent/97979797 e/johnd@example.com "
                + "a/311, Clementi Ave 2, #02-25 f/12S23 g/123.0 "
                + "c/673349 t/friends t/owesMoney");
    }
    //@@author
}
