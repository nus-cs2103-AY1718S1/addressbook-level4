# zhangshuoyang
###### /java/seedu/address/commons/events/ui/SwitchThemeRequestEvent.java
``` java
/**
 * Indicate a request to switch theme by the user
 */
public class SwitchThemeRequestEvent extends BaseEvent {

    public final Index index;

    public SwitchThemeRequestEvent(Index index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return this.getClass().toString();
    }
}
```
###### /java/seedu/address/logic/commands/AddTaskCommand.java
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
###### /java/seedu/address/logic/commands/SwitchThemeCommand.java
``` java
/**
 * Switch the theme of the address book
 */
public class SwitchThemeCommand extends Command {

    public static final String COMMAND_WORD = "switch";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Switch to selected theme\n"
            + "1. Dark;   2. Light;   3. Colourful\n"
            + "Parameters: INDEX (must be 1, 2 or 3)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_THEME_SUCCESS = "Theme updated: %1$s";

    private final Index index;

    public SwitchThemeCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute() throws CommandException {
        String[] themeArr = {"Dark", "Light", "Colourful"};
        if (index.getZeroBased() >= themeArr.length || index.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_THEME_INDEX);
        }

        EventsCenter.getInstance().post(new SwitchThemeRequestEvent(index));
        return new CommandResult(String.format(MESSAGE_THEME_SUCCESS, themeArr[index.getZeroBased()]));
    }


    @Override
    public boolean equals(Object other) {
        return other == this //shortcut if same object
                || (other instanceof SwitchThemeCommand
                && this.index.equals(((SwitchThemeCommand) other).index));
    }
}
```
###### /java/seedu/address/logic/parser/AddTaskCommandParser.java
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
                ArgumentTokenizer.tokenize(userInput, PREFIX_DESCIPTION, PREFIX_PRIORITY, PREFIX_DUEDATE);
        if (!arePrefixesPresent(argumentMultimap, PREFIX_DESCIPTION, PREFIX_PRIORITY, PREFIX_DUEDATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }
        try {
            Description description = ParserUtil.parseDescriptionOptional(argumentMultimap.getValue
                    (PREFIX_DESCIPTION)).get();
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
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
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
###### /java/seedu/address/logic/parser/SwitchThemeCommandParser.java
``` java
/**
 * Parses the given {@code String} of arguments in the context of the SwitchThemeCommand
 * and returns an SwitchThemeCommand object for execution.
 */
public class SwitchThemeCommandParser implements Parser<SwitchThemeCommand> {

    @Override
    public SwitchThemeCommand parse(String userInput) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(userInput);
            return new SwitchThemeCommand(index);
        } catch (IllegalValueException e) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    SwitchThemeCommand.MESSAGE_USAGE));
        }
    }

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
###### /java/seedu/address/model/Model.java
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


    /**
     * Clears the filters that have been applied to the filterd person list
     */

    void clearFiltersOnPersonList();

```
###### /java/seedu/address/model/ModelManager.java
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
###### /java/seedu/address/model/task/Date.java
``` java
/**
 * Represents the date of a certain task in the Address Book.
 */

public class Date {

    public static final String MESSAGE_DATE_FORMAT_CONSTRAINTS =
            "The date must be in the format dd/MM/yyyy";
    public static final String MESSAGE_DATE_INVALID_CONSTRAINTS =
            "The input date is not valid!";


    /**
     * Returns true if the given date is in the valid format.
     *
     * @throws java.time.format.DateTimeParseException if the date format is invalid
     */
    public static boolean isValidDate (String input) throws IllegalValueException {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate.parse(input, format);
            return true;
        } catch (DateTimeParseException exc) {
            return false;
        }
    }

}
```
###### /java/seedu/address/model/task/DescContainsKeywordsPredicate.java
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
###### /java/seedu/address/model/task/DueDate.java
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
            if (!Date.isValidDate(trimmedInput)) {
                throw new IllegalValueException(MESSAGE_DATE_INVALID_CONSTRAINTS);
            }
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
###### /java/seedu/address/model/task/exceptions/DuplicateTaskException.java
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
###### /java/seedu/address/model/task/exceptions/TaskNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specifed task.
 */
public class TaskNotFoundException extends Exception{}
```
###### /java/seedu/address/model/task/UniqueTaskList.java
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
###### /java/seedu/address/storage/XmlAdaptedTask.java
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
###### /java/seedu/address/storage/XmlSerializableAddressBook.java
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
###### /java/seedu/address/ui/CommandBox.java
``` java
            if (parser.parseCommand(userInput) instanceof AddTaskCommand) {
                // Process and display the most recently added task in a separate text field
                StringBuffer lastTaskFieldOutput = new StringBuffer();
                List<ReadOnlyTask> listOfTask = logic.getFilteredTaskList();
                lastTaskFieldOutput.append("\n");
                lastTaskFieldOutput.append(listOfTask.get(listOfTask.size() - 1).toString());
                lastTaskFieldOutput.append("\n");
                PrintWriter out = new PrintWriter("taskAdded.txt");
                out.println(lastTaskFieldOutput.toString());
                out.close();

                try {
                    String curr = System.getProperty("user.dir");
                    Scanner s = new Scanner(new File(curr + "/taskAdded.txt"));

                    taskDisplayed.clear();
                    taskDisplayed.appendText("===Last Task Added=== " + "\n");
                    while (s.hasNext()) {
                        String temp = s.next();
                        if (("Description:").equals(temp)
                                || ("Priority:").equals(temp)
                                || ("DueDate:").equals(temp)) {
                            taskDisplayed.appendText("\n");
                        }
                        taskDisplayed.appendText(temp + " ");
                    }
                } catch (FileNotFoundException fne) {
                    throw new ParseException(fne.getMessage(), fne);
                }
            }

```
###### /java/seedu/address/ui/MainWindow.java
``` java
    /**
     * Set theme based on user's input index
     */
    private void handleTheme(Index index) throws CommandException {
        String[] themeArr = {"DarkTheme", "Light", "Ugly"};
        String selectedTheme = themeArr[index.getZeroBased()];

        String path = new String("/view/" + selectedTheme + ".css");
        prefs.setAddressBookTheme(themeArr[index.getZeroBased()] + ".css");

        if (MainApp.class.getResource(path) == null) {
            throw new CommandException(Messages.MESSAGE_UNKNOWN_THEME);
        }

        mainWindow.getStylesheets().clear();
        mainWindow.getStylesheets().add(path);
        mainWindow.getStylesheets().add("/view/Extensions.css");

    }

```
###### /resources/view/Light.css
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
    background-image: url("../images/bg_image.jpg");
    /* BrowserPanel: width of pane and anchor is set to change size of image */
    background-size: cover;
    background-position: center center fixed;
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
###### /resources/view/Ugly.css
``` css

.background {
    -fx-background-color: red;
    background-color: #f2f2f2; /* Used in the default.html file */
    background-image: url("../images/world_map_1.jpg");
    background-size: 500px;
    background-position: left 1px;
    background-repeat: no-repeat;
}

.label { /* Used in status bar font size, tag font family */
    -fx-font-size: 10pt;
    -fx-font-family: "Helvetica";
    -fx-text-fill: black;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: "Helvetica";
    -fx-text-fill: black;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Helvetica";
    -fx-text-fill: black;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
    -fx-font-family: "Helvetica";
    -fx-border-color: transparent;
}

.tab-pane {
    -fx-padding: 0 0 0 1;
}

.tab-pane .tab-header-area {
    -fx-padding: 0 0 0 0;
    -fx-min-height: 0;
    -fx-max-height: 0;
}
/* TODO: 1 */
/* Not sure of what is it */
.table-view {
    -fx-base: #ca6850;
    -fx-control-inner-background: #bd976a;
    -fx-background-color: yellow;
    -fx-table-cell-border-color: transparent;
    -fx-table-header-border-color: transparent;
    -fx-padding: 5;
}

.table-view .column-header-background {
    -fx-background-color: transparent;
}

.table-view .column-header, .table-view .filler {
    -fx-size: 35;
    -fx-border-width: 0 0 1 0;
    -fx-background-color: transparent;
    -fx-border-color: red;
    -fx-border-insets: 0 10 1 0;
}

.table-view .column-header .label {
    -fx-font-size: 20pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider { /* Used for split pane divider color */
    -fx-background-color: yellow;
    -fx-border-color: transparent transparent transparent #CCCCCC;
}

.split-pane { /* Borders left pane. Borders right pane. */
    -fx-background-color: green;
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
}

.list-cell:filled:even { /* Used for cell background */
    -fx-background-color: #d0c8c0;
}

.list-cell:filled:odd { /* Used for cell background */
    -fx-background-color: #8d776d;
}

.list-cell:filled:selected { /* Used for cell background when selected */
    -fx-background-color: #a76666;
}

.list-cell:filled:selected #cardPane { /* Used for border on selected cell */
    -fx-border-color: blue;
    -fx-border-width: 0;
}

.list-cell .label {
    -fx-text-fill: blue;
}

.cell_big_label { /* Used for people's names */
    -fx-font-family: sans-serif;
    -fx-font-size: 16px;
    -fx-text-fill: #333333;
}

.cell_small_label { /* Used for people's details */
    -fx-font-family: sans-serif;
    -fx-font-size: 13px;
    -fx-text-fill: #666666;
}

/* not sure */
.anchor-pane { /* Effectively the command bar background */
     -fx-background-color: yellow;
     -fx-background-radius: 10px;
}

.anchor-pane-status { /* Effectively the status bar background */
}

.pane-with-border { /* Command pane border background color */
     -fx-background-color: green; /* could be the same as split-pane bg colour*/
}

.status-bar {
    -fx-background-color: B3B3B3;
    -fx-text-fill: black;
}

.result-display { /* Used for command results */
    -fx-font-family: sans-serif;
    -fx-font-size: 20pt;
    -fx-text-fill: yellow;
}

/* TODO: 2 */

.result-display .label {
    /* -fx-text-fill: black !important; */
}

.status-bar .label { /* Status bar font and colour of text */
    -fx-font-family: "Helvetica";
    -fx-text-fill: green;
}

.status-bar-with-border { /* not sure */
    -fx-background-color: red;
}

.status-bar-with-border .label { /* not sure */
    -fx-text-fill: blue;
}

.grid-pane { /* Background behind Status bar (border) */
    -fx-background-color: blue;
    -fx-background-radius: 0px;
}

.grid-pane .anchor-pane { /* Status bar background colour */
    -fx-background-color: white;
}

.context-menu { /* Menu button background */
    -fx-background-color: pink;
}

.context-menu .label { /* Menu button text colour */
    -fx-text-fill: white;
}

.menu-bar { /* Menu bar colour */
    -fx-background-color: #4F6D7A;
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: "Helvetica";
    -fx-text-fill: white;
    -fx-opacity: 0.9;
}

.menu .left-container { /* not sure */
    -fx-background-color: yellow;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button { /* not sure what is button */
    -fx-padding: 5 22 5 22;
    -fx-border-color: red;
    -fx-border-width: 2;
    -fx-background-color: blue;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: #d8d8d8;
    -fx-border-radius: 20;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: yellow;
}

.button:pressed, .button:default:hover:pressed {
  -fx-background-color: green;
  -fx-text-fill: blue;
}

.button:focused {
    -fx-border-color: white, white;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: blue;
    -fx-text-fill: white;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #ffffff;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

/* TODO: 3 */
.dialog-pane {
    -fx-background-color: yellow;
}

.dialog-pane > *.button-bar > *.container { /*not sure */
    -fx-background-color: green;
}

.dialog-pane > *.label.content { /*not sure */
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: pink;
}

.dialog-pane:header *.header-panel { /*not sure */
    -fx-background-color: blue;
}

.dialog-pane:header *.header-panel *.label { /*not sure */
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: white;
}

.scroll-bar { /* Scroll bar column background color */
    -fx-background-color: pink;
}

.scroll-bar .thumb { /* Scroll bar background color */
    -fx-background-color: orange;
    -fx-background-insets: 3;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 8 1 8;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 8 1 8 1;
}

#cardPane {
    -fx-background-color: transparent;
    -fx-border-width: 0;
}

#commandTypeLabel {
    -fx-font-size: 11px;
    -fx-text-fill: #F70D1A;
}

#commandTextField { /* Command box */
    -fx-background-color: red;
    -fx-background-insets: 0;
    -fx-font-family: "Helvetica";
    -fx-font-size: 14pt;
    -fx-text-fill: green;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);
}

#resultDisplay .content { /* Result box background color and radius */
    -fx-background-color: purple;
    -fx-padding: 10px;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: purple;
    -fx-background-color: #4F6D7A;
    -fx-padding: 2 7 2 7;
    -fx-border-radius: 2;
    -fx-background-radius: 10;
    -fx-font-size: 11;
}
```
