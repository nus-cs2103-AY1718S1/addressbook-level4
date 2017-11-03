package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindByAddressCommand;
import seedu.address.model.person.AddressContainsKeywordsPredicate;

//@@author YuchenHe98
public class FindByAddressCommandParserTest {

    private FindByAddressCommandParser parser = new FindByAddressCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindByAddressCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsFindCommand() {
        // no leading and trailing whitespaces
        FindByAddressCommand expectedCommand =
                new FindByAddressCommand(new AddressContainsKeywordsPredicate(Arrays.asList("street", "raffles")));
        assertParseSuccess(parser, "street raffles", expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n street \n \t raffles  \t", expectedCommand);
    }

}
