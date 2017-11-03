package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.MapCommand;

//@@author martyn-wong

public class MapCommandParserTest {

    private MapCommandParser parser = new MapCommandParser();

    @Test
    public void parse_validArgs_returnsMapCommand() throws Exception {
        assertParseSuccess(parser, "1", new MapCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws Exception {
        assertParseFailure(parser, "alex", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapCommand.MESSAGE_USAGE));
    }

}
