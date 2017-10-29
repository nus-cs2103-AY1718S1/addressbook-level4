package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.IMPORT_NO_PATH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_IMPORT_PATH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_IMPORT_TYPE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NUSMODS_IMPORT;
import static seedu.address.logic.commands.CommandTestUtil.NOT_FROM_NUSMODS_IMPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUSMODS_IMPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUSMODS_URL;
import static seedu.address.logic.commands.imports.ImportNusmodsCommand.INVALID_URL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ImportCommandParser.IMPORT_TYPE_NOT_FOUND;

import java.net.URL;

import org.junit.Test;

import seedu.address.logic.commands.imports.ImportCommand;
import seedu.address.logic.commands.imports.ImportNusmodsCommand;

public class ImportCommandParserTest {
    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        ImportCommand expected = new ImportNusmodsCommand(new URL(VALID_NUSMODS_URL));
        assertParseSuccess(parser, VALID_NUSMODS_IMPORT, expected);
    }

    @Test
    public void parse_invalidConfigType_expectException() {
        assertParseFailure(parser, INVALID_IMPORT_TYPE + INVALID_IMPORT_PATH,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, IMPORT_TYPE_NOT_FOUND));
    }

    @Test
    public void parse_noPathSpecified_expectException() {
        assertParseFailure(parser, IMPORT_NO_PATH,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidUrlForNusMods_expectException() {
        assertParseFailure(parser, NOT_FROM_NUSMODS_IMPORT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportNusmodsCommand.MESSAGE_USAGE));

        assertParseFailure(parser, INVALID_NUSMODS_IMPORT, String.format(INVALID_URL, ""));
    }
}
