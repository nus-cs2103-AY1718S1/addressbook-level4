//@@author srishag
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_HOMEWORK;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DESC_HOMEWORK;
import static seedu.address.logic.commands.CommandTestUtil.HEADER_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.HEADER_DESC_HOMEWORK;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HEADER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEADER_ASSIGNMENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.model.task.Desc;
import seedu.address.model.task.Header;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class AddTaskCommandParserTest {
    private AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder().withHeader(VALID_HEADER_ASSIGNMENT).withDesc(VALID_DESC_ASSIGNMENT)
                .withDeadline(VALID_DEADLINE_ASSIGNMENT).build();

        // multiple headers - last header accepted
        assertParseSuccess(parser, AddTaskCommand.COMMAND_WORD + HEADER_DESC_HOMEWORK + HEADER_DESC_ASSIGNMENT
                + DESC_DESC_ASSIGNMENT + DEADLINE_DESC_ASSIGNMENT, new AddTaskCommand(expectedTask));

        // multiple desc - last desc accepted
        assertParseSuccess(parser, AddTaskCommand.COMMAND_WORD + HEADER_DESC_ASSIGNMENT
                + DESC_DESC_HOMEWORK + DESC_DESC_ASSIGNMENT
                + DEADLINE_DESC_ASSIGNMENT, new AddTaskCommand(expectedTask));

        // multiple deadlines - last deadline accepted
        assertParseSuccess(parser, AddTaskCommand.COMMAND_WORD + HEADER_DESC_ASSIGNMENT
                + DESC_DESC_ASSIGNMENT + DEADLINE_DESC_HOMEWORK
                + DEADLINE_DESC_ASSIGNMENT, new AddTaskCommand(expectedTask));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE);

        // missing header prefix
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + VALID_HEADER_ASSIGNMENT
                + DESC_DESC_ASSIGNMENT + DEADLINE_DESC_ASSIGNMENT, expectedMessage);

        // missing desc prefix
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + HEADER_DESC_ASSIGNMENT
                + VALID_DESC_ASSIGNMENT + DEADLINE_DESC_ASSIGNMENT, expectedMessage);

        // missing deadline prefix
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + HEADER_DESC_ASSIGNMENT
                + DESC_DESC_ASSIGNMENT + VALID_DEADLINE_ASSIGNMENT, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + VALID_HEADER_ASSIGNMENT
                + VALID_DESC_ASSIGNMENT + VALID_DEADLINE_ASSIGNMENT, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + INVALID_HEADER_DESC
                + DESC_DESC_ASSIGNMENT + DEADLINE_DESC_ASSIGNMENT, Header.MESSAGE_HEADER_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + HEADER_DESC_ASSIGNMENT
                + INVALID_DESC_DESC + DEADLINE_DESC_ASSIGNMENT, Desc.MESSAGE_DESC_CONSTRAINTS);

        // invalid email
        //assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + HEADER_DESC_ASSIGNMENT
        //        + DESC_DESC_ASSIGNMENT + INVALID_DEADLINE_DESC, Deadline.MESSAGE_DEADLINE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + INVALID_HEADER_DESC
                + INVALID_DESC_DESC + DEADLINE_DESC_ASSIGNMENT, Header.MESSAGE_HEADER_CONSTRAINTS);
    }
}

