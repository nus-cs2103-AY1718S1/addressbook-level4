package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.model.tag.Tag;


public class RemoveCommandParserTest {

    private RemoveCommandParser parser = new RemoveCommandParser();

    @Test
    public void parse_validArgsWithIndex_returnsRemoveTagCommand() throws Exception {
        Tag tagToRemove = new Tag("friends");
        Set<Index> indexSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tagToRemove);
        indexSet.add(INDEX_SECOND_PERSON);
        RemoveTagCommand removeCommand = new RemoveTagCommand(tagSet, indexSet, "2");
        assertParseSuccess(parser, "friends 2", removeCommand);
    }

    @Test
    public void parse_validArgsNoIndex_returnsRemoveTagCommand() throws Exception {
        Tag tagToRemove = new Tag("friends");
        Set<Index> indexSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tagToRemove);
        RemoveTagCommand removeCommand = new RemoveTagCommand(tagSet, indexSet, "");
        assertParseSuccess(parser, "friends", removeCommand);
    }

    @Test
    public void parse_invalidArgsNoIndex_throwsParseException() {
        assertParseFailure(parser, "?", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsWithIndex_throwsParseException() {
        assertParseFailure(parser, "? 2", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsWithInvalidIndex_throwsParseException() {
        assertParseFailure(parser, "friends 0", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveTagCommand.MESSAGE_USAGE));
    }

}
