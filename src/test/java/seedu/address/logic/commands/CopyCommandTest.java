package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

//@@author rushan-khor
public class CopyCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private ReadOnlyPerson noEmailPerson = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("null@null.com").withAddress("little india")
            .withBloodType("AB-").withAppointment("Hoon Meier").build();

    @Test
    public void testGetValidTargetEmail() {
        CopyCommand command = prepareCommand(INDEX_FIRST_PERSON);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        String result = "";

        try {
            result = command.getTargetEmail();
        } catch (CommandException e) {
            fail();
        }

        assertTrue("alice@example.com".equals(result));
    }

    @Test
    public void testGetInvalidTargetEmail() {
        CopyCommand command = prepareInvalidCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void testIsEmailValid() {
        CopyCommand command = prepareCommand(INDEX_FIRST_PERSON);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        boolean result = command.isEmailValid(noEmailPerson.getEmail().toString());

        assertFalse(result);
    }

    @Test
    public void testExecute() {
        CopyCommand command = prepareInvalidPersonCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        CommandResult result = new CommandResult("Empty");

        try {
            result = command.execute();
        } catch (CommandException e) {
            fail();
        }

        assertEquals(result.feedbackToUser, "Jon Anderson has no email address.");
    }

    /**
     * Returns a {@code CopyCommand} with the parameter {@code index}.
     */
    private CopyCommand prepareCommand(Index index) {
        CopyCommand copyCommand = new CopyCommand(index);
        copyCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return copyCommand;
    }

    private CopyCommand prepareInvalidPersonCommand() {
        CopyCommand copyCommand = new CopyCommand(Index.fromOneBased(8));
        copyCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return copyCommand;
    }

    private CopyCommand prepareInvalidCommand() {
        CopyCommand copyCommand = new CopyCommand(Index.fromOneBased(999));
        copyCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return copyCommand;
    }
}

