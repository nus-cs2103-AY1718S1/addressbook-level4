package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.logic.commands.ArrangeCommand;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class ArrangeCommandParserTest {
    private ArrangeCommandParser parser = new ArrangeCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        int[] indexList = { 1, 2, 3, 4, 5 };

        assertParseSuccess(parser, "1 2 3 4 5", new ArrangeCommand(indexList));

        assertParseSuccess(parser, "5 4 3 2 1", new ArrangeCommand(indexList));

        assertParseSuccess(parser, "4 1 3 5 2", new ArrangeCommand(indexList));

    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, "a 1", MESSAGE_INVALID_COMMAND_FORMAT
                + ArrangeCommand.MESSAGE_USAGE);

        assertParseFailure(parser, "0 1", MESSAGE_INVALID_COMMAND_FORMAT
                + ArrangeCommand.MESSAGE_USAGE);

    }
}
