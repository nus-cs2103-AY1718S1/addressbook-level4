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
import seedu.address.model.ReadOnlyRolodex;

/**
 * A class to access Rolodex data stored as an xml file on the hard disk.
 */
public class XmlRolodexStorage implements RolodexStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlRolodexStorage.class);

    private String filePath;

    public XmlRolodexStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getRolodexFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyRolodex> readRolodex() throws DataConversionException, IOException {
        return readRolodex(filePath);
    }

    /**
     * Similar to {@link #readRolodex()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyRolodex> readRolodex(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        File rolodexFile = new File(filePath);

        if (!rolodexFile.exists()) {
            logger.info("Rolodex file "  + rolodexFile + " not found");
            return Optional.empty();
        }

        ReadOnlyRolodex rolodexOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(rolodexOptional);
    }

    @Override
    public void saveRolodex(ReadOnlyRolodex rolodex) throws IOException {
        saveRolodex(rolodex, filePath);
    }

    /**
     * Similar to {@link #saveRolodex(ReadOnlyRolodex)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveRolodex(ReadOnlyRolodex rolodex, String filePath) throws IOException {
        requireNonNull(rolodex);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableRolodex(rolodex));
    }

}
