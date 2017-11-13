package seedu.address.model;

import static seedu.address.storage.PasswordSecurity.getSalt;
import static seedu.address.storage.PasswordSecurity.getSha512SecurePassword;

import java.util.Base64;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private String addressBookFilePath = "data/addressbook.xml";
    private String profilePicturesFolderPath = "images/profilePics/";
    private String addressBookName = "Codii";
    private String adminUsername = "loanShark97";
    private String adminPassword = "89a6a0f1d74b471f96018a84ab9b5562a39e0d09e3f48872a5fca8fb8b01c404a993e47ce384495196d"
            + "f6eba140af5e83eb98d20b7e3dbb361a94bfe8827a695";
    private String passwordSaltInString = "KeAnoJrBdpqc0AnhiZDSPw==";

    public UserPrefs() {
        setGuiSettings(1700, 980, 0, 0);
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

    public String getAddressBookName() {
        return addressBookName;
    }

    public void setAddressBookName(String addressBookName) {
        this.addressBookName = addressBookName;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    //@@author jaivigneshvenugopal
    /**
     * @return path of the profile pictures folder that resides in user's workspace
     */
    public String getProfilePicturesFolderPath() {
        return profilePicturesFolderPath;
    }

    /**
     * Sets the path of the profile pictures folder indicated by user
     * @param path points to the folder that resides in user's workspace
     */
    public void setProfilePicturesFolderPath(String path) {
        this.profilePicturesFolderPath = path;
    }

    //@@author

    public void setAdminPassword(String adminPassword) {
        byte[] salt = getSalt();
        String hashedPassword = getSha512SecurePassword(adminPassword, salt);

        this.adminPassword = hashedPassword;
        this.passwordSaltInString = Base64.getEncoder().encodeToString(salt);
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public byte[] getPasswordSalt() {
        return Base64.getDecoder().decode(passwordSaltInString);
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
        sb.append("\nAddressBook name : " + addressBookName);
        return sb.toString();
    }

}
