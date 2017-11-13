package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.DAY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.END_TIME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ET_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ST_DESC;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ClearScheduleCommand;
import seedu.address.model.schedule.Day;
import seedu.address.model.schedule.Time;

//@@author YuchenHe98
public class ClearScheduleCommandParserTest {
    private ClearScheduleCommandParser parser = new ClearScheduleCommandParser();

    @Test
    public void parseAllFieldsPresentSuccess() throws IllegalValueException {

        Index exampleIndex = Index.fromOneBased(1);
        Day exampleDay = new Day("Friday");
        Time exampleStartTime = new Time("0730");
        Time exampleEndTime = new Time("1000");
        String input =  "1" + DAY_DESC_AMY
                + START_TIME_DESC_AMY
                + END_TIME_DESC_AMY;
        assertParseSuccess(parser, input, new ClearScheduleCommand(exampleIndex,
                exampleDay, exampleStartTime, exampleEndTime));
    }

    @Test
    public void parseInvalidTimeFailure() throws IllegalValueException {
        String tooEarly =  "1" + DAY_DESC_AMY
                + INVALID_ST_DESC
                + END_TIME_DESC_AMY;
        assertParseFailure(parser, tooEarly, "Not a proper time form");

        String tooLate =  "1" + DAY_DESC_AMY
                + START_TIME_DESC_AMY
                + INVALID_ET_DESC;
        assertParseFailure(parser, tooLate, "Not a proper time form");
    }
}

