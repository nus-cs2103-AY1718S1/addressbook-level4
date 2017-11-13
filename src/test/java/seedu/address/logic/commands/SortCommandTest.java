package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.SortCommand.SORT_EMAIL;
import static seedu.address.logic.commands.SortCommand.SORT_NAME;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalRecycleBin;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RecycleBin;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.exceptions.EmptyAddressBookException;
import seedu.address.storage.AddressBookData;

public class SortCommandTest {

    private Model model;
    private Model expectedModel;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalRecycleBin(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalRecycleBin(), new UserPrefs());
    }

    //@@author stan789
    @Test
    public void execute_sortByName_sortSuccess() throws EmptyAddressBookException {
        SortCommand sortCommand = prepareCommand(SORT_NAME);

        String expectedMessage = SortCommand.MESSAGE_SUCCESS;
        expectedModel.executeSort(SORT_NAME);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    //@@author stan789
    @Test
    public void execute_sortByName_sortSuccessBinMode() throws EmptyAddressBookException {
        SortCommand sortCommand = prepareCommand_binMode(SORT_NAME);

        String expectedMessage = SortCommand.MESSAGE_SUCCESS;
        expectedModel.setBinDisplay();
        expectedModel.executeBinSort(SORT_NAME);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    //@@author stan789
    @Test
    public void execute_sortByEmail_sortSuccess() throws EmptyAddressBookException {
        SortCommand sortCommand = prepareCommand(SORT_EMAIL);

        String expectedMessage = SortCommand.MESSAGE_SUCCESS;
        expectedModel.executeSort(SORT_EMAIL);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    //@@author stan789
    @Test
    public void execute_sortByEmail_sortSuccessBinMode() throws EmptyAddressBookException {
        SortCommand sortCommand = prepareCommand_binMode(SORT_EMAIL);

        String expectedMessage = SortCommand.MESSAGE_SUCCESS;
        expectedModel.setBinDisplay();
        expectedModel.executeBinSort(SORT_EMAIL);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    //@@author stan789
    @Test(expected = EmptyAddressBookException.class)
    public void testEmptyAddressBookException() throws EmptyAddressBookException {
        model.resetData(new AddressBookData(new AddressBook(), new RecycleBin()));
        model.executeSort(SORT_NAME);

    }

    //@@author stan789-reused
    /**
     * Generates a new {@code SortCommand} which upon execution, sorts the AddressBook.
     */
    private SortCommand prepareCommand(String sortType) {
        SortCommand command = new SortCommand(sortType);
        model.setListDisplay();
        command.setData(model, new CommandHistory(), new UndoRedoStack(), false);
        return command;
    }

    /**
     * Generates a new {@code SortCommand} which upon execution, sorts the AddressBook, used for when bin is displayed
     */
    private SortCommand prepareCommand_binMode(String sortType) {
        SortCommand command = new SortCommand(sortType);
        model.setBinDisplay();
        command.setData(model, new CommandHistory(), new UndoRedoStack(), true);
        return command;
    }
}
