package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DEBT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DEBT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.HANDPHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.HANDPHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.HOME_PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.HOME_PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INTEREST_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INTEREST_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEBT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HANDPHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HOME_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INTEREST_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_OFFICE_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POSTAL_CODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TOTAL_DEBT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.OFFICE_PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.OFFICE_PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.POSTAL_CODE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.POSTAL_CODE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TOTAL_DEBT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TOTAL_DEBT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEBT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HANDPHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOME_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTEREST_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OFFICE_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTAL_CODE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.ListObserver;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Debt;
import seedu.address.model.person.Email;
import seedu.address.model.person.Handphone;
import seedu.address.model.person.HomePhone;
import seedu.address.model.person.Interest;
import seedu.address.model.person.Name;
import seedu.address.model.person.OfficePhone;
import seedu.address.model.person.Person;
import seedu.address.model.person.PostalCode;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class EditCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void edit() throws Exception {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_PERSON;
        String command = " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + NAME_DESC_BOB + "  "
                + HANDPHONE_DESC_BOB + " " + HOME_PHONE_DESC_BOB + " " + OFFICE_PHONE_DESC_BOB + " " + EMAIL_DESC_BOB
                + "  " + ADDRESS_DESC_BOB + " " + POSTAL_CODE_DESC_BOB + " "
                + DEBT_DESC_BOB + " " + TOTAL_DEBT_DESC_BOB + " " + INTEREST_DESC_BOB + " " + DEADLINE_DESC_BOB + "  "
                + TAG_DESC_HUSBAND + " ";
        Person editedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withHandphone(VALID_HANDPHONE_BOB)
                .withHomePhone(VALID_HOME_PHONE_BOB).withOfficePhone(VALID_OFFICE_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withPostalCode(VALID_POSTAL_CODE_BOB)
                .withDebt(VALID_DEBT_BOB)
                .withTotalDebt(VALID_DEBT_BOB)
                .withInterest(VALID_INTEREST_BOB).withDeadline(VALID_DEADLINE_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertCommandSuccess(command, index, editedPerson);

        /* Case: undo editing the last person in the list -> last person restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last person in the list -> last person edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updatePerson(
                getModel().getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), editedPerson);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a person with new values same as existing values -> edited */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + HANDPHONE_DESC_BOB
                + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + POSTAL_CODE_DESC_BOB + DEBT_DESC_BOB + TOTAL_DEBT_DESC_BOB + INTEREST_DESC_BOB + DEADLINE_DESC_BOB
                + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        /* In the initial state of the address book, Alice was the first person in the list.
         * Although Alice's details was edited to Bob's in lines 86 to 88, the totalDebt for Alice remains unchanged.
         * Hence, there is a need to set totalDebt of BOB to be the same as Alice's*/
        assertCommandSuccess(command, index, BOB);

        /* Case: edit some fields -> edited */
        index = INDEX_FIRST_PERSON;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TAG_DESC_FRIEND;
        ReadOnlyPerson personToEdit = getModel().getFilteredPersonList().get(index.getZeroBased());
        editedPerson = new PersonBuilder(personToEdit).withTags(VALID_TAG_FRIEND).build();
        assertCommandSuccess(command, index, editedPerson);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_PERSON;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        editedPerson = new PersonBuilder(personToEdit).withTags().build();
        assertCommandSuccess(command, index, editedPerson);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered person list, edit index within bounds of address book and person list -> edited */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredPersonList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_BOB;
        personToEdit = getModel().getFilteredPersonList().get(index.getZeroBased());
        editedPerson = new PersonBuilder(personToEdit).withName(VALID_NAME_BOB).build();
        assertCommandSuccess(command, index, editedPerson);

        /* Case: filtered person list, edit index within bounds of address book but out of bounds of person list
         * -> rejected */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a person card is selected -------------------------- */

        /* Case: selects first card in the person list, edit a person -> edited, card selection remains unchanged but
         * info panel changes
         */
        showAllPersons();
        index = INDEX_FIRST_PERSON;
        selectPerson(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY + HANDPHONE_DESC_AMY
                + HOME_PHONE_DESC_AMY + OFFICE_PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + POSTAL_CODE_DESC_AMY + DEBT_DESC_AMY + TOTAL_DEBT_DESC_AMY + INTEREST_DESC_AMY + DEADLINE_DESC_AMY
                + TAG_DESC_FRIEND;
        // this can be misleading: card selection actually remains unchanged but the
        // info panel is updated to reflect the new person's name
        /* Although all other information changes, the total debt remains unchanged*/
        assertCommandSuccess(command, index, AMY, index);

        /* Case: missing index -> edited */
        command = EditCommand.COMMAND_WORD + " " + NAME_DESC_BOB + HANDPHONE_DESC_BOB + HOME_PHONE_DESC_BOB
                + OFFICE_PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + DEBT_DESC_BOB
                + TOTAL_DEBT_DESC_BOB + INTEREST_DESC_BOB + DEADLINE_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandSuccess(command, index, BOB, index);

        /* Re-edit to previous state */
        selectPerson(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY + HANDPHONE_DESC_AMY
                + HOME_PHONE_DESC_AMY + OFFICE_PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + POSTAL_CODE_DESC_AMY + DEBT_DESC_AMY + TOTAL_DEBT_DESC_AMY + INTEREST_DESC_AMY
                + DEADLINE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, index, AMY, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredPersonList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_NAME_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid handphone -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_HANDPHONE_DESC,
                Handphone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid home phone -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + INVALID_HOME_PHONE_DESC, HomePhone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid office phone -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + INVALID_OFFICE_PHONE_DESC, OfficePhone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_EMAIL_DESC,
                Email.MESSAGE_EMAIL_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_ADDRESS_DESC,
                Address.MESSAGE_ADDRESS_CONSTRAINTS);

        /* Case: invalid postal code -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + INVALID_POSTAL_CODE_DESC, PostalCode.MESSAGE_POSTAL_CODE_CONSTRAINTS);

        /* Case: invalid debt -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + INVALID_DEBT_DESC, Debt.MESSAGE_DEBT_CONSTRAINTS);

        /* Case: invalid total debt -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + INVALID_TOTAL_DEBT_DESC, ParserUtil.MESSAGE_INVALID_TOTAL_DEBT);

        /* Case: invalid interest -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + INVALID_INTEREST_DESC, Interest.MESSAGE_INTEREST_CONSTRAINTS);

        /* Case: invalid deadline -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + INVALID_DEADLINE_DESC, Deadline.MESSAGE_DEADLINE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_TAG_DESC,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: edit a person with new values same as another person's values -> rejected */
        executeCommand(PersonUtil.getAddCommand(BOB));
        assertTrue(getModel().getAddressBook().getPersonList().contains(BOB));
        index = INDEX_FIRST_PERSON;
        assertFalse(getModel().getFilteredPersonList().get(index.getZeroBased()).equals(BOB));
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + HANDPHONE_DESC_BOB
                + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + POSTAL_CODE_DESC_BOB + DEBT_DESC_BOB + TOTAL_DEBT_DESC_BOB + INTEREST_DESC_BOB + DEADLINE_DESC_BOB
                + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;

        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: edit a person with new values same as another person's values but with different tags -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + HANDPHONE_DESC_BOB
                + HOME_PHONE_DESC_BOB + OFFICE_PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + DEBT_DESC_BOB + TOTAL_DEBT_DESC_BOB + INTEREST_DESC_BOB
                + DEADLINE_DESC_BOB + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    /**
     * Returns the {@ReadOnlyPerson} at the specified index of tha address book
     */
    private ReadOnlyPerson getPersonFromAddressBook(Model model, Index index) {
        return model.getAddressBook().getPersonList().get(index.getZeroBased());
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, ReadOnlyPerson, Index)} except that
     * the info panel and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, ReadOnlyPerson, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, ReadOnlyPerson editedPerson) {
        assertCommandSuccess(command, toEdit, editedPerson, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the person at index {@code toEdit} being
     * updated to values specified {@code editedPerson}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, ReadOnlyPerson editedPerson,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        try {
            expectedModel.updatePerson(
                    expectedModel.getFilteredPersonList().get(toEdit.getZeroBased()), editedPerson);
            expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        } catch (DuplicatePersonException | PersonNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedPerson is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                        + String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson.getName()),
                expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * info panel and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the info panel and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged()
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the info panel, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
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
