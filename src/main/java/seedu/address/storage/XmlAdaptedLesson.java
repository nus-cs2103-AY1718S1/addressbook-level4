package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.*;
import seedu.address.model.Lecturer.Lecturer;

import javax.xml.bind.annotation.XmlElement;

public class XmlAdaptedLesson {
    @XmlElement(required = true)
    private String classType;
    @XmlElement(required = true)
    private String location;
    @XmlElement(required = true)
    private String group;
    @XmlElement(required = true)
    private String timeSlot;
    @XmlElement
    private String lecturer;

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedLesson() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedLesson(ReadOnlyLesson source) {
        classType = source.getClassType().value;
        location = source.getLocation().value;
        group = source.getGroup().value;
        timeSlot = source.getTimeSlot().value;
        lecturer = source.getLecturer().lecturerName;
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Lesson toModelType() throws IllegalValueException {
        final ClassType classType = new ClassType(this.classType);
        final Location location = new Location(this.location);
        final Group group = new Group(this.group);
        final TimeSlot timeSLot = new TimeSlot(this.timeSlot);
        final Lecturer lecturer = new Lecturer(this.lecturer);
        return new Lesson(classType, location, group, timeSLot, lecturer);
    }
}
