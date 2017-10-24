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
import seedu.room.model.ReadOnlyRoomBook;

/**
 * A class to access RoomBook data stored as an xml file on the hard disk.
 */
public class XmlRoomBookStorage implements RoomBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlRoomBookStorage.class);

    private String filePath;

    public XmlRoomBookStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getRoomBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyRoomBook> readRoomBook() throws DataConversionException, IOException {
        return readRoomBook(filePath);
    }

    /**
     * Similar to {@link #readRoomBook()}
     *
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyRoomBook> readRoomBook(String filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        File roomBookFile = new File(filePath);

        if (!roomBookFile.exists()) {
            logger.info("RoomBook file " + roomBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyRoomBook roomBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(roomBookOptional);
    }

    @Override
    public void saveRoomBook(ReadOnlyRoomBook roomBook) throws IOException {
        saveRoomBook(roomBook, filePath);
    }

    /**
     * Similar to {@link #saveRoomBook(ReadOnlyRoomBook)}
     *
     * @param filePath location of the data. Cannot be null
     */
    public void saveRoomBook(ReadOnlyRoomBook roomBook, String filePath) throws IOException {
        requireNonNull(roomBook);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableRoomBook(roomBook));
    }

    @Override
    public void backupRoomBook(ReadOnlyRoomBook roomBook) throws IOException {
        saveRoomBook(roomBook, filePath + "-backup.xml");
    }

}
