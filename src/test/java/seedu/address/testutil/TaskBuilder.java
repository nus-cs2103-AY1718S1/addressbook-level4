package seedu.address.testutil;
//@@author Esilocke
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
