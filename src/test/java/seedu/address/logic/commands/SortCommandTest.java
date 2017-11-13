package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getEmptyAddressBook;
import static seedu.address.testutil.TypicalPersons.getSortedAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author cjianhui
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class SortCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model emptyModel = new ModelManager(getEmptyAddressBook(), new UserPrefs());
    private Model modelSortedByNameInReverse = new ModelManager(getSortedAddressBook("name", true), new UserPrefs());
    private Model modelSortedByPhone = new ModelManager(getSortedAddressBook("phone", false), new UserPrefs());
    private Model modelSortedByPhoneInReverse = new ModelManager(getSortedAddressBook("phone", true), new UserPrefs());
    private Model modelSortedByEmail = new ModelManager(getSortedAddressBook("email", false), new UserPrefs());
    private Model modelSortedByEmailInReverse = new ModelManager(getSortedAddressBook("email", true), new UserPrefs());
    private Model modelSortedByAddress = new ModelManager(
            getSortedAddressBook("address", false), new UserPrefs());
    private Model modelSortedByAddressInReverse = new ModelManager(
            getSortedAddressBook("address", true), new UserPrefs());

    @Test
    public void constructor_nullSortOrder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SortCommand(CliSyntax.PREFIX_NAME.getPrefix(), null);
    }

    @Test
    public void constructor_nullSortField_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SortCommand(null, true);
    }

    @Test
    public void execute_catchNoPersonsException() throws CommandException {
        thrown.expect(CommandException.class);
        prepareCommand("n/", false, emptyModel).execute();
    }

    @Test
    public void execute_sortByName_success() throws Exception {
        SortCommand sortCommand = prepareCommand("n/", false, model);
        String expectedMessage = String.format(SortCommand.MESSAGE_SORT_PERSON_SUCCESS, "name", "ascending");
        assertCommandSuccess(sortCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_sortByNameInReverseOrder_success() throws Exception {
        SortCommand sortCommand = prepareCommand("n/", true, model);
        String expectedMessage = String.format(SortCommand.MESSAGE_SORT_PERSON_SUCCESS, "name", "descending");
        assertCommandSuccess(sortCommand, model, expectedMessage, modelSortedByNameInReverse);
    }

    @Test
    public void execute_sortByPhone_success() throws Exception {
        SortCommand sortCommand = prepareCommand("p/", false, model);
        String expectedMessage = String.format(SortCommand.MESSAGE_SORT_PERSON_SUCCESS, "phone", "ascending");
        assertCommandSuccess(sortCommand, model, expectedMessage, modelSortedByPhone);
    }

    @Test
    public void execute_sortByPhoneInReverseOrder_success() throws Exception {
        SortCommand sortCommand = prepareCommand("p/", true, model);
        String expectedMessage = String.format(SortCommand.MESSAGE_SORT_PERSON_SUCCESS, "phone", "descending");
        assertCommandSuccess(sortCommand, model, expectedMessage, modelSortedByPhoneInReverse);
    }

    @Test
    public void execute_sortByEmail_success() throws Exception {
        SortCommand sortCommand = prepareCommand("e/", false, model);
        String expectedMessage = String.format(SortCommand.MESSAGE_SORT_PERSON_SUCCESS, "email", "ascending");
        assertCommandSuccess(sortCommand, model, expectedMessage, modelSortedByEmail);
    }

    @Test
    public void execute_sortByEmailInReverseOrder_success() throws Exception {
        SortCommand sortCommand = prepareCommand("e/", true, model);
        String expectedMessage = String.format(SortCommand.MESSAGE_SORT_PERSON_SUCCESS, "email", "descending");
        assertCommandSuccess(sortCommand, model, expectedMessage, modelSortedByEmailInReverse);
    }

    @Test
    public void execute_sortByAddress_success() throws Exception {
        SortCommand sortCommand = prepareCommand("a/", false, model);
        String expectedMessage = String.format(SortCommand.MESSAGE_SORT_PERSON_SUCCESS, "address", "ascending");
        assertCommandSuccess(sortCommand, model, expectedMessage, modelSortedByAddress);
    }

    @Test
    public void execute_sortByAddressInReverseOrder_success() throws Exception {
        SortCommand sortCommand = prepareCommand("a/", true, model);
        String expectedMessage = String.format(SortCommand.MESSAGE_SORT_PERSON_SUCCESS, "address", "descending");
        assertCommandSuccess(sortCommand, model, expectedMessage, modelSortedByAddressInReverse);
    }

    /**
     * Returns a {@code sortCommand} with the parameters {@code field and @code isReverseOrder}.
     */
    private SortCommand prepareCommand(String field, boolean isReverseOrder, Model model) {
        SortCommand sortCommand = new SortCommand(field, isReverseOrder);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }


}
