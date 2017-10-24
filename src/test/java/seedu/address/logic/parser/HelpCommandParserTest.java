package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.model.commandidentifier.CommandIdentifier;
import seedu.address.testutil.CommandIdentifierUtils;

public class HelpCommandParserTest {
    private HelpCommandParser parser = new HelpCommandParser();

    @Test
    public void parse_noArgument_success() {
        CommandIdentifier expectedCommandIdentifier = CommandIdentifierUtils.createCommandIdentifier("");

        assertParseSuccess(parser, "", new HelpCommand(expectedCommandIdentifier));
        assertParseSuccess(parser, "         ", new HelpCommand(expectedCommandIdentifier));
    }

    @Test
    public void parse_validIdentifier_success() {
        // valid command word
        CommandIdentifier commandIdentifierWord =
                CommandIdentifierUtils.createCommandIdentifier(DeleteCommand.COMMAND_WORD);

        assertParseSuccess(parser, DeleteCommand.COMMAND_WORD, new HelpCommand(commandIdentifierWord));

        // valid command alias
        CommandIdentifier commandIdentifierAlias =
                CommandIdentifierUtils.createCommandIdentifier(DeleteCommand.COMMAND_ALIAS);

        assertParseSuccess(parser, DeleteCommand.COMMAND_ALIAS, new HelpCommand(commandIdentifierAlias));
    }

    @Test
    public void parse_invalidArgument_failure() {
        assertParseFailure(parser, "delete delete",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIdentifier_failure() {
        // invalid command word
        assertParseFailure(parser, "evolve", CommandIdentifier.MESSAGE_COMMAND_WORD_CONSTRAINTS);

        // invalid alias
        assertParseFailure(parser, "z", CommandIdentifier.MESSAGE_COMMAND_WORD_CONSTRAINTS);
    }
}
