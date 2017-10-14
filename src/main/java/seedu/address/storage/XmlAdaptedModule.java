package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.*;
import seedu.address.model.Lecturer.Lecturer;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class XmlAdaptedModule {
    @XmlElement(required = true)
    private String code;

    @XmlElement
    private List<XmlAdaptedLesson> lessonList = new ArrayList<>();

    @XmlElement
    private List<XmlAdaptedLecturer> lecturerList = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedModule() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedModule(ReadOnlyModule source) {
        code = source.getCode().fullCodeName;
        lessonList = new ArrayList<>();
        for (Lesson lesson : source.getUniqueLessonList()) {
            lessonList.add(new XmlAdaptedLesson(lesson));
        }
        lecturerList = new ArrayList<>();
        for (Lecturer lecturer : source.getLecturers()) {
            lecturerList.add(new XmlAdaptedLecturer(lecturer));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Module toModelType() throws IllegalValueException {
        final List<Lesson> lessons = new ArrayList<>();
        for (XmlAdaptedLesson lesson : lessonList) {
            lessons.add(lesson.toModelType());
        }
        final List<Lecturer> lecturers = new ArrayList<>();
        for (XmlAdaptedLecturer lecturer : lecturerList) {
            lecturers.add(lecturer.toModelType());
        }
        final Code code = new Code(this.code);
        UniqueLessonList lessonUniqueList = new UniqueLessonList();
        lessonUniqueList.setLessons(lessons);
        final Set<Lecturer> lecturerSet = new HashSet<>(lecturers);
        return new Module(code, lessonUniqueList ,lecturerSet);
    }
}
