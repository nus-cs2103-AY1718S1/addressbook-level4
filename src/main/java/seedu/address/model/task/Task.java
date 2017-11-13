package seedu.address.model.task;
//@@author Esilocke
import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Represents a task object in the address book.
 */
public class Task implements ReadOnlyTask {

    public static final String TASK_INCOMPLETE = "<Incomplete>";
    public static final String TASK_COMPLETE = "<Complete>";

    private ObjectProperty<TaskName> taskName;
    private ObjectProperty<Description> description;
    private ObjectProperty<Deadline> deadline;
    private ObjectProperty<Priority> priority;
    private ObjectProperty<Assignees> assignees;
    private ObjectProperty<Boolean> state;
    private ObjectProperty<TaskAddress> taskAddress;

    public Task(TaskName taskName, Description description, Deadline deadline, Priority priority,
                Assignees assignees, boolean isComplete, TaskAddress taskAddress) {
        this.taskName = new SimpleObjectProperty<>(taskName);
        this.description = new SimpleObjectProperty<>(description);
        this.deadline = new SimpleObjectProperty<>(deadline);
        this.priority = new SimpleObjectProperty<>(priority);
        this.assignees = new SimpleObjectProperty<>(assignees);
        this.state = new SimpleObjectProperty<>(isComplete);
        this.taskAddress = new SimpleObjectProperty<>(taskAddress);
    }

    /**
     * Creates a new Task object from the given arguments
     * New tasks will not have anyone assigned to them by default, and will be marked as incomplete
     * by default.
     */
    public Task(TaskName taskName, Description description, Deadline deadline, Priority priority,
                TaskAddress taskAddress) {
        this.taskName = new SimpleObjectProperty<>(taskName);
        this.description = new SimpleObjectProperty<>(description);
        this.deadline = new SimpleObjectProperty<>(deadline);
        this.priority = new SimpleObjectProperty<>(priority);
        this.assignees = new SimpleObjectProperty<>(new Assignees());
        this.state = new SimpleObjectProperty<>(false);
        this.taskAddress = new SimpleObjectProperty<>(taskAddress);
    }

    public Task(ReadOnlyTask task) {
        this(task.getTaskName(), task.getDescription(), task.getDeadline(), task.getPriority(),
                task.getAssignees(), task.getCompleteState(), task.getTaskAddress());
    }

    public TaskName getTaskName() {
        return taskName.get();
    }

    @Override
    public Description getDescription() {
        return description.get();
    }

    @Override
    public Deadline getDeadline() {
        return deadline.get();
    }

    @Override
    public Priority getPriority() {
        return priority.get();
    }

    @Override
    public Assignees getAssignees() {
        return assignees.get();
    }

    @Override
    public boolean getCompleteState() {
        return state.get();
    }

    @Override
    public TaskAddress getTaskAddress() {
        return taskAddress.get();
    }

    @Override
    public String toString() {
        return getAsText();
    }

    // JavaFX property functions
    @Override
    public ObjectProperty<TaskName> taskNameProperty() {
        return taskName;
    }

    @Override
    public ObjectProperty<Description> descriptionProperty() {
        return description;
    }

    @Override
    public ObjectProperty<Deadline> deadlineProperty() {
        return deadline;
    }

    @Override
    public ObjectProperty<Priority> priorityProperty() {
        return priority;
    }

    @Override
    public ObjectProperty<Assignees> assigneeProperty() {
        return assignees;
    }

    public void clearAssignees() {
        this.assignees = new SimpleObjectProperty<>(new Assignees());
    }

    @Override
    public ObjectProperty<TaskAddress> taskAddressProperty() {
        return taskAddress;
    }

    @Override
    public ObjectProperty<String> stateProperty() {
        String printableState = getPrintableState();
        return new SimpleObjectProperty<>(printableState);
    }

    @Override
    public ObjectProperty<String> changeStateProperty() {
        boolean state = getCompleteState();
        if (state) {
            return new SimpleObjectProperty<>("Set as incomplete");
        } else {
            return new SimpleObjectProperty<>("Set as complete");
        }
    }
    // Setters for TaskBuilder testing

    public void setTaskName(TaskName taskName) {
        this.taskName.set(taskName);
    }

    public void setDeadline(Deadline deadline) {
        this.deadline.set(deadline);
    }

    public void setDescription(Description description) {
        this.description.set(description);
    }

    public void setPriority(Priority priority) {
        this.priority.set(priority);
    }

    public void setAssignees(Assignees assignees) {
        this.assignees.set(assignees);
    }

    public void setState(boolean state) {
        this.state.set(state);
    }

    public void changeState() {
        this.setState(!this.getCompleteState());
    }

    public void setTaskAddress(TaskAddress taskAddress) {
        this.taskAddress.set(taskAddress);
    }

    public String getPrintableState() {
        String printableState;
        if (state.get()) {
            printableState = TASK_COMPLETE;
        } else {
            printableState = TASK_INCOMPLETE;
        }
        return printableState;
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ReadOnlyTask // instanceof handles nulls
            && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskName, description, deadline, priority, assignees, state);
    }
}
