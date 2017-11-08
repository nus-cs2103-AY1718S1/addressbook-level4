package seedu.address.model.font;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FONT_SIZE;

import org.junit.Test;

public class FontSizeTest {
    //@@author ChenXiaoman
    @Test
    public void isValidFontSize() throws Exception {
        assertTrue(FontSize.isValidFontSize(FontSize.FONT_SIZE_L_LABEL));
        assertFalse(FontSize.isValidFontSize(INVALID_FONT_SIZE));
        assertFalse(FontSize.isValidFontSize(""));
    }

}
