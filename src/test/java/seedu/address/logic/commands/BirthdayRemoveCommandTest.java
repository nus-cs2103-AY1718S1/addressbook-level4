package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Person;

//@@author dalessr
/**
 * Contains integration tests (interaction with the Model) and unit tests for BirthdayRemoveCommand.
 */
public class BirthdayRemoveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new AddressBook(), new UserPrefs());


    /**
     * Edit person list where index is smaller than (or equal to) the size of the address book person list
     */
    @Test
    public void execute_allFieldsSpecifiedCorrectlyUnfilteredList_success() throws Exception {
        Person originalPerson = (Person) model.getAddressBook().getPersonList().get(0);
        Person editedPerson = (Person) model.getAddressBook().getPersonList().get(0);
        Birthday birthday = new Birthday();
        editedPerson.setBirthday(birthday);

        BirthdayRemoveCommand birthdayRemoveCommand = prepareCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(BirthdayRemoveCommand.MESSAGE_REMOVE_BIRTHDAY_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new AddressBook(),
                new UserPrefs());
        expectedModel.updatePerson(originalPerson, editedPerson);

        assertCommandSuccess(birthdayRemoveCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Edit filtered list where index is smaller than (or equal to) the size of filtered list
     */
    @Test
    public void execute_allFieldsSpecifiedCorrectlyFilteredList_success() throws Exception {
        Person originalPerson = (Person) model.getFilteredPersonList().get(0);
        Person editedPerson = (Person) model.getFilteredPersonList().get(0);
        Birthday birthday = new Birthday();
        editedPerson.setBirthday(birthday);

        BirthdayRemoveCommand birthdayRemoveCommand = prepareCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(BirthdayRemoveCommand.MESSAGE_REMOVE_BIRTHDAY_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new AddressBook(),
                new UserPrefs());
        expectedModel.updatePerson(originalPerson, editedPerson);

        assertCommandSuccess(birthdayRemoveCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Edit person list where index is larger than size of the address book person list
     */
    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getAddressBook().getPersonList().size() + 1);
        Birthday birthday = new Birthday();
        BirthdayRemoveCommand birthdayRemoveCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(birthdayRemoveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Birthday birthday = new Birthday();
        BirthdayRemoveCommand birthdayRemoveCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(birthdayRemoveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        Birthday birthday = new Birthday();
        final BirthdayRemoveCommand standardCommand = new BirthdayRemoveCommand(INDEX_FIRST_PERSON);

        // same values -> returns true
        BirthdayRemoveCommand commandWithSameValues = new BirthdayRemoveCommand(INDEX_FIRST_PERSON);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new BirthdayRemoveCommand(INDEX_SECOND_PERSON)));
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code birthday}
     */
    private BirthdayRemoveCommand prepareCommand(Index index) {
        BirthdayRemoveCommand birthdayRemoveCommand = new BirthdayRemoveCommand(index);
        birthdayRemoveCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return birthdayRemoveCommand;
    }
}
