package seedu.address.commons.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class GuiSettingsTest {

    private GuiSettings guiSettings;

    @Before
    public void setUp() {
        guiSettings = new GuiSettings((double) 500, (double) 500, 0, 0);
    }

    @Test
    public void assertEqualsSameInstanceReturnsTrue() {
        assertTrue(guiSettings.equals(guiSettings));
    }

    @Test
    public void assertEqualsNotGuiSettingReturnsFalse() {
        assertFalse(guiSettings.equals(new Object()));
    }
}
