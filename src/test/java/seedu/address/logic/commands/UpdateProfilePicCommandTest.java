package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCAL_IMAGE_URL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEB_IMAGE_URL;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Files;
import java.nio.file.Paths;

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
import seedu.address.model.person.ProfilePic;
import seedu.address.model.person.ReadOnlyPerson;

//@@author soonwj
/**
 * Contains integration tests (interaction with the Model) and unit tests for UpdateProfilePicCommand.
 */
public class UpdateProfilePicCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_webUrlUnfilteredList_success() throws Exception {
        ReadOnlyPerson updatedProfilePicPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person updatedProfilePicturePerson = new Person(updatedProfilePicPerson);
        ProfilePic profilePic = new ProfilePic(VALID_WEB_IMAGE_URL);
        updatedProfilePicturePerson.setProfilePic(profilePic);
        UpdateProfilePicCommand updateProfilePicCommand = prepareCommand(INDEX_FIRST_PERSON, profilePic);

        String expectedMessage = String.format(UpdateProfilePicCommand.MESSAGE_UPDATE_PROFILE_PIC_SUCCESS,
                updatedProfilePicPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedProfilePicturePerson);

        assertCommandSuccess(updateProfilePicCommand, model, expectedMessage, expectedModel);

        Files.delete(Paths.get(urlToPath(model.getFilteredPersonList().get(0).getProfilePic().toString())));
    }

    @Test
    public void execute_localUrlUnfilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson updatedProfilePicPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person updatedProfilePicturePerson = new Person(updatedProfilePicPerson);
        ProfilePic profilePic = new ProfilePic(VALID_LOCAL_IMAGE_URL);
        updatedProfilePicturePerson.setProfilePic(profilePic);
        UpdateProfilePicCommand updateProfilePicCommand = prepareCommand(INDEX_FIRST_PERSON, profilePic);

        String expectedMessage = String.format(UpdateProfilePicCommand.MESSAGE_UPDATE_PROFILE_PIC_SUCCESS,
                updatedProfilePicturePerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedProfilePicturePerson);

        assertCommandSuccess(updateProfilePicCommand, model, expectedMessage, expectedModel);

        Files.delete(Paths.get(urlToPath(model.getFilteredPersonList().get(0).getProfilePic().toString())));
    }

    @Test
    public void execute_webUrlFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson updatedProfilePicPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person updatedProfilePicturePerson = new Person(updatedProfilePicPerson);
        ProfilePic profilePic = new ProfilePic(VALID_WEB_IMAGE_URL);
        updatedProfilePicturePerson.setProfilePic(profilePic);
        UpdateProfilePicCommand updateProfilePicCommand = prepareCommand(INDEX_FIRST_PERSON, profilePic);

        String expectedMessage = String.format(UpdateProfilePicCommand.MESSAGE_UPDATE_PROFILE_PIC_SUCCESS,
                updatedProfilePicPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedProfilePicturePerson);

        assertCommandSuccess(updateProfilePicCommand, model, expectedMessage, expectedModel);

        Files.delete(Paths.get(urlToPath(model.getFilteredPersonList().get(0).getProfilePic().toString())));
    }

    @Test
    public void execute_localUrlFilteredList_success() throws Exception {
        ReadOnlyPerson updatedProfilePicPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person updatedProfilePicturePerson = new Person(updatedProfilePicPerson);
        ProfilePic profilePic = new ProfilePic(VALID_LOCAL_IMAGE_URL);
        updatedProfilePicturePerson.setProfilePic(profilePic);
        UpdateProfilePicCommand updateProfilePicCommand = prepareCommand(INDEX_FIRST_PERSON, profilePic);

        String expectedMessage = String.format(UpdateProfilePicCommand.MESSAGE_UPDATE_PROFILE_PIC_SUCCESS,
                updatedProfilePicPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedProfilePicturePerson);

        assertCommandSuccess(updateProfilePicCommand, model, expectedMessage, expectedModel);

        Files.delete(Paths.get(urlToPath(model.getFilteredPersonList().get(0).getProfilePic().toString())));
    }

    @Test
    public void execute_filteredListInvalidIndex_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UpdateProfilePicCommand updateProfilePicCommand = prepareCommand(outOfBoundIndex, new ProfilePic());

        assertCommandFailure(updateProfilePicCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        final UpdateProfilePicCommand standardCommand = new UpdateProfilePicCommand(INDEX_FIRST_PERSON,
                new ProfilePic(VALID_LOCAL_IMAGE_URL));

        // same values -> returns true
        UpdateProfilePicCommand commandWithSameValues = new UpdateProfilePicCommand(INDEX_FIRST_PERSON,
                new ProfilePic(VALID_LOCAL_IMAGE_URL));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new UpdateProfilePicCommand(INDEX_SECOND_PERSON,
                new ProfilePic(VALID_LOCAL_IMAGE_URL))));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new UpdateProfilePicCommand(INDEX_FIRST_PERSON,
                new ProfilePic(VALID_WEB_IMAGE_URL))));
    }

    /**
     * Returns an {@code UpdateProfilePicCommand} with parameters {@code index} and {@code profilePic}
     */
    private UpdateProfilePicCommand prepareCommand(Index index, ProfilePic profilePic) {
        UpdateProfilePicCommand updateProfilePicCommand = new UpdateProfilePicCommand(index, profilePic);
        updateProfilePicCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return updateProfilePicCommand;
    }

    private String urlToPath(String url) {
        return url.substring(url.indexOf("ProfilePics"));
    }
}
