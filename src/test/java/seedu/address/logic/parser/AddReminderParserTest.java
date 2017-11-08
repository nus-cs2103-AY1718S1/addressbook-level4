package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.commands.CommandTestUtil.DETAILS_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.DETAILS_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.DUE_DATE_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.DUE_DATE_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DETAILS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DUE_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DETAILS_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DETAILS_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DUE_DATE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DUE_DATE_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_MEETING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddReminder;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminder.DueDate;
import seedu.address.model.reminder.Priority;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderDetails;
import seedu.address.testutil.ReminderBuilder;

//@@author RonakLakhotia
public class AddReminderParserTest {
    private AddReminderParser parser = new AddReminderParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        Reminder expectedReminder = new ReminderBuilder().withDetails(VALID_DETAILS_MEETING)
                .withPriority("Priority Level: " + VALID_PRIORITY_MEETING)
                .withDueDate(VALID_DUE_DATE_MEETING).build();

        // multiple details - last detail accepted
        assertParseSuccess(parser, AddReminder.COMMAND_WORD + DETAILS_DESC_ASSIGNMENT + DETAILS_DESC_MEETING
                        + PRIORITY_DESC_MEETING + DUE_DATE_DESC_MEETING,
                new AddReminder(expectedReminder));

        // multiple priorities - last priority accepted
        assertParseSuccess(parser, AddReminder.COMMAND_WORD + DETAILS_DESC_MEETING
                        + PRIORITY_DESC_ASSIGNMENT + PRIORITY_DESC_MEETING + DUE_DATE_DESC_MEETING,
                new AddReminder(expectedReminder));

        // multiple due dates - last due date accepted
        assertParseSuccess(parser, AddReminder.COMMAND_WORD + DETAILS_DESC_MEETING
                        + PRIORITY_DESC_MEETING + DUE_DATE_DESC_ASSIGNMENT + DETAILS_DESC_MEETING,
                new AddReminder(expectedReminder));

    }
    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminder.MESSAGE_USAGE);

        // missing detail prefix
        assertParseFailure(parser, AddReminder.COMMAND_WORD + VALID_DETAILS_ASSIGNMENT
                + PRIORITY_DESC_ASSIGNMENT
                + DUE_DATE_DESC_ASSIGNMENT , expectedMessage);

        // missing priority prefix
        assertParseFailure(parser, AddReminder.COMMAND_WORD
                + DETAILS_DESC_ASSIGNMENT + VALID_PRIORITY_ASSIGNMENT
                + DUE_DATE_DESC_ASSIGNMENT, expectedMessage);

        // missing duedate prefix
        assertParseFailure(parser, AddReminder.COMMAND_WORD + DETAILS_DESC_ASSIGNMENT + PRIORITY_DESC_MEETING
                + VALID_DUE_DATE_ASSIGNMENT, expectedMessage);

    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid reminder
        assertParseFailure(parser, AddReminder.COMMAND_WORD + INVALID_DETAILS_DESC + PRIORITY_DESC_MEETING
                + DUE_DATE_DESC_MEETING, ReminderDetails.MESSAGE_REMINDER_CONSTRAINTS);

        // invalid priority
        assertParseFailure(parser, AddReminder.COMMAND_WORD + DETAILS_DESC_MEETING + INVALID_PRIORITY_DESC
                + DUE_DATE_DESC_MEETING, Priority.PRIORITY_CONSTRAINTS);

        // invalid duedate
        assertParseFailure(parser, AddReminder.COMMAND_WORD + DETAILS_DESC_MEETING + PRIORITY_DESC_MEETING
                + INVALID_DUE_DATE_DESC, DueDate.MESSAGE_DATE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddReminder.COMMAND_WORD + INVALID_DETAILS_DESC + PRIORITY_DESC_MEETING
                + INVALID_DUE_DATE_DESC , ReminderDetails.MESSAGE_REMINDER_CONSTRAINTS);

    }
}
