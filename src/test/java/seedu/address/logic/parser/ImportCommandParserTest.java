package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_IMPORT_PATH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_IMPORT_TYPE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.ImportCommandParser.IMPORT_TYPE_NOT_FOUND;

import org.junit.Test;

public class ImportCommandParserTest {
    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {

    }

    @Test
    public void parse_invalidConfigType_expectException() {
        assertParseFailure(parser, INVALID_IMPORT_TYPE + INVALID_IMPORT_PATH,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, IMPORT_TYPE_NOT_FOUND));
    }
}
