package seedu.address.model;

import java.util.HashMap;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.tag.Tag;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private String addressBookFilePath = "data/addressbook.xml";
    private String addressBookName = "MyAddressBook";
    private HashMap<String, String> colourMap;

    public UserPrefs() {
        this.setGuiSettings(500, 500, 10, 10);
        colourMap = new HashMap<>();
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

    public HashMap<String, String> getColourMap() {
        return colourMap;
    }

    public void setColourMap(HashMap<String, String> colourMap) {
        this.colourMap = colourMap;
    }

    /**
     * updates colormap with the new hashmap
     * @param newMap
     */
    public void updateColorMap(HashMap<Tag, String> newMap) {
        colourMap.clear();
        for (HashMap.Entry<Tag, String> newEntry : newMap.entrySet()) {
            colourMap.put(newEntry.getKey().tagName, newEntry.getValue());
        }
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
