package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.events.model.RolodexChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyRolodex;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends RolodexStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getRolodexFilePath();

    @Override
    Optional<ReadOnlyRolodex> readRolodex() throws DataConversionException, IOException;

    @Override
    void saveRolodex(ReadOnlyRolodex rolodex) throws IOException;

    /**
     * Saves the current version of the Rolodex to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleRolodexChangedEvent(RolodexChangedEvent abce);
}
