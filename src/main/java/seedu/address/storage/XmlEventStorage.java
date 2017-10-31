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
import seedu.address.model.ReadOnlyEventList;

//@@author leonchowwenhao
/**
 * A class to access EventStorage data stored as an xml file on the hard disk.
 */
public class XmlEventStorage implements EventStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlEventStorage.class);

    private String filePath;

    public XmlEventStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getEventStorageFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyEventList> readEventStorage() throws DataConversionException, IOException {
        return readEventStorage(filePath);
    }

    /**
     * Similar to {@link #readEventStorage()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyEventList> readEventStorage(String filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        File eventStorageFile = new File(filePath);

        if (!eventStorageFile.exists()) {
            logger.info("EventStorage file "  + eventStorageFile + " not found");
            return Optional.empty();
        }

        ReadOnlyEventList eventStorageOptional = XmlFileStorage.loadDataFromSaveFileEventStorage(new File(filePath));

        return Optional.of(eventStorageOptional);
    }

    @Override
    public void saveEventStorage(ReadOnlyEventList eventStorage) throws IOException {
        saveEventStorage(eventStorage, filePath);
    }

    /**
     * Similar to {@link #saveEventStorage(ReadOnlyEventList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveEventStorage(ReadOnlyEventList eventStorage, String filePath) throws IOException {
        requireNonNull(eventStorage);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFileEventStorage(file, new XmlSerializableEventStorage(eventStorage));
    }

}
