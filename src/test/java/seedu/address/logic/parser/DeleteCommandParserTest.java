package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;

//@@author Pengyuz

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        ArrayList<Index> todelete = new ArrayList<>();
        todelete.add(INDEX_FIRST_PERSON);
        assertParseSuccess(parser, "I/1", new DeleteCommand(todelete));
    }

    @Test
    public void parse_validArgs_returnsDeleteCommand1() {
        ArrayList<Index> todelete = new ArrayList<>();
        todelete.add(INDEX_FIRST_PERSON);
        todelete.add(INDEX_SECOND_PERSON);
        assertParseSuccess(parser, "I/1 2", new DeleteCommand(todelete));
    }

    @Test
    public void parse_validArgs_returnsDeleteCommand2() {
        ArrayList<Index> todelete = new ArrayList<>();
        todelete.add(INDEX_FIRST_PERSON);
        assertParseSuccess(parser, "n/Alice Pauline", new DeleteCommand("Alice Pauline"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "I/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidstartArgs_throwsParseException() {
        assertParseFailure(parser, "aI/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException1() {
        assertParseFailure(parser, "n/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_invalidArgs_throwsParseException2() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
