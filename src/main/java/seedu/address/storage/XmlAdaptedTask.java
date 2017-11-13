//@@author srishag
package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Desc;
import seedu.address.model.task.Header;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String header;
    @XmlElement(required = true)
    private String desc;
    @XmlElement(required = true)
    private String deadline;

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
        header = source.getHeader().value;
        desc = source.getDesc().value;
        deadline = source.getDeadline().value;
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final Header header = new Header(this.header);
        final Desc desc = new Desc(this.desc);
        final Deadline deadline = new Deadline(this.deadline);
        return new Task(header, desc, deadline);
    }
}
