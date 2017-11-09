package seedu.address.model;

import java.util.Objects;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.events.BaseEvent;

import seedu.address.commons.events.model.PrefDefaultProfilePhotoChangedEvent;
import seedu.address.commons.events.ui.ChangeThemeEvent;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private String addressBookFilePath = "data/addressbook.xml";
    private String meetingListFilePath = "data/meetinglist.xml";
    private String addressBookName = "MyAddressBook";
    private String theme = "Light";
    private String defaultProfilePhoto = "person";

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

    public String getAddressBookFilePath() {
        return addressBookFilePath;
    }

    public void setAddressBookFilePath(String addressBookFilePath) {
        this.addressBookFilePath = addressBookFilePath;
    }

    public String getMeetingListFilePath() {
        return meetingListFilePath;
    }

    public void setMeetingListFilePath(String meetingListFilePath) {
        this.meetingListFilePath = meetingListFilePath;
    }

    public String getAddressBookName() {
        return addressBookName;
    }

    public void setAddressBookName(String addressBookName) {
        this.addressBookName = addressBookName;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
        raise(new ChangeThemeEvent(theme));
    }

    public String getDefaultProfilePhoto() {
        return defaultProfilePhoto;
    }

    public void setDefaultProfilePhoto(String defaultProfilePhoto) {
        String oldValue = getDefaultProfilePhoto();
        this.defaultProfilePhoto = defaultProfilePhoto;
        raise(new PrefDefaultProfilePhotoChangedEvent(oldValue, defaultProfilePhoto));

    }

    private static void raise(BaseEvent e) {
        EventsCenter.getInstance().post(e);
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
                && Objects.equals(meetingListFilePath, o.meetingListFilePath)
                && Objects.equals(addressBookFilePath, o.addressBookFilePath)
                && Objects.equals(addressBookName, o.addressBookName)
                && Objects.equals(theme, o.theme)
                && Objects.equals(defaultProfilePhoto, o.defaultProfilePhoto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, meetingListFilePath, addressBookFilePath, addressBookName, theme,
                defaultProfilePhoto);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal meeting list file location : " + meetingListFilePath);
        sb.append("\nLocal address book file location : " + addressBookFilePath);
        sb.append("\nAddressBook name : " + addressBookName);
        sb.append("\nTheme : " + theme);
        sb.append("\nDefault Profile Photo : " + defaultProfilePhoto);
        return sb.toString();
    }

}
