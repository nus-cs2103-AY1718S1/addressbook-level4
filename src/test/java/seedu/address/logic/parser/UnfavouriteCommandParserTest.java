package seedu.address.logic.parser;

//@@author wangyiming1019
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.UnfavouriteCommand;

public class UnfavouriteCommandParserTest {

    private UnfavouriteCommandParser parser = new UnfavouriteCommandParser();

    @Test
    public void parse_validArgs_returnsUnfavouriteCommand() {
        assertParseSuccess(parser, "1", new UnfavouriteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "u", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnfavouriteCommand.MESSAGE_USAGE));
    }
}
