package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
//import seedu.address.model.person.ReadOnlyPerson;

//@@author icehawker

/**
 * Contains integration tests (interaction with the Model) for {@code BackupCommand}.
 */

public class CopyCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        ArrayList<Index> indices = new ArrayList<>();
        indices.add(INDEX_FIRST_PERSON);
        CopyCommand copyOne = new CopyCommand(indices);
        ArrayList<Index> indicesBob = new ArrayList<>();
        indicesBob.add(INDEX_SECOND_PERSON);
        CopyCommand copyBob = new CopyCommand(indicesBob);

        // same values -> returns true
        CopyCommand copyOneCopy = new CopyCommand(indices);
        assertTrue(copyOneCopy.equals(copyOne));

        // different values -> returns false
        assertFalse(copyBob.equals(copyOne));

        // different types -> returns false
        assertFalse(copyOne.equals(1));

        // null -> returns false
        assertFalse(copyOne.equals(null));
    }
    /* Test passes in Gradle but not Travis
    @Test
    public void execute_copy_success() throws Exception {
        ReadOnlyPerson personToCopy = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        CopyCommand copyCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(CopyCommand.MESSAGE_COPY_PERSON_SUCCESS, personToCopy.getEmails());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(copyCommand, model, expectedMessage, expectedModel);
    }
    */
    /**
     * Returns a {@code EmailCommand} with the parameter {@code index}.
     */
    private CopyCommand prepareCommand(Index index) {
        ArrayList<Index> indices = new ArrayList<>();
        indices.add(index);

        UserPrefs prefs = new UserPrefs();
        CopyCommand copyCommand = new CopyCommand(indices);
        copyCommand.setData(model, prefs, new CommandHistory(), new UndoRedoStack());
        return copyCommand;
    }
}
