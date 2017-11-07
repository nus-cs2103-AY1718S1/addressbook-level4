package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Description;
import seedu.address.model.task.DueDate;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;

//@@author zhangshuoyang
/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private String priority;
    @XmlElement(required = true)
    private String duedate;


    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask () {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param  source future changes to this will not affect the created XmlAdaptedTask
     */

    public XmlAdaptedTask(ReadOnlyTask source) {
        description = source.getDescription().descriptionName;
        priority = source.getPriority().value;
        duedate = source.getDueDate().date;
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public Task toModelType() throws IllegalValueException {
        final Description description = new Description(this.description);
        final Priority priority = new Priority(this.priority);
        final DueDate dueDate = new DueDate(this.duedate);
        return new Task(description, priority, dueDate);
    }
}


