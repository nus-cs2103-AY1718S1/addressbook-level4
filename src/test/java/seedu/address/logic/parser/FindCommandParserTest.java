package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.FieldContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
    }
    //@@author NabeelZaheer
    @Test
    public void parse_validNameArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        List<String> keywords1 = Arrays.asList("Alice", "Bob");
        List<String> keywords2 = new ArrayList<>();
        List<String> keywords3 = new ArrayList<>();
        List<String> keywords4 = new ArrayList<>();
        List<String> keywords5 = new ArrayList<>();
        List<List<String>> list = new ArrayList<>();
        list.add(keywords1);
        list.add(keywords2);
        list.add(keywords3);
        list.add(keywords4);
        list.add(keywords5);
        FindCommand expectedFindCommand =
                new FindCommand(new FieldContainsKeywordsPredicate(list));
        assertParseSuccess(parser, " n/Alice n/Bob", expectedFindCommand);
    }

    @Test
    public void parse_missingInput_throwsParseException() {
        // multiple whitespaces between keywords
        assertParseFailure(parser, " n/Alice t/      ", "Missing input for field: t/ \n"
                + FindCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        assertParseFailure(parser, "alex", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
    }


}
