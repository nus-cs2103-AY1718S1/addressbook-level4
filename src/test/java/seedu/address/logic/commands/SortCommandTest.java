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
        assertSortCommandSuccess(TO_SORT_NAME, model);
    }

    @Test
    public void executeSortByPhoneSuccess() {
        assertSortCommandSuccess(TO_SORT_PHONE, model);
    }

    @Test
    public void executeSortByEmailSuccess() {
        assertSortCommandSuccess(TO_SORT_EMAIL, model);
    }

    @Test
    public void executeSortByAddressSuccess() {
        assertSortCommandSuccess(TO_SORT_ADDRESS, model);
    }

    /**
     * Executes a {@code SortCommand} with the given {@code toSortBy}, and checks that expected message and model
     * is correct.
     */
    private void assertSortCommandSuccess(String toSortBy, Model model) {
        SortCommand sortCommand = prepareCommand(toSortBy);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, toSortBy);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.sortList(toSortBy);

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
