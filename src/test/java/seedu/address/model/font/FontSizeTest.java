//@@author ChenXiaoman
package seedu.address.model.font;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FONT_SIZE;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class FontSizeTest {

    @Test
    public void isValidFontSize() throws Exception {
        assertTrue(FontSize.isValidFontSize(FontSize.FONT_SIZE_L_LABEL));
        assertFalse(FontSize.isValidFontSize(INVALID_FONT_SIZE));
        assertFalse(FontSize.isValidFontSize(""));
    }

    @Test
    public void equals() throws IllegalValueException {

        FontSize size = new FontSize("+");
        FontSize sizeCopy = new FontSize("+");
        FontSize newSize = new FontSize("-");

        assertTrue(size.equals(size));
        assertFalse(size.equals(newSize));
        assertTrue(size.equals(sizeCopy));
    }

}
