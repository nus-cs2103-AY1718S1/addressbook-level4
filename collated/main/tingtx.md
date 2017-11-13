# tingtx
###### /java/seedu/address/commons/core/index/Index.java
``` java
    @Override
    public String toString() {
        return Integer.toString(getOneBased());
    }
}
```
###### /java/seedu/address/logic/parser/AddCommandParser.java
``` java
            Email email = arePrefixesPresent(argMultimap, PREFIX_EMAIL)
                    ? ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get() : new Email("");
            Birthday birthday = arePrefixesPresent(argMultimap, PREFIX_BIRTHDAY)
                    ? ParserUtil.parseBirthday(argMultimap.getValue(PREFIX_BIRTHDAY)).get() : new Birthday("");
```
###### /java/seedu/address/logic/parser/ListCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Returns true if the prefixes contains no empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return Stream.of(prefix).allMatch(groupPrefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an ListCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GROUP);

        if (!trimmedArgs.isEmpty() && !isPrefixesPresent(argMultimap, PREFIX_GROUP)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        ContainsKeywordsPredicate.setPredicateType('g');
        return new ListCommand(trimmedArgs);
    }
}
```
###### /java/seedu/address/logic/parser/GroupCommandParser.java
``` java

/**
 * Parses input arguments and creates a new GroupCommand object
 */
public class GroupCommandParser implements Parser<GroupCommand> {


    /**
     * Returns true the agrs contain only alphabets
     */
    private static boolean containsAlphabetOnly(String args) {
        return args.matches("[a-zA-Z]+");
    }

    /**
     * Returns true the prefixes contains no empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return Stream.of(prefix).allMatch(groupPrefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the GroupCommand
     * and returns an GroupCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GroupCommand parse(String args) throws ParseException {
        requireNonNull(args);
        args = args.trim();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GROUP);

        List<Index> indexes = new ArrayList<>();
        if (containsAlphabetOnly(args)) {
            return new GroupCommand(indexes, args);
        }

        if (!isPrefixesPresent(argMultimap, PREFIX_GROUP) && !containsAlphabetOnly(args)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupCommand.MESSAGE_USAGE));
        }

        String preamble;
        String[] indexStr;
        try {
            preamble = argMultimap.getPreamble();
            indexStr = preamble.split("\\s+");
            for (String index : indexStr) {
                indexes.add(ParserUtil.parseIndex(index));
            }

        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GroupCommand.MESSAGE_USAGE));
        }

        String group = argMultimap.getValue(PREFIX_GROUP).get();
        return new GroupCommand(indexes, group);
    }
}
```
###### /java/seedu/address/logic/commands/ListCommand.java
``` java

/**
 * Lists all persons in the address book to the user or
 * list a specified group of persons.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": List the Address Book or ONE specified group of contact.\n"
            + "Parameters: [g/GROUP]\n"
            + "Example: " + COMMAND_WORD + "\n"
            + "Example: " + COMMAND_WORD + " g/friends";
    public static final String MESSAGE_LIST_ALL_SUCCESS = "Listed all persons";
    public static final String MESSAGE_LIST_GROUP_SUCCESS = "Listed all persons in ";
    public static final String MESSAGE_LIST_WRONG_PARAMETER = "Group does not exist!";

    private final ContainsKeywordsPredicate predicate;
    private final String listParameter;

    public ListCommand(String predicate) {
        this.listParameter = predicate;
        predicate = predicate.isEmpty() ? predicate : predicate.substring(2).trim();
        this.predicate = new ContainsKeywordsPredicate(Arrays.asList(predicate));

    }

    public static String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult execute() {

        if (listParameter.isEmpty()) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(MESSAGE_LIST_ALL_SUCCESS);
        }

        model.updateFilteredPersonList(predicate);

        if (model.getFilteredPersonList().size() == 0) {
            return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size())
                    + " " + MESSAGE_LIST_WRONG_PARAMETER);
        }
        return new CommandResult(MESSAGE_LIST_GROUP_SUCCESS + listParameter.substring(2).trim());

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && this.listParameter.equals(((ListCommand) other).listParameter)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/GroupCommand.java
``` java

/**
 * Group person(s) into user defined group.
 */
public class GroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Group the person(s) identified "
            + "by the index number used in the last person listing.\n "
            + "Person(s) will be grouped to the group name specified.\n"
            + "Parameters: INDEX_1 [INDEX_2...] (must be a positive integer) "
            + PREFIX_GROUP + "GROUPNAME "
            + "Example: " + COMMAND_WORD + " 1 3 4 "
            + PREFIX_GROUP + "FAMILY \n"
            + "To view the existing groups."
            + "Parameters : SHOWALL (case insensitive)\n"
            + "Example: " + COMMAND_WORD + " SHOWALL";

    public static final String MESSAGE_GROUP_PERSON_SUCCESS = "Grouped Person(s) to ";
    public static final String MESSAGE_UNGROUP_PERSON_SUCCESS = "Person(s) removed from group.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String SHOW_ALL_GROUP = "showall";
    public static final String MESSAGE_WRONG_PARAMETER = "To view existing groups, "
            + "the parameter must be SHOWALL\n" + "To group person(s) : " + COMMAND_WORD + " 1 3 4 "
            + PREFIX_GROUP + "FAMILY";

    private final List<Index> indexes;
    private final String group;

    /**
     * @param indexes of the person in the filtered person list to edit
     * @param group   group name to group the person with
     */
    public GroupCommand(List<Index> indexes, String group) {
        requireNonNull(indexes);
        requireNonNull(group);

        this.indexes = indexes;
        this.group = group;
    }

    public static String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        CommandResult commandResult;
        if (group.equalsIgnoreCase(SHOW_ALL_GROUP)) {
            commandResult = showAllGroupName();
        } else if (indexes.size() > 0) {
            commandResult = setGroupToPerson();
        } else {
            throw new CommandException(MESSAGE_WRONG_PARAMETER);
        }

        return commandResult;

    }

    private CommandResult showAllGroupName() {

        return new CommandResult("Groups:  " + model.getGroupList().toString()
                .replaceAll("\\[", "").replaceAll("\\]", ""));
    }

    private CommandResult setGroupToPerson() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        for (Index index : indexes) {

            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException("Index " + index.toString() + " is invalid!");
            }

            ReadOnlyPerson personToGroup = lastShownList.get(index.getZeroBased());

            try {
                Person editedPerson = new Person(personToGroup.getName(), personToGroup.getPhone(),
                        personToGroup.getEmail(), personToGroup.getAddress(), personToGroup.getBirthday(),
                        new Group(group), personToGroup.getRemark(), personToGroup.getTags());
                model.updatePerson(personToGroup, editedPerson);
                model.saveToEncryptedFile();
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (IllegalValueException ive) {
                throw new CommandException(ive.getMessage());
            } catch (PersonNotFoundException pnfe) {
                throw new CommandException("The target person cannot be missing");
            }

        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(generateSuccessMessage());
    }

    /**
     * @return the generated success message
     */
    private String generateSuccessMessage() {
        if (!group.isEmpty()) {
            return MESSAGE_GROUP_PERSON_SUCCESS + group;
        } else {
            return MESSAGE_UNGROUP_PERSON_SUCCESS;
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GroupCommand)) {
            return false;
        }

        // state check
        GroupCommand groupCommand = (GroupCommand) other;
        return indexes.equals(groupCommand.indexes)
                && group.equals(groupCommand.group);
    }
}
```
###### /java/seedu/address/storage/XmlAdaptedGroup.java
``` java
/**
 * JAXB-friendly adapted version of the Group.
 */
public class XmlAdaptedGroup {

    @XmlValue
    private String groupName;

    /**
     * Constructs an XmlAdaptedGroup.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedGroup() {
    }

    /**
     * Converts a given Group into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedGroup(Group source) {
        groupName = source.value;
    }

    /**
     * Converts this jaxb-friendly adapted group object into the model's Group object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Group toModelType() throws IllegalValueException {
        return new Group(groupName);
    }
}
```
###### /java/seedu/address/model/group/Group.java
``` java
/**
 * Represents a Person's group in the address book.
 */
public class Group {

    public static final String MESSAGE_GROUP_CONSTRAINTS =
            "Group name cannot be longer than 30 characters";
    public static final String GROUP_VALIDATION_REGEX = ".{0,30}";

    public final String value;

    public Group(String group) throws IllegalValueException {
        requireNonNull(group);
        String trimmedGroup = group.trim();
        if (!isValidGroup(trimmedGroup)) {
            throw new IllegalValueException(MESSAGE_GROUP_CONSTRAINTS);
        }
        this.value = group;
    }

    /**
     * Returns true if a given string is a valid group.
     */
    public static boolean isValidGroup(String test) {
        return test.isEmpty() || test.matches(GROUP_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Group //instanceof handles null
                && this.value.equals(((Group) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### /java/seedu/address/model/group/UniqueGroupList.java
``` java
/**
 * A list of groups that enforces no nulls and uniqueness between its elements.
 * Supports minimal set of list operations for the app's features.
 *
 */
public class UniqueGroupList implements Iterable<Group> {

    private final ObservableList<Group> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty GroupList.
     */
    public UniqueGroupList() {
    }


    /**
     * Replaces the group in this list with those in the argument group list.
     */
    public void setGroup(Set<Group> groups) {
        requireAllNonNull(groups);
        internalList.setAll(groups);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Gr oup as the given argument.
     */
    public boolean contains(Group toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Group to the list only if it is not already exist and Group does not contains empty value.
     */
    public void addIfNew(Group toAdd) {
        requireNonNull(toAdd);
        if (!toAdd.value.isEmpty() && !contains(toAdd)) {
            internalList.add(toAdd);
        }
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Deletes a Group from the list.
     */
    public void delete(Group toDelete) {
        requireNonNull(toDelete);
        internalList.remove(toDelete);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Group> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Group> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueGroupList other) {
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
    public static class DuplicateGroupException extends DuplicateDataException {
        protected DuplicateGroupException() {
            super("Operation would result in duplicate groups");
        }
    }

}
```
###### /java/seedu/address/model/person/ReadOnlyPerson.java
``` java
    ObjectProperty<Birthday> birthdayProperty();

    Birthday getBirthday();

    ObjectProperty<Group> groupProperty();

    Group getGroup();
```
###### /java/seedu/address/model/person/UniquePersonList.java
``` java
    /**
     * Order the list.
     */
    public void orderPersonList(String parameter) throws UnrecognisedParameterException {

        switch (parameter) {
        case "NAME":
            internalList.sort(Person.getPersonNameComparator());
            break;

        case "ADDRESS":
            internalList.sort(Person.getPersonAddressComparator());
            break;

        case "BIRTHDAY":
            internalList.sort(Person.getPersonBirthdayComparator());
            break;

        case "TAG":
            internalList.sort(Person.getPersonTagComparator());
            break;

        case "GROUP":
            internalList.sort(Person.getPersonGroupComparator());
            break;

        case "NAME ADDRESS":
            internalList.sort(Person.getPersonNameComparator().thenComparing(Person.getPersonAddressComparator()));
            break;

        case "ADDRESS NAME":
            internalList.sort(Person.getPersonAddressComparator().thenComparing(Person.getPersonNameComparator()));
            break;

        case "TAG NAME":
            internalList.sort(Person.getPersonTagComparator().thenComparing(Person.getPersonNameComparator()));
            break;

        case "NAME TAG":
            internalList.sort(Person.getPersonNameComparator().thenComparing(Person.getPersonTagComparator()));
            break;

        case "NAME BIRTHDAY":
            internalList.sort(Person.getPersonNameComparator().thenComparing(Person.getPersonBirthdayComparator()));
            break;

        case "BIRTHDAY NAME":
            internalList.sort(Person.getPersonBirthdayComparator().thenComparing(Person.getPersonNameComparator()));
            break;

        case "ADDRESS BIRTHDAY":
            internalList.sort(Person.getPersonAddressComparator().thenComparing(Person.getPersonBirthdayComparator()));
            break;

        case "BIRTHDAY ADDRESS":
            internalList.sort(Person.getPersonBirthdayComparator()
                    .thenComparing(Person.getPersonAddressComparator()));
            break;

        case "BIRTHDAY TAG":
            internalList.sort(nullsLast(Person.getPersonBirthdayComparator()
                    .thenComparing(Person.getPersonTagComparator())));
            break;

        case "TAG BIRTHDAY":
            internalList.sort(Person.getPersonTagComparator().thenComparing(Person.getPersonBirthdayComparator()));
            break;

        case "GROUP NAME":
            internalList.sort(Person.getPersonGroupComparator().thenComparing(Person.getPersonNameComparator()));
            break;

        case "GROUP BIRTHDAY":
            internalList.sort(Person.getPersonGroupComparator().thenComparing(Person.getPersonBirthdayComparator()));
            break;

        case "GROUP ADDRESS":
            internalList.sort(Person.getPersonGroupComparator().thenComparing(Person.getPersonBirthdayComparator()));
            break;

        case "GROUP TAG":
            internalList.sort(Person.getPersonGroupComparator().thenComparing(Person.getPersonTagComparator()));
            break;

        default:
            throw new UnrecognisedParameterException();
        }

    }
```
###### /java/seedu/address/model/person/Person.java
``` java
    public void setBirthday(Birthday birthday) {
        this.birthday.set(requireNonNull(birthday));
    }

    @Override
    public ObjectProperty<Group> groupProperty() {
        return group;
    }

    @Override
    public Group getGroup() {
        return group.get();
    }

    public void setGroup(Group group) {
        this.group.set(requireNonNull(group));
    }
```
###### /java/seedu/address/model/person/Person.java
``` java

    public static final Comparator<Person> getPersonNameComparator() {
        return (Person a, Person b) -> a.getName().toString()
                .compareToIgnoreCase(b.getName().toString());
    }

    public static final Comparator<Person> getPersonAddressComparator() {
        return (Person a, Person b) -> a.getAddress().toString()
                .compareToIgnoreCase(b.getAddress().toString());
    }

    public static final Comparator<Person> getPersonBirthdayComparator() {
        return comparing(a -> a.getBirthday().getReformatDate(),
                nullsLast(naturalOrder()));
    }

    public static final Comparator<Person> getPersonTagComparator() {
        return (Person a, Person b) -> a.getTags().toString()
                .compareToIgnoreCase(b.getTags().toString());
    }

    public static final Comparator<Person> getPersonGroupComparator() {
        return (a, b) -> {
            if (a.getGroup().toString().isEmpty()) {
                return 1;
            } else if (b.getGroup().toString().isEmpty()) {
                return -1;
            } else {
                return a.getGroup().toString().compareToIgnoreCase(b.getGroup().toString());
            }
        };
    }
```
###### /java/seedu/address/model/person/Birthday.java
``` java
/**
 * Represents a Person's birthday in the address book.
 */
public class Birthday {
    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Birthday can only contain numbers and dashes, and in the form dd-mm-yyyy";
    public static final String BIRTHDAY_VALIDATION = "dd-mm-yyyy";
    public final String value;

    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given birthday string is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        String trimmedBirthday = birthday.trim();
        if (!isValidBirthday(trimmedBirthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        this.value = trimmedBirthday;
    }

    /**
     * Returns true if a given string is in the correct format and has a valid date.
     */
    public static boolean isValidBirthday(String test) {
        if (test.isEmpty()) {
            return true;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(BIRTHDAY_VALIDATION);

        try {
            Date date = dateFormat.parse(test);
            return test.equals(dateFormat.format(date).toString());
        } catch (ParseException pe) {
            return false;
        }

    }

    public String getReformatDate() {
        if (value.isEmpty()) {
            return null;
        }
        return new StringBuilder().append(value.substring(6, 10)).append(value.substring(3, 5))
                .append(value.substring(0, 2)).toString();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && this.value.equals(((Birthday) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

```
###### /java/seedu/address/model/AddressBook.java
``` java

    /**
     * Ensures that the group in this person:
     * - exists in the master list {@link #groups}
     */
    private void syncMasterGroupListWith(Person person) {
        Group personGroup = person.getGroup();
        groups.addIfNew(personGroup);
    }

    /**
     * Ensures that every group in these persons:
     * - exists in the master list {@link #groups}
     *
     * @see #syncMasterGroupListWith(Person)
     */
    private void syncMasterGroupListWith(UniquePersonList persons) {
        persons.forEach(this::syncMasterGroupListWith);
    }

    /**
     * Ensures the previous group in this persons:
     * -is deleted if no other person is in the group
     */
    private void updateMasterGroupList(Group prevGroup) {
        boolean isGroupPresent = false;

        if (!prevGroup.toString().isEmpty()) {
            for (Person p : persons) {
                if (p.getGroup().equals(prevGroup)) {
                    isGroupPresent = true;
                }
            }
            if (!isGroupPresent) {
                groups.delete(prevGroup);
            }
        }
    }
```
