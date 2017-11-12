//@@author ShaocongDong
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DESC_DEMO;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DESC_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.END_DESC_DEMO;
import static seedu.address.logic.commands.CommandTestUtil.END_DESC_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_DEMO;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.START_DESC_DEMO;
import static seedu.address.logic.commands.CommandTestUtil.START_DESC_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_DEMO;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_DEMO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DEMO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DEMO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DEMO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DEMO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HOTPOT;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class AddTaskCommandParserTest {
    private AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTaskHotpot = new TaskBuilder().withName(VALID_NAME_HOTPOT)
                .withDescription(VALID_DESCRIPTION_HOTPOT).withStart(VALID_START_HOTPOT)
                .withEnd(VALID_END_HOTPOT).withTags(VALID_TAG_HOTPOT).build();

        Task expectedTaskDemo = new TaskBuilder().withName(VALID_NAME_DEMO)
                .withDescription(VALID_DESCRIPTION_DEMO).withStart(VALID_START_DEMO)
                .withEnd(VALID_END_DEMO).withTags(VALID_TAG_DEMO).build();

        assertParseSuccess(parser, AddTaskCommand.COMMAND_WORD + NAME_DESC_HOTPOT
                + DESC_DESC_HOTPOT + START_DESC_HOTPOT
                + END_DESC_HOTPOT + TAG_DESC_HOTPOT, new AddTaskCommand(expectedTaskHotpot));

        assertParseSuccess(parser, AddTaskCommand.COMMAND_WORD + NAME_DESC_DEMO
                + DESC_DESC_DEMO + START_DESC_DEMO
                + END_DESC_DEMO + TAG_DESC_DEMO, new AddTaskCommand(expectedTaskDemo));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + VALID_NAME_DEMO
                + DESC_DESC_DEMO + START_DESC_DEMO
                + END_DESC_DEMO + TAG_DESC_DEMO, expectedMessage);

        // missing description prefix
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + NAME_DESC_DEMO
                + VALID_DESCRIPTION_DEMO + START_DESC_DEMO
                + END_DESC_DEMO + TAG_DESC_DEMO, expectedMessage);

        // missing start prefix
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + NAME_DESC_DEMO
                + DESC_DESC_DEMO + VALID_START_DEMO
                + END_DESC_DEMO + TAG_DESC_DEMO, expectedMessage);

        // missing end prefix
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + NAME_DESC_DEMO
                + DESC_DESC_DEMO + START_DESC_DEMO
                + VALID_END_DEMO + TAG_DESC_DEMO, expectedMessage);

        // missing tag prefix -> to be un commented after date field being implemented
        //assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + NAME_DESC_DEMO +
        //        DESC_DESC_DEMO + START_DESC_DEMO
        //        + END_DESC_DEMO + VALID_TAG_DEMO, expectedMessage);

        // missing all prefix
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + VALID_NAME_DEMO
                + VALID_DESCRIPTION_DEMO + VALID_START_DEMO
                + VALID_END_DEMO + VALID_TAG_DEMO, expectedMessage);

    }

}
