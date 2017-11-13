package seedu.room.logic.parser;

import static seedu.room.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.room.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.room.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.room.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.room.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.room.logic.commands.SwaproomCommand;

//@@author sushinoya
public class SwaproomCommandParserTest {

    private SwaproomCommandParser parser = new SwaproomCommandParser();

    @Test
    public void parse_validArgs_returnsSwapCommand() {
        assertParseSuccess(parser, " 1 2", new SwaproomCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwaproomCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidNumArgs_throwsParseException() {
        assertParseFailure(parser, " 1 3 4", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SwaproomCommand.MESSAGE_USAGE));
    }
}
