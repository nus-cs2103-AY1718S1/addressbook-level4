# Sri-vatsa
###### /java/seedu/address/commons/events/model/MeetingListChangedEvent.java
``` java
/**
 *  Indicates the MeetingList in the model has changed
 */
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
        } else if (hasMapsBeenChosen) {
            try {
                loadPersonMap(person);
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
        setLinkedinChosenTrue();
        setMapsChosenFalse();
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
            setLinkedinChosenFalse();
            setMapsChosenFalse();
            loadPersonPage(personSelected);

        } else if (event.getBrowserSelection().equals("maps")) {
            loadPersonMap(personSelected);

        }
    }

    //@author martyn-wong
    @Subscribe
    private void handleMapPanelEvent(MapPersonEvent event) throws CommandException {
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
        String userInput = location.get().trim();
        if (userInput.isEmpty()) {
            throw new IllegalValueException("Location cannot be empty.");
        }
        return location.isPresent() ? Optional.of(location.get()) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> date} into an {@code Optional<String>} if {@code date} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<String> parseDate(Optional<String> date) throws IllegalValueException {
        requireNonNull(date);
        return date.isPresent() ? Optional.of(date.get()) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> time} into an {@code Optional<String>} if {@code time} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<String> parseTime(Optional<String> time) throws IllegalValueException {
        requireNonNull(time);
        return time.isPresent() ? Optional.of(time.get()) : Optional.empty();
    }

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
            if (localDateTime.isBefore(LocalDateTime.now())) {
                throw new IllegalValueException("Please enter a date & time that is in the future.");
            }
            return localDateTime;
        } catch (DateTimeParseException dtpe) {
            throw new IllegalValueException("Please enter a date & time in the format dd/mm/yyyy & hhmm respectively!");
        }
    }

    /**
     * Parses a {@code Optional<String> notes} into an {@code Optional<String>} if {@code notes} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<String> parseNotes(Optional<String> notes) throws IllegalValueException {
        requireNonNull(notes);
        String userInput = notes.get().trim();
        if (userInput.isEmpty()) {
            throw new IllegalValueException("Location cannot be empty.");
        }
        return notes.isPresent() ? Optional.of(notes.get()) : Optional.empty();
    }

    /**
     * Parses {@code Collection<String> ids} into a {@code Set<InternalIds>}.
     */
    public static ArrayList<InternalId> parseIds(Collection<String> ids) throws IllegalValueException {
        requireNonNull(ids);
        final ArrayList<InternalId> idSet = new ArrayList<>();

        try {
            for (String id : ids) {
                idSet.add(new InternalId(Integer.parseInt(id)));
            }
        } catch (NumberFormatException nfe) {
            throw new IllegalValueException("Please make sure teh person id is a valid number");
        }

        if (idSet.isEmpty()) {
            throw new IllegalValueException("Invalid command format! \n"
                    + "addMeeting: Adds a meeting to the address book. Parameters: on DATE from TIME at LOCATION about "
                    + "NOTES with PERSON 1 with PERSON 2 ...\n"
                    + "Example: addMeeting on 20/11/2017 from 1800 at UTown Starbucks about Project Meeting with 1");
        }

        return idSet;
    }

    /**
     * Parses {@code String userInput} into {@code String accessCode}.
     */
    public static String parseAccessCode(String userInput) throws IllegalValueException {
        requireNonNull(userInput);
        String trimmedInput = userInput.trim();
        String [] code = trimmedInput.split(" ");

        if (!isAccessCodeValid(code[FIRST_ENTRY])) {
            throw new IllegalValueException("Please make sure the access code you have copied follows the format:\n"
                    + "DIGIT/ALPHANUMERICS");
        }

        return code[FIRST_ENTRY];
    }

    /**
     * check if access code is of a valid format
     * @param accessCode
     * @return isAccessCodeValid True if accessCode is of valid format
     */
    private static boolean isAccessCodeValid(String accessCode) {
        boolean isAccessCodeValid = true;

        String [] splitAccessCode = accessCode.split("/");

        try {

            int firstPartOfAccessCode = Integer.parseInt(splitAccessCode[FIRST_ENTRY]);

            if (!accessCode.contains("/") || firstPartOfAccessCode < LOWER_LIMIT
                    || firstPartOfAccessCode > UPPER_LIMIT) {
                isAccessCodeValid = false;
            }
        } catch (Exception e) {
            isAccessCodeValid = false;
        }

        return isAccessCodeValid;
    }
}
```
###### /java/seedu/address/logic/parser/SetUniqueKeyCommandParser.java
``` java
/**
 * Parses the user inputted access code, which is given by Asana upon {@code setupAsana}
 */
public class SetUniqueKeyCommandParser implements Parser<SetUniqueKeyCommand> {
    @Override
    public SetUniqueKeyCommand parse(String userInput) throws ParseException {
        String accessCode;
        try {
            accessCode = ParserUtil.parseAccessCode(userInput);
        } catch (IllegalValueException ive) {
            throw new ParseException("Please make sure the access code you have copied follows the format:\n"
            + "DIGIT/ALPHANUMERICS");
        }

        return new SetUniqueKeyCommand(accessCode);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetUniqueKeyCommand); // instanceof handles nulls
    }

}
```
###### /java/seedu/address/logic/commands/AddMeetingCommand.java
``` java
/**
 * Adds a new meeting to the address book.
 */
public class AddMeetingCommand extends Command {

    public static final String COMMAND_WORD = "addMeeting";
    public static final String COMMAND_ALIAS = "am";

    public static final String GOOGLE_ADDRESS = "www.google.com";

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


    public static final String MESSAGE_SUCCESS_BOTH = "New meeting added locally and to Asana!";
    public static final String MESSAGE_SUCCESS_NO_INET = "New meeting added locally!\n "
            + "Connect to the internet to post the meeting on Asana.";
    public static final String MESSAGE_SUCCESS_ASANA_NO_CONFIG = "New meeting added locally!\n"
            + "Setup Asana to post the meeting on Asana.";
    public static final String MESSAGE_SUCCESS_LOCAL = "New meeting added locally!\n"
            + "Connect to the internet and setup Asana to post a meeting on Asana.";
    public static final String MESSAGE_DUPLICATE_MEETING = "This meeting already exists in the address book";
    public static final String MESSAGE_INVALID_ID = "Please input a valid person id!";
    public static final String MESSAGE_TEMPLATE = COMMAND_WORD + " "
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
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        AsanaCredentials asanaCredentials = new AsanaCredentials();

        //if there is internet connection && asana is configured
        if (isThereInternetConnection() && asanaCredentials.getIsAsanaConfigured()) {

            try {

                //add meeting on Asana
                PostTask newAsanaTask = null;
                try {
                    newAsanaTask = new PostTask(toAdd.getNotes(), toAdd.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                newAsanaTask.execute();

                //add meeting locally
                model.addMeeting(toAdd);

            } catch (DuplicateMeetingException e) {
                throw new CommandException(MESSAGE_DUPLICATE_MEETING);
            } catch (IllegalIdException ive) {
                throw new CommandException(MESSAGE_INVALID_ID);
            }

            return new CommandResult(String.format(MESSAGE_SUCCESS_BOTH, toAdd));
        } else {
            //only add meeting locally, not on Asana
            try {
                model.addMeeting(toAdd);
            } catch (DuplicateMeetingException e) {
                throw new CommandException(MESSAGE_DUPLICATE_MEETING);
            } catch (IllegalIdException ive) {
                throw new CommandException(MESSAGE_INVALID_ID);
            }

            //there is a stable internet connection but Asana is not configured
            if (isThereInternetConnection() && !asanaCredentials.getIsAsanaConfigured()) {
                return new CommandResult(MESSAGE_SUCCESS_ASANA_NO_CONFIG);
            } else if (!isThereInternetConnection() && asanaCredentials.getIsAsanaConfigured()) {
                //No internet connection but Asana is configured
                return new CommandResult(MESSAGE_SUCCESS_NO_INET);
            } else {
                //There is no internet connection and Asana is not configured
                return new CommandResult(MESSAGE_SUCCESS_LOCAL);
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddMeetingCommand // instanceof handles nulls
                && toAdd.equals(((AddMeetingCommand) other).toAdd));
    }

    /**
     * Check if there is an internet connection available
     * @return isThereInternetCOnnection, true if there is a connection and false otherwise
     */
    private boolean isThereInternetConnection() {
        Socket sock = new Socket();
        InetSocketAddress addr = new InetSocketAddress(GOOGLE_ADDRESS, 80);
        try {
            sock.connect(addr, 300);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                sock.close();
            } catch (IOException e) {
                return false;
            }
        }
    }
}
```
###### /java/seedu/address/logic/commands/SetupAsanaCommand.java
``` java
/**
 * Initiates Authorisation with Asana on Asana's website
 */
public class SetupAsanaCommand extends Command {
    public static final String COMMAND_WORD = "setupAsana";
    public static final String COMMAND_ALIAS = "sa";

    public static final boolean CONFIGURED = true;

    public static final String MESSAGE_SUCCESS = "1. Login & allow OurAB to access your Asana account\n"
            + "2. Copy from the site, the code: DIGIT/ALPHANUMERICS\n"
            + "Example: 0/123a689ny8912h324h78s\n"
            + "3. Type: setKey 0/ALPHANUMERICS";
    public static final String MESSAGE_TEMPLATE = COMMAND_WORD;

    @Override
    public CommandResult execute() throws CommandException {

        try {

            new AuthenticateAsanaUser();

            AsanaCredentials asanaCredentials = new AsanaCredentials();
            asanaCredentials.setIsAsanaConfigured(CONFIGURED);

        } catch (URISyntaxException e) {
            throw new CommandException("Failed to redirect to Asana's page. Please try again later!");
        } catch (IOException e) {
            throw new CommandException("Asana setup failed due to bad input. Please try again later!");
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### /java/seedu/address/logic/commands/SetUniqueKeyCommand.java
``` java
/**
 * Sets Unique key produced by Asana on Asana's webpage
 */
public class SetUniqueKeyCommand extends Command {

    public static final String COMMAND_WORD = "setKey";
    public static final String COMMAND_ALIAS = "sk";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " DIGIT/ALPHANUMERICS";


    public static final String MESSAGE_SUCCESS = "Asana setup successful!";
    public static final String MESSAGE_TEMPLATE = COMMAND_WORD + " DIGIT/ALPHANUMERICS";
    private final String userAccessCode;

    public SetUniqueKeyCommand(String code) {
        userAccessCode = code;
    }

    @Override
    public CommandResult execute() throws CommandException {

        try {
            new StoreAccessToken(userAccessCode);
        } catch (IOException e) {
            throw new CommandException("Please try again with a valid code from Asana");
        } catch (IllegalArgumentException iae) {
            throw new CommandException("Please try again with a valid code from Asana");
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetUniqueKeyCommand // instanceof handles nulls
                && this.userAccessCode.equals(((SetUniqueKeyCommand) other).userAccessCode)); // state check
    }

}
```
###### /java/seedu/address/logic/commands/DeleteTagCommand.java
``` java
/**
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
###### /java/seedu/address/model/ReadOnlyMeeting.java
``` java
/**
 * A read-only immutable interface for a Meeting in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyMeeting {
    String getDate();
    String getTime();
    String getDateTimeStr();
    String getLocation();
    String getNotes();
    LocalDateTime getDateTime();
    ArrayList<InternalId> getListOfPersonsId();


    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyMeeting other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getDateTimeStr().equals(this.getDateTimeStr()) // state checks here onwards
                && other.getLocation().equals(this.getLocation())
                && other.getNotes().equals(this.getNotes()));
    }

    int compareTo(Meeting other);
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
    //sorting methods
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
###### /java/seedu/address/model/exceptions/AsanaAuthenticationException.java
``` java
/**
 * Raises an exception when authentication/authorization fails
 */
public class AsanaAuthenticationException extends IllegalValueException {
    public AsanaAuthenticationException(String message) {
        super(message);
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
    /** Raises an event to indicate the model has changed */
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
###### /java/seedu/address/model/asana/StoreAccessToken.java
``` java
/**
 * Stores AccessToken from user input
 */
public class StoreAccessToken {

    private final AsanaCredentials asanaCredentials = new AsanaCredentials();

    public StoreAccessToken(String accessCode) throws IOException {
        String accessToken = retrieveToken(accessCode);
        asanaCredentials.setAccessToken(accessToken);
    }

    /***
     * Retrieve access token using userAccessCode if it is valid
     */
    private String retrieveToken(String accessCode) throws IOException {
        OAuthApp app = new OAuthApp(asanaCredentials.getClientId(), asanaCredentials.getClientSecret(),
                asanaCredentials.getRedirectUri());
        Client.oauth(app);
        String accessToken = app.fetchToken(accessCode);

        //check if user input is valid by testing if accesscode given by user successfully authorises the application
        if (!(app.isAuthorized())) {
            throw new IllegalArgumentException();
        }

        return accessToken;
    }

}
```
###### /java/seedu/address/model/asana/PostTask.java
``` java
/***
 * Posts a task onto users meeting project on Asana
 */
@Ignore
public class PostTask extends Command {

    private String notes;
    private String date;

    public PostTask(String notes, String localDate) throws ParseException {
        this.notes = notes;
        this.date = dateFormatter(localDate);
    }
    @Override
    public CommandResult execute() throws CommandException {

        Client client;
        try {
            new CheckAuthenticateAsanaUser();
            client = Client.accessToken((new AsanaCredentials()).getAccessToken());
            //get user data
            User user = client.users.me().execute();

            // find user's "Personal Projects" project //default asana personal workspace
            Workspace meetingsWorkspace = null;
            for (Workspace workspace : client.workspaces.findAll()) {
                if (workspace.name.equals("Personal Projects")) {
                    meetingsWorkspace = workspace;
                    break;
                }
            }

            if (meetingsWorkspace == null) {
                throw new CommandException("Please create a workspace called "
                        + "\"Personal Projects\" in your Asana account");
            }

            // create a "Meetings" if it doesn't exist
            List<Project> projects = client.projects.findByWorkspace(meetingsWorkspace.id).execute();
            Project myMeetings = null;
            for (Project project : projects) {
                if (project.name.equals("Meetings")) {
                    myMeetings = project;
                    break;
                }
            }
            if (myMeetings == null) {
                myMeetings = client.projects.createInWorkspace(meetingsWorkspace.id)
                        .data("name", "Meetings")
                        .execute();
            }

            // create a task in the project
            client.tasks.createInWorkspace(meetingsWorkspace.id)
                    .data("name", notes)
                    .data("projects", Arrays.asList(myMeetings.id))
                    .data("due_on", date)
                    .data("assignee", user)
                    .execute();

        } catch (IOException io) {
            throw new CommandException("Please setup Asana again!");
        } catch (AsanaAuthenticationException e) {
            throw new CommandException(e.getMessage());
        }

        return new CommandResult("");
    }

    /**
     * Formats date to suit input needs of Asana API
     */
    private String dateFormatter (String date) throws ParseException {

        DateFormat dateParse = new SimpleDateFormat("yyyy/mm/dd");

        Date formattedDate = dateParse.parse(date);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        return dateFormat.format(formattedDate);

    }

}
```
###### /java/seedu/address/model/asana/CheckAuthenticateAsanaUser.java
``` java
/**
 * Authenticate & Store access token for Asana
 */
public class CheckAuthenticateAsanaUser {

    private static String currentAccessToken;

    public CheckAuthenticateAsanaUser() throws AsanaAuthenticationException {
        if (!isAuthenticated()) {
            throw new AsanaAuthenticationException("Please make sure you have allowed "
                    + "OurAB to access your Asana account");
        }
    }

    /**
     * checks if user is authenticated by Asana
     */
    private boolean isAuthenticated() {
        currentAccessToken = new AsanaCredentials().getAccessToken();
        return !(currentAccessToken == null);
    }

}
```
###### /java/seedu/address/model/asana/AuthenticateAsanaUser.java
``` java
/**
 * Authenticates Asana user by redirecting user to Asana's webpage
 */
public class AuthenticateAsanaUser {

    public AuthenticateAsanaUser () throws URISyntaxException, IOException {
        AsanaCredentials asanaCredentials = new AsanaCredentials();
        OAuthApp app = new OAuthApp(asanaCredentials.getClientId(), asanaCredentials.getClientSecret(),
                asanaCredentials.getRedirectUri());
        Client client = Client.oauth(app);

        //to prevent CSRF attacks
        String currentState = UUID.randomUUID().toString();
        String url = app.getAuthorizationUrl(currentState);

        //open browser on desktop for authentication purpose --> Asana to show authorisation key
        Desktop.getDesktop().browse(new URI(url));

    }
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
    //// Sort methods
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
###### /java/seedu/address/storage/asana/storage/AsanaCredentials.java
``` java
/***
 * Stores all the relevant data required for Asana
 */
public class AsanaCredentials {
    private static final String CLIENT_ID = "474342738710406";
    private static final String CLIENT_SECRET = "a89bbb49213d6b58ebce25cfa0995290";
    private static final String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
    private static boolean isAsanaConfigured = false;
    private static String hashedToken;

    public AsanaCredentials() {
        hashedToken = null;
    }

    /**
     * Getter method for Access token
     */
    public String getAccessToken() {
        byte[] decoded = Base64.getDecoder().decode(hashedToken);
        String accessToken = new String(decoded, StandardCharsets.UTF_8);
        return accessToken;
    }

    /**
     * Getter method for CLIENT_ID
     */
    public String getClientId() {
        return CLIENT_ID;
    }

    /**
     * Getter method for Access token
     */
    public String getClientSecret() {
        return CLIENT_SECRET;
    }

    /**
     * Getter method for Access token
     */
    public String getRedirectUri() {
        return REDIRECT_URI;
    }

    /**
     * Setter method for access token
     */
    public void setAccessToken(String accessToken) {
        byte[] message = accessToken.getBytes(StandardCharsets.UTF_8);
        String encoded = Base64.getEncoder().encodeToString(message);
        hashedToken = encoded;
    }

    /**
     * Setter method for isAsanaConfigured
     */
    public void setIsAsanaConfigured(boolean value) {
        isAsanaConfigured = value;
    }

    /**
     * Getter method for isAsanaConfigured
     */
    public boolean getIsAsanaConfigured() {
        return isAsanaConfigured;
    }

}
```
