package seedu.address.model;

import static seedu.address.commons.core.GuiSettings.DEFAULT_HEIGHT;
import static seedu.address.commons.core.GuiSettings.DEFAULT_WIDTH;

import java.io.File;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private String addressBookFilePath = "data" + File.separator + "addressbook.xml";
    private String addressBookName = "MyAddressBook";
    private String addressBookTheme = "/css/DarkTheme.css"; //Sets the default theme to DarkTheme

    public UserPrefs() {
        this.setGuiSettings(DEFAULT_WIDTH, DEFAULT_HEIGHT, 0, 0);
        this.setAddressBookTheme(addressBookTheme);
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

    public String getAddressBookFilePath() {
        return addressBookFilePath;
    }

    public void setAddressBookFilePath(String addressBookFilePath) {
        this.addressBookFilePath = addressBookFilePath;
    }

    public void setAddressBookName(String addressBookName) {
        this.addressBookName = addressBookName;
    }

    //@@author junyango

    public String getAddressBookTheme() {
        return addressBookTheme;
    }
    public void setAddressBookTheme(String theme) {
        this.addressBookTheme = theme;
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
                && Objects.equals(addressBookName, o.addressBookName)
                && Objects.equals(addressBookTheme, o.addressBookTheme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath, addressBookName, addressBookTheme);
    }

    //@@author

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : ").append(guiSettings.toString());
        sb.append("\nLocal data file location : ").append(addressBookFilePath);
        sb.append("\nAddressBook name : ").append(addressBookName);
        return sb.toString();
    }
}
