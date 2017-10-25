package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        HashMap<String, List<String>> expectedFindCmdMap = new HashMap<>();
        expectedFindCmdMap.put(PREFIX_NAME.toString(), Arrays.asList("Alice", "Bob"));
        expectedFindCmdMap.put(PREFIX_TAG.toString(), Arrays.asList("friends", "family"));
        expectedFindCmdMap.put(PREFIX_EMAIL.toString(), Arrays.asList("@gmail.com", "@hotmail.com"));
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(expectedFindCmdMap));
        // leading whitespaces is not part of user input, but to accommodate for tokenizer
        assertParseSuccess(parser, " n/Alice Bob r/friends family e/@gmail.com @hotmail.com", expectedFindCommand);
        // multiple whitespaces between keywords
        assertParseSuccess(parser, "  n/ \n Alice \n \t Bob  \t  r/ \n friends \t family   \t e/ @gmail.com \n @hotmail.com",
                expectedFindCommand);
    }

}
