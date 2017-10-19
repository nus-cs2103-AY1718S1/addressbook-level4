package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.index.Index.fromOneBased;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.DetagCommand.MESSAGE_DETAG_PERSONS_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (Interaction with the Model) and unit tests for {@code DetagCommand}.
 */
public class DetagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Index[] indices1 = {fromOneBased(1), fromOneBased(2)};
    private final Index[] indices2 = {fromOneBased(2), fromOneBased(5)};

    @Test
    public void executeValidIndexSuccess() throws Exception {
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> tagList = personToDelete.getTags();
        Tag tag = tagList.iterator().next();

        DetagCommand detagCommand = prepareCommand(indices1, tag);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        for (Index index: indices1) {
            ReadOnlyPerson person = model.getFilteredPersonList().get(index.getZeroBased());
            expectedModel.deleteTag(person, tag);
        }
        assertCommandSuccess(detagCommand, model, String.format(MESSAGE_DETAG_PERSONS_SUCCESS, tag),
                expectedModel);
    }

    @Test
    public void executeInvalidIndexThrowsCommandException() throws Exception {
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> tagList = personToDelete.getTags();
        Tag tag = tagList.iterator().next();

        Index outOfBoundIndex = fromOneBased(model.getFilteredPersonList().size() + 1);
        Index[] indicesOutOfBound = {outOfBoundIndex};
        DetagCommand detagCommand = prepareCommand(indicesOutOfBound, tag);

        assertCommandFailure(detagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        ReadOnlyPerson personFirst = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> tagList1 = personFirst.getTags();
        Tag tag1 = tagList1.iterator().next();
        Tag tag2 = setupTestTag(tag1);

        assertFalse(tag1.equals(tag2));

        DetagCommand detagFirstCommand = new DetagCommand(indices1, tag1);
        DetagCommand detagSecondCommand = new DetagCommand(indices2, tag2);

        // same object -> returns true
        assertTrue(detagFirstCommand.equals(detagFirstCommand));

        // same values -> returns true
        DetagCommand detagFirstCommandCopy = new DetagCommand(indices1, tag1);
        assertTrue(detagFirstCommand.equals(detagFirstCommandCopy));

        // different types -> returns false
        assertFalse(detagFirstCommand.equals(1));

        // different person -> returns false
        assertFalse(detagFirstCommand.equals(detagSecondCommand));
    }

    /**
     * Return a different tag of a person other than First Person in the model
     * @param tagBase
     * @return
     */
    private Tag setupTestTag(Tag tagBase) {
        for (ReadOnlyPerson personNext : model.getFilteredPersonList()) {
            Set<Tag> tagList = personNext.getTags();
            Tag tag = tagList.iterator().next();
            if (!tag.equals(tagBase)) {
                return tag;
            }
        }
        return null;
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
