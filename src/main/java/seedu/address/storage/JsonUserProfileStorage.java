package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.person.UserProfile;

/**
 * A class to access UserPrefs stored in the hard disk as a json file
 */
public class JsonUserProfileStorage implements UserProfileStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonUserProfileStorage.class);

    private String filePath;

    public JsonUserProfileStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getUserProfileFilePath() {
        return filePath;
    }

    @Override
    public Optional<UserProfile> readUserProfile() throws DataConversionException, IOException {
        return readUserProfile(filePath);
    }

    /**
     * Similar to {@link #readUserProfile()}
     * @param userProfilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<UserProfile> readUserProfile(String userProfilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(userProfilePath, UserProfile.class);
    }

    @Override
    public void saveUserProfile(UserProfile userProfile) throws IOException {
        JsonUtil.saveJsonFile(userProfile, filePath);
    }

}
