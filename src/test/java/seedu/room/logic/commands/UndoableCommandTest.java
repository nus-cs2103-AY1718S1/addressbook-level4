package seedu.room.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.room.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.room.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.room.testutil.TypicalPersons.getTypicalResidentBook;

import org.junit.Test;

import seedu.room.logic.commands.exceptions.CommandException;
import seedu.room.model.Model;
import seedu.room.model.ModelManager;
import seedu.room.model.UserPrefs;
import seedu.room.model.person.ReadOnlyPerson;
import seedu.room.model.person.exceptions.PersonNotFoundException;

public class UndoableCommandTest {
    private final Model model = new ModelManager(getTypicalResidentBook(), new UserPrefs());
    private final DummyCommand dummyCommand = new DummyCommand(model);

    private Model expectedModel = new ModelManager(getTypicalResidentBook(), new UserPrefs());

    @Test
    public void executeUndo() throws Exception {
        dummyCommand.execute();
        deleteFirstPerson(expectedModel);
        assertEquals(expectedModel, model);

        showFirstPersonOnly(model);

        // undo() should cause the model's filtered list to show all persons
        dummyCommand.undo();
        expectedModel = new ModelManager(getTypicalResidentBook(), new UserPrefs());
        assertEquals(expectedModel, model);
    }

    @Test
    public void redo() {
        showFirstPersonOnly(model);

        // redo() should cause the model's filtered list to show all persons
        dummyCommand.redo();
        deleteFirstPerson(expectedModel);
        assertEquals(expectedModel, model);
    }

    /**
     * Deletes the first person in the model's filtered list.
     */
    class DummyCommand extends UndoableCommand {
        DummyCommand(Model model) {
            this.model = model;
        }

        @Override
        public CommandResult executeUndoableCommand() throws CommandException {
            ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(0);
            try {
                model.deletePerson(personToDelete);
            } catch (PersonNotFoundException pnfe) {
                fail("Impossible: personToDelete was retrieved from model.");
            }
            return new CommandResult("");
        }
    }
}
