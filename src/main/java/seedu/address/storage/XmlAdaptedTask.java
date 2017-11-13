package seedu.address.storage;
//@@author Esilocke
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

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


/** JAXB-friendly version of a Task */
public class XmlAdaptedTask {
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private String deadline;
    @XmlElement(required = true)
    private String priority;
    @XmlElement(required = true)
    private String state;
    @XmlElement
    private List<XmlAdaptedIndex> assignees = new ArrayList<>();
    @XmlElement(required = true)
    private String address;

    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getTaskName().taskName;
        description = source.getDescription().value;
        deadline = source.getDeadline().value;
        priority = Integer.toString(source.getPriority().value);
        state = String.valueOf(source.getCompleteState());
        address = source.getTaskAddress().taskAddress;
        assignees = new ArrayList<>();
        for (Index i : source.getAssignees().getList()) {
            assignees.add(new XmlAdaptedIndex(i));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Task toModelType() throws IllegalValueException {
        final TaskName name = new TaskName(this.name);
        final Description description = new Description(this.description);
        final Deadline deadline = new Deadline(this.deadline);
        final Priority priority = new Priority(this.priority);
        final Boolean state = Boolean.valueOf(this.state);
        final TaskAddress address = new TaskAddress(this.address);
        final ArrayList<Index> assigneeIndexes = new ArrayList<>();
        for (XmlAdaptedIndex index : assignees) {
            assigneeIndexes.add(index.toModelType());
        }
        final Assignees assignees = new Assignees(assigneeIndexes);
        return new Task(name, description, deadline, priority, assignees, state, address);
    }
}
