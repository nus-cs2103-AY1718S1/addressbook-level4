package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.email.Email;
import seedu.address.email.EmailManager;
import seedu.address.logic.commands.EmailSendCommand;


import static org.junit.Assert.*;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

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
