package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

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

