package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.model.tag.Tag;

//@@author liliwei25
public class RemoveTagCommandParserTest {
    public static final String FIRST_INDEX = "1";
    private static final String VALID_INPUT = "test";
    private static final String VALID_FIRST_INPUT = "1 test";
    private static final String INVALID_INPUT = "";

    private RemoveTagCommandParser parser = new RemoveTagCommandParser();

    @Test
    public void parse_validArgs_returnsRemoveTagCommand() throws Exception {
        // remove tag from all person
        assertParseSuccess(parser, VALID_INPUT, new RemoveTagCommand(RemoveTagCommand.ALL, new Tag(VALID_INPUT)));

        // remove tag from first person
        assertParseSuccess(parser, VALID_FIRST_INPUT, new RemoveTagCommand(FIRST_INDEX, new Tag(VALID_INPUT)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, INVALID_INPUT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
    }
}
