package seedu.room.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.room.commons.core.ComponentManager;
import seedu.room.commons.core.LogsCenter;
import seedu.room.commons.events.model.RoomBookChangedEvent;
import seedu.room.commons.events.storage.DataSavingExceptionEvent;
import seedu.room.commons.exceptions.DataConversionException;
import seedu.room.model.ReadOnlyRoomBook;
import seedu.room.model.UserPrefs;

/**
 * Manages storage of RoomBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private RoomBookStorage roomBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(RoomBookStorage roomBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.roomBookStorage = roomBookStorage;
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


    // ================ RoomBook methods ==============================

    @Override
    public String getRoomBookFilePath() {
        return roomBookStorage.getRoomBookFilePath();
    }

    @Override
    public Optional<ReadOnlyRoomBook> readRoomBook() throws DataConversionException, IOException {
        return readRoomBook(roomBookStorage.getRoomBookFilePath());
    }

    @Override
    public Optional<ReadOnlyRoomBook> readRoomBook(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return roomBookStorage.readRoomBook(filePath);
    }

    @Override
    public void saveRoomBook(ReadOnlyRoomBook roomBook) throws IOException {
        saveRoomBook(roomBook, roomBookStorage.getRoomBookFilePath());
    }

    @Override
    public void saveRoomBook(ReadOnlyRoomBook roomBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        roomBookStorage.saveRoomBook(roomBook, filePath);
    }
    @Override
    @Subscribe
    public void handleRoomBookChangedEvent(RoomBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveRoomBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
    @Override
    public void backupRoomBook(ReadOnlyRoomBook roomBook) throws IOException {
        saveRoomBook(roomBook, roomBookStorage.getRoomBookFilePath() + "-backup.xml");
    }

    public Optional<ReadOnlyRoomBook> readBackupRoomBook() throws DataConversionException, IOException {
        return readRoomBook(roomBookStorage.getRoomBookFilePath() + "-backup.xml");
    }
}
