package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.BirthdayRemoveCommand;

//@@author dalessr
/**
 * Test scope: similar to {@code SelectCommandParserTest}.
 * @see SelectCommandParserTest
 */
public class BirthdayRemoveCommandParserTest {

    private BirthdayRemoveCommandParser parser = new BirthdayRemoveCommandParser();

    public BirthdayRemoveCommandParserTest() {}

    @Test
    public void parse_validArgs_returnsBirthdayRemoveCommand() {
        assertParseSuccess(parser, "1", new BirthdayRemoveCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdayRemoveCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "2.2", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdayRemoveCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "aaa", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdayRemoveCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1abc", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdayRemoveCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 aaa", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdayRemoveCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 01/13/2000", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdayRemoveCommand.MESSAGE_USAGE));
    }
}
