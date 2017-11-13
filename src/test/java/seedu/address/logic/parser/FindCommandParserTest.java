package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
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
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    //@@author sofarsophie
    @Test
    public void parse_multiWhiteSpaceValidArgs_returnsFindCommand() {
        FindFields fieldsToFind = new FindFieldsBuilder().withName("Alice").build();
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(fieldsToFind));
        // Multiple whitespaces before and after keyword
        assertParseSuccess(parser, FindCommand.COMMAND_WORD + "     " + PREFIX_NAME + " Alice      \t",
                expectedFindCommand);
    }

    @Test
    public void parse_validDifferentFieldArgs_returnsFindCommand() {
        FindFields twoDifferentFields = new FindFieldsBuilder().withName("Alice")
                .withEmail("alice@example.com").build();
        // Two keywords from different fields
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(twoDifferentFields));
        assertParseSuccess(parser, FindCommand.COMMAND_WORD + " " + PREFIX_NAME + "Alice"
                + " " + PREFIX_EMAIL + "alice@example.com", expectedFindCommand);
    }

    @Test
    public void parse_validSameFieldArgs_returnsFindCommand() {
        FindFields twoSameFields = new FindFieldsBuilder().withPhone("99999999", "89898989").build();
        // Two keywords from the same field
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(twoSameFields));
        assertParseSuccess(parser, FindCommand.COMMAND_WORD + " " + PREFIX_PHONE + "99999999"
                + " " + PREFIX_PHONE + "89898989", expectedFindCommand);
    }
    //@@author

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // No arguments
        assertParseFailure(parser, FindCommand.COMMAND_WORD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        //@@author sofarsophie
        // Wrong prefix
        assertParseFailure(parser, FindCommand.COMMAND_WORD + " y/" + "Alice",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        //@@author
    }

}
