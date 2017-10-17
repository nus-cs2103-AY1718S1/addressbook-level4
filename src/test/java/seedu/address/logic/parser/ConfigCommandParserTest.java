package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CONFIG_ADD_PROPERTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONFIG_IMPORT_CALENDER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONFIG_NEW_PROPERTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONFIG_TAG_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONFIG_URL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
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
}
