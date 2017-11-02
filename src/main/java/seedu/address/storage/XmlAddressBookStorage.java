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
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.util.SampleDataUtil;

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

    public String getBackupAddressBookFilePath() {
        return filePath + "-backup.xml";
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
    public Optional<ReadOnlyAddressBook> readBackupAddressBook() throws DataConversionException, IOException {
        return readAddressBook(filePath + "-backup.xml");
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

    //@@author khooroko
    /**
     * Creates a backup of the current data, if available.
     */
    @Override
    public void backupAddressBook() {
        try {
            Optional<ReadOnlyAddressBook> addressBookOptional;
            ReadOnlyAddressBook initialData;
            addressBookOptional = readAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("No backup will be made as data file does not exist");
            } else {
                initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
                saveAddressBook(initialData, getBackupAddressBookFilePath());
            }
        } catch (DataConversionException e) {
            logger.info("No backup will be made due to data file being in incorrect format");
        } catch (IOException e) {
            logger.info("No backup will be made due to problems while reading from file");
        }
    }

    /**
     * @return address book data, if available and readable, or backup address book data if it is not.
     * If both are unavailable, a sample address book is returned. If backup address book is in the wrong format
     * or is unreadable, an empty address book is returned.
     */
    @Override
    public ReadOnlyAddressBook getBestAvailableAddressBook() {
        ReadOnlyAddressBook initialData = new AddressBook();
        boolean isDataFileOkay = false;
        Optional<ReadOnlyAddressBook> addressBookOptional;

        try {
            addressBookOptional = readAddressBook();
            if (addressBookOptional.isPresent()) {
                isDataFileOkay = true;
            } else {
                logger.info("Data file not found");
            }
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format");
        } catch (IOException e) {
            logger.warning("Problem while reading from the file");
        }

        if (!isDataFileOkay) {
            try {
                addressBookOptional = readBackupAddressBook();
                if (addressBookOptional.isPresent()) {
                    logger.info("Backup data file found. Will be starting with a backup");
                } else {
                    logger.info("Backup data file not found. Will be starting with a sample AddressBook");
                }
                initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
            } catch (DataConversionException e) {
                logger.warning("Backup data file not in the correct format. Will be starting with an empty "
                        + "AddressBook");
            } catch (IOException e) {
                logger.warning("Problem while reading from the backup file. Will be starting with an empty "
                        + "AddressBook");
            }
        }
        return initialData;
    }

}
