//@@author arturs68
package seedu.address.logic.commands;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.model.person.ProfilePicture.DEFAULT_PICTURE;
import static seedu.address.model.util.SampleDataUtil.SAMPLE_PICTURE;
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
import seedu.address.model.person.Person;
import seedu.address.model.person.ProfilePicture;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ChangePicCommand.
 */
public class ChangePicCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_changepic_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .build();
        editedPerson.setProfilePicture(new ProfilePicture(SAMPLE_PICTURE));

        ChangePicCommand changePicCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getProfilePicture().value);

        String expectedMessage = String.format(ChangePicCommand.MESSAGE_CHANGEPIC_SUCCESS, editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(changePicCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).build();
        editedPerson.setProfilePicture(new ProfilePicture(SAMPLE_PICTURE));
        ChangePicCommand changePicCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getProfilePicture().value);
        String expectedMessage = String.format(ChangePicCommand.MESSAGE_CHANGEPIC_SUCCESS, editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(changePicCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ChangePicCommand changePicCommand = prepareCommand(outOfBoundIndex, SAMPLE_PICTURE);

        assertCommandFailure(changePicCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ChangePicCommand changePicCommand = prepareCommand(outOfBoundIndex, SAMPLE_PICTURE);

        assertCommandFailure(changePicCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final ChangePicCommand standardCommand = new ChangePicCommand(INDEX_FIRST_PERSON, DEFAULT_PICTURE);

        // same values -> returns true
        ChangePicCommand commandWithSameValues = new ChangePicCommand(INDEX_FIRST_PERSON, DEFAULT_PICTURE);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new ChangePicCommand(INDEX_SECOND_PERSON, SAMPLE_PICTURE)));

        // different groups -> returns false
        assertFalse(standardCommand.equals(new ChangePicCommand(INDEX_FIRST_PERSON, SAMPLE_PICTURE)));
    }

    /**
     * Returns an {@code ChangePicCommand} with parameters {@code index} and {@code group}.
     */
    private ChangePicCommand prepareCommand(Index index, String picturePath) {
        ChangePicCommand changePictureCommand = new ChangePicCommand(index, picturePath);
        changePictureCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return changePictureCommand;
    }
}
