package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CONFIG_ADD_PROPERTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONFIG_NEW_PROPERTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONFIG_TAG_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.configCommands.AddPropertyCommand;
import seedu.address.logic.commands.configCommands.ChangeTagColorCommand;

public class ConfigCommandParserTest {
    private ConfigCommandParser parser = new ConfigCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, VALID_CONFIG_TAG_COLOR + VALID_TAG_HUSBAND + VALID_TAG_COLOR,
                new ChangeTagColorCommand("husband #7db9a1", "husband", "#7db9a1"));

        assertParseSuccess(parser, VALID_CONFIG_ADD_PROPERTY + VALID_CONFIG_NEW_PROPERTY,
                new AddPropertyCommand("1"));
    }
}
