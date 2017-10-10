package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.RemoveTagParser;

public class RemoveTagCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void parse_emptyArg_throwsParseException() {
        RemoveTagParser parser = new RemoveTagParser();
        assertParseFailure(parser, "     ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
    }

}
