package seedu.address.testutil;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.lecturer.Lecturer;
import seedu.address.model.module.ClassType;
import seedu.address.model.module.Code;
import seedu.address.model.module.Group;
import seedu.address.model.module.Lesson;
import seedu.address.model.module.Location;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.TimeSlot;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class LessonBuilder {

    public static final String DEFAULT_CODE = "MA1101R";
    public static final String DEFAULT_CLASS_TYPE = "LEC";
    public static final String DEFAULT_LOCATION = "LT27";
    public static final String DEFAULT_GROUP = "1";
    public static final String DEFAULT_TIME_SLOT = "FRI[1400-1600]";
    public static final String DEFAULT_LECTURER = "Ma Siu Lun";
    private static final boolean DEFAULT_ISMARKED = true;

    private Lesson lesson;

    public LessonBuilder() {
        try {
            Code defaultCode = new Code(DEFAULT_CODE);
            ClassType defaultClassType = new ClassType(DEFAULT_CLASS_TYPE);
            Location defaultLocation = new Location(DEFAULT_LOCATION);
            TimeSlot defaultTimeSlot = new TimeSlot(DEFAULT_TIME_SLOT);
            Set<Lecturer> defaultLecturer = SampleDataUtil.getLecturerSet(DEFAULT_LECTURER);
            Group defaultGroup = new Group(DEFAULT_GROUP);
            this.lesson = new Lesson(defaultClassType, defaultLocation, defaultGroup, defaultTimeSlot, defaultCode,
                    defaultLecturer);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default lesson's details are invalid.");
        }
    }

    /**
     * Initializes the LessonBuilder with the data of {@code readOnlyLesson}.
     */
    public LessonBuilder(ReadOnlyLesson readOnlyLesson) {
        this.lesson = new Lesson(readOnlyLesson);
    }

    /**
     * Sets the {@code Code} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withCode(String code) {
        try {
            this.lesson.setCodeType(new Code(code));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("code is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses the {@code lecturers} into a {@code Set<Lecturers>} and set it to the {@code Lesson} that we are building.
     */
    public LessonBuilder withLecturers(String... lecturers) {
        try {
            this.lesson.setLecturers(SampleDataUtil.getLecturerSet(lecturers));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("lecturers' names are expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code TimeSlot} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withTimeSlot(String timeSlot) {
        try {
            this.lesson.setTimeSlot(new TimeSlot(timeSlot));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("time slot is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withLocation(String location) {
        try {
            this.lesson.setLocation(new Location(location));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("location is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Group} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withGroup(String group) {
        try {
            this.lesson.setGroupType(new Group(group));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("group is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code ClassType} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withClassType(String classType) {
        try {
            this.lesson.setClassType(new ClassType(classType));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("class type is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code isMarked} to marked of the {@code Lesson} that we are building.
     */
    public LessonBuilder withMarked() {
        this.lesson.setAsMarked();
        return this;
    }

    /**
     * Sets the {@code isMarked} to unmarked of the {@code Lesson} that we are building.
     */
    public LessonBuilder withUnmarked() {
        this.lesson.setAsUnmarked();
        return this;
    }

    public Lesson build() {
        return this.lesson;
    }

}
