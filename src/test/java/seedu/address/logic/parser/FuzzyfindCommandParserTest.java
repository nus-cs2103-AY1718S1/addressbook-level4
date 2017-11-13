package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FuzzyfindCommand;
import seedu.address.model.person.NameContainsSubstringsPredicate;

//@@author bokwoon95
public class FuzzyfindCommandParserTest {

    private FuzzyfindCommandParser parser = new FuzzyfindCommandParser();

    @Test
    //empty input should throw error
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FuzzyfindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FuzzyfindCommand expectedFuzzyfindCommand =
                new FuzzyfindCommand(new NameContainsSubstringsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFuzzyfindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFuzzyfindCommand);
    }

}
