package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.ScheduleBuilder;

//@@author CT15
public class ScheduleCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //@@author 17navasaw
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullSchedule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);

        Set<Index> indices = new HashSet<>();
        indices.add(INDEX_FIRST_PERSON);

        new ScheduleCommand(null, null, null);
        new ScheduleCommand(indices, null, null);
    }

    @Test
    public void execute_addValidScheduleSuccessful() throws Exception {
        Schedule validSchedule = new ScheduleBuilder().build();
        Set<Index> indices = new HashSet<>();
        indices.add(INDEX_FIRST_PERSON);

        CommandResult commandResult = getScheduleCommandForPerson(indices, validSchedule, model).execute();

        assertEquals(String.format(ScheduleCommand.MESSAGE_SCHEDULE_SUCCESS, indices.size()),
                commandResult.feedbackToUser);
    }

    /**
     * Generates a new ScheduleCommand with the details of the given schedule.
     */
    private ScheduleCommand getScheduleCommandForPerson(Set<Index> indices, Schedule validSchedule, Model model) {
        ScheduleCommand command =
                new ScheduleCommand(indices, validSchedule.getScheduleDate(), validSchedule.getActivity());
        command.setData(model, new UserPrefs(), new CommandHistory(), new UndoRedoStack());
        return command;
    }

    //@@author CT15
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
