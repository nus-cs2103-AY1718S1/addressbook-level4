//@@author hthjthtrh
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.EditGroupCommand;

public class EditGroupCommandParserTest {

    private static String MESSAGE_PARSEFAILURE_TEST = MESSAGE_INVALID_COMMAND_FORMAT + EditGroupCommand.MESSAGE_USAGE;

    private EditGroupCommandParser parser = new EditGroupCommandParser();

    @Test
    public void parse_validArgument_success() {
        assertParseSuccess(parser, "  testGrp1 grpName TestGrp1!",
                new EditGroupCommand("testGrp1", "grpName", "TestGrp1!"));

        assertParseSuccess(parser, "TestGroup1 add 1", new EditGroupCommand("TestGroup1", "add", "1"));
        assertParseSuccess(parser, "TestGroup1 delete 1", new EditGroupCommand("TestGroup1", "delete", "1"));
    }

    @Test
    public void parse_invalidGroupName_failure() {
        testFailure("123 grpName validName");
        testFailure("validName grpName 123");
    }

    @Test
    public void parse_invalidOperation_failure() {
        testFailure("validName invalidOp validNameToo");
        testFailure("validName ADD 1");
    }

    @Test
    public void parse_invalidArgumentsCount_failure() {
        testFailure("validName grpName 1 2 3 4");
        testFailure("");
        testFailure("grpName 1");

    }

    private void testFailure(String inputToTest) {
        assertParseFailure(parser, inputToTest, MESSAGE_PARSEFAILURE_TEST);
    }
}
//@@author
