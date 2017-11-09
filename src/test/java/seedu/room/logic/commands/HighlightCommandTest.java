package seedu.room.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.room.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.room.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.room.testutil.TypicalPersons.getTypicalResidentBook;

import java.util.List;

import org.junit.Test;

import seedu.room.commons.exceptions.IllegalValueException;
import seedu.room.logic.CommandHistory;
import seedu.room.logic.UndoRedoStack;
import seedu.room.model.Model;
import seedu.room.model.ModelManager;
import seedu.room.model.UserPrefs;
import seedu.room.model.tag.Tag;

//@@author shitian007
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code SwaproomCommand}.
 */
public class HighlightCommandTest {

    private Model model = new ModelManager(getTypicalResidentBook(), new UserPrefs());

    @Test
    public void execute_validTag_success() throws Exception {
        List<Tag> listOfTags = model.getResidentBook().getTagList();
        String highlightTag = listOfTags.get(0).getTagName();
        HighlightCommand highlightCommand = prepareCommand(highlightTag);

        String expectedMessage = HighlightCommand.MESSAGE_PERSONS_HIGHLIGHTED_SUCCESS + highlightTag;

        ModelManager expectedModel = new ModelManager(model.getResidentBook(), new UserPrefs());
        expectedModel.updateHighlightStatus(highlightTag);

        assertCommandSuccess(highlightCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTag_throwsCommandException() throws Exception {
        String nonExistentTag = getNonExistentTag();
        HighlightCommand highlightCommand = prepareCommand(nonExistentTag);

        assertCommandFailure(highlightCommand, model, HighlightCommand.MESSAGE_TAG_NOT_FOUND + nonExistentTag);
    }

    @Test
    public void execute_noTag_throwsCommandException() {
        String emptyTag = "";
        HighlightCommand highlightCommand = prepareCommand(emptyTag);

        assertCommandFailure(highlightCommand, model, HighlightCommand.MESSAGE_TAG_NOT_FOUND + emptyTag);
    }

    @Test
    public void execute_removeHighlight_success() {
        String removeHighlight = "-";
        HighlightCommand highlightCommand = prepareCommand(removeHighlight);

        String expectedMessage = HighlightCommand.MESSAGE_PERSONS_HIGHLIGHTED_SUCCESS;

        List<Tag> listOfTags = model.getResidentBook().getTagList();
        String highlightTag = listOfTags.get(0).getTagName();
        ModelManager expectedModel = new ModelManager(model.getResidentBook(), new UserPrefs());
        expectedModel.updateHighlightStatus(highlightTag);
        expectedModel.resetHighlightStatus();

        assertCommandSuccess(highlightCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeHighlightNoneHighlighted_throwsNoneHighlightedException() {
        String removeHighlight = "-";
        HighlightCommand highlightCommand = prepareCommand(removeHighlight);

        String expectedMessage = HighlightCommand.MESSAGE_NONE_HIGHLIGHTED;

        assertCommandFailure(highlightCommand, model, expectedMessage);
    }

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
        String testTag = "test";
        String otherTestTag = "other test";
        HighlightCommand highlightCommandFirst = new HighlightCommand(testTag);
        HighlightCommand highlightCommandSecond = new HighlightCommand(otherTestTag);

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
     * Returns a {@code HighlightCommand} with the parameter {@code highlightTag}.
     */
    private HighlightCommand prepareCommand(String highlightTag) {
        HighlightCommand highlightCommand = new HighlightCommand(highlightTag);
        highlightCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return highlightCommand;
    }
}

