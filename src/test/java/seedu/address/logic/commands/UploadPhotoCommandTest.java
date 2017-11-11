package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Photo;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

//@@author LuLechuan
public class UploadPhotoCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_personAcceptedByModel_updatePhotoSuccessful() throws Exception {
        Person updatedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).withPhoto(
                System.getProperty("user.dir") + "/docs/images/wolf.jpg").build();

        Photo photo = new Photo(System.getProperty("user.dir") + "/docs/images/wolf.jpg");
        UploadPhotoCommand uploadPhotoCommand = prepareCommand(INDEX_FIRST_PERSON, photo);

        String expectedMessage = String.format(UploadPhotoCommand.MESSAGE_UPDATE_PERSON_PHOTO_SUCCESS, updatedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(uploadPhotoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_personAcceptedByModel_deleteIconPhoto() throws IllegalValueException, PersonNotFoundException {
        Person updatedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).withPhoto(null).build();

        Photo photo = new Photo(null);
        UploadPhotoCommand uploadPhotoCommand = prepareCommand(INDEX_FIRST_PERSON, photo);

        String expectedMessage = String.format(UploadPhotoCommand.MESSAGE_UPDATE_PERSON_PHOTO_SUCCESS, updatedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(uploadPhotoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws IllegalValueException {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Photo photo = new Photo(System.getProperty("user.dir") + "/docs/images/wolf.jpg");
        UploadPhotoCommand uploadPhotoCommand = prepareCommand(outOfBoundIndex, photo);

        assertCommandFailure(uploadPhotoCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Returns a {@code CustomCommand} with the parameters {@code index + CustomFieldName + CustomFieldValue}.
     */
    private UploadPhotoCommand prepareCommand(Index index, Photo photo) {
        UploadPhotoCommand command = new UploadPhotoCommand(index, photo);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
