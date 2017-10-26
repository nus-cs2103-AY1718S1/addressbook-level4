package seedu.room.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.room.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.room.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.room.testutil.TypicalPersons.getTypicalResidentBook;

import java.util.List;

import org.junit.Test;

import seedu.room.commons.core.Messages;
import seedu.room.commons.exceptions.IllegalValueException;
import seedu.room.logic.CommandHistory;
import seedu.room.logic.UndoRedoStack;
import seedu.room.model.Model;
import seedu.room.model.ModelManager;
import seedu.room.model.UserPrefs;
import seedu.room.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code SwaproomCommand}.
 */
public class HighlightCommandTest {

    private Model model = new ModelManager(getTypicalResidentBook(), new UserPrefs());

    @Test
    /**
     * Successful operation of highlight tag
     */
    public void execute_validTag_success() throws Exception {
        List<Tag> listOfTags = model.getResidentBook().getTagList();
        String highlightTag = listOfTags.get(0).getTagName();
        HighlightCommand highlightCommand = prepareCommand(highlightTag);

        String expectedMessage = HighlightCommand.MESSAGE_SUCCESS + highlightTag;

        ModelManager expectedModel = new ModelManager(model.getResidentBook(), new UserPrefs());
        expectedModel.updateHighlightStatus(highlightTag);

        assertCommandSuccess(highlightCommand, model, expectedMessage, expectedModel);
    }

    @Test
    /**
     * Non-existent Tag should throw TagNotFoundException
     */
    public void execute_invalidTag_throwsCommandException() throws Exception {
        String NONEXISTENT_TAG = getNonExistentTag();
        HighlightCommand highlightCommand = prepareCommand(NONEXISTENT_TAG);

        assertCommandFailure(highlightCommand, model, HighlightCommand.MESSAGE_TAG_NOT_FOUND + NONEXISTENT_TAG);
    }

    /**
     * Empty tag parameter should throw TagNotFoundException
     */
    @Test
    public void execute_noTag_throwsCommandException() {
        String EMPTY_TAG = "";
        HighlightCommand highlightCommand = prepareCommand(EMPTY_TAG);

        assertCommandFailure(highlightCommand, model, HighlightCommand.MESSAGE_TAG_NOT_FOUND + EMPTY_TAG);
    }

    /**
     * Get non-existent tag to test TagNotFoundException
     */
    public String getNonExistentTag() {
        String nonExistentTag = "No such tag exists";
        try {
            List<Tag> listOfTags = model.getResidentBook().getTagList();
            while (listOfTags.contains(new Tag(nonExistentTag))) {
                System.out.println("while");
                nonExistentTag += nonExistentTag;
            }
            return nonExistentTag;
        } catch (IllegalValueException e) {
            System.out.println("");
        }
        return nonExistentTag;
    }

    @Test
    public void equals() {
        String TEST_TAG = "test";
        String OTHER_TEST_TAG = "other test";
        HighlightCommand highlightCommandFirst = new HighlightCommand(TEST_TAG);
        HighlightCommand highlightCommandSecond = new HighlightCommand(OTHER_TEST_TAG);

        // same object -> returns true
        assertTrue(highlightCommandFirst.equals(highlightCommandFirst));

        // different argument -> returns false
        assertFalse(highlightCommandFirst.equals(highlightCommandSecond));

        // different values -> returns false
        assertFalse(highlightCommandFirst.equals(1));

        // null -> returns false
        assertFalse(highlightCommandFirst.equals(null));
    }

    /**
     * Returns a {@code SwaproomCommand} with the parameter {@code index}.
     */
    private HighlightCommand prepareCommand(String highlightTag) {
        HighlightCommand highlightCommand = new HighlightCommand(highlightTag);
        highlightCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return highlightCommand;
    }
}

