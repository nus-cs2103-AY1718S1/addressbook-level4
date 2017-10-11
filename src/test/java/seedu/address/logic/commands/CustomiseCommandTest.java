package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_LARGE;
import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_NORMAL;
import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_SMALL;
import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_XLARGE;
import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_XSMALL;
import static seedu.address.logic.commands.CustomiseCommand.MESSAGE_SUCCESS;
import static seedu.address.model.font.FontSize.MESSAGE_FONT_SIZE_CONSTRAINTS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.font.FontSize;

public class CustomiseCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_fontSizeChangedToXsmall_success() throws Exception {
        CustomiseCommand customiseCommand = prepareCommand(new FontSize(FONT_SIZE_XSMALL));

        String expectedMessage = MESSAGE_SUCCESS + FONT_SIZE_XSMALL + ".";

        assertEquals(expectedMessage, customiseCommand.execute().feedbackToUser);
    }

    @Test
    public void execute_fontSizeChangedToSmall_success() throws Exception {
        CustomiseCommand customiseCommand = prepareCommand(new FontSize(FONT_SIZE_SMALL));

        String expectedMessage = MESSAGE_SUCCESS + FONT_SIZE_SMALL + ".";

        assertEquals(expectedMessage, customiseCommand.execute().feedbackToUser);
    }

    @Test
    public void execute_fontSizeChangedToNormal_success() throws Exception {
        CustomiseCommand customiseCommand = prepareCommand(new FontSize(FONT_SIZE_NORMAL));

        String expectedMessage = MESSAGE_SUCCESS + FONT_SIZE_NORMAL + ".";

        assertEquals(expectedMessage, customiseCommand.execute().feedbackToUser);
    }

    @Test
    public void execute_fontSizeChangedToLarge_success() throws Exception {
        CustomiseCommand customiseCommand = prepareCommand(new FontSize(FONT_SIZE_LARGE));

        String expectedMessage = MESSAGE_SUCCESS + FONT_SIZE_LARGE + ".";

        assertEquals(expectedMessage, customiseCommand.execute().feedbackToUser);
    }

    @Test
    public void execute_fontSizeChangedToXLarge_success() throws Exception {
        CustomiseCommand customiseCommand = prepareCommand(new FontSize(FONT_SIZE_XLARGE));

        String expectedMessage = MESSAGE_SUCCESS + FONT_SIZE_XLARGE + ".";

        assertEquals(expectedMessage, customiseCommand.execute().feedbackToUser);
    }

    @Test
    public void equals() {
        try {
            FontSize fontSizeXsmall = new FontSize(FONT_SIZE_XSMALL);
            FontSize fontSizeXlarge = new FontSize(FONT_SIZE_XLARGE);

            final CustomiseCommand customiseCommand = new CustomiseCommand(fontSizeXsmall);

            // same object -> returns true
            assertTrue(customiseCommand.equals(customiseCommand));

            // same values -> returns true
            CustomiseCommand commandWithSameValues = new CustomiseCommand(fontSizeXsmall);
            assertTrue(customiseCommand.equals(commandWithSameValues));

            // different types -> returns false
            assertFalse(customiseCommand.equals(1));

            // null -> returns false
            assertFalse(customiseCommand.equals(null));

            // different person -> returns false
            CustomiseCommand commandWithDifferentValues = new CustomiseCommand(fontSizeXlarge);
            assertFalse(customiseCommand.equals(commandWithDifferentValues));
        } catch (IllegalValueException ile) {
            throw new AssertionError(MESSAGE_FONT_SIZE_CONSTRAINTS);
        }
    }

    /**
     * Returns an {@code RemarkCommand} with parameters {@code index} and {@code remark}
     */
    private CustomiseCommand prepareCommand(FontSize fontSize) {
        CustomiseCommand customiseCommand = new CustomiseCommand(fontSize);
        customiseCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return customiseCommand;
    }

}
