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
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.model.task.Appointment;
import seedu.address.model.task.Date;
import seedu.address.model.task.StartTime;
import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditTaskCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE);

    private EditTaskCommandParser parser = new EditTaskCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_APPOINTMENT_MEETING, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditTaskCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + APPOINTMENT_DESC_MEETING, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + APPOINTMENT_DESC_MEETING, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_APPOINTMENT_DESC,
                Appointment.MESSAGE_APPOINTMENT_CONSTRAINTS); // invalid appointment
        assertParseFailure(parser, "1" + INVALID_DATE_DESC,
                Date.MESSAGE_DATE_CONSTRAINTS); // invalid date
        assertParseFailure(parser, "1" + INVALID_START_TIME_DESC,
                StartTime.MESSAGE_TIME_CONSTRAINTS); // invalid start time

        // invalid date followed by valid start time
        assertParseFailure(parser, "1" + INVALID_DATE_DESC + START_TIME_DESC_MEETING,
                Date.MESSAGE_DATE_CONSTRAINTS);

        // valid date followed by invalid date. The test case for invalid date followed by valid date
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + DATE_DESC_MEETING + INVALID_DATE_DESC, Date.MESSAGE_DATE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_APPOINTMENT_DESC + INVALID_DATE_DESC
                + VALID_APPOINTMENT_MEETING + VALID_DATE_MEETING, Appointment.MESSAGE_APPOINTMENT_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_TASK;
        String userInput = targetIndex.getOneBased() + DATE_DESC_MEETING + START_TIME_DESC_MEETING
                + APPOINTMENT_DESC_MEETING;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withAppointment(VALID_APPOINTMENT_MEETING).withDate(VALID_DATE_MEETING)
                .withStartTime(VALID_START_TIME_MEETING).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + DATE_DESC_MEETING + START_TIME_DESC_MEETING;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withDate(VALID_DATE_MEETING)
                .withStartTime(VALID_START_TIME_MEETING).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // appointment
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + APPOINTMENT_DESC_MEETING;
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withAppointment(VALID_APPOINTMENT_MEETING).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = targetIndex.getOneBased() + DATE_DESC_MEETING;
        descriptor = new EditTaskDescriptorBuilder().withDate(VALID_DATE_MEETING).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // start time
        userInput = targetIndex.getOneBased() + START_TIME_DESC_MEETING;
        descriptor = new EditTaskDescriptorBuilder().withStartTime(VALID_START_TIME_MEETING).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_invalidValueFollowedByValidValue_failure() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_TASK;
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withDate(VALID_DATE_MEETING).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        String userInput = targetIndex.getOneBased() + INVALID_DATE_DESC + DATE_DESC_MEETING;
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        descriptor = new EditTaskDescriptorBuilder().withDate(VALID_DATE_MEETING)
                .withStartTime(VALID_START_TIME_MEETING).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        userInput = targetIndex.getOneBased() + INVALID_DATE_DESC + DATE_DESC_MEETING + START_TIME_DESC_MEETING;
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
