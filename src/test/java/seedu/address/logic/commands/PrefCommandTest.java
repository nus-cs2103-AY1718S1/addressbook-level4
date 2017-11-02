package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UniqueMeetingList;
import seedu.address.model.UserPrefs;
//@@author liuhang0213
/**
 * Contains integration tests (interaction with the Model) for {@code PrefCommand}.
 */
public class PrefCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UniqueMeetingList(), new UserPrefs());

    @Test
    public void equals() {
        String firstPrefKey = "AddressBookName";
        String firstPrefValue = model.getUserPrefs().getAddressBookName();
        String secondPrefKey = "AddressBookFilePath";
        String secondPrefValue = model.getUserPrefs().getAddressBookFilePath();

        PrefCommand prefFirstCommand = new PrefCommand(firstPrefKey, firstPrefValue);
        PrefCommand prefSecondCommand = new PrefCommand(secondPrefKey, secondPrefValue);

        // same object -> returns true
        assertTrue(prefFirstCommand.equals(prefFirstCommand));

        // same values -> returns true
        PrefCommand prefFirstCommandCopy = new PrefCommand(firstPrefKey, firstPrefValue);
        assertTrue(prefFirstCommand.equals(prefFirstCommandCopy));

        // different types -> returns false
        assertFalse(prefFirstCommand.equals(1));

        // null -> returns false
        assertFalse(prefFirstCommand.equals(null));

        // different value -> returns false
        assertFalse(prefFirstCommand.equals(prefSecondCommand));

    }

    @Test
    public void execute_invalidKey_failure() {
        String invalidKey = "This is not preference key.";
        String expectedMessage = String.format(PrefCommand.MESSAGE_PREF_KEY_NOT_FOUND, invalidKey);
        PrefCommand command = prepareCommand(invalidKey, "");
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_validKey_displayValue() {
        String key = "AddressBookName";
        String expectedOutput = model.getUserPrefs().getAddressBookName();
        PrefCommand command = prepareCommand(key, "");
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UniqueMeetingList(),
                new UserPrefs());
        assertCommandSuccess(command, model, expectedOutput, expectedModel);
    }

    @Test
    public void execute_validKey_updateValue() {
        String key = "AddressBookName";
        String newValue = "NewName";
        PrefCommand command = prepareCommand(key, newValue);
        String expectedOutput = String.format(PrefCommand.MESSAGE_EDIT_PREF_SUCCESS, key,
                model.getUserPrefs().getAddressBookName(), newValue);

        UserPrefs prefs = new UserPrefs();
        prefs.setAddressBookName(newValue);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UniqueMeetingList(), prefs);
        assertCommandSuccess(command, model, expectedOutput, expectedModel);
    }

    /**
     * Returns an {@code PrefCommand}
     */
    private PrefCommand prepareCommand(String prefKey, String newPrefValue) {
        PrefCommand prefCommand = new PrefCommand(prefKey, newPrefValue);
        prefCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return prefCommand;
    }

}
