package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyMeetingList;

/**
 * Represents a storage for meetings
 */
public interface MeetingListStorage {

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
    Optional<ReadOnlyMeetingList> readMeetingList() throws DataConversionException, IOException;

    /**
     * @see #getMeetingsFilePath()
     */
    Optional<ReadOnlyMeetingList> readMeetingList(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyMeetingList} to the storage.
     * @param meetingList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveMeetingList(ReadOnlyMeetingList meetingList) throws IOException;

    /**
     * @see #saveMeetingList(ReadOnlyMeetingList)
     */
    void saveMeetingList(ReadOnlyMeetingList meetingList, String filePath) throws IOException;

    /*
    /**
     * Backs up the current state of addressbook to local storage
     */
    //void backupMeetingList(ReadOnlyMeetingList meetingList) throws IOException;

    /*
    /**
     * Restores an earlier version of address book from local storage
     * @throws IOException if there was any problem reading the file
     * @throws DataConversionException if the data in storage is not in the expected format.
     */
    //Optional<ReadOnlyMeetingList> restoreMeetingList() throws IOException, DataConversionException;

}
