package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.Suffix;
import seedu.address.model.tag.Tag;
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
    @XmlElement
    private String startDate;
    private Suffix startDateRecurInterval;
    @XmlElement
    private String deadline;
    private Suffix deadlineRecurInterval;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();


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
        startDateRecurInterval = source.getStartDate().recurInterval;
        deadline = source.getDeadline().date;
        deadlineRecurInterval = source.getDeadline().recurInterval;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Description description = new Description(this.description);
        final StartDate startDate = new StartDate(this.startDate, this.startDateRecurInterval);
        final Deadline deadline = new Deadline(this.deadline, this.deadlineRecurInterval);
        final Set<Tag> tags = new HashSet<>(taskTags);
        return new Task(description, startDate, deadline, tags);
    }
}
