package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_WRITE_ERROR;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RecycleBin;
import seedu.address.model.UserPrefs;

public class ExportCommandTest {

    private Model model;
    private Model expectedModel;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new RecycleBin(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new RecycleBin(), new UserPrefs());
    }

    //@@author stan789
    @Test
    public void execute_exportVCardFile_importSuccess() throws IOException {

        String fileLocation = "src/test/data/VCardFileTest/OneBook.vcf";
        String extension = "vcf";
        String fileName = "OneBook";

        ExportCommand exportCommand = prepareCommand(fileLocation, fileName, extension);
        expectedModel.exportFile(fileLocation, extension);
        String expectedMessage = String.format(ExportCommand.MESSAGE_SUCCESS, fileName);
        assertCommandSuccess(exportCommand, model, expectedMessage, expectedModel);
    }

    //@@author stan789
    @Test
    public void execute_exportCsvFile_importSuccess() throws IOException {

        String fileLocation = "src/test/data/VCardFileTest/OneBook.csv";
        String extension = "csv";
        String fileName = "OneBook";

        ExportCommand exportCommand = prepareCommand(fileLocation, fileName, extension);
        expectedModel.exportFile(fileLocation, extension);
        String expectedMessage = String.format(ExportCommand.MESSAGE_SUCCESS, fileName);
        assertCommandSuccess(exportCommand, model, expectedMessage, expectedModel);
    }

    //@@author stan789
    @After
    public void tearDown() {
        File fileVcard = new File("src/test/data/VCardFileTest/OneBook.vcf");
        fileVcard.delete();
        File fileCsv = new File("src/test/data/VCardFileTest/OneBook.csv");
        fileCsv.delete();
    }

    //@@author stan789
    @Test
    public void execute_exportEmptyString_exportFailure() throws IOException {

        String fileLocation = "";
        String extension = "";
        String fileName = "";

        ExportCommand exportCommand = prepareCommand(fileLocation, fileName, extension);
        assertCommandFailure(exportCommand, model, MESSAGE_WRITE_ERROR);
    }

    //@@author stan789
    @Test(expected = IOException.class)
    public void emptyFileDirectoryVcardIoException() throws IOException {
        model.exportFile("", "vcf");

    }

    //@@author stan789
    @Test(expected = IOException.class)
    public void emptyFileDirectoryCsvIoException() throws IOException {
        model.exportFile("", "csv");

    }

    //@@author stan789-reused
    /**
     * Generates a new {@code ExportCommand} which upon execution, sorts the AddressBook.
     */
    private ExportCommand prepareCommand(String fileLocation, String fileName, String extension) {
        ExportCommand command = new ExportCommand(fileLocation, fileName, extension);
        command.setData(model, new CommandHistory(), new UndoRedoStack(), false);
        return command;
    }
}
