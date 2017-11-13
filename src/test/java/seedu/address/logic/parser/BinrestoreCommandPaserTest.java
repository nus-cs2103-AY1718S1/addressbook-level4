package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.BinrestoreCommand;
//@@author Pengyuz
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the BindeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class BinrestoreCommandPaserTest {

    private BinrestoreCommandParser parser = new BinrestoreCommandParser();

    @Test
    public void parse_validArgs_returnsBindresotreCommand() {
        ArrayList<Index> todelete = new ArrayList<>();
        todelete.add(INDEX_FIRST_PERSON);
        assertParseSuccess(parser, "1", new BinrestoreCommand(todelete));
    }

    @Test
    public void parse_twovalidArgs_returnsBindrestoreCommand() {
        ArrayList<Index> todelete = new ArrayList<>();
        todelete.add(INDEX_FIRST_PERSON);
        todelete.add(INDEX_SECOND_PERSON);
        assertParseSuccess(parser, "1 2", new BinrestoreCommand(todelete));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "I/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BinrestoreCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, BinrestoreCommand.MESSAGE_USAGE));
    }

}
