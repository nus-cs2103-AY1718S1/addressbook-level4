package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

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

        // Valid "AND"
        expectedListByTagCommand = new ListByTagCommand(
                new TagContainsKeywordsPredicate(Arrays.asList("Friend", "and", "Family")));
        assertParseSuccess(parser, "Friend and Family", expectedListByTagCommand);

        //Valid "OR"
        expectedListByTagCommand = new ListByTagCommand(
                new TagContainsKeywordsPredicate(Arrays.asList("Friend", "or", "Family")));
        assertParseSuccess(parser, "Friend or Family", expectedListByTagCommand);

        //Valid Combinational
        List<String> myStringArray = Arrays.asList("Friend", "or", "Family", "and", "Diabetic", "and",
                "Cousin", "or", "Female");
        expectedListByTagCommand = new ListByTagCommand(new TagContainsKeywordsPredicate(myStringArray));
        assertParseSuccess(parser, "Friend or Family and Diabetic and Cousin or Female",
                expectedListByTagCommand);

    }

    @Test
    public void parseInvalidArgs () {
        String invalidOutput = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListByTagCommand.MESSAGE_USAGE);

        // Empty args list
        assertParseFailure(parser, "    ", invalidOutput);

        // Starts with "AND"/"OR"
        assertParseFailure(parser, " and Friend Family", invalidOutput);
        assertParseFailure(parser, " or Friend Family", invalidOutput);

        // Ends with "AND"/"OR"
        assertParseFailure(parser, "Friend Family and", invalidOutput);
        assertParseFailure(parser, "Friend Family or", invalidOutput);

        // "AND" and "OR" cluster
        assertParseFailure(parser, "Friend and and family", invalidOutput);
        assertParseFailure(parser, "Friend or or family", invalidOutput);
        assertParseFailure(parser, "Friend and or family", invalidOutput);
        assertParseFailure(parser, "Friend or and family", invalidOutput);
    }

}
