package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.ListByBloodtypeCommand;
import seedu.address.model.person.BloodtypeContainsKeywordPredicate;

//@@author Jeremy
public class ListByBloodtypeCommandParserTest {

    private ListByBloodtypeCommandParser parser = new ListByBloodtypeCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListByBloodtypeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsListByBloodtypeCommand() {
        // no leading and trailing whitespaces
        ListByBloodtypeCommand expectedListByBloodtypeCommand =
                new ListByBloodtypeCommand(new BloodtypeContainsKeywordPredicate(Arrays.asList("AB", "O-")));
        assertParseSuccess(parser, "AB O-", expectedListByBloodtypeCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n AB \n \t O-  \t", expectedListByBloodtypeCommand);
    }

}
