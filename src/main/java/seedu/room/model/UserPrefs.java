package seedu.room.model;

import java.util.Objects;

import seedu.room.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private String roomBookFilePath = "data/roombook.xml";
    private String roomBookName = "MyRoomBook";

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

    public String getRoomBookFilePath() {
        return roomBookFilePath;
    }

    public void setRoomBookFilePath(String roomBookFilePath) {
        this.roomBookFilePath = roomBookFilePath;
    }

    public String getRoomBookName() {
        return roomBookName;
    }

    public void setRoomBookName(String roomBookName) {
        this.roomBookName = roomBookName;
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
                && Objects.equals(roomBookFilePath, o.roomBookFilePath)
                && Objects.equals(roomBookName, o.roomBookName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, roomBookFilePath, roomBookName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + roomBookFilePath);
        sb.append("\nRoomBook name : " + roomBookName);
        return sb.toString();
    }

}
