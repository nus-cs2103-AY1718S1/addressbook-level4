//@@author edwinghy
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPath.PATH_IMPORT;

import org.junit.Test;

import seedu.address.logic.commands.ImportCommand;

public class ImportCommandParserTest {
    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_path_success() throws Exception {

        String userInput = " " + PREFIX_PATH + PATH_IMPORT;
        ImportCommand expectedCommand = new ImportCommand(PATH_IMPORT);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
//@@author

