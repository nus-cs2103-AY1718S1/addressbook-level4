package seedu.address.storage;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.PersonChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.events.ui.ProfilePhotoChangedEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyMeetingList;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.PersonCard;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    public static final String CACHE_DIR = "cache/";
    public static final String INCOMPLETE_DOWNLOAD_SUFFIX = ".part";
    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private static final String READ_FILE_MESSAGE = "Attempting to read to data file: ";
    private static final String WRITE_FILE_MESSAGE = "Attempting to write to data file: ";
    private AddressBookStorage addressBookStorage;
    private MeetingListStorage meetingListStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(AddressBookStorage addressBookStorage, MeetingListStorage meetingListStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.meetingListStorage = meetingListStorage;
        this.userPrefsStorage = userPrefsStorage;

        // Create cache folder in case it is not there
        File cache = new File(CACHE_DIR);
        if (cache.mkdir()) {
            logger.info("Creating new folder cache/");
        }
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


    // ================ AddressBook methods ==============================

    @Override
    public String getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(String filePath) throws DataConversionException, IOException {
        logger.fine(READ_FILE_MESSAGE + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException {
        logger.fine(WRITE_FILE_MESSAGE + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

    //@@author liuhang0213
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        addressBookStorage.backupAddressBook(addressBook);
    }

    public Optional<ReadOnlyAddressBook> restoreAddressBook() throws IOException, DataConversionException {
        return addressBookStorage.restoreAddressBook();
    }

    //@@author
    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    //@@author liuhang0213
    @Override
    @Subscribe
    public void handlePersonChangedEvent(PersonChangedEvent event) {
        if (event.type == PersonChangedEvent.ChangeType.ADD
                            || event.type == PersonChangedEvent.ChangeType.EDIT) {
            downloadProfilePhoto(event.person, event.prefs.getDefaultProfilePhoto());
        }
    }

    // ============ Meeting List methods ==================
    @Override
    public String getMeetingsFilePath() {
        return meetingListStorage.getMeetingsFilePath();
    }

    @Override
    public Optional<ReadOnlyMeetingList> readMeetingList() throws IOException, DataConversionException {
        return meetingListStorage.readMeetingList();
    }

    @Override
    public Optional<ReadOnlyMeetingList> readMeetingList(String filePath) throws DataConversionException, IOException {
        return meetingListStorage.readMeetingList(filePath);
    }

    @Override
    public void saveMeetingList(ReadOnlyMeetingList meetingList) throws IOException {
        meetingListStorage.saveMeetingList(meetingList);
    }

    @Override
    public void saveMeetingList(ReadOnlyMeetingList meetingList, String filePath) throws IOException {
        meetingListStorage.saveMeetingList(meetingList, filePath);
    }

    // ============= Cache & Download methods =======================

    @Override
    public void saveFileFromUrl(String urlString, String filename) throws IOException {
        try {
            URL url = new URL(urlString);
            InputStream in = new BufferedInputStream(url.openStream());
            String filePath = CACHE_DIR + filename;
            String tempFilePath = filePath + INCOMPLETE_DOWNLOAD_SUFFIX;

            File file = new File(tempFilePath);
            file.createNewFile();

            // Download the file
            OutputStream out = new BufferedOutputStream(new FileOutputStream(tempFilePath));

            for (int i; (i = in.read()) != -1; ) {
                out.write(i);
            }

            in.close();
            out.close();

            // Rename the file
            Path tempPath = Paths.get(tempFilePath);
            Path path = Paths.get(filePath);
            Files.move(tempPath, path, REPLACE_EXISTING);
        } catch (MalformedURLException e) {
            logger.warning(String.format("URL %1$s is not valid. File not downloaded.", urlString));
        }
    }

    /**
     * Returns a cache file in the local folder
     *
     * @param filename of file to be opened
     * @throws IOException if file with given filename is not found
     */
    public static FileInputStream loadCacheFile(String filename) throws IOException {
        try {
            return new FileInputStream(CACHE_DIR + filename);
        } catch (FileNotFoundException e) {
            logger.warning(String.format("Cache file %1$s not found.", filename));
            throw new IOException();
        }
    }

    // Gravatar
    @Override
    public void downloadProfilePhoto(ReadOnlyPerson person, String def) {
        try {
            String gravatarUrl = StringUtil.generateGravatarUrl(person.getEmail().value, def);
            String filename = String.format(PersonCard.PROFILE_PHOTO_FILENAME_FORMAT,
                    person.getInternalId().value);
            saveFileFromUrl(gravatarUrl, filename);
            logger.info("Downloaded " + gravatarUrl + " to " + filename);
            EventsCenter.getInstance().post(new ProfilePhotoChangedEvent(person));
        } catch (IOException e) {
            logger.warning(String.format("Gravatar not downloaded for %1$s.", person.getName()));
        }
    }
}
