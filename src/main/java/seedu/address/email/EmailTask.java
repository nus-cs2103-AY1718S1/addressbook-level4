package seedu.address.email;

//@@author awarenessxz
/**
 * Keeps track of Current Email Command Task
 */
public class EmailTask {
    public static final String TASK_SEND = "send";
    public static final String TASK_CLEAR = "clear";

    private String task;

    public EmailTask() {
        task = "";
    }

    public EmailTask(String task) {
        this.task = task;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    /** Returns true if task is valid */
    public boolean isValid() {
        switch (task) {
        case TASK_CLEAR:
            return true;
        case TASK_SEND:
            return true;
        default:
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailTask // instanceof handles nulls
                && this.task.equals(((EmailTask) other).task));
    }
}
