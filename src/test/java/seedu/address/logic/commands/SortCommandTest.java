package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getSortedAddressAddressBook;
import static seedu.address.testutil.TypicalPersons.getSortedEmailAddressBook;
import static seedu.address.testutil.TypicalPersons.getSortedNumAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author justintkj
/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
public class SortCommandTest {

    public static final String SORT_NAME_ARG = "name";
    public static final String SORT_EMAIL_ARG = "email";
    public static final String SORT_ADDRESS_ARG = "address";
    public static final String SORT_NUM_ARG = "number";
    public static final String SORT_NUM_ARG_CAMELCASE = "NuMBeR";
    private Model model;
    private Model expectedModel;
    private SortCommand sortCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }
    @Test
    public void execute_showsSameList() {
        //Sort name -> command parsed successful
        assertSuccessSortWithParameter(SORT_NAME_ARG, model, sortCommand, SortCommand.MESSAGE_SORT_SUCCESS
                + SORT_NAME_ARG, expectedModel);

        //Sort email -> command parsed successful
        expectedModel = new ModelManager(getSortedEmailAddressBook(), new UserPrefs());
        assertSuccessSortWithParameter(SORT_EMAIL_ARG, model, sortCommand,
                SortCommand.MESSAGE_SORT_SUCCESS + SORT_EMAIL_ARG, expectedModel);

        //Sort address -> command parsed successful
        expectedModel = new ModelManager(getSortedAddressAddressBook(), new UserPrefs());
        assertSuccessSortWithParameter(SORT_ADDRESS_ARG, model, sortCommand,
                SortCommand.MESSAGE_SORT_SUCCESS + SORT_ADDRESS_ARG, expectedModel);

        //Sort number -> command parsed successful
        expectedModel = new ModelManager(getSortedNumAddressBook(), new UserPrefs());
        assertSuccessSortWithParameter(SORT_NUM_ARG, model, sortCommand,
                SortCommand.MESSAGE_SORT_SUCCESS + SORT_NUM_ARG, expectedModel);

        //Sort number CamelCase -> command parsed successful
        expectedModel = new ModelManager(getSortedNumAddressBook(), new UserPrefs());
        assertSuccessSortWithParameter(SORT_NUM_ARG, model, sortCommand,
                SortCommand.MESSAGE_SORT_SUCCESS + SORT_NUM_ARG, expectedModel);

    }

    /**
     * Validates the model addressbook is the same as expected when sort with valid parameter is given
     *
     * @param sortParameterArg the type of sort
     * @param model sample addressbook model
     * @param sortCommand SortCommand object
     * @param expectedMessage Expected message to produce after sort
     * @param expectedModel Expected addressbook after the sort
     */
    private void assertSuccessSortWithParameter(String sortParameterArg, Model model, SortCommand sortCommand,
                                                String expectedMessage, Model expectedModel) {
        sortCommand = new SortCommand(sortParameterArg);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }
}

