package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.schedule.Day;
import seedu.address.model.schedule.Slot;
import seedu.address.model.schedule.Time;

//@@author YuchenHe98
/**
 * test for visualizing a person's schedule
 */
public class VisualizeCommandTest {

    private Model model;
    private Model expectedModel;
    private VisualizeCommand visualizeCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        visualizeCommand = new VisualizeCommand(Index.fromOneBased(1));
        visualizeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void visualizeCommonSlotSuccess() throws IllegalValueException, PersonNotFoundException {
        Slot firstSlot = new Slot(new Day("Monday"), new Time("0700"), new Time("1000"));
        Slot secondSlot = new Slot(new Day("Tuesday"), new Time("0930"), new Time("1100"));
        model.addScheduleToPerson(0, firstSlot.getBusyTime());
        model.addScheduleToPerson(0, secondSlot.getBusyTime());
        assertCommandSuccess(visualizeCommand, model, VisualizeCommand.MESSAGE_VISUALIZE_PERSON_SUCCESS
                + "1" + visualizeCommand.scheduleInfo(), expectedModel);
    }
}

