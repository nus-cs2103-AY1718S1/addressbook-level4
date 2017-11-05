# junyango
###### \java\seedu\address\commons\events\ui\EventPanelSelectionChangedEvent.java
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
###### \java\seedu\address\commons\events\ui\SwitchToEventsListEvent.java
``` java
/**
 * Represents a change that invokes list switching
 */
public class SwitchToEventsListEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\event\DeleteEventCommand.java
``` java

/**
 * Deletes an event identified using it's last displayed index from the address book.
 */
public class DeleteEventCommand extends UndoableCommand {


    public static final String COMMAND_WORD = "deleteE";
    public static final String COMMAND_ALIAS = "dE";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the last event listing.\n"
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
###### \java\seedu\address\logic\commands\event\EditEventCommand.java
``` java

/**
 * Edits the details of an existing person in the address book.
 */
public class EditEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "editE";
    public static final String COMMAND_ALIAS = "eE";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the event identified "
            + "by the index number used in the last event listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_DATE_TIME + "DATE] "
            + "[" + PREFIX_ADDRESS + "VENUE]"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE_TIME + "12052015 08:30 "
            + PREFIX_ADDRESS + "JK street";

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited Event: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book.";

    private final Index index;
    private final EditEventDescriptor editEventDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editEventDescriptor details to edit the person with
     */
    public EditEventCommand(Index index, EditEventDescriptor editEventDescriptor) {
        requireNonNull(index);
        requireNonNull(editEventDescriptor);

        this.index = index;
        this.editEventDescriptor = new EditEventDescriptor(editEventDescriptor);
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
        } catch (DuplicateEventException dee) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        } catch (EventNotFoundException enfe) {
            throw new AssertionError("The target event cannot be missing");
        }
        model.updateFilteredEventsList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, editedEvent));
    }

    /**
     * Creates and returns a {@code Event} with the details of {@code personToEdit}
     * edited with {@code editEventDescriptor}.
     */
    private static Event createEditedEvent(ReadOnlyEvent eventToEdit,
                                             EditEventDescriptor editEventDescriptor) {
        assert eventToEdit != null;

        Name updatedName = editEventDescriptor.getName().orElse(eventToEdit.getName());
        DateTime updatedTime = editEventDescriptor.getTime().orElse(eventToEdit.getTime());
        Address updatedAddress = editEventDescriptor.getAddress().orElse(eventToEdit.getAddress());
        ArrayList<Reminder> reminders = new ArrayList<Reminder>();

        return new Event(updatedName, updatedTime, updatedAddress, reminders);
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
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditEventDescriptor {
        private Name name;
        private DateTime time;
        private Address address;

        public EditEventDescriptor() {}

        public EditEventDescriptor(EditEventDescriptor toCopy) {
            this.name = toCopy.name;
            this.time = toCopy.time;
            this.address = toCopy.address;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.time, this.address);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setTime(DateTime time) {
            this.time = time;
        }

        public Optional<DateTime> getTime() {
            return Optional.ofNullable(time);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
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

            return getName().equals(e.getName())
                    && getTime().equals(e.getTime())
                    && getAddress().equals(e.getAddress());
        }
    }
}

```
###### \java\seedu\address\logic\commands\event\ListEventCommand.java
``` java
/**
 * Lists all events in the address book to the user.
 */
public class ListEventCommand extends Command {

    public static final String COMMAND_WORD = "listE";
    public static final String COMMAND_ALIAS = "lE";

    public static final String MESSAGE_EVENT_SUCCESS = "Listed all events";

    public ListEventCommand() {
        eventsCenter.registerHandler(this);
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredEventsList(PREDICATE_SHOW_ALL_EVENTS);
        raise(new SwitchToEventsListEvent());
        return new CommandResult(MESSAGE_EVENT_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case AddEventCommand.COMMAND_WORD:
        case AddEventCommand.COMMAND_ALIAS:
            return new AddEventParser().parse(arguments);
        case EditEventCommand.COMMAND_WORD:
        case EditEventCommand.COMMAND_ALIAS:
            return new EditEventParser().parse(arguments);
        case DeleteEventCommand.COMMAND_WORD:
        case DeleteEventCommand.COMMAND_ALIAS:
            return new DeleteEventParser().parse(arguments);

        case ListEventCommand.COMMAND_WORD:
        case ListEventCommand.COMMAND_ALIAS:
            return new ListEventCommand();
```
###### \java\seedu\address\logic\parser\event\AddEventParser.java
``` java
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddEventParser implements Parser<AddEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {
        Set<Prefix> prefixes = PropertyManager.getAllPrefixes();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, prefixes);

        // TODO: Keep this checking for now. These pre-loaded properties are compulsory.
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_DATE_TIME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        try {
            Set<Property> propertyList = ParserUtil.parseProperties(argMultimap.getAllValues());
            ArrayList<Reminder> reminderList = new ArrayList<>();
            return new AddEventCommand(new Event(propertyList, reminderList));
        } catch (IllegalValueException | PropertyNotFoundException | DuplicatePropertyException e) {
            throw new ParseException(e.getMessage(), e);
        }
    }
}

```
###### \java\seedu\address\logic\parser\event\DeleteEventParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteEventCommand object
 */
public class DeleteEventParser implements Parser<DeleteEventCommand> {

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
###### \java\seedu\address\logic\parser\event\EditEventParser.java
``` java
/**
 * Parses input arguments and creates a new EditEventCommand object
 */
public class EditEventParser implements Parser<EditEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditEventCommand
     * and returns an EditEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE_TIME, PREFIX_ADDRESS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE));
        }

        EditEventDescriptor editEventDescriptor = new EditEventDescriptor();
        try {
            ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).ifPresent(editEventDescriptor::setName);
            ParserUtil.parseTime(argMultimap.getValue(PREFIX_DATE_TIME)).ifPresent(editEventDescriptor::setTime);
            ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).ifPresent(editEventDescriptor::setAddress);
        } catch (IllegalValueException | PropertyNotFoundException ive) {
            throw new ParseException(ive.getMessage());
        }

        if (!editEventDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditEventCommand.MESSAGE_NOT_EDITED);
        }

        return new EditEventCommand(index, editEventDescriptor);
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Replaces all events in this list with those in the argument event list.
     */
    public void setEvents(List<? extends ReadOnlyEvent> events) throws DuplicateEventException {
        this.events.setEvents(events);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /*****************************************************
     * Event-level operations
     *****************************************************/

    /**
     * Adds an event to the address book.
     *
     * @throws DuplicateEventException if an equivalent event already exists.
     */
    public void addEvent(ReadOnlyEvent e) throws DuplicateEventException {
        Event newEvent = new Event(e);
        events.add(newEvent);
    }

    /**
     * Replaces the given event {@code target} in the list with {@code editedReadOnlyPerson}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedReadOnlyPerson}.
     *
     * @throws DuplicateEventException if updating the event's details causes the event to be equivalent to
     *      another existing event in the list.
     * @throws EventNotFoundException if {@code target} could not be found in the list.
     *
     */
    public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedReadOnlyEvent)
            throws DuplicateEventException, EventNotFoundException {
        requireNonNull(editedReadOnlyEvent);

        Event editedEvent = new Event(editedReadOnlyEvent);

        events.setEvent(target, editedEvent);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws EventNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeEvent(ReadOnlyEvent key) throws EventNotFoundException {
        if (events.remove(key)) {
            return true;
        } else {
            throw new EventNotFoundException();
        }
    }

    /**
     * Sorts the events according to their date time.
     */
    public void sortEventList() {
        events.sortEvents();
    }
```
###### \java\seedu\address\model\event\exceptions\DuplicateEventException.java
``` java
/**
 * Signals that the operation will result in duplicate Person objects.
 */
public class DuplicateEventException extends DuplicateDataException {
    public DuplicateEventException() {
        super("Operation would result in duplicate events");
    }
}
```
###### \java\seedu\address\model\event\exceptions\EventNotFoundException.java
``` java

/**
 * Signals that the operation is unable to find the specified person.
 */
public class EventNotFoundException extends Exception {}
```
###### \java\seedu\address\model\event\ReadOnlyEvent.java
``` java
/**
 * A read-only immutable interface for an Event in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */

public interface ReadOnlyEvent {

    ObjectProperty<Name> nameProperty();
    Name getName();
    ObjectProperty<DateTime> timeProperty();
    DateTime getTime();
    ObjectProperty<Address> addressProperty();
    Address getAddress();
    ObjectProperty<UniquePropertyMap> properties();
    Set<Property> getProperties();
    ObjectProperty<UniqueReminderList> reminderProperty();
    ArrayList<Reminder> getReminders();
    void addReminder(ReadOnlyReminder r) throws DuplicateReminderException;

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyEvent other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getTime().equals(this.getTime())
                && other.getAddress().equals(this.getAddress()));
    }


    /**
     * Formats the event as text, showing all event details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Event: ")
                .append(getName())
                .append(" | ")
                .append(" Date/Time: ")
                .append(getTime())
                .append(" | ")
                .append(" Address: ")
                .append(getAddress());
        return builder.toString();
    }

}

```
###### \java\seedu\address\model\event\UniqueEventList.java
``` java
/**
 * A list of events that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Event#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
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
     * @throws DuplicateEventException if the person to add is a duplicate of an existing event in the list.
     */
    public void add(ReadOnlyEvent toAdd) throws DuplicateEventException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateEventException();
        }
        internalList.add(new Event(toAdd));
    }

    /**
     * Sorts the events based on time
     *
     */
    public void sortEvents() {
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("ddMMyyyy HH:mm");
        internalList.sort((e1, e2) -> (LocalDateTime.parse(e1.getTime().toString(), sdf)
                .compareTo(LocalDateTime.parse(e2.getTime().toString(), sdf))));
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedEvent}.
     *
     * @throws DuplicateEventException if the replacement is equivalent to another existing person in the list.
     * @throws PersonNotFoundException  if {@code target} could not be found in the list.
     */
    public void setEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent) throws DuplicateEventException,
            EventNotFoundException {

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
     * Removes the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public boolean remove(ReadOnlyEvent toRemove) throws EventNotFoundException {
        requireNonNull(toRemove);
        final boolean eventFoundAndDeleted = internalList.remove(toRemove);
        if (!eventFoundAndDeleted) {
            throw new EventNotFoundException();
        }
        return eventFoundAndDeleted;
    }

```
###### \java\seedu\address\model\event\UniqueEventList.java
``` java
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
    //=========== Model support for activity component =============================================================

    /** Adds an event */
    void addEvent(ReadOnlyEvent event) throws DuplicateEventException;

    /** Updates the given event */
    void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent)
            throws DuplicateEventException, EventNotFoundException;
    /** Deletes the given event */
    void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException;


    //=========== Model support for reminder component =============================================================

    /** Adds a reminder */
    void addReminder(ReadOnlyReminder reminder) throws DuplicateReminderException;

    /** Deletes the given event */
    void deleteReminder(ReadOnlyReminder target) throws ReminderNotFoundException;
```
###### \java\seedu\address\model\ModelManager.java
``` java
    //=========== Model support for activity component =============================================================

    @Override
    public synchronized void addEvent(ReadOnlyEvent event) throws DuplicateEventException {
        requireNonNull(event);
        addressBook.addEvent(event);
        addressBook.sortEventList();
        updateFilteredEventsList(PREDICATE_SHOW_ALL_EVENTS);
        indicateAddressBookChanged();
    }

    @Override
    public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent)
            throws DuplicateEventException, EventNotFoundException {
        requireAllNonNull(target, editedEvent);
        addressBook.updateEvent(target, editedEvent);
        addressBook.sortEventList();
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void deleteEvent(ReadOnlyEvent event) throws EventNotFoundException {
        addressBook.removeEvent(event);
        indicateAddressBookChanged();
    }

    //=========== Model support for activity component =============================================================
    @Override
    public synchronized void addReminder(ReadOnlyReminder reminder) throws DuplicateReminderException {
        requireNonNull(reminder);
        addressBook.addReminder(reminder);
        updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
        indicateAddressBookChanged();
    }
    @Override
    public synchronized void deleteReminder(ReadOnlyReminder reminder) throws ReminderNotFoundException {
        addressBook.removeReminder(reminder);
        indicateAddressBookChanged();
    }


```
###### \java\seedu\address\model\ModelManager.java
``` java
    //=========== Filtered Activity List Accessors =============================================================

    @Override
    public ObservableList<ReadOnlyEvent> getFilteredEventList() {
        return FXCollections.unmodifiableObservableList(filteredEvents);
    }

    /**
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    @Override
    public void updateFilteredEventsList(Predicate<ReadOnlyEvent> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }


    //=========== Filtered Reminder List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyReminder} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyReminder> getFilteredReminderList() {
        return FXCollections.unmodifiableObservableList(filteredReminders);
    }

    /**
     * Updates the filter of the filtered reminders list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    @Override
    public void updateFilteredReminderList(Predicate<ReadOnlyReminder> predicate) {
        requireNonNull(predicate);
        filteredReminders.setPredicate(predicate);
    }
```
###### \java\seedu\address\model\property\DateTime.java
``` java
/**
 * Represents an event's date/time in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class DateTime extends Property {
    private static final String PROPERTY_SHORT_NAME = "dt";

    // The standard format for storing a date time in string format.
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("ddMMyyyy HH:mm");
    private static final String STANDARD_FORMAT = "^(0[1-9]|[12][0-9]|3[01])(0[1-9]|1[012])[0-9]{4}"
            + "(\\s((0[1-9]|1[0-9]|2[0-3]):([0-5][0-9]))?$)";

    public DateTime(String value) throws IllegalValueException, PropertyNotFoundException {
        super(PROPERTY_SHORT_NAME, prepareDateTimeValue(value));
    }

    public DateTime(Date value) throws IllegalValueException, PropertyNotFoundException {
        super(PROPERTY_SHORT_NAME, formatDateTime(value));
    }

```
###### \java\seedu\address\model\property\EventNameContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyEvent}'s {@code Name} matches any of the keywords given.
 */
public class EventNameContainsKeywordsPredicate implements Predicate<ReadOnlyEvent> {
    private final List<String> keywords;

    public EventNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyEvent event) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(event.getName().getValue(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventNameContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((EventNameContainsKeywordsPredicate) other).keywords)); // state check
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
    private String name;
    @XmlElement(required = true)
    private String time;
    @XmlElement(required = true)
    private String venue;
    @XmlElement
    private List<XmlAdaptedReminder> reminders = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedProperty> properties = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEvent() {}


    /**
     * Converts a given Event into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedEvent
     */
    public XmlAdaptedEvent(ReadOnlyEvent source) {
        name = source.getName().getValue();
        time = source.getTime().getValue();
        venue = source.getAddress().getValue();
        properties = new ArrayList<>();
        for (Property property: source.getProperties()) {
            properties.add(new XmlAdaptedProperty(property));
        }
        reminders = new ArrayList<>();
        for (Reminder reminder: source.getReminders()) {
            reminders.add(new XmlAdaptedReminder(reminder));
        }
    }

    /**
     * Converts this jaxb-friendly adapted event object into the model's Event object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event
     */
    public Event toModelType() throws IllegalValueException, PropertyNotFoundException, DuplicatePropertyException {
        final List<Property> eventProperties = new ArrayList<>();
        for (XmlAdaptedProperty property: properties) {
            eventProperties.add(property.toModelType());
        }
        final ArrayList<Reminder> eventReminders = new ArrayList<>();
        for (XmlAdaptedReminder reminder : reminders) {
            eventReminders.add(reminder.toModelType());
        }
        final Set<Property> properties = new HashSet<>(eventProperties);
        return new Event(properties, eventReminders);
    }
}
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java

    @Override
    public ObservableList<ReadOnlyEvent> getEventList() {
        final ObservableList<ReadOnlyEvent> events = this.events.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException | PropertyNotFoundException | DuplicatePropertyException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(events);
    }
    @Override
    public ObservableList<ReadOnlyReminder> getReminderList() {
        final ObservableList<ReadOnlyReminder> reminders = this.reminders.stream().map(p -> {
            try {

                return p.toModelType();
            } catch (IllegalValueException | PropertyNotFoundException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(reminders);
    }

```
###### \java\seedu\address\ui\event\EventListPanel.java
``` java
/**
 * Panel containing the list of events.
 */
public class EventListPanel extends UiPart<Region> {
    private static final String FXML = "event/EventListPanel.fxml";
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
###### \resources\view\event\EventListCard.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.VBox?>

<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
            <padding>
                <Insets top="5" right="5" bottom="5" left="15" />
            </padding>
            <HBox spacing="5" alignment="CENTER_LEFT">
                <Label fx:id="idEvent" styleClass="cell_big_label">
                    <minWidth>
                        <!-- Ensures that the label text is never truncated -->
                        <Region fx:constant="USE_PREF_SIZE" />
                    </minWidth>
                </Label>
                <Label fx:id="name" text="\$event name" styleClass="cell_big_label" />
            </HBox>
            <Label fx:id="dateTime" styleClass="cell_small_label" text="\$date and time" />
            <Label fx:id="venue" styleClass="cell_small_label" text="\$venue" />
        </VBox>
    </GridPane>
</HBox>
```
###### \resources\view\event\EventListPanel.fxml
``` fxml
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <ListView fx:id="eventListView" VBox.vgrow="ALWAYS" />
</VBox>
```
