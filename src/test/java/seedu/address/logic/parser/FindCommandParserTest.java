package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    //@@author dalessr
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyPrefix_throwsParseException() {
        assertParseFailure(parser, "alice bob eve",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgAfterPrefix_throwsParseException() {
        assertParseFailure(parser, "n/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "n/ p/ e/ a/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "n/alice bob p/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "n/ p/12345678 e/alice@gmail.com",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_dummyValueBeforeFirstPrefix_throwsParseException() {
        assertParseFailure(parser, "alicen/alice bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "alice p/12345678",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommandOne =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("n/", "Alice", "Bob")));
        assertParseSuccess(parser, "n/Alice Bob", expectedFindCommandOne);
        FindCommand expectedFindCommandTwo =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("n/", "Alice", "p/", "12345678")));
        assertParseSuccess(parser, "n/Alice p/12345678", expectedFindCommandTwo);
        FindCommand expectedFindCommandThree =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("e/", "alice@com", "bob@com")));
        assertParseSuccess(parser, "e/ alice@com bob@com", expectedFindCommandThree);
        FindCommand expectedFindCommandFour =
                new FindCommand(new NameContainsKeywordsPredicate(
                        Arrays.asList("a/", "blk", "30", "Jurong", "East", "Street", "25,", "#21-31")));
        assertParseSuccess(parser, "a/blk 30 Jurong East Street 25, #21-31", expectedFindCommandFour);
    }

}
