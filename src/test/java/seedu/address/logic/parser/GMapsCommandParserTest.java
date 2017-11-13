package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.GMapsCommand;
import seedu.address.model.person.Address;

//@@author nbriannl
public class GMapsCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, GMapsCommand.MESSAGE_USAGE);

    private GMapsCommandParser parser = new GMapsCommandParser();


    @Test
    public void parse_invalidIndex_failure() {
        // wrong index
        assertParseFailure(parser, "-1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndexValidAddress_failure() {
        // wrong index
        assertParseFailure(parser, "-1 a/NUS", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndexInvalidAddress_failure() {
        // wrong index
        assertParseFailure(parser, "-1 a/", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, "a/", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidAddress_failure() {
        // no index specified
        assertParseFailure(parser, "1 a/", Address.MESSAGE_ADDRESS_CONSTRAINTS);
    }

    @Test
    public void parse_validValueWithoutAddress_success() throws Exception {
        GMapsCommand expectedCommand = new GMapsCommand(INDEX_FIRST_PERSON, null);
        assertParseSuccess(parser, " 1", expectedCommand);
    }

    @Test
    public void parse_validValueWithAddress_success() throws Exception {
        Address address = new Address("NUS");
        GMapsCommand expectedCommand = new GMapsCommand(INDEX_FIRST_PERSON, address);
        assertParseSuccess(parser, " 1 a/NUS", expectedCommand);
    }
}
