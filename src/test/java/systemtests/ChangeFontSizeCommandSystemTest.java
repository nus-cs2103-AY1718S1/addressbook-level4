//@@author ChenXiaoman
package systemtests;

import org.junit.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.ChangeFontSizeCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.font.FontSize;

public class ChangeFontSizeCommandSystemTest extends AddressBookSystemTest {
    private Model modelXs;
    private Model modelS;
    private Model modelM;
    private Model modelL;
    private Model modelXl;

    @Test
    public void changeFontSize() throws Exception {
        prepareExpectedModels();

        /* Case: change the font size to L -> font size changed */
        String changeFontSizeCommand = ChangeFontSizeCommand.COMMAND_WORD + " l";
        String expectedMessage = ChangeFontSizeCommand.MESSAGE_SUCCESS + "l.";
        assertCommandSuccess(changeFontSizeCommand, modelL, expectedMessage);

        /* Case: undo changing font size  -> font size changes back to default M */
        String command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelM, expectedResultMessage);

        /* Case: undo changing font size  -> font size changes back to L again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelL, expectedResultMessage);

        /* Case: increase font size -> font size increases to XL */
        changeFontSizeCommand = ChangeFontSizeCommand.COMMAND_WORD + " +";
        expectedMessage = ChangeFontSizeCommand.MESSAGE_SUCCESS + "xl.";
        assertCommandSuccess(changeFontSizeCommand, modelXl, expectedMessage);

        /* Case: increase font size when the current font size is the largest one -> rejected */
        changeFontSizeCommand = ChangeFontSizeCommand.COMMAND_WORD + " +";
        assertCommandFailure(changeFontSizeCommand, FontSize.MESSAGE_FONT_SIZE_IS_LARGEST);

        /* Case: change the font size to S -> font size changed */
        changeFontSizeCommand = ChangeFontSizeCommand.COMMAND_WORD + " s";
        expectedMessage = ChangeFontSizeCommand.MESSAGE_SUCCESS + "s.";
        assertCommandSuccess(changeFontSizeCommand, modelS, expectedMessage);

        /* Case: decrease font size -> font size decreases to XS */
        changeFontSizeCommand = ChangeFontSizeCommand.COMMAND_WORD + " -";
        expectedMessage = ChangeFontSizeCommand.MESSAGE_SUCCESS + "xs.";
        assertCommandSuccess(changeFontSizeCommand, modelXs, expectedMessage);

        /* Case: decrease font size when the current font size is the smallest one -> rejected */
        changeFontSizeCommand = ChangeFontSizeCommand.COMMAND_WORD + " -";
        assertCommandFailure(changeFontSizeCommand, FontSize.MESSAGE_FONT_SIZE_IS_SMALLEST);

        /* Case: input font size name is invalid -> rejected */
        changeFontSizeCommand = ChangeFontSizeCommand.COMMAND_WORD + " " + "invalid_font_size_name";
        assertCommandFailure(changeFontSizeCommand, FontSize.MESSAGE_FONT_SIZE_CONSTRAINTS);

        /* Case: input font size name is null -> rejected */
        changeFontSizeCommand = ChangeFontSizeCommand.COMMAND_WORD + " ";
        assertCommandFailure(changeFontSizeCommand, FontSize.MESSAGE_FONT_SIZE_CONSTRAINTS);

    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ReadOnlyPerson)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Generates five different models with five font sizes
     */
    private void prepareExpectedModels() {

        // Generate five models with five font size symbols
        modelXs = prepareExpectedModelGivenFontSize("xs");
        modelS = prepareExpectedModelGivenFontSize("s");
        modelM = prepareExpectedModelGivenFontSize("m");
        modelL = prepareExpectedModelGivenFontSize("l");
        modelXl = prepareExpectedModelGivenFontSize("xl");

    }

    /**
     * Generate models with different font size
     * @param fontSize
     * @return new model with given font size
     */
    private Model prepareExpectedModelGivenFontSize(String fontSize) {
        assert (FontSize.isValidFontSize(fontSize));

        // Generate new user preference with given font size
        UserPrefs newUserPrefs = new UserPrefs();
        GuiSettings guiSettings = newUserPrefs.getGuiSettings();
        newUserPrefs.setGuiSettings(
                guiSettings.getWindowHeight(),
                guiSettings.getWindowWidth(),
                guiSettings.getWindowCoordinates().x,
                guiSettings.getWindowCoordinates().y,
                fontSize, guiSettings.getTheme());

        return new ModelManager(new AddressBook(getModel().getAddressBook()), newUserPrefs);
    }

}
