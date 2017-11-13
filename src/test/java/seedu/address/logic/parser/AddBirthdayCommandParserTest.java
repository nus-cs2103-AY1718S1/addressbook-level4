package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddBirthdayCommand;
import seedu.address.model.person.Birthday;

//@@author jacoblipech
public class AddBirthdayCommandParserTest {

    private AddBirthdayCommandParser parser = new AddBirthdayCommandParser();

    @Test
    public void parse_validArgs_returnsBirthdayCommand() throws IllegalValueException {

        Birthday toAdd = new Birthday(VALID_BIRTHDAY_AMY);
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_BIRTHDAY.toString() + VALID_BIRTHDAY_AMY;
        assertParseSuccess(parser, userInput, new AddBirthdayCommand(INDEX_FIRST_PERSON, toAdd));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddBirthdayCommand.MESSAGE_USAGE));
    }
}
