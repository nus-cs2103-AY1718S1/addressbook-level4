package seedu.address.logic.commands;

//@@author wangyiming1019
import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.favouriteFirstPerson;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonsAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class FavouriteListCommandTest {

    private Model model;
    private Model expectedModel;
    private FavouriteListCommand favouriteListCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalPersonsAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        favouriteListCommand = new FavouriteListCommand();
        favouriteListCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void executeEmptyFavouriteListShowsNothing() {
        CommandResult result = favouriteListCommand.execute();
        assertEquals(result.feedbackToUser, FavouriteListCommand.MESSAGE_SUCCESS);
    }

    @Test
    public void executeFavouriteListNotEmptyShowsPerson() {
        favouriteFirstPerson(model);
        favouriteFirstPerson(expectedModel);
        assertEquals(model, expectedModel);

        CommandResult result = favouriteListCommand.execute();
        assertEquals(result.feedbackToUser, FavouriteListCommand.MESSAGE_SUCCESS);
    }
}
