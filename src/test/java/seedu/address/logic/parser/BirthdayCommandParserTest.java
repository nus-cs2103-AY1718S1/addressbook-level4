package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDAY_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.person.Birthday.MESSAGE_LATE_DATE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import seedu.address.logic.commands.BirthdayCommand;
import seedu.address.model.person.Birthday;

//@@author liliwei25
/**
 * Test BirthdayCommandParser
 */
public class BirthdayCommandParserTest {

    private static final String INPUT_INVALID_INDEX = " -1 12-12-2012";
    private static final String INPUT_INVALID_DATE = " 1 0000";
    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final int ONE_DAY_LATER = 1;
    private static final String INDEX_ONE = " 1 ";
    private static final String INPUT_MISSING_INDEX = "12-12-2012";
    private static final String INPUT_MISSING_BIRTHDAY = " 1 ";
    private static final String INPUT_CORRECT_FORMAT = " 1 " + "30-03-2002";
    private BirthdayCommandParser parser = new BirthdayCommandParser();

    @Test
    public void parse_validArgs_returnsBirthdayCommand() {
        assertParseSuccess(parser, INPUT_CORRECT_FORMAT, new BirthdayCommand(INDEX_FIRST_PERSON, BIRTHDAY_BOB));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // no birthday input
        assertParseFailure(parser, INPUT_MISSING_BIRTHDAY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, BirthdayCommand.MESSAGE_USAGE));

        // invalid input
        assertParseFailure(parser, INPUT_MISSING_INDEX, ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidValue_failure() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        // invalid date
        assertParseFailure(parser, INPUT_INVALID_DATE, Birthday.MESSAGE_WRONG_DATE);

        // invalid birthday (input date after current date)
        assertParseFailure(parser, INDEX_ONE + LocalDate.now().plusDays(ONE_DAY_LATER).format(formatter),
                String.format(MESSAGE_LATE_DATE, LocalDate.now().format(formatter)));

        // invalid index
        assertParseFailure(parser, INPUT_INVALID_INDEX, ParserUtil.MESSAGE_INVALID_INDEX);
    }
}
