package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindByTagCommand;
import seedu.address.model.person.TagContainsKeywordsPredicate;

public class FindByTagCommandParserTest {

    private FindByTagCommandParser parser = new FindByTagCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindByTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsFindCommand() {
        // no leading and trailing whitespaces
        FindByTagCommand expectedCommand = new FindByTagCommand(
                new TagContainsKeywordsPredicate(Arrays.asList("friends", "family")));
        assertParseSuccess(parser, "friends family", expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n friends \n \t family \t", expectedCommand);
    }
}
