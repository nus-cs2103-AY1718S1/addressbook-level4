package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.VisualizeCommand;

//@@author YuchenHe98
public class VisualizeCommandParserTest {

    private VisualizeCommandParser parser = new VisualizeCommandParser();

    @Test
    public void parseValidArgsReturnsVisualizeCommand() {
        assertParseSuccess(parser, "1", new VisualizeCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parseInvalidArgsThrowsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                VisualizeCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                VisualizeCommand.MESSAGE_USAGE));
    }
}