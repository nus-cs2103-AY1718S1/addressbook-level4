// @@author ahmadalkaff
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.APPOINTMENT_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_APPOINTMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_MEETING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.model.task.Appointment;
import seedu.address.model.task.Date;
import seedu.address.model.task.StartTime;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class AddTaskCommandParserTest {
    private AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder().withAppointment(VALID_APPOINTMENT_MEETING)
                .withDate(VALID_DATE_MEETING).withStartTime(VALID_START_TIME_MEETING).build();

        assertParseSuccess(parser, AddTaskCommand.COMMAND_WORD + APPOINTMENT_DESC_MEETING
                + DATE_DESC_MEETING + START_TIME_DESC_MEETING, new AddTaskCommand(expectedTask));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE);

        // missing appointment prefix
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + VALID_APPOINTMENT_MEETING + DATE_DESC_MEETING
                + START_TIME_DESC_MEETING, expectedMessage);

        // missing date prefix
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + APPOINTMENT_DESC_MEETING + VALID_DATE_MEETING
                + START_TIME_DESC_MEETING, expectedMessage);

        // missing start time prefix
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + APPOINTMENT_DESC_MEETING + DATE_DESC_MEETING
                + VALID_START_TIME_MEETING, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + VALID_APPOINTMENT_MEETING
                + VALID_DATE_MEETING + VALID_START_TIME_MEETING, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid appointment
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + INVALID_APPOINTMENT_DESC + DATE_DESC_MEETING
                + START_TIME_DESC_MEETING, Appointment.MESSAGE_APPOINTMENT_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + APPOINTMENT_DESC_MEETING + INVALID_DATE_DESC
                + START_TIME_DESC_MEETING, Date.MESSAGE_DATE_CONSTRAINTS);

        // invalid start time
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + APPOINTMENT_DESC_MEETING + DATE_DESC_MEETING
                + INVALID_START_TIME_DESC, StartTime.MESSAGE_TIME_CONSTRAINTS);

        // two invalid values, only invalid start time reported
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + APPOINTMENT_DESC_MEETING + INVALID_DATE_DESC
                + DATE_DESC_MEETING + INVALID_START_TIME_DESC, StartTime.MESSAGE_TIME_CONSTRAINTS);

        // three invalid values, only invalid start time reported
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + INVALID_APPOINTMENT_DESC
                + APPOINTMENT_DESC_MEETING + INVALID_DATE_DESC + DATE_DESC_MEETING
                + INVALID_START_TIME_DESC, StartTime.MESSAGE_TIME_CONSTRAINTS);
    }
}
