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

        String expectedMessage = String.format(HighlightCommand.MESSAGE_PERSONS_HIGHLIGHTED_SUCCESS, highlightTag);

        ModelManager expectedModel = new ModelManager(model.getResidentBook(), new UserPrefs());
        expectedModel.updateHighlightStatus(highlightTag);

        assertCommandSuccess(highlightCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTag_throwsCommandException() throws Exception {
        String nonExistentTag = getNonExistentTag();
        HighlightCommand highlightCommand = prepareCommand(nonExistentTag);

        String expectedMessage = String.format(HighlightCommand.MESSAGE_TAG_NOT_FOUND, nonExistentTag);
        assertCommandFailure(highlightCommand, model, expectedMessage);
    }

    @Test
    public void execute_noTag_throwsCommandException() {
        String emptyTag = "";
        HighlightCommand highlightCommand = prepareCommand(emptyTag);

        String expectedMessage = String.format(HighlightCommand.MESSAGE_TAG_NOT_FOUND, emptyTag);
        assertCommandFailure(highlightCommand, model, expectedMessage);
    }

    @Test
    public void execute_removeHighlight_success() {
        String removeHighlight = "-";
        HighlightCommand highlightCommand = prepareCommand(removeHighlight);

        String expectedMessage = HighlightCommand.MESSAGE_RESET_HIGHLIGHT;

        ModelManager expectedModel = new ModelManager(model.getResidentBook(), new UserPrefs());
        List<Tag> listOfTags = model.getResidentBook().getTagList();
        String highlightTag = listOfTags.get(0).getTagName();
        model.updateHighlightStatus(highlightTag);

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
        String tagRa = "RA";
        String tagExchange = "Exchange";
        HighlightCommand highlightCommandRa = new HighlightCommand(tagRa);
        HighlightCommand highlightCommandRaDuplicate = new HighlightCommand(tagRa);
        HighlightCommand highlightCommandExchange = new HighlightCommand(tagExchange);

        // same object -> returns true
        assertTrue(highlightCommandRa.equals(highlightCommandRa));

        // different object same values -> returns true
        assertTrue(highlightCommandRa.equals(highlightCommandRaDuplicate));

        // different argument -> returns false
        assertFalse(highlightCommandRa.equals(highlightCommandExchange));

        // different object type -> returns false
        assertFalse(highlightCommandRa.equals(tagRa));

        // null -> returns false
        assertFalse(highlightCommandRa.equals(null));
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

