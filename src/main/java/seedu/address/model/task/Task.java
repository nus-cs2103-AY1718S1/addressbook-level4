package seedu.address.model.task;

/**
 * This is a task class with only a name
 */
public class Task {
    private static Integer indexTask;

    private String taskName;
    private String taskDescription;
    private int taskIndex;


    /**
     * Default constructor may not be used
     */
    public Task () {
        if (indexTask == null) {
            indexTask = 1;
        }
        taskIndex = indexTask;
    }

    /**
     * Constructor with a name only to be passed in
     * @param name, the name of this task
     */
    public Task (String name, String description) {
        this();
        this.taskName = name;
        this.taskDescription = description;
    }

    /**
     * get index from this task
     * @return index
     */
    public int getIndex () {
        return taskIndex;
    }

    /**
     * get name from this task
     * @return name
     */
    public String getName () {
        return taskName;
    }

    /**
     * get description from this task
     * @return description
     */
    public String getTaskDescription () {
        return taskDescription;
    }
}
