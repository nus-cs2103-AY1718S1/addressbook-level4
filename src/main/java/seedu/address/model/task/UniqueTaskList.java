package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

//@@author Esilocke
/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {

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
     * Replaces the task {@code target} in the list with {@code editedTask}.
     *
     * @throws DuplicateTaskException if the replacement is equivalent to another existing task in the list.
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
        return true;
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
     * Removes all assignees from all tasks.
     */
    public void clearAssignees() {
        for (Task t : internalList) {
            t.clearAssignees();
        }
    }

    /** Removes the specified assignee from all tasks **/
    public void removeAssignee(Index personIndex) {
        ObservableList<Task> internalListCopy = FXCollections.observableArrayList();
        for (Task t : internalList) {
            TaskName name = t.getTaskName();
            Description description = t.getDescription();
            Deadline deadline = t.getDeadline();
            Priority priority = t.getPriority();
            TaskAddress taskAddress = t.getTaskAddress();
            boolean state = t.getCompleteState();
            Assignees assignees = t.getAssignees();

            Assignees updated = new Assignees(assignees);
            updated.decrementIndex(personIndex);
            internalListCopy.add(new Task(name, description, deadline, priority, updated, state, taskAddress));
        }
        internalList.clear();
        internalList.addAll(internalListCopy);
    }

    /**
     * Updates the assignee list within each task to match that of the newPersonIndexes.
     * This method is called after a sort persons operation due to the order change
     */
    public void updateAssignees(Index[] newPersonIndexes) {
        ObservableList<Task> internalListCopy = FXCollections.observableArrayList();
        for (Task t : internalList) {
            TaskName name = t.getTaskName();
            Description description = t.getDescription();
            Deadline deadline = t.getDeadline();
            Priority priority = t.getPriority();
            TaskAddress taskAddress = t.getTaskAddress();
            boolean state = t.getCompleteState();
            Assignees assignees = t.getAssignees();

            Assignees updated = new Assignees(assignees);
            updated.updateList(newPersonIndexes);
            internalListCopy.add(new Task(name, description, deadline, priority, updated, state, taskAddress));
        }
        internalList.clear();
        internalList.addAll(internalListCopy);
    }
    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyTask> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    /**
     * Sorts person list by all persons by any field in ascending or descending order
     */
    //@@author charlesgoh
    public void sortBy(String field, String order) {
        //sortyBy first chooses the right comparator
        Comparator<Task> comparator = null;

        /*
         * Comparators for the various fields available for sorting
         */
        Comparator<Task> priorityComparator = Comparator.comparingInt(o -> o.getPriority().value);

        Comparator<Task> deadlineComparator = (o1, o2) -> {
            if (o1.getDeadline().date == null || o2.getDeadline().date == null) {
                return 0;
            } else {
                return o1.getDeadline().date.compareTo(o2.getDeadline().date);
            }
        };

        switch (field) {
        case "priority":
            comparator = priorityComparator;
            break;

        case "deadline":
            comparator = deadlineComparator;
            break;

        default:
            try {
                System.out.println("An error occured");
                throw new Exception("Invalid field parameter entered...\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //sortBy then chooses the right ordering
        switch (order) {
        case "asc":
            Collections.sort(internalList, comparator);
            break;

        case "desc":
            Collections.sort(internalList, Collections.reverseOrder(comparator));
            break;

        default:
            try {
                System.out.println("An error occured");
                throw new Exception("Invalid field parameter entered...\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    //@@author

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
