# cqhchan
###### \java\seedu\address\logic\commands\LogoutCommandTest.java
``` java
package seedu.address.logic.commands;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.ui.testutil.EventsCollectorRule;

public class LogoutCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_exit_success() {
        CommandResult result = new LogoutCommand().execute();
    }
}
```
###### \java\seedu\address\logic\parser\LoginCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_DESC_PASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.USERNAME_DESC_USERNAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_PASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_PRIVATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.LoginCommand;

public class LoginCommandParserTest {
    private LoginCommandParser parser = new LoginCommandParser();


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE);

        // missing username prefix
        assertParseFailure(parser, LoginCommand.COMMAND_WORD + VALID_USERNAME_PRIVATE
                + PASSWORD_DESC_PASSWORD , expectedMessage);

        // missing password prefix
        assertParseFailure(parser, LoginCommand.COMMAND_WORD + USERNAME_DESC_USERNAME
                + VALID_PASSWORD_PASSWORD , expectedMessage);

    }

}
```
