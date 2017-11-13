//@@author conantteo
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.MULTIPLE_INDEX;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ExportCommand;

public class ExportCommandParserTest {

    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_validArgs_returnsExportCommand() {
        assertParseSuccess(parser, "1", prepareCommand(INDEX_FIRST_PERSON));
        assertParseSuccess(parser, "all", new ExportCommand());
        assertParseSuccess(parser, "1 2 3", new ExportCommand(MULTIPLE_INDEX));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatedArgs_throwsParseException() {
        assertParseFailure(parser, "1 1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ParserUtil.MESSAGE_INDEX_DUPLICATES));
    }

    /**
     * Returns a {@code ExportCommand} with the parameter {@code index}.
     * @param index is an index specified by user input.
     * @return a ExportCommand.
     */
    private ExportCommand prepareCommand(Index index) {
        Index[] indexArray = new Index[1];
        indexArray[0] = index;

        ExportCommand exportCommand = new ExportCommand(indexArray);

        return exportCommand;
    }
}
