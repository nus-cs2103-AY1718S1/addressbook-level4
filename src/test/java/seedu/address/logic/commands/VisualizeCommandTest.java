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

/**
 * Arrange a meeting test. As all the tests for date and time classes are added independently, this test only includes
 * the command test with valid time and date form.
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
    public void findCommonSlotSuccess() throws IllegalValueException, PersonNotFoundException {
        Slot FirstSlot = new Slot(new Day("Monday"), new Time("0700"), new Time("1000"));
        Slot SecondSlot = new Slot(new Day("Tuesday"), new Time("0930"), new Time("1100"));
        model.addScheduleToPerson(0, FirstSlot.getBusyTime());
        model.addScheduleToPerson(0, SecondSlot.getBusyTime());
        assertCommandSuccess(visualizeCommand, model, VisualizeCommand.MESSAGE_VISUALIZE_PERSON_SUCCESS
                + "1" + visualizeCommand.scheduleInfo(), expectedModel);
    }
}

