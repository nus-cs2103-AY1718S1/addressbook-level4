package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONFIG_TYPE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONFIG_VALUE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NEW_PROPERTY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONFIG_ADD_PROPERTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONFIG_TAG_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NEW_PROPERTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NEW_PROPERTY_NO_REGEX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PREDEFINED_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ConfigCommandParser.COLOR_CODE_WRONG;
import static seedu.address.logic.parser.ConfigCommandParser.CONFIG_TYPE_NOT_FOUND;
import static seedu.address.model.property.PropertyManager.DEFAULT_MESSAGE;
import static seedu.address.model.property.PropertyManager.DEFAULT_REGEX;

import org.junit.Test;

import seedu.address.logic.commands.configs.AddPropertyCommand;
import seedu.address.logic.commands.configs.ChangeTagColorCommand;
import seedu.address.logic.commands.configs.ConfigCommand;

public class ConfigCommandParserTest {
    private ConfigCommandParser parser = new ConfigCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        // Test for ChangeTagColorCommand.
        ConfigCommand expected = new ChangeTagColorCommand("husband #7db9a1", "husband", "#7db9a1");
        assertParseSuccess(parser, VALID_CONFIG_TAG_COLOR + VALID_TAG_HUSBAND + VALID_TAG_COLOR, expected);

        // Test for AddPropertyCommand.
        expected = new AddPropertyCommand(VALID_NEW_PROPERTY.trim(), "b",
                "birthday", "something", "[^\\s].*");
        assertParseSuccess(parser, VALID_CONFIG_ADD_PROPERTY + VALID_NEW_PROPERTY, expected);

        // Test for AddPropertyCommand (without using customize constraintMessage and regex).
        expected = new AddPropertyCommand(VALID_NEW_PROPERTY_NO_REGEX.trim(), "m",
                "major", String.format(DEFAULT_MESSAGE, "major"), DEFAULT_REGEX);
        assertParseSuccess(parser, VALID_CONFIG_ADD_PROPERTY + VALID_NEW_PROPERTY_NO_REGEX, expected);
    }

    @Test
    public void parse_usePreDefinedColor_success() throws Exception {
        ConfigCommand expected = new ChangeTagColorCommand("husband red", "husband", "FF0000");
        assertParseSuccess(parser, VALID_CONFIG_TAG_COLOR + VALID_TAG_HUSBAND + VALID_PREDEFINED_COLOR, expected);
    }

    @Test
    public void parse_invalidConfigType_expectException() {
        assertParseFailure(parser, INVALID_CONFIG_TYPE + INVALID_CONFIG_VALUE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CONFIG_TYPE_NOT_FOUND));
    }

    @Test
    public void parse_invalidTagColor_expectException() {
        assertParseFailure(parser, VALID_CONFIG_TAG_COLOR + VALID_TAG_HUSBAND + INVALID_TAG_COLOR,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, COLOR_CODE_WRONG));
    }

    @Test
    public void parse_invalidNewProperty_expectException() {
        assertParseFailure(parser, VALID_CONFIG_ADD_PROPERTY + INVALID_NEW_PROPERTY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPropertyCommand.MESSAGE_USAGE));
    }
}
