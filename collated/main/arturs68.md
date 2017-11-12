# arturs68
###### \java\seedu\address\logic\commands\ChangePicCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;

import java.io.File;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ProfilePicture;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Updates the profile picture of a Person
 */
public class ChangePicCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "changepic";
    public static final String COMMAND_ALIAS = "pic";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the profile picture of the person identified "
            + "by the index number used in the last person listing to the one located at PICTURE_PATH.\n"
            + "To reset to default picture, choose 'default_pic.png' as the path"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PATH + "PICTURE_PATH\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PATH + "C:\\Users\\User\\Pictures\\pic.jpg";

    public static final String MESSAGE_CHANGEPIC_SUCCESS = "Changed the picture of the Person: %1$s";

    private final Index index;
    private final String picturePath;

    /**
     * @param index of the person in the filtered person list to change picture
     * @param picturePath of the picture to be loaded
     */
    public ChangePicCommand(Index index, String picturePath) {
        requireNonNull(index);
        requireNonNull(picturePath);

        this.index = index;
        this.picturePath = picturePath;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (picturePath.contains("/") || picturePath.contains("\\")) {
            File file = new File(picturePath);
            if (!file.exists()) {
                throw new CommandException("No file found in the path: " + picturePath);
            }
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());

        Person editedPerson;
        try {
            editedPerson = new Person(personToEdit);
            editedPerson.setProfilePicture(new ProfilePicture(picturePath));
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException("The person cannot be duplicated when changing the profile picture");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage());
        }
        model.updateFilteredPersonList(p ->true);
        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        return String.format(MESSAGE_CHANGEPIC_SUCCESS, personToEdit.getName());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ChangePicCommand)) {
            return false;
        }

        // state check
        ChangePicCommand e = (ChangePicCommand) other;
        return index.equals(e.index)
                && picturePath.equals(e.picturePath);
    }
}
```
###### \java\seedu\address\logic\commands\GroupCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;
import seedu.address.model.group.UniqueGroupList;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Adds person with given index to a group. If such a group does not exist, creates it and then adds the person.
 */
public class GroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds person to the group identified "
            + "by the group's name. Person to be added is specified by "
            + "index used in the last person listing. "
            + "Non existing group will be created before adding persons to it.\n"
            + "Parameters: " + "INDEX (must be a positive integer) "
            + PREFIX_GROUP_NAME + "GROUP_NAME.\n"
            + "Example: " + COMMAND_WORD + " 3 " + PREFIX_GROUP_NAME + "Family";

    public static final String MESSAGE_ADD_GROUP_SUCCESS = "Added %1$s to the group: %2$s";
    public static final String MESSAGE_DUPLICATE_GROUP = "%1$s already belongs to: %2$s";

    private final Index index;
    private final Group group;

    /**
     * @param index of the person in the filtered person list to add to the group
     * @param group where person is to be added
     */
    public GroupCommand(Index index, Group group) {
        requireNonNull(index);
        requireNonNull(group);

        this.index = index;
        this.group = group;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        UniqueGroupList editedGroups = new UniqueGroupList(personToEdit.getGroups());

        Person editedPerson;
        try {
            editedGroups.add(group);
            editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                    personToEdit.getAddress(), personToEdit.getAppointment(), personToEdit.getProfilePicture(),
                    editedGroups.toSet(), personToEdit.getTags());
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException("The person cannot be duplicated when adding to a group");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        } catch (UniqueGroupList.DuplicateGroupException dge) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_GROUP, personToEdit.getName(), group.groupName));
        }
        model.updateFilteredPersonList(p ->true);
        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        return String.format(MESSAGE_ADD_GROUP_SUCCESS, personToEdit.getName(), group.groupName);
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
        GroupCommand e = (GroupCommand) other;
        return index.equals(e.index)
                && group.equals(e.group);
    }
}
```
###### \java\seedu\address\logic\commands\RemoveTagCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Removes the specified tag from all the persons and from the address book tag list
 */
public class RemoveTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "removetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the tag identified by TAG name.\n"
            + "Parameters: " + PREFIX_TAG + "TAG\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TAG + "OwesMoney";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Removed tag: %1$s";

    private final Tag tagToBeRemoved;

    public RemoveTagCommand(Tag tagToBeRemoved) {
        this.tagToBeRemoved = tagToBeRemoved;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            boolean wasRemoved = model.removeTag(tagToBeRemoved);
            if (!wasRemoved) {
                throw new CommandException("Such a tag does not exit");
            }
        } catch (DuplicatePersonException dpe) {
            throw new CommandException("The person cannot be duplicated when adding to a group");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, tagToBeRemoved.toString()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveTagCommand // instanceof handles nulls
                && this.tagToBeRemoved.equals(((RemoveTagCommand) other).tagToBeRemoved)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\UngroupCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;
import seedu.address.model.group.UniqueGroupList;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.GroupNotFoundException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Removes a person from a group
 */
public class UngroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "ungroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes from the group with name GROUP_NAME "
            + "the person identified by the index number used in the last person listing."
            + "If it was the last member of the group, the group is also removed.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_GROUP_NAME + "GROUP_NAME\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_GROUP_NAME + "Family";

    public static final String MESSAGE_UNGROUP_SUCCESS = "Removed %1$s from a group: %2$s";
    public static final String MESSAGE_GROUP_NOT_FOUND = "%1$s does not belong to: %2$s";

    private final Index index;
    private final Group group;

    /**
     * @param index of the person in the filtered person list to remove from the group
     * @param group from which the person is to be removed
     */
    public UngroupCommand(Index index, Group group) {
        requireNonNull(index);
        requireNonNull(group);

        this.index = index;
        this.group = group;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        UniqueGroupList editedGroups = new UniqueGroupList(personToEdit.getGroups());

        Person editedPerson;
        try {
            editedGroups.remove(group);
            editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                    personToEdit.getAddress(), personToEdit.getAppointment(), personToEdit.getProfilePicture(),
                    editedGroups.toSet(), personToEdit.getTags());
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException("The person cannot be duplicated when adding to a group");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        } catch (GroupNotFoundException gnfe) {
            throw new CommandException(String.format(MESSAGE_GROUP_NOT_FOUND, personToEdit.getName(), group.groupName));
        }
        model.updateGroups(group);
        model.updateFilteredPersonList(p ->true);
        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        return String.format(MESSAGE_UNGROUP_SUCCESS, personToEdit.getName(), group.groupName);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UngroupCommand)) {
            return false;
        }

        // state check
        UngroupCommand e = (UngroupCommand) other;
        return index.equals(e.index)
                && group.equals(e.group);
    }
}
```
###### \java\seedu\address\logic\parser\ChangePicCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ChangePicCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ChangePicCommand object
 */
public class ChangePicCommandParser implements Parser<ChangePicCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ChangePicCommand
     * and returns an ChangePicCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangePicCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PATH);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            String picturePath = argMultimap.getValue(PREFIX_PATH).orElse("");
            if (picturePath.equals("")) {
                throw new IllegalValueException("No path specified");
            }
            return new ChangePicCommand(index, picturePath);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePicCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\GroupCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.GroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Group;

/**
 * Parses input arguments and creates a new GroupCommand object
 */
public class GroupCommandParser implements Parser<GroupCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the GroupCommand
     * and returns a GroupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GroupCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUP_NAME);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            Group group = ParserUtil.parseGroup(argMultimap.getValue(PREFIX_GROUP_NAME)).get();
            return new GroupCommand(index, group);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> Group} into an {@code Optional<Group>} if {@code group} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Group> parseGroup(Optional<String> groupName) throws IllegalValueException {
        requireNonNull(groupName);
        return groupName.isPresent() ? Optional.of(new Group(groupName.get())) : Optional.empty();
    }
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> tag} into an {@code Optional<Tag>} if {@code tag} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Tag> parseTag(Optional<String> tag) throws IllegalValueException {
        requireNonNull(tag);
        return tag.isPresent() ? Optional.of(new Tag(tag.get())) : Optional.empty();
    }
}
```
###### \java\seedu\address\logic\parser\RemoveTagCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemoveTagCommand;

import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new RemoveTagCommand object
 */
public class RemoveTagCommandParser implements Parser<RemoveTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveTagCommand
     * and returns an RemoveTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveTagCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG);
        if (!isPrefixPresent(argMultimap, PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }

        try {
            Tag tag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG)).get();
            return new RemoveTagCommand(tag);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }
    }

    private boolean isPrefixPresent(ArgumentMultimap argMultimap, Prefix prefix) {
        return argMultimap.getValue(prefix).isPresent();
    }
}
```
###### \java\seedu\address\logic\parser\UngroupCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UngroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Group;

/**
 * Parses input arguments and creates a new UngroupCommand object
 */
public class UngroupCommandParser implements Parser<UngroupCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UngroupCommand
     * and returns an UngroupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UngroupCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUP_NAME);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            Group group = ParserUtil.parseGroup(argMultimap.getValue(PREFIX_GROUP_NAME)).get();
            return new UngroupCommand(index, group);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UngroupCommand.MESSAGE_USAGE));
        }
    }
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
        final UniqueGroupList personGroups = new UniqueGroupList(person.getGroups());
        groups.mergeFrom(personGroups);

        // Create map with values = group object references in the master list
        // used for checking person group references
        final Map<Group, Group> masterGroupObjects = new HashMap<>();
        groups.forEach(group -> masterGroupObjects.put(group, group));

        // Rebuild the list of person groups to point to the relevant groups in the master group list.
        final Set<Group> correctGroupReferences = new HashSet<>();
        personGroups.forEach(group -> correctGroupReferences.add(masterGroupObjects.get(group)));
        person.setGroups(correctGroupReferences);
    }

    /**
     * Ensures that every group in these persons:
     *  - exists in the master list {@link #groups}
     *  - points to a Group object in the master list
     *  @see #syncMasterGroupListWith(Person)
     */
    private void syncMasterGroupListWith(SortedUniquePersonList persons) {
        persons.forEach(this::syncMasterGroupListWith);
    }
```
###### \java\seedu\address\model\group\Group.java
``` java
package seedu.address.model.group;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Group in the address book.
 * Guarantees: immutable; groupName is valid as declared in {@link #isValidGroupName(String)}
 */
public class Group {

    public static final String MESSAGE_GROUP_CONSTRAINTS =
            "Group names should only contain alphanumeric characters and spaces, and it should not be blank";
    /*
    * The first character of the group must not be a whitespace,
    * otherwise " " (a blank string) becomes a valid input.
    */
    public static final String GROUP_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String groupName;

    /**
     * Validates given group groupName.
     *
     * @throws IllegalValueException if the given group groupName string is invalid.
     */
    public Group(String groupName) throws IllegalValueException {
        requireNonNull(groupName);
        String trimmedName = groupName.trim();
        if (!isValidGroupName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_GROUP_CONSTRAINTS);
        }
        this.groupName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid group groupName.
     */
    public static boolean isValidGroupName(String test) {
        return !test.equals("") && test.matches(GROUP_VALIDATION_REGEX);
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

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + groupName + ']';
    }

}
```
###### \java\seedu\address\model\group\UniqueGroupList.java
``` java
package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.exceptions.GroupNotFoundException;

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
    public UniqueGroupList(Set<Group> groups) {
        requireAllNonNull(groups);
        internalList.addAll(groups);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all groups in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Group> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Groups in this list with those in the argument group list.
     */
    public void setGroups(Set<Group> groups) {
        requireAllNonNull(groups);
        internalList.setAll(groups);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every group in the argument list exists in this object.
     */
    public void mergeFrom(UniqueGroupList from) {
        final Set<Group> alreadyInside = this.toSet();
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

    /**
     * Removes a Group from the list.
     *
     * @throws GroupNotFoundException if the Group to remove is not on the list.
     */
    public void remove(Group toRemove) throws GroupNotFoundException {
        requireNonNull(toRemove);
        if (!contains(toRemove)) {
            throw new GroupNotFoundException();
        }
        internalList.remove(toRemove);
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
###### \java\seedu\address\model\Model.java
``` java
    /** Removes the given tag from everyone in the address book and deletes it from the addressBook tag list.
     * Returns false if no such a tag exists and true otherwise
     */
    boolean removeTag(Tag tag) throws DuplicatePersonException, PersonNotFoundException;

    /** Checks if the group has some members. If it does, does nothing,
     * otherwise removes the group from the group list of the addressBook */
    void updateGroups(Group group);

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void updateGroups(Group group) {
        if (!addressBook.getGroupList().contains(group)) {
            return;
        }
        for (ReadOnlyPerson person : addressBook.getPersonList()) {
            if (person.getGroups().contains(group)) {
                return;
            }
        }
        Set<Group> newGroups = addressBook.getGroupList()
                .stream()
                .filter(x -> !x.equals(group))
                .collect(Collectors.toSet());

        addressBook.setGroups(newGroups);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\person\exceptions\GroupNotFoundException.java
``` java
package seedu.address.model.person.exceptions;

/**
 * Signals that the operation is unable to find the specified group.
 */
public class GroupNotFoundException extends Exception {
}
```
###### \java\seedu\address\model\person\Person.java
``` java
    public void setProfilePicture(ProfilePicture profilePicture) {
        this.profilePicture.set(requireNonNull(profilePicture));
    }

    @Override
    public ObjectProperty<ProfilePicture> profilePictureProperty() {
        return profilePicture;
    }

    @Override
    public ProfilePicture getProfilePicture() {
        return profilePicture.get();
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Replaces this person's groups with the groups in the argument group set.
     */
    public void setGroups(Set<Group> replacement) {
        groups.set(new UniqueGroupList(replacement));
    }

    /**
     * Returns an immutable group set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Group> getGroups() {
        return Collections.unmodifiableSet(groups.get().toSet());
    }

    public ObjectProperty<UniqueGroupList> groupProperty() {
        return groups;
    }
```
###### \java\seedu\address\model\person\ProfilePicture.java
``` java
package seedu.address.model.person;

import static seedu.address.model.util.SampleDataUtil.SAMPLE_PICTURE;

import java.io.File;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's profile picture in the address book.
 */
public class ProfilePicture {

    public static final String MESSAGE_FILENAME_CONSTRAINTS = "Profile picture file name invalid";

    public static final String DEFAULT_PICTURE = "default_pic.png";
    private static final String PATH_TO_IMAGE_FOLDER = "/images/";

    public final String value;

    /**
     * Assigns the path to the profile picture
     */
    public ProfilePicture(String fileName) throws IllegalValueException {
        String nameToBeSet = fileName;
        if (fileName.contains("/") || fileName.contains("\\")) {
            File file = new File(fileName);
            if (!file.exists()) {
                nameToBeSet = DEFAULT_PICTURE;
            }
        } else {
            if (!(fileName.equals(DEFAULT_PICTURE) || fileName.equals(SAMPLE_PICTURE))) {
                throw new IllegalValueException(
                        "Wrong file path specified. Perhaps you meant: " + DEFAULT_PICTURE + " ?");
            }
        }
        this.value = nameToBeSet;
    }

    public static String getPath(String value) {
        if (value.contains("/") || value.contains("\\")) {
            File file = new File(value);
            return file.toURI().toString();
        } else {
            return PATH_TO_IMAGE_FOLDER + value;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProfilePicture // instanceof handles nulls
                && this.value.equals(((ProfilePicture) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\SortedUniquePersonList.java
``` java
    /**
     * Sorts the list of unique persons
     */
    public void sort() {
        internalList.sort(Comparator.comparing((ReadOnlyPerson person) -> person.getName().toString()));
    }
```
###### \java\seedu\address\storage\AddressBookStorage.java
``` java
    /**
     * Saves the given {@link ReadOnlyAddressBook} to the fixed temporary location (standard location + "-backup.xml")
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
}
```
###### \java\seedu\address\storage\XmlAdaptedGroup.java
``` java
package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.group.Group;

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
     * Converts a given Group into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedGroup(Group source) {
        groupName = source.groupName;
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
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
    @Override
    public ObservableList<Group> getGroupList() {
        final ObservableList<Group> groups = this.groups.stream().map(g -> {
            try {
                return g.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(groups);
    }
}
```
###### \java\seedu\address\ui\PersonCard.java
``` java
        person.groupProperty().addListener((observable, oldValue, newValue) -> {
            groups.getChildren().clear();
            person.getGroups().forEach(group -> groups.getChildren().add((new Label(group.groupName))));
        });
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Initializes the group labels and sets style to them
     *
     * If the path of the image is valid, initializes the image. Otherwise leaves the picture blank.
     */
    private void initPicture(ReadOnlyPerson person) {
        Image im;
        try {
            im = new Image(getPath(person.getProfilePicture().value));
        } catch (IllegalArgumentException iae) {
            im = new Image(getPath(DEFAULT_PICTURE));
        }
        profilePicture.setPreserveRatio(false);
        profilePicture.setImage(im);
        profilePicture.setFitWidth(90);
        profilePicture.setFitHeight(120);
    }

    /**
     * Initializes the group labels and sets style to them
     */
    private void initGroups(ReadOnlyPerson person) {
        person.getGroups().forEach(group -> groups.getChildren().add(new Label(group.groupName)));
        groups.getChildren()
                .forEach(label -> label
                        .setStyle("-fx-background-color: mediumblue;"
                                + "-fx-effect: dropshadow( one-pass-box , gray , 8 , 0.0 , 2 , 0 );"));
    }
```
###### \resources\view\PersonListCard.fxml
``` fxml
      <FlowPane fx:id="groups" />
```
###### \resources\view\PersonListCard.fxml
``` fxml
   <ImageView fx:id="profilePicture" fitHeight="150.0" fitWidth="92.0" pickOnBounds="true" preserveRatio="true">
      <image>
         <Image url="/images/default_pic.png" />
      </image>
   </ImageView>
```
