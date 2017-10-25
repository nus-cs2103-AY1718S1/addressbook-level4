package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.logic.commands.AddMultipleCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

public class AddMultipleCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();

        /* Case: add from a file that contains duplicated persons --> rejected */
        final String DUPLICATE_PERSONS_FILEPATH = "./src/test/data/AddMultipleCommandSystemTest/duplicatePersons.txt";
        String command = AddMultipleCommand.COMMAND_WORD + " " + DUPLICATE_PERSONS_FILEPATH;
        String expectedResultMessage = AddMultipleCommand.MESSAGE_DUPLICATE_PERSON;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: add from a file that contains missing field name */
        final String MISSING_FIELD_NAME_FILEPATH = "./src/test/data/AddMultipleCommandSystemTest/missingPrefix_name.txt";
        command = AddMultipleCommand.COMMAND_WORD + " " + MISSING_FIELD_NAME_FILEPATH;
        expectedResultMessage = String.format(MESSAGE_INVALID_PERSON_FORMAT, AddMultipleCommand.MESSAGE_PERSON_FORMAT);
        assertCommandFailure(command, expectedResultMessage);

        /* Case: add from a file that does not exist in the data folder --> rejected */
        String NOT_EXISTS_FILE = "doesNotExist.txt";
        command = AddMultipleCommand.COMMAND_WORD + "  " + NOT_EXISTS_FILE;
        expectedResultMessage = String.format(AddMultipleCommand.MESSAGE_INVALID_FILE, NOT_EXISTS_FILE);
        assertCommandFailure(command, expectedResultMessage);

       /* Case add from a file containing valid persons --> added */ 
        String VALID_PERSONS_FILEPATH = "./src/test/data/AddMultipleCommandSystemTest/validPersons_missingOptionalFields.txt";
        ArrayList<ReadOnlyPerson> personList = new ArrayList<>();
        ReadOnlyPerson amy = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withTags().build();
        ReadOnlyPerson bob = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags().build();
        personList.add(amy);
        personList.add(bob);
        command = AddMultipleCommand.COMMAND_WORD + " " + VALID_PERSONS_FILEPATH;
        assertCommandSuccess(command, personList);

         /* Case: undo adding persons to the list -> persons deleted */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding persons to the list -> persons added again */
        command = RedoCommand.COMMAND_WORD;
        try {
            for (ReadOnlyPerson person : personList) {
                model.addPerson(person);
            }
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);
    }

    /**
     * Executes the {@code AddMultipleCommand} that adds {@code toAdd} to the model and verifies that the command box displays
     * an empty string, the result display box displays the success message of executing {@code AddMultipleCommand} with the
     * details of {@code toAdd}, and the model related components equal to the current model added with {@code toAdd}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class, the status bar's sync status changes,
     * the browser url and selected card remains unchanged.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, ArrayList<ReadOnlyPerson> toAdd) {
        Model expectedModel = getModel();
        try {
            for (ReadOnlyPerson person : toAdd) {
                expectedModel.addPerson(person);
            }
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }

        StringBuilder successMessage = new StringBuilder();
        for (ReadOnlyPerson personToAdd: toAdd) {
            successMessage.append(System.lineSeparator());
            successMessage.append(personToAdd);
        }
        String expectedResultMessage = String.format(AddMultipleCommand.MESSAGE_SUCCESS, successMessage);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ArrayList<ReadOnlyPerson>)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     * @see AddMultipleCommandSystemTest#assertCommandSuccess(String, ArrayList<ReadOnlyPerson>)
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
