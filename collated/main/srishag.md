# srishag
###### /java/seedu/address/commons/core/Messages.java
``` java
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided is invalid";
```
###### /java/seedu/address/commons/events/ui/SendEmailEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

/**
 * Event raised on SendEmail command's successful execution
 */
public class SendEmailEvent extends BaseEvent {

    public final Index recipient;
    public final String subject;
    public final String body;

    public SendEmailEvent(Index recipient, String subject, String body) {
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

```
###### /java/seedu/address/commons/events/ui/TaskPanelSelectionChangedEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.TaskCard;

/***
 * Represents a selection change in the Task List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {

    private final TaskCard newSelection;

    public TaskPanelSelectionChangedEvent(TaskCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public TaskCard getNewSelection() {
        return newSelection;
    }
}
```
###### /java/seedu/address/logic/commands/AddCommand.java
``` java
            + "[" + PREFIX_BIRTHDAY + "BIRTHDAY] " //not compulsory
```
###### /java/seedu/address/logic/commands/AddCommand.java
``` java
            + PREFIX_BIRTHDAY + "09081965"
```
###### /java/seedu/address/logic/commands/AddTaskCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEADER;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;

/**
 * Adds a task to the address book
 */
public class AddTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addt";
    public static final String COMMAND_ALIAS = "at";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the address book. "
            + "Parameters: "
            + PREFIX_HEADER + "HEADER "
            + PREFIX_DESC + "DESC "
            + PREFIX_DEADLINE + "DATE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_HEADER + "Homework "
            + PREFIX_DESC + "Finish page 6 to 9 "
            + PREFIX_DEADLINE + "08/11/2017";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";

    public final Task toAdd;

    /**
     * Creates an AddTaskCommand to add the specified {@code ReadOnlyTask}
     */
    public AddTaskCommand (ReadOnlyTask task) {
        toAdd = new Task(task);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddTaskCommand
                && toAdd.equals(((AddTaskCommand) other).toAdd));
    }
}
```
###### /java/seedu/address/logic/commands/DeleteTaskCommand.java
``` java
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * Deletes a task identifies using its last displayed index from the address book.
 */
public class DeleteTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deletet";
    public static final String COMMAND_ALIAS = "dt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted task: %1$s";

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
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteTaskCommand
                && this.targetIndex.equals(((DeleteTaskCommand) other).targetIndex));
    }
}
```
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
            + "[" + PREFIX_BIRTHDAY + "BIRTHDAY] "
```
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
        private Birthday birthday;
```
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
            this.birthday = toCopy.birthday;
```
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
        public void setBirthday(Birthday birthday) {
            this.birthday = birthday;
        }

        public Optional<Birthday> getBirthday() {
            return Optional.ofNullable(birthday);
        }
```
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
                    && getBirthday().equals(e.getBirthday())
```
###### /java/seedu/address/logic/commands/EditTaskCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEADER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Desc;
import seedu.address.model.task.Header;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * Edts the details of an existing task in the address book
 */
public class EditTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "editt";
    public static final String COMMAND_ALIAS = "et";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_HEADER + "HEADER] "
            + "[" + PREFIX_DESC + "DESC] "
            + "[" + PREFIX_DEADLINE + "DEADLINE]\n"
            + "Example: " + COMMAND_WORD + " 1"
            + PREFIX_HEADER + " homework "
            + PREFIX_DEADLINE + "31/12/2017";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";

    private final Index index;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param index of the task in the filtered task list to edit
     * @return editTaskDescriptor details to edit the task with
     */
    public EditTaskCommand(Index index, EditTaskDescriptor editTaskDescriptor) {
        requireNonNull(index);
        requireNonNull(editTaskDescriptor);

        this.index = index;
        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
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
        } catch (DuplicateTaskException dee) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException enfe) {
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
                                         EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Header updatedHeader = editTaskDescriptor.getHeader().orElse(taskToEdit.getHeader());
        Desc updatedDesc = editTaskDescriptor.getDesc().orElse(taskToEdit.getDesc());
        Deadline updatedDeadline = editTaskDescriptor.getDeadline().orElse(taskToEdit.getDeadline());

        return new Task(updatedHeader, updatedDesc, updatedDeadline);
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
        private Header header;
        private Desc desc;
        private Deadline deadline;

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.header = toCopy.header;
            this.desc = toCopy.desc;
            this.deadline = toCopy.deadline;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.header, this.desc, this.deadline);
        }

        public void setHeader(Header header) {
            this.header = header;
        }

        public Optional<Header> getHeader() {
            return Optional.ofNullable(header);
        }

        public void setDesc(Desc desc) {
            this.desc = desc;
        }

        public Optional<Desc> getDesc() {
            return Optional.ofNullable(desc);
        }

        public void setDeadline(Deadline deadline) {
            this.deadline = deadline;
        }

        public Optional<Deadline> getDeadline() {
            return Optional.ofNullable(deadline);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTaskDescriptor)) {
                return false;
            }

            // state check
            EditTaskDescriptor e = (EditTaskDescriptor) other;

            return getHeader().equals(e.getHeader())
                    && getDesc().equals(e.getDesc())
                    && getDeadline().equals(e.getDeadline());
        }
    }

}
```
###### /java/seedu/address/logic/commands/UndoableCommand.java
``` java
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
```
###### /java/seedu/address/logic/commands/UndoableCommand.java
``` java
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
```
###### /java/seedu/address/logic/Logic.java
``` java
    /** Returns an unmodifiable view of the filtered list of tasks */
    ObservableList<ReadOnlyTask> getFilteredTaskList();
```
###### /java/seedu/address/logic/LogicManager.java
``` java
    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
```
###### /java/seedu/address/logic/parser/AddCommandParser.java
``` java
            Optional<String> birthdayOptional = argMultimap.getValue(PREFIX_BIRTHDAY);
            Birthday birthday = new Birthday("");
            if (birthdayOptional.isPresent()) {
                birthday = ParserUtil.parseBirthday(argMultimap.getValue(PREFIX_BIRTHDAY)).get();
            }
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case SendEmailCommand.COMMAND_WORD:
        case SendEmailCommand.COMMAND_ALIAS:
            return new SendEmailCommandParser().parse(arguments);

        case AddTaskCommand.COMMAND_WORD:
        case AddTaskCommand.COMMAND_ALIAS:
            return new AddTaskCommandParser().parse(arguments);

        case EditTaskCommand.COMMAND_WORD:
        case EditTaskCommand.COMMAND_ALIAS:
            return new EditTaskCommandParser().parse(arguments);

        case DeleteTaskCommand.COMMAND_WORD:
        case DeleteTaskCommand.COMMAND_ALIAS:
            return new DeleteTaskCommandParser().parse(arguments);
```
###### /java/seedu/address/logic/parser/AddTaskCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEADER;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Desc;
import seedu.address.model.task.Header;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;

/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTaskCommand
     * and returns an AddTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_HEADER, PREFIX_DESC, PREFIX_DEADLINE);

        if (!arePrefixesPresent(argMultimap, PREFIX_HEADER, PREFIX_DESC, PREFIX_DEADLINE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        try {
            Header header = ParserUtil.parseHeader(argMultimap.getValue(PREFIX_HEADER)).get();
            Desc desc = ParserUtil.parseDesc(argMultimap.getValue(PREFIX_DESC)).get();
            Deadline deadline = ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_DEADLINE)).get();

            ReadOnlyTask task = new Task(header, desc, deadline);

            return new AddTaskCommand(task);
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
###### /java/seedu/address/logic/parser/CliSyntax.java
``` java
    public static final Prefix PREFIX_BIRTHDAY = new Prefix("b/");
```
###### /java/seedu/address/logic/parser/CliSyntax.java
``` java
    public static final Prefix PREFIX_EMAIL_SUBJECT = new Prefix("es/");
    public static final Prefix PREFIX_EMAIL_BODY = new Prefix("eb/");
```
###### /java/seedu/address/logic/parser/CliSyntax.java
``` java
    public static final Prefix PREFIX_HEADER = new Prefix("th/");
    public static final Prefix PREFIX_DESC = new Prefix("td/");
    public static final Prefix PREFIX_DEADLINE = new Prefix("tdl/");
```
###### /java/seedu/address/logic/parser/DeleteTaskCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteTaskCommand object
 */
public class DeleteTaskCommandParser implements Parser<DeleteTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTaskCommand
     * and returns an DeleteTaskCommand object for execution.
     *
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
###### /java/seedu/address/logic/parser/EditCommandParser.java
``` java
            ParserUtil.parseBirthday(argMultimap.getValue(PREFIX_BIRTHDAY))
                    .ifPresent(editPersonDescriptor::setBirthday);
```
###### /java/seedu/address/logic/parser/EditTaskCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEADER;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
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
                ArgumentTokenizer.tokenize(args, PREFIX_HEADER, PREFIX_DESC, PREFIX_DEADLINE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();

        try {
            ParserUtil.parseHeader(argMultimap.getValue(PREFIX_HEADER)).ifPresent(editTaskDescriptor::setHeader);
            ParserUtil.parseDesc(argMultimap.getValue(PREFIX_DESC)).ifPresent(editTaskDescriptor::setDesc);
            ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_DEADLINE))
                    .ifPresent(editTaskDescriptor::setDeadline);
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
###### /java/seedu/address/logic/parser/SendEmailCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_BODY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_SUBJECT;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SendEmailCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SendEmailCommand object
 */
public class SendEmailCommandParser implements Parser<SendEmailCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SendEmailCommand
     * and returns an SendEmailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SendEmailCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EMAIL_SUBJECT, PREFIX_EMAIL_BODY);

        if (!arePrefixesPresent(argMultimap, PREFIX_EMAIL_SUBJECT, PREFIX_EMAIL_BODY)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SendEmailCommand.MESSAGE_USAGE));
        }

        try {
            //parse args to find index entered until PREFIX_EMAIL_SUBJECT
            Index index = ParserUtil.parseIndex(args.substring(0, args.indexOf("e")));

            Optional<String> subjectOptional = argMultimap.getValue(PREFIX_EMAIL_SUBJECT);
            String emailSubject = new String("");
            if (subjectOptional.isPresent()) {
                emailSubject = ParserUtil.parseEmailSubject(argMultimap.getValue(PREFIX_EMAIL_SUBJECT)).get();
            }

            Optional<String> bodyOptional = argMultimap.getValue(PREFIX_EMAIL_BODY);
            String emailBody = new String("");
            if (bodyOptional.isPresent()) {
                emailBody = ParserUtil.parseEmailBody(argMultimap.getValue(PREFIX_EMAIL_BODY)).get()
                        .replaceAll("//", "\n");
            }
            return new SendEmailCommand(index, emailSubject, emailBody);
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
###### /java/seedu/address/model/AddressBook.java
``` java
    public void setTasks(List<? extends ReadOnlyTask> tasks) throws DuplicateTaskException {
        this.tasks.setTasks(tasks);
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
        try {
            setTasks(newData.getTaskList());
        } catch (DuplicateTaskException e) {
            assert false : "AddressBooks should not have duplicate tasks";
        }
```
###### /java/seedu/address/model/AddressBook.java
``` java

    //// email-level operations

    /**
     * Sends email to person at index
     * @param index
     * with subject
     * @param subject
     * with body
     * @param body
     * @throws CommandException
     * @throws GoogleAuthException
     */
    public void sendEmail(Index index, String subject, String body) throws CommandException, GoogleAuthException {
        SendEmailCommand send = new SendEmailCommand(index, subject, body);
        send.execute();
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    //// task-level operations

    /**
     * Adds a task to the address book.
     *
     * @throws DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(ReadOnlyTask t) throws DuplicateTaskException {
        Task newTask = new Task(t);
        tasks.add(newTask);
    }

    /**
     * Replaces the given task {@code target} in the list with {@code editedReadOnlyTask}.
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     *
     */
    public void updateTask(ReadOnlyTask target, ReadOnlyTask editedReadOnlyTask)
            throws DuplicateTaskException, TaskNotFoundException {
        requireNonNull(editedReadOnlyTask);

        Task editedTask = new Task(editedReadOnlyTask);
        tasks.setTask(target, editedTask);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws TaskNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeTask(ReadOnlyTask key) throws TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new TaskNotFoundException();
        }
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        return tasks.asObservableList();
    }
```
###### /java/seedu/address/model/Model.java
``` java
    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyTask> PREDICATE_SHOW_ALL_TASKS = unused -> true;
```
###### /java/seedu/address/model/Model.java
``` java
    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws TaskNotFoundException;

    /** Adds the given task */
    void addTask(ReadOnlyTask person) throws DuplicateTaskException;
```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Replaces the given task {@code target} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     */
    void updateTask(ReadOnlyTask target, ReadOnlyTask editedPerson)
            throws DuplicateTaskException, TaskNotFoundException;
```
###### /java/seedu/address/model/Model.java
``` java
    void sendEmail(Index index, String subject, String body) throws CommandException, GoogleAuthException;

    ///** Creates the email to be sent */
    //MimeMessage createEmail(String to, String from, String subject, String bodyText) throws MessagingException;

    ///** Sends the email */
    //Message sendMessage(Gmail service, String userId, MimeMessage emailContent)
    //        throws MessagingException, IOException;

    ///** Creates message using email */
    //Message createMessageWithEmail(MimeMessage emailContent)
    //        throws MessagingException, IOException;
```
###### /java/seedu/address/model/Model.java
``` java

    /** Returns an unmodifiable view of the filtered task list */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<ReadOnlyTask> predicate);

}
```
###### /java/seedu/address/model/ModelManager.java
``` java
    private final FilteredList<ReadOnlyTask> filteredTasks;
```
###### /java/seedu/address/model/ModelManager.java
``` java
        filteredTasks = new FilteredList<>(this.addressBook.getTaskList());
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        addressBook.removeTask(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(ReadOnlyTask task) throws DuplicateTaskException {
        addressBook.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        indicateAddressBookChanged();
    }

    @Override
    public void updateTask(ReadOnlyTask target, ReadOnlyTask editedTask)
            throws DuplicateTaskException, TaskNotFoundException {
        requireAllNonNull(target, editedTask);

        addressBook.updateTask(target, editedTask);
        indicateAddressBookChanged();
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void sendEmail(Index index, String subject, String body) throws CommandException, GoogleAuthException {
        addressBook.sendEmail(index, subject, body);
    }

    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param to email address of the receiver
     * @param from email address of the sender, the mailbox account
     * @param subject subject of the email
     * @param bodyText body text of the email
     * @return the MimeMessage to be used to send email
     * @throws MessagingException
     */
    public static MimeMessage createEmail(String to,
                                          String from,
                                          String subject,
                                          String bodyText)
            throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }

    /**
     * Send an email from the user's mailbox to its recipient.
     *
     * @param service Authorized Gmail API instance.
     * @param userId User's email address. The special value "me"
     * can be used to indicate the authenticated user.
     * @param emailContent Email to be sent.
     * @return The sent message
     * @throws MessagingException
     * @throws IOException
     */
    public static Message sendMessage(Gmail service,
                                      String userId,
                                      MimeMessage emailContent)
            throws MessagingException, IOException {
        Message message = createMessageWithEmail(emailContent);
        message = service.users().messages().send(userId, message).execute();

        System.out.println("Message id: " + message.getId());
        System.out.println(message.toPrettyString());
        return message;
    }

    /**
     * Create a message from an email.
     *
     * @param emailContent Email to be set to raw of message
     * @return a message containing a base64url encoded email
     * @throws IOException
     * @throws MessagingException
     */
    public static Message createMessageWithEmail(MimeMessage emailContent)
            throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java

    //=========== Filtered Task List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyTask} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return FXCollections.unmodifiableObservableList(filteredTasks);
    }

    @Override
    public void updateFilteredTaskList(Predicate<ReadOnlyTask> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
                && filteredTasks.equals(other.filteredTasks);
    }

}
```
###### /java/seedu/address/model/person/Birthday.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Person birthday can only be digits, and should be a valid date "
                    + "represented by a 8 digit number with format ddMMyyyy";
    public static final String BIRTHDAY_VALIDATION_REGEX = "\\b\\d{8}\\b";
    public static final String DATE_FORMAT = "ddMMyyyy";
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
     * Returns if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {
        if (test.matches(BIRTHDAY_VALIDATION_REGEX)) {
            try {
                DateFormat df = new SimpleDateFormat(DATE_FORMAT);
                df.setLenient(false);
                df.parse(test);
                return true;
            } catch (ParseException pe) {
                return false;
            }
        } else if (test.matches("")) {
            return true;
        }
        return false;
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
###### /java/seedu/address/model/person/Person.java
``` java
    private ObjectProperty<Birthday> birthday;
```
###### /java/seedu/address/model/person/Person.java
``` java
        this.birthday = new SimpleObjectProperty<>(birthday);
```
###### /java/seedu/address/model/person/Person.java
``` java
    public void setBirthday(Birthday birthday) {
        this.birthday.set(requireNonNull(birthday));
    }

    @Override
    public ObjectProperty<Birthday> birthdayProperty() {
        return birthday;
    }

    @Override
    public Birthday getBirthday() {
        return birthday.get();
    }
```
###### /java/seedu/address/model/person/ReadOnlyPerson.java
``` java
    ObjectProperty<Birthday> birthdayProperty();
    Birthday getBirthday();
```
###### /java/seedu/address/model/person/ReadOnlyPerson.java
``` java
                && other.getBirthday().equals(this.getBirthday())
```
###### /java/seedu/address/model/person/ReadOnlyPerson.java
``` java
                .append(" Birthday: ")
                .append(getBirthday())
```
###### /java/seedu/address/model/ReadOnlyAddressBook.java
``` java
    /**
     * Returns an unmodifiable view of the tasks list.
     * This list will not contain any duplicate tasks.
     */
    ObservableList<ReadOnlyTask> getTaskList();
```
###### /java/seedu/address/model/task/Deadline.java
``` java
package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a task's deadline in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */
public class Deadline {

    public static final String MESSAGE_DEADLINE_CONSTRAINTS = "Event must have a valid date or day input\n";

    public final String value;

    /**
     * Validates given eventDate.
     *
     * @throws IllegalValueException if given eventDate string is invalid.
     */
    public Deadline(String deadline) throws IllegalValueException {
        requireNonNull(deadline);
        this.value = deadline;
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && this.value.equals(((Deadline) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/model/task/Desc.java
``` java
package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a task's header in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDesc(String)}
 */
public class Desc {

    public static final String MESSAGE_DESC_CONSTRAINTS =
            "Task descriptions can take any values, and it should not be blank";

    /**
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DESC_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given description.
     *
     * @throws IllegalValueException if given desc string is invalid.
     */
    public Desc(String desc) throws IllegalValueException {
        requireNonNull(desc);
        if (!isValidDesc(desc)) {
            throw new IllegalValueException(MESSAGE_DESC_CONSTRAINTS);
        }
        this.value = desc;
    }

    /**
     * Returns true if a given string is a valid task description.
     */
    public static boolean isValidDesc(String test) {
        return test.matches(DESC_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Desc // instanceof handles nulls
                && this.value.equals(((Desc) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/model/task/exceptions/DuplicateTaskException.java
``` java
package seedu.address.model.task.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Task objects.
 */
public class DuplicateTaskException extends DuplicateDataException {
    public DuplicateTaskException() {
        super("Operation would result in duplicate tasks");
    }
}
```
###### /java/seedu/address/model/task/exceptions/TaskNotFoundException.java
``` java
package seedu.address.model.task.exceptions;

/**
 * Signal sthat the operation is unable to find the specified task.
 */
public class TaskNotFoundException extends Exception {
}
```
###### /java/seedu/address/model/task/Header.java
``` java
package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a task's header in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidHeader(String)}
 */
public class Header {

    public static final String MESSAGE_HEADER_CONSTRAINTS =
            "Task's headers can take any values, and it should not be blank";

    /*
     * The first character of the header must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String HEADER_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given header.
     *
     * @throws IllegalValueException if given header string is invalid.
     */
    public Header(String header) throws IllegalValueException {
        requireNonNull(header);
        if (!isValidHeader(header)) {
            throw new IllegalValueException(MESSAGE_HEADER_CONSTRAINTS);
        }
        this.value = header;
    }

    /**
     * Returns true if a given string is a valid task header.
     */
    public static boolean isValidHeader(String test) {
        return test.matches(HEADER_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Header // instanceof handles nulls
                && this.value.equals(((Header) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

```
###### /java/seedu/address/model/task/HeaderContainsKeywordsPredicate.java
``` java
package seedu.address.model.task;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyTask}'s {@code Header} matches any of the keywords given.
 */
public class HeaderContainsKeywordsPredicate implements Predicate<ReadOnlyTask> {
    private final List<String> keywords;

    public HeaderContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyTask event) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(event.getHeader().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HeaderContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((HeaderContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### /java/seedu/address/model/task/ReadOnlyTask.java
``` java
package seedu.address.model.task;

import javafx.beans.property.ObjectProperty;

/**
 * A read-only immutable interface for a Task in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    ObjectProperty<Header> headerProperty();
    Header getHeader();
    ObjectProperty<Desc> descProperty();
    Desc getDesc();
    ObjectProperty<Deadline> deadlineProperty();
    Deadline getDeadline();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getHeader().equals(this.getHeader()) // state checks here onwards
                && other.getDesc().equals(this.getDesc())
                && other.getDeadline().equals(this.getDeadline()));
    }

    /**
     * Formats the task as text, showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getHeader())
                .append(" Desc: ")
                .append(getDesc())
                .append(" Deadline: ")
                .append(getDeadline());
        return builder.toString();
    }

}
```
###### /java/seedu/address/model/task/Task.java
``` java
package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Represents a task in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private ObjectProperty<Header> header;
    private ObjectProperty<Desc> desc;
    private ObjectProperty<Deadline> deadline;

    /**
     * Every field must be present and not null.
     */
    public Task(Header header, Desc desc, Deadline deadline) {
        requireAllNonNull(header, desc, deadline);
        this.header = new SimpleObjectProperty<>(header);
        this.desc = new SimpleObjectProperty<>(desc);
        this.deadline = new SimpleObjectProperty<>(deadline);
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getHeader(), source.getDesc(), source.getDeadline());
    }

    public void setHeader(Header header) {
        this.header.set(requireNonNull(header));
    }

    @Override
    public ObjectProperty<Header> headerProperty() {
        return header;
    }

    @Override
    public Header getHeader() {
        return header.get();
    }

    public void setDesc(Desc desc) {
        this.desc.set(requireNonNull(desc));
    }

    @Override
    public ObjectProperty<Desc> descProperty() {
        return desc;
    }

    @Override
    public Desc getDesc() {
        return desc.get();
    }

    public void setDeadline(Deadline deadline) {
        this.deadline.set(requireNonNull(deadline));
    }

    @Override
    public ObjectProperty<Deadline> deadlineProperty() {
        return deadline;
    }

    @Override
    public Deadline getDeadline() {
        return deadline.get();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(header, desc, deadline);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
```
###### /java/seedu/address/model/task/UniqueTaskList.java
``` java
package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Tasks#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyTask> mappedList = EasyBind.map(internalList, (task) -> task);

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(ReadOnlyTask toAdd) throws DuplicateTaskException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(new Task(toAdd));
    }

    /**
     * Replaces the task {@code target} in the list with {@code editedTask}.
     *
     * @throws DuplicateTaskException if the replacement is equivalent to another existing task in the list.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     */
    public void setTask(ReadOnlyTask target, ReadOnlyTask editedTask)
            throws DuplicateTaskException, TaskNotFoundException {
        requireNonNull(editedTask);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TaskNotFoundException();
        }

        if (!target.equals(editedTask) && internalList.contains(editedTask)) {
            throw new DuplicateTaskException();
        }

        internalList.set(index, new Task(editedTask));
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        requireNonNull(toRemove);
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

    public void setTasks(UniqueTaskList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setTasks(List<?extends ReadOnlyTask> tasks) throws DuplicateTaskException {
        final UniqueTaskList replacement = new UniqueTaskList();
        for (final ReadOnlyTask task : tasks) {
            replacement.add(new Task(task));
        }
        setTasks(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyTask> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && this.internalList.equals(((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### /java/seedu/address/model/util/SampleDataUtil.java
``` java
    public static Task[] getSampleTasks() {
        try {
            return new Task[]{
                new Task(new Header("Homework"), new Desc("Page 6 to 9"), new Deadline("21/11/2017")),
                new Task(new Header("Assignment"), new Desc("Tutorial homework"), new Deadline("17/12/2017")),
                new Task(new Header("Project"), new Desc("Finish Verilog code"), new Deadline("13/12/2017"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }
```
###### /java/seedu/address/storage/XmlAdaptedPerson.java
``` java
    @XmlElement(required = true)
    private String birthday;
```
###### /java/seedu/address/storage/XmlAdaptedPerson.java
``` java
        birthday = source.getBirthday().value;
```
###### /java/seedu/address/storage/XmlAdaptedPerson.java
``` java
        final Birthday birthday = new Birthday(this.birthday);
```
###### /java/seedu/address/storage/XmlAdaptedTask.java
``` java
package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Desc;
import seedu.address.model.task.Header;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String header;
    @XmlElement(required = true)
    private String desc;
    @XmlElement(required = true)
    private String deadline;

    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        header = source.getHeader().value;
        desc = source.getDesc().value;
        deadline = source.getDeadline().value;
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final Header header = new Header(this.header);
        final Desc desc = new Desc(this.desc);
        final Deadline deadline = new Deadline(this.deadline);
        return new Task(header, desc, deadline);
    }
}
```
###### /java/seedu/address/storage/XmlSerializableAddressBook.java
``` java
    @XmlElement
    private List<XmlAdaptedTask> tasks;
```
###### /java/seedu/address/storage/XmlSerializableAddressBook.java
``` java
        tasks = new ArrayList<>();
```
###### /java/seedu/address/storage/XmlSerializableAddressBook.java
``` java
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
```
###### /java/seedu/address/storage/XmlSerializableAddressBook.java
``` java
    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        final ObservableList<ReadOnlyTask> tasks = this.tasks.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(tasks);
    }
```
###### /java/seedu/address/ui/MainWindow.java
``` java
    @FXML
    private StackPane taskListPanelPlaceholder;
```
###### /java/seedu/address/ui/MainWindow.java
``` java
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
```
###### /java/seedu/address/ui/MainWindow.java
``` java
    public PersonListPanel getPersonListPanel() {
        return this.personListPanel;
    }
```
###### /java/seedu/address/ui/PersonCard.java
``` java
    @FXML
    private Label birthday;
```
###### /java/seedu/address/ui/PersonCard.java
``` java
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
```
###### /java/seedu/address/ui/TaskCard.java
``` java
package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.task.ReadOnlyTask;

/**
 * An UI component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyTask task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label header;
    @FXML
    private Label id;
    @FXML
    private Label desc;
    @FXML
    private Label deadline;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        bindListeners(task);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Task} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyTask task) {
        header.textProperty().bind(Bindings.convert(task.headerProperty()));
        desc.textProperty().bind(Bindings.convert(task.descProperty()));
        deadline.textProperty().bind(Bindings.convert(task.deadlineProperty()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        TaskCard card = (TaskCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }
}
```
###### /java/seedu/address/ui/TaskListPanel.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.address.model.task.ReadOnlyTask;


/**
 * Panel containing the list of tasks.
 */
public class TaskListPanel extends UiPart<Region> {
    private static final String FXML = "TaskListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);

    @FXML
    private ListView<TaskCard> taskListView;

    public TaskListPanel(ObservableList<ReadOnlyTask> taskList) {
        super(FXML);
        setConnections(taskList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        ObservableList<TaskCard> mappedList = EasyBind.map(
                taskList, (task) -> new TaskCard(task, taskList.indexOf(task) + 1));
        taskListView.setItems(mappedList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        taskListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                        raise(new TaskPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code TaskCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            taskListView.scrollTo(index);
            taskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code TaskCard}.
     */
    class TaskListViewCell extends ListCell<TaskCard> {

        @Override
        protected void updateItem(TaskCard task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(task.getRoot());
            }
        }
    }

}
```
###### /resources/view/MainWindow.fxml
``` fxml
    <VBox fx:id="taskList" minWidth="140" prefWidth="410" maxWidth="410" prefHeight="620"
          SplitPane.resizableWithParent="false">
      <children>
        <StackPane fx:id="taskListPanelPlaceholder" VBox.vgrow="ALWAYS"/>
      </children>
    </VBox>
```
###### /resources/view/PersonListCard.fxml
``` fxml
      <Label fx:id="birthday" styleClass="cell_small_label" text="\$birthday" />
```
###### /resources/view/TaskListCard.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
  <GridPane HBox.hgrow="ALWAYS">
    <columnConstraints>
      <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
    </columnConstraints>
    <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
      <padding>
        <Insets top="5" right="5" bottom="5" left="15" />
      </padding>
      <HBox spacing="5" alignment="CENTER_LEFT">
        <Label fx:id="id" styleClass="cell_big_label">
          <minWidth>
            <!-- Ensures that the label text is never truncated -->
            <Region fx:constant="USE_PREF_SIZE" />
          </minWidth>
        </Label>
        <Label fx:id="header" text="\$header" styleClass="cell_big_label" />
      </HBox>
      <Label fx:id="desc" styleClass="cell_small_label" text="\$desc" />
      <Label fx:id="deadline" styleClass="cell_small_label" text="\$deadline" />
    </VBox>
    <rowConstraints>
      <RowConstraints />
    </rowConstraints>
  </GridPane>
</HBox>
```
###### /resources/view/TaskListPanel.fxml
``` fxml
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
  <ListView fx:id="taskListView" VBox.vgrow="ALWAYS" />
</VBox>
```
