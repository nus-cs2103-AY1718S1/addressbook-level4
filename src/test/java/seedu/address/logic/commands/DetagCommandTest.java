package seedu.address.logic.commands;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.index.Index.fromOneBased;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.DetagCommand.MESSAGE_DETAG_PERSONS_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

/**
 * Contains integration tests (Interaction with the Model) and unit tests for {@code DetagCommand}.
 */
public class DetagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Index[] indices1 = {fromOneBased(1), fromOneBased(2), fromOneBased(3)};
    private final Index[] indices2 = {fromOneBased(4), fromOneBased(5)};

    @Test
    public void execute_ValidIndex_success() throws Exception {
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> tagList = personToDelete.getTags();
        Tag tag = tagList.iterator().next();

        DetagCommand detagCommand = prepareCommand(indices1, tag);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTag(indices1, tag);
        assertCommandSuccess(detagCommand, model, String.format(MESSAGE_DETAG_PERSONS_SUCCESS),
                expectedModel);
    }

    @Test
    public void execute_InvalidIndex_throwsCommandException() throws Exception {
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> tagList = personToDelete.getTags();
        Tag tag = tagList.iterator().next();

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Index[] indicesOutOfBound = {outOfBoundIndex};
        DetagCommand detagCommand = prepareCommand(indicesOutOfBound, tag);

        assertCommandFailure(detagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        ReadOnlyPerson personFirst = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ReadOnlyPerson personFourth = model.getFilteredPersonList().get(INDEX_FOURTH_PERSON.getZeroBased());
        Set<Tag> tagList1 = personFirst.getTags();
        Set<Tag> tagList2 = personFourth.getTags();
        Tag tag1 = tagList1.iterator().next();
        Tag tag2 = tagList2.iterator().next();

        DetagCommand detagFirstCommand = new DetagCommand(indices1, tag1);
        DetagCommand detagFourthCommand = new DetagCommand(indices2, tag2);

        // same object -> returns true
        assertTrue(detagFirstCommand.equals(detagFirstCommand));

        // same values -> returns true
        DetagCommand detagFirstCommandCopy = new DetagCommand(indices1, tag1);
        assertTrue(detagFirstCommand.equals(detagFirstCommandCopy));

        // different types -> returns false
        assertFalse(detagFirstCommand.equals(1));

        // null -> returns false
        assertFalse(detagFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(detagFirstCommand.equals(detagFourthCommand));
    }

    /**
     * Returns an {@code DetagCommand}.
     */
    private DetagCommand prepareCommand(Index[] indices, Tag tag) {
        DetagCommand detagCommand = new DetagCommand(indices, tag);
        detagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return detagCommand;
    }
}
