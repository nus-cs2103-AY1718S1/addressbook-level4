package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalPersons;

public class SortCommandTest {
    private Model model;

    @Test
    public void execute() throws Exception {
        //setting up the model to for entries in reverse order
        AddressBook abNIO = new AddressBook();
        abNIO.addPerson(TypicalPersons.ELLE);
        abNIO.addPerson(TypicalPersons.DANIEL);
        abNIO.addPerson(TypicalPersons.CARL);
        model = new ModelManager(abNIO, new UserPrefs());

        //create expectations
        AddressBook abIO = new AddressBook();
        abIO.addPerson(TypicalPersons.CARL);
        abIO.addPerson(TypicalPersons.DANIEL);
        abIO.addPerson(TypicalPersons.ELLE);
        ModelManager expectedModel = new ModelManager(abIO, new UserPrefs());

        //command and return result
        SortCommand sortCommand = prepareCommand();
        String expectedMessage = SortCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    private SortCommand prepareCommand() {
        SortCommand sortCommand = new SortCommand();
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }
}
