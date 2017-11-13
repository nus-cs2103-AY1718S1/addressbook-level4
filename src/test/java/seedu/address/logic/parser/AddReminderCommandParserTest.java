//@@author duyson98

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MESSAGE_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.MESSAGE_DESC_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_OFFICE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_SOFTCOPY;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESC_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MESSAGE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MESSAGE_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_OFFICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SOFTCOPY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_PROJECT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddReminderCommand;
import seedu.address.model.reminder.Date;
import seedu.address.model.reminder.Priority;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.Task;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ReminderBuilder;

public class AddReminderCommandParserTest {
    private AddReminderCommandParser parser = new AddReminderCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Reminder expectedReminder = new ReminderBuilder().withTask(VALID_TASK_ASSIGNMENT)
                .withPriority(VALID_PRIORITY_ASSIGNMENT).withDate(VALID_DATE_ASSIGNMENT)
                .withMessage(VALID_MESSAGE_ASSIGNMENT).withTags(VALID_TAG_SOFTCOPY).build();

        // multiple task names - last task name accepted
        assertParseSuccess(parser, AddReminderCommand.COMMAND_WORD + TASK_DESC_PROJECT + TASK_DESC_ASSIGNMENT
                + PRIORITY_DESC_ASSIGNMENT + DATE_DESC_ASSIGNMENT + MESSAGE_DESC_ASSIGNMENT
                + TAG_DESC_SOFTCOPY, new AddReminderCommand(expectedReminder));

        // multiple priorities - last priority accepted
        assertParseSuccess(parser, AddReminderCommand.COMMAND_WORD + TASK_DESC_ASSIGNMENT
                + PRIORITY_DESC_PROJECT + PRIORITY_DESC_ASSIGNMENT + DATE_DESC_ASSIGNMENT + MESSAGE_DESC_ASSIGNMENT
                + TAG_DESC_SOFTCOPY, new AddReminderCommand(expectedReminder));

        // multiple dates - last date accepted
        assertParseSuccess(parser, AddReminderCommand.COMMAND_WORD + TASK_DESC_ASSIGNMENT
                + PRIORITY_DESC_ASSIGNMENT + DATE_DESC_PROJECT + DATE_DESC_ASSIGNMENT + MESSAGE_DESC_ASSIGNMENT
                + TAG_DESC_SOFTCOPY, new AddReminderCommand(expectedReminder));

        // multiple messages - last message accepted
        assertParseSuccess(parser, AddReminderCommand.COMMAND_WORD + TASK_DESC_ASSIGNMENT
                + PRIORITY_DESC_ASSIGNMENT + DATE_DESC_ASSIGNMENT + MESSAGE_DESC_PROJECT + MESSAGE_DESC_ASSIGNMENT
                + TAG_DESC_SOFTCOPY, new AddReminderCommand(expectedReminder));

        // multiple tags - all accepted
        Reminder expectedReminderMultipleTags = new ReminderBuilder().withTask(VALID_TASK_ASSIGNMENT)
                .withPriority(VALID_PRIORITY_ASSIGNMENT).withDate(VALID_DATE_ASSIGNMENT)
                .withMessage(VALID_MESSAGE_ASSIGNMENT).withTags(VALID_TAG_OFFICE, VALID_TAG_SOFTCOPY).build();
        assertParseSuccess(parser, AddReminderCommand.COMMAND_WORD + TASK_DESC_ASSIGNMENT
                + PRIORITY_DESC_ASSIGNMENT + DATE_DESC_ASSIGNMENT + MESSAGE_DESC_ASSIGNMENT + TAG_DESC_SOFTCOPY
                + TAG_DESC_OFFICE, new AddReminderCommand(expectedReminderMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Reminder expectedReminder = new ReminderBuilder().withTask(VALID_TASK_PROJECT)
                .withPriority(VALID_PRIORITY_PROJECT).withDate(VALID_DATE_PROJECT).withMessage(VALID_MESSAGE_PROJECT)
                .withTags().build();
        assertParseSuccess(parser, AddReminderCommand.COMMAND_WORD + TASK_DESC_PROJECT + PRIORITY_DESC_PROJECT
                + DATE_DESC_PROJECT + MESSAGE_DESC_PROJECT, new AddReminderCommand(expectedReminder));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE);

        // missing task name prefix
        assertParseFailure(parser, AddReminderCommand.COMMAND_WORD + VALID_TASK_ASSIGNMENT
                + PRIORITY_DESC_ASSIGNMENT + DATE_DESC_ASSIGNMENT + MESSAGE_DESC_ASSIGNMENT, expectedMessage);

        // missing priority prefix
        assertParseFailure(parser, AddReminderCommand.COMMAND_WORD + TASK_DESC_ASSIGNMENT
                + VALID_PRIORITY_ASSIGNMENT + DATE_DESC_ASSIGNMENT + MESSAGE_DESC_ASSIGNMENT, expectedMessage);

        // missing date prefix
        assertParseFailure(parser, AddReminderCommand.COMMAND_WORD + TASK_DESC_ASSIGNMENT
                + PRIORITY_DESC_ASSIGNMENT + VALID_DATE_ASSIGNMENT + MESSAGE_DESC_ASSIGNMENT, expectedMessage);

        // missing message prefix
        assertParseFailure(parser, AddReminderCommand.COMMAND_WORD + TASK_DESC_ASSIGNMENT
                + PRIORITY_DESC_ASSIGNMENT + DATE_DESC_ASSIGNMENT + VALID_MESSAGE_ASSIGNMENT, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, AddReminderCommand.COMMAND_WORD + VALID_TASK_ASSIGNMENT
                + VALID_PRIORITY_ASSIGNMENT + VALID_DATE_ASSIGNMENT + VALID_MESSAGE_ASSIGNMENT, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid task name
        assertParseFailure(parser, AddReminderCommand.COMMAND_WORD + INVALID_TASK_DESC
                + PRIORITY_DESC_ASSIGNMENT + DATE_DESC_ASSIGNMENT + MESSAGE_DESC_ASSIGNMENT + TAG_DESC_OFFICE
                + TAG_DESC_SOFTCOPY, Task.MESSAGE_TASK_NAME_CONSTRAINTS);

        // invalid priority
        assertParseFailure(parser, AddReminderCommand.COMMAND_WORD + TASK_DESC_ASSIGNMENT
                + INVALID_PRIORITY_DESC + DATE_DESC_ASSIGNMENT + MESSAGE_DESC_ASSIGNMENT + TAG_DESC_OFFICE
                + TAG_DESC_SOFTCOPY, Priority.MESSAGE_PRIORITY_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, AddReminderCommand.COMMAND_WORD + TASK_DESC_ASSIGNMENT
                + PRIORITY_DESC_ASSIGNMENT + INVALID_DATE_DESC + MESSAGE_DESC_ASSIGNMENT + TAG_DESC_OFFICE
                + TAG_DESC_SOFTCOPY, Date.MESSAGE_DATE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, AddReminderCommand.COMMAND_WORD + TASK_DESC_ASSIGNMENT
                + PRIORITY_DESC_ASSIGNMENT + DATE_DESC_ASSIGNMENT + MESSAGE_DESC_ASSIGNMENT + INVALID_TAG_DESC
                + VALID_TAG_SOFTCOPY, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddReminderCommand.COMMAND_WORD + INVALID_TASK_DESC + INVALID_PRIORITY_DESC
                + DATE_DESC_ASSIGNMENT + MESSAGE_DESC_ASSIGNMENT, Task.MESSAGE_TASK_NAME_CONSTRAINTS);
    }
}
