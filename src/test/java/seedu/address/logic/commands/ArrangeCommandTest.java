package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

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
 * Arrange a meeting test. As all the tests for date and time classes are added independently, this test only includes
 * the command test with valid time and date form.
 */
public class ArrangeCommandTest {

    private Model model;
    private Model expectedModel;
    private ArrangeCommand arrangeCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        int[] indexToTest = {1, 2};
        arrangeCommand = new ArrangeCommand(indexToTest);
        arrangeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void findCommonSlotSuccess() throws IllegalValueException, PersonNotFoundException {
        Slot firstSlot = new Slot(new Day("Monday"), new Time("0700"), new Time("1000"));
        Slot secondSlot = new Slot(new Day("Monday"), new Time("0930"), new Time("1100"));
        model.addScheduleToPerson(0, firstSlot.getBusyTime());
        model.addScheduleToPerson(1, secondSlot.getBusyTime());
        assertCommandSuccess(arrangeCommand, model, ArrangeCommand.MESSAGE_ARRANGE_PERSON_SUCCESS
                + arrangeCommand.scheduleInfo(), expectedModel);
    }
}
