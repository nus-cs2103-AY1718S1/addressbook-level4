package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindByEmailCommand;
import seedu.address.model.person.EmailContainsKeywordsPredicate;

public class FindByEmailCommandParserTest {

    private FindByEmailCommandParser parser = new FindByEmailCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindByEmailCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsFindCommand() {
        // no leading and trailing whitespaces
        FindByEmailCommand expectedCommand = new FindByEmailCommand(
                new EmailContainsKeywordsPredicate(Arrays.asList("alice@mail.com", "bob@m.com")));
        assertParseSuccess(parser, "alice@mail.com bob@m.com", expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n alice@mail.com \n \t bob@m.com \t", expectedCommand);
    }

}
