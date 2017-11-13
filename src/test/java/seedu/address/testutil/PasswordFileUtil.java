package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_SAMPLE;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import seedu.address.commons.exceptions.WrongPasswordException;
import seedu.address.commons.util.FileUtil;
import seedu.address.storage.SecurityManager;

/**
 *
 */
public class PasswordFileUtil {
    private static File testPassFile = new File("data/pass");

    /**
     * Removes the Password file if it exists.
     */
    public static void removePassFile() {
        try {
            if (FileUtil.isFileExists(testPassFile)) {
                removePass();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean passFileExist() {
        return FileUtil.isFileExists(testPassFile);
    }

    private static void removePass() throws IOException, NoSuchAlgorithmException, WrongPasswordException {
        SecurityManager.removePass(PASSWORD_SAMPLE);
    }
}
