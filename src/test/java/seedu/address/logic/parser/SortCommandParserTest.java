package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SortCommand;

//@@author khooroko
public class SortCommandParserTest {

    private static final String ORDERING_DEFAULT = "";
    private static final String ORDERING_NAME = "name";
    private static final String ORDERING_DEBT = "debt";
    private static final String ORDERING_CLUSTER = "cluster";
    private static final String ORDERING_DEADLINE = "deadline";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_invalidOrdering_failure() {

        // number
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // invalid string
        assertParseFailure(parser, "weight", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validOrdering_success() throws Exception {
        SortCommand expectedCommand;

        expectedCommand = new SortCommand(ORDERING_DEFAULT);
        assertParseSuccess(parser, ORDERING_DEFAULT, expectedCommand);

        expectedCommand = new SortCommand(ORDERING_NAME);
        assertParseSuccess(parser, ORDERING_NAME, expectedCommand);

        expectedCommand = new SortCommand(ORDERING_DEBT);
        assertParseSuccess(parser, ORDERING_DEBT, expectedCommand);

        expectedCommand = new SortCommand(ORDERING_CLUSTER);
        assertParseSuccess(parser, ORDERING_CLUSTER, expectedCommand);

        expectedCommand = new SortCommand(ORDERING_DEADLINE);
        assertParseSuccess(parser, ORDERING_DEADLINE, expectedCommand);
    }
}
