package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.task.AddTaskCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.alias.ReadOnlyAliasToken;
import seedu.address.model.alias.exceptions.DuplicateTokenKeywordException;
import seedu.address.model.alias.exceptions.TokenKeywordNotFoundException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;
import seedu.address.testutil.TaskBuilder;

public class AddTaskCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void execute_taskAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        Task validTask = new TaskBuilder().build();

        CommandResult commandResult = getAddCommandForTask(validTask, modelStub).execute();

        assertEquals(String.format(AddTaskCommand.MESSAGE_SUCCESS, validTask), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateTaskException();
        Task validTask = new TaskBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddTaskCommand.MESSAGE_DUPLICATE_TASK);

        getAddCommandForTask(validTask, modelStub).execute();
    }

    @Test
    public void equals() {
        Task study = buildTask("Study");
        Task play = buildTask("Play");

        AddTaskCommand addStudyCommand = new AddTaskCommand(study);
        AddTaskCommand addPlayCommand = new AddTaskCommand(play);

        // same object -> returns true
        assertTrue(addStudyCommand.equals(addStudyCommand));

        // same values -> returns true
        AddTaskCommand addStudyCommandCopy = new AddTaskCommand(study);
        assertTrue(addStudyCommand.equals(addStudyCommandCopy));

        // different types -> returns false
        assertFalse(addStudyCommand.equals(1));

        // null -> returns false
        assertFalse(addStudyCommand.equals(null));

        // different person -> returns false
        assertFalse(addStudyCommand.equals(addPlayCommand));
    }

    /**
     * Builds a Task object with only the header
     */
    private static Task buildTask(String header) {
        try {
            return new TaskBuilder().withHeader(header).withIncompletionStatus().build();
        } catch (IllegalValueException ive) {
            assert false : "Not possible";
            return null;
        }
    }

    /**
     * Generates a new AddTaskCommand with the details of the given Task.
     */
    private AddTaskCommand getAddCommandForTask(Task task, Model model) throws IllegalValueException {
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
        public void sortList(String toSort) {
            fail("This method should not be called.");
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void hidePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void pinPerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void unpinPerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
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
        public void addAliasToken(ReadOnlyAliasToken target) throws DuplicateTokenKeywordException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteAliasToken(ReadOnlyAliasToken target) throws TokenKeywordNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public int getAliasTokenCount() {
            fail("This method should not be called.");
            return 0;
        }

        @Override
        public ObservableList<ReadOnlyAliasToken> getFilteredAliasTokenList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void addTask(ReadOnlyTask target) throws DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public void updateTask(ReadOnlyTask target, ReadOnlyTask updatedTask)
                throws TaskNotFoundException, DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public void markTasks(List<ReadOnlyTask> targets)
                throws TaskNotFoundException, DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public void unmarkTasks(List<ReadOnlyTask> targets)
                throws TaskNotFoundException, DuplicateTaskException {
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
