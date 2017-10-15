package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.lecturer.Lecturer;
import seedu.address.model.module.ClassType;
import seedu.address.model.module.Code;
import seedu.address.model.module.Group;
import seedu.address.model.module.Lesson;
import seedu.address.model.module.Location;
import seedu.address.model.module.TimeSlot;
import seedu.address.model.module.exceptions.DuplicateLessonException;


/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Lesson[] getSampleLessons() {
        try {
            return new Lesson[]{
                new Lesson(new ClassType("LEC"), new Location("LT19"), new Group("SL1"),
                    new TimeSlot("FRI[1400-1600]"), new Code("CS2103T"), getLecturerSet("Damith")),
                new Lesson(new ClassType("TUT"), new Location("COM1-0207"), new Group("T01"),
                    new TimeSlot("TUE[1000-1100]"), new Code("CS2103T"), getLecturerSet("David")),
                new Lesson(new ClassType("LEC"), new Location("LT27"), new Group("SL1"),
                    new TimeSlot("WED[1200-1400]"), new Code("MA1101R"), getLecturerSet("Ma Siu Lun")),
                new Lesson(new ClassType("TUT"), new Location("LT27"), new Group("T01"),
                    new TimeSlot("MON[0900-1000]"), new Code("MA1101R"), getLecturerSet("LI Sheng")),
                new Lesson(new ClassType("TUT"), new Location("LT27"), new Group("T02"),
                    new TimeSlot("MON[1000-1100]"), new Code("MA1101R"), getLecturerSet("LI Sheng")),
                new Lesson(new ClassType("LEC"), new Location("LT27"), new Group("SL2"),
                    new TimeSlot("THU[1600-1800]"), new Code("MA1101R"), getLecturerSet("Smith"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }


    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Lesson sampleLesson : getSampleLessons()) {

                sampleAb.addLesson(sampleLesson);
            }
            return sampleAb;
        } catch (DuplicateLessonException e) {
            throw new AssertionError("sample data cannot contain duplicate lessons", e);
        }
    }

    /**
     * Returns a lecturer set containing the list of strings given.
     */
    public static Set<Lecturer> getLecturerSet(String... strings) throws IllegalValueException {
        HashSet<Lecturer> lecturers = new HashSet<>();
        for (String s : strings) {
            lecturers.add(new Lecturer(s));
        }

        return lecturers;
    }

}
