//@@author Hailinx
package seedu.address.storage;

import static seedu.address.model.util.TimeConvertUtil.convertStringToTime;
import static seedu.address.model.util.TimeConvertUtil.convertTimeToString;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.TodoItem;

/**
 * JAXB-friendly adapted version of the TodoItem.
 */
public class XmlAdapterTodoItem {

    @XmlElement(required = true)
    private String startTimeStr;
    @XmlElement
    private String endTimeStr;
    @XmlElement(required = true)
    private String task;

    /**
     * Constructs an XmlAdapterTodoItem.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdapterTodoItem() {}

    /**
     * Converts a given TodoItem into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdapterTodoItem
     */
    public XmlAdapterTodoItem(TodoItem source) {
        startTimeStr = convertTimeToString(source.start);
        endTimeStr = convertTimeToString(source.end);
        task = source.task;
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's TodoItem object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted todoItem
     */
    public TodoItem toModelType() throws IllegalValueException {
        LocalDateTime end = null;
        if (endTimeStr != null && !endTimeStr.isEmpty()) {
            end = convertStringToTime(endTimeStr);
        }

        return new TodoItem(convertStringToTime(startTimeStr), end, task);
    }
}
