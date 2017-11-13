package systemtests;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_SUCCESS;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.ImportAnalysis;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.RecycleBin;
import seedu.address.storage.AddressBookData;


public class ImportCommandSystemTest extends AddressBookSystemTest {

    //@@author stan789
    @Test
    public void importing() {

        Model expectedModel = getModel();
        Model model = getModel();

        /* Case: export VCard file and import the file back to OneBook -> import successful */
        String command = ImportCommand.COMMAND_WORD + " src/test/data/VCardFileTest/OneBook.vcf";
        executeCommand(ExportCommand.COMMAND_WORD + " src/test/data/VCardFileTest/OneBook.vcf");
        executeCommand(ClearCommand.COMMAND_WORD);
        model.resetData(new AddressBookData(model.getAddressBook(), new RecycleBin()));
        expectedModel.resetData(new AddressBookData(new AddressBook(), new RecycleBin()));
        ImportAnalysis importAnalysis = new ImportAnalysis();
        try {
            expectedModel.importFile(Paths.get("src/test/data/VCardFileTest/OneBook.vcf"), importAnalysis);
        } catch (IOException e) {
            throw new IllegalArgumentException("Execution of command should not fail.", e);
        }
        assertCommandSuccess(command, importAnalysis.getNumContacts(), expectedModel);
        assertEquals(model, expectedModel);

        /* Case: import VCard file with valid format -> import successful */
        command = ImportCommand.COMMAND_WORD + " src/test/data/VCardFileTest/contacts.vcf";
        importAnalysis = new ImportAnalysis();
        try {
            expectedModel.importFile(Paths.get("src/test/data/VCardFileTest/contacts.vcf"), importAnalysis);
        } catch (IOException e) {
            throw new IllegalArgumentException("Execution of command should not fail.", e);
        }
        assertCommandSuccess(command, importAnalysis.getNumContacts(), expectedModel);

        /* Case: import VCard file with valid format to empty address book -> import successful */
        executeCommand(ClearCommand.COMMAND_WORD);
        expectedModel.resetData(new AddressBookData(new AddressBook(), new RecycleBin()));
        command = ImportCommand.COMMAND_WORD + " src/test/data/VCardFileTest/contacts.vcf";
        importAnalysis = new ImportAnalysis();
        try {
            expectedModel.importFile(Paths.get("src/test/data/VCardFileTest/contacts.vcf"), importAnalysis);
        } catch (IOException e) {
            throw new IllegalArgumentException("Execution of command should not fail.", e);
        }
        assertCommandSuccess(command, importAnalysis.getNumContacts(), expectedModel);

        /* Case: import VCard file with name only -> import successful */
        executeCommand(ClearCommand.COMMAND_WORD);
        expectedModel.resetData(new AddressBookData());
        command = ImportCommand.COMMAND_WORD + " src/test/data/VCardFileTest/name_only.vcf";
        importAnalysis = new ImportAnalysis();
        try {
            expectedModel.importFile(Paths.get("src/test/data/VCardFileTest/name_only.vcf"), importAnalysis);
        } catch (IOException e) {
            throw new IllegalArgumentException("Execution of command should not fail.", e);
        }
        assertCommandSuccess(command, importAnalysis.getNumContacts(), expectedModel);
    }

    //@@author stan789
    @After
    public void tearDown() {
        File file = new File("src/test/data/VCardFileTest/OneBook.vcf");
        file.delete();
    }

    //@@author stan789-reused
    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code String.format(MESSAGE_SUCCESS, count)},
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Integer count, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", String.format(MESSAGE_SUCCESS, count), expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }
}
