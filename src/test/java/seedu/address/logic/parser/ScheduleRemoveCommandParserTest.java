package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ScheduleRemoveCommand;

//@@author eldriclim
public class ScheduleRemoveCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ScheduleRemoveCommandParser parser = new ScheduleRemoveCommandParser();

    private String fullArgs = " I/1";
    private String invalidIndex = " I/a";

    @Test
    public void parseSuccessTest() throws Exception {
        assertParseSuccess(parser, ScheduleRemoveCommand.COMMAND_WORD + fullArgs,
                new ScheduleRemoveCommand(new HashSet<>(Arrays.asList(new Index(0)))));

    }

    @Test
    public void parseFailureTest() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleRemoveCommand.MESSAGE_USAGE);

        assertParseFailure(parser, ScheduleRemoveCommand.COMMAND_WORD + invalidIndex, MESSAGE_INVALID_INDEX);

        assertParseFailure(parser, ScheduleRemoveCommand.COMMAND_WORD, expectedMessage);
    }

}
