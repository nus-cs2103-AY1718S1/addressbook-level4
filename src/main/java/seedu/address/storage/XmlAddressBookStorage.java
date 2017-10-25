package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * A class to access AddressBook data stored as an xml file on the hard disk.
 */
public class XmlAddressBookStorage implements AddressBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlAddressBookStorage.class);

    public static final String DEFAULT_MERGE_FILE_PATH = "./data/mergeAddressBook.xml";

    private String filePath;

    public XmlAddressBookStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getAddressBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(filePath);
    }

    /**
     * Similar to {@link #readAddressBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAddressBook> readAddressBook(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("AddressBook file "  + addressBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyAddressBook addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(addressBookOptional);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyAddressBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableAddressBook(addressBook));
    }

    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        //saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath() + "-backup.xml");
    }

    @Override
    public void mergeAddressBook(String newFilePath) throws DataConversionException, IOException {
        mergeAddressBook(newFilePath, filePath);
    }

    /**
     * Similar to {@link #mergeAddressBook(String)}
     * @param defaultFilePath location of the data. Cannot be null
     * @param newFilePath location of the data. Cannot be null
     */
    public void mergeAddressBook(String newFilePath, String defaultFilePath) throws DataConversionException, IOException {
        requireNonNull(defaultFilePath);
        requireNonNull(newFilePath);

        File defaultFile = new File(defaultFilePath);
        File newFile = new File(newFilePath);

        XmlSerializableAddressBook defaultFileData = XmlFileStorage.loadDataFromSaveFile(defaultFile);
        XmlSerializableAddressBook newFileData = XmlFileStorage.loadDataFromSaveFile(newFile);

        XmlFileStorage.mergeDataToFile(defaultFileData, newFileData);
    }

}
