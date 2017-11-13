//@@author tshradheya
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.io.IOException;

import org.junit.Test;

import seedu.address.logic.commands.DetailsCommand;

public class DetailsCommandParserTest {

    private DetailsCommandParser parser = new DetailsCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() throws IOException {
        assertParseSuccess(parser, "1", new DetailsCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws IOException {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DetailsCommand.MESSAGE_USAGE));
    }
}
