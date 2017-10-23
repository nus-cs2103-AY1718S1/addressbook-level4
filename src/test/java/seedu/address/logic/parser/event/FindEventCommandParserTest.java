//@@author A0162268B
package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.event.FindEventCommand;
import seedu.address.model.event.TitleContainsKeywordsPredicate;

/**
 * @@reginleiff
 */
public class FindEventCommandParserTest {
    private FindEventCommandParser parser = new FindEventCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindEventCommand() {
        // no leading and trailing whitespaces
        FindEventCommand expectedFindEventCommand =
                new FindEventCommand(new TitleContainsKeywordsPredicate(Arrays.asList("CS2103", "Midterm")));
        assertParseSuccess(parser, "CS2103 Midterm", expectedFindEventCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n CS2103 \n \t Midterm  \t", expectedFindEventCommand);
    }
}
