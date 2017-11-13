package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PLACE;

import org.junit.Test;

import seedu.address.logic.commands.BookmarkCommand;

//@@author Chng-Zhi-Xuan-reused
public class BookmarkCommandParserTest {

    private BookmarkCommandParser parser = new BookmarkCommandParser();

    @Test
    public void parse_validArgs_returnsBookmarkCommand() {
        assertParseSuccess(parser, "1", new BookmarkCommand(INDEX_FIRST_PLACE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, BookmarkCommand.MESSAGE_USAGE));
    }
}
