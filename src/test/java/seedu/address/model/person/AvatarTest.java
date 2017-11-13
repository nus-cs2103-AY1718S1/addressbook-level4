package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.person.avatar.Avatar;

//@@author teclu
public class AvatarTest {

    @Test
    public void isValidUrl() {
        // Invalid Avatar URLs
        assertFalse(Avatar.isValidUrl("")); // Empty string
        assertFalse(Avatar.isValidUrl("i.imgur.com/jNNT4LE.jpg")); // Missing http(s)://
        assertFalse(Avatar.isValidUrl("C:/Users/Bob/AddressBook/data/-1552827182.png")); // Missing file:/

        // Valid Avatar URLs
        assertTrue(Avatar.isValidUrl("https://i.imgur.com/jNNT4LE.jpg"));
        assertTrue(Avatar.isValidUrl("file:/C:/Users/Bob/AddressBook/data/-1552827182.png"));
    }

    @Test
    public void isSavedInData() {
        // Invalid Avatar Saved URLs
        assertFalse(Avatar.isSavedInData("")); // Empty string
        assertFalse(Avatar.isSavedInData("https://i.imgur.com/jNNT4LE.jpg")); // Online URL
        assertFalse(Avatar.isSavedInData("C:/Users/Bob/pictures/Alice.png")); // Local URL
        assertFalse(Avatar.isSavedInData("file:/C:/Users/Bob/pictures/Alice.png")); // Not in Data Folder

        // Valid Avatar Saved URLs
        assertTrue(Avatar.isSavedInData("file:/C:/Users/Bob/AddressBook/data/-1552827182.png"));
    }
}
