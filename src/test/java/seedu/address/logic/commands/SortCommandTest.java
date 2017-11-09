package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
//@@author mzxc152

public class SortCommandTest {
    private static final String NAME_SORT = "name";
    private static final String EMAIL_SORT = "email";
    private static final String PHONE_SORT = "phone";
    private static final String ADDRESS_SORT = "address";
    private static final String TAG_SORT = "tag";

    private Model model;
    private Model expectedModel;


    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void executeNameSortSuccess() {
        SortCommand sortCommand = prepareCommand(NAME_SORT);
        expectedModel.sortList(NAME_SORT);
        assertCommandSuccess(sortCommand, model, sortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeEmailSortSuccess() {
        SortCommand sortCommand = prepareCommand(EMAIL_SORT);
        expectedModel.sortList(EMAIL_SORT);
        assertCommandSuccess(sortCommand, model, sortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executePhoneSortSuccess() {
        SortCommand sortCommand = prepareCommand(PHONE_SORT);
        expectedModel.sortList(PHONE_SORT);
        assertCommandSuccess(sortCommand, model, sortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeAddressSortSuccess() {
        SortCommand sortCommand = prepareCommand(ADDRESS_SORT);
        expectedModel.sortList(ADDRESS_SORT);
        assertCommandSuccess(sortCommand, model, sortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeTagSortSuccess() {
        SortCommand sortCommand = prepareCommand(TAG_SORT);
        expectedModel.sortList(TAG_SORT);
        assertCommandSuccess(sortCommand, model, sortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     *  Parses {@code toSort} into a {@code SortCommand}.
     *  This sorts Contags according to the type of attribute parsed into SortCommand.
     */
    private SortCommand prepareCommand(String toSort) {
        SortCommand command = new SortCommand(toSort);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
