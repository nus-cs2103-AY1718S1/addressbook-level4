# caoliangnus
###### /java/seedu/address/logic/commands/ColorKeywordCommandTest.java
``` java
public class ColorKeywordCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_enableColorCommand_success() {
        CommandResult result = new ColorKeywordCommand("enable").execute();
        assertEquals(ENABLE_COLOR + MESSAGE_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ColorKeywordEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_disableColorCommand_success() {
        CommandResult result = new ColorKeywordCommand("disable").execute();
        assertEquals(DISABLE_COLOR + MESSAGE_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ColorKeywordEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

}
```
###### /java/seedu/address/logic/parser/ColorKeywordCommandParserTest.java
``` java
public class ColorKeywordCommandParserTest {
    private ColorKeywordCommandParser parser = new ColorKeywordCommandParser();

    @Test
    public void parse_validArgs_returnsColorCommand() {
        assertParseSuccess(parser, "enable", new ColorKeywordCommand("enable"));
    }


    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "enabled",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ColorKeywordCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/model/AddressBookTest.java
``` java
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalLessons.MA1101R_L1;
import static seedu.address.testutil.TypicalLessons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.lecturer.Lecturer;
import seedu.address.model.module.Lesson;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.Remark;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();


    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getLessonList());
        assertEquals(Collections.emptyList(), addressBook.getLecturerList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsAssertionError() {
        // Repeat ALICE twice
        List<Lesson> newLessons = Arrays.asList(new Lesson(MA1101R_L1), new Lesson(MA1101R_L1));
        List<Lecturer> newLecturers = new ArrayList<>(MA1101R_L1.getLecturers());
        AddressBookStub newData = new AddressBookStub(newLessons, newLecturers);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    @Test
    public void getLessonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getLessonList().remove(0);
    }

    @Test
    public void getLecturerList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getLecturerList().remove(0);
    }

    /**
     * A stub ReadOnlyAddressBook whose lessons and lecturers lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<ReadOnlyLesson> lessons = FXCollections.observableArrayList();
        private final ObservableList<Lecturer> lecturers = FXCollections.observableArrayList();
        private final ObservableList<Remark> remarks = FXCollections.observableArrayList();

        AddressBookStub(Collection<? extends ReadOnlyLesson> lessons, Collection<? extends Lecturer> lecturers) {
            this.lessons.setAll(lessons);
            this.lecturers.setAll(lecturers);
            this.remarks.setAll(remarks);
        }

        @Override
        public ObservableList<ReadOnlyLesson> getLessonList() {
            return lessons;
        }

        @Override
        public ObservableList<Lecturer> getLecturerList() {
            return lecturers;
        }

        @Override
        public ObservableList<Remark> getRemarkList() {
            return remarks;
        }
    }

}
```
###### /java/seedu/address/model/lesson/ClassTypeTest.java
``` java
public class ClassTypeTest {

    @Test
    public void isValidClassType() {
        // invalid addresses
        assertFalse(ClassType.isValidClassType("")); // empty string
        assertFalse(ClassType.isValidClassType(" ")); // spaces only
        assertFalse(ClassType.isValidClassType("Lecture")); // spell out 'lecture'
        assertFalse(ClassType.isValidClassType("Tutorial")); // spell out 'Tutorial'


        // valid addresses
        assertTrue(ClassType.isValidClassType("lec"));
        assertTrue(ClassType.isValidClassType("Lec")); // One capital character
        assertTrue(ClassType.isValidClassType("LEc")); // Two capital characters
        assertTrue(ClassType.isValidClassType("LEC")); // Three capital characters

        assertTrue(ClassType.isValidClassType("tut"));
        assertTrue(ClassType.isValidClassType("Tut")); // One capital character
        assertTrue(ClassType.isValidClassType("TUt")); // Two capital characters
        assertTrue(ClassType.isValidClassType("TUT")); // Three capital characters
    }
}
```
###### /java/seedu/address/model/lesson/CodeTest.java
``` java
public class CodeTest {
    @Test
    public void isValidCode() {
        // invalid name
        assertFalse(Code.isValidCode("")); // empty string
        assertFalse(Code.isValidCode(" ")); // spaces only
        assertFalse(Code.isValidCode("C2101")); // contains only 1 letter
        assertFalse(Code.isValidCode("CS")); // no digit
        assertFalse(Code.isValidCode("CS*")); // contains non-alphanumeric characters
        assertFalse(Code.isValidCode("CS221")); // contains only 3 digits
        assertFalse(Code.isValidCode("2101")); // contains only digit
        assertFalse(Code.isValidCode("2101")); // contains only digit
        assertFalse(Code.isValidCode("GEQR221")); // contains 4 letters

        // valid name
        assertTrue(Code.isValidCode("CS2101")); // 2 letters and 4 digits
        assertTrue(Code.isValidCode("cs2101")); // all small letter for

        assertTrue(Code.isValidCode("MA1101R")); // 2 letters, 4 digits and 1 letter
        assertTrue(Code.isValidCode("ma1101R")); // last letter is capital
        assertTrue(Code.isValidCode("ma1101r")); // all small letter
        assertTrue(Code.isValidCode("Ma1101r")); // first letter is capital

        assertTrue(Code.isValidCode("GEQ1000")); // 3 letters and 4 digits
        assertTrue(Code.isValidCode("geq1000")); // all small letter for
    }
}
```
###### /java/seedu/address/model/lesson/GroupTest.java
``` java
public class GroupTest {

    @Test
    public void isValidGroup() {
        // invalid phone numbers
        assertFalse(Group.isValidGroup("")); // empty string
        assertFalse(Group.isValidGroup(" ")); // spaces only
        assertFalse(Group.isValidGroup("phone")); // non-numeric
        assertFalse(Group.isValidGroup("9011p041")); // alphabets within digits
        assertFalse(Group.isValidGroup("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Group.isValidGroup("9")); // exactly 1 numbers
        assertTrue(Group.isValidGroup("93121534")); //more than 1 number
        assertTrue(Group.isValidGroup("124293842033123")); // long phone numbers
    }
}
```
###### /java/seedu/address/model/lesson/LocationTest.java
``` java
public class LocationTest {
    @Test
    public void isValidLocation() {
        // invalid addresses
        assertFalse(Location.isValidLocation("")); // empty string
        assertFalse(Location.isValidLocation(" ")); // spaces only

        // valid addresses
        assertTrue(Location.isValidLocation("COM2 02-03"));
        assertTrue(Location.isValidLocation("LT29")); // No space
        assertTrue(Location.isValidLocation("NUS SOC COM2 02-05")); // long address
    }
}
```
###### /java/seedu/address/model/lesson/TimeSlotTest.java
``` java
public class TimeSlotTest {
    @Test
    public void isValidTimeSlot() {
        // invalid phone numbers
        assertFalse(TimeSlot.isValidTimeSLot("")); // empty string
        assertFalse(TimeSlot.isValidTimeSLot(" ")); // spaces only
        assertFalse(TimeSlot.isValidTimeSLot("FRI")); // no '['
        assertFalse(TimeSlot.isValidTimeSLot("FRI[]")); // no start time and end time
        assertFalse(TimeSlot.isValidTimeSLot("FRI[1000]")); // no end time
        assertFalse(TimeSlot.isValidTimeSLot("FRI[10001200]")); // no '-'
        assertFalse(TimeSlot.isValidTimeSLot("FRI[1200-1000]")); // start time less than end time

        // valid phone numbers
        assertTrue(TimeSlot.isValidTimeSLot("FRI[1000-1200]")); // Must follow this format
    }
}
```
###### /java/seedu/address/testutil/AddressBookBuilder.java
``` java
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.lecturer.Lecturer;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.exceptions.DuplicateLessonException;


/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withLesson("MA1101R").withLecturer("Dr Tan").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Lesson} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withLesson(ReadOnlyLesson lesson) {
        try {
            addressBook.addLesson(lesson);
        } catch (DuplicateLessonException dpe) {
            throw new IllegalArgumentException("lesson is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses {@code tagName} into a {@code Tag} and adds it to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withLecturer(String lecturerName) {
        try {
            addressBook.addLecturer(new Lecturer(lecturerName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("lecturerName is expected to be valid.");
        }
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
```
###### /java/seedu/address/testutil/EditLessonDescriptorBuilder.java
``` java
import java.util.Arrays;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand.EditLessonDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.module.ReadOnlyLesson;


/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditLessonDescriptorBuilder {

    private EditLessonDescriptor descriptor;

    public EditLessonDescriptorBuilder() {
        descriptor = new EditLessonDescriptor();
    }

    public EditLessonDescriptorBuilder(EditLessonDescriptor descriptor) {
        this.descriptor = new EditLessonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditLessonDescriptorBuilder(ReadOnlyLesson lesson) {
        descriptor = new EditLessonDescriptor();
        descriptor.setCode(lesson.getCode());
        descriptor.setClassType(lesson.getClassType());
        descriptor.setLocation(lesson.getLocation());
        descriptor.setGroup(lesson.getGroup());
        descriptor.setTimeSlot(lesson.getTimeSlot());
        descriptor.setLecturer(lesson.getLecturers());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withCode(String name) {
        try {
            ParserUtil.parseCode(Optional.of(name)).ifPresent(descriptor::setCode);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withClassType(String phone) {
        try {
            ParserUtil.parseClassType(Optional.of(phone)).ifPresent(descriptor::setClassType);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("phone is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withLocation(String email) {
        try {
            ParserUtil.parseLocation(Optional.of(email)).ifPresent(descriptor::setLocation);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("email is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withGroup(String address) {
        try {
            ParserUtil.parseGroup(Optional.of(address)).ifPresent(descriptor::setGroup);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withTimeSlot(String address) {
        try {
            ParserUtil.parseTimeSlot(Optional.of(address)).ifPresent(descriptor::setTimeSlot);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditLessonDescriptorBuilder withLecturers(String... tags) {
        try {
            descriptor.setLecturer(ParserUtil.parseLecturer(Arrays.asList(tags)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    public EditLessonDescriptor build() {
        return descriptor;
    }
}
```
###### /java/seedu/address/testutil/LessonBuilder.java
``` java
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
 * A utility class to help with building Lesson objects.
 */
public class LessonBuilder {

    public static final String DEFAULT_CODE = "MA1101R";
    public static final String DEFAULT_CLASS_TYPE = "LEC";
    public static final String DEFAULT_LOCATION = "LT27";
    public static final String DEFAULT_GROUP = "1";
    public static final String DEFAULT_TIME_SLOT = "FRI[1400-1600]";
    public static final String DEFAULT_LECTURER = "Ma Siu Lun";

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
```
###### /java/seedu/address/testutil/LessonUtil.java
``` java
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LECTURER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_SLOT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.module.ReadOnlyLesson;

/**
 * A utility class for Lesson.
 */
public class LessonUtil {

    /**
     * Returns an add command string for adding the {@code lesson}.
     */
    public static String getAddCommand(ReadOnlyLesson lesson) {
        return AddCommand.COMMAND_WORD + " " + getLessonDetails(lesson);
    }


    /**
     * Returns the part of command string for the given {@code lesson}'s details.
     */
    public static String getLessonDetails(ReadOnlyLesson lesson) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_MODULE_CODE + lesson.getCode().fullCodeName + " ");
        sb.append(PREFIX_CLASS_TYPE + lesson.getClassType().value + " ");
        sb.append(PREFIX_VENUE + lesson.getLocation().value + " ");
        sb.append(PREFIX_GROUP + lesson.getGroup().value + " ");
        sb.append(PREFIX_TIME_SLOT + lesson.getTimeSlot().value + " ");
        lesson.getLecturers().stream().forEach(
            s -> sb.append(PREFIX_LECTURER + s.lecturerName + " ")
        );
        return sb.toString();
    }
}
```
###### /java/seedu/address/testutil/TypicalLessonComponents.java
``` java
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Code;

/**
 * A utility class containing a list of {@code LessonComponent} objects to be used in tests.
 */
public class TypicalLessonComponents {

    public static final Code MA1101R = initCode("MA1101R");
    public static final Code CS2101 = initCode("CS2101");
    public static final Code GEQ1000 = initCode("GEQ1000");

    /**
     * This method init Code
     * @param name
     * @return
     */
    private static Code initCode(String name) {
        Code code = null;
        try {
            code = new Code(name);
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        return code;
    }


}
```
###### /java/seedu/address/testutil/TypicalLessons.java
``` java
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
            .withLecturers("Ma Siu Lun").withMarked().build();
    public static final ReadOnlyLesson MA1101R_L2 = new LessonBuilder().withCode("MA1101R")
            .withClassType("Lec").withLocation("LT27")
            .withGroup("2").withTimeSlot("TUE[0800-1000]")
            .withLecturers("Ma Siu Lun, Victor Tan").withMarked().build();
    public static final ReadOnlyLesson GEQ_T66 = new LessonBuilder().withCode("GEQ1000")
            .withClassType("tut").withLocation("ERC02-08")
            .withGroup("66").withTimeSlot("FRI[1000-1200]")
            .withLecturers("Carl").withMarked().build();
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


    // Manually added - Lesson's details found in {@code CommandTestUtil}
    public static final ReadOnlyLesson TYPICAL_MA1101R = new LessonBuilder().withCode(VALID_CODE_MA1101R)
                .withClassType(VALID_CLASSTYPE_MA1101R).withLocation(VALID_VENUE_MA1101R).withGroup(VALID_GROUP_MA1101R)
                .withTimeSlot(VALID_TIMESLOT_MA1101R).withLecturers(VALID_LECTURER_MA1101R).build();
    public static final ReadOnlyLesson TYPICAL_CS2101 = new LessonBuilder().withCode(VALID_CODE_CS2101)
            .withClassType(VALID_CLASSTYPE_CS2101).withLocation(VALID_VENUE_CS2101).withGroup(VALID_GROUP_CS2101)
            .withTimeSlot(VALID_TIMESLOT_CS2101).withLecturers(VALID_LECTURER_CS2101).build();

    public static final String CLASS_TYPE_LECTURE = "Lec"; // A keyword that matches class type lecture
    public static final String KEYWORD_MATCHING_MA1101R = "MA1101R"; // A keyword that matches MEIER
    public static final String KEYWORD_MATCHING_LT27 = "LT27"; // A keyword that matches MEIER

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
```
###### /java/seedu/address/ui/CommandBoxTest.java
``` java
    @Test
    public void configActiveKeywordTest() {
        String commandKeyword = "list";
        String commandColorTrue = "red";
        String commandColorFalse = "black";
        assertColorSame(commandColorTrue, commandKeyword);
        assertColorNotSame(commandColorFalse, commandKeyword);
    }


    private void assertColorSame(String commandColor, String commandKeyword) {
        assertEquals(commandColor, keywordColorMap.get(commandKeyword));
    }

    private void assertColorNotSame(String commandColor, String commandKeyword) {
        assertNotEquals(commandColor, keywordColorMap.get(commandKeyword));
    }
```
###### /java/systemtests/ColorEnableSystemTest.java
``` java

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ColorKeywordCommand;
import seedu.address.model.Model;

public class ColorEnableSystemTest extends AddressBookSystemTest {
    @Test
    public void colorEnable() {
        String command;
        String expectedResultMessage;

        /* Case: enable highlighting feature with leading spaces and trailing space
         */
        assertCommandSuccess("   " + ColorKeywordCommand.COMMAND_WORD + " enable   ");

        /* Case: enable highlighting feature when already enabled
         */
        assertCommandSuccess("   " + ColorKeywordCommand.COMMAND_WORD + " enable   ");

        /* Case: attempt to enable highlighting command keyword that are undefined */
        command = ColorKeywordCommand.COMMAND_WORD + " " + "Enabled";
        expectedResultMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                ColorKeywordCommand.MESSAGE_USAGE);
        assertCommandFailure(command, expectedResultMessage);

        /* Case: disable highlighting feature with leading spaces and trailing space
         */
        assertCommandSuccess("   " + ColorKeywordCommand.COMMAND_WORD + " disable   ");

        /* Case: disable highlighting feature when already disabled
         */
        assertCommandSuccess("   " + ColorKeywordCommand.COMMAND_WORD + " disable   ");

        /* Case: attempt to disable highlighting command keyword that are undefined */
        command = ColorKeywordCommand.COMMAND_WORD + " " + "Disabled";
        expectedResultMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                ColorKeywordCommand.MESSAGE_USAGE);
        assertCommandFailure(command, expectedResultMessage);


        /* Case: mixed case command word -> rejected */
        assertCommandFailure("EnaBle", MESSAGE_UNKNOWN_COMMAND);

        /* Case: Wrong command word -> rejected */
        assertCommandFailure("enabledisable", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code ColorKeywordCommand#MESSAGE_SUCCESS} and the model related components equal to an
     * empty model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command) {
        String text;
        if (command.contains("enable")) {
            text = ColorKeywordCommand.ENABLE_COLOR + ColorKeywordCommand.MESSAGE_SUCCESS;
        } else {
            text = ColorKeywordCommand.DISABLE_COLOR + ColorKeywordCommand.MESSAGE_SUCCESS;
        }

        assertCommandSuccess(command, text, getModel());
        assertStatusBarUnchanged();


    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     * @see ColorEnableSystemTest # assertCommandSuccess(String)
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
