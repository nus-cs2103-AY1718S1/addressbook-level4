package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.BirthdayCommand;
import seedu.address.model.person.Birthday;

public class BirthdayCommandParserTest {
    private BirthdayCommandParser parser = new BirthdayCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final Birthday birthday = new Birthday("06-01-1995");

        //have birthdays
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_BIRTHDAY.toString() + birthday;
        BirthdayCommand expectedCommand = new BirthdayCommand(INDEX_FIRST_PERSON, birthday);
        assertParseSuccess(parser, userInput, expectedCommand);

        //no birthdays
        userInput = targetIndex.getOneBased() + " " + PREFIX_BIRTHDAY.toString();
        expectedCommand = new BirthdayCommand(INDEX_FIRST_PERSON, new Birthday(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, BirthdayCommand.MESSAGE_USAGE);

        //nothing at all
        assertParseFailure(parser, BirthdayCommand.COMMAND_WORD, expectedMessage);
    }
}
