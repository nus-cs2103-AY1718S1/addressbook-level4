package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.MultiFilterCommand;
import seedu.address.model.person.AddressContainsKeywordsSubstrPredicate;
import seedu.address.model.person.EmailContainsKeywordsSubstrPredicate;
import seedu.address.model.person.NameContainsKeywordsSubstrPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhoneContainsKeywordsSubstrPredicate;
import seedu.address.testutil.PersonBuilder;

public class MultiFilterCommandParserTest {
    private MultiFilterCommandParser parser = new MultiFilterCommandParser();

    //@@author lancehaoh
    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND).build();

        // multiple names - last name accepted
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB, new MultiFilterCommand(
                new NameContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getName().toString())),
                new PhoneContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getPhone().toString())),
                new EmailContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getEmail().toString())),
                new AddressContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getAddress().toString()))
        ));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB, new MultiFilterCommand(
                new NameContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getName().toString())),
                new PhoneContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getPhone().toString())),
                new EmailContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getEmail().toString())),
                new AddressContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getAddress().toString()))
        ));

        // multiple emails - last email accepted
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB, new MultiFilterCommand(
                new NameContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getName().toString())),
                new PhoneContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getPhone().toString())),
                new EmailContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getEmail().toString())),
                new AddressContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getAddress().toString()))
        ));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB, new MultiFilterCommand(
                new NameContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getName().toString())),
                new PhoneContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getPhone().toString())),
                new EmailContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getEmail().toString())),
                new AddressContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getAddress().toString()))
        ));

        // name prefix is entered but value is empty
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + " " + PREFIX_NAME + ""
                        + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                new MultiFilterCommand(
                        null,
                        new PhoneContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getPhone().toString())),
                        new EmailContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getEmail().toString())),
                        new AddressContainsKeywordsSubstrPredicate(
                                Arrays.asList(expectedPerson.getAddress().toString()))
                ));

        //  phone prefix is entered but value is empty
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + NAME_DESC_BOB
                        + " " + PREFIX_PHONE + "" + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                new MultiFilterCommand(
                        new NameContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getName().toString())),
                        null,
                        new EmailContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getEmail().toString())),
                        new AddressContainsKeywordsSubstrPredicate(
                                Arrays.asList(expectedPerson.getAddress().toString()))
                ));

        // email prefix is entered but value is empty
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + NAME_DESC_BOB
                        + PHONE_DESC_BOB + " " + PREFIX_EMAIL + "" + ADDRESS_DESC_BOB,
                new MultiFilterCommand(
                        new NameContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getName().toString())),
                        new PhoneContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getPhone().toString())),
                        null,
                        new AddressContainsKeywordsSubstrPredicate(
                                Arrays.asList(expectedPerson.getAddress().toString()))
                ));


        // address prefix is entered but value is empty
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + NAME_DESC_BOB
                        + PHONE_DESC_BOB + EMAIL_DESC_BOB + " " + PREFIX_ADDRESS + "",
                new MultiFilterCommand(
                        new NameContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getName().toString())),
                        new PhoneContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getPhone().toString())),
                        new EmailContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getEmail().toString())),
                        null
                ));

        // test if able to parse data person with data of arbitrary length and characters
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + " " + PREFIX_NAME + "a"
                        + " " + PREFIX_PHONE + "9" + " " + PREFIX_EMAIL + "ex" + " " + PREFIX_ADDRESS + "a",
                new MultiFilterCommand(
                        new NameContainsKeywordsSubstrPredicate(Arrays.asList("a")),
                        new PhoneContainsKeywordsSubstrPredicate(Arrays.asList("9")),
                        new EmailContainsKeywordsSubstrPredicate(Arrays.asList("ex")),
                        new AddressContainsKeywordsSubstrPredicate(Arrays.asList("a"))
                ));
    }

    //@@author lancehaoh
    @Test
    /**
     * Tests if parser can handle cases where certain fields are not entered by the user
     */
    public void parse_partialFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND).build();

        // name is not entered
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB, new MultiFilterCommand(
                null,
                new PhoneContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getPhone().toString())),
                new EmailContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getEmail().toString())),
                new AddressContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getAddress().toString()))
        ));

        // phone number is not entered
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + NAME_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB, new MultiFilterCommand(
                new NameContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getName().toString())),
                null,
                new EmailContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getEmail().toString())),
                new AddressContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getAddress().toString()))
        ));

        // email is not entered
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB, new MultiFilterCommand(
                new NameContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getName().toString())),
                new PhoneContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getPhone().toString())),
                null,
                new AddressContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getAddress().toString()))
        ));

        // address is not entered
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB,
                new MultiFilterCommand(
                        new NameContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getName().toString())),
                        new PhoneContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getPhone().toString())),
                        new EmailContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getEmail().toString())),
                        null
                ));

        // name prefix is entered but value is empty
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + " " + PREFIX_NAME + ""
                        + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                new MultiFilterCommand(
                        null,
                        new PhoneContainsKeywordsSubstrPredicate(
                                Arrays.asList(expectedPerson.getPhone().toString())),
                        new EmailContainsKeywordsSubstrPredicate(
                                Arrays.asList(expectedPerson.getEmail().toString())),
                        new AddressContainsKeywordsSubstrPredicate(
                                Arrays.asList(expectedPerson.getAddress().toString()))
                ));

        //  phone prefix is entered but value is empty
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + NAME_DESC_BOB
                        + " " + PREFIX_PHONE + "" + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                new MultiFilterCommand(
                        new NameContainsKeywordsSubstrPredicate(
                                Arrays.asList(expectedPerson.getName().toString())),
                        null,
                        new EmailContainsKeywordsSubstrPredicate(
                                Arrays.asList(expectedPerson.getEmail().toString())),
                        new AddressContainsKeywordsSubstrPredicate(
                                Arrays.asList(expectedPerson.getAddress().toString()))
                ));

        // email prefix is entered but value is empty
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + NAME_DESC_BOB
                        + PHONE_DESC_BOB + " " + PREFIX_EMAIL + "" + ADDRESS_DESC_BOB,
                new MultiFilterCommand(
                        new NameContainsKeywordsSubstrPredicate(
                                Arrays.asList(expectedPerson.getName().toString())),
                        new PhoneContainsKeywordsSubstrPredicate(
                                Arrays.asList(expectedPerson.getPhone().toString())),
                        null,
                        new AddressContainsKeywordsSubstrPredicate(
                                Arrays.asList(expectedPerson.getAddress().toString()))
                ));


        // address prefix is entered but value is empty
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + NAME_DESC_BOB
                        + PHONE_DESC_BOB + EMAIL_DESC_BOB + " " + PREFIX_ADDRESS + "",
                new MultiFilterCommand(
                        new NameContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getName().toString())),
                        new PhoneContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getPhone().toString())),
                        new EmailContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getEmail().toString())),
                        null
                ));

        // all fields are empty
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD,
                new MultiFilterCommand(null, null,
                        null, null));
    }

    //@@author lancehaoh
    @Test
    /**
     * Tests if fields can be parsed properly when they are entered in an arbitrary order
     */
    public void parse_rearrangedFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND).build();

        // multiple names - last name accepted
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + PHONE_DESC_BOB, new MultiFilterCommand(
                null,
                new PhoneContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getPhone().toString())),
                new EmailContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getEmail().toString())),
                new AddressContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getAddress().toString()))
        ));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + ADDRESS_DESC_BOB
                + NAME_DESC_BOB + EMAIL_DESC_BOB, new MultiFilterCommand(
                new NameContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getName().toString())),
                null,
                new EmailContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getEmail().toString())),
                new AddressContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getAddress().toString()))
        ));

        // multiple emails - last email accepted
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + NAME_DESC_BOB, new MultiFilterCommand(
                new NameContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getName().toString())),
                new PhoneContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getPhone().toString())),
                null,
                new AddressContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getAddress().toString()))
        ));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, MultiFilterCommand.COMMAND_WORD + EMAIL_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB,
                new MultiFilterCommand(
                        new NameContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getName().toString())),
                        new PhoneContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getPhone().toString())),
                        new EmailContainsKeywordsSubstrPredicate(Arrays.asList(expectedPerson.getEmail().toString())),
                        null
                ));
    }
}
