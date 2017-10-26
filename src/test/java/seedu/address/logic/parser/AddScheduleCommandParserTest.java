package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddScheduleCommand;
import seedu.address.model.schedule.Day;
import seedu.address.model.schedule.Time;

public class AddScheduleCommandParserTest {
    private AddScheduleCommandParser parser = new AddScheduleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws IllegalValueException {

        Index exampleIndex = Index.fromOneBased(1);
        Day exampleDay = new Day("Friday");
        Time exampleStartTime = new Time("0730");
        Time exampleEndTime = new Time("1000");
        String input = AddScheduleCommand.COMMAND_WORD + " 1 " + DAY_DESC_AMY +
                START_TIME_DESC_AMY
                + END_TIME_DESC_AMY;

        System.out.println(input);
        // multiple names - last name accepted
        assertParseSuccess(parser, input, new AddScheduleCommand(exampleIndex, exampleDay, exampleStartTime,
                exampleEndTime));
    }
}

