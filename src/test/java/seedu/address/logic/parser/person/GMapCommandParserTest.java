package seedu.address.logic.parser.person;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.person.GMapCommand;

public class GMapCommandParserTest {
    private final GMapCommandParser parser = new GMapCommandParser();

    @Test
    public void parse_validIndex_checkCorrectness() {
        Command expected = new GMapCommand((Index.fromOneBased(1)));
        assertParseSuccess(parser, " 1 ", expected);
    }

    @Test
    public void parse_invalidIndex_expectException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        assertParseFailure(parser, " -1 ", expectedMessage);
    }
}
