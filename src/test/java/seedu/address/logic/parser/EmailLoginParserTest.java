package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.logic.commands.EmailLoginCommand;

import static org.junit.Assert.*;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

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
