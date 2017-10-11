package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.ListByTagCommand;
import seedu.address.model.person.TagContainsKeywordsPredicate;

public class ListByTagCommandParserTest {

    private ListByTagCommandParser parser = new ListByTagCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListByTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsListByTagCommand() {
        // no leading and trailing whitespaces
        ListByTagCommand expectedListByTagCommand =
                new ListByTagCommand(new TagContainsKeywordsPredicate(Arrays.asList("Friend", "Family")));
        assertParseSuccess(parser, "Friend Family", expectedListByTagCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Friend \n \t Family  \t", expectedListByTagCommand);
    }

}
