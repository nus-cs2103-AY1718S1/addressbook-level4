package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Photo;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.storage.PhotoStorage;
import seedu.address.testutil.PersonBuilder;
//@@author wishingmaid
public class AddPhotoCommandTest {
    private static final String INVALID_FILETYPE = "docs/AboutUs.adoc";
    private static final String VALID_FILEPATH = "src/main/resources/images/noPhoto.png";
    private static final String VALID_ALT_FILEPATH = "src/main/resources/images/fail.png";
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void equals() {
        AddPhotoCommand standardCommand = new AddPhotoCommand(INDEX_FIRST_PERSON, new Photo(""));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different index -> returns false
        assertFalse(standardCommand.equals(new AddPhotoCommand(INDEX_SECOND_PERSON, new Photo(VALID_ALT_FILEPATH))));
    }
    @Test
    public void execute_addPhoto_success() throws PersonNotFoundException, DuplicatePersonException, CommandException {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withPhoto(VALID_FILEPATH).build();
        AddPhotoCommand addPhotoCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getPhoto().getFilePath());
        String expectedMessage = String.format(AddPhotoCommand.MESSAGE_ADD_PHOTO_SUCCESS, editedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        assertCommandSuccess(addPhotoCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_deletePhoto_success() throws Exception {
        Person editedPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        editedPerson.setPhoto(new Photo(""));
        AddPhotoCommand addPhotoCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getPhoto().getFilePath()
                .toString());
        String expectedMessage = String.format(AddPhotoCommand.MESSAGE_DELETE_PHOTO_SUCCESS, editedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        assertCommandSuccess(addPhotoCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_addPhoto_failure() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withPhoto(INVALID_FILETYPE).build();
        try {
            AddPhotoCommand addPhotoCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getPhoto().getFilePath());
        } catch (CommandException e) {
            assertEquals(e.getMessage(), PhotoStorage.WRITE_FAILURE_MESSAGE);
        }
    }
    @Test
    public void constructor_nullPhoto_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddPhotoCommand(null, null);
    }
    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddPhotoCommand addPhotoCommand = prepareCommand(outOfBoundIndex, VALID_FILEPATH);
        assertCommandFailure(addPhotoCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        AddPhotoCommand addPhotoCommand = prepareCommand(outOfBoundIndex, VALID_FILEPATH);
        assertCommandFailure(addPhotoCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    private AddPhotoCommand prepareCommand(Index index, String filepath) throws CommandException {
        AddPhotoCommand addPhotoCommand = new AddPhotoCommand(index, new Photo(filepath));
        addPhotoCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addPhotoCommand;
    }
}
