# duyson98
###### \java\seedu\address\logic\commands\RetrieveCommand.java
``` java

package seedu.address.logic.commands;

import java.util.StringJoiner;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagContainsKeywordPredicate;

/**
 * Lists all contacts having a certain tag in the address book.
 */
public class RetrieveCommand extends Command {

    public static final String COMMAND_WORD = "retrieve";
    public static final String COMMAND_ALIAS = "re";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Retrieves all persons belonging to an existing tag "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: TAGNAME\n"
            + "Example: " + COMMAND_WORD + " friends";

    public static final String MESSAGE_EMPTY_ARGS = "Please provide a tag name! \n%1$s";

    public static final String MESSAGE_NOT_FOUND = "Tag not found in person list.";

    private final TagContainsKeywordPredicate predicate;

    public RetrieveCommand(TagContainsKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        final int personListSize = model.getFilteredPersonList().size();
        if (personListSize == 0) {
            StringJoiner joiner = new StringJoiner(", ");
            for (Tag tag: model.getAddressBook().getTagList()) {
                joiner.add(tag.toString());
            }
            return new CommandResult(MESSAGE_NOT_FOUND + "\n"
                    + "You may want to refer to the following existing tags: "
                    + joiner.toString());
        }
        return new CommandResult(getMessageForPersonListShownSummary(personListSize));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RetrieveCommand // instanceof handles nulls
                && this.predicate.equals(((RetrieveCommand) other).predicate)); // state check
    }

}
```
###### \java\seedu\address\logic\commands\TagCommand.java
``` java

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Tags one or more persons identified using their last displayed targetIndexes from the address book.
 */
public class TagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "tag";
    public static final String COMMAND_ALIAS = "t";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Tags one or more persons identified by the index numbers used in the last person listing.\n"
            + "Parameters: INDEX,[MORE_INDEXES]... (must be positive integers) + TAGNAME\n"
            + "Example: " + COMMAND_WORD + " 1,2,3 friends";

    public static final String MESSAGE_SUCCESS = "%d persons successfully tagged with %s:";
    public static final String MESSAGE_PERSONS_ALREADY_HAVE_TAG = "%d person(s) already have this tag:";

    public static final String MESSAGE_EMPTY_INDEX_LIST = "Please provide one or more indexes! \n%1$s";
    public static final String MESSAGE_INVALID_INDEXES = "One or more person indexes provided are invalid.";
    public static final String MESSAGE_DUPLICATE_TAG = "One or more persons already have this tag.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private List<Index> targetIndexes;
    private Tag tag;

    /**
     * @param targetIndexes of the person in the filtered person list to tag
     * @param tag of the person
     */
    public TagCommand(List<Index> targetIndexes, Tag tag) {
        requireNonNull(targetIndexes);
        requireNonNull(tag);

        this.targetIndexes = targetIndexes;
        this.tag = tag;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        for (Index targetIndex : targetIndexes) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(MESSAGE_INVALID_INDEXES);
            }
        }

        ArrayList<ReadOnlyPerson> alreadyTaggedPersons = new ArrayList<>();
        ArrayList<ReadOnlyPerson> toBeTaggedPersons = new ArrayList<>();
        for (Index targetIndex : targetIndexes) {
            ReadOnlyPerson personToTag = lastShownList.get(targetIndex.getZeroBased());
            Person taggedPerson = new Person(personToTag);
            UniqueTagList updatedTags = new UniqueTagList(personToTag.getTags());
            if (updatedTags.contains(tag)) {
                alreadyTaggedPersons.add(personToTag);
                continue;
            }

            toBeTaggedPersons.add(personToTag);

            try {
                updatedTags.add(tag);
            } catch (UniqueTagList.DuplicateTagException e) {
                throw new CommandException(MESSAGE_DUPLICATE_TAG);
            }

            taggedPerson.setTags(updatedTags.toSet());

            try {
                model.updatePerson(personToTag, taggedPerson);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
        }

        StringJoiner toBeTaggedJoiner = new StringJoiner(", ");
        for (ReadOnlyPerson person : toBeTaggedPersons) {
            toBeTaggedJoiner.add(person.getName().toString());
        }
        if (alreadyTaggedPersons.size() > 0) {
            StringJoiner alreadyTaggedJoiner = new StringJoiner(", ");
            for (ReadOnlyPerson person : alreadyTaggedPersons) {
                alreadyTaggedJoiner.add(person.getName().toString());
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS,
                    targetIndexes.size() - alreadyTaggedPersons.size(), tag.toString()) + " "
                    + toBeTaggedJoiner.toString() + "\n"
                    + String.format(MESSAGE_PERSONS_ALREADY_HAVE_TAG, alreadyTaggedPersons.size()) + " "
                    + alreadyTaggedJoiner.toString());
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                targetIndexes.size(), tag.toString()) + " " + toBeTaggedJoiner.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagCommand // instanceof handles nulls
                && this.targetIndexes.equals(((TagCommand) other).targetIndexes)) // state check
                && this.tag.equals(((TagCommand) other).tag); // state check
    }

}

```
###### \java\seedu\address\logic\commands\UntagCommand.java
``` java

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Untags one or more persons identified using their last displayed targetIndexes from the address book.
 */
public class UntagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "untag";
    public static final String COMMAND_ALIAS = "ut";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Untags one or more persons in the last person listing.\n"
            + "- Untag all tags of persons identified by the index numbers used\n"
            + "Parameters: INDEX,[MORE_INDEXES]... (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1,2,3\n"
            + "- Untag one or more tags of persons identified by the index numbers used\n"
            + "Parameters: INDEX,[MORE_INDEXES]... (must be positive integers) + TAGNAME\n"
            + "Example: " + COMMAND_WORD + " 1,2,3 friends/colleagues\n"
            + "- Untag all tags of all persons in the last person listing\n"
            + "Parameters: -a\n"
            + "Example: " + COMMAND_WORD + " -a\n"
            + "- Untag one or more tags of all persons in the last person listing\n"
            + "Parameters: -a + TAGNAME\n"
            + "Example: " + COMMAND_WORD + " -a friends/colleagues";

    public static final String MESSAGE_SUCCESS = "%d person(s) successfully untagged from %s:";
    public static final String MESSAGE_SUCCESS_ALL_TAGS = "%d person(s) sucessfully untagged:";
    public static final String MESSAGE_SUCCESS_MULTIPLE_TAGS_IN_LIST = "%s tag(s) successfully removed.";
    public static final String MESSAGE_SUCCESS_ALL_TAGS_IN_LIST = "All tags in the list successfully removed.";

    public static final String MESSAGE_TAG_NOT_FOUND = "Tag not found.";
    public static final String MESSAGE_PERSONS_DO_NOT_HAVE_TAGS = "%d person(s) do not have any of the specified tags:";
    public static final String MESSAGE_EMPTY_INDEX_LIST = "Please provide one or more indexes! \n%1$s";
    public static final String MESSAGE_INVALID_INDEXES = "One or more person indexes provided are invalid.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private boolean toAllInFilteredList;
    private List<Index> targetIndexes;
    private List<Tag> tags;

    /**
     * @param targetIndexes of the persons in the filtered person list to untag
     * @param tags of the persons
     */
    public UntagCommand(boolean toAllInFilteredList, List<Index> targetIndexes, List<Tag> tags) {
        requireNonNull(targetIndexes);
        requireNonNull(tags);

        this.toAllInFilteredList = toAllInFilteredList;
        this.targetIndexes = targetIndexes;
        this.tags = tags;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (toAllInFilteredList) {
            removeTagsFromPersons(lastShownList, tags);
            deleteUnusedTagsInTagList(new ArrayList<>(tags.isEmpty()
                    ? new ArrayList<>(model.getAddressBook().getTagList()) : tags));
            return (tags.isEmpty()) ? new CommandResult(MESSAGE_SUCCESS_ALL_TAGS_IN_LIST)
                    : new CommandResult(String.format(MESSAGE_SUCCESS_MULTIPLE_TAGS_IN_LIST, joinList(tags)));
        }

        for (Index targetIndex : targetIndexes) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(MESSAGE_INVALID_INDEXES);
            }
        }

        List<ReadOnlyPerson> personsToUpdate = new ArrayList<>();
        List<Name> toBeUntaggedPersonNames = new ArrayList<>();
        if (tags.isEmpty()) {
            for (Index targetIndex : targetIndexes) {
                ReadOnlyPerson person = lastShownList.get(targetIndex.getZeroBased());
                personsToUpdate.add(person);
                toBeUntaggedPersonNames.add(person.getName());
            }
            removeTagsFromPersons(personsToUpdate, tags);
            deleteUnusedTagsInTagList(new ArrayList<>(model.getAddressBook().getTagList()));
            return new CommandResult(String.format(MESSAGE_SUCCESS_ALL_TAGS, targetIndexes.size()) + " "
                    + joinList(toBeUntaggedPersonNames));
        }

        List<Name> alreadyUntaggedPersonNames = new ArrayList<>();
        for (Index targetIndex : targetIndexes) {
            ReadOnlyPerson person = lastShownList.get(targetIndex.getZeroBased());
            if (Collections.disjoint(person.getTags(), tags)) {
                alreadyUntaggedPersonNames.add(person.getName());
                continue;
            }
            personsToUpdate.add(person);
            toBeUntaggedPersonNames.add(person.getName());
        }

        removeTagsFromPersons(personsToUpdate, tags);
        deleteUnusedTagsInTagList(tags);

        if (alreadyUntaggedPersonNames.size() > 0) {
            return new CommandResult(String.format(MESSAGE_SUCCESS,
                    targetIndexes.size() - alreadyUntaggedPersonNames.size(), joinList(tags)) + " "
                    + joinList(toBeUntaggedPersonNames) + "\n"
                    + String.format(MESSAGE_PERSONS_DO_NOT_HAVE_TAGS, alreadyUntaggedPersonNames.size()) + " "
                    + joinList(alreadyUntaggedPersonNames));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                targetIndexes.size(), joinList(tags)) + " " + joinList(toBeUntaggedPersonNames));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UntagCommand // instanceof handles nulls
                && this.targetIndexes.equals(((UntagCommand) other).targetIndexes))
                && this.tags.equals(((UntagCommand) other).tags); // state check
    }

    /**
     * Removes a tag from the person
     * Removes all tags if tag is not specified
     * @param persons to be untagged
     * @param tags to be removed
     */
    private void removeTagsFromPersons(List<ReadOnlyPerson> persons, List<Tag> tags) throws CommandException {
        for (ReadOnlyPerson person : persons) {
            Person untaggedPerson = new Person(person);
            UniqueTagList updatedTags = new UniqueTagList();
            if (!tags.isEmpty()) {
                updatedTags = new UniqueTagList(person.getTags());
                for (Tag t : tags) {
                    updatedTags.remove(t);
                }
            }
            untaggedPerson.setTags(updatedTags.toSet());

            try {
                model.updatePerson(person, untaggedPerson);
            } catch (DuplicatePersonException e) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException e) {
                throw new AssertionError("The target person cannot be missing");
            }
        }
    }

    private void deleteUnusedTagsInTagList(List<Tag> tags) {
        for (Tag tag : tags) {
            model.deleteUnusedTag(tag);
        }
    }

    /**
     * Join all list elements by commas
     * @param list to be joined
     */
    private String joinList(List list) {
        StringJoiner joiner = new StringJoiner(", ");
        for (Object obj : list) {
            joiner.add(obj.toString());
        }
        return joiner.toString();
    }

}
```
###### \java\seedu\address\logic\parser\RetrieveCommandParser.java
``` java

package seedu.address.logic.parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RetrieveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagContainsKeywordPredicate;

/**
 * Parses input arguments and creates a new RetrieveCommand object
 */
public class RetrieveCommandParser implements Parser<RetrieveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RetrieveCommand
     * and returns an RetrieveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RetrieveCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(RetrieveCommand.MESSAGE_EMPTY_ARGS, RetrieveCommand.MESSAGE_USAGE));
        }
        try {
            return new RetrieveCommand(new TagContainsKeywordPredicate(new Tag(trimmedArgs)));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

    }

}
```
###### \java\seedu\address\logic\parser\TagCommandParser.java
``` java

package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.TagCommand.MESSAGE_EMPTY_INDEX_LIST;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new TagCommand object
 */
public class TagCommandParser implements Parser<TagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagCommand
     * and returns a TagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        String[] splittedArgs = trimmedArgs.split("\\s+");
        if (trimmedArgs.isEmpty() || splittedArgs.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

        Set<String> uniqueIndexes = new HashSet<>(Arrays.asList(splittedArgs[0].split(",")));
        if (uniqueIndexes.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_EMPTY_INDEX_LIST, TagCommand.MESSAGE_USAGE));
        }
        List<Index> indexList = new ArrayList<>();
        try {
            for (String indexArg : uniqueIndexes) {
                indexList.add(ParserUtil.parseIndex(indexArg));
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

        try {
            Tag tag = new Tag(splittedArgs[1]);
            return new TagCommand(indexList, tag);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

}

```
###### \java\seedu\address\logic\parser\UntagCommandParser.java
``` java

package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.UntagCommand.MESSAGE_EMPTY_INDEX_LIST;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UntagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new UntagCommand object
 */
public class UntagCommandParser implements Parser<UntagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UntagCommand
     * and returns a UntagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UntagCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String trimmedArgs = args.trim();
        if (trimmedArgs.equals("-a")) {
            return new UntagCommand(true, Collections.emptyList(), Collections.emptyList());
        }
        String[] splittedArgs = trimmedArgs.split("\\s+");
        if (trimmedArgs.isEmpty() || splittedArgs.length != 1 && splittedArgs.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
        }

        List<Tag> tagList = new ArrayList<>();
        if (splittedArgs.length == 2) {
            Set<String> uniqueTags = new HashSet<>(Arrays.asList(splittedArgs[1].split("/")));
            try {
                for (String tagArg : uniqueTags) {
                    tagList.add(new Tag(tagArg));
                }
            } catch (IllegalValueException ive) {
                throw new ParseException(ive.getMessage(), ive);
            }
        }

        List<Index> indexList = new ArrayList<>();
        if (splittedArgs[0].equals("-a")) {
            return new UntagCommand(true, indexList, tagList);
        }
        Set<String> uniqueIndexes = new HashSet<>(Arrays.asList(splittedArgs[0].split(",")));
        if (uniqueIndexes.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_EMPTY_INDEX_LIST, UntagCommand.MESSAGE_USAGE));
        }
        try {
            for (String indexArg : uniqueIndexes) {
                indexList.add(ParserUtil.parseIndex(indexArg));
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
        }

        return new UntagCommand(false, indexList, tagList);
    }

}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    //// reminder-level operations

    /**
     * Adds a reminder to the address book.
     * Also checks the new reminder's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the reminder to point to those in {@link #tags}.
     *
     * @throws DuplicateReminderException if an equivalent reminder already exists.
     */
    public void addReminder(ReadOnlyReminder p) throws DuplicateReminderException {
        Reminder newReminder = new Reminder(p);
        syncMasterTagListWith(newReminder);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any reminder
        // in the reminder list.
        reminders.add(newReminder);
    }

    /**
     * Replaces the given reminder {@code target} in the list with {@code editedReadOnlyReminder}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedReadOnlyReminder}.
     *
     * @throws DuplicateReminderException if updating the reminder's details causes the reminder to be equivalent to
     *      another existing reminder in the list.
     * @throws ReminderNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncMasterTagListWith(Reminder)
     */
    public void updateReminder(ReadOnlyReminder target, ReadOnlyReminder editedReadOnlyReminder)
            throws DuplicateReminderException, ReminderNotFoundException {
        requireNonNull(editedReadOnlyReminder);

        Reminder editedReminder = new Reminder(editedReadOnlyReminder);
        syncMasterTagListWith(editedReminder);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any reminder
        // in the reminder list.
        reminders.setReminder(target, editedReminder);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws ReminderNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeReminder(ReadOnlyReminder key) throws ReminderNotFoundException {
        if (reminders.remove(key)) {
            return true;
        } else {
            throw new ReminderNotFoundException();
        }
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Ensures that every tag in this reminder:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(Reminder reminder) {
        final UniqueTagList reminderTags = new UniqueTagList(reminder.getTags());
        tags.mergeFrom(reminderTags);

        // Create map with values = tag object references in the master list
        // used for checking reminder tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of reminder tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        reminderTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        reminder.setTags(correctTagReferences);
    }

    /**
     * Ensures that every tag in these reminders:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     *  @see #syncMasterTagListWith(Reminder)
     */
    private void syncMasterTagListWith(UniqueReminderList reminders) {
        reminders.forEach(this::syncMasterTagListWith);
    }
```
###### \java\seedu\address\model\clock\ClockDisplay.java
``` java

package seedu.address.model.clock;

import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Represents the clock display on the GUI of the address book application.
 */
public class ClockDisplay {

    private ObjectProperty<RunningTime> time;
    private ObjectProperty<RunningDate> date;

    public ClockDisplay() {
        this.time = new SimpleObjectProperty<>(new RunningTime());
        this.date = new SimpleObjectProperty<>(new RunningDate());
    }

    public void setTime(RunningTime time) {
        this.time.set(time);
    }

    public ObjectProperty<RunningTime> timeProperty() {
        return time;
    }

    public RunningTime getTime() {
        return time.get();
    }

    public void setDate(RunningDate date) {
        this.date.set(date);
    }

    public ObjectProperty<RunningDate> dateProperty() {
        return date;
    }

    public RunningDate getDate() {
        return date.get();
    }

    public String getTimeAsText() {
        return time.get().toString();
    }

    public String getDateAsText() {
        return date.get().toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClockDisplay // instanceof handles nulls
                && this.time.equals(((ClockDisplay) other).time) // state checks onwards
                && this.date.equals(((ClockDisplay) other).date));
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, date);
    }

}
```
###### \java\seedu\address\model\clock\RunningTime.java
``` java

package seedu.address.model.clock;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a running time inside the address book application.
 */
public class RunningTime {

    private int hour;
    private int minute;
    private int second;

    public RunningTime() {
        setCurrentTime();
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public void setCurrentTime() {
        this.hour = LocalDateTime.now().getHour();
        this.minute = LocalDateTime.now().getMinute();
        this.second = LocalDateTime.now().getSecond();
    }

    @Override
    public String toString() {
        return ((hour < 10) ? ("0" + hour) : hour) + ":"
                + ((minute < 10) ? ("0" + minute) : minute) + ":"
                + ((second < 10) ? ("0" + second) : second);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RunningTime // instanceof handles nulls
                && this.hour == ((RunningTime) other).hour // state checks onwards
                && this.minute == ((RunningTime) other).minute
                && this.second == ((RunningTime) other).second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hour, minute, second);
    }

}
```
###### \java\seedu\address\model\Model.java
``` java
    //// reminder-level operations

    /** Deletes the given reminder. */
    void deleteReminder(ReadOnlyReminder target) throws ReminderNotFoundException;

    /** Adds the given reminder */
    void addReminder(ReadOnlyReminder reminder) throws DuplicateReminderException;

    /**
     * Replaces the given reminder {@code target} with {@code editedReminder}.
     *
     * @throws DuplicateReminderException if updating the reminder's details causes the reminder to be equivalent to
     *      another existing reminder in the list.
     * @throws ReminderNotFoundException if {@code target} could not be found in the list.
     */
    void updateReminder(ReadOnlyReminder target, ReadOnlyReminder editedReminder)
            throws DuplicateReminderException, ReminderNotFoundException;

    void deleteReminderTag(Tag tag) throws ReminderNotFoundException, DuplicateReminderException;

    //// tag-level operations

    /** Checks if the given tag is unused by any person and deletes it if necessary. */
    void deleteUnusedTag(Tag tag);

    /** Returns an unmodifiable view of the filtered reminder list */
    ObservableList<ReadOnlyReminder> getFilteredReminderList();

    /**
     * Updates the filter of the filtered reminder list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredReminderList(Predicate<ReadOnlyReminder> predicate);

    /**
     * Checks if list is empty
     * Returns true if is empty
     */
    Boolean checkIfReminderListEmpty(ArrayList<ReadOnlyReminder> reminderList);
```
###### \java\seedu\address\model\ModelManager.java
``` java
    //// reminder-level operations

    @Override
    public synchronized void deleteReminder(ReadOnlyReminder target) throws ReminderNotFoundException {
        addressBook.removeReminder(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addReminder(ReadOnlyReminder reminder) throws DuplicateReminderException {
        addressBook.addReminder(reminder);
        updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
        indicateAddressBookChanged();
    }

    @Override
    public void updateReminder(ReadOnlyReminder target, ReadOnlyReminder editedReminder)
            throws DuplicateReminderException, ReminderNotFoundException {
        requireAllNonNull(target, editedReminder);
        addressBook.updateReminder(target, editedReminder);
        indicateAddressBookChanged();
    }

    @Override
    public void deleteReminderTag(Tag tag) throws ReminderNotFoundException, DuplicateReminderException  {
        for (int i = 0; i < addressBook.getReminderList().size(); i++) {
            ReadOnlyReminder oldReminder = addressBook.getReminderList().get(i);

            Reminder newReminder = new Reminder(oldReminder);
            Set<Tag> newTags = newReminder.getTags();
            newTags.remove(tag);
            newReminder.setTags(newTags);

            addressBook.updateReminder(oldReminder, newReminder);
        }
    }

    @Override
    public synchronized void deleteAccount(ReadOnlyAccount target) throws PersonNotFoundException {
        database.removeAccount(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addAccount(ReadOnlyAccount account) throws DuplicateAccountException {
        database.addAccount(account);
        updateFilteredAccountList(PREDICATE_SHOW_ALL_ACCOUNTS);
        indicateDatabaseChanged();
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    //=========== Filtered Reminder List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyReminder} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyReminder> getFilteredReminderList() {
        logger.info("it came here");

        return FXCollections.unmodifiableObservableList(filteredReminders);
    }

    @Override
    public void updateFilteredReminderList(Predicate<ReadOnlyReminder> predicate) {
        requireNonNull(predicate);
        filteredReminders.setPredicate(predicate);
    }

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
                && filteredReminders.equals(other.filteredReminders);
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public Boolean checkIfReminderListEmpty(ArrayList<ReadOnlyReminder> contactList) {
        if (filteredReminders.isEmpty()) {
            return true;
        }
        return false;
    }
```
###### \java\seedu\address\model\reminder\Date.java
``` java

package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a reminder's date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date should conform the following format: dd/mm/yyyy hh:mm";

    public final String date;

    /**
     * Validates given date.
     */
    public Date(String dateAndTime) throws IllegalValueException {
        requireNonNull(dateAndTime);
        if (!isValidDate(dateAndTime)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        String[] splittedDateAndTime = dateAndTime.trim().split("\\s+");
        String date = splittedDateAndTime[0].trim();
        String time = splittedDateAndTime[1].trim();

        this.date = date + " " + time;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String dateAndTime) {
        String[] splittedDateAndTime = dateAndTime.trim().split("\\s+");
        if (splittedDateAndTime.length != 2) {
            return false;
        }

        final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            String date = splittedDateAndTime[0].trim();
            LocalDate.parse(date, dateFormatter);
            String time = splittedDateAndTime[1].trim();
            LocalTime.parse(time, timeFormatter);
        } catch (DateTimeParseException dtpe) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && this.date.equals(((Date) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
```
###### \java\seedu\address\model\reminder\exceptions\DuplicateReminderException.java
``` java

package seedu.address.model.reminder.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Reminder objects.
 */
public class DuplicateReminderException extends DuplicateDataException {
    public DuplicateReminderException() {
        super("Operation would result in duplicate reminders");
    }
}
```
###### \java\seedu\address\model\reminder\exceptions\ReminderNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified reminder.
 */
public class ReminderNotFoundException extends Exception {
}
```
###### \java\seedu\address\model\reminder\Message.java
``` java

package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;

/**
 * Represents a reminder's message in the address book.
 * Guarantees: immutable
 */
public class Message {

    public final String message;

    /**
     * Validates given message.
     */
    public Message(String message) {
        requireNonNull(message);
        this.message = message.trim();
    }

    public static boolean isValidMessage(String message) {
        return true;
    }

    @Override
    public String toString() {
        return message;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Message // instanceof handles nulls
                && this.message.equals(((Message) other).message)); // state check
    }

    @Override
    public int hashCode() {
        return message.hashCode();
    }

}
```
###### \java\seedu\address\model\reminder\Priority.java
``` java

package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a reminder's priority in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {

    public static final String MESSAGE_PRIORITY_CONSTRAINTS =
            "Priority can only be Low / Medium / High";
    public static final String PRIORITY_VALIDATION_REGEX = "(?:Low|Medium|High)";
    public final String value;

    /**
     * Validates given priority word.
     *
     * @throws IllegalValueException if given priority string is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        requireNonNull(priority);
        String trimmedPriority = priority.trim();
        if (!isValidPriority(trimmedPriority)) {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
        this.value = trimmedPriority;
    }

    /**
     * Returns true if a given string is a valid reminder priority word.
     */
    public static boolean isValidPriority(String test) {
        return test.matches(PRIORITY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.value.equals(((Priority) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\reminder\ReadOnlyReminder.java
``` java

package seedu.address.model.reminder;

import java.util.Set;

import javafx.beans.property.ObjectProperty;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Reminder in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyReminder {

    ObjectProperty<Task> taskProperty();
    Task getTask();
    ObjectProperty<Priority> priorityProperty();
    Priority getPriority();
    ObjectProperty<Date> dateProperty();
    Date getDate();
    ObjectProperty<Message> messageProperty();
    Message getMessage();
    ObjectProperty<UniqueTagList> tagProperty();
    Set<Tag> getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyReminder other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTask().equals(this.getTask()) // state checks here onwards
                && other.getPriority().equals(this.getPriority())
                && other.getDate().equals(this.getDate())
                && other.getMessage().equals(this.getMessage()));
    }

    /**
     * Formats the reminder as text, showing all reminder details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTask())
                .append(" Priority: ")
                .append(getPriority())
                .append(" Date: ")
                .append(getDate())
                .append(" Message: ")
                .append(getMessage())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
```
###### \java\seedu\address\model\reminder\Reminder.java
``` java

package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Reminder in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Reminder implements ReadOnlyReminder {
    private ObjectProperty<Task> task;
    private ObjectProperty<Priority> priority;
    private ObjectProperty<Date> date;
    private ObjectProperty<Message> message;

    private ObjectProperty<UniqueTagList> tags;

    /**
     * Every field must be present and not null.
     */
    public Reminder(Task task, Priority priority, Date date, Message message, Set<Tag> tags) {
        requireAllNonNull(task, priority, date, message, tags);
        this.task = new SimpleObjectProperty<>(task);
        this.priority = new SimpleObjectProperty<>(priority);
        this.date = new SimpleObjectProperty<>(date);
        this.message = new SimpleObjectProperty<>(message);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
    }

    /**
     * Creates a copy of the given ReadOnlyReminder.
     */
    public Reminder(ReadOnlyReminder source) {
        this(source.getTask(), source.getPriority(), source.getDate(), source.getMessage(), source.getTags());
    }

    public void setTask(Task task) {
        this.task.set(requireNonNull(task));
    }

    @Override
    public ObjectProperty<Task> taskProperty() {
        return task;
    }

    @Override
    public Task getTask() {
        return task.get();
    }

    public void setPriority(Priority priority) {
        this.priority.set(requireNonNull(priority));
    }

    @Override
    public ObjectProperty<Priority> priorityProperty() {
        return priority;
    }

    @Override
    public Priority getPriority() {
        return priority.get();
    }

    public void setDate(Date date) {
        this.date.set(requireNonNull(date));
    }

    @Override
    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    @Override
    public Date getDate() {
        return date.get();
    }

    public void setMessage(Message message) {
        this.message.set(requireNonNull(message));
    }

    @Override
    public ObjectProperty<Message> messageProperty() {
        return message;
    }

    @Override
    public Message getMessage() {
        return message.get();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.get().toSet());
    }

    public ObjectProperty<UniqueTagList> tagProperty() {
        return tags;
    }

    /**
     * Replaces this reminder's tags with the tags in the argument tag set.
     */
    public void setTags(Set<Tag> replacement) {
        tags.set(new UniqueTagList(replacement));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyReminder // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyReminder) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(task, priority, date, message, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    /**
     * @param replacement
     */
    public void resetData(ReadOnlyReminder replacement) {
        requireNonNull(replacement);

        this.setTask(replacement.getTask());
        this.setPriority(replacement.getPriority());
        this.setDate(replacement.getDate());
        this.setMessage(replacement.getMessage());
        this.setTags(replacement.getTags());
    }
}
```
###### \java\seedu\address\model\reminder\Task.java
``` java

package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a reminder's task name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTaskName(String)}
 */
public class Task {

    public static final String MESSAGE_TASK_NAME_CONSTRAINTS =
            "Tasks should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TASK_NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String taskName;

    /**
     * Validates given task.
     *
     * @throws IllegalValueException if given task name string is invalid.
     */
    public Task(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidTaskName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_TASK_NAME_CONSTRAINTS);
        }
        this.taskName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid reminder task name.
     */
    public static boolean isValidTaskName(String test) {
        return test.matches(TASK_NAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return taskName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Task // instanceof handles nulls
                && this.taskName.equals(((Task) other).taskName)); // state check
    }

    @Override
    public int hashCode() {
        return taskName.hashCode();
    }

}
```
###### \java\seedu\address\model\reminder\UniqueReminderList.java
``` java

package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;
import seedu.address.model.reminder.exceptions.ReminderNotFoundException;

/**
 * A list of reminders that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Reminder#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueReminderList implements Iterable<Reminder> {

    private final ObservableList<Reminder> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyReminder> mappedList = EasyBind.map(internalList, (reminder) -> reminder);

    /**
     * Returns true if the list contains an equivalent reminder as the given argument.
     */
    public boolean contains(ReadOnlyReminder toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a reminder to the list.
     *
     * @throws DuplicateReminderException if the reminder to add is a duplicate of an existing reminder in the list.
     */
    public void add(ReadOnlyReminder toAdd) throws DuplicateReminderException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateReminderException();
        }
        internalList.add(new Reminder(toAdd));
    }

    /**
     * Replaces the reminder {@code target} in the list with {@code editedReminder}.
     *
     * @throws DuplicateReminderException if the replacement is equivalent to another existing reminder in the list.
     * @throws ReminderNotFoundException if {@code target} could not be found in the list.
     */
    public void setReminder(ReadOnlyReminder target, ReadOnlyReminder editedReminder)
            throws DuplicateReminderException, ReminderNotFoundException {
        requireNonNull(editedReminder);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ReminderNotFoundException();
        }

        if (!target.equals(editedReminder) && internalList.contains(editedReminder)) {
            throw new DuplicateReminderException();
        }

        internalList.set(index, new Reminder(editedReminder));
    }

    /**
     * Removes the equivalent reminder from the list.
     *
     * @throws ReminderNotFoundException if no such reminder could be found in the list.
     */
    public boolean remove(ReadOnlyReminder toRemove) throws ReminderNotFoundException {
        requireNonNull(toRemove);
        final boolean reminderFoundAndDeleted = internalList.remove(toRemove);
        if (!reminderFoundAndDeleted) {
            throw new ReminderNotFoundException();
        }
        return reminderFoundAndDeleted;
    }

    public void setReminders(UniqueReminderList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setReminders(List<? extends ReadOnlyReminder> reminders) throws DuplicateReminderException {
        final UniqueReminderList replacement = new UniqueReminderList();
        for (final ReadOnlyReminder reminder : reminders) {
            replacement.add(new Reminder(reminder));
        }
        setReminders(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyReminder> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    @Override
    public Iterator<Reminder> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueReminderList // instanceof handles nulls
                && this.internalList.equals(((UniqueReminderList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### \java\seedu\address\model\tag\TagContainsKeywordPredicate.java
``` java

package seedu.address.model.tag;

import java.util.function.Predicate;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tag} matches the keyword given.
 */
public class TagContainsKeywordPredicate implements Predicate<ReadOnlyPerson> {

    private final Tag tag;

    public TagContainsKeywordPredicate(Tag tag) {
        this.tag = tag;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return person.getTags().contains(tag);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordPredicate // instanceof handles nulls
                && this.tag.equals(((TagContainsKeywordPredicate) other).tag)); // state check
    }

}
```
###### \java\seedu\address\storage\XmlAdaptedReminder.java
``` java

package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.reminder.Date;
import seedu.address.model.reminder.Message;
import seedu.address.model.reminder.Priority;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.Task;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Reminder.
 */
public class XmlAdaptedReminder {

    @XmlElement(required = true)
    private String taskName;
    @XmlElement(required = true)
    private String priority;
    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String message;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedReminder.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedReminder() {}


    /**
     * Converts a given Reminder into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedReminder
     */
    public XmlAdaptedReminder(ReadOnlyReminder source) {
        taskName = source.getTask().taskName;
        priority = source.getPriority().value;
        date = source.getDate().date;
        message = source.getMessage().message;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted reminder object into the model's Reminder object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted reminder
     */
    public Reminder toModelType() throws IllegalValueException {
        final List<Tag> reminderTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            reminderTags.add(tag.toModelType());
        }
        final Task taskName = new Task(this.taskName);
        final Priority priority = new Priority(this.priority);
        final Date date = new Date(this.date);
        final Message message = new Message(this.message);
        final Set<Tag> tags = new HashSet<>(reminderTags);
        return new Reminder(taskName, priority, date, message, tags);
    }
}
```
###### \java\seedu\address\ui\StatusBarFooter.java
``` java
    /**
     * Starts running the clock display.
     */
    private void startFooterClock(ClockDisplay footerClock) {
        final Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                setFooterClockTime(footerClock);
                setFooterClockDate(footerClock);
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    private void setFooterClockTime(ClockDisplay footerClock) {
        requireNonNull(footerClock.getTime());

        footerClock.getTime().setCurrentTime();
        Platform.runLater(() -> displayTime.setText(footerClock.getTimeAsText()));
    }

    private void setFooterClockDate(ClockDisplay footerClock) {
        requireNonNull(footerClock.getDate());

        footerClock.getDate().setCurrentDate();
        Platform.runLater(() -> displayDate.setText(footerClock.getDateAsText()));
    }
```
###### \resources\view\DarkTheme.css
``` css
#personListHeading {
    -fx-background-color: deepskyblue;
    -fx-border-color: white;
}

#personListHeading .label {
    -fx-font-style: italic;
    -fx-font-weight: bolder;
    -fx-font-size: 25px;
    -fx-text-fill: white;
}

#reminderListHeading {
    -fx-background-color: deepskyblue;
    -fx-border-color: white;
}

#reminderListHeading .label {
    -fx-font-style: italic;
    -fx-font-weight: bolder;
    -fx-font-size: 25px;
    -fx-text-fill: white;
}

#displayDate {
    -fx-font-family: 'Nova Square';
    -fx-font-size: 25;
    -fx-text-fill: white;
}

#displayTime {
    -fx-padding: 0 150 0 0;
    -fx-font-family: 'Digital-7 Italic';
    -fx-font-size: 50;
    -fx-text-fill: white;
}

.opening-img {
    height: 584px;
}

#profilePic {
    -fx-border-color: deepskyblue;
}

#profileInfo {
    -fx-border-color: deepskyblue;
}

#profileName {
    -fx-font-family: 'HaloHandletter';
    -fx-font-size: 50px;
}

.profile_big_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 20px;
    -fx-font-style: italic;
    -fx-text-fill: white;
}

.profile_small_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 16px;
    -fx-text-fill: white;
}

.profile_small_text_area {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 18px;
}

.reminder_big_label {
    -fx-font-size: 15px;
}
```
###### \resources\view\ReminderListPanel.fxml
``` fxml

<?import javafx.scene.control.Label?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <AnchorPane fx:id="personListHeading" prefHeight="55.0" prefWidth="248.0">
        <children>
            <ImageView fitHeight="50.0" fitWidth="50.0" layoutX="58.0" pickOnBounds="true" preserveRatio="true" AnchorPane.bottomAnchor="2.0" AnchorPane.topAnchor="2.0">
                <image>
                    <Image url="@../images/reminder-icon.png" />
                </image>
            </ImageView>
            <Label layoutX="116.0" layoutY="8.0" prefHeight="38.0" prefWidth="125.0" text="Reminders" underline="true">
            </Label>
        </children>
    </AnchorPane>
    <ListView fx:id="reminderListView" VBox.vgrow="ALWAYS" />
</VBox>
```
