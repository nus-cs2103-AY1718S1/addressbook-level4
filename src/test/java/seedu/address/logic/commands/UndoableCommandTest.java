package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstPlace;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPlaceOnly;
import static seedu.address.testutil.TypicalPlaces.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.place.ReadOnlyPlace;
import seedu.address.model.place.exceptions.PlaceNotFoundException;

public class UndoableCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final DummyCommand dummyCommand = new DummyCommand(model);

    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executeUndo() throws Exception {
        dummyCommand.execute();
        deleteFirstPlace(expectedModel);
        assertEquals(expectedModel, model);

        showFirstPlaceOnly(model);

        // undo() should cause the model's filtered list to show all places
        dummyCommand.undo();
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertEquals(expectedModel, model);
    }

    @Test
    public void redo() {
        showFirstPlaceOnly(model);

        // redo() should cause the model's filtered list to show all places
        dummyCommand.redo();
        deleteFirstPlace(expectedModel);
        assertEquals(expectedModel, model);
    }

    /**
     * Deletes the first place in the model's filtered list.
     */
    class DummyCommand extends UndoableCommand {
        DummyCommand(Model model) {
            this.model = model;
        }

        @Override
        public CommandResult executeUndoableCommand() throws CommandException {
            ReadOnlyPlace placeToDelete = model.getFilteredPlaceList().get(0);
            try {
                model.deletePlace(placeToDelete);
            } catch (PlaceNotFoundException pnfe) {
                fail("Impossible: placeToDelete was retrieved from model.");
            }
            return new CommandResult("");
        }
    }
}
