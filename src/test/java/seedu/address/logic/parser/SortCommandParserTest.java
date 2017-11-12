package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.ARG_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.ARG_ADDRESS_ALIAS;
import static seedu.address.logic.parser.CliSyntax.ARG_DEFAULT;
import static seedu.address.logic.parser.CliSyntax.ARG_EMAIL;
import static seedu.address.logic.parser.CliSyntax.ARG_EMAIL_ALIAS;
import static seedu.address.logic.parser.CliSyntax.ARG_NAME;
import static seedu.address.logic.parser.CliSyntax.ARG_NAME_ALIAS;
import static seedu.address.logic.parser.CliSyntax.ARG_PHONE;
import static seedu.address.logic.parser.CliSyntax.ARG_PHONE_ALIAS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SortCommand;

//@@author bladerail
public class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_valid_arguments() throws Exception {

        // FilterType: Name
        assertValidFilterType(ARG_NAME_ALIAS, ARG_NAME);
        assertValidFilterType(ARG_NAME, ARG_NAME);

        // FilterType: Email
        assertValidFilterType(ARG_EMAIL, ARG_EMAIL);
        assertValidFilterType(ARG_EMAIL_ALIAS, ARG_EMAIL);

        // FilterType: Phone
        assertValidFilterType(ARG_PHONE, ARG_PHONE);
        assertValidFilterType(ARG_PHONE_ALIAS, ARG_PHONE);

        // FilterType: Address
        assertValidFilterType(ARG_ADDRESS, ARG_ADDRESS);
        assertValidFilterType(ARG_ADDRESS_ALIAS, ARG_ADDRESS);

        // FilterType: Default.
        assertValidFilterType(ARG_DEFAULT, ARG_DEFAULT);

        // No filterType argument: Should set to default
        assertValidFilterType("", ARG_DEFAULT);

        // filterType can be in any case:
        assertValidFilterType("nAMe", ARG_NAME);
        assertValidFilterType("NaME", ARG_NAME);
        assertValidFilterType("eMaIl", ARG_EMAIL);
        assertValidFilterType("PHone", ARG_PHONE);
        assertValidFilterType("aDDress", ARG_ADDRESS);
        assertValidFilterType("PHONE", ARG_PHONE);

    }

    @Test
    public void parse_invalid_arguments() throws Exception {
        String filterType = "abc";
        String userInput = SortCommand.COMMAND_WORD + " " + filterType;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);

        filterType = "olosw";
        userInput = SortCommand.COMMAND_WORD + " " + filterType;
        expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    /**
     * Asserts if the input filterType returns a SortCommand with the expected filterType
     * @param inputFilterType
     * @param expectedFilterType
     */
    public void assertValidFilterType(String inputFilterType, String expectedFilterType) {
        String userInput = SortCommand.COMMAND_WORD + " " + inputFilterType;
        SortCommand expectedCommand = new SortCommand(expectedFilterType);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
