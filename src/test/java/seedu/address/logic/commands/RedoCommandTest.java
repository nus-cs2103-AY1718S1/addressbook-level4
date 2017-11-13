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
import seedu.address.logic.LogicManager;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Account;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

public class RedoCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs(), new
            Account(), new Config());

    private final DeleteCommand deleteCommandOne = new DeleteCommand(INDEX_FIRST_PERSON);
    private final DeleteEventCommand deleteEventCommandTwo = new DeleteEventCommand(INDEX_FIRST_EVENT);

    public RedoCommandTest() {
    }

    @Before
    public void setUp() {
        UserPrefs userPrefs = new UserPrefs();
        Config config = new Config();
        Logic logic = null;
        deleteCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK, new Config(), new UiManager(logic, config,
                userPrefs));
        deleteEventCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK, new Config(), new UiManager(logic,
                config, userPrefs));
    }

    @Test
    public void execute() {
        UndoRedoStack undoRedoStack = prepareStack(
                Collections.emptyList(), Arrays.asList(deleteEventCommandTwo, deleteCommandOne));
        RedoCommand redoCommand = new RedoCommand();
        UserPrefs userPrefs = new UserPrefs();
        Config config = new Config();
        Ui ui = null;
        Logic logic = new LogicManager(model, userPrefs, config, ui);
        ui = new UiManager(logic, config, userPrefs);
        logic.setUi(ui);
        redoCommand.setData(model, new CommandHistory(), undoRedoStack, new Config(), ui);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs(), new
                Account(), new Config());

        // multiple commands in redoStack
        deleteFirstPerson(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in redoStack
        deleteFirstEvent(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in redoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }
}
