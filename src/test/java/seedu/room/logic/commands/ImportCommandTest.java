package seedu.room.logic.commands;

import static org.junit.Assert.assertNotNull;
import static seedu.room.logic.commands.CommandTestUtil.INVALID_FILE;
import static seedu.room.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.room.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.room.testutil.TypicalImportFile.TYPICAL_IMPORT_SUCCESS_MESSAGE;
import static seedu.room.testutil.TypicalImportFile.getTypicalImportFile;
import static seedu.room.testutil.TypicalPersons.getTypicalResidentBook;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.room.logic.CommandHistory;
import seedu.room.logic.UndoRedoStack;
import seedu.room.model.Model;
import seedu.room.model.ModelManager;
import seedu.room.model.ResidentBook;
import seedu.room.model.UserPrefs;
import seedu.room.storage.JsonUserPrefsStorage;
import seedu.room.storage.StorageManager;
import seedu.room.storage.XmlEventBookStorage;
import seedu.room.storage.XmlResidentBookStorage;
import seedu.room.testutil.TypicalImportFile;
import seedu.room.ui.testutil.EventsCollectorRule;

//@@author blackroxs

/**
 * Contains integration tests (interaction with the Model) and unit tests for ImportCommandTest.
 */
public class ImportCommandTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalResidentBook(), new UserPrefs());
    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlResidentBookStorage residentBookStorage = new XmlResidentBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        XmlEventBookStorage eventBookStorage = new XmlEventBookStorage(getTempFilePath("bc"));
        storageManager = new StorageManager(residentBookStorage, eventBookStorage, userPrefsStorage);

        try {
            storageManager.backupResidentBook(getTypicalImportFile());
        } catch (IOException e) {
            throw new AssertionError("Execution of command should not fail.", e);
        }
    }

    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }

    @Test
    public void fileNotFound() {
        ImportCommand command = prepareCommand(INVALID_FILE);
        assertCommandFailure(command, model, ImportCommand.MESSAGE_ERROR);
    }

    @Test
    public void validImport() {
        ClassLoader classLoader = getClass().getClassLoader();
        File backup = new File(classLoader.getResource("backup.xml").getFile());
        assertNotNull(backup);

        ImportCommand command = prepareCommand(backup.getAbsolutePath());
        ResidentBook correctVersion = TypicalImportFile.getCombinedResult();

        ModelManager expectedModel = new ModelManager(correctVersion, new UserPrefs());

        assertCommandSuccess(command, model, TYPICAL_IMPORT_SUCCESS_MESSAGE, expectedModel);
    }

    /**
     * Returns an {@code ImportCommand} with parameters {@code filePath}
     */
    private ImportCommand prepareCommand(String filePath) {
        ImportCommand command = new ImportCommand(filePath);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
