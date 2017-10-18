package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.person.UserProfile;

/**
 * Represents a storage for {@link seedu.address.model.AddressBook}.
 */
public interface UserProfileStorage {

    /**
     * Returns the file path of the UserProfile data file.
     */
    String getUserProfileFilePath();

    /**
     * Returns UserProfile data from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<UserProfile> readUserProfile() throws DataConversionException, IOException;

    /**
     * Saves the given {@link seedu.address.model.person.UserProfile} to the storage.
     * @param userProfile cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserProfile(UserProfile userProfile) throws IOException;

}
