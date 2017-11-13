package seedu.room.model;

import java.util.Objects;

import seedu.room.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private String residentBookFilePath = "data/residentbook.xml";
    private String residentBookName = "MyResidentBook";
    private String eventBookFilePath = "data/eventbook.xml";

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

    public String getResidentBookFilePath() {
        return residentBookFilePath;
    }

    public void setResidentBookFilePath(String residentBookFilePath) {
        this.residentBookFilePath = residentBookFilePath;
    }

    public String getResidentBookName() {
        return residentBookName;
    }

    public void setResidentBookName(String residentBookName) {
        this.residentBookName = residentBookName;
    }

    public String getEventBookFilePath() {
        return eventBookFilePath;
    }

    public void setEventBookFilePath(String eventBookFilePath) {
        this.eventBookFilePath = eventBookFilePath;
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
                && Objects.equals(residentBookFilePath, o.residentBookFilePath)
                && Objects.equals(residentBookName, o.residentBookName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, residentBookFilePath, residentBookName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + residentBookFilePath);
        sb.append("\nResidentBook name : " + residentBookName);
        return sb.toString();
    }

}
