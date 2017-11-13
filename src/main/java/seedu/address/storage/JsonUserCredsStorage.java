//@@author zenghou
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
