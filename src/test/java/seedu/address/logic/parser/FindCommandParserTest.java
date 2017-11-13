package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.PersonContainsKeywordsPredicate;
//@@author KhorSL
public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noPrefix_throwsParseException() {
        assertParseFailure(parser, "alex john 91234567",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyFields_throwsParseException() {
        assertParseFailure(parser, " n/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " a/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " r/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " c/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " ap/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " p/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " e/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {

        // single user input
        HashMap<String, List<String>> expectedSingleFindCmdMap = new HashMap<>();
        expectedSingleFindCmdMap.put(PREFIX_NAME.toString(), Collections.singletonList("Alice"));
        FindCommand expectedSingleFindCmd =
                new FindCommand(new PersonContainsKeywordsPredicate(expectedSingleFindCmdMap));
        assertParseSuccess(parser, " n/Alice", expectedSingleFindCmd);

        expectedSingleFindCmdMap = new HashMap<>();
        expectedSingleFindCmdMap.put(PREFIX_TAG.toString(), Collections.singletonList("friends"));
        expectedSingleFindCmd = new FindCommand(new PersonContainsKeywordsPredicate(expectedSingleFindCmdMap));
        assertParseSuccess(parser, " r/friends", expectedSingleFindCmd);

        expectedSingleFindCmdMap = new HashMap<>();
        expectedSingleFindCmdMap.put(PREFIX_EMAIL.toString(), Collections.singletonList("friends@gmail.com"));
        expectedSingleFindCmd = new FindCommand(new PersonContainsKeywordsPredicate(expectedSingleFindCmdMap));
        assertParseSuccess(parser, " e/friends@gmail.com", expectedSingleFindCmd);

        expectedSingleFindCmdMap = new HashMap<>();
        expectedSingleFindCmdMap.put(PREFIX_PHONE.toString(), Collections.singletonList("91234567"));
        expectedSingleFindCmd = new FindCommand(new PersonContainsKeywordsPredicate(expectedSingleFindCmdMap));
        assertParseSuccess(parser, " p/91234567", expectedSingleFindCmd);

        expectedSingleFindCmdMap = new HashMap<>();
        expectedSingleFindCmdMap.put(PREFIX_ADDRESS.toString(), Arrays.asList("Felix", "Road", "23", "#12-12"));
        expectedSingleFindCmd = new FindCommand(new PersonContainsKeywordsPredicate(expectedSingleFindCmdMap));
        assertParseSuccess(parser, " a/Felix Road 23 #12-12", expectedSingleFindCmd);

        expectedSingleFindCmdMap = new HashMap<>();
        expectedSingleFindCmdMap.put(PREFIX_APPOINT.toString(), Arrays.asList("01/01/2017", "10:30"));
        expectedSingleFindCmd = new FindCommand(new PersonContainsKeywordsPredicate(expectedSingleFindCmdMap));
        assertParseSuccess(parser, " ap/01/01/2017 10:30", expectedSingleFindCmd);

        expectedSingleFindCmdMap = new HashMap<>();
        expectedSingleFindCmdMap.put(PREFIX_COMMENT.toString(), Collections.singletonList("funny"));
        expectedSingleFindCmd = new FindCommand(new PersonContainsKeywordsPredicate(expectedSingleFindCmdMap));
        assertParseSuccess(parser, " c/funny", expectedSingleFindCmd);

        // no leading and trailing whitespaces
        HashMap<String, List<String>> expectedFindCmdMap = new HashMap<>();
        expectedFindCmdMap.put(PREFIX_NAME.toString(), Arrays.asList("Alice", "Bob"));
        expectedFindCmdMap.put(PREFIX_TAG.toString(), Arrays.asList("friends", "family"));
        expectedFindCmdMap.put(PREFIX_EMAIL.toString(), Arrays.asList("@gmail.com", "@hotmail.com"));
        expectedFindCmdMap.put(PREFIX_ADDRESS.toString(), Arrays.asList("Felix", "Road", "23", "#12-12"));
        expectedFindCmdMap.put(PREFIX_APPOINT.toString(), Arrays.asList("01/01/2017", "10:30"));
        expectedFindCmdMap.put(PREFIX_COMMENT.toString(), Arrays.asList("funny", "swim"));
        expectedFindCmdMap.put(PREFIX_PHONE.toString(), Arrays.asList("91234567", "81234567"));

        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(expectedFindCmdMap));
        // leading whitespaces is not part of user input, but to accommodate for tokenizer
        assertParseSuccess(parser, " n/Alice Bob r/friends family e/@gmail.com @hotmail.com a/Felix Road 23 #12-12 "
                        + "ap/01/01/2017 10:30 c/funny swim p/91234567 81234567", expectedFindCommand);
        // multiple whitespaces between keywords
        assertParseSuccess(parser, "  n/ \n Alice \n \t Bob  \t  r/ \n friends \t family   "
                + "\t e/ @gmail.com \n @hotmail.com a/Felix Road \n 23 #12-12 ap/01/01/2017 \t  "
                + "\n 10:30 c/funny \n swim p/91234567 81234567", expectedFindCommand);
    }

}
