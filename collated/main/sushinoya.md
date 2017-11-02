# sushinoya
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

        if (targetIndex1.getZeroBased() >= lastShownList.size()
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
