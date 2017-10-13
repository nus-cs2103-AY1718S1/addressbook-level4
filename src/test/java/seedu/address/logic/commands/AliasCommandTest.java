package seedu.address.logic.commands;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class AliasCommandTest {

    private static final String LIST_COMMAND_ALIAS = "show";

    private Model model;
    private AliasCommand aliasCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        aliasCommand = new AliasCommand(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD);
        aliasCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_alias_addSuccess() throws Exception {
        assertCommandSuccess(
                aliasCommand,
                model,
                String.format(AliasCommand.MESSAGE_ADD_SUCCESS, LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD),
                model
        );

        assertTrue(UserPrefs.getAliases().getCommand(LIST_COMMAND_ALIAS) == ListCommand.COMMAND_WORD);
    }

    @Test
    public void execute_alias_listSuccess() throws Exception {
        aliasCommand.execute();

        Command aliasListCommand = new AliasCommand();
        aliasListCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        assertCommandSuccess(
                aliasListCommand,
                model,
                String.format(AliasCommand.MESSAGE_LIST_SUCCESS,
                        String.format("%1$s=%2$s\n", LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD)),
                model
        );
    }

    @Test
    public void execute_alias_deleteSuccess() throws Exception {
        aliasCommand.execute();

        Command aliasDeleteCommand = new AliasCommand(LIST_COMMAND_ALIAS, true);
        aliasDeleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        assertCommandSuccess(
                aliasDeleteCommand,
                model,
                String.format(AliasCommand.MESSAGE_DELETE_SUCCESS, LIST_COMMAND_ALIAS),
                model
        );

        assertNull(UserPrefs.getAliases().getCommand(LIST_COMMAND_ALIAS));
    }

    @Test
    public void execute_alias_commandsEqual() throws Exception {
        aliasCommand.execute();

        final AddressBookParser parser = new AddressBookParser();

        Command firstCommand = parser.parseCommand(LIST_COMMAND_ALIAS);
        Command secondCommand = parser.parseCommand(ListCommand.COMMAND_WORD);
        assertEquals(firstCommand.getClass(), secondCommand.getClass());
    }

    @Test
    public void equals() {
        AliasCommand aliasFirstCommand = new AliasCommand(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD);

        // same object -> returns true
        assertTrue(aliasFirstCommand.equals(aliasFirstCommand));

        // same values -> returns true
        AliasCommand aliasFirstCommandCopy = new AliasCommand(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD);
        assertTrue(aliasFirstCommand.equals(aliasFirstCommandCopy));

        // different types -> returns false
        assertFalse(aliasFirstCommand.equals(1));

        // null -> returns false
        assertFalse(aliasFirstCommand.equals(null));

        // different alias -> returns false
        AliasCommand aliasSecondCommand = new AliasCommand(LIST_COMMAND_ALIAS + "2", ListCommand.COMMAND_WORD);
        assertFalse(aliasFirstCommand.equals(aliasSecondCommand));

        // different command -> returns false
        AliasCommand aliasThirdCommand = new AliasCommand(LIST_COMMAND_ALIAS, FindCommand.COMMAND_WORD);
        assertFalse(aliasFirstCommand.equals(aliasThirdCommand));
    }

}
