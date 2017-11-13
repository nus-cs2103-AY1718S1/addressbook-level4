//@@author danielbrzn

package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class AliasCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addAlias_success() throws Exception {

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addAlias("a", "add");

        assertCommandSuccess(prepareCommand("a", "add"), model,
                String.format(AliasCommand.MESSAGE_SUCCESS, "a", "add"),
                expectedModel);
    }

    @Test
    public void execute_addAliasForInvalidCommand_failure() throws Exception {

        assertCommandFailure(prepareCommand("a", "abcde"), model, AliasCommand.MESSAGE_INVALID_COMMAND);
    }

    @Test
    public void execute_addAliasOverride_success() throws Exception {

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addAlias("a", "add");

        model.addAlias("a", "list");

        assertCommandSuccess(prepareCommand("a", "add"), model,
                String.format(AliasCommand.MESSAGE_OVERRIDE, "a", "add", "list"),
                expectedModel);

    }

    @Test
    public void execute_addRestrictedAlias_failure() throws Exception {

        assertCommandFailure(prepareCommand("add", "add"), model,
                AliasCommand.MESSAGE_RESTRICTED_ALIAS);
    }

    @Test
    public void equals() {
        AliasCommand aliasAddCommand = new AliasCommand("a", "add");
        AliasCommand aliasFindCommand = new AliasCommand("f", "find");

        // same object -> returns true
        assertTrue(aliasAddCommand.equals(aliasAddCommand));

        // same values -> returns true;
        AliasCommand aliasAddCommandCopy = new AliasCommand("a", "add");
        assertTrue(aliasAddCommand.equals(aliasAddCommandCopy));

        // different types -> returns false
        assertFalse(aliasAddCommand.equals(1));

        // null -> returns false
        assertFalse(aliasAddCommand.equals(null));

        // different commands -> returns false
        assertFalse(aliasAddCommand.equals(aliasFindCommand));
    }

    /**
     * Returns an {@code AliasCommand} with parameters {@code alias} and {@code command}
     */
    private AliasCommand prepareCommand(String alias, String command) {
        AliasCommand aliasCommand = new AliasCommand(alias, command);
        aliasCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return aliasCommand;
    }
}
