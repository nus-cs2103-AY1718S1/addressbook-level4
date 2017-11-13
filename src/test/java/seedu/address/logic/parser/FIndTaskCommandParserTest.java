package seedu.address.logic.parser;
//@@author Esilocke
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.TASK_SEPARATOR;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindTaskCommand;
import seedu.address.model.task.TaskContainsKeywordPredicate;

public class FIndTaskCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, TASK_SEPARATOR + "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindTaskCommand expectedFindTaskCommand =
                new FindTaskCommand(new TaskContainsKeywordPredicate(Arrays.asList("Lucy", "Date")));
        assertParseSuccess(parser, TASK_SEPARATOR + "Lucy Date", expectedFindTaskCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, TASK_SEPARATOR + " \n Lucy \n \t Date  \t", expectedFindTaskCommand);
    }

}
