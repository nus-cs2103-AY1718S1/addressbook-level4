package seedu.address.commons.util.encryption;

import seedu.address.logic.currentuser.CurrentUserDetails;

/**
 * auto-save current address book to the encrypted file from mutating commands
 */
public class SaveToEncryptedFile {

    /**
     * auto-save current address book to the encrypted file from mutating commands
     */
    public static void save() {
        CurrentUserDetails curUser = new CurrentUserDetails();
        String fileName = curUser.getUserIdHex();
        fileName = fileName.substring(0, Math.min(fileName.length(), 10));
        String passPhrase = curUser.getSaltText() + curUser.getPasswordText();
        try {
            FileEncryptor.encryptFile(fileName, passPhrase, false);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
