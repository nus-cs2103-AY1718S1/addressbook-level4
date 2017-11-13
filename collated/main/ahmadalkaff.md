# ahmadalkaff
###### \java\seedu\address\logic\commands\DeleteTaskCommand.java
``` java
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from ContactHub.
 */
public class DeleteTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deletetask";
    public static final String COMMAND_ALIAS = "dt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the task list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1" + "\n"
            + "Example: " + COMMAND_ALIAS + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    private final Index targetIndex;

    public DeleteTaskCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTaskCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteTaskCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        Set<Phone> updatedPhones = editPersonDescriptor.getPhones().orElse(personToEdit.getPhones());
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        Set<Email> updatedEmails = editPersonDescriptor.getEmails().orElse(personToEdit.getEmails());
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setPhones(Set<Phone> phones) {
            this.phones = phones;
        }

        public Optional<Set<Phone>> getPhones() {
            return Optional.ofNullable(phones);
        }
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setEmails(Set<Email> emails) {
            this.emails = emails;
        }

        public Optional<Set<Email>> getEmails() {
            return Optional.ofNullable(emails);
        }
```
###### \java\seedu\address\logic\commands\EditTaskCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.Appointment;
import seedu.address.model.task.Date;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.StartTime;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edittask";
    public static final String COMMAND_ALIAS = "et";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_APPOINTMENT + "APPOINTMENT] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_STARTTIME + "STARTTIME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_APPOINTMENT + "Interview "
            + PREFIX_DATE + "28/10/2017 "
            + PREFIX_STARTTIME + "10:00\n"
            + "Example: " + COMMAND_ALIAS + " 1 "
            + PREFIX_APPOINTMENT + "Interview "
            + PREFIX_DATE + "28/10/2017 "
            + PREFIX_STARTTIME + "10:00 ";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param index              of the task in the filtered task list to edit
     * @param editTaskDescriptor details to edit the task with
     */
    public EditTaskCommand(Index index, EditTaskCommand.EditTaskDescriptor editTaskDescriptor) {
        requireNonNull(index);
        requireNonNull(editTaskDescriptor);

        this.index = index;
        this.editTaskDescriptor = new EditTaskCommand.EditTaskDescriptor(editTaskDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(index.getZeroBased());
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);

        try {
            model.updateTask(taskToEdit, editedTask);
        } catch (TaskNotFoundException tnfe) {
            throw new AssertionError("The target task cannot be missing");
        }
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit,
                                             EditTaskCommand.EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Appointment updatedAppointment = editTaskDescriptor.getAppointment().orElse(taskToEdit.getAppointment());
        Date updatedDate = editTaskDescriptor.getDate().orElse(taskToEdit.getDate());
        StartTime updatedStartTime = editTaskDescriptor.getStartTime().orElse(taskToEdit.getStartTime());

        return new Task(updatedAppointment, updatedDate, updatedStartTime);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditTaskCommand)) {
            return false;
        }

        // state check
        EditTaskCommand e = (EditTaskCommand) other;
        return index.equals(e.index)
                && editTaskDescriptor.equals(e.editTaskDescriptor);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Appointment appointment;
        private Date date;
        private StartTime startTime;

        public EditTaskDescriptor() {
        }

        public EditTaskDescriptor(EditTaskCommand.EditTaskDescriptor toCopy) {
            this.appointment = toCopy.appointment;
            this.date = toCopy.date;
            this.startTime = toCopy.startTime;
        }


        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.appointment, this.date, this.startTime);
        }

        public void setAppointment(Appointment appointment) {
            this.appointment = appointment;
        }

        public Optional<Appointment> getAppointment() {
            return Optional.ofNullable(appointment);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public void setStartTime(StartTime startTime) {
            this.startTime = startTime;
        }

        public Optional<StartTime> getStartTime() {
            return Optional.ofNullable(startTime);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTaskCommand.EditTaskDescriptor)) {
                return false;
            }

            // state check
            EditTaskCommand.EditTaskDescriptor e = (EditTaskCommand.EditTaskDescriptor) other;

            return getAppointment().equals(e.getAppointment())
                    && getDate().equals(e.getDate())
                    && getStartTime().equals(e.getStartTime());
        }
    }
}
```
###### \java\seedu\address\logic\commands\ListModuleCommand.java
``` java
package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Collections;

import seedu.address.model.module.Module;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Lists all modules in the address book to the user.
 */
public class ListModuleCommand extends Command {

    public static final String COMMAND_WORD = "listmodules";
    public static final String COMMAND_ALIAS = "lm";

    public static final String MESSAGE_SUCCESS = "Listed all modules";

    private ArrayList<Module> moduleList = new ArrayList<>();
    private ArrayList<String> tempList = new ArrayList<>();
    private String moduleString = "Module names: ";


    @Override
    public CommandResult execute() {
        extractAllModules();
        sortModules();
        return new CommandResult(MESSAGE_SUCCESS + "\n" + moduleString + formatModuleString());
    }

    /**
     * Formats the module list into a string
     */
    private String formatModuleString() {
        return tempList.toString().replace(",", "]").replace(" ", " [");
    }

    /**
     * Sorts the modules alphabetically
     */
    private void sortModules() {
        for (Module m : moduleList) {
            tempList.add(m.moduleName);
        }

        Collections.sort(tempList);
    }

    /**
     * Extract all the modules from the address book and stores it in a list
     */
    private void extractAllModules() {
        for (ReadOnlyPerson person : model.getAddressBook().getPersonList()) {
            for (Module m : person.getModules()) {
                if (!moduleList.contains(m)) {
                    moduleList.add(m);
                }
            }
        }
    }

}
```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Sorts all persons in the address book in an alphabetical
 * order to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "s";

    public static final String MESSAGE_SUCCESS = "Sorted all persons in the address book alphabetically.";

    @Override
    public CommandResult execute() {
        model.sortPersonListByName();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
            Set<Phone> phoneList = ParserUtil.parsePhones(argMultimap.getAllValues(PREFIX_PHONE));
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
            Set<Email> emailList = ParserUtil.parseEmails(argMultimap.getAllValues(PREFIX_EMAIL));
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case AddCommand.COMMAND_ALIAS:
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case EditTaskCommand.COMMAND_WORD:
        case EditTaskCommand.COMMAND_ALIAS:
            return new EditTaskCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case SelectCommand.COMMAND_ALIAS:
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case DeleteCommand.COMMAND_ALIAS:
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case DeleteTaskCommand.COMMAND_WORD:
        case DeleteTaskCommand.COMMAND_ALIAS:
            return new DeleteTaskCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case ClearCommand.COMMAND_ALIAS:
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case FindCommand.COMMAND_ALIAS:
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case ListCommand.COMMAND_ALIAS:
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case ListModuleCommand.COMMAND_WORD:
        case ListModuleCommand.COMMAND_ALIAS:
            return new ListModuleCommand();
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case HistoryCommand.COMMAND_ALIAS:
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case SortCommand.COMMAND_WORD:
        case SortCommand.COMMAND_ALIAS:
            return new SortCommand();
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case ExitCommand.COMMAND_ALIAS:
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case UndoCommand.COMMAND_ALIAS:
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case RedoCommand.COMMAND_ALIAS:
```
###### \java\seedu\address\logic\parser\DeleteTaskCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteTaskCommandParser implements Parser<DeleteTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTaskCommand
     * and returns an DeleteTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTaskCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteTaskCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\logic\parser\EditCommandParser.java
``` java
            parsePhonesForEdit(argMultimap.getAllValues(PREFIX_PHONE)).ifPresent(editPersonDescriptor::setPhones);
```
###### \java\seedu\address\logic\parser\EditCommandParser.java
``` java
            parseEmailsForEdit(argMultimap.getAllValues(PREFIX_EMAIL)).ifPresent(editPersonDescriptor::setEmails);
```
###### \java\seedu\address\logic\parser\EditCommandParser.java
``` java
    /**
     * Parses {@code Collection<String> phones} into a {@code Set<Phone>} if {@code phones} is non-empty.
     * If {@code phones} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Phone>} containing zero phones.
     */
    private Optional<Set<Phone>> parsePhonesForEdit(Collection<String> phones) throws IllegalValueException {
        assert phones != null;

        if (phones.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> phoneSet = phones.size() == 1 && phones.contains("") ? Collections.emptySet() : phones;
        return Optional.of(ParserUtil.parsePhones(phoneSet));
    }

    /**
     * Parses {@code Collection<String> emails} into a {@code Set<Email>} if {@code emails} is non-empty.
     * If {@code emails} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Email>} containing zero emails.
     */
    private Optional<Set<Email>> parseEmailsForEdit(Collection<String> emails) throws IllegalValueException {
        assert emails != null;

        if (emails.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> emailSet = emails.size() == 1 && emails.contains("") ? Collections.emptySet() : emails;
        return Optional.of(ParserUtil.parseEmails(emailSet));
    }
```
###### \java\seedu\address\logic\parser\EditTaskCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditTaskCommand object
 */
public class EditTaskCommandParser implements Parser<EditTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditTaskCommand
     * and returns an EditTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_APPOINTMENT, PREFIX_DATE, PREFIX_STARTTIME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));
        }

        EditTaskCommand.EditTaskDescriptor editTaskDescriptor = new EditTaskCommand.EditTaskDescriptor();
        try {
            ParserUtil.parseAppointment(argMultimap.getValue(PREFIX_APPOINTMENT))
                    .ifPresent(editTaskDescriptor::setAppointment);
            ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE)).ifPresent(editTaskDescriptor::setDate);
            ParserUtil.parseStartTime(argMultimap.getValue(PREFIX_STARTTIME))
                    .ifPresent(editTaskDescriptor::setStartTime);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTaskCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTaskCommand(index, editTaskDescriptor);
    }

}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Set<Phone> parsePhones(Collection<String> phones) throws IllegalValueException {
        requireNonNull(phones);
        final Set<Phone> phoneSet = new HashSet<>();
        for (String phoneNum : phones) {
            phoneSet.add(new Phone(phoneNum));
        }
        return phoneSet;
    }
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Set<Email> parseEmails(Collection<String> emails) throws IllegalValueException {
        requireNonNull(emails);
        final Set<Email> emailSet = new HashSet<>();
        for (String e : emails) {
            emailSet.add(new Email(e));
        }
        return emailSet;
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Sorts the personList alphabetically
     */
    public void sortPersonListByName() {
        persons.sortPersonListByName();
    }
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Sorts the list alphabetically
     */
    void sortPersonListByName();
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void sortPersonListByName() {
        addressBook.sortPersonListByName();
    }
```
###### \java\seedu\address\model\person\EmailList.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of emails that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Phone#equals(Object)
 */
public class EmailList implements Iterable<Email> {

    private final ObservableList<Email> internalList = FXCollections.observableArrayList();

    /**
     * Constructs an empty EmailList
     */
    public EmailList() {

    }

    /**
     * Creates a EmailList using given emails.
     * Enforces no nulls.
     */
    public EmailList(Set<Email> emails) {
        requireAllNonNull(emails);
        internalList.addAll(emails);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all emails in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Email> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Adds an email to the list.
     *
     * @throws PhoneList.DuplicatePhoneException if the email to add is a duplicate of an existing Phone in the list.
     */
    public void add(Email toAdd) throws EmailList.DuplicateEmailException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new EmailList.DuplicateEmailException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }


    @Override
    public Iterator<Email> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns true if the list contains an equivalent email as the given argument.
     */
    public boolean contains(Email toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof EmailList // instanceof handles nulls
                && this.internalList.equals(((EmailList) other).internalList));
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateEmailException extends DuplicateDataException {
        protected DuplicateEmailException() {
            super("Operation would result in duplicate mods");
        }
    }
}
```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Returns an immutable phone set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Phone> getPhones() {
        return Collections.unmodifiableSet(phones.get().toSet());
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Replaces this person's phones with the phones in the argument phone set.
     */
    public void setPhones(Set<Phone> replacement) {
        phones.set(new PhoneList(replacement));
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Returns an immutable email set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Email> getEmails() {
        return Collections.unmodifiableSet(emails.get().toSet());
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Replaces this person's emails with the emails in the argument email set.
     */
    public void setEmails(Set<Email> replacement) {
        emails.set(new EmailList(replacement));
    }
```
###### \java\seedu\address\model\person\PhoneList.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of phones that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Phone#equals(Object)
 */
public class PhoneList implements Iterable<Phone> {

    private final ObservableList<Phone> internalList = FXCollections.observableArrayList();

    /**
     * Constructs an empty PhoneList
     */
    public PhoneList() {

    }

    /**
     * Creates a PhoneList using given phones.
     * Enforces no nulls.
     */
    public PhoneList(Set<Phone> phones) {
        requireAllNonNull(phones);
        internalList.addAll(phones);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all phones in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Phone> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Adds a phone to the list.
     *
     * @throws PhoneList.DuplicatePhoneException if the phone to add is a duplicate of an existing phone in the list.
     */
    public void add(Phone toAdd) throws PhoneList.DuplicatePhoneException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new PhoneList.DuplicatePhoneException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Phone> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns true if the list contains an equivalent phone as the given argument.
     */
    public boolean contains(Phone toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof PhoneList // instanceof handles nulls
                && this.internalList.equals(((PhoneList) other).internalList));
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicatePhoneException extends DuplicateDataException {
        protected DuplicatePhoneException() {
            super("Operation would result in duplicate mods");
        }
    }
}
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Sorts the list alphabetically
     */
    public void sortPersonListByName() {
        Collections.sort(internalList, Comparator.comparing(firstPerson -> firstPerson
                .getName().fullName.toUpperCase()));
    }
```
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java
    /**
     * Returns a phone set containing the list of strings given.
     */
    public static Set<Phone> getPhoneSet(String... strings) throws IllegalValueException {
        HashSet<Phone> phones = new HashSet<>();
        for (String s : strings) {
            phones.add(new Phone(s));
        }

        return phones;
    }

    /**
     * Returns a email set containing the list of strings given.
     */
    public static Set<Email> getEmailSet(String... strings) throws IllegalValueException {
        HashSet<Email> emails = new HashSet<>();
        for (String s : strings) {
            emails.add(new Email(s));
        }

        return emails;
    }
```
###### \java\seedu\address\storage\XmlAdaptedEmail.java
``` java
package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Email;

/**
 * JAXB-friendly adapted version of the Email.
 */
public class XmlAdaptedEmail {

    @XmlValue
    private String email;

    /**
     * Constructs an XmlAdaptedEmail.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEmail() {}
    /**
     * Converts a given Email into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedEmail(Email source) {
        email = source.value;
    }

    /**
     * Converts this jaxb-friendly adapted phone object into the model's Email object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Email toModelType() throws IllegalValueException {
        return new Email(email);
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        phones = new ArrayList<>();
        for (Phone phone : source.getPhones()) {
            phones.add(new XmlAdaptedPhone(phone));
        }
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        emails = new ArrayList<>();
        for (Email email : source.getEmails()) {
            emails.add(new XmlAdaptedEmail(email));
        }
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        final List<Phone> personPhones = new ArrayList<>();
        for (XmlAdaptedPhone phone : phones) {
            personPhones.add(phone.toModelType());
        }

        final List<Email> personEmails = new ArrayList<>();
        for (XmlAdaptedEmail email : emails) {
            personEmails.add(email.toModelType());
        }
```
###### \java\seedu\address\storage\XmlAdaptedPhone.java
``` java
package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Phone;

/**
 * JAXB-friendly adapted version of the Phone.
 */
public class XmlAdaptedPhone {

    @XmlValue
    private String phone;

    /**
     * Constructs an XmlAdaptedPhone.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPhone() {}
    /**
     * Converts a given Mod into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedPhone(Phone source) {
        phone = source.value;
    }

    /**
     * Converts this jaxb-friendly adapted phone object into the model's Phone object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Phone toModelType() throws IllegalValueException {
        return new Phone(phone);
    }
}
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    @FXML
    private FlowPane phones;
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    @FXML
    private FlowPane emails;
```
###### \java\seedu\address\ui\PersonCard.java
``` java
        initPhones(person);
        initEmails(person);
        initModules(person);
```
###### \java\seedu\address\ui\PersonCard.java
``` java
        person.phoneProperty().addListener((observable, oldValue, newValue) -> {
            phones.getChildren().clear();
            initPhones(person);
        });

```
###### \java\seedu\address\ui\PersonCard.java
``` java
        person.emailProperty().addListener((observable, oldValue, newValue) -> {
            emails.getChildren().clear();
            initEmails(person);
        });

```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Initialise the phones for each person
     */
    private void initPhones(ReadOnlyPerson person) {
        person.getPhones().forEach(phone -> {
            Label phoneLabel = new Label(phone.value);
            phones.getChildren().add(phoneLabel);
            phones.setHgap(GAP);
        });
    }

    /**
     * Initialise the emails for each person
     */
    private void initEmails(ReadOnlyPerson person) {
        person.getEmails().forEach(email -> {
            Label emailLabel = new Label(email.value);
            emails.getChildren().add(emailLabel);
            emails.setHgap(GAP);
        });
    }
```
###### \resources\view\PersonListCard.fxml
``` fxml
      <FlowPane fx:id="phones" prefHeight="16.0" prefWidth="130.0" styleClass="cell_small_label" />
```
###### \resources\view\PersonListCard.fxml
``` fxml
      <FlowPane fx:id="emails" prefHeight="16.0" prefWidth="130.0" styleClass="cell_small_label" />
    </VBox>
```
