package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSTYPE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSTYPE_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LECTURER_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LECTURER_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMESLOT_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMESLOT_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_MA1101R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.module.Code;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.exceptions.DuplicateLessonException;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalLessons {

    public static final ReadOnlyLesson MA1101R_L1 = new LessonBuilder().withCode("MA1101R")
            .withClassType("Lec").withLocation("LT27")
            .withGroup("1").withTimeSlot("MON[1600-1800]")
            .withLecturers("Ma Siu Lun").build();
    public static final ReadOnlyLesson MA1101R_L2 = new LessonBuilder().withCode("MA1101R")
            .withClassType("Lec").withLocation("LT27")
            .withGroup("2").withTimeSlot("TUE[0800-1000]")
            .withLecturers("Ma Siu Lun, Victor Tan").build();
    public static final ReadOnlyLesson GEQ_T66 = new LessonBuilder().withCode("GEQ1000")
            .withClassType("tut").withLocation("ERC02-08")
            .withGroup("66").withTimeSlot("FRI[1000-1200]")
            .withLecturers("Carl").build();
    public static final ReadOnlyLesson MA1101R_T1 = new LessonBuilder().withCode("MA1101R")
            .withClassType("Tut").withLocation("COM02-07")
            .withGroup("1").withTimeSlot("WED[0900-1000]")
            .withLecturers("Ma Siu Lun").build();
    public static final ReadOnlyLesson MA1101R_T2 = new LessonBuilder().withCode("MA1101R")
            .withClassType("Tut").withLocation("COM02-07")
            .withGroup("2").withTimeSlot("WED[1000-1100]")
            .withLecturers("Ma Siu Lun").build();
    public static final ReadOnlyLesson CS2101_L1 = new LessonBuilder().withCode("CS2101")
            .withClassType("Lec").withLocation("COM02-03")
            .withGroup("1").withTimeSlot("MON[1000-1200]")
            .withLecturers("Diana").build();
    public static final ReadOnlyLesson CS2101_L2 = new LessonBuilder().withCode("CS2101")
            .withClassType("Lec").withLocation("COM02-03")
            .withGroup("2").withTimeSlot("THU[1000-1200]")
            .withLecturers("Diana").build();

    // Manually added
    public static final ReadOnlyLesson CS2103_L1 = new LessonBuilder().withCode("CS2103")
            .withClassType("Lec").withLocation("LT19")
            .withGroup("1").withTimeSlot("FRI[1400-1600]")
            .withLecturers("Damith").build();
    public static final ReadOnlyLesson CS2103T_L1 = new LessonBuilder().withCode("CS2103T")
            .withClassType("Lec").withLocation("LT19")
            .withGroup("1").withTimeSlot("FRI[1400-1600]").build();


    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final ReadOnlyLesson TYPICAL_MA1101R = new LessonBuilder().withCode(VALID_CODE_MA1101R)
                .withClassType(VALID_CLASSTYPE_MA1101R).withLocation(VALID_VENUE_MA1101R).withGroup(VALID_GROUP_MA1101R)
                .withTimeSlot(VALID_TIMESLOT_MA1101R).withLecturers(VALID_LECTURER_MA1101R).build();
    public static final ReadOnlyLesson TYPICAL_CS2101 = new LessonBuilder().withCode(VALID_CODE_CS2101)
            .withClassType(VALID_CLASSTYPE_CS2101).withLocation(VALID_VENUE_CS2101).withGroup(VALID_GROUP_CS2101)
            .withTimeSlot(VALID_TIMESLOT_CS2101).withLecturers(VALID_LECTURER_CS2101).build();

    public static final String KEYWORD_MATCHING_MA1101R = "MA1101R"; // A keyword that matches MEIER

    private TypicalLessons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyLesson lesson : getTypicalLessons()) {
            try {
                ab.addLesson(lesson);
            } catch (DuplicateLessonException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    public static List<ReadOnlyLesson> getTypicalLessons() {
        return new ArrayList<>(Arrays.asList(MA1101R_L1, MA1101R_L2, GEQ_T66, MA1101R_T1,
                MA1101R_T2, CS2101_L1, CS2101_L2));
    }

    public static HashSet<Code> getTypicalModuleCodeSet() {
        HashSet<Code> set = new HashSet<>();
        List<ReadOnlyLesson> lessonLst = getTypicalLessons();
        for (ReadOnlyLesson l : lessonLst) {
            if (!set.contains(l.getCode())) {
                set.add(l.getCode());
            }
        }
        return set;
    }
}
