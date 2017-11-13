package seedu.room.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.room.commons.core.ComponentManager;
import seedu.room.commons.core.LogsCenter;
import seedu.room.commons.events.model.EventBookChangedEvent;
import seedu.room.commons.events.model.ResidentBookChangedEvent;
import seedu.room.commons.events.storage.DataSavingExceptionEvent;
import seedu.room.commons.exceptions.DataConversionException;
import seedu.room.model.ReadOnlyEventBook;
import seedu.room.model.ReadOnlyResidentBook;
import seedu.room.model.UserPrefs;
import seedu.room.model.person.Picture;

/**
 * Manages storage of ResidentBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private static final String backup_location = "/backup.xml";
    private static final String backup = "_backup";
    private static final String xml = ".xml";
    private ResidentBookStorage residentBookStorage;
    private EventBookStorage eventBookStorage;
    private UserPrefsStorage userPrefsStorage;

    public StorageManager(ResidentBookStorage residentBookStorage, EventBookStorage eventBookStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.residentBookStorage = residentBookStorage;
        this.eventBookStorage = eventBookStorage;
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

    //@@author blackroxs
    @Override
    public void backupResidentBook(ReadOnlyResidentBook residentBook) throws IOException {
        saveResidentBook(residentBook, getDirAbsolutePath() + backup_location);
        backupImages();
    }

    public Optional<ReadOnlyResidentBook> readBackupResidentBook() throws DataConversionException, IOException {
        return readResidentBook(getDirAbsolutePath() + backup_location);
    }

    /**
     * Get the absolute parent path of residentbook.xml
     *
     * @return absolute path of the residentbook.xml directory
     */
    public String getDirAbsolutePath() {
        File file = new File(residentBookStorage.getResidentBookFilePath());
        String absPath = file.getParent();

        return absPath;
    }

    /**
     * Stores the contact images into a backup folder
     *
     * @throws IOException if unable to read or write in the folder
     */
    public void backupImages() throws IOException {
        String backupFolder = getDirAbsolutePath() + File.separator + Picture.FOLDER_NAME + backup;
        String originalFolder = getDirAbsolutePath() + File.separator + Picture.FOLDER_NAME;

        handleImageBackupFolder(backupFolder, originalFolder);
        handleImagesBackupFiles(backupFolder, originalFolder);
    }

    /**
     * Copies each file from source to destination backup folder
     *
     * @param backupFolder   cannot be null
     * @param originalFolder cannot be null
     * @throws IOException if there is any problem writing to the file
     */
    private void handleImagesBackupFiles(String backupFolder, String originalFolder) throws IOException {
        File source = new File(originalFolder);

        if (source.exists()) {
            File[] listOfImages = source.listFiles();

            for (int i = 0; i < listOfImages.length; i++) {
                File dest = new File(backupFolder + File.separator + listOfImages[i].getName());
                copy(listOfImages[i], dest);
            }
        }

    }

    /**
     * Creates the image backup folder if it does not exist
     *
     * @param backupFolder cannot be empty or null
     * @throws IOException if folder cannot be created due to read or write access
     */
    private void handleImageBackupFolder(String backupFolder, String originalFolder) throws IOException {
        File source = new File(originalFolder);
        if (source.exists()) {
            boolean backupExist = new File(backupFolder).exists();

            if (!backupExist) {
                boolean isSuccess = (new File(backupFolder)).mkdirs();
                if (!isSuccess) {
                    throw new IOException();
                }
            }
        }
    }

    /**
     * Copies the file from source to destination
     *
     * @param source cannot be null
     * @param dest   cannot be null
     * @throws IOException if there is any problem writing to the file
     */
    public static void copy(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buf)) > 0) {
                os.write(buf, 0, bytesRead);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    //@@author
    // ================ EventBook methods ==============================

    @Override
    public String getEventBookFilePath() {
        return eventBookStorage.getEventBookFilePath();
    }

    @Override
    public Optional<ReadOnlyEventBook> readEventBook() throws DataConversionException, IOException {
        return readEventBook(eventBookStorage.getEventBookFilePath());
    }

    @Override
    public Optional<ReadOnlyEventBook> readEventBook(String filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return eventBookStorage.readEventBook(filePath);
    }

    @Override
    public void saveEventBook(ReadOnlyEventBook residentBook) throws IOException {
        saveEventBook(residentBook, eventBookStorage.getEventBookFilePath());
    }

    @Override
    public void saveEventBook(ReadOnlyEventBook residentBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        eventBookStorage.saveEventBook(residentBook, filePath);
    }

    @Override
    @Subscribe
    public void handleEventBookChangedEvent(EventBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveEventBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    public void backupEventBook(ReadOnlyEventBook residentBook) throws IOException {
        saveEventBook(residentBook, eventBookStorage.getEventBookFilePath() + backup + xml);
    }

    public Optional<ReadOnlyEventBook> readBackupEventBook() throws DataConversionException, IOException {
        return readEventBook(eventBookStorage.getEventBookFilePath() + backup + xml);
    }
}
