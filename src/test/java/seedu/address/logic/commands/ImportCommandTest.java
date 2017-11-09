package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

//@@author freesoup
public class ImportCommandTest {
    private Model model;
    private List<ReadOnlyPerson> list1;
    private List<ReadOnlyPerson> list2;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        list1 = model.getAddressBook().getPersonList();
        list2 = new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL));
    }

    @Test
    public void equals() {

        ImportCommand test1Import = new ImportCommand(list1);
        ImportCommand test2Import = new ImportCommand(list2);

        //same Object -> return true
        assertTrue(test1Import.equals(test1Import));

        //same attributes -> return true
        ImportCommand test1ImportCopy = new ImportCommand(list1);
        assertTrue(test1Import.equals(test1ImportCopy));

        //Different object -> return false
        assertFalse(test1Import.equals(false));

        //null -> return false
        assertFalse(test1Import.equals(null));

        //Different attributes -> return false
        assertFalse(test1Import.equals(test2Import));
    }

    @Test
    public void import_toEmptyAddressBook_success() throws DuplicatePersonException {

        Model testModel = new ModelManager(new AddressBook(), new UserPrefs());
        AddressBook testAddressBook = new AddressBook();
        testAddressBook.setPersons(list2);
        Model testModelAliceBensonCarl = new ModelManager(testAddressBook, new UserPrefs());

        //Import list of 3 people into empty addressbook
        assertCommandSuccess(prepareCommand(list2, testModel), testModel,
                String.format(ImportCommand.MESSAGE_SUCCESS, 0), testModelAliceBensonCarl);

        //Import list of 7 people into addressbook with 3 people inside.
        assertCommandSuccess(prepareCommand(list1, testModel), testModel,
                String.format(ImportCommand.MESSAGE_SUCCESS, 3), model);

    }

    /**
     * Generates a new {@code ExportCommand} which upon execution, exports the contacts in {@code model}.
     */
    private ImportCommand prepareCommand(List list, Model model) {
        ImportCommand command = new ImportCommand(list);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
