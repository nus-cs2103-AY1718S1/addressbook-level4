package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.predicates.AnyContainsKeywordsPredicate;
import seedu.address.model.person.predicates.EmailContainsKeywordsPredicate;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new AnyContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgsWithPrefix_returnsFindCommand() {
        FindCommand expectedFindCommand1 =
                new FindCommand(new NameContainsKeywordsPredicate(Collections.singletonList("Alice")));
        FindCommand expectedFindCommand2 =
                new FindCommand(new EmailContainsKeywordsPredicate(Collections.singletonList("Alice@gmail.com")));
        // with name prefix
        assertParseSuccess(parser, FindCommand.COMMAND_WORD + " n/Alice", expectedFindCommand1);

        //with an email prefix
        assertParseSuccess(parser, FindCommand.COMMAND_WORD + " e/Alice@gmail.com", expectedFindCommand2);
    }

    @Test
    public void parse_validArgsWithMultiplePrefixes_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Collections.singletonList("Alice")));
        // search by searchTerm with highest priority: name
        assertParseSuccess(parser, FindCommand.COMMAND_WORD + " n/Alice p/98765432", expectedFindCommand);

    }

}
