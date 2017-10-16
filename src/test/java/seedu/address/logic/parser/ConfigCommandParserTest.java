package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CONFIG_ADD_PROPERTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONFIG_NEW_PROPERTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONFIG_TAG_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.configCommands.ConfigCommand.ConfigType.ADD_PROPERTY;
import static seedu.address.logic.commands.configCommands.ConfigCommand.ConfigType.TAG_COLOR;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.configCommands.ConfigCommand;

public class ConfigCommandParserTest {
    private ConfigCommandParser parser = new ConfigCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, VALID_CONFIG_TAG_COLOR + VALID_TAG_HUSBAND + VALID_TAG_COLOR,
                new ConfigCommand(TAG_COLOR, "husband #7db9a1"));

        assertParseSuccess(parser, VALID_CONFIG_ADD_PROPERTY + VALID_CONFIG_NEW_PROPERTY,
                new ConfigCommand(ADD_PROPERTY, "1"));
    }
}
