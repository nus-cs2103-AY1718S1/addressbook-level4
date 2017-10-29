# inGall
###### \java\seedu\address\logic\commands\AddReminderCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MESSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;

/**
 * Adds a reminder to the address book.
 */
public class AddReminderCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a reminder to the address book. "
            + "Parameters: "
            + PREFIX_TASK + "NAME "
            + PREFIX_PRIORITY + "PRIORITY "
            + PREFIX_DATE + "DATE "
            + PREFIX_MESSAGE + "MESSAGE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TASK + "Proposal submission "
            + PREFIX_PRIORITY + "Low "
            + PREFIX_DATE + "25/12/2017 1500 "
            + PREFIX_MESSAGE + "Submit to manager "
            + PREFIX_TAG + "Work "
            + PREFIX_TAG + "John";

    public static final String MESSAGE_SUCCESS = "New reminder added: %1$s";
    public static final String MESSAGE_DUPLICATE_REMINDER = "This reminder already exists in the address book";

    private final Reminder toAdd;

    /**
     * Creates an AddReminderCommand to add the specified {@code ReadOnlyReminder}
     */
    public AddReminderCommand(ReadOnlyReminder reminder) {
        toAdd = new Reminder(reminder);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addReminder(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateReminderException e) {
            throw new CommandException(MESSAGE_DUPLICATE_REMINDER);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddReminderCommand // instanceof handles nulls
                && toAdd.equals(((AddReminderCommand) other).toAdd));
    }
}
```
###### \java\seedu\address\logic\commands\BirthdayCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Changes the remark of an existing person in the address book.
 */
public class BirthdayCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "birthday";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the birthday of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing birthday will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_BIRTHDAY + "dd/mm/yyyy\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_BIRTHDAY + "02/03/1994";

    public static final String MESSAGE_ADD_BIRTHDAY_SUCCESS = "Added birthday to Person: %1$s";
    public static final String MESSAGE_DELETE_BIRTHDAY_SUCCESS = "Removed birthday from Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final Birthday birthday;

    /**
     * @param index of the person in the filtered person list to edit the birthday
     * @param birthday of the person
     */
    public BirthdayCommand(Index index, Birthday birthday) {
        requireNonNull(index);
        requireNonNull(birthday);
        this.index = index;
        this.birthday = birthday;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), birthday, personToEdit.getTags());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * @param personToEdit
     * @return
     */
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (!birthday.value.isEmpty()) {
            return String.format(MESSAGE_ADD_BIRTHDAY_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_DELETE_BIRTHDAY_SUCCESS, personToEdit);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BirthdayCommand)) {
            return false;
        }

        // state check
        BirthdayCommand e = (BirthdayCommand) other;
        return index.equals(e.index)
                && birthday.equals(e.birthday);
    }
}
```
###### \java\seedu\address\logic\commands\DeleteReminderCommand.java
``` java
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.exceptions.ReminderNotFoundException;

/**
 * Deletes a reminder identified using it's last displayed index from the address book.
 */
public class DeleteReminderCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the reminder identified by the index number used in the last reminder listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_REMINDER_SUCCESS = "Deleted Reminder: %1$s";

    private final Index targetIndex;

    public DeleteReminderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyReminder> lastShownList = model.getFilteredReminderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
        }

        ReadOnlyReminder reminderToDelete = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.deleteReminder(reminderToDelete);
        } catch (ReminderNotFoundException pnfe) {
            assert false : "The target reminder cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_REMINDER_SUCCESS, reminderToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteReminderCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteReminderCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\EditReminderCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MESSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_REMINDERS;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.reminder.Date;
import seedu.address.model.reminder.Message;
import seedu.address.model.reminder.Priority;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.Task;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;
import seedu.address.model.reminder.exceptions.ReminderNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing reminder in the address book.
 */
public class EditReminderCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "editReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the reminder identified "
            + "by the index number used in the last reminder listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TASK + "TASK] "
            + "[" + PREFIX_PRIORITY + "PRIORITY] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_MESSAGE + "MESSAGE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PRIORITY + "7 "
            + PREFIX_DATE + "16/02/2017 1630";

    public static final String MESSAGE_EDIT_REMINDER_SUCCESS = "Edited Reminder: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_REMINDER = "This reminder already exists in the address book.";

    private final Index index;
    private final EditReminderDescriptor editReminderDescriptor;

    /**
     * @param index of the reminder in the filtered reminder list to edit
     * @param editReminderDescriptor details to edit the reminder with
     */
    public EditReminderCommand(Index index, EditReminderDescriptor editReminderDescriptor) {
        requireNonNull(index);
        requireNonNull(editReminderDescriptor);

        this.index = index;
        this.editReminderDescriptor = new EditReminderDescriptor(editReminderDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyReminder> lastShownList = model.getFilteredReminderList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
        }

        ReadOnlyReminder reminderToEdit = lastShownList.get(index.getZeroBased());
        Reminder editedReminder = createEditedReminder(reminderToEdit, editReminderDescriptor);

        try {
            model.updateReminder(reminderToEdit, editedReminder);
        } catch (DuplicateReminderException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_REMINDER);
        } catch (ReminderNotFoundException pnfe) {
            throw new AssertionError("The target reminder cannot be missing");
        }
        model.updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
        return new CommandResult(String.format(MESSAGE_EDIT_REMINDER_SUCCESS, editedReminder));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code reminderToEdit}
     * edited with {@code editReminderDescriptor}.
     */
    private static Reminder createEditedReminder(ReadOnlyReminder reminderToEdit,
                                             EditReminderDescriptor editReminderDescriptor) {
        assert reminderToEdit != null;

        Task updatedTask = editReminderDescriptor.getTask().orElse(reminderToEdit.getTask());
        Priority updatedPriority = editReminderDescriptor.getPriority().orElse(reminderToEdit.getPriority());
        Date updatedDate = editReminderDescriptor.getDate().orElse(reminderToEdit.getDate());
        Message updatedMessage = editReminderDescriptor.getMessage().orElse(reminderToEdit.getMessage());
        Set<Tag> updatedTags = editReminderDescriptor.getTags().orElse(reminderToEdit.getTags());

        return new Reminder(updatedTask, updatedPriority, updatedDate, updatedMessage, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditReminderCommand e = (EditReminderCommand) other;
        return index.equals(e.index)
                && editReminderDescriptor.equals(e.editReminderDescriptor);
    }

    /**
     * Stores the details to edit the reminder with. Each non-empty field value will replace the
     * corresponding field value of the reminder.
     */
    public static class EditReminderDescriptor {
        private Task task;
        private Priority priority;
        private Date date;
        private Message message;
        private Set<Tag> tags;

        public EditReminderDescriptor() {}

        public EditReminderDescriptor(EditReminderDescriptor toCopy) {
            this.task = toCopy.task;
            this.priority = toCopy.priority;
            this.date = toCopy.date;
            this.message = toCopy.message;
            this.tags = toCopy.tags;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.task, this.priority, this.date,
                    this.message, this.tags);
        }

        public void setTask(Task task) {
            this.task = task;
        }

        public Optional<Task> getTask() {
            return Optional.ofNullable(task);
        }

        public void setPriority(Priority priority) {
            this.priority = priority;
        }

        public Optional<Priority> getPriority() {
            return Optional.ofNullable(priority);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public void setMessage(Message message) {
            this.message = message;
        }

        public Optional<Message> getMessage() {
            return Optional.ofNullable(message);
        }

        public void setTags(Set<Tag> tags) {
            this.tags = tags;
        }

        public Optional<Set<Tag>> getTags() {
            return Optional.ofNullable(tags);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditReminderDescriptor)) {
                return false;
            }

            // state check
            EditReminderDescriptor e = (EditReminderDescriptor) other;

            return getTask().equals(e.getTask())
                    && getPriority().equals(e.getPriority())
                    && getDate().equals(e.getDate())
                    && getMessage().equals(e.getMessage())
                    && getTags().equals(e.getTags());
        }
    }
}
```
###### \java\seedu\address\logic\commands\FindEmailCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.model.person.EmailContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose email contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindEmailCommand extends Command {

    public static final String COMMAND_WORD = "findEmail";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose emails contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " test@example.com";

    private final EmailContainsKeywordsPredicate predicate;

    public FindEmailCommand(EmailContainsKeywordsPredicate predicate) {
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
                || (other instanceof FindEmailCommand // instanceof handles nulls
                && this.predicate.equals(((FindEmailCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\FindPhoneCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.model.person.PhoneContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindPhoneCommand extends Command {

    public static final String COMMAND_WORD = "findPhone";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose phone numbers contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " 84281299";

    private final PhoneContainsKeywordsPredicate predicate;

    public FindPhoneCommand(PhoneContainsKeywordsPredicate predicate) {
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
                || (other instanceof FindPhoneCommand // instanceof handles nulls
                && this.predicate.equals(((FindPhoneCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\FindReminderCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.model.reminder.TaskContainsKeywordsPredicate;

/**
 * Finds and lists all reminders in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindReminderCommand extends Command {

    public static final String COMMAND_WORD = "findReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all reminders whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final TaskContainsKeywordsPredicate predicate;

    public FindReminderCommand(TaskContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredReminderList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredReminderList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindReminderCommand // instanceof handles nulls
                && this.predicate.equals(((FindReminderCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\ListReminderCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_REMINDERS;

/**
 * Lists all persons in the address book to the user.
 */
public class ListReminderCommand extends Command {

    public static final String COMMAND_WORD = "listReminder";

    public static final String MESSAGE_SUCCESS = "Listed all reminders";


    @Override
    public CommandResult execute() {
        model.updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\SortAgeCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Sort names of contacts by alphabetical order
 */
public class SortAgeCommand extends Command {
    public static final String COMMAND_WORD = "sortAge";

    public static final String MESSAGE_SUCCESS = "All contacts are sorted by age. (Oldest To Youngest)";
    public static final String MESSAGE_EMPTY_LIST = "Contact list is empty.";

    private ArrayList<ReadOnlyPerson> contactList;

    public SortAgeCommand() {
        contactList = new ArrayList<>();
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        Boolean isEmpty = model.checkIfPersonListEmpty(contactList);
        if (!isEmpty) {
            model.sortListByAge(contactList);
            return new CommandResult(MESSAGE_SUCCESS);
        }
        return new CommandResult(MESSAGE_EMPTY_LIST);
    }
}
```
###### \java\seedu\address\logic\commands\SortBirthdayCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Sort names of contacts by alphabetical order
 */
public class SortBirthdayCommand extends Command {
    public static final String COMMAND_WORD = "sortBirthday";

    public static final String MESSAGE_SUCCESS = "All contacts are sorted by birthday.";
    public static final String MESSAGE_EMPTY_LIST = "Contact list is empty.";

    private ArrayList<ReadOnlyPerson> contactList;

    public SortBirthdayCommand() {
        contactList = new ArrayList<>();
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        Boolean isEmpty = model.checkIfPersonListEmpty(contactList);
        if (!isEmpty) {
            model.sortListByBirthday(contactList);
            return new CommandResult(MESSAGE_SUCCESS);
        }
        return new CommandResult(MESSAGE_EMPTY_LIST);
    }
}
```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.person.ReadOnlyPerson;

/**
  * Sort names of contacts by alphabetical order
  */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "All contacts are sorted.";
    public static final String MESSAGE_EMPTY_LIST = "Contact list is empty.";

    private ArrayList<ReadOnlyPerson> contactList;

    public SortCommand() {
        contactList = new ArrayList<>();
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        Boolean isEmpty = model.checkIfPersonListEmpty(contactList);
        if (!isEmpty) {
            model.sortListByName(contactList);
            return new CommandResult(MESSAGE_SUCCESS);
        }
        return new CommandResult(MESSAGE_EMPTY_LIST);
    }
}
```
###### \java\seedu\address\logic\commands\SortPriorityCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_REMINDERS;

import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.reminder.ReadOnlyReminder;

/**
 * Sort reminders in order or priority.
 */
public class SortPriorityCommand extends Command {
    public static final String COMMAND_WORD = "sortPriority";

    public static final String MESSAGE_SUCCESS = "All contacts are sorted by priority. (High -> Medium -> Low)";
    public static final String MESSAGE_EMPTY_LIST = "Contact list is empty.";

    private ArrayList<ReadOnlyReminder> contactList;

    public SortPriorityCommand() {
        contactList = new ArrayList<>();
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
        Boolean isEmpty = model.checkIfReminderListEmpty(contactList);
        if (!isEmpty) {
            model.sortListByPriority(contactList);
            return new CommandResult(MESSAGE_SUCCESS);
        }
        return new CommandResult(MESSAGE_EMPTY_LIST);
    }
}
```
###### \java\seedu\address\logic\parser\AddReminderCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MESSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminder.Date;
import seedu.address.model.reminder.Message;
import seedu.address.model.reminder.Priority;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.Task;

import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddReminderCommand object
 */
public class AddReminderCommandParser implements Parser<AddReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddReminderCommand
     * and returns an AddReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddReminderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK, PREFIX_PRIORITY, PREFIX_DATE, PREFIX_MESSAGE,
                        PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_TASK, PREFIX_PRIORITY, PREFIX_DATE, PREFIX_MESSAGE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE));
        }

        try {
            Task task = ParserUtil.parseTask(argMultimap.getValue(PREFIX_TASK)).get();
            Priority priority = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY)).get();
            Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE)).get();
            Message message = ParserUtil.parseMessage(argMultimap.getValue(PREFIX_MESSAGE)).get();
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            ReadOnlyReminder reminder = new Reminder(task, priority, date, message, tagList);

            return new AddReminderCommand(reminder);
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
###### \java\seedu\address\logic\parser\BirthdayCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.BirthdayCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Birthday;

/**
 * Parses input arguments and creates a new BirthdayCommand object
 */
public class BirthdayCommandParser implements Parser<BirthdayCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the BirthdayCommand
     * and returns an BirthdayCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BirthdayCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_BIRTHDAY);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BirthdayCommand.MESSAGE_USAGE));
        }

        String birthday = argMultimap.getValue(PREFIX_BIRTHDAY).orElse("");

        return new BirthdayCommand(index, new Birthday(birthday));
    }
}
```
###### \java\seedu\address\logic\parser\DeleteReminderCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteReminderCommand object
 */
public class DeleteReminderCommandParser implements Parser<DeleteReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteReminderCommand
     * and returns an DeleteReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteReminderCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteReminderCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteReminderCommand.MESSAGE_USAGE));
        }
    }

}

```
###### \java\seedu\address\logic\parser\EditReminderCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MESSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditReminderCommand;
import seedu.address.logic.commands.EditReminderCommand.EditReminderDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditReminderCommand object
 */
public class EditReminderCommandParser implements Parser<EditReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditReminderCommand
     * and returns an EditReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditReminderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK, PREFIX_PRIORITY, PREFIX_DATE, PREFIX_TAG,
                        PREFIX_MESSAGE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditReminderCommand.MESSAGE_USAGE));
        }

        EditReminderDescriptor editReminderDescriptor = new EditReminderDescriptor();
        try {
            ParserUtil.parseTask(argMultimap.getValue(PREFIX_TASK)).ifPresent(
                    editReminderDescriptor::setTask);
            ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY)).ifPresent(
                    editReminderDescriptor::setPriority);
            ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE)).ifPresent(
                    editReminderDescriptor::setDate);
            ParserUtil.parseMessage(argMultimap.getValue(PREFIX_MESSAGE)).ifPresent(
                    editReminderDescriptor::setMessage);
            parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(
                    editReminderDescriptor::setTags);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editReminderDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditReminderCommand.MESSAGE_NOT_EDITED);
        }

        return new EditReminderCommand(index, editReminderDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
```
###### \java\seedu\address\logic\parser\FindEmailCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindEmailCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.EmailContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindEmailCommand object
 */
public class FindEmailCommandParser implements Parser<FindEmailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindEmailCommand
     * and returns an FindEmailCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindEmailCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEmailCommand.MESSAGE_USAGE));
        }

        String[] emailKeywords = trimmedArgs.split("\\s+");

        return new FindEmailCommand(new EmailContainsKeywordsPredicate(Arrays.asList(emailKeywords)));
    }
}
```
###### \java\seedu\address\logic\parser\FindPhoneCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindPhoneCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindPhoneCommand object
 */
public class FindPhoneCommandParser implements Parser<FindPhoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindPhoneCommand
     * and returns an FindPhoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPhoneCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPhoneCommand.MESSAGE_USAGE));
        }

        String[] phoneKeywords = trimmedArgs.split("\\s+");

        return new FindPhoneCommand(new PhoneContainsKeywordsPredicate(Arrays.asList(phoneKeywords)));
    }

}
```
###### \java\seedu\address\logic\parser\FindReminderCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminder.TaskContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindReminderCommand object
 */
public class FindReminderCommandParser implements Parser<FindReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindReminderCommand
     * and returns an FindReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindReminderCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindReminderCommand.MESSAGE_USAGE));
        }

        String[] reminderKeywords = trimmedArgs.split("\\s+");

        return new FindReminderCommand(new TaskContainsKeywordsPredicate(Arrays.asList(reminderKeywords)));
    }

}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * @param contactList
     * @throws CommandException
     */
    public void sortListByName(ArrayList<ReadOnlyPerson> contactList) throws CommandException {
        contactList.addAll(filteredPersons);
        Collections.sort(contactList, Comparator.comparing(p -> p.toString().toLowerCase()));

        try {
            addressBook.setPersons(contactList);
            indicateAddressBookChanged();
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
    }

    /**
     * @param contactList
     * @throws CommandException
     */
    public void sortListByAge(ArrayList<ReadOnlyPerson> contactList) throws CommandException {
        contactList.addAll(filteredPersons);
        Collections.sort(contactList, new AgeComparator());

        try {
            addressBook.setPersons(contactList);
            indicateAddressBookChanged();
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
    }

    /**
     * @param contactList
     * @throws CommandException
     */
    public void sortListByBirthday(ArrayList<ReadOnlyPerson> contactList) throws CommandException {
        contactList.addAll(filteredPersons);
        Collections.sort(contactList, new BirthdayComparator());

        try {
            addressBook.setPersons(contactList);
            indicateAddressBookChanged();
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
    }

    /**
     * @param contactList
     * @throws CommandException
     */
    public void sortListByPriority(ArrayList<ReadOnlyReminder> contactList) throws CommandException {
        contactList.addAll(filteredReminders);
        Collections.sort(contactList, new PriorityComparator());

        try {
            addressBook.setReminders(contactList);
            indicateAddressBookChanged();
        } catch (DuplicateReminderException e) {
            throw new CommandException(MESSAGE_DUPLICATE_REMINDER);
        }
    }
```
###### \java\seedu\address\model\person\AgeComparator.java
``` java
package seedu.address.model.person;

import java.util.Comparator;

/**
 * Compares Age of ReadOnlyPerson
 */
public class AgeComparator implements Comparator<ReadOnlyPerson> {

    @Override
    public int compare(ReadOnlyPerson firstNum, ReadOnlyPerson secondNum) {
        String newFirstNum = getNewStringAgeFormat(firstNum);
        String newSecondNum = getNewStringAgeFormat(secondNum);
        if (newFirstNum.equals("") || newSecondNum.equals("")) {
            return newSecondNum.compareTo(newFirstNum);
        } else {
            return newFirstNum.compareTo(newSecondNum);
        }
    }

    public static String getNewStringAgeFormat(ReadOnlyPerson person) {
        if (person.getBirthday().toString().equals("")) {
            return "";
        } else {
            String numInString = person.getBirthday().toString();    // Converts birthday to String type
            String dayForNum = numInString.substring(0, 2);        // Index of day in dd/mm/yyyy
            String monthForNum = numInString.substring(3, 5);      // Index of month in dd/mm/yyyy
            String yearForNum = numInString.substring(6, 10);      // Index of year in dd/mm/yyyy
            return yearForNum + monthForNum + dayForNum;           // Return string format yyyymmdd
        }
    }
}
```
###### \java\seedu\address\model\person\Birthday.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is always valid
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Person birthdays can take any values, can even be blank";

    public final String value;

    public Birthday(String birthday) {
        requireNonNull(birthday);
        this.value = birthday;
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
###### \java\seedu\address\model\person\BirthdayComparator.java
``` java
package seedu.address.model.person;

import java.util.Comparator;

/**
 * Compares Birthday of ReadOnlyPerson
 */
public class BirthdayComparator implements Comparator<ReadOnlyPerson> {

    @Override
    public int compare(ReadOnlyPerson firstPerson, ReadOnlyPerson secondPerson) {
        String newFirstNum = getNewStringBirthdayFormat(firstPerson);
        String newSecondNum = getNewStringBirthdayFormat(secondPerson);
        if (newFirstNum.equals("") || newSecondNum.equals("")) {
            return newSecondNum.compareTo(newFirstNum);
        } else {
            return newFirstNum.compareTo(newSecondNum);
        }
    }

    public static String getNewStringBirthdayFormat(ReadOnlyPerson person) {
        if (person.getBirthday().toString().equals("")) {
            return "";
        } else {
            String numInString = person.getBirthday().toString();  // Converts birthday to String type
            String dayForNum = numInString.substring(0, 2);        // Index of day in dd/mm/yyyy
            String monthForNum = numInString.substring(3, 5);      // Index of month in dd/mm/yyyy
            String yearForNum = numInString.substring(6, 10);      // Index of year in dd/mm/yyyy
            return monthForNum + dayForNum + yearForNum;           // Return String format mmddyyy
        }
    }
}
```
###### \java\seedu\address\model\person\EmailContainsKeywordsPredicate.java
``` java
package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class EmailContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public EmailContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((EmailContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\PhoneContainsKeywordsPredicate.java
``` java
package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class PhoneContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public PhoneContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PhoneContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\reminder\PriorityComparator.java
``` java
package seedu.address.model.reminder;

import java.util.Comparator;

/**
 * Compares Age of ReadOnlyPerson
 */
public class PriorityComparator implements Comparator<ReadOnlyReminder> {

    @Override
    public int compare(ReadOnlyReminder firstPrior, ReadOnlyReminder secondPrior) {
        String newFirstPrior = firstPrior.getPriority().toString();
        String newSecondPrior = secondPrior.getPriority().toString();
        if (newFirstPrior.equals("High") || newSecondPrior.equals("High")) {
            return newFirstPrior.compareTo(newSecondPrior);
        } else {
            return newSecondPrior.compareTo(newFirstPrior);
        }
    }
}
```
###### \java\seedu\address\model\reminder\TaskContainsKeywordsPredicate.java
``` java
package seedu.address.model.reminder;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class TaskContainsKeywordsPredicate implements Predicate<ReadOnlyReminder> {
    private final List<String> keywords;

    public TaskContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyReminder reminder) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(reminder.getTask().taskName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TaskContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
