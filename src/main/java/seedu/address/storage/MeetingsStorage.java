package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UniqueMeetingList;

/**
 * Represents a storage for meetings
 */
public interface MeetingsStorage {

    /**
     * Returns the file path of the data file.
     */
    String getMeetingsFilePath();

    /**
     * Returns Meetings data
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<UniqueMeetingList> readMeetings() throws DataConversionException, IOException;

    /**
     * @see #getMeetingsFilePath()
     */
    Optional<UniqueMeetingList> readMeetingList(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link UniqueMeetingList} to the storage.
     * @param meetingList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveMeetingList(UniqueMeetingList meetingList) throws IOException;

    /**
     * @see #saveMeetingList(UniqueMeetingList)
     */
    void saveMeetingList(UniqueMeetingList meetingList, String filePath) throws IOException;

    /*
    /**
     * Backs up the current state of addressbook to local storage
     */
    //void backupMeetingList(UniqueMeetingList meetingList) throws IOException;

    /*
    /**
     * Restores an earlier version of address book from local storage
     * @throws IOException if there was any problem reading the file
     * @throws DataConversionException if the data in storage is not in the expected format.
     */
    //Optional<UniqueMeetingList> restoreMeetingList() throws IOException, DataConversionException;

}
