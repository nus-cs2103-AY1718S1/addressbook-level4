package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.BirthdayContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.TagListContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    //@@author k-l-a
    @Test
    public void parse_validNameArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);

        // use of prefix to indicate the property to match (name)
        assertParseSuccess(parser, "n/ Alice Bob", expectedFindCommand);
        assertParseSuccess(parser, "n/Alice Bob", expectedFindCommand); //no space between prefix & keyword

        expectedFindCommand = new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("a")));

        // a one-character keyword is correctly parsed as searching the name field
        assertParseSuccess(parser, "a", expectedFindCommand);

    }

    @Test
    public void parse_validTagsArgs_returnsFindCommand() {
        FindCommand expectedFindCommandForTags =
                new FindCommand(new TagListContainsKeywordsPredicate(Arrays.asList("Friends", "Family")));
        assertParseSuccess(parser, "t/ Friends Family", expectedFindCommandForTags);
        assertParseSuccess(parser, "t/Friends Family", expectedFindCommandForTags); //no space between prefix & keyword
    }

    //@@author nadhira15
    @Test
    public void parse_validPhoneArgs_returnsFindCommand() {
        FindCommand expectedFindCommandForPhone =
                new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("85355251")));
        assertParseSuccess(parser, "p/ 85355251", expectedFindCommandForPhone);
    }

    @Test
    public void parse_validEmailArgs_returnsFindCommand() {
        FindCommand expectedFindCommandForEmail =
                new FindCommand(new EmailContainsKeywordsPredicate(Arrays.asList("asdf@example.com")));
        assertParseSuccess(parser, "e/ asdf@example.com", expectedFindCommandForEmail);
    }

    @Test
    public void parse_validAddressArgs_returnsFindCommand() {
        FindCommand expectedFindCommandForAddress =
                new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList("clementi")));
        assertParseSuccess(parser, "a/ clementi", expectedFindCommandForAddress);
    }

    @Test
    public void parse_validBirthdayArgs_returnsFindCommand() {
        FindCommand expectedFindCommandForBirthday =
                new FindCommand(new BirthdayContainsKeywordsPredicate(Arrays.asList("01/01/1991")));
        assertParseSuccess(parser, "b/ 01/01/1991", expectedFindCommandForBirthday);
    }
    //@@author

}
