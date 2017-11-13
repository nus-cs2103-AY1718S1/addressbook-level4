package seedu.room.logic.commands;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static seedu.room.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.room.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.room.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.room.testutil.TypicalPersons.getTypicalResidentBook;

import org.junit.Test;

import seedu.room.commons.core.Messages;
import seedu.room.commons.core.index.Index;
import seedu.room.logic.CommandHistory;
import seedu.room.logic.UndoRedoStack;
import seedu.room.model.Model;
import seedu.room.model.ModelManager;
import seedu.room.model.ResidentBook;
import seedu.room.model.UserPrefs;
import seedu.room.model.person.Person;
import seedu.room.model.person.Picture;

//@@author shitian007
public class AddImageCommandTest {

    private Model model = new ModelManager(getTypicalResidentBook(), new UserPrefs());

    @Test
    public void execute_imageUrlValid_success() throws Exception {
        final String validUrl = Picture.PLACEHOLDER_IMAGE;
        Person editedPerson = (Person) model.getFilteredPersonList().get(0);
        editedPerson.getPicture().setPictureUrl(validUrl);
        AddImageCommand addImageCommand = prepareCommand(INDEX_FIRST_PERSON, validUrl);

        String expectedMessage = String.format(AddImageCommand.MESSAGE_ADD_IMAGE_SUCCESS,
                editedPerson.getName().toString());

        Model expectedModel = new ModelManager(new ResidentBook(model.getResidentBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        expectedModel.updateFilteredPersonListPicture(Model.PREDICATE_SHOW_ALL_PERSONS, editedPerson);

        assertCommandSuccess(addImageCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndex_failure() throws Exception {
        final String validUrl = "Invalid Image Url";
        final Index invalidIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Person editedPerson = (Person) model.getFilteredPersonList().get(0);
        AddImageCommand addImageCommand = prepareCommand(invalidIndex, validUrl);

        String expectedMessage = String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                editedPerson.getName().toString());

        assertCommandFailure(addImageCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidImageUrlValid_failure() throws Exception {
        final String validUrl = "Invalid Image Url";
        Person editedPerson = (Person) model.getFilteredPersonList().get(0);
        AddImageCommand addImageCommand = prepareCommand(INDEX_FIRST_PERSON, validUrl);

        String expectedMessage = String.format(Messages.MESSAGE_INVALID_IMAGE_URL,
                editedPerson.getName().toString());

        assertCommandFailure(addImageCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        Index index1 = Index.fromOneBased(1);
        Index index2 = Index.fromOneBased(2);
        String urlAlice = "/pictures/Alice.jpg";
        String urlBob = "/pictures/Bob.jpg";
        AddImageCommand addImageCommandAliceIndex1 = new AddImageCommand(index1, urlAlice);
        AddImageCommand addImageCommandAliceIndex1Duplicate = new AddImageCommand(index1, urlAlice);
        AddImageCommand addImageCommandAliceIndex2 = new AddImageCommand(index2, urlAlice);
        AddImageCommand addImageCommandBobIndex1 = new AddImageCommand(index1, urlBob);
        AddImageCommand addImageCommandBobIndex2 = new AddImageCommand(index2, urlBob);

        // same object -> returns true
        assertTrue(addImageCommandAliceIndex1.equals(addImageCommandAliceIndex1));

        // different object same arguments -> returns true
        assertTrue(addImageCommandAliceIndex1.equals(addImageCommandAliceIndex1Duplicate));

        // different indexes -> returns false
        assertFalse(addImageCommandAliceIndex1.equals(addImageCommandAliceIndex2));

        // different image url -> returns false
        assertFalse(addImageCommandAliceIndex1.equals(addImageCommandBobIndex1));

        // different image and index -> returns false
        assertFalse(addImageCommandAliceIndex1.equals(addImageCommandBobIndex2));

        // different object type -> returns false
        assertFalse(addImageCommandAliceIndex1.equals(index1));

        // null -> returns false
        assertFalse(addImageCommandAliceIndex1.equals(null));
    }

    /**
     * Returns an {@code AddImageCommand} with parameters {@code index} and {@code imageURL}
     */
    private AddImageCommand prepareCommand(Index index, String imageUrl) {
        AddImageCommand addImageCommand = new AddImageCommand(index, imageUrl);
        addImageCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addImageCommand;
    }
}
