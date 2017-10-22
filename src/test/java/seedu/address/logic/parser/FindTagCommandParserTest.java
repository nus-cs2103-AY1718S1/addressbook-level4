package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.tags.FindTagCommand;
import seedu.address.model.task.TagContainsKeywordsPredicate;

public class FindTagCommandParserTest {

    private FindTagCommandParser parser = new FindTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindTagCommand expectedTagFindCommand =
            new FindTagCommand(new TagContainsKeywordsPredicate(Arrays.asList("friends", "family")));
        assertParseSuccess(parser, "friends family", expectedTagFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n friends \n \t family  \t", expectedTagFindCommand);
    }

}
