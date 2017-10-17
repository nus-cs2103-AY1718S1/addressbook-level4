package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.RolodexChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyRolodex;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of Rolodex data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private RolodexStorage rolodexStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(RolodexStorage rolodexStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.rolodexStorage = rolodexStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public String getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ Rolodex methods ==============================

    @Override
    public String getRolodexFilePath() {
        return rolodexStorage.getRolodexFilePath();
    }

    @Override
    public Optional<ReadOnlyRolodex> readRolodex() throws DataConversionException, IOException {
        return readRolodex(rolodexStorage.getRolodexFilePath());
    }

    @Override
    public Optional<ReadOnlyRolodex> readRolodex(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return rolodexStorage.readRolodex(filePath);
    }

    @Override
    public void saveRolodex(ReadOnlyRolodex rolodex) throws IOException {
        saveRolodex(rolodex, rolodexStorage.getRolodexFilePath());
    }

    @Override
    public void saveRolodex(ReadOnlyRolodex rolodex, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        rolodexStorage.saveRolodex(rolodex, filePath);
    }


    @Override
    @Subscribe
    public void handleRolodexChangedEvent(RolodexChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveRolodex(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
