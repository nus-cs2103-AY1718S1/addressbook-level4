# chernghann
###### \java\seedu\address\commons\events\ui\CalendarPanelSelectionEvent.java
``` java
import seedu.address.commons.events.BaseEvent;

/**
 * Represents a selection change in the Calendar Panel
 */

public class CalendarPanelSelectionEvent extends BaseEvent {

    private final String newSelection;

    public CalendarPanelSelectionEvent (String newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\logic\commands\AddEventCommand.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;

/**
 * Adds a event to the address book.
 */
public class AddEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addevent";
    public static final String COMMAND_ALIAS = "ae";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATE + "DATE "
            + PREFIX_ADDRESS + "ADDRESS "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "ZoukOut "
            + PREFIX_DATE + "05/12/2017 "
            + PREFIX_ADDRESS + "Sentosa Beach, Siloso";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists.";

    private final Event toAdd;

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyPerson}
     */
    public AddEventCommand(ReadOnlyEvent event) {
        toAdd = new Event(event);
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
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddEventCommand) other).toAdd));
    }
}
```
###### \java\seedu\address\logic\parser\AddEventCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;

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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE, PREFIX_ADDRESS);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DATE, PREFIX_ADDRESS)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddEventCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE)).get();
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get();

            ReadOnlyEvent event = new Event(name, date, address);

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
###### \java\seedu\address\model\AddressBook.java
``` java
    public void setEvents(List<? extends ReadOnlyEvent> events) throws DuplicateEventException {
        this.events.setEvent(events);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void addEvent(ReadOnlyEvent p) throws DuplicateEventException {
        Event newEvent = new Event(p);
        events.add(newEvent);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    @Override
    public ObservableList<ReadOnlyEvent> getEventList() {
        return events.asObservableList();
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags))
                && this.events.equals(((AddressBook) other).events);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags, events);
    }
}
```
###### \java\seedu\address\model\event\Date.java
``` java
import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "The day month and year must be valid in form dd-mm-yyyy";

    public static final String DATE_VALIDATION_REGEX = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)"
            + "(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3"
            + "(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$"
            + "|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";

    public final String value;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Date(String date) throws IllegalValueException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!isValidDate(trimmedDate)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        this.value = trimmedDate;
    }

    /**
     * Returns true if a given string is a valid person birthday.
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
                || (other instanceof Date // instanceof handles nulls
                && this.value.equals(((Date) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\event\Event.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;

/**
 * Represents a Event in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Event implements ReadOnlyEvent {

    private ObjectProperty<Name> name;
    private ObjectProperty<Date> date;
    private ObjectProperty<Address> address;

    /**
     * Every field must be present and not null.
     */
    public Event(Name name, Date date, Address address) {
        requireAllNonNull(name, date, address);
        this.name = new SimpleObjectProperty<>(name);
        this.date = new SimpleObjectProperty<>(date);
        this.address = new SimpleObjectProperty<>(address);
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getName(), source.getDate(), source.getAddress());
    }

    public void setName(Name name) {
        this.name.set(requireNonNull(name));
    }

    @Override
    public ObjectProperty<Name> nameProperty() {
        return name;
    }

    @Override
    public Name getName() {
        return name.get();
    }

    public void setAddress(Address address) {
        this.address.set(requireNonNull(address));
    }

    @Override
    public ObjectProperty<Address> addressProperty() {
        return address;
    }

    @Override
    public Address getAddress() {
        return address.get();
    }

    public void setDate(Date date) {
        this.date.set(requireNonNull(date));
    }

    @Override
    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    @Override
    public Date getDate() {
        return date.get();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyEvent // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyEvent) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, date, address);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
```
###### \java\seedu\address\model\event\exceptions\BuildEvent.java
``` java
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;

/**
 * A utility class to help with building Event objects.
 */
public class BuildEvent {
    public static final String DEFAULT_NAME = "ZoukOut";
    public static final String DEFAULT_ADDRESS = "Sentosa, Siloso Beach";
    public static final String DEFAULT_DATE = "12/12/2018";

    private Event event;

    public BuildEvent() {
        try {
            Name defaultName = new Name(DEFAULT_NAME);
            Date defaultDate = new Date(DEFAULT_DATE);
            Address defaultAddress = new Address(DEFAULT_ADDRESS);

            this.event = new Event(defaultName, defaultDate, defaultAddress);

        } catch (IllegalValueException ive) {
            throw new AssertionError("Default event's values are invalid.");
        }
    }

    /**
     * Sets the {@code Name} of the {@code Event} that we are building.
     */
    public BuildEvent withName(String name) {
        try {
            this.event.setName(new Name(name));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Event} that we are building.
     */
    public BuildEvent withAddress(String address) {
        try {
            this.event.setAddress(new Address(address));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Event} that we are building.
     */
    public BuildEvent withDate(String date) {
        try {
            this.event.setDate(new Date(date));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("date is expected to be unique.");
        }
        return this;
    }

    public Event build() {
        return this.event;
    }

}
```
###### \java\seedu\address\model\event\exceptions\DuplicateEventException.java
``` java
import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Person objects.
 */
public class DuplicateEventException extends DuplicateDataException {
    public DuplicateEventException() {
        super("Operation would result in duplicate event");
    }
}
```
###### \java\seedu\address\model\event\exceptions\EventNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified event
 */

public class EventNotFoundException extends Exception{
}
```
###### \java\seedu\address\model\event\ReadOnlyEvent.java
``` java
import javafx.beans.property.ObjectProperty;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;

/**
 * A read-only immutable interface for a Person in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyEvent {

    ObjectProperty<Name> nameProperty();
    Name getName();
    ObjectProperty<Date> dateProperty();
    Date getDate();
    ObjectProperty<Address> addressProperty();
    Address getAddress();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyEvent other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getDate().equals(this.getDate())
                && other.getAddress().equals(this.getAddress()));
    }

    /**
     * Formats the event as text, showing all event details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Date: ")
                .append(getDate())
                .append(" Address: ")
                .append(getAddress());
        return builder.toString();
    }

}
```
###### \java\seedu\address\model\event\UniqueEventList.java
``` java
import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;

/**
 * A list of events that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Event#equals(Object)
 */
public class UniqueEventList implements Iterable<Event> {

    private final ObservableList<Event> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyEvent> mappedList = EasyBind.map(internalList, (event) -> event);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(ReadOnlyEvent toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds an event to the list.
     *
     * @throws DuplicateEventException if the person to add is a duplicate of an existing person in the list.
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
     * @throws DuplicateEventException if the replacement is equivalent to another existing person in the list.
     * @throws EventNotFoundException if {@code target} could not be found in the list.
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

    public void setEvent(UniqueEventList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setEvent(List<? extends ReadOnlyEvent> events) throws DuplicateEventException {
        final UniqueEventList replacement = new UniqueEventList();
        for (final ReadOnlyEvent event : events) {
            replacement.add(new Event(event));
        }
        setEvent(replacement);
    }

    /**
     * Removes the equivalent event from the list.
     *
     * @throws EventNotFoundException if no such person could be found in the list.
     */
    public boolean remove(ReadOnlyEvent toRemove) throws EventNotFoundException {
        requireNonNull(toRemove);
        final boolean eventFoundAndDeleted = internalList.remove(toRemove);
        if (!eventFoundAndDeleted) {
            throw new EventNotFoundException();
        }
        return eventFoundAndDeleted;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyEvent> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    @Override
    public Iterator<Event> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueEventList // instanceof handles nulls
                && this.internalList.equals(((UniqueEventList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### \java\seedu\address\model\Model.java
``` java
    /** Adds the given event */
    void addEvent(ReadOnlyEvent event) throws DuplicateEventException;
```
###### \java\seedu\address\model\Model.java
``` java
    /** Returns an unmodifiable view of the filtered event list */
    ObservableList<ReadOnlyEvent> getFilteredEventList();
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Updates the filter of the filtered events list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<ReadOnlyEvent> predicate);
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void addEvent(ReadOnlyEvent event) throws DuplicateEventException {
        addressBook.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyEvent} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyEvent> getFilteredEventList() {
        return FXCollections.unmodifiableObservableList(filteredEvents);
    }

    @Override
    public void updateFilteredEventList(Predicate<ReadOnlyEvent> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Subscribe
    private void handleAddEvent(AddEventRequestEvent event) throws DuplicateEventException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        addressBook.addEvent(event.event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\person\HomeNumber.java
``` java
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's home number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidHomeNumber(String)}
 */
public class HomeNumber {


    public static final String MESSAGE_HOME_NUMBER_CONSTRAINTS =
            "Home numbers can only contain numbers, and should be at least 3 digits long";
    public static final String HOME_NUMBER_VALIDATION_REGEX = "\\d{3,}";
    public static final String HOME_NUMBER_TEMPORARY = "NIL";

    public final String value;

    /**
     * Validates given home number.
     *
     * @throws IllegalValueException if given home string is invalid.
     */
    public HomeNumber(String homeNumber) throws IllegalValueException {
        if (homeNumber == null) {
            this.value = HOME_NUMBER_TEMPORARY;
        } else {
            String trimmedHomeNumber = homeNumber.trim();
            if (!isValidHomeNumber(trimmedHomeNumber)) {
                throw new IllegalValueException(MESSAGE_HOME_NUMBER_CONSTRAINTS);
            }
            this.value = trimmedHomeNumber;
        }
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidHomeNumber(String test) {
        return test.matches(HOME_NUMBER_VALIDATION_REGEX)
                || test.matches(HOME_NUMBER_TEMPORARY);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HomeNumber // instanceof handles nulls
                && this.value.equals(((HomeNumber) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\Person.java
``` java
    public void setHomeNumber(HomeNumber homeNumber) {
        this.homeNumber.set(requireNonNull(homeNumber));
    }

    @Override
    public ObjectProperty<HomeNumber> homeNumberProperty() {
        return homeNumber;
    }

    @Override
    public HomeNumber getHomeNumber() {
        return homeNumber.get();
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    public void setSchEmail(SchEmail schEmail) {
        this.schEmail.set(requireNonNull(schEmail));
    }

    @Override
    public ObjectProperty<SchEmail> schEmailProperty() {
        return schEmail;
    }

    @Override
    public SchEmail getSchEmail() {
        return schEmail.get();
    }
    public void setWebsite(Website website) {
        this.website.set(requireNonNull(website));
    }
```
###### \java\seedu\address\model\person\SchEmail.java
``` java
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSchEmail(String)}
 */
public class SchEmail {

    public static final String MESSAGE_SCH_EMAIL_CONSTRAINTS =
            "School emails should be 2 alphanumeric/period strings separated by '@'";
    public static final String SCH_EMAIL_VALIDATION_REGEX = "[\\w\\.]+@[\\w\\.]+";
    public static final String SCH_EMAIL_TEMPORARY = "NIL";

    public final String value;

    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public SchEmail(String schEmail) throws IllegalValueException {
        if (schEmail == null) {
            this.value = SCH_EMAIL_TEMPORARY;
        } else {
            String trimmedSchEmail = schEmail.trim();
            if (!isValidSchEmail(trimmedSchEmail)) {
                throw new IllegalValueException(MESSAGE_SCH_EMAIL_CONSTRAINTS);
            }
            this.value = trimmedSchEmail;
        }
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidSchEmail(String test) {
        return test.matches(SCH_EMAIL_VALIDATION_REGEX)
                || test.matches(SCH_EMAIL_TEMPORARY);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SchEmail // instanceof handles nulls
                && this.value.equals(((SchEmail) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\ReadOnlyAddressBook.java
``` java
    ObservableList<ReadOnlyEvent> getEventList();
```
###### \java\seedu\address\storage\Storage.java
``` java
    Optional<ReadOnlyAddressBook> readBackupAddressBook() throws DataConversionException, IOException;
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    public Optional<ReadOnlyAddressBook> readBackupAddressBook() throws DataConversionException, IOException {
        return readAddressBook(backUpLocation.getAddressBookFilePath());
    }
```
###### \java\seedu\address\storage\StorageManager.java
``` java

    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
            backUpAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

```
###### \java\seedu\address\storage\StorageManager.java
``` java
    private void backUpAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        String backupPath = backUpLocation.getAddressBookFilePath();
        logger.fine("Backing up data to: " + backupPath);
        saveAddressBook(addressBook, backupPath);
    }
```
###### \java\seedu\address\ui\AddEventRequestEvent.java
``` java
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.event.ReadOnlyEvent;

/**
 * Indicates a request to add Event.
 */
public class AddEventRequestEvent extends BaseEvent {

    public final ReadOnlyEvent event;

    public AddEventRequestEvent(ReadOnlyEvent event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\ui\AnchorPaneNode.java
``` java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CalendarPanelSelectionEvent;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.BuildEvent;

/**
 * Create an anchor pane that can store additional data.
 */
public class AnchorPaneNode extends AnchorPane {

    // Date associated with this pane
    private LocalDate date;
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    /**
     * Create a anchor pane node. Date is not assigned in the constructor.
     * @param children children of the anchor pane
     */
    public AnchorPaneNode(Node... children) {
        super(children);
        // Add action handler for mouse clicked
        this.setOnMouseClicked(e ->
                handleCalendarEvent(date));
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Handles the event when the anchorpane is being clicked
     * @param event
     */
    private void handleCalendarEvent(LocalDate event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String newDate = formatter.format(event);
        startDialog(newDate);
        EventsCenter.getInstance().post(new CalendarPanelSelectionEvent(date.toString()));
        logger.info(LogsCenter.getEventHandlingLogMessage(new CalendarPanelSelectionEvent(newDate)));
    }

    /**
     * This creates the Dialog window for Add Event
     */
    private void startDialog(String date) {
        Dialog<Event> dialog = new Dialog<>();
        dialog.setTitle("Add Event");
        dialog.setHeaderText("Fill up the details for the event on " + date);

        Label label1 = new Label("Name: ");
        Label label2 = new Label("Address: ");
        TextField text1 = new TextField();
        TextField text2 = new TextField();

        GridPane grid = new GridPane();
        grid.add(label1, 1, 1);
        grid.add(text1, 2, 1);
        grid.add(label2, 1, 2);
        grid.add(text2, 2, 2);
        dialog.getDialogPane().setContent(grid);

        ButtonType button = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(button);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == button) {
                return new BuildEvent().withName(text1.getText()).withDate(date).withAddress(text2.getText()).build();
            }
            return null;
        });

        Optional<Event> result = dialog.showAndWait();
        result.ifPresent(event -> {
            EventsCenter.getInstance().post(new AddEventRequestEvent(event));
        });
    }
}
```
###### \java\seedu\address\ui\Calendar.java
``` java
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * The UI component that is responsible for implemented Calendar.
 */
public class Calendar {

    private ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(35);
    private VBox view;
    private Text calendarTitle;
    private YearMonth currentYearMonth;

    /**
     * Create a calendar view
     * @param yearMonth year month to create the calendar of
     */
    public Calendar(YearMonth yearMonth) {
        currentYearMonth = yearMonth;
        // Create the calendar grid pane
        GridPane calendar = new GridPane();
        calendar.setPrefSize(600, 400);
        // Create rows and columns with anchor panes for the calendar
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPaneNode ap = new AnchorPaneNode();
                ap.setPrefSize(200, 200);
                calendar.add(ap, j, i);
                allCalendarDays.add(ap);
                ap.getStyleClass().add("calendar-color");
            }
        }
        // Days of the week labels
        Text[] dayNames = new Text[]{ new Text("Sunday"), new Text("Monday"),
                                      new Text("Tuesday"), new Text("Wednesday"), new Text("Thursday"),
                                      new Text("Friday"), new Text("Saturday") };


        GridPane dayLabels = new GridPane();
        dayLabels.setPrefWidth(600);
        Integer col = 0;
        for (Text txt : dayNames) {
            txt.getStyleClass().add("calendar-color");
            AnchorPane ap = new AnchorPane();
            ap.setPrefSize(200, 10);
            ap.setBottomAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            dayLabels.add(ap, col++, 0);
        }

        // Create calendarTitle and buttons to change current month
        calendarTitle = new Text();
        calendarTitle.getStyleClass().add("calendar-color");
        Button previousMonth = new Button("<");
        previousMonth.setOnAction(e -> previousMonth());
        Button nextMonth = new Button(">");
        nextMonth.setOnAction(e -> nextMonth());
        HBox titleBar = new HBox(previousMonth, calendarTitle, nextMonth);
        titleBar.setSpacing(5);
        titleBar.setAlignment(Pos.BASELINE_CENTER);
        // Populate calendar with the appropriate day numbers
        populateCalendar(yearMonth);
        // Create the calendar view
        view = new VBox(titleBar, dayLabels, calendar);
    }

    /**
     * Set the days of the calendar to correspond to the appropriate date
     * @param yearMonth year and month of month to render
     */
    public void populateCalendar(YearMonth yearMonth) {
        // Get the date we want to start with on the calendar
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        // Dial back the day until it is SUNDAY (unless the month starts on a sunday)
        while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY")) {
            calendarDate = calendarDate.minusDays(1);
        }
        // Populate the calendar with day numbers
        for (AnchorPaneNode ap : allCalendarDays) {
            if (ap.getChildren().size() != 0) {
                ap.getChildren().remove(0);
            }
            Text txt = new Text(String.valueOf(calendarDate.getDayOfMonth()));
            txt.getStyleClass().add("calendar-color");
            ap.setDate(calendarDate);
            ap.setTopAnchor(txt, 5.0);
            ap.setLeftAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            calendarDate = calendarDate.plusDays(1);
        }
        // Change the title of the calendar
        calendarTitle.setText(yearMonth.getMonth().toString() + " " + String.valueOf(yearMonth.getYear()));
    }

    /**
     * Move the month back by one. Repopulate the calendar with the correct dates.
     */
    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        populateCalendar(currentYearMonth);
    }

    /**
     * Move the month forward by one. Repopulate the calendar with the correct dates.
     */
    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        populateCalendar(currentYearMonth);
    }

    public VBox getView() {
        return view;
    }

    public ArrayList<AnchorPaneNode> getAllCalendarDays() {
        return allCalendarDays;
    }

    public void setAllCalendarDays(ArrayList<AnchorPaneNode> allCalendarDays) {
        this.allCalendarDays = allCalendarDays;
    }
}
```
