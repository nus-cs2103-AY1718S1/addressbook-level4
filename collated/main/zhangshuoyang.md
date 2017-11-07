# zhangshuoyang
###### \java\seedu\address\logic\commands\AddTaskCommand.java
``` java
/**
 * Adds a task to the address book.
 */
public class AddTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "task";

    public static final String AUTOCOMPLETE_FORMAT = COMMAND_WORD + " "
            + PREFIX_DESCIPTION + " "
            + PREFIX_PRIORITY + " "
            + PREFIX_DUEDATE;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the address book."
            + "Parameters: "
            + PREFIX_DESCIPTION + "DESCRIPTION "
            + PREFIX_PRIORITY + "PRIORITY(0/1/2) "
            + PREFIX_DUEDATE + "DUE DATE(dd/MM/yyyy) \n"
            + "EXAMPLE: " + COMMAND_WORD + " "
            + PREFIX_DESCIPTION + "CS ASSIGNMENT "
            + PREFIX_PRIORITY + "2 "
            + PREFIX_DUEDATE + "25/10/2017";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";

    private final Task toAdd;

    /**
     * Creates an AddTaskCommand to add the specified {@code ReadOnlyTask}
     */
    public AddTaskCommand(ReadOnlyTask task) {
        toAdd = new Task(task);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addTask(toAdd);
            String str = System.getProperty("user.dir");
            System.out.println(str);
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
###### \java\seedu\address\logic\parser\AddTaskCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class AddTaskCommandParser implements  Parser<AddTaskCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the AddTaskCommand
     * and returns an AddTaskCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddTaskCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_PRIORITY, PREFIX_DUEDATE);
        if (!isFieldPresent(argumentMultimap)) {
            // if empty
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }
        try {
            Description description = ParserUtil.parseDescription(argumentMultimap.getPreamble());
            Priority priority = ParserUtil.parsePriority(argumentMultimap.getValue(PREFIX_PRIORITY)).get();
            DueDate date = ParserUtil.parseDueDate(argumentMultimap.getValue(PREFIX_DUEDATE)).get();

            ReadOnlyTask task = new Task(description, priority, date);
            return new AddTaskCommand(task);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private boolean isFieldPresent(ArgumentMultimap argumentMultimap) {
        return !argumentMultimap.getPreamble().isEmpty();
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> desciption} into an {@code Optional<Description>} if {@code description}
     * is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Description parseDescription (String description) throws IllegalValueException {
        requireNonNull(description);
        return new Description(description);
    }

    /**
     * Parses a {@code Optional<String> desciption} into an {@code Optional<Description>} if {@code description}
     * is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Description> parseDescriptionOptional (Optional<String> description)
            throws IllegalValueException {
        requireNonNull(description);
        return description.isPresent() ? Optional.of(new Description(description.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> priority} into an {@code Optional<Priority>} if {@code priority} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Priority> parsePriority (Optional<String> priority) throws IllegalValueException {
        requireNonNull(priority);
        return priority.isPresent() ? Optional.of(new Priority(priority.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> duedate} into an {@code Optional<DueDate>} if {@code duedate} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<DueDate> parseDueDate (Optional<String> duedate) throws IllegalValueException {
        requireNonNull(duedate);
        return duedate.isPresent() ? Optional.of(new DueDate(duedate.get())) : Optional.empty();
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    //// task-level operations

    /**
     * Adds a task to the address book.
     *
     * @throws DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(ReadOnlyTask task) throws DuplicateTaskException {
        Task newTask = new Task(task);
        tasks.add(newTask);
    }

    /**
     * Replaces the given task {@code target} in the list with {@code editedReadOnlyTask}.
     *
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
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Replaces the given person {@code target} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *     another existing task in the list.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     *
     * For further implementation.
     */
    void updateTask(ReadOnlyTask target, ReadOnlyTask editedTask)
        throws DuplicateTaskException, TaskNotFoundException;

    /** Returns an unmodifiable view of the filtered task list */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<ReadOnlyTask> predicate);


```
###### \java\seedu\address\model\ModelManager.java
``` java
    /** For further implementation. */
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

    /** For further implementation. */
    @Override
    public void updateTask(ReadOnlyTask target, ReadOnlyTask editedTask)
            throws DuplicateTaskException, TaskNotFoundException {
        requireAllNonNull(target, editedTask);

        addressBook.updateTask(target, editedTask);
        indicateAddressBookChanged();
    }


```
###### \java\seedu\address\model\ModelManager.java
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
###### \java\seedu\address\model\task\Date.java
``` java
/**
 * Represents the date of a certain task in the Address Book.
 */

public class Date {

    public static final String MESSAGE_DATE_FORMAT_CONSTRAINTS =
            "The date must be in the format dd/MM/yyyy";


    /**
     * Returns true if the given date is in the valid format.
     *
     * @throws java.time.format.DateTimeParseException if the date format is invalid
     */
    public static boolean isValidDate (String input) throws IllegalValueException {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate.parse(input, format);
            return true;
        } catch (DateTimeParseException exc) {
            return false;
        }
    }

    /**
     * Format the given date
     */
    public static LocalDate formatDate (String input) throws IllegalValueException {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(input, format);
        return date;
    }


}
```
###### \java\seedu\address\model\task\DescContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyTask}'s {@code Name} matches any of the keywords given.
 */
public class DescContainsKeywordsPredicate implements Predicate<ReadOnlyTask> {
    private final List<String> keywords;

    public DescContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyTask task) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getDescription().descriptionName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DescContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((DescContainsKeywordsPredicate) other).keywords)); // state check
    }
}
```
###### \java\seedu\address\model\task\DueDate.java
``` java
/**
 * Represents the date of a certain task in the Address Book.
 */

public class DueDate extends Date {


    public final String date;


    /**
     * Validates given due date.
     *
     * @throws IllegalValueException if the given date string is invalid
     */
    public DueDate (String input) throws IllegalValueException {
        requireNonNull(input);
        String trimmedInput = input.trim();
        if (!Date.isValidDate(trimmedInput) && !trimmedInput.isEmpty()) {
            throw new IllegalValueException(MESSAGE_DATE_FORMAT_CONSTRAINTS);
        }
        this.date = trimmedInput;
    }

    @Override
    public String toString() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if sme object
                        || (other instanceof DueDate // instanceof handles nulls
                        && this.date.equals(((DueDate) other).date));
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
```
###### \java\seedu\address\model\task\exceptions\DuplicateTaskException.java
``` java
/**
 * Signals that the operation will result in duplicate Task objects.
 */

public class DuplicateTaskException extends DuplicateDataException {

    public DuplicateTaskException() {
        super("Operation would result in duplicated tasks.");
    }

}
```
###### \java\seedu\address\model\task\exceptions\TaskNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specifed task.
 */
public class TaskNotFoundException extends Exception{}
```
###### \java\seedu\address\model\task\UniqueTaskList.java
``` java
/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */

public class UniqueTaskList implements  Iterable<Task> {

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
     * @throws seedu.address.model.task.exceptions.DuplicateTaskException if the task to add is a duplicate of an
     *                                                                    existing task in the list.
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
     * @throws DuplicateTaskException  if the replacement is equivalent to another existing task in the list.
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

    public void setTasks(List<? extends ReadOnlyTask> tasks) throws DuplicateTaskException {
        final UniqueTaskList replacement = new UniqueTaskList();
        for (final ReadOnlyTask task : tasks) {
            replacement.add(new Task(task));
        }
        setTasks(replacement);
    }

    /**
     * Returns the backing lsit as an unmodifiable {@code ObservableList}.
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
                || (other instanceof UniqueTaskList // instanceof handle nulls
                && this.internalList.equals(((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedTask.java
``` java
/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private String priority;
    @XmlElement(required = true)
    private String duedate;


    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask () {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param  source future changes to this will not affect the created XmlAdaptedTask
     */

    public XmlAdaptedTask(ReadOnlyTask source) {
        description = source.getDescription().descriptionName;
        priority = source.getPriority().value;
        duedate = source.getDueDate().date;
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public Task toModelType() throws IllegalValueException {
        final Description description = new Description(this.description);
        final Priority priority = new Priority(this.priority);
        final DueDate dueDate = new DueDate(this.duedate);
        return new Task(description, priority, dueDate);
    }
}


```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        final ObservableList<ReadOnlyTask> tasks = this.tasks.stream().map(t -> {
            try {
                return t.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(tasks);
    }

}
```
###### \resources\view\Light.css
``` css

/**
 * Shared
 */
.root * {
    midLight: derive(white, -70%);
    -fx-font-family: "Helvetica";
    -fx-background-color: transparent;
    -fx-text-fill: black;
}

.scroll-bar .thumb {
    -fx-background-color: derive(grey, -20%);
}


/**
 * Background
 */
.background {
    -fx-background-color: derive(grey, -20%);
    background-color: #f2f2f2; /* Used in the default.html file */
    background-image: url("../images/world_map_1.jpg");
    background-size: 500px;
    background-position: left 1px;
    background-repeat: no-repeat;
}


/**
 * Menu Bar
 */
.context-menu {
    -fx-background-color: derive(grey, 90%);
}

.context-menu .label {
    -fx-text-fill: black;
}

.menu-bar {
    -fx-background-color: #fafafa;
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-text-fill: black;
    -fx-opacity: 0.9;
}


/**
 * Command Box
 */
#commandTextField {
    -fx-font-size: 20pt;
    -fx-font-weight: bold;
    -fx-border-width: 1;
}


/**
 * Result Display
 */
#resultDisplay .content {
    -fx-background-color: #fafafa;
    -fx-background-radius: 5;
    /*
    -fx-border-color: transparent transparent black transparent;
    */
}


/**
 * Person List
 */
#personListVBox #name, #personListVBox #id{
    -fx-font-size: 15pt;
}

#personListVBox .scroll-bar:horizontal .thumb {
   /*
    -fx-background-color: transparent;
    */
    -fx-background-color: blue;
}

/**
 * List Cell
 */
.list-cell:filled:odd {
    -fx-background-color: #fafafa;
}

.list-cell:filled:even {
    -fx-background-color: #f2f2f2;
}

.list-cell:filled:hover {
    -fx-background-color: rgba(192, 192, 192, 5);
}

.list-cell:filled:selected {
    -fx-background-color: rgba(192, 192, 192, 10);
}


/**
 * Cell
 */
.cell_big_label {
    -fx-font-size: 20px;
    -fx-text-fill: black;
    -fx-font-weight: normal;
}

.cell_small_label {
    -fx-font-size: 13px;
    -fx-text-fill: #424242;
    -fx-font-weight: lighter;
}


/**
 * Status Bar
 */
.status-bar {
    -fx-background-color: #fafafa;
    -fx-text-fill: black;
    -fx-font-weight: bold;
}


/**
 * Tags
 */
#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
 }

#tags .label {
    -fx-text-fill: black;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
    -fx-font-weight: bold;

 }
```
