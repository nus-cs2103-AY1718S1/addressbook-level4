package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author freesoup
public class ExportCommandTest {
    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void equals() {
        String filepath1 = "/exports/test1/";
        String filepath2 = "/exports/test2/";

        ExportCommand test1Export = new ExportCommand(filepath1);
        ExportCommand test2Export = new ExportCommand(filepath2);

        //same Object -> return true
        assertTrue(test1Export.equals(test1Export));

        //same attributes -> return true
        ExportCommand test1ExportCopy = new ExportCommand(filepath1);
        assertTrue(test1Export.equals(test1ExportCopy));

        //Different object -> return false
        assertFalse(test1Export.equals(false));

        //null -> return false
        assertFalse(test1Export.equals(null));

        //Different attributes -> return false
        assertFalse(test1Export.equals(test2Export));
    }

    @Test
    public void export_emptyAddressBook_emptyBookError() throws PersonNotFoundException {
        Model emptyAddressBookModel = new ModelManager();
        assertCommandFailure(prepareCommand(
                "out.xml", emptyAddressBookModel), emptyAddressBookModel, ExportCommand.MESSAGE_EMPTY_BOOK);
    }

    @Test
    public void export_validAddressBook_success() {
        //Valid .xml export.
        assertCommandSuccess(prepareCommand("out.xml", model), model, ExportCommand.MESSAGE_SUCCESS, model);

        //Valid .vcf export
        assertCommandSuccess(prepareCommand("out.vcf", model), model, ExportCommand.MESSAGE_SUCCESS, model);
    }

    @After
    public void cleanUp() {
        //Deletes the files that were created.
        File xmlfile = new File("out.xml");
        File vcffile = new File("out.vcf");

        if (xmlfile.exists()) {
            xmlfile.delete();
        }

        if (vcffile.exists()) {
            vcffile.delete();
        }
    }

    /**
     * Generates a new {@code ExportCommand} which upon execution, exports the contacts in {@code model}.
     */
    private ExportCommand prepareCommand(String filePath, Model model) {
        ExportCommand command = new ExportCommand(filePath);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
