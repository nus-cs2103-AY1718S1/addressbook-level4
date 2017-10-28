package seedu.address.logic.commands;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.ChangePicCommand.MESSAGE_ARGUMENTS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PICTURE_PATH;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.model.person.ProfilePicture.DEFAULT_PICTURE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ChangePicCommand.
 */
public class ChangePicCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() throws Exception {
        final String picturePath = "pic1.jpg";

        assertCommandFailure(prepareCommand(INDEX_FIRST_PERSON, picturePath), model,
                String.format(MESSAGE_ARGUMENTS, INDEX_FIRST_PERSON.getOneBased(), picturePath));
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
        assertFalse(standardCommand.equals(new ChangePicCommand(INDEX_SECOND_PERSON, VALID_PICTURE_PATH)));

        // different groups -> returns false
        assertFalse(standardCommand.equals(new ChangePicCommand(INDEX_FIRST_PERSON, VALID_PICTURE_PATH)));
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
