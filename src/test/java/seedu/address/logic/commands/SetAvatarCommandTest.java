package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVATAR_IMAGE_URL_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVATAR_IMAGE_URL_TWO;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.FindHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Avatar;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.storage.util.ProcessImageUtil;
import seedu.address.testutil.PersonBuilder;

//@@author karrui
public class SetAvatarCommandTest {
    private Model model;
    private ArrayList<String> filesCreated = new ArrayList<>();

    @Before
    public void setup() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @After
    public void cleanup() {
        for (String path : filesCreated) {
            ProcessImageUtil.removeImageFromStorage(path);
        }
    }

    @Test
    public void execute_removeAvatar_success() throws Exception {
        ReadOnlyPerson firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withAvatar(VALID_AVATAR_IMAGE_URL_ONE).build();
        model.updatePerson(firstPerson, editedPerson);
        SetAvatarCommand setAvatarCommand = prepareCommand(INDEX_FIRST_PERSON, "");

        String expectedMessage = String.format(SetAvatarCommand.MESSAGE_REMOVE_AVATAR_SUCCESS, editedPerson);
        assertEquals(setAvatarCommand.execute().feedbackToUser, expectedMessage);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(setAvatarCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeAvatarWithHomepage_success() throws Exception {
        ReadOnlyPerson firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withAvatar(VALID_AVATAR_IMAGE_URL_ONE).withBooleanHomepageManuallySet(true).build();
        model.updatePerson(firstPerson, editedPerson);
        SetAvatarCommand setAvatarCommand = prepareCommand(INDEX_FIRST_PERSON, "");

        String expectedMessage = String.format(SetAvatarCommand.MESSAGE_REMOVE_AVATAR_SUCCESS, editedPerson);
        assertEquals(setAvatarCommand.execute().feedbackToUser, expectedMessage);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(setAvatarCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_setAvatar_success() throws Exception {
        ReadOnlyPerson firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withAvatar(VALID_AVATAR_IMAGE_URL_ONE).build();
        model.updatePerson(firstPerson, editedPerson);
        SetAvatarCommand setAvatarCommand = prepareCommand(INDEX_FIRST_PERSON, VALID_AVATAR_IMAGE_URL_TWO);

        String expectedMessage = String.format(SetAvatarCommand.MESSAGE_SET_AVATAR_SUCCESS, editedPerson);
        assertEquals(setAvatarCommand.execute().feedbackToUser, expectedMessage);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        assertCommandSuccess(setAvatarCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size()  +  1);
        SetAvatarCommand setAvatarCommand = prepareCommand(outOfBoundIndex, VALID_AVATAR_IMAGE_URL_ONE);

        assertCommandFailure(setAvatarCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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

        SetAvatarCommand setAvatarCommand = prepareCommand(outOfBoundIndex, VALID_AVATAR_IMAGE_URL_ONE);

        assertCommandFailure(setAvatarCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }


    @Test
    public void equals() throws IllegalValueException {
        final SetAvatarCommand standardCommand = new SetAvatarCommand(
                INDEX_FIRST_PERSON, new Avatar(VALID_AVATAR_IMAGE_URL_ONE));
        filesCreated.add(standardCommand.getAvatar().path);

        // same values -> returns true (checks if initialURL is equal)
        SetAvatarCommand commandWithSameValues = new SetAvatarCommand(
                INDEX_FIRST_PERSON, new Avatar(VALID_AVATAR_IMAGE_URL_ONE));
        filesCreated.add(commandWithSameValues.getAvatar().path);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        SetAvatarCommand commandWithDifferentIndex = new SetAvatarCommand(
                INDEX_SECOND_PERSON, new Avatar(VALID_AVATAR_IMAGE_URL_ONE));
        filesCreated.add(commandWithDifferentIndex.getAvatar().path);
        assertFalse(standardCommand.equals(commandWithDifferentIndex));

        // different descriptor  -> returns false
        SetAvatarCommand commandWithDifferentDescriptor = new SetAvatarCommand(
                INDEX_FIRST_PERSON, new Avatar(VALID_AVATAR_IMAGE_URL_TWO));
        filesCreated.add(commandWithDifferentDescriptor.getAvatar().path);
        assertFalse(standardCommand.equals(commandWithDifferentDescriptor));
    }

    /**
     +    * Returns a {@code SetAvatarCommand}.
     */
    private SetAvatarCommand prepareCommand(Index index, String path) throws IllegalValueException {
        SetAvatarCommand setAvatarCommand = new SetAvatarCommand(index, new Avatar(path));
        setAvatarCommand.setData(model, new CommandHistory(), new FindHistory(), new UndoRedoStack());
        filesCreated.add(setAvatarCommand.getAvatar().path);
        return setAvatarCommand;
    }
}
