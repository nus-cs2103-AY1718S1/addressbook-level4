# Adoby7
###### \java\seedu\address\logic\commands\AddCommand.java
``` java
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            newTags = model.extractNewTag(toAdd);
            model.addPerson(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

    }
```
###### \java\seedu\address\logic\commands\AddCommand.java
``` java
    @Override
    protected void undo() {
        requireNonNull(model);
        try {
            model.deletePerson(toAdd);
            model.removeTags(newTags);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError(MESSAGE_UNDO_ASSERTION_ERROR);
        } catch (DeleteOnCascadeException doce) {
            throw new AssertionError(MESSAGE_UNDO_ASSERTION_ERROR);
        }
    }

    @Override
    protected void redo() {
        requireNonNull(model);
        try {
            model.addPerson(toAdd);
        } catch (DuplicatePersonException e) {
            throw new AssertionError(MESSAGE_REDO_ASSERTION_ERROR);
        }
    }
}
```
###### \java\seedu\address\logic\commands\ClearCommand.java
``` java
/**
 * Clears the address book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book and Event list have been cleared!";

    private ReadOnlyAddressBook previousAddressBook;
    private ReadOnlyEventList previousEventList;

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        this.previousAddressBook = new AddressBook(model.getAddressBook());
        this.previousEventList = new EventList(model.getEventList());
        model.resetData(new AddressBook(), new EventList());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    protected void undo() {
        requireAllNonNull(model, previousAddressBook);
        model.resetData(previousAddressBook, previousEventList);
    }

    @Override
    protected void redo() {
        requireNonNull(model);
        model.resetData(new AddressBook(), new EventList());
    }
}
```
###### \java\seedu\address\logic\commands\DeleteCommand.java
``` java
    @Override
    protected void undo() {
        requireAllNonNull(model, personToDelete);
        model.addPerson(targetIndex.getZeroBased(), personToDelete);
    }

    @Override
    protected void redo() {
        requireAllNonNull(model, personToDelete);
        try {
            model.deletePerson(personToDelete);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError(MESSAGE_REDO_ASSERTION_ERROR);
        } catch (DeleteOnCascadeException doce) {
            throw new AssertionError(MESSAGE_REDO_ASSERTION_ERROR);
        }
    }

}
```
###### \java\seedu\address\logic\commands\DisjoinCommand.java
``` java
/**
 * Shows a person does not participate an event any more
 */
public class DisjoinCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "disjoin";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Disconnect a person to an event "
            + "Parameters: "
            + PREFIX_PERSON + "Person index"
            + PREFIX_EVENT + "Event index (index must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PERSON + "1 "
            + PREFIX_EVENT + "2";

    public static final String MESSAGE_DISJOIN_SUCCESS = "Person \"%1$s\" does not participate Event \"%2$s\"";
    public static final String MESSAGE_PERSON_NOT_PARTICIPATE = "This person does not participate this event";

    private final Index personIndex;
    private final Index eventIndex;
    private Person personToRemove;
    private Event eventToRemove;

    public DisjoinCommand(Index personIndex, Index eventIndex) {
        this.personIndex = personIndex;
        this.eventIndex = eventIndex;
    }


    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        List<ReadOnlyPerson> lastShownPersonList = model.getFilteredPersonList();
        List<ReadOnlyEvent> lastShownEventList = model.getFilteredEventList();

        if (personIndex.getZeroBased() >= lastShownPersonList.size()
                || eventIndex.getZeroBased() >= lastShownEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToRemove = new Person (lastShownPersonList.get(personIndex.getZeroBased()));
        eventToRemove = new Event (lastShownEventList.get(eventIndex.getZeroBased()));
        try {
            model.quitEvent(personToRemove, eventToRemove);
            return new CommandResult(String.format(MESSAGE_DISJOIN_SUCCESS, personToRemove.getName(),
                    eventToRemove.getEventName()));
        } catch (PersonNotParticipateException pnpe) {
            throw  new CommandException(MESSAGE_PERSON_NOT_PARTICIPATE);
        } catch (NotParticipateEventException npee) {
            throw  new CommandException(MESSAGE_PERSON_NOT_PARTICIPATE);
        }
    }

    @Override
    protected void undo() {
        try {
            model.joinEvent(personToRemove, eventToRemove);
        } catch (PersonHaveParticipateException pnpe) {
            throw new AssertionError(MESSAGE_UNDO_ASSERTION_ERROR);
        } catch (HaveParticipateEventException hpee) {
            throw new AssertionError(MESSAGE_UNDO_ASSERTION_ERROR);
        }
    }

    @Override
    protected void redo() {
        try {
            model.quitEvent(personToRemove, eventToRemove);
        } catch (PersonNotParticipateException pnpe) {
            throw new AssertionError(MESSAGE_REDO_ASSERTION_ERROR);
        } catch (NotParticipateEventException npee) {
            throw new AssertionError(MESSAGE_REDO_ASSERTION_ERROR);
        }

    }

    /**
     * Assign the target person and event
     * Only used for testing
     */
    public void assignValueForTest(Person person, Event event) {
        this.personToRemove = person;
        this.eventToRemove = event;
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof DisjoinCommand
                && this.eventIndex.equals(((DisjoinCommand) other).eventIndex)
                && this.personIndex.equals(((DisjoinCommand) other).personIndex));
    }
}
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
    @Override
    protected void undo() {
        try {
            model.updatePerson(editedPerson, personToEdit);
            model.removeTags(newTags);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError(MESSAGE_UNDO_ASSERTION_ERROR);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError(MESSAGE_UNDO_ASSERTION_ERROR);
        }
    }

    @Override
    protected void redo() {
        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError(MESSAGE_REDO_ASSERTION_ERROR);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError(MESSAGE_REDO_ASSERTION_ERROR);
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
            + "[" + PREFIX_EVENT_NAME + "NAME] "
            + "[" + PREFIX_EVENT_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_EVENT_TIME + "TIME] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EVENT_DESCRIPTION + "Discuss how to handle Q&A "
            + PREFIX_EVENT_TIME + "02/11/2017 ";

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited Event: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book.";

    private Index index;
    private EditEventDescriptor editEventDescriptor;
    private ReadOnlyEvent editedEvent;
    private ReadOnlyEvent eventToEdit;

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
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyEvent> lastShownList = model.getFilteredEventList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        eventToEdit = lastShownList.get(index.getZeroBased());
        editedEvent = createEditedEvent(eventToEdit, editEventDescriptor);

        try {
            model.updateEvent(eventToEdit, editedEvent);
        } catch (DuplicateEventException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        } catch (EventNotFoundException pnfe) {
            throw new AssertionError("The target event cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, editedEvent));
    }

    @Override
    protected void undo() {
        try {
            model.updateEvent(editedEvent, eventToEdit);
        } catch (DuplicateEventException dpe) {
            throw new AssertionError(MESSAGE_UNDO_ASSERTION_ERROR);
        } catch (EventNotFoundException pnfe) {
            throw new AssertionError(MESSAGE_UNDO_ASSERTION_ERROR);
        }
    }

    @Override
    protected void redo() {
        try {
            model.updateEvent(eventToEdit, editedEvent);
        } catch (DuplicateEventException dpe) {
            throw new AssertionError(MESSAGE_REDO_ASSERTION_ERROR);
        } catch (EventNotFoundException pnfe) {
            throw new AssertionError(MESSAGE_REDO_ASSERTION_ERROR);
        }
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
     * Creates and returns a {@code Event} with the details of {@code eventToEdit}
     * edited with {@code editEventDescriptor}.
     */
    private static Event createEditedEvent(ReadOnlyEvent eventToEdit,
                                             EditEventDescriptor editEventDescriptor) {
        assert eventToEdit != null;

        EventName updatedName = editEventDescriptor.getEventName().orElse(eventToEdit.getEventName());
        EventDescription updatedDescription = editEventDescriptor.getEventDescription().orElse(
                eventToEdit.getDescription());
        EventTime updatedTime = editEventDescriptor.getEventTime().orElse(eventToEdit.getEventTime());

        return new Event(updatedName, updatedDescription, updatedTime);
    }

    /**
     * Stores the details to edit the event with. Each non-empty field value will replace the
     * corresponding field value of the event.
     */
    public static class EditEventDescriptor {
        private EventName name;
        private EventDescription description;
        private EventTime time;

        public EditEventDescriptor() {}

        public EditEventDescriptor(EditEventDescriptor toCopy) {
            this.name = toCopy.name;
            this.description = toCopy.description;
            this.time = toCopy.time;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.description, this.time);
        }

        public void setEventName(EventName name) {
            this.name = name;
        }

        public Optional<EventName> getEventName() {
            return Optional.ofNullable(name);
        }

        public void setEventDescription(EventDescription description) {
            this.description = description;
        }

        public Optional<EventDescription> getEventDescription() {
            return Optional.ofNullable(description);
        }

        public void setEventTime(EventTime time) {
            this.time = time;
        }

        public Optional<EventTime> getEventTime() {
            return Optional.ofNullable(time);
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

            return getEventName().equals(e.getEventName())
                    && getEventDescription().equals(e.getEventDescription())
                    && getEventTime().equals(e.getEventTime());
        }
    }
}
```
###### \java\seedu\address\logic\commands\PortraitCommand.java
``` java
/**
 * A command that add an head portrait to a person
 */
public class PortraitCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "portrait";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Attach a head portrait to a person"
            + "Parameters: INDEX "
            + "[" + PREFIX_PORTRAIT + "PICTURE_FILE_NAME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PORTRAIT + "C:/user/images/sample.png";
    public static final String MESSAGE_ADD_PORTRAIT_SUCCESS = "Attached a head portrait to Person: %1$s";
    public static final String MESSAGE_DELETE_PORTRAIT_SUCCESS = "Removed head portrait from Person: %1$s";

    private Index index;
    private PortraitPath filePath;
    private ReadOnlyPerson personToEdit;
    private ReadOnlyPerson editedPerson;

    public PortraitCommand (Index index, PortraitPath filePath) {
        this.index = index;
        this.filePath = filePath;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(index.getZeroBased());

        editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
            personToEdit.getAddress(), personToEdit.getBirthday(), filePath, personToEdit.getTags(),
            personToEdit.getParticipation());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("The edited person cannot cause duplicate");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        String feedback = filePath.toString().isEmpty()
            ? MESSAGE_DELETE_PORTRAIT_SUCCESS : MESSAGE_ADD_PORTRAIT_SUCCESS;
        return new CommandResult(String.format(feedback, personToEdit.getName()));
    }

    @Override
    protected void undo() {
        try {
            model.updatePerson(editedPerson, personToEdit);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError(MESSAGE_UNDO_ASSERTION_ERROR);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError(MESSAGE_UNDO_ASSERTION_ERROR);
        }
    }

    @Override
    protected void redo() {
        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError(MESSAGE_REDO_ASSERTION_ERROR);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError(MESSAGE_REDO_ASSERTION_ERROR);
        }
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof PortraitCommand
                && this.index.equals(((PortraitCommand) other).index)
                && this.filePath.equals(((PortraitCommand) other).filePath));
    }
}
```
###### \java\seedu\address\logic\commands\UndoableCommand.java
``` java
/**
 * Represents a command which can be undone and redone.
 */
public abstract class UndoableCommand extends Command {

    protected abstract CommandResult executeUndoableCommand() throws CommandException;

    /**
     * Reverts the AddressBook to the state before this command
     * was executed and updates the filtered person list to
     * show all persons.
     */
    protected abstract void undo();

    /**
     * Executes the command and updates the filtered person
     * list to show all persons.
     */
    protected abstract void redo();

    @Override
    public final CommandResult execute() throws CommandException {
        return executeUndoableCommand();
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case EditEventCommand.COMMAND_WORD:
            return new EditEventCommandParser().parse(arguments);

```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case DisjoinCommand.COMMAND_WORD:
            return new DisjoinCommandParser().parse(arguments);

```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case PortraitCommand.COMMAND_WORD:
            return new PortraitCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

```
###### \java\seedu\address\logic\parser\ArgumentMultimap.java
``` java
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     * Refactoring this method into this class, because multiple classes use this method
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
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
    @Override
    public EditEventCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_NAME, PREFIX_EVENT_DESCRIPTION,
                        PREFIX_EVENT_TIME);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE));
        }

        EditEventDescriptor editEventDescriptor = new EditEventDescriptor();
        try {
            ParserUtil.parseEventName(argMultimap.getValue(PREFIX_EVENT_NAME)).ifPresent(
                    editEventDescriptor::setEventName);
            ParserUtil.parseEventDescription(argMultimap.getValue(PREFIX_EVENT_DESCRIPTION)).ifPresent(
                    editEventDescriptor::setEventDescription);
            ParserUtil.parseEventTime(argMultimap.getValue(PREFIX_EVENT_TIME)).ifPresent(
                    editEventDescriptor::setEventTime);
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
###### \java\seedu\address\logic\parser\PortraitCommandParser.java
``` java
/**
 * Parse the argument to be a portrait command
 */
public class PortraitCommandParser implements Parser<PortraitCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PortraitCommand
     * and returns an PortraitCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PortraitCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PORTRAIT);
        if (!arePrefixesPresent(argMultimap, PREFIX_PORTRAIT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PortraitCommand.MESSAGE_USAGE));
        }
        Index index;
        String filePath;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            filePath = argMultimap.getValue(PREFIX_PORTRAIT).get();
            return new PortraitCommand(index, new PortraitPath(filePath));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Remove tags that only in this deleted person
     * Used for undo Add & Edit Command
     */
    public void separateMasterTagListWith(Set<Tag> tagsToRemove) {
        for (Tag tag : tagsToRemove) {
            tags.remove(tag);
        }
    }


    /**
     * Get the tags in the new-added person, but not in the list
     */
    public Set<Tag> extractNewTags(ReadOnlyPerson person) {
        Set<Tag> personTags = person.getTags();
        Set<Tag> newTags = new HashSet<Tag>();

        for (Tag tag : personTags) {
            if (!tags.contains(tag)) {
                newTags.add(tag);
            }
        }

        return newTags;
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void removeParticipation(Person person, ReadOnlyEvent event) throws NotParticipateEventException {
        persons.removeParticipateEvent(person, event);
    }
```
###### \java\seedu\address\model\event\EventTime.java
``` java
    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public EventTime(String time) throws IllegalValueException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!isValidFormat(trimmedTime)) {
            throw new IllegalValueException(MESSAGE_EVENT_TIME_CONSTRAINTS);
        }
        this.eventTime = trimmedTime;
        splitTime(trimmedTime);

        if (!isValidTime()) {
            throw new IllegalValueException(MESSAGE_EVENT_TIME_CONSTRAINTS);
        }
    }

    /**
     * Splits the time into year, day, month
     */
    private void splitTime(String trimmedTime) {
        String[] splitTime = trimmedTime.split("/");
        day = splitTime[0];
        month = splitTime[1];
        year = splitTime[2];
    }

    public String orderForSort() {
        return year + month + day;
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public boolean isValidTime() {
        return isValidMonth(Integer.parseInt(month))
                && isValidDay(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
    }

    /**
     * Returns true if a given string is a valid time
     */
    public static boolean isValidEventTime(String eventTime) {
        String trimmedTime = eventTime.trim();
        if (!isValidFormat(trimmedTime)) {
            return false;
        }
        String[] splitTime = trimmedTime.split("/");
        String day = splitTime[0];
        String month = splitTime[1];
        String year = splitTime[2];

        return isValidMonth(Integer.parseInt(month))
                && isValidDay(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
    }

    /**
     * Returns true if a given day is valid
     */
    private static boolean isValidDay(int year, int month, int day) {
        int[] daysInMonth = new int[] {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (month == 2 && isLeapYear(year)) {
            return day >= 1 && day <= daysInMonth[month - 1] + 1;
        }

        return day >= 1 && day <= daysInMonth[month - 1];
    }

    /**
     * Returns true if a given month is valid
     */
    private static boolean isValidMonth(int month) {
        return month <= 12 && month >= 1;
    }

    /**
     * Returns true if a given year is a leap year
     */
    private static boolean isLeapYear(int year) {
        return (year % 400 == 0)
                || (year % 100 != 0 && year % 4 == 0);
    }

    /**
     * Returns true if a given time is valid formatted.
     */
    public static boolean isValidFormat(String test) {
        return test.matches(EVNET_TIME_VALIDATION_REGEX);
    }

```
###### \java\seedu\address\model\EventList.java
``` java
/**
 * Wraps all events at event list level
 */
public class EventList implements ReadOnlyEventList {
    private final UniqueEventList events;

    public EventList() {
        events = new UniqueEventList();
    }

    /**
     * Creates an EventList using the Persons and Tags and events in the {@code toBeCopied}
     */
    public EventList(ReadOnlyEventList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    public void setEvents(List<? extends ReadOnlyEvent> events) throws DuplicateEventException {
        this.events.setEvents(events);
    }

    /**
     * Resets the existing data of this {@code EventList} with {@code newData}.
     */
    public void resetData(ReadOnlyEventList newData) {
        requireNonNull(newData);
        try {
            setEvents(newData.getEventList());
        } catch (DuplicateEventException e) {
            assert false : "EventList should not have duplicate events";
        }
    }

    /**
     * Adds a event to the event list.
     * Also checks the new event's participants and updates {@link #events} with any new persons found,
     * and updates the Person objects in the event to point to those in {@link #events}.
     *
     * @throws DuplicateEventException if an equivalent person already exists.
     */
    public void addEvent(ReadOnlyEvent e) throws DuplicateEventException {
        Event newEvent = new Event(e);
        events.add(newEvent);
    }

    /**
     * Adds a event to the specific position in list.
     * Only used to undo deletion
     */
    public void addEvent(int position, ReadOnlyEvent e) {
        Event newEvent = new Event(e);
        events.add(position, newEvent);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedReadOnlyPerson}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedReadOnlyPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *                                  another existing person in the list.
     * @throws PersonNotFoundException  if {@code target} could not be found in the list.
     */
    public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedReadOnlyEvent)
            throws DuplicateEventException, EventNotFoundException {
        requireNonNull(editedReadOnlyEvent);

        Event editedEvent = new Event(editedReadOnlyEvent);
        events.setEvent(target, editedEvent);
    }


    /**
     * Removes {@code key} from this {@code EventList}.
     *
     * @throws EventNotFoundException if the {@code key} is not in this {@code EventList}.
     */
    public boolean removeEvent(ReadOnlyEvent key) throws EventNotFoundException, DeleteOnCascadeException {
        if (events.remove(key)) {
            return true;
        } else {
            throw new EventNotFoundException();
        }
    }

    /**
     * Remove a specific person from the participant list of an event
     */
    public void removeParticipant(ReadOnlyPerson person, Event targetEvent)
            throws PersonNotParticipateException {

        events.removeParticipant(person, targetEvent);
    }

    public void addParticipant(Person person, Event targetEvent)
            throws PersonHaveParticipateException {
        events.addParticipant(person, targetEvent);
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public Set<Tag> extractNewTag(ReadOnlyPerson person) {
        return addressBook.extractNewTags(person);
    }

    @Override
    public void removeTags(Set<Tag> tagList) {
        addressBook.separateMasterTagListWith(tagList);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent)
            throws DuplicateEventException, EventNotFoundException {
        requireAllNonNull(target, editedEvent);

        eventList.updateEvent(target, editedEvent);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        indicateEventListChanged();
    }

    //=========== Participant Operations =============================================================
    @Override
    public void quitEvent(Person person, Event event)
            throws PersonNotParticipateException, NotParticipateEventException {
        eventList.removeParticipant(person, event);
        indicateEventListChanged();

        addressBook.removeParticipation(person, event);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\storage\XmlAdaptedEventNoParticipant.java
``` java
/**
 * No participants in this adapted event, to avoid infinite loop when store person
 */
public class XmlAdaptedEventNoParticipant {

    @XmlElement(required = true)
    private String eventName;
    @XmlElement
    private String eventDesc;
    @XmlElement(required = true)
    private String eventTime;

    /**
     * Constructs an XmlAdaptedEvent.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEventNoParticipant() {}


    /**
     * Converts a given Event into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedEvent
     */
    public XmlAdaptedEventNoParticipant(ReadOnlyEvent source) {
        eventName = source.getEventName().fullEventName;
        eventDesc = source.getDescription().eventDesc;
        eventTime = source.getEventTime().eventTime;
    }

    /**
     * Converts this jaxb-friendly adapted event object into the model's Event object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event
     */
    public Event toModelType() throws IllegalValueException {

        final EventName eventName = new EventName(this.eventName);
        final EventDescription eventDesc = new EventDescription(this.eventDesc);
        final EventTime eventTime = new EventTime(this.eventTime);
        return new Event(eventName, eventDesc, eventTime);
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedPersonNoParticipation.java
``` java
/**
 * No participation events in this adapted event, to avoid infinite loop when store event
 */
public class XmlAdaptedPersonNoParticipation {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String birthday;
    @XmlElement(required = true)
    private String portraitPath;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPersonNoParticipation() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPersonNoParticipation(ReadOnlyPerson source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        birthday = source.getBirthday().value;
        portraitPath = source.getPortraitPath().filePath;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final Phone phone = new Phone(this.phone);
        final Email email = new Email(this.email);
        final Address address = new Address(this.address);
        final Birthday birthday = new Birthday(this.birthday);
        final PortraitPath path = new PortraitPath(this.portraitPath);
        final Set<Tag> tags = new HashSet<>(personTags);
        final Set<Event> emptyParticipation = new HashSet<Event>();
        return new Person(name, phone, email, address, birthday, path, tags, emptyParticipation);
    }
}
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Add the picture to portrait field
     * @param filePath the picture file
     */
    private void loadPortrait(String filePath) {
        String url;
        if (filePath.isEmpty() || !new File(filePath).exists()) { // In case user deletes the image file
            url = PortraitPath.DEFAULT_PORTRAIT_PATH;
        } else {
            url = PortraitPath.FILE_PREFIX + filePath;
        }
        Image portrait = new Image(url);

        this.portrait.setImage(portrait);
    }
```
###### \resources\view\PersonListCard.fxml
``` fxml
                  <ImageView fx:id="portrait" fitHeight="78.0" fitWidth="75.0" pickOnBounds="true" preserveRatio="true">
                     <HBox.margin>
                        <Insets right="50.0" top="14.0" />
                     </HBox.margin></ImageView>
```
