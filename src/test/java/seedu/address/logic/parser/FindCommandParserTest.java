package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.AddressContainsKeywordPredicate;
import seedu.address.model.person.EmailContainsKeywordPredicate;
import seedu.address.model.person.NameContainsKeywordPredicate;
import seedu.address.model.person.PersonContainsFieldsPredicate;
import seedu.address.model.person.PhoneContainsKeywordPredicate;
import seedu.address.model.person.TagsContainKeywordPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsFieldsPredicate(Arrays.asList(
                                        new NameContainsKeywordPredicate("Alice"),
                                        new TagsContainKeywordPredicate("friend"),
                                        new PhoneContainsKeywordPredicate("111"),
                                        new AddressContainsKeywordPredicate("Big Road"),
                                        new EmailContainsKeywordPredicate("email@email.com"))));

        assertParseSuccess(parser, "n/Alice t/friend a/Big Road e/email@email.com p/111", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser,
                " \n n/Alice a/Big Road\n  \ne/email@email.com\t t/friend   p/111\t", expectedFindCommand);
    }

}
