package seedu.room.logic.commands;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
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
public class DeleteImageCommandTest {

    private Model model = new ModelManager(getTypicalResidentBook(), new UserPrefs());

    @Test
    public void execute_validPersonIndex_success() throws Exception {
        Person editedPerson = (Person) model.getFilteredPersonList().get(0);
        editedPerson.getPicture().setPictureUrl(Picture.PLACEHOLDER_IMAGE);
        DeleteImageCommand deleteImageCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteImageCommand.MESSAGE_RESET_IMAGE_SUCCESS,
                editedPerson.getName().toString());

        Model expectedModel = new ModelManager(new ResidentBook(model.getResidentBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        expectedModel.updateFilteredPersonListPicture(Model.PREDICATE_SHOW_ALL_PERSONS, editedPerson);

        assertCommandSuccess(deleteImageCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndex_failure() throws Exception {
        Person editedPerson = (Person) model.getFilteredPersonList().get(0);
        final Index invalidIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteImageCommand deleteImageCommand = prepareCommand(invalidIndex);

        String expectedMessage = String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                editedPerson.getName().toString());

        assertCommandFailure(deleteImageCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        Index index1 = Index.fromOneBased(1);
        Index index2 = Index.fromOneBased(2);
        DeleteImageCommand deleteImageIndex1 = new DeleteImageCommand(index1);
        DeleteImageCommand deleteImageIndex1Duplicate = new DeleteImageCommand(index1);
        DeleteImageCommand deleteImageIndex2 = new DeleteImageCommand(index2);

        // same object -> returns true
        assertTrue(deleteImageIndex1.equals(deleteImageIndex1));

        // different object same values -> returns true
        assertTrue(deleteImageIndex1.equals(deleteImageIndex1Duplicate));

        // different argument -> returns false
        assertFalse(deleteImageIndex1.equals(deleteImageIndex2));

        // different object type -> returns false
        assertFalse(deleteImageIndex1.equals(index1));

        // null -> returns false
        assertFalse(deleteImageIndex1.equals(null));
    }

    private DeleteImageCommand prepareCommand(Index index) {
        DeleteImageCommand deleteImageCommand = new DeleteImageCommand(index);
        deleteImageCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteImageCommand;
    }
}
