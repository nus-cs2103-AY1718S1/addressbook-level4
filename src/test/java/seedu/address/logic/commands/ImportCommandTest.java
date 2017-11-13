package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RecycleBin;
import seedu.address.model.UserPrefs;

public class ImportCommandTest {
    private Model model;
    private Model expectedModel;
    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new RecycleBin(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new RecycleBin(), new UserPrefs());
    }

    //@@author stan789
    @Test
    public void execute_importVCardFile_importSuccess() throws IOException {

        Path fileLocation = Paths.get("src/test/data/VCardFileTest/contacts.vcf");
        ImportCommand importCommand = prepareCommand(fileLocation);
        ImportAnalysis importAnalysis = new ImportAnalysis();
        expectedModel.importFile(fileLocation, importAnalysis);
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, importAnalysis.getNumContacts());
        assertCommandSuccess(importCommand, model, expectedMessage, expectedModel);
    }

    //@@author stan789
    @Test
    public void execute_importVCardFileWithOneTag_importSuccess() throws IOException {

        Path fileLocation = Paths.get("src/test/data/VCardFileTest/contacts_with_one_tag.vcf");

        ImportCommand importCommand = prepareCommand(fileLocation);
        ImportAnalysis importAnalysis = new ImportAnalysis();
        expectedModel.importFile(fileLocation, importAnalysis);
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, importAnalysis.getNumContacts());
        assertCommandSuccess(importCommand, model, expectedMessage, expectedModel);
    }

    //@@author stan789
    @Test
    public void execute_importVCardFileWithoutFullName_importSuccess() throws IOException {

        Path fileLocation = Paths.get("src/test/data/VCardFileTest/contacts_with_empty_fullName.vcf");

        ImportCommand importCommand = prepareCommand(fileLocation);
        ImportAnalysis importAnalysis = new ImportAnalysis();
        expectedModel.importFile(fileLocation, importAnalysis);
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, importAnalysis.getNumContacts());
        assertCommandSuccess(importCommand, model, expectedMessage, expectedModel);
    }

    //@@author stan789
    @Test
    public void execute_importVCardFileWithDuplicate_importSuccessWithWarning() throws IOException {

        Path fileLocation = Paths.get("src/test/data/VCardFileTest/contacts_with_duplicate.vcf");

        ImportCommand importCommand = prepareCommand(fileLocation);
        ImportAnalysis importAnalysis = new ImportAnalysis();
        expectedModel.importFile(fileLocation, importAnalysis);
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, importAnalysis.getNumContacts())
                + ImportCommand.MESSAGE_DUPLICATE;
        assertCommandSuccess(importCommand, model, expectedMessage, expectedModel);
    }

    //@@author stan789
    @Test
    public void execute_importVCardFileWithInfoInvalidFormat_importSuccessWithWarning() throws IOException {

        Path fileLocation = Paths.get("src/test/data/VCardFileTest/contacts_with_illegal_value.vcf");

        ImportCommand importCommand = prepareCommand(fileLocation);
        ImportAnalysis importAnalysis = new ImportAnalysis();
        expectedModel.importFile(fileLocation, importAnalysis);
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, importAnalysis.getNumContacts())
                + ImportCommand.MESSAGE_ILLEGAL_VALUE;
        assertCommandSuccess(importCommand, model, expectedMessage, expectedModel);
    }

    //@@author stan789
    @Test
    public void execute_importVCardFileWithInfoInvalidFormatAndDuplicate_importSuccessWithWarning() throws IOException {

        Path fileLocation = Paths.get("src/test/data/VCardFileTest/contacts_duplicate_and_illegal_value.vcf");

        ImportCommand importCommand = prepareCommand(fileLocation);
        ImportAnalysis importAnalysis = new ImportAnalysis();
        expectedModel.importFile(fileLocation, importAnalysis);
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, importAnalysis.getNumContacts())
                + ImportCommand.MESSAGE_DUPLICATE + ImportCommand.MESSAGE_ILLEGAL_VALUE;
        assertCommandSuccess(importCommand, model, expectedMessage, expectedModel);
    }

    //@@author stan789
    @Test(expected = IOException.class)
    public void testFileWithInvalidFormat() throws IOException {
        ImportAnalysis importAnalysis = new ImportAnalysis();
        model.importFile(Paths.get("src/test/data/VCardFileTest/contacts_example.vcf"), importAnalysis);
    }

    //@@author stan789
    @Test
    public void execute_importEmptyVCardFile_importFailure() throws IOException {

        Path fileLocation = Paths.get("src/test/data/VCardFileTest/empty.vcf");
        ImportCommand importCommand = prepareCommand(fileLocation);
        String expectedMessage = ImportCommand.MESSAGE_EMPTY_FILE;
        assertCommandFailure(importCommand, model, expectedMessage);
    }

    //@@author stan789
    @Test
    public void execute_importInvalidVCardFileNoBeginCard_importFailure() throws IOException {

        Path fileLocation = Paths.get("src/test/data/VCardFileTest/contacts_without_begin.vcf");

        ImportCommand importCommand = prepareCommand(fileLocation);
        String expectedMessage = ImportCommand.MESSAGE_FILE_INVALID;
        assertCommandFailure(importCommand, model, expectedMessage);
    }

    //@@author stan789-reused
    /**
     * Generates a new {@code ImportCommand} which upon execution, sorts the AddressBook.
     */
    private ImportCommand prepareCommand(Path fileLocation) {
        ImportCommand command = new ImportCommand(fileLocation);
        command.setData(model, new CommandHistory(), new UndoRedoStack(), false);
        return command;
    }
}
