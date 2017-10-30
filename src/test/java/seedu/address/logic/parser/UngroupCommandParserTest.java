//@@author arturs68
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UngroupCommand;
import seedu.address.model.group.Group;

public class UngroupCommandParserTest {
    private UngroupCommandParser parser = new UngroupCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final Group group = new Group("Some group");

        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_GROUP_NAME.toString() + " " + group.groupName;
        UngroupCommand expectedCommand = new UngroupCommand(INDEX_FIRST_PERSON, group);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UngroupCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, UngroupCommand.COMMAND_WORD, expectedMessage);
    }
}
