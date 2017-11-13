package seedu.address.logic.parser;
//@@author Esilocke
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import org.junit.Test;

import seedu.address.logic.commands.SetCompleteCommand;

public class SetCompleteCommandParserTest {

    private SetTaskCompleteCommandParser parser = new SetTaskCompleteCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, " 1",
                new SetCompleteCommand(INDEX_FIRST_TASK));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " -1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCompleteCommand.MESSAGE_USAGE));
    }
}
