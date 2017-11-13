package seedu.address.logic.commands;

import static seedu.address.logic.UndoRedoStackUtil.prepareStack;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstEvent;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Config;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Logic;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Account;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.UiManager;

public class UndoCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs(), new
            Account(), new Config());
    private final DeleteCommand deleteCommandOne = new DeleteCommand(INDEX_FIRST_PERSON);
    private final DeleteEventCommand deleteEventCommandTwo = new DeleteEventCommand(INDEX_FIRST_EVENT);

    @Before
    public void setUp() {
        UserPrefs userPrefs = new UserPrefs();
        Config config = new Config();
        Logic logic = null;
        deleteCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK, new Config(),
                new UiManager(logic, config, userPrefs));
        deleteEventCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK, new Config(),
                new UiManager(logic, config, userPrefs));
    }

    @Test
    public void execute() throws Exception {
        UndoRedoStack undoRedoStack = prepareStack(
                Arrays.asList(deleteCommandOne, deleteEventCommandTwo), Collections.emptyList());
        UndoCommand undoCommand = new UndoCommand();
        UserPrefs userPrefs = new UserPrefs();
        Config config = new Config();
        Logic logic = null;
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack, new Config(),
                new UiManager(logic, config, userPrefs));
        deleteCommandOne.execute();
        deleteEventCommandTwo.execute();

        // multiple commands in undoStack
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs(),
                new Account(), new Config());
        deleteFirstPerson(expectedModel);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in undoStack
        expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs(),
                new Account(), new Config());
        deleteFirstEvent(expectedModel);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in undoStack
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }
}
