//@@author arturs68
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.GroupCommand;
import seedu.address.model.group.Group;

public class GroupCommandParserTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private GroupCommandParser parser = new GroupCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final String groupName = "Some group name";

        // have group
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_GROUP_NAME.toString() + " " + groupName;
        GroupCommand expectedCommand = new GroupCommand(INDEX_FIRST_PERSON, new Group(groupName));
        assertParseSuccess(parser, userInput, expectedCommand);

        // no group specified - exception thrown
        thrown.expect(IllegalValueException.class);
        new GroupCommand(INDEX_FIRST_PERSON, new Group(""));
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, GroupCommand.COMMAND_WORD, expectedMessage);
    }
}
