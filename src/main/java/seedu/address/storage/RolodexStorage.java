package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyRolodex;
import seedu.address.model.Rolodex;

/**
 * Represents a storage for {@link Rolodex}.
 */
public interface RolodexStorage {

    /**
     * Returns the file path of the data file.
     */
    String getRolodexFilePath();

    /**
     * Returns Rolodex data as a {@link ReadOnlyRolodex}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyRolodex> readRolodex() throws DataConversionException, IOException;

    /**
     * @see #getRolodexFilePath()
     */
    Optional<ReadOnlyRolodex> readRolodex(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyRolodex} to the storage.
     * @param rolodex cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveRolodex(ReadOnlyRolodex rolodex) throws IOException;

    /**
     * @see #saveRolodex(ReadOnlyRolodex)
     */
    void saveRolodex(ReadOnlyRolodex rolodex, String filePath) throws IOException;

}
