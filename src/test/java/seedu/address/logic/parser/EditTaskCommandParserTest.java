package seedu.address.logic.parser;
//@@author Esilocke
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.TASK_ADDRESS_DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.TASK_ADDRESS_DESC_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.TASK_NAME_DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.TASK_SEPARATOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_ADDRESS_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_ADDRESS_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_PAPER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Priority;
import seedu.address.model.task.TaskName;
import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditTaskCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, TASK_SEPARATOR + VALID_TASK_NAME_PAPER, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, TASK_SEPARATOR + "1", EditTaskCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, TASK_SEPARATOR + "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, TASK_SEPARATOR + "-5" + TASK_NAME_DESC_PAPER, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, TASK_SEPARATOR + "0" + TASK_NAME_DESC_PAPER, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, TASK_SEPARATOR + "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, TASK_SEPARATOR + "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, TASK_SEPARATOR + "1" + INVALID_TASK_NAME_DESC,
                TaskName.MESSAGE_NAME_CONSTRAINTS); // invalid name
        assertParseFailure(parser, TASK_SEPARATOR + "1" + INVALID_DEADLINE_DESC,
                Deadline.MESSAGE_INVALID_DATE); // invalid deadline
        assertParseFailure(parser, TASK_SEPARATOR + "1" + INVALID_PRIORITY_DESC,
                Priority.MESSAGE_PRIORITY_CONSTRAINTS); // invalid priority

        // invalid phone followed by valid email
        assertParseFailure(parser, TASK_SEPARATOR + "1" + INVALID_DEADLINE_DESC + PRIORITY_DESC_PAPER,
                Deadline.MESSAGE_INVALID_DATE);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, TASK_SEPARATOR + "1" + DEADLINE_DESC_PAPER + INVALID_DEADLINE_DESC,
                Deadline.MESSAGE_INVALID_DATE);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, TASK_SEPARATOR + "1" + INVALID_TASK_NAME_DESC + INVALID_PRIORITY_DESC
                + VALID_DEADLINE_PAPER + VALID_DESCRIPTION_PAPER, TaskName.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_TASK;
        String userInput = TASK_SEPARATOR + targetIndex.getOneBased() + TASK_NAME_DESC_PAPER + DESCRIPTION_DESC_PAPER
                + DEADLINE_DESC_PAPER + PRIORITY_DESC_PAPER + TASK_ADDRESS_DESC_PAPER;

        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withTaskName(VALID_TASK_NAME_PAPER).withDescription(VALID_DESCRIPTION_PAPER)
                .withDeadline(VALID_DEADLINE_PAPER).withPriority(VALID_PRIORITY_PAPER)
                .withTaskAddress(VALID_TASK_ADDRESS_PAPER).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = TASK_SEPARATOR + targetIndex.getOneBased() + DESCRIPTION_DESC_PAPER + DEADLINE_DESC_PAPER;

        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_PAPER).withDeadline(VALID_DEADLINE_PAPER).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = TASK_SEPARATOR + targetIndex.getOneBased() + TASK_NAME_DESC_PAPER;
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withTaskName(VALID_TASK_NAME_PAPER).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = TASK_SEPARATOR + targetIndex.getOneBased() + DESCRIPTION_DESC_PAPER;
        descriptor = new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_PAPER).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = TASK_SEPARATOR + targetIndex.getOneBased() + DEADLINE_DESC_PAPER;
        descriptor = new EditTaskDescriptorBuilder().withDeadline(VALID_DEADLINE_PAPER).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = TASK_SEPARATOR + targetIndex.getOneBased() + PRIORITY_DESC_PAPER;
        descriptor = new EditTaskDescriptorBuilder().withPriority(VALID_PRIORITY_PAPER).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = TASK_SEPARATOR + targetIndex.getOneBased() + TASK_ADDRESS_DESC_PAPER;
        descriptor = new EditTaskDescriptorBuilder().withTaskAddress(VALID_TASK_ADDRESS_PAPER).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = TASK_SEPARATOR + targetIndex.getOneBased()  + DESCRIPTION_DESC_PAPER + DEADLINE_DESC_PAPER
                + PRIORITY_DESC_PAPER + TASK_ADDRESS_DESC_PAPER + DESCRIPTION_DESC_PAPER + DEADLINE_DESC_PAPER
                + PRIORITY_DESC_PAPER + TASK_ADDRESS_DESC_PAPER + DESCRIPTION_DESC_PENCIL + DEADLINE_DESC_PENCIL
                + PRIORITY_DESC_PENCIL + TASK_ADDRESS_DESC_PENCIL;

        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_PENCIL).withDeadline(VALID_DEADLINE_PENCIL)
                .withPriority(VALID_PRIORITY_PENCIL).withTaskAddress(VALID_TASK_ADDRESS_PENCIL).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = TASK_SEPARATOR + targetIndex.getOneBased() + INVALID_DEADLINE_DESC + DEADLINE_DESC_PAPER;
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withDeadline(VALID_DEADLINE_PAPER).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = TASK_SEPARATOR + targetIndex.getOneBased() + PRIORITY_DESC_PAPER + INVALID_DEADLINE_DESC
                + DESCRIPTION_DESC_PAPER + DEADLINE_DESC_PAPER;
        descriptor = new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_PAPER)
                .withDeadline(VALID_DEADLINE_PAPER).withPriority(VALID_PRIORITY_PAPER).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
