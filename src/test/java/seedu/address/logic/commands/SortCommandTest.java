package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.SortCommand.ARGUMENT_ASCENDING_WORD;
import static seedu.address.logic.commands.SortCommand.ARGUMENT_DEFAULT_ORDER;
import static seedu.address.logic.commands.SortCommand.ARGUMENT_DESCENDING_WORD;
import static seedu.address.logic.commands.SortCommand.MESSAGE_SORT_SUCCESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author k-l-a
/**
 * Contains integration tests (interaction with Model) and unit tests for SortCommand
 */
public class SortCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        String validPrefix = PREFIX_NAME.getPrefix();
        String validOrder = ARGUMENT_ASCENDING_WORD;
        final SortCommand typicalCommand = new SortCommand(validPrefix, validOrder);

        //same object -> returns true
        assertTrue(typicalCommand.equals(typicalCommand));

        //same values -> returns true
        SortCommand commandWithSameValues = new SortCommand(PREFIX_NAME.getPrefix(), ARGUMENT_ASCENDING_WORD);
        assertTrue(typicalCommand.equals(commandWithSameValues));

        //null -> returns false
        assertFalse(typicalCommand.equals(null));

        //different type -> returns false
        assertFalse(typicalCommand.equals(new ClearCommand()));

        //different prefix -> returns false
        assertFalse(typicalCommand.equals(new SortCommand(PREFIX_EMAIL.getPrefix(), validOrder)));

        //different order -> returns false
        assertFalse(typicalCommand.equals(new SortCommand(validPrefix, ARGUMENT_DESCENDING_WORD)));
    }

    @Test
    public void execute_defaultOrder_success() {
        String expectedMessageA = String.format(MESSAGE_SORT_SUCCESS, ARGUMENT_DEFAULT_ORDER, ARGUMENT_ASCENDING_WORD);
        String expectedMessageD = String.format(MESSAGE_SORT_SUCCESS, ARGUMENT_DEFAULT_ORDER, ARGUMENT_DESCENDING_WORD);

        SortCommand commandA = prepareCommand(ARGUMENT_DEFAULT_ORDER, ARGUMENT_ASCENDING_WORD);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateSortedFilteredPersonList((person1, person2) -> (-1));
        assertCommandSuccess(commandA, model, expectedMessageA, expectedModel);

        SortCommand commandD = prepareCommand(ARGUMENT_DEFAULT_ORDER, ARGUMENT_DESCENDING_WORD);
        expectedModel.updateSortedFilteredPersonList(null); //reset
        expectedModel.updateSortedFilteredPersonList((person1, person2) -> (1));
        assertCommandSuccess(commandD, model, expectedMessageD, expectedModel);
    }

    @Test
    public void execute_validPrefix_success() {
        String expectedMessageA = String
                .format(MESSAGE_SORT_SUCCESS, PREFIX_NAME.getPrefix(), ARGUMENT_ASCENDING_WORD);
        String expectedMessageD = String
                .format(MESSAGE_SORT_SUCCESS, PREFIX_PHONE.getPrefix(), ARGUMENT_DESCENDING_WORD);

        //ascending name (alphanumeric)
        String testedPrefix = PREFIX_NAME.getPrefix();
        SortCommand commandA = prepareCommand(testedPrefix, ARGUMENT_ASCENDING_WORD);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateSortedFilteredPersonList((person1, person2) -> (person1.getName()
                .fullName.compareToIgnoreCase(person2.getName().fullName)));
        assertCommandSuccess(commandA, model, expectedMessageA, expectedModel);

        //descending phone (digits)
        testedPrefix = PREFIX_PHONE.getPrefix();
        SortCommand commandD = prepareCommand(testedPrefix, ARGUMENT_DESCENDING_WORD);
        expectedModel.updateSortedFilteredPersonList((person1, person2) -> (person2.getPhone()
                .value.compareToIgnoreCase(person1.getPhone().value)));
        assertCommandSuccess(commandD, model, expectedMessageD, expectedModel);

        //ascending address
        testedPrefix = PREFIX_ADDRESS.getPrefix();
        expectedMessageA = String.format(MESSAGE_SORT_SUCCESS, testedPrefix, ARGUMENT_ASCENDING_WORD);
        commandA = prepareCommand(testedPrefix, ARGUMENT_ASCENDING_WORD);
        expectedModel.updateSortedFilteredPersonList((person1, person2) -> (person1.getAddress()
                .value.compareToIgnoreCase(person2.getAddress().value)));
        assertCommandSuccess(commandA, model, expectedMessageA, expectedModel);
    }

    /**
     * Parses the prefix and order string and returns a SortCommand.
     */
    public SortCommand prepareCommand(String prefix, String order) {
        SortCommand sortCommand = new SortCommand(prefix, order);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }
}
