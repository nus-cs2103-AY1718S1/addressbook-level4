package seedu.address.logic.commands;

// @@author itsdickson

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.favouriteFirstPerson;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class FavouriteListCommandTest {

    private Model model;
    private Model expectedModel;
    private Model emptyModel;
    private FavouriteListCommand favouriteListCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        emptyModel = new ModelManager(new AddressBook(), new UserPrefs());

        favouriteListCommand = new FavouriteListCommand();
        favouriteListCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_favouriteListEmpty_showsNothing() {
        CommandResult result = favouriteListCommand.execute();
        assertEquals(result.feedbackToUser, FavouriteListCommand.MESSAGE_SUCCESS);
        assertEquals(model.getFilteredPersonList(), emptyModel.getFilteredPersonList());
    }

    @Test
    public void execute_favouriteListNotEmpty_showsPerson() {
        favouriteFirstPerson(model);
        favouriteFirstPerson(expectedModel);
        assertEquals(model, expectedModel);

        CommandResult result = favouriteListCommand.execute();
        assertEquals(result.feedbackToUser, FavouriteListCommand.MESSAGE_SUCCESS);
    }
}
// @@author
