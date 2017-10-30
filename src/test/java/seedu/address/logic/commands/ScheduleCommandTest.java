package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.ScheduleBuilder;

public class ScheduleCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullSchedule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);

        Set<Index> indices = new HashSet<>();
        indices.add(INDEX_FIRST_PERSON);

        new ScheduleCommand(null, null, null);
        new ScheduleCommand(indices, null, null);
        //new ScheduleCommand(null, new ScheduleBuilder().build());
    }

    @Test
    public void equals() {
        Schedule meeting = new ScheduleBuilder().withActivity("Meeting").build();
        Schedule playing = new ScheduleBuilder().withActivity("Playing").build();

        Set<Index> indices = new HashSet<>();
        indices.add(INDEX_FIRST_PERSON);

        ScheduleCommand scheduleMeetingCommand = new ScheduleCommand(indices,
                meeting.getScheduleDate(), meeting.getActivity());
        ScheduleCommand schedulePlayingCommand = new ScheduleCommand(indices,
                playing.getScheduleDate(), playing.getActivity());

        // same object -> returns true
        assertTrue(scheduleMeetingCommand.equals(scheduleMeetingCommand));

        // same values -> returns true
        ScheduleCommand scheduleMeetingCommandCopy = new ScheduleCommand(indices,
                meeting.getScheduleDate(), meeting.getActivity());
        assertTrue(scheduleMeetingCommand.equals(scheduleMeetingCommandCopy));

        // different types -> returns false
        assertFalse(scheduleMeetingCommand.equals(1));

        // null -> returns false
        assertFalse(scheduleMeetingCommand.equals(null));

        // different activities -> returns false
        assertFalse(scheduleMeetingCommand.equals(schedulePlayingCommand));
    }
}
