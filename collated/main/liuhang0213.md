# liuhang0213
###### \java\seedu\address\logic\commands\NextMeetingCommand.java
``` java
/**
 * Lists all upcoming meetings to the user.
 */
public class NextMeetingCommand extends Command {

    public static final String COMMAND_WORD = "nextMeeting";
    public static final String COMMAND_ALIAS = "nm";

    public static final String MESSAGE_SUCCESS = "Displays the upcoming meeting";
    public static final String MESSAGE_OUTPUT_PREFIX = "Upcoming meeting with ";
    public static final String MESSAGE_INVALID_PARTICIPANT = "Meeting involves person not present in address book";
    public static final String MESSAGE_NO_UPCOMING_MEETINGS = "No upcoming meetings";
    public static final String MESSAGE_TEMPLATE = COMMAND_WORD;

    @Override
    public CommandResult execute() throws CommandException {
        Meeting nextMeeting = model.getMeetingList().getUpcomingMeeting();
        if (nextMeeting == null) {
            return new CommandResult(MESSAGE_NO_UPCOMING_MEETINGS);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(MESSAGE_OUTPUT_PREFIX);
        try {
            for (InternalId id : nextMeeting.getListOfPersonsId()) {
                sb.append(model.getAddressBook().getPersonByInternalIndex(id.getId()).getName().fullName);
                sb.append(", ");
            }
        } catch (PersonNotFoundException e) {
            throw new CommandException(MESSAGE_INVALID_PARTICIPANT);
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append('\n');

        return new CommandResult(sb.toString() + nextMeeting.toString());
    }
}




```
###### \java\seedu\address\logic\commands\PrefCommand.java
``` java
/**
 * Edits the details of an existing person in the address book.
 */
public class PrefCommand extends Command {

    public static final String COMMAND_WORD = "pref";
    public static final String COMMAND_ALIAS = "p";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits user preferences "
            + "Parameters: KEY [NEW_VALUE]\n"
            + "Example: " + COMMAND_WORD + " AddressBookName" + " MyNewAddressBook\n";
    public static final String MESSAGE_TEMPLATE = COMMAND_WORD + "KEY [NEW_VALUE]";

    public static final String MESSAGE_EDIT_PREF_SUCCESS = "Edited preference: %1$s \nfrom %2$s \nto %3$s";
    public static final String MESSAGE_PREF_KEY_NOT_FOUND = "Invalid preference key: %1$s";
    public static final String MESSAGE_ACCESSING_PREF_ERROR = "Unable to read preference value for %1$s";

    private String prefKey;
    private String newPrefValue = "";

    /**
     */
    public PrefCommand(String prefKey, String newPrefValue) {
        this.prefKey = prefKey;
        this.newPrefValue = newPrefValue;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        String currentPrefValue;
        currentPrefValue = readPrefValue(prefKey);
        if (!newPrefValue.isEmpty()) {
            // Editing preference
            writePrefValue(prefKey, newPrefValue);
            return new CommandResult(String.format(MESSAGE_EDIT_PREF_SUCCESS, prefKey, currentPrefValue, newPrefValue));
        }
        return new CommandResult(currentPrefValue);
    }

    /**
     * Reads current value for the preference key
     * @param prefKey Key name of the preference
     * @return Value of the preference
     * @throws CommandException if the preference key is not defined in UserPrefs or not accessible
     */
    private String readPrefValue(String prefKey) throws CommandException {
        String prefValue;
        try {
            UserPrefs prefs = model.getUserPrefs();
            String getMethodName = "get" + prefKey;
            Class<?> userPrefsClass = prefs.getClass();
            Method getMethod = userPrefsClass.getMethod(getMethodName);
            prefValue = getMethod.invoke(prefs).toString();
        } catch (NoSuchMethodException e) {
            throw new CommandException(String.format(MESSAGE_PREF_KEY_NOT_FOUND, prefKey));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new CommandException(String.format(MESSAGE_ACCESSING_PREF_ERROR, prefKey));
        }
        return prefValue;
    }

    /**
     * Modifies the value of preference given by the key
     * @param prefKey Key name of the preference
     * @param newPrefValue New value of the preference
     * @throws CommandException if the preference key is not defined in UserPrefs or not accessible
     */
    private void writePrefValue(String prefKey, String newPrefValue) throws CommandException {
        try {
            UserPrefs prefs = model.getUserPrefs();
            String setMethodName = "set" + prefKey;
            Class<?> userPrefsClass = prefs.getClass();
            Method setMethod = userPrefsClass.getMethod(setMethodName, String.class);
            setMethod.invoke(prefs, newPrefValue);
        } catch (NoSuchMethodException e) {
            throw new CommandException(String.format(MESSAGE_PREF_KEY_NOT_FOUND, prefKey));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new CommandException(String.format(MESSAGE_ACCESSING_PREF_ERROR, prefKey));
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PrefCommand)) {
            return false;
        }

        return Objects.equals(this.toString(), other.toString());
    }

    @Override
    public String toString() {
        return prefKey + ": " + newPrefValue;
    }

}
```
###### \java\seedu\address\logic\parser\PrefCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Objects;

import seedu.address.logic.commands.PrefCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PrefCommand object
 */
public class PrefCommandParser implements Parser<PrefCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PrefCommand
     * and returns an PrefCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PrefCommand parse(String args) throws ParseException {
        String[] splitArgs = args.trim().split("\\s+");
        String prefKey;
        String newPrefValue;
        newPrefValue = "";

        if (splitArgs.length > 2 || (splitArgs.length == 1 && Objects.equals(splitArgs[0], ""))) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PrefCommand.MESSAGE_USAGE));
        } else if (splitArgs.length == 2) {
            // The second argument is optional
            newPrefValue = splitArgs[1].trim();
        }

        prefKey = splitArgs[0].trim();

        return new PrefCommand(prefKey, newPrefValue);
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Returns the maximum internal index in the unique person list
     */
    public int getMaxInternalIndex() {
        return persons.getMaxInternalIndex();
    }

    @Override
    public ReadOnlyPerson getPersonByInternalIndex(int index) throws PersonNotFoundException {
        return persons.getPersonByInternalIndex(index);
    }

```
###### \java\seedu\address\model\Meeting.java
``` java
/**
 * Represents a Meeting
 * Guarantees: immutable; meeting time is in the future
 */
public class Meeting implements Comparable<Meeting> {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final String MESSAGE_INVALID_DATE = "The meeting must be in the future.";
    private LocalDateTime dateTime;
    private String location;
    private String notes;
    private ArrayList<InternalId> listOfPersonsId;
    private Boolean isMeetingInFuture;

    /**
     * Validates params given for meeting
     *
     * @throws IllegalValueException if the given meeting time is not in the future
     */
    public Meeting(LocalDateTime dateTime, String location, String notes, ArrayList<InternalId> listOfPersonsId) {
        requireNonNull(dateTime);
        requireNonNull(location);
        requireNonNull(listOfPersonsId);
        if (dateTime.isBefore(LocalDateTime.now())) {
            isMeetingInFuture = false;
        } else {
            isMeetingInFuture = true;
        }

        this.dateTime = dateTime;
        this.location = location.trim();
        this.notes = notes.trim();
        this.listOfPersonsId = listOfPersonsId;
    }

    /**
     * Creates a copy of the given meeting
     */
    public Meeting(Meeting source) {
        this(source.dateTime, source.location, source.notes, source.listOfPersonsId);
    }

    // Get methods
    public String getDate() {
        return dateTime.format(DATE_FORMATTER);
    }

    public String getTime() {
        return dateTime.format(TIME_FORMATTER);
    }

    public String getDateTime() {
        return dateTime.toString();
    }

    public String getLocation() {
        return location;
    }

    public String getNotes() {
        return notes;
    }

    public ArrayList<InternalId> getListOfPersonsId() {
        return listOfPersonsId;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Meeting // instanceof handles nulls
                && this.dateTime.equals(((Meeting) other).dateTime)
                && this.location.equals(((Meeting) other).location)
                && this.notes.equals(((Meeting) other).notes)
                && this.listOfPersonsId.equals(((Meeting) other).listOfPersonsId)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, location, notes, listOfPersonsId);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return "Date: " + dateTime.format(DATE_FORMATTER) + "  Time: " + dateTime.format(TIME_FORMATTER) + '\n'
                + "Location: " + location + '\n'
                + "Notes: " + notes;
    }

    @Override
    public int compareTo(Meeting other) {
        return dateTime.compareTo(other.dateTime);
    }
}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /** Raises an event to indicate a person been added */
    private void indicatePersonAdded(ReadOnlyPerson person) {
        raise(new PersonChangedEvent(person, PersonChangedEvent.ChangeType.ADD, userPrefs));
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    /** Raises an event to indicate a person been edited */
    private void indicatePersonEdited(ReadOnlyPerson person) {
        raise(new PersonChangedEvent(person, PersonChangedEvent.ChangeType.EDIT, userPrefs));
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    /** Raises an event to indicate a person been deleted */
    private void indicatePersonDeleted(ReadOnlyPerson person) {
        raise(new PersonChangedEvent(person, PersonChangedEvent.ChangeType.DELETE, userPrefs));
    }

    @Override
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
        indicatePersonDeleted(target);
    }

    @Override
    public boolean deleteTag(Tag [] tags) throws PersonNotFoundException, DuplicatePersonException {
        boolean isTagRemoved;
        boolean hasOneOrMoreDeletion = false;
        for (int i = 0; i < addressBook.getPersonList().size(); i++) {

            ReadOnlyPerson oldPerson = addressBook.getPersonList().get(i);
            //creates a new person without each of the tags
            Person newPerson = new Person(oldPerson);
            Set<Tag> newTags = new HashSet<>(newPerson.getTags());

            for (Tag tag : tags) {
                isTagRemoved = newTags.remove(tag);
                if (isTagRemoved) {
                    hasOneOrMoreDeletion = isTagRemoved;
                }
            }
            newPerson.setTags(newTags);

            addressBook.updatePerson(oldPerson, newPerson);
        }
        return hasOneOrMoreDeletion;
    }

    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        addressBook.addPerson(person);

        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
        indicatePersonAdded(person);
    }

    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
        indicatePersonEdited(editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList() {
        updateFilteredPersonList();
    }

    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public UserPrefs getUserPrefs() {
        return this.userPrefs;
    }

```
###### \java\seedu\address\model\person\InternalId.java
``` java
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's id in the address book.
 * Guarantees: immutable; is valid long as it is a positive integer
 */
public class InternalId {

    public static final String MESSAGE_ID_CONSTRAINTS = "Id must be a positive interger.";
    public final int value;

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public InternalId(int id) throws IllegalValueException {
        if (id < 0) {
            throw new IllegalValueException(MESSAGE_ID_CONSTRAINTS);
        }
        this.value = id;
    }

    public int getId() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InternalId // instanceof handles nulls
                && this.value == (((InternalId) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

}
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Returns the maximum internal index among all persons in the address book
     */
    public int getMaxInternalIndex() {
        return maxInternalIndex;
    }

    public ReadOnlyPerson getPersonByInternalIndex(int index) throws PersonNotFoundException {
        for (Person p : internalList) {
            if (p.getInternalId().getId() == index) {
                return p;
            }
        }
        throw new PersonNotFoundException();
    }

```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Updates the maximum internal index among all persons in the person list
     * Currently not used; implemented previously for remove(), but it was unnecessary to update
     * after each deletion
     * @return the maximum internal index
     */
    private int updateMaxInternalIndex() {
        int maxIndex = 0;
        for (Person p : internalList) {
            if (p.getInternalId().getId() > maxIndex) {
                maxIndex = p.getInternalId().getId();
            }
        }
        return maxIndex;
    }

```
###### \java\seedu\address\model\ReadOnlyAddressBook.java
``` java
    /**
     * Returns an unmodifiable view of a person by the given internal index
     * @param i internal index of the person
     *
     */
    ReadOnlyPerson getPersonByInternalIndex(int i) throws PersonNotFoundException;

    /**
     * Returns the maximum index of persons in the address book.
     * @return
     */
    int getMaxInternalIndex();

}
```
###### \java\seedu\address\model\ReadOnlyMeetingList.java
``` java
/**
 * Unmodifiable view of a meeting list
 */
public interface ReadOnlyMeetingList {

    /**
     * Returns an unmodifiable view of the meetings list.
     * This list will not contain any duplicate meetings.
     */
    ObservableList<Meeting> getMeetingList();

    /**
     * Returns the next upcoming meeting
     * This is required for nextMeeting command
     */
    Meeting getUpcomingMeeting();
}
```
###### \java\seedu\address\model\UniqueMeetingList.java
``` java
/**
 * A list of meetings that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Meeting#equals(Object)
 */
public class UniqueMeetingList implements Iterable<Meeting>, ReadOnlyMeetingList {

    private final ObservableList<Meeting> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty MeetingList.
     */
    public UniqueMeetingList() {}

    /**
     * Creates a UniqueMeetingList using given meetings.
     * Enforces no nulls.
     */
    public UniqueMeetingList(List<Meeting> meetings) {
        requireAllNonNull(meetings);
        internalList.addAll(meetings);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Creates a copy of an existing meeting list
     */
    public UniqueMeetingList(ReadOnlyMeetingList newData) {
        requireNonNull(newData);
        this.internalList.setAll(newData.getMeetingList());
    }

    @Override
    public ObservableList<Meeting> getMeetingList() {
        return internalList;
    }

    /**
     * Returns all meetings in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Meeting> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Meetings in this list with those in the argument meeting list.
     */
    public void setMeetings(List<Meeting> meetings) {
        requireAllNonNull(meetings);
        internalList.setAll(meetings);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every meeting in the argument list exists in this object.
     */
    public void mergeFrom(UniqueMeetingList from) {
        final Set<Meeting> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(meeting -> !alreadyInside.contains(meeting))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Meeting as the given argument.
     */
    public boolean contains(Meeting toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Meeting to the list.
     *
     * @throws DuplicateMeetingException if the Meeting to add is a duplicate of an existing Meeting in the list.
     */
    public void add(Meeting toAdd) throws DuplicateMeetingException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMeetingException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Sorts the meeting by date. For retrieving earliest meeting in the list
     */
    public void sortByDate() {
        Collections.sort(internalList);
    }

    /**
     * Returns the meeting with earliest date in the internal list
     * Currently not checking if it is happening in the future
     */
    @Override
    public Meeting getUpcomingMeeting() {
        this.sortByDate();
        return internalList.get(0);
    }

    public ObservableList<Meeting> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<Meeting> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Meeting> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueMeetingList // instanceof handles nulls
                        && this.internalList.equals(((UniqueMeetingList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueMeetingList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateMeetingException extends DuplicateDataException {
        protected DuplicateMeetingException() {
            super("Operation would result in duplicate meetings");
        }
    }
}
```
###### \java\seedu\address\storage\AddressBookStorage.java
``` java
    /**
     * Backs up the current state of addressbook to local storage
     */
    void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * Restores an earlier version of address book from local storage
     * @throws IOException if there was any problem reading the file
     * @throws DataConversionException if the data in storage is not in the expected format.
     */
    Optional<ReadOnlyAddressBook> restoreAddressBook() throws IOException, DataConversionException;

}
```
###### \java\seedu\address\storage\MeetingListStorage.java
``` java
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
```
###### \java\seedu\address\storage\Storage.java
``` java
    @Subscribe
    void handlePersonChangedEvent(PersonChangedEvent event);

    @Override
    Optional<ReadOnlyMeetingList> readMeetingList() throws IOException, DataConversionException;

    @Override
    void saveMeetingList(ReadOnlyMeetingList meetingList) throws IOException;

    void saveFileFromUrl(String urlString, String filePath) throws IOException;

```
###### \java\seedu\address\storage\Storage.java
``` java
    /**
     * Downloads gravatar image and save in local storage using each person's email address
     * @param person The person whose profile photo is requried
     * @param def The default style of profile photo
     */
    void downloadProfilePhoto(ReadOnlyPerson person, String def);
}
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        addressBookStorage.backupAddressBook(addressBook);
    }

    public Optional<ReadOnlyAddressBook> restoreAddressBook() throws IOException, DataConversionException {
        return addressBookStorage.restoreAddressBook();
    }

```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    @Subscribe
    public void handlePersonChangedEvent(PersonChangedEvent event) {
        if (event.type == PersonChangedEvent.ChangeType.ADD
                || event.type == PersonChangedEvent.ChangeType.EDIT) {
            downloadProfilePhoto(event.person, event.prefs.getDefaultProfilePhoto());
        }
    }

    // ============ Meeting List methods ==================
    @Override
    public String getMeetingsFilePath() {
        return meetingListStorage.getMeetingsFilePath();
    }

    @Override
    public Optional<ReadOnlyMeetingList> readMeetingList() throws IOException, DataConversionException {
        return meetingListStorage.readMeetingList();
    }

    @Override
    public Optional<ReadOnlyMeetingList> readMeetingList(String filePath) throws DataConversionException, IOException {
        return meetingListStorage.readMeetingList(filePath);
    }

    @Override
    public void saveMeetingList(ReadOnlyMeetingList meetingList) throws IOException {
        meetingListStorage.saveMeetingList(meetingList);
    }

    @Override
    public void saveMeetingList(ReadOnlyMeetingList meetingList, String filePath) throws IOException {
        meetingListStorage.saveMeetingList(meetingList, filePath);
    }

    // ============= Cache & Download methods =======================

    @Override
    public void saveFileFromUrl(String urlString, String filename) throws IOException {
        try {
            URL url = new URL(urlString);
            InputStream in = new BufferedInputStream(url.openStream());
            String filePath = CACHE_DIR + filename;

            File file = new File(filePath);
            file.createNewFile();

            OutputStream out = new BufferedOutputStream(new FileOutputStream(filePath));

            for (int i; (i = in.read()) != -1; ) {
                out.write(i);
            }
            in.close();
            out.close();
        } catch (MalformedURLException e) {
            logger.warning(String.format("URL %1$s is not valid. File not downloaded.", urlString));
        }
    }

    /**
     * Returns a cache file in the local folder
     * @param filename of file to be opened
     * @throws IOException if file with given filename is not found
     */
    public static FileInputStream loadCacheFile(String filename) throws IOException {
        try {
            return new FileInputStream(CACHE_DIR + filename);
        } catch (FileNotFoundException e) {
            logger.warning(String.format("Cache file %1$s not found.", filename));
            throw new IOException();
        }
    }

    /**
     * Returns a image in the resources folder
     * @param imageFilename of the image file to be opened
     * @throws IOException if the image with given filename is not found
     */
    public static FileInputStream loadResourceImage(String imageFilename) throws IOException {
        try {
            return new FileInputStream(IMAGE_RESOURCE_DIR + imageFilename);
        } catch (FileNotFoundException e) {
            logger.warning(String.format("Image resource %1$s not found.", imageFilename));
            throw new IOException();
        }
    }

    // Gravatar

    @Override
    public void downloadProfilePhoto(ReadOnlyPerson person, String def) {
        try {
            String gravatarUrl = StringUtil.generateGravatarUrl(person.getEmail().value,
                    def);
            String filename = String.format(PersonCard.PROFILE_PHOTO_FILENAME_FORMAT, person.getInternalId().value);
            saveFileFromUrl(gravatarUrl, filename);
            logger.info("Downloaded " + gravatarUrl + " to " + filename);
        } catch (IOException e) {
            logger.warning(String.format("Gravatar not downloaded for %1$s.", person.getName()));
        }
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedMeeting.java
``` java
/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedMeeting {

    @XmlElement(required = true)
    private String dateTime;
    @XmlElement(required = true)
    private String location;
    @XmlElement(required = true)
    private String notes;

    @XmlElement
    private List<Integer> listOfPersonsId = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMeeting() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedMeeting(Meeting source) {
        dateTime = source.getDateTime();
        location = source.getLocation();
        notes = source.getNotes();
        listOfPersonsId = new ArrayList<Integer>();
        for (InternalId id : source.getListOfPersonsId()) {
            listOfPersonsId.add(id.getId());
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Meeting toModelType() throws IllegalValueException {
        final ArrayList<InternalId> personIds = new ArrayList<>();
        for (Integer id : listOfPersonsId) {
            personIds.add(new InternalId(id));
        }

        final LocalDateTime dateTime = LocalDateTime.parse(this.dateTime);
        return new Meeting(dateTime, location, notes, personIds);
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
    /**
     * Returns the internal id of the person as read from the xml file
     * This is needed for address book initialization
     */
    public int getInternalId() {
        return internalId;
    }
}
```
###### \java\seedu\address\storage\XmlAddressBookStorage.java
``` java
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, backupFilePath);
    }

    public Optional<ReadOnlyAddressBook> restoreAddressBook() throws IOException, DataConversionException {
        return readAddressBook(backupFilePath);
    }


}
```
###### \java\seedu\address\storage\XmlMeetingListStorage.java
``` java
/**
 * A class to access Meeting data stored as an xml file on the hard disk.
 */
public class XmlMeetingListStorage implements MeetingListStorage {

    // Creates a new folder for all backup data
    //private static final String BACKUP_FILE_PREFIX = "backup/";
    private static final Logger logger = LogsCenter.getLogger(XmlMeetingListStorage.class);

    private String filePath;
    //private String backupFilePath;

    public XmlMeetingListStorage(String filePath) {
        this.filePath = filePath;
        //this.backupFilePath = BACKUP_FILE_PREFIX + filePath;
    }

    public String getMeetingsFilePath() {
        return filePath;
    }

    /*
    public String getBackupFilePath() {
        return backupFilePath;
    }
    */

    @Override
    public Optional<ReadOnlyMeetingList> readMeetingList() throws DataConversionException, IOException {
        return readMeetingList(filePath);
    }

    /**
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyMeetingList> readMeetingList(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        File meetingListFile = new File(filePath);

        if (!meetingListFile.exists()) {
            logger.info("MeetingList file "  + meetingListFile + " not found");
            return Optional.empty();
        }

        ReadOnlyMeetingList meetingListOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath),
                XmlSerializableMeetingList.class);

        return Optional.of(meetingListOptional);
    }

    @Override
    public void saveMeetingList(ReadOnlyMeetingList meetingList) throws IOException {
        saveMeetingList(meetingList, filePath);
    }

    /**
     * Similar to {@link #saveMeetingList(ReadOnlyMeetingList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveMeetingList(ReadOnlyMeetingList meetingList, String filePath) throws IOException {
        requireNonNull(meetingList);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableMeetingList(meetingList));
    }

    /*
    @Override
    public void backupMeetingList(ReadOnlyMeetingList meetingList) throws IOException {
        saveMeetingList(meetingList, backupFilePath);
    }

    public Optional<ReadOnlyMeetingList> restoreMeetingList() throws IOException, DataConversionException {
        return readMeetingList(backupFilePath);
    }
    */


}
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
    @Override
    public ReadOnlyPerson getPersonByInternalIndex(int index) throws PersonNotFoundException {
        try {
            for (XmlAdaptedPerson p : persons) {
                if (p.getInternalId() == index) {
                    return p.toModelType();
                }
            }
        } catch (IllegalValueException e) {
            e.printStackTrace();
            return null;
        }
        throw new PersonNotFoundException();
    }

    @Override
    public int getMaxInternalIndex() {
        int maxIndex = 0;
        for (XmlAdaptedPerson p : persons) {
            if (p.getInternalId() > maxIndex) {
                maxIndex = p.getInternalId();
            }
        }
        return maxIndex;
    }
}
```
###### \java\seedu\address\storage\XmlSerializableData.java
``` java
/**
 * An abstract class for address book and meeting list
 * Used for storage utils for xml format data
 */
public abstract class XmlSerializableData {
}
```
###### \java\seedu\address\storage\XmlSerializableMeetingList.java
``` java
/**
 * An Immutable MeetingList that is serializable to XML format
 */
@XmlRootElement(name = "meetingList")
public class XmlSerializableMeetingList extends XmlSerializableData implements ReadOnlyMeetingList {

    @XmlElement
    private List<XmlAdaptedMeeting> meetings;

    /**
     * Creates an empty XmlSerializableMeetingList
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableMeetingList() {
        meetings = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableMeetingList(ReadOnlyMeetingList src) {
        this();
        meetings.addAll(src.getMeetingList().stream().map(XmlAdaptedMeeting::new).collect(Collectors.toList()));
    }

    @Override
    public ObservableList<Meeting> getMeetingList() {
        final ObservableList<Meeting> meetings = this.meetings.stream().map(m -> {
            try {
                return m.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(meetings);
    }

    /**
     * Should not be reached since the list should be immutable and cannot be sorted
     * @return
     */
    @Override
    public Meeting getUpcomingMeeting() {
        return null;
    }
}
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Initializes the profile picture using Gravatar
     */
    private void initPicture(ReadOnlyPerson person) {

        Image image;

        try {
            FileInputStream imageFile = StorageManager.loadCacheFile(String.format(PROFILE_PHOTO_FILENAME_FORMAT,
                    person.getInternalId().value));
            image = new Image(imageFile);
            gravatar.setImage(image);
        } catch (IOException e) {
            try {
                FileInputStream defImageFile = StorageManager.loadResourceImage(DEFAULT_PROFILE_PHOTO_FILENAME);
                image =  new Image(defImageFile);
                gravatar.setImage(image);
            } catch (IOException e1) {
                // Shouldn't happen unless the default profile photo is missing
                e.printStackTrace();
            }
        }
    }

```
