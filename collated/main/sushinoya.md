# sushinoya
###### \java\seedu\room\commons\events\model\EventBookChangedEvent.java
``` java
/** Indicates the ResidentBook in the model has changed*/
public class EventBookChangedEvent extends BaseEvent {

    public final ReadOnlyEventBook data;

    public EventBookChangedEvent(ReadOnlyEventBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of events " + data.getEventList().size();
    }
}
```
###### \java\seedu\room\commons\events\ui\EventPanelSelectionChangedEvent.java
``` java
/**
 * Represents a selection change in the Event List Panel
 */
public class EventPanelSelectionChangedEvent extends BaseEvent {


    private final EventCard newSelection;

    public EventPanelSelectionChangedEvent(EventCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public EventCard getNewSelection() {
        return newSelection;
    }
}
```
###### \java\seedu\room\commons\util\CommandUtil.java
``` java
/**
 * Utility methods related to Collections
 */
public class CommandUtil {

    /**
     * Returns true if the command parsed is an eventbook-related command
     */
    public static boolean isEventCommand(String command) {
        String[] commandComponents = command.split(" ");
        String commandKeyword = commandComponents[0];
        return commandKeyword.equals("deleteEvent") || commandKeyword.equals("addevent")
                || commandKeyword.equals("de") || commandKeyword.equals("ae");
    }
}
```
###### \java\seedu\room\logic\commands\AddEventCommand.java
``` java
/**
 * Adds a person to the resident book.
 */
public class AddEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addevent";
    public static final String COMMAND_ALIAS = "ae";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a event to the event book. \n"
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_LOCATION + "LOCATION "
            + "[" + PREFIX_DATETIME + "STARTTIME TO ENDTIME"
            + " or STARTTIME DURATION (in hours)]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "End of Sem Dinner "
            + PREFIX_DESCRIPTION + "Organised by USC "
            + PREFIX_LOCATION + "Cinnamon College "
            + PREFIX_DATETIME + "25/11/2017 2030 to 2359"
            + " or " + PREFIX_DATETIME + "25/11/2017 2030 2";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the event book";
    private final Event toAdd;

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyEvent}
     */
    public AddEventCommand(ReadOnlyEvent person) {
        toAdd = new Event(person);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addEvent(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateEventException e) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && toAdd.equals(((AddEventCommand) other).toAdd));
    }
}
```
###### \java\seedu\room\logic\commands\DeleteEventCommand.java
``` java
/**
 * Deletes a event identified using it's last displayed index from the resident book.
 */
public class DeleteEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteEvent";
    public static final String COMMAND_ALIAS = "de";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the event identified by the index number used in the last event listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event: %1$s";

    private final Index targetIndex;

    public DeleteEventCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyEvent> lastShownList = model.getFilteredEventList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        ReadOnlyEvent eventToDelete = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.deleteEvent(eventToDelete);
        } catch (EventNotFoundException enfe) {
            assert false : "The target event cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteEventCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteEventCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\room\logic\commands\exceptions\AlreadySortedException.java
``` java
/**
 * Represents an error which occurs during execution of a Sort Command Execution.
 */
public class AlreadySortedException extends Exception {
    public AlreadySortedException(String message) {
        super(message);
    }
}
```
###### \java\seedu\room\logic\commands\SortCommand.java
``` java
/**
 * Adds a person to the resident book.
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "srt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the displayed resident book. "
            + "Parameters: "
            + "Sorting-Criteria" + " "
            + "Example: " + COMMAND_WORD + " "
            + "name";

    public static final String MESSAGE_SUCCESS = "Sorted resident book by: %1$s";
    public static final String MESSAGE_FAILURE = "Resident book already sorted by: %1$s";

    private final String sortCriteria;

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyPerson}
     */
    public SortCommand(String sortBy) {
        this.sortCriteria = sortBy;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.sortBy(sortCriteria);
            return new CommandResult(String.format(MESSAGE_SUCCESS, sortCriteria));
        } catch (AlreadySortedException e) {
            String failureMessage = String.format(MESSAGE_FAILURE, sortCriteria);
            throw new CommandException(failureMessage);
        } catch (IllegalArgumentException e) {
            String failureMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);
            throw new CommandException(failureMessage);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && sortCriteria.equals(((SortCommand) other).sortCriteria));
    }
}
```
###### \java\seedu\room\logic\commands\SwaproomCommand.java
``` java
/**
 * Swaps two residents identified using their last displayed indexes from the resident book.
 */
public class SwaproomCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "swaproom";
    public static final String COMMAND_ALIAS = "sr";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Swaps the rooms for the two residents identified by index numbers used in the last person listing.\n"
            + "Parameters: INDEX1 and INDEX2 (both must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_SWAP_PERSONS_SUCCESS = "Swapped Rooms : %1$s and %2$s.";
    public static final String ROOMS_NOT_SET_ERROR = "Both %1$s and %2$s have not been assigned any rooms yet.";

    private final Index targetIndex1;
    private final Index targetIndex2;

    public SwaproomCommand(Index targetIndex1, Index targetIndex2) {
        this.targetIndex1 = targetIndex1;
        this.targetIndex2 = targetIndex2;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex1.getZeroBased() >= lastShownList.size()
                || targetIndex2.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson person1 = lastShownList.get(targetIndex1.getZeroBased());
        ReadOnlyPerson person2 = lastShownList.get(targetIndex2.getZeroBased());


        if (person1.getRoom().toString().equals(ROOM_NOT_SET_DEFAULT)
                && person2.getRoom().toString().equals(ROOM_NOT_SET_DEFAULT)) {
            throw new CommandException(String.format(ROOMS_NOT_SET_ERROR, person1.getName(), person2.getName()));
        }

        try {
            model.swapRooms(person1, person2);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person(s) cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_SWAP_PERSONS_SUCCESS, person1.getName(), person2.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SwaproomCommand // instanceof handles nulls
                && this.targetIndex1.equals(((SwaproomCommand) other).targetIndex1)
                && this.targetIndex2.equals(((SwaproomCommand) other).targetIndex2)) // state check
                || (other instanceof SwaproomCommand // instanceof handles nulls
                && this.targetIndex1.equals(((SwaproomCommand) other).targetIndex2)
                && this.targetIndex2.equals(((SwaproomCommand) other).targetIndex1)); // state check
    }
}
```
###### \java\seedu\room\logic\parser\AddCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {

        Phone phone;
        Email email;
        Room room;
        Timestamp timestamp;

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ROOM,
                        PREFIX_TEMPORARY, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            //Gets the name of the person being added
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();

            //Gets the phone of the person being added. If no phone is provided, it creates a phone with null as value
            Optional<Phone> optionalPhone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE));
            if (optionalPhone.isPresent()) {
                phone = optionalPhone.get();
            } else {
                phone = new Phone(null);
            }

            //Gets the email of the person being added. If no email is provided, it creates an email with null as value
            Optional<Email> optionalEmail = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL));
            if (optionalEmail.isPresent()) {
                email = optionalEmail.get();
            } else {
                email = new Email(null);
            }

            //Gets the room of the person being added. If no room is provided, it creates an room with null
            //as value
            Optional<Room> optionalRoom = ParserUtil.parseRoom(argMultimap.getValue(PREFIX_ROOM));
            if (optionalRoom.isPresent()) {
                room = optionalRoom.get();
            } else {
                room = new Room(null);
            }

            //Gets the temporary duration of the person being added. If no temporary duration is provided,
            // it creates a person with permanent duration
            Optional<Timestamp> optionalTimestamp = ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_TEMPORARY));
            if (optionalTimestamp.isPresent()) {
                timestamp = optionalTimestamp.get();
            } else {
                timestamp = new Timestamp(0);
            }


            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            ReadOnlyPerson person = new Person(name, phone, email, room, timestamp, tagList);

            return new AddCommand(person);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        } catch (NumberFormatException nfe) {
            throw new ParseException("Invalid number format for timestamp", nfe);
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
###### \java\seedu\room\logic\parser\AddEventCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddEventCommand
     * and returns an AddEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {

        Title title;
        Location location;
        Description description;
        Datetime datetime;

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_LOCATION, PREFIX_DESCRIPTION, PREFIX_DATETIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_LOCATION, PREFIX_DATETIME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        try {
            //Gets the title of the event being added
            title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE)).get();

            //Gets the location of the event being added. If no location is provided, it creates a location with null
            // as value
            Optional<Location> optionalLocation = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION));
            if (optionalLocation.isPresent()) {
                location = optionalLocation.get();
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
            }

            //Gets the description of the event being added. If no description is provided, it creates an description
            // with null as value
            Optional<Description> optionalDescription = ParserUtil.parseDescription(
                                                                            argMultimap.getValue(PREFIX_DESCRIPTION));
            if (optionalDescription.isPresent()) {
                description = optionalDescription.get();
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
            }

            //Gets the datetime of the event being added. If no datetime is provided, it creates an datetime with null
            //as value
            Optional<Datetime> optionalDatetime = ParserUtil.parseDatetime(argMultimap.getValue(PREFIX_DATETIME));
            if (optionalDatetime.isPresent()) {
                datetime = optionalDatetime.get();
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
            }

            ReadOnlyEvent event = new Event(title, description, location, datetime);

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
###### \java\seedu\room\logic\parser\DeleteEventCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteEventCommand object
 */
public class DeleteEventCommandParser implements Parser<DeleteEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteEventCommand
     * and returns an DeleteEventCommand object for execution.
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
###### \java\seedu\room\logic\parser\SortCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {

        String sortCriteria = args.trim();

        if (!isValidField(sortCriteria)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        } else {
            return new SortCommand(sortCriteria);
        }
    }

    private static boolean isValidField(String sortCriteria) {
        Set<String> validFields = new HashSet<String>(Arrays.asList(
                new String[] {"name", "phone", "email", "room"}));
        return validFields.contains(sortCriteria);
    }

}
```
###### \java\seedu\room\logic\parser\SwaproomCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class SwaproomCommandParser implements Parser<SwaproomCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SwaproomCommand parse(String args) throws ParseException {
        try {
            String[] indexes = args.split("\\s+");

            if (indexes.length != 3) {
                throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwaproomCommand.MESSAGE_USAGE));
            }

            Index index1 = ParserUtil.parseIndex(indexes[1]);
            Index index2 = ParserUtil.parseIndex(indexes[2]);
            return new SwaproomCommand(index1, index2);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwaproomCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\room\model\event\Datetime.java
``` java
/**
 * Represents a Event's Datetime in the event book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDatetime(String)}
 */
public class Datetime {

    public static final String MESSAGE_DATETIME_CONSTRAINTS =
            "Event datetime should contain dd/mm/yyyy hhmm to hhmm";

    public static final String DATE_CONSTRAINTS_VIOLATION =
            "The date does not exist";

    public static final String NUMBER_CONSTRAINTS_VIOLATION =
            "Date, time and duration can only be represented using digits";

    public static final String INVALID_TIME_FORMAT =
            "Time should be represented only by digits and in the format hhmm";

    public final String value;
    public final LocalDateTime datetime;

    /**
     * Validates given datetime.
     * @throws IllegalValueException if given datetime string is invalid.
     */
    public Datetime(String datetime) throws IllegalValueException {
        requireNonNull(datetime);
        try {
            if (!isValidDatetime(datetime)) {
                throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
            }

            //Update datetime
            String[] components = datetime.split(" ");
            String date = components[0];
            int starttime = Integer.parseInt(components[1]);;
            int duration;
            int endtime;

            //If the format is dd/mm/yyyy hhmm k
            if (components.length == 3) {
                duration = Integer.parseInt(components[2]);
                endtime = (starttime + 100 * duration) % 2400;

            //If the format is dd/mm/yyyy hhmm to hhmm
            } else if (components.length == 4) {
                endtime = Integer.parseInt(components[3]);
            } else {
                endtime = 0;
            }

            String endtimeString = this.toValidTimeString(endtime);
            String starttimeString = this.toValidTimeString(starttime);

            //Store as a LocalDateTime object
            this.datetime = this.toLocalDateTime(components[0] + " " + starttimeString);

            this.value = date + " " + starttimeString + " to " + endtimeString;

        } catch (DateTimeException e) {
            throw new IllegalValueException(DATE_CONSTRAINTS_VIOLATION);
        } catch (NumberFormatException e) {
            throw new IllegalValueException(NUMBER_CONSTRAINTS_VIOLATION);
        }
    }


    /**
     * Returns a LocalDateTime object for the Datetime object
     */
    public LocalDateTime getLocalDateTime() {
        return this.datetime;
    }

    /**
     * Returns true if a given string is a valid datetime in the format dd/mm/yyyy hhmm k.
     */
    public static boolean isValidDatetime(String test) {

        String[] components = test.split(" ");

        //If the format is dd/mm/yyyy hhmm k
        if (components.length == 3) {
            return isValidDate(components[0]) && isValidTime(components[1]) && isValidDuration(components[2]);
        //If the format is dd/mm/yyyy hhmm to hhmm
        } else if (components.length == 4) {

            for (String k : components) {
            }


            return isValidDate(components[0]) && isValidTime(components[1]) && isValidTime(components[3]);
        } else {
            return false;
        }
    }

    /**
     * Returns true if a given string is a valid date in the format dd/mm/yyyy.
     * @throws NumberFormatException if string contains non-digit characters.
     * @throws DateTimeException if the date represented by the string is invalid
     */
    public static boolean isValidDate(String date) throws DateTimeException, NumberFormatException {

        if (date.length() != 10) {
            return false;
        }

        String[] dateComponents = date.split("/");

        if (dateComponents.length != 3) {
            return false;
        }

        int day = Integer.parseInt(dateComponents[0]);
        int month = Integer.parseInt(dateComponents[1]);
        int year = Integer.parseInt(dateComponents[2]);

        LocalDate localdate;
        localdate = LocalDate.of(year, month, day);
        return true;
    }

    /**
     * Returns true if a given string is a valid time in the format hhmm.
     * @throws NumberFormatException if string contains non-digit characters.
     */
    public static boolean isValidTime(String time) throws NumberFormatException {

        int timeInt = Integer.parseInt(time); //throws NumberFormatException if "time" contains non-digit characters

        if (time.length() != 4) {
            return false;
        }

        char[] timeArray = time.toCharArray();
        int hour = Integer.parseInt(new String(timeArray, 0, 2));

        int minute = 10 * Integer.parseInt(Character.toString(timeArray[2]))
                        + Integer.parseInt(Character.toString(timeArray[3]));

        return hour >= 0 && hour < 24 && minute >= 0 && minute < 60;

    }

    /**
     * Returns true if a given string is a valid duration.
     * @throws NumberFormatException if string contains non-digit characters
     */
    public static boolean isValidDuration(String duratonString) throws NumberFormatException {
        double duration =  Double.parseDouble(duratonString);
        return duration > 0 && duration < 24;
    }

    /**
     * Returns a LocalDateTime object for the input in the format dd/MM/yyyy HHmm
     * @throws DateTimeException if the datetime represented by the string is invalid
     */
    public LocalDateTime toLocalDateTime(String value) throws DateTimeException {
        String[] valueComponents = value.split(" ");
        String dateWithStartTime = valueComponents[0] + " " + valueComponents[1];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        return LocalDateTime.parse(dateWithStartTime, formatter);
    }

    /**
     * Returns a time string in the format HHmm for an integer input in the format hmm or hhmm
     * @throws IllegalValueException if the time represented by the integer is invalid
     */
    public String toValidTimeString(int time) throws IllegalValueException {
        String updatedString = String.valueOf(time);
        if (time < 1000) {
            while (updatedString.length() < 4) {
                updatedString = "0" + updatedString;
            }
        } else {
            updatedString = String.valueOf(time);
        }
        if (this.isValidTime(updatedString)) {
            return updatedString;
        } else {
            throw new IllegalValueException(INVALID_TIME_FORMAT);
        }
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Datetime // instanceof handles nulls
                && this.value.equals(((Datetime) other).value)); // state check
    }

    @Override
    public String toString() {
        return value;
    }
}
```
###### \java\seedu\room\model\event\Description.java
``` java
/**
 * Represents a Event's Description in the event book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class Description {

    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS =
            "Event descriptions should only contain alphanumeric characters and spaces, and it should not be blank";
    /*
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DESCRIPTION_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Validates given description.
     *
     * @throws IllegalValueException if given description string is invalid.
     */
    public Description(String description) throws IllegalValueException {
        requireNonNull(description);
        if (!isValidDescription(description)) {
            throw new IllegalValueException(MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        this.value = description;
    }

    /**
     * Returns true if a given string is a valid event description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && this.value.equals(((Description) other).value)); // state check
    }

    @Override
    public String toString() {
        return value;
    }
}
```
###### \java\seedu\room\model\event\Event.java
``` java
/**
 * Represents a Event in the event book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Event implements ReadOnlyEvent {

    private ObjectProperty<Title> title;
    private ObjectProperty<Description> description;
    private ObjectProperty<Location> location;
    private ObjectProperty<Datetime> datetime;
    private String sortCriteria = "title";

    /**
     * Every field must be present and not null.
     */
    public Event(Title title, Description description, Location location, Datetime datetime) {
        requireAllNonNull(title, description, location, datetime);
        this.title = new SimpleObjectProperty<>(title);
        this.description = new SimpleObjectProperty<>(description);
        this.location = new SimpleObjectProperty<>(location);
        this.datetime = new SimpleObjectProperty<>(datetime);
    }

    /**
     * Creates a copy of the given ReadOnlyEvent.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getTitle(), source.getDescription(), source.getLocation(), source.getDatetime());
    }

    @Override
    public ObjectProperty<Title> titleProperty() {
        return title;
    }

    @Override
    public Title getTitle() {
        return title.get();
    }

    public void setTitle(Title title) {
        this.title.set(requireNonNull(title));
    }

    @Override
    public ObjectProperty<Description> descriptionProperty() {
        return description;
    }

    @Override
    public Description getDescription() {
        return description.get();
    }

    public void setDescription(Description description) {
        this.description.set(requireNonNull(description));
    }

    @Override
    public ObjectProperty<Location> locationProperty() {
        return location;
    }

    @Override
    public Location getLocation() {
        return location.get();
    }

    public void setLocation(Location location) {
        this.location.set(requireNonNull(location));
    }

    @Override
    public ObjectProperty<Datetime> datetimeProperty() {
        return datetime;
    }

    @Override
    public Datetime getDatetime() {
        return datetime.get();
    }

    public void setDatetime(Datetime datetime) {
        this.datetime.set(requireNonNull(datetime));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, description, location, datetime);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    /**
     * Sets the field the list should be sorted by
     */
    public void setComparator(String field) {
        Set<String> validFields = new HashSet<String>(Arrays.asList(
                new String[] {"title", "description", "location", "datetime"}
        ));

        if (validFields.contains(field)) {
            this.sortCriteria = field;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return this.title.getValue().equals(otherEvent.title.getValue())
                &&  this.location.getValue().equals(otherEvent.location.getValue())
                && this.datetime.getValue().equals(otherEvent.datetime.getValue());
    }

    @Override
    public int compareTo(Object otherEvent) {

        ReadOnlyEvent event = (ReadOnlyEvent) otherEvent;
        String firstField = this.getTitle().toString();
        String secondField = event.getTitle().toString();

        if (sortCriteria.equals("title")) {
            firstField = this.getTitle().toString();
            secondField = event.getTitle().toString();

        } else if (sortCriteria.equals("location")) {
            firstField = this.getLocation().toString();
            secondField = event.getLocation().toString();

        } else if (sortCriteria.equals("datetime")) {
            firstField = this.getDatetime().toString();
            secondField = event.getDatetime().toString();
        } else {
            return firstField.compareTo(secondField);
        }

        // If a field is "Not Set" put the corresponding event at the end of the list.
        if (firstField.equals("Not Set") && secondField.equals("Not Set")) {
            return 0;
        } else if (!firstField.equals("Not Set") && secondField.equals("Not Set")) {
            return -1;
        } else if (firstField.equals("Not Set") && !secondField.equals("Not Set")) {
            return 1;
        } else {
            return firstField.compareTo(secondField);
        }
    }
}
```
###### \java\seedu\room\model\event\Location.java
``` java
/**
 * Represents a Event's Location in the event book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLocation(String)}
 */
public class Location {

    public static final String MESSAGE_LOCATION_CONSTRAINTS =
            "Event location should only contain alphanumeric characters and spaces, and it should not be blank";
    /*
     * The first character of the location must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String LOCATION_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Validates given description.
     *
     * @throws IllegalValueException if given location string is invalid.
     */
    public Location(String location) throws IllegalValueException {
        requireNonNull(location);
        if (!isValidLocation(location)) {
            throw new IllegalValueException(MESSAGE_LOCATION_CONSTRAINTS);
        }
        this.value = location;
    }

    /**
     * Returns true if a given string is a valid event location.
     */
    public static boolean isValidLocation(String test) {
        return test.matches(LOCATION_VALIDATION_REGEX);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Location // instanceof handles nulls
                && this.value.equals(((Location) other).value)); // state check
    }

    @Override
    public String toString() {
        return value;
    }
}
```
###### \java\seedu\room\model\event\ReadOnlyEvent.java
``` java
/**
 * A read-only immutable interface for a event in the eventbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyEvent extends Comparable {

    ObjectProperty<Title> titleProperty();

    Title getTitle();

    ObjectProperty<Description> descriptionProperty();

    Description getDescription();

    ObjectProperty<Location> locationProperty();

    Location getLocation();

    ObjectProperty<Datetime> datetimeProperty();

    Datetime getDatetime();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyEvent other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTitle().equals(this.getTitle()) // state checks here onwards
                && other.getDescription().equals(this.getDescription())
                && other.getLocation().equals(this.getLocation())
                && other.getDatetime().equals(this.getDatetime()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Description: ")
                .append(getDescription())
                .append(" Location: ")
                .append(getLocation())
                .append(" Datetime: ")
                .append(getDatetime());
        return builder.toString();
    }
}
```
###### \java\seedu\room\model\event\Title.java
``` java
/**
 * Represents a Event's title in the event book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTitle(String)}
 */
public class Title {

    public static final String MESSAGE_TITLE_CONSTRAINTS =
            "Event titles should only contain alphanumeric characters and spaces, and it should not be blank";
    /*
     * The first character of the title must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TITLE_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Validates given title.
     *
     * @throws IllegalValueException if given title string is invalid.
     */
    public Title(String title) throws IllegalValueException {
        requireNonNull(title);
        if (!isValidTitle(title)) {
            throw new IllegalValueException(MESSAGE_TITLE_CONSTRAINTS);
        }
        this.value = title;
    }

    /**
     * Returns true if a given string is a valid event title.
     */
    public static boolean isValidTitle(String test) {
        return test.matches(TITLE_VALIDATION_REGEX);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Title // instanceof handles nulls
                && this.value.equals(((Title) other).value)); // state check
    }

    @Override
    public String toString() {
        return value;
    }
}
```
###### \java\seedu\room\model\event\TitleContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyEvent}'s {@code Title} matches any of the keywords given.
 */
public class TitleContainsKeywordsPredicate implements Predicate<ReadOnlyEvent> {
    private final List<String> keywords;

    public TitleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyEvent event) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(event.getTitle().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TitleContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TitleContainsKeywordsPredicate) other).keywords)); // state check
    }
}
```
###### \java\seedu\room\model\event\UniqueEventList.java
``` java
/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Event#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueEventList implements Iterable<Event> {

    private final ObservableList<Event> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyEvent> mappedList = EasyBind.map(internalList, (event) -> event);
    private String currentlySortedBy = "title";

    /**
     * Returns true if the list contains an equivalent event as the given argument.
     */
    public boolean contains(ReadOnlyEvent toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a event to the list.
     *
     * @throws DuplicateEventException if the event to add is a duplicate of an existing event in the list.
     */
    public void add(ReadOnlyEvent toAdd) throws DuplicateEventException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateEventException();
        }
        internalList.add(new Event(toAdd));
    }

    /**
     * Replaces the event {@code target} in the list with {@code editedEvent}.
     *
     * @throws DuplicateEventException if the replacement is equivalent to another existing event in the list.
     * @throws EventNotFoundException  if {@code target} could not be found in the list.
     */
    public void setEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent)
            throws DuplicateEventException, EventNotFoundException {
        requireNonNull(editedEvent);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new EventNotFoundException();
        }

        if (!target.equals(editedEvent) && internalList.contains(editedEvent)) {
            throw new DuplicateEventException();
        }

        internalList.set(index, new Event(editedEvent));
    }

    /**
     * Removes the equivalent event from the list.
     *
     * @throws EventNotFoundException if no such event could be found in the list.
     */
    public boolean remove(ReadOnlyEvent toRemove) throws EventNotFoundException {
        requireNonNull(toRemove);
        final boolean eventFoundAndDeleted = internalList.remove(toRemove);
        if (!eventFoundAndDeleted) {
            throw new EventNotFoundException();
        }
        return eventFoundAndDeleted;
    }

    public void setEvents(UniqueEventList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setEvents(List<? extends ReadOnlyEvent> events) throws DuplicateEventException {
        final UniqueEventList replacement = new UniqueEventList();
        for (final ReadOnlyEvent event : events) {
            replacement.add(new Event(event));
        }
        setEvents(replacement);
    }


    public String getCurrentlySortedBy() {
        return this.currentlySortedBy;
    }

    /**
     * Sorts the Person's List by sorting criteria
     */
    public void sortBy(String sortCriteria) {
        this.currentlySortedBy = sortCriteria;
        for (Event e : internalList) {
            e.setComparator(sortCriteria);
        }
        FXCollections.sort(internalList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyEvent> asObservableList() {
        sortBy(currentlySortedBy);
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    @Override
    public Iterator<Event> iterator() {
        return internalList.iterator();
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueEventList // instanceof handles nulls
                && this.internalList.equals(((UniqueEventList) other).internalList));
    }
}
```
###### \java\seedu\room\model\EventBook.java
``` java
/**
 * Wraps all data at the event-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class EventBook implements ReadOnlyEventBook {

    private final UniqueEventList events;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        events = new UniqueEventList();
    }

    public EventBook() {}

    /**
     * Creates an EventBook using the Events in the {@code toBeCopied}
     */
    public EventBook(ReadOnlyEventBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setEvents(List<? extends ReadOnlyEvent> events) throws DuplicateEventException {
        this.events.setEvents(events);
    }

    /**
     * Resets the existing data of this {@code EventBook} with {@code newData}.
     */
    public void resetData(ReadOnlyEventBook newData) {
        requireNonNull(newData);
        try {
            setEvents(newData.getEventList());
        } catch (DuplicateEventException e) {
            assert false : "EventBooks should not have duplicate events";
        }
    }

    //// event-level operations

    /**
     * Adds an event to the event book.
     *
     * @throws DuplicateEventException if an equivalent event already exists.
     */
    public void addEvent(ReadOnlyEvent e) throws DuplicateEventException {
        Event newEvent = new Event(e);
        events.add(newEvent);
    }

    /**
     * Replaces the given event {@code target} in the list with {@code editedReadOnlyEvent}.
     *
     * @throws DuplicateEventException if updating the event's details causes the event to be equivalent to
     *                                 another existing event in the list.
     * @throws EventNotFoundException  if {@code target} could not be found in the list.
     */
    public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedReadOnlyEvent)
            throws DuplicateEventException, EventNotFoundException {
        requireNonNull(editedReadOnlyEvent);

        Event editedEvent = new Event(editedReadOnlyEvent);
        events.setEvent(target, editedEvent);
    }

    /**
     * Removes {@code key} from this {@code EventBook}.
     * @throws EventNotFoundException if the {@code key} is not in this {@code EventBook}.
     */
    public boolean removeEvent(ReadOnlyEvent key) throws EventNotFoundException {
        if (events.remove(key)) {
            return true;
        } else {
            throw new EventNotFoundException();
        }
    }

    //// sort resident book
    /**
     * Sorts the UniquePersonList, persons.
     *
     * @throws AlreadySortedException if the list is already sorted by given criteria.
     */
    public void sortBy(String sortCriteria) throws AlreadySortedException {

        if (events.getCurrentlySortedBy().equals(sortCriteria)) {
            throw new AlreadySortedException("List already sorted by: " + sortCriteria);
        } else {
            events.sortBy(sortCriteria);
        }
    }


    //// util methods

    @Override
    public String toString() {
        return events.asObservableList().size() + " events";
    }

    @Override
    public ObservableList<ReadOnlyEvent> getEventList() {
        return events.asObservableList();
    }

    public UniqueEventList getUniqueEventList() {
        return events;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventBook // instanceof handles nulls
                && this.events.equals(((EventBook) other).events));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(events);
    }

}
```
###### \java\seedu\room\model\ModelManager.java
``` java
    /**
     * Sorts the Resident Book by name, phone, room or phone depending on the sortCriteria
     */
    public void sortBy(String sortCriteria) throws AlreadySortedException {
        residentBook.sortBy(sortCriteria);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateResidentBookChanged();
    }

    //=========== Swapping Residents' Rooms =============================================================

```
###### \java\seedu\room\model\ModelManager.java
``` java
    /**
     * Swaps the rooms between two residents.
     *
     * @throws PersonNotFoundException if the persons specified are not found in the list.
     */
    @Override
    public void swapRooms(ReadOnlyPerson person1, ReadOnlyPerson person2)
            throws PersonNotFoundException {
        residentBook.swapRooms(person1, person2);
        indicateResidentBookChanged();
    }

    //=========== Events Methods =============================================================

    @Override
    public ReadOnlyEventBook getEventBook() {
        return this.eventBook;
    }

    @Override
    public synchronized void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException {
        eventBook.removeEvent(target);
        indicateEventBookChanged();
    }

    @Override
    public synchronized void addEvent(ReadOnlyEvent event) throws DuplicateEventException {
        eventBook.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        indicateEventBookChanged();
    }

    @Override
    public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent)
            throws DuplicateEventException, EventNotFoundException {
        requireAllNonNull(target, editedEvent);

        eventBook.updateEvent(target, editedEvent);
        indicateEventBookChanged();
    }

    @Override
    public ObservableList<ReadOnlyEvent> getFilteredEventList() {
        return FXCollections.unmodifiableObservableList(filteredEvents);
    }

    @Override
    public void updateFilteredEventList(Predicate<ReadOnlyEvent> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }

    @Override
    public void sortEventsBy(String sortCriteria) throws AlreadySortedException {
        eventBook.sortBy(sortCriteria);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateResidentBookChanged();
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateEventBookChanged() {
        raise(new EventBookChangedEvent(eventBook));
    }

}
```
###### \java\seedu\room\model\person\Person.java
``` java
    /**
     * Sets the field the list should be sorted by
     */
    public void setComparator(String field) {
        Set<String> validFields = new HashSet<String>(Arrays.asList(
                new String[] {"name", "phone", "email", "room"}
        ));

        if (validFields.contains(field)) {
            this.sortCriteria = field;
        } else {
            throw new IllegalArgumentException();
        }
    }

```
###### \java\seedu\room\model\person\Person.java
``` java
    /**
    * CompareTo function to allow implementing Comparable
    */
    @Override
    public int compareTo(Object otherPerson) {

        ReadOnlyPerson person = (ReadOnlyPerson) otherPerson;
        String firstField = this.getName().toString();
        String secondField = person.getName().toString();

        if (sortCriteria.equals("email")) {
            firstField = this.getEmail().toString();
            secondField = person.getEmail().toString();

        } else if (sortCriteria.equals("phone")) {
            firstField = this.getPhone().toString();
            secondField = person.getPhone().toString();

        } else if (sortCriteria.equals("room")) {
            firstField = this.getRoom().toString();
            secondField = person.getRoom().toString();
        } else {
            return firstField.compareTo(secondField);
        }

        // If a field is "Not Set" put the corresponding person at the end of the list.
        if (firstField.equals("Not Set") && secondField.equals("Not Set")) {
            return 0;
        } else if (!firstField.equals("Not Set") && secondField.equals("Not Set")) {
            return -1;
        } else if (firstField.equals("Not Set") && !secondField.equals("Not Set")) {
            return 1;
        } else {
            return firstField.compareTo(secondField);
        }
    }

}
```
###### \java\seedu\room\model\person\Room.java
``` java
/**
 * Represents a Person's room in the resident book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRoom(String)}
 */
public class Room {

    public static final String MESSAGE_ROOM_CONSTRAINTS =
            "Room should be in the format FF-UUUA where FF is the floor, "
                   + "UUU is unit number and A is an optional letter "
                   + "to denote exact room within a suite";

    /*
     * The first character of the room must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ROOM_VALIDATION_REGEX = "\\d{2}-\\d{3}[A-Z]?";
    public static final String ROOM_NOT_SET_DEFAULT = "Not Set";

    public final String value;

    /**
     * Validates given room.
     *
     * @throws IllegalValueException if given room string is invalid.
     */
    public Room(String room) throws IllegalValueException {
        if (room == null) {
            this.value = ROOM_NOT_SET_DEFAULT;
        } else {
            if (!isValidRoom(room)) {
                throw new IllegalValueException(MESSAGE_ROOM_CONSTRAINTS);
            }
            this.value = room;
        }
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidRoom(String test) {
        return test.matches(ROOM_VALIDATION_REGEX) || test.equals(ROOM_NOT_SET_DEFAULT);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Room // instanceof handles nulls
                && this.value.equals(((Room) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\room\model\ReadOnlyEventBook.java
``` java
/**
 * Unmodifiable view of an event book
 */
public interface ReadOnlyEventBook {

    /**
     * Returns an unmodifiable view of the events list.
     * This list will not contain any duplicate events.
     */
    ObservableList<ReadOnlyEvent> getEventList();

}
```
###### \java\seedu\room\model\ResidentBook.java
``` java
    /**
     * Sorts the UniquePersonList, persons.
     *
     * @throws AlreadySortedException if the list is already sorted by given criteria.
     */
    public void sortBy(String sortCriteria) throws AlreadySortedException {

        if (persons.getCurrentlySortedBy().equals(sortCriteria)) {
            throw new AlreadySortedException("List already sorted by: " + sortCriteria);
        } else {
            persons.sortBy(sortCriteria);
        }
    }

    ////
```
###### \java\seedu\room\model\ResidentBook.java
``` java
    /**
     * Swaps the rooms between two residents.
     * @throws PersonNotFoundException if the persons specified are not found in the list.
     */
    public void swapRooms(ReadOnlyPerson person1, ReadOnlyPerson person2)
        throws PersonNotFoundException {
        persons.swapRooms(person1, person2);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() + " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyPerson> getPersonList() {
        return persons.asObservableList();
    }

    public UniquePersonList getUniquePersonList() {
        return persons;
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ResidentBook // instanceof handles nulls
                && this.persons.equals(((ResidentBook) other).persons)
                && this.tags.equalsOrderInsensitive(((ResidentBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }

}
```
###### \java\seedu\room\model\util\SampleDataUtil.java
``` java
/**
 * Contains utility methods for populating {@code ResidentBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Room("09-119"), new Timestamp(0),
                    getTagSet("professor")),

                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Room("09-100A"), new Timestamp(0),
                    getTagSet("level10", "RA")),

                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Room("21-118"), new Timestamp(0),
                    getTagSet("level16")),

                new Person(new Name("David Li"), new Phone("91023282"), new Email("lidavid@example.com"),
                    new Room("26-105"), new Timestamp(0),
                    getTagSet("level17")),

                new Person(new Name("Irfan Ibrahim"), new Phone("92492321"), new Email("irfan@example.com"),
                    new Room("17-135F"), new Timestamp(0),
                    getTagSet("level18")),

                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Room("12-120E"), new Timestamp(0),
                    getTagSet("staff")),

                new Person(new Name("Jeremy Arnold"), new Phone("87432307"), new Email("jeremy@example.com"),
                    new Room("10-152"), new Timestamp(0),
                    getTagSet("professor", "fulltime")),

                new Person(new Name("Peter Vail"), new Phone("99098758"), new Email("peter@example.com"),
                    new Room("05-150A"), new Timestamp(0),
                    getTagSet("level21", "RA")),

                new Person(new Name("Shawna Metzger"), new Phone("98383283"), new Email("shwana@example.com"),
                    new Room("20-119"), new Timestamp(0),
                    getTagSet("level12")),

                new Person(new Name("Varun Gupta"), new Phone("91030982"), new Email("varun@example.com"),
                    new Room("30-115"), new Timestamp(0),
                    getTagSet("level7")),

                new Person(new Name("Govind Koyal"), new Phone("92412321"), new Email("govind@example.com"),
                    new Room("16-134F"), new Timestamp(0),
                    getTagSet("level9", "NUSSU")),

                new Person(new Name("James Hobbit"), new Phone("92620917"), new Email("jamesh@example.com"),
                    new Room("04-120"), new Timestamp(0),
                    getTagSet("staff", "cleaner")),

                new Person(new Name("Leia Krotes"), new Phone("87488807"), new Email("leia@example.com"),
                    new Room("07-119B"), new Timestamp(0),
                    getTagSet("management")),

                new Person(new Name("Sherlock Holmes"), new Phone("90982758"), new Email("sherlock@example.com"),
                    new Room("16-100A"), new Timestamp(0),
                    getTagSet("level5", "RA")),

                new Person(new Name("Harry Jones"), new Phone("93211233"), new Email("harry@example.com"),
                    new Room("07-118"), new Timestamp(0),
                    getTagSet("level6", "staff", "helper")),

                new Person(new Name("Katniss Mallark"), new Phone("92981282"), new Email("kat92@example.com"),
                    new Room("03-115"), new Timestamp(0),
                    getTagSet("level8")),

                new Person(new Name("James T Kirk"), new Phone("98765654"), new Email("kirk@example.com"),
                    new Room("04-135F"), new Timestamp(0),
                    getTagSet("level19")),

                new Person(new Name("Luna Lovegood"), new Phone("92620977"), new Email("luna@example.com"),
                    new Room("21-130"), new Timestamp(0),
                    getTagSet("staff")),

                new Person(new Name("Dolores Umbridge"), new Phone("91928282"), new Email("dolores@example.com"),
                    new Room("16-115"), new Timestamp(0),
                    getTagSet("level8")),

                new Person(new Name("Abeforth"), new Phone("92491321"), new Email("abeforth@example.com"),
                    new Room("15-132"), new Timestamp(0),
                    getTagSet("level20"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static Event[] getSampleEvents() {
        try {
            return new Event[]{
                new Event(new Title("End of Semester Dinner"), new Description("Organised by USC"),
                        new Location("Cinnamon College"), new Datetime("01/11/2017 2030 2")),

                new Event(new Title("USPolymath Talk"), new Description("Talk by Students"),
                        new Location("Chatterbox"), new Datetime("02/11/2017 0800 1")),

                new Event(new Title("USProductions"), new Description("Drama performance"),
                            new Location("Blackbox"), new Datetime("03/10/2017 1200 3")),

                new Event(new Title("Neighbourhood Party"), new Description("Organised by USC"),
                        new Location("YaleNUS"), new Datetime("07/11/2017 2030 2")),

                new Event(new Title("Frisbee Finals"), new Description("Talk by Students"),
                        new Location("MPSH"), new Datetime("08/11/2017 0800 1")),

                new Event(new Title("Chess Training"), new Description("Drama performance"),
                            new Location("Level 4 Lounge"), new Datetime("11/10/2017 1200 3")),

                new Event(new Title("End of Semester Dinner"), new Description("Organised by USC"),
                        new Location("Cinnamon College"), new Datetime("15/11/2017 2030 2")),

                new Event(new Title("Meet Minister"), new Description("Talk by Students"),
                        new Location("Chua Thian Poh Hall"), new Datetime("17/11/2017 0800 1")),

                new Event(new Title("USAmbassadors Meeting"), new Description("Drama performance"),
                            new Location("Dining Hall"), new Datetime("17/10/2017 1200 3")),

                new Event(new Title("Orientation Day 1"), new Description("Organised by House Comm"),
                        new Location("Cinnamon College"), new Datetime("12/12/2017 2030 2")),

                new Event(new Title("Orientation Day 2"), new Description("Talk by Students"),
                        new Location("Cinnamon College"), new Datetime("13/12/2017 2030 2")),

                new Event(new Title("Deans Address"), new Description("College Wide Event"),
                            new Location("UTSH"), new Datetime("19/12/2017 1200 3")),

                new Event(new Title("Diwali Celebrations"), new Description("Festival"),
                        new Location("Cinnamon College"), new Datetime("01/12/2017 2030 2")),

                new Event(new Title("Movie Screening"), new Description("Free Admission"),
                        new Location("Chatterbox"), new Datetime("03/12/2017 0800 3")),

                new Event(new Title("Volleyball Training"), new Description("Sports"),
                            new Location("CTPH"), new Datetime("29/11/2017 1200 3")),

                new Event(new Title("End of Semester Dinner"), new Description("Organised by USC"),
                        new Location("Cinnamon College"), new Datetime("28/10/2017 2030 2")),

                new Event(new Title("USPolymath Talk"), new Description("Talk by Students"),
                        new Location("Chatterbox"), new Datetime("04/10/2017 0800 1")),

                new Event(new Title("USProductions"), new Description("Drama performance"),
                            new Location("Blackbox"), new Datetime("17/09/2017 1200 3")),

                new Event(new Title("Gala Dinner"), new Description("Freshmen Event"),
                        new Location("CHIJMES"), new Datetime("25/09/2017 2030 2")),

                new Event(new Title("Piano Concert"), new Description("By USClassical"),
                        new Location("Chatterbox"), new Datetime("05/09/2017 0800 1")),

                new Event(new Title("New Year Celebrations"), new Description("Festival"),
                            new Location("Blackbox"), new Datetime("01/01/2018 1200 3"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyResidentBook getSampleResidentBook() {
        try {
            ResidentBook sampleAb = new ResidentBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }

    public static ReadOnlyEventBook getSampleEventBook() {
        try {
            EventBook sampleEb = new EventBook();
            for (Event sampleEvent : getSampleEvents()) {
                sampleEb.addEvent(sampleEvent);
            }
            return sampleEb;
        } catch (DuplicateEventException e) {
            throw new AssertionError("sample data cannot contain duplicate events", e);
        }
    }
    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) throws IllegalValueException {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

}
```
###### \java\seedu\room\storage\EventBookStorage.java
``` java
/**
 * Represents a storage for {@link seedu.room.model.EventBook}.
 */
public interface EventBookStorage {

    /**
     * Returns the file path of the data file.
     */
    String getEventBookFilePath();

    /**
     * Returns EventBook data as a {@link ReadOnlyEventBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyEventBook> readEventBook() throws DataConversionException, IOException;

    /**
     * @see #getEventBookFilePath()
     */
    Optional<ReadOnlyEventBook> readEventBook(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyEventBook} to the storage.
     * @param eventBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveEventBook(ReadOnlyEventBook eventBook) throws IOException;

    /**
     * @see #saveEventBook(ReadOnlyEventBook)
     */
    void saveEventBook(ReadOnlyEventBook eventBook, String filePath) throws IOException;

    /**
     * Saves the given {@link ReadOnlyEventBook} as backup at fixed temporary location.
     *
     * @param eventBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void backupEventBook(ReadOnlyEventBook eventBook) throws IOException;

}
```
###### \java\seedu\room\storage\Storage.java
``` java
    @Override
    String getEventBookFilePath();

    @Override
    Optional<ReadOnlyEventBook> readEventBook() throws DataConversionException, IOException;

    @Override
    void saveEventBook(ReadOnlyEventBook residentBook) throws IOException;

    /**
     * Saves the current version of the Event Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleEventBookChangedEvent(EventBookChangedEvent ebce);
}
```
###### \java\seedu\room\storage\XmlAdaptedEvent.java
``` java
/**
 * JAXB-friendly version of the Event.
 */
public class XmlAdaptedEvent {

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private String location;
    @XmlElement(required = true)
    private String datetime;


    /**
     * Constructs an XmlAdaptedEvent.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEvent() {}

    public XmlAdaptedEvent(ReadOnlyEvent source) {
        title = source.getTitle().value;
        description = source.getDescription().value;
        location = source.getLocation().value;
        datetime = source.getDatetime().value;
    }

    /**
     * Converts this jaxb-friendly adapted event object into the model's Event object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event
     */
    public Event toModelType() throws IllegalValueException {
        final Title title = new Title(this.title);
        final Description description = new Description(this.description);
        final Location location = new Location(this.location);
        final Datetime datetime = new Datetime(this.datetime);
        return new Event(title, description, location, datetime);
    }
}
```
###### \java\seedu\room\storage\XmlEventBookStorage.java
``` java
/**
 * A class to access EventBook data stored as an xml file on the hard disk.
 */
public class XmlEventBookStorage implements EventBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlEventBookStorage.class);

    private String filePath;

    public XmlEventBookStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getEventBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyEventBook> readEventBook() throws DataConversionException, IOException {
        return readEventBook(filePath);
    }

    /**
     * Similar to {@link #readEventBook()}
     *
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyEventBook> readEventBook(String filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        File eventBookFile = new File(filePath);

        if (!eventBookFile.exists()) {
            logger.info("EventBook file " + eventBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyEventBook eventBookOptional = XmlFileStorage.loadEventDataFromSaveFile(new File(filePath));

        return Optional.of(eventBookOptional);
    }

    @Override
    public void saveEventBook(ReadOnlyEventBook eventBook) throws IOException {
        saveEventBook(eventBook, filePath);
    }

    /**
     * Similar to {@link #saveEventBook(ReadOnlyEventBook)}
     *
     * @param filePath location of the data. Cannot be null
     */
    public void saveEventBook(ReadOnlyEventBook eventBook, String filePath) throws IOException {
        requireNonNull(eventBook);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveEventDataToFile(file, new XmlSerializableEventBook(eventBook));
    }

    @Override
    public void backupEventBook(ReadOnlyEventBook eventBook) throws IOException {
        saveEventBook(eventBook, "-backup.xml");
    }
}
```
###### \java\seedu\room\storage\XmlSerializableEventBook.java
``` java
/**
 * An Immutable EventBook that is serializable to XML format
 */
@XmlRootElement(name = "eventbook")
public class XmlSerializableEventBook implements ReadOnlyEventBook {

    @XmlElement
    private List<XmlAdaptedEvent> events;

    /**
     * Creates an empty XmlSerializableEventBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableEventBook() {
        events = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableEventBook(ReadOnlyEventBook src) {
        this();
        events.addAll(src.getEventList().stream().map(XmlAdaptedEvent::new).collect(Collectors.toList()));
    }

    @Override
    public ObservableList<ReadOnlyEvent> getEventList() {
        final ObservableList<ReadOnlyEvent> events = this.events.stream().map(p -> {
            try {
                return p.toModelType();
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
###### \java\seedu\room\ui\EventCard.java
``` java
/**
 * An UI component that displays information of a {@code Event}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyEvent event;

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label eventLocation;
    @FXML
    private Label datetime;

    public EventCard(ReadOnlyEvent event, int displayedIndex) {
        super(FXML);
        id.setText(displayedIndex + ". ");
        this.event = event;
        bindListeners(event);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Event} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyEvent event) {
        title.textProperty().bind(Bindings.convert(event.titleProperty()));
        description.textProperty().bind(Bindings.convert(event.descriptionProperty()));
        eventLocation.textProperty().bind(Bindings.convert(event.locationProperty()));
        datetime.textProperty().bind(Bindings.convert(event.datetimeProperty()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventCard)) {
            return false;
        }

        // state check
        EventCard card = (EventCard) other;
        return id.getText().equals(card.id.getText())
                && event.equals(card.event);
    }
}
```
###### \java\seedu\room\ui\EventListPanel.java
``` java
/**
 * Panel containing the list of events.
 */
public class EventListPanel extends UiPart<Region> {
    private static final String FXML = "EventListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EventListPanel.class);

    @FXML
    private ListView<EventCard> eventListView;

    public EventListPanel(ObservableList<ReadOnlyEvent> eventList) {
        super(FXML);
        setConnections(eventList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyEvent> eventList) {
        ObservableList<EventCard> mappedList = EasyBind.map(
                eventList, (event) -> new EventCard(event, eventList.indexOf(event) + 1));
        eventListView.setItems(mappedList);
        eventListView.setCellFactory(listView -> new EventListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        eventListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in event list panel changed to : '" + newValue + "'");
                        raise(new EventPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code EventCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            eventListView.scrollTo(index);
            eventListView.getSelectionModel().clearAndSelect(index);
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code EventCard}.
     */
    class EventListViewCell extends ListCell<EventCard> {

        @Override
        protected void updateItem(EventCard event, boolean empty) {
            super.updateItem(event, empty);

            if (empty || event == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(event.getRoot());
            }
        }
    }
}
```
###### \resources\view\DarkTheme.css
``` css

/*BASE STYLING*/
.background {
    -fx-background-color: #232A34;
    background-color: #383838; /* Used in the default.html file */
}

.label {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #555555;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
    -fx-font-family: "Segoe UI Semibold";
}

.table-view {
    -fx-base: #1d1d1d;
    -fx-control-inner-background: #1d1d1d;
    -fx-background-color: #232A34;
    -fx-table-cell-border-color: transparent;
    -fx-table-header-border-color: transparent;
    -fx-padding: 5;
}

.table-view .column-header-background {
    -fx-background-color: #232A34;
}

.table-view .column-header, .table-view .filler {
    -fx-size: 35;
    -fx-border-width: 0 0 1 0;
    -fx-background-color: #232A34;
    -fx-border-color:
        transparent
        transparent
        derive(-fx-base, 80%)
        transparent;
    -fx-border-insets: 0 10 1 0;
}

.table-view .column-header .label {
    -fx-font-size: 20pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: #232A34;
}


/*TAB AND TAB PANE STYLING*/
.tab-pane {
    -fx-padding: 0 0 0 1;
    -fx-background-color: #232A34;
}

.tab-pane .tab-header-area {
    -fx-background-color: #232A34;
    -fx-padding: 0 0 0 0;
    -fx-min-height: 0;
    -fx-max-height: 0;
}

.tab-pane .tab-header-area .tab-header-background {
    -fx-opacity: 0;
}

.tab-pane {
    -fx-tab-min-width:115px;
}

.tab {
    -fx-background-insets: 0 1 0 1,0,0;
}

.tab-pane .tab {
    -fx-background-color: #404040;

}

.tab-pane .tab:selected {
    -fx.border-color: transparent !important;
    -fx-background-color: #336D1C;
}

.tab .tab-label {
    -fx-alignment: CENTER;
    -fx-text-fill: #f3f3f3;
    -fx-font-size: 12px;
    -fx-font-weight: bold;
}

.tab:selected .tab-label {
    -fx.border-color: transparent !important;
    -fx-text-fill: white;
}


/*SPLIT PANE AND LIST CSS*/
.pane-with-border {
     -fx-background-color: #232A34;
     -fx-border-color: derive(#1d1d1d, 10%);
     -fx-border-top-width: 1px;
}

#calendarDaysPane {
    -fx-background-color: transparent;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: #232A34;
    -fx-border-width: 0;
    -fx-border-color: transparent transparent transparent #4d4d4d;
}

.split-pane {
    -fx-background-color: #232A34;
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
}

.list-cell:filled:even {
    -fx-background-color: #232A34;
}

.list-cell:filled:odd {
    -fx-background-color: #232A34;
}

.list-cell:filled:selected {
    -fx-background-color: #232A34;
}

.list-cell:filled:selected #cardPane {
    -fx-background-color: #410964 !important;
}

.list-cell .label {
    -fx-text-fill: white;
}

.cell_big_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 16px;
    -fx-text-fill: #010504;
}

.cell_small_label {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 13px;
    -fx-text-fill: #010504;
}

.anchor-pane {
     -fx-background-color: #232A34;
}

.status-bar {
    -fx-background-color: #232A34;
    -fx-text-fill: black;
}

.result-display {
    -fx-background-color: #232A34;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: white;
}

.result-display .label {
    -fx-text-fill: black !important;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
}

.status-bar-with-border {
    -fx-background-color: #232A34;
    -fx-border-color: derive(#1d1d1d, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: white;
}

.grid-pane {
    -fx-background-color: #232A34;
    -fx-border-color: derive(#1d1d1d, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: #232A34;
}

.context-menu {
    -fx-background-color: #232A34;
}

.context-menu .label {
    -fx-text-fill: white;
}

.menu-bar {
    -fx-background-color: #232A34;
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-opacity: 0.9;
}

.menu .left-container {
    -fx-background-color: #232A34;
}


/*BUTTON SYTLING*/
.button {
    -fx-padding: 2 4 2 4;
    -fx-border-color: white;
    -fx-border-radius: 4;
    -fx-border-width: 1;
    -fx-background-radius: 4;
    -fx-background-color: black;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: #d8d8d8;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #232A34;
}

.button:pressed, .button:default:hover:pressed {
  -fx-background-color: #232A34;
  -fx-text-fill: #1d1d1d;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #232A34;
    -fx-text-fill: white;
}

.button:default {
    -fx-padding: 0 500 0 500;
    -fx-background-color: #232A34;
    -fx-text-fill: #ffffff;
}

.button:default:hover {
    -fx-background-color: #232A34;
}

/*BUTTON SYTLING IN PERSON PANEL*/
#resetImageButton, #addImageButton {
    -fx-min-width: 70;
}


/*DIALOG PANE STYLING*/
.dialog-pane {
    -fx-background-color: #232A34;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #232A34;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: white;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: #232A34;
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: white;
}


/*SCROLL BAR STYLING*/
.scroll-bar {
    -fx-background-color: #232A34;
}

.scroll-bar .thumb {
    -fx-background-color: #232A34;
    -fx-background-insets: 3;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: #232A34;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow,
.scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 8 1 8;
}

.scroll-bar:horizontal .increment-arrow,
.scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 8 1 8 1;
}

.scroll-bar {
    -fx-background-color: #232A34;

}

.scroll-bar:horizontal .track,
.scroll-bar:vertical .track {
	-fx-background-color: transparent;
	-fx-border-color: transparent;
	-fx-background-radius: 0em;
	-fx-border-radius: 1em;
}

.scroll-bar:horizontal .thumb,
.scroll-bar:vertical .thumb {
    -fx-background-color:derive(black,90%);
	-fx-background-insets: 1, 0, 0;
	-fx-background-radius: 1em;
}


/*CARD PANE STYLING*/
#cardPane {
    -fx-background-color: #232A34;
    -fx-border-width: 0;
}

.highlightedCardPane {
    -fx-background-color: #CA9733;
    -fx-border-width: 0;
    -fx-border-style: solid inside;
    -fx-border-width: 2;
    -fx-border-insets: 5;
    -fx-border-radius: 5;
}


/*COMMAND FIELD STYLING*/
#commandTypeLabel {
    -fx-font-size: 11px;
    -fx-text-fill: #F70D1A;
}

#commandTextField {
    -fx-background-color: #404040;
    -fx-background-insets: 0;
    -fx-border-color: #404040 #404040 #404040 #404040;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: #A6A6A6;
}

.auto-complete-popup .list-cell {
    -fx-background-color: transparent;
    -fx-font-size: 11pt;
}

.auto-complete-popup .clipped-container {
    -fx-font-family: "Verdana";
    -fx-background-color: grey;
    -fx-background-radius: 2px;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);
}

#resultDisplay .content {
    -fx-background-color: #232A34;
    -fx-background-radius: 0;
    -fx-text-fill: #A6A6A6;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: white;
    -fx-background-color: #232A34;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}


/*PERSON PANEL STYLING*/
#personPanel {
    -fx-background-color: #232A34;
    -fx-padding: 61 35 50 35;
}

#personDetailsBox {
    -fx-padding: 20 20 20 20;
    -fx-background-color: #404040;
    -fx-text-color: white !important;
}

#personDetailsBox #name {
    -fx-padding: 7, 0, 0, 0;
}

#name, #phone, #address, #email {
    -fx-text-fill: white !important;
}

.label {
    -fx-text-fill: #C99733;
}
```
