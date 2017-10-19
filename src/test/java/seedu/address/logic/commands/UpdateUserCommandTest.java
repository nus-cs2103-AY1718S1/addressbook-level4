package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getSortedTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UserPerson;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UpdateUserCommand.
 */
public class UpdateUserCommandTest {

    private Model model = new ModelManager(getSortedTypicalAddressBook(), new UserPrefs(), new UserPerson());

    @Test
    public void execute_allFieldsSpecified_success() throws Exception {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        UpdateUserCommand updateUserCommand = prepareCommand(descriptor);

        String expectedMessage = String.format(UpdateUserCommand.MESSAGE_UPDATE_USER_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new UserPerson());
        expectedModel.updateUserPerson(editedPerson);

        assertCommandSuccess(updateUserCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() throws Exception {
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).build();
        UpdateUserCommand updateUserCommand = prepareCommand(descriptor);

        UserPerson editedPerson = new UserPerson();
        editedPerson.setName(new Name(VALID_NAME_BOB));
        editedPerson.setPhone(new Phone(VALID_PHONE_BOB));

        String expectedMessage = String.format(UpdateUserCommand.MESSAGE_UPDATE_USER_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(getSortedTypicalAddressBook(), new UserPrefs(),
                new UserPerson(editedPerson));

        assertCommandSuccess(updateUserCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecified_success() {
        UpdateUserCommand updateUserCommand = prepareCommand(new EditPersonDescriptor());
        ReadOnlyPerson editedPerson = model.getUserPerson();

        String expectedMessage = String.format(UpdateUserCommand.MESSAGE_UPDATE_USER_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new UserPerson(editedPerson));

        assertCommandSuccess(updateUserCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final UpdateUserCommand standardCommand = new UpdateUserCommand(DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        UpdateUserCommand commandWithSameValues = new UpdateUserCommand(copyDescriptor);

        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new UpdateUserCommand(DESC_BOB)));
    }

    /**
     * Returns an {@code UserCommand} with parameters {@code descriptor}
     */
    private UpdateUserCommand prepareCommand(EditPersonDescriptor descriptor) {
        UpdateUserCommand updateUserCommand = new UpdateUserCommand(descriptor);
        updateUserCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return updateUserCommand;
    }
}
