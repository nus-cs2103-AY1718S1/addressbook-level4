# Sri-vatsa
###### /java/seedu/address/commons/events/model/MeetingListChangedEvent.java
``` java
/** Indicates the MeetingList in the model has changed*/
public class MeetingListChangedEvent extends BaseEvent {

    public final ReadOnlyMeetingList meetingData;

    public MeetingListChangedEvent(ReadOnlyMeetingList meetingList) {
        this.meetingData = meetingList;
    }

    @Override
    public String toString() {
        return "Number of Meetings: " + meetingData.getMeetingList().size();
    }
}

```
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    public static final String DEFAULT_PAGE = "default.html";
    public static final String LINKEDIN_SEARCH_URL_PREFIX = "https://www.linkedin.com/search/results/";
    public static final String LINKEDIN_SEARCH_PEOPLE = "people/";
    public static final String LINKEDIN_SEARCH_PARAM_LOCATION = "?facetGeoRegion=%5B%22sg%3A0%22%5D";
    public static final String LINKEDIN_SEARCH_PARAM_FIRST_NAME = "&firstName=";
    public static final String LINKEDIN_SEARCH_PARAM_LAST_NAME = "&lastName=";
    public static final String LINKEDIN_URL_SUFFIX = "&origin=FACETED_SEARCH";
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com.sg/search?safe=off&q=";
    public static final String GOOGLE_SEARCH_URL_SUFFIX = "&cad=h";
    public static final String GOOGLE_MAPS_URL_PREFIX = "https://www.google.com.sg/maps?safe=off&q=";
```
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    /***
     * Loads person page
     * @param person
     */
    private void loadPersonPage(ReadOnlyPerson person) {
        personSelected = person;
        if (hasLinkedinBeenChosen) {
            try {
                loadLinkedIn();
            } catch (CommandException e) {
                e.printStackTrace();
            }
        } else {

            loadPage(GOOGLE_SEARCH_URL_PREFIX + person.getName().fullName.replaceAll(" ", "+")
                    + GOOGLE_SEARCH_URL_SUFFIX);
        }
    }

```
###### /java/seedu/address/ui/BrowserPanel.java
``` java

    /***
     * Loads pages based on choose command selection
     */
    private void loadLinkedIn() throws CommandException {
        if (personSelected == null) {
            throw new CommandException("Please select a person");
        }
        hasLinkedinBeenChosen = true;
        String[] name = personSelected.getName().fullName.split(" ");

        loadPage(LINKEDIN_SEARCH_URL_PREFIX + LINKEDIN_SEARCH_PEOPLE + LINKEDIN_SEARCH_PARAM_LOCATION
                + LINKEDIN_SEARCH_PARAM_FIRST_NAME + name[0] + LINKEDIN_SEARCH_PARAM_LAST_NAME + name[1]
                + LINKEDIN_URL_SUFFIX);
    }
```
###### /java/seedu/address/ui/BrowserPanel.java
``` java

    /**
     * Setter method to set the Boolean value of hasLinkedinBeenChosen
     */
    public void setLinkedinChosenTrue () {
        hasLinkedinBeenChosen = true;
    }

    public void setLinkedinChosenFalse () {
        hasLinkedinBeenChosen = false;
    }
```
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    @Subscribe
    private void handleBrowserPanelSelectionChangedEvent(BrowserPanelSelectionChangedEvent event)
            throws CommandException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.getBrowserSelection().equals("linkedin")) {
            loadLinkedIn();
        } else if (event.getBrowserSelection().equals("google")) {
            hasLinkedinBeenChosen = false;
            loadPersonPage(personSelected);
        }
    }

    //@author martyn-wong
    @Subscribe
    private void handleMapPanelEvent(MapPersonEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonMap(event.getPerson());
    }
}
```
###### /java/seedu/address/ui/ResultDisplay.java
``` java

    public ResultDisplay(String message) {
        super(FXML);
        resultDisplay.textProperty().bind(displayed);
        registerAsAnEventHandler(this);

        Platform.runLater(() -> {
            displayed.setValue(message);
            resultDisplay.setStyle("-fx-text-fill: white");
        });
    }

    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> {
            displayed.setValue(event.message);
            if (event.errorStatus) {
                resultDisplay.setStyle("-fx-text-fill: #e35252");
            } else {
                resultDisplay.setStyle("-fx-text-fill: #70db75");
            }
        });
    }

}
```
###### /java/seedu/address/logic/Logic.java
``` java
    /** Returns the address book */
    ArrayList<String> getMeetingNames(ReadOnlyMeeting meeting);
```
###### /java/seedu/address/logic/parser/CliSyntax.java
``` java
    public static final Prefix PREFIX_DATE = new Prefix("on ");
    public static final Prefix PREFIX_TIME = new Prefix("from ");
    public static final Prefix PREFIX_LOCATION = new Prefix("at ");
    public static final Prefix PREFIX_NOTES = new Prefix("about ");
    public static final Prefix PREFIX_PERSON = new Prefix("with ");

}
```
###### /java/seedu/address/logic/parser/AddMeetingCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddMeetingCommand object
 */
public class AddMeetingCommandParser implements Parser<AddMeetingCommand>  {

    /**
     * Parses the given {@code String} of arguments in the context of the AddMeetingCommand
     * and returns an AddMeetingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMeetingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_TIME, PREFIX_LOCATION, PREFIX_NOTES,
                        PREFIX_PERSON);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_TIME, PREFIX_LOCATION, PREFIX_NOTES)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE));
        }

        try {
            String location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION)).get();
            String notes = ParserUtil.parseNotes(argMultimap.getValue(PREFIX_NOTES)).get();
            ArrayList<InternalId> idList = ParserUtil.parseIds(argMultimap.getAllValues(PREFIX_PERSON));

            String date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE)).get();
            String time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME)).get();
            LocalDateTime localDateTime = ParserUtil.parseDateTime(date, time);

            ReadOnlyMeeting meeting = new Meeting(localDateTime, location, notes, idList);

            return new AddMeetingCommand(meeting);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### /java/seedu/address/logic/parser/exceptions/IllegalDateTimeException.java
``` java

/**
 * Catches illegal dates and times in user inputs
 */
public class IllegalDateTimeException extends IllegalValueException {

    public IllegalDateTimeException (String message) {
        super (message);
    }

}
```
###### /java/seedu/address/logic/parser/DeleteTagCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DeleteTagCommand;

import seedu.address.logic.parser.exceptions.ParseException;


/***
 * Parses the given arguments in the context of the DeleteTagCommand to faciliate execution of method
 * @throws ParseException if the user input does not conform the expected format
 */

public class DeleteTagCommandParser implements Parser<DeleteTagCommand>  {

    /***
     * Parses the given {@code String} of arguments in the context of the DeleteTagCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
        }

        String[] deleteKeywords = trimmedArgs.split("\\s+");

        return new DeleteTagCommand(deleteKeywords);
    }
}



```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> location} into an {@code Optional<String>} if {@code location} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<String> parseLocation(Optional<String> location) throws IllegalValueException {
        requireNonNull(location);
        return location.isPresent() ? Optional.of(location.get()) : Optional.empty();
    }

```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> date} into an {@code Optional<String>} if {@code date} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<String> parseDate(Optional<String> date) throws IllegalValueException {
        requireNonNull(date);
        return date.isPresent() ? Optional.of(date.get()) : Optional.empty();
    }

```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> time} into an {@code Optional<String>} if {@code time} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<String> parseTime(Optional<String> time) throws IllegalValueException {
        requireNonNull(time);
        return time.isPresent() ? Optional.of(time.get()) : Optional.empty();
    }

```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses {@code String date} & {@code String time} if {@code date} & {@code time} are present.
     */
    public static LocalDateTime parseDateTime(String date, String time) throws IllegalValueException {
        requireNonNull(date);
        requireNonNull(time);

        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm");
            String dateTime = date + " " + time;
            LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
            return localDateTime;
        } catch (DateTimeParseException dtpe) {
            throw new IllegalValueException("Please enter a date & time in the format dd/mm/yyyy & hhmm respectively!");
        }
    }



```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> notes} into an {@code Optional<String>} if {@code notes} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<String> parseNotes(Optional<String> notes) throws IllegalValueException {
        requireNonNull(notes);
        return notes.isPresent() ? Optional.of(notes.get()) : Optional.empty();
    }

```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses {@code Collection<String> ids} into a {@code Set<>}.
     */
    public static ArrayList<InternalId> parseIds(Collection<String> ids) throws IllegalValueException {
        requireNonNull(ids);
        final ArrayList<InternalId> idSet = new ArrayList<>();
        for (String id : ids) {
            idSet.add(new InternalId(Integer.parseInt(id)));
        }
        return idSet;
    }
}
```
###### /java/seedu/address/logic/commands/AddMeetingCommand.java
``` java
/**
 * Adds a new meeting to the address book.
 */
public class AddMeetingCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addMeeting";
    public static final String COMMAND_ALIAS = "am";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a meeting to the address book. "
            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_TIME + "TIME "
            + PREFIX_LOCATION + "LOCATION "
            + PREFIX_NOTES + "NOTES "
            + PREFIX_PERSON + "PERSON 1 "
            + PREFIX_PERSON + "PERSON 2 ...\n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_DATE + "20/11/2017 "
            + PREFIX_TIME + "1800 "
            + PREFIX_LOCATION + "UTown Starbucks "
            + PREFIX_NOTES + "Project Meeting "
            + PREFIX_PERSON + "1";


    public static final String MESSAGE_SUCCESS = "New meeting added!";
    public static final String MESSAGE_DUPLICATE_MEETING = "This meeting already exists in the address book";
    public static final String MESSAGE_INVALID_ID = "Please input a valid person id!";
    public static final String MESSAGE_TEMPLATE = COMMAND_WORD
            + PREFIX_DATE + "DATE "
            + PREFIX_TIME + "TIME "
            + PREFIX_LOCATION + "LOCATION "
            + PREFIX_NOTES + "NOTES "
            + PREFIX_PERSON + "PERSON ...";


    private final Meeting toAdd;

    public AddMeetingCommand(ReadOnlyMeeting meeting) {
        toAdd = new Meeting(meeting);
    }


    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addMeeting(toAdd);
        } catch (DuplicateMeetingException e) {
            throw new CommandException(MESSAGE_DUPLICATE_MEETING);
        } catch (IllegalIdException ive) {
            throw new CommandException(MESSAGE_INVALID_ID);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddMeetingCommand // instanceof handles nulls
                && toAdd.equals(((AddMeetingCommand) other).toAdd));
    }
}

```
###### /java/seedu/address/logic/commands/DeleteTagCommand.java
``` java
/**
 *
 * Deletes all tags identified from the address book.
 */
public class DeleteTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteTag";
    public static final String COMMAND_ALIAS = "dt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a particular tag from everyone.\n"
            + "Parameters: Tag1(text) Tag2(text)\n"
            + "Example: " + COMMAND_WORD + " friends" + " family";

    public static final String MESSAGE_SUCCESS = "Tag(s) successfully deleted";
    public static final String MESSAGE_NO_TAGS_DELETED = "Tag(s) not in address book; Nothing to delete";
    public static final String MESSAGE_TEMPLATE = COMMAND_WORD + " TAG 1" + " TAG2" + " ...";

    private final String[] mTagsArgs;
    private Tag[] mTagsToDelete;

    public DeleteTagCommand(String[] tag) throws NullPointerException {
        if (tag == null) {
            throw new NullPointerException("Arguments cannot be null");
        }
        mTagsArgs = tag;
    }

    /***
     * Helper method that converts array of arguments (string type) to array of tags (Tag class)
     * @param tag array of arguments in String
     * @throws IllegalValueException
     */
    private Tag[] stringToTag (String[] tag) throws IllegalValueException {
        int numOfArgs = tag.length;
        Tag[] tagsToDelete = new Tag[numOfArgs];

        try {
            for (int i = 0; i < numOfArgs; i++) {
                tagsToDelete[i] = new Tag(tag[i]);
            }
        } catch (IllegalValueException ive) {
            throw new IllegalValueException("Illegal tag value.");
        } catch (IndexOutOfBoundsException ibe) {
            throw new IndexOutOfBoundsException("Accessing tags that do not exist.");
        }
        return tagsToDelete;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        boolean hasOneOrMoreDeletion = false;
        try {
            mTagsToDelete = stringToTag(mTagsArgs);
            hasOneOrMoreDeletion = model.deleteTag(mTagsToDelete);

        } catch (IllegalValueException ive) {
            assert false : "The tag is not a proper value";
        } catch (PersonNotFoundException pnfe) {
            assert false : "The person associated with the tag cannot be missing";
        }

        if (hasOneOrMoreDeletion) {
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } else {
            return new CommandResult(String.format(MESSAGE_NO_TAGS_DELETED));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTagCommand // instanceof handles nulls
                && Arrays.equals(this.mTagsArgs, ((DeleteTagCommand) other).mTagsArgs)); // state check
    }

}
```
###### /java/seedu/address/logic/commands/ListCommand.java
``` java
    @Override
    public CommandResult executeUndoableCommand() {
        model.sortPersonListLexicographically();
        return new CommandResult(MESSAGE_SUCCESS);
    }
```
###### /java/seedu/address/logic/commands/ListByMostSearchedCommand.java
``` java
package seedu.address.logic.commands;

/***
 * Lists all users in the addressbook based on how frequently they are searched
 * Sorts by search frequency
 *
 */

public class ListByMostSearchedCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "listMostSearched";
    public static final String COMMAND_ALIAS = "lms";
    public static final String MESSAGE_TEMPLATE = COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed all persons sorted by frequency of search";

    @Override
    public CommandResult executeUndoableCommand() {
        model.sortPersonListBySearchCount();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}

```
###### /java/seedu/address/logic/commands/FindCommand.java
``` java
    @Override
    public CommandResult execute() throws CommandException {

        model.updateFilteredPersonList(predicate);
        int searchResultsCount = model.getFilteredPersonList().size();

        if (searchResultsCount != NO_RESULTS) {
            model.recordSearchHistory();
        }
        return new CommandResult(getMessageForPersonListShownSummary(searchResultsCount));
    }
```
###### /java/seedu/address/model/UniqueMeetingList.java
``` java
    /**
     * Sorts the meeting by date. For retrieving earliest meeting in the list
     */
    public void sortByDate() {
        internalList.sort(new DateTimeComparator());
    }

    /**
     * Comparator that compares date time to sort meetings
     */
    public class DateTimeComparator implements Comparator<ReadOnlyMeeting> {
        /**
         * Custom comparator to compare meetings based on chronological order
         * @param rom1 ReadOnlyMeeting 1
         * @param rom2 ReadOnlyMeeting 2
         * @return which meeting comes before the other
         */
        @Override
        public int compare(ReadOnlyMeeting rom1, ReadOnlyMeeting rom2) {
            return rom1.getDateTimeStr().compareTo(rom2.getDateTimeStr());
        }
    }

    //@@liuhang0213
    /**
     * Returns the meeting with earliest date in the internal list
     * Currently not checking if it is happening in the future
     */
    @Override
    public ReadOnlyMeeting getUpcomingMeeting() {
        this.sortByDate();
        return internalList.get(0);
    }

    public ObservableList<ReadOnlyMeeting> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<ReadOnlyMeeting> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyMeeting> asObservableList() {
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

}
```
###### /java/seedu/address/model/person/Person.java
``` java
    @Override
    public SearchData getSearchData() {
        return searchCount.get();
    }
```
###### /java/seedu/address/model/person/SearchData.java
``` java
package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/***
 * Represents number of times a person is searched for
 * Guarantees: Not editable through user interface; auto updates after each search
 */


public class SearchData {

    private String searchCount;

    /**
     *
     * @throws IllegalValueException if searchCount string is invalid.
     */
    public SearchData(String searchCount) throws IllegalValueException {
        this.searchCount = searchCount;
    }


    public void setSearchCount(String searchCount) {
        this.searchCount = searchCount;
    }

    public String getSearchCount() {
        return this.searchCount;
    }

    /**
     * Increases search count by 1 each time it is called
     */
    public void incrementSearchCount() {
        int searchCountInt = Integer.parseInt(this.searchCount);
        searchCountInt++;
        this.searchCount = Integer.toString(searchCountInt);
    }

    @Override
    public String toString() {
        return searchCount;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Phone // instanceof handles nulls
                && this.searchCount.equals(((Phone) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return searchCount.hashCode();
    }


}
```
###### /java/seedu/address/model/person/UniquePersonList.java
``` java
    /***
     * sort addressbook persons by number of times they were searched for
     */
    public void sortBySearchCount () {
        internalList.sort(new SearchCountComparator());
    }

    /**
     * Custom Comparator class to compare two ReadOnlyPerson Objects by their search Count
     */
    public class SearchCountComparator implements Comparator<ReadOnlyPerson> {

        /**
         * Basis of comparison between ReadOnlyPerson
         * Compares two persons by Search Count
         *
         * @param o1 is an instance of ReadOnlyPerson
         * @param o2 is another instance of ReadOnlyPerson
         * @return Result of Comparison
         */
        public int compare (ReadOnlyPerson o1, ReadOnlyPerson o2) {

            int personASearchCount = Integer.parseInt(o1.getSearchData().getSearchCount());
            int personBSearchCount = Integer.parseInt(o2.getSearchData().getSearchCount());

            if (personASearchCount > personBSearchCount) {
                return -1;
            } else if (personASearchCount < personBSearchCount) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /***
     * sort address book persons in alphabetical order
     */
    public void sortLexicographically () {
        internalList.sort(new LexicographicComparator());
    }

    /**
     * Custom Comparator class to compare two ReadOnlyPerson Objects lexicographically
     */
    public class LexicographicComparator implements Comparator<ReadOnlyPerson> {

        /**
         * Basis of comparison between ReadOnlyPerson
         * Compares two persons lexicographically
         *
         * @param o1 is an instance of ReadOnlyPerson
         * @param o2 is another instance of ReadOnlyPerson
         * @return Result of Comparison
         */
        public int compare (ReadOnlyPerson o1, ReadOnlyPerson o2) {

            String personAFullName = o1.getName().fullName;
            String personBFullName = o2.getName().fullName;

            return personAFullName.compareTo(personBFullName);
        }

    }
```
###### /java/seedu/address/model/exceptions/DuplicateMeetingException.java
``` java
/**
 *Signals that an operation would have violated the 'no duplicates' property of the list.
 */
public class DuplicateMeetingException extends DuplicateDataException {
    public DuplicateMeetingException() {
        super("Operation would result in duplicate meetings");
    }
}
```
###### /java/seedu/address/model/exceptions/IllegalIdException.java
``` java

/***
 * Signals that a particular person id does not exist in address book
 */
public class IllegalIdException extends IllegalValueException {

    public IllegalIdException(String message) {
        super (message);
    }
}
```
###### /java/seedu/address/model/ModelManager.java
``` java
    /** Raises an event to indicate the model has chnaged */
    private void indicateMeetingListChanged() {
        raise(new MeetingListChangedEvent(meetingList));
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
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
```
###### /java/seedu/address/model/ModelManager.java
``` java

    /***
     * Adds a meeting to the Unique meeting list
     * @param meeting
     * @throws DuplicateMeetingException
     * @throws IllegalIdException
     */
    public synchronized void addMeeting(ReadOnlyMeeting meeting) throws DuplicateMeetingException, IllegalIdException {
        boolean isIdValid;
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        //search through all Internal Ids, making sure that every id provided in argument is valid.
        for (InternalId id: meeting.getListOfPersonsId()) {
            isIdValid = false;
            for (ReadOnlyPerson person:filteredPersons) {
                if (person.getInternalId().equals(id)) {
                    isIdValid = true;
                }
            }
            if (!isIdValid) {
                throw new IllegalIdException("Please input a valid person id");
            }
        }

        meetingList.add(meeting);
        indicateMeetingListChanged();
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    /***
     * Records how many times each person in addressbook is searched for
     * @throws CommandException
     */
    @Override
    public void recordSearchHistory() throws CommandException {

        int searchResultsCount = filteredPersons.size();

        for (int i = 0; i < searchResultsCount; i++) {
            ReadOnlyPerson searchedPerson = filteredPersons.get(i);
            SearchData updatedSearchData = searchedPerson.getSearchData();
            updatedSearchData.incrementSearchCount();
            Person modifiedPerson = new Person(searchedPerson.getInternalId(), searchedPerson.getName(),
                    searchedPerson.getPhone(), searchedPerson.getEmail(), searchedPerson.getAddress(),
                    searchedPerson.getTags(), updatedSearchData);
            try {
                updatePerson(searchedPerson, modifiedPerson);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
        }
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    //=========== Sort addressBook methods =============================================================
    /***
     * Sorts persons in address book by searchCount
     */
    @Override
    public void sortPersonListBySearchCount() {
        addressBook.sortBySearchCount();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    /***
     * Sorts persons in Address book alphabetically
     */
    @Override
    public void sortPersonListLexicographically() {
        addressBook.sortLexicographically();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }
```
###### /java/seedu/address/model/Model.java
``` java
    /** Deletes given tag from everyone in the addressbook */
    boolean deleteTag(Tag [] tags) throws PersonNotFoundException, DuplicatePersonException;
    /** Adds the given person */
    void addMeeting(ReadOnlyMeeting meeting) throws DuplicateMeetingException, IllegalIdException;
```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Updates search count for each person who is searched using {@code FindCommand}
     * Assumes filtered List of persons contains search results
     */
    void recordSearchHistory() throws CommandException;

    /**
     * Sort everyone in addressbook by searchCount
     */
    void sortPersonListBySearchCount();

    /**
     * Sort everyone in addressbook lexicographically
     */
    void sortPersonListLexicographically();

```
###### /java/seedu/address/model/AddressBook.java
``` java
    /***
     * sorts persons in the addressbook by number of times they were previously searched
     */
    public void sortBySearchCount() {
        persons.sortBySearchCount();
    }


    /***
     * sorts persons in the addressbook alphabetically
     */
    public void sortLexicographically() {
        persons.sortLexicographically();
    }
```
