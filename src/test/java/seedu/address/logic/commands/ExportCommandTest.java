package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_EXPORT_SUCCESS;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.XmlAddressBookStorage;

//@@author k-l-a
public class ExportCommandTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/ImportExportCommandTest/");
    private static final String VALID_ADDRESSBOOK_PATH = TEST_DATA_FOLDER + "validAddressBook.xml";
    private static final String TEMP_ADDRESSBOOK_NAME = "tempAddressBook.xml";
    private static final File TEMP_FILE = new File(TEST_DATA_FOLDER + TEMP_ADDRESSBOOK_NAME);

    private Model model;

    @Before
    public void setUp() {
        try {
            model = new ModelManager(new XmlAddressBookStorage(VALID_ADDRESSBOOK_PATH)
                    .readAddressBook().get(), new UserPrefs());
        } catch (Exception e) {
            fail("Failed to set up test, unable to read data");
        }
    }

    @Test
    public void equals() {
        String validFileName = "backup.xml";
        final ExportCommand typicalCommand = new ExportCommand(validFileName);

        //same object -> true
        assertTrue(typicalCommand.equals(typicalCommand));

        //same values-> true
        assertTrue(typicalCommand.equals(new ExportCommand(ExportCommand.DEFAULT_EXPORT_FILEPATH, validFileName)));

        //null -> false
        assertFalse(typicalCommand.equals(null));

        //different type -> false
        assertFalse(typicalCommand.equals(new ClearCommand()));

        //different filename -> false
        assertFalse(typicalCommand.equals(new ExportCommand("differentName")));

        //different export path -> false
        assertFalse(typicalCommand.equals(new ExportCommand("different/path/", validFileName)));

    }


    @Test
    public void execute_validName_success() throws Exception {
        TEMP_FILE.createNewFile();
        String exportFilePath = TEST_DATA_FOLDER + TEMP_ADDRESSBOOK_NAME;
        ExportCommand command = prepareCommand(TEMP_ADDRESSBOOK_NAME);
        String expectedMessage = String.format(MESSAGE_EXPORT_SUCCESS, exportFilePath);
        assertCommandSuccess(command, model, expectedMessage, model);

        //check if exported file is equal to current model
        Model expectedExportedModel = new ModelManager(new XmlAddressBookStorage(exportFilePath)
                .readAddressBook().get(), new UserPrefs());
        assertTrue(model.equals(expectedExportedModel));
    }

    /**
     * Prepares an ExportCommand for testing based on given filename.
     */
    public ExportCommand prepareCommand(String filename) {
        ExportCommand exportCommand = new ExportCommand(TEST_DATA_FOLDER, filename);
        exportCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return exportCommand;
    }
}
