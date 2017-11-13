package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.REL_DESC_COLLEAGUE;
import static seedu.address.logic.commands.CommandTestUtil.REL_DESC_SIBLINGS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALICE_REL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BENSON_REL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REL_SIBLINGS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLEAR_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_RELATIONSHIP;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.relationship.SetRelCommand;
import seedu.address.logic.parser.relationship.SetRelCommandParser;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

//@@author huiyiiih
public class SetRelCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void set() throws Exception {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index indexOne = INDEX_FIRST_PERSON;
        Index indexTwo = INDEX_SECOND_PERSON;
        ReadOnlyPerson personInFilteredListOne = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ReadOnlyPerson personInFilteredListTwo = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person personOne = new PersonBuilder(personInFilteredListOne).withRelation(VALID_BENSON_REL).build();
        Person personTwo = new PersonBuilder(personInFilteredListTwo).withRelation(VALID_ALICE_REL).build();
        String command = " " + SetRelCommand.COMMAND_WORD + "  " + indexOne.getOneBased() + " " + indexTwo.getOneBased()
            + "  " + REL_DESC_SIBLINGS + "  ";
        assertCommandSuccess(command, indexOne, indexTwo, personOne, personTwo);

        /* Case: undo the adding of the relationship of the two persons in the list -> relationship not added */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding of the relationship of the two persons in the list -> relationship added edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updatePerson(
            getModel().getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), personOne);
        model.updatePerson(
            getModel().getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased()), personTwo);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: deleting the relationship between the two persons -> relationship deleted */
        command = SetRelCommand.COMMAND_WORD + " " + indexOne.getOneBased() + " " + indexTwo.getOneBased()
            + " " + PREFIX_DELETE_RELATIONSHIP + VALID_REL_SIBLINGS;
        personOne = new PersonBuilder(personInFilteredListOne).withRelation().build();
        personTwo = new PersonBuilder(personInFilteredListTwo).withRelation().build();
        assertCommandSuccess(command, indexOne, indexTwo, personOne, personTwo);

        /* Case: undo the deleting of the relationship of the two persons in the list -> relationship not deleted */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: clearing the relationship between the two persons -> relationship cleared */
        command = SetRelCommand.COMMAND_WORD + " " + indexOne.getOneBased() + " " + indexTwo.getOneBased()
            + " " + PREFIX_CLEAR_RELATIONSHIP;
        personOne = new PersonBuilder(personInFilteredListOne).withRelation().build();
        personTwo = new PersonBuilder(personInFilteredListTwo).withRelation().build();
        assertCommandSuccess(command, indexOne, indexTwo, personOne, personTwo);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index(0) and index(1) -> rejected */
        assertCommandFailure(SetRelCommand.COMMAND_WORD + " 0 1 " + REL_DESC_SIBLINGS,
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SetRelCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) and index(2) -> rejected */
        assertCommandFailure(SetRelCommand.COMMAND_WORD + " -1 2 " + REL_DESC_SIBLINGS,
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SetRelCommand.MESSAGE_USAGE));

        /* Case: same indexes (2) -> rejected */
        assertCommandFailure(SetRelCommand.COMMAND_WORD + " 2 2 " + REL_DESC_SIBLINGS,
            SetRelCommandParser.SAME_INDEX_ERROR);

        /* Case: missing index -> rejected */
        assertCommandFailure(SetRelCommand.COMMAND_WORD + REL_DESC_SIBLINGS,
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SetRelCommand.MESSAGE_USAGE));

        /* Case: set multiple relationship for the same persons -> rejected */
        assertCommandFailure(SetRelCommand.COMMAND_WORD + " 1 2" + REL_DESC_SIBLINGS + REL_DESC_COLLEAGUE,
            String.format(SetRelCommandParser.ONE_RELATIONSHIP_ALLOWED));

        /* Case: setting another relationship to the same two persons -> rejected */
        executeCommand(SetRelCommand.COMMAND_WORD + " 1 2" + REL_DESC_COLLEAGUE);
        assertCommandFailure(SetRelCommand.COMMAND_WORD + " 1 2 " + REL_DESC_COLLEAGUE,
            SetRelCommand.MESSAGE_NO_MULTIPLE_REL);

        /* Case: only one index is present -> rejected */
        executeCommand(SetRelCommand.COMMAND_WORD + " 1" + REL_DESC_SIBLINGS);
        assertCommandFailure(SetRelCommand.COMMAND_WORD + " 1" + REL_DESC_SIBLINGS,
            SetRelCommandParser.INVALID_INDEX);
    }

    /**
     * Performs the same verification as
     * {@code assertCommandSuccess(String, Index, Index, ReadOnlyPerson, ReadOnlyPerson)}
     * except that the browser url and selected card remain unchanged.
     *
     * @param toEdit the index of the current model's filtered list
     * @param toEditOne the index of the current model's filtered list
     * @see SetRelCommandSystemTest#assertCommandSuccess(String, Index, Index, ReadOnlyPerson, ReadOnlyPerson)
     */
    private void assertCommandSuccess(String command, Index toEdit, Index toEditOne, ReadOnlyPerson editedPersonOne,
                                      ReadOnlyPerson editedPersonTwo) {
        assertCommandSuccess(command, toEdit, toEditOne, editedPersonOne, editedPersonTwo, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the person at index {@code toEdit} being
     * updated to values specified {@code editedPerson}.<br>
     *
     * @param toEdit the index of the current model's filtered list.
     * @see SetRelCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Index toEditOne, ReadOnlyPerson
        editedPersonOne, ReadOnlyPerson editedPersonTwo, Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        ReadOnlyPerson personInFilteredListOne = expectedModel.getFilteredPersonList().get(toEdit.getZeroBased());
        ReadOnlyPerson personInFilteredListTwo = expectedModel.getFilteredPersonList().get(toEditOne.getZeroBased());
        try {
            expectedModel.updatePerson(personInFilteredListOne, editedPersonOne);
            expectedModel.updatePerson(personInFilteredListTwo, editedPersonTwo);
            expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        } catch (DuplicatePersonException | PersonNotFoundException e) {
            throw new IllegalArgumentException(
            "editedPerson is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
            String.format(SetRelCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPersonOne, editedPersonTwo),
                expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     *
     * @see SetRelCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
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
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
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

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
//@@author
