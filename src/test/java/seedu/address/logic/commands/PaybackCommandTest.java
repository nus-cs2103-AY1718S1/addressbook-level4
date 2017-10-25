package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Debt;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author jelneo
/**
 * Contains integration tests (interaction with the Model) and unit tests for PaybackCommand.
 */
public class PaybackCommandTest {

    private static final String VALID_DEBT_FIGURE = "5000000.50";
    private static final String INVALID_DEBT_FIGURE = "-5000000.50";
    private static final String ENORMOUS_DEBT_FIGURE = "50000000000000000000.50";
    private static final String MESSAGE_INVALID_FORMAT =  String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            PaybackCommand.MESSAGE_USAGE);

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_successfulPayback() {
        Index firstPerson = Index.fromOneBased(1);
        ReadOnlyPerson personWhoPayback = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String expectedMessage = String.format(PaybackCommand.MESSAGE_PAYBACK_SUCCESS,
                personWhoPayback.getName().toString(), VALID_DEBT_FIGURE);
        try {
            Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
            expectedModel.deductDebtFromPerson(personWhoPayback, new Debt(VALID_DEBT_FIGURE));

            PaybackCommand paybackCommand = new PaybackCommand(firstPerson, new Debt(VALID_DEBT_FIGURE));
            paybackCommand.setData(model, new CommandHistory(), new UndoRedoStack());

            assertCommandSuccess(paybackCommand, model, expectedMessage, expectedModel);
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        } catch (PersonNotFoundException pnfe) {
            pnfe.printStackTrace();
        }
    }

    //@@author khooroko
    @Test
    public void execute_noIndex_successfulPayback() {
        model.updateSelectedPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        ReadOnlyPerson personWhoPayback = model.getSelectedPerson();
        String expectedMessage = String.format(PaybackCommand.MESSAGE_PAYBACK_SUCCESS,
                personWhoPayback.getName().toString(), VALID_DEBT_FIGURE);
        try {
            Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
            expectedModel.deductDebtFromPerson(personWhoPayback, new Debt(VALID_DEBT_FIGURE));

            PaybackCommand paybackCommand = new PaybackCommand(new Debt(VALID_DEBT_FIGURE));
            paybackCommand.setData(model, new CommandHistory(), new UndoRedoStack());

            assertCommandSuccess(paybackCommand, model, expectedMessage, expectedModel);
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        } catch (PersonNotFoundException pnfe) {
            pnfe.printStackTrace();
        }
    }

    //@@author jelneo
    @Test
    public void execute_unsuccessfulPayback_dueToInvalidFigure() {
        Index firstPerson = Index.fromOneBased(1);
        try {
            PaybackCommand paybackCommand = new PaybackCommand(firstPerson, new Debt(INVALID_DEBT_FIGURE));
            paybackCommand.setData(model, new CommandHistory(), new UndoRedoStack());

            assertCommandFailure(paybackCommand, model, MESSAGE_INVALID_FORMAT);
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
    }

    @Test
    public void execute_unsuccessfulPayback_dueToAmountExceedingDebtOwed() {
        Index firstPerson = Index.fromOneBased(1);
        try {
            PaybackCommand paybackCommand = new PaybackCommand(firstPerson, new Debt(ENORMOUS_DEBT_FIGURE));
            paybackCommand.setData(model, new CommandHistory(), new UndoRedoStack());

            assertCommandFailure(paybackCommand, model, PaybackCommand.MESSAGE_PAYBACK_FAILURE);
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
    }

    @Test
    public void equals() {
        try {
            PaybackCommand paybackFirstCommand = new PaybackCommand(INDEX_FIRST_PERSON, new Debt("500"));
            PaybackCommand paybackSecondCommand = new PaybackCommand(INDEX_SECOND_PERSON, new Debt("300"));
            PaybackCommand paybackThirdCommand = new PaybackCommand(new Debt("200"));

            // same object -> returns true
            assertTrue(paybackFirstCommand.equals(paybackFirstCommand));
            assertTrue(paybackThirdCommand.equals(paybackThirdCommand));

            // same values -> returns true
            PaybackCommand paybackFirstCommandCopy = new PaybackCommand(INDEX_FIRST_PERSON, new Debt("500"));
            assertTrue(paybackFirstCommand.equals(paybackFirstCommandCopy));

            // different types -> returns false
            assertFalse(paybackFirstCommand.equals(1));

            // null -> returns false
            assertFalse(paybackFirstCommand.equals(null));

            // different person -> returns false
            assertFalse(paybackFirstCommand.equals(paybackSecondCommand));
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
    }

}
