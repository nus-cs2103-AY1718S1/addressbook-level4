package seedu.address.model;

import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private String addressBookFilePath = "data/addressbook.xml";
    private String remindersFilePath = "data/reminders.xml";
    private String addressBookPicturesPath = "pictures/default.png";
    private String addressBookName = "MyAddressBook";
    private String themeFilePath = "view/NightTheme.css";

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


    //@@author justinpoh
    public String getRemindersFilePath() {
        return remindersFilePath;
    }

    public void setRemindersFilePath(String remindersFilePath) {
        this.remindersFilePath = remindersFilePath;
    }
    //@@author

    public String getAddressBookFilePath() {
        return addressBookFilePath;
    }
    //@@author tshradheya

    public String getAddressBookPicturesPath() {
        return addressBookPicturesPath;
    }
    //@@author

    public void setAddressBookFilePath(String addressBookFilePath) {
        this.addressBookFilePath = addressBookFilePath;
    }
    //@@author tshradheya
    public void setAddressBookPicturesPath(String addressBookPicturesPath) {
        this.addressBookPicturesPath = addressBookPicturesPath;
    }
    //@@author

    public String getAddressBookName() {
        return addressBookName;
    }

    public void setAddressBookName(String addressBookName) {
        this.addressBookName = addressBookName;
    }

    //@@author chuaweiwen
    public String getThemeFilePath() {
        return themeFilePath;
    }

    public void setThemeFilePath(String themeFilePath) {
        this.themeFilePath = themeFilePath;
    }
    //@@author

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
                && Objects.equals(remindersFilePath, o.remindersFilePath)
                && Objects.equals(addressBookPicturesPath, o.addressBookPicturesPath)
                && Objects.equals(addressBookName, o.addressBookName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath, addressBookPicturesPath,
                            remindersFilePath, addressBookName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + addressBookFilePath);
        sb.append("\nLocal reminders file location : " + remindersFilePath);
        sb.append("\nAddressBook name : " + addressBookName);
        sb.append("\nTheme file location : " + themeFilePath);
        return sb.toString();
    }

}
