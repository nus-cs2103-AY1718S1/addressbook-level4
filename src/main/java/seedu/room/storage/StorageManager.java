package seedu.room.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.room.commons.core.ComponentManager;
import seedu.room.commons.core.LogsCenter;
import seedu.room.commons.events.model.ResidentBookChangedEvent;
import seedu.room.commons.events.storage.DataSavingExceptionEvent;
import seedu.room.commons.exceptions.DataConversionException;
import seedu.room.model.ReadOnlyResidentBook;
import seedu.room.model.UserPrefs;

/**
 * Manages storage of ResidentBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ResidentBookStorage residentBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(ResidentBookStorage residentBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.residentBookStorage = residentBookStorage;
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


    // ================ ResidentBook methods ==============================

    @Override
    public String getResidentBookFilePath() {
        return residentBookStorage.getResidentBookFilePath();
    }

    @Override
    public Optional<ReadOnlyResidentBook> readResidentBook() throws DataConversionException, IOException {
        return readResidentBook(residentBookStorage.getResidentBookFilePath());
    }

    @Override
    public Optional<ReadOnlyResidentBook> readResidentBook(String filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return residentBookStorage.readResidentBook(filePath);
    }

    @Override
    public void saveResidentBook(ReadOnlyResidentBook residentBook) throws IOException {
        saveResidentBook(residentBook, residentBookStorage.getResidentBookFilePath());
    }

    @Override
    public void saveResidentBook(ReadOnlyResidentBook residentBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        residentBookStorage.saveResidentBook(residentBook, filePath);
    }
    @Override
    @Subscribe
    public void handleResidentBookChangedEvent(ResidentBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveResidentBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
    @Override
    public void backupResidentBook(ReadOnlyResidentBook residentBook) throws IOException {
        saveResidentBook(residentBook, residentBookStorage.getResidentBookFilePath() + "-backup.xml");
    }

    public Optional<ReadOnlyResidentBook> readBackupResidentBook() throws DataConversionException, IOException {
        return readResidentBook(residentBookStorage.getResidentBookFilePath() + "-backup.xml");
    }
}
