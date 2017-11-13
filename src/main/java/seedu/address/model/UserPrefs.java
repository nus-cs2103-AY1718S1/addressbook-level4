package seedu.address.model;

import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private static final String DEFAULT_THEME = "/view/DarkTheme.css";

    private GuiSettings guiSettings;

    private String addressBookFilePath = "data/addressbook.xml";
    private String recycleBinFilePath = "data/recyclebin.xml";
    private String addressBookName = "MyOneBook";
    private String theme;

    public UserPrefs() {
        this.setGuiSettings(500, 500, 0, 0);
    }

    //@@author darrinloh
    public String getTheme() {
        return theme == null ? DEFAULT_THEME : theme;
    }

    public void updateLastUsedThemeSetting(String theme) {
        this.theme = theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
    //@@author

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }

    public String getAddressBookFilePath() {
        return addressBookFilePath;
    }

    public void setAddressBookFilePath(String addressBookFilePath) {
        this.addressBookFilePath = addressBookFilePath;
    }

    public String getAddressBookName() {
        return addressBookName;
    }

    public void setAddressBookName(String addressBookName) {
        this.addressBookName = addressBookName;
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
                && Objects.equals(addressBookFilePath, o.addressBookFilePath)
                && Objects.equals(recycleBinFilePath, o.recycleBinFilePath)
                && Objects.equals(addressBookName, o.addressBookName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath, addressBookName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + addressBookFilePath);
        sb.append("\nRecycle bin data file location : " + recycleBinFilePath);
        sb.append("\nAddressBook name : " + addressBookName);
        return sb.toString();
    }

}
