package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_TASK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DUEDATE_TASK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_TASK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALIDTASKDESCAMY;
import static seedu.address.logic.commands.CommandTestUtil.VALIDTASKDUEDATEAMY;
import static seedu.address.logic.commands.CommandTestUtil.VALIDTASKPRIORITYAMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.testutil.EditTaskDescriptorBuilder;

//@@author JYL123
public class EditTaskCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE);

    private EditTaskCommandParser parser = new EditTaskCommandParser();

    @Test
    public void parseMissingPartsFailure() {
        // no index specified
        assertParseFailure(parser, VALIDTASKDESCAMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditTaskCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parseInvalidPreambleFailure() {
        // negative index
        assertParseFailure(parser, "-5" + DESC_TASK_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + DESC_TASK_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parseOneFieldSpecifiedSuccess() {
        // set up
        Index targetIndex = INDEX_THIRD_TASK.get(0);
        String userInput = targetIndex.getOneBased() + DESC_TASK_AMY;
        EditTaskCommand.EditTaskDescriptor descriptor =
                new EditTaskDescriptorBuilder().withDescription(VALIDTASKDESCAMY).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        // priority
        userInput = targetIndex.getOneBased() + PRIORITY_TASK_AMY;
        descriptor = new EditTaskDescriptorBuilder().withPriority(VALIDTASKPRIORITYAMY).build();
        expectedCommand = expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // duedate
        userInput = targetIndex.getOneBased() + DUEDATE_TASK_AMY;
        descriptor = new EditTaskDescriptorBuilder().withDueDate(VALIDTASKDUEDATEAMY).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
