//@@author A0143832J
package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_ANY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TestUtil.getPerson;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class RemarkCommandSystemTest extends AddressBookSystemTest {

    // this message is used to match message in result display, which should be empty for any failed execution
    private static final String MESSAGE_EXECUTION_FAILURE_EMPTY = "";

    @Test
    public void remark() throws Exception {
        Model model = getModel();

        /* -------------- Performing remark operation while an unfiltered list is being shown -------------------- */

        /* Case: remark a valid index in the list -> edited
         */
        Model expectedModel = getModel();
        Index index = INDEX_FIRST_PERSON;
        String command = " " + RemarkCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + REMARK_DESC_ANY;

        ReadOnlyPerson personToEdit = getPerson(expectedModel, index);
        Person newPerson = RemarkCommand.addOrChangeRemark(
                personToEdit, new Remark(VALID_REMARK));
        assertCommandSuccess(command, index, newPerson);

        /* Case: undo remarking the last person in the list -> last person restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo remarking the last person in the list -> last person edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updatePerson(
                getModel().getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), newPerson);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: remark a person with new values same as existing values -> remark */
        command = RemarkCommand.COMMAND_WORD + " " + INDEX_SECOND_PERSON.getOneBased()
                + " ";
        assertCommandSuccess(command, INDEX_SECOND_PERSON, BENSON);



        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered person list, edit index within bounds of address book and person list -> edited */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredPersonList().size());
        command = RemarkCommand.COMMAND_WORD + " " + index.getOneBased() + " " + REMARK_DESC_ANY;
        personToEdit = getModel().getFilteredPersonList().get(index.getZeroBased());
        newPerson = new PersonBuilder(personToEdit).withRemark(VALID_REMARK).build();
        assertCommandSuccess(command, index, newPerson);

        /* Case: filtered person list, edit index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        assertCommandFailure(RemarkCommand.COMMAND_WORD + " " + invalidIndex + REMARK_DESC_ANY,
                MESSAGE_EXECUTION_FAILURE_EMPTY);

        /* ---------------------Performing remark operation while a person card is selected ------------------------- */

        /* Case: selects first card in the person list, remark a person -> remarked, card selection
         * remains unchanged but browser url changes
         */
        showAllPersons();
        index = INDEX_FIRST_PERSON;
        selectPerson(index);
        command = RemarkCommand.COMMAND_WORD + " "
                + index.getOneBased() + REMARK_DESC_ANY;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new person's name
        newPerson = new Person(ALICE);
        newPerson.setRemark(new Remark("dev"));
        assertCommandSuccess(command, index, newPerson, index);

        /* -------------------------------- Performing invalid remark operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(RemarkCommand.COMMAND_WORD + " 0" + NAME_DESC_BOB,
                MESSAGE_EXECUTION_FAILURE_EMPTY);

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(RemarkCommand.COMMAND_WORD + " -1" + NAME_DESC_BOB,
                MESSAGE_EXECUTION_FAILURE_EMPTY);

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredPersonList().size() + 1;
        assertCommandFailure(RemarkCommand.COMMAND_WORD + " " + invalidIndex + REMARK_DESC_ANY,
                MESSAGE_EXECUTION_FAILURE_EMPTY);

        /* Case: missing index -> rejected */
        assertCommandFailure(RemarkCommand.COMMAND_WORD + NAME_DESC_BOB,
                MESSAGE_EXECUTION_FAILURE_EMPTY);

    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, ReadOnlyPerson, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toRemark the index of the current model's filtered list
     * @see RemarkCommandSystemTest#assertCommandSuccess(String, Index, ReadOnlyPerson, Index)
     */
    private void assertCommandSuccess(String command, Index toRemark, ReadOnlyPerson editedPerson) {
        assertCommandSuccess(command, toRemark, editedPerson, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the person at index {@code toEdit} being
     * updated to values specified {@code editedPerson}.<br>
     * @param toRemark the index of the current model's filtered list.
     * @see RemarkCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toRemark, ReadOnlyPerson editedPerson,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        try {
            expectedModel.updatePerson(
                    expectedModel.getFilteredPersonList().get(toRemark.getZeroBased()), editedPerson);
            expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        } catch (DuplicatePersonException | PersonNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedPerson is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(RemarkCommand.MESSAGE_REMARK_PERSON_SUCCESS, editedPerson), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see RemarkCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command, false);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command, true);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
//@@author
