package seedu.address.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private String addressBookFilePath = "data/addressbook.xml";
    private String addressBookName = "MyAddressBook";
    private String password = "";

    public UserPrefs() {
        this.setGuiSettings(500, 500, 0, 0, "DarkTheme");
    }

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public void setGuiSettings(double width, double height, int x, int y, String theme) {
        guiSettings = new GuiSettings(width, height, x, y, theme);
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

    /**
     * Checks if password is correct
     * @param input
     * @return boolean
     */
    public boolean checkPassword(String input) {
        if (password.equals("")) {
            return true;
        }
        return password.equals(convertToMD5(input));
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

    /**
     * To change password
     * @param oldPwd old password
     * @param newPwd new password
     * @return success or fail
     */
    public boolean changePwd(String oldPwd, String newPwd) {
        if (checkPassword(oldPwd)) {
            password = convertToMD5(newPwd);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Convert String to MD5 Hash
     * @param input String to be converted
     * @return MD5 hash as a string
     */
    private String convertToMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            //convert the byte to hex format
            byte[] byteData = md.digest();
            StringBuilder buffer = new StringBuilder();
            for (int i = 0; i < byteData.length; i++) {
                buffer.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

}
