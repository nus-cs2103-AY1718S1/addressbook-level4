# alexanderleegs
###### \java\seedu\address\logic\commands\AddTagCommand.java
``` java
/**
 * Adds a tag to an existing person in the address book.
 */
public class AddTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addtag";
    public static final String COMMAND_ALIAS = "at";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tag to the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "TAG NAME (one alphanumeric tag only)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "friends";

    public static final String MESSAGE_ADD_TAG_SUCCESS = "Added Tag: %1$s";
    public static final String MESSAGE_DUPLICATE_TAG = "This person already has this tag.";

    private final Index index;
    private final Tag newTag;

    /**
     * @param index of the person in the filtered person list to edit
     * @param newTag to be added to the person
     */
    public AddTagCommand(Index index, Tag newTag) {
        requireNonNull(index);

        this.index = index;
        this.newTag = newTag;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());

        Set<Tag> oldTags = new HashSet<Tag>(personToEdit.getTags());
        if (oldTags.contains(newTag)) {
            throw new CommandException(MESSAGE_DUPLICATE_TAG);
        }
        Person editedPerson = new Person(personToEdit);
        oldTags.add(newTag);
        editedPerson.setTags(oldTags);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("Not creating a new person");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_ADD_TAG_SUCCESS, newTag.tagName));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTagCommand)) {
            return false;
        }

        // state check
        AddTagCommand toCompare = (AddTagCommand) other;
        return index.equals(toCompare.index)
                && newTag.equals(toCompare.newTag);
    }
}
```
###### \java\seedu\address\logic\commands\DeleteTagCommand.java
``` java
/**
 * Deletes a tag identified using its name from the address book.
 */
public class DeleteTagCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "deletetag";
    public static final String COMMAND_ALIAS = "dt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes a given tag from a specified person.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "TAG NAME (one alphanumeric tag only)\n"
            + "Example: " + COMMAND_WORD + " 1" + " friends";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tag: %1$s";
    public static final String MESSAGE_NO_TAG = "This person does not have this tag.";

    private final Tag targetTag;
    private Index index = null;

    public DeleteTagCommand(Index index, Tag targetTag) {
        requireNonNull(index);

        this.index = index;
        this.targetTag = targetTag;
    }

    public DeleteTagCommand(Tag targetTag) {
        this.targetTag = targetTag;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        if (index == null) {
            try {
                model.deleteTag(targetTag);
            } catch (DuplicatePersonException | PersonNotFoundException ex) {
                throw new AssertionError("The target person cannot be missing");
            } catch (TagNotFoundException tnfe) {
                throw new CommandException(Messages.MESSAGE_INVALID_TAG_DISPLAYED);
            }
        } else {
            List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());

            Set<Tag> oldTags = new HashSet<Tag>(personToEdit.getTags());
            if (!(oldTags.contains(targetTag))) {
                throw new CommandException(MESSAGE_NO_TAG);
            }
            Person editedPerson = new Person(personToEdit);
            oldTags.remove(targetTag);
            editedPerson.setTags(oldTags);

            try {
                model.updatePerson(personToEdit, editedPerson);
            } catch (DuplicatePersonException dpe) {
                throw new AssertionError("Not creating a new person");
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, targetTag.tagName));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteTagCommand)) {
            return false;
        }

        // state check
        DeleteTagCommand toCompare = (DeleteTagCommand) other;
        return ((index == null && toCompare.index == null)
                || (index != null && toCompare.index != null && index.equals(toCompare.index)))
                && targetTag.equals(toCompare.targetTag);
    }
}
```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
/**
 * Sorts the list of contacts in the address book.
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the list of contacts "
            + "by the field specified and displays them as a list. "
            + "Parameters: FIELD\n"
            + "Example: " + COMMAND_WORD + " tag";

    public static final String MESSAGE_SORT_PERSON_SUCCESS = "List sorted successfully!";
    public static final String MESSAGE_INVALID_FIELD = "Field provided is invalid!";

    private final String field;

    public SortCommand(String field) {
        this.field = field.toLowerCase();
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        switch (field) {
        case "name":
        case "phone":
        case "email":
        case "address":
        case "tag":
        case "meeting":
            model.sort(field);
            break;
        default:
            throw new CommandException(MESSAGE_INVALID_FIELD);
        }
        return new CommandResult(MESSAGE_SORT_PERSON_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortCommand)) {
            return false;
        }

        // state check
        SortCommand s = (SortCommand) other;
        return field.equals(s.field);
    }
}

```
###### \java\seedu\address\logic\parser\AddTagCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddTagCommand object
 */
public class AddTagCommandParser implements Parser<AddTagCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddTagCommand
     * and returns an AddTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        String[] parts = trimmedArgs.split("\\s+", 2);
        Index index;
        if (parts.length < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }
        try {
            index = ParserUtil.parseIndex(parts[0]);
            Tag tag = new Tag(parts[1]);
            return new AddTagCommand(index, tag);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\DeleteTagCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteTagCommand object
 */
public class DeleteTagCommandParser implements Parser<DeleteTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTagCommand
     * and returns an DeleteTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTagCommand parse(String args) throws ParseException {
        if (args == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
        }
        String trimmedArgs = args.trim();
        String[] parts = trimmedArgs.split("\\s+", 2);
        Index index;
        if (parts.length < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
        }
        if (parts[0].toLowerCase().equals("all")) {
            try {
                Tag tag = new Tag(parts[1]);
                return new DeleteTagCommand(tag);
            } catch (IllegalValueException ive) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
            }
        } else {
            try {
                index = ParserUtil.parseIndex(parts[0]);
                Tag tag = new Tag(parts[1]);
                return new DeleteTagCommand(index, tag);
            } catch (IllegalValueException ive) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
            }
        }
    }
}
```
###### \java\seedu\address\logic\parser\SortCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns an FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        return new SortCommand(trimmedArgs);
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Ensures that every meeting in this person:
     *  - exists in the master list {@link #meetings}
     *  - points to a Meeting object in the master list
     */
    private void syncMasterMeetingListWith(Person person) {
        final UniqueMeetingList personMeetings = new UniqueMeetingList(person.getMeetings());
        meetings.mergeFrom(personMeetings);

        // Create map with values = meeting object references in the master list
        // used for checking person meeting references
        final Map<Meeting, Meeting> masterMeetingObjects = new HashMap<>();
        meetings.forEach(meeting -> masterMeetingObjects.put(meeting, meeting));

        // Rebuild the list of person meetings to point to the relevant meetings in the master tag list.
        final Set<Meeting> correctMeetingReferences = new HashSet<>();
        personMeetings.forEach(meeting -> correctMeetingReferences.add(masterMeetingObjects.get(meeting)));
        person.setMeetings(correctMeetingReferences);
    }

    /**
     * Ensures that every meeting in these persons:
     *  - exists in the master list {@link #meetings}
     *  - points to a Meeting object in the master list
     *  @see #syncMasterMeetingListWith(Person)
     */
    private void syncMasterMeetingListWith(UniquePersonList persons) {
        persons.forEach(this::syncMasterMeetingListWith);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void sort(String field) {
        persons.sort(field);
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    @Override
    public ObservableList<Meeting> getMeetingList() {
        return meetings.asObservableList();
    }

```
###### \java\seedu\address\model\meeting\Meeting.java
``` java
/**
 * Represents a Meeting in the address book.
 * Guarantees: immutable
 */
public class Meeting {

    public static final String MESSAGE_TIME_CONSTRAINTS = "Time format should be YYYY-MM-DD HH-MM";

    public final LocalDateTime date;
    public final String value;
    private Name name;
    private ObjectProperty<Name> displayName;

    /**
     * Validates given tag name.
     *
     * @throws IllegalValueException if the given tag name string is invalid.
     */
    public Meeting(String time, Name name) throws IllegalValueException {
        setName(name);
        requireNonNull(time);
        String trimmedTime = time.trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            LocalDateTime date = LocalDateTime.parse(trimmedTime, formatter);
            this.date = date;
            value = date.format(formatter);
        } catch (DateTimeParseException dtpe) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
    }

    /**
     * Overloaded constructor to be used in edit command parser
     */
    public Meeting(String time) throws IllegalValueException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            LocalDateTime date = LocalDateTime.parse(trimmedTime, formatter);
            this.date = date;
            value = date.format(formatter);
        } catch (DateTimeParseException dtpe) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
    }

    /**
     * Set the name attributes of the meeting object.
     */
    public void setName(Name name) {
        this.name = name;
        this.displayName = new SimpleObjectProperty<>(name);
    }

    /**
     * Returns name of the meeting
     */
    public Name getName() {
        return name;
    }

    /**
     * Return name for use by UI
     */
    public ObjectProperty<Name> nameProperty() {
        return displayName;
    }



    @Override
    public boolean equals(Object other) {
        /* Only happens for testing as name attribute will be set for the main app*/
        if (this.name == null && other instanceof Meeting && ((Meeting) other).name == null) {
            return other == this // short circuit if same object
                    || (other instanceof Meeting // instanceof handles nulls
                    && this.date.equals(((Meeting) other).date)); //state check
        }

        return other == this // short circuit if same object
                || (other instanceof Meeting // instanceof handles nulls
                && this.date.equals(((Meeting) other).date)
                && this.name.toString().equals(((Meeting) other).name.toString())); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return "[" + value + "]";
    }

}
```
###### \java\seedu\address\model\meeting\UniqueMeetingList.java
``` java
/**
 * A list of meetings that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Meeting#equals(Object)
 */
public class UniqueMeetingList implements Iterable<Meeting> {

    private final ObservableList<Meeting> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TagList.
     */
    public UniqueMeetingList() {}

    /**
     * Creates a UniqueMeetingList using given meetings.
     * Enforces no nulls.
     */
    public UniqueMeetingList(Set<Meeting> meetings) {
        requireAllNonNull(meetings);
        internalList.addAll(meetings);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all Meetings in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Meeting> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Meetings in this list with those in the argument Meeting list.
     */
    public void setMeetings(Set<Meeting> meetings) {
        requireAllNonNull(meetings);
        internalList.setAll(meetings);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every Meeting in the argument list exists in this object.
     */
    public void mergeFrom(UniqueMeetingList from) {
        final Set<Meeting> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(meeting -> !alreadyInside.contains(meeting))
                .forEach(internalList::add);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Meeting as the given argument.
     */
    public boolean contains(Meeting toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Meeting to the list.
     *
     * @throws DuplicateMeetingException if the Meeting to add is a duplicate of an existing Meeting in the list.
     */
    public void add(Meeting toAdd) throws DuplicateMeetingException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMeetingException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Meeting> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Meeting> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueMeetingList // instanceof handles nulls
                && this.internalList.equals(((UniqueMeetingList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueMeetingList other) {
        for (Meeting m : internalList) {
            System.out.println(m.value + " " + m.getName().toString() + "checking order insensitive");
        }
        for (Meeting m : other.internalList) {
            System.out.println(m.value + " " + m.getName().toString() + " other checking order insensitive");
        }
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateMeetingException extends DuplicateDataException {
        protected DuplicateMeetingException() {
            super("Operation would result in duplicate meetings");
        }
    }

}
```
###### \java\seedu\address\model\Model.java
``` java
    /** Deletes given tag from AddressBook */
    void deleteTag(Tag tag) throws DuplicatePersonException, PersonNotFoundException, TagNotFoundException;

    /** Sorts AddressBook by a field in alphabetical order */
    void sort(String field);

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void deleteTag(Tag tag) throws DuplicatePersonException, PersonNotFoundException, TagNotFoundException {
        ObservableList<ReadOnlyPerson> personList = addressBook.getPersonList();
        boolean tagFound = false;

        for (int i = 0; i < personList.size(); i++) {
            ReadOnlyPerson oldPerson = personList.get(i);
            Set<Tag> oldTags = new HashSet<Tag>(oldPerson.getTags());
            if (oldTags.contains(tag)) {
                Person newPerson = new Person(oldPerson);
                oldTags.remove(tag);
                newPerson.setTags(oldTags);
                addressBook.updatePerson(oldPerson, newPerson);
                tagFound = true;
            }
        }

        if (!tagFound) {
            throw new TagNotFoundException();
        }
        indicateAddressBookChanged();
    }

    @Override
    public void sort(String field) {
        addressBook.sort(field);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

```
###### \java\seedu\address\model\person\exceptions\TagNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified tag.
 */
public class TagNotFoundException extends Exception {}
```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Returns an immutable meeting set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Meeting> getMeetings() {
        return Collections.unmodifiableSet(meetings.get().toSet());
    }

    public ObjectProperty<UniqueMeetingList> meetingProperty() {
        return meetings;
    }

    /**
     * Replaces this person's meetings with the meetings in the argument meeting set.
     */
    public void setMeetings(Set<Meeting> replacement) {
        meetings.set(new UniqueMeetingList(replacement));
    }

```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Sorts the list by field.
     * TO DO: More abstraction. Comparators in respective classes?
     */
    public void sort(String field) {
        switch (field) {
        case "name":
            Collections.sort(internalList, new Comparator<Person>() {
                public int compare(Person one, Person other) {
                    String oneName = one.getName().toString().toLowerCase();
                    String otherName = other.getName().toString().toLowerCase();
                    return oneName.compareTo(otherName);
                }
            });
            break;
        case "phone":
            Collections.sort(internalList, new Comparator<Person>() {
                public int compare(Person one, Person other) {
                    return one.getPhone().toString().compareTo(other.getPhone().toString());
                }
            });
            break;
        case "email":
            Collections.sort(internalList, new Comparator<Person>() {
                public int compare(Person one, Person other) {
                    String oneEmail = one.getEmail().toString().toLowerCase();
                    String otherEmail = other.getEmail().toString().toLowerCase();
                    String noEmail = "no email";
                    if (oneEmail.equals(noEmail)) {
                        if (otherEmail.equals(noEmail)) {
                            return 0;
                        } else {
                            return 1;
                        }
                    } else if (otherEmail.equals(noEmail)) {
                        return -1;
                    } else {
                        return oneEmail.compareTo(otherEmail);
                    }
                }
            });
            break;
        case "address":
            Collections.sort(internalList, new Comparator<Person>() {
                public int compare(Person one, Person other) {
                    String oneAddress = one.getAddress().toString().toLowerCase();
                    String otherAddress = other.getAddress().toString().toLowerCase();
                    String noAddress = "no address";
                    if (oneAddress.equals(noAddress)) {
                        if (otherAddress.equals(noAddress)) {
                            return 0;
                        } else {
                            return 1;
                        }
                    } else if (otherAddress.equals(noAddress)) {
                        return -1;
                    } else {
                        return oneAddress.compareTo(otherAddress);
                    }
                }
            });
            break;
        case "tag":
            Collections.sort(internalList, new Comparator<Person>() {
                public int compare(Person one, Person other) {
                    Set<Tag> oneTags = one.getTags();
                    ArrayList<String> oneTagsString = new ArrayList<String>();
                    for (Tag tag : oneTags) {
                        oneTagsString.add(tag.toString().toLowerCase());
                    }
                    Collections.sort(oneTagsString);
                    Set<Tag> otherTags = other.getTags();
                    ArrayList<String> otherTagsString = new ArrayList<String>();
                    for (Tag tag : otherTags) {
                        otherTagsString.add(tag.toString().toLowerCase());
                    }
                    Collections.sort(otherTagsString);
                    for (int i = 0; i < Math.min(oneTagsString.size(), otherTagsString.size()); i++) {
                        if (!(oneTagsString.get(i).equals(otherTagsString.get(i)))) {
                            return oneTagsString.get(i).compareTo(otherTagsString.get(i));
                        }
                    }
                    return 0;
                }
            });
            break;
        default:
            // case "meeting"
            Collections.sort(internalList, new Comparator<Person>() {
                public int compare(Person one, Person other) {
                    Set<Meeting> oneMeetings = one.getMeetings();
                    ArrayList<String> oneMeetingsString = new ArrayList<String>();
                    for (Meeting meeting : oneMeetings) {
                        oneMeetingsString.add(meeting.toString().toLowerCase());
                    }
                    Collections.sort(oneMeetingsString);
                    Set<Meeting> otherMeetings = other.getMeetings();
                    ArrayList<String> otherMeetingsString = new ArrayList<String>();
                    for (Meeting meeting : otherMeetings) {
                        otherMeetingsString.add(meeting.toString().toLowerCase());
                    }
                    Collections.sort(otherMeetingsString);
                    for (int i = 0; i < Math.min(oneMeetingsString.size(), otherMeetingsString.size()); i++) {
                        if (!(oneMeetingsString.get(i).equals(otherMeetingsString.get(i)))) {
                            return oneMeetingsString.get(i).compareTo(otherMeetingsString.get(i));
                        }
                    }
                    return 0;
                }
            });
        }
    }
```
###### \java\seedu\address\model\ReadOnlyAddressBook.java
``` java
    /**
     * Returns an unmodifiable view of the meetings list.
     * This list will not contain any duplicate meetings.
     */
    ObservableList<Meeting> getMeetingList();
```
###### \java\seedu\address\storage\XmlAdaptedMeeting.java
``` java
/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedMeeting {

    @XmlElement
    private String meetingName;
    @XmlElement
    private String userName;

    /**
     * Constructs an XmlAdaptedMeeting.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMeeting() {}

    /**
     * Converts a given Meeting into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedMeeting(Meeting source) {
        meetingName = source.value;
        userName = source.getName().toString();
    }

    /**
     * Converts this jaxb-friendly adapted meeting object into the model's Meeting object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Meeting toModelType() throws IllegalValueException {
        return new Meeting(meetingName, new Name(userName));
    }

}
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
    @Override
    public ObservableList<Meeting> getMeetingList() {
        final ObservableList<Meeting> meetings = this.meetings.stream().map(m -> {
            try {
                return m.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(meetings);
    }
```
