package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author justintkj
/**
 * Tests for all possible type of arguments possible for sortCommand.
 */
public class SortCommandParserTest {
    public static final String PARAM_NUMBER = "number";
    public static final String PARAM_NAME = "name";
    public static final String PARAM_ADDRESS = "address";
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_BIRTHDAY = "birthday";
    public static final String PARAM_REMARK = "remark";
    public static final String PARAM_CAMEL_CASE = "NuMbEr";
    public static final String INVALID_ARG = "x";
    public static final String MULTIPLE_VALID_PARAM = "number name";
    private SortCommandParser parser = new SortCommandParser();

    //Tests for valid argument, sort number
    @Test
    public void parseNumber_returnsSortCommand() {
        assertParseSuccess(PARAM_NUMBER);
    }
    //Tests for valid argument, sort name
    @Test
    public void parseName_returnsSortCommand() {
        assertParseSuccess(PARAM_NAME);
    }
    //Tests for valid argument, sort address
    @Test
    public void parseAddress_returnsSortCommand() {
        assertParseSuccess(PARAM_ADDRESS);
    }
    //Tests for valid argument, sort email
    @Test
    public void parseEmail_returnsSortCommand() {
        assertParseSuccess(PARAM_EMAIL);
    }
    //Tests for valid argument, sort birthday
    @Test
    public void parseBirthday_returnsSortCommand() {
        assertParseSuccess(PARAM_BIRTHDAY);
    }
    //Tests for valid argument, sort remark
    @Test
    public void parseRemark_returnsSortCommand() {
        assertParseSuccess(PARAM_REMARK);
    }
    //Tests for valid argument, sort number with CamelCase
    @Test
    public void parseCamelCase_returnsSortCommand() {
        assertParseSuccess(PARAM_CAMEL_CASE);
    }

    //Tests for invalid Argument
    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, INVALID_ARG, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
    }

    //Tests for invalid Argument , Empty
    @Test
    public void parse_emptyInvalidArgs_throwsParseException() {
        assertParseFailure(parser, ParserUtil.EMPTY_STRING, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
    }

    //Tests for invalid Argument , multiple valid argument
    @Test
    public void parse_multipleInvalidArgs_throwsParseException() {
        assertParseFailure(parser, MULTIPLE_VALID_PARAM, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
    }
    /**
     * Validates correct parsing
     *
     * @param param user input parameter
     */
    private void assertParseSuccess(String param) {
        try {
            SortCommand newCommand = parser.parse(param);
            assert true;
        } catch (ParseException pe) {
            assert false;
        }
    }
}
