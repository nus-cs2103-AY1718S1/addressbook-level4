package seedu.address.logic.commands;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
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
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.UiManager;

//@@author tingtx
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code OrderCommand}.
 */
public class OrderCommandTest {

    private Model model;
    private Model expectedModel;

    private String firstParameter;
    private String secondParameter;
    private String thirdParameter;
    private String fourthParameter;
    private String fifthParameter;

    @Before
    public void setUp() {
        firstParameter = "NAME";
        secondParameter = "ADDRESS";
        thirdParameter = "BIRTHDAY";
        fourthParameter = "TAG";
        fifthParameter = "GROUP";

        model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs(), new Account(),
                new Config());
        expectedModel = new ModelManager(model.getAddressBook(), model.getEventBook(), new UserPrefs(), new Account(),
                new Config());

    }

    //Interaction with model
    @Test
    public void execute_listIsOrdered_showsEverything() {
        OrderCommand command = prepareCommand(firstParameter);
        assertCommandSuccess(command, model, OrderCommand.MESSAGE_ORDER_SUCCESS
                + firstParameter, expectedModel);
    }

    @Test
    public void execute_invalidParameter_throwsCommandException() {
        OrderCommand command = prepareCommand(" ");
        assertCommandFailure(command, model, OrderCommand.MESSAGE_ORDER_WRONG_PARAMETER);

        command = prepareCommand("EMAIL");
        assertCommandFailure(command, model, OrderCommand.MESSAGE_ORDER_WRONG_PARAMETER);

    }

    @Test
    public void execute_nameParameter_orderSuccess() throws CommandException {
        OrderCommand command = prepareCommand(firstParameter);
        assertOrderSuccess(command, OrderCommand.MESSAGE_ORDER_SUCCESS + firstParameter,
                Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }


    @Test
    public void execute_addressParameter_orderSuccess() throws CommandException {
        OrderCommand command = prepareCommand(secondParameter);
        assertOrderSuccess(command, OrderCommand.MESSAGE_ORDER_SUCCESS + secondParameter,
                Arrays.asList(DANIEL, ALICE, BENSON, GEORGE, FIONA, ELLE, CARL));
    }

    @Test
    public void execute_birthdayParameter_orderSuccess() throws CommandException {
        OrderCommand command = prepareCommand(thirdParameter);
        assertOrderSuccess(command, OrderCommand.MESSAGE_ORDER_SUCCESS + thirdParameter,
                Arrays.asList(GEORGE, ALICE, CARL, DANIEL, BENSON, FIONA, ELLE));
    }


    @Test
    public void execute_tagParameter_orderSuccess() throws CommandException {
        OrderCommand command = prepareCommand(fourthParameter);
        assertOrderSuccess(command, OrderCommand.MESSAGE_ORDER_SUCCESS + fourthParameter,
                Arrays.asList(ALICE, CARL, DANIEL, ELLE, FIONA, GEORGE, BENSON));
    }

    @Test
    public void execute_groupParameter_orderSuccess() throws CommandException {
        OrderCommand command = prepareCommand(fifthParameter);
        assertOrderSuccess(command, OrderCommand.MESSAGE_ORDER_SUCCESS + fifthParameter,
                Arrays.asList(ELLE, FIONA, GEORGE, CARL, DANIEL, BENSON, ALICE));
    }

    @Test
    public void execute_mulitiParameters_orderSuccess() throws CommandException {
        OrderCommand command = prepareCommand(thirdParameter.concat(" " + fourthParameter));
        assertOrderSuccess(command, OrderCommand.MESSAGE_ORDER_SUCCESS + thirdParameter + " "
                        + fourthParameter,
                Arrays.asList(GEORGE, ALICE, CARL, DANIEL, FIONA, BENSON, ELLE));

        command = prepareCommand(fifthParameter.concat(" " + firstParameter));
        assertOrderSuccess(command, OrderCommand.MESSAGE_ORDER_SUCCESS + fifthParameter + " "
                        + firstParameter,
                Arrays.asList(ELLE, FIONA, GEORGE, CARL, DANIEL, BENSON, ALICE));
    }

    @Test
    public void equals() {
        final OrderCommand standardCommand = new OrderCommand(thirdParameter + " " + firstParameter);

        // save values -> returns true
        OrderCommand commandWithSameValues = new OrderCommand("BIRTHDAY NAME");
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        //null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> return false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different parameter -> returns false
        assertFalse(standardCommand.equals(new OrderCommand(fourthParameter + " " + thirdParameter)));

        //different order of parameter -> return false
        assertFalse(standardCommand.equals(new OrderCommand(firstParameter + " " + thirdParameter)));

    }


    /**
     * Generates a new OrderCommand
     */
    private OrderCommand prepareCommand(String parameter) {
        OrderCommand orderCommand = new OrderCommand(parameter);
        UserPrefs userPrefs = new UserPrefs();
        Config config = new Config();
        Logic logic = null;
        orderCommand.setData(model, new CommandHistory(), new UndoRedoStack(), new Config(),
                new UiManager(logic, config, userPrefs));
        return orderCommand;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * * - the command feedback is equal to {@code expectedMessage}<br>
     * - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     */
    private void assertOrderSuccess(OrderCommand command, String expectedMessage, List<ReadOnlyPerson> expectedList)
            throws CommandException {
        CommandResult commandResult = command.executeUndoableCommand();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
    }
}
