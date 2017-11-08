package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindByPhoneCommand;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;

public class FindByPhoneCommandParserTest {

    private FindByPhoneCommandParser parser = new FindByPhoneCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindByPhoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsFindCommand() {
        // no leading and trailing whitespaces
        FindByPhoneCommand expectedCommand =
                new FindByPhoneCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("street", "raffles")));
        assertParseSuccess(parser, "street raffles", expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n street \n \t raffles  \t", expectedCommand);
    }

}
