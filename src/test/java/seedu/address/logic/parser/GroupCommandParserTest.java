package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GroupCommand;

public class GroupCommandParserTest {
    private GroupCommandParser parser = new GroupCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final String groupName = "Some group name";

        // have group
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_GROUP_NAME.toString() + " " + groupName;
        GroupCommand expectedCommand = new GroupCommand(INDEX_FIRST_PERSON, groupName);
        assertParseSuccess(parser, userInput, expectedCommand);

        // no group TBI - exception!
        userInput = targetIndex.getOneBased() + " " + PREFIX_GROUP_NAME.toString();
        expectedCommand = new GroupCommand(INDEX_FIRST_PERSON, "");
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, GroupCommand.COMMAND_WORD, expectedMessage);
    }
}