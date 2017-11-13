# YuchenHe98
###### \java\seedu\address\commons\core\PossibleDays.java
``` java
/**
 * Possible Days.
 */
public class PossibleDays {

    public static final int[] DAYS = { 1, 2, 3, 4, 5, 6, 7 };

    public static final int DAY_COEFFICIENT = 10000;

    public static final String[] DAY_TIME = { "Monday", "Tuesday", "Wednesday",
        "Thursday", "Friday", "Saturday", "Sunday" };
}
```
###### \java\seedu\address\commons\core\PossibleTimes.java
``` java
/**
 * Possible time integer values..
 */
public class PossibleTimes {
    public static final int[] TIMES = {
        600, 630, 700, 730, 800, 830, 900, 930, 1000, 1030, 1100, 1130, 1200, 1230, 1300, 1330, 1400, 1430, 1500, 1530,
        1600, 1630, 1700, 1730, 1800, 1830, 1900, 1930, 2000, 2030, 2100, 2130, 2200, 2230, 2300, 2330
    };
}
```
###### \java\seedu\address\commons\events\ui\LocateCommandEvent.java
``` java
/**
 * An event requesting to view the address of the person specified on Google Map.
 */
public class LocateCommandEvent extends BaseEvent {

    private ReadOnlyPerson person;

    public LocateCommandEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyPerson getPerson() {
        return person;
    }
}
```
###### \java\seedu\address\logic\commands\AddScheduleCommand.java
``` java
/**
 * Add a busy time span for a person identified using it's last displayed index from the address book.
 */
public class AddScheduleCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addSchedule";
    public static final String COMMAND_ALIAS = "as";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Add a specific time slot when a person is free.\n"
            + "Parameters: INDEX (must be a positive integer); Day(From Monday to Saturday); start time "
            + "(Should be expressed in standard 24 hours time, no more accurate than 30 minutes and no earlier "
            + "than 0600 and no later than 2330\n"
            + "Example: "
            + COMMAND_WORD + " 1 "
            + PREFIX_DAY + "Monday "
            + PREFIX_START_TIME + "0700 "
            + PREFIX_END_TIME + "1430 ";

    public static final String MESSAGE_ADD_SCHEDULE_PERSON_SUCCESS = "Free time slot successfully added";

    private final Index targetIndex;

    private Day day;
    private Time startTime;
    private Time endTime;
    private TreeSet<Integer> timeToAdd;

    public AddScheduleCommand(Index targetIndex, Day day, Time startTime, Time endTime)
            throws IllegalValueException {
        this.targetIndex = targetIndex;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        Slot slot = new Slot(day, startTime, endTime);
        timeToAdd = slot.getBusyTime();
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToAddSchedule = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.addScheduleToPerson(targetIndex.getZeroBased(), timeToAdd);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_ADD_SCHEDULE_PERSON_SUCCESS, personToAddSchedule));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddScheduleCommand // instanceof handles nulls
                && this.targetIndex.equals(((AddScheduleCommand) other).targetIndex)
                && this.day.equals(((AddScheduleCommand) other).day)
                && this.startTime.equals(((AddScheduleCommand) other).startTime)
                && this.endTime.equals(((AddScheduleCommand) other).endTime)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\ArrangeCommand.java
``` java
/**
 * Selects a person identified using it's last displayed index from the address book.
 */
public class ArrangeCommand extends Command {

    public static final String COMMAND_WORD = "arrange";
    public static final String COMMAND_ALIAS = "ar";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": arrange meeting time for a list of indices of persons who appeared in the last list \n"
            + "Parameters: Indices separated by spaces (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 2 5 7 8";

    public static final String MESSAGE_ARRANGE_PERSON_SUCCESS = "meeting successfully arranged!";

    private final Index[] listOfIndex;

    public ArrangeCommand(int[] listOfIndex) {
        this.listOfIndex = new Index[listOfIndex.length];
        for (int i = 0; i < listOfIndex.length; i++) {
            this.listOfIndex[i] = Index.fromOneBased(listOfIndex[i]);
        }
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        for (int i = 0; i < listOfIndex.length; i++) {
            if (listOfIndex[i].getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }
        TreeSet<Integer>[] timeSetArray = Schedule.splitScheduleToDays(model.generateMeetingTime(listOfIndex));
        ScheduleTable.generates(timeSetArray);
        String toShow = scheduleInfo();
        return new CommandResult(String.format(MESSAGE_ARRANGE_PERSON_SUCCESS) + toShow);

    }

    public int[] getSortedZeroBasedIndex() {
        int[] thisIndexList = new int[listOfIndex.length];
        for (int i = 0; i < listOfIndex.length; i++) {
            thisIndexList[i] = listOfIndex[i].getZeroBased();
        }
        Arrays.sort(thisIndexList);
        return thisIndexList;
    }

    /**
     * Returns the info of schedule to be shown to the user later.
     */
    public String scheduleInfo() {
        TreeSet<Integer>[] timeSetArray = Schedule.splitScheduleToDays(model.generateMeetingTime(listOfIndex));
        String toShow = "\nAll common free time: \n";
        for (int i = 0; i < timeSetArray.length; i++) {
            toShow = toShow + PossibleDays.DAY_TIME[i] + ":\n";
            for (Integer time : timeSetArray[i]) {
                toShow = toShow + Time.getTimeToString(time) + "--"
                        + Time.getTimeToString(Time.increaseTimeInteger(time)) + " ";
            }
            toShow += "\n";
        }
        return toShow;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ArrangeCommand // instanceof handles nulls
                && Arrays.equals(this.getSortedZeroBasedIndex(), ((ArrangeCommand) other).getSortedZeroBasedIndex()));
        // state check
    }
}
```
###### \java\seedu\address\logic\commands\ClearScheduleCommand.java
``` java
/**
 * Clear a time span for a person identified using it's last displayed index from the address book.
 */
public class ClearScheduleCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clearSchedule";
    public static final String COMMAND_ALIAS = "cs";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clear a specific time slot to show that this person is no longer free at this time period.\n"
            + "Parameters: INDEX (must be a positive integer); Day(From Monday to Saturday); start time "
            + "(Should be expressed in standard 24 hours time, no more accurate than 30 minutes and no earlier "
            + "than 0600 and no later than 2330\n"
            + "Example: "
            + COMMAND_WORD + " 1"
            + PREFIX_DAY + "Monday"
            + PREFIX_START_TIME + "0700"
            + PREFIX_END_TIME + "1430";

    public static final String MESSAGE_CLEAR_SCHEDULE_PERSON_SUCCESS = "Schedule successfully cleared";

    private final Index targetIndex;

    private Day day;
    private Time startTime;
    private Time endTime;
    private TreeSet<Integer> timeToClear;

    public ClearScheduleCommand(Index targetIndex, Day day, Time startTime, Time endTime)
            throws IllegalValueException {
        this.targetIndex = targetIndex;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        Slot slot = new Slot(day, startTime, endTime);
        timeToClear = slot.getBusyTime();
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToClearSchedule = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.clearScheduleForPerson(targetIndex.getZeroBased(), timeToClear);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_CLEAR_SCHEDULE_PERSON_SUCCESS, personToClearSchedule));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClearScheduleCommand // instanceof handles nulls
                && this.targetIndex.equals(((ClearScheduleCommand) other).targetIndex)
                && this.day.equals(((ClearScheduleCommand) other).day)
                && this.startTime.equals(((ClearScheduleCommand) other).startTime)
                && this.endTime.equals(((ClearScheduleCommand) other).endTime)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\FindByAddressCommand.java
``` java
/**
 * Finds and lists all persons in address book whose address contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindByAddressCommand extends Command {

    public static final String COMMAND_WORD = "findByAddress";
    public static final String COMMAND_ALIAS = "fba";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose addresses contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + "Ang Mo Kio";

    private final AddressContainsKeywordsPredicate predicate;

    public FindByAddressCommand(AddressContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindByAddressCommand // instanceof handles nulls
                && this.predicate.equals(((FindByAddressCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\LocateCommand.java
``` java
/**
 * Locate a person's address on Google Map using it's last displayed index from the address book.
 */
public class LocateCommand extends Command {

    public static final String COMMAND_WORD = "locate";
    public static final String COMMAND_ALIAS = "lct";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Locate the person specified on google map.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_LOCATE_PERSON_SUCCESS = "Showing the address of person ";

    private final Index targetIndex;

    public LocateCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToSearchAddress = lastShownList.get(targetIndex.getZeroBased());

        EventsCenter.getInstance().post(new LocateCommandEvent(personToSearchAddress));
        return new CommandResult(String.format(MESSAGE_LOCATE_PERSON_SUCCESS, personToSearchAddress));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LocateCommand // instanceof handles nulls
                && this.targetIndex.equals(((LocateCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\VisualizeCommand.java
``` java
/**
 * Selects a person identified using it's last displayed index from the address book.
 */
public class VisualizeCommand extends Command {

    public static final String COMMAND_WORD = "visualize";
    public static final String COMMAND_ALIAS = "v";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Visualizes the person's schedule identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VISUALIZE_PERSON_SUCCESS = "Visualized Success! ";

    private final Index targetIndex;

    public VisualizeCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Schedule scheduleToBeShown =
                model.getAddressBook().getPersonList().get(targetIndex.getZeroBased()).getSchedule();
        ScheduleTable.generates(scheduleToBeShown);
        String toShow = scheduleInfo();
        return new CommandResult(String.format(MESSAGE_VISUALIZE_PERSON_SUCCESS + targetIndex.getOneBased() + toShow));

    }
    /**
     * Show schedule info as a message.
     */
    public String scheduleInfo() {

        Schedule scheduleToBeShown =
                model.getAddressBook().getPersonList().get(targetIndex.getZeroBased()).getSchedule();
        TreeSet<Integer>[] timeSetArray = scheduleToBeShown.splitScheduleToDays();
        String toShow = "\nAll free time: \n";
        for (int i = 0; i < timeSetArray.length; i++) {
            toShow = toShow + PossibleDays.DAY_TIME[i] + ":\n";
            for (Integer time : timeSetArray[i]) {
                toShow = toShow + Time.getTimeToString(time) + "--"
                        + Time.getTimeToString(Time.increaseTimeInteger(time)) + " ";
            }
            toShow += "\n";
        }
        return toShow;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VisualizeCommand // instanceof handles nulls
                && this.targetIndex.equals(((VisualizeCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\AddScheduleCommandParser.java
``` java
/**
 *
 * Parses input arguments and creates a new AddScheduleCommand object
 */
public class AddScheduleCommandParser implements Parser<AddScheduleCommand> {



    /**
     * Parses the given {@code String} of arguments in the context of the AddScheduleCommand
     * and returns an AddScheduleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddScheduleCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DAY, PREFIX_START_TIME,
                PREFIX_END_TIME);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddScheduleCommand.MESSAGE_USAGE));
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_DAY, PREFIX_START_TIME, PREFIX_END_TIME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddScheduleCommand.MESSAGE_USAGE));
        }

        try {
            Day day = ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY)).get();
            Time startTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_START_TIME)).get();
            Time endTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_END_TIME)).get();
            return new AddScheduleCommand(index, day, startTime, endTime);
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
###### \java\seedu\address\logic\parser\ArrangeCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ChangePasswordCommand object
 */
public class ArrangeCommandParser implements Parser<ArrangeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ArrangeCommand
     * and returns an ArrangeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ArrangeCommand parse(String args) throws ParseException {

        try {
            String[] listOfPerson = args.trim().split("\\s+");

            int[] listOfIndex = new int[listOfPerson.length];
            for (int i = 0; i < listOfPerson.length; i++) {
                try {
                    listOfIndex[i] = Integer.parseInt(listOfPerson[i]);
                } catch (NumberFormatException e) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT
                            + ArrangeCommand.MESSAGE_USAGE));
                }
                if (listOfIndex[i] <= 0) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT
                            + ArrangeCommand.MESSAGE_USAGE));
                }
            }
            return new ArrangeCommand(listOfIndex);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ChangePasswordCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\ClearScheduleCommandParser.java
``` java
/**
 *
 * Parses input arguments and creates a new ClearScheduleCommand object
 */
public class ClearScheduleCommandParser implements Parser<ClearScheduleCommand> {



    /**
     * Parses the given {@code String} of arguments in the context of the ClearScheduleCommand
     * and returns an ClearScheduleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ClearScheduleCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DAY, PREFIX_START_TIME,
                PREFIX_END_TIME);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearScheduleCommand.MESSAGE_USAGE));
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_DAY, PREFIX_START_TIME, PREFIX_END_TIME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearScheduleCommand.MESSAGE_USAGE));
        }

        try {
            Day day = ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY)).get();
            Time startTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_START_TIME)).get();
            Time endTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_END_TIME)).get();
            return new ClearScheduleCommand(index, day, startTime, endTime);
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
###### \java\seedu\address\logic\parser\FindByAddressCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindByAddressCommand object
 */
public class FindByAddressCommandParser implements Parser<FindByAddressCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindByAddressCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindByAddressCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindByAddressCommand(new AddressContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
```
###### \java\seedu\address\logic\parser\LocateCommandParser.java
``` java
/**
 * Parses input arguments and creates a new LocateCommand object
 */
public class LocateCommandParser implements Parser<LocateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LocateCommand
     * and returns an LocateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LocateCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new LocateCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LocateCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> Day} into an {@code Optional<Day>} if {@code day} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Day> parseDay(Optional<String> day) throws IllegalValueException {
        requireNonNull(day);
        return day.isPresent() ? Optional.of(new Day(day.get())) : Optional.empty();
    }

```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> Time} into an {@code Optional<Time>} if {@code time} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Time> parseTime(Optional<String> time) throws IllegalValueException {
        requireNonNull(time);
        return time.isPresent() ? Optional.of(new Time(time.get())) : Optional.empty();
    }

    /**
     * Parses {@code type} into an boolean value and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified keyword is invalid.
     */
    public static boolean parseType(String type) throws IllegalValueException {
        String trimmedType = type.trim();
        if (trimmedType.equals("AND")) {
            return true;
        } else if (trimmedType.equals("OR")) {
            return false;
        } else {
            throw new IllegalValueException(MESSAGE_INVALID_TYPE);
        }
    }
}
```
###### \java\seedu\address\logic\parser\VisualizeCommandParser.java
``` java
/**
 * Parses input arguments and creates a new VisualizeCommand object
 */
public class VisualizeCommandParser implements Parser<VisualizeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the VisualizeCommand
     * and returns an VisualizeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public VisualizeCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new VisualizeCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, VisualizeCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\model\Model.java
``` java
    void addScheduleToPerson(Integer index, TreeSet<Integer> schedule) throws PersonNotFoundException;

```
###### \java\seedu\address\model\Model.java
``` java
    void clearScheduleForPerson(Integer index, TreeSet<Integer> schedule) throws PersonNotFoundException;
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void addScheduleToPerson(Integer index, TreeSet<Integer> schedule) throws PersonNotFoundException {
        addressBook.addScheduleToPerson(index, schedule);
        indicateAddressBookChanged();
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void clearScheduleForPerson(Integer index, TreeSet<Integer> schedule) throws PersonNotFoundException {
        addressBook.clearScheduleForPerson(index, schedule);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Generate possible meeting time slots based on a list of indices.
     */
    @Override
    public TreeSet<Integer> generateMeetingTime(Index[] listOfIndex) {
        return addressBook.generateMeetingTime(listOfIndex);
    }
}
```
###### \java\seedu\address\model\person\AddressContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Address} matches any of the keywords given.
 */
public class AddressContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public AddressContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((AddressContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\Person.java
``` java
    public Person(Name name, Phone phone, Email email, Address address, Mrt mrt, Set<Tag> tags, Schedule schedule) {
        requireAllNonNull(name, phone, email, address, tags, schedule);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.mrt = new SimpleObjectProperty<>(mrt);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        this.schedule = new SimpleObjectProperty<>(schedule);
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    @Override
    public Schedule getSchedule() {
        return schedule.get();
    }

```
###### \java\seedu\address\model\person\Person.java
``` java
    public ObjectProperty<Schedule> scheduleProperty() {
        return schedule;
    }

```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Create an empty schedule object
     */
    public void initiateSchedule() {
        Schedule schedule = new Schedule();
        this.schedule = new SimpleObjectProperty<>(schedule);
    }

```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Set the person's schedule based on a given schedule.
     */
    public void setSchedule(Schedule schedule) {
        this.schedule.set(schedule);
    }

```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Add a time span to a person's schedule to indicate that he is free at this time.
     */
    public void addSpanToSchedule(TreeSet<Integer> span) {
        for (Integer startTime : span) {
            getSchedule().addTime(startTime);
        }
    }

```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     *Clear a time span to a person's schedule to indicate that he is busy at this time.
     */
    public void clearSpanForSchedule(TreeSet<Integer> span) {
        for (Integer startTime : span) {
            getSchedule().clearTime(startTime);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyPerson) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
}
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Add free time slot based on the index.
     */
    public void addSchedule(Integer toAdd, TreeSet<Integer> timeSpan) {
        internalList.get(toAdd).addSpanToSchedule(timeSpan);
    }

```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Clear free time slot based on the index.
     */
    public void clearSchedule(Integer toClear, TreeSet<Integer> timeSpan) {
        internalList.get(toClear).clearSpanForSchedule(timeSpan);
    }
}
```
###### \java\seedu\address\model\schedule\Day.java
``` java
/**
 * The object representing a day, from Monday to Sunday in a person's schedule.
 */
public class Day {

    private final Integer day;

    public Day(String dayToAdd) throws IllegalValueException {
        if ("Monday".equals(dayToAdd)) {
            day = 1;
        } else if ("Tuesday".equals(dayToAdd)) {
            day = 2;
        } else if ("Wednesday".equals(dayToAdd)) {
            day = 3;
        } else if ("Thursday".equals(dayToAdd)) {
            day = 4;
        } else if ("Friday".equals(dayToAdd)) {
            day = 5;
        } else if ("Saturday".equals(dayToAdd)) {
            day = 6;
        } else if ("Sunday".equals(dayToAdd)) {
            day = 7;
        } else {
            throw new IllegalValueException("Not a proper day");
        }
    }

    public boolean isValid() {
        return day >= 1 && day <= 7;
    }

    /**
     * Returns if the string represents a valid day.
     */
    public static boolean isValidDay(String test) {
        return "Monday".equals(test)
                || "Tuesday".equals(test)
                || "Wednesday".equals(test)
                || "Thursday".equals(test)
                || "Friday".equals(test)
                || "Saturday".equals(test)
                || "Sunday".equals(test);
    }

    public Integer getDay() {
        return day;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Day) // instanceof handles nulls
                && (this.day == ((Day) other).day); // state check
    }
}
```
###### \java\seedu\address\model\schedule\Schedule.java
``` java
/**
 * Represents a Person's schedule in the address book.
 */
public class Schedule {

    private TreeSet<Integer> timeSet;

    /**
     * Validates given schedule
     *
     * @throws IllegalValueException if given timeSet is invalid.
     */
    public Schedule() {
        timeSet = new TreeSet<Integer>();
    }
    public Schedule(TreeSet<Integer> timeSet) throws IllegalValueException {
        requireNonNull(timeSet);
        if (!isValidTimeSet(timeSet)) {
            throw new IllegalValueException("Should not use this constructor as invalid values are passed. ");
        }
        this.timeSet = timeSet;
    }

    /**
     * Add a slot of time with the unit of 30min based on the startTime.
     */
    public void addTime(Integer startTime) {
        if (!timeSet.contains(startTime)) {
            timeSet.add(startTime);
        }
    }

    /**
     * Clear a slot of time with the unit of 30min based on the startTime.
     */
    public void clearTime(Integer startTime) {
        if (timeSet.contains(startTime)) {
            timeSet.remove(startTime);
        }
    }

    @Override
    public String toString() {
        return timeSet.toString();
    }

    public TreeSet<Integer> getTimeSet() {
        return timeSet;
    }

    public boolean containsTimeNumber(Integer timeNumber) {
        return timeSet.contains(timeNumber);
    }

    @Override
    public int hashCode() {
        return timeSet.hashCode();
    }

    /**
     * Split the time set into seven sets according to the days.
     */
    public TreeSet<Integer>[] splitScheduleToDays() {
        TreeSet<Integer>[] timeSetArray = new TreeSet[7];
        for (int i = 0; i < 7; i++) {
            timeSetArray[i] = new TreeSet<>();
        }
        for (Integer time : this.timeSet) {
            int day = (time - time % PossibleDays.DAY_COEFFICIENT) / PossibleDays.DAY_COEFFICIENT;
            timeSetArray[day - 1].add(time % PossibleDays.DAY_COEFFICIENT);
        }
        return timeSetArray;
    }

    /**
     * Split the time set into seven sets according to the days given a time set.
     */
    public static TreeSet<Integer>[] splitScheduleToDays(TreeSet<Integer> toBeSplitted) {
        TreeSet<Integer>[] timeSetArray = new TreeSet[7];
        for (int i = 0; i < 7; i++) {
            timeSetArray[i] = new TreeSet<>();
        }
        for (Integer time : toBeSplitted) {
            int day = (time - time % PossibleDays.DAY_COEFFICIENT) / PossibleDays.DAY_COEFFICIENT;
            timeSetArray[day - 1].add(time % PossibleDays.DAY_COEFFICIENT);
        }
        return timeSetArray;
    }

    /**
     * Returns if the set input containing day and time info is a valid set.
     */
    public static boolean isValidTimeSet(TreeSet<Integer> setOfTime) {
        for (Integer timeNumber : setOfTime) {
            if (!isValidTimeNumber(timeNumber)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns if the integer input containing day and time info is a valid schedule time.
     */
    private static boolean isValidTimeNumber(Integer timeNumber) {
        Integer temp = timeNumber;
        temp -= temp % PossibleDays.DAY_COEFFICIENT;
        if (temp < 1 * PossibleDays.DAY_COEFFICIENT || temp > 7 * PossibleDays.DAY_COEFFICIENT) {
            return false;
        }
        Integer timeInDay = timeNumber % PossibleDays.DAY_COEFFICIENT;
        String timeString;
        if (timeInDay < 1000) {
            timeString = "0" + timeInDay;
        } else {
            timeString = timeInDay + "";
        }
        if (Time.isValidTime(timeString)) {
            return true;
        } else {
            return false;
        }
    }
}
```
###### \java\seedu\address\model\schedule\Slot.java
``` java
/**
 * The object representing a slot representing a span of time when a person is busy.
 */
public class Slot {

    public final int dayCoefficent = 10000;

    private TreeSet<Integer> busyTime;

    public Slot(Day day, Time start, Time end) throws IllegalValueException {
        if (!start.isValid() || !end.isValid() || !day.isValid()) {
            throw new IllegalValueException("Time given is not valid!");
        }

        busyTime = new TreeSet<>();
        int timeNumber;
        for (timeNumber = start.getTime(); timeNumber < end.getTime(); timeNumber += 10) {
            if (timeNumber % 100 != 30 && timeNumber % 100 != 0) {
                continue;
            } else {
                busyTime.add(timeNumber + day.getDay() * dayCoefficent);
            }
        }
    }

    /**
     * Returns if the time input startTime is earlier than endTime.
     */
    public static boolean isValidSlot (Day day, Time startTime, Time endTime) {
        if (startTime.getTime() >= endTime.getTime()) {
            return false;
        }
        return true;
    }
    public TreeSet<Integer> getBusyTime() {
        return busyTime;
    }
}
```
###### \java\seedu\address\model\schedule\Time.java
``` java
/**
 * The object representing the time of the start of a 30-minute-span when a person is free.
 */
public class Time {

    private final Integer earliestTime = 600;
    private final Integer latestTime = 2330;

    private Integer time;

    public Time(String time) throws IllegalValueException {
        this.time = Integer.parseInt(time);
        if (!isValid()) {
            throw new IllegalValueException("Not a proper time form");
        }
    }


    /**
     * Returns if the time input is a valid schedule time.
     */
    public boolean isValid() {
        //As the string representing the time is no more accurate than 30 min, the ast two digits must be 30 or 00.
        if (time % 100 != 0 && time % 100 != 30) {
            return false;
        }
        return (this.time >= earliestTime && this.time <= latestTime);
    }

    public Integer getTime() {
        return time;
    }

    /**
     * Returns if the time input is a valid schedule time.
     */
    public static boolean isValidTime(String test) {
        if (test.length() != 4) {
            return false;
        }
        char[] toTest = test.toCharArray();
        for (int i = 0; i < toTest.length; i++) {
            if (!Character.isDigit(toTest[i])) {
                return false;
            }
        }
        int timeNumber = Integer.parseInt(test);
        if (timeNumber > 2330 || timeNumber < 600) {
            return false;
        }
        if (timeNumber % 100 != 0 && timeNumber % 100 != 30) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time) // instanceof handles nulls
                && (this.time.equals(((Time) other).time)); // state check
    }

    public static String getTimeToString(Integer time) {
        String toShow;
        if (time < 1000) {
            toShow = "0" + time;
        } else {
            toShow = "" + time;
        }
        return toShow;
    }

    /**
     * Next time integer. This method is only to be used in visualizing and arraging where exceptions are already
     * thrown so there is no need to check the format.
     */
    public static Integer increaseTimeInteger(Integer timeInteger) {
        if (timeInteger % 100 == 30) {
            int newTime = timeInteger + 70;
            return newTime;
        } else {
            int newTime = timeInteger + 30;
            return newTime;
        }
    }
}
```
###### \java\seedu\address\ui\MapPanel.java
``` java
    private void loadLocationPage(ReadOnlyPerson person) {
        loadPage(GOOGLE_MAP_URL_PREFIX + person.getAddress().value.replaceAll(" ", "+"));
    }
```
###### \java\seedu\address\ui\MapPanel.java
``` java
    @Subscribe
    private void handleLocateCommandEvent(LocateCommandEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadLocationPage(event.getPerson());
    }
```
###### \java\seedu\address\ui\ScheduleTable.java
``` java
/**
 * A ui used in ArrangeCommand and VisualizeCommand to show the schedule available.
 */
public class ScheduleTable extends JFrame {

    public ScheduleTable(Schedule schedule) {
        TreeSet[] timeSetArray = schedule.splitScheduleToDays();
        construct(timeSetArray);
    }

    public ScheduleTable(TreeSet[] timeSetArray) {
        construct(timeSetArray);
    }

    /**
     * Constructs the timetable when given the array of timeSet representing days from Monday to Sunday.
     */
    private void construct(TreeSet[] timeSetArray) {
        String[] columnNames = {"", "Monday", "Tuesday" , "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
        Object[][] data = new Object[36][8];
        for (int s = 0; s < 36; s++) {
            for (int t = 0; t < 8; t++) {
                data[s][t] = "";
            }
        }
        for (int i = 0; i < PossibleTimes.TIMES.length; i++) {
            data[i][0] = Time.getTimeToString(PossibleTimes.TIMES[i])
                    + "--" + Time.increaseTimeInteger(PossibleTimes.TIMES[i]);
        }
        for (int j = 1; j <= timeSetArray.length; j++) {
            for (int k = 0; k < PossibleTimes.TIMES.length; k++) {
                if (timeSetArray[j - 1].contains(PossibleTimes.TIMES[k])) {
                    data[k][j] = "AVAILABLE";
                } else {
                    data[k][j] = "N";
                }
            }
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model) {
            //  Returning the Class of each column will allow different
            //  renderers to be used based on Class
            public Class getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };
        table.setShowHorizontalLines(true);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());

        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);

    }

    /**
     * Launch the ui based on schedule.
     */
    public static void generates(Schedule schedule) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ScheduleTable frame = new ScheduleTable(schedule);
        frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Launch the ui based on array of time set.
     */
    public static void generates(TreeSet[] timeSetArray) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ScheduleTable frame = new ScheduleTable(timeSetArray);
        frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
```
