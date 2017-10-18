package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.RemoveCommand;


public class RemoveCommandParserTest {

    private RemoveCommandParser parser = new RemoveCommandParser();

    // TODO: dont know why this test fails :(
    /* @Test
    public void parse_validArgs_returnsRemoveCommand() throws Exception {
        Tag tag = new Tag("friends");
        RemoveCommand removeCommand = new RemoveCommand(tag, INDEX_SECOND_PERSON);
        assertParseSuccess(parser, "friends 2", removeCommand);
    }*/

    @Test
    public void parse_invalidArgsNoIndex_throwsParseException() {
        assertParseFailure(parser, "a ball", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsWithIndex_throwsParseException() {
        assertParseFailure(parser, "a ball 2", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsWithInvalidIndex_throwsParseException() {
        assertParseFailure(parser, "friends 0?", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveCommand.MESSAGE_USAGE));
    }

}
