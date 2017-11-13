package seedu.address.logic.parser;

//@@author wangyiming1019
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindTagCommand;
import seedu.address.model.person.NameContainsTagsPredicate;

public class FindTagCommandParserTest {

    private FindTagCommandParser parser = new FindTagCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsFindTagCommand() {
        FindTagCommand expectedFindTagCommand =
                new FindTagCommand(new NameContainsTagsPredicate(
                        Arrays.asList("friend", "colleague")));

        // no leading and trailing whitespaces
        assertParseSuccess(parser, "friend colleague", expectedFindTagCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n friend \n \t colleague  \t", expectedFindTagCommand);
    }


}
