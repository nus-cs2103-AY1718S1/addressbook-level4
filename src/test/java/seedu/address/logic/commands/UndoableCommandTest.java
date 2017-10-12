package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstParcel;
import static seedu.address.logic.commands.CommandTestUtil.showFirstParcelOnly;
import static seedu.address.testutil.TypicalParcels.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.parcel.ReadOnlyParcel;
import seedu.address.model.parcel.exceptions.ParcelNotFoundException;

public class UndoableCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final DummyCommand dummyCommand = new DummyCommand(model);

    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executeUndo() throws Exception {
        dummyCommand.execute();
        deleteFirstParcel(expectedModel);
        assertEquals(expectedModel, model);

        showFirstParcelOnly(model);

        // undo() should cause the model's filtered list to show all parcels
        dummyCommand.undo();
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertEquals(expectedModel, model);
    }

    @Test
    public void redo() {
        showFirstParcelOnly(model);

        // redo() should cause the model's filtered list to show all parcels
        dummyCommand.redo();
        deleteFirstParcel(expectedModel);
        assertEquals(expectedModel, model);
    }

    /**
     * Deletes the first parcel in the model's filtered list.
     */
    class DummyCommand extends UndoableCommand {
        DummyCommand(Model model) {
            this.model = model;
        }

        @Override
        public CommandResult executeUndoableCommand() throws CommandException {
            ReadOnlyParcel parcelToDelete = model.getFilteredParcelList().get(0);
            try {
                model.deleteParcel(parcelToDelete);
            } catch (ParcelNotFoundException pnfe) {
                fail("Impossible: parcelToDelete was retrieved from model.");
            }
            return new CommandResult("");
        }
    }
}
