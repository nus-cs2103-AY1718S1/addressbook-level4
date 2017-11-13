package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.email.EmailTask;
import seedu.address.logic.commands.EmailCommand;

//@@author awarenessxz
public class EmailCommandParserTest {
    private EmailCommandParser parser = new EmailCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String [] loginDetails = {"adam@gmail.com", "password"};

        //zero Augment -> fails
        assertParseFailure(parser, "email", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EmailCommand.MESSAGE_USAGE));

        //Empty Message -> fails
        assertParseFailure(parser, "email em/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EmailCommand.MESSAGE_USAGE));

        //Empty Subject -> fails
        assertParseFailure(parser, "email es/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EmailCommand.MESSAGE_USAGE));

        //Invalid Login Format - Multiple Arguments -fails
        assertParseFailure(parser, "email el/adam@gmail.com:password:testing",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));

        //Invalid Login Format - only login - fails
        assertParseFailure(parser, "email el/adam@gamil.com",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));

        //Invalid email task -- fails
        assertParseFailure(parser, "email et/testing",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));

        //Empty email task -- fails
        assertParseFailure(parser, "email et/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emailDraft_success() {
        String [] loginDetails = new String[0];
        EmailTask task = new EmailTask();
        String userInput = "";

        //valid email message
        userInput = "email em/message";
        EmailCommand expectedCommand = new EmailCommand("message", "", loginDetails, task);
        assertParseSuccess(parser, userInput, expectedCommand);

        //valid email subject
        userInput = "email es/subject";
        expectedCommand = new EmailCommand("", "subject", loginDetails, task);
        assertParseSuccess(parser, userInput, expectedCommand);

        //valid email message and subject
        userInput = "email em/message es/subject";
        expectedCommand = new EmailCommand("message", "subject", loginDetails, task);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_emailTask_success() {
        String [] loginDetails = new String[0];
        EmailTask task = new EmailTask("");
        String userInput = "";

        //valid task clear
        task.setTask("clear");
        userInput = "email et/clear";
        EmailCommand expectedCommand = new EmailCommand("", "", loginDetails, task);
        assertParseSuccess(parser, userInput, expectedCommand);

        //valid task send
        task.setTask("send");
        userInput = "email et/send";
        expectedCommand = new EmailCommand("", "", loginDetails, task);
        assertParseSuccess(parser, userInput, expectedCommand);

    }
}
