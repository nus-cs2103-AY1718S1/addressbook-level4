package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.BirthdayContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.NumberContainsKeywordsPredicate;
import seedu.address.model.person.RemarkContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindNameCommand =
                //@@author Affalen
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice")));
        assertParseSuccess(parser, "n/Alice", expectedFindNameCommand);

        FindCommand expectedFindNumberCommand =
                new FindCommand(new NumberContainsKeywordsPredicate(Arrays.asList("98765432")));
        assertParseSuccess(parser, "p/98765432", expectedFindNumberCommand);

        FindCommand expectedFindAddressCommand =
                new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList("Clementi")));
        assertParseSuccess(parser, "a/Clementi", expectedFindAddressCommand);

        FindCommand expectedFindBirthdayCommand =
                new FindCommand(new BirthdayContainsKeywordsPredicate(Arrays.asList("10-10-1995")));
        assertParseSuccess(parser, "b/10-10-1995", expectedFindBirthdayCommand);

        FindCommand expectedFindEmailCommand =
                new FindCommand(new EmailContainsKeywordsPredicate(Arrays.asList("john@example.com")));
        assertParseSuccess(parser, "e/john@example.com", expectedFindEmailCommand);

        FindCommand expectedFindRemarkCommand =
                new FindCommand(new RemarkContainsKeywordsPredicate(Arrays.asList("Swimmer")));
        assertParseSuccess(parser, "r/Swimmer", expectedFindRemarkCommand);

        FindCommand expectedFindTagCommand =
                new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList("friends")));
        assertParseSuccess(parser, "t/friends", expectedFindTagCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n n/Alice \n \t  \t", expectedFindNameCommand);

        assertParseSuccess(parser, " \n p/98765432 \n \t  \t", expectedFindNumberCommand);

        assertParseSuccess(parser, " \n a/Clementi \n \t  \t", expectedFindAddressCommand);

        assertParseSuccess(parser, " \n b/10-10-1995 \n \t  \t", expectedFindBirthdayCommand);

        assertParseSuccess(parser, " \n e/john@example.com \n \t  \t", expectedFindEmailCommand);

        assertParseSuccess(parser, " \n r/Swimmer \n \t  \t", expectedFindRemarkCommand);

        assertParseSuccess(parser, " \n t/friends \n \t  \t", expectedFindTagCommand);
    }

}
