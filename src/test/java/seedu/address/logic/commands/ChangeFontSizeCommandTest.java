//@@author ChenXiaoman
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FONT_SIZE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.font.FontSize;

/**
 * Contains integration tests (interaction with the Model) for {@code ChangeFontSizeCommand}.
 */
public class ChangeFontSizeCommandTest {
    private UserPrefs userPrefs = new UserPrefs();
    private Model model = new ModelManager(getTypicalAddressBook(), userPrefs);
    private GuiSettings guiSettings = userPrefs.getGuiSettings();

    @Test
    public void executeChangeFontSizeCommandSuccess() throws Exception {
        ChangeFontSizeCommand changeFontSizeCommand = prepareCommand("xl");

        String expectedMessage = ChangeFontSizeCommand.MESSAGE_SUCCESS + "xl.";

        UserPrefs expectedUserPrefs = new UserPrefs();
        expectedUserPrefs.setGuiSettings(
                guiSettings.getWindowHeight(),
                guiSettings.getWindowWidth(),
                guiSettings.getWindowCoordinates().x,
                guiSettings.getWindowCoordinates().y,
                "xl", guiSettings.getTheme());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), expectedUserPrefs);

        assertCommandSuccess(changeFontSizeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeChangeFontSizeCommandInvalidFontSizeNameFailure() throws Exception {
        ChangeFontSizeCommand changeFontSizeCommand = prepareCommand(INVALID_FONT_SIZE);

        String expectedMessage = String.format(FontSize.MESSAGE_FONT_SIZE_CONSTRAINTS);

        assertCommandFailure(changeFontSizeCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        ChangeFontSizeCommand fontSizeFirstCommand = new ChangeFontSizeCommand(FontSize.FONT_SIZE_L_LABEL);
        ChangeFontSizeCommand fontSizeSecondCommand = new ChangeFontSizeCommand(FontSize.FONT_SIZE_XL_LABEL);

        // same object -> returns true
        assertTrue(fontSizeFirstCommand.equals(fontSizeFirstCommand));

        // same values -> returns true
        ChangeFontSizeCommand fontSizeFirstCommandCopy = new ChangeFontSizeCommand(FontSize.FONT_SIZE_L_LABEL);
        assertTrue(fontSizeFirstCommand.equals(fontSizeFirstCommandCopy));

        // different types -> returns false
        assertFalse(fontSizeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(fontSizeFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(fontSizeFirstCommand.equals(fontSizeSecondCommand));
    }

    /**
     * Returns an {@code ChangeFontSizeCommand} with parameters {@code theme}
     */
    private ChangeFontSizeCommand prepareCommand(String fontSize) {
        ChangeFontSizeCommand changeFontSizeCommand = new ChangeFontSizeCommand(fontSize);
        changeFontSizeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return changeFontSizeCommand;
    }

}
