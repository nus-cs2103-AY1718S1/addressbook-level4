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

/**
 * Contains integration tests (interaction with the Model) and unit tests for BirthdayAddCommand.
 */
public class BirthdayAddCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedCorrectlyUnfilteredList_success() throws Exception {
        Person originalPerson = (Person) model.getAddressBook().getPersonList().get(0);
        Person editedPerson = (Person) model.getAddressBook().getPersonList().get(0);
        Birthday birthday = new Birthday("01/01/2000");
        editedPerson.setBirthday(birthday);

        BirthdayAddCommand birthdayAddCommand = prepareCommand(INDEX_FIRST_PERSON, birthday);
        String expectedMessage = String.format(BirthdayAddCommand.MESSAGE_ADD_BIRTHDAY_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(originalPerson, editedPerson);

        assertCommandSuccess(birthdayAddCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allFieldsSpecifiedCorrectlyFilteredList_success() throws Exception {
        Person originalPerson = (Person) model.getFilteredPersonList().get(0);
        Person editedPerson = (Person) model.getFilteredPersonList().get(0);
        Birthday birthday = new Birthday("01/01/2000");
        editedPerson.setBirthday(birthday);

        BirthdayAddCommand birthdayAddCommand = prepareCommand(INDEX_FIRST_PERSON, birthday);
        String expectedMessage = String.format(BirthdayAddCommand.MESSAGE_ADD_BIRTHDAY_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(originalPerson, editedPerson);

        assertCommandSuccess(birthdayAddCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getAddressBook().getPersonList().size() + 1);
        Birthday birthday = new Birthday("01/01/2000");
        BirthdayAddCommand birthdayAddCommand = prepareCommand(outOfBoundIndex, birthday);

        assertCommandFailure(birthdayAddCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Birthday birthday = new Birthday("01/01/2000");
        BirthdayAddCommand birthdayAddCommand = prepareCommand(outOfBoundIndex, birthday);

        assertCommandFailure(birthdayAddCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        Birthday birthday = new Birthday("01/01/2000");
        final BirthdayAddCommand standardCommand = new BirthdayAddCommand(INDEX_FIRST_PERSON, birthday);

        // same values -> returns true
        BirthdayAddCommand commandWithSameValues = new BirthdayAddCommand(INDEX_FIRST_PERSON, birthday);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new BirthdayAddCommand(INDEX_SECOND_PERSON, birthday)));

        // different birthday value -> returns false
        Birthday anotherBirthday = new Birthday("10/10/2010");
        assertFalse(standardCommand.equals(new BirthdayAddCommand(INDEX_FIRST_PERSON, anotherBirthday)));
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code birthday}
     */
    private BirthdayAddCommand prepareCommand(Index index, Birthday birthday) {
        BirthdayAddCommand birthdayAddCommand = new BirthdayAddCommand(index, birthday);
        birthdayAddCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return birthdayAddCommand;
    }
}
