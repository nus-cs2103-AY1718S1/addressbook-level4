package seedu.address.model;

import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private String rolodexFilePath = "data/rolodex.xml";
    private String rolodexName = "MyRolodex";

    public UserPrefs() {
        this.setGuiSettings(500, 500, 0, 0);
    }

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }

    public String getRolodexFilePath() {
        return rolodexFilePath;
    }

    public void setRolodexFilePath(String rolodexFilePath) {
        this.rolodexFilePath = rolodexFilePath;
    }

    public String getRolodexName() {
        return rolodexName;
    }

    public void setRolodexName(String rolodexName) {
        this.rolodexName = rolodexName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return Objects.equals(guiSettings, o.guiSettings)
                && Objects.equals(rolodexFilePath, o.rolodexFilePath)
                && Objects.equals(rolodexName, o.rolodexName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, rolodexFilePath, rolodexName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + rolodexFilePath);
        sb.append("\nRolodex name : " + rolodexName);
        return sb.toString();
    }

}
