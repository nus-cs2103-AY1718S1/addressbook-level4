package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.ContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        assertParseFailure(parser, FindCommand.KEYWORD_NAME, FindCommand.MESSAGE_NO_KEYWORD);

        assertParseFailure(parser, "abc", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // find name, no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new ContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"),
                        FindCommand.KEYWORD_NAME));
        assertParseSuccess(parser, FindCommand.KEYWORD_NAME + " Alice Bob", expectedFindCommand);

        // find name, multiple whitespaces between keywords
        assertParseSuccess(parser, FindCommand.KEYWORD_NAME + "\n Alice \n \t Bob  \t", expectedFindCommand);

        // find address, no leading and trailing whitespaces
        expectedFindCommand =
                new FindCommand(new ContainsKeywordsPredicate(Arrays.asList("Serangoon"),
                        FindCommand.KEYWORD_ADDRESS));
        assertParseSuccess(parser, FindCommand.KEYWORD_ADDRESS + " Serangoon", expectedFindCommand);

        // find address, multiple whitespaces between keywords
        assertParseSuccess(parser, FindCommand.KEYWORD_ADDRESS
                + "\n Serangoon \n  \t", expectedFindCommand);

        // find email, no leading and trailing whitespaces
        expectedFindCommand =
                new FindCommand(new ContainsKeywordsPredicate(Arrays.asList("example1", "example2"),
                        FindCommand.KEYWORD_EMAIL));
        assertParseSuccess(parser, FindCommand.KEYWORD_EMAIL + " example1 example2", expectedFindCommand);

        // find email, multiple whitespaces between keywords
        assertParseSuccess(parser, FindCommand.KEYWORD_EMAIL + "\n example1 \n \t example2  \t", expectedFindCommand);

        // find phone, no leading and trailing whitespaces
        expectedFindCommand =
                new FindCommand(new ContainsKeywordsPredicate(Arrays.asList("123456789"), FindCommand.KEYWORD_PHONE));
        assertParseSuccess(parser, FindCommand.KEYWORD_PHONE + " 123456789", expectedFindCommand);

        // find phone, multiple whitespaces between keywords
        assertParseSuccess(parser, FindCommand.KEYWORD_PHONE + "\n 123456789  \t", expectedFindCommand);

        // find organisation, no leading and trailing whitespaces
        expectedFindCommand =
                new FindCommand(
                        new ContainsKeywordsPredicate(Arrays.asList("Apple"), FindCommand.KEYWORD_ORGANISATION));
        assertParseSuccess(parser, FindCommand.KEYWORD_ORGANISATION + " Apple", expectedFindCommand);

        // find phone, multiple whitespaces between keywords
        assertParseSuccess(parser, FindCommand.KEYWORD_ORGANISATION + "\n Apple  \t", expectedFindCommand);
    }

}
