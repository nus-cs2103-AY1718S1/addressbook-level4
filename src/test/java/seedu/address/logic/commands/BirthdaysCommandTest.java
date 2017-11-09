package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class BirthdaysCommandTest {

    private Model model;
    private Model expectedModel;
    private BirthdaysCommand birthdaysCommand;
    private Model emptyModel;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        birthdaysCommand = new BirthdaysCommand();
        birthdaysCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void noBirthdaysToday() {
        CommandResult result = birthdaysCommand.execute();
        assertEquals(result.feedbackToUser, "Wish these 0 people a Happy Birthday!");
        assertEquals(model.getFilteredPersonList(), emptyModel.getFilteredPersonList());
    }

}
