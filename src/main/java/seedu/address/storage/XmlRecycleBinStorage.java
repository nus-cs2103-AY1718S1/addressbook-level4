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
//@@author Pengyuz
/**
 * A class to access AddressBook data stored as an xml file on the hard disk.
 */
public class XmlRecycleBinStorage implements RecycleBinStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlRecycleBinStorage.class);

    private String filePath;

    public XmlRecycleBinStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getRecycleBinFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readRecycleBin() throws DataConversionException, IOException {
        return readRecycleBin(filePath);
    }

    /**
     * Similar to {@link #readRecycleBin()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAddressBook> readRecycleBin(String filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("Recyclebin file "  + addressBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyAddressBook addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(addressBookOptional);
    }

    @Override
    public void saveRecycleBin(ReadOnlyAddressBook addressBook) throws IOException {
        saveRecycleBin(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveRecycleBin(ReadOnlyAddressBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveRecycleBin(ReadOnlyAddressBook addressBook, String filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableAddressBook(addressBook));
    }

}
