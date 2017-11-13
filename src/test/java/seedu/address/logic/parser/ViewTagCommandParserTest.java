//@@author tshradheya
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ViewTagCommand;
import seedu.address.model.person.PersonContainsTagPredicate;

public class ViewTagCommandParserTest {

    private ViewTagCommandParser parser = new ViewTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() throws Exception {
        assertParseFailure(parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewTagCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_validArgs_returnsViewTagCommand() throws Exception {

        ViewTagCommand expectedViewTagCommand =
                new ViewTagCommand(new PersonContainsTagPredicate("foo"));

        //Testing when no whitespaces trailing keywords
        assertParseSuccess(parser, "foo", expectedViewTagCommand);

        //Testing when whitespaces are present before or after keyword
        assertParseSuccess(parser, " \n  foo  ", expectedViewTagCommand);
    }

}
