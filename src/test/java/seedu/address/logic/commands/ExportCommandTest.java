//@@author edwinghy
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPath.PATH_CONTACT;
import static seedu.address.testutil.TypicalPath.PATH_EXPORT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRange.RANGE_1;
import static seedu.address.testutil.TypicalRange.RANGE_ALL;
import static seedu.address.testutil.TypicalReminders.getUniqueTypicalReminders;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ExportCommandTest {

    public static final String VALID_PATH = "/storage/classmates";
    private Model model = new ModelManager(getTypicalAddressBook(), getUniqueTypicalReminders(), new UserPrefs());

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ExportCommand exportCommand = prepareCommand(Integer.toString(outOfBoundIndex.getOneBased()), VALID_PATH);

        assertCommandFailure(exportCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still within bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ExportCommand exportCommand = prepareCommand(Integer.toString(outOfBoundIndex.getOneBased()), VALID_PATH);

        assertCommandFailure(exportCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final ExportCommand standardCommand = new ExportCommand(RANGE_ALL, PATH_EXPORT);

        // same values -> returns true
        ExportCommand commandWithSameValues = new ExportCommand(RANGE_ALL, PATH_EXPORT);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different range -> returns false
        assertFalse(standardCommand.equals(new ExportCommand(RANGE_1, PATH_EXPORT)));

        // different remarks -> returns false
        assertFalse(standardCommand.equals(new ExportCommand(RANGE_ALL, PATH_CONTACT)));
    }

    private ExportCommand prepareCommand(String range, String path) {
        ExportCommand exportCommand = new ExportCommand(range, path);
        exportCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return exportCommand;
    }
}
//@@author
