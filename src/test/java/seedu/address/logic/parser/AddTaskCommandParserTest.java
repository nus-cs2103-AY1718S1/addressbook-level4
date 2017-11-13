package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_QUOTED_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.ENDTIME_DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.ENDTTIME_DESC_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STARTTIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STARTTIME_VALID_ENDTIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_DESC_INCORRECT_ORDER;
import static seedu.address.logic.commands.CommandTestUtil.STARTTIME_DESC_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_GROUP;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_NOT_URGENT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_URGENT;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.UNQUOTED_DESCRIPTION_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_TODAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDTIME_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDTIME_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDTIME_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTTIME_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTTIME_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_GROUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_NOT_URGENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_URGENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.tasks.AddTaskCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.DateTimeValidator;
import seedu.address.model.task.Description;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

//@@author raisa2010
public class AddTaskCommandParserTest  {
    private AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        Task expectedTask = new TaskBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP)
                .withDeadline(VALID_DEADLINE_INTERNSHIP).withStartTime(VALID_STARTTIME_INTERNSHIP)
                .withEndTime(VALID_ENDTIME_INTERNSHIP).withTags(VALID_TAG_URGENT).build();

        // multiple deadlines - first date accepted
        assertParseSuccess(parser, VALID_DESCRIPTION_INTERNSHIP + DEADLINE_DESC_INTERNSHIP
                        + VALID_DEADLINE_GRAD_SCHOOL + TIME_DESC_INTERNSHIP + TAG_DESC_URGENT,
                new AddTaskCommand(expectedTask));

        // multiple time pairs - last accepted
        assertParseSuccess(parser, VALID_DESCRIPTION_INTERNSHIP + DEADLINE_DESC_INTERNSHIP
                + TIME_DESC_GRAD_SCHOOL + TIME_DESC_INTERNSHIP + TAG_DESC_URGENT, new AddTaskCommand(expectedTask));

        // multiple tags - all accepted
        Task expectedTaskWithMultipleTags = new TaskBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP)
                .withDeadline(VALID_DEADLINE_INTERNSHIP).withStartTime(VALID_STARTTIME_INTERNSHIP)
                .withEndTime(VALID_ENDTIME_INTERNSHIP).withTags(VALID_TAG_URGENT, VALID_TAG_GROUP).build();
        assertParseSuccess(parser, VALID_DESCRIPTION_INTERNSHIP + DEADLINE_DESC_INTERNSHIP
                        + TIME_DESC_INTERNSHIP + TAG_DESC_URGENT + TAG_DESC_GROUP,
                new AddTaskCommand(expectedTaskWithMultipleTags));

        // prefix repeated in description - correct prefix accepted
        Task expectedTaskWithPrefixInDesc = new TaskBuilder().withDescription(UNQUOTED_DESCRIPTION_PAPER)
                .withDeadline(VALID_DEADLINE_PAPER).withStartTime(VALID_STARTTIME_INTERNSHIP)
                .withEndTime(VALID_ENDTIME_INTERNSHIP).withTags(VALID_TAG_URGENT).build();
        assertParseSuccess(parser, DESCRIPTION_QUOTED_PAPER + DEADLINE_DESC_PAPER + TIME_DESC_INTERNSHIP
                + TAG_DESC_URGENT, new AddTaskCommand(expectedTaskWithPrefixInDesc));

        // invalid start time, valid end time - end time accepted only [same applies for valid start, time and invalid
        // end time - start time is accepted only]
        expectedTask = new TaskBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP)
                .withDeadline(VALID_DEADLINE_INTERNSHIP).withStartTime("").withEndTime(VALID_ENDTIME_INTERNSHIP)
                .withTags(VALID_TAG_URGENT).build();
        assertParseSuccess(parser, VALID_DESCRIPTION_INTERNSHIP + INVALID_STARTTIME_VALID_ENDTIME_DESC
                + DEADLINE_DESC_INTERNSHIP + TAG_DESC_URGENT, new AddTaskCommand(expectedTask));
    }

    @Test
    public void parse_optionalFieldsMissing_success() throws Exception {
        // no deadline but has times -> today's deadline added
        ReadOnlyTask expectedTask = new TaskBuilder().withDescription(VALID_DESCRIPTION_GRAD_SCHOOL)
                .withDeadline(VALID_DEADLINE_TODAY).withStartTime(VALID_STARTTIME_GRAD_SCHOOL)
                .withEndTime(VALID_ENDTIME_GRAD_SCHOOL).withTags(VALID_TAG_URGENT).build();
        assertParseSuccess(parser, VALID_DESCRIPTION_GRAD_SCHOOL + " " + TIME_DESC_GRAD_SCHOOL + TAG_DESC_URGENT,
                new AddTaskCommand(expectedTask));

        // no tags
        expectedTask = new TaskBuilder().withDescription(VALID_DESCRIPTION_GRAD_SCHOOL)
                .withDeadline(VALID_DEADLINE_GRAD_SCHOOL).withStartTime(VALID_STARTTIME_GRAD_SCHOOL)
                .withEndTime(VALID_ENDTIME_GRAD_SCHOOL).withTags().build();
        assertParseSuccess(parser, VALID_DESCRIPTION_GRAD_SCHOOL + DEADLINE_DESC_GRAD_SCHOOL
                + TIME_DESC_GRAD_SCHOOL, new AddTaskCommand(expectedTask));

        // multiple single times using "at" prefix - last accepted
        expectedTask = new TaskBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP)
                .withDeadline(VALID_DEADLINE_INTERNSHIP).withStartTime("").withEndTime(VALID_ENDTIME_INTERNSHIP)
                .withTags(VALID_TAG_NOT_URGENT).build();
        assertParseSuccess(parser, VALID_DESCRIPTION_INTERNSHIP + DEADLINE_DESC_INTERNSHIP
            + STARTTIME_DESC_INTERNSHIP + ENDTTIME_DESC_INTERNSHIP + TAG_DESC_NOT_URGENT,
                new AddTaskCommand(expectedTask));

        // no deadline, deadline prefix in description with quotes - no deadline accepted
        expectedTask = new TaskBuilder().withDescription(UNQUOTED_DESCRIPTION_PAPER)
                .withDeadline("").withStartTime("").withEndTime("").withTags(VALID_TAG_URGENT).build();
        assertParseSuccess(parser, DESCRIPTION_QUOTED_PAPER + TAG_DESC_URGENT,
                new AddTaskCommand(expectedTask));

        // no times and deadline
        expectedTask = new TaskBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP).withDeadline("")
                .withStartTime("").withEndTime("").withTags(VALID_TAG_URGENT).build();
        assertParseSuccess(parser, VALID_DESCRIPTION_INTERNSHIP + TAG_DESC_URGENT,
                new AddTaskCommand(expectedTask));

        // no start time and deadline, deadline prefix in description with quotes - today's deadline generated
        expectedTask = new TaskBuilder().withDescription(UNQUOTED_DESCRIPTION_PAPER).withDeadline(VALID_DEADLINE_TODAY)
                .withStartTime("").withEndTime(VALID_ENDTIME_PAPER).withTags(VALID_TAG_URGENT).build();
        assertParseSuccess(parser, DESCRIPTION_QUOTED_PAPER + ENDTIME_DESC_PAPER + TAG_DESC_URGENT,
                new AddTaskCommand(expectedTask));

        // no times, deadline prefix in description with quotes - correct deadline accepted
        expectedTask = new TaskBuilder().withDescription(UNQUOTED_DESCRIPTION_PAPER).withDeadline(VALID_DEADLINE_PAPER)
                .withStartTime("").withEndTime("").withTags().build();
        assertParseSuccess(parser, DESCRIPTION_QUOTED_PAPER + DEADLINE_DESC_PAPER,
                new AddTaskCommand(expectedTask));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE);

        // missing description
        assertParseFailure(parser, DEADLINE_DESC_INTERNSHIP + TAG_DESC_URGENT,
                expectedMessage);

        // all missing values
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid description
        assertParseFailure(parser, INVALID_DESCRIPTION + TIME_DESC_INTERNSHIP
                + DEADLINE_DESC_INTERNSHIP + TAG_DESC_URGENT, Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        // invalid deadline - invalid format
        assertParseFailure(parser, VALID_DESCRIPTION_INTERNSHIP + TIME_DESC_INTERNSHIP
                + INVALID_DEADLINE_DESC + TAG_DESC_URGENT, DateTimeValidator.MESSAGE_DATE_CONSTRAINTS);

        // invalid start time, no end time (single time)
        assertParseFailure(parser, VALID_DESCRIPTION_INTERNSHIP + INVALID_STARTTIME_DESC
                + DEADLINE_DESC_INTERNSHIP + TAG_DESC_URGENT, DateTimeValidator.MESSAGE_TIME_CONSTRAINTS);

        // invalid start time and end time
        assertParseFailure(parser, VALID_DESCRIPTION_INTERNSHIP + INVALID_TIME_DESC_INCORRECT_ORDER
                + DEADLINE_DESC_INTERNSHIP + TAG_DESC_URGENT, DateTimeValidator.MESSAGE_TIME_CONSTRAINTS);

        // invalid start time - after end time
        assertParseFailure(parser, VALID_DESCRIPTION_INTERNSHIP + INVALID_TIME_DESC_INCORRECT_ORDER
                + DEADLINE_DESC_INTERNSHIP + TAG_DESC_URGENT, DateTimeValidator.MESSAGE_TIME_CONSTRAINTS);

        // invalid deadline - multiple deadline prefixes
        assertParseFailure(parser, VALID_DESCRIPTION_INTERNSHIP + DEADLINE_DESC_INTERNSHIP
                + DEADLINE_DESC_GRAD_SCHOOL + TAG_DESC_URGENT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));

        // invalid tag
        assertParseFailure(parser, VALID_DESCRIPTION_INTERNSHIP + TIME_DESC_INTERNSHIP
                + DEADLINE_DESC_INTERNSHIP + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_DESCRIPTION + INVALID_DEADLINE_DESC
                + TIME_DESC_INTERNSHIP + TAG_DESC_URGENT, Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
    }
}
