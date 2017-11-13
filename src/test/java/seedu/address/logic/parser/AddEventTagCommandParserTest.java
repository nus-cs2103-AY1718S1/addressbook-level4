package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddEventTagCommand;
import seedu.address.model.schedule.Day;
import seedu.address.model.schedule.Time;
import seedu.address.model.tag.Tag;

import static seedu.address.logic.commands.CommandTestUtil.DAY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.END_TIME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class AddEventTagCommandParserTest {
    private AddEventTagCommandParser parser = new AddEventTagCommandParser();
    @Test
    public void parseAllFieldsPresentSuccess() throws IllegalValueException {
        Index[] exampleIndices = {Index.fromOneBased(1), Index.fromOneBased(2), Index.fromOneBased(3)};
        String indicesDes = " persons/1 2 3";
        String eventName = "ProjectMeeting";
        String eventDes = "n/ProjectMeeting ";
        String location = "School";
        String locationDes = "loc/School ";
        Day exampleDay = new Day("Friday");
        Time exampleStartTime = new Time("0730");
        Time exampleEndTime = new Time("1000");
        String tagStr = eventName + "on" + exampleDay.toString() + "at" + location;
        Tag tag = new Tag(tagStr);

        String input =  eventDes + " " + locationDes + " "
                + DAY_DESC_AMY
                + START_TIME_DESC_AMY
                + END_TIME_DESC_AMY;
        assertParseSuccess(parser, input, new AddEventTagCommand(
                exampleDay, exampleStartTime, exampleEndTime, exampleIndices, tag));
    }
}
