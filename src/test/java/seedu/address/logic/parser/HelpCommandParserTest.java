package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.model.commandidentifier.CommandIdentifier;

public class HelpCommandParserTest {
    private HelpCommandParser parser = new HelpCommandParser();

//    @Test
//    public void parse_noArgument_returnsDeleteCommand() {
//        CommandIdentifier expectedCommandIdentifier = new CommandIdentifier("");
//        assertParseSuccess(parser, "", new HelpCommand(expectedCommandIdentifier));
//    }

//    @Test
//    public void parse_invalidArgs_throwsParseException() {
//        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
//    }
}
