package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.MergeCommand;

public class MergeCommandParserTest {
    private MergeCommandParser parser = new MergeCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MergeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsMergeCommand() {
        // no leading and trailing whitespaces
        MergeCommand expectedMergeCommand = new MergeCommand("./some/data/path/file.xml");
        // leading and trailing whitespaces is not part of user input, but to accommodate for tokenizer
        assertParseSuccess(parser, "  \n    \t  ./some/data/path/file.xml   \n  \t",
                expectedMergeCommand);
    }

}
