# Hoang
###### \java\seedu\address\logic\commands\EmailLoginCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.EmailLoginCommand.MESSAGE_SUCCESS;

import org.junit.Test;

import seedu.address.email.Email;
import seedu.address.email.EmailManager;
import seedu.address.logic.commands.exceptions.CommandException;

public class EmailLoginCommandTest {
    private EmailLoginCommand command;
    private Email emailManager = new EmailManager();

    //test for wrong email / password
    @Test
    public void wrongCredentials() throws CommandException {
        command = new EmailLoginCommand("phungtuanhoang1996@gmail.com", "thispasswordiswrong");
        command.setData(null, null, null, emailManager);
        CommandResult result = command.execute();
        assertEquals(EmailLoginCommand.MESSAGE_FAILED + EmailManager.MESSAGE_LOGIN_FAILED,
                result.feedbackToUser);
    }

    //test for correct email / password
    @Test
    public void correctCredentials() throws CommandException {
        String email = "cs2103testacc@gmail.com";
        command = new EmailLoginCommand(email, "testpass");
        command.setData(null, null, null, emailManager);
        CommandResult result = command.execute();
        assertEquals(MESSAGE_SUCCESS + email,
                result.feedbackToUser);
    }
}
```
###### \java\seedu\address\logic\commands\EmailLoginCommandTest.java
``` java

```
###### \java\seedu\address\logic\commands\EmailSendCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.email.Email;
import seedu.address.email.EmailManager;
import seedu.address.email.exceptions.LoginFailedException;
import seedu.address.logic.commands.exceptions.CommandException;

public class EmailSendCommandTest {
    private EmailSendCommand command;
    private Email emailManager = new EmailManager();

    //Test case of not logged in
    @Test
    public void notLoggedIn() throws CommandException {
        command = new EmailSendCommand(new String[]{"cs2103testacc@gmail.com"}, "Title", "Body");
        command.setData(null, null, null, emailManager);

        CommandResult result = command.execute();
        assertEquals("No email logged in", result.feedbackToUser);
    }

    //Test case of invalid recipients address
    @Test
    public void notValidRecipients() throws LoginFailedException, CommandException {
        //logged in email
        emailManager.login("cs2103testacc@gmail.com", "testpass");

        command = new EmailSendCommand(new String[]{"cs2103testacc;adffd"}, "Title", "Body");
        command.setData(null, null, null, emailManager);

        CommandResult result = command.execute();
        assertEquals("One or more of the given emails is not valid", result.feedbackToUser);
    }
}
```
###### \java\seedu\address\logic\commands\EmailSendCommandTest.java
``` java

```
###### \java\seedu\address\logic\commands\ExportCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.ExportCommand.MESSAGE_ACCESS_DENIED;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.GuiUnitTest;

public class ExportCommandTest extends GuiUnitTest {
    private Model model;
    private String os;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        os = System.getProperty("os.name").toLowerCase();

    }

    @Test
    public void accessDeniedFolder() throws CommandException {
        //when trying to create parent folder
        ExportCommand command = new ExportCommand(".txt", "C:/Windows/a");
        command.setData(model, null, null, null);
        if (os.indexOf("win") > 0) {
            assertEquals(command.execute(), new CommandException(MESSAGE_ACCESS_DENIED));
        }
    }

    @Test
    public void accessDeniedFile() throws CommandException {
        //when trying to create file
        ExportCommand command = new ExportCommand(".txt", "C:/Windows/a");
        command.setData(model, null, null, null);
        if (os.indexOf("win") > 0) {
            assertEquals(command.execute(), new CommandException(MESSAGE_ACCESS_DENIED));
        }
    }

    @Test
    public void success() throws CommandException {
        ExportCommand command = new ExportCommand(".txt", "C:/a");
        command.setData(model, null, null, null);
        assertTrue(command.execute().feedbackToUser.equals(new CommandResult(MESSAGE_SUCCESS).feedbackToUser));
    }
}
```
###### \java\seedu\address\logic\commands\ExportCommandTest.java
``` java

```
###### \java\seedu\address\logic\parser\EmailLoginParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.EmailLoginCommand;

public class EmailLoginParserTest {
    private EmailLoginParser parser = new EmailLoginParser();

    //wrong arguments testing
    @Test
    public void parse_wrongArguments() {
        //missing arguments
        assertParseFailure(parser, "email_login \"a@gmail.com\"",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailLoginCommand.MESSAGE_USAGE));

        //excessive arguments
        assertParseFailure(parser, "email_login \"a@gmail.com\" \"password\" \"password\"",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailLoginCommand.MESSAGE_USAGE));

        //wrong arguments
        assertParseFailure(parser, "email_login a@gmail.com \"password\"",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailLoginCommand.MESSAGE_USAGE));
    }

    //invalid email testing
    @Test
    public void parse_invalidEmail() {
        assertParseFailure(parser, "email_login \"agmail\" \"password\"",
               EmailLoginCommand.MESSAGE_INVALID_EMAIL);
    }
}
```
###### \java\seedu\address\logic\parser\EmailLoginParserTest.java
``` java

```
###### \java\seedu\address\logic\parser\EmailSendParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.email.Email;
import seedu.address.email.EmailManager;
import seedu.address.logic.commands.EmailSendCommand;

public class EmailSendParserTest {
    private EmailSendParser parser = new EmailSendParser();
    private Email emailManager = new EmailManager();

    //test for missing / excessive arguments
    @Test
    public void parse_wrongArguments() {
        //missing arguments
        assertParseFailure(parser, "email_send \"a@a.a\"",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailSendCommand.MESSAGE_USAGE));

        //excessive arguments
        assertParseFailure(parser, "email_send \"a@a.a\" \"title\" \"title\" \"body\"",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailSendCommand.MESSAGE_USAGE));

        //not putting in quotation mark
        assertParseFailure(parser, "email_send a@a.a title title \"body\"",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailSendCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\EmailSendParserTest.java
``` java

```
###### \java\seedu\address\logic\parser\ExportCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_FILE_TYPE_NOT_SUPPORTED;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.ExportCommand;

public class ExportCommandParserTest {
    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_wrongFileType() {
        assertParseFailure(parser, "export .pdf C:/", MESSAGE_FILE_TYPE_NOT_SUPPORTED);
    }

    @Test
    public void parse_missingArguments() {
        assertParseFailure(parser, "export C:/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ExportCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\ExportCommandParserTest.java
``` java

```
