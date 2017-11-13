package seedu.address.logic.parser;
//@@author Esilocke
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.TASK_SEPARATOR;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import org.junit.Test;

import seedu.address.logic.commands.DeleteTaskCommand;

public class DeleteTaskCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parseTaskValidArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, TASK_SEPARATOR + "1",
                new DeleteTaskCommand(INDEX_FIRST_TASK));
    }

    @Test
    public void parseTaskInvalidArgs_throwsParseException() {
        assertParseFailure(parser, TASK_SEPARATOR + "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE));
    }
}
