# sushinoya
###### /java/seedu/room/commons/events/model/EventBookChangedEvent.java
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
###### /java/seedu/room/commons/events/ui/EventPanelSelectionChangedEvent.java
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
###### /java/seedu/room/logic/commands/exceptions/AlreadySortedException.java
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
###### /java/seedu/room/logic/commands/SortCommand.java
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
###### /java/seedu/room/logic/commands/SwaproomCommand.java
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

    public static final String MESSAGE_SWAP_PERSONS_SUCCESS = "Swapped Rooms : %1$s and %2$s";

    private final Index targetIndex1;
    private final Index targetIndex2;

    public SwaproomCommand(Index targetIndex1, Index targetIndex2) {
        this.targetIndex1 = targetIndex1;
        this.targetIndex2 = targetIndex2;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex1.getZeroBased() >= lastShownList.size()/*
        ;lp.oik jhgv*/
                || targetIndex2.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson person1 = lastShownList.get(targetIndex1.getZeroBased());
        ReadOnlyPerson person2 = lastShownList.get(targetIndex2.getZeroBased());

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
###### /java/seedu/room/logic/parser/AddCommandParser.java
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
###### /java/seedu/room/logic/parser/AddEventCommandParser.java
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
                location = new Location(null);
            }

            //Gets the description of the event being added. If no description is provided, it creates an description
            // with null as value
            Optional<Description> optionalDescription = ParserUtil.parseDescription(
                                                                            argMultimap.getValue(PREFIX_DESCRIPTION));
            if (optionalDescription.isPresent()) {
                description = optionalDescription.get();
            } else {
                description = new Description(null);
            }

            //Gets the datetime of the event being added. If no datetime is provided, it creates an datetime with null
            //as value
            Optional<Datetime> optionalDatetime = ParserUtil.parseDatetime(argMultimap.getValue(PREFIX_DATETIME));
            if (optionalDatetime.isPresent()) {
                datetime = optionalDatetime.get();
            } else {
                datetime = new Datetime(null);
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
###### /java/seedu/room/logic/parser/DeleteEventCommandParser.java
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
###### /java/seedu/room/logic/parser/SortCommandParser.java
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
###### /java/seedu/room/logic/parser/SwaproomCommandParser.java
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
###### /java/seedu/room/model/event/Datetime.java
``` java
/**
 * Represents a Event's Datetime in the event book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDatetime(String)}
 */
public class Datetime {

    public static final String MESSAGE_DATETIME_CONSTRAINTS =
            "Event datetime should only contain dd-mm-yyyy hhmm";
    /*
     * The first character of the datetime must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DATETIME_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given datetime.
     *
     * @throws IllegalValueException if given datetime string is invalid.
     */
    public Datetime(String datetime) throws IllegalValueException {
        requireNonNull(datetime);
        if (!isValidDatetime(datetime)) {
            throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
        }
        this.value = datetime;
    }

    /**
     * Returns true if a given string is a valid event datetime.
     */
    public static boolean isValidDatetime(String test) {
        return test.matches(DATETIME_VALIDATION_REGEX);
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
###### /java/seedu/room/model/event/Description.java
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
###### /java/seedu/room/model/event/Event.java
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
###### /java/seedu/room/model/event/Location.java
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
###### /java/seedu/room/model/event/ReadOnlyEvent.java
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
###### /java/seedu/room/model/event/Title.java
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
###### /java/seedu/room/model/event/TitleContainsKeywordsPredicate.java
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
###### /java/seedu/room/model/event/UniqueEventList.java
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
###### /java/seedu/room/model/EventBook.java
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
###### /java/seedu/room/model/ModelManager.java
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
###### /java/seedu/room/model/ModelManager.java
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
        return null;
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
###### /java/seedu/room/model/person/Person.java
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
###### /java/seedu/room/model/person/Person.java
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
###### /java/seedu/room/model/person/Room.java
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
###### /java/seedu/room/model/ReadOnlyEventBook.java
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
###### /java/seedu/room/model/ResidentBook.java
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
###### /java/seedu/room/model/ResidentBook.java
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
###### /java/seedu/room/storage/EventBookStorage.java
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
###### /java/seedu/room/storage/Storage.java
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
###### /java/seedu/room/storage/XmlAdaptedEvent.java
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
###### /java/seedu/room/storage/XmlEventBookStorage.java
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
###### /java/seedu/room/storage/XmlSerializableEventBook.java
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
###### /java/seedu/room/ui/EventCard.java
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
###### /java/seedu/room/ui/EventListPanel.java
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
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new EventPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            eventListView.scrollTo(index);
            eventListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
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
