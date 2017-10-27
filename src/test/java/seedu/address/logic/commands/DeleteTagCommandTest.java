package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalParcels.getTypicalAddressBook;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.parcel.ReadOnlyParcel;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagNotFoundException;


/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class DeleteTagCommandTest {

    private Model model;
    private Model expectedModel;
    private DeleteTagCommand deleteTagCommand;
    private Tag tagToDelete;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        ObservableList<ReadOnlyParcel> parcelsToManipulate = model.getFilteredParcelList();

        Iterator it = parcelsToManipulate.iterator();
        Boolean noCandidate = true;

        while (it.hasNext() && noCandidate) {
            ReadOnlyParcel parcelToManipulate = (ReadOnlyParcel) it.next();
            if (!parcelToManipulate.getTags().isEmpty()) {
                tagToDelete = (Tag) parcelToManipulate.getTags().toArray()[0];
                noCandidate = false;
            }
        }
    }

    @Test
    public void execute_deleteTag_success() throws Exception {

        deleteTagCommand = prepareCommand(tagToDelete);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, tagToDelete);

        try {
            expectedModel.deleteTag(tagToDelete);
        } catch (TagNotFoundException e) {
            e.printStackTrace();
        }

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteTag_tagNotFoundFailure() throws Exception {

        try {
            tagToDelete = new Tag("testFailureTag");
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        deleteTagCommand = prepareCommand(tagToDelete);

        String exceptionMessage = String.format(DeleteTagCommand.MESSAGE_INVALID_DELETE_TAG_NOT_FOUND, tagToDelete);

        assertCommandFailure(deleteTagCommand, model, exceptionMessage);
    }

    @Test
    public void execute_deleteTag_tagNotValid() throws Exception {

        String exceptionMessage = "";

        try {
            tagToDelete = new Tag("!@#$%^&*()");
        } catch (IllegalValueException e) {
            exceptionMessage = e.getMessage();
        }

        String expectedMessage = Tag.MESSAGE_TAG_CONSTRAINTS;

        assertEquals(expectedMessage, exceptionMessage);
    }

    private DeleteTagCommand prepareCommand(Tag target) {
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(target);
        deleteTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteTagCommand;
    }

    @Test
    public void equals() {
        Tag urgent = null;
        Tag fragile = null;
        try {
            urgent = new Tag("urgent");
            fragile = new Tag("fragile");
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        DeleteTagCommand deleteUrgentTagCommand = new DeleteTagCommand(urgent);
        DeleteTagCommand deleteFragileTagCommand = new DeleteTagCommand(fragile);

        // same object -> returns true
        assertTrue(deleteUrgentTagCommand.equals(deleteUrgentTagCommand));

        // same values -> returns true
        DeleteTagCommand deleteUrgentTagCommandCopy = new DeleteTagCommand(urgent);
        assertTrue(deleteUrgentTagCommand.equals(deleteUrgentTagCommandCopy));

        // different types -> returns false
        assertFalse(deleteUrgentTagCommand.equals(1));

        // null -> returns false
        assertFalse(deleteUrgentTagCommand.equals(null));

        // different parcel -> returns false
        assertFalse(deleteUrgentTagCommand.equals(deleteFragileTagCommand));
    }
}
