package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.RemoveCommand;
import seedu.address.model.tag.Tag;


public class RemoveCommandParserTest {

    private RemoveCommandParser parser = new RemoveCommandParser();

    @Test
    public void parse_validArgsWithIndex_returnsRemoveCommand() throws Exception {
        Tag tag = new Tag("friends");
        RemoveCommand removeCommand = new RemoveCommand(tag, INDEX_SECOND_PERSON);
        assertParseSuccess(parser, "friends 2", removeCommand);
    }

    @Test
    public void parse_validArgsNoIndex_returnsRemoveCommand() throws Exception {
        Tag tag = new Tag("friends");
        RemoveCommand removeCommand = new RemoveCommand(tag, null);
        assertParseSuccess(parser, "friends", removeCommand);
    }

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
