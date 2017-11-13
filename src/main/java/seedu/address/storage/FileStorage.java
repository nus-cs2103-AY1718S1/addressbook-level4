package seedu.address.storage;

import java.io.IOException;

import seedu.address.model.ReadOnlyAddressBook;

// @@author johnweikangong
/**
 * Stores the addressbook in a CSV file.
 */
public interface FileStorage {

    /**
     * Returns the file path of the CSV data file.
     */
    String getCsvFilePath();

    /**
     * Saves the given {@code addressBook} to the CSV file.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveToCsvFile(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * @see #saveToCsvFile(ReadOnlyAddressBook)
     */
    void saveToCsvFile(ReadOnlyAddressBook addressBook, String filePath) throws IOException;
}
