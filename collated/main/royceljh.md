# royceljh
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
###### \java\seedu\address\logic\commands\AddEventCommand.java
``` java
/**
 * Adds an event to the address book.
 */
public class AddEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addE";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the address book. "
            + "Parameters: "
            + PREFIX_HEADER + "HEADER "
            + PREFIX_DESC + "DESC "
            + PREFIX_EVENT_DATE + "DATE \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_HEADER + "Birthday party "
            + PREFIX_DESC + "John Doe's house "
            + PREFIX_EVENT_DATE + "2017-10-15 ";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book";

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
###### \java\seedu\address\logic\commands\EditEventCommand.java
``` java
/**
 * Edits the details of an existing event in the address book.
 */
public class EditEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "editE";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the event identified "
            + "by the index number used in the last event listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_HEADER + "HEADER] "
            + "[" + PREFIX_DESC + "DESC] "
            + "[" + PREFIX_EVENT_DATE + "DATE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_HEADER + "birthday party "
            + PREFIX_EVENT_DATE + "01/12";

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited Event: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book.";

    private final Index index;
    private final EditEventDescriptor editEventDescriptor;

    /**
     * @param index of the event in the filtered event list to edit
     * @param editEventDescriptor details to edit the event with
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
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, editedEvent));
    }

    /**
     * Creates and returns a {@code Event} with the details of {@code eventToEdit}
     * edited with {@code editEventDescriptor}.
     */
    private static Event createEditedEvent(ReadOnlyEvent eventToEdit,
                                           EditEventDescriptor editEventDescriptor) {
        assert eventToEdit != null;

        Header updatedHeader = editEventDescriptor.getHeader().orElse(eventToEdit.getHeader());
        Desc updatedDesc = editEventDescriptor.getDesc().orElse(eventToEdit.getDesc());
        EventDate updatedEventDate = editEventDescriptor.getEventDate().orElse(eventToEdit.getEventDate());

        return new Event(updatedHeader, updatedDesc, updatedEventDate);
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
        private Header header;
        private Desc desc;
        private EventDate eventDate;

        public EditEventDescriptor() {}

        public EditEventDescriptor(EditEventDescriptor toCopy) {
            this.header = toCopy.header;
            this.desc = toCopy.desc;
            this.eventDate = toCopy.eventDate;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.header, this.desc, this.eventDate);
        }

        public void setHeader(Header header) {
            this.header = header;
        }

        public Optional<Header> getHeader() {
            return Optional.ofNullable(header);
        }

        public void setDesc(Desc desc) {
            this.desc = desc;
        }

        public Optional<Desc> getDesc() {
            return Optional.ofNullable(desc);
        }

        public void setEventDate(EventDate eventDate) {
            this.eventDate = eventDate;
        }

        public Optional<EventDate> getEventDate() {
            return Optional.ofNullable(eventDate);
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

            return getHeader().equals(e.getHeader())
                    && getDesc().equals(e.getDesc())
                    && getEventDate().equals(e.getEventDate());
        }
    }
}
```
###### \java\seedu\address\logic\Logic.java
``` java
    /** Returns an unmodifiable view of the filtered list of events */
    ObservableList<ReadOnlyEvent> getFilteredEventList();
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Override
    public ObservableList<ReadOnlyEvent> getFilteredEventList() {
        return model.getFilteredEventList();
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
                ArgumentTokenizer.tokenize(args, PREFIX_HEADER, PREFIX_DESC, PREFIX_EVENT_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_HEADER, PREFIX_DESC, PREFIX_EVENT_DATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        try {
            Header header = ParserUtil.parseHeader(argMultimap.getValue(PREFIX_HEADER)).get();
            Desc desc = ParserUtil.parseDesc(argMultimap.getValue(PREFIX_DESC)).get();
            EventDate eventDate = ParserUtil.parseEventDate(argMultimap.getValue(PREFIX_EVENT_DATE)).get();

            ReadOnlyEvent event = new Event(header, desc , eventDate);

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
###### \java\seedu\address\logic\parser\DeleteEventCommandParser.java
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
###### \java\seedu\address\logic\parser\EditEventCommandParser.java
``` java
/**
 * Parses input arguments and creates a new EditEventCommand object
 */
public class EditEventCommandParser implements Parser<EditEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditEventCommand
     * and returns an EditEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_HEADER, PREFIX_DESC, PREFIX_EVENT_DATE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE));
        }

        EditEventDescriptor editEventDescriptor = new EditEventDescriptor();
        try {
            ParserUtil.parseHeader(argMultimap.getValue(PREFIX_HEADER)).ifPresent(editEventDescriptor::setHeader);
            ParserUtil.parseDesc(argMultimap.getValue(PREFIX_DESC)).ifPresent(editEventDescriptor::setDesc);
            ParserUtil.parseEventDate(argMultimap.getValue(PREFIX_EVENT_DATE))
                    .ifPresent(editEventDescriptor::setEventDate);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editEventDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditEventCommand.MESSAGE_NOT_EDITED);
        }

        return new EditEventCommand(index, editEventDescriptor);
    }

}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> Header} into an {@code Optional<Header>} if {@code header} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Header> parseHeader(Optional<String> header) throws IllegalValueException {
        requireNonNull(header);
        return header.isPresent() ? Optional.of(new Header(header.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> desc} into an {@code Optional<Desc>} if {@code desc} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Desc> parseDesc(Optional<String> desc) throws IllegalValueException {
        requireNonNull(desc);
        return desc.isPresent() ? Optional.of(new Desc(desc.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> eventDate} into an {@code Optional<EventDate>} if {@code eventDate} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<EventDate> parseEventDate(Optional<String> eventDate) throws IllegalValueException {
        requireNonNull(eventDate);
        return eventDate.isPresent() ? Optional.of(new EventDate(eventDate.get())) : Optional.empty();
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void setEvents(List<? extends ReadOnlyEvent> events) throws DuplicateEventException {
        this.events.setEvents(events);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Adds an event to the address book.
     * @throws DuplicateEventException if an equivalent event already exists.
     */
    public void addEvent(ReadOnlyEvent e) throws DuplicateEventException {
        Event newEvent = new Event(e);
        events.add(newEvent);
    }

    /**
     * Replaces the given event {@code target} in the list with {@code editedReadOnlyEvent}.
     * @throws DuplicateEventException if updating the event's details causes the event to be equivalent to
     *      another existing event in the list.
     * @throws EventNotFoundException if {@code target} could not be found in the list.
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
```
###### \java\seedu\address\model\AddressBook.java
``` java
    @Override
    public ObservableList<ReadOnlyEvent> getEventList() {
        return events.asObservableList();
    }
```
###### \java\seedu\address\model\Model.java
``` java
    /** Adds the given event */
    void addEvent(ReadOnlyEvent event) throws DuplicateEventException;

    /** Deletes the given event. */
    void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException;

    /**
     * Replaces the given event {@code target} with {@code editedEvent}.
     *
     * @throws DuplicateEventException if updating the event's details causes the event to be equivalent to
     *      another existing event in the list.
     * @throws EventNotFoundException if {@code target} could not be found in the list.
     */
    void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent)
            throws DuplicateEventException, EventNotFoundException;

```
###### \java\seedu\address\model\Model.java
``` java
    /** Returns an unmodifiable view of the filtered event list */
    ObservableList<ReadOnlyEvent> getFilteredEventList();

```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<ReadOnlyEvent> predicate);
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException {
        addressBook.removeEvent(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addEvent(ReadOnlyEvent event) throws DuplicateEventException {
        addressBook.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        indicateAddressBookChanged();
    }

    @Override
    public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent)
            throws DuplicateEventException, EventNotFoundException {
        requireAllNonNull(target, editedEvent);

        addressBook.updateEvent(target, editedEvent);
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
###### \java\seedu\address\model\person\event\Desc.java
``` java
/**
 * Represents a Event's header in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDesc(String)}
 */
public class Desc {

    public static final String MESSAGE_DESC_CONSTRAINTS =
            "Event description can take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DESC_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given description.
     *
     * @throws IllegalValueException if given desc string is invalid.
     */
    public Desc(String desc) throws IllegalValueException {
        requireNonNull(desc);
        if (!isValidDesc(desc)) {
            throw new IllegalValueException(MESSAGE_DESC_CONSTRAINTS);
        }
        this.value = desc;
    }

    /**
     * Returns true if a given string is a valid event description.
     */
    public static boolean isValidDesc(String test) {
        return test.matches(DESC_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Desc // instanceof handles nulls
                && this.value.equals(((Desc) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\event\Event.java
``` java
/**
 * Represents a Event in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Event implements ReadOnlyEvent {

    private ObjectProperty<Header> header;
    private ObjectProperty<Desc> desc;
    private ObjectProperty<EventDate> eventDate;

    /**
     * Every field must be present and not null.
     */
    public Event(Header header, Desc desc, EventDate eventDate) {
        requireAllNonNull(header, desc, eventDate);
        this.header = new SimpleObjectProperty<>(header);
        this.desc = new SimpleObjectProperty<>(desc);
        this.eventDate = new SimpleObjectProperty<>(eventDate);
    }

    /**
     * Creates a copy of the given ReadOnlyEvent.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getHeader(), source.getDesc(), source.getEventDate());
    }

    public void setHeader(Header header) {
        this.header.set(requireNonNull(header));
    }

    @Override
    public ObjectProperty<Header> headerProperty() {
        return header;
    }

    @Override
    public Header getHeader() {
        return header.get();
    }

    public void setDesc(Desc desc) {
        this.desc.set(requireNonNull(desc));
    }

    @Override
    public ObjectProperty<Desc> descProperty() {
        return desc;
    }

    @Override
    public Desc getDesc() {
        return desc.get();
    }

    public void setEventDate(EventDate eventDate) {
        this.eventDate.set(requireNonNull(eventDate));
    }

    @Override
    public ObjectProperty<EventDate> eventDateProperty() {
        return eventDate;
    }

    @Override
    public EventDate getEventDate() {
        return eventDate.get();
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
        return Objects.hash(header, desc, eventDate);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
```
###### \java\seedu\address\model\person\event\EventDate.java
``` java
/**
 * Represents a Event's header in the address book.
 * Guarantees: immutable; is valid as declared in {@link #EventDate(String)}
 */
public class EventDate {

    public static final String MESSAGE_EVENT_DATE_CONSTRAINTS =
            "Event must have a valid date input\n"
                    + "Format: year-month-day";

    public final String value;
    public final LocalDate eventLocalDate;
    private Period period;
    private String countDown;

    /**
     * Validates given eventDate.
     *
     * @throws IllegalValueException if given eventDate string is invalid.
     */
    public EventDate(String eventDate) throws IllegalValueException {
        requireNonNull(eventDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd")
                .withResolverStyle(ResolverStyle.STRICT);
        try {
            eventLocalDate = LocalDate.parse(eventDate, formatter);
        } catch (DateTimeParseException ex) {
            throw new IllegalValueException(MESSAGE_EVENT_DATE_CONSTRAINTS, ex);
        }
        getCountDown();
        this.value = eventDate + "\n" + countDown;
    }

    private void getCountDown() {
        ZoneId sgt = ZoneId.of("GMT+8");
        LocalDate currentDate = LocalDate.now(sgt);
        this.period = currentDate.until(eventLocalDate);
        int years = period.getYears();
        int months = period.getMonths();
        int days = period.getDays();
        if (period.isNegative()) {
            this.countDown = "Event is overdue.";
        } else if (period.isZero()) {
            this.countDown = "Event is today!";
        } else {
            this.countDown = "Event in: " + years + "years " + months + "months " + days + "days";
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventDate // instanceof handles nulls
                && this.value.equals(((EventDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\event\Header.java
``` java
/**
 * Represents a Event's header in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidHeader(String)}
 */
public class Header {

    public static final String MESSAGE_HEADER_CONSTRAINTS =
            "Event header can take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String HEADER_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given header.
     *
     * @throws IllegalValueException if given header string is invalid.
     */
    public Header(String header) throws IllegalValueException {
        requireNonNull(header);
        if (!isValidHeader(header)) {
            throw new IllegalValueException(MESSAGE_HEADER_CONSTRAINTS);
        }
        this.value = header;
    }

    /**
     * Returns true if a given string is a valid event header.
     */
    public static boolean isValidHeader(String test) {
        return test.matches(HEADER_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Header // instanceof handles nulls
                && this.value.equals(((Header) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\event\HeaderContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyEvent}'s {@code Header} matches any of the keywords given.
 */
public class HeaderContainsKeywordsPredicate implements Predicate<ReadOnlyEvent> {
    private final List<String> keywords;

    public HeaderContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyEvent event) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(event.getHeader().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HeaderContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((HeaderContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\event\ReadOnlyEvent.java
``` java
/**
 * A read-only immutable interface for a Event in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyEvent {

    ObjectProperty<Header> headerProperty();
    Header getHeader();
    ObjectProperty<Desc> descProperty();
    Desc getDesc();
    ObjectProperty<EventDate> eventDateProperty();
    EventDate getEventDate();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyEvent other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getHeader().equals(this.getHeader())  // state checks here onwards
                && other.getDesc().equals(this.getDesc())
                && other.getEventDate().equals(this.getEventDate()));
    }

    /**
     * Formats the event as text, showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDesc())
                .append(" Header: ")
                .append(getHeader())
                .append(" Desc: ")
                .append(getDesc())
                .append(" Date: ")
                .append(getEventDate());
        return builder.toString();
    }

}
```
###### \java\seedu\address\model\person\event\UniqueEventList.java
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
        Collections.sort(internalList, new EventDateComparator());
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
        Collections.sort(internalList, new EventDateComparator());
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
###### \java\seedu\address\model\person\exceptions\DuplicateEventException.java
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
###### \java\seedu\address\model\person\exceptions\EventNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified event.
 */
public class EventNotFoundException extends Exception {}
```
###### \java\seedu\address\model\ReadOnlyAddressBook.java
``` java
    /**
     * Returns an unmodifiable view of the events list.
     * This list will not contain any duplicate events.
     */
    ObservableList<ReadOnlyEvent> getEventList();

}
```
###### \java\seedu\address\model\util\EventDateComparator.java
``` java
/**
 * EventDateComparator compares events based on the LocalDate eventDate.
 * It sorts events starting from the dates that are closest to the currentDate.
 * Expired eventDate will be sorted last.
 */
public class EventDateComparator implements Comparator<Event> {

    @Override
    public int compare(Event e1, Event e2) {
        ZoneId sgt = ZoneId.of("GMT+8");
        LocalDate currentDate = LocalDate.now(sgt);
        if (e1.getEventDate().eventLocalDate.isBefore(currentDate)
                || e2.getEventDate().eventLocalDate.isBefore(currentDate)) {
            return e2.getEventDate().eventLocalDate.compareTo(e1.getEventDate().eventLocalDate);
        } else {
            return e1.getEventDate().eventLocalDate.compareTo(e2.getEventDate().eventLocalDate);
        }
    }
}
```
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java
    public static Event[] getSampleEvents() {
        try {
            return new Event[] {
                new Event(new Header("Birthday party"), new Desc("location"), new EventDate("2017-06-12")),
                new Event(new Header("Meeting"), new Desc("location"), new EventDate("2017-10-23"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }
```
###### \java\seedu\address\ui\EventCard.java
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
     */

    public final ReadOnlyEvent event;

    @FXML
    private HBox eventPane;
    @FXML
    private Label header;
    @FXML
    private Label id;
    @FXML
    private Label desc;
    @FXML
    private Label eventDate;

    public EventCard(ReadOnlyEvent event, int displayedIndex) {
        super(FXML);
        this.event = event;
        id.setText(displayedIndex + ". ");
        bindListeners(event);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Event} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyEvent event) {
        header.textProperty().bind(Bindings.convert(event.headerProperty()));
        desc.textProperty().bind(Bindings.convert(event.descProperty()));
        eventDate.textProperty().bind(Bindings.convert(event.eventDateProperty()));
        buildEventBackground();
    }

    /**
     * Change the background color of an event.
     */
    private void buildEventBackground() {
        ZoneId sgt = ZoneId.of("GMT+8");
        LocalDate currentDate = LocalDate.now(sgt);
        Period period = currentDate.until(event.getEventDate().eventLocalDate);
        String colorDate;
        if (period.getDays() < 0) {
            colorDate = "#CE5A57;"; // Red
        } else if (period.getDays() < 3 && period.getMonths() == 0 && period.getYears() == 0) {
            colorDate = "#E1B16A;"; // Orange
        } else {
            colorDate = "#78A5A3;"; // Green
        }
        eventPane.setStyle("-fx-background-color: " + colorDate + "-fx-border-width: 2;" + "-fx-border-color: black;");
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
###### \java\seedu\address\ui\EventListPanel.java
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
###### \resources\view\EventListCard.fxml
``` fxml
<HBox id="eventPane" fx:id="eventPane" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
            <padding>
                <Insets bottom="5" left="15" right="5" top="5" />
            </padding>
            <HBox alignment="CENTER_LEFT" spacing="5">
                <Label fx:id="id" styleClass="cell_big_label">
                    <minWidth>
                        <!-- Ensures that the label text is never truncated -->
                        <Region fx:constant="USE_PREF_SIZE" />
                    </minWidth>
                </Label>
                <Label fx:id="header" styleClass="cell_big_label" text="\$header" />
            </HBox>
            <Label fx:id="desc" styleClass="cell_small_label" text="\$desc" />
            <Label fx:id="eventDate" styleClass="cell_small_label" text="\$eventDate" />
        </VBox>
      <rowConstraints>
         <RowConstraints />
      </rowConstraints>
    </GridPane>
</HBox>
```
###### \resources\view\EventListPanel.fxml
``` fxml
<VBox xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <ListView fx:id="eventListView" VBox.vgrow="ALWAYS" />
</VBox>
```
