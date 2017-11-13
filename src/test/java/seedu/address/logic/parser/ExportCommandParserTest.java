//@@author edwinghy
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RANGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import static seedu.address.testutil.TypicalPath.PATH_EXPORT;
import static seedu.address.testutil.TypicalRange.RANGE_ALL;

import org.junit.Test;

import seedu.address.logic.commands.ExportCommand;

public class ExportCommandParserTest {
    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_export_success() throws Exception {

        String userInput = " " + PREFIX_RANGE + RANGE_ALL + " " + PREFIX_PATH + PATH_EXPORT;
        ExportCommand expectedCommand = new ExportCommand(RANGE_ALL, PATH_EXPORT);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
//@@author

