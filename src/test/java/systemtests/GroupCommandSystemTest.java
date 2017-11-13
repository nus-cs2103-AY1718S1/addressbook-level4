package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GroupCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

//@@author tingtx
public class GroupCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void group() throws Exception {
        Model model = getModel();
        String expectedMessageGrouped =   String.format(GroupCommand.MESSAGE_GROUP_PERSON_SUCCESS + VALID_GROUP_BOB);
        String expectedMessageUnGrouped =   String.format(GroupCommand.MESSAGE_UNGROUP_PERSON_SUCCESS);
        /* ----------------- Performing group operation while an unfiltered list is being shown --------------------*/

        /* Case: group a person, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> grouped
         */
        Index index = INDEX_FIRST_PERSON;
        String command = " " + GroupCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + GROUP_DESC_BOB;
        Person groupedPerson = new PersonBuilder(ALICE).withGroup(VALID_GROUP_BOB).build();
        assertCommandSuccess(command, index, groupedPerson, expectedMessageGrouped);

        /* Case: undo grouping the last person in the list -> last person restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo grouping the last person in the list -> last person grouped again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updatePerson(
                getModel().getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), groupedPerson);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: group a person with new values same as existing values -> grouped */
        ReadOnlyPerson personToGroup = getModel().getFilteredPersonList().get(index.getZeroBased());
        command = GroupCommand.COMMAND_WORD + " " + index.getOneBased() + GROUP_DESC_BOB;
        assertCommandSuccess(command, index, personToGroup, expectedMessageGrouped);

        /* Case: clear group -> cleared */
        command = GroupCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_GROUP.getPrefix();
        groupedPerson = new PersonBuilder(personToGroup).withGroup("").build();
        assertCommandSuccess(command, index, groupedPerson, expectedMessageUnGrouped);

        /* ------------------ Performing group operation while a filtered list is being shown ---------------------- */

        /* Case: filtered person list, group index within bounds of address book and person list -> grouped*/
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredPersonList().size());
        command = GroupCommand.COMMAND_WORD + " " + index.getOneBased() + " " + GROUP_DESC_BOB;
        personToGroup = getModel().getFilteredPersonList().get(index.getZeroBased());
        groupedPerson = new PersonBuilder(personToGroup).withGroup(VALID_GROUP_BOB).build();
        assertCommandSuccess(command, index, groupedPerson, expectedMessageGrouped);

        /* Case: filtered person list, group index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        assertCommandFailure(GroupCommand.COMMAND_WORD + " " + invalidIndex + GROUP_DESC_BOB,
                "Index " + invalidIndex + " is invalid!");

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */
        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(GroupCommand.COMMAND_WORD + " 0" + GROUP_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, GroupCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(GroupCommand.COMMAND_WORD + " -1" + GROUP_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, GroupCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredPersonList().size() + 1;
        assertCommandFailure(GroupCommand.COMMAND_WORD + " " + invalidIndex + GROUP_DESC_BOB,
                "Index " + invalidIndex + " is invalid!");

        /* Case: missing index -> rejected */
        assertCommandFailure(GroupCommand.COMMAND_WORD + GROUP_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, GroupCommand.MESSAGE_USAGE));

        /* Case: missing index and prefix -> rejected */
        assertCommandFailure(GroupCommand.COMMAND_WORD + " " + VALID_GROUP_BOB,
                String.format(GroupCommand.MESSAGE_WRONG_PARAMETER));
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, ReadOnlyPerson, Index)} except that
     * the browser url and selected card remain unchanged.
     *
     * @param toGroup the index of the current model's filtered list
     * @see GroupCommandSystemTest#assertCommandSuccess(String, Index, ReadOnlyPerson, Index, String)
     */
    private void assertCommandSuccess(String command, Index toGroup, ReadOnlyPerson groupedPerson,
                                      String expectedMessage) {
        assertCommandSuccess(command, toGroup, groupedPerson, null, expectedMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code GroupCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the person at index {@code toEdit} being
     * updated to values specified {@code editedPerson}.<br>
     *
     * @param toGroup the index of the current model's filtered list.
     * @see GroupCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toGroup, ReadOnlyPerson groupedPerson,
                                      Index expectedSelectedCardIndex, String expectedMessage) {
        Model expectedModel = getModel();
        try {
            expectedModel.updatePerson(
                    expectedModel.getFilteredPersonList().get(toGroup.getZeroBased()), groupedPerson);
            expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        } catch (DuplicatePersonException | PersonNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedPerson is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
              expectedMessage, expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     *
     * @see GroupCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
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
