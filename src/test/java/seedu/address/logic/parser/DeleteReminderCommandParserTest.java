//@@author duyson98

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_REMINDER;

import org.junit.Test;

import seedu.address.logic.commands.DeleteReminderCommand;

public class DeleteReminderCommandParserTest {
    private DeleteReminderCommandParser parser = new DeleteReminderCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteReminderCommand() {
        assertParseSuccess(parser, "1", new DeleteReminderCommand(INDEX_FIRST_REMINDER));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteReminderCommand.MESSAGE_USAGE));
    }
}
