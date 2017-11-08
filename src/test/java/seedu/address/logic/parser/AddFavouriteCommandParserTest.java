package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.AddFavouriteCommand;

//@@author nassy93

public class AddFavouriteCommandParserTest {
    private AddFavouriteCommandParser parser = new AddFavouriteCommandParser();

    @Test
    public void validArgsReturnsAddFavouriteCommand() {
        assertParseSuccess(parser, "1", new AddFavouriteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void invalidArgsThrowsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddFavouriteCommand.MESSAGE_USAGE));
    }
}
