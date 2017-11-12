package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import org.junit.Test;

import seedu.address.logic.commands.ShowParticipantsCommand;

// @@author HouDenghao
public class ShowParticipantsCommandParserTest {

    private ShowParticipantsCommandParser parser = new ShowParticipantsCommandParser();

    @Test
    public void parseValidArgsReturnsShowParticipantsCommand() {
        assertParseSuccess(parser, "1", new ShowParticipantsCommand(INDEX_FIRST_EVENT));
    }

    @Test
    public void parseInvalidArgsThrowsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowParticipantsCommand.MESSAGE_USAGE));
    }
}
