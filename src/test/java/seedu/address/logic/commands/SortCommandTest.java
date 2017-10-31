package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.person.SortCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
//@@author Alim95

public class SortCommandTest {

    private static final String TO_SORT_NAME = "name";
    private static final String TO_SORT_PHONE = "phone";
    private static final String TO_SORT_EMAIL = "email";
    private static final String TO_SORT_ADDRESS = "address";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executeSortByNameSuccess() {
        SortCommand sortCommand = prepareCommand(TO_SORT_NAME);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, TO_SORT_NAME);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.sortList(TO_SORT_NAME);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeSortByPhoneSuccess() {
        SortCommand sortCommand = prepareCommand(TO_SORT_PHONE);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, TO_SORT_PHONE);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.sortList(TO_SORT_PHONE);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeSortByEmailSuccess() {
        SortCommand sortCommand = prepareCommand(TO_SORT_EMAIL);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, TO_SORT_EMAIL);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.sortList(TO_SORT_EMAIL);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeSortByAddressSuccess() {
        SortCommand sortCommand = prepareCommand(TO_SORT_ADDRESS);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, TO_SORT_ADDRESS);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.sortList(TO_SORT_ADDRESS);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Generates a new {@code SortCommand} which upon execution, sorts the AddressBook accordingly.
     */
    private SortCommand prepareCommand(String toSort) {
        SortCommand command = new SortCommand(toSort);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
