package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_ALIAS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_ALIAS;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ALIAS_VALUE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COMMAND_VALUE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMMAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SetAliasCommand;

public class SetAliasCommandParserTest {
    private SetAliasCommandParser parser = new SetAliasCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // default entry
        assertParseSuccess(parser, SetAliasCommand.COMMAND_WORD + ALIAS_COMMAND + ALIAS_ALIAS,
                new SetAliasCommand(VALID_COMMAND, VALID_ALIAS));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetAliasCommand.MESSAGE_USAGE);

        // missing command prefix
        assertParseFailure(parser, SetAliasCommand.COMMAND_WORD + " " + VALID_COMMAND + ALIAS_ALIAS,
                expectedMessage);

        // missing alias prefix
        assertParseFailure(parser, SetAliasCommand.COMMAND_WORD + ALIAS_COMMAND + " " + VALID_ALIAS,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, SetAliasCommand.COMMAND_WORD + " " + VALID_COMMAND + " " + VALID_ALIAS,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage1 = String.format(MESSAGE_UNKNOWN_COMMAND);
        String expectedMessage2 = String.format(MESSAGE_DUPLICATE_ALIAS);

        // invalid command
        assertParseFailure(parser, SetAliasCommand.COMMAND_WORD + INVALID_COMMAND_VALUE + ALIAS_ALIAS,
                expectedMessage1);

        // protected command
        System.out.println(SetAliasCommand.COMMAND_WORD + ALIAS_COMMAND + INVALID_ALIAS_VALUE);
        assertParseFailure(parser, SetAliasCommand.COMMAND_WORD + ALIAS_COMMAND + INVALID_ALIAS_VALUE,
                expectedMessage2);
    }
}
