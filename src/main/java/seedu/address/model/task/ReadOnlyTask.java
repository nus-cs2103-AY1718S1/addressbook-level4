package seedu.address.model.task;

import javafx.beans.property.ObjectProperty;

//@@author Esilocke
/**
 * Provides an immutable interface for a Task in the address book.
 */
public interface ReadOnlyTask {

    TaskName getTaskName();
    Description getDescription();
    Deadline getDeadline();
    Priority getPriority();
    Assignees getAssignees();
    boolean getCompleteState();
    String getPrintableState();
    TaskAddress getTaskAddress();
    ObjectProperty<TaskName> taskNameProperty();
    ObjectProperty<Description> descriptionProperty();
    ObjectProperty<Deadline> deadlineProperty();
    ObjectProperty<Priority> priorityProperty();
    ObjectProperty<Assignees> assigneeProperty();
    ObjectProperty<TaskAddress> taskAddressProperty();
    ObjectProperty<String> stateProperty();
    ObjectProperty<String> changeStateProperty();
    void changeState();

    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTaskName())
                .append(" Description: ")
                .append(getDescription())
                .append(" Deadline: ")
                .append(getDeadline())
                .append(" Priority: ")
                .append(getPriority())
                .append(" Address: ")
                .append(getTaskAddress())
                .append(" ")
                .append(getPrintableState());
        return builder.toString();
    }

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTaskName().equals(this.getTaskName()) // state checks here onwards
                && other.getDescription().equals(this.getDescription())
                && other.getDeadline().equals(this.getDeadline())
                && other.getPriority().equals(this.getPriority()))
                && other.getAssignees().equals(this.getAssignees())
                && other.getCompleteState() == this.getCompleteState()
                && other.getTaskAddress().equals(this.getTaskAddress());
    }
}
