# deep4k
###### /java/seedu/address/commons/events/model/AliasTokenChangedEvent.java
``` java
/**
 * Represents an event where the user has changed an AliasToken
 */
public class AliasTokenChangedEvent extends BaseEvent {

    /**
     * Action markers for each AliasToken added and removed
     */
    public enum Action {
        Added,
        Removed
    }

    private final ReadOnlyAliasToken token;
    private final Action action;

    public AliasTokenChangedEvent(ReadOnlyAliasToken token, Action action) {
        requireAllNonNull(token, action);

        this.token = token;
        this.action = action;
    }

    @Override
    public String toString() {
        return "Alias token " + action.toString() + ": " + token.toString();
    }

    public ReadOnlyAliasToken getToken() {
        return this.token;
    }

    public Action getAction() {
        return this.action;
    }

}
```
###### /java/seedu/address/commons/events/model/ModelToggleEvent.java
``` java
/**
 * Represents an event where the user has changed between person mode and task mode
 */
public class ModelToggleEvent extends BaseEvent {

    /**
     * Toggle markers for each mode in the application
     */
    public enum Toggle {
        personEnabled,
        taskEnabled
    }

    private final Toggle toggle;

    public ModelToggleEvent(Toggle toggle) {
        requireNonNull(toggle);

        this.toggle = toggle;
    }

    @Override
    public String toString() {
        return "Toggle action: " + toggle.toString();
    }

    public Toggle getToggle() {
        return this.toggle;
    }
}
```
###### /java/seedu/address/logic/commands/alias/AliasCommand.java
``` java
/**
 * Command to create aliases
 */
public class AliasCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "alias";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates an alias for a command or shortcut. "
            + "Parameters: "
            + PREFIX_ALIAS_KEYWORD + "KEYWORD "
            + PREFIX_ALIAS_REPRESENTATION + "REPRESENTATION "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ALIAS_KEYWORD + "ph "
            + PREFIX_ALIAS_REPRESENTATION + " Public Holiday";

    public static final String MESSAGE_SUCCESS = "New alias added: %1$s";
    public static final String MESSAGE_DUPLICATE_ALIAS = "This alias already exists";
    public static final String MESSAGE_INVALID_KEYWORD = "Unable to use a command name as a keyword!";

    private final AliasToken toAdd;
    private Logic logic;

    /**
     * Creates an AliasCommand to add the specified {@code ReadOnlyAliasToken}
     */
    public AliasCommand(ReadOnlyAliasToken aliasToken) {
        toAdd = new AliasToken(aliasToken);
    }

    @Override
    public void setLogic(Logic logic) {
        requireNonNull(logic);
        this.logic = logic;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        for (ReadOnlyAliasToken token : model.getAddressBook().getAliasTokenList()) {
            if (token.getKeyword().keyword.equals(toAdd.getKeyword().keyword)) {
                throw new CommandException(MESSAGE_DUPLICATE_ALIAS);
            }
        }

        if ((logic != null) && (logic.isCommandWord(toAdd.getKeyword().keyword))) {
            throw new CommandException(MESSAGE_INVALID_KEYWORD);
        }
        try {
            model.addAliasToken(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateTokenKeywordException e) {
            throw new CommandException(MESSAGE_DUPLICATE_ALIAS);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AliasCommand // instanceof handles nulls
                && toAdd.equals(((AliasCommand) other).toAdd));
    }

}
```
###### /java/seedu/address/logic/commands/alias/UnaliasCommand.java
``` java
/**
 * Command to remove aliases
 */
public class UnaliasCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "unalias";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes an alias for a command or shortcut. "
            + "Parameters: "
            + PREFIX_ALIAS_KEYWORD + "KEYWORD "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ALIAS_KEYWORD + "ph";

    public static final String MESSAGE_SUCCESS = "Alias removed: %1$s";
    public static final String MESSAGE_UNKNOWN_ALIAS = "This alias is not in use";

    private Keyword keyword;
    private ReadOnlyAliasToken toRemove;

    /**
     * Creates an UnaliasCommand to add the specified {@code Keyword, @ReadOnlyAliasToken}
     */

    public UnaliasCommand(Keyword keyword) {
        this.keyword = keyword;
        this.toRemove = null;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        for (ReadOnlyAliasToken token : model.getAddressBook().getAliasTokenList()) {
            if (token.getKeyword().keyword.equalsIgnoreCase(keyword.keyword)) {
                toRemove = token;
                break;
            }
        }
        try {
            if (toRemove == null) {
                throw new CommandException(MESSAGE_UNKNOWN_ALIAS);
            }
            model.deleteAliasToken(toRemove);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toRemove));
        } catch (TokenKeywordNotFoundException e) {
            throw new CommandException(MESSAGE_UNKNOWN_ALIAS);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnaliasCommand // instanceof handles nulls
                && keyword.equals(((UnaliasCommand) other).keyword));
    }
}


```
###### /java/seedu/address/logic/commands/EnablePersonCommand.java
``` java
/**
 * Enables person commands for the user
 */
public class EnablePersonCommand extends Command {

    public static final String COMMAND_WORD = "person";

    public static final String MESSAGE_SUCCESS = "Showing all persons and only person commands are enabled";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ModelToggleEvent(ModelToggleEvent.Toggle.personEnabled));
        EventsCenter.getInstance().post(new ToggleToPersonViewEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### /java/seedu/address/logic/commands/EnableTaskCommand.java
``` java
/**
 * Enables task commands for the user
 */
public class EnableTaskCommand extends Command {

    public static final String COMMAND_WORD = "task";

    public static final String MESSAGE_SUCCESS = "Showing all tasks and only task commands are enabled";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ModelToggleEvent(ModelToggleEvent.Toggle.taskEnabled));
        EventsCenter.getInstance().post(new ToggleToTaskViewEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### /java/seedu/address/logic/commands/person/RemarkCommand.java
``` java
/**
 * Changes the remark of an existing person in the address book.
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the remark of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMARK + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Likes to swim.";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Person: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed remark from Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final Remark remark;

    /**
     * @param index  of the person in the filtered person list to edit the remark
     * @param remark of the person
     */
    public RemarkCommand(Index index, Remark remark) {
        requireNonNull(index);
        requireNonNull(remark);

        this.index = index;
        this.remark = remark;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getBirthday(), remark, personToEdit.getTags(),
                personToEdit.isPrivate(), personToEdit.isPinned());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_NOT_HIDDEN);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * @param personToEdit
     * @return
     */
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (!remark.value.isEmpty()) {
            return String.format(MESSAGE_ADD_REMARK_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_DELETE_REMARK_SUCCESS, personToEdit);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        // state check
        RemarkCommand e = (RemarkCommand) other;
        return index.equals(e.index)
                && remark.equals(e.remark);
    }
}
```
###### /java/seedu/address/logic/commands/task/AddTaskCommand.java
``` java
/**
 * Adds the given user input as a task in the application
 */
public class AddTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";

    public static final String COMMAND_FORMAT = "add [HEADER]\n"
            + "add [HEADER] by [DEADLINE] \n"
            + "add [HEADER] from [START TIME] to <[END TIME]>"
            + "add '[full text to be used as header]'";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the address book. "
            + COMMAND_FORMAT + "\n"
            + "Example: " + COMMAND_WORD + " "
            + "Go Fishing from 9am to 11am";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book";

    private ReadOnlyTask toAdd = null;


    /**
     * Empty Constructor
     */
    public AddTaskCommand() {
    }

    /**
     * Copy Constructor
     */
    public AddTaskCommand(ReadOnlyTask task) {
        this.toAdd = new Task(task);
    }

    /**
     * Creates an AddTaskCommand to add a task with only a header
     *
     * @throws IllegalValueException if the String specified{@code header} is invalid
     */
    public AddTaskCommand(String header) throws IllegalValueException {
        this.toAdd = new Task(new Header(header));
    }

    /**
     * Creates an AddTaskCommand to add a task with a header and deadline
     *
     * @throws IllegalValueException if input values are invalid
     */
    public AddTaskCommand(String header, Optional<LocalDateTime> deadLineDate)
            throws IllegalValueException {
        this.toAdd = new Task(new Header(header), deadLineDate);
    }

    /**
     * Creates an AddTaskCommand to add a task with header, start date time and end end date time
     *
     * @throws IllegalValueException if input values are invalid
     */
    public AddTaskCommand(String header, Optional<LocalDateTime> startDateTime, Optional<LocalDateTime> endDateTime)
            throws IllegalValueException {
        Optional<LocalDateTime> balancedEndDateTime = endDateTime;
        if (startDateTime.isPresent() && endDateTime.isPresent()) {
            balancedEndDateTime = Optional.of(DateTimeParserUtil.balanceStartAndEndDateTime(
                    startDateTime.get(), endDateTime.get()));
        }
        this.toAdd = new Task(new Header(header), startDateTime, balancedEndDateTime);
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
        return other == this // short circuit if same object
                || (other instanceof AddTaskCommand // instanceof handles nulls
                && toAdd.equals(((AddTaskCommand) other).toAdd));
    }
}
```
###### /java/seedu/address/logic/commands/task/DeleteTaskCommand.java
``` java
/**
 * Deletes a task identified using it's last displayed index from the task listing.
 */
public class DeleteTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX START (must be a positive integer) ~ INDEX END(must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1" + " ~" + " 3";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task(s): %1$s";

    private final List<Index> targetIndices = new ArrayList<>();

    public DeleteTaskCommand(Index targetIndex) {
        this.targetIndices.add(targetIndex);
    }

    public DeleteTaskCommand(List<Index> indices) {
        targetIndices.addAll(indices);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyTask> tasksToDelete = new ArrayList<>();
        int counter = 0;
        for (Index checkException : targetIndices) {
            List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
            if (checkException.getZeroBased() - counter >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
        }
        for (Index targetIndex : targetIndices) {
            List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
            ReadOnlyTask taskToDelete = lastShownList.get(targetIndex.getZeroBased() - counter);
            tasksToDelete.add(taskToDelete);

            try {
                model.deleteTask(taskToDelete);
            } catch (TaskNotFoundException tnfe) {
                assert false : "The target task cannot be missing";
            }
            counter++;
        }
        StringBuilder builder = new StringBuilder();
        for (ReadOnlyTask toAppend : tasksToDelete) {
            builder.append("\n" + toAppend.toString());
        }
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, builder));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTaskCommand // instanceof handles nulls
                && this.targetIndices.equals(((DeleteTaskCommand) other).targetIndices)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/task/FindTaskCommand.java
``` java
/**
 * Finds and lists all tasks in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindTaskCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose headers contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " dental checkup";

    private final TaskHasKeywordsPredicate predicate;

    public FindTaskCommand(TaskHasKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(predicate);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTaskCommand // instanceof handles nulls
                && this.predicate.equals(((FindTaskCommand) other).predicate)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/task/ListTaskCommand.java
``` java
/**
 * Lists all persons in the address book to the user.
 */
public class ListTaskCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";


    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

```
###### /java/seedu/address/logic/commands/task/MarkTaskCommand.java
``` java
/**
 * Marks task(s) identified using it's last displayed index in the task listing.
 */
public class MarkTaskCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the identified task(s) as done "
            + "by the index number used in the last task listing.\n"
            + "Parameters: INDEX START (must be a positive integer) ~ INDEX END(must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1" + " ~" + " 3";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "Marked Task(s): %1$s";
    private static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";

    private final List<Index> targetIndices = new ArrayList<>();
    private ArrayList<ReadOnlyTask> tasksToMark;

    public MarkTaskCommand(Index targetIndex) {
        this.targetIndices.add(targetIndex);
    }

    public MarkTaskCommand(List<Index> indices) {
        this.targetIndices.addAll(indices);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        List<ReadOnlyTask> tasksToMark = new ArrayList<ReadOnlyTask>();
        int counter = 0;
        for (Index checkException : targetIndices) {
            List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
            if (checkException.getZeroBased() - counter >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
        }
        for (Index targetIndex : targetIndices) {
            List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
            ReadOnlyTask taskToMark = lastShownList.get(targetIndex.getZeroBased());
            tasksToMark.add(taskToMark);
            counter++;
        }

        try {
            model.markTasks(tasksToMark);
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        for (ReadOnlyTask toAppend : tasksToMark) {
            builder.append(toAppend.toString());
        }

        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new

                CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, builder));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MarkTaskCommand // instanceof handles nulls
                && this.targetIndices.equals(((MarkTaskCommand) other).targetIndices)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/task/RenameTaskCommand.java
``` java
/**
 * Renames the header target task in the task listing.
 */
public class RenameTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "rename";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the header the task identified "
            + "by the index number used in the last task listing. "
            + "Parameters: INDEX (must be a positive integer) "
            + "[New header]"
            + "Example: " + COMMAND_WORD + " 2 Football training";

    public static final String MESSAGE_RENAME_TASK_SUCCESS = "Renamed Task: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task header already exists in the address book.";

    private final Index index;
    private Header newTaskHeader;

    public RenameTaskCommand(Index targetIndex, String header) throws IllegalValueException {
        requireNonNull(targetIndex);
        requireNonNull(header);
        this.index = targetIndex;
        this.newTaskHeader = new Header(header);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        ObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToRename = lastShownList.get(index.getZeroBased());
        Task renamedTask = new Task(taskToRename);
        renamedTask.setHeader(newTaskHeader);

        try {
            model.updateTask(taskToRename, renamedTask);
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException e) {
            throw new AssertionError("The target task cannot be missing");
        }

        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_RENAME_TASK_SUCCESS, renamedTask));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RenameTaskCommand)) {
            return false;
        }

        // state check
        RenameTaskCommand e = (RenameTaskCommand) other;
        return index.equals(e.index)
                && newTaskHeader.equals(e.newTaskHeader);
    }
}
```
###### /java/seedu/address/logic/commands/task/RescheduleTaskCommand.java
``` java
/**
 * Reschedules a task in the task list
 */
public class RescheduleTaskCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "reschedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the times of the task identified "
            + "by the index number used in the last task listing. "
            + "Parameters: INDEX (must be a positive integer) "
            + "1. by [DEADLINE] "
            + "2. from [START TIME] to [END TIME] "
            + "Example: " + COMMAND_WORD + " 1 from 8am to 12pm";

    private static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";
    private static final String MESSAGE_RESCHEDULE_SUCCESS = "Task rescheduled: %1$s";

    private Index index;
    private Optional<LocalDateTime> newStartDateTime = Optional.empty();
    private Optional<LocalDateTime> newEndDateTime = Optional.empty();

    /**
     * Empty Constructor
     */
    public RescheduleTaskCommand() {
    }

    public RescheduleTaskCommand(Index targetIndex, Optional<LocalDateTime> startTime,
                                 Optional<LocalDateTime> endTime) throws IllegalValueException {

        requireNonNull(targetIndex);
        Optional<LocalDateTime> balancedEndTime = endTime;
        if (startTime.isPresent() && endTime.isPresent()) {
            balancedEndTime = Optional.of(
                    DateTimeParserUtil.balanceStartAndEndDateTime(startTime.get(), endTime.get()));
        }
        this.index = targetIndex;
        this.newEndDateTime = balancedEndTime;
        this.newStartDateTime = startTime;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        ObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToReschedule = lastShownList.get(index.getZeroBased());
        Task rescheduledTask = new Task(taskToReschedule);
        rescheduledTask.setStartDateTime(newStartDateTime);
        rescheduledTask.setEndDateTime(newEndDateTime);

        try {
            model.updateTask(taskToReschedule, rescheduledTask);
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException e) {
            throw new AssertionError("The target task cannot be missing");
        }

        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_RESCHEDULE_SUCCESS, rescheduledTask));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RescheduleTaskCommand)) {
            return false;
        }

        // state check
        RescheduleTaskCommand e = (RescheduleTaskCommand) other;
        return index.equals(e.index)
                && newEndDateTime.equals(e.newEndDateTime)
                && newStartDateTime.equals(e.newStartDateTime);
    }

}
```
###### /java/seedu/address/logic/commands/task/UnmarkTaskCommand.java
``` java
/**
 * Unmarks task(s) identified using it's last displayed indices in the task listing.
 */

public class UnmarkTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unmarks the identified task(s) from done "
            + "by the index number used in the last task listing.\n"
            + "Parameters: INDEX START (must be a positive integer) ~ INDEX END(must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1" + " ~" + " 3";

    public static final String MESSAGE_UNMARK_TASK_SUCCESS = "Unmarked Task(s): %1$s";
    private static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";

    private final List<Index> targetIndices = new ArrayList<>();
    private ArrayList<ReadOnlyTask> tasksToUnmark;

    public UnmarkTaskCommand(Index targetIndex) {
        this.targetIndices.add(targetIndex);
    }

    public UnmarkTaskCommand(List<Index> indices) {
        targetIndices.addAll(indices);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        List<ReadOnlyTask> tasksToUnmark = new ArrayList<>();
        int counter = 0;
        for (Index checkException : targetIndices) {
            List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
            if (checkException.getZeroBased() - counter >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
        }

        for (Index targetIndex : targetIndices) {
            List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
            ReadOnlyTask taskToUnmark = lastShownList.get(targetIndex.getZeroBased());
            tasksToUnmark.add(taskToUnmark);
        }

        try {
            model.unmarkTasks(tasksToUnmark);
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        for (ReadOnlyTask toAppend : tasksToUnmark) {
            builder.append(toAppend.toString());
        }

        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_UNMARK_TASK_SUCCESS, builder));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnmarkTaskCommand // instanceof handles nulls
                && this.targetIndices.equals(((UnmarkTaskCommand) other).targetIndices)); // state check
    }
}
```
###### /java/seedu/address/logic/LogicManager.java
``` java
    /**
     * Registers all commands with parsers into addressBookParser
     */
    private void registerAllDefaultCommandParsers() {
        addressBookParser.registerCommandParser(new AddCommandParser());
        addressBookParser.registerCommandParser(new DeleteCommandParser());
        addressBookParser.registerCommandParser(new FindCommandParser());
        addressBookParser.registerCommandParser(new EditCommandParser());
        addressBookParser.registerCommandParser(new AliasCommandParser());
        addressBookParser.registerCommandParser(new SortCommandParser());
        addressBookParser.registerCommandParser(new HideCommandParser());
        addressBookParser.registerCommandParser(new RemarkCommandParser());
        addressBookParser.registerCommandParser(new UnaliasCommandParser());
        addressBookParser.registerCommandParser(new SelectCommandParser());
        addressBookParser.registerCommandParser(new PinCommandParser());
        addressBookParser.registerCommandParser(new UnpinCommandParser());
        addressBookParser.registerCommandParser(new AddTaskCommandParser());
        addressBookParser.registerCommandParser(new DeleteTaskCommandParser());
        addressBookParser.registerCommandParser(new FindTaskCommandParser());
        addressBookParser.registerCommandParser(new MarkTaskCommandParser());
        addressBookParser.registerCommandParser(new UnmarkTaskCommandParser());
        addressBookParser.registerCommandParser(new RenameTaskCommandParser());
        addressBookParser.registerCommandParser(new RescheduleTaskCommandParser());
    }

    /**
     * Registers all other commands without parsers into addressBookParser
     */
    private void registerAllOtherCommands() {
        addressBookParser.registerOtherCommands();
    }

    /**
     * Loads existing aliases in model into addressBookParser
     */
    private void loadAllAliasTokens() {
        ObservableList<ReadOnlyAliasToken> allAliasTokens = model.getAddressBook().getAliasTokenList();
        for (ReadOnlyAliasToken token : allAliasTokens) {
            addressBookParser.addAliasToken(token);
        }
    }

    @Subscribe
    public void handleAliasTokenChangedEvent(AliasTokenChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(
                event, "Alias " + event.getAction().toString().toLowerCase()));
        if (event.getAction().equals(AliasTokenChangedEvent.Action.Added)) {
            logAliasChangeForParser(
                    addressBookParser.addAliasToken(event.getToken()),
                    event.getToken(),
                    "Successfully added AliasToken %s to main parser",
                    "Failed to add AliasToken '%s' to parser");
        } else if (event.getAction().equals(AliasTokenChangedEvent.Action.Removed)) {
            logAliasChangeForParser(
                    addressBookParser.removeAliasToken(event.getToken()),
                    event.getToken(),
                    "Successfully removed AliasToken '%s' from parser",
                    "Failed to removed AliasToken '%s' from parser");
        }
    }

    @Subscribe
    public void handleModeToggleEvent(ModelToggleEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(
                event, "Toggle: " + event.getToggle().toString()));
        if (event.getToggle().equals(ModelToggleEvent.Toggle.personEnabled)) {
            logModelToggelForParser(addressBookParser.enablePersonToggle(),
                    "Successfully toggled to Person Commands in main parser",
                    "Failed to toggle to Person Commands in main parser");
        } else if (event.getToggle().equals(ModelToggleEvent.Toggle.taskEnabled)) {
            logModelToggelForParser(
                    addressBookParser.enableTaskToggle(),
                    "Successfully toggled to Task Commands in main parser",
                    "Failed to toggle to Task Commands in main parser");
        }
    }

    /**
     * Enter result of changed AliasToken with the logger into the main parser.
     *
     * @param messageSuccessful The String to be input if the operation is a success
     * @param messageFailure    The String to be input if the operation is a failure
     */
    private void logAliasChangeForParser(boolean isSuccessful, ReadOnlyAliasToken tokenChanged,
                                         String messageSuccessful, String messageFailure) {
        if (isSuccessful) {
            logger.info(String.format(messageSuccessful, tokenChanged));
        } else {
            logger.warning(String.format(messageFailure, tokenChanged));
        }
    }

    /**
     * Enter result of changed ModelToggle with the logger into the main parser.
     *
     * @param messageSuccessful The String to be input if the operation is a success
     * @param messageFailure    The String to be input if the operation is a failure
     */
    private void logModelToggelForParser(boolean isSuccessful,
                                         String messageSuccessful, String messageFailure) {
        if (isSuccessful) {
            logger.info(messageSuccessful);
        } else {
            logger.warning(messageFailure);
        }
    }


    @Override
    public boolean isCommandWord(String keyword) {
        return addressBookParser.isCommandRegistered(keyword);
    }

    @Override
    public ObservableList<ReadOnlyAliasToken> getFilteredAliasTokenList() {
        return model.getFilteredAliasTokenList();
    }

    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Pattern KEYWORD_PATTERN =
            Pattern.compile("([^\\s/]+)([\\s/]+|$)");

    private static final String MESSAGE_PERSON_MODEL_MODE = "This command only works with persons"
            + "\nPlease toggle to person view.";

    private static final String MESSAGE_TASK_MODEL_MODE = "This command only works with tasks"
            + "\nPlease toggle to task view.";

    private final Map<String, Parser<? extends Command>> commandMap;
    private final Map<String, ReadOnlyAliasToken> aliasMap;
    private final ObservableList<ReadOnlyAliasToken> aliasList = FXCollections.observableArrayList();
    private boolean isPersonEnabled;
    private boolean isTaskEnabled;

    public AddressBookParser() {
        this.commandMap = new HashMap<String, Parser<? extends Command>>();
        this.aliasMap = new HashMap<String, ReadOnlyAliasToken>();
        this.isPersonEnabled = true;
        this.isTaskEnabled = false;
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        final String checkedCommandWord = commandWordCheck(commandWord);
        final String checkedArguments = argumentsCheck(checkedCommandWord, arguments);


        switch (checkedCommandWord) {

        case AddCommand.COMMAND_WORD:
            if (isPersonEnabled && !isTaskEnabled) {
                return new AddCommandParser().parse(checkedArguments);
            } else {
                return new AddTaskCommandParser().parse(checkedArguments);
            }

        case EditCommand.COMMAND_WORD:
            if (isPersonEnabled && !isTaskEnabled) {
                return new EditCommandParser().parse(checkedArguments);
            } else {
                throw new ParseException(MESSAGE_PERSON_MODEL_MODE);
            }

        case SelectCommand.COMMAND_WORD:
            if (isPersonEnabled && !isTaskEnabled) {
                return new SelectCommandParser().parse(checkedArguments);
            } else {
                throw new ParseException(MESSAGE_PERSON_MODEL_MODE);
            }

        case DeleteCommand.COMMAND_WORD:
            if (isPersonEnabled && !isTaskEnabled) {
                return new DeleteCommandParser().parse(checkedArguments);
            } else {
                return new DeleteTaskCommandParser().parse(checkedArguments);
            }

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case PinCommand.COMMAND_WORD:
            if (isPersonEnabled && !isTaskEnabled) {
                return new PinCommandParser().parse(arguments);
            } else {
                throw new ParseException(MESSAGE_PERSON_MODEL_MODE);
            }

        case ListPinCommand.COMMAND_WORD:
            if (isPersonEnabled && !isTaskEnabled) {
                return new ListPinCommand();
            } else {
                throw new ParseException(MESSAGE_PERSON_MODEL_MODE);
            }

        case UnpinCommand.COMMAND_WORD:
            if (isPersonEnabled && !isTaskEnabled) {
                return new UnpinCommandParser().parse(arguments);
            } else {
                throw new ParseException(MESSAGE_PERSON_MODEL_MODE);
            }

        case HideCommand.COMMAND_WORD:
            if (isPersonEnabled && !isTaskEnabled) {
                return new HideCommandParser().parse(checkedArguments);
            } else {
                throw new ParseException(MESSAGE_PERSON_MODEL_MODE);
            }

        case FindCommand.COMMAND_WORD:
            if (isPersonEnabled && !isTaskEnabled) {
                return new FindCommandParser().parse(checkedArguments);
            } else {
                return new FindTaskCommandParser().parse(checkedArguments);
            }

        case RemarkCommand.COMMAND_WORD:
            if (isPersonEnabled && !isTaskEnabled) {
                return new RemarkCommandParser().parse(checkedArguments);
            } else {
                throw new ParseException(MESSAGE_PERSON_MODEL_MODE);
            }

        case ListCommand.COMMAND_WORD:
            if (isPersonEnabled && !isTaskEnabled) {
                return new ListCommand();
            } else {
                return new ListTaskCommand();
            }

        case SortCommand.COMMAND_WORD:
            if (isPersonEnabled && !isTaskEnabled) {
                return new SortCommandParser().parse(checkedArguments);
            } else {
                throw new ParseException(MESSAGE_PERSON_MODEL_MODE);
            }

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case AliasCommand.COMMAND_WORD:
            return new AliasCommandParser().parse(checkedArguments);

        case UnaliasCommand.COMMAND_WORD:
            return new UnaliasCommandParser().parse(checkedArguments);

        case EnableTaskCommand.COMMAND_WORD:
            return new EnableTaskCommand();

        case EnablePersonCommand.COMMAND_WORD:
            return new EnablePersonCommand();

        case MarkTaskCommand.COMMAND_WORD:
            if (!isPersonEnabled && isTaskEnabled) {
                return new MarkTaskCommandParser().parse(checkedArguments);
            } else {
                throw new ParseException(MESSAGE_TASK_MODEL_MODE);
            }

        case UnmarkTaskCommand.COMMAND_WORD:
            if (!isPersonEnabled && isTaskEnabled) {
                return new UnmarkTaskCommandParser().parse(checkedArguments);
            } else {
                throw new ParseException(MESSAGE_TASK_MODEL_MODE);
            }

        case RenameTaskCommand.COMMAND_WORD:
            if (!isPersonEnabled && isTaskEnabled) {
                return new RenameTaskCommandParser().parse(checkedArguments);
            } else {
                throw new ParseException(MESSAGE_TASK_MODEL_MODE);
            }

        case RescheduleTaskCommand.COMMAND_WORD:
            if (!isPersonEnabled && isTaskEnabled) {
                return new RescheduleTaskCommandParser().parse(checkedArguments);
            } else {
                throw new ParseException(MESSAGE_TASK_MODEL_MODE);
            }

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Checks if the commandWord is used with an alias
     *
     * @param commandWord - the first arg of the command statement
     * @return checkedCommandWord , containing the representation if it has been aliased
     */
    private String commandWordCheck(String commandWord) {
        String checkedCommandWord = commandWord;

        ReadOnlyAliasToken token = aliasMap.get(commandWord);
        if (token != null) {
            checkedCommandWord = token.getRepresentation().representation;
        }
        return checkedCommandWord;
    }

    /**
     * Checks if any of the arguments have an alias
     *
     * @param arguments - the arguments after the command word
     * @returns a string that contains the arguments with replaced representations if any of them had aliases.
     */
    private String argumentsCheck(String checkedCommandWord, String arguments) {

        if ("alias".equals(checkedCommandWord) || ("unalias".equals(checkedCommandWord))) {
            return arguments;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(" ");
        Matcher matcher = KEYWORD_PATTERN.matcher(arguments);

        while (matcher.find()) {
            String keyword = matcher.group(1);
            String spaces = matcher.group(2); // The amount of spaces entered is kept the same
            ReadOnlyAliasToken token = aliasMap.get(keyword);
            if (token != null) {
                keyword = token.getRepresentation().representation;
            }
            builder.append(keyword);
            builder.append(spaces);
        }

        return builder.toString();
    }

    /**
     * Adds an AliasToken to be used by parser to replace all alias's keyword with its representation
     * before parsing.
     *
     * @param toAdd
     * @return
     */
    public boolean addAliasToken(ReadOnlyAliasToken toAdd) {
        requireNonNull(toAdd);

        if (aliasMap.containsKey(toAdd.getKeyword().keyword)) {
            return false;
        }

        if (isCommandRegistered(toAdd.getKeyword().keyword)) {
            return false;
        }

        aliasList.add(toAdd);
        aliasMap.put(toAdd.getKeyword().keyword, toAdd);
        return true;
    }

    /**
     * Removes an AliasToken that is used by parser to replace all alias's keyword with its representation
     * before parsing.
     *
     * @param toRemove
     * @return
     */
    public boolean removeAliasToken(ReadOnlyAliasToken toRemove) {
        requireNonNull(toRemove);

        ReadOnlyAliasToken token = aliasMap.remove(toRemove.getKeyword().keyword);
        if (token != null) {
            return aliasList.remove(token);
        } else {
            return false;
        }
    }


    /**
     * Registers a command parser into the commandMap
     *
     * @param commandParser the map of command parsers
     * @return true if successfully registered, false if parser with same command word
     * already registered or if an alias with the same keyword is previously added.
     */
    public boolean registerCommandParser(Parser<? extends Command> commandParser) {
        requireNonNull(commandParser);

        if (commandMap.containsKey(commandParser.getCommandWord())) {
            return false;
        }
        if (aliasMap.containsKey(commandParser.getCommandWord())) {
            return false;
        }

        commandMap.put(commandParser.getCommandWord(), commandParser);
        return true;
    }

    /**
     * Registers the command words of commands that do not have a parser into the commandMap
     */
    public void registerOtherCommands() {
        commandMap.put("clear", null);
        commandMap.put("exit", null);
        commandMap.put("help", null);
        commandMap.put("history", null);
        commandMap.put("list", null);
        commandMap.put("listpin", null);
        commandMap.put("redo", null);
        commandMap.put("undo", null);
    }

    public boolean isCommandRegistered(String header) {
        return commandMap.containsKey(header);
    }

    public Parser<? extends Command> unregisterCommandParser(String header) {
        return commandMap.remove(header);
    }

    public ObservableList<ReadOnlyAliasToken> getAliasTokenList() {
        return aliasList;
    }

    /**
     * Enables the the model command mode of either person or task to be used by main parser
     *
     * @return true for confirmation of enablePersonToggle
     */
    public boolean enablePersonToggle() {
        isPersonEnabled = true;
        isTaskEnabled = false;
        return true;
    }

    /**
     * Enables the the model command mode of either person or task to be used by main parser
     *
     * @return true for confirmation of enablePersonToggle
     */
    public boolean enableTaskToggle() {
        isPersonEnabled = false;
        isTaskEnabled = true;
        return true;
    }


}
```
###### /java/seedu/address/logic/parser/alias/AliasCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AliasCommand object
 */
public class AliasCommandParser implements Parser<AliasCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AliasCommand
     * and returns an AliasCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AliasCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ALIAS_KEYWORD, PREFIX_ALIAS_REPRESENTATION);
        if (!arePrefixesPresent(argMultimap, PREFIX_ALIAS_KEYWORD, PREFIX_ALIAS_REPRESENTATION)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
        }
        try {
            Keyword keyword = ParserUtil.parseKeyword(
                    argMultimap.getValue(PREFIX_ALIAS_KEYWORD)).get();
            Representation representation = ParserUtil.parseRepresentation(
                    argMultimap.getValue(PREFIX_ALIAS_REPRESENTATION)).get();
            ReadOnlyAliasToken aliasToken = new AliasToken(keyword, representation);
            return new AliasCommand(aliasToken);
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

    @Override
    public String getCommandWord() {
        return AliasCommand.COMMAND_WORD;
    }
}


```
###### /java/seedu/address/logic/parser/alias/UnaliasCommandParser.java
``` java
/**
 * Parses input arguments and creates a new UnaliasCommand object
 */
public class UnaliasCommandParser implements Parser<UnaliasCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnaliasCommand
     * and returns an UnaliasCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public UnaliasCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ALIAS_KEYWORD);
        if (!arePrefixesPresent(argMultimap, PREFIX_ALIAS_KEYWORD)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnaliasCommand.MESSAGE_USAGE));
        }
        try {
            Keyword keyword = ParserUtil.parseKeyword(
                    argMultimap.getValue(PREFIX_ALIAS_KEYWORD)).get();
            return new UnaliasCommand(keyword);
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

    @Override
    public String getCommandWord() {
        return UnaliasCommand.COMMAND_WORD;
    }
}

```
###### /java/seedu/address/logic/parser/DateTimeParserUtil.java
``` java
/**
 * Contains utility methods used for parsing DateTime in the various *Parser classes.
 */
public class DateTimeParserUtil {

    /**
     * Parses user input String specified{@code args} into LocalDateTime objects using Natural Language Parsing(NLP)
     *
     * @return Empty Optional if args could not be parsed
     * @Disclaimer : The parser used is a dependency called 'natty' developed by 'Joe Stelmach'
     */
    public static Optional<LocalDateTime> nattyParseDateTime(String args) {
        if (args == null || args.isEmpty()) {
            return Optional.empty();
        }

        com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
        List groups = parser.parse(args);

        if (groups.size() <= 0) {
            return Optional.empty();
        }

        DateGroup dateGroup = (DateGroup) groups.get(0);

        if (dateGroup.getDates().size() < 0) {
            return Optional.empty();
        }

        Date date = dateGroup.getDates().get(0);

        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return Optional.ofNullable(localDateTime);
    }

    /**
     * Receives two LocalDateTime and ensures that the specified {@code endDateTime} is always later in time than
     * specified {@code startDateTime}
     *
     * @return endDateTime that checks the above confirmation
     */
    public static LocalDateTime balanceStartAndEndDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        LocalDateTime newEndDateTime = endDateTime;
        while (startDateTime.compareTo(newEndDateTime) >= 1) {
            newEndDateTime = newEndDateTime.plusDays(1);
        }
        return newEndDateTime;
    }

    public static boolean containsTime(String args) {
        return nattyParseDateTime(args).isPresent();
    }
}
```
###### /java/seedu/address/logic/parser/person/RemarkCommandParser.java
``` java
/**
 * Parses input arguments and creates new RemarkCommand object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns an RemarkCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        String remark = argMultimap.getValue(PREFIX_REMARK).orElse("");

        return new RemarkCommand(index, new Remark(remark));
    }

    @Override
    public String getCommandWord() {
        return RemarkCommand.COMMAND_WORD;
    }
}

```
###### /java/seedu/address/logic/parser/task/AddTaskCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    private static final Pattern ADD_SCHEDULE_ARGS_REGEX = Pattern.compile("(?:.+?(?=(?:(?:(?i)by|from|to)\\s|$)))+?");
    private static final Pattern QUOTATION_REGEX = Pattern.compile("\'([^\']*)\'");

    private static final String ARGS_FROM = "from";
    private static final String ARGS_BY = "by";
    private static final String ARGS_TO = "to";
    private static final String FILLER_WORD = "FILLER ";

    private static final String SINGLE_QUOTE = "\'";
    private static final String[] PARSED_TIME_ARGS = new String[]{ARGS_FROM, ARGS_TO, ARGS_BY};

    /**
     * Parses the given {@code String} of arguments in the context of the AddTaskCommand
     * and returns an AddTaskCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTaskCommand parse(String args) throws ParseException {

        StringBuilder headerBuilder = new StringBuilder();
        HashMap<String, Optional<LocalDateTime>> dateTimeMap = new HashMap<>();

        Optional<String> checkedArgs = checkForQuotation(args);
        if (checkedArgs.isPresent()) {
            headerBuilder.append(checkedArgs.get().replace(SINGLE_QUOTE, ""));
            args = FILLER_WORD + args.replace(checkedArgs.get(), "");
        }

        Matcher matcher = ADD_SCHEDULE_ARGS_REGEX.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        try {
            matcher.reset();
            matcher.find();
            if (headerBuilder.length() == 0) {
                headerBuilder.append(matcher.group(0));
            }

            BiConsumer<String, String> consumer = (matchedGroup, token) -> {
                String time = matchedGroup.substring(token.length(), matchedGroup.length());
                if (DateTimeParserUtil.containsTime(time)) {
                    dateTimeMap.put(token, DateTimeParserUtil.nattyParseDateTime(time));
                } else {
                    headerBuilder.append(matchedGroup);
                }
            };

            executeOnEveryMatcherToken(matcher, consumer);

            String header = headerBuilder.toString();

            if (header.length() == 0) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
            }
            boolean hasDeadlineKeyword = dateTimeMap.containsKey(ARGS_BY);
            boolean hasStartTimeKeyword = dateTimeMap.containsKey(ARGS_FROM);
            boolean hasEndTimeKeyword = dateTimeMap.containsKey(ARGS_TO);

            if (hasDeadlineKeyword && !hasStartTimeKeyword && !hasEndTimeKeyword) {
                return new AddTaskCommand(header, dateTimeMap.get(ARGS_BY));
            }

            if (!hasDeadlineKeyword && hasStartTimeKeyword && hasEndTimeKeyword) {
                return new AddTaskCommand(header, dateTimeMap.get(ARGS_FROM), dateTimeMap.get(ARGS_TO));
            }

            if (!hasDeadlineKeyword && !hasStartTimeKeyword && !hasEndTimeKeyword) {
                return new AddTaskCommand(header);
            }

            return new AddTaskCommand();

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Parses arguments according to the tokens defined in  AddTaskCommand
     */
    private void executeOnEveryMatcherToken(Matcher matcher, BiConsumer<String, String> consumer) {
        while (matcher.find()) {
            for (String token : PARSED_TIME_ARGS) {
                String matchedGroup = matcher.group(0).toLowerCase();
                if (matchedGroup.startsWith(token)) {
                    consumer.accept(matchedGroup, token);
                }
            }
        }
    }

    /**
     * Parses args for quotation marks , signifying header of task
     *
     * @return returns parsed string within quotation tokens
     */
    private Optional<String> checkForQuotation(String args) {
        Matcher matcher = QUOTATION_REGEX.matcher(args.trim());
        if (!matcher.find()) {
            return Optional.empty();
        }
        return Optional.of(matcher.group(0));

    }

    @Override
    public String getCommandWord() {
        return AddTaskCommand.COMMAND_WORD;
    }
}
```
###### /java/seedu/address/logic/parser/task/DeleteTaskCommandParser.java
``` java
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
        if (args.length() == 2) {
            try {
                Index index = ParserUtil.parseIndex(args);
                return new DeleteTaskCommand(index);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE));
            }
        } else if (args.contains("~")) {
            String[] indices = args.trim().split("~");
            List<Index> indexes = new ArrayList<>();
            for (int i = Integer.parseInt(indices[0]); i <= Integer.parseInt(indices[1]); i++) {
                try {
                    indexes.add(ParserUtil.parseIndex(Integer.toString(i)));
                } catch (IllegalValueException ive) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE));
                }
            }
            return new DeleteTaskCommand(indexes);
        } else {
            String[] tokens = args.trim().split(" ");
            List<Index> indexes = new ArrayList<>();

            for (String token : tokens) {
                try {
                    indexes.add(ParserUtil.parseIndex(token));
                } catch (IllegalValueException ive) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE));
                }
            }
            return new DeleteTaskCommand(indexes);
        }
    }

    @Override
    public String getCommandWord() {
        return DeleteTaskCommand.COMMAND_WORD;
    }
}
```
###### /java/seedu/address/logic/parser/task/FindTaskCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindTaskCommand object
 */
public class FindTaskCommandParser implements Parser<FindTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindTaskCommand
     * and returns an FindTaskCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTaskCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTaskCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindTaskCommand(new TaskHasKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

    @Override
    public String getCommandWord() {
        return FindTaskCommand.COMMAND_WORD;
    }
}

```
###### /java/seedu/address/logic/parser/task/MarkTaskCommandParser.java
``` java
/**
 * Parses input arguments and creates a new MarkTaskCommand object
 */
public class MarkTaskCommandParser implements Parser<MarkTaskCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MarkTaskCommand
     * and returns an MarkTaskCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkTaskCommand parse(String args) throws ParseException {
        if (args.length() == 2) {
            try {
                Index index = ParserUtil.parseIndex(args);
                return new MarkTaskCommand(index);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkTaskCommand.MESSAGE_USAGE));
            }
        } else if (args.contains("~")) {
            String[] indices = args.trim().split("~");
            List<Index> indexes = new ArrayList<>();
            for (int i = Integer.parseInt(indices[0]); i <= Integer.parseInt(indices[1]); i++) {
                try {
                    indexes.add(ParserUtil.parseIndex(Integer.toString(i)));
                } catch (IllegalValueException ive) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkTaskCommand.MESSAGE_USAGE));
                }
            }
            return new MarkTaskCommand(indexes);
        } else {
            String[] tokens = args.trim().split(" ");
            List<Index> indexes = new ArrayList<>();

            for (String token : tokens) {
                try {
                    indexes.add(ParserUtil.parseIndex(token));
                } catch (IllegalValueException ive) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkTaskCommand.MESSAGE_USAGE));
                }
            }
            return new MarkTaskCommand(indexes);
        }
    }

    @Override
    public String getCommandWord() {
        return MarkTaskCommand.COMMAND_WORD;
    }
}
```
###### /java/seedu/address/logic/parser/task/RenameTaskCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RenameTaskCommand object
 */
public class RenameTaskCommandParser implements Parser<RenameTaskCommand> {

    private static final Pattern RENAME_ARGS_FORMAT = Pattern.compile("(?<targetIndex>\\d+)\\s+(?<name>.+)");

    /**
     * Parses the given {@code String} of arguments in the context of the RenameTaskCommand
     * and returns an RenameTaskCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RenameTaskCommand parse(String args) throws ParseException {
        final Matcher matcher = RENAME_ARGS_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameTaskCommand.MESSAGE_USAGE));
        }

        final String inputName = matcher.group("name").trim();
        final String inputIndex = matcher.group("targetIndex");

        Index index;
        try {
            index = ParserUtil.parseIndex(inputIndex);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameTaskCommand.MESSAGE_USAGE));
        }

        try {
            return new RenameTaskCommand(index, inputName);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    @Override
    public String getCommandWord() {
        return RenameTaskCommand.COMMAND_WORD;
    }
}
```
###### /java/seedu/address/logic/parser/task/RescheduleTaskCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RescheduleTaskCommand object
 */
public class RescheduleTaskCommandParser implements Parser<RescheduleTaskCommand> {

    private static final Pattern ADD_SCHEDULE_ARGS_REGEX = Pattern.compile("(?:.+?(?=(?:(?:(?i)by|from|to)\\s|$)))+?");

    private static final String ARGS_FROM = "from";
    private static final String ARGS_BY = "by";
    private static final String ARGS_TO = "to";

    private static final String[] PARSED_TIME_ARGS = new String[]{ARGS_FROM, ARGS_TO, ARGS_BY};

    /**
     * Parses the given {@code String} of arguments in the context of the RescheduleTaskCommand
     * and returns an RescheduleTaskCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RescheduleTaskCommand parse(String args) throws ParseException {

        HashMap<String, Optional<LocalDateTime>> dateTimeMap = new HashMap<>();

        Matcher matcher = ADD_SCHEDULE_ARGS_REGEX.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RescheduleTaskCommand.MESSAGE_USAGE));
        }

        matcher.reset();
        matcher.find();
        Index index;
        try {
            index = ParserUtil.parseIndex(matcher.group(0));
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RescheduleTaskCommand.MESSAGE_USAGE));
        }
        try {
            BiConsumer<String, String> consumer = (matchedGroup, token) -> {
                String time = matchedGroup.substring(token.length(), matchedGroup.length());
                if (DateTimeParserUtil.containsTime(time)) {
                    dateTimeMap.put(token, DateTimeParserUtil.nattyParseDateTime(time));
                }
            };

            executeOnEveryMatcherToken(matcher, consumer);

            boolean hasDeadlineKeyword = dateTimeMap.containsKey(ARGS_BY);
            boolean hasStartTimeKeyword = dateTimeMap.containsKey(ARGS_FROM);
            boolean hasEndTimeKeyword = dateTimeMap.containsKey(ARGS_TO);

            if (hasDeadlineKeyword && !hasStartTimeKeyword && !hasEndTimeKeyword) {
                return new RescheduleTaskCommand(index, Optional.empty(), dateTimeMap.get(ARGS_BY));
            }

            if (!hasDeadlineKeyword && hasStartTimeKeyword && hasEndTimeKeyword) {
                return new RescheduleTaskCommand(index, dateTimeMap.get(ARGS_FROM), dateTimeMap.get(ARGS_TO));
            }

            if (!hasDeadlineKeyword && !hasStartTimeKeyword && !hasEndTimeKeyword) {
                return new RescheduleTaskCommand(index, Optional.empty(), Optional.empty());
            }

            return new RescheduleTaskCommand();
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Parses arguments according to the tokens defined in  RescheduleTaskCommand
     */
    private void executeOnEveryMatcherToken(Matcher matcher, BiConsumer<String, String> consumer) {
        while (matcher.find()) {
            for (String token : PARSED_TIME_ARGS) {
                String matchedGroup = matcher.group(0).toLowerCase();
                if (matchedGroup.startsWith(token)) {
                    consumer.accept(matchedGroup, token);
                }
            }
        }
    }

    @Override
    public String getCommandWord() {
        return RescheduleTaskCommand.COMMAND_WORD;
    }
}
```
###### /java/seedu/address/logic/parser/task/UnmarkTaskCommandParser.java
``` java
/**
 * Parses input arguments and creates a new UnmarkTaskCommand object
 */
public class UnmarkTaskCommandParser implements Parser<UnmarkTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkTaskCommand
     * and returns an MarkTaskCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnmarkTaskCommand parse(String args) throws ParseException {
        if (args.length() == 2) {
            try {
                Index index = ParserUtil.parseIndex(args);
                return new UnmarkTaskCommand(index);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkTaskCommand.MESSAGE_USAGE));
            }
        } else if (args.contains("~")) {
            String[] indices = args.trim().split("~");
            List<Index> indexes = new ArrayList<>();
            for (int i = Integer.parseInt(indices[0]); i <= Integer.parseInt(indices[1]); i++) {
                try {
                    indexes.add(ParserUtil.parseIndex(Integer.toString(i)));
                } catch (IllegalValueException ive) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkTaskCommand.MESSAGE_USAGE));
                }
            }
            return new UnmarkTaskCommand(indexes);
        } else {
            String[] tokens = args.trim().split(" ");
            List<Index> indexes = new ArrayList<>();

            for (String token : tokens) {
                try {
                    indexes.add(ParserUtil.parseIndex(token));
                } catch (IllegalValueException ive) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkTaskCommand.MESSAGE_USAGE));
                }
            }
            return new UnmarkTaskCommand(indexes);
        }
    }

    @Override
    public String getCommandWord() {
        return UnmarkTaskCommand.COMMAND_WORD;
    }
}
```
###### /java/seedu/address/model/AddressBook.java
``` java
    // ================ Alias-level operations ==============================
    
    /**
     * Adds an alias token
     *
     * @throws DuplicateTokenKeywordException if another token with same keyword exists.
     */
    public void addAliasToken(ReadOnlyAliasToken toAdd) throws DuplicateTokenKeywordException {
        aliasTokens.add(toAdd);
    }

    /**
     * Removes an alias token
     *
     * @throws TokenKeywordNotFoundException if no such tokens exists.
     */
    public boolean removeAliasToken(ReadOnlyAliasToken toRemove) throws TokenKeywordNotFoundException {
        if (aliasTokens.remove(toRemove)) {
            return true;
        } else {
            throw new TokenKeywordNotFoundException();
        }
    }

    public int getAliasTokenCount() {
        return aliasTokens.size();
    }

    // ================ Task-level operations ==============================

    /**
     * Adds a task
     *
     * @throws DuplicateTaskException if an equivalent task already exists
     */
    public void addTask(ReadOnlyTask toAdd) throws DuplicateTaskException {
        tasks.add(toAdd);
    }

    /**
     * Removes a task
     *
     * @throws TaskNotFoundException if no such task exists
     */
    public boolean removeTask(ReadOnlyTask toRemove) throws TaskNotFoundException {
        if (tasks.remove(toRemove)) {
            return true;
        } else {
            throw new TaskNotFoundException();
        }
    }

    /**
     * Replaces the given task {@code target} in the list with {@code updatedTask}..
     *
     * @throws DuplicateTaskException if updating the task's details causes the person to be equivalent to
     *                                another existing task in the list.
     * @throws TaskNotFoundException  if {@code target} could not be found in the list.
     */
    public void updateTask(ReadOnlyTask target, ReadOnlyTask updatedTask)
            throws TaskNotFoundException, DuplicateTaskException {
        requireNonNull(updatedTask);
        tasks.setTask(target, updatedTask);
    }

    /**
     * Marks a task as complete
     *
     * @throws TaskNotFoundException  if no such task exists
     * @throws DuplicateTaskException if equivalent task is already marked
     */
    public void markTask(ReadOnlyTask toMark)
            throws TaskNotFoundException, DuplicateTaskException {
        tasks.setCompletion(toMark);
    }

    /**
     * Marks a task as incomplete
     *
     * @throws TaskNotFoundException  if no such task exists
     * @throws DuplicateTaskException if equivalent task is already unmarked
     */
    public void unmarkTask(ReadOnlyTask toUnmark)
            throws TaskNotFoundException, DuplicateTaskException {
        tasks.setIncompletion(toUnmark);
    }
```
###### /java/seedu/address/model/alias/AliasToken.java
``` java
/**
 * Represents all aliases used in address book. Each alias is a token that contains a keyword and representation.
 * Instances of AliasToken are immutable.
 * Guarantees : details are present and not null
 */
public class AliasToken implements ReadOnlyAliasToken {

    private ObjectProperty<Keyword> keyword;
    private ObjectProperty<Representation> representation;

    /**
     * Every field must be present and not null.
     */
    public AliasToken(Keyword keyword, Representation representation) {
        requireAllNonNull(keyword, representation);

        this.keyword = new SimpleObjectProperty<>(keyword);
        this.representation = new SimpleObjectProperty<>(representation);
    }

    public AliasToken(ReadOnlyAliasToken source) {
        this(source.getKeyword(), source.getRepresentation());
    }

    public void setKeyword(Keyword keyword) {
        this.keyword.set(requireNonNull(keyword));
    }

    public void setRepresentation(Representation representation) {
        this.representation.set(requireNonNull(representation));
    }

    @Override
    public ObjectProperty<Keyword> keywordProperty() {
        return keyword;
    }

    public Keyword getKeyword() {
        return keyword.get();
    }

    @Override
    public ObjectProperty<Representation> representationProperty() {
        return representation;
    }

    public Representation getRepresentation() {
        return representation.get();
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyAliasToken // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyAliasToken) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(keyword, representation);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
```
###### /java/seedu/address/model/alias/exceptions/DuplicateTokenKeywordException.java
``` java
/**
 * Signals that the operation will result in duplicate AliasToken objects.
 */
public class DuplicateTokenKeywordException extends DuplicateDataException {
    public DuplicateTokenKeywordException() {
        super("Operation would result in duplicate tokens with same keyword defined");
    }
}
```
###### /java/seedu/address/model/alias/exceptions/TokenKeywordNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified AliasToken.
 */
public class TokenKeywordNotFoundException extends Exception {
}
```
###### /java/seedu/address/model/alias/Keyword.java
``` java
/**
 * Represents the alias keyword in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidKeyword(String)}
 */
public class Keyword {
    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Alias keywords should only contain one word with at least 2 letters and it should not be blank";

    public static final String KEYWORD_INVALIDATION_REGEX = ".*\\s+.*";

    public final String keyword;

    /**
     * Validates given keyword.
     *
     * @throws IllegalValueException if given keyword string is invalid.
     */
    public Keyword(String keyword) throws IllegalValueException {
        requireNonNull(keyword);
        if (!isValidKeyword(keyword)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.keyword = keyword.toLowerCase();
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidKeyword(String test) {
        String trimmedKeyword = test.trim();
        return ((!test.matches(KEYWORD_INVALIDATION_REGEX)) && (trimmedKeyword.length() > 1));
    }

    @Override
    public String toString() {
        return keyword;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Keyword // instanceof handles nulls
                && this.keyword.equals(((Keyword) other).keyword)); // state check
    }

    @Override
    public int hashCode() {
        return keyword.hashCode();
    }
}
```
###### /java/seedu/address/model/alias/ReadOnlyAliasToken.java
``` java
/**
 * A read-only immutable interface for a AliasToken in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyAliasToken {
    ObjectProperty<Keyword> keywordProperty();

    Keyword getKeyword();

    ObjectProperty<Representation> representationProperty();

    Representation getRepresentation();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyAliasToken other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid  below
                && other.getKeyword().equals(this.getKeyword()) // state checks here onwards
                && other.getRepresentation().equals(this.getRepresentation()));
    }

    /**
     * Formats the AliasToken as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" keyword: ")
                .append(getKeyword())
                .append(" representation: ")
                .append(getRepresentation());
        return builder.toString();
    }
}
```
###### /java/seedu/address/model/alias/Representation.java
``` java
/**
 * For the alias representation in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRepresentation(String)}
 */
public class Representation {
    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Alias representations should not be empty";

    public final String representation;

    /**
     * Validates given representation.
     *
     * @throws IllegalValueException if given representation string is invalid.
     */
    public Representation(String representation) throws IllegalValueException {
        requireNonNull(representation);
        if (!isValidRepresentation(representation)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.representation = representation;
    }

    /**
     * Returns true if a given string is a valid alias representation.
     */
    public static boolean isValidRepresentation(String test) {
        return !test.isEmpty();
    }

    @Override
    public String toString() {
        return representation;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Representation // instanceof handles nulls
                && this.representation.equals(((Representation) other).representation)); // state check
    }

    @Override
    public int hashCode() {
        return representation.hashCode();
    }
}

```
###### /java/seedu/address/model/alias/UniqueAliasTokenList.java
``` java
/**
 * A list of alias that enforces no nulls and uniqueness between its elements.
 * <p>
 * Supports minimal set of list operation.
 *
 * @see AliasToken#equals(Object)
 */
public class UniqueAliasTokenList implements Iterable<AliasToken> {
    private final ObservableList<AliasToken> internalList = FXCollections.observableArrayList();
    private final ObservableList<ReadOnlyAliasToken> mappedList = EasyBind.map(internalList, (aliasToken)
        -> aliasToken);

    /**
     * Constructs empty AliasTokenList.
     */
    public UniqueAliasTokenList() {
    }

    /**
     * Creates a UniqueAliasTokenList using given AliasTokens.
     * Enforces no nulls
     */
    public UniqueAliasTokenList(Set<AliasToken> aliasTokens) {
        requireAllNonNull(aliasTokens);
        internalList.addAll(aliasTokens);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent aliasToken as the given argument.
     */
    public boolean contains(ReadOnlyAliasToken toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Returns true if a symbol with the specified tokenKeyword exists in the list
     *
     * @param tokenKeyword the tokenKeyword to check for
     * @return true if exists, false otherwise
     */
    public boolean contains(Keyword tokenKeyword) {
        for (AliasToken token : internalList) {
            if (token.getKeyword().equals(tokenKeyword)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Adds a AliasToken to the list.
     * An AliasToken is duplicate if another AliasTokens contains the same keyword.
     *
     * @throws DuplicateTokenKeywordException if the AliasToken to add is a duplicate.
     */
    public void add(ReadOnlyAliasToken toAdd) throws DuplicateTokenKeywordException {
        requireNonNull(toAdd);
        if (internalList.contains(toAdd.getKeyword())) {
            throw new DuplicateTokenKeywordException();
        }
        internalList.add(new AliasToken(toAdd));
    }

    /**
     * Removes the equivalent AliasToken from the list.
     *
     * @throws TokenKeywordNotFoundException if no such AliasToken could be found in the list.
     */
    public boolean remove(ReadOnlyAliasToken toRemove) throws TokenKeywordNotFoundException {
        requireNonNull(toRemove);
        if (!internalList.contains(toRemove)) {
            throw new TokenKeywordNotFoundException();
        }
        final boolean aliasFoundAndDeleted = internalList.remove(toRemove);
        return aliasFoundAndDeleted;
    }

    /**
     * Replaces the AliasToken {@code target} in the list with {@code newToken}.
     * newToken must have same keyword to target.
     *
     * @throws TokenKeywordNotFoundException if {@code target} could not be found in the list.
     */

    public void setAliasToken(ReadOnlyAliasToken target, ReadOnlyAliasToken newToken)
            throws DuplicateTokenKeywordException, TokenKeywordNotFoundException {
        requireAllNonNull(target, newToken);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TokenKeywordNotFoundException();
        }

        assert target.getKeyword().equals(newToken.getKeyword());
        internalList.set(index, new AliasToken(newToken));
    }

    /**
     * Clears this list and copies all elements from the replacement list to this.
     *
     * @param replacement the other list
     */
    public void setAliasTokens(UniqueAliasTokenList replacement) {
        requireAllNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    public void setAliasTokens(List<? extends ReadOnlyAliasToken> aliasTokens) throws DuplicateTokenKeywordException {
        final UniqueAliasTokenList replacement = new UniqueAliasTokenList();
        for (final ReadOnlyAliasToken aliasToken : aliasTokens) {
            replacement.add(new AliasToken(aliasToken));
        }
        setAliasTokens(replacement);
    }

    public int size() {
        return internalList.size();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyAliasToken> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    @Override
    public Iterator<AliasToken> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueAliasTokenList // instanceof handles nulls
                && this.internalList.equals(((UniqueAliasTokenList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
```
###### /java/seedu/address/model/Model.java
``` java
    // ================ Related to AliasTokens ==============================

    /**
     * Adds the given AliasToken
     */
    void addAliasToken(ReadOnlyAliasToken target) throws DuplicateTokenKeywordException;

    /**
     * Removes the given AliasToken.
     */
    void deleteAliasToken(ReadOnlyAliasToken target) throws TokenKeywordNotFoundException;

    /**
     * Returns the number of Aliases
     */
    int getAliasTokenCount();

    /**
     * Returns an unmodifiable view of the filtered AliasToken list
     */
    ObservableList<ReadOnlyAliasToken> getFilteredAliasTokenList();

    // ================ Related to Tasks ==============================

    /**
     * Deletes the given task
     */
    void deleteTask(ReadOnlyTask target) throws TaskNotFoundException;

    /**
     * Adds the given task
     */
    void addTask(ReadOnlyTask target) throws DuplicateTaskException;

    /**
     * Updates the given task
     */
    void updateTask(ReadOnlyTask target, ReadOnlyTask updatedTask)
            throws TaskNotFoundException, DuplicateTaskException;

    /**
     * Marks the given tasks as completed
     */
    void markTasks(List<ReadOnlyTask> targets)
            throws TaskNotFoundException, DuplicateTaskException;

    /**
     * Unmarks the given tasks as completed
     */
    void unmarkTasks(List<ReadOnlyTask> targets)
            throws TaskNotFoundException, DuplicateTaskException;

    /**
     * Returns an unmodifiable view of the filtered Task list
     */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<ReadOnlyTask> predicate);
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public synchronized void addAliasToken(ReadOnlyAliasToken target) throws DuplicateTokenKeywordException {
        addressBook.addAliasToken(target);
        indicateAddressBookChanged();
        indicateAliasTokenAdded(target);
    }

    @Override
    public synchronized void deleteAliasToken(ReadOnlyAliasToken target) throws TokenKeywordNotFoundException {
        addressBook.removeAliasToken(target);
        indicateAddressBookChanged();
        indicateAliasTokenRemoved(target);
    }

    @Override
    public int getAliasTokenCount() {
        return addressBook.getAliasTokenCount();
    }

    // ================ Filtered-alias list accessors ==============================

    @Override
    public ObservableList<ReadOnlyAliasToken> getFilteredAliasTokenList() {
        return FXCollections.unmodifiableObservableList(filteredAliases);
    }

    // ================ Filtered-persons list accessors ==============================

    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    // ================ Task-Related methods ==============================
    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        addressBook.removeTask(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(ReadOnlyTask target) throws DuplicateTaskException {
        addressBook.addTask(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void updateTask(ReadOnlyTask target, ReadOnlyTask updatedTask)
            throws TaskNotFoundException, DuplicateTaskException {
        requireAllNonNull(target, updatedTask);
        addressBook.updateTask(target, updatedTask);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void markTasks(List<ReadOnlyTask> targets)
            throws TaskNotFoundException, DuplicateTaskException {
        for (ReadOnlyTask target : targets) {
            addressBook.markTask(target);
        }
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void unmarkTasks(List<ReadOnlyTask> targets)
            throws TaskNotFoundException, DuplicateTaskException {
        for (ReadOnlyTask target : targets) {
            addressBook.unmarkTask(target);
        }
        indicateAddressBookChanged();
    }

    // ================ Filtered-task list accessors ==============================

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

    // ================ Utility methods ==============================

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
                && filteredAliases.equals(other.filteredAliases)
                && filteredTasks.equals(other.filteredTasks);
    }

}
```
###### /java/seedu/address/model/person/Remark.java
``` java
/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class Remark {

    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "Person remarks can take any values, can even be blank";

    public final String value;

    public Remark(String remark) {
        requireNonNull(remark);
        this.value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && this.value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### /java/seedu/address/model/task/exceptions/DuplicateTaskException.java
``` java
/**
 * Signals that the operation will result in duplicate Tasks objects.
 */
public class DuplicateTaskException extends DuplicateDataException {
    public DuplicateTaskException() {
        super("Operation would result in duplicate tasks");
    }
}
```
###### /java/seedu/address/model/task/exceptions/TaskNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified task.
 */
public class TaskNotFoundException extends Exception {
}
```
###### /java/seedu/address/model/task/Header.java
``` java
/**
 * Represents a Task's heading/title in addressbook
 * Guarantees: immutable; is valid as declared in {@link #isValidHeader(String)}
 */
public class Header {

    public static final String MESSAGE_HEADER_CONSTRAINTS =
            "Task headers should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String HEADER_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String header;

    /**
     * Validates given header
     *
     * @throws IllegalValueException if given header is invalid
     */
    public Header(String header) throws IllegalValueException {
        requireNonNull(header);
        String trimmedHeader = header.trim();
        if (!isValidHeader(trimmedHeader)) {
            throw new IllegalValueException(MESSAGE_HEADER_CONSTRAINTS);
        }
        this.header = trimmedHeader;
    }

    /**
     * Returns true if given string is a valid task header
     */
    public static boolean isValidHeader(String test) {
        return test.matches(HEADER_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return header;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Header // instanceof handles nulls
                && this.header.equals(((Header) other).header)); // state check
    }

    @Override
    public int hashCode() {
        return header.hashCode();
    }
}
```
###### /java/seedu/address/model/task/ReadOnlyTask.java
``` java
/**
 * A read-only immutable interface for a Task in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    ObjectProperty<Header> headerProperty();

    Header getHeader();

    boolean isCompleted();

    boolean isUpcoming();

    boolean isOverdue();

    boolean isEvent();

    boolean hasDeadline();

    boolean hasTime();

    Optional<LocalDateTime> getStartDateTime();

    Optional<LocalDateTime> getEndDateTime();

    LocalDateTime getLastUpdatedTime();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getHeader().equals(this.getHeader()) // state checks here onwards
                && (other.isCompleted() == this.isCompleted())
                && other.getStartDateTime().equals(this.getStartDateTime())
                && other.getEndDateTime().equals(this.getEndDateTime()));
    }

    /**
     * Formats the task as text, showing task header only.
     */
    default String getAsText() {
        return String.valueOf(getHeader()) + "\n";
    }

    /**
     * Formats the task as text, showing all task details
     */
    default String getDetailedText() {
        String completionState = (isCompleted()) ? "Completed" : "Incomplete";
        String startTime = (getStartDateTime().isPresent()) ? getStartDateTime().get().toString()
                : "None";
        String endTime = (getEndDateTime().isPresent()) ? getEndDateTime().get().toString()
                : "None";
        String lastUpdatedTime = getLastUpdatedTime().toString();

        final StringBuilder builder = new StringBuilder();
        builder.append("Task: ")
                .append(getHeader())
                .append(" Completion State: ")
                .append(completionState)
                .append(" Start Time: ")
                .append(startTime)
                .append(" End Time: ")
                .append(endTime)
                .append(" Last Updated Time: ")
                .append(lastUpdatedTime);
        return builder.toString();
    }
}
```
###### /java/seedu/address/model/task/Task.java
``` java
/**
 * Represents a Task in the addressbook
 */
public class Task implements ReadOnlyTask, Comparable<Task> {

    private static final int UPCOMING_DAYS_THRESHOLD = 7;

    private ObjectProperty<Header> header;
    private boolean isCompleted;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private LocalDateTime lastUpdatedTime;

    // ================ Constructors ==============================

    /**
     * Constructor for a task without date/time
     */
    public Task(Header header) {
        requireNonNull(header);
        this.header = new SimpleObjectProperty<>(header);
        this.isCompleted = false;
        this.startDateTime = null;
        this.endDateTime = null;
        setLastUpdatedTimeToCurrent();
    }

    /**
     * Constructor for  a task with only a deadline
     */
    public Task(Header header, Optional<LocalDateTime> deadline) {
        requireNonNull(header);
        this.header = new SimpleObjectProperty<>(header);
        this.isCompleted = false;
        this.startDateTime = null;
        this.endDateTime = deadline.orElse(null);
        this.setLastUpdatedTimeToCurrent();
    }

    /**
     * Constructor for a task with a start time and end time
     */
    public Task(Header header, Optional<LocalDateTime> startDateTime,
                Optional<LocalDateTime> endDateTime) {
        requireNonNull(header);
        this.header = new SimpleObjectProperty<>(header);
        this.isCompleted = false;
        this.startDateTime = startDateTime.orElse(null);
        this.endDateTime = endDateTime.orElse(null);
        this.setLastUpdatedTimeToCurrent();
    }

    /**
     * Constructor for a ReadOnlyTask copy
     */
    public Task(ReadOnlyTask source) {
        this(source.getHeader(), source.getStartDateTime(), source.getEndDateTime());
        if (source.isCompleted()) {
            this.setComplete();
        }
        this.setLastUpdatedTime(source.getLastUpdatedTime());
    }

    // ================ Getter methods ==============================

    @Override
    public ObjectProperty<Header> headerProperty() {
        return header;
    }

    @Override
    public Header getHeader() {
        return header.get();
    }

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Returns true if a task is not completed and has a start to end time which is not current but within
     * the threshold amount of days
     */
    @Override
    public boolean isUpcoming() {
        if (isCompleted()) {
            return false;
        }

        if (!hasTime()) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime thresholdTime = currentTime.plusDays(UPCOMING_DAYS_THRESHOLD);
        boolean isBeforeUpcomingDaysThreshold = getTaskTime().isBefore(thresholdTime);
        boolean isAfterCurrentTime = getTaskTime().isAfter(currentTime);

        return isBeforeUpcomingDaysThreshold && isAfterCurrentTime;

    }

    /**
     * Returns true if task is not complete and has a start to end time before current time
     */
    @Override
    public boolean isOverdue() {
        if (isCompleted()) {
            return false;
        }

        if (!hasTime()) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        boolean isBeforeCurrentTime = getTaskTime().isBefore(currentTime);

        return isBeforeCurrentTime;
    }

    /**
     * Returns true if a task has a start time or end time
     */
    @Override
    public boolean hasTime() {
        return getStartDateTime().isPresent() || getEndDateTime().isPresent();
    }

    /**
     * Returns true if the task has both start and end time
     */
    @Override
    public boolean isEvent() {
        return getStartDateTime().isPresent() && getEndDateTime().isPresent();
    }

    @Override
    public boolean hasDeadline() {
        return !getStartDateTime().isPresent() && getEndDateTime().isPresent();
    }

    @Override
    public Optional<LocalDateTime> getStartDateTime() {
        return Optional.ofNullable(startDateTime);
    }

    @Override
    public Optional<LocalDateTime> getEndDateTime() {
        return Optional.ofNullable(endDateTime);
    }

    /**
     * Returns the time when the task its most recent update (any change)
     */
    @Override
    public LocalDateTime getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    /**
     * Returns the start time if present, else returns end time
     */
    private LocalDateTime getTaskTime() {
        assert hasTime();
        return getStartDateTime().orElse(getEndDateTime().get());
    }

    // ================ Setter methods ==============================

    public void setHeader(Header header) {
        this.header.set(requireNonNull(header));
        setLastUpdatedTimeToCurrent();
    }

    public void setComplete() {
        this.isCompleted = true;
        setLastUpdatedTimeToCurrent();
    }

    public void setIncomplete() {
        this.isCompleted = false;
        setLastUpdatedTimeToCurrent();
    }

    public void setStartDateTime(Optional<LocalDateTime> startDateTime) {
        this.startDateTime = startDateTime.orElse(null);
        setLastUpdatedTimeToCurrent();
    }

    public void setEndDateTime(Optional<LocalDateTime> endDateTime) {
        this.endDateTime = endDateTime.orElse(null);
        setLastUpdatedTimeToCurrent();
    }

    public void setLastUpdatedTime(LocalDateTime updatedTime) {
        this.lastUpdatedTime = updatedTime;
    }

    public void setLastUpdatedTimeToCurrent() {
        this.lastUpdatedTime = LocalDateTime.now().withNano(0);
    }

    // ================ Utility methods ==============================

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    /**
     * Compares if this task is the same as task @code{other}
     * The task is different if
     * 1. Both tasks have different completion status
     * 2. Both tasks have different start and end dates
     * 3. Both tasks have different updated times
     * 4. One tasks has a different name than the other
     */
    @Override
    public int compareTo(Task other) {
        int comparedCompletionStatus = compareCompletionStatus(other);
        if (comparedCompletionStatus != 0) {
            return comparedCompletionStatus;
        }

        int comparedTaskTime = compareTaskTime(other);
        if (!isCompleted() && comparedTaskTime != 0) {
            return comparedTaskTime;
        }

        int comparedLastUpdatedTime = compareLastUpdatedTime(other);
        if (comparedLastUpdatedTime != 0) {
            return comparedLastUpdatedTime;
        }

        return compareHeader(other);
    }

    public int compareCompletionStatus(Task other) {
        return Boolean.compare(this.isCompleted(), other.isCompleted());
    }

    /**
     * Compares the start time of this task and task @code{other}
     * Both tasks are considered the same if both have no times or the start time is same
     */
    public int compareTaskTime(Task other) {
        if (this.hasTime() && other.hasTime()) {
            return this.getTaskTime().compareTo(other.getTaskTime());
        } else if (this.hasTime()) {
            return -1;
        } else if (other.hasTime()) {
            return 1;
        } else {
            return 0;
        }
    }

    public int compareLastUpdatedTime(Task other) {
        return other.getLastUpdatedTime().compareTo(this.getLastUpdatedTime());
    }

    public int compareHeader(Task other) {
        return this.getHeader().toString().compareTo(other.getHeader().toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, isCompleted, startDateTime, endDateTime);
    }

    public int syncCode() {
        return hashCode();
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
```
###### /java/seedu/address/model/task/TaskHasKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyTask}'s details matches any of the keywords given.
 */
public class TaskHasKeywordsPredicate implements Predicate<ReadOnlyTask> {

    private final List<String> keywords;

    public TaskHasKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyTask task) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getHeader().header, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskHasKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TaskHasKeywordsPredicate) other).keywords)); // state check
    }

}

```
###### /java/seedu/address/model/task/UniqueTaskList.java
``` java
/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(java.util.Collection)
 */
public class UniqueTaskList implements Iterable<Task> {

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();
    private final ObservableList<ReadOnlyTask> mappedList = EasyBind.map(internalList, (task)
        -> task);

    /**
     * Constructs empty TaskList
     */
    public UniqueTaskList() {
    }

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

    /**
     * Replaces the task {@code target} in the list with {@code updatedTask}.
     *
     * @throws DuplicateTaskException if the replacement is equivalent to another existing task in the list.
     * @throws TaskNotFoundException  if {@code target} could not be found in the list.
     */
    public void setTask(ReadOnlyTask target, ReadOnlyTask updatedTask)
            throws TaskNotFoundException, DuplicateTaskException {
        requireAllNonNull(target, updatedTask);

        int index = internalList.indexOf(target);

        if (index == -1) {
            throw new TaskNotFoundException();
        }

        if (contains(updatedTask)) {
            throw new DuplicateTaskException();
        }

        internalList.set(index, new Task(updatedTask));
    }

    /**
     * Sets the given task @code{toSetComplete} as complete
     */
    public void setCompletion(ReadOnlyTask toSetComplete)
            throws TaskNotFoundException, DuplicateTaskException {
        requireNonNull(toSetComplete);

        Task completedTask = new Task(toSetComplete);
        completedTask.setComplete();
        setTask(toSetComplete, completedTask);
    }

    /**
     * Sets the given task @code{toSetIncomplete} as incomplete
     */
    public void setIncompletion(ReadOnlyTask toSetIncomplete)
            throws TaskNotFoundException, DuplicateTaskException {
        requireNonNull(toSetIncomplete);

        Task incompleteTask = new Task(toSetIncomplete);
        incompleteTask.setIncomplete();
        setTask(toSetIncomplete, incompleteTask);
    }

    public void setTasks(UniqueTaskList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setTasks(List<? extends ReadOnlyTask> tasks) throws DuplicateTaskException {
        final UniqueTaskList replacement = new UniqueTaskList();
        for (final ReadOnlyTask task : tasks) {
            replacement.add(new Task(task));
        }
        setTasks(replacement);
    }

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
###### /java/seedu/address/storage/XmlAdaptedAliasToken.java
``` java
/**
 * JAXB-friendly version of the AliasToken.
 */
public class XmlAdaptedAliasToken {

    @XmlElement(required = true)
    private String keyword;
    @XmlElement(required = true)
    private String representation;

    public XmlAdaptedAliasToken() {
    }

    /**
     * Converts a given AliasToken into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedAliasToken
     */
    public XmlAdaptedAliasToken(ReadOnlyAliasToken source) {
        keyword = source.getKeyword().keyword;
        representation = source.getRepresentation().representation;
    }

    /**
     * Converts this jaxb-friendly adapted AliasToken object into the model's AliasToken object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted AliasToken
     */

    public AliasToken toModelType() throws IllegalValueException {
        final Keyword keyword = new Keyword(this.keyword);
        final Representation representation = new Representation(this.representation);
        return new AliasToken(keyword, representation);
    }


}
```
###### /java/seedu/address/storage/XmlAdaptedTask.java
``` java
/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @XmlElement(required = true)
    private String header;
    @XmlElement(required = true)
    private String isCompleted;
    @XmlElement(required = true)
    private String lastUpdatedTime;
    @XmlElement(required = false)
    private String startDateTime;
    @XmlElement(required = false)
    private String endDateTime;

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask() {
    }


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        header = source.getHeader().header;
        isCompleted = Boolean.toString(source.isCompleted());
        lastUpdatedTime = source.getLastUpdatedTime().format(formatter);

        if (source.getStartDateTime().isPresent()) {
            startDateTime = source.getStartDateTime().get().format(formatter);
        }

        if (source.getEndDateTime().isPresent()) {
            endDateTime = source.getEndDateTime().get().format(formatter);
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final Header header = new Header(this.header);
        final boolean setAsCompleted = Boolean.valueOf(isCompleted);

        Task newTask = new Task(header);

        if (lastUpdatedTime != null) {
            newTask.setLastUpdatedTime(LocalDateTime.parse(lastUpdatedTime, formatter));
        } else {
            newTask.setLastUpdatedTimeToCurrent();
        }

        if (setAsCompleted) {
            newTask.setComplete();
        }

        if (startDateTime != null) {
            newTask.setStartDateTime(Optional.ofNullable(LocalDateTime.parse(startDateTime, formatter)));
        }

        if (endDateTime != null) {
            newTask.setEndDateTime(Optional.ofNullable(LocalDateTime.parse(endDateTime, formatter)));
        }

        return newTask;
    }
}
```
###### /java/seedu/address/storage/XmlSerializableAddressBook.java
``` java
    @Override
    public ObservableList<ReadOnlyAliasToken> getAliasTokenList() {
        final ObservableList<ReadOnlyAliasToken> aliasTokens = this.aliasTokens.stream().map(a -> {
            try {
                return a.toModelType();
            } catch (IllegalValueException e) {
                logger.warning("Failed to convert XmlAdaptedAliasToken to AliasToken or "
                        + "add it to AliasTokenList: ");
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(aliasTokens);
    }

    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        final ObservableList<ReadOnlyTask> tasks = this.tasks.stream().map(t -> {
            try {
                return t.toModelType();
            } catch (IllegalValueException e) {
                logger.warning("Failed to convert XmlAdaptedTask to Task or"
                        + "add it to TaskList: ");
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(tasks);
    }
```
###### /java/seedu/address/ui/TaskCard.java
``` java
/**
 * UI component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";
    private static final String OVERDUE_PREFIX = "Overdue\n";
    private static final String COMPLETED_PREFIX = "Completed on ";
    private static final String TASK_TIME_PATTERN = "HH:mm EEE, dd MMM";
    private static final String COMPLETED_TIME_PATTERN = "EEE, dd MMM";
    private static final String START_TIME_PREFIX = "from ";
    private static final String END_TIME_PREFIX = " to ";
    private static final String DEADLINE_PREFIX = "by ";
    private static final String EMPTY_PREFIX = "";
    private static final String OVERDUE_STYLE = "-fx-background-color: rgba(244, 67, 54, 0.8)";
    private static final String UPCOMING_STYLE = "-fx-background-color: rgba(170,181,46,0.8)";
    private static final String OTHER_STYLE = "-fx-background-color: rgba(7,38,255,0.51)";
    private static final Color NAME_COLOR_DARK = Color.web("#3a3d42");
    private static final Color TIME_COLOR_DARK = Color.web("#4172c1");
    private static final Color NAME_COLOR_LIGHT = Color.web("#ffffff");
    private static final Color TIME_COLOR_LIGHT = Color.web("#fff59d");

    private final ReadOnlyTask task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label header;
    @FXML
    private Label id;
    @FXML
    private VBox taskVbox;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        initTimeStatus();
        bindListeners(task);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Task} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyTask task) {
        header.textProperty().bind(Bindings.convert(task.headerProperty()));
    }

    /**
     * Initializes and binds the values that are not implemented with ObjectProperty
     */
    @FXML
    public void initTimeStatus() {

        Label time = new Label();
        time.setId("time");

        if (task.isOverdue()) {
            cardPane.setStyle(OVERDUE_STYLE);
            header.setTextFill(NAME_COLOR_LIGHT);
            time.setTextFill(TIME_COLOR_LIGHT);
            id.setTextFill(NAME_COLOR_LIGHT);
        } else if (task.isUpcoming()) {
            cardPane.setStyle(UPCOMING_STYLE);
            header.setTextFill(NAME_COLOR_DARK);
            time.setTextFill(TIME_COLOR_DARK);
        } else {
            cardPane.setStyle(OTHER_STYLE);
            header.setTextFill(NAME_COLOR_DARK);
            time.setTextFill(TIME_COLOR_DARK);
        }

        StringBuilder timeDescription = new StringBuilder();
        timeDescription.append(formatTaskTime(task));

        if (task.isCompleted()) {
            timeDescription.append(formatUpdatedTime(task));
        }

        time.setText(timeDescription.toString());
        time.setMaxHeight(Control.USE_COMPUTED_SIZE);
        time.setWrapText(true);

        if (task.hasTime() || task.isCompleted()) {
            taskVbox.getChildren().add(time);
            taskVbox.setAlignment(Pos.CENTER_LEFT);
            time.setAlignment(Pos.CENTER_LEFT);
            time.setFont(Font.font("Segoe UI Semibold", FontPosture.ITALIC, 11));
        }

    }

    /**
     * Formats LocalTime to String based on the pattern provided
     *
     * @param dateTimePattern String format for date
     * @param prefix          Start-time , end-time , deadline
     * @return required time depending on prefix, as String
     */
    private String formatTime(String dateTimePattern, String prefix, Optional<LocalDateTime> dateTime) {

        StringBuilder sb = new StringBuilder();
        DateTimeFormatter format = DateTimeFormatter.ofPattern(dateTimePattern);
        sb.append(prefix).append(dateTime.get().format(format));

        return sb.toString();
    }

    /**
     * Extracts LocalDateTime from provided ReadOnlyTask and returns it as String
     *
     * @param task : ReadOnlyTask provided
     * @return required time as String
     */
    private String formatTaskTime(ReadOnlyTask task) {

        StringBuilder timeStringBuilder = new StringBuilder();

        if (task.isOverdue()) {
            timeStringBuilder.append(OVERDUE_PREFIX);
        }

        if (task.isEvent()) {
            String startTime = formatTime(TASK_TIME_PATTERN, START_TIME_PREFIX, task.getStartDateTime());
            String endTime = formatTime(TASK_TIME_PATTERN, END_TIME_PREFIX, task.getEndDateTime());
            timeStringBuilder.append(startTime);
            timeStringBuilder.append(endTime);
        } else if (task.hasDeadline()) {
            String deadline = formatTime(TASK_TIME_PATTERN, DEADLINE_PREFIX, task.getEndDateTime());
            timeStringBuilder.append(deadline);
        }

        return timeStringBuilder.toString();
    }

    /**
     * Updated LocalDateTime from provided ReadOnlyTask and returns it as String
     *
     * @param task : ReadOnlyTask provided
     * @return updated time as String
     */
    private String formatUpdatedTime(ReadOnlyTask task) {
        StringBuilder timeStringBuilder = new StringBuilder();
        if (task.hasTime()) {
            timeStringBuilder.append("\n");
        }
        timeStringBuilder.append(COMPLETED_PREFIX);
        timeStringBuilder.append(formatTime(COMPLETED_TIME_PATTERN, EMPTY_PREFIX,
                Optional.ofNullable(task.getLastUpdatedTime())));
        return timeStringBuilder.toString();
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
/**
 * Panel containing the list of tasks.
 */
public class TaskListPanel extends UiPart<Region> {
    private static final String FXML = "TaskListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @javafx.fxml.FXML
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

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
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
