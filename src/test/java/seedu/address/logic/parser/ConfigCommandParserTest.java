package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONFIG_TYPE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONFIG_VALUE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NEW_PROPERTY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_URL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONFIG_ADD_PROPERTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONFIG_IMPORT_CALENDER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONFIG_NEW_PROPERTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONFIG_TAG_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONFIG_URL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.configs.AddPropertyCommand;
import seedu.address.logic.commands.configs.ChangeTagColorCommand;
import seedu.address.logic.commands.configs.ConfigCommand;
import seedu.address.logic.commands.configs.ImportCalenderCommand;

public class ConfigCommandParserTest {
    private ConfigCommandParser parser = new ConfigCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // Test for ChangeTagColorCommand.
        ConfigCommand expected = new ChangeTagColorCommand("husband #7db9a1", "husband", "#7db9a1");
        assertParseSuccess(parser, VALID_CONFIG_TAG_COLOR + VALID_TAG_HUSBAND + VALID_TAG_COLOR, expected);

        // Test for AddPropertyCommand.
        expected = new AddPropertyCommand("s/b f/birthday m/something r/[^\\s].*", "b",
                "birthday", "something", "[^\\s].*");
        assertParseSuccess(parser, VALID_CONFIG_ADD_PROPERTY + VALID_CONFIG_NEW_PROPERTY, expected);

        // Test for ImportCalenderCommand
        expected = new ImportCalenderCommand("https://www.url.com/");
        assertParseSuccess(parser, VALID_CONFIG_IMPORT_CALENDER + VALID_CONFIG_URL, expected);
    }

    @Test
    public void parse_invalidConfigType_expectException() {
        assertParseFailure(parser, INVALID_CONFIG_TYPE + INVALID_CONFIG_VALUE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConfigCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTagColor_expectException() {
        assertParseFailure(parser, VALID_CONFIG_TAG_COLOR + VALID_TAG_HUSBAND + INVALID_TAG_COLOR,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConfigCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidNewProperty_expectException() {
        assertParseFailure(parser, VALID_CONFIG_ADD_PROPERTY + INVALID_NEW_PROPERTY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConfigCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidUrl_expectException() {
        assertParseFailure(parser, VALID_CONFIG_IMPORT_CALENDER + INVALID_URL,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConfigCommand.MESSAGE_USAGE));
    }
}
