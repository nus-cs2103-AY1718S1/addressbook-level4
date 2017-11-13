//@@author HanYaodong
package seedu.address.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Represents a storage for a list of {@link seedu.address.model.person.Person}.
 */
public interface PersonListStorage {

    /**
     * Returns the file path of the data file.
     */
    public String getPersonListFilePath();

    /**
     * Returns person list data as {@link UniquePersonList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    public Optional<UniquePersonList> readPersonList()
        throws DataConversionException, IOException, DuplicatePersonException;

    /**
     * @see #readPersonList()
     */
    public Optional<UniquePersonList> readPersonList(String filePath)
        throws DataConversionException, FileNotFoundException, DuplicatePersonException;

    /**
     * Saves the given {@link ReadOnlyPerson} to the storage.
     * @param persons cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    public void savePersonList(List<ReadOnlyPerson> persons) throws IOException;

    /**
     * @see #savePersonList(List)
     */
    public void savePersonList(List<ReadOnlyPerson> persons, String filePath) throws IOException;

    /**
     * @see #savePersonList(List)
     */
    public void savePersonList(UniquePersonList persons) throws IOException;

    /**
     * @see #savePersonList(List)
     */
    public void savePersonList(UniquePersonList persons, String filePath) throws IOException;

}
