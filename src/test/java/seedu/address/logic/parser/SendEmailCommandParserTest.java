//@@author srishag
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_EMAIL_BODY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_EMAIL_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BODY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_SUBJECT_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.SendEmailCommand;

public class SendEmailCommandParserTest {
    private SendEmailCommandParser parser = new SendEmailCommandParser();

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SendEmailCommand.MESSAGE_USAGE);

        //both subject and body stated
        assertParseFailure(parser, SendEmailCommand.COMMAND_WORD + INDEX_FIRST_PERSON + VALID_EMAIL_SUBJECT_DESC
                + VALID_EMAIL_BODY_DESC, expectedMessage);
        //only body stated
        assertParseFailure(parser, SendEmailCommand.COMMAND_WORD + INDEX_FIRST_PERSON + EMPTY_EMAIL_SUBJECT_DESC
                + VALID_EMAIL_BODY_DESC, expectedMessage);
        //only subject stated
        assertParseFailure(parser, SendEmailCommand.COMMAND_WORD + INDEX_FIRST_PERSON + VALID_EMAIL_SUBJECT_DESC
                + EMPTY_EMAIL_BODY_DESC, expectedMessage);
        //neither subject nor body stated
        assertParseFailure(parser, SendEmailCommand.COMMAND_WORD + INDEX_FIRST_PERSON + EMPTY_EMAIL_SUBJECT_DESC
                + EMPTY_EMAIL_BODY_DESC, expectedMessage);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, SendEmailCommand.COMMAND_WORD + "a" + VALID_EMAIL_SUBJECT_DESC
                + VALID_EMAIL_BODY_DESC, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SendEmailCommand.MESSAGE_USAGE));
    }

}

