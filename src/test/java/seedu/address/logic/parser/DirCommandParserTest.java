package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PLACE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PLACE;

import org.junit.Test;

import seedu.address.logic.commands.DirCommand;

//@@author Chng-Zhi-Xuan-reused
/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class DirCommandParserTest {

    private DirCommandParser parser = new DirCommandParser();

    @Test
    public void parse_validArgs_returnsDirCommand() {
        assertParseSuccess(parser, "1 2", new DirCommand(INDEX_FIRST_PLACE, INDEX_SECOND_PLACE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DirCommand.MESSAGE_USAGE));
    }
}
