package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Photo;

public class AddPhotoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Test 
    public void equals() {
        
    }
    @Test
    public void constructor_nullPhoto_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddPhotoCommand(null,null);
    }
    /*@Test
    public void execute_invalidPersonIndex_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still within bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DisplayPictureCommand displayPictureCommand = prepareCommand(outOfBoundIndex, DISPLAY_PICTURE_ALICE_PATH);

        assertCommandFailure(displayPictureCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }*/
    private AddPhotoCommand prepareCommand(Index index, String filepath) {
        AddPhotoCommand addPhotoCommand = new AddPhotoCommand(index, new Photo(filepath));
        addPhotoCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addPhotoCommand;
    }
}
