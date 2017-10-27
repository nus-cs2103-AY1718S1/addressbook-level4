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
    private String username = "";

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
     *
     * @param input (Password typed in command line)
     * @return true if password is valid
     */
    public boolean checkPassword(String input) {
        return (("").equals(password)) || password.equals(hashBySha256(input));
    }

    /**
     *
     * @param input
     * @return true if username is valid
     */
    public boolean checkUsername(String input) {
        return username.equals(input);
    }

    /**
     *
     * @param user
     * @param oldPw
     * @param newPw
     * @return true if password is changed successfully
     */
    public boolean changePassword(String user, String oldPw, String newPw) {
        if (checkPassword(oldPw) && checkUsername(user)) {
            password = hashBySha256(newPw);
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param oldUsername
     * @param newUsername
     * @param password
     * @return true if username is changes successfully
     */
    public boolean changeUsername(String oldUsername, String newUsername, String password) {
        if (checkPassword(password) && checkUsername(oldUsername)) {
            username = newUsername;
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param input
     * @return a String that is hashed using SHA-256
     */
    public String hashBySha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(input.getBytes());

            byte[] mdBytes = md.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < mdBytes.length; i++) {
                hexString.append(Integer.toString((mdBytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            return "No Such Algorithm Exception";
        }
    }
}
