package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GROUP_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GROUP_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CreateGroupCommand;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.testutil.GroupBuilder;

//@@author eldonng
public class CreateGroupCommandParserTest {

    private CreateGroupCommandParser parser = new CreateGroupCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Group expectedGroup = new GroupBuilder().withGroupName(VALID_GROUP_NAME)
                .withGroupMembers(Arrays.asList(ALICE)).build();
        assertParseSuccess(parser, CreateGroupCommand.COMMAND_WORD + GROUP_NAME_DESC + GROUP_INDEX_DESC,
                new CreateGroupCommand(expectedGroup.getGroupName(), Arrays.asList(Index.fromOneBased(1))));

        //multiple same indexes - accepted
        assertParseSuccess(parser, CreateGroupCommand.COMMAND_WORD + GROUP_NAME_DESC + GROUP_INDEX_DESC
                + " " + VALID_INDEX, new CreateGroupCommand(expectedGroup.getGroupName(),
                Arrays.asList(Index.fromOneBased(1), Index.fromOneBased(1))));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateGroupCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, CreateGroupCommand.COMMAND_WORD + VALID_GROUP_NAME + GROUP_INDEX_DESC,
                expectedMessage);

        //missing all prefix
        assertParseFailure(parser, CreateGroupCommand.COMMAND_WORD + VALID_GROUP_NAME + VALID_INDEX,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        //invalid group name
        assertParseFailure(parser, CreateGroupCommand.COMMAND_WORD + INVALID_GROUP_NAME_DESC
                + GROUP_INDEX_DESC, GroupName.MESSAGE_NAME_CONSTRAINTS);

        //invalid index
        assertParseFailure(parser, CreateGroupCommand.COMMAND_WORD + GROUP_NAME_DESC
                + INVALID_GROUP_INDEX_DESC, MESSAGE_INVALID_INDEX);
    }
}
