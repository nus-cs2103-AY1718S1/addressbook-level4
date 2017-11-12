//@@author ShaocongDong
package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Description;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String taskName;
    @XmlElement(required = true)
    private String taskDescription;
    @XmlElement(required = true)
    private String startDateTime;
    @XmlElement(required = true)
    private String endDateTime;
    @XmlElement
    private Integer id;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    @XmlElement(required = true)
    private Boolean complete;
    @XmlElement
    private List<Integer> peopleIndices = new ArrayList<>();
    @XmlElement(required = true)
    private Integer priority;


    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        taskName = source.getName().toString();
        taskDescription = source.getDescription().toString();
        startDateTime = source.getStartDateTime().toString();
        endDateTime = source.getEndDateTime().toString();
        tagged = new ArrayList<>();
        id = source.getId();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        complete = source.getComplete();
        peopleIndices = source.getPeopleIds();
        priority = source.getPriority();
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        final Name taskName = new Name(this.taskName);
        final Description taskDescription = new Description(this.taskDescription);
        final DateTime startDateTime = new DateTime(this.startDateTime);
        final DateTime endDateTime = new DateTime(this.endDateTime);
        final Set<Tag> tags = new HashSet<>(personTags);
        final Boolean complete = this.complete;
        final ArrayList<Integer> peopleIndices = new ArrayList<>(this.peopleIndices);
        final Integer priority = this.priority;
        final Integer id = this.id;
        return new Task(taskName, taskDescription, startDateTime, endDateTime, tags, complete, priority, id,
                peopleIndices);
    }

}
