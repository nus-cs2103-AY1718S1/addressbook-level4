package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.tag.Tag;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    //@@author April0616
    @Test
    public void parse_validArgsOnePerson_returnsDeleteCommand() {
        ArrayList<Index> deletePersonList = new ArrayList<>();
        deletePersonList.add(INDEX_FIRST_PERSON);
        assertParseSuccess(parser, "1", new DeleteCommand(deletePersonList));
    }

    //@@author April0616
    @Test
    public void parse_validArgsMultiplePersons_returnsDeleteCommand() {
        ArrayList<Index> deletePersonList = new ArrayList<>();
        deletePersonList.add(INDEX_FIRST_PERSON);
        deletePersonList.add(INDEX_SECOND_PERSON);
        deletePersonList.add(INDEX_THIRD_PERSON);
        assertParseSuccess(parser, "1, 2, 3", new DeleteCommand(deletePersonList));
    }


    //@@author April0616
    @Test
    public void parse_validArgsMultiplePersonsNoWhiteSpace_returnsDeleteCommand() {
        ArrayList<Index> deletePersonList = new ArrayList<>();
        deletePersonList.add(INDEX_FIRST_PERSON);
        deletePersonList.add(INDEX_THIRD_PERSON);
        assertParseSuccess(parser, "1,3", new DeleteCommand(deletePersonList));
    }

    //@@author April0616
    @Test
    public void parse_validArgsMultiplePersonsDuplicatedIndexes_returnsDeleteCommand() {
        ArrayList<Index> deletePersonList = new ArrayList<>();
        deletePersonList.add(INDEX_FIRST_PERSON);
        deletePersonList.add(INDEX_THIRD_PERSON);
        assertParseSuccess(parser, "1, 1, 3, 3", new DeleteCommand(deletePersonList));
    }
    //@@author April0616
    @Test
    public void parse_validArgsMultiplePersonsManyWhiteSpaces_returnsDeleteCommand() {
        ArrayList<Index> deletePersonList = new ArrayList<>();
        deletePersonList.add(INDEX_FIRST_PERSON);
        deletePersonList.add(INDEX_SECOND_PERSON);
        deletePersonList.add(INDEX_THIRD_PERSON);
        assertParseSuccess(parser, "  1  ,  2  , 3 ", new DeleteCommand(deletePersonList));
    }
    //@@author April0616
    @Test
    public void parse_validArgsMultiplePersonsSplitByWhiteSpace_returnsDeleteCommand() {
        ArrayList<Index> deletePersonList = new ArrayList<>();
        deletePersonList.add(INDEX_FIRST_PERSON);
        deletePersonList.add(INDEX_SECOND_PERSON);
        deletePersonList.add(INDEX_THIRD_PERSON);

        assertParseSuccess(parser, "  1 2 3 ", new DeleteCommand(deletePersonList));
    }
    //@@author April0616
    @Test
    public void parse_invalidArgsMultiplePersonsManyWhiteSpacesLessComma_returnsDeleteCommand() {
        ArrayList<Index> deletePersonList = new ArrayList<>();
        deletePersonList.add(INDEX_FIRST_PERSON);
        deletePersonList.add(INDEX_SECOND_PERSON);
        deletePersonList.add(INDEX_THIRD_PERSON);
        assertParseFailure(parser, "  1, 2 3 ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    //@@author nbriannl
    @Test
    public void parse_validArgsTag_returnsDeleteCommand() throws Exception {
        Set<Tag> expectedTagSet = Stream.of(new Tag("tag")).collect(Collectors.toSet());
        assertParseSuccess(parser, " t/tag", new DeleteCommand(expectedTagSet));
        Set<Tag> expectedTagSet2 = Stream.of(new Tag("tag"), new Tag("tags")).collect(Collectors.toSet());
        assertParseSuccess(parser, " t/tag t/tags", new DeleteCommand(expectedTagSet2));
    }

    //@@author nbriannl
    @Test
    public void parse_invalidArgsTag_failure() throws Exception {
        assertParseFailure(parser, " t/*",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " t/tag t/*",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " t/* t/*",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    //@@author
    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "0, -1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-1, -2, -3", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));
    }

}
