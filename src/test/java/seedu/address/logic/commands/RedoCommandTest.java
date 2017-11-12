package seedu.address.logic.commands;

import static seedu.address.logic.UndoRedoStackUtil.prepareStack;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.CommandTest;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class RedoCommandTest extends CommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    @Test
    public void execute() throws Exception {
        DeleteCommand deleteCommandOne = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteCommandTwo = new DeleteCommand(INDEX_SECOND_PERSON);
        deleteCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        deleteCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        UndoRedoStack undoRedoStack = prepareStack(
                Collections.emptyList(), Arrays.asList(deleteCommandTwo, deleteCommandOne));
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // multiple commands in redoStack
        deleteFirstPerson(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in redoStack
        deleteFirstPerson(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in redoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_redoCommandAddsWhitelistedPersonAndUpdatesWhitelist_success()
            throws Exception {

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.addWhitelistedPerson(model.getFilteredPersonList().get(0));

        // Preparation done on actual model
        RepaidCommand repaidCommand = new RepaidCommand(INDEX_FIRST_PERSON);
        repaidCommand.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);

        // Preparation done on actual model
        UndoRedoStack undoRedoStack = prepareStack(
                Collections.emptyList(), Arrays.asList(repaidCommand));
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);

        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_redoCommandUnbansBlacklistedPersonAndUpdatesBlacklist_success()
            throws Exception {

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.removeBlacklistedPerson(expectedModel
                .getFilteredBlacklistedPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        expectedModel.changeListTo("blacklist");

        // Preparation done on actual model
        model.changeListTo("blacklist");
        UnbanCommand unbanCommand = new UnbanCommand(INDEX_FIRST_PERSON);
        unbanCommand.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);


        // Preparation done on actual model
        UndoRedoStack undoRedoStack = prepareStack(
                Collections.emptyList(), Arrays.asList(unbanCommand));
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);

        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
