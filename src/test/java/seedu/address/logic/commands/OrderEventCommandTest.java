package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.DEEPAVALI;
import static seedu.address.testutil.TypicalEvents.HENNA;
import static seedu.address.testutil.TypicalEvents.SPECTRA;
import static seedu.address.testutil.TypicalEvents.WINE;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Config;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Logic;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Account;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.ui.UiManager;

//@@author kaiyu92
public class OrderEventCommandTest {
    private Model model;
    private Model expectedModel;

    private String firstParameter;
    private String secondParameter;
    private String thirdParameter;

    @Before
    public void setUp() {
        firstParameter = "TITLE";
        secondParameter = "LOCATION";
        thirdParameter = "DATETIME";

        model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs(), new Account(),
                new Config());
        expectedModel = new ModelManager(model.getAddressBook(), model.getEventBook(), new UserPrefs(), new Account(),
                new Config());

    }

    //Interaction with model
    @Test
    public void execute_listIsOrdered_showsEverything() {
        OrderEventCommand command = prepareCommand(firstParameter);
        assertCommandSuccess(command, model, OrderEventCommand.MESSAGE_SORT_SUCCESS
                + firstParameter, expectedModel);
    }

    @Test
    public void execute_zeroParameter_listNotSorted() throws CommandException {
        OrderEventCommand command = prepareCommand(" ");
        assertOrderSuccess(command, OrderEventCommand.MESSAGE_SORT_WRONG_PARAMETER,
                Arrays.asList(SPECTRA, DEEPAVALI, HENNA, WINE));
    }

    @Test
    public void execute_titleParameter_listSorted() throws CommandException {
        OrderEventCommand command = prepareCommand(firstParameter);
        assertOrderSuccess(command, OrderEventCommand.MESSAGE_SORT_SUCCESS + firstParameter,
                Arrays.asList(DEEPAVALI, HENNA, SPECTRA, WINE));
    }


    @Test
    public void execute_locationParameter_listSorted() throws CommandException {
        OrderEventCommand command = prepareCommand(secondParameter);
        assertOrderSuccess(command, OrderEventCommand.MESSAGE_SORT_SUCCESS + secondParameter,
                Arrays.asList(WINE, DEEPAVALI, SPECTRA, HENNA));
    }

    @Test
    public void execute_datetimeParameter_listSorted() throws CommandException {
        OrderEventCommand command = prepareCommand(thirdParameter);
        assertOrderSuccess(command, OrderEventCommand.MESSAGE_SORT_SUCCESS + thirdParameter,
                Arrays.asList(DEEPAVALI, WINE, HENNA, SPECTRA));
    }

    @Test
    public void equals() {
        final OrderEventCommand standardCommand = new OrderEventCommand(firstParameter);

        // save values -> returns true
        OrderEventCommand commandWithSameValues = new OrderEventCommand("TITLE");
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        //null -> returns false
        assertFalse(standardCommand.equals(null));

        // different parameter -> returns false
        assertFalse(standardCommand.equals(new OrderEventCommand(thirdParameter)));

        //different order of parameter -> return false
        assertFalse(standardCommand.equals(new OrderEventCommand(secondParameter)));

    }

    /**
     * Generates a new OrderCommand
     */
    private OrderEventCommand prepareCommand(String parameter) {
        OrderEventCommand orderEventCommand = new OrderEventCommand(parameter);
        UserPrefs userPrefs = new UserPrefs();
        Config config = new Config();
        Logic logic = null;
        orderEventCommand.setData(model, new CommandHistory(), new UndoRedoStack(), new Config(),
                new UiManager(logic, config, userPrefs));
        return orderEventCommand;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * * - the command feedback is equal to {@code expectedMessage}<br>
     * - the {@code FilteredList<ReadOnlyEvent>} is equal to {@code expectedList}<br>
     */
    private void assertOrderSuccess(OrderEventCommand command, String expectedMessage,
                                    List<ReadOnlyEvent> expectedList) throws CommandException {
        CommandResult commandResult = command.executeUndoableCommand();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredEventList());
    }
}
