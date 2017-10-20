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
import seedu.address.model.ReadOnlyTaskBook;

/**
 * A class to access AddressBook data stored as an xml file on the hard disk.
 */
public class XmlTaskBookStorage implements TaskBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlAddressBookStorage.class);

    private String filePath;

    public XmlTaskBookStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getTaskBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTaskBook> readTaskBook() throws DataConversionException, IOException {
        return readTaskBook(filePath);
    }

    /**
     * Similar to {@link #readTaskBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTaskBook> readTaskBook(String filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        File taskBookFile = new File(filePath);

        if (!taskBookFile.exists()) {
            logger.info("TaskBook file "  + taskBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTaskBook taskBookOptional = XmlFileStorage.loadTaskDataFromSaveFile(new File(filePath));

        return Optional.of(taskBookOptional);
    }

    @Override
    public void saveTaskBook(ReadOnlyTaskBook taskBook) throws IOException {
        saveTaskBook(taskBook, filePath);
    }

    /**
     * Similar to {@link #saveTaskBook(ReadOnlyTaskBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTaskBook(ReadOnlyTaskBook taskBook, String filePath) throws IOException {
        requireNonNull(taskBook);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveTaskDataToFile(file, new XmlSerializableTaskBook(taskBook));
    }

    @Override
    public void backupTaskBook(ReadOnlyTaskBook taskBook) throws IOException {
        backupTaskBook(taskBook, filePath);
    }

    /**
     * Similar to {@link #saveTaskBook(ReadOnlyTaskBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void backupTaskBook(ReadOnlyTaskBook taskBook, String filePath) throws IOException {
        requireNonNull(taskBook);
        requireNonNull(filePath);
        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveTaskDataToFile(file, new XmlSerializableTaskBook(taskBook));
    }

}
