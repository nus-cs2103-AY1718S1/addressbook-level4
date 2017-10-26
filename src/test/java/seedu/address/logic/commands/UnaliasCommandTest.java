package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_KEYWORD_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAliasTokens.getTypicalAddressBookWithAlias;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.alias.UnaliasCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.alias.Keyword;
import seedu.address.model.alias.ReadOnlyAliasToken;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UnaliasCommand.
 */
public class UnaliasCommandTest {
    private Model model = new ModelManager(getTypicalAddressBookWithAlias(), new UserPrefs());

    @Test
    public void executeUnaliasCommandSuccess() throws Exception {
        Keyword toDelete = new Keyword(VALID_ALIAS_KEYWORD_MONDAY);

        ReadOnlyAliasToken aliasTokenToRemove = null;

        for (ReadOnlyAliasToken token : model.getAddressBook().getAliasTokenList()) {
            if (token.getKeyword().keyword.equalsIgnoreCase(toDelete.keyword)) {
                aliasTokenToRemove = token;
                break;
            }
        }
        String message = aliasTokenToRemove.toString();
        String expectedMessage = String.format(UnaliasCommand.MESSAGE_SUCCESS, message);

        UnaliasCommand unaliasCommand = prepareCommand(toDelete);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteAliasToken(aliasTokenToRemove);

        assertCommandSuccess(unaliasCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void executeUnaliasCommandFailure() throws Exception {
        Keyword toDelete = new Keyword("tues"); // no such alias keyword exists
        String expectedMessage = UnaliasCommand.MESSAGE_UNKNOWN_ALIAS;
        UnaliasCommand unaliasCommand = prepareCommand(toDelete);

        assertCommandFailure(unaliasCommand, model, expectedMessage);
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private UnaliasCommand prepareCommand(Keyword keyword) {
        UnaliasCommand unaliasCommand = new UnaliasCommand(keyword);
        unaliasCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return unaliasCommand;
    }
}
