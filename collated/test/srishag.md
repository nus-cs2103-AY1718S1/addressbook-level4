# srishag
###### /data/XmlUtilTest/validAddressBook.xml
``` xml
        <birthday isPrivate="false">06091971</birthday>
```
###### /data/XmlUtilTest/validAddressBook.xml
``` xml
        <birthday isPrivate="false">02121983</birthday>
```
###### /data/XmlUtilTest/validAddressBook.xml
``` xml
        <birthday isPrivate="false">19041999</birthday>
```
###### /data/XmlUtilTest/validAddressBook.xml
``` xml
        <birthday isPrivate="false">02011990</birthday>
```
###### /data/XmlUtilTest/validAddressBook.xml
``` xml
        <birthday isPrivate="false">29021992</birthday>
```
###### /data/XmlUtilTest/validAddressBook.xml
``` xml
        <birthday isPrivate="false">09021994</birthday>
```
###### /data/XmlUtilTest/validAddressBook.xml
``` xml
        <birthday isPrivate="false">19021990</birthday>
```
###### /data/XmlUtilTest/validAddressBook.xml
``` xml
        <birthday isPrivate="false">22021996</birthday>
```
###### /data/XmlUtilTest/validAddressBook.xml
``` xml
        <birthday isPrivate="false">27061994</birthday>
```
###### /java/guitests/AddressBookGuiTest.java
``` java
    protected TaskListPanelHandle getTaskListPanel() {
        return mainWindowHandle.getTaskListPanel();
    }
```
###### /java/guitests/guihandles/MainWindowHandle.java
``` java
    private final TaskListPanelHandle taskListPanel;
```
###### /java/guitests/guihandles/MainWindowHandle.java
``` java
        taskListPanel = new TaskListPanelHandle(getChildNode(TaskListPanelHandle.TASK_LIST_VIEW_ID));
```
###### /java/guitests/guihandles/MainWindowHandle.java
``` java
    public TaskListPanelHandle getTaskListPanel() {
        return taskListPanel;
    }
```
###### /java/guitests/guihandles/PersonCardHandle.java
``` java
    private static final String BIRTHDAY_FIELD_ID = "#birthday";
```
###### /java/guitests/guihandles/PersonCardHandle.java
``` java
    private final Label birthdayLabel;
```
###### /java/guitests/guihandles/PersonCardHandle.java
``` java
        this.birthdayLabel = getChildNode(BIRTHDAY_FIELD_ID);
```
###### /java/guitests/guihandles/PersonCardHandle.java
``` java
    public String getBirthday() {
        return birthdayLabel.getText();
    }
```
###### /java/guitests/guihandles/TaskCardHandle.java
``` java
package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String HEADER_FIELD_ID = "#header";
    private static final String DESC_FIELD_ID = "#desc";
    private static final String DEADLINE_FIELD_ID = "#deadline";

    private final Label idLabel;
    private final Label headerLabel;
    private final Label descLabel;
    private final Label deadlineLabel;

    public TaskCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.headerLabel = getChildNode(HEADER_FIELD_ID);
        this.descLabel = getChildNode(DESC_FIELD_ID);
        this.deadlineLabel = getChildNode(DEADLINE_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getHeader() {
        return headerLabel.getText();
    }

    public String getDesc() {
        return descLabel.getText();
    }

    public String getDeadline() {
        return deadlineLabel.getText();
    }

}
```
###### /java/guitests/guihandles/TaskListPanelHandle.java
``` java
package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.ui.TaskCard;

/**
 * Provides a handle for {@code TaskListPanel} containing the list of {@code TaskCard}.
 */
public class TaskListPanelHandle extends NodeHandle<ListView<TaskCard>> {
    public static final String TASK_LIST_VIEW_ID = "#taskListView";

    private Optional<TaskCard> lastRememberedSelectedTaskCard;

    public TaskListPanelHandle(ListView<TaskCard> taskListPanelNode) {
        super(taskListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code TaskCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public TaskCardHandle getHandleToSelectedCard() {
        List<TaskCard> taskList = getRootNode().getSelectionModel().getSelectedItems();

        if (taskList.size() != 1) {
            throw new AssertionError("Task list size expected 1.");
        }

        return new TaskCardHandle(taskList.get(0).getRoot());
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<TaskCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the task.
     */
    public void navigateToCard(ReadOnlyTask task) {
        List<TaskCard> cards = getRootNode().getItems();
        Optional<TaskCard> matchingCard = cards.stream().filter(card -> card.task.equals(task)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Task does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the task card handle of a task associated with the {@code index} in the list.
     */
    public TaskCardHandle getTaskCardHandle(int index) {
        return getTaskCardHandle(getRootNode().getItems().get(index).task);
    }

    /**
     * Returns the {@code TaskCardHandle} of the specified {@code task} in the list.
     */
    public TaskCardHandle getTaskCardHandle(ReadOnlyTask task) {
        Optional<TaskCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.task.equals(task))
                .map(card -> new TaskCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Task does not exist."));
    }

    /**
     * Selects the {@code TaskCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code TaskCard} in the list.
     */
    public void rememberSelectedTaskCard() {
        List<TaskCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedTaskCard = Optional.empty();
        } else {
            lastRememberedSelectedTaskCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code TaskCard} is different from the value remembered by the most recent
     * {@code rememberSelectedTaskCard()} call.
     */
    public boolean isSelectedTaskCardChanged() {
        List<TaskCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedTaskCard.isPresent();
        } else {
            return !lastRememberedSelectedTaskCard.isPresent()
                    || !lastRememberedSelectedTaskCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
```
###### /java/guitests/SampleDataTest.java
``` java
    @Test
    public void addressBook_dataFileDoesNotExist_loadSampleData() {
        Person[] expectedPersonList = SampleDataUtil.getSamplePersons();
        Task[] expectedTaskList = SampleDataUtil.getSampleTasks();
        assertListMatching(getPersonListPanel(), expectedPersonList);
        assertTaskListMatching(getTaskListPanel(), expectedTaskList);
    }
}
```
###### /java/seedu/address/logic/commands/AddCommandTest.java
``` java
        @Override
        public void sendEmail(Index index, String subject, String body) {
            fail("This method should not be called");
        }
```
###### /java/seedu/address/logic/commands/AddCommandTest.java
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
```
###### /java/seedu/address/logic/commands/AddTaskCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.GoogleAuthException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;
import seedu.address.testutil.TaskBuilder;

public class AddTaskCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddTaskCommand(null);
    }

    @Test
    public void execute_taskAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        Task validTask = new TaskBuilder().build();

        CommandResult commandResult = getAddTaskCommandForTask(validTask, modelStub).execute();

        assertEquals(String.format(AddTaskCommand.MESSAGE_SUCCESS, validTask), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateTaskException();
        Task validTask = new TaskBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddTaskCommand.MESSAGE_DUPLICATE_TASK);

        getAddTaskCommandForTask(validTask, modelStub).execute();
    }

    @Test
    public void equals() {
        Task homework = new TaskBuilder().withHeader("Homework").build();
        Task assignment = new TaskBuilder().withHeader("Assignment").build();
        AddTaskCommand addHomeworkCommand = new AddTaskCommand(homework);
        AddTaskCommand addAssignmentCommand = new AddTaskCommand(assignment);

        // same object -> returns true
        assertTrue(addHomeworkCommand.equals(addHomeworkCommand));

        // same values -> returns true
        AddTaskCommand addHomeworkCommandCopy = new AddTaskCommand(homework);
        assertTrue(addHomeworkCommand.equals(addHomeworkCommandCopy));

        // different types -> returns false
        assertFalse(addHomeworkCommand.equals(1));

        // null -> returns false
        assertFalse(addHomeworkCommand.equals(null));

        // different person -> returns false
        assertFalse(addHomeworkCommand.equals(addAssignmentCommand));
    }

    /**
     * Generates a new AddTaskCommand with the details of the given task.
     */
    private AddTaskCommand getAddTaskCommandForTask(Task task, Model model) {
        AddTaskCommand command = new AddTaskCommand(task);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

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
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException {
            fail("This method should not be called");
        }

        @Override
        public void sendEmail(Index index, String subject, String body) throws CommandException, GoogleAuthException {
            fail("This method should not be called");
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

    /**
     * A Model stub that always throw a DuplicateTaskException when trying to add a task.
     */
    private class ModelStubThrowingDuplicateTaskException extends ModelStub {
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
    private class ModelStubAcceptingTaskAdded extends ModelStub {
        final ArrayList<Task> tasksAdded = new ArrayList<>();

        @Override
        public void addTask(ReadOnlyTask task) throws DuplicateTaskException {
            tasksAdded.add(new Task(task));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
```
###### /java/seedu/address/logic/commands/AddTaskIntegrationTest.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddTaskCommand}.
 */
public class AddTaskIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newTask_success() throws Exception {
        Task validTask = new TaskBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTask(validTask);

        assertCommandSuccess(prepareCommand(validTask, model), model,
                String.format(AddTaskCommand.MESSAGE_SUCCESS, validTask), expectedModel);
    }

    /**
     * Generates a new {@code AddTaskCommand} which upon execution, adds {@code task} into the {@code model}.
     */
    private AddTaskCommand prepareCommand(Task task, Model model) {
        AddTaskCommand command = new AddTaskCommand(task);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    public static final String VALID_BIRTHDAY_AMY = "04061999";
    public static final String VALID_BIRTHDAY_BOB = "08081984";
```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    public static final String VALID_HEADER_HOMEWORK = "Homework";
    public static final String VALID_HEADER_ASSIGNMENT = "Assignment";
    public static final String VALID_DESC_HOMEWORK = "Page 6 to 9";
    public static final String VALID_DESC_ASSIGNMENT = "Tutorial homework";
    public static final String VALID_DEADLINE_HOMEWORK = "27/11/2017";
    public static final String VALID_DEADLINE_ASSIGNMENT = "05/12/2017";
```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    public static final String BIRTHDAY_DESC_AMY = " " + PREFIX_BIRTHDAY + VALID_BIRTHDAY_AMY;
    public static final String BIRTHDAY_DESC_BOB = " " + PREFIX_BIRTHDAY + VALID_BIRTHDAY_BOB;
```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    public static final String HEADER_DESC_HOMEWORK = " " + PREFIX_HEADER + VALID_HEADER_HOMEWORK;
    public static final String HEADER_DESC_ASSIGNMENT = " " + PREFIX_HEADER + VALID_HEADER_ASSIGNMENT;
    public static final String DESC_DESC_HOMEWORK = " " + PREFIX_DESC + VALID_DESC_HOMEWORK;
    public static final String DESC_DESC_ASSIGNMENT = " " + PREFIX_DESC + VALID_DESC_ASSIGNMENT;
    public static final String DEADLINE_DESC_HOMEWORK = " " + PREFIX_DEADLINE + VALID_DEADLINE_HOMEWORK;
    public static final String DEADLINE_DESC_ASSIGNMENT = " " + PREFIX_DEADLINE + VALID_DEADLINE_ASSIGNMENT;

    public static final String INVALID_HEADER_DESC = " " + PREFIX_HEADER; // empty string not allowed for headers
    public static final String INVALID_DESC_DESC = " " + PREFIX_DESC; // empty string not allowed for desc
```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    public static final String EMAIL_SENDER = "me"; //unique userId as recognized by Google

    public static final String VALID_EMAIL_SUBJECT = "Meeting agenda for next week.";
    public static final String VALID_EMAIL_BODY = "See you next Monday at 10 am.//Thanks.";

    public static final String VALID_EMAIL_SUBJECT_DESC = PREFIX_EMAIL_SUBJECT + VALID_EMAIL_SUBJECT;
    public static final String VALID_EMAIL_BODY_DESC = PREFIX_EMAIL_BODY + VALID_EMAIL_BODY;

    public static final String EMPTY_EMAIL_SUBJECT_DESC = PREFIX_EMAIL_SUBJECT + "";
    public static final String EMPTY_EMAIL_BODY_DESC = PREFIX_EMAIL_BODY + "";
```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    public static final EditTaskCommand.EditTaskDescriptor DESC_HOMEWORK;
    public static final EditTaskCommand.EditTaskDescriptor DESC_ASSIGNMENT;

    static {
        DESC_HOMEWORK = new EditTaskDescriptorBuilder().withHeader(VALID_HEADER_HOMEWORK)
                .withDesc(VALID_DESC_HOMEWORK).withDeadline(VALID_DEADLINE_HOMEWORK).build();
        DESC_ASSIGNMENT = new EditTaskDescriptorBuilder().withHeader(VALID_HEADER_ASSIGNMENT)
                .withDesc(VALID_DESC_ASSIGNMENT).withDeadline(VALID_DEADLINE_ASSIGNMENT).build();
    }
```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
        List<ReadOnlyPerson> expectedPersonFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());
        List<ReadOnlyTask> expectedTaskFilteredList = new ArrayList<>(actualModel.getFilteredTaskList());
```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    /**
     * Updates {@code model}'s filtered list to show only the first task in the {@code model}'s address book.
     */
    public static void showFirstTaskOnly(Model model) {
        ReadOnlyTask task = model.getAddressBook().getTaskList().get(0);
        final String[] splitHeader = task.getHeader().value.split("\\s+");
        model.updateFilteredTaskList(new HeaderContainsKeywordsPredicate(Arrays.asList(splitHeader[0])));

        assert model.getFilteredTaskList().size() == 1;
    }

    /**
     * Deletes the first task in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteTaskPerson(Model model) {
        ReadOnlyTask firstTask = model.getFilteredTaskList().get(0);
        try {
            model.deleteTask(firstTask);
        } catch (TaskNotFoundException e) {
            throw new AssertionError("Task in filtered list must exist in model.", e);
        }
    }
}
```
###### /java/seedu/address/logic/commands/DeleteTaskCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteTaskCommand}.
 */
public class DeleteTaskCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeleteTaskCommand deleteTaskCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteTaskCommand deleteFirstCommand = new DeleteTaskCommand(INDEX_FIRST_TASK);
        DeleteTaskCommand deleteSecondCommand = new DeleteTaskCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTaskCommand deleteFirstCommandCopy = new DeleteTaskCommand(INDEX_FIRST_TASK);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteTaskCommand} with the parameter {@code index}.
     */
    private DeleteTaskCommand prepareCommand(Index index) {
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(index);
        deleteTaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteTaskCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoTask(Model model) {
        model.updateFilteredTaskList(p -> false);

        assert model.getFilteredTaskList().isEmpty();
    }
}
```
###### /java/seedu/address/logic/commands/EditPersonDescriptorTest.java
``` java
        // different birthday -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withBirthday(VALID_BIRTHDAY_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));
```
###### /java/seedu/address/logic/commands/EditTaskCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_HOMEWORK;
import static seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditTaskCommand.
 */
public class EditTaskCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        final EditTaskCommand standardCommand = new EditTaskCommand(INDEX_FIRST_TASK, DESC_HOMEWORK);

        // same values -> returns true
        EditTaskDescriptor copyDescriptor = new EditTaskDescriptor(DESC_HOMEWORK);
        EditTaskCommand commandWithSameValues = new EditTaskCommand(INDEX_FIRST_TASK, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_SECOND_TASK, DESC_HOMEWORK)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_FIRST_TASK, DESC_ASSIGNMENT)));
    }

    /**
     * Returns an {@code EditTaskCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditTaskCommand prepareCommand(Index index, EditTaskDescriptor descriptor) {
        EditTaskCommand editTaskCommand = new EditTaskCommand(index, descriptor);
        editTaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editTaskCommand;
    }
}
```
###### /java/seedu/address/logic/commands/EditTaskDescriptorTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DESC_HOMEWORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEADER_ASSIGNMENT;

import org.junit.Test;

import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditTaskDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditTaskDescriptor descriptorWithSameValues = new EditTaskDescriptor(DESC_HOMEWORK);
        assertTrue(DESC_HOMEWORK.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_HOMEWORK.equals(DESC_HOMEWORK));

        // null -> returns false
        assertFalse(DESC_HOMEWORK.equals(null));

        // different types -> returns false
        assertFalse(DESC_HOMEWORK.equals(5));

        // different values -> returns false
        assertFalse(DESC_HOMEWORK.equals(DESC_BOB));

        // different name -> returns false
        EditTaskDescriptor editedHomework = new EditTaskDescriptorBuilder(DESC_HOMEWORK)
                .withHeader(VALID_HEADER_ASSIGNMENT).build();
        assertFalse(DESC_HOMEWORK.equals(editedHomework));

        // different phone -> returns false
        editedHomework = new EditTaskDescriptorBuilder(DESC_HOMEWORK).withDesc(VALID_DESC_ASSIGNMENT).build();
        assertFalse(DESC_HOMEWORK.equals(editedHomework));

        // different email -> returns false
        editedHomework = new EditTaskDescriptorBuilder(DESC_HOMEWORK).withDeadline(VALID_DEADLINE_ASSIGNMENT).build();
        assertFalse(DESC_HOMEWORK.equals(editedHomework));
    }
}
```
###### /java/seedu/address/logic/commands/SendEmailCommandTest.java
``` java
package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BODY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_SUBJECT;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.GoogleAuthException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

public class SendEmailCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;
    private final AddressBookParser parser = new AddressBookParser();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    /**
     * Checks if login is authenticated. In this case it is not, GoogleAuthException is thrown.
     */
    @Test
    public void executeLogin() throws Exception {
        LoginCommand loginCommand = new LoginCommand();
        CommandResult commandResult = loginCommand.execute();
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, VALID_EMAIL_SUBJECT, VALID_EMAIL_BODY,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showFirstPersonOnly(model);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, VALID_EMAIL_SUBJECT, VALID_EMAIL_BODY,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SendEmailCommand command = new SendEmailCommand(INDEX_FIRST_PERSON, VALID_EMAIL_SUBJECT, VALID_EMAIL_BODY);
        SendEmailCommand sameCommand = new SendEmailCommand(INDEX_FIRST_PERSON, VALID_EMAIL_SUBJECT, VALID_EMAIL_BODY);
        SendEmailCommand differentCommand = new SendEmailCommand(INDEX_SECOND_PERSON, VALID_EMAIL_SUBJECT,
                VALID_EMAIL_BODY);

        assertTrue(command.equals(sameCommand));

        assertTrue(command.equals(command));

        assertFalse(command == null);

        assertFalse(command == differentCommand);
    }

    /**
     * Executes a {@code SendEmailCommand} with the given {@code index, subject, body},
     * and checks that a {@code CommandException or GoogleAuthException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String subject, String body, String expectedMessage) {
        SendEmailCommand sendEmailCommand = prepareCommand(index, subject, body);

        try {
            sendEmailCommand.execute();
            Assert.fail("The expected exception was not thrown.");
        } catch (CommandException | GoogleAuthException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    /**
     * Returns a {@code SendEmailCommand} with parameters {@code index, subject, body}.
     */
    private SendEmailCommand prepareCommand(Index index, String subject, String body) {
        SendEmailCommand sendEmailCommand = new SendEmailCommand(index, subject, body);
        sendEmailCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sendEmailCommand;
    }
}

```
###### /java/seedu/address/logic/LogicManagerTest.java
``` java
    @Test
    public void getFilteredTaskList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        logic.getFilteredTaskList().remove(0);
    }
```
###### /java/seedu/address/logic/parser/AddCommandParserTest.java
``` java
        // multiple birthdays - last birthday accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + BIRTHDAY_DESC_AMY + BIRTHDAY_DESC_BOB + FACEBOOK_ADDRESS_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
```
###### /java/seedu/address/logic/parser/AddCommandParserTest.java
``` java
        // no birthday
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withBirthday("")
                .withFacebookAddress(VALID_FACEBOOKADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();

        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + FACEBOOK_ADDRESS_DESC_AMY
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_addTask() throws Exception {
        Task task = new TaskBuilder().build();
        AddTaskCommand command = (AddTaskCommand) parser.parseCommand(TaskUtil.getAddTaskCommand(task));
        assertEquals(new AddTaskCommand(task), command);

        listOfAllCommandWordsAndAliases.add(AddTaskCommand.COMMAND_WORD);
    }

    @Test
    public void parseCommand_addTask_alias() throws Exception {
        Task task = new TaskBuilder().build();
        AddTaskCommand command = (AddTaskCommand) parser.parseCommand(TaskUtil.getAddTaskCommand(task));
        assertEquals(new AddTaskCommand(task), command);

        listOfAllCommandWordsAndAliases.add(AddTaskCommand.COMMAND_ALIAS);
    }
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_deleteTask() throws Exception {
        DeleteTaskCommand command = (DeleteTaskCommand) parser.parseCommand(
                DeleteTaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased());
        assertEquals(new DeleteTaskCommand(INDEX_FIRST_TASK), command);

        listOfAllCommandWordsAndAliases.add(DeleteTaskCommand.COMMAND_WORD);

    }

    @Test
    public void parseCommand_deleteTask_alias() throws Exception {
        DeleteTaskCommand command = (DeleteTaskCommand) parser.parseCommand(
                DeleteTaskCommand.COMMAND_ALIAS + " " + INDEX_FIRST_TASK.getOneBased());
        assertEquals(new DeleteTaskCommand(INDEX_FIRST_TASK), command);

        listOfAllCommandWordsAndAliases.add(DeleteTaskCommand.COMMAND_ALIAS);
    }
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_editTask() throws Exception {
        Task task = new TaskBuilder().build();
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(task).build();
        EditTaskCommand command = (EditTaskCommand) parser.parseCommand(EditTaskCommand.COMMAND_WORD + " "
                + INDEX_FIRST_TASK.getOneBased() + " " + TaskUtil.getTaskDetails(task));
        assertEquals(new EditTaskCommand(INDEX_FIRST_TASK, descriptor), command);

        listOfAllCommandWordsAndAliases.add(EditTaskCommand.COMMAND_WORD);
    }

    @Test
    public void parseCommand_editTask_alias() throws Exception {
        Task task = new TaskBuilder().build();
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(task).build();
        EditTaskCommand command = (EditTaskCommand) parser.parseCommand(EditTaskCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_TASK.getOneBased() + " " + TaskUtil.getTaskDetails(task));
        assertEquals(new EditTaskCommand(INDEX_FIRST_TASK, descriptor), command);

        listOfAllCommandWordsAndAliases.add(EditTaskCommand.COMMAND_ALIAS);
    }
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_sendEmail() throws Exception {
        SendEmailCommand command = (SendEmailCommand) parser.parseCommand(SendEmailCommand.COMMAND_WORD
                + " " + "1" + " " + VALID_EMAIL_SUBJECT_DESC + " " + VALID_EMAIL_BODY_DESC);
        assertEquals(new SendEmailCommand(INDEX_FIRST_PERSON, VALID_EMAIL_SUBJECT, VALID_EMAIL_BODY), command);

        listOfAllCommandWordsAndAliases.add(SendEmailCommand.COMMAND_WORD);
    }

    @Test
    public void parseCommand_sendEmail_alias() throws Exception {
        SendEmailCommand command = (SendEmailCommand) parser.parseCommand(SendEmailCommand.COMMAND_ALIAS
                + " " + "1" + " " + VALID_EMAIL_SUBJECT_DESC + " " + VALID_EMAIL_BODY_DESC);
        assertEquals(new SendEmailCommand(INDEX_FIRST_PERSON, VALID_EMAIL_SUBJECT, VALID_EMAIL_BODY), command);

        listOfAllCommandWordsAndAliases.add(SendEmailCommand.COMMAND_ALIAS);
    }
```
###### /java/seedu/address/logic/parser/AddTaskCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_HOMEWORK;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DESC_HOMEWORK;
import static seedu.address.logic.commands.CommandTestUtil.HEADER_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.HEADER_DESC_HOMEWORK;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HEADER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEADER_ASSIGNMENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.model.task.Desc;
import seedu.address.model.task.Header;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class AddTaskCommandParserTest {
    private AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder().withHeader(VALID_HEADER_ASSIGNMENT).withDesc(VALID_DESC_ASSIGNMENT)
                .withDeadline(VALID_DEADLINE_ASSIGNMENT).build();

        // multiple headers - last header accepted
        assertParseSuccess(parser, AddTaskCommand.COMMAND_WORD + HEADER_DESC_HOMEWORK + HEADER_DESC_ASSIGNMENT
                + DESC_DESC_ASSIGNMENT + DEADLINE_DESC_ASSIGNMENT, new AddTaskCommand(expectedTask));

        // multiple desc - last desc accepted
        assertParseSuccess(parser, AddTaskCommand.COMMAND_WORD + HEADER_DESC_ASSIGNMENT
                + DESC_DESC_HOMEWORK + DESC_DESC_ASSIGNMENT
                + DEADLINE_DESC_ASSIGNMENT, new AddTaskCommand(expectedTask));

        // multiple deadlines - last deadline accepted
        assertParseSuccess(parser, AddTaskCommand.COMMAND_WORD + HEADER_DESC_ASSIGNMENT
                + DESC_DESC_ASSIGNMENT + DEADLINE_DESC_HOMEWORK
                + DEADLINE_DESC_ASSIGNMENT, new AddTaskCommand(expectedTask));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE);

        // missing header prefix
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + VALID_HEADER_ASSIGNMENT
                + DESC_DESC_ASSIGNMENT + DEADLINE_DESC_ASSIGNMENT, expectedMessage);

        // missing desc prefix
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + HEADER_DESC_ASSIGNMENT
                + VALID_DESC_ASSIGNMENT + DEADLINE_DESC_ASSIGNMENT, expectedMessage);

        // missing deadline prefix
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + HEADER_DESC_ASSIGNMENT
                + DESC_DESC_ASSIGNMENT + VALID_DEADLINE_ASSIGNMENT, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + VALID_HEADER_ASSIGNMENT
                + VALID_DESC_ASSIGNMENT + VALID_DEADLINE_ASSIGNMENT, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + INVALID_HEADER_DESC
                + DESC_DESC_ASSIGNMENT + DEADLINE_DESC_ASSIGNMENT, Header.MESSAGE_HEADER_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + HEADER_DESC_ASSIGNMENT
                + INVALID_DESC_DESC + DEADLINE_DESC_ASSIGNMENT, Desc.MESSAGE_DESC_CONSTRAINTS);

        // invalid email
        //assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + HEADER_DESC_ASSIGNMENT
        //        + DESC_DESC_ASSIGNMENT + INVALID_DEADLINE_DESC, Deadline.MESSAGE_DEADLINE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + INVALID_HEADER_DESC
                + INVALID_DESC_DESC + DEADLINE_DESC_ASSIGNMENT, Header.MESSAGE_HEADER_CONSTRAINTS);
    }
}

```
###### /java/seedu/address/logic/parser/DeleteTaskCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import org.junit.Test;

import seedu.address.logic.commands.DeleteTaskCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteTaskCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteTaskCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteTaskCommandParserTest {

    private DeleteTaskCommandParser parser = new DeleteTaskCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteTaskCommand() {
        assertParseSuccess(parser, "1", new DeleteTaskCommand(INDEX_FIRST_TASK));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteTaskCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/EditTaskCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_HOMEWORK;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DESC_HOMEWORK;
import static seedu.address.logic.commands.CommandTestUtil.HEADER_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.HEADER_DESC_HOMEWORK;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HEADER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_HOMEWORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_HOMEWORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEADER_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEADER_HOMEWORK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.model.task.Desc;
import seedu.address.model.task.Header;
import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditTaskCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE);

    private EditTaskCommandParser parser = new EditTaskCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_HEADER_HOMEWORK, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditTaskCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + HEADER_DESC_HOMEWORK, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + HEADER_DESC_HOMEWORK, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_HEADER_DESC,
                Header.MESSAGE_HEADER_CONSTRAINTS); // invalid header
        assertParseFailure(parser, "1" + INVALID_DESC_DESC,
                Desc.MESSAGE_DESC_CONSTRAINTS); // invalid desc
        //assertParseFailure(parser, "1" + INVALID_DEADLINE_DESC,
        //        Deadline.MESSAGE_DEADLINE_CONSTRAINTS); // invalid deadline


        // invalid deadline followed by valid desc
        //assertParseFailure(parser, "1" + DESC_DESC_HOMEWORK + INVALID_DEADLINE_DESC,
        //        Deadline.MESSAGE_DEADLINE_CONSTRAINTS);

        // valid desc followed by invalid desc. The test case for invalid desc followed by valid desc
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + DESC_DESC_ASSIGNMENT + INVALID_DESC_DESC,
                Desc.MESSAGE_DESC_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_HEADER_DESC + INVALID_DESC_DESC
                + VALID_DEADLINE_HOMEWORK, Header.MESSAGE_HEADER_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_TASK;
        String userInput = targetIndex.getOneBased() + DESC_DESC_ASSIGNMENT
                + HEADER_DESC_HOMEWORK + DEADLINE_DESC_HOMEWORK;

        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withHeader(VALID_HEADER_HOMEWORK)
                .withDesc(VALID_DESC_ASSIGNMENT).withDeadline(VALID_DEADLINE_HOMEWORK).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + DESC_DESC_ASSIGNMENT + DEADLINE_DESC_HOMEWORK;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withDesc(VALID_DESC_ASSIGNMENT)
                .withDeadline(VALID_DEADLINE_HOMEWORK).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // header
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + HEADER_DESC_HOMEWORK;
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withHeader(VALID_HEADER_HOMEWORK).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // desc
        userInput = targetIndex.getOneBased() + DESC_DESC_HOMEWORK;
        descriptor = new EditTaskDescriptorBuilder().withDesc(VALID_DESC_HOMEWORK).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // deadline
        userInput = targetIndex.getOneBased() + DEADLINE_DESC_HOMEWORK;
        descriptor = new EditTaskDescriptorBuilder().withDeadline(VALID_DEADLINE_HOMEWORK).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased()  + HEADER_DESC_HOMEWORK + DESC_DESC_HOMEWORK
                + DEADLINE_DESC_HOMEWORK + HEADER_DESC_HOMEWORK + DESC_DESC_HOMEWORK + DEADLINE_DESC_HOMEWORK;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withHeader(VALID_HEADER_HOMEWORK)
                .withDesc(VALID_DESC_HOMEWORK).withDeadline(VALID_DEADLINE_HOMEWORK).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + INVALID_DESC_DESC + DESC_DESC_ASSIGNMENT;
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withDesc(VALID_DESC_ASSIGNMENT).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + DEADLINE_DESC_ASSIGNMENT + INVALID_DESC_DESC + HEADER_DESC_ASSIGNMENT
                + DESC_DESC_ASSIGNMENT;
        descriptor = new EditTaskDescriptorBuilder().withDesc(VALID_DESC_ASSIGNMENT)
                .withDeadline(VALID_DEADLINE_ASSIGNMENT).withHeader(VALID_HEADER_ASSIGNMENT).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtilTest.java
``` java
    private static final String INVALID_HEADER = " ";
    private static final String INVALID_DESC = " ";
    private static final String INVALID_DEADLINE = "81947113";

    private static final String VALID_HEADER = "Homework";
    private static final String VALID_DESC = "Questions 1 and 2";
    private static final String VALID_DEADLINE = "23/12/2017";
```
###### /java/seedu/address/logic/parser/ParserUtilTest.java
``` java
    @Test
    public void parseBirthday_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseBirthday(null);
    }

    @Test
    public void parseBirthday_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseBirthday(Optional.of(INVALID_BIRTHDAY));
    }

    @Test
    public void parseBirthday_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseBirthday(Optional.empty()).isPresent());
    }

    @Test
    public void parseBirthday_validValue_returnsBirthday() throws Exception {
        Birthday expectedBirthday = new Birthday(VALID_BIRTHDAY);
        Optional<Birthday> actualBirthday = ParserUtil.parseBirthday(Optional.of(VALID_BIRTHDAY));

        assertEquals(expectedBirthday, actualBirthday.get());
    }
```
###### /java/seedu/address/logic/parser/ParserUtilTest.java
``` java
    @Test
    public void parseHeader_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseHeader(null);
    }

    @Test
    public void parseHeader_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseHeader(Optional.of(INVALID_HEADER));
    }

    @Test
    public void parseHeader_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseHeader(Optional.empty()).isPresent());
    }

    @Test
    public void parseHeader_validValue_returnsName() throws Exception {
        Header expectedHeader = new Header(VALID_HEADER);
        Optional<Header> actualHeader = ParserUtil.parseHeader(Optional.of(VALID_HEADER));

        assertEquals(expectedHeader, actualHeader.get());
    }

    @Test
    public void parseDesc_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseDesc(null);
    }

    @Test
    public void parseDesc_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseDesc(Optional.of(INVALID_DESC));
    }

    @Test
    public void parseDesc_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseDesc(Optional.empty()).isPresent());
    }

    @Test
    public void parseDesc_validValue_returnsPhone() throws Exception {
        Desc expectedDesc = new Desc(VALID_DESC);
        Optional<Desc> actualDesc = ParserUtil.parseDesc(Optional.of(VALID_DESC));

        assertEquals(expectedDesc, actualDesc.get());
    }

    @Test
    public void parseDeadline_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseDeadline(null);
    }

    /*@Test
    public void parseDeadline_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseDeadline(Optional.of(INVALID_DEADLINE));
    }*/

    @Test
    public void parseDeadline_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseDeadline(Optional.empty()).isPresent());
    }

    @Test
    public void parseDeadline_validValue_returnsAddress() throws Exception {
        Deadline expectedDeadline = new Deadline(VALID_DEADLINE);
        Optional<Deadline> actualDeadline = ParserUtil.parseDeadline(Optional.of(VALID_DEADLINE));

        assertEquals(expectedDeadline, actualDeadline.get());
    }
}

```
###### /java/seedu/address/logic/parser/SendEmailCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_EMAIL_BODY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_EMAIL_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BODY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_SUBJECT_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.SendEmailCommand;

public class SendEmailCommandParserTest {
    private SendEmailCommandParser parser = new SendEmailCommandParser();

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SendEmailCommand.MESSAGE_USAGE);

        //both subject and body stated
        assertParseFailure(parser, SendEmailCommand.COMMAND_WORD + INDEX_FIRST_PERSON + VALID_EMAIL_SUBJECT_DESC
                + VALID_EMAIL_BODY_DESC, expectedMessage);
        //only body stated
        assertParseFailure(parser, SendEmailCommand.COMMAND_WORD + INDEX_FIRST_PERSON + EMPTY_EMAIL_SUBJECT_DESC
                + VALID_EMAIL_BODY_DESC, expectedMessage);
        //only subject stated
        assertParseFailure(parser, SendEmailCommand.COMMAND_WORD + INDEX_FIRST_PERSON + VALID_EMAIL_SUBJECT_DESC
                + EMPTY_EMAIL_BODY_DESC, expectedMessage);
        //neither subject nor body stated
        assertParseFailure(parser, SendEmailCommand.COMMAND_WORD + INDEX_FIRST_PERSON + EMPTY_EMAIL_SUBJECT_DESC
                + EMPTY_EMAIL_BODY_DESC, expectedMessage);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, SendEmailCommand.COMMAND_WORD + "a" + VALID_EMAIL_SUBJECT_DESC
                + VALID_EMAIL_BODY_DESC, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SendEmailCommand.MESSAGE_USAGE));
    }

}

```
###### /java/seedu/address/model/AddressBookTest.java
``` java
    @Test
    public void getTaskList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getTaskList().remove(0);
    }
```
###### /java/seedu/address/model/AddressBookTest.java
``` java
        private final ObservableList<ReadOnlyTask> tasks = FXCollections.observableArrayList();
```
###### /java/seedu/address/model/AddressBookTest.java
``` java
            this.tasks.setAll(tasks);
```
###### /java/seedu/address/model/AddressBookTest.java
``` java
        @Override
        public ObservableList<ReadOnlyTask> getTaskList() {
            return tasks;
        }
```
###### /java/seedu/address/model/person/BirthdayTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BirthdayTest {

    @Test
    public void isValidBirthday() {
        // invalid birthdays
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday("9199")); // less than 8 numbers
        assertFalse(Birthday.isValidBirthday("birthday")); // non-numeric
        assertFalse(Birthday.isValidBirthday("0911a991")); // alphabets within digits
        assertFalse(Birthday.isValidBirthday("1203 1996")); // spaces within digits
        assertFalse(Birthday.isValidBirthday("15/09/1993")); // forward slash within digits
        assertFalse(Birthday.isValidBirthday("15.09.1993")); // fullstops within digits
        assertFalse(Birthday.isValidBirthday("99999999")); // invalid date

        // valid birthdays
        assertTrue(Birthday.isValidBirthday(""));
        assertTrue(Birthday.isValidBirthday("12111999")); // exactly 8 digits
    }
}
```
###### /java/seedu/address/model/UniqueTaskListTest.java
``` java
package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.task.UniqueTaskList;

public class UniqueTaskListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueTaskList uniqueTaskList = new UniqueTaskList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTaskList.asObservableList().remove(0);
    }
}
```
###### /java/seedu/address/storage/XmlAddressBookStorageTest.java
``` java
        original.addTask(new Task(MILK));
        original.removeTask(new Task(MILK));
```
###### /java/seedu/address/storage/XmlAddressBookStorageTest.java
``` java
    @Test
    public void getTaskList_modifyList_throwsUnsupportedOperationException() {
        XmlSerializableAddressBook addressBook = new XmlSerializableAddressBook();
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getTaskList().remove(0);
    }
```
###### /java/seedu/address/testutil/AddressBookBuilder.java
``` java
    /**
     * Adds a new {@code Task} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withTask(ReadOnlyTask task) {
        try {
            addressBook.addTask(task);
        } catch (DuplicateTaskException dte) {
            throw new IllegalArgumentException("task is expected to be unique.");
        }
        return this;
    }
```
###### /java/seedu/address/testutil/EditPersonDescriptorBuilder.java
``` java
        descriptor.setBirthday(person.getBirthday());
```
###### /java/seedu/address/testutil/EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code Birthday} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withBirthday(String birthday) {
        try {
            ParserUtil.parseBirthday(Optional.of(birthday)).ifPresent(descriptor::setBirthday);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("birthday is expected to be unique.");
        }
        return this;
    }
```
###### /java/seedu/address/testutil/EditTaskDescriptorBuilder.java
``` java
package seedu.address.testutil;

import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.task.ReadOnlyTask;

/**
 * A utility class to help with building EditTaskDescriptor objects.
 */
public class EditTaskDescriptorBuilder {

    private EditTaskDescriptor descriptor;

    public EditTaskDescriptorBuilder() {
        descriptor = new EditTaskDescriptor();
    }

    public EditTaskDescriptorBuilder(EditTaskDescriptor descriptor) {
        this.descriptor = new EditTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTaskDescriptor} with fields containing {@code Task}'s details
     */
    public EditTaskDescriptorBuilder(ReadOnlyTask task) {
        descriptor = new EditTaskDescriptor();
        descriptor.setHeader(task.getHeader());
        descriptor.setDesc(task.getDesc());
        descriptor.setDeadline(task.getDeadline());
    }

    /**
     * Sets the {@code header} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withHeader(String header) {
        try {
            ParserUtil.parseHeader(Optional.of(header)).ifPresent(descriptor::setHeader);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("header is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code desc} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withDesc(String desc) {
        try {
            ParserUtil.parseDesc(Optional.of(desc)).ifPresent(descriptor::setDesc);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("desc is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code deadline} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withDeadline(String deadline) {
        try {
            ParserUtil.parseDeadline(Optional.of(deadline)).ifPresent(descriptor::setDeadline);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("deadline is expected to be unique.");
        }
        return this;
    }


    public EditTaskDescriptor build() {
        return descriptor;
    }
}
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    public static final String DEFAULT_BIRTHDAY = "14051998";
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
            Birthday defaultBirthday = new Birthday(DEFAULT_BIRTHDAY);
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    /**
     *
     * Sets the {@code Birthday} of the {@code Person} that we are building.
     */
    public PersonBuilder withBirthday(String birthday) {
        try {
            this.person.setBirthday(new Birthday(birthday));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("birthday is expected to be unique.");
        }
        return this;
    }
```
###### /java/seedu/address/testutil/PersonUtil.java
``` java
        sb.append(PREFIX_BIRTHDAY + person.getBirthday().value + " ");
```
###### /java/seedu/address/testutil/TaskBuilder.java
``` java
package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Desc;
import seedu.address.model.task.Header;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_HEADER = "Homework";
    public static final String DEFAULT_DESC = "Questions 1 and 2";
    public static final String DEFAULT_DEADLINE = "23/12/2017";

    private Task task;

    public TaskBuilder() {
        try {
            Header defaultHeader = new Header(DEFAULT_HEADER);
            Desc defaultDesc = new Desc(DEFAULT_DESC);
            Deadline defaultDeadline = new Deadline(DEFAULT_DEADLINE);

            this.task = new Task(defaultHeader, defaultDesc, defaultDeadline);

        } catch (IllegalValueException ive) {
            throw new AssertionError("Default task's values are invalid.");
        }
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(ReadOnlyTask taskToCopy) {
        this.task = new Task(taskToCopy);
    }

    /**
     * Sets the {@code Header} of the {@code Task} that we are building.
     */
    public TaskBuilder withHeader(String header) {
        try {
            this.task.setHeader(new Header(header));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("header is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Desc} of the {@code Task} that we are building.
     */
    public TaskBuilder withDesc(String desc) {
        try {
            this.task.setDesc(new Desc(desc));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("desc are expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code Task} that we are building.
     */
    public TaskBuilder withDeadline(String deadline) {
        try {
            this.task.setDeadline(new Deadline(deadline));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("deadline is expected to be unique.");
        }
        return this;
    }

    public Task build() {
        return this.task;
    }

}
```
###### /java/seedu/address/testutil/TaskUtil.java
``` java
package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEADER;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.model.task.ReadOnlyTask;

/**
 * A utility class for Person.
 */
public class TaskUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddTaskCommand(ReadOnlyTask task) {
        return AddTaskCommand.COMMAND_WORD + " " + getTaskDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getTaskDetails(ReadOnlyTask task) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_HEADER + task.getHeader().value + " ");
        sb.append(PREFIX_DESC + task.getDesc().value + " ");
        sb.append(PREFIX_DEADLINE + task.getDeadline().value + " ");
        return sb.toString();
    }
}
```
###### /java/seedu/address/testutil/TypicalIndexes.java
``` java
    public static final Index INDEX_FIRST_TASK = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_TASK = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_TASK = Index.fromOneBased(3);
}
```
###### /java/seedu/address/testutil/TypicalTasks.java
``` java
package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_HOMEWORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_HOMEWORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEADER_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEADER_HOMEWORK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.exceptions.DuplicateTaskException;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final ReadOnlyTask PRESENTATION = new TaskBuilder().withHeader("Prepare for presentation")
            .withDesc("Complete slides and script").withDeadline("31/12/2017").build();
    public static final ReadOnlyTask CODE = new TaskBuilder().withHeader("CS2103T")
            .withDesc("Complete testing").withDeadline("12/11/2017").build();

    // Manually added
    public static final ReadOnlyTask MILK = new TaskBuilder().withHeader("Buy milk")
            .withDesc("From NTUC").withDeadline("25/12/2017").build();
    public static final ReadOnlyTask EMAIL = new TaskBuilder().withHeader("Send email")
            .withDesc("Regarding interview").withDeadline("01/11/2017").build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final ReadOnlyTask HOMEWORK = new TaskBuilder().withHeader(VALID_HEADER_HOMEWORK)
            .withDesc(VALID_DESC_HOMEWORK).withDeadline(VALID_DEADLINE_HOMEWORK).build();
    public static final ReadOnlyTask ASSIGNMENT = new TaskBuilder().withHeader(VALID_HEADER_ASSIGNMENT)
            .withDesc(VALID_DESC_ASSIGNMENT).withDeadline(VALID_DEADLINE_ASSIGNMENT).build();

    private TypicalTasks() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical tasks.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyTask task : getTypicalTasks()) {
            try {
                ab.addTask(task);
            } catch (DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    public static List<ReadOnlyTask> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(PRESENTATION, CODE, MILK, EMAIL, HOMEWORK, ASSIGNMENT));
    }
}
```
###### /java/seedu/address/ui/PersonCardTest.java
``` java
            personWithTags.setBirthday(ALICE.getBirthday());
```
###### /java/seedu/address/ui/TaskCardTest.java
``` java
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalTasks.CODE;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysTask;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class TaskCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Task task = new TaskBuilder().build();
        TaskCard taskCard = new TaskCard(task, 1);
        uiPartRule.setUiPart(taskCard);
        assertCardDisplay(taskCard, task, 1);

        // changes made to Task reflects on card
        guiRobot.interact(() -> {
            task.setHeader(CODE.getHeader());
            task.setDesc(CODE.getDesc());
            task.setDeadline(CODE.getDeadline());
        });
        assertCardDisplay(taskCard, task, 1);
    }

    @Test
    public void equals() {
        Task task = new TaskBuilder().build();
        TaskCard taskCard = new TaskCard(task, 0);

        // same task, same index -> returns true
        TaskCard copy = new TaskCard(task, 0);
        assertTrue(taskCard.equals(copy));

        // same object -> returns true
        assertTrue(taskCard.equals(taskCard));

        // null -> returns false
        assertFalse(taskCard.equals(null));

        // different types -> returns false
        assertFalse(taskCard.equals(0));

        // different tasks, same index -> returns false
        Task differentTask = new TaskBuilder().withHeader("differentHeader").build();
        assertFalse(taskCard.equals(new TaskCard(differentTask, 0)));

        // same task, different index -> returns false
        assertFalse(taskCard.equals(new TaskCard(task, 1)));
    }

    /**
     * Asserts that {@code taskCard} displays the details of {@code expectedCard} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(TaskCard taskCard, ReadOnlyTask expectedTask, int expectedId) {
        guiRobot.pauseForHuman();

        TaskCardHandle taskCardHandle = new TaskCardHandle(taskCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", taskCardHandle.getId());

        // verify person details are displayed correctly
        assertCardDisplaysTask(expectedTask, taskCardHandle);
    }
}
```
###### /java/seedu/address/ui/TaskListPanelTest.java
``` java
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalTasks.getTypicalTasks;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysTask;
import static seedu.address.ui.testutil.GuiTestAssert.assertTaskCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import guitests.guihandles.TaskListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.task.ReadOnlyTask;

public class TaskListPanelTest extends GuiUnitTest {
    private static final ObservableList<ReadOnlyTask> TYPICAL_TASKS =
            FXCollections.observableList(getTypicalTasks());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_TASK);

    private TaskListPanelHandle taskListPanelHandle;

    @Before
    public void setUp() {
        TaskListPanel taskListPanel = new TaskListPanel(TYPICAL_TASKS);
        uiPartRule.setUiPart(taskListPanel);

        taskListPanelHandle = new TaskListPanelHandle(getChildNode(taskListPanel.getRoot(),
                TaskListPanelHandle.TASK_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_TASKS.size(); i++) {
            taskListPanelHandle.navigateToCard(TYPICAL_TASKS.get(i));
            ReadOnlyTask expectedTask = TYPICAL_TASKS.get(i);
            TaskCardHandle actualCard = taskListPanelHandle.getTaskCardHandle(i);

            assertCardDisplaysTask(expectedTask, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        TaskCardHandle expectedCard = taskListPanelHandle.getTaskCardHandle(INDEX_SECOND_TASK.getZeroBased());
        TaskCardHandle selectedCard = taskListPanelHandle.getHandleToSelectedCard();
        assertTaskCardEquals(expectedCard, selectedCard);
    }
}
```
###### /java/seedu/address/ui/testutil/GuiTestAssert.java
``` java
        assertEquals(expectedCard.getBirthday(), actualCard.getBirthday());
```
###### /java/seedu/address/ui/testutil/GuiTestAssert.java
``` java
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertTaskCardEquals(TaskCardHandle expectedCard, TaskCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getHeader(), actualCard.getHeader());
        assertEquals(expectedCard.getDesc(), actualCard.getDesc());
        assertEquals(expectedCard.getDeadline(), actualCard.getDeadline());
    }
```
###### /java/seedu/address/ui/testutil/GuiTestAssert.java
``` java
        assertEquals(expectedPerson.getBirthday().value, actualCard.getBirthday());
```
###### /java/seedu/address/ui/testutil/GuiTestAssert.java
``` java
    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedTask}.
     */
    public static void assertCardDisplaysTask(ReadOnlyTask expectedTask, TaskCardHandle actualCard) {
        assertEquals(expectedTask.getHeader().value, actualCard.getHeader());
        assertEquals(expectedTask.getDesc().value, actualCard.getDesc());
        assertEquals(expectedTask.getDeadline().value, actualCard.getDeadline());
    }
```
###### /java/seedu/address/ui/testutil/GuiTestAssert.java
``` java
    /**
     * Asserts that the list in {@code taskListPanelHandle} displays the details of {@code tasks} correctly and
     * in the correct order.
     */
    public static void assertTaskListMatching(TaskListPanelHandle taskListPanelHandle, ReadOnlyTask... tasks) {
        for (int i = 0; i < tasks.length; i++) {
            assertCardDisplaysTask(tasks[i], taskListPanelHandle.getTaskCardHandle(i));
        }
    }
```
###### /java/systemtests/AddCommandSystemTest.java
``` java
        /* Case: add a person with all fields same as another person in the address book except birthday -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_BOB)
                .withFacebookAddress(VALID_FACEBOOKADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + FACEBOOK_ADDRESS_DESC_AMY + BIRTHDAY_DESC_BOB + TAG_DESC_FRIEND;

        assertCommandSuccess(command, toAdd);
```
###### /java/systemtests/AddCommandSystemTest.java
``` java
        /* Case: invalid birthday -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + INVALID_BIRTHDAY_DESC;
        assertCommandFailure(command, Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);
```
###### /java/systemtests/EditCommandSystemTest.java
``` java
        /* Case: invalid birthday -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_BIRTHDAY_DESC,
                Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);
```
###### /java/systemtests/FindCommandSystemTest.java
``` java
        /* Case: find birthday of person in address book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + DANIEL.getBirthday().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
```
###### /java/systemtests/SendEmailCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BODY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_SUBJECT_DESC;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SendEmailCommand;
import seedu.address.model.Model;

public class SendEmailCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_SEND_EMAIL_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SendEmailCommand.MESSAGE_USAGE);
    private String command;

    @Test
    public void send() {

        /* ----------------------- Perform send email operation while a filtered list is being shown --------------- */

        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        command = SendEmailCommand.COMMAND_WORD + " " + invalidIndex + " " + VALID_EMAIL_SUBJECT_DESC
                + " " + VALID_EMAIL_BODY_DESC;
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* --------------------------------- Performing invalid send operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = SendEmailCommand.COMMAND_WORD + " 0" + " " + VALID_EMAIL_SUBJECT_DESC + " " + VALID_EMAIL_BODY_DESC;
        assertCommandFailure(command, MESSAGE_INVALID_INDEX);

        /* Case: invalid index (-1) -> rejected */
        command = SendEmailCommand.COMMAND_WORD + " -1" + " " + VALID_EMAIL_SUBJECT_DESC + " " + VALID_EMAIL_BODY_DESC;
        assertCommandFailure(command, MESSAGE_INVALID_INDEX);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getPersonList().size() + 1);
        command = SendEmailCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased()
                + " " + VALID_EMAIL_SUBJECT_DESC + " " + VALID_EMAIL_BODY_DESC;
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        command = SendEmailCommand.COMMAND_WORD + " " + "abc"
                + " " + VALID_EMAIL_SUBJECT_DESC + " " + VALID_EMAIL_BODY_DESC;
        assertCommandFailure(command, MESSAGE_INVALID_INDEX);

        /* Case: invalid arguments (extra argument) -> rejected */
        command = SendEmailCommand.COMMAND_WORD + " " + "1 abc"
                + " " + VALID_EMAIL_SUBJECT_DESC + " " + VALID_EMAIL_BODY_DESC;
        assertCommandFailure(command, MESSAGE_INVALID_INDEX);

        /* Case: mixed case command word -> rejected */
        command = "sEnD" + " " + "1" + " " + VALID_EMAIL_SUBJECT_DESC + " " + VALID_EMAIL_BODY_DESC;
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card remains unchanged.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
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
