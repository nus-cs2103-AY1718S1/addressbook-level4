package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ACTIVITY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SCHEDULE_DATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACTIVITY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_DATE_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.ScheduleBuilder;

public class ScheduleCommandParserTest {
    private ScheduleCommandParser parser = new ScheduleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Schedule expectedSchedule = new ScheduleBuilder().withScheduleDate(VALID_SCHEDULE_DATE_AMY)
                .withActivity(VALID_ACTIVITY_AMY).build();
        assertParseSuccess(parser, "1" + SCHEDULE_DATE_DESC_AMY + ACTIVITY_DESC_AMY,
                new ScheduleCommand(INDEX_FIRST_PERSON, expectedSchedule.getScheduleDate(),
                        expectedSchedule.getActivity()));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        assertParseFailure(parser, SCHEDULE_DATE_DESC_AMY + ACTIVITY_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1" + SCHEDULE_DATE_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1" + ACTIVITY_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
    }
}
