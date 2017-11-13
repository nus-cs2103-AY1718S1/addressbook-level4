package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.SuggestCommand.MESSAGE_SUCCESS;

import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author CindyTsai1
public class SuggestCommandParserTest {

    private SuggestCommandParser parser = new SuggestCommandParser();

    @Test
    public void parse_validCommand_returnsSuggestCommand() {
        String expectedMessage = String.format(MESSAGE_SUCCESS, "edit");

        //extra character at the back of the commandWord
        assertParseSuccess(parser, "editt", expectedMessage);

        //extra character at the front of the commandWord
        assertParseSuccess(parser, "eedit", expectedMessage);

        //extra character in the middle of the commandWord
        assertParseSuccess(parser, "ediit", expectedMessage);

        //missing character at the back of the commandWord
        assertParseSuccess(parser, "edi", expectedMessage);

        //missing character at the front of the commandWord
        assertParseSuccess(parser, "dit", expectedMessage);

        //missing character in the middle of the commandWord
        assertParseSuccess(parser, "edt", expectedMessage);

        //swapping character in the middle of the commandWord
        assertParseSuccess(parser, "eidt", expectedMessage);

        //swapping character at the back of the commandWord
        assertParseSuccess(parser, "edti", expectedMessage);

        //swapping character at the front of the commandWord
        assertParseSuccess(parser, "deit", expectedMessage);

        //wrong character in the middle of the commandWord
        assertParseSuccess(parser, "edet", expectedMessage);

        //wrong character at the back of the commandWord
        assertParseSuccess(parser, "edip", expectedMessage);

        //wrong character at the front of the commandWord
        assertParseSuccess(parser, "adit", expectedMessage);
    }

    @Test
    public void parse_invalidCommand_returnsfailure() {
        //suggested command not found
        assertParseSuccess(parser, "amend", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Asserts that the parsing of {@code command} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertParseSuccess(Parser parser, String command, String expectedMessage) {
        try {
            parser.parse(command).execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
        } catch (ParseException e) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, e.getMessage());
        }
    }
}
