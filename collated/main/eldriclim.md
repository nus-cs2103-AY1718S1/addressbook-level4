# eldriclim
###### /java/seedu/address/commons/events/ui/CalendarSelectionChangedEvent.java
``` java
/**
 * When a selection is made in the calendar
 */
public class CalendarSelectionChangedEvent extends BaseEvent {

    private LocalDate selectedDate;

    public CalendarSelectionChangedEvent(LocalDate selectedDate) {
        this.selectedDate = selectedDate;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public LocalDate getSelectedDate() {
        return selectedDate;
    }
}
```
###### /java/seedu/address/commons/events/ui/EventPanelSelectionChangedEvent.java
``` java
/**
 * When a selection is made in the EventListPanel
 */
public class EventPanelSelectionChangedEvent extends BaseEvent {

    private Event selectedEvent;

    public EventPanelSelectionChangedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ObservableList<ReadOnlyPerson> getMemberAsArrayList() {

        List<ReadOnlyPerson> memberList = new ArrayList<>(
                selectedEvent.getMemberList().asReadOnlyMemberList());

        return FXCollections.observableArrayList(memberList);
    }

    public String getEventName() {
        return selectedEvent.getEventName().toString();
    }
}
```
###### /java/seedu/address/commons/events/ui/ScheduleUpdateEvent.java
``` java
/**
 * When schedule is being updated
 */
public class ScheduleUpdateEvent extends BaseEvent {

    private ObservableList<Event> events;

    public ScheduleUpdateEvent(ObservableList<Event> events) {
        this.events = events;
    }

    public ObservableList<Event> getEvents() {
        return events;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/util/DateTimeUtil.java
``` java
/**
 * Utility methods for checking event overlaps
 */
public class DateTimeUtil {

    public static final DateTimeFormatter EVENT_DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static String parseLocalDateTimeToString(LocalDateTime dateTime) {
        return dateTime.format(EVENT_DATETIME_FORMAT);
    }

    public static LocalDateTime parseStringToLocalDateTime(String input) throws DateTimeParseException {
        return LocalDateTime.parse(input, EVENT_DATETIME_FORMAT);
    }

    /**
     * Extracts duration of event if exist.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Duration parseDuration(String durationInput) throws IllegalValueException {

        if (durationInput.equals("")) {
            return Duration.ofMinutes(0);
        }

        String dayPattern = "(\\d+)d";
        String hourPattern = "(\\d+)h";
        String minPattern = "(\\d+)m";

        int dayCount = 0;
        int hourCount = 0;
        int minCount = 0;

        Pattern pattern = Pattern.compile(dayPattern);
        Matcher matcher = pattern.matcher(durationInput);
        if (matcher.find()) {
            dayCount = Integer.parseInt(matcher.group(1));
        }

        pattern = Pattern.compile(hourPattern);
        matcher = pattern.matcher(durationInput);
        if (matcher.find()) {
            hourCount = Integer.parseInt(matcher.group(1));
        }

        pattern = Pattern.compile(minPattern);
        matcher = pattern.matcher(durationInput);
        if (matcher.find()) {
            minCount = Integer.parseInt(matcher.group(1));
        }

        if (dayCount < 0 || hourCount < 0 || minCount < 0 || hourCount > 23 || minCount > 59) {
            throw new IllegalValueException("Illegal values detected.");
        }

        return Duration.ofMinutes(dayCount * 24 * 60 + hourCount * 60 + minCount);
    }


    /**
     * Checks if two events on a timeline overlaps
     *
     * @param e1 event to compare
     * @param e2 event to compare against
     * @return true if overlap is detected; else return false
     */
    public static boolean checkEventClash(Event e1, Event e2) {

        if (e1.getEventTime().getStart().isEqual(e2.getEventTime().getStart())) {
            return true;
        }

        if (e1.getEventTime().getEnd().isEqual(e2.getEventTime().getEnd())) {
            return true;
        }

        if (isBetween(e1.getEventTime().getEnd(), e2)) {
            return true;
        }

        if (isBetween(e1.getEventTime().getStart(), e2)) {
            return true;
        }

        if (e1.getEventTime().getStart().isAfter(e2.getEventTime().getStart())
                && e1.getEventTime().getEnd().isBefore(e2.getEventTime().getEnd())) {
            return true;
        }

        if (e2.getEventTime().getStart().isAfter(e1.getEventTime().getStart())
                && e2.getEventTime().getEnd().isBefore(e1.getEventTime().getEnd())) {
            return true;
        }

        return false;

    }

    /**
     * Checks if a given time lines between an event
     *
     * @param t1 a given time
     * @param e1 an event with a specified duration (start time & end time)
     * @return true if t1 lines within e1; else returns false
     */
    public static boolean isBetween(LocalDateTime t1, Event e1) {
        if (t1.isAfter(e1.getEventTime().getStart()) && t1.isBefore(e1.getEventTime().getEnd())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks to see if date lies in between Event start time and end time.
     *
     * @param event
     * @param refDate
     * @return
     */
    public static boolean containsReferenceDate(Event event, LocalDate refDate) {
        LocalDate startDate = event.getEventTime().getStart().toLocalDate();
        LocalDate endDate = event.getEventTime().getEnd().toLocalDate();

        return
                startDate.isEqual(refDate)
                        || endDate.isEqual(refDate)
                        || (startDate.isBefore(refDate) && endDate.isAfter(refDate));
    }

}
```
###### /java/seedu/address/commons/util/EventOutputUtil.java
``` java

/**
 * Utility method to output Event attributes in human-readable time
 */
public class EventOutputUtil {

    /**
     * Takes in a Duration and outputs it hours and minutes
     *
     * @param d1 given duration of Event, guaranteed to be less than 23hr59min
     * @return a String of Duration in human-readable form
     */
    public static String toStringDuration(Duration d1) {

        StringBuilder sb = new StringBuilder();

        int totalSeconds = (int) d1.getSeconds();
        int daysOutput = totalSeconds / (60 * 60 * 24);
        int hoursOutput = (totalSeconds % (60 * 60 * 24)) / (60 * 60);
        int minutesOutput = (totalSeconds % (60 * 60)) / 60;

        if (daysOutput > 0) {
            sb.append(daysOutput + "day");
        }

        if (hoursOutput > 0) {
            sb.append(hoursOutput + "hr");
        }

        if (minutesOutput > 0) {
            sb.append(minutesOutput + "min");
        }

        if (daysOutput == 0 && hoursOutput == 0 && minutesOutput == 0) {
            sb.append(0 + "min");
        }

        return sb.toString();

    }

    /**
     * Takes in a list of members and output their names in readable form
     *
     * @param members
     * @return a String with members name separated by commas
     */
    public static String toStringMembers(ArrayList<ReadOnlyPerson> members) {
        if (members.isEmpty()) {
            return "none";
        }

        ArrayList<String> memberNames = new ArrayList<>(
                members.stream().map(p -> p.getName().toString()).collect(Collectors.toList()));

        return StringUtil.multiStringPrint(memberNames, ", ");
    }

    /**
     * Outputs an Event details in readable form
     *
     * @param eventName
     * @param eventTime
     * @param eventDuration
     * @param memberList
     * @return
     */
    public static String toStringEvent(EventName eventName, EventTime eventTime,
                                       EventDuration eventDuration, MemberList memberList) {

        ArrayList<ReadOnlyPerson> members = new ArrayList<>(memberList.asReadOnlyMemberList());

        return "Name: " + eventName.toString() + " Time: " + eventTime.toString()
                + " Duration: " + eventDuration.toString() + "\n"
                + "Members: " + toStringMembers(members);
    }
}
```
###### /java/seedu/address/commons/util/StringUtil.java
``` java
    /**
     * Output a series of Strings with the specified separator.
     *
     * @param list
     * @param separator
     * @return
     */
    public static String multiStringPrint(List<String> list, String separator) {
        Iterator<String> iterator = list.iterator();
        String output = "";

        if (iterator.hasNext()) {
            output += iterator.next();

            while (iterator.hasNext()) {
                output += separator + iterator.next();
            }
        }

        return output;
    }
}
```
###### /java/seedu/address/logic/commands/ScheduleAddCommand.java
``` java

/**
 * Adds an Event to address book
 */
public class ScheduleAddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "s-add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the address book. "
            + "Parameters: "
            + PREFIX_EVENT_MEMBER + "[INDEX]... "
            + PREFIX_EVENT_NAME + "NAME "
            + PREFIX_EVENT_TIME + "DATETIME "
            + PREFIX_EVENT_DURATION + "DURATION \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_MEMBER + "1 2 3 "
            + PREFIX_EVENT_NAME + "Movie Date "
            + PREFIX_EVENT_TIME + "2017-01-01 10:00 "
            + PREFIX_EVENT_DURATION + "2h30m ";

    public static final String MESSAGE_SUCCESS = "New Event added: \n%1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book";
    public static final String ERROR_INVALID_INDEX = "Invalid index detected.";
    public static final String ERROR_FAIL_TO_UPDATE_MEMBER = "An update error has occured.";

    private final EventName eventName;
    private final EventTime eventTime;
    private final EventDuration eventDuration;
    private final ArrayList<Index> uniqueMemberIndexes;

    public ScheduleAddCommand(EventName eventName, EventTime eventTime, EventDuration eventDuration,
                              Set<Index> uniqueMemberIndexs) {
        this.eventName = eventName;
        this.eventTime = eventTime;
        this.eventDuration = eventDuration;
        this.uniqueMemberIndexes = new ArrayList<>(uniqueMemberIndexs);
    }


    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        ArrayList<ReadOnlyPerson> toUpdate = new ArrayList<>();
        ArrayList<ReadOnlyPerson> toReplace = new ArrayList<>();
        Event event = new Event(new MemberList(), eventName, eventTime, eventDuration);

        for (Index index : uniqueMemberIndexes) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(ERROR_INVALID_INDEX);
            }
            EditEventListPersonDescriptor toEditPerson = new EditEventListPersonDescriptor(
                    lastShownList.get(index.getZeroBased()), event);

            toUpdate.add(lastShownList.get(index.getZeroBased()));
            toReplace.add(toEditPerson.createUpdatedPerson());
        }

        try {
            String commandResultString = "";
            if (model.hasEvenClashes(event)) {
                commandResultString += "Warning: An event clash has been detected.\n";
            }

            model.addEvent(toUpdate, toReplace, event);

            commandResultString += String.format(MESSAGE_SUCCESS, event.toString());
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            Index defaultIndex = new Index(0);
            EventsCenter.getInstance().post(new JumpToListRequestEvent(defaultIndex));

            return new CommandResult(commandResultString);
        } catch (DuplicateEventException e) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        } catch (DuplicatePersonException dpe) {
            //Should not reach this point
            throw new CommandException(ERROR_FAIL_TO_UPDATE_MEMBER);
        } catch (PersonNotFoundException pnfe) {
            //Should not reach this point
            throw new AssertionError("The target person cannot be missing");
        }
    }


    /**
     * Stores the details of modified person with updated event list. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    private static class EditEventListPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;
        private Set<Event> events;
        private DateAdded dateAdded;
        private Birthday birthday;

        public EditEventListPersonDescriptor(ReadOnlyPerson toCopy, Event event) {
            this.name = toCopy.getName();
            this.phone = toCopy.getPhone();
            this.email = toCopy.getEmail();
            this.address = toCopy.getAddress();
            this.tags = toCopy.getTags();
            this.dateAdded = toCopy.getDateAdded();
            this.birthday = toCopy.getBirthday();

            this.events = createModifiableEventList(toCopy.getEvents());
            this.events.add(event);
        }

        public Set<Event> createModifiableEventList(Set<Event> unmodifiableEventList) {
            Set<Event> modifiableEventList = new HashSet<>(unmodifiableEventList);
            return modifiableEventList;
        }

        public Person createUpdatedPerson() {
            return new Person(name, birthday, phone, email, address, tags, events, dateAdded);
        }
    }
}
```
###### /java/seedu/address/logic/commands/ScheduleRemoveCommand.java
``` java

/**
 * Remove multiple Events from address book
 */
public class ScheduleRemoveCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "s-remove";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Remove events from the address book. "
            + "Parameters: "
            + PREFIX_EVENT_INDEXES + "[INDEX]... \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_INDEXES + "1 2 3 ";

    public static final String MESSAGE_SUCCESS = "Event(s) removed: \n%1$s";
    public static final String ERROR_EVENT_NOT_FOUND = "Invalid event detected.";
    public static final String ERROR_INVALID_INDEX = "Invalid index detected.";
    public static final String ERROR_FAIL_TO_UPDATE_MEMBER = "An update error has occured.";

    private final ArrayList<Index> uniqueEventIndexes;

    public ScheduleRemoveCommand(Set<Index> eventListIndex) {
        this.uniqueEventIndexes = new ArrayList<>(eventListIndex);
    }

    public String getRemovedEventsString(ArrayList<Event> removedEvents) {
        ArrayList<String> outputString = new ArrayList<>();
        removedEvents.forEach(e -> outputString.add(e.toString()));

        return StringUtil.multiStringPrint(outputString, "\n");
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        List<Event> lastShownEventList = model.getEventList();

        ArrayList<Event> toRemoveEvents = new ArrayList<>();
        ArrayList<ReadOnlyPerson> toUpdatePersons = new ArrayList<>();
        ArrayList<ReadOnlyPerson> toReplacePersons = new ArrayList<>();


        for (Index index : uniqueEventIndexes) {
            if (index.getZeroBased() >= lastShownEventList.size()) {
                throw new CommandException(ERROR_INVALID_INDEX);
            }
            toRemoveEvents.add(lastShownEventList.get(index.getZeroBased()));
        }

        model.getAddressBook().getPersonList().stream().filter(p ->
                !Collections.disjoint(p.getEvents(), toRemoveEvents)).forEach(toUpdatePersons::add);

        toUpdatePersons.forEach(p -> {
            EditEventListPersonDescriptor toEditPerson = new EditEventListPersonDescriptor(
                    p, toRemoveEvents);
            toReplacePersons.add(toEditPerson.createUpdatedPerson());
        });

        try {
            model.removeEvents(toUpdatePersons, toReplacePersons, toRemoveEvents);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            Index defaultIndex = new Index(0);
            EventsCenter.getInstance().post(new JumpToListRequestEvent(defaultIndex));

            return new CommandResult(String.format(MESSAGE_SUCCESS, getRemovedEventsString(toRemoveEvents)));

        } catch (DuplicatePersonException dpe) {
            //Should not reach this point
            throw new CommandException(ERROR_FAIL_TO_UPDATE_MEMBER);
        } catch (PersonNotFoundException pnfe) {
            //Should not reach this point
            throw new AssertionError("The target person cannot be missing");
        } catch (EventNotFoundException enf) {
            //Should not reach this point
            throw new CommandException(ERROR_EVENT_NOT_FOUND);
        }
    }


    /**
     * Stores the details of modified person with updated event list. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    private static class EditEventListPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;
        private Set<Event> events;
        private DateAdded dateAdded;
        private Birthday birthday;

        public EditEventListPersonDescriptor(ReadOnlyPerson toCopy, ArrayList<Event> toRemoveEvents) {
            this.name = toCopy.getName();
            this.phone = toCopy.getPhone();
            this.email = toCopy.getEmail();
            this.address = toCopy.getAddress();
            this.tags = toCopy.getTags();
            this.dateAdded = toCopy.getDateAdded();

            this.events = createModifiableEventList(toCopy.getEvents());
            this.birthday = toCopy.getBirthday();

            removeEvents(toRemoveEvents);
        }

        private void removeEvents(ArrayList<Event> toRemoveEvents) {
            toRemoveEvents.stream().filter(e -> events.contains(e)).forEach(e -> events.remove(e));
        }

        public Set<Event> createModifiableEventList(Set<Event> unmodifiableEventList) {
            Set<Event> modifiableEventList = new HashSet<>(unmodifiableEventList);
            return modifiableEventList;
        }

        public Name getName() {
            return name;
        }

        public Phone getPhone() {
            return phone;
        }

        public Email getEmail() {
            return email;
        }

        public Address getAddress() {
            return address;
        }

        public Set<Tag> getTags() {
            return tags;
        }

        public Set<Event> getEvents() {
            return events;
        }

        public DateAdded getDateAdded() {
            return dateAdded;
        }

        public Person createUpdatedPerson() {
            return new Person(name, birthday, phone, email, address, tags, events, dateAdded);
        }
    }
}
```
###### /java/seedu/address/logic/commands/SortCommand.java
``` java
/**
 * Sorts list of all contacts base on given parameter.
 * Accepts both ascending and descending.
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";
    public static final String BY_ASCENDING = "asc";
    public static final String BY_DESCENDING = "dsc";

    public static final String MESSAGE_SORT_LIST_SUCCESS = "List has been sorted by %1$s in %2$s order.";
    public static final String MESSAGE_EMPTY_LIST = "The list is empty.";

    private static final String PREFIX_NAME_SORT = "n/";
    private static final String PREFIX_PHONE_SORT = "p/";
    private static final String PREFIX_EMAIL_SORT = "e/";
    private static final String PREFIX_ADDRESS_SORT = "a/";
    private static final String PREFIX_DATEADDED_SORT = "t/";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts list of all contacts by their attributes,"
            + " defaults to name when no parameters found and ascending when order not specified.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME_SORT + "(" + BY_ASCENDING + " OR " + BY_DESCENDING + ")] \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PHONE_SORT + BY_DESCENDING + " OR "
            + COMMAND_WORD + " " + PREFIX_ADDRESS_SORT + " OR "
            + COMMAND_WORD;

    private final String sortType;
    private final Boolean isDescending;

    //Default setting, not final as subjected to change
    private String sortTypeReadable = "name";
    private String sortOrderReadable = "ascending";

    /**
     * @param sortType     specify which attribute to sort by
     * @param isDescending specify if sorting is to be in descending order
     */
    public SortCommand(String sortType, Boolean isDescending) {
        requireNonNull(sortType);
        requireNonNull(isDescending);

        this.sortType = sortType;
        this.isDescending = isDescending;

        sortOrderReadable = (isDescending) ? "descending" : "ascending";
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        Comparator<ReadOnlyPerson> sortType = getComparator(this.sortType);
        try {
            model.sortPerson(sortType, isDescending);
        } catch (EmptyListException ele) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        Index defaultIndex = new Index(0);
        EventsCenter.getInstance().post(new JumpToListRequestEvent(defaultIndex));
        return new CommandResult(String.format(MESSAGE_SORT_LIST_SUCCESS, sortTypeReadable, sortOrderReadable));
    }

    public Comparator<ReadOnlyPerson> getComparator(String sortType) {
        switch (sortType) {
        case PREFIX_NAME_SORT:
            sortTypeReadable = "name";
            return (o1, o2) -> o1.getName().toString().compareToIgnoreCase(
                    o2.getName().toString()
            );
        case PREFIX_PHONE_SORT:
            sortTypeReadable = "phone";
            return (o1, o2) -> o1.getPhone().toString().compareToIgnoreCase(
                    o2.getPhone().toString()
            );
        case PREFIX_EMAIL_SORT:
            sortTypeReadable = "email";
            return (o1, o2) -> o1.getEmail().toString().compareToIgnoreCase(
                    o2.getEmail().toString()
            );
        case PREFIX_ADDRESS_SORT:
            sortTypeReadable = "address";
            return (o1, o2) -> o1.getAddress().toString().compareToIgnoreCase(
                    o2.getAddress().toString()
            );
        case PREFIX_DATEADDED_SORT:
            sortTypeReadable = "date added";
            return Comparator.comparing(o -> o.getDateAdded().getDateObject());
        default:
            sortTypeReadable = "name";
            return (o1, o2) -> o1.getName().toString().compareToIgnoreCase(
                    o2.getName().toString()
            );
        }
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && sortType.equals(((SortCommand) other).sortType)
                && isDescending.equals(((SortCommand) other).isDescending));
    }
}
```
###### /java/seedu/address/logic/parser/exceptions/DateParseException.java
``` java
/**
 * Represents a parse error encountered when parsing Date.
 */
public class DateParseException extends ParseException {

    public DateParseException(String message) {
        super(message);
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    //// Event-related parsing

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<EventName>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<EventName> parseEventName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(new EventName(name.get())) : Optional.empty();
    }

    /**
     * Parses two {@code Optional<String> time} and {@code String duration} into an
     * {@code Optional<EventTime>} if {@code time} is present.
     *
     * String duration is guaranteed to be initialised from input validation in
     * @see ScheduleAddCommandParser
     *
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<EventTime> parseEventTime(Optional<String> time, String duration)
            throws DateTimeParseException, NumberFormatException, IllegalValueException {
        requireNonNull(time);
        requireNonNull(duration);
        return (time.isPresent())
                ? Optional.of(new EventTime(DateTimeUtil.parseStringToLocalDateTime(time.get()),
                DateTimeUtil.parseDuration(duration)))
                : Optional.empty();
    }

    /**
     * Parses a {@code String duration} into an {@code EventDuration}
     *
     * String duration is guaranteed to be initialised from input validation in
     * @see ScheduleAddCommandParser

     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static EventDuration parseEventDuration(String duration)
            throws NumberFormatException, IllegalValueException {
        requireNonNull(duration);
        return new EventDuration(DateTimeUtil.parseDuration(duration));
    }


}
```
###### /java/seedu/address/logic/parser/ScheduleRemoveCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ScheduleRemoveCommand object
 */
public class ScheduleRemoveCommandParser implements Parser<ScheduleRemoveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ScheduleRemoveCommand
     * and returns an ScheduleRemoveCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ScheduleRemoveCommand parse(String args) throws ParseException {
        args.trim();


        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_INDEXES);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_INDEXES)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ScheduleRemoveCommand.MESSAGE_USAGE));
        }


        try {

            ArrayList<Index> indexList = ParserUtil.parseIndexes(argMultimap.getValue(PREFIX_EVENT_INDEXES).get());
            Set<Index> uniqueEventIndexes = new HashSet<>(indexList);

            return new ScheduleRemoveCommand(uniqueEventIndexes);
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
###### /java/seedu/address/logic/parser/SortCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    private String sortType;
    private Boolean isDescending;

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);

        if (!args.matches("^|( [npeat]/((asc)|(dsc)|))$")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        if ("".equals(args)) {
            args = " n/asc";
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_DATEADDED);

        assert argMultimap.size() == 2;

        argMultimap.getValue(PREFIX_NAME).ifPresent(setSortingOrder(PREFIX_NAME));
        argMultimap.getValue(PREFIX_PHONE).ifPresent(setSortingOrder(PREFIX_PHONE));
        argMultimap.getValue(PREFIX_EMAIL).ifPresent(setSortingOrder(PREFIX_EMAIL));
        argMultimap.getValue(PREFIX_ADDRESS).ifPresent(setSortingOrder(PREFIX_ADDRESS));
        argMultimap.getValue(PREFIX_DATEADDED).ifPresent(setSortingOrder(PREFIX_DATEADDED));

        return new SortCommand(sortType, isDescending);
    }

    private Consumer<String> setSortingOrder(Prefix prefix) {
        return s -> {

            sortType = prefix.toString();

            if (s.equals(SortCommand.BY_ASCENDING)) {
                isDescending = new Boolean(false);
                return;
            }

            //Defaults to ascending when order not specified
            if ("".equals(s)) {
                isDescending = new Boolean(false);
                return;
            }

            if (s.equals(SortCommand.BY_DESCENDING)) {
                isDescending = new Boolean(true);
                return;
            }


        };
    }
}
```
###### /java/seedu/address/model/AddressBook.java
``` java

    /**
     * Replaces the given person {@code target} in the list with {@code editedReadOnlyPerson}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedReadOnlyPerson}.
     * {@code AddressBook}'s event list will be updated with the event of {@code editedReadOnlyPerson}.
     * <p>
     * <p>
     * Guarantees that both list are of the same size and elements are ordered in such a way where
     * one replaces the other.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *                                  another existing person in the list.
     * @throws PersonNotFoundException  if {@code target} could not be found in the list.
     * @see #syncMasterTagListWith(Person)
     */
    public void updateListOfPerson(ArrayList<ReadOnlyPerson> targets, ArrayList<ReadOnlyPerson> editedPersons)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(targets);
        requireNonNull(editedPersons);

        Iterator<ReadOnlyPerson> targetsIterator = targets.iterator();
        Iterator<ReadOnlyPerson> editPersonIterator = editedPersons.iterator();

        while (targetsIterator.hasNext() && editPersonIterator.hasNext()) {
            updatePerson(targetsIterator.next(), editPersonIterator.next());
        }

    }

    /**
     * Takes in two list of ReadOnlyPerson, one to be edited and one already edited.
     * Checks if event already exist in master list and throws DuplicateEventException when found.
     * Otherwise, updated persons list will replace the list that is to be edited, updating the master
     * event list in the process.
     * <p>
     * Guarantees that person to edit exist in address book, handled in {@see ScheduleRemoveCommand}.
     * Guarantees that update person does not exist in address book, handled in {@see ScheduleRemoveCommand}.
     *
     * @param targets       list of person to be edited
     * @param editedPersons list of edited person
     * @param event         event to be added
     * @throws DuplicateEventException  when Events with the same name, datetime and duration is detecte
     *                                  {@see Event#equals(Object)}
     * @throws PersonNotFoundException  when person to edit is not found, should not reach this point
     * @throws DuplicatePersonException when updated person already exist, should not reach this point
     */
    public void addEvent(ArrayList<ReadOnlyPerson> targets, ArrayList<ReadOnlyPerson> editedPersons, Event event)
            throws DuplicateEventException, PersonNotFoundException, DuplicatePersonException {

        requireNonNull(targets);
        requireNonNull(editedPersons);
        requireNonNull(event);

        if (events.contains(event)) {
            throw new DuplicateEventException();
        }

        if (targets.isEmpty() && editedPersons.isEmpty()) {
            events.add(event);
        } else {
            updateListOfPerson(targets, editedPersons);
        }
        events.sort(LocalDate.now());
    }

    /**
     * Takes in two list of ReadOnlyPerson, one to be edited and one already edited.
     * The updated persons list will replace the list that is to be edited, updating the master
     * event list in the process.
     * <p>
     * Guarantees that events to be removed from master list exist in address book, handled
     * in {@see ScheduleRemoveCommand}
     * Guarantees that person to edit exist in address book, handled in {@see ScheduleRemoveCommand}.
     * Guarantees that update person does not exist in address book, handled in {@see ScheduleRemoveCommand}.
     *
     * @param targets        list of person to be edited
     * @param editedPersons  list of edited person
     * @param toRemoveEvents list of events that is to be removed from master list
     * @throws EventNotFoundException   when event that is to be deleted from master list is not found
     * @throws PersonNotFoundException  when person to edit is not found, should not reach this point
     * @throws DuplicatePersonException when updated person already exist, should not reach this point
     */
    public void removeEvents(ArrayList<ReadOnlyPerson> targets, ArrayList<ReadOnlyPerson> editedPersons,
                             ArrayList<Event> toRemoveEvents)
            throws PersonNotFoundException, DuplicatePersonException, EventNotFoundException {

        requireNonNull(targets);
        requireNonNull(editedPersons);

        for (Event e : toRemoveEvents) {
            events.remove(e);
        }


        updateListOfPerson(targets, editedPersons);
        events.sort(LocalDate.now());
    }

    /**
     * Sort list of Events based on the the given date.
     * <p>
     * Comparator logic and sorting details is found in {@code UniquePersonList#sort}
     *
     * @param date
     */
    public void sortEvents(LocalDate date) {
        events.sort(date);
    }

    /**
     * Check if the given event clashes with any events in the master list of events
     *
     * @param event
     * @return true if a clash exist, otherwise return false
     */
    public boolean hasEventClashes(Event event) {

        boolean hasClash = false;

        for (Event e : events) {
            hasClash = DateTimeUtil.checkEventClash(e, event);

            if (hasClash) {
                return hasClash;
            }
        }

        return hasClash;

    }

    /**
     * Ensures that every event in this person:
     * - exists in the master list {@link #events}
     * - points to a Event object in the master list
     */
    private void syncMasterEventListWith(Person person) {
        final UniqueEventList personEvents = new UniqueEventList(person.getEvents());
        events.mergeFrom(personEvents);

        // Create map with values = event object references in the master list
        // used for checking person event references
        final Map<Event, Event> masterEventObjects = new HashMap<>();
        events.forEach(event -> masterEventObjects.put(event, event));

        // Rebuild the personal list of events to point to the relevant events in the master event list.
        final Set<Event> correctEventReferences = new HashSet<>();
        personEvents.forEach(event -> correctEventReferences.add(masterEventObjects.get(event)));
        person.setEvents(correctEventReferences);
    }


    /**
     * Ensures that every event in these persons:
     * - exists in the master list {@link #events}
     * - points to an Event object in the master list
     *
     * @see #syncMasterEventListWith(Person)
     */
    private void syncMasterEventListWith(UniquePersonList persons) {
        persons.forEach(this::syncMasterEventListWith);
    }


    /**
     * Ensures that every member in this event:
     * - points to a person object in the master person list
     */
    private void syncMasterEventListMembers(Event event) {

        // Create map with values = person object references in the master list
        // used for checking member references
        final Map<Person, Person> masterPersonObjects = new HashMap<>();
        this.persons.forEach(person -> masterPersonObjects.put(person, person));

        ArrayList<ReadOnlyPerson> eventMembers = new ArrayList<>();
        this.persons.asObservableList().stream().filter(readOnlyPerson ->
                readOnlyPerson.getEvents().contains(event)).forEach(eventMembers::add);


        // Rebuild the list of member to point to the relevant person in the master person list.
        final Set<Person> correctPersonReferences = new HashSet<>();
        eventMembers.forEach(person -> correctPersonReferences.add(masterPersonObjects.get(person)));
        event.setMemberList(new MemberList(
                new ArrayList<>(correctPersonReferences)));
    }

    /**
     * Ensures that every member in these events:
     * - points to a person object in the master list
     *
     * @see #syncMasterEventListMembers(Event)
     */
    private void syncMasterEventListMembers(UniqueEventList events) {
        events.forEach(this::syncMasterEventListMembers);
    }
```
###### /java/seedu/address/model/event/Event.java
``` java

/**
 * Represents a Event in the address book, accepts an event with no members.
 * Guarantees: validity upon creation; potential errors handled in UniqueEventList
 *
 * @see UniqueEventList
 */
public class Event {

    private ObjectProperty<MemberList> members;
    private ObjectProperty<EventName> eventName;
    private ObjectProperty<EventTime> eventTime;
    private ObjectProperty<EventDuration> eventDuration;
    private ObjectProperty<String> eventStatus = new SimpleObjectProperty<>();
    private ObjectProperty<String> eventStatusStyle = new SimpleObjectProperty<>();


    public Event(MemberList members, EventName eventName, EventTime eventStartTime, EventDuration eventDuration) {
        requireNonNull(members);
        requireNonNull(eventName);
        requireNonNull(eventStartTime);
        requireNonNull(eventDuration);

        this.members = new SimpleObjectProperty<>(members);
        this.eventName = new SimpleObjectProperty<>(eventName);
        this.eventTime = new SimpleObjectProperty<>(eventStartTime);
        this.eventDuration = new SimpleObjectProperty<>(eventDuration);

        setEventStatusDefaultState();

        assert getEventTime().getStart().plus(getEventDuration().getDuration()).equals(
                getEventTime().getEnd());
    }

    public Event(Event source) {
        this(source.getMemberList(), source.getEventName(), source.getEventTime(), source.getEventDuration());
    }

    public void setEventName(EventName eventName) {
        this.eventName.set(requireNonNull(eventName));
    }

    public ObjectProperty<EventName> eventNameProperty() {
        return eventName;
    }

    public EventName getEventName() {
        return eventName.get();
    }

    public void setEventTime(EventTime eventTime) {
        this.eventTime.set(requireNonNull(eventTime));
    }

    public ObjectProperty<EventTime> eventTimeProperty() {
        return eventTime;
    }

    public EventTime getEventTime() {
        return eventTime.get();
    }

    public void setEventDuration(EventDuration eventDuration) {
        this.eventDuration.set(requireNonNull(eventDuration));
    }

    public ObjectProperty<EventDuration> eventDurationProperty() {
        return eventDuration;
    }

    public EventDuration getEventDuration() {
        return eventDuration.get();
    }

    public void setMemberList(MemberList members) {
        this.members.set(requireNonNull(members));
    }

    public ObjectProperty<MemberList> eventMemberListProperty() {
        return members;
    }

    public MemberList getMemberList() {
        return members.get();
    }


    public ObjectProperty<String> eventStatusProperty() {
        return eventStatus;
    }

    public String getEventStatus() {
        return eventStatus.get();
    }

    public void setEventStatusDefaultState() {
        if (DateTimeUtil.containsReferenceDate(this, LocalDate.now())) {
            eventStatus.setValue("Today");
            eventStatusStyle.setValue("-fx-background-color: #fd720f");
        } else if (eventTime.get().isUpcoming()) {
            eventStatus.setValue("Upcoming");
            eventStatusStyle.setValue("-fx-background-color: #009e73");
        } else {
            eventStatus.setValue("Past");
            eventStatusStyle.setValue("-fx-background-color: #a31621");
        }
    }

    /**
     * Reset name and style of all status label
     *
     * @param date
     */
    public void updateEventStatusSelection(LocalDate date) {

        setEventStatusDefaultState();

        if (DateTimeUtil.containsReferenceDate(this, date)) {
            eventStatus.setValue("Selected");
            eventStatusStyle.setValue("-fx-background-color: #b91372");
        }
    }

    public ObjectProperty<String> eventStatusStyleProperty() {
        return eventStatusStyle;
    }

    public String getEventStatusStyle() {
        return eventStatusStyle.get();
    }

    /**
     * Checks all attribute of Events
     *
     * @param other
     * @return true if all attributes are similar; false if otherwise
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Event // instanceof handles nulls
                && this.eventName.get().equals(((Event) other).getEventName())
                && this.eventTime.get().equals(((Event) other).getEventTime())
                && this.eventDuration.get().equals(((Event) other).getEventDuration())); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEventName(), getEventTime(), getEventDuration());
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return EventOutputUtil.toStringEvent(getEventName(), getEventTime(), getEventDuration(), getMemberList());
    }

}
```
###### /java/seedu/address/model/event/EventDuration.java
``` java
/**
 * This Object only serves as a placeholder for UI purposes.
 *
 * @see EventTime for actual manipulation of event time
 * <p>
 * Justification:
 * DateTime and Duration are to be tightly-coupled,
 * therefore both values are placed in EventTime.java
 * <p>
 * The purpose of this class is to store the duration
 * value, which is to be outputted via its toString method
 * from its ObjectProperty binding at EventCard.java
 * @see seedu.address.ui.EventCard#bindListeners(Event)
 */
public class EventDuration {

    private Duration eventDuration;

    public EventDuration(Duration eventDuration) {
        this.eventDuration = eventDuration;
    }


    public Duration getDuration() {
        return eventDuration;
    }

    @Override
    public String toString() {
        return EventOutputUtil.toStringDuration(eventDuration);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventDuration // instanceof handles nulls
                && this.eventDuration.equals(((EventDuration) other).getDuration())); // state check
    }

    @Override
    public int hashCode() {
        return eventDuration.hashCode();
    }
}
```
###### /java/seedu/address/model/event/EventName.java
``` java
/**
 * Represents an Event's name in the address book.
 * Guarantees: valid as declared in {@link #isValidName(String)}
 */
public class EventName {
    public static final String MESSAGE_EVENTNAME_CONSTRAINTS =
            "Event names should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String EVENTNAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public EventName(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_EVENTNAME_CONSTRAINTS);
        }
        this.fullName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid event name.
     */
    public static boolean isValidName(String test) {
        return test.matches(EVENTNAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventName // instanceof handles nulls
                && this.fullName.equals(((EventName) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
```
###### /java/seedu/address/model/event/EventTime.java
``` java
/**
 * Represents an Event's start time and duration in the address book.
 */
public class EventTime {


    private final LocalDateTime eventStartTime;
    private final Duration eventDuration;
    private final LocalDateTime eventEndTime;
    private Boolean isUpcoming;

    public EventTime(LocalDateTime eventStartTime, Duration eventDuration) {
        this.eventStartTime = eventStartTime;
        this.eventDuration = eventDuration;
        this.eventEndTime = eventStartTime.plus(eventDuration);

        isUpcoming = LocalDateTime.now().isBefore(this.eventStartTime);
    }

    /**
     * Check the value of isUpcoming by checking current time against start time
     *
     * @return boolean value of isUpcoming
     */
    public boolean isUpcoming() {
        if (LocalDateTime.now().isBefore(eventStartTime)) {
            isUpcoming = Boolean.TRUE;
            return isUpcoming;
        } else {
            isUpcoming = Boolean.FALSE;
            return isUpcoming;
        }
    }

    public LocalDateTime getStart() {
        return eventStartTime;
    }

    public LocalDateTime getEnd() {
        return eventEndTime;
    }

    @Override
    public String toString() {
        return DateTimeUtil.parseLocalDateTimeToString(eventStartTime);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventTime // instanceof handles nulls
                && this.eventStartTime.isEqual(((EventTime) other).eventStartTime)
                && this.eventDuration.equals(((EventTime) other).eventDuration)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventStartTime, eventDuration);
    }

}
```
###### /java/seedu/address/model/event/exceptions/DuplicateEventException.java
``` java
/**
 * Signals that the operation will result in duplicate Person objects.
 */
public class DuplicateEventException extends DuplicateDataException {
    public DuplicateEventException() {
        super("Operation would result in duplicate event");
    }
}
```
###### /java/seedu/address/model/event/exceptions/EventNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified event.
 */
public class EventNotFoundException extends Exception {}
```
###### /java/seedu/address/model/event/MemberList.java
``` java
/**
 * A list of members of a given event that enforces uniqueness between its elements.
 * Accept an empty list
 * <p>
 * Supports minimal set of list operations for the app's features.
 *
 * @see ReadOnlyPerson#equals(Object)
 */
public class MemberList {

    private ArrayList<ReadOnlyPerson> members;

    public MemberList() {
        members = new ArrayList<>();
    }

    public MemberList(ArrayList<ReadOnlyPerson> members) {
        requireNonNull(members);
        CollectionUtil.elementsAreUnique(members);

        this.members = new ArrayList<>();
        this.members.addAll(members);
        this.members.sort((o1, o2) -> o1.getName().toString().compareToIgnoreCase(o2.getName().toString()));
    }

    /**
     * Returns a read-only list of member.
     *
     * @return an unmodifiable List of members
     */
    public List<ReadOnlyPerson> asReadOnlyMemberList() {
        ArrayList<ReadOnlyPerson> readOnlyList = new ArrayList<>();
        readOnlyList.addAll(members);

        return Collections.unmodifiableList(readOnlyList);
    }

    /**
     * Search member list to see if given person exist.
     *
     * @param toFind
     * @return returns true if success; false if not found
     */
    public boolean contains(ReadOnlyPerson toFind) {
        requireNonNull(toFind);
        return members.contains(toFind);
    }

    public Boolean isEmpty() {
        return members.isEmpty();
    }

    @Override
    public String toString() {
        return EventOutputUtil.toStringMembers(members);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MemberList // instanceof handles nulls
                && this.members.equals(((MemberList) other).asReadOnlyMemberList())); // state check
    }

    @Override
    public int hashCode() {
        return members.hashCode();
    }


}
```
###### /java/seedu/address/model/event/UniqueEventList.java
``` java
/**
 * A list of events that enforces no nulls and uniqueness between its elements.
 * Ensures that there is no overlap of events in the list
 * <p>
 * Supports minimal set of list operations for the app's features.
 *
 * @see Event#equals(Object)
 */
public class UniqueEventList implements Iterable<Event> {

    private final ObservableList<Event> internalList = FXCollections.observableArrayList();

    //used by asObservableList()
    private final ObservableList<Event> mappedList = EasyBind.map(internalList, (event) -> event);

    /**
     * Constructs empty UniqueEventList.
     */
    public UniqueEventList() {
    }

    /**
     * Creates a UniqueEventList using given tags.
     * Enforces no nulls.
     */
    public UniqueEventList(Set<Event> events) {
        requireAllNonNull(events);
        internalList.addAll(events);

        sort(LocalDate.now());

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all event in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Event> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Ensures every tag in the argument list exists in this object.
     */
    public void mergeFrom(UniqueEventList from) {
        final Set<Event> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(event -> !alreadyInside.contains(event))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }


    /**
     * Returns true if the list contains an equivalent event as the given argument.
     */
    public boolean contains(Event toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Checks if event clashes exist and adds an event to the list.
     *
     * @throws DuplicateEventException if the event to add is a duplicate of an existing event in the list.
     */
    public void add(Event toAdd) throws DuplicateEventException {
        requireNonNull(toAdd);

        if (contains(toAdd)) {
            throw new DuplicateEventException();
        }
        internalList.add(new Event(toAdd));

        assert CollectionUtil.elementsAreUnique(internalList);
    }


    /**
     * Replaces the event {@code target} in the list with {@code editedEvent}.
     *
     * @throws DuplicateEventException if the replacement is equivalent to another existing event in the list.
     * @throws EventNotFoundException  if {@code target} could not be found in the list.
     */
    public void setEvent(Event target, Event editedEvent) throws DuplicateEventException, EventNotFoundException {
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
    public boolean remove(Event toRemove) throws EventNotFoundException {
        requireNonNull(toRemove);
        final boolean eventFoundAndDeleted = internalList.remove(toRemove);
        if (!eventFoundAndDeleted) {
            throw new EventNotFoundException();
        }
        return eventFoundAndDeleted;
    }

    /**
     * Checks for event clashes when events of the same date is found.
     *
     * @param toCheck
     * @return true when clash exist; else return false
     */
    public boolean hasClashes(Event toCheck) {
        for (Event e : internalList) {
            if (DateTimeUtil.checkEventClash(toCheck, e)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Events from selected date always appears at the top, followed by upcoming
     * events then past events. Within the three different groups of Events,
     * namely selected date, upcoming events and past events, the events will
     * be sorted by their starting time in ascending order, and suppose Events
     * share same starting time shorter events will appear first.
     */
    public void sort(LocalDate selectedDate) {
        requireNonNull(selectedDate);

        internalList.sort(new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                LocalDateTime today = LocalDate.now().atStartOfDay();

                //Priority 1: This section pushes ongoing events to top of list (with reference to selected date)
                if (DateTimeUtil.containsReferenceDate(o1, selectedDate)
                        && !DateTimeUtil.containsReferenceDate(o2, selectedDate)) {
                    return -1;
                } else if (!DateTimeUtil.containsReferenceDate(o1, selectedDate)
                        && DateTimeUtil.containsReferenceDate(o2, selectedDate)) {
                    return 1;
                } else if (DateTimeUtil.containsReferenceDate(o1, selectedDate)
                        && DateTimeUtil.containsReferenceDate(o2, selectedDate)) {
                    if (o1.getEventTime().getStart().isBefore(o2.getEventTime().getStart())) {
                        return -1;
                    } else if (o2.getEventTime().getStart().isBefore(o1.getEventTime().getStart())) {
                        return 1;
                    } else {
                        return o1.getEventDuration().getDuration().compareTo(o2.getEventDuration().getDuration());
                    }
                }
                //End of Priority 1

                //Priority 2: This section pushes events on selected date to top
                if (o1.getEventTime().getStart().toLocalDate().equals(selectedDate)
                        && !o2.getEventTime().getStart().toLocalDate().equals(selectedDate)) {
                    return -1;
                } else if (o2.getEventTime().getStart().toLocalDate().equals(selectedDate)
                        && !o1.getEventTime().getStart().toLocalDate().equals(selectedDate)) {
                    return 1;
                } else if (o1.getEventTime().getStart().toLocalDate().equals(selectedDate)
                        && o2.getEventTime().getStart().toLocalDate().equals(selectedDate)) {
                    if (o1.getEventTime().getStart().isBefore(o2.getEventTime().getStart())) {
                        return -1;
                    } else if (o2.getEventTime().getStart().isBefore(o1.getEventTime().getStart())) {
                        return 1;
                    } else {
                        return o1.getEventDuration().getDuration().compareTo(o2.getEventDuration().getDuration());
                    }
                }
                //End of Priority 2

                //Priority 3: This section pushes upcoming events above past events
                if (o1.getEventTime().getStart().isAfter(today)
                        && !o2.getEventTime().getStart().isAfter(today)) {
                    return -1;
                } else if (o2.getEventTime().getStart().isAfter(today)
                        && !o1.getEventTime().getStart().isAfter(today)) {
                    return 1;
                } else if (o1.getEventTime().getStart().isAfter(today)
                        && o2.getEventTime().getStart().isAfter(today)) {
                    if (o1.getEventTime().getStart().isBefore(o2.getEventTime().getStart())) {
                        return -1;
                    } else if (o2.getEventTime().getStart().isBefore(o1.getEventTime().getStart())) {
                        return 1;
                    } else {
                        return o1.getEventDuration().getDuration().compareTo(o2.getEventDuration().getDuration());
                    }
                }
                //End of Priority 3

                //Priority 4: This section handles the leftover past events
                if (o1.getEventTime().getStart().isBefore(o2.getEventTime().getStart())) {
                    return -1;
                } else if (o2.getEventTime().getStart().isBefore(o1.getEventTime().getStart())) {
                    return 1;
                } else {
                    return o1.getEventDuration().getDuration().compareTo(o2.getEventDuration().getDuration());
                }
                //End of Priority 4
            }
        });
    }

    public void setEvents(UniqueEventList replacement) {
        this.internalList.setAll(replacement.internalList);
        sort(LocalDate.now());
    }

    public void setEvents(List<? extends Event> events) throws DuplicateEventException {
        final UniqueEventList replacement = new UniqueEventList();
        for (final Event event : events) {
            replacement.add(new Event(event));
        }
        setEvents(replacement);

    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Event> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
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
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void sortPerson(Comparator<ReadOnlyPerson> sortType, boolean isDescending) throws EmptyListException {
        addressBook.sortPerson(sortType, isDescending);
    }

    @Override
    public void updateListOfPerson(ArrayList<ReadOnlyPerson> targets, ArrayList<ReadOnlyPerson> editedPersons)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(targets, editedPersons);

        addressBook.updateListOfPerson(targets, editedPersons);
        indicateAddressBookChanged();
    }

    @Override
    public void addEvent(ArrayList<ReadOnlyPerson> targets, ArrayList<ReadOnlyPerson> editedPersons, Event event)
            throws DuplicateEventException, DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(targets, editedPersons, event);

        addressBook.addEvent(targets, editedPersons, event);

        EventsCenter.getInstance().post(new ScheduleUpdateEvent(getEventList()));
        indicateAddressBookChanged();

    }

    @Override
    public void removeEvents(ArrayList<ReadOnlyPerson> targets, ArrayList<ReadOnlyPerson> editedPersons,
                             ArrayList<Event> toRemoveEvents)
            throws DuplicatePersonException, PersonNotFoundException, EventNotFoundException {
        requireAllNonNull(targets, editedPersons);

        addressBook.removeEvents(targets, editedPersons, toRemoveEvents);

        EventsCenter.getInstance().post(new ScheduleUpdateEvent(getEventList()));
        indicateAddressBookChanged();
    }

    @Override
    public void sortEvents(LocalDate date) {
        requireAllNonNull(date);
        addressBook.sortEvents(date);
    }

    @Override
    public ObservableList<Event> getEventList() {
        return FXCollections.unmodifiableObservableList(filteredEvents);

    }

    @Override
    public boolean hasEvenClashes(Event event) {
        requireNonNull(event);

        return addressBook.hasEventClashes(event);
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java

    /**
     * Handle event when Event in Event list is clicked.
     * <p>
     * Update {@code FilteredList<ReadOnlyPerson> filteredPersons} to show members of Event upon clicking on Event.
     *
     * @param event
     * @see EventListPanel#setEventHandlerForSelectionChangeEvent()
     */
    @Subscribe
    private void handleEventPanelSelectionChangedEvent(EventPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        updateFilteredPersonList(p ->
                event.getMemberAsArrayList().contains(p)
        );
    }

    /**
     * Handle event when date in CalenderView is clicked.
     * <p>
     * Update master UniqueEventList by running a sort with the given date as reference.
     * Comparator logic and sorting details is found in {@see UniqueEventList#sort(LocalDate)}
     *
     * @param event
     */
    @Subscribe
    private void handleCalendarSelectionChangedEvent(CalendarSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        sortEvents(event.getSelectedDate());
    }
```
###### /java/seedu/address/model/person/DateAdded.java
``` java
/**
 * Represents a Person's address in the address book.
 * Guarantees: Date is valid
 */
public class DateAdded {

    public static final String MESSAGE_DATEADDED_CONSTRAINTS =
            "DateAdded is in DD-MM-YYYY format, and it should not be blank";
    public static final String MESSAGE_DATE_PARSE_ERROR =
            "Error parsing from xml to Date object.";
    public static final String DATE_FORMAT = "dd/MM/yyyy hh:mm:ss";
    private static final SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

    public final Date dateAdded;


    public DateAdded() {
        dateAdded = new Date();
    }

    public DateAdded(String dateAddedString) throws IllegalValueException {
        requireNonNull(dateAddedString);

        try {
            dateAdded = format.parse(dateAddedString);
        } catch (ParseException e) {
            throw new DateParseException(MESSAGE_DATE_PARSE_ERROR);
        }

    }

    public Date getDateObject() {
        return dateAdded;
    }

    @Override
    public String toString() {
        return format.format(dateAdded);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateAdded // instanceof handles nulls
                && (this.dateAdded.getTime() == (((DateAdded) other).dateAdded.getTime()))); // state check
    }

    @Override
    public int hashCode() {
        return dateAdded.hashCode();
    }

}
```
###### /java/seedu/address/model/person/UniquePersonList.java
``` java
    /**
     * Sorts the list of persons.
     *
     * @throws EmptyListException if the list is empty.
     */
    public void sort(Comparator<ReadOnlyPerson> sortType, boolean isDescending) throws EmptyListException {
        requireNonNull(sortType);
        requireNonNull(isDescending);

        if (internalList.size() < 1) {
            throw new EmptyListException();
        }

        Collections.sort(internalList, sortType);

        if (isDescending) {
            Collections.reverse(internalList);
        }
    }
```
###### /java/seedu/address/storage/XmlAdaptedEvent.java
``` java
/**
 * JAXB-friendly adapted version of the Event.
 */
public class XmlAdaptedEvent {

    @XmlElement(required = true)
    private String eventName;

    @XmlElement(required = true)
    private String eventTime;

    @XmlElement(required = true)
    private String eventDuration;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEvent() {
    }

    /**
     * Converts a given Event into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedEvent
     */
    public XmlAdaptedEvent(Event source) {

        eventName = source.getEventName().fullName;
        eventTime = DateTimeUtil.parseLocalDateTimeToString(source.getEventTime().getStart());
        eventDuration = String.valueOf(source.getEventDuration().getDuration().toMinutes());

    }


    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Event toModelType() throws IllegalValueException {

        final EventName newName = new EventName(eventName);
        final EventTime newTime = new EventTime(DateTimeUtil.parseStringToLocalDateTime(eventTime),
                Duration.ofMinutes(Long.parseLong(eventDuration)));
        final EventDuration newDuration = new EventDuration(Duration.ofMinutes(Long.parseLong(eventDuration)));

        return new Event(new MemberList(), newName, newTime, newDuration);
    }

}
```
###### /java/seedu/address/storage/XmlSerializableAddressBook.java
``` java
    @Override
    public ObservableList<Event> getEventList() {
        final ObservableList<Event> events = this.events.stream().map(e -> {
            try {
                return e.toModelType();
            } catch (IllegalValueException ive) {
                ive.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(events);
    }
```
###### /java/seedu/address/ui/CalendarView.java
``` java

/**
 * An UI component that displays a clickable-Calendar.
 */
public class CalendarView extends UiPart<Region> {
    private static final String FXML = "CalendarView.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private AnchorPane box;

    private DatePicker datePicker;
    private ObservableList<Event> eventList;

    public CalendarView(ObservableList<Event> eventList) {
        super(FXML);

        this.eventList = eventList;

        initCalendar(this.eventList);
        initListener();

        registerAsAnEventHandler(this);
    }

    /**
     * Initialise the calendar and highlight dates with Event.
     *
     * @param masterEventList
     */
    private void initCalendar(ObservableList<Event> masterEventList) {

        box.getChildren().clear();
        datePicker = new DatePicker(LocalDate.now());

        HashMap<LocalDate, ArrayList<String>> eventsByDate = new HashMap<>();
        for (Event event : masterEventList) {
            LocalDate pointerDay = event.getEventTime().getStart().toLocalDate();
            LocalDate endDay = event.getEventTime().getEnd().toLocalDate().plusDays(1);

            do {
                ArrayList<String> eventsInPointerDay = eventsByDate.computeIfAbsent(pointerDay, k -> new ArrayList<>());
                eventsInPointerDay.add(event.getEventName().toString());

                pointerDay = pointerDay.plusDays(1);
            } while (!pointerDay.isEqual(endDay));

        }

        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);

        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (eventsByDate.containsKey(item)) {
                            setTooltip(new Tooltip(
                                    StringUtil.multiStringPrint(eventsByDate.get(item), "\n")));
                            if (!item.isEqual(LocalDate.now())) {
                                setStyle("-fx-background-color: #a7a7a7; -fx-text-fill: #ffffff;");

                            }
                        }
                    }
                };
            }
        };


        datePicker.setDayCellFactory(dayCellFactory);


        DatePickerContent calendarView = (DatePickerContent) datePickerSkin.getPopupContent();

        calendarView.minWidthProperty().setValue(box.minWidthProperty().getValue());

        AnchorPane.setTopAnchor(calendarView, 0.0);
        AnchorPane.setLeftAnchor(calendarView, 0.0);
        AnchorPane.setRightAnchor(calendarView, 1.0);
        AnchorPane.setBottomAnchor(calendarView, 1.0);

        box.getChildren().add(calendarView);

    }

    /**
     * Add listener to register users mouse click.
     */
    private void initListener() {

        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            LocalDate selectedDate = datePicker.getValue();
            logger.fine("Selection in calendar: '" + selectedDate + "'");

            raise(new CalendarSelectionChangedEvent(selectedDate));
        });


    }

    @Subscribe
    private void handleScheduleUpdateEvent(ScheduleUpdateEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        initCalendar(event.getEvents());
        initListener();
    }
}
```
###### /java/seedu/address/ui/EventCard.java
``` java

/**
 * An UI component that displays information of a {@code Person}.
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

    public final Event event;

    @FXML
    private HBox cardPane;
    @FXML
    private Label eventName;
    @FXML
    private Label id;
    @FXML
    private Label eventDateTime;
    @FXML
    private Label eventDuration;

    @FXML
    private Label eventStatus;
    @FXML
    private VBox members;

    public EventCard(Event event, int displayedIndex) {
        super(FXML);
        this.event = event;

        id.setText(displayedIndex + ". ");
        initMembers(event);
        bindListeners(event);
        registerAsAnEventHandler(this);

    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(Event event) {
        eventName.textProperty().bind(Bindings.convert(event.eventNameProperty()));
        eventDateTime.textProperty().bind(Bindings.convert(event.eventTimeProperty()));
        eventDuration.textProperty().bind(Bindings.convert(event.eventDurationProperty()));
        event.eventMemberListProperty().addListener((observable, oldValue, newValue) -> {
            members.getChildren().clear();
            event.getMemberList().asReadOnlyMemberList().forEach(member -> members.getChildren().add(
                    new Label(member.getName().toString())));
        });
        eventStatus.textProperty().bind(Bindings.convert(event.eventStatusProperty()));
        eventStatus.styleProperty().bind(Bindings.convert(event.eventStatusStyleProperty()));
    }

    private void initMembers(Event event) {
        event.getMemberList().asReadOnlyMemberList().forEach(member -> members.getChildren().add(
                new Label(member.getName().toString())));
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

    /**
     * Handle event when date in CalenderView is clicked.
     * <p>
     * Update master UniqueEventList by running a sort with the given date as reference.
     * Comparator logic and sorting details is found in {@see UniqueEventList#sort(LocalDate)}
     *
     * @param event
     */
    @Subscribe
    private void handleCalendarSelectionChangedEvent(CalendarSelectionChangedEvent event) {

        this.event.updateEventStatusSelection(event.getSelectedDate());

    }
}
```
###### /java/seedu/address/ui/EventListPanel.java
``` java

/**
 * Panel containing the list of events.
 */
public class EventListPanel extends UiPart<Region> {
    private static final String FXML = "EventListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EventListPanel.class);
    private final ObservableList<Event> eventList;
    @FXML
    private ListView<EventCard> eventListView;

    public EventListPanel(ObservableList<Event> eventList) {
        super(FXML);
        this.eventList = eventList;

        setConnections(eventList);
        setEventHandlerForSelectionChangeEvent();
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Event> eventList) {
        ObservableList<EventCard> mappedList = EasyBind.map(
                eventList, (event) -> new EventCard(event, eventList.indexOf(event) + 1));
        eventListView.setItems(mappedList);
        eventListView.setCellFactory(listView -> new EventListViewCell());
    }

    private void setEventHandlerForSelectionChangeEvent() {
        eventListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in event list panel changed to : '" + newValue + "'");
                        raise(new EventPanelSelectionChangedEvent(newValue.event));
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
