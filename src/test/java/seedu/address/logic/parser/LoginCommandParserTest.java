//@@author zenghou
package seedu.address.logic.parser;

import static junit.framework.TestCase.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class LoginCommandParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private LoginCommandParser parser = new LoginCommandParser();

    @Test
    public void parse_emptyArg_throwsParserException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        parser.parse("  ");
    }

    @Test
    public void parse_correctArg_returnCorrectCommand() throws Exception {
        // no leading or trailing white space
        Command cmd = parser.parse("login usr/test pwd/123456");
        assertEquals(cmd, new LoginCommand("test", "123456"));

        // with leading and trailing white space
        Command cmdWithSpace = parser.parse("login     usr/ test    pwd/ 123456");
        assertEquals(cmdWithSpace, new LoginCommand("test", "123456"));
    }
}
