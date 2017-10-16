package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CONFIG_TAG_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ConfigCommand;

public class ConfigCommandParserTest {
    private ConfigCommandParser parser = new ConfigCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, VALID_CONFIG_TAG_COLOR + VALID_TAG_HUSBAND + VALID_TAG_COLOR,
                new ConfigCommand("set-tag-color", "husband #7db9a1"));
    }
}
