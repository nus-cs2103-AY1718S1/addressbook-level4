package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DETAILS_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.DUE_DATE_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DETAILS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DUE_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DETAILS_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DUE_DATE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_MEETING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_REMINDER;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ChangeReminderCommand;
import seedu.address.logic.commands.ChangeReminderCommand.ChangeReminderDescriptor;
import seedu.address.model.reminder.DueDate;
import seedu.address.model.reminder.Priority;
import seedu.address.model.reminder.ReminderDetails;
import seedu.address.testutil.ChangeReminderDescriptorBuilder;

//@@author RonakLakhotia
public class ChangeReminderCommandParserTest {


    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeReminderCommand.MESSAGE_USAGE);

    private ChangeReminderCommandParser parser = new ChangeReminderCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_DETAILS_ASSIGNMENT, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", ChangeReminderCommand.MESSAGE_NOT_CHANGED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + DETAILS_DESC_ASSIGNMENT, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + DETAILS_DESC_ASSIGNMENT, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1"
                + INVALID_DETAILS_DESC, ReminderDetails.MESSAGE_REMINDER_CONSTRAINTS); // invalid details
        assertParseFailure(parser, "1"
                + INVALID_PRIORITY_DESC, Priority.PRIORITY_CONSTRAINTS); // invalid priority
        assertParseFailure(parser, "1"
                + INVALID_DUE_DATE_DESC, DueDate.MESSAGE_DATE_CONSTRAINTS); // invalid duedate

        // invalid priority followed by valid duedate
        assertParseFailure(parser, "1" + INVALID_PRIORITY_DESC
                + DUE_DATE_DESC_ASSIGNMENT, Priority.PRIORITY_CONSTRAINTS);

        // valid priority followed by invalid priority
        assertParseFailure(parser, "1" + INVALID_PRIORITY_DESC + VALID_PRIORITY_MEETING,
                Priority.PRIORITY_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_DETAILS_DESC + INVALID_DUE_DATE_DESC
                        + VALID_PRIORITY_ASSIGNMENT ,
                ReminderDetails.MESSAGE_REMINDER_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_REMINDER;
        String userInput = targetIndex.getOneBased() + DETAILS_DESC_ASSIGNMENT + PRIORITY_DESC_ASSIGNMENT
                + DUE_DATE_DESC_ASSIGNMENT;

        ChangeReminderDescriptor descriptor = new ChangeReminderDescriptorBuilder()
                .withDetails(VALID_DETAILS_ASSIGNMENT)
                .withPriority(VALID_PRIORITY_ASSIGNMENT)
                .withDueDate(VALID_DUE_DATE_ASSIGNMENT).build();
        ChangeReminderCommand expectedCommand = new ChangeReminderCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
    @Test
    public void parse_someFieldsSpecified_success() {

        Index targetIndex = INDEX_SECOND_REMINDER;
        String userInput = targetIndex.getOneBased() + DETAILS_DESC_ASSIGNMENT + PRIORITY_DESC_ASSIGNMENT;

        ChangeReminderDescriptor descriptor = new ChangeReminderDescriptorBuilder()
                .withDetails(VALID_DETAILS_ASSIGNMENT)
                .withPriority(VALID_PRIORITY_ASSIGNMENT).build();

        ChangeReminderCommand expectedCommand = new ChangeReminderCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

    }
    @Test
    public void parse_oneFieldSpecified_success() {

        Index targetIndex = INDEX_SECOND_REMINDER;
        //details
        String userInput = targetIndex.getOneBased() + DETAILS_DESC_ASSIGNMENT;

        ChangeReminderDescriptor descriptor = new ChangeReminderDescriptorBuilder()
                .withDetails(VALID_DETAILS_ASSIGNMENT).build();

        ChangeReminderCommand expectedCommand = new ChangeReminderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // PRIORITY
        userInput = targetIndex.getOneBased() + PRIORITY_DESC_ASSIGNMENT;
        descriptor = new ChangeReminderDescriptorBuilder().withPriority(VALID_PRIORITY_ASSIGNMENT)
                .build();

        expectedCommand = new ChangeReminderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        //due date
        userInput = targetIndex.getOneBased() + DUE_DATE_DESC_ASSIGNMENT;
        descriptor = new ChangeReminderDescriptorBuilder().withDueDate(VALID_DUE_DATE_ASSIGNMENT)
                .build();

        expectedCommand = new ChangeReminderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);


    }


    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_SECOND_REMINDER;

        String userInput = targetIndex.getOneBased()  + PRIORITY_DESC_ASSIGNMENT + DETAILS_DESC_ASSIGNMENT
                + DUE_DATE_DESC_ASSIGNMENT;

        ChangeReminderDescriptor descriptor = new ChangeReminderDescriptorBuilder()
                .withPriority(VALID_PRIORITY_ASSIGNMENT)
                .withDetails(VALID_DETAILS_ASSIGNMENT)
                .withDueDate(VALID_DUE_DATE_ASSIGNMENT)
                .build();
        ChangeReminderCommand expectedCommand = new ChangeReminderCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {

        Index targetIndex = INDEX_SECOND_REMINDER;

        String userInput = targetIndex.getOneBased() + INVALID_PRIORITY_DESC + PRIORITY_DESC_ASSIGNMENT;

        ChangeReminderDescriptor descriptor = new ChangeReminderDescriptorBuilder()
                .withPriority(VALID_PRIORITY_ASSIGNMENT)
                .build();

        ChangeReminderCommand expectedCommand = new ChangeReminderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

    }

}
