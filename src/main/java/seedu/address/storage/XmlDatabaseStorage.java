//@@author cqhchan
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
import seedu.address.model.ReadOnlyDatabase;

/**
 * A class to access Database data stored as an xml file on the hard disk.
 */
public class XmlDatabaseStorage implements DataBaseStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlDatabaseStorage.class);

    private String filePath;

    public XmlDatabaseStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getDatabaseFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyDatabase> readDatabase() throws DataConversionException, IOException {
        return readDatabase(filePath);
    }

    /**
     * Similar to {@link #readDatabase()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyDatabase> readDatabase(String filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        File databaseFile = new File(filePath);

        if (!databaseFile.exists()) {
            logger.info("Database file "  + databaseFile + " not found");
            return Optional.empty();
        }

        ReadOnlyDatabase databaseOptional = XmlFileStorage.loadDataBaseFromSaveFile(new File(filePath));

        return Optional.of(databaseOptional);
    }

    @Override
    public void saveDatabase(ReadOnlyDatabase database) throws IOException {
        saveDatabase(database, filePath);
    }

    /**
     * Similar to {@link #saveDatabase(ReadOnlyDatabase)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveDatabase(ReadOnlyDatabase database, String filePath) throws IOException {
        requireNonNull(database);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataBaseToFile(file, new XmlSerializableDatabase(database));
    }

    @Override
    public void backupDatabase(ReadOnlyDatabase database) throws IOException {
        String databaseBackupFilePath = "backup/addressbook-backup.xml";
        saveDatabase(database, databaseBackupFilePath);
    }

}
