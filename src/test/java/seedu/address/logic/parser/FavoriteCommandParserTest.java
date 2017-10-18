package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FavoriteCommand;

public class FavoriteCommandParserTest {

    private FavoriteCommandParser parser = new FavoriteCommandParser();

    @Test
    public void parse_validArgs_returnsFavoriteCommand() {
        assertParseSuccess(parser, "1", new FavoriteCommand(Arrays.asList(INDEX_FIRST_PERSON)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavoriteCommand.MESSAGE_USAGE));
    }
}
