package seedu.room.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.room.commons.core.LogsCenter;
import seedu.room.commons.exceptions.DataConversionException;
import seedu.room.commons.util.FileUtil;
import seedu.room.model.ReadOnlyResidentBook;

/**
 * A class to access ResidentBook data stored as an xml file on the hard disk.
 */
public class XmlResidentBookStorage implements ResidentBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlResidentBookStorage.class);

    private String filePath;

    public XmlResidentBookStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getResidentBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyResidentBook> readResidentBook() throws DataConversionException, IOException {
        return readResidentBook(filePath);
    }

    /**
     * Similar to {@link #readResidentBook()}
     *
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyResidentBook> readResidentBook(String filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        File residentBookFile = new File(filePath);

        if (!residentBookFile.exists()) {
            logger.info("ResidentBook file " + residentBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyResidentBook residentBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(residentBookOptional);
    }

    @Override
    public void saveResidentBook(ReadOnlyResidentBook residentBook) throws IOException {
        saveResidentBook(residentBook, filePath);
    }

    /**
     * Similar to {@link #saveResidentBook(ReadOnlyResidentBook)}
     *
     * @param filePath location of the data. Cannot be null
     */
    public void saveResidentBook(ReadOnlyResidentBook residentBook, String filePath) throws IOException {
        requireNonNull(residentBook);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableResidentBook(residentBook));
    }

    @Override
    public void backupResidentBook(ReadOnlyResidentBook residentBook) throws IOException {
        saveResidentBook(residentBook, filePath + "-backup.xml");
    }

}
