//@@author 1moresec
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindTaskCommand;
import seedu.address.model.task.TaskNameContainsKeywordsPredicate;

public class FindTaskCommandParserTest {

    private FindTaskCommandParser parser = new FindTaskCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsFindTaskCommand() {
        // no leading and trailing whitespaces
        FindTaskCommand expectedFindTaskCommand =
                new FindTaskCommand(new TaskNameContainsKeywordsPredicate(Arrays.asList("picnic", "meet")));
        assertParseSuccess(parser, "picnic / meet", expectedFindTaskCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n picnic \n \t / meet  \t", expectedFindTaskCommand);
    }

}
