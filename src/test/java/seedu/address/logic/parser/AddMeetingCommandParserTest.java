package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.ADD_MEETING_INVALID_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_INVALID;
import static seedu.address.logic.commands.CommandTestUtil.DATE_PAST;
import static seedu.address.logic.commands.CommandTestUtil.DATE_VALID;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_1;
import static seedu.address.logic.commands.CommandTestUtil.NOTES_1;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_1;
import static seedu.address.logic.commands.CommandTestUtil.TIME_VALID;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.logic.commands.AddMeetingCommand;

import seedu.address.model.Meeting;

import seedu.address.model.person.InternalId;

//@@author Sri-vatsa
public class AddMeetingCommandParserTest {
    private AddMeetingCommandParser parser = new AddMeetingCommandParser();
    @Test
    public void parse_allFieldsPresent_success() throws IllegalValueException {
        ArrayList<InternalId> ids = new ArrayList<>();
        ids.add(new InternalId(2));
        LocalDateTime localDateTime = LocalDateTime.of(2020, 10, 31, 18, 00);
        Meeting expectedMeeting = new Meeting(localDateTime, "Computing", "Project meeting", ids);

        // Add meeting successfully
        assertParseSuccess(parser, AddMeetingCommand.COMMAND_WORD + DATE_VALID + TIME_VALID + LOCATION_1
                + NOTES_1 + PERSON_1, new AddMeetingCommand(expectedMeeting));
    }

    //meeting date is in the past
    @Test
    public void parse_dateFromPast_failure() throws IllegalValueException {

        assertParseFailure(parser, AddMeetingCommand.COMMAND_WORD + DATE_PAST + TIME_VALID + LOCATION_1
                + NOTES_1 + PERSON_1, "Please enter a date & time that is in the future.");
    }

    //missing fields
    @Test
    public void parse_missingFields_failure() {

        //missing id
        assertParseFailure(parser, AddMeetingCommand.COMMAND_WORD + DATE_VALID + TIME_VALID + LOCATION_1
                + NOTES_1, ADD_MEETING_INVALID_FORMAT);

        //missing date
        assertParseFailure(parser, AddMeetingCommand.COMMAND_WORD + TIME_VALID + LOCATION_1
                + NOTES_1 + PERSON_1, ADD_MEETING_INVALID_FORMAT);

        //missing time
        assertParseFailure(parser, AddMeetingCommand.COMMAND_WORD + DATE_VALID + LOCATION_1
                + NOTES_1 + PERSON_1, ADD_MEETING_INVALID_FORMAT);

        //missing location
        assertParseFailure(parser, AddMeetingCommand.COMMAND_WORD + DATE_VALID + TIME_VALID
                + NOTES_1 + PERSON_1, ADD_MEETING_INVALID_FORMAT);

        //missing notes
        assertParseFailure(parser, AddMeetingCommand.COMMAND_WORD + DATE_VALID + TIME_VALID + LOCATION_1
                 + PERSON_1, ADD_MEETING_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidDate_failure() {

        assertParseFailure(parser, AddMeetingCommand.COMMAND_WORD + DATE_INVALID + TIME_VALID + LOCATION_1
                + NOTES_1, ADD_MEETING_INVALID_FORMAT);

    }

}
