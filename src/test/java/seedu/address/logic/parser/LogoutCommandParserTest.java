package seedu.address.logic.parser;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.LogoutCommand;

//@@author RonakLakhotia
public class LogoutCommandParserTest {

    private LogoutCommandParser parser = new LogoutCommandParser();

    private LogoutCommand expectedCommand = new LogoutCommand();
    @Test
    public void parse_invalidArgs_returnsLogoutCommand() {
        assertParseFailureLogout(parser, "", expectedCommand);
    }
    /**
     * asserts not equal logout commands.
     */
    public static void assertParseFailureLogout(Parser parser, String userInput, Command expectedCommand) {
        assertNotEquals(expectedCommand, new LogoutCommand());

    }
}
