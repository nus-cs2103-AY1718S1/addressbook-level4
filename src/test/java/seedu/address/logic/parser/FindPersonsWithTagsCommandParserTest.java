//@@author PokkaKiyo
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindPersonsWithTagsCommand;
import seedu.address.model.person.PersonContainsTagsPredicate;

public class FindPersonsWithTagsCommandParserTest {

    private FindPersonsWithTagsCommandParser parser = new FindPersonsWithTagsCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonsWithTagsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_singularToPluralLeniency() {
        FindPersonsWithTagsCommand expectedFindPersonsWithTagsCommand =
                new FindPersonsWithTagsCommand(new PersonContainsTagsPredicate(
                        Arrays.asList("friend", "friends")));
        //input of args in singular form should return command with both singular and plural form
        assertParseSuccess(parser, "friend", expectedFindPersonsWithTagsCommand);
    }

    @Test
    public void parse_pluralToSingularLeniency() {
        FindPersonsWithTagsCommand expectedFindPersonsWithTagsCommand =
                new FindPersonsWithTagsCommand(new PersonContainsTagsPredicate(
                        Arrays.asList("friends", "friend")));
        //input of args in plural form should return command with both singular and plural form
        assertParseSuccess(parser, "friends", expectedFindPersonsWithTagsCommand);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        FindPersonsWithTagsCommand expectedFindPersonsWithTagsCommand =
                new FindPersonsWithTagsCommand(new PersonContainsTagsPredicate(
                        Arrays.asList("friend", "friends", "colleague", "colleagues")));

        // no leading and trailing whitespaces
        assertParseSuccess(parser, "friend colleague", expectedFindPersonsWithTagsCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n friend \n \t colleague  \t", expectedFindPersonsWithTagsCommand);
    }
}
