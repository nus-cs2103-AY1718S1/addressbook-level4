package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.GuiSettings;

public class UserPrefsTest {

    private GuiSettings guiSettings;
    private String rolodexName;
    private UserPrefs userPrefs;

    @Before
    public void setUp() {
        // Set up default values to values from an empty UserPrefs object
        guiSettings = new GuiSettings((double) 800, (double) 600, 0, 0);
        String rolodexFilePath = "data/default.rldx";
        rolodexName = "MyRolodex";
        userPrefs = new UserPrefs();
        assertEquals(guiSettings, userPrefs.getGuiSettings());
        assertEquals(rolodexFilePath, userPrefs.getRolodexFilePath());
        assertEquals(rolodexName, userPrefs.getRolodexName());
    }

    @Test
    public void assertSetRolodexName() {
        // Check set to different name on setter call
        userPrefs.setRolodexName("DifferentRolodex");
        assertFalse(rolodexName.equals(userPrefs.getRolodexName()));
    }

    @Test
    public void assertGetRolodexName() {
        // Check name is default name on getter call
        assertEquals(userPrefs.getRolodexName(), rolodexName);
    }

    @Test
    public void assertEqualsSameObjectReturnsTrue() {
        assertTrue(userPrefs.equals(userPrefs));
    }

    @Test
    public void assertEqualsNotUserPrefsObjectReturnsFalse() {
        assertFalse(userPrefs.equals(new Object()));
    }

    @Test
    public void assertEqualsDifferentRolodexNameReturnsFalse() {
        userPrefs.setRolodexName("DifferentRolodex");
        assertFalse(userPrefs.equals(new UserPrefs()));
    }

    @Test
    public void assertEqualsDifferentRolodexFilePathReturnsFalse() {
        userPrefs.setRolodexFilePath("data/different.rldx");
        assertFalse(userPrefs.equals(new UserPrefs()));
    }

    @Test
    public void assertEqualsDifferentGuiSettingsReturnsFalse() {
        userPrefs.setGuiSettings(501, 500, 0, 0);
        assertFalse(userPrefs.equals(new UserPrefs()));
        assertFalse(guiSettings.equals(userPrefs.getGuiSettings()));

        userPrefs.setGuiSettings(500, 501, 0, 0);
        assertFalse(userPrefs.equals(new UserPrefs()));
        assertFalse(guiSettings.equals(userPrefs.getGuiSettings()));

        userPrefs.setGuiSettings(500, 500, 1, 0);
        assertFalse(userPrefs.equals(new UserPrefs()));
        assertFalse(guiSettings.equals(userPrefs.getGuiSettings()));

        userPrefs.setGuiSettings(500, 500, 0, 1);
        assertFalse(userPrefs.equals(new UserPrefs()));
        assertFalse(guiSettings.equals(userPrefs.getGuiSettings()));
    }

    @Test
    public void assertEqualsSameRolodexDifferentInstanceReturnsTrue() {
        UserPrefs newUserPrefs = new UserPrefs();
        assertTrue(userPrefs.equals(newUserPrefs));

        assertEquals(userPrefs.hashCode(), newUserPrefs.hashCode());
    }
}
