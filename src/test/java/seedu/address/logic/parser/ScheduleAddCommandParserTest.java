package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ScheduleAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventDuration;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;

//@@author eldriclim
public class ScheduleAddCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    final String fullArgs = " m/1 n/Event name t/2017-01-01 10:00 d/5m";
    final String noMembersArgs = "  n/Event name t/2017-01-01 10:00 d/5m";
    final String noDurationArgs = " m/1 n/Event name t/2017-01-01 10:00";
    final String noMemberAndDurationArgs = " n/Event name t/2017-01-01 10:00";

    final String invalidDateFormat = " m/1 n/Event name t/2017-02-29 10:00 d/5m";
    final String invalidDurationFormat = " n/Event name t/2017-01-01 10:00 d/5m2h";
    final String invalidNoNameArgs = " t/2017-01-01 10:00";
    final String invalidNoTimeArgs = " n/Event name";

    private ScheduleAddCommandParser parser = new ScheduleAddCommandParser();

    @Test
    public void parseSuccessTest() throws IllegalValueException {
        EventName eventName = new EventName("Event name");
        Duration duration = Duration.ofMinutes(5);
        EventTime eventTime = new EventTime(LocalDateTime.of(2017, 1, 1, 10, 0),
                duration);
        EventDuration eventDuration = new EventDuration(duration);
        Set<Index> uniqueMemberIndexes = new HashSet<>();
        uniqueMemberIndexes.add(new Index(0));


        assertParseSuccess(parser, ScheduleAddCommand.COMMAND_WORD + fullArgs,
                new ScheduleAddCommand(eventName, eventTime, eventDuration, uniqueMemberIndexes));

        assertParseSuccess(parser, ScheduleAddCommand.COMMAND_WORD + noMembersArgs,
                new ScheduleAddCommand(eventName, eventTime, eventDuration, new HashSet<>()));


        //Set duration to default 0 mins
        eventTime = new EventTime(LocalDateTime.of(2017, 1, 1, 10, 0),
                Duration.ofMinutes(0));
        eventDuration = new EventDuration(Duration.ofMinutes(0));

        assertParseSuccess(parser, ScheduleAddCommand.COMMAND_WORD + noDurationArgs,
                new ScheduleAddCommand(eventName, eventTime, eventDuration, uniqueMemberIndexes));

        assertParseSuccess(parser, ScheduleAddCommand.COMMAND_WORD + noMemberAndDurationArgs,
                new ScheduleAddCommand(eventName, eventTime, eventDuration, new HashSet<>()));


    }

    @Test
    public void invalidDateTest() throws ParseException {
        thrown.expectMessage(ScheduleAddCommandParser.ERROR_INVALID_DATE);
        parser.parse(invalidDateFormat);
    }

    @Test
    public void invalidDurationFormatTest() throws ParseException {
        thrown.expectMessage(ScheduleAddCommandParser.ERROR_PARSING_DURATION);
        parser.parse(invalidDurationFormat);
    }

    @Test
    public void parseFailureTest() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleAddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, ScheduleAddCommand.COMMAND_WORD + invalidNoNameArgs,
                expectedMessage);

        // missing time prefix
        assertParseFailure(parser, ScheduleAddCommand.COMMAND_WORD + invalidNoTimeArgs,
                expectedMessage);

        // missing prefixes
        assertParseFailure(parser, ScheduleAddCommand.COMMAND_WORD,
                expectedMessage);
    }
}
