package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STARTDATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.STARTDATE_DESC_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.STARTDATE_DESC_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.STARTDATE_DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_GROUP;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_NOT_URGENT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_URGENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.UNQUOTED_DESCRIPTION_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATE_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATE_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_GROUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_NOT_URGENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_URGENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Description;
import seedu.address.model.task.TaskDates;
import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditTaskCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE);

    private EditTaskCommandParser parser = new EditTaskCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_STARTDATE_INTERNSHIP, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditTaskCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndex_failure() throws Exception {
        // negative index
        assertParseFailure(parser, "-5" + UNQUOTED_DESCRIPTION_PAPER, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_DESCRIPTION_INTERNSHIP, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid description
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION, Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
        // invalid start date
        assertParseFailure(parser, "1" + INVALID_STARTDATE_DESC, TaskDates.MESSAGE_DATE_CONSTRAINTS);
        // invalid deadline
        assertParseFailure(parser, "1" + INVALID_DEADLINE_DESC, TaskDates.MESSAGE_DATE_CONSTRAINTS);
        // invalid tag
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);

        // invalid start date followed by valid deadline
        assertParseFailure(parser, "1" + INVALID_STARTDATE_DESC + VALID_DEADLINE_INTERNSHIP,
                TaskDates.MESSAGE_DATE_CONSTRAINTS);

        //TODO: Make command accept the last date not the first one
        // valid start date followed by invalid start date. The test case for invalid start date followed by valid start
        // date is tested at {@code parse_invalidValueFollowedByValidValue_success()}
      //  assertParseFailure(parser, "1" + VALID_STARTDATE_INTERNSHIP + INVALID_STARTDATE_DESC,
      //          TaskDates.MESSAGE_DATE_CONSTRAINTS);

        // invalid description containing deadline prefix, without quotes
        // TODO: Make it throw Date constraint message
        assertParseFailure(parser, "1" + UNQUOTED_DESCRIPTION_PAPER + STARTDATE_DESC_PAPER + TAG_DESC_URGENT,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION + INVALID_STARTDATE_DESC
                        + VALID_DEADLINE_INTERNSHIP + VALID_TAG_NOT_URGENT,
                Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_TASK;
        String userInput = targetIndex.getOneBased() + VALID_DESCRIPTION_INTERNSHIP + STARTDATE_DESC_INTERNSHIP
                + TAG_DESC_URGENT + DEADLINE_DESC_INTERNSHIP + TAG_DESC_GROUP;

        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_INTERNSHIP).withStartDate(VALID_STARTDATE_INTERNSHIP)
                .withDeadline(VALID_DEADLINE_INTERNSHIP).withTags(VALID_TAG_URGENT, VALID_TAG_GROUP).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + STARTDATE_DESC_INTERNSHIP + DEADLINE_DESC_GRAD_SCHOOL;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withStartDate(VALID_STARTDATE_INTERNSHIP).withDeadline(VALID_DEADLINE_GRAD_SCHOOL).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // description
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + VALID_DESCRIPTION_GRAD_SCHOOL;
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_GRAD_SCHOOL)
                .build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // start date
        userInput = targetIndex.getOneBased() + STARTDATE_DESC_GRAD_SCHOOL;
        descriptor = new EditTaskDescriptorBuilder().withStartDate(VALID_STARTDATE_GRAD_SCHOOL).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // deadline
        userInput = targetIndex.getOneBased() + DEADLINE_DESC_GRAD_SCHOOL;
        descriptor = new EditTaskDescriptorBuilder().withDeadline(VALID_DEADLINE_GRAD_SCHOOL).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_NOT_URGENT;
        descriptor = new EditTaskDescriptorBuilder().withTags(VALID_TAG_NOT_URGENT).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTags().build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
