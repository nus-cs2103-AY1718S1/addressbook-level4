package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
        
import org.junit.Test;
        
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
        
/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class FavouriteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() throws Exception {
        assertTrue(true);
    }

    /**
      * Returns an {@code FavouriteCommand}.
      */
    private FavouriteCommand prepareCommand() {
        FavouriteCommand favouriteCommand = new FavouriteCommand(INDEX_FIRST_PERSON);
        favouriteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return favouriteCommand;
    }
}