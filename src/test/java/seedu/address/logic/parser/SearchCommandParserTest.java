package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DOB_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DOB_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_OF_BIRTH_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOB_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.SearchContainsKeywordsPredicate;

//@@author RonakLakhotia
public class SearchCommandParserTest {

    public static final String INVALID_DETAILS = "You might have entered invalid date or name with invalid characters!";
    private SearchCommandParser parser = new SearchCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {

        /* no input */
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));

        /* only name -> rejected */
        assertParseFailure(parser, "n/Ronak",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));

        /* only date of birth -> rejected */
        assertParseFailure(parser, "b/13.10.1997",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));

        /* prefixes missing */
        assertParseFailure(parser, "ronak 13.10.1997",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));

        /* Invalid date */
        assertParseFailure(parser, " n/ronak b/31.02.1997",
                String.format(INVALID_DETAILS));

        /* Invalid name */
        assertParseFailure(parser, " n/ronak. b/31.01.1997",
                String.format(INVALID_DETAILS));
    }
    @Test
    public void parse_allFieldsPresent_success() throws ParseException {

        /* Multiple names last one taken */
        assertParseSuccess(parser, SearchCommand.COMMAND_WORD + NAME_DESC_BOB
                + NAME_DESC_AMY + DOB_DESC_AMY, new SearchCommand(
                        new SearchContainsKeywordsPredicate(Arrays.asList(VALID_NAME_AMY, VALID_DOB_AMY)))
        );

        /* Multiple dates last one taken */
        assertParseSuccess(parser, SearchCommand.COMMAND_WORD + NAME_DESC_AMY
                + DOB_DESC_BOB + DOB_DESC_AMY, new SearchCommand(
                        new SearchContainsKeywordsPredicate(Arrays.asList(VALID_NAME_AMY, VALID_DOB_AMY))
        ));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE);
        // missing name
        assertParseFailure(parser, SearchCommand.COMMAND_WORD + VALID_DOB_AMY,
                expectedMessage);

        // missing date
        assertParseFailure(parser, SearchCommand.COMMAND_WORD + VALID_NAME_AMY,
                expectedMessage);


    }
    @Test
    public void parse_invalidValue_failure() {

        // Invalid name
        assertParseFailure(parser, SearchCommand.COMMAND_WORD + INVALID_NAME_DESC
                + INVALID_DATE_OF_BIRTH_DESC, String.format(INVALID_DETAILS));

        // Invalid date
        assertParseFailure(parser, SearchCommand.COMMAND_WORD + NAME_DESC_AMY
                + INVALID_DATE_OF_BIRTH_DESC, String.format((INVALID_DETAILS)));

    }

    @Test
    public void parse_validArgs_returnsSearchCommand() {
        // no leading and trailing whitespaces
        SearchCommand expectedSearchCommand =
                new SearchCommand(new SearchContainsKeywordsPredicate(Arrays.asList("Alice", "13.10.1997")));
        assertParseSuccess(parser, " n/Alice b/13.10.1997", expectedSearchCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n n/Alice \n \t b/13.10.1997  \t", expectedSearchCommand);
    }

}
