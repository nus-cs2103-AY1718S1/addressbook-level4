package seedu.address.logic.commands;

import org.junit.Before;
import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class AliasCommandTest {

    private UserPrefs userPrefs;
    private UserPrefs expectedUserPrefs;
    private Model model;
    private AliasCommand aliasCommand;

    public static final String LIST_COMMAND_ALIAS = "show";

    @Before
    public void setUp() {
        userPrefs = new UserPrefs();
        expectedUserPrefs = new UserPrefs();

        model = new ModelManager(getTypicalAddressBook(), userPrefs);

        aliasCommand = new AliasCommand(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD);
        aliasCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_alias_success() throws Exception {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addAlias(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD);

        assertCommandSuccess(aliasCommand, model, String.format(AliasCommand.MESSAGE_SUCCESS, LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD), expectedModel);
    }

    @Test
    public void execute_alias_commands_equal() throws Exception {
        aliasCommand.execute();

        final AddressBookParser parser = new AddressBookParser();
        parser.setAliases(model.getAliases());

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
