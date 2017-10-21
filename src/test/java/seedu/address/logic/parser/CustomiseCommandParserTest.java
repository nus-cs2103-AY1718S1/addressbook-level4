package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.FONT_SIZE_DESC_SMALL;
import static seedu.address.logic.commands.CommandTestUtil.FONT_SIZE_DESC_XSMALL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FONT_SIZE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FONT_SIZE_SMALL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FONT_SIZE_XSMALL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.font.FontSize.MESSAGE_FONT_SIZE_CONSTRAINTS;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CustomiseCommand;
import seedu.address.model.font.FontSize;

public class CustomiseCommandParserTest {

    private CustomiseCommandParser parser = new CustomiseCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        try {
            FontSize fontSize = new FontSize(VALID_FONT_SIZE_SMALL);

            // multiple font sizes - last font size accepted
            assertParseSuccess(parser, CustomiseCommand.COMMAND_WORD + FONT_SIZE_DESC_XSMALL
                    + FONT_SIZE_DESC_SMALL, new CustomiseCommand(fontSize));

        } catch (IllegalValueException ile) {
            throw new AssertionError(MESSAGE_FONT_SIZE_CONSTRAINTS);
        }
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CustomiseCommand.MESSAGE_USAGE);

        // missing font size prefix
        assertParseFailure(parser, CustomiseCommand.COMMAND_WORD + VALID_FONT_SIZE_XSMALL, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid font size
        assertParseFailure(parser, CustomiseCommand.COMMAND_WORD
                + INVALID_FONT_SIZE_DESC, FontSize.MESSAGE_FONT_SIZE_CONSTRAINTS);
    }

}
