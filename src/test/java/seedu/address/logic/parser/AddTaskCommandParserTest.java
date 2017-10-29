package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_QUOTED_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.STARTDATE_DESC_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.STARTDATE_DESC_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.STARTDATE_DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_GROUP;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_URGENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.UNQUOTED_DESCRIPTION_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATE_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATE_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATE_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_GROUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_URGENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.tasks.AddTaskCommand;
import seedu.address.model.task.Description;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class AddTaskCommandParserTest {
    private AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        Task expectedTask = new TaskBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP)
                .withStartDate(VALID_STARTDATE_INTERNSHIP).withDeadline(VALID_DEADLINE_INTERNSHIP)
                .withTags(VALID_TAG_URGENT).build();

        // multiple start dates - last date accepted
        assertParseSuccess(parser, VALID_DESCRIPTION_INTERNSHIP + STARTDATE_DESC_GRAD_SCHOOL
                + STARTDATE_DESC_INTERNSHIP + DEADLINE_DESC_INTERNSHIP + TAG_DESC_URGENT,
                new AddTaskCommand(expectedTask));

        //multiple deadlines (with different prefix) - last deadline accepted
        assertParseSuccess(parser, VALID_DESCRIPTION_INTERNSHIP + STARTDATE_DESC_INTERNSHIP
                + DEADLINE_DESC_GRAD_SCHOOL + DEADLINE_DESC_INTERNSHIP
                + TAG_DESC_URGENT, new AddTaskCommand(expectedTask));

        // multiple tags - all accepted
        Task expectedTaskWithMultipleTags = new TaskBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP)
                .withStartDate(VALID_STARTDATE_INTERNSHIP).withDeadline(VALID_DEADLINE_INTERNSHIP)
                .withTags(VALID_TAG_URGENT, VALID_TAG_GROUP).build();
        assertParseSuccess(parser, VALID_DESCRIPTION_INTERNSHIP + STARTDATE_DESC_INTERNSHIP
                + DEADLINE_DESC_INTERNSHIP + TAG_DESC_URGENT + TAG_DESC_GROUP,
                new AddTaskCommand(expectedTaskWithMultipleTags));

        // prefix repeated in description - correct prefix accepted
        Task expectedTaskWithPrefixInDesc = new TaskBuilder().withDescription(UNQUOTED_DESCRIPTION_PAPER)
                .withStartDate(VALID_STARTDATE_PAPER).withDeadline(VALID_DEADLINE_PAPER)
                .withTags(VALID_TAG_URGENT).build();
        assertParseSuccess(parser, DESCRIPTION_QUOTED_PAPER + STARTDATE_DESC_PAPER + DEADLINE_DESC_PAPER
                + TAG_DESC_URGENT, new AddTaskCommand(expectedTaskWithPrefixInDesc));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // no start date
        Task expectedTask = new TaskBuilder().withDescription(VALID_DESCRIPTION_GRAD_SCHOOL)
                .withDeadline(VALID_DEADLINE_GRAD_SCHOOL).withStartDate("").withTags(VALID_TAG_URGENT).build();
        assertParseSuccess(parser, VALID_DESCRIPTION_GRAD_SCHOOL + DEADLINE_DESC_GRAD_SCHOOL
                + TAG_DESC_URGENT, new AddTaskCommand(expectedTask));

        // no deadline
        expectedTask = new TaskBuilder().withDescription(VALID_DESCRIPTION_GRAD_SCHOOL).withDeadline("")
                .withStartDate(VALID_STARTDATE_GRAD_SCHOOL).withTags(VALID_TAG_URGENT).build();
        assertParseSuccess(parser, VALID_DESCRIPTION_GRAD_SCHOOL + STARTDATE_DESC_GRAD_SCHOOL
                + TAG_DESC_URGENT, new AddTaskCommand(expectedTask));

        // no tags
        expectedTask = new TaskBuilder().withDescription(VALID_DESCRIPTION_GRAD_SCHOOL)
                .withDeadline(VALID_DEADLINE_GRAD_SCHOOL).withStartDate(VALID_STARTDATE_GRAD_SCHOOL).withTags().build();
        assertParseSuccess(parser, VALID_DESCRIPTION_GRAD_SCHOOL + STARTDATE_DESC_GRAD_SCHOOL
                + DEADLINE_DESC_GRAD_SCHOOL, new AddTaskCommand(expectedTask));

        // no deadline, deadline prefix in description with quotes - no deadline accepted
        expectedTask = new TaskBuilder().withDescription(UNQUOTED_DESCRIPTION_PAPER).withStartDate(VALID_STARTDATE_PAPER)
                .withDeadline("").withTags(VALID_TAG_URGENT).build();
        assertParseSuccess(parser, DESCRIPTION_QUOTED_PAPER + STARTDATE_DESC_PAPER + TAG_DESC_URGENT,
                new AddTaskCommand(expectedTask));

        // no start date and deadline
        expectedTask = new TaskBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP).withStartDate("")
                .withDeadline("")
                .withTags(VALID_TAG_URGENT).build();
        assertParseSuccess(parser, VALID_DESCRIPTION_INTERNSHIP + TAG_DESC_URGENT,
                new AddTaskCommand(expectedTask));

        // no start date and deadline, deadline prefix in description with quotes - no deadline accepted
        expectedTask = new TaskBuilder().withDescription(UNQUOTED_DESCRIPTION_PAPER).withStartDate("")
                .withDeadline("").withTags(VALID_TAG_URGENT).build();
        assertParseSuccess(parser, DESCRIPTION_QUOTED_PAPER + TAG_DESC_URGENT,
                new AddTaskCommand(expectedTask));

        // no optional field
        expectedTask = new TaskBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP).withStartDate("")
                .withDeadline("")
                .withTags().build();
        assertParseSuccess(parser, VALID_DESCRIPTION_INTERNSHIP, new AddTaskCommand(expectedTask));

        // no optional field, deadline prefix in description with quotes - no deadline accepted
        expectedTask = new TaskBuilder().withDescription(UNQUOTED_DESCRIPTION_PAPER).withStartDate("")
                .withDeadline("").withTags().build();
        assertParseSuccess(parser, DESCRIPTION_QUOTED_PAPER, new AddTaskCommand(expectedTask));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE);

        // missing description
        assertParseFailure(parser, STARTDATE_DESC_PAPER + DEADLINE_DESC_INTERNSHIP + TAG_DESC_URGENT,
                expectedMessage);

        // all missing values
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid description
        assertParseFailure(parser, INVALID_DESCRIPTION + VALID_STARTDATE_INTERNSHIP
                + VALID_DEADLINE_INTERNSHIP + VALID_TAG_URGENT, Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        // invalid start date - invalid format

        // invalid start date - not enough information

        // invalid start date - after deadline

        // invalid deadline - invalid format

        // invalid deadline - not enough information

        // invalid deadline - before start date

        // invalid tag

        // two invalid values, only first invalid value reported
    }
}
