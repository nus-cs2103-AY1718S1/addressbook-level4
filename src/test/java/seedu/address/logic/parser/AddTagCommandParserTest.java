package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.model.tag.Tag;

//@@author NabeelZaheer
public class AddTagCommandParserTest {

    private AddTagCommandParser parser = new AddTagCommandParser();

    @Test
    public void parse_singleTagWithIndex_returnsAddTagCommand() throws Exception {
        Tag tagToAdd = new Tag("enemy");
        Set<Index> indexSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tagToAdd);
        indexSet.add(INDEX_SECOND_PERSON);
        AddTagCommand addTagCommand = new AddTagCommand(tagSet, indexSet, "2");
        assertParseSuccess(parser, "2 enemy", addTagCommand);
    }

    @Test
    public void parse_singleTagWithRange_returnsAddTagCommand() throws Exception {
        Tag tagToAdd = new Tag("enemy");
        Set<Index> indexSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tagToAdd);
        indexSet.add(INDEX_SECOND_PERSON);
        indexSet.add(INDEX_THIRD_PERSON);
        indexSet.add(INDEX_FOURTH_PERSON);
        AddTagCommand addTagCommand = new AddTagCommand(tagSet, indexSet, "2, 3, 4");
        assertParseSuccess(parser, "2-4 enemy", addTagCommand);
    }

    @Test
    public void parse_singleTagWithMultipleRange_returnsAddTagCommand() throws Exception {
        Tag tagToAdd = new Tag("enemy");
        Set<Index> indexSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tagToAdd);
        indexSet.add(INDEX_FIRST_PERSON);
        indexSet.add(INDEX_SECOND_PERSON);
        indexSet.add(INDEX_THIRD_PERSON);
        indexSet.add(INDEX_FOURTH_PERSON);
        AddTagCommand addTagCommand = new AddTagCommand(tagSet, indexSet, "1, 2, 3, 4");
        assertParseSuccess(parser, "2-4 4 1-3 enemy", addTagCommand);
    }

    @Test
    public void parse_multipleTagsWithIndex_returnsAddTagCommand() throws Exception {
        Tag tagToAdd1 = new Tag("enemy");
        Tag tagToAdd2 = new Tag("singer");
        Set<Index> indexSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tagToAdd1);
        tagSet.add(tagToAdd2);
        indexSet.add(INDEX_SECOND_PERSON);
        AddTagCommand addTagCommand = new AddTagCommand(tagSet, indexSet, "2");
        assertParseSuccess(parser, "2 enemy singer", addTagCommand);
    }

    @Test
    public void parse_singleTagWithIndexes_returnsAddTagCommand() throws Exception {
        Tag tagToAdd = new Tag("enemy");
        Set<Index> indexSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tagToAdd);
        indexSet.add(INDEX_FIRST_PERSON);
        indexSet.add(INDEX_SECOND_PERSON);
        AddTagCommand addTagCommand = new AddTagCommand(tagSet, indexSet, "1, 2");
        assertParseSuccess(parser, "1 2 enemy", addTagCommand);
    }

    @Test
    public void parse_singleTagWithIndexesAndRange_returnsAddTagCommand() throws Exception {
        Tag tagToAdd = new Tag("enemy");
        Set<Index> indexSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tagToAdd);
        indexSet.add(INDEX_FIRST_PERSON);
        indexSet.add(INDEX_SECOND_PERSON);
        indexSet.add(INDEX_THIRD_PERSON);
        AddTagCommand addTagCommand = new AddTagCommand(tagSet, indexSet, "1, 2, 3");
        assertParseSuccess(parser, "1 2 1-3 enemy", addTagCommand);
    }

    @Test
    public void parse_multipleTagsWithIndexes_returnsAddTagCommand() throws Exception {
        Tag tagToAdd1 = new Tag("enemy");
        Tag tagToAdd2 = new Tag("singer");
        Set<Index> indexSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tagToAdd1);
        tagSet.add(tagToAdd2);
        indexSet.add(INDEX_FIRST_PERSON);
        indexSet.add(INDEX_SECOND_PERSON);
        AddTagCommand addTagCommand = new AddTagCommand(tagSet, indexSet, "1, 2");
        assertParseSuccess(parser, "1 2 enemy singer", addTagCommand);
    }

    @Test
    public void parse_invalidTagNoIndex_throwsParseException() {
        assertParseFailure(parser, "?", String.format("Please provide an input for index.\n"
                + MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTagValidIndex_throwsParseException() {
        assertParseFailure(parser, "2", String.format("Please provide an input for tag\n"
                + MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validTagWithInvalidIndex_throwsParseException() {
        assertParseFailure(parser, "0 friends", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTagWithValidIndex_throwsParseException() {
        assertParseFailure(parser, "2 ?", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTagsOrder_throwsParseException() {
        assertParseFailure(parser, "1 enemy 3", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsEmptyIndex_throwsParseException() {
        assertParseFailure(parser, "enemy", "Please provide an input for index.\n"
                + String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidRange1_throwsParseException() {
        assertParseFailure(parser, "4-2 enemy", "Invalid index range provided.\n"
                + String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidRange2_throwsParseException() {
        assertParseFailure(parser, "4-a enemy", "Invalid index range provided.\n"
                + String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidRange3_throwsParseException() {
        assertParseFailure(parser, "1 4-2 enemy", "Invalid index range provided.\n"
                + String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidMultipleRange_throwsParseException() {
        assertParseFailure(parser, " 2-4 4-a enemy", "Invalid index range provided.\n"
                + String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
    }


}
