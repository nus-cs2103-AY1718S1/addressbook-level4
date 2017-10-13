package seedu.address.logic.commands;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code UnaliasCommand}.
 */
public class UnaliasCommandTest {

    private static final String LIST_COMMAND_ALIAS = "show";

    private Model model;

    @Before
    public void setUp() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.getAliases().addAlias(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD);
        model = new ModelManager(getTypicalAddressBook(), userPrefs);
    }

    @Test
    public void execute_validAlias_success() throws Exception {
        UnaliasCommand unaliasCommand = new UnaliasCommand(LIST_COMMAND_ALIAS);
        unaliasCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        assertCommandSuccess(
                unaliasCommand,
                model,
                String.format(UnaliasCommand.MESSAGE_SUCCESS, LIST_COMMAND_ALIAS),
                model
        );

        assertNull(UserPrefs.getInstance().getAliases().getCommand(LIST_COMMAND_ALIAS));
    }

    @Test
    public void execute_invalidAlias_throwsNoSuchElementException() throws Exception {
        UnaliasCommand unaliasCommand = new UnaliasCommand(LIST_COMMAND_ALIAS);
        unaliasCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        assertCommandFailure(
                unaliasCommand,
                model,
                String.format(UnaliasCommand.MESSAGE_NO_SUCH_ALIAS, LIST_COMMAND_ALIAS)
        );
    }

    @Test
    public void equals() {
        UnaliasCommand unaliasCommand = new UnaliasCommand(LIST_COMMAND_ALIAS);
        unaliasCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        // same object -> returns true
        assertTrue(unaliasCommand.equals(unaliasCommand));

        // same values -> returns true
        UnaliasCommand unaliasCommandCopy = new UnaliasCommand(LIST_COMMAND_ALIAS);
        assertTrue(unaliasCommand.equals(unaliasCommandCopy));

        // different types -> returns false
        assertFalse(unaliasCommand.equals(1));

        // null -> returns false
        assertFalse(unaliasCommand.equals(null));

        // different values -> returns false
        UnaliasCommand unaliasCommandOther = new UnaliasCommand(LIST_COMMAND_ALIAS + "2");
        assertFalse(unaliasCommand.equals(unaliasCommandOther));
    }

}
