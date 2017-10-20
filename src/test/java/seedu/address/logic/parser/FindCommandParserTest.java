package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.AnyContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.tag.TagContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedGlobalFindCommand =
                new FindCommand(new AnyContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedGlobalFindCommand);

        FindCommand expectedNameFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, " n/Alice Bob", expectedNameFindCommand);

        FindCommand expectedAddressFindCommand =
                new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList("Serangoon", "Bishan")));
        assertParseSuccess(parser, " a/Serangoon Bishan", expectedAddressFindCommand);

        FindCommand expectedEmailFindCommand =
                new FindCommand(new EmailContainsKeywordsPredicate(
                        Arrays.asList("alice@example.com", "Bob@gmail.com")));
        assertParseSuccess(parser, " e/alice@example.com Bob@gmail.com", expectedEmailFindCommand);

        FindCommand expectedPhoneFindCommand =
                new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("12345678", "98454632")));
        assertParseSuccess(parser, " p/12345678 98454632", expectedPhoneFindCommand);

        FindCommand expectedTagFindCommand =
                new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList("friends", "enemy")));
        assertParseSuccess(parser, " t/friends enemy", expectedTagFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedGlobalFindCommand);
        assertParseSuccess(parser, " n/\n Alice \n \t Bob  \t", expectedNameFindCommand);
        assertParseSuccess(parser, " a/\n Serangoon \n \t Bishan  \t", expectedAddressFindCommand);
        assertParseSuccess(parser, " e/\n alice@example.com \n \t Bob@gmail.com  \t", expectedEmailFindCommand);
        assertParseSuccess(parser, " p/\n 12345678 \n \t 98454632  \t", expectedPhoneFindCommand);
        assertParseSuccess(parser, " t/\n friends \n \t enemy  \t", expectedTagFindCommand);


    }

}
