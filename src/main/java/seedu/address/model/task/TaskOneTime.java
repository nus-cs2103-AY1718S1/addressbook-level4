package seedu.address.model.task;

/**
 * This is task with one deadline / time point inherited from the task class
 */
public class TaskOneTime extends Task {

    private String dateTime;

    /**
     * Default constructor, just inherited from the task super class
     */
    public TaskOneTime () {
        super();
    }

    /**
     * Constructor with only the name and description,
     * should not be called explicitly as it is not a one time task but a task with only a name
     * @param name task name
     * @param description task description
     */
    public TaskOneTime (String name, String description) {
        super(name, description);
    }

    /**
     * Constructor with one argument, which is the date time.
     * @param dateTime date time of this task
     */
    public TaskOneTime (String dateTime) {
        this();
        this.dateTime = dateTime;
    }

    /**
     * Constructor with all of the attributes
     * @param name name of this task
     * @param description description of this task
     * @param dateTime date time of this task
     */
    public TaskOneTime (String name, String description, String dateTime) {
        this(name, description);
        this.dateTime = dateTime;
    }

    /**
     * get the date and time from this task
     * @return the date and time of this task
     */
    public String getDateTime () {
        return dateTime;
    }
}
