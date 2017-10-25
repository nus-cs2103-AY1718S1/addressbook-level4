package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.model.tag.Tag;


public class AddTagCommandParserTest {

    private AddTagCommandParser parser = new AddTagCommandParser();

    @Test
    public void parse_validArgsWithIndex_returnsAddTagCommand() throws Exception {
        Tag tagToAdd = new Tag("enemy");
        Set<Index> indexSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tagToAdd);
        indexSet.add(INDEX_SECOND_PERSON);
        AddTagCommand addTagCommand = new AddTagCommand(tagSet, indexSet);
        assertParseSuccess(parser, "enemy 2", addTagCommand);
    }

    @Test
    public void parse_validArgsWithIndexes_returnsAddTagCommand() throws Exception {
        Tag tagToAdd = new Tag("enemy");
        Set<Index> indexSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tagToAdd);
        indexSet.add(INDEX_FIRST_PERSON);
        indexSet.add(INDEX_SECOND_PERSON);
        AddTagCommand addTagCommand = new AddTagCommand(tagSet, indexSet);
        assertParseSuccess(parser, "enemy 1 2", addTagCommand);
    }

    @Test
    public void parse_invalidArgsNoIndex_throwsParseException() {
        assertParseFailure(parser, "?", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsWithIndex_throwsParseException() {
        assertParseFailure(parser, "? 2", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsWithInvalidIndex_throwsParseException() {
        assertParseFailure(parser, "friends 0", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsOrder_throwsParseException() {
        assertParseFailure(parser, "enemy 1 wendy", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsEmptyIndex_throwsParseException() {
        assertParseFailure(parser, "enemy", "Please provide at least one index.\n"
                + String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
    }

}
