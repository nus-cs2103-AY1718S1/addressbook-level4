package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Header;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @XmlElement(required = true)
    private String header;
    @XmlElement(required = true)
    private String isCompleted;
    @XmlElement(required = true)
    private String lastUpdatedTime;
    @XmlElement(required = false)
    private String startDateTime;
    @XmlElement(required = false)
    private String endDateTime;

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask() {
    }


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        header = source.getHeader().header;
        isCompleted = Boolean.toString(source.isCompleted());
        lastUpdatedTime = source.getLastUpdatedTime().format(formatter);

        if (source.getStartDateTime().isPresent()) {
            startDateTime = source.getStartDateTime().get().format(formatter);
        }

        if (source.getEndDateTime().isPresent()) {
            endDateTime = source.getEndDateTime().get().format(formatter);
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final Header header = new Header(this.header);
        final boolean setAsCompleted = Boolean.valueOf(isCompleted);

        Task newTask = new Task(header);

        if (lastUpdatedTime != null) {
            newTask.setLastUpdatedTime(LocalDateTime.parse(lastUpdatedTime, formatter));
        } else {
            newTask.setLastUpdatedTimeToCurrent();
        }

        if (setAsCompleted) {
            newTask.setComplete();
        }

        if (startDateTime != null) {
            newTask.setStartDateTime(Optional.ofNullable(LocalDateTime.parse(startDateTime, formatter)));
        }

        if (endDateTime != null) {
            newTask.setEndDateTime(Optional.ofNullable(LocalDateTime.parse(endDateTime, formatter)));
        }

        return newTask;
    }
}
