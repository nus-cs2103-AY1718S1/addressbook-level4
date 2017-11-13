# kengying
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setGroup(Group group) {
            this.group = group;
        }

        public Optional<Group> getGroup() {
            return Optional.ofNullable(group);
        }
```
###### \java\seedu\address\logic\commands\ListCommand.java
``` java
/**
 * Lists all persons/groups/tags in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_ALIAS = "l";
    public static final String SEARCH_TERM = "all";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + SEARCH_TERM + " : List all persons\n"
            + "Example: " + COMMAND_WORD +  " " + SEARCH_TERM + "\n"
            + ListTagCommand.MESSAGE_USAGE + "\n"
            + ListGroupCommand.MESSAGE_USAGE;

    private Predicate<ReadOnlyPerson> predicate = PREDICATE_SHOW_ALL_PERSONS;

    public ListCommand() {
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && this.predicate.equals(((ListCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\ListGroupCommand.java
``` java

/**
 * Lists all group in the address book to the user.
 */
public class ListGroupCommand extends ListCommand {

    public static final String SEARCH_TERM = "groups";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + SEARCH_TERM + " : List all groups\n"
            + "Example: " + COMMAND_WORD +  " " + SEARCH_TERM;

    public static final String MESSAGE_SUCCESS = "List all groups: ";

    @Override
    public CommandResult execute() {
        List<Group> listGroups = model.getGroupList();
        return new CommandResult(String.format(MESSAGE_SUCCESS + " "
                + listGroups.toString().replaceAll("[\\[\\]]", "")));
    }

}
```
###### \java\seedu\address\logic\commands\ListTagCommand.java
``` java

/**
 * Lists all tags in the address book to the user.
 */
public class ListTagCommand extends ListCommand {

    public static final String SEARCH_TERM = "tags";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + SEARCH_TERM + " : List all tags\n"
            + "Example: " + COMMAND_WORD +  " " + SEARCH_TERM;

    public static final String MESSAGE_SUCCESS = "List all tags: ";

    @Override
    public CommandResult execute() {
        List<Tag> listTags = model.getTagList();
        return new CommandResult(String.format(MESSAGE_SUCCESS + " " + listTags.toString().replaceAll("[\\[\\]]", "")));
    }

}
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
    /**
     * Returns "" if prefixes contains empty values in the given
     * {@value}.
     */
    private static Optional<String> areValuePresent(Optional<String> value) {
        value = Optional.of(value.orElse(""));
        return value;
    }
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
    private String firstLetterToLowerCase(String commandWord) {
        return commandWord.substring(0, 1).toLowerCase() + commandWord.substring(1);
    }
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
    /**
     * Parses {@code List<String> group} into a {@code List<Group>} if {@code group} is non-empty.
     * If {@code group} contain only one element which is an empty string, it will be parsed into a
     * {@code List<Group>} containing zero groups.
     */
    private Optional<List<Group>> parseGroupForSearch(List<String> group) throws IllegalValueException {
        assert group != null;

        if (group.isEmpty()) {
            return Optional.empty();
        }
        List<String> groupList = group.size() == 1 && group.contains("")
                ? Collections.emptyList() : group;
        return Optional.of(ParserUtil.parseGroup(groupList));
    }

    /**
     * Parses {@code List<String> tags} into a {@code List<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code List<String>} containing zero tags.
     */
    private Optional<List<String>> parseTagForSearch(List<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        List<String> tagList = tags.size() == 1 && tags.contains("")
                ? Collections.emptyList() : tags;
        return Optional.of(tagList);
    }
```
###### \java\seedu\address\logic\parser\ListCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.equalsIgnoreCase("all")) {
            return new ListCommand();
        } else if (trimmedArgs.equalsIgnoreCase("tags")) {
            return new ListTagCommand();
        } else if (trimmedArgs.equalsIgnoreCase("groups")) {
            return new ListGroupCommand();
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE,
                            ListTagCommand.MESSAGE_USAGE, ListGroupCommand.MESSAGE_SUCCESS));
        }

    }

}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> group} into an {@code Optional<Group>} if {@code group} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Group> parseGroup(Optional<String> group) throws IllegalValueException {
        requireNonNull(group);
        return group.isPresent() ? Optional.of(new Group(group.get())) : Optional.empty();
    }

    /**
     *
     * Parses a {@code Collection<String> group} into a {@code List<Group>}
     */
    public static List<Group> parseGroup(Collection<String> groups) throws IllegalValueException {
        requireNonNull(groups);
        final List<Group> groupList = new ArrayList<>();
        for (String group : groups) {
            groupList.add(new Group(group));
        }
        return groupList;
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void setGroups(List<? extends Group> persons) {
        this.groups.setGroups(groups);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Ensures that every group in this person:
     *  - exists in the master list {@link #groups}
     *  - points to a Group object in the master list
     */
    private void syncMasterGroupListWith(Person person) {
        final UniqueGroupList personGroup = new UniqueGroupList(person.getGroup());
        groups.mergeFrom(personGroup);
    }

    /**
     * Ensures that every group in these persons:
     *  - exists in the master list {@link #tags}
     *  - points to a Group object in the master list
     *  @see #syncMasterGroupListWith(Person)
     */
    private void syncMasterGroupListWith(UniquePersonList persons) {
        persons.forEach(this::syncMasterGroupListWith);
    }
    //@@ author

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removePerson(ReadOnlyPerson key) throws PersonNotFoundException {
        if (persons.remove(key)) {
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    //// group-level operations
    public void addGroup(Group g) throws UniqueGroupList.DuplicateGroupException {
        groups.add(g);
    }
```
###### \java\seedu\address\model\group\Group.java
``` java
/**
 * Represents a Person's group in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGroup(String)}
 */
public class Group implements Comparable<Group>, Comparator<Group> {

    public static final String MESSAGE_GROUP_CONSTRAINTS =
            "Person groups should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String GROUP_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String groupName;

    /**
     * Validates given group.
     *
     * @throws IllegalValueException if given group address string is invalid.
     */
    public Group(String group) throws IllegalValueException {
        requireNonNull(group);
        String trimmedGroup = group.trim();
        if (!isValidGroup(trimmedGroup)) {
            throw new IllegalValueException(MESSAGE_GROUP_CONSTRAINTS);
        }
        this.groupName = trimmedGroup;
    }

    public Group(Group group) throws IllegalValueException {
        this(group.toString());
    }

    /**
     * Returns if a given string is a valid person group.
     */
    public static boolean isValidGroup(String test) {
        return test.matches(GROUP_VALIDATION_REGEX);
    }

    public int compareTo(Group g) {
        return this.groupName.compareTo(g.groupName);
    }

    public int compare(Group a, Group b) {
        return a.groupName.compareTo(b.groupName);
    }

    @Override
    public String toString() {
        return groupName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Group // instanceof handles nulls
                && this.groupName.equals(((Group) other).groupName)); // state check
    }

    @Override
    public int hashCode() {
        return groupName.hashCode();
    }

}
```
###### \java\seedu\address\model\group\UniqueGroupList.java
``` java
/**
 * A list of groups that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Group#equals(Object)
 */
public class UniqueGroupList implements Iterable<Group> {

    private final ObservableList<Group> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty GroupList.
     */
    public UniqueGroupList() {}

    /**
     * Creates a UniqueGroupList using given groups.
     * Enforces no nulls.
     */
    public UniqueGroupList(Group groups) {
        requireAllNonNull(groups);
        internalList.addAll(groups);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Replaces the Groups in this list with those in the argument group list.
     */
    public void setGroups(UniqueGroupList groups) {
        requireAllNonNull(groups);
        internalList.setAll(groups.internalList);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    public void setGroups(List<? extends Group> groups) throws DuplicateGroupException {
        final UniqueGroupList replacement = new UniqueGroupList();
        for (final Group group : groups) {
            replacement.add(group);
        }
        setGroups(replacement);
    }
    /**
     * Ensures every group in the argument list exists in this object.
     */
    public void mergeFrom(UniqueGroupList from) {
        final UniqueGroupList alreadyInside = this;
        from.internalList.stream()
                .filter(group -> !alreadyInside.contains(group))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Group as the given argument.
     */
    public boolean contains(Group toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Group to the list.
     *
     * @throws DuplicateGroupException if the Group to add is a duplicate of an existing Group in the list.
     */
    public void add(Group toAdd) throws DuplicateGroupException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateGroupException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Group> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Group> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueGroupList // instanceof handles nulls
                        && this.internalList.equals(((UniqueGroupList) other).internalList));
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
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public List<Tag> getTagList() {
        List<Tag> listTagsWithDuplicates = new ArrayList<>();

        filteredPersons.forEach(persons -> listTagsWithDuplicates.addAll(persons.getTags()));

        List<Tag> listTags = listTagsWithDuplicates.stream()
                .distinct()
                .collect(Collectors.toList());

        listTags.sort(Comparator.comparing(Tag::toString));


        return listTags;
    }

    @Override
    public List<Group> getGroupList() {
        ObservableList<ReadOnlyPerson> personList = addressBook.getPersonList();
        List<Group> listGroupWithDuplicates = new ArrayList<>();
        for (int i = 0; i < personList.size(); i++) {
            ReadOnlyPerson currReadOnlyPerson = personList.get(i);

            Person newPerson = new Person(currReadOnlyPerson);
            listGroupWithDuplicates.add(newPerson.getGroup());
        }

        List<Group> listGroups = listGroupWithDuplicates.stream()
                .distinct()
                .collect(Collectors.toList());

        listGroups.sort(Comparator.comparing(Group::toString));


        return listGroups;
    }
```
###### \java\seedu\address\storage\XmlAdaptedGroup.java
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
    public XmlAdaptedGroup() {}

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedGroup(Group source) {
        groupName = source.groupName;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Group toModelType() throws IllegalValueException {
        return new Group(groupName);
    }

}
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
    @Override
    public ObservableList<Group> getGroupList() {
        final ObservableList<Group> tags = this.groups.stream().map(g -> {
            try {
                return g.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(tags);
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    private void initGroups(ReadOnlyPerson person) {
        group.setStyle("-fx-background-color: " + ColorUtil.getUniqueHsbColorForObject(person.getGroup()));
    }
```
###### \resources\view\DarkTheme.css
``` css
VBox .cell_big_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 16px;
    -fx-text-fill: #EAECEE;
}

VBox .cell_small_label {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 13px;
    -fx-text-fill: #EAECEE;
}
```
