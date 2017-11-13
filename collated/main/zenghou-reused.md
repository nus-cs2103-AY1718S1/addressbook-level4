# zenghou-reused
###### /java/seedu/address/storage/JsonUserCredsStorage.java
``` java
package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.user.UserCreds;

/**
 * A class to access UserCreds stored in the hard disk as a json file
 */
public class JsonUserCredsStorage implements UserCredsStorage {

    private String filePath;

    public JsonUserCredsStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getUserCredsFilePath() {
        return filePath;
    }

    @Override
    public Optional<UserCreds> readUserCreds() throws DataConversionException, IOException {
        return readUserCreds(filePath);
    }

    /**
     * Similar to {@link #readUserCreds()}
     * @param credsFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<UserCreds> readUserCreds(String credsFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(credsFilePath, UserCreds.class);
    }

    @Override
    public void saveUserCreds (UserCreds userCreds) throws IOException {
        JsonUtil.saveJsonFile(userCreds, filePath);
    }

}
```
###### /java/seedu/address/storage/UserCredsStorage.java
``` java
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
```
