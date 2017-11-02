package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.PersonChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyMeetingList;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, MeetingListStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(AddressBookChangedEvent abce);

    //@@author liuhang0213
    @Subscribe
    void handlePersonChangedEvent(PersonChangedEvent event);

    @Override
    Optional<ReadOnlyMeetingList> readMeetingList() throws IOException, DataConversionException;

    @Override
    void saveMeetingList(ReadOnlyMeetingList meetingList) throws IOException;

    void saveFileFromUrl(String urlString, String filePath) throws IOException;

    //@@author liuhang0213
    /**
     * Downloads gravatar image and save in local storage using each person's email address
     * @param person The person whose profile photo is requried
     * @param def The default style of profile photo
     */
    void downloadProfilePhoto(ReadOnlyPerson person, String def);
}
