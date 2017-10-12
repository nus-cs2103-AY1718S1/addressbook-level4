package seedu.address.commons.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GuiSettingsTest {

    @Test
    public void testGuiSettingsEquals() {

        GuiSettings guiSettingOne = new GuiSettings();
        GuiSettings guiSettingTwo = new GuiSettings(13.6, 13.6, 0, 0);

        // Same object -> Returns True
        assertTrue(guiSettingOne.equals(guiSettingOne));

        // Same instance different values -> Returns False
        assertFalse(guiSettingOne.equals(guiSettingTwo));

        // Different type -> Returns False
        assertNotNull(guiSettingOne);
        assertFalse(guiSettingOne.equals(1));

    }

}
