package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.tasks.FindTaskCommand;
import seedu.address.model.task.TaskContainsKeywordsPredicate;

public class FindTaskCommandParserTest {

    private FindTaskCommandParser parser = new FindTaskCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsFindCommand() {
        // no leading and trailing whitespaces
        FindTaskCommand expectedTaskFindCommand =
            new FindTaskCommand(new TaskContainsKeywordsPredicate(Arrays.asList("Finish", "art")));
        assertParseSuccess(parser, "Finish art", expectedTaskFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Finish \n \t art  \t", expectedTaskFindCommand);
    }

}
