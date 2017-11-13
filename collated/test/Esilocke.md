# Esilocke
###### \java\seedu\address\logic\commands\AddTaskCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.TaskBuilder;

public class AddTaskCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_taskAcceptedByModel_addSuccessful() throws Exception {
        AddTaskCommandTest.ModelStubAcceptingTaskAdded modelStub = new AddTaskCommandTest.ModelStubAcceptingTaskAdded();
        Task validTask = new TaskBuilder().build();

        CommandResult commandResult = prepareCommand(validTask, modelStub).execute();

        assertEquals(String.format(AddTaskCommand.MESSAGE_SUCCESS, validTask), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() throws Exception {
        ModelStub modelStub = new AddTaskCommandTest.ModelStubAlwaysThrowingDuplicateException();
        Task validTask = new TaskBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddTaskCommand.MESSAGE_DUPLICATE_TASK);

        prepareCommand(validTask, modelStub).execute();
    }

    @Test
    public void equals() {
        Task paper = new TaskBuilder().withTaskName("Paper").build();
        Task pencil = new TaskBuilder().withTaskName("Pencil").build();

        AddTaskCommand addPaperCommand = new AddTaskCommand(paper);
        AddTaskCommand addPencilCommand = new AddTaskCommand(pencil);

        // same object -> returns true
        assertTrue(addPaperCommand.equals(addPaperCommand));

        // same values -> returns true
        AddTaskCommand addPaperCommandCopy = new AddTaskCommand(paper);
        assertTrue(addPaperCommand.equals(addPaperCommandCopy));

        // different types -> returns false
        assertFalse(addPaperCommand.equals(""));

        // null -> returns false
        assertFalse(addPaperCommand.equals(null));

        // different task -> returns false
        assertFalse(addPaperCommand.equals(addPencilCommand));
    }

    /**
     * Generates a new AddTaskCommand with the details of the given task.
     */
    private AddTaskCommand prepareCommand(Task task, Model model) {
        AddTaskCommand command = new AddTaskCommand(task);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A Model stub that always throw a DuplicateTaskException when trying to add a task.
     */
    private class ModelStubAlwaysThrowingDuplicateException extends ModelStub {

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
}
```
###### \java\seedu\address\logic\commands\AssignCommandTest.java
``` java
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.task.ReadOnlyTask;

public class AssignCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_assignOnePerson_success() throws Exception {
        List<Index> toAssign = Arrays.asList(INDEX_FIRST_PERSON);
        ReadOnlyTask assignedTask = model.getFilteredTaskList().get(0);
        ReadOnlyPerson assignedPerson = model.getFilteredPersonList().get(0);
        ArrayList<ReadOnlyPerson> persons = new ArrayList<>();
        persons.add(assignedPerson);

        AssignCommand assignCommand = prepareCommand(toAssign, INDEX_FIRST_TASK);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.assignToTask(persons, assignedTask);
        String expectedMessage = String.format(AssignCommand.MESSAGE_SUCCESS, toAssign.size(),
                assignedTask);
        assertCommandSuccess(assignCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_assignManyPersons_success() throws Exception {
        List<Index> toAssign = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);
        ReadOnlyTask assignedTask = model.getFilteredTaskList().get(0);
        ReadOnlyPerson firstPerson = model.getFilteredPersonList().get(0);
        ReadOnlyPerson secondPerson = model.getFilteredPersonList().get(1);
        ReadOnlyPerson thirdPerson = model.getFilteredPersonList().get(2);
        ArrayList<ReadOnlyPerson> persons = new ArrayList<>();
        persons.add(firstPerson);
        persons.add(secondPerson);
        persons.add(thirdPerson);

        AssignCommand assignCommand = prepareCommand(toAssign, INDEX_FIRST_TASK);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.assignToTask(persons, assignedTask);
        String expectedMessage = String.format(AssignCommand.MESSAGE_SUCCESS, toAssign.size(),
                assignedTask);
        assertCommandSuccess(assignCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_assignDuplicates_success() throws Exception {
        List<Index> toAssign = Arrays.asList(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON, INDEX_FIRST_PERSON);
        ReadOnlyTask assignedTask = model.getFilteredTaskList().get(0);
        ReadOnlyPerson assignedPerson = model.getFilteredPersonList().get(0);
        ArrayList<ReadOnlyPerson> persons = new ArrayList<>();
        persons.add(assignedPerson);

        AssignCommand assignCommand = prepareCommand(toAssign, INDEX_FIRST_TASK);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        String expectedMessage = String.format(AssignCommand.MESSAGE_SUCCESS, 1, assignedTask);
        expectedModel.assignToTask(persons, assignedTask);
        assertCommandSuccess(assignCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() throws Exception {
        Index outOfRangeIndex = Index.fromZeroBased(model.getFilteredPersonList().size());
        List<Index> toAssign = Arrays.asList(INDEX_FIRST_PERSON, outOfRangeIndex, INDEX_SECOND_PERSON);

        AssignCommand assignCommand = prepareCommand(toAssign, INDEX_FIRST_TASK);
        assertCommandFailure(assignCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidTaskIndex_throwsCommandException() throws Exception {
        Index outOfRangeIndex = Index.fromZeroBased(model.getFilteredTaskList().size());
        List<Index> toAssign = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        AssignCommand assignCommand = prepareCommand(toAssign, outOfRangeIndex);
        assertCommandFailure(assignCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_allPersonsAlreadyAssigned_throwsCommandException() throws Exception {
        List<Index> toAssign = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);
        ReadOnlyTask assignedTask = model.getFilteredTaskList().get(0);
        ReadOnlyPerson firstPerson = model.getFilteredPersonList().get(0);
        ReadOnlyPerson secondPerson = model.getFilteredPersonList().get(1);
        ReadOnlyPerson thirdPerson = model.getFilteredPersonList().get(2);
        ArrayList<ReadOnlyPerson> persons = new ArrayList<>();
        persons.add(firstPerson);
        persons.add(secondPerson);
        persons.add(thirdPerson);

        AssignCommand assignCommand = prepareCommand(toAssign, INDEX_FIRST_TASK);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.assignToTask(persons, assignedTask);
        assignCommand.setData(expectedModel, new CommandHistory(), new UndoRedoStack());
        assertCommandFailure(assignCommand, expectedModel, AssignCommand.MESSAGE_NONE_ASSIGNED);
    }

    @Test
    public void equals() {
        ArrayList<Index> assignFirstThree = new ArrayList<>(Arrays.asList(INDEX_FIRST_PERSON,
                INDEX_SECOND_PERSON, INDEX_THIRD_PERSON));
        ArrayList<Index> assignFirstTwo = new ArrayList<>(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
        AssignCommand assignTwoToFirst = new AssignCommand(assignFirstTwo, INDEX_FIRST_TASK);
        AssignCommand assignThreeToFirst = new AssignCommand(assignFirstThree, INDEX_FIRST_TASK);
        AssignCommand assignTwoToSecond = new AssignCommand(assignFirstTwo, INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(assignTwoToFirst.equals(assignTwoToFirst));

        // same values -> returns true
        AssignCommand assignTwoToFirstCopy = new AssignCommand(assignFirstTwo, INDEX_FIRST_TASK);
        assertTrue(assignTwoToFirst.equals(assignTwoToFirstCopy));

        // different types -> returns false
        assertFalse(assignTwoToFirst.equals(1));

        // null -> returns false
        assertFalse(assignTwoToFirst.equals(null));

        // different person/task indexes -> returns false
        assertFalse(assignTwoToFirst.equals(assignThreeToFirst));
        assertFalse(assignTwoToFirst.equals(assignTwoToSecond));
    }
    /**
     * Generates a new AssignCommand with the specified targets.
     */
    private AssignCommand prepareCommand(List<Index> personsToAssign, Index taskIndex) {
        ArrayList<Index> listIndexes = new ArrayList<>(personsToAssign);
        AssignCommand command = new AssignCommand(listIndexes, taskIndex);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\ClearPersonCommandTest.java
``` java
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearPersonCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        assertCommandSuccess(prepareCommand(model), model, ClearPersonCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertCommandSuccess(prepareCommand(model), model, ClearPersonCommand.MESSAGE_SUCCESS, model);
    }

    /**
     * Generates a new {@code ClearPersonCommand} which upon execution, clears the contents in {@code model}.
     */
    private ClearPersonCommand prepareCommand(Model model) {
        ClearPersonCommand command = new ClearPersonCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\ClearTaskCommandTest.java
``` java
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonsAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearTaskCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        assertCommandSuccess(prepareCommand(model), model, ClearTaskCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalPersonsAddressBook(), new UserPrefs());
        // Verify that only the tasks are cleared
        assertCommandSuccess(prepareCommand(model), model, ClearTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * Generates a new {@code ClearTaskCommand} which upon execution, clears the contents in {@code model}.
     */
    private ClearTaskCommand prepareCommand(Model model) {
        ClearTaskCommand command = new ClearTaskCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\DeleteTaskCommandTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstTaskOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalTasks.getTypicalTasksOnlyAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.ReadOnlyTask;

public class DeleteTaskCommandTest {
    private Model model = new ModelManager(getTypicalTasksOnlyAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyTask taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        DeleteTaskCommand deleteTaskCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_SUCCESS, taskToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTask(taskToDelete);

        assertCommandSuccess(deleteTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeleteTaskCommand deleteTaskCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstTaskOnly(model);

        ReadOnlyTask taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        DeleteTaskCommand deleteTaskCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_SUCCESS, taskToDelete);

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
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getTasksList().size());

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

        // different task -> returns false
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
###### \java\seedu\address\logic\commands\DismissCommandTest.java
``` java
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.task.ReadOnlyTask;

public class DismissCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());


    @Test
    public void execute_dismissOnePerson_success() throws Exception {
        List<Index> toDismiss = Arrays.asList(INDEX_FIRST_PERSON);
        ReadOnlyTask dismissedTask = model.getFilteredTaskList().get(5);
        ReadOnlyPerson dismissedPerson = model.getFilteredPersonList().get(0);
        ArrayList<ReadOnlyPerson> persons = new ArrayList<>();
        persons.add(dismissedPerson);

        DismissCommand dismissCommand = prepareCommand(toDismiss, Index.fromZeroBased(5));
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.dismissFromTask(persons, dismissedTask);
        String expectedMessage = String.format(DismissCommand.MESSAGE_SUCCESS, toDismiss.size(),
                dismissedTask);
        assertCommandSuccess(dismissCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_dismissManyPersons_success() throws Exception {
        List<Index> toDismiss = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);
        ReadOnlyTask dismissedTask = model.getFilteredTaskList().get(5);
        ReadOnlyPerson firstPerson = model.getFilteredPersonList().get(0);
        ReadOnlyPerson secondPerson = model.getFilteredPersonList().get(1);
        ReadOnlyPerson thirdPerson = model.getFilteredPersonList().get(2);
        ArrayList<ReadOnlyPerson> persons = new ArrayList<>();
        persons.add(firstPerson);
        persons.add(secondPerson);
        persons.add(thirdPerson);

        DismissCommand dismissCommand = prepareCommand(toDismiss, Index.fromZeroBased(5));
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.dismissFromTask(persons, dismissedTask);
        String expectedMessage = String.format(DismissCommand.MESSAGE_SUCCESS, toDismiss.size(),
                dismissedTask);
        assertCommandSuccess(dismissCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_dismissDuplicates_success() throws Exception {
        List<Index> toDismiss = Arrays.asList(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON, INDEX_FIRST_PERSON);
        ReadOnlyTask dismissedTask = model.getFilteredTaskList().get(5);
        ReadOnlyPerson dismissedPerson = model.getFilteredPersonList().get(0);
        ArrayList<ReadOnlyPerson> persons = new ArrayList<>();
        persons.add(dismissedPerson);

        DismissCommand dismissCommand = prepareCommand(toDismiss, Index.fromZeroBased(5));
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        String expectedMessage = String.format(DismissCommand.MESSAGE_SUCCESS, 1, dismissedTask);
        expectedModel.dismissFromTask(persons, dismissedTask);
        assertCommandSuccess(dismissCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() throws Exception {
        Index outOfRangeIndex = Index.fromZeroBased(model.getFilteredPersonList().size());
        List<Index> toDismiss = Arrays.asList(INDEX_FIRST_PERSON, outOfRangeIndex, INDEX_SECOND_PERSON);

        DismissCommand dismissCommand = prepareCommand(toDismiss, INDEX_FIRST_TASK);
        assertCommandFailure(dismissCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidTaskIndex_throwsCommandException() throws Exception {
        Index outOfRangeIndex = Index.fromZeroBased(model.getFilteredTaskList().size());
        List<Index> toDismiss = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        DismissCommand dismissCommand = prepareCommand(toDismiss, outOfRangeIndex);
        assertCommandFailure(dismissCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }


    @Test
    public void execute_noneDismissed_throwsCommandException() throws Exception {
        List<Index> toDismiss = Arrays.asList(INDEX_FIRST_PERSON);
        ReadOnlyPerson dismissedPerson = model.getFilteredPersonList().get(0);
        ArrayList<ReadOnlyPerson> persons = new ArrayList<>();
        persons.add(dismissedPerson);

        DismissCommand dismissCommand = prepareCommand(toDismiss, INDEX_FIRST_TASK);
        assertCommandFailure(dismissCommand, model, DismissCommand.MESSAGE_NONE_ASSIGNED);
    }

    @Test
    public void equals() {
        ArrayList<Index> dismissFirstThree = new ArrayList<>(Arrays.asList(INDEX_FIRST_PERSON,
                INDEX_SECOND_PERSON, INDEX_THIRD_PERSON));
        ArrayList<Index> dismissFirstTwo = new ArrayList<>(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
        DismissCommand dismissTwoToFirst = new DismissCommand(dismissFirstTwo, INDEX_FIRST_TASK);
        DismissCommand dismissThreeToFirst = new DismissCommand(dismissFirstThree, INDEX_FIRST_TASK);
        DismissCommand dismissTwoToSecond = new DismissCommand(dismissFirstTwo, INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(dismissTwoToFirst.equals(dismissTwoToFirst));

        // same values -> returns true
        DismissCommand dismissTwoToFirstCopy = new DismissCommand(dismissFirstTwo, INDEX_FIRST_TASK);
        assertTrue(dismissTwoToFirst.equals(dismissTwoToFirstCopy));

        // different types -> returns false
        assertFalse(dismissTwoToFirst.equals(1));

        // null -> returns false
        assertFalse(dismissTwoToFirst.equals(null));

        // different person/task indexes -> returns false
        assertFalse(dismissTwoToFirst.equals(dismissThreeToFirst));
        assertFalse(dismissTwoToFirst.equals(dismissTwoToSecond));
    }
    /**
     * Generates a new DismissCommand with the specified targets.
     */
    private DismissCommand prepareCommand(List<Index> personsToDismiss, Index taskIndex) {
        ArrayList<Index> listIndexes = new ArrayList<>(personsToDismiss);
        DismissCommand command = new DismissCommand(listIndexes, taskIndex);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\EditTagCommandTest.java
``` java
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLLEAGUE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.EditTagCommand.MESSAGE_EDIT_TAG_SUCCESS;
import static seedu.address.logic.commands.EditTagCommand.MESSAGE_TAG_NOT_FOUND;
import static seedu.address.testutil.TypicalPersons.getTaglessAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonsAddressBook;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;


public class EditTagCommandTest {
    private Model model = new ModelManager(getTypicalPersonsAddressBook(), new UserPrefs());
    @Test
    public void noTagsPresent_throwsCommandException() throws IllegalValueException {
        Model taglessModel = new ModelManager(getTaglessAddressBook(), new UserPrefs());
        Model blankModel = new ModelManager(new AddressBook(), new UserPrefs());
        String absentTag = "notInAddressBook";
        EditTagCommand noPersonCommand = prepareCommand(VALID_TAG_FRIEND, absentTag, blankModel);
        EditTagCommand noTagsCommand = prepareCommand(VALID_TAG_FRIEND, absentTag, taglessModel);
        EditTagCommand absentTagCommand = prepareCommand(VALID_TAG_FRIEND, absentTag, model);

        // No people are in this address book
        assertCommandFailure(noPersonCommand, taglessModel, MESSAGE_TAG_NOT_FOUND);
        // All persons do not have tags
        assertCommandFailure(noTagsCommand, taglessModel, MESSAGE_TAG_NOT_FOUND);
        // No persons in address book has the required tag
        assertCommandFailure(absentTagCommand, model, MESSAGE_TAG_NOT_FOUND);
    }

    @Test
    public void editTagSubset_success() throws IllegalValueException, PersonNotFoundException {
        AddressBook testBook = prepareAddressBook();
        Model testModel = new ModelManager(testBook, new UserPrefs());
        EditTagCommand tagChangeColleagueToHusband = prepareCommand(VALID_TAG_COLLEAGUE, VALID_TAG_HUSBAND, testModel);
        String expectedMessage = String.format(MESSAGE_EDIT_TAG_SUCCESS, VALID_TAG_COLLEAGUE, VALID_TAG_HUSBAND);
        Tag husbandTag = new Tag(VALID_TAG_HUSBAND);
        Tag colleagueTag = new Tag(VALID_TAG_COLLEAGUE);

        // Attempt to change some Person objects
        Model expectedModel = new ModelManager(testModel.getAddressBook(), new UserPrefs());
        ArrayList<Index> indices = new ArrayList<>();
        indices.add(Index.fromZeroBased(0));
        indices.add(Index.fromZeroBased(2));
        expectedModel.editTag(colleagueTag, husbandTag, indices);
        assertCommandSuccess(tagChangeColleagueToHusband, testModel, expectedMessage, expectedModel);
    }
    @Test
    public void editTagAll_success() throws IllegalValueException, PersonNotFoundException {
        AddressBook testBook = prepareAddressBook();
        Model testModel = new ModelManager(testBook, new UserPrefs());
        EditTagCommand tagChangeFriendToHusband = prepareCommand(VALID_TAG_FRIEND, VALID_TAG_HUSBAND, testModel);
        String expectedMessage = String.format(MESSAGE_EDIT_TAG_SUCCESS, VALID_TAG_FRIEND, VALID_TAG_HUSBAND);
        Tag friendTag = new Tag(VALID_TAG_FRIEND);
        Tag husbandTag = new Tag(VALID_TAG_HUSBAND);

        // Attempt to change all Person objects
        Model expectedModel = new ModelManager(testModel.getAddressBook(), new UserPrefs());
        ArrayList<Index> indices = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            indices.add(Index.fromZeroBased(i));
        }
        expectedModel.editTag(friendTag, husbandTag, indices);
        assertCommandSuccess(tagChangeFriendToHusband, testModel, expectedMessage, expectedModel);
    }
    /** Returns a new EditTagCommand with the parameters */
    public EditTagCommand prepareCommand(String toChange, String newValue, Model model) throws IllegalValueException {
        Tag changedTag = new Tag(toChange);
        Tag newTag = new Tag(newValue);
        EditTagCommand editTagCommand = new EditTagCommand(changedTag, newTag);
        editTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editTagCommand;
    }
    /** Returns a pre-made Address Book for testing purposes */
    private AddressBook prepareAddressBook() throws DuplicatePersonException {
        ReadOnlyPerson alice = new PersonBuilder().withName("Alice Pauline")
                .withTags(VALID_TAG_FRIEND, VALID_TAG_COLLEAGUE).build();
        ReadOnlyPerson bernice = new PersonBuilder().withName("Bernice Applecut")
                .withTags(VALID_TAG_FRIEND).build();
        ReadOnlyPerson clarice = new PersonBuilder().withName("Clarice Fenderbunt")
                .withTags(VALID_TAG_FRIEND, VALID_TAG_COLLEAGUE).build();
        ReadOnlyPerson denise = new PersonBuilder().withName("Denise Lieselocke")
                .withTags(VALID_TAG_FRIEND).build();
        ArrayList<ReadOnlyPerson> toAdd = new ArrayList<>(Arrays.asList(alice, bernice, clarice, denise));
        AddressBook preparedBook = new AddressBook();
        for (ReadOnlyPerson r : toAdd) {
            preparedBook.addPerson(r);
        }
        return preparedBook;
    }
}
```
###### \java\seedu\address\logic\commands\EditTaskCommandTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.DESC_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstTaskOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalTasks.getTypicalTasksOnlyAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
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

    private Model model = new ModelManager(getTypicalTasksOnlyAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Task editedTask = new TaskBuilder().build();
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        EditTaskCommand editTaskCommand = prepareCommand(Index.fromZeroBased(3), descriptor);

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateTask(model.getFilteredTaskList().get(3), editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastTask = Index.fromOneBased(model.getFilteredTaskList().size());
        ReadOnlyTask lastTask = model.getFilteredTaskList().get(indexLastTask.getZeroBased());

        TaskBuilder taskInList = new TaskBuilder(lastTask);
        Task editedTask = taskInList.withTaskName(VALID_TASK_NAME_PAPER).withDescription(VALID_DESCRIPTION_PAPER)
                .withDeadline(VALID_DEADLINE_PAPER).build();


        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withTaskName(VALID_TASK_NAME_PAPER).withDescription(VALID_DESCRIPTION_PAPER)
                .withDeadline(VALID_DEADLINE_PAPER).build();
        EditTaskCommand editTaskCommand = prepareCommand(indexLastTask, descriptor);

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateTask(lastTask, editedTask);
        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstTaskOnly(model);

        ReadOnlyTask taskInFilteredList = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task editedTask = new TaskBuilder(taskInFilteredList).withTaskName(VALID_TASK_NAME_PAPER).build();
        EditTaskCommand editTaskCommand = prepareCommand(INDEX_FIRST_TASK,
                new EditTaskDescriptorBuilder().withTaskName(VALID_TASK_NAME_PAPER).build());

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateTask(model.getFilteredTaskList().get(0), editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTaskIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withTaskName(VALID_TASK_NAME_PAPER).build();
        EditTaskCommand editTaskCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidTaskIndexFilteredList_failure() {
        showFirstTaskOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getTasksList().size());

        EditTaskCommand editTaskCommand = prepareCommand(outOfBoundIndex,
                new EditTaskDescriptorBuilder().withTaskName(VALID_TASK_NAME_PAPER).build());

        assertCommandFailure(editTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditTaskCommand standardCommand = new EditTaskCommand(INDEX_FIRST_TASK, DESC_PENCIL);

        // same values -> returns true
        EditTaskCommand.EditTaskDescriptor copyDescriptor = new EditTaskCommand.EditTaskDescriptor(DESC_PENCIL);
        EditTaskCommand commandWithSameValues = new EditTaskCommand(INDEX_FIRST_TASK, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_SECOND_TASK, DESC_PENCIL)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_FIRST_TASK, DESC_PAPER)));
    }

    /**
     * Returns an {@code EditTaskCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditTaskCommand prepareCommand(Index index, EditTaskCommand.EditTaskDescriptor descriptor) {
        EditTaskCommand editTaskCommand = new EditTaskCommand(index, descriptor);
        editTaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editTaskCommand;
    }
}
```
###### \java\seedu\address\logic\commands\EditTaskDescriptorTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.DESC_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_ADDRESS_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_PAPER;

import org.junit.Test;

import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.testutil.EditTaskDescriptorBuilder;


public class EditTaskDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        EditTaskDescriptor descriptorWithSameValues = new EditTaskDescriptor(DESC_PENCIL);
        assertTrue(DESC_PENCIL.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_PENCIL.equals(DESC_PENCIL));

        // null -> returns false
        assertFalse(DESC_PENCIL.equals(null));

        // different types -> returns false
        assertFalse(DESC_PENCIL.equals(5));

        // different values -> returns false
        assertFalse(DESC_PENCIL.equals(DESC_PAPER));

        // different name -> returns false
        EditTaskDescriptor editedPencil = new EditTaskDescriptorBuilder(DESC_PENCIL)
                .withTaskName(VALID_TASK_NAME_PAPER).build();
        assertFalse(DESC_PENCIL.equals(editedPencil));

        // different description -> returns false
        editedPencil = new EditTaskDescriptorBuilder(DESC_PENCIL).withDescription(VALID_DESCRIPTION_PAPER).build();
        assertFalse(DESC_PENCIL.equals(editedPencil));

        // different deadline -> returns false
        editedPencil = new EditTaskDescriptorBuilder(DESC_PENCIL).withDeadline(VALID_DEADLINE_PAPER).build();
        assertFalse(DESC_PENCIL.equals(editedPencil));

        // different priority -> returns false
        editedPencil = new EditTaskDescriptorBuilder(DESC_PENCIL).withPriority(VALID_PRIORITY_PAPER).build();
        assertFalse(DESC_PENCIL.equals(editedPencil));

        // different task address -> returns false
        editedPencil = new EditTaskDescriptorBuilder(DESC_PENCIL).withTaskAddress(VALID_TASK_ADDRESS_PAPER).build();
        assertFalse(DESC_PENCIL.equals(editedPencil));
    }
}
```
###### \java\seedu\address\logic\commands\FindTaskCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_TASKS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalTasks.ACCEPT;
import static seedu.address.testutil.TypicalTasks.DATE;
import static seedu.address.testutil.TypicalTasks.GRADLE;
import static seedu.address.testutil.TypicalTasks.getTypicalTasksOnlyAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.TaskContainsKeywordPredicate;


public class FindTaskCommandTest {
    private Model model = new ModelManager(getTypicalTasksOnlyAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        TaskContainsKeywordPredicate firstPredicate =
                new TaskContainsKeywordPredicate(Collections.singletonList("first"));
        TaskContainsKeywordPredicate secondPredicate =
                new TaskContainsKeywordPredicate(Collections.singletonList("second"));

        FindTaskCommand findFirstCommand = new FindTaskCommand(firstPredicate);
        FindTaskCommand findSecondCommand = new FindTaskCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindTaskCommand findFirstCommandCopy = new FindTaskCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noTaskFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 0);
        FindTaskCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multipleTasksFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 3);
        FindTaskCommand command = prepareCommand("Resolve Acceptance Date");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ACCEPT, DATE, GRADLE));
    }

    /**
     * Parses {@code userInput} into a {@code FindTaskCommand}.
     */
    private FindTaskCommand prepareCommand(String userInput) {
        FindTaskCommand command =
                new FindTaskCommand(new TaskContainsKeywordPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyTask>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindTaskCommand command, String expectedMessage,
                                      List<ReadOnlyTask> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredTaskList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\commands\SelectTaskCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.showFirstTaskOnly;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestTaskEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

public class SelectTaskCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();
    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastTaskIndex = Index.fromOneBased(model.getFilteredTaskList().size());

        assertExecutionSuccess(INDEX_FIRST_TASK);
        assertExecutionSuccess(INDEX_THIRD_TASK);
        assertExecutionSuccess(lastTaskIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFirstTaskOnly(model);

        assertExecutionSuccess(INDEX_FIRST_TASK);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showFirstTaskOnly(model);

        Index outOfBoundsIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getTasksList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectTaskCommand selectFirstCommand = new SelectTaskCommand(INDEX_FIRST_TASK);
        SelectTaskCommand selectSecondCommand = new SelectTaskCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectTaskCommand selectFirstCommandCopy = new SelectTaskCommand(INDEX_FIRST_TASK);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectTaskCommand} with the given {@code index}, and checks that
     * {@code JumpToListRequestTaskEvent} is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectTaskCommand selectTaskCommand = prepareCommand(index);
        String expectedMessage = String.format(SelectTaskCommand.MESSAGE_SUCCESS, index.getOneBased());
        try {
            CommandResult commandResult = selectTaskCommand.execute();
            assertEquals(expectedMessage, commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }
        JumpToListRequestTaskEvent lastEvent =
                (JumpToListRequestTaskEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectTaskCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the .
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectTaskCommand selectTaskCommand = prepareCommand(index);

        try {
            selectTaskCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code SelectTaskCommand} with parameters {@code index}.
     */
    private SelectTaskCommand prepareCommand(Index index) {
        SelectTaskCommand selectTaskCommand = new SelectTaskCommand(index);
        selectTaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return selectTaskCommand;
    }
}
```
###### \java\seedu\address\logic\commands\SetCompleteCommandTest.java
``` java
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstTaskOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalTasks.getTypicalTasksOnlyAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.ReadOnlyTask;

public class SetCompleteCommandTest {
    private Model model = new ModelManager(getTypicalTasksOnlyAddressBook(), new UserPrefs());

    @Test
    public void execute_unfilteredListValidIndex_success() throws Exception {
        ReadOnlyTask taskToChange = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        SetCompleteCommand setCompleteCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(SetCompleteCommand.MESSAGE_SUCCESS, taskToChange);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setAsComplete(taskToChange, true);

        assertCommandSuccess(setCompleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unfilteredListInvalidIndex_failure() throws Exception {
        Index outOfRangeIndex = Index.fromZeroBased(model.getFilteredTaskList().size());
        SetCompleteCommand setCompleteCommand = prepareCommand(outOfRangeIndex);

        assertCommandFailure(setCompleteCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_changeCompletedTask_failure() throws Exception {
        ReadOnlyTask taskToChange = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setAsComplete(taskToChange, true);
        SetCompleteCommand setCompleteCommand = new SetCompleteCommand(INDEX_FIRST_TASK);
        setCompleteCommand.setData(expectedModel, new CommandHistory(), new UndoRedoStack());

        assertCommandFailure(setCompleteCommand, expectedModel, SetCompleteCommand.MESSAGE_TASK_ALREADY_COMPLETE);
    }

    @Test
    public void execute_filteredListValidIndex_success() throws Exception {
        showFirstTaskOnly(model);

        ReadOnlyTask taskToChange = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        SetCompleteCommand setCompleteCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(SetCompleteCommand.MESSAGE_SUCCESS, taskToChange);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setAsComplete(taskToChange, true);
        showFirstTaskOnly(expectedModel);

        assertCommandSuccess(setCompleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredListInvalidIndex_throwsCommandException() {
        showFirstTaskOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getTasksList().size());

        SetCompleteCommand setCompleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(setCompleteCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SetCompleteCommand setFirstCommand = new SetCompleteCommand(INDEX_FIRST_TASK);
        SetCompleteCommand setSecondCommand = new SetCompleteCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(setFirstCommand.equals(setFirstCommand));

        // same values -> returns true
        SetCompleteCommand setFirstCommandCopy = new SetCompleteCommand(INDEX_FIRST_TASK);
        assertTrue(setFirstCommand.equals(setFirstCommandCopy));

        // different types -> returns false
        assertFalse(setFirstCommand.equals(1));

        // null -> returns false
        assertFalse(setFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(setFirstCommand.equals(setSecondCommand));
    }

    /**
     * Returns a {@code SetCompleteCommand} with the parameter {@code index}.
     */
    public SetCompleteCommand prepareCommand(Index taskIndex) {
        SetCompleteCommand command = new SetCompleteCommand(taskIndex);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\SetIncompleteCommandTest.java
``` java
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstTaskOnly;
import static seedu.address.logic.commands.CommandTestUtil.showSecondTaskOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;
import static seedu.address.testutil.TypicalTasks.getTypicalTasksOnlyAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.ReadOnlyTask;

public class SetIncompleteCommandTest {
    private Model model = new ModelManager(getTypicalTasksOnlyAddressBook(), new UserPrefs());

    @Test
    public void execute_validTaskIndex_success() throws Exception {
        ReadOnlyTask taskToChange = model.getFilteredTaskList().get(INDEX_THIRD_TASK.getZeroBased());
        SetIncompleteCommand setIncompleteCommand = prepareCommand(INDEX_THIRD_TASK);

        String expectedMessage = String.format(SetIncompleteCommand.MESSAGE_SUCCESS, taskToChange);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setAsComplete(taskToChange, false);

        assertCommandSuccess(setIncompleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTaskIndex_failure() throws Exception {
        Index outOfRangeIndex = Index.fromZeroBased(model.getFilteredTaskList().size());
        SetIncompleteCommand setIncompleteCommand = prepareCommand(outOfRangeIndex);

        assertCommandFailure(setIncompleteCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_changeIncompleteTask_failure() throws Exception {
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        SetIncompleteCommand setIncompleteCommand = new SetIncompleteCommand(INDEX_FIRST_TASK);
        setIncompleteCommand.setData(expectedModel, new CommandHistory(), new UndoRedoStack());

        assertCommandFailure(setIncompleteCommand, expectedModel, SetIncompleteCommand.MESSAGE_TASK_ALREADY_COMPLETE);
    }

    @Test
    public void equals() {
        SetIncompleteCommand setFirstCommand = new SetIncompleteCommand(INDEX_FIRST_TASK);
        SetIncompleteCommand setSecondCommand = new SetIncompleteCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(setFirstCommand.equals(setFirstCommand));

        // same values -> returns true
        SetIncompleteCommand setFirstCommandCopy = new SetIncompleteCommand(INDEX_FIRST_TASK);
        assertTrue(setFirstCommand.equals(setFirstCommandCopy));

        // different types -> returns false
        assertFalse(setFirstCommand.equals(1));

        // null -> returns false
        assertFalse(setFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(setFirstCommand.equals(setSecondCommand));
    }

    @Test
    public void execute_filteredListValidIndex_success() throws Exception {
        showSecondTaskOnly(model);

        ReadOnlyTask taskToChange = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        SetIncompleteCommand setIncompleteCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(SetIncompleteCommand.MESSAGE_SUCCESS, taskToChange);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setAsComplete(taskToChange, false);
        showSecondTaskOnly(expectedModel);

        assertCommandSuccess(setIncompleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredListInvalidIndex_throwsCommandException() {
        showFirstTaskOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getTasksList().size());

        SetIncompleteCommand setIncompleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(setIncompleteCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Returns a {@code SetIncompleteCommand} with the parameter {@code index}.
     */
    public SetIncompleteCommand prepareCommand(Index taskIndex) {
        SetIncompleteCommand command = new SetIncompleteCommand(taskIndex);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\ViewAssignCommandTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstTaskOnly;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.ReadOnlyTask;

public class ViewAssignCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyTask taskToShow = model.getFilteredTaskList().get(5);
        ViewAssignCommand viewAssignCommand = prepareCommand(Index.fromZeroBased(5));

        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 3);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.viewAssignees(taskToShow);

        assertCommandSuccess(viewAssignCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        ViewAssignCommand viewAssignCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(viewAssignCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstTaskOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getTasksList().size());

        ViewAssignCommand viewAssignCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(viewAssignCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ViewAssignCommand viewFirstCommand = new ViewAssignCommand(INDEX_FIRST_TASK);
        ViewAssignCommand viewSecondCommand = new ViewAssignCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewAssignCommand viewFirstCommandCopy = new ViewAssignCommand(INDEX_FIRST_TASK);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }

    /**
     * Returns a {@code ViewAssignCommand} with the parameter {@code index}.
     */
    private ViewAssignCommand prepareCommand(Index index) {
        ViewAssignCommand viewAssignCommand = new ViewAssignCommand(index);
        viewAssignCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return viewAssignCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommandAssign() throws Exception {
        ArrayList<Index> personIndexes = new ArrayList<>(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
        AssignCommand command = (AssignCommand) parser.parseCommand(AssignCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + INDEX_SECOND_PERSON.getOneBased() + " "
                + PREFIX_TARGET + INDEX_FIRST_TASK.getOneBased(), DEFAULT_STATE_LOCK);
        assertEquals(new AssignCommand(personIndexes, INDEX_FIRST_TASK), command);
    }

    @Test
    public void parseCommandAliasAssign() throws Exception {
        ArrayList<Index> personIndexes = new ArrayList<>(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
        AssignCommand command = (AssignCommand) parser.parseCommand(AssignCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + INDEX_SECOND_PERSON.getOneBased() + " "
                + PREFIX_TARGET + INDEX_FIRST_TASK.getOneBased(), DEFAULT_STATE_LOCK);
        assertEquals(new AssignCommand(personIndexes, INDEX_FIRST_TASK), command);
    }

```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommandDismiss() throws Exception {
        ArrayList<Index> personIndexes = new ArrayList<>(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
        DismissCommand command = (DismissCommand) parser.parseCommand(DismissCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + INDEX_SECOND_PERSON.getOneBased() + " "
                + PREFIX_FROM + INDEX_FIRST_TASK.getOneBased(), DEFAULT_STATE_LOCK);
        assertEquals(new DismissCommand(personIndexes, INDEX_FIRST_TASK), command);
    }

    @Test
    public void parseCommandAliasDismiss() throws Exception {
        ArrayList<Index> personIndexes = new ArrayList<>(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
        DismissCommand command = (DismissCommand) parser.parseCommand(DismissCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + INDEX_SECOND_PERSON.getOneBased() + " "
                + PREFIX_FROM + INDEX_FIRST_TASK.getOneBased(), DEFAULT_STATE_LOCK);
        assertEquals(new DismissCommand(personIndexes, INDEX_FIRST_TASK), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommandEditTag() throws Exception {
        EditTagCommand command = (EditTagCommand) parser.parseCommand(EditTagCommand.COMMAND_WORD
                + " " + PREFIX_TAG_FULL + " "
                + " friends enemies", DEFAULT_STATE_LOCK);
        Tag friends = new Tag("friends");
        Tag enemies = new Tag("enemies");
        assertEquals(new EditTagCommand(friends, enemies), command);
    }

    @Test
    public void parseCommandAliasEditTag() throws Exception {
        EditTagCommand command = (EditTagCommand) parser.parseCommand(EditTagCommand.COMMAND_ALIAS
                + " " + PREFIX_TAG_FULL + " "
                + " friends enemies", DEFAULT_STATE_LOCK);
        Tag friends = new Tag("friends");
        Tag enemies = new Tag("enemies");
        assertEquals(new EditTagCommand(friends, enemies), command);
    }

```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void  parseCommandSetComplete() throws Exception {
        SetCompleteCommand command = (SetCompleteCommand) parser.parseCommand(SetCompleteCommand.COMMAND_WORD
                + " " + INDEX_FIRST_TASK.getOneBased(), DEFAULT_STATE_LOCK);
        assertEquals(new SetCompleteCommand(INDEX_FIRST_TASK), command);
    }

    @Test
    public void  parseCommandAliasSetComplete() throws Exception {
        SetCompleteCommand command = (SetCompleteCommand) parser.parseCommand(SetCompleteCommand.COMMAND_ALIAS
                + " " + INDEX_FIRST_TASK.getOneBased(), DEFAULT_STATE_LOCK);
        assertEquals(new SetCompleteCommand(INDEX_FIRST_TASK), command);
    }

    @Test
    public void  parseCommandSetIncomplete() throws Exception {
        SetIncompleteCommand command = (SetIncompleteCommand) parser.parseCommand(SetIncompleteCommand.COMMAND_ALIAS
                + " " + INDEX_FIRST_TASK.getOneBased(), DEFAULT_STATE_LOCK);
        assertEquals(new SetIncompleteCommand(INDEX_FIRST_TASK), command);
    }

    @Test
    public void  parseCommandAliasSetIncomplete() throws Exception {
        SetIncompleteCommand command = (SetIncompleteCommand) parser.parseCommand(SetIncompleteCommand.COMMAND_ALIAS
                + " " + INDEX_FIRST_TASK.getOneBased(), DEFAULT_STATE_LOCK);
        assertEquals(new SetIncompleteCommand(INDEX_FIRST_TASK), command);
    }
```
###### \java\seedu\address\logic\parser\AddTaskCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.TASK_ADDRESS_DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.TASK_ADDRESS_DESC_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.TASK_NAME_DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.TASK_NAME_DESC_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.TASK_SEPARATOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_ADDRESS_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_PENCIL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddPersonCommand;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class AddTaskCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parseTasksAllFieldsPresent_success() {
        Task expectedTask = new TaskBuilder().withTaskName(VALID_TASK_NAME_PENCIL)
                .withDescription(VALID_DESCRIPTION_PENCIL).withDeadline(VALID_DEADLINE_PENCIL)
                .withPriority(VALID_PRIORITY_PENCIL).withTaskAddress(VALID_TASK_ADDRESS_PENCIL).build();

        // multiple names - last name accepted
        assertParseSuccess(parser, AddPersonCommand.COMMAND_WORD + TASK_SEPARATOR + TASK_NAME_DESC_PAPER
                + TASK_NAME_DESC_PENCIL + DESCRIPTION_DESC_PENCIL + DEADLINE_DESC_PENCIL
                + PRIORITY_DESC_PENCIL + TASK_ADDRESS_DESC_PENCIL, new AddTaskCommand(expectedTask));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, AddTaskCommand.COMMAND_WORD + TASK_SEPARATOR + TASK_NAME_DESC_PENCIL
                + DESCRIPTION_DESC_PAPER + DESCRIPTION_DESC_PENCIL + DEADLINE_DESC_PENCIL
                + PRIORITY_DESC_PENCIL + TASK_ADDRESS_DESC_PENCIL, new AddTaskCommand(expectedTask));

        // multiple deadlines - last deadline accepted
        assertParseSuccess(parser, AddTaskCommand.COMMAND_WORD + TASK_SEPARATOR + TASK_NAME_DESC_PENCIL
                + DESCRIPTION_DESC_PENCIL + DEADLINE_DESC_PAPER + DEADLINE_DESC_PENCIL
                + PRIORITY_DESC_PENCIL + TASK_ADDRESS_DESC_PENCIL, new AddTaskCommand(expectedTask));

        // multiple priorities - last priority accepted
        assertParseSuccess(parser, AddTaskCommand.COMMAND_WORD + TASK_SEPARATOR + TASK_NAME_DESC_PENCIL
                + DESCRIPTION_DESC_PENCIL + DEADLINE_DESC_PENCIL + PRIORITY_DESC_PAPER
                + PRIORITY_DESC_PENCIL + TASK_ADDRESS_DESC_PENCIL, new AddTaskCommand(expectedTask));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, AddTaskCommand.COMMAND_WORD + TASK_SEPARATOR + TASK_NAME_DESC_PENCIL
                + DESCRIPTION_DESC_PENCIL + DEADLINE_DESC_PENCIL + PRIORITY_DESC_PENCIL
                + TASK_ADDRESS_DESC_PAPER + TASK_ADDRESS_DESC_PENCIL, new AddTaskCommand(expectedTask));
    }


    @Test
    public void parseTasksCompulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE);

        // missing task name prefix
        assertParseFailure(parser, AddPersonCommand.COMMAND_WORD + TASK_SEPARATOR + VALID_TASK_NAME_PAPER
                + DESCRIPTION_DESC_PENCIL + DEADLINE_DESC_PENCIL + PRIORITY_DESC_PENCIL, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, AddPersonCommand.COMMAND_WORD + TASK_SEPARATOR + VALID_TASK_NAME_PENCIL
                + VALID_DESCRIPTION_PENCIL + VALID_DEADLINE_PENCIL + VALID_PRIORITY_PENCIL, expectedMessage);
    }


    @Test
    public void parseTaskInvalidValue_failure() {
        // invalid deadline
        assertParseFailure(parser, AddPersonCommand.COMMAND_WORD + TASK_SEPARATOR + TASK_NAME_DESC_PENCIL
                + DESCRIPTION_DESC_PENCIL + INVALID_DEADLINE_DESC
                + PRIORITY_DESC_PENCIL + TASK_ADDRESS_DESC_PENCIL, Deadline.MESSAGE_INVALID_DATE);

        // invalid priority
        assertParseFailure(parser, AddPersonCommand.COMMAND_WORD + TASK_SEPARATOR + TASK_NAME_DESC_PENCIL
                + DESCRIPTION_DESC_PENCIL + DEADLINE_DESC_PENCIL
                + INVALID_PRIORITY_DESC + TASK_ADDRESS_DESC_PENCIL, Priority.MESSAGE_PRIORITY_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddPersonCommand.COMMAND_WORD + TASK_SEPARATOR + TASK_NAME_DESC_PENCIL
                + DESCRIPTION_DESC_PENCIL + INVALID_DEADLINE_DESC
                + INVALID_PRIORITY_DESC + TASK_ADDRESS_DESC_PENCIL, Deadline.MESSAGE_INVALID_DATE);
    }


}
```
###### \java\seedu\address\logic\parser\AssignCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TARGET;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AssignCommandParserTest {

    private AssignTaskCommandParser parser = new AssignTaskCommandParser();

    @Test
    public void parseValidPersonIndexAndTaskIndex_success() throws ParseException {
        ArrayList<Index> indexes = new ArrayList<>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);

        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + INDEX_SECOND_PERSON.getOneBased() + " "
                + PREFIX_TARGET.getPrefix() + INDEX_FIRST_TASK.getOneBased();
        assertParseSuccess(parser, userInput, new AssignCommand(indexes, INDEX_FIRST_TASK));
    }

    @Test
    public void parseInvalidIndexes_failure() throws ParseException {
        // invalid person indexes
        String userInput = "aaaaa" + " " + INDEX_SECOND_PERSON.getOneBased() + " "
                + PREFIX_TARGET.getPrefix() + INDEX_FIRST_TASK.getOneBased();
        assertParseFailure(parser, userInput, MESSAGE_INVALID_INDEX);

        // invalid task index
        userInput = INDEX_FIRST_PERSON.getOneBased() + " " + INDEX_SECOND_PERSON.getOneBased() + " "
                + PREFIX_TARGET.getPrefix() + "aaaaa";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parseInvalidArgsFailure() throws Exception {
        // no person indexes specified
        String userInput = " " + PREFIX_TARGET.getPrefix() + INDEX_FIRST_TASK.getOneBased();
        assertParseFailure(parser, userInput, AssignCommand.MESSAGE_INVALID_PERSONS_ARGS);
        // no target prefix
        userInput = INDEX_FIRST_PERSON.getOneBased() + " " + INDEX_SECOND_PERSON.getOneBased() + " "
                + INDEX_FIRST_TASK.getOneBased();
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AssignCommand.MESSAGE_USAGE));
        // no task index specified
        userInput = INDEX_FIRST_PERSON.getOneBased() + " " + INDEX_SECOND_PERSON.getOneBased() + " "
                + PREFIX_TARGET.getPrefix();
        assertParseFailure(parser, userInput, AssignCommand.MESSAGE_INVALID_TARGET_ARGS);
    }
}
```
###### \java\seedu\address\logic\parser\DeleteTaskCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.TASK_SEPARATOR;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import org.junit.Test;

import seedu.address.logic.commands.DeleteTaskCommand;

public class DeleteTaskCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parseTaskValidArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, TASK_SEPARATOR + "1",
                new DeleteTaskCommand(INDEX_FIRST_TASK));
    }

    @Test
    public void parseTaskInvalidArgs_throwsParseException() {
        assertParseFailure(parser, TASK_SEPARATOR + "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\EditTagCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.EditTagCommand.MESSAGE_DUPLICATE_TAGS;
import static seedu.address.logic.commands.EditTagCommand.MESSAGE_INSUFFICIENT_ARGS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditTagCommand;
import seedu.address.model.tag.Tag;


public class EditTagCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTagCommand.MESSAGE_USAGE);


    private EditTagCommandParser parser = new EditTagCommandParser();
    @Test
    public void invalidInputTest() {
        // empty argument
        assertParseFailure(parser, " ", MESSAGE_INVALID_FORMAT);
        // too little args
        assertParseFailure(parser, VALID_TAG_FRIEND, MESSAGE_INSUFFICIENT_ARGS);
        // too many args
        assertParseFailure(parser, VALID_TAG_FRIEND + " " + VALID_TAG_FRIEND
                + " " + VALID_TAG_FRIEND, MESSAGE_INSUFFICIENT_ARGS);
        // args are the same
        assertParseFailure(parser, VALID_TAG_FRIEND + " " + VALID_TAG_FRIEND, MESSAGE_DUPLICATE_TAGS);
        // args are invalid
        assertParseFailure(parser, INVALID_TAG_DESC + " " + INVALID_TAG_DESC, MESSAGE_INVALID_FORMAT);
    }
    @Test
    public void validInputTest() throws IllegalValueException {
        Tag friendTag = new Tag(VALID_TAG_FRIEND);
        Tag husbandTag = new Tag(VALID_TAG_HUSBAND);
        Tag friendTagUpper = new Tag (VALID_TAG_FRIEND.toUpperCase());
        // case changes
        assertParseSuccess(parser, VALID_TAG_FRIEND + " "
                + VALID_TAG_FRIEND.toUpperCase(), new EditTagCommand(friendTag, friendTagUpper));
        // two distinct words
        assertParseSuccess(parser, VALID_TAG_FRIEND + " "
                + VALID_TAG_HUSBAND, new EditTagCommand(friendTag, husbandTag));

    }
}
```
###### \java\seedu\address\logic\parser\EditTaskCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TASK_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.TASK_ADDRESS_DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.TASK_ADDRESS_DESC_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.TASK_NAME_DESC_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.TASK_SEPARATOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_ADDRESS_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_ADDRESS_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_PAPER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Priority;
import seedu.address.model.task.TaskName;
import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditTaskCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, TASK_SEPARATOR + VALID_TASK_NAME_PAPER, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, TASK_SEPARATOR + "1", EditTaskCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, TASK_SEPARATOR + "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, TASK_SEPARATOR + "-5" + TASK_NAME_DESC_PAPER, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, TASK_SEPARATOR + "0" + TASK_NAME_DESC_PAPER, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, TASK_SEPARATOR + "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, TASK_SEPARATOR + "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, TASK_SEPARATOR + "1" + INVALID_TASK_NAME_DESC,
                TaskName.MESSAGE_NAME_CONSTRAINTS); // invalid name
        assertParseFailure(parser, TASK_SEPARATOR + "1" + INVALID_DEADLINE_DESC,
                Deadline.MESSAGE_INVALID_DATE); // invalid deadline
        assertParseFailure(parser, TASK_SEPARATOR + "1" + INVALID_PRIORITY_DESC,
                Priority.MESSAGE_PRIORITY_CONSTRAINTS); // invalid priority

        // invalid phone followed by valid email
        assertParseFailure(parser, TASK_SEPARATOR + "1" + INVALID_DEADLINE_DESC + PRIORITY_DESC_PAPER,
                Deadline.MESSAGE_INVALID_DATE);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, TASK_SEPARATOR + "1" + DEADLINE_DESC_PAPER + INVALID_DEADLINE_DESC,
                Deadline.MESSAGE_INVALID_DATE);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, TASK_SEPARATOR + "1" + INVALID_TASK_NAME_DESC + INVALID_PRIORITY_DESC
                + VALID_DEADLINE_PAPER + VALID_DESCRIPTION_PAPER, TaskName.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_TASK;
        String userInput = TASK_SEPARATOR + targetIndex.getOneBased() + TASK_NAME_DESC_PAPER + DESCRIPTION_DESC_PAPER
                + DEADLINE_DESC_PAPER + PRIORITY_DESC_PAPER + TASK_ADDRESS_DESC_PAPER;

        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withTaskName(VALID_TASK_NAME_PAPER).withDescription(VALID_DESCRIPTION_PAPER)
                .withDeadline(VALID_DEADLINE_PAPER).withPriority(VALID_PRIORITY_PAPER)
                .withTaskAddress(VALID_TASK_ADDRESS_PAPER).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = TASK_SEPARATOR + targetIndex.getOneBased() + DESCRIPTION_DESC_PAPER + DEADLINE_DESC_PAPER;

        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_PAPER).withDeadline(VALID_DEADLINE_PAPER).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = TASK_SEPARATOR + targetIndex.getOneBased() + TASK_NAME_DESC_PAPER;
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withTaskName(VALID_TASK_NAME_PAPER).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = TASK_SEPARATOR + targetIndex.getOneBased() + DESCRIPTION_DESC_PAPER;
        descriptor = new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_PAPER).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = TASK_SEPARATOR + targetIndex.getOneBased() + DEADLINE_DESC_PAPER;
        descriptor = new EditTaskDescriptorBuilder().withDeadline(VALID_DEADLINE_PAPER).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = TASK_SEPARATOR + targetIndex.getOneBased() + PRIORITY_DESC_PAPER;
        descriptor = new EditTaskDescriptorBuilder().withPriority(VALID_PRIORITY_PAPER).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = TASK_SEPARATOR + targetIndex.getOneBased() + TASK_ADDRESS_DESC_PAPER;
        descriptor = new EditTaskDescriptorBuilder().withTaskAddress(VALID_TASK_ADDRESS_PAPER).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = TASK_SEPARATOR + targetIndex.getOneBased()  + DESCRIPTION_DESC_PAPER + DEADLINE_DESC_PAPER
                + PRIORITY_DESC_PAPER + TASK_ADDRESS_DESC_PAPER + DESCRIPTION_DESC_PAPER + DEADLINE_DESC_PAPER
                + PRIORITY_DESC_PAPER + TASK_ADDRESS_DESC_PAPER + DESCRIPTION_DESC_PENCIL + DEADLINE_DESC_PENCIL
                + PRIORITY_DESC_PENCIL + TASK_ADDRESS_DESC_PENCIL;

        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_PENCIL).withDeadline(VALID_DEADLINE_PENCIL)
                .withPriority(VALID_PRIORITY_PENCIL).withTaskAddress(VALID_TASK_ADDRESS_PENCIL).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = TASK_SEPARATOR + targetIndex.getOneBased() + INVALID_DEADLINE_DESC + DEADLINE_DESC_PAPER;
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withDeadline(VALID_DEADLINE_PAPER).build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = TASK_SEPARATOR + targetIndex.getOneBased() + PRIORITY_DESC_PAPER + INVALID_DEADLINE_DESC
                + DESCRIPTION_DESC_PAPER + DEADLINE_DESC_PAPER;
        descriptor = new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_PAPER)
                .withDeadline(VALID_DEADLINE_PAPER).withPriority(VALID_PRIORITY_PAPER).build();
        expectedCommand = new EditTaskCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### \java\seedu\address\logic\parser\FIndTaskCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.TASK_SEPARATOR;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindTaskCommand;
import seedu.address.model.task.TaskContainsKeywordPredicate;

public class FIndTaskCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, TASK_SEPARATOR + "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindTaskCommand expectedFindTaskCommand =
                new FindTaskCommand(new TaskContainsKeywordPredicate(Arrays.asList("Lucy", "Date")));
        assertParseSuccess(parser, TASK_SEPARATOR + "Lucy Date", expectedFindTaskCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, TASK_SEPARATOR + " \n Lucy \n \t Date  \t", expectedFindTaskCommand);
    }

}
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseRemark_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseRemark(null);
    }

    @Test
    public void parseRemark_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseRemark(Optional.of(INVALID_REMARK));
    }

    @Test
    public void parseRemark_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseRemark(Optional.empty()).isPresent());
    }

    @Test
    public void parseRemark_validValue_returnsRemark() throws Exception {
        Remark expectedRemark = new Remark(VALID_REMARK);
        Optional<Remark> actualRemark = ParserUtil.parseRemark(Optional.of(VALID_REMARK));
        Remark expectedPrivateRemark = new Remark(VALID_REMARK, PRIVATE_FIELD);
        Optional<Remark> actualPrivateRemark = ParserUtil.parseRemark(Optional.of(VALID_REMARK));

        assertEquals(expectedRemark, actualRemark.get());
        assertEquals(expectedPrivateRemark, actualPrivateRemark.get());
    }

    @Test
    public void parseAvatar_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseAvatar(null);
    }

    @Test
    public void parseAvatar_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseAvatar(Optional.of(INVALID_AVATAR));
    }

    @Test
    public void parseAvatar_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseAvatar(Optional.empty()).isPresent());
    }

    @Test
    public void parseAvatar_validValue_returnsAvatar() throws Exception {
        Avatar expectedAvatar = new Avatar(VALID_AVATAR);
        Optional<Avatar> actualAvatar = ParserUtil.parseAvatar(Optional.of(VALID_AVATAR));

        assertEquals(expectedAvatar, actualAvatar.get());
    }

    @Test
    public void parseTags_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTags(null);
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseTaskName_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTaskName(null);
    }

    @Test
    public void parseTaskName_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTaskName(Optional.of(INVALID_TASK_NAME));
    }

    @Test
    public void parseTaskName_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseTaskName(Optional.empty()).isPresent());
    }

    @Test
    public void parseTaskName_validValue_returnsTaskName() throws Exception {
        TaskName expectedTaskName = new TaskName(VALID_TASK_NAME);
        Optional<TaskName> actualTaskName = ParserUtil.parseTaskName(Optional.of(VALID_TASK_NAME));

        assertEquals(expectedTaskName, actualTaskName.get());
    }

    @Test
    public void parseDescription_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseDescription(null);
    }

    @Test
    public void parseDescription_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseDescription(Optional.empty()).isPresent());
    }

    @Test
    public void parseDescription_validValue_returnsDescription() throws Exception {
        Description expectedDescription = new Description(VALID_DESCRIPTION);
        Optional<Description> actualDescription = ParserUtil.parseDescription(Optional.of(VALID_DESCRIPTION));

        assertEquals(expectedDescription, actualDescription.get());
    }

    @Test
    public void parseDeadline_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseDeadline(null);
    }

    @Test
    public void parseDeadline_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseDeadline(Optional.of(INVALID_DEADLINE));
    }

    @Test
    public void parseDeadline_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseDeadline(Optional.empty()).isPresent());
    }

    @Test
    public void parseDeadline_validValue_returnsDeadline() throws Exception {
        Deadline expectedDeadline = new Deadline(VALID_DEADLINE);
        Optional<Deadline> actualDeadline = ParserUtil.parseDeadline(Optional.of(VALID_DEADLINE));

        assertEquals(expectedDeadline, actualDeadline.get());
    }

    @Test
    public void parsePriority_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parsePriority(null);
    }

    @Test
    public void parsePriority_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parsePriority(Optional.of(INVALID_PRIORITY));
    }

    @Test
    public void parsePriority_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parsePriority(Optional.empty()).isPresent());
    }

    @Test
    public void parsePriority_validValue_returnsPriority() throws Exception {
        Priority expectedPriority = new Priority(VALID_PRIORITY);
        Optional<Priority> actualPriority = ParserUtil.parsePriority(Optional.of(VALID_PRIORITY));

        assertEquals(expectedPriority, actualPriority.get());
    }

    @Test
    public void parseTaskAddress_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTaskAddress(null);
    }

    @Test
    public void parseTaskAddress_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTaskAddress(Optional.of(INVALID_TASK_ADDRESS));
    }

    @Test
    public void parseTaskAddress_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseTaskAddress(Optional.empty()).isPresent());
    }

    @Test
    public void parseTaskAddress_validValue_returnsTaskAddress() throws Exception {
        TaskAddress expectedTaskAddress = new TaskAddress(VALID_TASK_ADDRESS);
        Optional<TaskAddress> actualTaskAddress = ParserUtil.parseTaskAddress(Optional.of(VALID_TASK_ADDRESS));

        assertEquals(expectedTaskAddress, actualTaskAddress.get());
    }

    @Test
    public void parseIndexes_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseIndexes(null);
    }

    @Test
    public void parseIndexes_invalidValue_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIndexes("1 2 a b c");
    }

    @Test
    public void parseIndexes_validValue_returnsIndexes() throws Exception {
        ArrayList<Index> expectedIndexes = new ArrayList<>(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON,
                INDEX_THIRD_PERSON));

        assertEquals(expectedIndexes, ParserUtil.parseIndexes("1 2 3"));
        assertEquals(expectedIndexes, ParserUtil.parseIndexes("     1   2    3   "));
    }
}
```
###### \java\seedu\address\logic\parser\SelectTaskCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import org.junit.Test;

import seedu.address.logic.commands.SelectTaskCommand;

public class SelectTaskCommandParserTest {

    private SelectCommandParser parser = new SelectCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, " " + PREFIX_TASK.getPrefix() + " 1",
                new SelectTaskCommand(INDEX_FIRST_TASK));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_TASK.getPrefix() + " -1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectTaskCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\SetCompleteCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import org.junit.Test;

import seedu.address.logic.commands.SetCompleteCommand;

public class SetCompleteCommandParserTest {

    private SetTaskCompleteCommandParser parser = new SetTaskCompleteCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, " 1",
                new SetCompleteCommand(INDEX_FIRST_TASK));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " -1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCompleteCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\SetIncompleteCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import org.junit.Test;

import seedu.address.logic.commands.SetIncompleteCommand;

public class SetIncompleteCommandParserTest {

    private SetTaskIncompleteCommandParser parser = new SetTaskIncompleteCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, " 1",
                new SetIncompleteCommand(INDEX_FIRST_TASK));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " -1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetIncompleteCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\ViewAssignCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.ViewAssignCommand;

public class ViewAssignCommandParserTest {

    private ViewAssignCommandParser parser = new ViewAssignCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new ViewAssignCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewAssignCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\task\DeadlineTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DeadlineTest {

    @Test
    public void isValidDeadline() {
        // invalid deadlines
        assertFalse(Deadline.isValidDeadline(" ")); // spaces only
        assertFalse(Deadline.isValidDeadline("alphabets")); // non-numeric letters
        assertFalse(Deadline.isValidDeadline("!@#$%^")); // invalid symbols

        // valid deadline (empty deadline for optional data)
        assertTrue(Deadline.isValidDeadline("")); // empty string

        // valid deadlines
        assertTrue(Deadline.isValidDeadline("04-04-2017")); // dashes
        assertTrue(Deadline.isValidDeadline("the day after tomorrow")); // slashes
        assertTrue(Deadline.isValidDeadline("4-11"));
    }
}
```
###### \java\seedu\address\model\task\DescriptionTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DescriptionTest {

    @Test
    public void isValidDescription() {
        // invalid descriptions
        assertFalse(Description.isValidDescription(" ")); // spaces only

        // valid descriptions
        assertTrue(Description.isValidDescription("")); // empty string
        assertTrue(Description.isValidDescription("alphanumerical contents")); // alphanumerical contents
        assertTrue(Description.isValidDescription("-")); // single character
    }
}
```
###### \java\seedu\address\model\task\PriorityTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PriorityTest {

    @Test
    public void isValidPriority() {
        // invalid names

        assertFalse(Priority.isValidPriority(" ")); // spaces only
        assertFalse(Priority.isValidPriority("invalid")); // invalid priority
        assertFalse(Priority.isValidPriority("777")); // priority out of range
        assertFalse(Priority.isValidPriority("-1")); // priority out of range

        // valid names
        assertTrue(Priority.isValidPriority("")); // empty string
        assertTrue(Priority.isValidPriority("1")); // numerical representation
    }
}
```
###### \java\seedu\address\model\task\TaskContainsKeywordPredicateTest.java
``` java

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.TaskBuilder;

public class TaskContainsKeywordPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TaskContainsKeywordPredicate firstPredicate = new TaskContainsKeywordPredicate(firstPredicateKeywordList);
        TaskContainsKeywordPredicate secondPredicate = new TaskContainsKeywordPredicate(secondPredicateKeywordList);
        TaskContainsKeywordPredicate thirdPredicate = new TaskContainsKeywordPredicate(firstPredicateKeywordList,
                true, false, false, 0);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TaskContainsKeywordPredicate firstPredicateCopy = new TaskContainsKeywordPredicate(firstPredicateKeywordList,
                false, false, false, 0);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
        assertFalse(firstPredicate.equals(thirdPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        TaskContainsKeywordPredicate predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Pencil"));
        assertTrue(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").build()));

        // Multiple keywords
        predicate = new TaskContainsKeywordPredicate(Arrays.asList("Buy", "Pencil"));
        assertTrue(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").build()));

        // Only one matching keyword
        predicate = new TaskContainsKeywordPredicate(Arrays.asList("Pencil", "Pen"));
        assertTrue(predicate.test(new TaskBuilder().withTaskName("Buy Pen").build()));

        // Mixed-case keywords
        predicate = new TaskContainsKeywordPredicate(Arrays.asList("buY", "pEnciL"));
        assertTrue(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TaskContainsKeywordPredicate predicate = new TaskContainsKeywordPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TaskBuilder().withTaskName("Buy").build()));

        // Non-matching keyword
        predicate = new TaskContainsKeywordPredicate(Arrays.asList("Pen"));
        assertFalse(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").build()));
    }

    @Test
    public void test_descriptionContainsKeywords_returnsTrue() {
        // One keyword
        TaskContainsKeywordPredicate predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Pencil"));
        assertTrue(predicate.test(new TaskBuilder().withDescription("Buy 3 Pencil").build()));

        // Multiple keywords
        predicate = new TaskContainsKeywordPredicate(Arrays.asList("Buy", "Pencil"));
        assertTrue(predicate.test(new TaskBuilder().withDescription("Buy Pencil").build()));

        // Only one matching keyword
        predicate = new TaskContainsKeywordPredicate(Arrays.asList("Pencil", "Pen"));
        assertTrue(predicate.test(new TaskBuilder().withDescription("Buy Pen").build()));

        // Mixed-case keywords
        predicate = new TaskContainsKeywordPredicate(Arrays.asList("buY", "pEnciL"));
        assertTrue(predicate.test(new TaskBuilder().withDescription("Buy Pencil").build()));
    }

    @Test
    public void priorityMatches() {
        // Priority level equal to required level
        TaskContainsKeywordPredicate predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Pencil"),
                false, true, false, 3);
        assertTrue(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").withPriority("3").build()));

        // Priority level greater than required level
        predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Pencil"),
                false, true, false, 3);
        assertTrue(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").withPriority("4").build()));

        // Priority level less than required level
        predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Pencil"),
                false, true, false, 3);
        assertFalse(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").withPriority("1").build()));

        // Name matches, priority check not required, even though priority level does not match
        predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Pencil"),
                false, false, false, 3);
        assertTrue(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").withPriority("1").build()));

        // Priority matches, but name does not match
        predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Something"),
                false, true, false, 3);
        assertFalse(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").withPriority("3").build()));
    }

    @Test
    public void stateMatches() {
        // States are equivalent
        TaskContainsKeywordPredicate predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Pencil"),
                true, false, true, 0);
        assertTrue(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").withState(true).build()));

        // States are different
        predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Pencil"),
                true, false, false, 0);
        assertFalse(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").withState(true).build()));

        // Name matches, state check not required, even though state does not match
        predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Pencil"),
                false, false, false, 0);
        assertTrue(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").withState(true).build()));

        // State matches, but name does not match
        predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Something"),
                true, false, true, 0);
        assertFalse(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").withState(true).build()));
    }


    @Test
    public void combinationTests() {
        // At most 1 invalid input per test case

        // Matches all
        TaskContainsKeywordPredicate predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Pencil"),
                true, true, true, 3);
        assertTrue(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").withDescription("Get 3 Pencil now")
                .withPriority("4").withState(true).build()));

        // Name does not match, but description does
        predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Pencil"),
                true, true, true, 3);
        assertTrue(predicate.test(new TaskBuilder().withTaskName("Get something").withDescription("Get 3 Pencil now")
                .withPriority("4").withState(true).build()));

        // Description does not match, but name does
        predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Pencil"),
                true, true, true, 3);
        assertTrue(predicate.test(new TaskBuilder().withTaskName("Buy 3 Pencil").withDescription("Get something")
                .withPriority("4").withState(true).build()));

        // Name or description matches, but priority does not
        predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Pencil"),
                true, true, true, 3);
        assertFalse(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").withDescription("Get 3 Pencils now")
                .withPriority("2").withState(true).build()));

        // Name or description matches, but state does not
        predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Pencil"),
                true, true, true, 3);
        assertFalse(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").withDescription("Get 3 Pencils now")
                .withPriority("4").withState(false).build()));
    }
}
```
###### \java\seedu\address\model\task\TaskNameTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TaskNameTest {

    @Test
    public void isValidName() {
        // invalid names
        assertFalse(TaskName.isValidName(" ")); // spaces only
        assertFalse(TaskName.isValidName("")); // empty string

        // valid names
        assertTrue(TaskName.isValidName(".")); // single character
        assertTrue(TaskName.isValidName("buy pencil")); // alphanumerical with spaces
        assertTrue(TaskName.isValidName("buy pencil!")); // special symbols
    }
}
```
###### \java\seedu\address\testutil\EditTaskDescriptorBuilder.java
``` java
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
     * Returns an {@code EditTaskDescriptor} with fields containing {@code task}'s details
     */
    public EditTaskDescriptorBuilder(ReadOnlyTask task) {
        descriptor = new EditTaskDescriptor();
        descriptor.setTaskName(task.getTaskName());
        descriptor.setDescription(task.getDescription());
        descriptor.setDeadline(task.getDeadline());
        descriptor.setPriority(task.getPriority());
        descriptor.setTaskAddress(task.getTaskAddress());
    }

    /**
     * Sets the {@code TaskName} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withTaskName(String name) {
        try {
            ParserUtil.parseTaskName(Optional.of(name)).ifPresent(descriptor::setTaskName);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withDescription(String description) {
        try {
            ParserUtil.parseDescription(Optional.of(description)).ifPresent(descriptor::setDescription);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("description is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withDeadline(String deadline) {
        try {
            ParserUtil.parseDeadline(Optional.of(deadline)).ifPresent(descriptor::setDeadline);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("deadline is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withPriority(String priority) {
        try {
            ParserUtil.parsePriority(Optional.of(priority)).ifPresent(descriptor::setPriority);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("priority is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code TaskAddress} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withTaskAddress(String address) {
        try {
            ParserUtil.parseTaskAddress(Optional.of(address)).ifPresent(descriptor::setTaskAddress);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

    public EditTaskDescriptor build() {
        return descriptor;
    }
}
```
###### \java\seedu\address\testutil\TaskBuilder.java
``` java
import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Assignees;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskAddress;
import seedu.address.model.task.TaskName;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_NAME = "Buy pencil";
    public static final String DEFAULT_DESCRIPTION = "Buy a pencil from ABS by tomorrow";
    public static final String DEFAULT_DEADLINE = "04-04-2017";
    public static final String DEFAULT_PRIORITY = "4";
    public static final String DEFAULT_ADDRESS = "12 Kent Ridge Crescent, 119275";

    private Task task;

    public TaskBuilder() {
        try {
            TaskName defaultTaskName = new TaskName(DEFAULT_NAME);
            Description defaultDescription = new Description(DEFAULT_DESCRIPTION);
            Deadline defaultDeadline = new Deadline(DEFAULT_DEADLINE);
            Priority defaultPriority = new Priority(DEFAULT_PRIORITY);
            Assignees defaultAssignees = new Assignees();
            Boolean defaultState = false;
            TaskAddress defaultAddress = new TaskAddress(DEFAULT_ADDRESS);
            this.task = new Task(defaultTaskName, defaultDescription, defaultDeadline, defaultPriority,
                    defaultAssignees, defaultState, defaultAddress);
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
     * Sets the {@code TaskName} of the {@code Task} that we are building.
     */
    public TaskBuilder withTaskName(String name) {
        try {
            this.task.setTaskName(new TaskName(name));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code Task} that we are building.
     */
    public TaskBuilder withPriority(String priority) {
        try {
            this.task.setPriority(new Priority(priority));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("priority is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Task} that we are building.
     */
    public TaskBuilder withDescription(String description) {
        try {
            this.task.setDescription(new Description(description));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("description is expected to be unique.");
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

    /**
     * Sets the state of the {@code Task} that we are building.
     */
    public TaskBuilder withState(boolean state) {
        this.task.setState(state);
        return this;
    }

    /**
     * Sets the {@code TaskAddress} of the {@code Task} that we are building.
     */
    public TaskBuilder withTaskAddress(String address) {
        try {
            this.task.setTaskAddress(new TaskAddress(address));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Assignees} of the {@code Task} that we are building.
     */
    public TaskBuilder withAssignees(String... args) {
        ArrayList<Index> indexes = new ArrayList<>();
        for (String s : args) {
            indexes.add(Index.fromOneBased(Integer.parseInt(s)));
        }
        this.task.setAssignees(new Assignees(indexes));
        return this;
    }

    public Task build() {
        return this.task;
    }
}
```
###### \java\seedu\address\testutil\TaskUtil.java
``` java
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Utility class for Tasks
 */
public class TaskUtil {

    /**
     * Returns an add command string for adding the {@code task}.
     */
    public static String getAddCommand(ReadOnlyTask task) {
        return AddTaskCommand.COMMAND_WORD + " " + PREFIX_TASK + " " + getTaskDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code task}'s details.
     */
    public static String getTaskDetails(ReadOnlyTask task) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + task.getTaskName().taskName + " ");
        sb.append(PREFIX_DESCRIPTION + task.getDescription().value + " ");
        sb.append(PREFIX_DEADLINE + task.getDeadline().value + " ");
        sb.append(PREFIX_PRIORITY + Integer.toString(task.getPriority().value) + " ");
        sb.append(PREFIX_ADDRESS + task.getTaskAddress().taskAddress + " ");

        return sb.toString();
    }
}
```
###### \java\seedu\address\testutil\TypicalTasks.java
``` java
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_ADDRESS_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_ADDRESS_PENCIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_PENCIL;

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
    public static final ReadOnlyTask ACCEPT = new TaskBuilder().withTaskName("Acceptance Testing")
            .withDescription("Perform acceptance testing on application")
            .withDeadline("04-04-2017").withPriority("3").withAssignees("4")
            .withTaskAddress("21 Heng Mui Keng Terrace, #02-01-01 I-Cube Building").build();
    public static final ReadOnlyTask BUY = new TaskBuilder().withTaskName("Buy pencil")
            .withDescription("Buy pencils for tomorrow's test").withState(true)
            .withDeadline("04-04-2017").withPriority("5").withTaskAddress("Tampines Mall").build();
    public static final ReadOnlyTask COOK = new TaskBuilder().withTaskName("Cook Paella")
            .withDescription("Cook Paella for 4 people tonight")
            .withDeadline("11-04-2016").withPriority("5").withState(true).withTaskAddress("27 Prince George's Park")
            .build();
    public static final ReadOnlyTask DATE = new TaskBuilder().withTaskName("Date with Lucy")
            .withDescription("Sunday, 10am at Central Park")
            .withDeadline("21-05-2015").withPriority("5").withTaskAddress("Central Park").build();
    public static final ReadOnlyTask ESCAPE = new TaskBuilder().withTaskName("Escape dungeon")
            .withDescription("Escape dungeon group formation")
            .withDeadline("04-04-2017").withPriority("1").withTaskAddress("16 Gemmill Ln").build();
    public static final ReadOnlyTask FREE = new TaskBuilder().withTaskName("Free memory space")
            .withDescription("Implement new version of free()")
            .withDeadline("21-08-2019").withPriority("2").withState(true).withAssignees("1", "2", "3")
            .withTaskAddress("NUS School of Computing, COM1, 13 Computing Drive, 117417").build();
    public static final ReadOnlyTask GRADLE = new TaskBuilder().withTaskName("Resolve gradle")
            .withDescription("Resolve gradle problems when building project")
            .withDeadline("06-06-2016").withPriority("5")
            .withTaskAddress("Changi Airport").build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final ReadOnlyTask PENCIL = new TaskBuilder().withTaskName(VALID_TASK_NAME_PENCIL)
            .withDescription(VALID_DESCRIPTION_PENCIL)
            .withDeadline(VALID_DEADLINE_PENCIL).withPriority(VALID_PRIORITY_PENCIL)
            .withTaskAddress(VALID_TASK_ADDRESS_PENCIL).build();
    public static final ReadOnlyTask PAPER = new TaskBuilder().withTaskName(VALID_TASK_NAME_PAPER)
            .withDescription(VALID_DESCRIPTION_PAPER)
            .withDeadline(VALID_DEADLINE_PAPER).withPriority(VALID_PRIORITY_PAPER)
            .withTaskAddress(VALID_TASK_ADDRESS_PAPER).build();

    public static final String KEYWORD_MATCHING_LUCY = "Lucy"; // A keyword that matches LUCY

    private TypicalTasks() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalTasksOnlyAddressBook() {
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
        return new ArrayList<>(Arrays.asList(ACCEPT, BUY, COOK, DATE, ESCAPE, FREE, GRADLE));
    }
}
```
