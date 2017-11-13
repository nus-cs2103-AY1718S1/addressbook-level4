package seedu.address.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyEventBook;

//@@author kaiyu92

/**
 * Represents a storage for {@link seedu.address.model.EventBook}.
 */
public interface EventBookStorage {

    /**
     * Returns the file path of the data file.
     */
    String getEventBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyEventBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyEventBook> readEventBook() throws DataConversionException, IOException;

    /**
     * @see #getEventBookFilePath()
     */
    Optional<ReadOnlyEventBook> readEventBook(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyEventBook} to the storage.
     *
     * @param eventBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveEventBook(ReadOnlyEventBook eventBook) throws IOException;

    /**
     * @see #saveEventBook(ReadOnlyEventBook)
     */
    void saveEventBook(ReadOnlyEventBook eventBook, String filePath) throws IOException;

    /**
     * @see #saveEventBook(ReadOnlyEventBook)
     */
    void backupEventBook(ReadOnlyEventBook eventBook) throws IOException;

    /**
     * @see #exportEventBook()
     */
    void exportEventBook() throws FileNotFoundException, ParserConfigurationException,
            IOException, SAXException, TransformerException;

}
