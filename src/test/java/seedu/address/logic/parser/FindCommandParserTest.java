package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.PersonContainsKeywordsPredicate;
import seedu.address.model.person.PersonContainsKeywordsPredicate.FindFields;
import seedu.address.testutil.FindFieldsBuilder;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        FindFields fieldsToFind = new FindFieldsBuilder().withName("Alice", "Bob").build();
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(fieldsToFind));
        assertParseSuccess(parser, FindCommand.COMMAND_WORD + " " + PREFIX_NAME + "Alice"
                + " " + PREFIX_NAME + "Bob", expectedFindCommand);

        // multiple whitespaces before and after keyword
        assertParseSuccess(parser, FindCommand.COMMAND_WORD + " " + PREFIX_NAME + "\n Alice \t"
                + " " + PREFIX_NAME + "   \t \t Bob", expectedFindCommand);
    }

}
