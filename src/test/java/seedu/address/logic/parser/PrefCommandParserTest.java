package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.PrefCommand;
//@@author liuhang0213
public class PrefCommandParserTest {

    private PrefCommandParser parser = new PrefCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PrefCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_tooManyArgs_throwsParseException() {
        assertParseFailure(parser, "key value a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PrefCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsPrefCommand() {
        String prefKey = "AddressBookName";
        String prefValue = "NewName";
        PrefCommand expectedPrefCommand =
                new PrefCommand(prefKey, prefValue);
        assertParseSuccess(parser, prefKey + " " + prefValue, expectedPrefCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n " + prefKey + " \n \t " + prefValue + "  \t", expectedPrefCommand);
    }
}
