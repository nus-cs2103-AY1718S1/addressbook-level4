package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.ScheduleBuilder;

public class ScheduleCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullSchedule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ScheduleCommand(null, null);
        new ScheduleCommand(INDEX_FIRST_PERSON, null);
        new ScheduleCommand(null, new ScheduleBuilder().build());
    }

    @Test
    public void equals() {
        Schedule meeting = new ScheduleBuilder().withActivity("Meeting").build();
        Schedule playing = new ScheduleBuilder().withActivity("Playing").build();
        ScheduleCommand scheduleMeetingCommand = new ScheduleCommand(INDEX_FIRST_PERSON, meeting);
        ScheduleCommand schedulePlayingCommand = new ScheduleCommand(INDEX_FIRST_PERSON, playing);

        // same object -> returns true
        assertTrue(scheduleMeetingCommand.equals(scheduleMeetingCommand));

        // same values -> returns true
        ScheduleCommand scheduleMeetingCommandCopy = new ScheduleCommand(INDEX_FIRST_PERSON, meeting);
        assertTrue(scheduleMeetingCommand.equals(scheduleMeetingCommandCopy));

        // different types -> returns false
        assertFalse(scheduleMeetingCommand.equals(1));

        // null -> returns false
        assertFalse(scheduleMeetingCommand.equals(null));

        // different person -> returns false
        assertFalse(scheduleMeetingCommand.equals(schedulePlayingCommand));
    }
}
