package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.schedule.Day;
import seedu.address.model.schedule.Slot;
import seedu.address.model.schedule.Time;

//@@author YuchenHe98
public class AddScheduleCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executeTypicalPersonSuccess() throws Exception {

        AddScheduleCommand addScheduleCommand = prepareCommand(Index.fromZeroBased(0),
                new Day("Monday"), new Time("0900"), new Time("1100"));
        Slot slot = new Slot(new Day("Monday"), new Time("0900"), new Time("1100"));
        model.addScheduleToPerson(0, slot.getBusyTime());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.addScheduleToPerson(0, slot.getBusyTime());
        String expectedMessage = String.format(AddScheduleCommand.MESSAGE_ADD_SCHEDULE_PERSON_SUCCESS);
        assertCommandSuccess(addScheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeInvalidIndexFilteredListThrowsCommandException() throws IllegalValueException {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        AddScheduleCommand addScheduleCommand = prepareCommand(outOfBoundIndex,
                new Day("Monday"), new Time("0900"), new Time("1100"));

        assertCommandFailure(addScheduleCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    private AddScheduleCommand prepareCommand(Index index, Day day, Time startTime, Time endTime)
            throws IllegalValueException {
        AddScheduleCommand addScheduleCommand = new AddScheduleCommand(index, day, startTime, endTime);
        addScheduleCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addScheduleCommand;
    }
}
