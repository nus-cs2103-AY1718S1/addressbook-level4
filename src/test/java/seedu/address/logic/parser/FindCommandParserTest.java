package seedu.address.logic.parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_FINDCOMMAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.CountryContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.ScheduleContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

public class FindCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommandName() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommandName =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "n/Alice Bob", expectedFindCommandName);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "n/ Alice    Bob", expectedFindCommandName);
    }


    @Test
    public void parse_validArgs_returnsFindCommandPhone() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommandPhone =
                new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("9898", "4553")));
        assertParseSuccess(parser, " " + "p/98984553", expectedFindCommandPhone);

        // digits not exactly 4 or 8
        assertParseFailure(parser, "p/988754445", PhoneContainsKeywordsPredicate.MESSAGE_PHONE_VALIDATION);
        assertParseFailure(parser, "p/988754445", PhoneContainsKeywordsPredicate.MESSAGE_PHONE_VALIDATION);
    }

    @Test
    public void parse_validArgs_returnsFindCommandCountry() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommandCountry =
                new FindCommand(new CountryContainsKeywordsPredicate(Arrays.asList("china", "paris")));
        assertParseSuccess(parser, "c/china paris", expectedFindCommandCountry);
    }

    @Test
    public void parse_validArgs_returnsFindCommandAddress() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommandAddress =
                new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList("1", "Barn", "Ave", "6", "#08-10")));
        assertParseSuccess(parser, "a/1 Barn Ave 6 #08-10", expectedFindCommandAddress);
    }

    @Test
    public void parse_validArgs_returnsFindCommandEmail() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommandEmail =
                new FindCommand(new EmailContainsKeywordsPredicate(Arrays.asList("abc@yahoo.com.sg")));
        assertParseSuccess(parser, " " + "e/abc@yahoo.com.sg", expectedFindCommandEmail);

    }

    @Test
    public void parse_validArgs_returnsFindCommandTag() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommandTag =
                new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList("friends", "teachers")));
        assertParseSuccess(parser, " " + "t/friends teachers", expectedFindCommandTag);


        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + "t/friends      teachers", expectedFindCommandTag);

    }

    @Test
    public void parse_validArgs_returnsFindCommandSchedule() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommandSchedule =
                new FindCommand(new ScheduleContainsKeywordsPredicate(Arrays.asList("party", "interview")));
        assertParseSuccess(parser, " " + "act/party interview", expectedFindCommandSchedule);


        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + "act/party      interview", expectedFindCommandSchedule);

    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "/abc@example.com", MESSAGE_INVALID_FORMAT);


        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "i/ string", MESSAGE_UNKNOWN_FINDCOMMAND);
    }

    @Test
    public void checkValidPhoneNumbers() {
        String[] testPhoneNumbersValid = new String[]{"9898", "98874577"};
        String[] testPhoneNumbersNotValid = new String[]{"989545"};

        // valid phone numbers
        assertTrue(FindCommandParser.validPhoneNumbers(testPhoneNumbersValid)); // exactly 4 or 8 digits

        //Invalid phone numbers
        assertFalse(FindCommandParser.validPhoneNumbers(testPhoneNumbersNotValid)); // Not exactly 4 or 8 digits


    }
}
