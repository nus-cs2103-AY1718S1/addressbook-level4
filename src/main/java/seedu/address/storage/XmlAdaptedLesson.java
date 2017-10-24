package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.lecturer.Lecturer;
import seedu.address.model.module.ClassType;
import seedu.address.model.module.Code;
import seedu.address.model.module.Group;
import seedu.address.model.module.Lesson;
import seedu.address.model.module.Location;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.TimeSlot;







/**
 * Stores lesson data in an XML file
 */
public class XmlAdaptedLesson {
    @XmlElement(required = true)
    private String classType;
    @XmlElement(required = true)
    private String location;
    @XmlElement(required = true)
    private String group;
    @XmlElement(required = true)
    private String code;
    @XmlElement(required = true)
    private String timeSlot;
    @XmlElement(required = true)
    private Boolean isMarked;

    @XmlElement
    private List<XmlAdaptedLecturer> lecturerList = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedLesson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedLesson() {}


    /**
     * Converts a given Lesson into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedLesson
     */
    public XmlAdaptedLesson(ReadOnlyLesson source) {
        classType = source.getClassType().value;
        location = source.getLocation().value;
        group = source.getGroup().value;
        code = source.getCode().fullCodeName;
        timeSlot = source.getTimeSlot().value;
        isMarked = source.isMarked();

        lecturerList = new ArrayList<>();
        for (Lecturer lecturer : source.getLecturers()) {
            lecturerList.add(new XmlAdaptedLecturer(lecturer));
        }
    }

    /**
     * Converts this jaxb-friendly adapted lesson object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted lesson
     */
    public Lesson toModelType() throws IllegalValueException {
        final ClassType classType = new ClassType(this.classType);
        final Location location = new Location(this.location);
        final Group group = new Group(this.group);
        final Code code = new Code(this.code);
        final TimeSlot timeSLot = new TimeSlot(this.timeSlot);
        final Boolean isMarked = this.isMarked;

        final List<Lecturer> lecturers = new ArrayList<>();
        for (XmlAdaptedLecturer lecturer : lecturerList) {
            lecturers.add(lecturer.toModelType());
        }
        final Set<Lecturer> lecturerSet = new HashSet<>(lecturers);
        Lesson lesson = new Lesson(classType, location, group, timeSLot, code, lecturerSet);
        if (isMarked) {
            lesson.setAsMarked();
        }
        return lesson;
    }
}
