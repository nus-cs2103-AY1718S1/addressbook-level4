//@@author viviantan95
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCAL_PHOTO_URL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEB_PHOTO_URL;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookPersons;

import org.junit.Before;
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
import seedu.address.model.person.Photo;
import seedu.address.model.person.ReadOnlyPerson;

public class AddPhotoCommandTest {
    private Model model;
    private Model expectedModel;
    private String expectedMessage;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBookPersons(), new UserPrefs());
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
    }

    @Test
    public void execute_localPhotoUrlFilteredList_success() throws Exception {
        ReadOnlyPerson updatedPhotoPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person addedPhotoPerson = new Person(updatedPhotoPerson);
        Photo photo = new Photo(VALID_LOCAL_PHOTO_URL);
        addedPhotoPerson.setPhoto(photo);
        AddPhotoCommand addPhotoCommand = prepareCommand(INDEX_FIRST_PERSON, photo);

        expectedMessage = String.format(AddPhotoCommand.MESSAGE_ADDPHOTO_SUCCESS, addedPhotoPerson);
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPhotoPerson);

        assertCommandSuccess(addPhotoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_webPhotoUrlFilteredList_success() throws Exception {
        ReadOnlyPerson updatedPhotoPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person addedPhotoPerson = new Person(updatedPhotoPerson);
        Photo photo = new Photo(VALID_WEB_PHOTO_URL);
        addedPhotoPerson.setPhoto(photo);
        AddPhotoCommand addPhotoCommand = prepareCommand(INDEX_FIRST_PERSON, photo);

        expectedMessage = String.format(AddPhotoCommand.MESSAGE_ADDPHOTO_SUCCESS, updatedPhotoPerson);
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), addedPhotoPerson);

        assertCommandSuccess(addPhotoCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_defaultPhotoFilteredList_success() throws Exception {
        ReadOnlyPerson updatedPhotoPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Photo photo = new Photo();
        AddPhotoCommand addPhotoCommand = prepareCommand(INDEX_FIRST_PERSON, photo);

        expectedMessage = String.format(AddPhotoCommand.MESSAGE_ADDPHOTO_SUCCESS, updatedPhotoPerson);
        updatedPhotoPerson.getPhoto();
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPhotoPerson);

        assertCommandSuccess(addPhotoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_defaultPhotoUrlUnfilteredList_success() throws Exception {
        showFirstPersonOnly(model);
        ReadOnlyPerson updatedPhotoPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Photo photo = new Photo();
        AddPhotoCommand addPhotoCommand = prepareCommand(INDEX_FIRST_PERSON, photo);

        expectedMessage = String.format(AddPhotoCommand.MESSAGE_ADDPHOTO_SUCCESS, updatedPhotoPerson);
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPhotoPerson);

        assertCommandSuccess(addPhotoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_failure () throws Exception {
        Index indexOutOfBound = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Photo photo = new Photo();
        AddPhotoCommand addPhotoCommand = prepareCommand(indexOutOfBound, photo);

        expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        assertCommandFailure(addPhotoCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        Photo photo = new Photo();
        final AddPhotoCommand command = new AddPhotoCommand(INDEX_FIRST_PERSON, photo);

        //same object -> returns true
        assertTrue(command.equals(command));

        //same values -> returns true
        AddPhotoCommand sameValueCommand = new AddPhotoCommand(INDEX_FIRST_PERSON, photo);
        assertTrue(command.equals(sameValueCommand));

        //different types -> return false
        assertFalse(command.equals(new ClearCommand()));

        // null -> returns false
        assertFalse(command.equals(null));

        //different person
        AddPhotoCommand diffPersonCommand = new AddPhotoCommand(INDEX_SECOND_PERSON, photo);
        assertFalse(command.equals(diffPersonCommand));

        //different index -> returns false
        assertFalse(command.equals(new AddPhotoCommand(INDEX_SECOND_PERSON, photo)));

        //different types -> returns false
        assertFalse(command.equals(new ClearCommand()));
    }

    //Returns an object of AddPhotoCommand with {@param index} and {@param photo}
    private AddPhotoCommand prepareCommand(Index index, Photo photo) {
        AddPhotoCommand addPhotoCommand = new AddPhotoCommand(index, photo);
        addPhotoCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addPhotoCommand;
    }

}
