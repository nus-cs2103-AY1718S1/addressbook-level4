package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.RedoCommand.INDEX_ONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.COMMA_STRING;
import static seedu.address.logic.parser.ParserUtil.SPACE_COMMMA_STRING;
import static seedu.address.logic.parser.ParserUtil.SPACE_STRING;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EmailCommand;

//@@author justintkj
/**
 * Tests for all valid Email types of command.
 */
public class EmailCommandParserTest {

    public static final String SUBJECT_MESSAGE = "subject";
    public static final String BODY_MESSAGE = "body";
    public static final String ALPHABET_INDEX = "a";
    public static final String EXTRA_STRING = "extra";
    private EmailCommandParser parser = new EmailCommandParser();

    @Test
    public void parse_validArgs_returnsEmailCommand() {
        //VALID EMAIL FORMAT
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + COMMA_STRING + SUBJECT_MESSAGE + COMMA_STRING + BODY_MESSAGE;
        EmailCommand expectedCommand = new EmailCommand(INDEX_FIRST_PERSON, SUBJECT_MESSAGE, BODY_MESSAGE);
        assertParseSuccess(parser, userInput, expectedCommand);

        //VALID EMAIL FORMAT - extra whitespaces
        targetIndex = INDEX_FIRST_PERSON;
        userInput = SPACE_STRING + targetIndex.getOneBased() + SPACE_COMMMA_STRING + SUBJECT_MESSAGE + COMMA_STRING
                + BODY_MESSAGE;
        expectedCommand = new EmailCommand(INDEX_FIRST_PERSON, SUBJECT_MESSAGE, BODY_MESSAGE);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        //No Index selected
        assertParseFailure(parser, ALPHABET_INDEX, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EmailCommand.MESSAGE_USAGE));

        //Too many inputs
        assertParseFailure(parser, INDEX_ONE + COMMA_STRING + SUBJECT_MESSAGE + COMMA_STRING + BODY_MESSAGE
                + COMMA_STRING + EXTRA_STRING, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EmailCommand.MESSAGE_USAGE));

        //Too little input
        assertParseFailure(parser, INDEX_ONE + COMMA_STRING + SUBJECT_MESSAGE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
    }
}
