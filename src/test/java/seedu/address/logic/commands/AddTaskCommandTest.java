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
import seedu.address.logic.commands.tasks.AddTaskCommand;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.exceptions.TagNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
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
        AddTaskCommandTest.ModelStubAcceptingTaskAdded modelStub = new AddTaskCommandTest.ModelStubAcceptingTaskAdded();
        Task validTask = new TaskBuilder().build();

        CommandResult commandResult = getAddTaskCommandForTask(validTask, modelStub).execute();

        assertEquals(String.format(AddTaskCommand.MESSAGE_SUCCESS, validTask), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() throws Exception {
        AddTaskCommandTest.ModelStub modelStub = new AddTaskCommandTest.ModelStubThrowingDuplicateTaskException();
        Task validTask = new TaskBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddTaskCommand.MESSAGE_DUPLICATE_TASK);

        getAddTaskCommandForTask(validTask, modelStub).execute();
    }

    @Test
    public void equals() {
        Task assignment = new TaskBuilder().withDescription("Complete assignment").build();
        Task project = new TaskBuilder().withDescription("Project due").build();
        AddTaskCommand addAssignmentCommand = new AddTaskCommand(assignment);
        AddTaskCommand addProjectCommand = new AddTaskCommand(project);

        // same object -> returns true
        assertTrue(addAssignmentCommand.equals(addAssignmentCommand));

        // same values -> returns true
        AddTaskCommand addAssignmentCommandCopy = new AddTaskCommand(assignment);
        assertTrue(addAssignmentCommand.equals(addAssignmentCommandCopy));

        // different types -> returns false
        assertFalse(addAssignmentCommand.equals(1));

        // null -> returns false
        assertFalse(addAssignmentCommand.equals(null));

        // different task -> returns false
        assertFalse(addAssignmentCommand.equals(addProjectCommand));
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
        public void deleteTag(ReadOnlyPerson person, Tag tag) throws PersonNotFoundException,
                DuplicatePersonException, TagNotFoundException {
            fail("This method must not be called.");
        }

        @Override
        /** Add tag of given person */
        public void attachTag(ReadOnlyPerson person, Tag tag) throws PersonNotFoundException,
                DuplicatePersonException, UniqueTagList.DuplicateTagException {
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
        public void addTask(ReadOnlyTask task) throws DuplicateTaskException {
            fail("This method should not be called");
        }

        @Override
        public void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
            fail("This method should not be called");
        }

        @Override
        public void updateTask(ReadOnlyTask target, ReadOnlyTask editedTask)
                throws DuplicateTaskException, TaskNotFoundException {
            fail("This method should not be called");
        }

        @Override
        public ObservableList<ReadOnlyTask> getFilteredTaskList() {
            fail("This method should not be called");
            return null;
        }

        @Override
        public void updateFilteredTaskList(Predicate<ReadOnlyTask> predicate) {
            fail("This method should not be called");
        }
    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to add a person.
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
     * A Model stub that always accepts the task being added.
     */
    private class ModelStubAcceptingTaskAdded extends AddTaskCommandTest.ModelStub {
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
