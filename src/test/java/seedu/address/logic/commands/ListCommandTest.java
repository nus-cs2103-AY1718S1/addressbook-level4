package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.logic.commands.CommandTestUtil.sortAllPersons;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_ADDRESS_ASCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_ADDRESS_DEFAULT;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_ADDRESS_DESCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_EMAIL_ASCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_EMAIL_DEFAULT;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_EMAIL_DESCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_NAME_ASCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_NAME_DEFAULT;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_NAME_DESCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_PHONE_ASCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_PHONE_DEFAULT;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_PHONE_DESCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_REMARK_ASCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_REMARK_DEFAULT;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_REMARK_DESCENDING;
import static seedu.address.testutil.TypicalPersons.getTypicalRolodex;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private ListCommand listCommand;
    private ListCommand listCommandNameDefault;
    private ListCommand listCommandNameDescending;
    private ListCommand listCommandNameAscending;
    private ListCommand listCommandPhoneDefault;
    private ListCommand listCommandPhoneDescending;
    private ListCommand listCommandPhoneAscending;
    private ListCommand listCommandEmailDefault;
    private ListCommand listCommandEmailDescending;
    private ListCommand listCommandEmailAscending;
    private ListCommand listCommandAddressDefault;
    private ListCommand listCommandAddressDescending;
    private ListCommand listCommandAddressAscending;
    private ListCommand listCommandRemarkDefault;
    private ListCommand listCommandRemarkDescending;
    private ListCommand listCommandRemarkAscending;


    @Before
    public void setUp() {
        model = new ModelManager(getTypicalRolodex(), new UserPrefs());
        expectedModel = new ModelManager(model.getRolodex(), new UserPrefs());

        listCommand = new ListCommand(new ArrayList<>());
        listCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        listCommandNameDefault = new ListCommand(Arrays.asList(SORT_ARGUMENT_NAME_DEFAULT));
        listCommandNameDefault.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommandNameDescending = new ListCommand(Arrays.asList(SORT_ARGUMENT_NAME_DESCENDING));
        listCommandNameDescending.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommandNameAscending = new ListCommand(Arrays.asList(SORT_ARGUMENT_NAME_ASCENDING));
        listCommandNameAscending.setData(model, new CommandHistory(), new UndoRedoStack());

        listCommandPhoneDefault = new ListCommand(Arrays.asList(SORT_ARGUMENT_PHONE_DEFAULT));
        listCommandPhoneDefault.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommandPhoneDescending = new ListCommand(Arrays.asList(SORT_ARGUMENT_PHONE_DESCENDING));
        listCommandPhoneDescending.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommandPhoneAscending = new ListCommand(Arrays.asList(SORT_ARGUMENT_PHONE_ASCENDING));
        listCommandPhoneAscending.setData(model, new CommandHistory(), new UndoRedoStack());

        listCommandEmailDefault = new ListCommand(Arrays.asList(SORT_ARGUMENT_EMAIL_DEFAULT));
        listCommandEmailDefault.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommandEmailDescending = new ListCommand(Arrays.asList(SORT_ARGUMENT_EMAIL_DESCENDING));
        listCommandEmailDescending.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommandEmailAscending = new ListCommand(Arrays.asList(SORT_ARGUMENT_EMAIL_ASCENDING));
        listCommandEmailAscending.setData(model, new CommandHistory(), new UndoRedoStack());

        listCommandAddressDefault = new ListCommand(Arrays.asList(SORT_ARGUMENT_ADDRESS_DEFAULT));
        listCommandAddressDefault.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommandAddressDescending = new ListCommand(Arrays.asList(SORT_ARGUMENT_ADDRESS_DESCENDING));
        listCommandAddressDescending.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommandAddressAscending = new ListCommand(Arrays.asList(SORT_ARGUMENT_ADDRESS_ASCENDING));
        listCommandAddressAscending.setData(model, new CommandHistory(), new UndoRedoStack());

        listCommandRemarkDefault = new ListCommand(Arrays.asList(SORT_ARGUMENT_REMARK_DEFAULT));
        listCommandRemarkDefault.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommandRemarkDescending = new ListCommand(Arrays.asList(SORT_ARGUMENT_REMARK_DESCENDING));
        listCommandRemarkDescending.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommandRemarkAscending = new ListCommand(Arrays.asList(SORT_ARGUMENT_REMARK_ASCENDING));
        listCommandRemarkAscending.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void executeListIsNotFilteredShowsSameList() {
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsFilteredShowsEverything() {
        showFirstPersonOnly(model);
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsSortedByNameDefaultShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_NAME_DEFAULT);
        assertCommandSuccess(listCommandNameDefault, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsSortedByNameDescendingShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_NAME_DESCENDING);
        assertCommandSuccess(listCommandNameDescending, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsSortedByNameAscendingShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_NAME_ASCENDING);
        assertCommandSuccess(listCommandNameAscending, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsSortedByPhoneDefaultShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_PHONE_DEFAULT);
        assertCommandSuccess(listCommandPhoneDefault, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsSortedByPhoneDescendingShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_PHONE_DESCENDING);
        assertCommandSuccess(listCommandPhoneDescending, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsSortedByPhoneAscendingShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_PHONE_ASCENDING);
        assertCommandSuccess(listCommandPhoneAscending, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsSortedByEmailDefaultShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_EMAIL_DEFAULT);
        assertCommandSuccess(listCommandEmailDefault, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsSortedByEmailDescendingShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_EMAIL_DESCENDING);
        assertCommandSuccess(listCommandEmailDescending, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsSortedByEmailAscendingShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_EMAIL_ASCENDING);
        assertCommandSuccess(listCommandEmailAscending, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsSortedByAddressDefaultShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_ADDRESS_DEFAULT);
        assertCommandSuccess(listCommandAddressDefault, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsSortedByAddressDescendingShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_ADDRESS_DESCENDING);
        assertCommandSuccess(listCommandAddressDescending, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsSortedByAddressAscendingShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_ADDRESS_ASCENDING);
        assertCommandSuccess(listCommandAddressAscending, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsSortedByRemarkDefaultShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_REMARK_DEFAULT);
        assertCommandSuccess(listCommandRemarkDefault, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsSortedByRemarkDescendingShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_REMARK_DESCENDING);
        assertCommandSuccess(listCommandRemarkDescending, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsSortedByRemarkAscendingShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_REMARK_ASCENDING);
        assertCommandSuccess(listCommandRemarkAscending, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
