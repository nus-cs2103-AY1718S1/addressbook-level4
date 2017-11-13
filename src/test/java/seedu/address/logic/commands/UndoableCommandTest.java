package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstEvent;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.address.logic.commands.CommandTestUtil.showFirstEventOnly;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Account;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

public class UndoableCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs(),
            new Account(), new Config());

    private final DummyCommand dummyCommand = new DummyCommand(model);

    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs(),
            new Account(), new Config());

    // 1 is for address book turn, 2 is for event book turn;
    private int undoRedoTurn = 1;

    public UndoableCommandTest() throws IllegalValueException {
    }


    @Test
    public void executeUndo() throws Exception {

        //excute undo for address book
        undoRedoTurn = 1;

        dummyCommand.execute();
        deleteFirstPerson(expectedModel);
        assertEquals(expectedModel, model);

        showFirstPersonOnly(model);

        // undo() should cause the model's filtered list to show all persons
        dummyCommand.undo();
        expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs(),
                new Account(), new Config());
        assertEquals(expectedModel, model);

        //excute undo for event book
        undoRedoTurn = 2;

        dummyCommand.execute();
        deleteFirstEvent(expectedModel);
        assertEquals(expectedModel, model);

        showFirstEventOnly(model);

        // undo() should cause the model's filtered list to show all persons
        dummyCommand.undo();
        expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs(),
                new Account(), new Config());
        assertEquals(expectedModel, model);
    }

    @Test
    public void redo() {

        //excute redo for address book
        undoRedoTurn = 1;

        showFirstPersonOnly(model);

        // redo() should cause the model's filtered list to show all persons
        dummyCommand.redo();
        deleteFirstPerson(expectedModel);
        assertEquals(expectedModel, model);

        //excute undo for event book
        undoRedoTurn = 2;

        showFirstEventOnly(model);

        // redo() should cause the model's filtered list to show all persons
        dummyCommand.redo();
        deleteFirstEvent(expectedModel);
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

            if (undoRedoTurn == 1) {
                ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(0);
                try {
                    model.deletePerson(personToDelete);
                } catch (PersonNotFoundException pnfe) {
                    fail("Impossible: personToDelete was retrieved from model.");
                }

            } else if (undoRedoTurn == 2) {
                ReadOnlyEvent eventToDelete = model.getFilteredEventList().get(0);
                try {
                    model.deleteEvent(eventToDelete);
                } catch (EventNotFoundException enfe) {
                    fail("Impossible: eventToDelete was retrieved from model.");
                }
            }
            return new CommandResult("");
        }
    }
}
