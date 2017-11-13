package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMINDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMINDER_DESC_DATE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMINDER_DESC_TIME;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_DESC_COFFEE;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_DESC_DATE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_DESC_DATE_COFFEE;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_DESC_TIME_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_DESC_TIME_COFFEE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_COFFEE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_DATE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_DATE_COFFEE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_TIME_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_TIME_COFFEE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.io.IOException;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditReminderCommand;
import seedu.address.logic.commands.EditReminderCommand.EditReminderDescriptor;
import seedu.address.model.reminders.Date;
import seedu.address.model.reminders.Time;
import seedu.address.testutil.EditReminderDescriptorBuilder;

//@@author justinpoh
public class EditReminderCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditReminderCommand.MESSAGE_USAGE);

    private EditReminderCommandParser parser = new EditReminderCommandParser();

    @Test
    public void parse_missingParts_failure() throws IOException {
        // no index specified
        assertParseFailure(parser, VALID_REMINDER_COFFEE, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditReminderCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() throws IOException {
        // negative index
        assertParseFailure(parser, "-5" + REMINDER_DESC_COFFEE, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + REMINDER_DESC_COFFEE, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() throws IOException {
        assertParseFailure(parser, "1" + INVALID_REMINDER_DESC, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditReminderCommand.MESSAGE_REMINDER_FORMAT)); // invalid reminder
        assertParseFailure(parser, "1" + INVALID_REMINDER_DESC_DATE, Date.MESSAGE_DATE_CONSTRAINTS); // invalid date
        assertParseFailure(parser, "1" + INVALID_REMINDER_DESC_TIME, Time.MESSAGE_TIME_CONSTRAINTS); // invalid time

        // invalid date followed by valid time
        assertParseFailure(parser, "1" + INVALID_REMINDER_DESC_DATE + VALID_REMINDER_TIME_COFFEE,
                Date.MESSAGE_DATE_CONSTRAINTS);

        // valid date followed by invalid date
        assertParseFailure(parser, "1" + REMINDER_DESC_COFFEE + INVALID_REMINDER_DESC_DATE,
                Date.MESSAGE_DATE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_REMINDER_DESC + INVALID_REMINDER_DESC_TIME
                + INVALID_REMINDER_DESC_DATE, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditReminderCommand.MESSAGE_REMINDER_FORMAT));
    }

    @Test
    public void parse_allFieldsSpecified_success() throws IOException {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + REMINDER_DESC_COFFEE + REMINDER_DESC_DATE_COFFEE
                + REMINDER_DESC_TIME_COFFEE;

        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder().withReminder(VALID_REMINDER_COFFEE)
                .withDate(VALID_REMINDER_DATE_COFFEE).withTime(VALID_REMINDER_TIME_COFFEE).build();
        EditReminderCommand expectedCommand = new EditReminderCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() throws IOException {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + REMINDER_DESC_COFFEE + REMINDER_DESC_DATE_COFFEE;

        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder().withReminder(VALID_REMINDER_COFFEE)
                .withDate(VALID_REMINDER_DATE_COFFEE).build();
        EditReminderCommand expectedCommand = new EditReminderCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() throws IOException {
        // reminder
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + REMINDER_DESC_COFFEE;
        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder()
                .withReminder(VALID_REMINDER_COFFEE).build();
        EditReminderCommand expectedCommand = new EditReminderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = targetIndex.getOneBased() + REMINDER_DESC_DATE_COFFEE;
        descriptor = new EditReminderDescriptorBuilder().withDate(VALID_REMINDER_DATE_COFFEE).build();
        expectedCommand = new EditReminderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // time
        userInput = targetIndex.getOneBased() + REMINDER_DESC_TIME_COFFEE;
        descriptor = new EditReminderDescriptorBuilder().withTime(VALID_REMINDER_TIME_COFFEE).build();
        expectedCommand = new EditReminderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() throws IOException {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased()  + REMINDER_DESC_ASSIGNMENT + REMINDER_DESC_DATE_COFFEE
                + REMINDER_DESC_DATE_ASSIGNMENT + REMINDER_DESC_TIME_ASSIGNMENT + REMINDER_DESC_TIME_COFFEE
                + REMINDER_DESC_COFFEE;

        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder().withReminder(VALID_REMINDER_COFFEE)
                .withDate(VALID_REMINDER_DATE_ASSIGNMENT).withTime(VALID_REMINDER_TIME_COFFEE).build();
        EditReminderCommand expectedCommand = new EditReminderCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() throws IOException {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_REMINDER_DESC_DATE + REMINDER_DESC_DATE_COFFEE;
        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder()
                .withDate(VALID_REMINDER_DATE_COFFEE).build();
        EditReminderCommand expectedCommand = new EditReminderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + REMINDER_DESC_ASSIGNMENT + INVALID_REMINDER_DESC_DATE
                + REMINDER_DESC_DATE_COFFEE + REMINDER_DESC_TIME_ASSIGNMENT;
        descriptor = new EditReminderDescriptorBuilder().withReminder(VALID_REMINDER_ASSIGNMENT)
                .withDate(VALID_REMINDER_DATE_COFFEE).withTime(VALID_REMINDER_TIME_ASSIGNMENT).build();
        expectedCommand = new EditReminderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
