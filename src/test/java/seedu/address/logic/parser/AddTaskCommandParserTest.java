package seedu.address.logic.parser;
//@@author Esilocke
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.TASK_ADDRESS_DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.TASK_ADDRESS_DESC_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.TASK_NAME_DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.TASK_NAME_DESC_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.TASK_SEPARATOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_ADDRESS_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_PENCIL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddPersonCommand;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class AddTaskCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parseTasksAllFieldsPresent_success() {
        Task expectedTask = new TaskBuilder().withTaskName(VALID_TASK_NAME_PENCIL)
                .withDescription(VALID_DESCRIPTION_PENCIL).withDeadline(VALID_DEADLINE_PENCIL)
                .withPriority(VALID_PRIORITY_PENCIL).withTaskAddress(VALID_TASK_ADDRESS_PENCIL).build();

        // multiple names - last name accepted
        assertParseSuccess(parser, AddPersonCommand.COMMAND_WORD + TASK_SEPARATOR + TASK_NAME_DESC_PAPER
                + TASK_NAME_DESC_PENCIL + DESCRIPTION_DESC_PENCIL + DEADLINE_DESC_PENCIL
                + PRIORITY_DESC_PENCIL + TASK_ADDRESS_DESC_PENCIL, new AddTaskCommand(expectedTask));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, AddTaskCommand.COMMAND_WORD + TASK_SEPARATOR + TASK_NAME_DESC_PENCIL
                + DESCRIPTION_DESC_PAPER + DESCRIPTION_DESC_PENCIL + DEADLINE_DESC_PENCIL
                + PRIORITY_DESC_PENCIL + TASK_ADDRESS_DESC_PENCIL, new AddTaskCommand(expectedTask));

        // multiple deadlines - last deadline accepted
        assertParseSuccess(parser, AddTaskCommand.COMMAND_WORD + TASK_SEPARATOR + TASK_NAME_DESC_PENCIL
                + DESCRIPTION_DESC_PENCIL + DEADLINE_DESC_PAPER + DEADLINE_DESC_PENCIL
                + PRIORITY_DESC_PENCIL + TASK_ADDRESS_DESC_PENCIL, new AddTaskCommand(expectedTask));

        // multiple priorities - last priority accepted
        assertParseSuccess(parser, AddTaskCommand.COMMAND_WORD + TASK_SEPARATOR + TASK_NAME_DESC_PENCIL
                + DESCRIPTION_DESC_PENCIL + DEADLINE_DESC_PENCIL + PRIORITY_DESC_PAPER
                + PRIORITY_DESC_PENCIL + TASK_ADDRESS_DESC_PENCIL, new AddTaskCommand(expectedTask));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, AddTaskCommand.COMMAND_WORD + TASK_SEPARATOR + TASK_NAME_DESC_PENCIL
                + DESCRIPTION_DESC_PENCIL + DEADLINE_DESC_PENCIL + PRIORITY_DESC_PENCIL
                + TASK_ADDRESS_DESC_PAPER + TASK_ADDRESS_DESC_PENCIL, new AddTaskCommand(expectedTask));
    }


    @Test
    public void parseTasksCompulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE);

        // missing task name prefix
        assertParseFailure(parser, AddPersonCommand.COMMAND_WORD + TASK_SEPARATOR + VALID_TASK_NAME_PAPER
                + DESCRIPTION_DESC_PENCIL + DEADLINE_DESC_PENCIL + PRIORITY_DESC_PENCIL, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, AddPersonCommand.COMMAND_WORD + TASK_SEPARATOR + VALID_TASK_NAME_PENCIL
                + VALID_DESCRIPTION_PENCIL + VALID_DEADLINE_PENCIL + VALID_PRIORITY_PENCIL, expectedMessage);
    }


    @Test
    public void parseTaskInvalidValue_failure() {
        // invalid deadline
        assertParseFailure(parser, AddPersonCommand.COMMAND_WORD + TASK_SEPARATOR + TASK_NAME_DESC_PENCIL
                + DESCRIPTION_DESC_PENCIL + INVALID_DEADLINE_DESC
                + PRIORITY_DESC_PENCIL + TASK_ADDRESS_DESC_PENCIL, Deadline.MESSAGE_INVALID_DATE);

        // invalid priority
        assertParseFailure(parser, AddPersonCommand.COMMAND_WORD + TASK_SEPARATOR + TASK_NAME_DESC_PENCIL
                + DESCRIPTION_DESC_PENCIL + DEADLINE_DESC_PENCIL
                + INVALID_PRIORITY_DESC + TASK_ADDRESS_DESC_PENCIL, Priority.MESSAGE_PRIORITY_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddPersonCommand.COMMAND_WORD + TASK_SEPARATOR + TASK_NAME_DESC_PENCIL
                + DESCRIPTION_DESC_PENCIL + INVALID_DEADLINE_DESC
                + INVALID_PRIORITY_DESC + TASK_ADDRESS_DESC_PENCIL, Deadline.MESSAGE_INVALID_DATE);
    }


}
