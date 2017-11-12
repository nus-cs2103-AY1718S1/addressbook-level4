package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SCHEDULE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SCHEDULE;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Calendar;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.schedule.Schedule;

//@@author limcel
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteScheduleCommand}.
 */
public class DeleteScheduleCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexScheduleList_success() throws Exception {

        model = createAndAddScheduleToModel(model);
        Schedule scheduleToDelete = model.getScheduleList().get(INDEX_FIRST_SCHEDULE.getZeroBased());
        DeleteScheduleCommand deleteScheduleCommand = prepareCommand(INDEX_FIRST_SCHEDULE);

        String expectedMessage = String.format(DeleteScheduleCommand.MESSAGE_DELETE_SCHEDULE_SUCCESS,
                scheduleToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeSchedule(scheduleToDelete);

        assertCommandSuccess(deleteScheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexScheduleList_throwsCommandException() {

        Index outOfBoundIndex = INDEX_SECOND_SCHEDULE;
        // ensures that outOfBoundIndex is not in bounds of address book list, because
        // schedule list contains zero schedules by default
        assertFalse(outOfBoundIndex.getZeroBased() < model.getAddressBook().getScheduleList().size());

        DeleteScheduleCommand deleteScheduleCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteScheduleCommand, model, Messages.MESSAGE_INVALID_SCHEDULE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteScheduleCommand deleteFirstScheduleCommand = new DeleteScheduleCommand(INDEX_FIRST_SCHEDULE);
        DeleteScheduleCommand deleteSecondScheduleCommand = new DeleteScheduleCommand(INDEX_SECOND_SCHEDULE);

        // same object -> returns true
        assertTrue(deleteFirstScheduleCommand.equals(deleteFirstScheduleCommand));

        // same values -> returns true
        DeleteScheduleCommand deleteFirstCommandCopy = new DeleteScheduleCommand(INDEX_FIRST_SCHEDULE);
        assertTrue(deleteFirstScheduleCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstScheduleCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstScheduleCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstScheduleCommand.equals(deleteSecondScheduleCommand));
    }

    //======================================= HELPER METHODS ==========================================
    /**
     * Returns a {@code DeleteScheduleCommand} with the parameter {@code index}.
     */
    private DeleteScheduleCommand prepareCommand(Index index) {
        DeleteScheduleCommand deleteScheduleCommand = new DeleteScheduleCommand(index);
        deleteScheduleCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteScheduleCommand;
    }

    /**
     * Updates {@code model}'s list to show no schedule.
     */
    private void showNoSchedule(Model model) {
        assert model.getScheduleList().isEmpty();
    }

    /**
     * Creates and add schedule to the model.
     */
    private Model createAndAddScheduleToModel(Model model) throws PersonNotFoundException {
        Calendar calendar = Calendar.getInstance();
        Schedule newSchedule = new Schedule(ALICE.getName().toString(), calendar);
        model.addSchedule(newSchedule);
        return model;
    }

}
