
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.ENDTIME_DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.ENDTTIME_DESC_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STARTTIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STARTTIME_VALID_ENDTIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_DESC_CORRECT_ORDER;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_DESC_INCORRECT_ORDER;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_GROUP;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_NOT_URGENT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_URGENT;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_GYM;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.UNQUOTED_DESCRIPTION_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_INTERNSHIP;
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
import seedu.address.logic.commands.tasks.EditTaskCommand;
import seedu.address.logic.commands.tasks.EditTaskCommand.EditTaskDescriptor;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.DateTimeValidator;
import seedu.address.model.task.Description;
import seedu.address.testutil.EditTaskDescriptorBuilder;

//@@author raisa2010
public class EditTaskCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE);

    private EditTaskCommandParser parser = new EditTaskCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_DEADLINE_INTERNSHIP, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditTaskCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() throws Exception {
        // negative index
        assertParseFailure(parser, "-5" + UNQUOTED_DESCRIPTION_PAPER, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_DESCRIPTION_INTERNSHIP, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid description
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION, Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        // invalid deadline
        assertParseFailure(parser, "1" + INVALID_DEADLINE_DESC, DateTimeValidator.MESSAGE_DATE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);

        // one invalid time only
        assertParseFailure(parser, "1" + INVALID_STARTTIME_DESC, DateTimeValidator.MESSAGE_TIME_CONSTRAINTS);

        // both times invalid
        assertParseFailure(parser, "1" + INVALID_TIME_DESC_CORRECT_ORDER,
                DateTimeValidator.MESSAGE_TIME_CONSTRAINTS);

        // invalid times - start time after end time
        assertParseFailure(parser, "1" + INVALID_TIME_DESC_INCORRECT_ORDER,
                DateTimeValidator.MESSAGE_TIME_CONSTRAINTS);

        // invalid description containing deadline prefix, without quotes
        assertParseFailure(parser, "1" + UNQUOTED_DESCRIPTION_PAPER + TIME_DESC_INTERNSHIP + TAG_DESC_URGENT,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION + INVALID_TIME_DESC_INCORRECT_ORDER
                        + VALID_DEADLINE_INTERNSHIP + VALID_TAG_NOT_URGENT,
                Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        // all fields valid
        Index targetIndex = INDEX_SECOND_TASK;
        String userInput = targetIndex.getOneBased() + VALID_DESCRIPTION_INTERNSHIP + TIME_DESC_INTERNSHIP
                + TAG_DESC_URGENT + DEADLINE_DESC_INTERNSHIP + TAG_DESC_GROUP;

        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_INTERNSHIP).withEventTimes(TIME_DESC_INTERNSHIP)
                .withDeadline(VALID_DEADLINE_INTERNSHIP).withTags(VALID_TAG_URGENT, VALID_TAG_GROUP).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // one invalid time, one valid time - only one valid time added
        targetIndex = INDEX_SECOND_TASK;
        userInput = targetIndex.getOneBased() + VALID_DESCRIPTION_INTERNSHIP + INVALID_STARTTIME_VALID_ENDTIME_DESC
                + TAG_DESC_URGENT + DEADLINE_DESC_INTERNSHIP + TAG_DESC_GROUP;

        descriptor = new EditTaskDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_INTERNSHIP).withEventTimes(ENDTTIME_DESC_INTERNSHIP)
                .withDeadline(VALID_DEADLINE_INTERNSHIP).withTags(VALID_TAG_URGENT, VALID_TAG_GROUP).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        // deadline specified
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + DEADLINE_DESC_GRAD_SCHOOL;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withDeadline(VALID_DEADLINE_GRAD_SCHOOL)
                .build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // event times specified
        userInput = targetIndex.getOneBased() + TIME_DESC_GRAD_SCHOOL;

        descriptor = new EditTaskDescriptorBuilder().withEventTimes(TIME_DESC_GRAD_SCHOOL).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);

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

        // single time specified
        userInput = targetIndex.getOneBased() + ENDTIME_DESC_PAPER;
        descriptor = new EditTaskDescriptorBuilder().withEventTimes(ENDTIME_DESC_PAPER).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // start and end times specified
        userInput = targetIndex.getOneBased() + TIME_DESC_GYM;
        descriptor = new EditTaskDescriptorBuilder().withEventTimes(TIME_DESC_GYM).build();
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
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + INVALID_DEADLINE_DESC + DEADLINE_DESC_INTERNSHIP;
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withDeadline(VALID_DEADLINE_INTERNSHIP)
                .build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_DEADLINE_DESC + TIME_DESC_INTERNSHIP + DEADLINE_DESC_INTERNSHIP
                + TAG_DESC_NOT_URGENT;
        descriptor = new EditTaskDescriptorBuilder().withDeadline(VALID_DEADLINE_INTERNSHIP)
                .withDeadline(VALID_DEADLINE_INTERNSHIP).withEventTimes(TIME_DESC_INTERNSHIP)
                .withTags(VALID_TAG_NOT_URGENT).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // invalid time PAIR followed by valid time PAIR
        userInput = targetIndex.getOneBased() + INVALID_TIME_DESC_CORRECT_ORDER + TIME_DESC_GYM;
        descriptor = new EditTaskDescriptorBuilder().withEventTimes(TIME_DESC_GYM).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    //@@author
    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTags().build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
