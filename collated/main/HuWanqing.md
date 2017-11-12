# HuWanqing
###### \java\seedu\address\logic\commands\AbortedCommand.java
``` java
/**
 * A dummy command class used to abort add Event command
 */
public class AbortedCommand extends AddEventCommand {
    public static final String MESSAGE_SUCCESS = "This command is aborted";

    private Event toAdd;

    public AbortedCommand (Event event) {
        super(event);
    }

    @Override
    protected CommandResult executeUndoableCommand() {
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    protected void undo() {
    }

    @Override
    protected void redo() {
    }
}
```
###### \java\seedu\address\logic\commands\AddEventCommand.java
``` java
/**
 * Adds an event to the address book.
 */
public class AddEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addE";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a event to the event list. "
            + "Parameters: "
            + PREFIX_EVENT_NAME + "NAME "
            + PREFIX_EVENT_DESCRIPTION + "DESCRIPTION "
            + PREFIX_EVENT_TIME + "TIME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_NAME + "Project Meeting "
            + PREFIX_EVENT_DESCRIPTION + "Discuss how to conduct software demo "
            + PREFIX_EVENT_TIME + "30/10/2017 ";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the event list";

    private final Event toAdd;

    public AddEventCommand (ReadOnlyEvent event) {
        toAdd = new Event (event);
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
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

    @Override
    protected void undo() {
        requireNonNull(model);
        try {
            model.deleteEvent(toAdd);
        } catch (EventNotFoundException enfe) {
            throw new AssertionError(MESSAGE_UNDO_ASSERTION_ERROR);
        } catch (DeleteOnCascadeException doce) {
            throw new AssertionError(MESSAGE_UNDO_ASSERTION_ERROR);
        }
    }

    @Override
    protected void redo() {
        requireNonNull(model);
        try {
            model.addEvent(toAdd);
        } catch (DuplicateEventException dee) {
            throw new AssertionError(MESSAGE_REDO_ASSERTION_ERROR);
        }
    }
}
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredPersonList(predicate);
        List<String> tags = predicate.getSelectedTags();
        if (tags != null) {
            if (tags.size() != 0) {
                String selectedTags = " Selected tags:";
                for (String eachTag: tags) {
                    selectedTags += " " + eachTag;
                }
                return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size())
                        + selectedTags);
            }
        }
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }
```
###### \java\seedu\address\logic\commands\JoinCommand.java
``` java
/**
 * adds a participant to an event, and adds this event to the participant
 */
public class JoinCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "join";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Connect a person to an event "
            + "Parameters: "
            + PREFIX_PERSON + "Person index"
            + PREFIX_EVENT + "Event index (index must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PERSON + "1 "
            + PREFIX_EVENT + "2";
    public static final String MESSAGE_JOIN_SUCCESS = "Person \"%1$s\" now participates Event \"%2$s\"";
    public static final String MESSAGE_DUPLICATE_EVENT = "This person has already participated the event ";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private Index personIdx;
    private Index eventIdx;
    private Person personToJoin;
    private Event eventToJoin;

    /**
     * creates a new commands to add the specified {@code ReadOnlyPerson}
     */
    public JoinCommand(Index personIdx, Index eventIdx) {
        requireAllNonNull(personIdx, eventIdx);

        this.personIdx = personIdx;
        this.eventIdx = eventIdx;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        List<ReadOnlyPerson> lastShownPersonList = model.getFilteredPersonList();
        List<ReadOnlyEvent> lastShownEventList = model.getFilteredEventList();
        if (personIdx.getZeroBased() >= lastShownPersonList.size()) {
            throw new  CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        if (eventIdx.getZeroBased() >= lastShownEventList.size()) {
            throw new  CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        personToJoin = (Person) lastShownPersonList.get(personIdx.getZeroBased());
        eventToJoin = (Event) lastShownEventList.get(eventIdx.getZeroBased());
        try {
            model.joinEvent(personToJoin, eventToJoin);
        } catch (PersonHaveParticipateException phpe) {
            return new CommandResult(MESSAGE_DUPLICATE_PERSON);
        } catch (HaveParticipateEventException npee) {
            return new CommandResult(MESSAGE_DUPLICATE_EVENT);
        }
        return new CommandResult(String.format(MESSAGE_JOIN_SUCCESS, personToJoin.getName(),
                eventToJoin.getEventName()));
    }

    @Override
    protected void undo() {
        try {
            model.quitEvent(personToJoin, eventToJoin);
        } catch (PersonNotParticipateException pnpe) {
            throw new AssertionError(MESSAGE_UNDO_ASSERTION_ERROR);
        } catch (NotParticipateEventException npee) {
            throw new AssertionError(MESSAGE_UNDO_ASSERTION_ERROR);
        }
    }

    @Override
    protected void redo() {
        try {
            model.joinEvent(personToJoin, eventToJoin);
        } catch (PersonHaveParticipateException phpe) {
            throw new AssertionError(MESSAGE_REDO_ASSERTION_ERROR);
        } catch (HaveParticipateEventException hpee) {
            throw new AssertionError(MESSAGE_REDO_ASSERTION_ERROR);
        }
    }
}
```
###### \java\seedu\address\logic\LogicManager.java
``` java
            if (!(command instanceof AbortedCommand)) {
                undoRedoStack.push(command);
            }
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
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_NAME, PREFIX_EVENT_DESCRIPTION,
                        PREFIX_EVENT_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_NAME, PREFIX_EVENT_DESCRIPTION, PREFIX_EVENT_TIME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddEventCommand.MESSAGE_USAGE));
        }

        try {
            EventName name = ParserUtil.parseEventName(argMultimap.getValue(PREFIX_EVENT_NAME)).get();
            EventDescription description = ParserUtil.parseEventDescription(
                    argMultimap.getValue(PREFIX_EVENT_DESCRIPTION)).get();
            EventTime time = ParserUtil.parseEventTime(argMultimap.getValue(PREFIX_EVENT_TIME)).get();

            Event event = new Event(name, description, time);

            if (time.getDays() < 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alert");
                alert.setHeaderText("This event date is outdated");
                alert.setContentText("Do you still want to add this event to Planno?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    return new AddEventCommand(event);
                } else {
                    return new AbortedCommand(event);
                }
            }

            return new AddEventCommand(event);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

    }

}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case JoinCommand.COMMAND_WORD:
            return new JoinCommandParser().parse(arguments);

```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void addParticipation(Person person, Event event) throws HaveParticipateEventException {
        persons.addParticipateEvent(person, event);
    }
```
###### \java\seedu\address\model\event\Event.java
``` java
/**
 * Represents a Event in the address book.
 */
public class Event implements ReadOnlyEvent {
    private ObjectProperty<EventName> name;
    private ObjectProperty<EventDescription> desc;
    private ObjectProperty<EventTime> time;
    private ObjectProperty<ParticipantList> participants;
    private ObjectProperty<String> days;

    /**
     * Event name and time must be present and not null.
     */
    public Event (EventName name, EventDescription desc, EventTime time, Set<Person> participants) {
        requireAllNonNull(name, time);
        this.days = new SimpleObjectProperty<>(time.getDaysLeft());
        this.name = new SimpleObjectProperty<>(name);
        this.desc = new SimpleObjectProperty<>(desc);
        this.time = new SimpleObjectProperty<>(time);
        this.participants = new SimpleObjectProperty<>(new ParticipantList(participants));
    }

    /**
     * Event name and time must be present and not null.
     */
    public Event (EventName name, EventDescription desc, EventTime time) {
        requireAllNonNull(name, time);
        this.days = new SimpleObjectProperty<>(time.getDaysLeft());
        this.name = new SimpleObjectProperty<>(name);
        this.desc = new SimpleObjectProperty<>(desc);
        this.time = new SimpleObjectProperty<>(time);
        this.participants = new SimpleObjectProperty<>(new ParticipantList());
    }

    public Event (ReadOnlyEvent source) {
        this(source.getEventName(), source.getDescription(), source.getEventTime(), source.getParticipants());
    }

    public void setEventName(EventName name) {
        this.name.set(requireNonNull(name));
    }

    @Override
    public ObjectProperty<EventName> eventNameProperty() {
        return name;
    }

    @Override
    public EventName getEventName() {
        return name.get();
    }

    public void setEventDescription(EventDescription desc) {
        this.desc.set(desc);
    }

    @Override
    public ObjectProperty<EventDescription> descriptionProperty() {
        return desc;
    }

    @Override
    public EventDescription getDescription() {
        return desc.get();
    }

    public void setETime(EventTime time) {
        this.time.set(requireNonNull(time));
    }

    @Override
    public ObjectProperty<EventTime> timeProperty() {
        return time;
    }

    @Override
    public EventTime getEventTime() {
        return time.get();
    }

    @Override
    public ObjectProperty<ParticipantList> participantProperty() {
        return participants;
    }

    @Override
    public Set<Person> getParticipants() {
        return Collections.unmodifiableSet(participants.get().toSet());
    }

    public ParticipantList getParticipantList() {
        return participants.get();
    }

    @Override
    public ObjectProperty<String> daysProperty() {
        return days;
    }
    /**
     * Replaces this event's participants with the persons in the argument set.
     */
    public void setParticipants(Set<Person> replacement) {
        this.participants.set(new ParticipantList(replacement));
    }

    public void removeParticipant(ReadOnlyPerson person) throws PersonNotFoundException {
        this.participants.get().remove(person);
    }

    public void addParticipant(Person person) throws DuplicatePersonException {
        this.participants.get().add(person);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyEvent // instanceof handles nulls
                && this.eventIsSameStateAs((ReadOnlyEvent) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, desc, time, participants);
    }

    @Override
    public String toString() {
        return getEventAsText();
    }
}
```
###### \java\seedu\address\model\event\EventDescription.java
``` java
/**
 * Represents a Event's description in the address book.
 */

public class EventDescription {


    public static final String MESSAGE_EVENT_DESCRIPTION_CONSTRAINTS =
            "Event description should not be blank";

    /**
     * The first character of the event name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[^\\s].*";

    public final String eventDesc;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public EventDescription(String desc) throws IllegalValueException {
        requireNonNull(desc);
        String trimmedDesc = desc.trim();
        if (!isValidDesc(trimmedDesc)) {
            throw new IllegalValueException(MESSAGE_EVENT_DESCRIPTION_CONSTRAINTS);
        }
        this.eventDesc = trimmedDesc;
    }

    /**
     * Returns true if a given string is a valid event description.
     */
    public static boolean isValidDesc(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return eventDesc;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventDescription // instanceof handles nulls
                && this.eventDesc.equals(((EventDescription) other).eventDesc)); // state check
    }

    @Override
    public int hashCode() {
        return eventDesc.hashCode();
    }

}
```
###### \java\seedu\address\model\event\EventName.java
``` java
/**
 * Represents a Event's name in the address book.
 */

public class EventName {


    public static final String MESSAGE_EVENT_NAME_CONSTRAINTS =
            "Event names should only contain alphanumeric characters and spaces, and it should not be blank";

    /**
     * The first character of the event name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullEventName;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public EventName(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_EVENT_NAME_CONSTRAINTS);
        }
        this.fullEventName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid event name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
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
###### \java\seedu\address\model\event\EventTime.java
``` java
    public String getDaysLeft() {
        long day = getDays();
        if (day < 0) {
            return Long.toString(-day) + "â†?";
        }
        return Long.toString(day) + "â†?";
    }

    public Long getDays() {
        long day = 0;
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String today = format.format(now);
        try {
            Date ddl = format.parse(eventTime);
            Date thisDay = format.parse(today);
            day = (ddl.getTime() - thisDay.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return day;
        }
        return day;
    }

    @Override
    public String toString() {
        return eventTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.event.EventTime // instanceof handles nulls
                && this.eventTime.equals(((seedu.address.model.event.EventTime) other).eventTime)); // state check
    }

    @Override
    public int hashCode() {
        return eventTime.hashCode();
    }
}
```
###### \java\seedu\address\model\event\exceptions\DuplicateEventException.java
``` java
/**
 * Signals that the operation will result in duplicate Event objects.
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
 * Signals that the operation is unable to find the specified event.
 */
public class EventNotFoundException extends Exception {
}

```
###### \java\seedu\address\model\event\exceptions\PersonHaveParticipateException.java
``` java
/**
 * Signals that a specific person has participated an specific event
 */
public class PersonHaveParticipateException extends Exception {
}
```
###### \java\seedu\address\model\event\exceptions\PersonNotParticipateException.java
``` java
/**
 * Signals that a specific person does not participate an specific event
 */
public class PersonNotParticipateException extends Exception {
}
```
###### \java\seedu\address\model\event\ParticipantList.java
``` java
/**
 * Represents a Event's participant list in the event list.
 */

public class ParticipantList implements Iterable<Person> {
    private ObservableList<Person> internalList = FXCollections.observableArrayList();

    /**
     * construct empty participant list
     */
    public ParticipantList() {}

    /**
     * Construct participant list using given participants
     * Enforces no nulls
     */
    public ParticipantList(Set<Person> participants) {
        requireNonNull(participants);
        internalList.addAll(participants);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all participants in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */

    public Set<Person> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Participants in this list with those in the argument Participant list.
     */
    public void setParticipants(Set<Person> participants) {
        requireAllNonNull(participants);
        internalList.setAll(participants);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every participant in the argument list exists in this object.
     */
    public void mergeFrom(ParticipantList from) {
        final Set<Person> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(participant -> !alreadyInside.contains(participant))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Participant as the given argument.
     */
    public boolean contains(ReadOnlyPerson toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Participant to the list.
     *
     * @throws DuplicatePersonException if the participant list has the same person
     */
    public void add(Person toAdd) throws DuplicatePersonException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Remove a Participant in the list.
     *
     * @throws PersonNotFoundException if the person to remove is not in the list.
     */
    public void remove(ReadOnlyPerson toRemove) throws PersonNotFoundException {
        requireNonNull(toRemove);
        if (!contains(toRemove)) {
            throw new PersonNotFoundException();
        }
        internalList.remove(toRemove);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Person> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Person> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof ParticipantList // instanceof handles nulls
                && this.internalList.equals(((ParticipantList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(ParticipantList other) {
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
###### \java\seedu\address\model\event\ReadOnlyEvent.java
``` java
/**
 * Represents an interface for event in the address book.
 */
public interface ReadOnlyEvent {
    ObjectProperty<EventName> eventNameProperty();
    EventName getEventName();
    ObjectProperty<EventDescription> descriptionProperty();
    EventDescription getDescription();
    ObjectProperty<EventTime> timeProperty();
    EventTime getEventTime();
    ObjectProperty<ParticipantList> participantProperty();
    Set<Person> getParticipants();
    ObjectProperty<String> daysProperty();

    /**
     * returns true if both event has the same state
     */
    default boolean eventIsSameStateAs(ReadOnlyEvent other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getEventName().equals(this.getEventName()) // state checks here onwards
                && other.getEventTime().equals(this.getEventTime()));
    }

    /**
     * Formats the event as text, showing all event details.
     */
    default String getEventAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getEventName())
                .append(" Description: ")
                .append(getDescription())
                .append(" Time: ")
                .append(getEventTime())
                .append(" Participants: ");
        for (ReadOnlyPerson person : getParticipants()) {
            builder.append(person.getName())
                    .append(" ");
        }
        return builder.toString();
    }
}
```
###### \java\seedu\address\model\event\UniqueEventList.java
``` java
/**
 * Represents a unique event list in the EventList
 */

public class UniqueEventList implements Iterable<Event> {

    private final ObservableList<Event> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyEvent> mappedList = EasyBind.map(internalList, (event) -> event);

    /**
     * Returns true if the list contains an equivalent event as the given argument.
     */
    public boolean contains(ReadOnlyEvent toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds an event to the list.
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
     * Adds an event to the specific position in list.
     * Only used to undo deletion
     */
    public void add(int position, ReadOnlyEvent toAdd) {
        requireNonNull(toAdd);
        internalList.add(position, new Event(toAdd));
    }

    /**
     * Sorts the event list.
     */
    public void sort() {
        Collections.sort(internalList, new Comparator<Event>() {
            public int compare (Event p1, Event p2) {
                return p1.getEventTime().orderForSort().compareTo(p2.getEventTime().orderForSort()); } });
    }

    /**
     * Replaces the event {@code target} in the list with {@code editedEvent}.
     *
     * @throws DuplicateEventException if the replacement is equivalent to another existing event in the list.
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

    /**
     * Removes the equivalent event from the list.
     *
     * @throws EventNotFoundException if no such event could be found in the list.
     */
    public boolean remove(ReadOnlyEvent toRemove) throws EventNotFoundException, DeleteOnCascadeException {
        requireNonNull(toRemove);
        if (!toRemove.getParticipants().isEmpty()) {
            throw new DeleteOnCascadeException();
        }

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

    /**
     * Remove a specific person from the participant list of an event
     */
    public void removeParticipant(ReadOnlyPerson participant, Event targetEvent)
            throws PersonNotParticipateException {

        try {
            targetEvent.removeParticipant(participant);
        } catch (PersonNotFoundException pnfe) {
            throw new PersonNotParticipateException();
        }

    }

    /**
     * add a specific person to the participant list of an event
     */
    public void addParticipant(Person participant, Event targetEvent)
            throws PersonHaveParticipateException {
        try {
            targetEvent.addParticipant(participant);
        } catch (DuplicatePersonException dpe) {
            throw new PersonHaveParticipateException();
        }
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
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void addEvent(ReadOnlyEvent event) throws DuplicateEventException {
        eventList.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        indicateEventListChanged();
    }

    @Override
    public synchronized void addEvent(int position, ReadOnlyEvent event) {
        eventList.addEvent(position, event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        indicateEventListChanged();
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void joinEvent(Person person, Event event)
            throws PersonHaveParticipateException, HaveParticipateEventException {
        eventList.addParticipant(person, event);
        indicateEventListChanged();

        addressBook.addParticipation(person, event);
        indicateAddressBookChanged();
    }
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

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && filteredPersons.equals(other.filteredPersons)
                && filteredEvents.equals(other.filteredEvents);
    }

}
```
###### \java\seedu\address\model\person\exceptions\HaveParticipateEventException.java
``` java
/**
 * Signals that a specific person has participated a specific event.
 */
public class HaveParticipateEventException extends Exception {
}
```
###### \java\seedu\address\model\person\exceptions\NotParticipateEventException.java
``` java
/**
 * Signals that a specific person does not participate a specific event.
 */
public class NotParticipateEventException extends Exception {
}
```
###### \java\seedu\address\model\person\exceptions\PersonNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified person.
 */
public class PersonNotFoundException extends Exception {}
```
###### \java\seedu\address\model\person\NameContainsKeywordsPredicate.java
``` java
    @Override
    public boolean test(ReadOnlyPerson person) {
        Boolean isSelected = keywords.stream().anyMatch(keyword ->
                StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
        if (isSelected == true) {
            return isSelected;
        }
        for (String keyword: keywords) {
            if (keyword.length() >= 2 && keyword.substring(0, 2).equals("t/")) {
                String tagName = "[" + keyword.substring(2) + "]";
                for (Tag tag: person.getTags()) {
                    if (tag.toString().equals(tagName)) {
                        isSelected = true;
                    }
                }
            }
        }
        return isSelected;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

    public List<String> getSelectedTags() {
        List<String> tags = new ArrayList<String>();
        if (keywords.size() == 0) {
            return null;
        } else {
            for (String keyword : keywords) {
                if (keyword.length() >= 2 && keyword.substring(0, 2).equals("t/")) {
                    String tagName = "[" + keyword.substring(2) + "]";
                    tags.add(tagName);
                }
            }
        }
        return tags;
    }
}
```
###### \java\seedu\address\model\person\ParticipationList.java
``` java
/**
 * Represents all events which a person participates.
 */
public class ParticipationList implements Iterable<Event> {

    private ObservableList<Event> internalList = FXCollections.observableArrayList();

    /**
     * construct empty participation list
     */
    public ParticipationList() {}

    /**
     * Construct participation list using given participation
     * Enforces no nulls
     */
    public ParticipationList(Set<Event> participation) {
        requireNonNull(participation);
        internalList.addAll(participation);

        assert CollectionUtil.elementsAreUnique(internalList);
    }
    /**
     * Returns all participation in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Event> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Returns true if the participation list contains a specific event given by argument.
     */
    public boolean contains(ReadOnlyEvent toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds an event to the participation list.
     *
     * @throws DuplicateEventException if this person has already participate this event
     */
    public void add(Event toAdd) throws DuplicateEventException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateEventException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Remove an participated event from the list.
     *
     * @throws EventNotFoundException if the does not participate this event.
     */
    public void remove(ReadOnlyEvent toRemove) throws EventNotFoundException {
        requireNonNull(toRemove);
        if (!contains(toRemove)) {
            throw new EventNotFoundException();
        }
        internalList.remove(toRemove);

        assert CollectionUtil.elementsAreUnique(internalList);
    }
```
