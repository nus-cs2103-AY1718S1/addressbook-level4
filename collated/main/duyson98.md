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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Retrieves all persons belonging to an existing tag "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: TAGNAME\n"
            + "Example: " + COMMAND_WORD + " friends";

    public static final String MESSAGE_EMPTY_ARGS = "Please provide a tag name! \n%1$s";

    public static final String MESSAGE_NOT_FOUND = "Tag not found.";

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
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

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

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Tags one or more persons identified by the index numbers used in the last person listing.\n"
            + "Parameters: INDEX,[MORE_INDEXES]... (must be positive integers) + TAGNAME\n"
            + "Example: " + COMMAND_WORD + " 1,2,3 friends";

    public static final String MESSAGE_SUCCESS = "%d persons successfully tagged with %s:";
    public static final String MESSAGE_PERSONS_ALREADY_HAVE_TAG = "%d persons already have this tag:";

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

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
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
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
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

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Untags one or more persons identified by the index numbers used in the last person listing.\n"
            + "Parameters: INDEX,[MORE_INDEXES]... (must be positive integers) + TAGNAME\n"
            + "Example: " + COMMAND_WORD + " 1,2,3 friends";

    public static final String MESSAGE_SUCCESS = "%d persons successfully untagged from %s:";
    public static final String MESSAGE_PERSONS_DO_NOT_HAVE_TAG = "%d persons do not have this tag:";

    public static final String MESSAGE_EMPTY_INDEX_LIST = "Please provide one or more indexes! \n%1$s";
    public static final String MESSAGE_INVALID_INDEXES = "One or more person indexes provided are invalid.";
    public static final String MESSAGE_DUPLICATE_TAG = "One or more persons already have this tag.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private boolean toAll;
    private List<Index> targetIndexes;
    private Tag tag;

    public UntagCommand() {
        this.toAll = true;
    }

    /**
     * @param tag of the person
     */
    public UntagCommand(Tag tag) {
        requireNonNull(tag);

        this.toAll = true;
        this.tag = tag;
    }

    /**
     * @param targetIndexes of the person in the filtered person list to untag
     * @param tag of the person
     */
    public UntagCommand(List<Index> targetIndexes, Tag tag) {
        requireNonNull(targetIndexes);
        requireNonNull(tag);

        this.toAll = false;
        this.targetIndexes = targetIndexes;
        this.tag = tag;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        if (toAll && tag != null) {
            for (ReadOnlyPerson personToUntag : lastShownList) {
                Person untaggedPerson = new Person(personToUntag);
                UniqueTagList updatedTags = new UniqueTagList(personToUntag.getTags());
                updatedTags.remove(tag);
                untaggedPerson.setTags(updatedTags.toSet());
                try {
                    model.updatePerson(personToUntag, untaggedPerson);
                } catch (DuplicatePersonException e) {
                    throw new CommandException(MESSAGE_DUPLICATE_PERSON);
                } catch (PersonNotFoundException e) {
                    throw new AssertionError("The target person cannot be missing");
                }
            }
        } else if (tag == null) {
            for (ReadOnlyPerson personToUntag : lastShownList) {
                Person untaggedPerson = new Person(personToUntag);
                UniqueTagList updatedTags = new UniqueTagList();
                untaggedPerson.setTags(updatedTags.toSet());
                try {
                    model.updatePerson(personToUntag, untaggedPerson);
                } catch (DuplicatePersonException e) {
                    throw new CommandException(MESSAGE_DUPLICATE_PERSON);
                } catch (PersonNotFoundException e) {
                    throw new AssertionError("The target person cannot be missing");
                }
            }
        }

        for (Index targetIndex : targetIndexes) {
            ReadOnlyPerson personToUntag = lastShownList.get(targetIndex.getZeroBased());
            Person untaggedPerson = new Person(personToUntag);
            UniqueTagList updatedTags = new UniqueTagList(personToUntag.getTags());
            updatedTags.remove(tag);
            untaggedPerson.setTags(updatedTags.toSet());
            try {
                model.updatePerson(personToUntag, untaggedPerson);
            } catch (DuplicatePersonException e) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException e) {
                throw new AssertionError("The target person cannot be missing");
            }
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult("Persons succcessfully untagged.");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UntagCommand // instanceof handles nulls
                && this.targetIndexes.equals(((UntagCommand) other).targetIndexes)) // state check
                && this.tag.equals(((UntagCommand) other).tag); // state check
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
public class UntagCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the UntagCommand
     * and returns a UntagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UntagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        if (trimmedArgs.equals("-all")) {
            return new UntagCommand();
        }

        String[] splittedArgs = trimmedArgs.split("\\s+");
        if (trimmedArgs.isEmpty() || splittedArgs.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
        }

        if (splittedArgs[0].equals("-all")) {
            try {
                return new UntagCommand(new Tag(splittedArgs[1]));
            } catch (IllegalValueException ive) {
                throw new ParseException(ive.getMessage(), ive);
            }
        }

        Set<String> uniqueIndexes = new HashSet<>(Arrays.asList(splittedArgs[0].split(",")));
        if (uniqueIndexes.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_EMPTY_INDEX_LIST, UntagCommand.MESSAGE_USAGE));
        }
        List<Index> indexList = new ArrayList<>();
        try {
            for (String indexArg : uniqueIndexes) {
                indexList.add(ParserUtil.parseIndex(indexArg));
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
        }

        try {
            Tag tag = new Tag(splittedArgs[1]);
            return new UntagCommand(indexList, tag);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
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

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Represents the clock display on the GUI of the address book application.
 */
public class ClockDisplay {

    private ObjectProperty<RunningClock> clock;

    public ClockDisplay() {
        this.clock = new SimpleObjectProperty<>(new RunningClock());
    }

    public void setClock(RunningClock clock) {
        this.clock.set(clock);
    }

    public ObjectProperty<RunningClock> clockProperty() {
        return clock;
    }

    public RunningClock getClock() {
        return clock.get();
    }

    @Override
    public String toString() {
        return clock.get().toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClockDisplay // instanceof handles nulls
                && this.clock.equals(((ClockDisplay) other).clock)); // state check
    }

    @Override
    public int hashCode() {
        return clock.hashCode();
    }

}
```
###### \java\seedu\address\model\clock\RunningClock.java
``` java

package seedu.address.model.clock;

import java.time.LocalDateTime;

/**
 * Represents a running clock inside the address book application.
 */
public class RunningClock {

    private String value;

    public RunningClock() {
        setCurrentTime();
    }

    public String getValue() {
        return value;
    }

    public int getHour() {
        return LocalDateTime.now().getHour();
    }

    public int getMinute() {
        return LocalDateTime.now().getMinute();
    }

    public int getSecond() {
        return LocalDateTime.now().getSecond();
    }

    public void setCurrentTime() {
        this.value = ((getHour() < 10) ? ("0" + getHour()) : getHour()) + ":"
                + ((getMinute() < 10) ? ("0" + getMinute()) : getMinute()) + ":"
                + ((getSecond() < 10) ? ("0" + getSecond()) : getSecond());
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RunningClock // instanceof handles nulls
                && this.value.equals(((RunningClock) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
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

/**
 * Represents a reminder's date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public final String date;

    /**
     * Validates given date.
     */
    public Date(String date) {
        requireNonNull(date);
        this.date = date.trim();
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
    public static final String PRIORITY_VALIDATION_REGEX = ".*\\b(Low|Medium|High)\\b.*";
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
    private void startClock(ClockDisplay footerClock) {
        final Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                footerClock.getClock().setCurrentTime();
                footerClock.setClock(requireNonNull(footerClock.getClock()));
                Platform.runLater(() -> displayClock.setText(footerClock.toString()));
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
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

#displayClock {
    -fx-font-size: 50;
    -fx-text-fill: white;
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
