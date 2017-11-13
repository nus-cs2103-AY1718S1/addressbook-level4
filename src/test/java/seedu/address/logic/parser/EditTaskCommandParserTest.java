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
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_HOMEWORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_HOMEWORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEADER_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEADER_HOMEWORK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.model.task.Desc;
import seedu.address.model.task.Header;
import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditTaskCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE);

    private EditTaskCommandParser parser = new EditTaskCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_HEADER_HOMEWORK, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditTaskCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + HEADER_DESC_HOMEWORK, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + HEADER_DESC_HOMEWORK, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_HEADER_DESC,
                Header.MESSAGE_HEADER_CONSTRAINTS); // invalid header
        assertParseFailure(parser, "1" + INVALID_DESC_DESC,
                Desc.MESSAGE_DESC_CONSTRAINTS); // invalid desc
        //assertParseFailure(parser, "1" + INVALID_DEADLINE_DESC,
        //        Deadline.MESSAGE_DEADLINE_CONSTRAINTS); // invalid deadline


        // invalid deadline followed by valid desc
        //assertParseFailure(parser, "1" + DESC_DESC_HOMEWORK + INVALID_DEADLINE_DESC,
        //        Deadline.MESSAGE_DEADLINE_CONSTRAINTS);

        // valid desc followed by invalid desc. The test case for invalid desc followed by valid desc
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + DESC_DESC_ASSIGNMENT + INVALID_DESC_DESC,
                Desc.MESSAGE_DESC_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_HEADER_DESC + INVALID_DESC_DESC
                + VALID_DEADLINE_HOMEWORK, Header.MESSAGE_HEADER_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_TASK;
        String userInput = targetIndex.getOneBased() + DESC_DESC_ASSIGNMENT
                + HEADER_DESC_HOMEWORK + DEADLINE_DESC_HOMEWORK;

        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withHeader(VALID_HEADER_HOMEWORK)
                .withDesc(VALID_DESC_ASSIGNMENT).withDeadline(VALID_DEADLINE_HOMEWORK).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + DESC_DESC_ASSIGNMENT + DEADLINE_DESC_HOMEWORK;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withDesc(VALID_DESC_ASSIGNMENT)
                .withDeadline(VALID_DEADLINE_HOMEWORK).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // header
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + HEADER_DESC_HOMEWORK;
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withHeader(VALID_HEADER_HOMEWORK).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // desc
        userInput = targetIndex.getOneBased() + DESC_DESC_HOMEWORK;
        descriptor = new EditTaskDescriptorBuilder().withDesc(VALID_DESC_HOMEWORK).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // deadline
        userInput = targetIndex.getOneBased() + DEADLINE_DESC_HOMEWORK;
        descriptor = new EditTaskDescriptorBuilder().withDeadline(VALID_DEADLINE_HOMEWORK).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased()  + HEADER_DESC_HOMEWORK + DESC_DESC_HOMEWORK
                + DEADLINE_DESC_HOMEWORK + HEADER_DESC_HOMEWORK + DESC_DESC_HOMEWORK + DEADLINE_DESC_HOMEWORK;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withHeader(VALID_HEADER_HOMEWORK)
                .withDesc(VALID_DESC_HOMEWORK).withDeadline(VALID_DEADLINE_HOMEWORK).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + INVALID_DESC_DESC + DESC_DESC_ASSIGNMENT;
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withDesc(VALID_DESC_ASSIGNMENT).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + DEADLINE_DESC_ASSIGNMENT + INVALID_DESC_DESC + HEADER_DESC_ASSIGNMENT
                + DESC_DESC_ASSIGNMENT;
        descriptor = new EditTaskDescriptorBuilder().withDesc(VALID_DESC_ASSIGNMENT)
                .withDeadline(VALID_DEADLINE_ASSIGNMENT).withHeader(VALID_HEADER_ASSIGNMENT).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
