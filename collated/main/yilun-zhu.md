# yilun-zhu
###### \java\seedu\address\commons\events\external\AddEventRequestEvent.java
``` java

/**
 * Indicates a request for adding an calendar event
 */

public class AddEventRequestEvent extends BaseEvent {

    private CalendarEvent calendarEvent;

    public AddEventRequestEvent(CalendarEvent calendarEvent) {
        this.calendarEvent = calendarEvent;
    }

    public CalendarEvent getCalendarEvent() {
        return calendarEvent;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\CalendarRequestEvent.java
``` java
/**
 * Indicates a request for Calendar
 */

public class CalendarRequestEvent extends BaseEvent {

    public CalendarRequestEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\external\addevent\AddEventManager.java
``` java
/**
 * Follows Singleton and Facade design pattern,
 * for other parts of the app to interact with the google calendar API service
 */
public class AddEventManager {

    private static AddEventManager instance = null;

    protected AddEventManager() {
        registerAsAnEventHandler(this);
    }

    /**
     * Registers the object as an event handler at the {@link EventsCenter}
     * @param handler usually {@code this}
     */
    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(handler);
    }

    /**
     * Creates an instance of the CalendarApi and registers it as an event handler
     * @return The Singleton instance of the CalendarApi
     */
    public static AddEventManager init() {
        if (instance == null) {
            instance = new AddEventManager();
        }
        return instance;
    }

    @Subscribe
    public static void handleAddEventRequestEvent(AddEventRequestEvent addEventRequestEvent) throws IOException {
        CalendarEvent eventSent = addEventRequestEvent.getCalendarEvent();
        CalendarApi.createEvent(eventSent);
    }


}
```
###### \java\seedu\address\external\addevent\CalendarApi.java
``` java
/** Calls Calendar API **/

public class CalendarApi {

    private static final Logger logger = LogsCenter.getLogger(CalendarApi.class);
    /** Application name. */
    private static final String APPLICATION_NAME =
            "Google Calendar API Java Quickstart";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/calendar-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory dataStoreFactory;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport httpTransport;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/calendar-java-quickstart
     */
    private static final List<String> SCOPES =
            Arrays.asList(CalendarScopes.CALENDAR);

    static {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
                CalendarApi.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(dataStoreFactory)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Calendar client service.
     * @return an authorized Calendar client service
     * @throws IOException
     */
    public static com.google.api.services.calendar.Calendar getCalendarService() throws IOException {
        Credential credential = authorize();
        return new com.google.api.services.calendar.Calendar.Builder(
                httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Build and return an authorized Calendar client service.
     * @throws IOException
     */
    public static void createEvent(CalendarEvent eventSent) throws IOException {
        com.google.api.services.calendar.Calendar service =
                getCalendarService();

        String nameSent = eventSent.getEventName().toString();
        String startDate = eventSent.getStartDate().toString();
        String startTime = eventSent.getStartTime().toString() + ":00+08:00";
        String endDate = eventSent.getEndDate().toString();
        String endTime = eventSent.getEndTime().toString() + ":00+08:00";

        Event event = new Event()
                .setSummary(nameSent);

        DateTime startDateTime = new DateTime(startDate + "T" + startTime);
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Singapore");
        event.setStart(start);

        DateTime endDateTime = new DateTime(endDate + "T" + endTime);
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Singapore");
        event.setEnd(end);
        String calendarId = "primary";

        service.events().insert(calendarId, event).execute();

        EventsCenter.getInstance().post(new NewResultAvailableEvent(MESSAGE_ADD_EVENT_SUCCESS));
        EventsCenter.getInstance().post(new CalendarRequestEvent());
        logger.info("Event created: " + eventSent.toString());
    }

}
```
###### \java\seedu\address\logic\commands\AddEventCommand.java
``` java
/**
 * Adds a event to the google calendar.
 */
public class AddEventCommand extends Command {

    public static final String COMMAND_WORD = "addevent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a event to the google calendar. "
            + "Parameters: "
            + PREFIX_EVENT_NAME + "EVENT NAME "
            + PREFIX_EVENT_START_DATE + "START DATE "
            + PREFIX_EVENT_START_TIME + "START TIME "
            + PREFIX_EVENT_END_DATE + "END DATE "
            + PREFIX_EVENT_END_TIME + "END TIME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_NAME + "Halloween Party "
            + PREFIX_EVENT_START_DATE + "2015-07-08 "
            + PREFIX_EVENT_START_TIME + "15:00 "
            + PREFIX_EVENT_END_DATE + "2015-07-08 "
            + PREFIX_EVENT_END_TIME + "18:00";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";


    private final CalendarEvent toAdd;

    /**
     * Creates an AddEventCommand to add the specified {@code ReadOnlyCalendarEvent}
     */
    public AddEventCommand(ReadOnlyCalendarEvent event) {
        toAdd = new CalendarEvent(event);
    }

    @Override
    public CommandResult execute() {
        requireNonNull(toAdd);
        AddEventManager.init();
        EventsCenter.getInstance().post(new AddEventRequestEvent(toAdd));
        return new CommandResult(getMessageForAddEvent(toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && toAdd.equals(((AddEventCommand) other).toAdd));
    }
}
```
###### \java\seedu\address\logic\commands\CalendarCommand.java
``` java
/**
 * Opens the calendar panel.
 */

public class CalendarCommand extends Command {

    public static final String COMMAND_WORD = "calendar";
    public static final String MESSAGE_SUCCESS = "Calendar loaded!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows calendar ";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new CalendarRequestEvent());
        return new CommandResult(getMessageForCalendar());
    }
}
```
###### \java\seedu\address\logic\commands\Command.java
``` java
    public static String getMessageForCalendar() {
        StringBuilder mess = new StringBuilder(String.format(Messages.CALENDAR_MESSAGE));
        return mess.toString();
    }

    public static String getMessageForAddEvent(CalendarEvent toAdd) {
        StringBuilder mess = new StringBuilder(String.format(Messages.MESSAGE_ADD_EVENT_SUCCESS, toAdd));
        return mess.toString();
    }
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names / address / tags contain"
            + " any of the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob family Serangoon";
```
###### \java\seedu\address\logic\parser\AddEventCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddEventCommand object
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddEventCommand
     * and returns an AddEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_NAME, PREFIX_EVENT_START_DATE,
                        PREFIX_EVENT_START_TIME, PREFIX_EVENT_END_DATE, PREFIX_EVENT_END_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_NAME, PREFIX_EVENT_START_DATE, PREFIX_EVENT_START_TIME,
                PREFIX_EVENT_END_DATE, PREFIX_EVENT_END_TIME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        try {
            EventName name = ParserUtil.parseEventName(argMultimap.getValue(PREFIX_EVENT_NAME)).get();
            EventStartDate startDate = ParserUtil.parseStartDate(argMultimap.getValue(PREFIX_EVENT_START_DATE)).get();
            EventStartTime startTime = ParserUtil.parseStartTime(argMultimap.getValue(PREFIX_EVENT_START_TIME)).get();
            EventEndDate endDate = ParserUtil.parseEndDate(argMultimap.getValue(PREFIX_EVENT_END_DATE)).get();
            EventEndTime endTime = ParserUtil.parseEndTime(argMultimap.getValue(PREFIX_EVENT_END_TIME)).get();

            ReadOnlyCalendarEvent event = new CalendarEvent(name, startDate, startTime, endDate, endTime);

            return new AddEventCommand(event);
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
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case AddEventCommand.COMMAND_WORD:
            return new AddEventCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case CalendarCommand.COMMAND_WORD:
            return new CalendarCommand();
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<EventName>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<EventName> parseEventName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(new EventName(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> date} into an {@code Optional<EventStartDate>} if {@code date} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<EventStartDate> parseStartDate(Optional<String> date) throws IllegalValueException {
        requireNonNull(date);
        return date.isPresent() ? Optional.of(new EventStartDate(date.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> time} into an {@code Optional<EventStartDate>} if {@code time} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<EventStartTime> parseStartTime(Optional<String> time) throws IllegalValueException {
        requireNonNull(time);
        return time.isPresent() ? Optional.of(new EventStartTime(time.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> date} into an {@code Optional<EventEndDate>} if {@code date} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<EventEndDate> parseEndDate(Optional<String> date) throws IllegalValueException {
        requireNonNull(date);
        return date.isPresent() ? Optional.of(new EventEndDate(date.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> time} into an {@code Optional<EventStartDate>} if {@code time} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<EventEndTime> parseEndTime(Optional<String> time) throws IllegalValueException {
        requireNonNull(time);
        return time.isPresent() ? Optional.of(new EventEndTime(time.get())) : Optional.empty();
    }
```
###### \java\seedu\address\model\calendarevent\CalendarEvent.java
``` java
/**
 * Represents a calendar event in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class CalendarEvent implements ReadOnlyCalendarEvent {

    private ObjectProperty<EventName> name;
    private ObjectProperty<EventStartDate> startDate;
    private ObjectProperty<EventStartTime> startTime;
    private ObjectProperty<EventEndDate> endDate;
    private ObjectProperty<EventEndTime> endTime;


    /**
     * Every field must be present and not null.
     */
    public CalendarEvent(EventName name, EventStartDate startDate, EventStartTime startTime,
                         EventEndDate endDate, EventEndTime endTime) {
        requireAllNonNull(name, startDate, startTime, endDate, endTime);
        this.name = new SimpleObjectProperty<>(name);
        this.startDate = new SimpleObjectProperty<>(startDate);
        this.startTime = new SimpleObjectProperty<>(startTime);
        this.endDate = new SimpleObjectProperty<>(endDate);
        this.endTime = new SimpleObjectProperty<>(endTime);
    }

    /**
     * Creates a copy of the given ReadOnlyCalendarEvent.
     */
    public CalendarEvent(ReadOnlyCalendarEvent source) {
        this(source.getEventName(), source.getStartDate(), source.getStartTime(),
                source.getEndDate(), source.getEndTime());
    }

    public void setName(EventName name) {
        this.name.set(requireNonNull(name));
    }

    @Override
    public ObjectProperty<EventName> nameProperty() {
        return name;
    }

    @Override
    public EventName getEventName() {
        return name.get();
    }

    public void setStartDate(EventStartDate date) {
        this.startDate.set(requireNonNull(date));
    }

    public void setStartTime(EventStartTime time) {
        this.startTime.set(requireNonNull(time));
    }

    public void setEndDate(EventEndDate date) {
        this.endDate.set(requireNonNull(date));
    }

    public void setEndTime(EventEndTime time) {
        this.endTime.set(requireNonNull(time));
    }

    @Override
    public ObjectProperty<EventEndDate> endDateProperty() {
        return this.endDate;
    }

    @Override
    public ObjectProperty<EventEndTime> endTimeProperty() {
        return this.endTime;
    }

    @Override
    public ObjectProperty<EventStartDate> startDateProperty() {
        return this.startDate;
    }

    @Override
    public ObjectProperty<EventStartTime> startTimeProperty() {
        return this.startTime;
    }

    @Override
    public EventStartDate getStartDate() {
        return this.startDate.get();
    }

    @Override
    public EventStartTime getStartTime() {
        return this.startTime.get();
    }

    @Override
    public EventEndDate getEndDate() {
        return this.endDate.get();
    }

    @Override
    public EventEndTime getEndTime() {
        return this.endTime.get();
    }



    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyCalendarEvent // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyCalendarEvent) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, startDate, startTime, endDate, endTime);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
```
###### \java\seedu\address\model\calendarevent\EventEndDate.java
``` java
/**
 * Represents event end date in the calendar.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class EventEndDate {


    public static final String END_DATE_CONSTRAINTS =
            "Date should be in the format YYYY-MM-DD. Date should be valid. Eg. 2017-10-01";
    public static final String DATE_VALIDATION_REGEX = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";
    public final String value;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public EventEndDate(String date) throws IllegalValueException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!isValidDate(trimmedDate)) {
            throw new IllegalValueException(END_DATE_CONSTRAINTS);
        }
        this.value = trimmedDate;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventEndDate // instanceof handles nulls
                && this.value.equals(((EventEndDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\calendarevent\EventEndTime.java
``` java
/**
 * Represents event end time in the calendar.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class EventEndTime {


    public static final String END_TIME_CONSTRAINTS =
            "Time should be in the format HH:MM, and must be valid. Eg. 09:00";
    public static final String TIME_VALIDATION_REGEX = "^[0-2][0-9]:[0-5][0-9]$";
    public final String value;

    /**
     * Validates given time.
     *
     * @throws IllegalValueException if given time string is invalid.
     */
    public EventEndTime(String time) throws IllegalValueException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!isValidTime(trimmedTime)) {
            throw new IllegalValueException(END_TIME_CONSTRAINTS);
        }
        this.value = trimmedTime;
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventStartDate // instanceof handles nulls
                && this.value.equals(((EventStartDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\calendarevent\EventName.java
``` java
/**
 * Represents a event name in the address book calendar.
 * Guarantees: immutable; is valid as declared in {@link #isValidEventName(String)}
 */
public class EventName {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Event names should not be blank, and should not contain special characters";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String EVENT_NAME_VALIDATION_REGEX = "^[^-\\s][a-zA-Z0-9_\\s-]+$";

    public final String fullEventName;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public EventName(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidEventName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.fullEventName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid event name.
     */
    public static boolean isValidEventName(String test) {
        return test.matches(EVENT_NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullEventName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventName // instanceof handles nulls
                && this.fullEventName.equals(((EventName) other).fullEventName)); // state check
    }

    @Override
    public int hashCode() {
        return fullEventName.hashCode();
    }

}
```
###### \java\seedu\address\model\calendarevent\EventStartDate.java
``` java
/**
 * Represents event start date.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class EventStartDate {


    public static final String START_DATE_CONSTRAINTS =
            "Date should be in the format YYYY-MM-DD. Date should be valid. Eg. 2017-10-01";
    public static final String DATE_VALIDATION_REGEX = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";
    public final String value;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public EventStartDate(String date) throws IllegalValueException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!isValidDate(trimmedDate)) {
            throw new IllegalValueException(START_DATE_CONSTRAINTS);
        }
        this.value = trimmedDate;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventStartDate // instanceof handles nulls
                && this.value.equals(((EventStartDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\calendarevent\EventStartTime.java
``` java
/**
 * Represents event start time.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class EventStartTime {


    public static final String START_TIME_CONSTRAINTS =
            "Time should be in the format HH:MM, and must be valid. Eg. 09:00";
    public static final String TIME_VALIDATION_REGEX = "^[0-2][0-9]:[0-5][0-9]$";
    public final String value;

    /**
     * Validates given time.
     *
     * @throws IllegalValueException if given time string is invalid.
     */
    public EventStartTime(String time) throws IllegalValueException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!isValidTime(trimmedTime)) {
            throw new IllegalValueException(START_TIME_CONSTRAINTS);
        }
        this.value = trimmedTime;
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventStartDate // instanceof handles nulls
                && this.value.equals(((EventStartDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\calendarevent\ReadOnlyCalendarEvent.java
``` java
/**
 * A read-only immutable interface for a Person in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyCalendarEvent {

    ObjectProperty<EventName> nameProperty();
    EventName getEventName();
    ObjectProperty<EventStartDate> startDateProperty();
    EventStartDate getStartDate();
    ObjectProperty<EventStartTime> startTimeProperty();
    EventStartTime getStartTime();
    ObjectProperty<EventEndDate> endDateProperty();
    EventEndDate getEndDate();
    ObjectProperty<EventEndTime> endTimeProperty();
    EventEndTime getEndTime();


    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyCalendarEvent other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getEventName().equals(this.getEventName()) // state checks here onwards
                && other.getStartDate().equals(this.getStartDate())
                && other.getStartTime().equals(this.getStartTime())
                && other.getEndDate().equals(this.getEndDate())
                && other.getEndTime().equals(this.getEndTime()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getEventName())
                .append(" Start: ")
                .append(getStartDate())
                .append(" Time: ")
                .append(getEndTime())
                .append(" End: ")
                .append(getEndDate())
                .append(" Time: ")
                .append(getEndTime());
        return builder.toString();
    }

}
```
###### \java\seedu\address\model\person\FindFunctionPredicate.java
``` java
    @Override
    public boolean test(ReadOnlyPerson person) {
        boolean name = keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
        boolean address = keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getAddress().toString(), keyword));
        boolean email = keywords.stream()
                .anyMatch(keyword -> StringUtil.containsSubStringIgnoreCase(person.getEmail().toString(), keyword));
        boolean number = keywords.stream()
                .anyMatch(keyword -> StringUtil.containsSubStringIgnoreCase(person.getPhone().toString(), keyword));
        boolean tag = tagSearch(person);

        if (name || address || tag || email || number) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Tests that a {@code ReadOnlyPerson}'s {@code Tag} matches any of the keywords given.
     */
    public boolean tagSearch(ReadOnlyPerson person) {
        Set<Tag> tags = person.getTags();
        for (Tag s : tags) {
            for (String key : keywords) {
                if (key.equalsIgnoreCase(s.tagName)) {
                    return true;
                }
            }
        }
        return false;
    }
```
###### \java\seedu\address\ui\CalendarPanel.java
``` java
/**
 * The Calendar Panel of the App.
 */
public class CalendarPanel extends UiPart<Region> {

    public static final String DEFAULT_CALENDAR_URL = "https://calendar.google.com/calendar";

    private static final String FXML = "CalendarPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView calendar;

    public CalendarPanel() {

        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);
        loadDefaultCalendarPage();
        registerAsAnEventHandler(this);
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
    }

    private void loadDefaultCalendarPage() {
        loadCalendarPage(DEFAULT_CALENDAR_URL);
    }

    public void loadCalendarPage(String url) {
        Platform.runLater(() -> calendar.getEngine().load(url));
    }

    /**
     * Frees resources allocated to the calendarPanel.
     */
    public void freeResources() {
        calendar = null;
    }

    @Subscribe
    private void handleCalendarUpdateEvent(CalendarRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadCalendarPage(DEFAULT_CALENDAR_URL);
    }

}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleCalendarRequestEvent(CalendarRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleCalendar();
    }
```
###### \resources\view\CalendarPanel.fxml
``` fxml
<StackPane xmlns:fx="http://javafx.com/fxml/1">
    <WebView fx:id="calendar"/>
</StackPane>
```
