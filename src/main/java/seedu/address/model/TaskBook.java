package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskBook implements ReadOnlyTaskBook {

    private final UniqueTaskList tasks;
    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        tasks = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public TaskBook() {}

    /**
     * Creates an AddressBook using the tasks and Tags in the {@code toBeCopied}
     */
    public TaskBook(ReadOnlyTaskBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setTasks(List<? extends ReadOnlyTask> tasks) throws DuplicateTaskException {
        this.tasks.setTasks(tasks);
    }

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyTaskBook newData) {
        requireNonNull(newData);
        try {
            setTasks(newData.getTaskList());
        } catch (DuplicateTaskException e) {
            assert false : "AddressBooks should not have duplicate tasks";
        }

        setTags(new HashSet<>(newData.getTagList()));
        syncMasterTagListWith(tasks);
    }

    //// task-level operations

    /**
     * Adds a task to the task book.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(ReadOnlyTask p) throws DuplicateTaskException {
        Task newTask = new Task(p);
        syncMasterTagListWith(newTask);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any task
        // in the task list.
        tasks.add(newTask);
    }

    /**
     * Marks an existing task to be completed.
     * @throws TaskNotFoundException if the task could not be found in the list..
     */
    public void markTask(ReadOnlyTask p) throws TaskNotFoundException {
        tasks.setComplete(p);
    }

    /**
     * Replaces the given task {@code target} in the list with {@code editedReadOnlyTask}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedReadOnlyTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncMasterTagListWith(Task)
     */
    public void updateTask(ReadOnlyTask target, ReadOnlyTask editedReadOnlyTask)
            throws DuplicateTaskException, TaskNotFoundException {
        requireNonNull(editedReadOnlyTask);

        Task editedTask = new Task(editedReadOnlyTask);
        syncMasterTagListWith(editedTask);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any task
        // in the task list.
        tasks.setTask(target, editedTask);
    }

    /**
     * Updates the given task with the new priority value provided.
     * @param target , the target task to be updated
     * @param value , the value to be updated for the task's new priority
     * @throws TaskNotFoundException
     * @throws DuplicateTaskException
     */
    public void updateTaskPriority(ReadOnlyTask target, Integer value)
            throws TaskNotFoundException, DuplicateTaskException {

        // Preliminary checking
        if (value < 0 || value > 5) {
            return;
        }

        Task updatedTask = new Task(
                target.getName(),
                target.getDescription(),
                target.getStartDateTime(),
                target.getEndDateTime(),
                target.getTags(),
                target.getComplete(),
                value);

        // syncMasterTagListWith(updatedTask);
        // Don't update master tags for now because this method doesn't modify the tags property.

        tasks.setTask(target, updatedTask);

    }


    /**
     * Ensures that every tag in this task:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(Task task) {
        final UniqueTagList taskTags = new UniqueTagList(task.getTags());
        tags.mergeFrom(taskTags);

        // Create map with values = tag object references in the master list
        // used for checking task tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of task tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        taskTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        task.setTags(correctTagReferences);
    }

    /**
     * Ensures that every tag in these tasks:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(UniqueTaskList tasks) {
        tasks.forEach(this::syncMasterTagListWith);
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

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return tasks.asObservableList().size() + " tasks, " + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        return tasks.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskBook // instanceof handles nulls
                && this.tasks.equals(((TaskBook) other).tasks)
                && this.tags.equalsOrderInsensitive(((TaskBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }
}
