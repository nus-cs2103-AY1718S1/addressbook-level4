# leonchowwenhao
###### \java\seedu\address\commons\events\ui\TogglePanelEvent.java
``` java

/**
 * Indicates a request to toggle between event display and browser.
 */
public class TogglePanelEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\ToggleSelectEvent.java
``` java

/**
 * Indicates that a select command has been entered and a request to toggle browser to the front.
 */
public class ToggleSelectEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\logic\commands\DeleteEventCommand.java
``` java
/**
 * Deletes an event identified using it's last displayed index from the address book.
 */
public class DeleteEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteE";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the event identified by the index number used in the last event listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event: %1$s";
    public static final String MESSAGE_EVENT_BEING_PARTICIPATED_FAIL = "Some person participates this event,"
            + "please disjoin all participated persons before deleting this event";

    private final Index targetIndex;
    private ReadOnlyEvent eventToDelete;

    public DeleteEventCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyEvent> lastShownList = model.getFilteredEventList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        eventToDelete = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.deleteEvent(eventToDelete);
        } catch (EventNotFoundException pnfe) {
            assert false : "The target event cannot be missing";
        } catch (DeleteOnCascadeException doce) {
            throw new CommandException(MESSAGE_EVENT_BEING_PARTICIPATED_FAIL);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteEventCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteEventCommand) other).targetIndex)); // state check
    }

    @Override
    protected void undo() {
        requireAllNonNull(model, eventToDelete);
        model.addEvent(targetIndex.getZeroBased(), eventToDelete);
    }

    @Override
    protected void redo() {
        requireAllNonNull(model, eventToDelete);
        try {
            model.deleteEvent(eventToDelete);
        } catch (EventNotFoundException pnfe) {
            throw new AssertionError(MESSAGE_REDO_ASSERTION_ERROR);
        } catch (DeleteOnCascadeException doce) {
            throw new AssertionError(MESSAGE_REDO_ASSERTION_ERROR);
        }
    }

}
```
###### \java\seedu\address\logic\commands\DisplayCommand.java
``` java
/**
 * Selects an event identified using it's last displayed index from the address book
 * and displays the emails of every person that has joined.
 */
public class DisplayCommand extends Command {

    public static final String COMMAND_WORD = "display";
    public static final String PARTICULAR_EMAIL = "email";
    public static final String PARTICULAR_PHONE = "phone";
    public static final String PARTICULAR_ADDRESS = "address";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the event identified by the index number used in the last event listing "
            + "and displays the target particular of every person that has joined.\n"
            + "Parameters: INDEX (must be a positive integer) PARTICULAR (either email, phone, or address)"
            + "Example: " + COMMAND_WORD + " 1 " + PARTICULAR_EMAIL;
    public static final String MESSAGE_NO_PARTICIPANT = "No one has joined this event.";
    public static final String MESSAGE_INVALID_PARTICULAR = "%1$s is not a valid particular. Please type \""
        + PARTICULAR_EMAIL + "\", \"" + PARTICULAR_PHONE + "\", or \"" + PARTICULAR_ADDRESS + "\" instead.";

    private final Index targetIndex;
    private final String particular;

    public DisplayCommand(Index targetIndex, String particular) {
        this.targetIndex = targetIndex;
        this.particular = particular;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyEvent> lastShownList = model.getFilteredEventList();
        String result = "Details: ";
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        if (!lastShownList.get(targetIndex.getZeroBased()).getParticipants().isEmpty()) {
            switch (particular) {

            case PARTICULAR_EMAIL:
                for (Person person : lastShownList.get(targetIndex.getZeroBased()).getParticipants()) {
                    result += person.getEmail().value + "\n\t     ";
                }
                return new CommandResult(result.trim());

            case PARTICULAR_PHONE:
                for (Person person : lastShownList.get(targetIndex.getZeroBased()).getParticipants()) {
                    result += person.getPhone().value + "\n\t     ";
                }
                return new CommandResult(result.trim());

            case PARTICULAR_ADDRESS:
                for (Person person : lastShownList.get(targetIndex.getZeroBased()).getParticipants()) {
                    result += person.getAddress().value + "\n\t     ";
                }
                return new CommandResult(result.trim());

            default:
                return new CommandResult(String.format(MESSAGE_INVALID_PARTICULAR, particular));
            }

        } else {
            return new CommandResult(MESSAGE_NO_PARTICIPANT);
        }


    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DisplayCommand // instanceof handles nulls
                && this.targetIndex.equals(((DisplayCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\SelectJoinedEventsCommand.java
``` java
/**
 * Selects a person identified using it's last displayed index from the address book
 * and displays all events the person has joined.
 */
public class SelectJoinedEventsCommand extends Command {

    public static final String COMMAND_WORD = "selectE";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the person identified by the index number used in the last person listing "
            + "and displays all events the person has joined.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final List<Index> indexList;

    public SelectJoinedEventsCommand(List<Index> indexList) {
        this.indexList = indexList;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        StringBuilder eventNames = new StringBuilder();
        StringBuilder personNames = new StringBuilder();

        personNames.append("For ");

        for (Index targetIndex: indexList) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            personNames.append(lastShownList.get(targetIndex.getZeroBased()).getName()).append(", ");
            for (ReadOnlyEvent event: lastShownList.get(targetIndex.getZeroBased()).getParticipation()) {
                eventNames.append(event.getEventName()).append("[-]");
            }
        }

        String[] eventNameKeywords = (eventNames.toString().trim()).split("\\[-]+");
        EventContainsKeywordPredicate predicate = new EventContainsKeywordPredicate(Arrays.asList(eventNameKeywords));
        model.updateFilteredEventList(predicate);

        personNames.append(getMessageForEventListShownSummary(model.getFilteredEventList().size()));
        return new CommandResult(personNames.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectJoinedEventsCommand // instanceof handles nulls
                && this.indexList.equals(((SelectJoinedEventsCommand) other).indexList)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case SelectJoinedEventsCommand.COMMAND_WORD:
            return new SelectJoinedEventsCommandParser().parse(arguments);

```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case DeleteEventCommand.COMMAND_WORD:
            return new DeleteEventCommandParser().parse(arguments);

```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case DisplayCommand.COMMAND_WORD:
            return new DisplayCommandParser().parse(arguments);

```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case ToggleCommand.COMMAND_WORD:
            return new ToggleCommand();

        //@@ author
        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
```
###### \java\seedu\address\logic\parser\DeleteEventCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteEventCommandParser implements Parser<DeleteEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteEventCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteEventCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\logic\parser\DisplayCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DisplayCommand object
 */
public class DisplayCommandParser implements Parser<DisplayCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DisplayCommand
     * and returns an DisplayCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DisplayCommand parse(String args) throws ParseException {
        try {
            String[] splitArgs = args.trim().split(" ");
            Index index = ParserUtil.parseIndex(splitArgs[0]);
            String particular = splitArgs[1];
            return new DisplayCommand(index, particular);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\SelectJoinedEventsCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SelectJoinedEventsCommand object
 */
public class SelectJoinedEventsCommandParser implements Parser<SelectJoinedEventsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectJoinedEventsCommand
     * and returns an SelectJoinedEventsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectJoinedEventsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectJoinedEventsCommand.MESSAGE_USAGE));
        }
        String[] indexes = trimmedArgs.split("\\s+");
        try {
            List<Index> indexList = new ArrayList<>();
            for (String test : indexes) {
                Index index = ParserUtil.parseIndex(test);
                indexList.add(index);
            }
            return new SelectJoinedEventsCommand(indexList);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectJoinedEventsCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\model\event\EventContainsKeywordPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyEvent}'s {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class EventContainsKeywordPredicate implements Predicate<ReadOnlyEvent> {
    private final List<String> keywords;

    public EventContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyEvent event) {
        Boolean isSelected = false;
        if (!event.getParticipants().isEmpty()) {
            isSelected = keywords.stream().anyMatch(keyword ->
                    event.getEventName().fullEventName.equalsIgnoreCase(keyword));
        }
        return isSelected;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventContainsKeywordPredicate // instanceof handles nulls
                && this.keywords.equals(((EventContainsKeywordPredicate) other).keywords)); // state check
    }
}
```
###### \java\seedu\address\model\person\Birthday.java
``` java
/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Person birthdays should be 6-8 numbers separated by '/', '-' or '.' with the format of day/month/year"
            + "\nExamples of valid format: 01/12/1990, 6-3-1991, 18-07-1992";
    public static final String BIRTHDAY_VALIDATION_REGEX = "^(0[1-9]|[12][\\d]|3[01]|[1-9])[///./-]"
            + "(0[1-9]|1[012]|[1-9])[///./-](19|20)\\d\\d$";

    public final String value;

    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given birthday string is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        if (birthday.equals("")) {
            this.value = birthday;
        } else {
            String trimmedBirthday = birthday.trim();
            if (!isValidBirthday(trimmedBirthday)) {
                throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
            }
            this.value = trimmedBirthday;
        }
    }

    /**
     * Returns if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {
        return (isValidBirthdayFormat(test) && isValidDate(test));
    }

    /**
     * Returns if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        Boolean result = false;
        String[] birthdayParts = splitBirthday(test);
        int day = Integer.parseInt(birthdayParts[0]);
        int month = Integer.parseInt(birthdayParts[1]);
        int year = Integer.parseInt(birthdayParts[2]);

        if (month == 2) {
            if (isLeapYear(year) && day <= 29) {
                result = true;
            } else if (day <= 28) {
                result = true;
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day <= 30) {
                result = true;
            }
        } else {
            result = true;
        }

        return (result);
    }

    /**
     * Returns if a given string has a valid person birthday format.
     */
    public static boolean isValidBirthdayFormat(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX);
    }

    /**
     * Returns an array with String split into 3 parts.
     */
    public static String[] splitBirthday(String test) {
        String[] birthdayParts = test.split("[///./-]");

        return birthdayParts;
    }

    /**
     * Returns if a given int is a leap year.
     */
    public static boolean isLeapYear(int year) {
        Boolean result = false;

        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            result = true;
        }

        return result;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && this.value.equals(((Birthday) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    // ================ EventStorage methods ==============================

    @Override
    public String getEventStorageFilePath() {
        return eventStorage.getEventStorageFilePath();
    }

    @Override
    public Optional<ReadOnlyEventList> readEventStorage() throws DataConversionException, IOException {
        return readEventStorage(eventStorage.getEventStorageFilePath());
    }

    @Override
    public Optional<ReadOnlyEventList> readEventStorage(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return eventStorage.readEventStorage(filePath);
    }

    @Override
    public void saveEventStorage(ReadOnlyEventList eventStore) throws IOException {
        saveEventStorage(eventStore, eventStorage.getEventStorageFilePath());
    }

    @Override
    public void saveEventStorage(ReadOnlyEventList eventStore, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        eventStorage.saveEventStorage(eventStore, filePath);
    }


    @Override
    @Subscribe
    public void handleEventStorageChangedEvent(EventStorageChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveEventStorage(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
```
###### \java\seedu\address\storage\XmlAdaptedEvent.java
``` java
/**
 * JAXB-friendly version of the Event.
 */
public class XmlAdaptedEvent {

    @XmlElement(required = true)
    private String eventName;
    @XmlElement
    private String eventDesc;
    @XmlElement(required = true)
    private String eventTime;

    @XmlElement
    private List<XmlAdaptedPersonNoParticipation> participants = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedEvent.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEvent() {}


    /**
     * Converts a given Event into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedEvent
     */
    public XmlAdaptedEvent(ReadOnlyEvent source) {
        eventName = source.getEventName().fullEventName;
        eventDesc = source.getDescription().eventDesc;
        eventTime = source.getEventTime().eventTime;
        participants = new ArrayList<>();
        for (Person participant : source.getParticipants()) {
            participants.add(new XmlAdaptedPersonNoParticipation(participant));
        }
    }

    /**
     * Converts this jaxb-friendly adapted event object into the model's Event object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event
     */
    public Event toModelType() throws IllegalValueException {
        final List<Person> eventParticipants = new ArrayList<>();
        for (XmlAdaptedPersonNoParticipation participant : participants) {
            eventParticipants.add(participant.toModelType());
        }
        final EventName eventName = new EventName(this.eventName);
        final EventDescription eventDesc = new EventDescription(this.eventDesc);
        final EventTime eventTime = new EventTime(this.eventTime);
        final Set<Person> participants = new HashSet<>(eventParticipants);
        return new Event(eventName, eventDesc, eventTime, participants);
    }
}
```
###### \java\seedu\address\storage\XmlEventStorage.java
``` java
/**
 * A class to access EventStorage data stored as an xml file on the hard disk.
 */
public class XmlEventStorage implements EventStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlEventStorage.class);

    private String filePath;

    public XmlEventStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getEventStorageFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyEventList> readEventStorage() throws DataConversionException, IOException {
        return readEventStorage(filePath);
    }

    /**
     * Similar to {@link #readEventStorage()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyEventList> readEventStorage(String filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        File eventStorageFile = new File(filePath);

        if (!eventStorageFile.exists()) {
            logger.info("EventStorage file "  + eventStorageFile + " not found");
            return Optional.empty();
        }

        ReadOnlyEventList eventStorageOptional = XmlFileStorage.loadDataFromSaveFileEventStorage(new File(filePath));

        return Optional.of(eventStorageOptional);
    }

    @Override
    public void saveEventStorage(ReadOnlyEventList eventStorage) throws IOException {
        saveEventStorage(eventStorage, filePath);
    }

    /**
     * Similar to {@link #saveEventStorage(ReadOnlyEventList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveEventStorage(ReadOnlyEventList eventStorage, String filePath) throws IOException {
        requireNonNull(eventStorage);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFileEventStorage(file, new XmlSerializableEventStorage(eventStorage));
    }

}
```
###### \java\seedu\address\storage\XmlSerializableEventStorage.java
``` java
/**
 * An Immutable EventStorage that is serializable to XML format
 */
@XmlRootElement(name = "eventstorage")
public class XmlSerializableEventStorage implements ReadOnlyEventList {

    @XmlElement
    private List<XmlAdaptedEvent> events;

    /**
     * Creates an empty XmlSerializableEventStorage.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableEventStorage() {
        events = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableEventStorage(ReadOnlyEventList src) {
        this();
        events.addAll(src.getEventList().stream().map(XmlAdaptedEvent::new).collect(Collectors.toList()));
    }

    @Override
    public ObservableList<ReadOnlyEvent> getEventList() {
        final ObservableList<ReadOnlyEvent> events = this.events.stream().map(ev -> {
            try {
                return ev.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(events);
    }

}
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
        raise(new ToggleSelectEvent());
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
        TogglePanel togglePanel = new TogglePanel(browserPanel, informationBoard, eventListPanel);
        togglePlaceHolder.getChildren().add(togglePanel.getRoot());

```
###### \java\seedu\address\ui\TogglePanel.java
``` java

/**
 * A ui containing browserpanel, informationboard and eventlistpanel.
 * This UI is also responsible for handling toggle request events, switching between displaying details or the browser.
 */
public class TogglePanel extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(TogglePanel.class);
    private static final String FXML = "TogglePanel.fxml";

    private boolean browserIsFront;

    @FXML
    private SplitPane toggleSplitPane;

    @FXML
    private StackPane browserPlaceHolder;

    @FXML
    private StackPane informationBoardPlaceHolder;

    @FXML
    private StackPane eventListPanelPlaceHolder;

    public TogglePanel(BrowserPanel browserPanel, InformationBoard informationBoard,
                       EventListPanel eventListPanel) {
        super(FXML);
        browserPlaceHolder.getChildren().add(browserPanel.getRoot());
        informationBoardPlaceHolder.getChildren().add(informationBoard.getRoot());
        eventListPanelPlaceHolder.getChildren().add(eventListPanel.getRoot());
        browserIsFront = false;
        registerAsAnEventHandler(this);
    }

    @Subscribe
    public void handleToggleSelectEvent(ToggleSelectEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        browserToFront();
    }

    @Subscribe
    public void handleTogglePanelEvent(TogglePanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        triggerToggle();
    }

    /**
    * Triggers the toggling mechanism to switch the browserPlaceHolder and the splitPane.
    */
    private void triggerToggle() {
        if (browserIsFront) {
            browserToBack();
        } else {
            browserToFront();
        }
    }

    /**
     * Sets the browserPlaceHolder to be visible and in front
     * while setting the splitPane to be invisible and at the back.
     */
    private void browserToFront() {
        browserPlaceHolder.setVisible(true);
        browserPlaceHolder.toFront();
        toggleSplitPane.setVisible(false);
        toggleSplitPane.toBack();
        browserIsFront = true;
    }

    /**
     * Sets the browserPlaceHolder to be invisible and at the back
     * while setting the splitPane to be visible and in front.
     */
    private void browserToBack() {
        browserPlaceHolder.setVisible(false);
        browserPlaceHolder.toBack();
        toggleSplitPane.setVisible(true);
        toggleSplitPane.toFront();
        browserIsFront = false;
    }
}
```
