package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.BirthdayAddCommand;
import seedu.address.model.person.Birthday;

/**
 * Test scope: similar to {@code SelectCommandParserTest}.
 * @see SelectCommandParserTest
 */
public class BirthdayAddCommandParserTest {

    private BirthdayAddCommandParser parser = new BirthdayAddCommandParser();
    private Birthday birthday = new Birthday("01/01/2000");

    public BirthdayAddCommandParserTest() throws IllegalValueException {
    }

    @Test
    public void parse_validArgs_returnsBirthdayAddCommand() {
        assertParseSuccess(parser, "1 01/01/2000", new BirthdayAddCommand(INDEX_FIRST_PERSON, birthday));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdayAddCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdayAddCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "aaa", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdayAddCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 01012000000", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdayAddCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 01012000", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdayAddCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 0101/2000", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdayAddCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 32/01/2000", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdayAddCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 01/13/2000", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdayAddCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 01/01/3000", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdayAddCommand.MESSAGE_USAGE));
    }
}
