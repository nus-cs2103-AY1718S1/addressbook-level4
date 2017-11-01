package seedu.address.logic.parser;
//@@author LeeYingZheng
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.tag.TagContainsKeywordsPredicate;


public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand =
                new FilterCommand(new TagContainsKeywordsPredicate(Arrays.asList("friend", "classmate")));
        assertParseSuccess(parser, "friend classmate", expectedFilterCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n friend \n \t classmate  \t", expectedFilterCommand);
    }

}
