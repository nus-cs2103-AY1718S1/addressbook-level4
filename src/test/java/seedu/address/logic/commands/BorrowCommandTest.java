package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.ListObserver;
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
 * Contains integration tests (interaction with the Model) and unit tests for BorrowCommand.
 */
public class BorrowCommandTest {

    private static final String VALID_DEBT_FIGURE = "5000000.50";
    private static final String INVALID_DEBT_FIGURE = "-5000000.50";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_successfulBorrowing() {
        ReadOnlyPerson personWhoBorrowed = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                + String.format(BorrowCommand.MESSAGE_BORROW_SUCCESS,
                personWhoBorrowed.getName().toString(), VALID_DEBT_FIGURE);
        try {
            Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
            expectedModel.addDebtToPerson(personWhoBorrowed, new Debt(VALID_DEBT_FIGURE));

            BorrowCommand borrowCommand = prepareCommand(INDEX_FIRST_PERSON, new Debt(VALID_DEBT_FIGURE));

            assertCommandSuccess(borrowCommand, model, expectedMessage, expectedModel);
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        } catch (PersonNotFoundException pnfe) {
            pnfe.printStackTrace();
        }
    }

    @Test
    public void execute_unsuccessfulBorrowing() throws IllegalValueException {
        // Only case where borrowing fails is when debt amount is entered incorrectly
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(Debt.MESSAGE_DEBT_CONSTRAINTS);

        Debt debtAmount = new Debt(INVALID_DEBT_FIGURE);
    }

    //@@author khooroko
    @Test
    public void execute_successfulBorrowing_withoutIndex() {
        model.updateSelectedPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        ReadOnlyPerson personWhoBorrowed = model.getSelectedPerson();
        String expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                + String.format(BorrowCommand.MESSAGE_BORROW_SUCCESS,
                personWhoBorrowed.getName().toString(), VALID_DEBT_FIGURE);
        try {
            Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
            expectedModel.addDebtToPerson(personWhoBorrowed, new Debt(VALID_DEBT_FIGURE));

            BorrowCommand borrowCommand = prepareCommand(null, new Debt(VALID_DEBT_FIGURE));
            assertCommandSuccess(borrowCommand, model, expectedMessage, expectedModel);
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        } catch (PersonNotFoundException pnfe) {
            pnfe.printStackTrace();
        }
    }

    //@@author jelneo
    @Test
    public void equals() {
        try {
            BorrowCommand borrowFirstCommand = new BorrowCommand(INDEX_FIRST_PERSON, new Debt("50000"));
            BorrowCommand borrowSecondCommand = new BorrowCommand(INDEX_SECOND_PERSON, new Debt("20000"));
            BorrowCommand borrowThirdCommand = new BorrowCommand(new Debt("20000"));

            // same object -> returns true
            assertTrue(borrowThirdCommand.equals(borrowThirdCommand));
            assertTrue(borrowThirdCommand.equals(borrowThirdCommand));

            // same values -> returns true
            BorrowCommand borrowFirstCommandCopy = new BorrowCommand(INDEX_FIRST_PERSON, new Debt("50000"));
            assertTrue(borrowFirstCommand.equals(borrowFirstCommandCopy));

            // different types -> returns false
            assertFalse(borrowFirstCommand.equals(1));

            // null -> returns false
            assertFalse(borrowFirstCommand.equals(null));

            // different person -> returns false
            assertFalse(borrowFirstCommand.equals(borrowSecondCommand));
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
    }

    /**
     * Prepares a {@code BorrowCommand}.
     */
    private BorrowCommand prepareCommand(Index index, Debt debt) {
        BorrowCommand command = null;
        if (index == null) {
            command = new BorrowCommand(debt);
        } else {
            command = new BorrowCommand(index, debt);
        }
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
