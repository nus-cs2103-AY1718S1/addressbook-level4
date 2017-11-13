package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_SUBJECT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_TAG;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_SUBJECT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.EmailSubject;

//@@author RonakLakhotia
public class EmailCommandParserTest {

    private EmailCommandParser parser = new EmailCommandParser();

    @Test
    public void parse_allFieldsPresent_faliure() throws ParseException {

        //multiple tags -> rejected
        assertParseFailure(parser, EmailCommand.COMMAND_WORD + EMAIL_TAG + " t/owesMoney"
                + EMAIL_SUBJECT, String.format(
                        EmailCommandParser.MULTIPLE_TAGS_FALIURE, EmailCommand.MESSAGE_USAGE
        ));

        //incorrect subject
        assertParseFailure(parser, EmailCommand.COMMAND_WORD + EMAIL_TAG + INVALID_EMAIL_SUBJECT,
                EmailSubject.MESSAGE_NAME_CONSTRAINTS);


    }
    @Test
    public void parse_compulsoryFieldMissing_failure() {

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE);

        //tags missing
        assertParseFailure(parser, EmailCommand.COMMAND_WORD + EMAIL_SUBJECT, expectedMessage);

        //subject missing
        assertParseFailure(parser, EmailCommand.COMMAND_WORD + EMAIL_TAG, expectedMessage);

        //both tags and subject missing
        assertParseFailure(parser, EmailCommand.COMMAND_WORD + " ", expectedMessage);
    }
}
