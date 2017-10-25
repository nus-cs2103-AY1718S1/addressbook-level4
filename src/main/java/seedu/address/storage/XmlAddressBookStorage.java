package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.InvalidFileExtensionException;
import seedu.address.commons.exceptions.InvalidFilePathException;
import seedu.address.commons.exceptions.InvalidNameException;
import seedu.address.commons.exceptions.InvalidNameSeparatorException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * A class to access AddressBook data stored as an xml file on the hard disk.
 */
public class XmlAddressBookStorage implements AddressBookStorage {
    private static final Logger logger = LogsCenter.getLogger(XmlAddressBookStorage.class);
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

        XmlSerializableAddressBook addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));
        addressBookOptional.initializePropertyManager();

        return Optional.of(addressBookOptional);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException, InvalidFilePathException {
        saveAddressBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyAddressBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException,
            InvalidFilePathException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        validateFilePath(filePath);
        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableAddressBook(addressBook));
    }

    /**
     * Makes a local backup of the addressBook storage file by appending the filename with {@code backup}.
     */
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException, InvalidFilePathException {
        String newFilePath = removeFileExtension(filePath) + "-backup.xml";
        saveAddressBook(addressBook, newFilePath);
    }

    /**
     * Helper method to check the validity of an address book data file path
     */
    private void validateFilePath(String filePath) throws InvalidFilePathException {
        if (!FileUtil.isValidXmlFile(filePath)) {
            throw new InvalidFileExtensionException();
        } else if (FileUtil.hasInvalidNonExistentNames(filePath)) {
            throw new InvalidNameException();
        } else if (FileUtil.hasInvalidNameSeparators(filePath)) {
            throw new InvalidNameSeparatorException();
        } else if (FileUtil.hasConsecutiveNameSeparators(filePath)
                || FileUtil.hasConsecutiveExtensionSeparators(filePath)) {
            throw new InvalidFilePathException();
        }
    }
}
