//@@author hthjthtrh
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditGroupCommand;

public class EditGroupCommandParserTest {

    private static final String MESSAGE_PARSE_FAILURE = MESSAGE_INVALID_COMMAND_FORMAT + EditGroupCommand.MESSAGE_USAGE;

    private EditGroupCommandParser parser = new EditGroupCommandParser();

    @Test
    public void parse_validArgument_success() {
        // indicate by group name
        assertParseSuccess(parser, "  testGrp1 gn TestGrp1!",
                new EditGroupCommand("testGrp1", null, "gn", "TestGrp1!", false));

        // indicate by index
        assertParseSuccess(parser, "  2    gn       TestGrp1!   ",
                new EditGroupCommand(null, Index.fromOneBased(2), "gn", "TestGrp1!", true));

        // weird group name
        assertParseSuccess(parser, "  1!wieirdName!1    gn       ST@@lWeird   ",
                new EditGroupCommand("1!wieirdName!1", null, "gn", "ST@@lWeird", false));

        assertParseSuccess(parser, "  test   add   1  ",
                new EditGroupCommand("test", null, "add", Index.fromOneBased(1), false));

        assertParseSuccess(parser, "  2    add    2   ",
                new EditGroupCommand(null, Index.fromOneBased(2), "add", Index.fromOneBased(2), true));


        assertParseSuccess(parser, "  test   delete   1  ",
                new EditGroupCommand("test", null, "delete", Index.fromOneBased(1), false));

        assertParseSuccess(parser, "  2    delete    2   ",
                new EditGroupCommand(null, Index.fromOneBased(2), "delete", Index.fromOneBased(2), true));
    }

    @Test
    public void parse_invalidGroupName_failure() {
        testFailure("2   gn   123");
        testFailure("validName  gn  -1");
    }

    @Test
    public void parse_invalidOperation_failure() {
        testFailure("validName invalidOp validNameToo");
        testFailure("validName ADD 1");
        testFailure(" 1 something something");
    }

    @Test
    public void parse_invalidArgumentsCount_failure() {
        testFailure("validName grpName 1 2 3 4");
        testFailure("");
        testFailure("grpName 1");
        testFailure("test   add -1 1 2 3 4");
    }

    private void testFailure(String inputToTest) {
        assertParseFailure(parser, inputToTest, MESSAGE_PARSE_FAILURE);
    }
}
//@@author
