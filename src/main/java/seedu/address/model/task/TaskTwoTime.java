package seedu.address.model.task;

/**
 * task modelling last part: task with a start time and an end time
 * We still model this kind of task as a class inherited from the task super class
 */
public class TaskTwoTime extends Task {
    private String startDateTime;
    private String endDateTime;

    /**
     * This only constructor we need as we require all four arguments to be passed in
     * @param name name of the task
     * @param description description of the task
     * @param startDateTime start date and time of this task
     * @param endDateTime end date and time of this task
     */
    public TaskTwoTime (String name, String description, String startDateTime, String endDateTime) {
        super (name, description);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * get the start date time of this task
     * @return the start and date time
     */
    String getStartDateTime () {
        return startDateTime;
    }

    /**
     * get the end date and time of this task
     * @return the end date and time
     */
    String getEndDateTime () {
        return endDateTime;
    }
}
