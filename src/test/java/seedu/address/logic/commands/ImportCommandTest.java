package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Ignore;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.Storage;
import seedu.address.testutil.TypicalStorage;

//@@author LimeFallacie

/**
 * Contains integration tests (interaction with the Model) and unit tests for ImportCommand.
 */
public class ImportCommandTest {
    private Model model;
    private ImportCommand importCommand;
    private Storage storage;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        storage = new TypicalStorage().setUp();
    }

    /**
     * test if corrupted file will fail import
     */
    @Ignore
    public void execute_importCorruptedFile_fails() {
        importCommand = new ImportCommand("src\\test\\resources\\data\\testCorruptedFile.xml");
        importCommand.setData(model, new CommandHistory(), new UndoRedoStack(), storage);
        assertCommandFailure(importCommand, model, ImportCommand.MESSAGE_WRITE_ERROR);
    }
}

