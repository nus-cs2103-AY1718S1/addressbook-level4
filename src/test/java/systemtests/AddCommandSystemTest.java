package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_POSTFIX_POSTALCODE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_PREFIX_POSTALCODE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDAY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDAY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.FAV_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BIRTHDAY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FAVOURITE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POSTALCODE_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POSTALCODE_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POSTALCODE_DESC_3;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POSTALCODE_DESC_4;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.POSTALCODE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.POSTALCODE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FAV_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTALCODE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTALCODE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Favourite;
import seedu.address.model.person.Name;

import seedu.address.model.person.Phone;
import seedu.address.model.person.PostalCode;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();
        /* Case: add a person without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        ReadOnlyPerson toAdd = AMY;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
                + BIRTHDAY_DESC_AMY + " " + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   " + POSTALCODE_DESC_AMY
                + "  " + FAV_DESC_AMY + " " + TAG_DESC_FRIEND + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addPerson(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a duplicate person -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + POSTALCODE_DESC_AMY + FAV_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different tags -> rejected */
        // "friends" is an existing tag used in the default model, see TypicalPersons#ALICE
        // This test will fail if a new tag that is not in the model is used, see the bug documented in
        // AddressBook#addPerson(ReadOnlyPerson)
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + POSTALCODE_DESC_AMY + FAV_DESC_AMY + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a person with all fields same as another person in the address book except name -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_AMY).withBirthday(VALID_BIRTHDAY_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withPostalCode(VALID_POSTALCODE_AMY).withFavourite(VALID_FAV_AMY).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + POSTALCODE_DESC_AMY + FAV_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except phone -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_BOB).withBirthday(VALID_BIRTHDAY_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withPostalCode(VALID_POSTALCODE_AMY).withFavourite(VALID_FAV_AMY).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_BOB + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + POSTALCODE_DESC_AMY + FAV_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except birthday -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withBirthday(VALID_BIRTHDAY_BOB)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withPostalCode(VALID_POSTALCODE_AMY).withFavourite(VALID_FAV_AMY).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_BOB + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + POSTALCODE_DESC_AMY + FAV_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except email -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withBirthday(VALID_BIRTHDAY_AMY)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_AMY)
                .withPostalCode(VALID_POSTALCODE_AMY).withFavourite(VALID_FAV_AMY).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_AMY + POSTALCODE_DESC_AMY + FAV_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except address -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withBirthday(VALID_BIRTHDAY_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_BOB)
                .withPostalCode(VALID_POSTALCODE_AMY).withFavourite(VALID_FAV_AMY).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_BOB + POSTALCODE_DESC_AMY + FAV_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except postal code -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withBirthday(VALID_BIRTHDAY_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withPostalCode(VALID_POSTALCODE_BOB).withFavourite(VALID_FAV_AMY).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + POSTALCODE_DESC_BOB + FAV_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: filters the person list before adding -> added */
        executeCommand(FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER);
        assert getModel().getFilteredPersonList().size()
                < getModel().getAddressBook().getPersonList().size();
        assertCommandSuccess(IDA);

        /* Case: add to empty address book -> added */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        assertCommandSuccess(ALICE);

        /* Case: add a person with postfix postal code in address -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withBirthday(VALID_BIRTHDAY_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY + " " + VALID_POSTALCODE_AMY)
                .withPostalCode(VALID_POSTALCODE_AMY).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY
                 + ADDRESS_POSTFIX_POSTALCODE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with prefix postal code in address -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_BOB).withBirthday(VALID_BIRTHDAY_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_POSTALCODE_AMY + " " + VALID_ADDRESS_AMY)
                .withPostalCode(VALID_POSTALCODE_AMY).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_BOB + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY
                 + ADDRESS_PREFIX_POSTALCODE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with tags, command with parameters in random order -> added */
        toAdd = BOB;
        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIEND + BIRTHDAY_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + NAME_DESC_BOB + POSTALCODE_DESC_BOB + TAG_DESC_HUSBAND + EMAIL_DESC_BOB;
        assertCommandSuccess(command, toAdd);

        /* Case: selects first card in the person list, add a person -> added, card selection remains unchanged */
        executeCommand(SelectCommand.COMMAND_WORD + " 1");
        assert getPersonListPanel().isAnyCardSelected();
        assertCommandSuccess(CARL);

        /* Case: add a person, missing tags -> added */
        assertCommandSuccess(HOON);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY
                + POSTALCODE_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY
                + POSTALCODE_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing birthday -> added */
        ReadOnlyPerson person = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withPostalCode(VALID_POSTALCODE_AMY).build();
        assertCommandSuccess(person);

        /* Case: missing email -> added */
        person = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withBirthday(VALID_BIRTHDAY_AMY).withAddress(VALID_ADDRESS_AMY).withPostalCode(VALID_POSTALCODE_AMY)
                .build();
        assertCommandSuccess(person);

        /* Case: missing address -> added */
        person = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withBirthday(VALID_BIRTHDAY_AMY).withEmail(VALID_EMAIL_AMY).withPostalCode(VALID_POSTALCODE_AMY)
                .build();
        assertCommandSuccess(person);

        /* Case: invalid keyword -> rejected */
        command = "adds " + PersonUtil.getPersonDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid keyword -> rejected */
        command = "as " + PersonUtil.getPersonDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + FAV_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + INVALID_PHONE_DESC + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + FAV_DESC_AMY;
        assertCommandFailure(command, Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid birthday -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + INVALID_BIRTHDAY_DESC + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + FAV_DESC_AMY;
        assertCommandFailure(command, Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY + INVALID_EMAIL_DESC
                + ADDRESS_DESC_AMY + FAV_DESC_AMY;
        assertCommandFailure(command, Email.MESSAGE_EMAIL_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY
                + INVALID_ADDRESS_DESC + FAV_DESC_AMY;
        assertCommandFailure(command, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        /* Case: invalid postal code, letters not allowed -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY
                + INVALID_POSTALCODE_DESC_1 + ADDRESS_DESC_AMY + FAV_DESC_AMY;
        assertCommandFailure(command, PostalCode.MESSAGE_POSTALCODE_CONSTRAINTS);

        /* Case: invalid postal code, less than 6 digits not allowed -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY
                + INVALID_POSTALCODE_DESC_2 + ADDRESS_DESC_AMY + FAV_DESC_AMY;
        assertCommandFailure(command, PostalCode.MESSAGE_POSTALCODE_CONSTRAINTS);

        /* Case: invalid postal code, more than 6 digits not allowed -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY
                + INVALID_POSTALCODE_DESC_3 + ADDRESS_DESC_AMY + FAV_DESC_AMY;
        assertCommandFailure(command, PostalCode.MESSAGE_POSTALCODE_CONSTRAINTS);

        /* Case: invalid postal code, beyond range of postal codes in Singapore, not allowed -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY
                + INVALID_POSTALCODE_DESC_4 + ADDRESS_DESC_AMY + FAV_DESC_AMY;
        assertCommandFailure(command, PostalCode.MESSAGE_POSTALCODE_CONSTRAINTS);

        /* Case: invalid favourite -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY
                + POSTALCODE_DESC_AMY + ADDRESS_DESC_AMY + INVALID_FAVOURITE_DESC;
        assertCommandFailure(command, Favourite.MESSAGE_FAVOURITE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + FAV_DESC_AMY + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and verifies that the command box displays
     * an empty string, the result display box displays the success message of executing {@code AddCommand} with the
     * details of {@code toAdd}, and the model related components equal to the current model added with {@code toAdd}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class, the status bar's sync status changes,
     * the browser url and selected card remains unchanged.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(ReadOnlyPerson toAdd) {
        assertCommandSuccess(PersonUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(ReadOnlyPerson)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(ReadOnlyPerson)
     */
    private void assertCommandSuccess(String command, ReadOnlyPerson toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addPerson(toAdd);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ReadOnlyPerson)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     * @see AddCommandSystemTest#assertCommandSuccess(String, ReadOnlyPerson)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
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
