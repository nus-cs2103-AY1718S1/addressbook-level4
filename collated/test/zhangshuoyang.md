# zhangshuoyang
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void addTask(ReadOnlyTask task) throws DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTask(ReadOnlyTask task) throws TaskNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateTask(ReadOnlyTask target, ReadOnlyTask editedTask)
                throws DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyTask> getFilteredTaskList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredTaskList(Predicate<ReadOnlyTask> predicate) {
            fail("This method should not be called.");
        }

    }

```
###### \java\seedu\address\logic\commands\AddTaskCommandIntegrationTest.java
``` java
/**
 * Contains integraion tests (interactin with the Model) for {@code AddTaskCommand}.
 */
public class AddTaskCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void executeNewTaskSuccess() throws Exception {
        Task validTask = new TaskBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTask(validTask);

        assertCommandSuccess(prepareCommand(validTask, model), model,
                String.format(AddTaskCommand.MESSAGE_SUCCESS, validTask), expectedModel);
    }

    @Test
    public void executeDuplicateTaskThrowsCommandException() {
        Task taskInList = new Task(model.getAddressBook().getTaskList().get(0));
        assertCommandFailure(prepareCommand(taskInList, model), model, AddTaskCommand.MESSAGE_DUPLICATE_TASK);
    }

    /**
     * Generates a new {@code AddTaskCommand} which upon execution, adds {@code task} into the {@code model}.
     */
    private AddTaskCommand prepareCommand(Task task, Model model) {
        AddTaskCommand cmd = new AddTaskCommand(task);
        cmd.setData(model, new CommandHistory(), new UndoRedoStack());
        return cmd;
    }
}
```
###### \java\seedu\address\logic\commands\AddTaskCommandTest.java
``` java
public class AddTaskCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructorNullTaskThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddTaskCommand(null);
    }

    @Test
    public void executeTaskAcceptedByModelAddSuccessful() throws Exception {
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        Task validTask = new TaskBuilder().build();

        CommandResult cmdResult = getAddTaskCommandForTask(validTask, modelStub).execute();

        assertEquals(String.format(AddTaskCommand.MESSAGE_SUCCESS, validTask), cmdResult.feedbackToUser);
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }

    /**
     * Generates a new AddTaskCommand with the details of the given task.
     */
    private AddTaskCommand getAddTaskCommandForTask(Task task, Model model) {
        AddTaskCommand command = new AddTaskCommand(task);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }


    @Test
    public void executeDuplicateTaskThrowsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateTaskException();
        Task validTask = new TaskBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddTaskCommand.MESSAGE_DUPLICATE_TASK);

        getAddTaskCommandForTask(validTask, modelStub).execute();
    }


    /**
     * A Model stub that always throw a DuplicateTaskException when trying to add a task.
     */
    private class ModelStubThrowingDuplicateTaskException extends AddTaskCommandTest.ModelStub {
        @Override
        public void addTask(ReadOnlyTask task) throws DuplicateTaskException {
            throw new DuplicateTaskException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the task being added.
     */
    private class ModelStubAcceptingTaskAdded extends AddTaskCommandTest.ModelStub {
        private final ArrayList<Task> tasksAdded = new ArrayList<>();

        @Override
        public void addTask(ReadOnlyTask task) throws DuplicateTaskException {
            tasksAdded.add(new Task(task));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    private class ModelStub implements Model {

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void addTask(ReadOnlyTask task) throws DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTask(ReadOnlyTask task) throws TaskNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException, PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateTask(ReadOnlyTask target, ReadOnlyTask editedTask)
                throws DuplicateTaskException, TaskNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyTask> getFilteredTaskList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredTaskList(Predicate<ReadOnlyTask> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void clearFiltersOnPersonList() {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonByTagList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonByTagList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteTag(Tag t) throws DuplicatePersonException, PersonNotFoundException {
            fail("This method should not be called.");
        }
    }

    @Test
    public void equals() {
        Task assignment = new TaskBuilder().withDescription("Assignment").build();
        Task shopping = new TaskBuilder().withDescription("Shopping").build();
        AddTaskCommand addAssignmentCommand = new AddTaskCommand(assignment);
        AddTaskCommand addShoppingCommand = new AddTaskCommand(shopping);

        // same object -> returns true
        assertTrue(addAssignmentCommand.equals(addAssignmentCommand));

        // same values -> returns true
        AddTaskCommand addAssignmentCommandCopy = new AddTaskCommand(assignment);
        assertTrue(addAssignmentCommand.equals(addAssignmentCommandCopy));

        // different types -> returns false
        assertFalse(addAssignmentCommand.equals(1));

        // null -> returns false
        assertFalse(addAssignmentCommand == (null));

        // different task -> returns false
        assertFalse(addAssignmentCommand.equals(addShoppingCommand));
    }

}

```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String VALID_DESCRIPTION_ASSIGNMENT = "CS2103T Assignment";
    public static final String VALID_DESCRIPTION_SHOPPING = "Go shopping";
    public static final String VALID_PRIORITY_ASSIGNMENT = "2";
    public static final String VALID_PRIORITY_SHOPPING = "0";
    public static final String VALID_DUEDATE_ASSIGNMENT = "30/10/2017";
    public static final String VALID_DUEDATE_SHOPPING = "01/01/2018";

    public static final String INVALID_PRIORITY_SHOPPING = "6";
    public static final String INVALID_DUEDATE_SHOPPING = "40/20/100";

    public static final String DESCRIPTION_DESC_ASSIGNMENT = " " +  VALID_DESCRIPTION_ASSIGNMENT;
    public static final String DESCRIPTION_DESC_SHOPPING = " " +  VALID_DESCRIPTION_SHOPPING;
    public static final String PRIORITY_DESC_ASSIGNMENT = " " + PREFIX_PRIORITY + VALID_PRIORITY_ASSIGNMENT;
    public static final String PRIORITY_DESC_SHOPPING = " " + PREFIX_PRIORITY + VALID_PRIORITY_SHOPPING;
    public static final String DUEDATE_DESC_ASSIGNMENT = " " + PREFIX_DUEDATE + VALID_DUEDATE_ASSIGNMENT;
    public static final String DUEDATE_DESC_SHOPPING = " " + PREFIX_DUEDATE + VALID_DUEDATE_SHOPPING;
    public static final String INVALID_PRIORITY_DESC_SHOPPING = " " + PREFIX_PRIORITY + INVALID_PRIORITY_SHOPPING;
    public static final String INVALID_DUEDATE_DESC_SHOPPING = " " + PREFIX_DUEDATE + INVALID_DUEDATE_SHOPPING;

```
###### \java\seedu\address\testutil\AddressBookBuilder.java
``` java
    /**
     * Adds a new {@code Task} to the AddressBook that we are building.
     */
    public AddressBookBuilder withTask(ReadOnlyTask task) {
        try {
            addressBook.addTask(task);
        } catch (DuplicateTaskException e) {
            throw new IllegalArgumentException("task is expected to be unique.");
        }
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
```
###### \java\seedu\address\testutil\TaskBuilder.java
``` java
/**
 * A utility class to help with buidling Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_DESCRIPTION = "CS2103T Homework";
    public static final String DEFAULT_PRIORITY = "2";
    public static final String DEFAULT_DUEDATE = "25/10/2017";

    private Task task;

    public TaskBuilder() {
        try {
            Description description = new Description(DEFAULT_DESCRIPTION);
            Priority priority = new Priority(DEFAULT_PRIORITY);
            DueDate dueDate = new DueDate(DEFAULT_DUEDATE);
            this.task = new Task(description, priority, dueDate);
        } catch (IllegalValueException e) {
            throw new AssertionError("The value of default task is invalid.");
        }
    }

    /**
     * Initializes the TaskBuider with the data of {@code taskToCopy}.
     */
    public TaskBuilder(ReadOnlyTask taskToCopy) {
        this.task = new Task(taskToCopy);
    }

    /**
     * Sets the {@code Description} of the {@code Task} that we are buidling.
     */
    public TaskBuilder withDescription(String description) {
        try {
            this.task.setDescription(new Description(description));
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException("invalid name");
        }
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code Task} that we are buidling.
     */
    public TaskBuilder withPriority(String priority) {
        try {
            this.task.setPriority(new Priority(priority));
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException("invalid priority");
        }
        return this;
    }

    /**
     * Sets the {@code DueDate} of the {@code Task} that we are buidling.
     */
    public TaskBuilder withDueDate(String dueDate) {
        try {
            this.task.setDuedate(new DueDate(dueDate));
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException("invalid due date");
        }
        return this;
    }

    public Task build() {
        return this.task;
    }
}
```
###### \java\seedu\address\testutil\TaskUtil.java
``` java
/**
 * A utility class for Task.
 */
public class TaskUtil {

    /**
     * Returns an add task command string for adding the {@code task}.
     */
    public static String getAddTaskCommand(ReadOnlyTask task) {
        return AddTaskCommand.COMMAND_WORD + " " + getTaskDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code task}'s details.
     */
    public static String getTaskDetails(ReadOnlyTask task) {
        StringBuilder sb = new StringBuilder();
        sb.append(task.getDescription().descriptionName + " ");
        sb.append(PREFIX_PRIORITY + task.getPriority().value + " ");
        sb.append(PREFIX_DUEDATE + task.getDueDate().date + " ");
        return sb.toString();
    }
}
```
###### \java\seedu\address\testutil\TypicalTasks.java
``` java
/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final ReadOnlyTask HOMEWORK = new TaskBuilder().withDescription("CS2103 Homework")
            .withPriority("2")
            .withDueDate("25/10/2017").build();

    public static final ReadOnlyTask SURVEY = new TaskBuilder().withDescription("Complete Peer Review Survey")
            .withPriority("1")
            .withDueDate("01/11/2017").build();


    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final ReadOnlyTask ASSIGNMENT = new TaskBuilder().withDescription(VALID_DESCRIPTION_ASSIGNMENT)
            .withPriority(VALID_PRIORITY_ASSIGNMENT).withDueDate(VALID_DUEDATE_ASSIGNMENT).build();

    public static final ReadOnlyTask SHOPPING = new TaskBuilder().withDescription(VALID_DESCRIPTION_SHOPPING)
            .withPriority(VALID_PRIORITY_SHOPPING).withDueDate(VALID_DUEDATE_SHOPPING).build();

    private TypicalTasks() {} // prevents instantiation

    public static List<ReadOnlyTask> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(HOMEWORK, SURVEY));
    }

}

```
###### \java\systemtests\AddTaskCommandSystemTest.java
``` java
public class AddTaskCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();
        /* Case: add a task to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        ReadOnlyTask toAdd = ASSIGNMENT;
        String command = "   " + AddTaskCommand.COMMAND_WORD + "  " + DESCRIPTION_DESC_ASSIGNMENT + "  "
                + PRIORITY_DESC_ASSIGNMENT + " " + DUEDATE_DESC_ASSIGNMENT;
        assertCommandSuccess(command, toAdd);


        //TODO: undo/redo cases need to be checked again.

        /* Case: undo adding Assignment to the list -> ASSIGNMENT deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Assignment to the list -> Assignment added again */
        command = RedoCommand.COMMAND_WORD;
        model.addTask(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a duplicate task -> rejected */
        command = AddTaskCommand.COMMAND_WORD + DESCRIPTION_DESC_ASSIGNMENT + PRIORITY_DESC_ASSIGNMENT
                + DUEDATE_DESC_ASSIGNMENT;
        assertCommandFailure(command, AddTaskCommand.MESSAGE_DUPLICATE_TASK);

        /* Case: add a task with all fields same as another task in the address book except description -> added */
        toAdd = new TaskBuilder().withDescription(VALID_DESCRIPTION_SHOPPING).withPriority(VALID_PRIORITY_ASSIGNMENT)
                .withDueDate(VALID_DUEDATE_ASSIGNMENT).build();
        command = AddTaskCommand.COMMAND_WORD + DESCRIPTION_DESC_SHOPPING + PRIORITY_DESC_ASSIGNMENT
                + DUEDATE_DESC_ASSIGNMENT;
        assertCommandSuccess(command, toAdd);

        /* Case: add a task with all fields same as another task in the address book except priority -> added */
        toAdd = new TaskBuilder().withDescription(VALID_DESCRIPTION_ASSIGNMENT).withPriority(VALID_PRIORITY_SHOPPING)
                .withDueDate(VALID_DUEDATE_ASSIGNMENT).build();
        command = AddTaskCommand.COMMAND_WORD + DESCRIPTION_DESC_ASSIGNMENT + PRIORITY_DESC_SHOPPING
                + DUEDATE_DESC_ASSIGNMENT;
        assertCommandSuccess(command, toAdd);

        /* Case: add a task with all fields same as another task in the address book except due date -> added */
        toAdd = new TaskBuilder().withDescription(VALID_DESCRIPTION_ASSIGNMENT).withPriority(VALID_PRIORITY_ASSIGNMENT)
                .withDueDate(VALID_DUEDATE_SHOPPING).build();
        command = AddTaskCommand.COMMAND_WORD + DESCRIPTION_DESC_ASSIGNMENT + PRIORITY_DESC_ASSIGNMENT
                + DUEDATE_DESC_SHOPPING;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty address book -> added */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getTaskList().size() == 0;
        assertCommandSuccess(ASSIGNMENT);


        /* Case: add a task, missing description -> added? */

        /* Case: invalid keyword -> rejected */
        command = "taks" + TaskUtil.getTaskDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid priority -> rejected */
        command = AddTaskCommand.COMMAND_WORD + DESCRIPTION_DESC_SHOPPING + INVALID_PRIORITY_DESC_SHOPPING
                + DUEDATE_DESC_SHOPPING;
        assertCommandFailure(command, Priority.MESSAGE_PRIORITY_CONSTRAINTS);

        /* Case: invalid due date -> rejected */
        command = AddTaskCommand.COMMAND_WORD + DESCRIPTION_DESC_SHOPPING + PRIORITY_DESC_SHOPPING
                + INVALID_DUEDATE_DESC_SHOPPING;
        assertCommandFailure(command, DueDate.MESSAGE_DATE_FORMAT_CONSTRAINTS);
    }


    /**
     * Executes the {@code AddTaskCommand} that adds {@code toAdd} to the model and verifies that the command
     * box displays an empty string, the result display box displays the success message of executing
     * {@code AddTaskCommand} with the details of {@code toAdd}, and the model related components equal to the
     * current model added with {@code toAdd}. These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class, the status bar's sync status changes,
     * the browser url and selected card remains unchanged.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(ReadOnlyTask toAdd) {
        assertCommandSuccess(TaskUtil.getAddTaskCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(ReadOnlyTask)}. Executes {@code command}
     * instead.
     * @see AddTaskCommandSystemTest#assertCommandSuccess(ReadOnlyTask)
     */
    private void assertCommandSuccess(String command, ReadOnlyTask toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addTask(toAdd);
        } catch (DuplicateTaskException dte) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddTaskCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ReadOnlyTask)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     * @see AddTaskCommandSystemTest#assertCommandSuccess(String, ReadOnlyTask)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
