package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.STARTDATE_DESC_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.STARTDATE_DESC_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_GROUP;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_URGENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SINGLEEVENTDATE_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SINGLEEVENT_DATE_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATE_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_GROUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_URGENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddTaskCommand;
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
        
        //multiple deadlines - last deadline accepted
        /*assertParseSuccess(parser, VALID_DESCRIPTION_INTERNSHIP + STARTDATE_DESC_INTERNSHIP
                + DEADLINE_DESC_GRAD_SCHOOL + DEADLINE_DESC_INTERNSHIP
                + TAG_DESC_URGENT, new AddTaskCommand(expectedTask));*/

        // multiple tags - all accepted
        Task expectedTaskWithMultipleTags = new TaskBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP)
                .withStartDate(VALID_STARTDATE_INTERNSHIP).withDeadline(VALID_DEADLINE_INTERNSHIP)
                .withTags(VALID_TAG_URGENT, VALID_TAG_GROUP).build();
        assertParseSuccess(parser, VALID_DESCRIPTION_INTERNSHIP + STARTDATE_DESC_INTERNSHIP
                + DEADLINE_DESC_INTERNSHIP + TAG_DESC_URGENT + TAG_DESC_GROUP,
                new AddTaskCommand(expectedTaskWithMultipleTags));
    }

   /* @Test
    public void parse_optionalFieldsMissing_success() {
        // no start date
        /*Task expectedTask = new TaskBuilder().withDescription(VALID_DESCRIPTION_GRAD_SCHOOL)
                .withDeadline(VALID_DEADLINE_GRAD_SCHOOL).withSingleEventDate(VALID_SINGLEEVENT_DATE_GRAD_SCHOOL)
                .withTags(VALID_TAG_URGENT).build();
        assertParseSuccess(parser, VALID_DESCRIPTION_GRAD_SCHOOL + DEADLINE_DESC_GRAD_SCHOOL
                + SINGLEEVENTDATE_DESC_GRAD_SCHOOL + TAG_DESC_URGENT, new AddTaskCommand(expectedTask));
        
        // no deadline
    }*/
}
