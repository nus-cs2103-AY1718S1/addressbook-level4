//@@author zenghou
package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.user.UserCreds;

/**
 * Represents a storage for {@link seedu.address.model.user.UserCreds}.
 */
public interface UserCredsStorage {

    /**
     * Returns the file path of the UserCreds data file.
     */
    String getUserCredsFilePath();

    /**
     * Returns UserCreds data from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<UserCreds> readUserCreds() throws DataConversionException, IOException;

    /**
     * Saves the given {@link UserCreds} to the storage.
     * @param userCreds cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserCreds(UserCreds userCreds) throws IOException;

}
