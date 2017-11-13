# ahmadalkaff
###### /java/guitests/guihandles/PersonCardHandle.java
``` java
        Region phonesContainer = getChildNode(PHONES_FIELD_ID);
        this.phoneLabels = phonesContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());

        Region emailsContainer = getChildNode(EMAILS_FIELD_ID);
        this.emailLabels = emailsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
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
    private static final String APPOINTMENT_FIELD_ID = "#appointment";
    private static final String DATE_FIELD_ID = "#date";
    private static final String START_TIME_FIELD_ID = "#startTime";

    private final Label appointmentLabel;
    private final Label dateLabel;
    private final Label startTimeLabel;

    public TaskCardHandle(Node cardNode) {
        super(cardNode);

        this.appointmentLabel = getChildNode(APPOINTMENT_FIELD_ID);
        this.dateLabel = getChildNode(DATE_FIELD_ID);
        this.startTimeLabel = getChildNode(START_TIME_FIELD_ID);
    }

    public String getAppointment() {
        return appointmentLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText();
    }

    public String getStartTime() {
        return startTimeLabel.getText();
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
###### /java/seedu/address/logic/commands/AddCommandTest.java
``` java
        @Override
        public void sortPersonListByName() {
            fail("This method should not be called.");
        }
```
###### /java/seedu/address/logic/commands/DeleteTaskCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstTaskOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalTasks.getTypicalAddressBookTasks;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.ReadOnlyTask;


/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteTaskCommand}.
 */
public class DeleteTaskCommandTest {

    private Model model = new ModelManager(getTypicalAddressBookTasks(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredTaskList_success() throws Exception {
        ReadOnlyTask taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        DeleteTaskCommand deleteTaskCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTask(taskToDelete);

        assertCommandSuccess(deleteTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredTaskList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeleteTaskCommand deleteTaskCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredTaskList_success() throws Exception {
        showFirstTaskOnly(model);

        ReadOnlyTask taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        DeleteTaskCommand deleteTaskCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTask(taskToDelete);
        showNoTask(expectedModel);

        assertCommandSuccess(deleteTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstTaskOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getTaskList().size());

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
     * Updates {@code model}'s filtered list to show no tasks.
     */
    private void showNoTask(Model model) {
        model.updateFilteredTaskList(p -> false);

        assert model.getFilteredTaskList().isEmpty();
    }

}
```
###### /java/seedu/address/logic/commands/EditTaskCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_INTERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstTaskOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalTasks.getTypicalAddressBookTasks;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.testutil.EditTaskDescriptorBuilder;
import seedu.address.testutil.TaskBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditTaskCommand.
 */
public class EditTaskCommandTest {

    private Model model = new ModelManager(getTypicalAddressBookTasks(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredTaskList_success() throws Exception {
        Task editedTask = new TaskBuilder().build();
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        EditTaskCommand editTaskCommand = prepareCommand(INDEX_FIRST_TASK, descriptor);

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateTask(model.getFilteredTaskList().get(0), editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredTaskList_success() throws Exception {
        Index indexLastTask = Index.fromOneBased(model.getFilteredTaskList().size());
        ReadOnlyTask lastTask = model.getFilteredTaskList().get(indexLastTask.getZeroBased());

        TaskBuilder taskInList = new TaskBuilder(lastTask);
        Task editedTask = taskInList.withAppointment(VALID_APPOINTMENT_EVENT).withDate(VALID_DATE_EVENT)
                .withStartTime(VALID_START_TIME_EVENT).build();

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withAppointment(VALID_APPOINTMENT_EVENT)
                .withDate(VALID_DATE_EVENT).withStartTime(VALID_START_TIME_EVENT).build();
        EditTaskCommand editTaskCommand = prepareCommand(indexLastTask, descriptor);

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateTask(lastTask, editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredTaskList_success() {
        EditTaskCommand editTaskCommand = prepareCommand(INDEX_FIRST_TASK, new EditTaskDescriptor());
        ReadOnlyTask editedTask = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredTaskList_success() throws Exception {
        showFirstTaskOnly(model);

        ReadOnlyTask taskInFilteredList = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task editedTask = new TaskBuilder(taskInFilteredList).withAppointment(VALID_APPOINTMENT_EVENT).build();
        EditTaskCommand editTaskCommand = prepareCommand(INDEX_FIRST_TASK,
                new EditTaskDescriptorBuilder().withAppointment(VALID_APPOINTMENT_EVENT).build());

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateTask(model.getFilteredTaskList().get(0), editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTaskIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withAppointment(VALID_APPOINTMENT_EVENT).build();
        EditTaskCommand editTaskCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered task list where index is larger than size of filtered task list,
     * but smaller than size of ContactHub
     */
    @Test
    public void execute_invalidTaskIndexFilteredList_failure() {
        showFirstTaskOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getTaskList().size());

        EditTaskCommand editTaskCommand = prepareCommand(outOfBoundIndex,
                new EditTaskDescriptorBuilder().withAppointment(VALID_APPOINTMENT_EVENT).build());

        assertCommandFailure(editTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditTaskCommand standardCommand = new EditTaskCommand(INDEX_FIRST_TASK, DESC_MEETING);

        // same values -> returns true
        EditTaskDescriptor copyDescriptor = new EditTaskDescriptor(DESC_MEETING);
        EditTaskCommand commandWithSameValues = new EditTaskCommand(INDEX_FIRST_TASK, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_SECOND_TASK, DESC_MEETING)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_FIRST_TASK, DESC_INTERVIEW)));
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
###### /java/seedu/address/logic/commands/ListModuleCommandTest.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_GER1000;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.BOB_EDITED;
import static seedu.address.testutil.TypicalPersons.CARRIE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookPersons;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

public class ListModuleCommandTest {
    private Model model;
    private Model expectedModel;
    private ListModuleCommand listModuleCommand;
    private ArrayList<Module> moduleList;
    private ArrayList<String> tempList;
    private String expectedMessage;

    public final String listModuleMessage = listModuleCommand.MESSAGE_SUCCESS + "\n" + "Module names: ";

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBookPersons(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        moduleList = new ArrayList<>();
        tempList = new ArrayList<>();
        listModuleCommand = new ListModuleCommand();
        listModuleCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        for (ReadOnlyPerson person : model.getAddressBook().getPersonList()) {
            for (Module mod : person.getModules()) {
                if (!moduleList.contains(mod)) {
                    moduleList.add(mod);
                }
            }
        }
        for (Module m : moduleList) {
            tempList.add(m.moduleName);
        }
        Collections.sort(tempList);

        expectedMessage = listModuleMessage + tempList.toString().replace(",", "]").replace(" ", " [");

    }

    @Test
    public void execute_showsCorrectModuleList() {
        assertCommandSuccess(listModuleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addNewPersonWithNewModule_showsCorrectModuleList()
            throws DuplicatePersonException {
        model.addPerson(AMY);
        expectedModel.addPerson(AMY);
        tempList.add(VALID_MODULE_CS2101);
        Collections.sort(tempList);
        expectedMessage = listModuleMessage + tempList.toString().replace(",", "]").replace(" ", " [");
        assertCommandSuccess(listModuleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addNewPersonWithNoModule_showsCorrectModList()
            throws DuplicatePersonException {
        model.addPerson(CARRIE);
        expectedModel.addPerson(CARRIE);
        expectedMessage = listModuleMessage + tempList.toString().replace(",", "]").replace(" ", " [");
        assertCommandSuccess(listModuleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editPersonWithPresentModule_showsCorrectModuleList()
            throws DuplicatePersonException, PersonNotFoundException {
        model.addPerson(BOB);
        expectedModel.addPerson(BOB);
        tempList.add(VALID_MODULE_GER1000);
        tempList.add(VALID_MODULE_CS2101);

        model.updatePerson(BOB, BOB_EDITED);
        expectedModel.updatePerson(BOB, BOB_EDITED);
        tempList.remove(VALID_MODULE_GER1000);
        tempList.add(VALID_MODULE_CS2103);
        Collections.sort(tempList);
        expectedMessage = listModuleMessage + tempList.toString().replace(",", "]").replace(" ", " [");
        assertCommandSuccess(listModuleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deletePersonWithUniqueModule_showsCorrectModuleList()
            throws DuplicatePersonException, PersonNotFoundException {
        model.addPerson(AMY);
        model.addPerson(BOB);
        model.addPerson(CARRIE);
        expectedModel.addPerson(AMY);
        expectedModel.addPerson(BOB);
        expectedModel.addPerson(CARRIE);
        tempList.add(VALID_MODULE_CS2101);
        tempList.add(VALID_MODULE_GER1000);

        model.deletePerson(BOB);
        expectedModel.deletePerson(BOB);
        tempList.remove(VALID_MODULE_GER1000);
        Collections.sort(tempList);
        expectedMessage = listModuleMessage + tempList.toString().replace(",", "]").replace(" ", " [");
        assertCommandSuccess(listModuleCommand, model, expectedMessage, expectedModel);
    }
}
```
###### /java/seedu/address/logic/commands/SortCommandTest.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookPersons;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SortCommandTest {
    private Model model;
    private Model expectedModel;
    private SortCommand sortCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBookPersons(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        sortCommand = new SortCommand();
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_sortIsNotFiltered_showsSameList() {
        assertCommandSuccess(sortCommand, model, sortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_sortIsFiltered_showsEverything() {
        showFirstPersonOnly(model);
        assertCommandSuccess(sortCommand, model, sortCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_findModule() throws Exception {
        List<String> keywords = Arrays.asList("CS2101", "CS2103");
        FindModuleCommand command = (FindModuleCommand) parser.parseCommand(
                FindModuleCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        FindModuleCommand commandAlias = (FindModuleCommand) parser.parseCommand(
                FindModuleCommand.COMMAND_ALIAS + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindModuleCommand(new ModuleContainsKeywordPredicate(keywords)), command);
        assertEquals(new FindModuleCommand(new ModuleContainsKeywordPredicate(keywords)), commandAlias);
    }
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_listModule() throws Exception {
        assertTrue(parser.parseCommand(ListModuleCommand.COMMAND_WORD) instanceof ListModuleCommand);
        assertTrue(parser.parseCommand(ListModuleCommand.COMMAND_ALIAS) instanceof ListModuleCommand);
        assertTrue(parser.parseCommand(ListModuleCommand.COMMAND_WORD + " 3") instanceof ListModuleCommand);
        assertTrue(parser.parseCommand(ListModuleCommand.COMMAND_ALIAS + " 3") instanceof ListModuleCommand);
    }

    @Test
    public void parseCommand_sort() throws Exception {
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD) instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_ALIAS) instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " 3") instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_ALIAS + " 3") instanceof SortCommand);
    }
```
###### /java/seedu/address/logic/parser/AddTaskCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.APPOINTMENT_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_APPOINTMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_MEETING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.model.task.Appointment;
import seedu.address.model.task.Date;
import seedu.address.model.task.StartTime;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class AddTaskCommandParserTest {
    private AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder().withAppointment(VALID_APPOINTMENT_MEETING)
                .withDate(VALID_DATE_MEETING).withStartTime(VALID_START_TIME_MEETING).build();

        assertParseSuccess(parser, AddTaskCommand.COMMAND_WORD + APPOINTMENT_DESC_MEETING
                + DATE_DESC_MEETING + START_TIME_DESC_MEETING, new AddTaskCommand(expectedTask));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE);

        // missing appointment prefix
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + VALID_APPOINTMENT_MEETING + DATE_DESC_MEETING
                + START_TIME_DESC_MEETING, expectedMessage);

        // missing date prefix
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + APPOINTMENT_DESC_MEETING + VALID_DATE_MEETING
                + START_TIME_DESC_MEETING, expectedMessage);

        // missing start time prefix
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + APPOINTMENT_DESC_MEETING + DATE_DESC_MEETING
                + VALID_START_TIME_MEETING, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + VALID_APPOINTMENT_MEETING
                + VALID_DATE_MEETING + VALID_START_TIME_MEETING, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid appointment
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + INVALID_APPOINTMENT_DESC + DATE_DESC_MEETING
                + START_TIME_DESC_MEETING, Appointment.MESSAGE_APPOINTMENT_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + APPOINTMENT_DESC_MEETING + INVALID_DATE_DESC
                + START_TIME_DESC_MEETING, Date.MESSAGE_DATE_CONSTRAINTS);

        // invalid start time
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + APPOINTMENT_DESC_MEETING + DATE_DESC_MEETING
                + INVALID_START_TIME_DESC, StartTime.MESSAGE_TIME_CONSTRAINTS);

        // two invalid values, only invalid start time reported
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + APPOINTMENT_DESC_MEETING + INVALID_DATE_DESC
                + DATE_DESC_MEETING + INVALID_START_TIME_DESC, StartTime.MESSAGE_TIME_CONSTRAINTS);

        // three invalid values, only invalid start time reported
        assertParseFailure(parser, AddTaskCommand.COMMAND_WORD + INVALID_APPOINTMENT_DESC
                + APPOINTMENT_DESC_MEETING + INVALID_DATE_DESC + DATE_DESC_MEETING
                + INVALID_START_TIME_DESC, StartTime.MESSAGE_TIME_CONSTRAINTS);
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
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/EditTaskCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.APPOINTMENT_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_APPOINTMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_MEETING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.model.task.Appointment;
import seedu.address.model.task.Date;
import seedu.address.model.task.StartTime;
import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditTaskCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE);

    private EditTaskCommandParser parser = new EditTaskCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_APPOINTMENT_MEETING, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditTaskCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + APPOINTMENT_DESC_MEETING, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + APPOINTMENT_DESC_MEETING, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_APPOINTMENT_DESC,
                Appointment.MESSAGE_APPOINTMENT_CONSTRAINTS); // invalid appointment
        assertParseFailure(parser, "1" + INVALID_DATE_DESC,
                Date.MESSAGE_DATE_CONSTRAINTS); // invalid date
        assertParseFailure(parser, "1" + INVALID_START_TIME_DESC,
                StartTime.MESSAGE_TIME_CONSTRAINTS); // invalid start time

        // invalid date followed by valid start time
        assertParseFailure(parser, "1" + INVALID_DATE_DESC + START_TIME_DESC_MEETING,
                Date.MESSAGE_DATE_CONSTRAINTS);

        // valid date followed by invalid date. The test case for invalid date followed by valid date
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + DATE_DESC_MEETING + INVALID_DATE_DESC, Date.MESSAGE_DATE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_APPOINTMENT_DESC + INVALID_DATE_DESC
                + VALID_APPOINTMENT_MEETING + VALID_DATE_MEETING, Appointment.MESSAGE_APPOINTMENT_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_TASK;
        String userInput = targetIndex.getOneBased() + DATE_DESC_MEETING + START_TIME_DESC_MEETING
                + APPOINTMENT_DESC_MEETING;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withAppointment(VALID_APPOINTMENT_MEETING).withDate(VALID_DATE_MEETING)
                .withStartTime(VALID_START_TIME_MEETING).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + DATE_DESC_MEETING + START_TIME_DESC_MEETING;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withDate(VALID_DATE_MEETING)
                .withStartTime(VALID_START_TIME_MEETING).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // appointment
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + APPOINTMENT_DESC_MEETING;
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withAppointment(VALID_APPOINTMENT_MEETING).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = targetIndex.getOneBased() + DATE_DESC_MEETING;
        descriptor = new EditTaskDescriptorBuilder().withDate(VALID_DATE_MEETING).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // start time
        userInput = targetIndex.getOneBased() + START_TIME_DESC_MEETING;
        descriptor = new EditTaskDescriptorBuilder().withStartTime(VALID_START_TIME_MEETING).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_invalidValueFollowedByValidValue_failure() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_TASK;
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withDate(VALID_DATE_MEETING).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        String userInput = targetIndex.getOneBased() + INVALID_DATE_DESC + DATE_DESC_MEETING;
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        descriptor = new EditTaskDescriptorBuilder().withDate(VALID_DATE_MEETING)
                .withStartTime(VALID_START_TIME_MEETING).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        userInput = targetIndex.getOneBased() + INVALID_DATE_DESC + DATE_DESC_MEETING + START_TIME_DESC_MEETING;
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
```
###### /java/seedu/address/logic/parser/ParserUtilTest.java
``` java
    @Test
    public void parsePhones_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parsePhones(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parsePhones_collectionWithValidPhones_returnsPhoneSet() throws Exception {
        Set<Phone> actualPhoneSet = ParserUtil.parsePhones(Arrays.asList(VALID_PHONE_1, VALID_PHONE_2));
        Set<Phone> expectedPhoneSet = new HashSet<>(Arrays.asList(new Phone(VALID_PHONE_1), new Phone(VALID_PHONE_2)));

        assertEquals(expectedPhoneSet, actualPhoneSet);
    }
```
###### /java/seedu/address/logic/parser/ParserUtilTest.java
``` java
    @Test
    public void parseEmails_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseEmails(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseEmails_collectionWithValidEmails_returnsEmailSet() throws Exception {
        Set<Email> actualEmailSet = ParserUtil.parseEmails(Arrays.asList(VALID_EMAIL_1, VALID_EMAIL_2));
        Set<Email> expectedEmailSet = new HashSet<>(Arrays.asList(new Email(VALID_EMAIL_1), new Email(VALID_EMAIL_2)));

        assertEquals(expectedEmailSet, actualEmailSet);
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
###### /java/seedu/address/storage/XmlAddressBookStorageTest.java
``` java
    @Test
    public void getTaskList_modifyList_throwsUnsupportedOperationException() {
        XmlSerializableAddressBook addressBook = new XmlSerializableAddressBook();
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getTaskList().remove(0);
    }
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
            Set<Phone> defaultPhones = SampleDataUtil.getPhoneSet(DEFAULT_PHONES);
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
            Set<Email> defaultEmails = SampleDataUtil.getEmailSet(DEFAULT_EMAILS);
```
###### /java/seedu/address/testutil/PersonUtil.java
``` java
        person.getPhones().stream().forEach(
            s -> sb.append(PREFIX_PHONE + s.value + " ")
        );

```
###### /java/seedu/address/testutil/PersonUtil.java
``` java
        person.getEmails().stream().forEach(
            s -> sb.append(PREFIX_EMAIL + s.value + " ")
        );
```
###### /java/seedu/address/testutil/TypicalPersons.java
``` java
    /**
     * Returns an {@code AddressBook} with all the typical persons and typical tasks.
     */
    public static AddressBook getTypicalAddressBookData() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyPerson person : getTypicalPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }

        for (ReadOnlyTask task : getTypicalTasks()) {
            ab.addTask(task);
        }

        return ab;
    }
```
###### /java/systemtests/DeleteTaskCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.address.logic.commands.DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS;
import static seedu.address.testutil.TestUtil.getLastTaskIndex;
import static seedu.address.testutil.TestUtil.getMidTaskIndex;
import static seedu.address.testutil.TestUtil.getTask;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteTaskCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.exceptions.TaskNotFoundException;

public class DeleteTaskCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_TASK_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* -------------- Performing delete task operation while an unfiltered list is being shown ---------------- */

        /* Case: delete the first task in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteTaskCommand.COMMAND_WORD + "      "
                + INDEX_FIRST_TASK.getOneBased() + "       ";
        ReadOnlyTask deletedTask = removeTask(expectedModel, INDEX_FIRST_TASK);
        String expectedResultMessage = String.format(MESSAGE_DELETE_TASK_SUCCESS, deletedTask);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last task in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastTaskIndex = getLastTaskIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastTaskIndex);

        /* Case: undo deleting the last task in the list -> last task restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last task in the list -> last task deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeTask(modelBeforeDeletingLast, lastTaskIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle task in the list -> deleted */
        Index middleTaskIndex = getMidTaskIndex(getModel());
        assertCommandSuccess(middleTaskIndex);


        /* ---------------------------- Performing invalid delete task operation ------------------------------- */

        /* Case: invalid index (0) -> rejected */
        command = DeleteTaskCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_TASK_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteTaskCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_TASK_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getTaskList().size() + 1);
        command = DeleteTaskCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteTaskCommand.COMMAND_WORD + " abc",
                MESSAGE_INVALID_DELETE_TASK_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteTaskCommand.COMMAND_WORD + " 1 abc",
                MESSAGE_INVALID_DELETE_TASK_COMMAND_FORMAT);
    }

    /**
     * Removes the {@code ReadOnlyTask} at the specified {@code index} in {@code model}'s address book.
     * @return the removed task
     */
    private ReadOnlyTask removeTask(Model model, Index index) {
        ReadOnlyTask targetTask = getTask(model, index);
        try {
            model.deleteTask(targetTask);
        } catch (TaskNotFoundException tnfe) {
            throw new AssertionError("targetTask is retrieved from model.");
        }
        return targetTask;
    }

    /**
     * Deletes the task at {@code toDelete} by creating a default {@code DeleteTaskCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteTaskCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        ReadOnlyTask deletedTask = removeTask(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_TASK_SUCCESS, deletedTask);

        assertCommandSuccess(
                DeleteTaskCommand.COMMAND_WORD + " "
                        + toDelete.getOneBased(), expectedModel, expectedResultMessage);
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
     * @see DeleteTaskCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        // assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);

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
###### /java/systemtests/EditTaskCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.APPOINTMENT_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_APPOINTMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_MEETING;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalTasks.MEETING;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.task.Appointment;
import seedu.address.model.task.Date;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.StartTime;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.TaskNotFoundException;
import seedu.address.testutil.TaskBuilder;

public class EditTaskCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void edit() throws Exception {
        Model model = getModel();

        /* --------------- Performing edit task operation while an unfiltered list is being shown ------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_TASK;
        String command = " " + EditTaskCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
                + APPOINTMENT_DESC_MEETING + "  " + DATE_DESC_MEETING + "  " + START_TIME_DESC_MEETING + " ";
        Task editedTask = new TaskBuilder().withAppointment(VALID_APPOINTMENT_MEETING).withDate(VALID_DATE_MEETING)
                .withStartTime(VALID_START_TIME_MEETING).build();
        assertCommandSuccess(command, index, editedTask);

        /* Case: undo editing the last task in the list -> last task restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last task in the list -> last task edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateTask(
                getModel().getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased()), editedTask);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a task with new values same as existing values -> edited */
        command = EditTaskCommand.COMMAND_WORD + " " + index.getOneBased() + APPOINTMENT_DESC_MEETING
                + DATE_DESC_MEETING + START_TIME_DESC_MEETING;
        assertCommandSuccess(command, index, MEETING);
```
###### /java/systemtests/EditTaskCommandSystemTest.java
``` java
        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " 0" + APPOINTMENT_DESC_MEETING,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " -1" + APPOINTMENT_DESC_MEETING,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredTaskList().size() + 1;
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " " + invalidIndex + APPOINTMENT_DESC_MEETING,
                Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + APPOINTMENT_DESC_MEETING,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased(),
                EditTaskCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid appointment -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased()
                        + INVALID_APPOINTMENT_DESC,
                Appointment.MESSAGE_APPOINTMENT_CONSTRAINTS);

        /* Case: invalid date -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased()
                        + INVALID_DATE_DESC,
                Date.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid start time -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased()
                        + INVALID_START_TIME_DESC,
                StartTime.MESSAGE_TIME_CONSTRAINTS);

    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, ReadOnlyTask, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditTaskCommandSystemTest#assertCommandSuccess(String, Index, ReadOnlyTask, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, ReadOnlyTask editedTask) {
        assertCommandSuccess(command, toEdit, editedTask, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditTaskCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the task at index {@code toEdit} being
     * updated to values specified {@code editedTask}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditTaskCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, ReadOnlyTask editedTask,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateTask(
                    expectedModel.getFilteredTaskList().get(toEdit.getZeroBased()), editedTask);
            expectedModel.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        } catch (TaskNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedTask isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditTaskCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);

        expectedModel.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        System.out.println(command);
        System.out.println(expectedModel.getFilteredTaskList());
        // assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
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
###### /java/systemtests/ModelHelper.java
``` java
    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredTaskList(Model model, List<ReadOnlyTask> toDisplay) {
        Optional<Predicate<ReadOnlyTask>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatchingTasks).reduce(Predicate::or);
        model.updateFilteredTaskList(predicate.orElse(PREDICATE_MATCHING_NO_TASKS));
    }

    /**
     * @see ModelHelper#setFilteredTaskList(Model, List)
     */
    public static void setFilteredTaskList(Model model, ReadOnlyTask... toDisplay) {
        setFilteredTaskList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code ReadOnlyTask} equals to {@code other}.
     */
    private static Predicate<ReadOnlyTask> getPredicateMatchingTasks(ReadOnlyTask other) {
        return task -> task.equals(other);
    }
```
