# reginleiff
###### \java\seedu\address\logic\commands\event\AddEventCommand.java
``` java
package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMESLOT;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.EventTimeClashException;

/**
 * Adds an event to the address book.
 */
public class AddEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "eventadd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "TITLE "
            + PREFIX_TIMESLOT + "TIMING "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John's 21st Birthday "
            + PREFIX_TIMESLOT + "22/10/2017 1900-2200 "
            + PREFIX_DESCRIPTION + "johnd@example.com ";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_TIME_CLASH = "This event has time clash with an existing event";

    private final Event toAdd;

    /**
     * Creates an AddEventCommand to add the specified {@code ReadOnlyEvent}
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
        } catch (EventTimeClashException e) {
            throw new CommandException(MESSAGE_TIME_CLASH);
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
###### \java\seedu\address\logic\commands\event\DeleteEventCommand.java
``` java
package seedu.address.logic.commands.event;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.EventNotFoundException;

/**
 * Deletes a event identified using it's last displayed index from the address book.
 */
public class DeleteEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "eventdel";

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
        } catch (EventNotFoundException pnfe) {
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
###### \java\seedu\address\logic\commands\event\EditEventCommand.java
``` java
package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMESLOT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.Title;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.event.exceptions.EventTimeClashException;
import seedu.address.model.event.timeslot.Timeslot;

/**
 * Edits the details of an existing event in the address book.
 */
public class EditEventCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "eventedit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the event identified "
            + "by the index number used in the last event listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "TITLE] "
            + "[" + PREFIX_TIMESLOT + "TIMING] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TIMESLOT + "1300-1500 "
            + PREFIX_DESCRIPTION + "New description for event x";

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited Event: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_TIME_CLASH = "The edited event has time clash with an existing event";

    private final Index index;
    private final EditEventDescriptor editEventDescriptor;

    /**
     * @param index               of the event in the filtered event list to edit
     * @param editEventDescriptor details to edit the event with
     */
    public EditEventCommand(Index index, EditEventDescriptor editEventDescriptor) {
        requireNonNull(index);
        requireNonNull(editEventDescriptor);

        this.index = index;
        this.editEventDescriptor = new EditEventDescriptor(editEventDescriptor);
    }

    /**
     * Creates and returns a {@code Event} with the details of {@code eventToEdit}
     * edited with {@code editEventDescriptor}.
     */
    private static Event createEditedEvent(ReadOnlyEvent eventToEdit,
                                           EditEventDescriptor editEventDescriptor) {
        assert eventToEdit != null;

        Title updatedTitle = editEventDescriptor.getTitle().orElse(eventToEdit.getTitle());
        Timeslot updatedTimeslot = editEventDescriptor.getTimeslot().orElse(eventToEdit.getTimeslot());
        Description updatedDescription = editEventDescriptor.getDescription().orElse(eventToEdit.getDescription());

        return new Event(updatedTitle, updatedTimeslot, updatedDescription);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyEvent> lastShownList = model.getFilteredEventList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        ReadOnlyEvent eventToEdit = lastShownList.get(index.getZeroBased());
        Event editedEvent = createEditedEvent(eventToEdit, editEventDescriptor);

        try {
            model.updateEvent(eventToEdit, editedEvent);
        } catch (EventNotFoundException pnfe) {
            throw new AssertionError("The target event cannot be missing");
        } catch (EventTimeClashException etce) {
            throw new CommandException(MESSAGE_TIME_CLASH);
        }
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, editedEvent));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditEventCommand)) {
            return false;
        }

        // state check
        EditEventCommand e = (EditEventCommand) other;
        return index.equals(e.index)
                && editEventDescriptor.equals(e.editEventDescriptor);
    }

    /**
     * Stores the details to edit the event with. Each non-empty field value will replace the
     * corresponding field value of the event.
     */
    public static class EditEventDescriptor {
        private Title title;
        private Timeslot timeslot;
        private Description description;

        public EditEventDescriptor() {
        }

        public EditEventDescriptor(EditEventDescriptor toCopy) {
            this.title = toCopy.title;
            this.timeslot = toCopy.timeslot;
            this.description = toCopy.description;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.title, this.timeslot, this.description);
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Timeslot> getTimeslot() {
            return Optional.ofNullable(timeslot);
        }

        public void setTimeslot(Timeslot timeslot) {
            this.timeslot = timeslot;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEventDescriptor)) {
                return false;
            }

            // state check
            EditEventDescriptor e = (EditEventDescriptor) other;

            return getTitle().equals(e.getTitle())
                    && getTimeslot().equals(e.getTimeslot())
                    && getDescription().equals(e.getDescription());
        }
    }
}

```
###### \java\seedu\address\logic\commands\event\FindEventCommand.java
``` java
package seedu.address.logic.commands.event;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.event.TitleContainsKeywordsPredicate;

/**
 * @@reginleiff Finds and lists all events in Sales Navigator which title contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindEventCommand extends Command {
    public static final String COMMAND_WORD = "eventfind";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all events whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice birthday";

    private final TitleContainsKeywordsPredicate predicate;

    public FindEventCommand(TitleContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredEventList(predicate);
        return new CommandResult(getMessageForEventListShownSummary(model.getFilteredEventList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindEventCommand // instanceof handles nulls
                && this.predicate.equals(((FindEventCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\Logic.java
``` java
    /**
     * Returns an unmodifiable view of the filtered list of events
     */
    ObservableList<ReadOnlyEvent> getFilteredEventList();

    /**
     * Returns an unmodifiable view of the schedule.
     */
    ObservableList<ReadOnlyEvent> getSchedule();

```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Override
    public ObservableList<ReadOnlyEvent> getFilteredEventList() {
        return model.getFilteredEventList();
    }

    @Override
    public ObservableList<ReadOnlyEvent> getSchedule() {
        return model.getSchedule();
    }
```
###### \java\seedu\address\logic\parser\event\DateParser.java
``` java
package seedu.address.logic.parser.event;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses a date string input into an array of date arguments (day, month, year).
 */
public class DateParser {

    /**
     * Main parser function
     *
     * @param date input argument
     * @return array of arguments (day, month, year)
     * @throws ParseException if format of date input is not dd/mm/yyyy
     */
    public int[] parse(String date) throws ParseException {
        int[] dateInfo = new int[3];

        try {
            dateInfo[0] = Integer.parseInt(date.substring(0, 2));
            dateInfo[1] = Integer.parseInt(date.substring(3, 5));
            dateInfo[2] = Integer.parseInt(date.substring(6, 10));
        } catch (Exception e) {
            throw new ParseException(e.getMessage());
        }
        return dateInfo;
    }
}

```
###### \java\seedu\address\model\AddressBook.java
``` java
    private Date currentDate;
    private final EventList events;
```
###### \java\seedu\address\model\AddressBook.java
``` java
        events = new EventList();
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void setEvents(List<? extends ReadOnlyEvent> events) throws EventTimeClashException {
        this.events.setEvents(events);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
        try {
            setEvents(newData.getEventList());
        } catch (EventTimeClashException e) {
            assert false : "AddressBooks should not have time-clashing events";
        }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    //// event-level operations

    /**
     * Adds an event to the address book.
     */
    public void addEvent(ReadOnlyEvent e) throws EventTimeClashException {
        Event newEvent = new Event(e);
        events.add(newEvent);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     *
     * @throws EventNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeEvent(ReadOnlyEvent key) throws EventNotFoundException {
        lastChangedEvent = key;
        if (events.remove(key)) {
            return true;
        } else {
            throw new EventNotFoundException();
        }
    }

    /**
     * Replaces the given event {@code target} in the list with {@code editedReadOnlyEvent}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedReadOnlyEvent}.
     * another existing event in the list.
     *
     * @throws EventNotFoundException if {@code target} could not be found in the list.
     */
    public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedReadOnlyEvent)
            throws EventNotFoundException, EventTimeClashException {
        requireNonNull(editedReadOnlyEvent);
        Event editedEvent = new Event(editedReadOnlyEvent);
        events.setEvent(target, editedEvent);
        lastChangedEvent = target;
    }

    @Override
    public ReadOnlyEvent getLastChangedEvent() {
        return this.lastChangedEvent;
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    @Override
    public ObservableList<ReadOnlyEvent> getEventList() {
        return events.asObservableList();
    }

    @Override
    public ObservableList<ReadOnlyEvent> getSchedule(Date currentDate) {
        return events.getObservableSubList(currentDate);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java

    /**
     *
     * Gets the current date and returns the local implementation of date.
     *
     * @return the current date
     */
    @Override
    public Date getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date currentDate = new java.util.Date();

        try {
            return new Date(dateFormat.format(currentDate));
        } catch (IllegalValueException e) {
            return null;
        }
    }
}
```
###### \java\seedu\address\model\event\Description.java
``` java
/**
 * Represents an Event's Description in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class Description {
    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS =
            "Event descriptions should not be blank";

    /*
     * The first character of the Description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DESCRIPTION_VALIDATION_REGEX = ".*[^ ].*";

    public final String description;

    /**
     * Validates given Description.
     *
     * @throws IllegalValueException if given Description string is invalid.
     */
    public Description(String description) throws IllegalValueException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!isValidDescription(trimmedDescription)) {
            throw new IllegalValueException(MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        this.description = trimmedDescription;
    }

    /**
     * Returns true if a given string is a valid event's Description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && this.description.equals(((Description) other).description)); // state check
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }
}
```
###### \java\seedu\address\model\event\Event.java
``` java
/**
 * Represents an Event in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Event implements ReadOnlyEvent {
    private ObjectProperty<Title> title;
    private ObjectProperty<Date> date;
    private ObjectProperty<Timing> timing;
    private ObjectProperty<Timeslot> timeslot;
    private ObjectProperty<Description> description;


    /**
     * Every field must be present and not null.
     */
    public Event(Title title, Timeslot timeslot, Description description) {
        requireAllNonNull(title, timeslot, description);
        this.title = new SimpleObjectProperty<>(title);
        this.date = new SimpleObjectProperty<>(timeslot.getDate());
        this.timing = new SimpleObjectProperty<>(timeslot.getTiming());
        this.timeslot = new SimpleObjectProperty<>(timeslot);
        this.description = new SimpleObjectProperty<>(description);
    }

    /**
     * Creates a copy of the given ReadOnlyEvent.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getTitle(), source.getTimeslot(), source.getDescription());
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
    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    @Override
    public Date getDate() {
        return date.get();
    }

    public void setDate(Date date) {
        this.date.set(requireNonNull(date));
    }

    @Override
    public ObjectProperty<Timeslot> timeslotProperty() {
        return timeslot;
    }

    @Override
    public ObjectProperty<Timing> timingProperty() {
        return timing;
    }

    @Override
    public Timing getTiming() {
        return timing.get();
    }

    public void setTiming(Timing timing) {
        this.timing.set(requireNonNull(timing));
    }

    @Override
    public Timeslot getTimeslot() {
        return timeslot.get();
    }

    public void setTimeslot(Timeslot timeslot) {
        this.timeslot.set(requireNonNull(timeslot));
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

    /**
     * Check if this event happens at an earlier time than the given timeslot.
     * @return true if indeed earlier.
     */
    public boolean happensBefore(Timeslot slot) {
        int comparison = this.getTimeslot().compareTo(slot);
        return comparison < 0;
    }

    /**
     * Check if this event happens at a later time than the given timeslot.
     * @return true if indeed earlier.
     */
    public boolean happensAfter(Timeslot slot) {
        int comparison = this.getTimeslot().compareTo(slot);
        return comparison > 0;
    }

    /**
     * Obtain the duration of the event.
     * @return a Duration object.
     */
    public Duration getDuration() {
        return Duration.ofMinutes(MINUTES.between(this.getStartTime(), this.getEndTime()));
    }

    /**
     * Obtain the start time of the event.
     * @return a LocalTime object.
     */
    public LocalTime getStartTime() {
        int start = this.getTiming().getStart();
        return LocalTime.of(start / 100, start % 100);
    }

    /**
     * Obtain the end time of the event.
     * @return a LocalTime object.
     */
    public LocalTime getEndTime() {
        int end = this.getTiming().getEnd();
        return LocalTime.of(end / 100, end % 100);
    }

    /**
     * Check if two events have time clash.
     * @param other Event to compare with
     * @return true if clashes.
     */
    public boolean clashesWith(Event other) {
        int ts = this.getTiming().getStart();
        int te = this.getTiming().getEnd();
        int os = other.getTiming().getStart();
        int oe = other.getTiming().getEnd();

        if (this.getDate().equals(other.getDate()) && !(ts >= oe) && !(te <= os)) {
            return true;
        }
        return false;
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
        return Objects.hash(title, timeslot, description);
    }

    @Override
    public String toString() {
        return getAsText();
    }
}
```
###### \java\seedu\address\model\event\EventList.java
``` java
/**
 * A list of events that does not allow nulls.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Event#equals(Object)
 */
public class EventList implements Iterable<Event> {

    private static final String MIDNIGHT_HOURS = " 0000-0001";

    private static final Logger logger = LogsCenter.getLogger(EventList.class);

    private final ObservableTreeMap<Timeslot, Event> internalMap = new
            ObservableTreeMap<>();
    // used by asObservableList()
    private final ObservableList<ReadOnlyEvent> mappedList = FXCollections.observableArrayList(new
            ArrayList<>(internalMap.values()));

    public EventList() {
        internalMap.addListener((MapChangeListener.Change<? extends Timeslot, ? extends Event> change) -> {
            logger.info("Change heard.");
            boolean removed = change.wasRemoved();
            if (removed != change.wasAdded()) {
                if (removed) {
                    mappedList.remove(change.getValueRemoved());
                } else {
                    mappedList.add(change.getValueAdded());
                }
            }
        });
    }
    /**
     * Adds a event to the tree map.
     */
    public void add(ReadOnlyEvent toAdd) throws EventTimeClashException {
        requireNonNull(toAdd);
        if (hasClashWith(new Event(toAdd))) {
            throw new EventTimeClashException();
        }
        internalMap.put(toAdd.getTimeslot(), new Event(toAdd));
    }

    /**
     * Replaces the event {@code target} in the tree map with {@code editedEvent}.
     *
     * @throws EventNotFoundException if {@code target} could not be found in the tree map.
     */
    public void setEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent)
            throws EventNotFoundException, EventTimeClashException {
        requireNonNull(editedEvent);

```
###### \java\seedu\address\model\event\exceptions\EventNotFoundException.java
``` java
package seedu.address.model.event.exceptions;

/**
 * Signals that the operation is unable to find the specified event.
 */
public class EventNotFoundException extends Exception {
    public EventNotFoundException() {
        super("Unable to find specified event");
    }
}
```
###### \java\seedu\address\model\event\ReadOnlyEvent.java
``` java
package seedu.address.model.event;

import java.time.Duration;
import java.time.LocalTime;

import javafx.beans.property.ObjectProperty;
import seedu.address.model.event.timeslot.Date;
import seedu.address.model.event.timeslot.Timeslot;
import seedu.address.model.event.timeslot.Timing;

```
###### \java\seedu\address\model\event\ReadOnlyEvent.java
``` java
/**
 * A read-only immutable interface for an Event in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyEvent {
    ObjectProperty<Title> titleProperty();

    Title getTitle();

    ObjectProperty<Date> dateProperty();

    Date getDate();

    ObjectProperty<Timeslot> timeslotProperty();

    Timeslot getTimeslot();

    ObjectProperty<Timing> timingProperty();

    Timing getTiming();

    ObjectProperty<Description> descriptionProperty();

    Description getDescription();

    boolean happensBefore(Timeslot slot);

    boolean happensAfter(Timeslot slot);

    Duration getDuration();

    LocalTime getStartTime();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyEvent other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTitle().equals(this.getTitle()) // state checks here onwards
                && other.getTimeslot().equals(this.getTimeslot())
                && other.getDescription().equals(this.getDescription()));
    }

    /**
     * Formats the event as text, showing all its details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Timeslot: ")
                .append(getTimeslot())
                .append(" Description: ")
                .append(getDescription());
        return builder.toString();
    }
}
```
###### \java\seedu\address\model\event\timeslot\Date.java
``` java
/**
 * Represents an Timeslot's date in sales navigator.
 * Is valid as declared in {@link #isValidDate(int, int, int)}
 */
public class Date implements Comparable<Date> {
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Dates should represent a valid date in the gregorian calendar";

    private int day;
    private int month;
    private int year;
    private String date;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public Date(String date) throws IllegalValueException {
        requireNonNull(date);

        String trimmedDate = date.trim();
        DateParser parser = new DateParser();

        int[] dateInfo = parser.parse(trimmedDate);
        //Check if valid gregorian date
        this.day = dateInfo[0];
        this.month = dateInfo[1];
        this.year = dateInfo[2];

        if (isValidDate(year, month, day)) {
            this.date = trimmedDate;
        } else {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }

    }

    /**
     * Checks if the given arguments for a date is valid in the gregorian calendar.
     */
    public boolean isValidDate(int year, int month, int day) {
        try {
            LocalDate.of(year, month, day);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && this.date.equals(((Date) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

    @Override
    public int compareTo(Date other) {
        int thisYear = this.getYear();
        int comparingYear = other.getYear();
        int thisMonth = this.getMonth();
        int comparingMonth = other.getMonth();
        int thisDay = this.getDay();
        int comparingDay = other.getDay();

        if (thisYear != comparingYear) {
            return thisYear - comparingYear;
        } else if (thisMonth != comparingMonth) {
            return thisMonth - comparingMonth;
        } else {
            return thisDay - comparingDay;
        }
    }

    public LocalDate toLocalDate() {
        return LocalDate.of(year, month, day);
    }

    /**
     * Sets date after {@code days} have elapsed. Works for negative numbers.
     */
    public Date addDays(int days) {
        Calendar cal = Calendar.getInstance();
        cal.set(this.year, this.month, this.day);
        cal.add(Calendar.DATE, days);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        try {
            return new Date(dateBuilder(day, month, year));
        } catch (IllegalValueException e) {
            return null;
        }
    }

    /**
     * Builds a string in the format of a correct date.
     */
    private String dateBuilder(int day, int month, int year) {
        String date = "";
        if (day < 10) {
            date += "0" + String.valueOf(day) + "/";
        } else {
            date += String.valueOf(day) + "/";
        }

        if (month < 10) {
            date += "0" + String.valueOf(month) + "/";
        } else {
            date += String.valueOf(month) + "/";
        }

        date += String.valueOf(year);

        return date;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    //================================= Setter methods for testing ==========================================

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
```
###### \java\seedu\address\model\event\timeslot\Timeslot.java
``` java
/**
 * Timeslot contains date and time and is comparable based on year, month, day and starting time, in this order.
 */
public class Timeslot implements Comparable<Timeslot> {

    public static final String MESSAGE_TIMESLOT_CONSTRAINTS =
            "Proper format: [dd/mm/yyyy ssss-eeee] Event timings should not be blank but contain:\n "
                    + "1. A 6-digit date specifying day, month and year (in the format of dd/mm/yyyy) followed by\n "
                    + "2. A 4-digit 24-hour format start timing and end timing separated by a \"-\"";
    public static final String TIMESLOT_VALIDATION_REGEX =
            "(0[1-9]|1[0-9]|2[0-9]|3[0-1])/(0[1-9]|1[0-2])/[0-9][0-9][0-9][0-9] "

                    + "(0[0-9]|1[0-9]|2[0-3])[0-5][0-9]-(0[0-9]|1[0-9]|2[0-3])[0-5][0-9]";

    private Date date;
    private Timing timing;

    /**
     * Validates given Timeslot.
     *
     * @throws IllegalValueException if given timeslot arguments are invalid.
     */
    public Timeslot(String timeslot) throws IllegalValueException {
        requireNonNull(timeslot);
        String trimmedTimeslot = timeslot.trim();

        if (!isValidTiming(trimmedTimeslot)) {
            throw new IllegalValueException(MESSAGE_TIMESLOT_CONSTRAINTS);
        }

        String[] args = trimmedTimeslot.split(" ");
        String dateArgs = args[0];
        String timeArgs = args[1];

        try {
            this.date = new Date(dateArgs);
            this.timing = new Timing(timeArgs);
        } catch (IllegalValueException e) {
            throw e;
        }
    }

    /**
     * Returns true if a given string is a valid event's Timing.
     */
    public static boolean isValidTiming(String test) {
        return test.matches(TIMESLOT_VALIDATION_REGEX);
    }

    public Date getDate() {
        return date;
    }

    public Timing getTiming() {
        return timing;
    }

    public String toString() {
        return this.date.toString() + " " + this.timing.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Timeslot // instanceof handles nulls
                && this.date.equals(((Timeslot) other).date)) // state check
                && this.timing.equals(((Timeslot) other).timing);
    }

    @Override
    public int compareTo(Timeslot other) {
        if (!this.getDate().equals(other.getDate())) {
            return this.getDate().compareTo(other.getDate());
        } else {
            return this.getTiming().compareTo(other.getTiming());
        }
    }

    //================================= Setter methods for testing ==========================================
    public void withDay(int day) {
        this.date.setDay(day);
    }

    public void withMonth(int month) {
        this.date.setMonth(month);
    }

    public void withYear(int year) {
        this.date.setYear(year);
    }

    public void withStartTime(int start) {
        this.timing.setStart(start);
    }

    public void withEndTime(int end) {
        this.timing.setEnd(end);
    }
}
```
###### \java\seedu\address\model\event\timeslot\Timing.java
``` java
/**
 * Represents an Timeslot's timing in sales navigator.
 * Is valid as declared in {@link #isValidTiming(String)}
 */
public class Timing implements Comparable<Timing> {
    public static final String MESSAGE_TIMING_CONSTRAINTS =
            "Event timings should contain a 4-digit 24-hour format start timing and end timing separated by a \"-\", "
                    + "and it should not be blank";

    public static final String TIMING_VALIDATION_REGEX =
            "(0[0-9]|1[0-9]|2[0-3])[0-5][0-9]-(0[0-9]|1[0-9]|2[0-3])[0-5][0-9]";

    private String timing;
    private int start;
    private int end;

    /**
     * Validates given Timing.
     *
     * @throws IllegalValueException if given Timing string is invalid.
     */
    public Timing(String timing) throws IllegalValueException {
        requireNonNull(timing);
        String trimmedTiming = timing.trim();

        String[] timings = trimmedTiming.split("-");
        String start = timings[0];
        String end = timings[1];

        int startTiming = Integer.parseInt(start);
        int endTiming = Integer.parseInt(end);

        if (!isValidTiming(trimmedTiming) || !isValidTimingInterval(startTiming, endTiming)) {
            throw new IllegalValueException(MESSAGE_TIMING_CONSTRAINTS);
        }
        this.timing = trimmedTiming;
        this.start = startTiming;
        this.end = endTiming;
    }

    /**
     * Returns true if a given string is a valid event's Timing.
     */
    public static boolean isValidTiming(String test) {
        return test.matches(TIMING_VALIDATION_REGEX);
    }

    /**
     * Returns true if start timing and end timing forms a valid 24-hour interval.
     *
     * @param start starting time
     * @param end   ending time
     * @return if timing is a valid 24-hour interval
     */
    public static boolean isValidTimingInterval(int start, int end) {
        if (start > end) {
            return false;
        } else {
            return true;
        }
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
        setTiming(this.start + "-" + this.end);
    }

    public int getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return timing;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Timing // instanceof handles nulls
                && this.timing.equals(((Timing) other).timing)); // state check
    }

    @Override
    public int hashCode() {
        return timing.hashCode();
    }

    //================================= Setter methods for testing ==========================================

    @Override
    public int compareTo(Timing other) {
        return this.getStart() - other.getStart();
    }

    public void setEnd(int end) {
        this.end = end;
        setTiming(this.start + "-" + this.end);
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }
}
```
###### \java\seedu\address\model\event\Title.java
``` java
/**
 * Represents an Event's title in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTitle(String)}
 */
public class Title {
    public static final String MESSAGE_TITLE_CONSTRAINTS =
            "Event titles should not be blank";

    /*
     * The first character of the title must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TITLE_VALIDATION_REGEX = ".*[^ ].*";

    public final String title;

    /**
     * Validates given title.
     *
     * @throws IllegalValueException if given title string is invalid.
     */
    public Title(String title) throws IllegalValueException {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        if (!isValidTitle(trimmedTitle)) {
            throw new IllegalValueException(MESSAGE_TITLE_CONSTRAINTS);
        }
        this.title = trimmedTitle;
    }

    /**
     * Returns true if a given string is a valid event title.
     */
    public static boolean isValidTitle(String test) {
        return !test.equals("") && test.matches(TITLE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Title // instanceof handles nulls
                && this.title.equals(((Title) other).title)); // state check
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }
}
```
###### \java\seedu\address\model\event\TitleContainsKeywordsPredicate.java
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
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(event.getTitle().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TitleContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TitleContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Adds the given event
     */
    void addEvent(ReadOnlyEvent event) throws EventTimeClashException;

    /**
     *  Deletes the given event.
     */
    void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException;

    /**
     * @throws EventNotFoundException if {@code target} could not be found in the list.
     * Replaces the given event {@code target} with {@code editedPerson}.
     */
    void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent) throws EventNotFoundException,
            EventTimeClashException;

    /**
     * Returns an unmodifiable view of the filtered event list
     */
    ObservableList<ReadOnlyEvent> getFilteredEventList();

    /**
     * Returns an unmodifiable view of the schedule
     */
    ObservableList<ReadOnlyEvent> getSchedule();

    /**
     * @throws NullPointerException if {@code predicate} is null.
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     */
    void updateFilteredEventList(Predicate<ReadOnlyEvent> predicate);


```
###### \java\seedu\address\model\ModelManager.java
``` java
    private FilteredList<ReadOnlyEvent> filteredEvents;
    private FilteredList<ReadOnlyEvent> scheduledEvents;
```
###### \java\seedu\address\model\ModelManager.java
``` java
    //=========== Schedule Accessors  =========================================================================

    @Override
    public ObservableList<ReadOnlyEvent> getSchedule() {
        return FXCollections.unmodifiableObservableList(scheduledEvents);
    }

    //=========== Filtered Event List Accessors  ==============================================================

    @Override
    public void updateFilteredEventList(Predicate<ReadOnlyEvent> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }

    @Override
    public ObservableList<ReadOnlyEvent> getFilteredEventList() {
        Predicate<? super ReadOnlyEvent> predicate = filteredEvents.getPredicate();
        filteredEvents = new FilteredList<>(this.addressBook.getEventList());
        filteredEvents.setPredicate(predicate);
        ObservableList<ReadOnlyEvent> list = FXCollections.unmodifiableObservableList(filteredEvents);
        return list;
    }

    //=========== Event Operations  ===========================================================================

    @Override
    public synchronized void addEvent(ReadOnlyEvent event) throws EventTimeClashException {
        addressBook.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException {
        addressBook.removeEvent(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent)
            throws EventNotFoundException, EventTimeClashException {
        addressBook.updateEvent(target, editedEvent);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\ReadOnlyAddressBook.java
``` java
    /**
     * Returns an unmodifiable view of the events list.
     */
    ObservableList<ReadOnlyEvent> getEventList();

    /**
     * Returns an unmodifiable view of the schedule.
     */
    ObservableList<ReadOnlyEvent> getSchedule(Date currentDate);

```
###### \java\seedu\address\storage\XmlAdaptedEvent.java
``` java
/**
 * JAXB-friendly version of the Event.
 */
public class XmlAdaptedEvent {

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String timeslot;
    @XmlElement(required = true)
    private String description;

    /**
     * Constructs an XmlAdaptedEvent.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEvent() {
    }


    /**
     * Converts a given Event into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedEvent
     */
    public XmlAdaptedEvent(ReadOnlyEvent source) {
        title = source.getTitle().toString();
        timeslot = source.getTimeslot().toString();
        description = source.getDescription().toString();
    }

    /**
     * Converts this jaxb-friendly adapted event objet into the model's Event object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event
     */
    public Event toModelType() throws IllegalValueException {
        final Title title = new Title(this.title);
        final Timeslot timeslot = new Timeslot(this.timeslot);
        final Description description = new Description(this.description);
        return new Event(title, timeslot, description);
    }
}

```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
    @XmlElement
    private List<XmlAdaptedEvent> events;
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
        events = new ArrayList<>();
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
        events.addAll(src.getEventList().stream().map(XmlAdaptedEvent::new).collect(Collectors.toList()));
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
    @Override
    public ObservableList<ReadOnlyEvent> getEventList() {
        final ObservableList<ReadOnlyEvent> events = this.events.stream().map(event -> {
            try {
                return event.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(events);
    }

    @Override
    public ObservableList<ReadOnlyEvent> getSchedule(Date currentDate) {
        return null;
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    private EventPanel schedulePanel;
    private ScheduleListPanel scheduleListPanel;
```
###### \java\seedu\address\ui\ScheduleListCard.java
``` java

/**
 * An UI component that displays information of a {@code Event} on the schedule.
 *
 */
public class ScheduleListCard extends UiPart<Region> {

    private static final String FXML = "ScheduleListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable titles cannot be set to such keywords
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
    private Label timing;

    public ScheduleListCard(ReadOnlyEvent event) {
        super(FXML);
        this.event = event;
        bindListeners(event);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Event} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyEvent event) {
        title.textProperty().bind(Bindings.convert(event.titleProperty()));
        timing.textProperty().bind(Bindings.convert(event.timingProperty()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ScheduleListCard)) {
            return false;
        }

        // state check
        ScheduleListCard card = (ScheduleListCard) other;
        return event.equals(card.event);
    }
}
```
###### \java\seedu\address\ui\ScheduleListPanel.java
``` java
/**
 * Panel containing the list of events in the schedule.
 *
 */
public class ScheduleListPanel extends UiPart<Region> {
    private static final String FXML = "ScheduleListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ScheduleListPanel.class);

    @FXML
    private ListView<ScheduleListCard> scheduleListView;

    public ScheduleListPanel(ObservableList<ReadOnlyEvent> eventList) {
        super(FXML);
        setConnections(eventList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyEvent> eventList) {
        ObservableList<ScheduleListCard> mappedList = EasyBind.map(eventList, (event) -> new ScheduleListCard(event));
        scheduleListView.setItems(mappedList);
        scheduleListView.setCellFactory(listView -> new ScheduleListViewCell());
        scheduleListView.setOrientation(Orientation.HORIZONTAL);
        logger.info("UI ------ Got eventList with " + eventList.size() + " events.");
    }

    /**
     * Upon receiving an AddressBookChangedEvent, update the event list accordingly.
     */
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        ObservableList<ReadOnlyEvent> eventList = abce.data.getSchedule(abce.data.getCurrentDate());
        ObservableList<ScheduleListCard> mappedList = EasyBind.map(eventList, (event) -> new ScheduleListCard(event));
        scheduleListView.setItems(mappedList);
    }

    /**
     * Scrolls to the {@code ScheduleListCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            scheduleListView.scrollTo(index);
            scheduleListView.getSelectionModel().clearAndSelect(index);
        });
    }
    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ScheduleListCard}.
     */
    class ScheduleListViewCell extends ListCell<ScheduleListCard> {

        @Override
        protected void updateItem(ScheduleListCard event, boolean empty) {
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
###### \resources\view\MainWindow.fxml
``` fxml
    <SplitPane id="schedule" dividerPositions="0.15" minHeight="70.0" prefHeight="70.0" prefWidth="200.0" HBox.hgrow="ALWAYS">
        <StackPane maxHeight="60.0" maxWidth="85.0" minHeight="60.0" minWidth="85.0" prefHeight="60.0" prefWidth="85.0">
            <Label fx:id="scheduleTitle" text="Schedule: " StackPane.alignment="CENTER_LEFT">
               <StackPane.margin>
                  <Insets />
               </StackPane.margin>
               <padding>
                  <Insets left="10.0" />
               </padding></Label>
        </StackPane>
        <StackPane fx:id="scheduleListPanelPlaceholder" styleClass="background" HBox.hgrow="ALWAYS">
            <padding>
               <Insets left="10.0" />
            </padding></StackPane>
         <VBox.margin>
            <Insets />
         </VBox.margin>
         <padding>
            <Insets bottom="5.0" top="5.0" />
         </padding>
    </SplitPane>
```
###### \resources\view\ScheduleListCard.fxml
``` fxml
<HBox fx:id="cardPane" minHeight="60.0" mouseTransparent="true" prefHeight="60.0" prefWidth="200.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <padding>
      <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
   </padding>
   <children>
      <VBox prefHeight="200.0" prefWidth="100.0">
         <children>
            <Label fx:id="title" text="title" />
            <Label fx:id="timing" text="timing" />
         </children>
      </VBox>
   </children>
</HBox>
```
###### \resources\view\ScheduleListPanel.fxml
``` fxml
<HBox HBox.hgrow="ALWAYS" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <ListView fx:id="scheduleListView" mouseTransparent="true" prefHeight="60.0" stylesheets="@BrightTheme.css" HBox.hgrow="ALWAYS" />
</HBox>
```
