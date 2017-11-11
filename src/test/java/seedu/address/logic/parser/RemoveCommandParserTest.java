package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.model.tag.Tag;

//@@author NabeelZaheer
public class RemoveCommandParserTest {

    private RemoveCommandParser parser = new RemoveCommandParser();

    @Test
    public void parse_validTagWithIndex_returnsRemoveTagCommand() throws Exception {
        Tag tagToRemove = new Tag("friends");
        Set<Index> indexSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tagToRemove);
        indexSet.add(INDEX_SECOND_PERSON);
        List<String> indexDisplay = new ArrayList<>();
        indexDisplay.add("2");
        RemoveTagCommand removeCommand = new RemoveTagCommand(tagSet, indexSet, indexDisplay);
        assertParseSuccess(parser, "2 friends", removeCommand);
    }

    @Test
    public void parse_validTagNoIndex_returnsRemoveTagCommand() throws Exception {
        Tag tagToRemove = new Tag("friends");
        Set<Index> indexSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tagToRemove);
        List<String> indexDisplay = new ArrayList<>();
        indexDisplay.add("");
        RemoveTagCommand removeCommand = new RemoveTagCommand(tagSet, indexSet, indexDisplay);
        assertParseSuccess(parser, "friends", removeCommand);
    }

    @Test
    public void parse_invalidArgsNoIndex_throwsParseException() {
        assertParseFailure(parser, "?", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTagWithValidIndex_throwsParseException() {
        assertParseFailure(parser, "2 ?", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validTagWithInvalidIndex_throwsParseException() {
        assertParseFailure(parser, "0 friends", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validTagInvalidRange1_throwsParseException() {
        assertParseFailure(parser, "1-2a friends", String.format("Invalid index range provided.\n"
                + MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_validTagInvalidRange3_throwsParseException() {
        assertParseFailure(parser, "3-2 friends", String.format("Invalid index range provided.\n"
                + MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validTagInvalidRange4_throwsParseException() {
        assertParseFailure(parser, "2 4-3 friends", String.format("Invalid index range provided.\n"
                + MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
    }

}
