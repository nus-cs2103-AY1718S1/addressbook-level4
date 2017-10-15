package seedu.address.storage;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.StartDate;
import seedu.address.model.task.Task;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private Date startDate;
    @XmlElement(required = true)
    private Date deadline;

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
        description = source.getDescription().taskDescription;
        startDate = source.getStartDate().date;
        deadline = source.getDeadline().date;
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final Description description = new Description(this.description);
        final StartDate startDate = new StartDate(this.startDate);
        final Deadline deadline = new Deadline(this.deadline);
        return new Task(description, startDate, deadline);
    }
}
