# caoliangnus
###### \java\seedu\address\logic\commands\ColorKeywordCommandTest.java
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
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FONT_SIZE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LECTURER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_SLOT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.module.Code;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.exceptions.LessonNotFoundException;
import seedu.address.model.module.predicates.FixedCodePredicate;

import seedu.address.model.module.predicates.ShowSpecifiedLessonPredicate;
import seedu.address.testutil.EditLessonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_CODE_MA1101R = "MA1101R";
    public static final String VALID_CODE_CS2101 = "CS2101";
    public static final String VALID_CODE_MA1102R = "MA1102R";
    public static final String VALID_CLASSTYPE_MA1101R = "Lec";
    public static final String VALID_CLASSTYPE_CS2101 = "Tut";
    public static final String VALID_VENUE_MA1101R = "LT30";
    public static final String VALID_VENUE_MA1102R = "LT29";
    public static final String VALID_VENUE_CS2101 = "COM02-04";
    public static final String VALID_GROUP_MA1101R = "3";
    public static final String VALID_GROUP_CS2101 = "2";
    public static final String VALID_TIMESLOT_MA1101R = "TUE[1300-1500]";
    public static final String VALID_TIMESLOT_CS2101 = "TUE[1600-1800]";
    public static final String VALID_LECTURER_MA1101R = "Ma Siu Lun";
    public static final String VALID_LECTURER_CS2101 = "Diana";
    public static final String VALID_FONT_SIZE_XSMALL = "xsmall";
    public static final String VALID_FONT_SIZE_SMALL = "small";
    public static final String VALID_THEME_LIGHT = "light";
    public static final String VALID_THEME_DARK = "dark";


    public static final String CODE_DESC_MA1101R = " " + PREFIX_MODULE_CODE + VALID_CODE_MA1101R;
    public static final String CODE_DESC_CS2101 = " " + PREFIX_MODULE_CODE + VALID_CODE_CS2101;
    public static final String CLASSTYPE_DESC_MA1101R = " " + PREFIX_CLASS_TYPE + VALID_CLASSTYPE_MA1101R;
    public static final String CLASSTYPE_DESC_CS2101 = " " + PREFIX_CLASS_TYPE + VALID_CLASSTYPE_CS2101;
    public static final String VENUE_DESC_MA1101R = " " + PREFIX_VENUE + VALID_VENUE_MA1101R;
    public static final String VENUE_DESC_CS2101 = " " + PREFIX_VENUE + VALID_VENUE_CS2101;
    public static final String GROUP_DESC_MA1101R = " " + PREFIX_GROUP + VALID_GROUP_MA1101R;
    public static final String GROUP_DESC_CS2101 = " " + PREFIX_GROUP + VALID_GROUP_CS2101;
    public static final String TIMESLOT_DESC_MA1101R = " " + PREFIX_TIME_SLOT + VALID_TIMESLOT_MA1101R;
    public static final String TIMESLOT_DESC_CS2101 = " " + PREFIX_TIME_SLOT + VALID_TIMESLOT_CS2101;
    public static final String LECTURER_DESC_MA1101R = " " + PREFIX_LECTURER + VALID_LECTURER_MA1101R;
    public static final String LECTURER_DESC_CS2101 = " " + PREFIX_LECTURER + VALID_LECTURER_CS2101;
    public static final String FONT_SIZE_DESC_XSMALL = " " + PREFIX_FONT_SIZE + VALID_FONT_SIZE_XSMALL;
    public static final String FONT_SIZE_DESC_SMALL = " " + PREFIX_FONT_SIZE + VALID_FONT_SIZE_SMALL;


    public static final String INVALID_CODE_DESC = " " + PREFIX_MODULE_CODE + "MA*"; //code format is not correct
    public static final String INVALID_CLASSTYPE_DESC = " " + PREFIX_CLASS_TYPE + "1a"; // 'a' not allowed in class type
    public static final String INVALID_VENUE_DESC = " " + PREFIX_VENUE; // empty string not allowed for venue
    public static final String INVALID_GROUP_DESC = " " + PREFIX_GROUP + "SL1"; // 'SL' not allowed for addresses
    public static final String INVALID_TIMESLOT_DESC = " " + PREFIX_TIME_SLOT + "FRIDAY[1200-1300]"; // Only 3 letters
    public static final String INVALID_LECTURER_DESC = " " + PREFIX_LECTURER + ""; // '*' not allowed in tags
    public static final String INVALID_FONT_SIZE_DESC = " " + PREFIX_FONT_SIZE
            + "small!"; // '!' not allowed in font size
    public static final String INVALID_THEME_DESC = "blue";

    public static final EditCommand.EditLessonDescriptor DESC_MA1101R;
    public static final EditCommand.EditLessonDescriptor DESC_CS2101;

    public static final FixedCodePredicate MA1101R_CODE_PREDICATE;

    static {
        DESC_MA1101R = new EditLessonDescriptorBuilder().withCode(VALID_CODE_MA1101R)
                .withClassType(VALID_CLASSTYPE_MA1101R).withLocation(VALID_VENUE_MA1101R).withGroup(VALID_GROUP_MA1101R)
                .withTimeSlot(VALID_TIMESLOT_MA1101R).withLecturers(VALID_LECTURER_MA1101R).build();
        DESC_CS2101 = new EditLessonDescriptorBuilder().withCode(VALID_CODE_CS2101)
                .withClassType(VALID_CLASSTYPE_CS2101).withLocation(VALID_VENUE_CS2101).withGroup(VALID_GROUP_CS2101)
                .withTimeSlot(VALID_TIMESLOT_CS2101).withLecturers(VALID_LECTURER_CS2101).build();
        try {
            MA1101R_CODE_PREDICATE = new FixedCodePredicate(new Code(VALID_CODE_MA1101R));
        } catch (IllegalValueException e) {
            throw new AssertionError("The code cannot be invalid");
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered person list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<ReadOnlyLesson> expectedFilteredList = new ArrayList<>(actualModel.getFilteredLessonList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredLessonList());
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the first lesson in the {@code model}'s address book.
     */
    public static void showFirstLessonOnly(Model model) {
        ReadOnlyLesson lesson = model.getAddressBook().getLessonList().get(0);
        model.updateFilteredLessonList(new ShowSpecifiedLessonPredicate(lesson));

        assert model.getFilteredLessonList().size() == 1;
    }

    /**
     * Deletes the first lesson in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstLesson(Model model) {
        ReadOnlyLesson firstLesson = model.getFilteredLessonList().get(0);
        try {
            model.deleteLesson(firstLesson);
        } catch (LessonNotFoundException pnfe) {
            throw new AssertionError("Person in filtered list must exist in model.", pnfe);
        }
    }
}
```
###### \java\seedu\address\logic\commands\EditPersonDescriptorTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LECTURER_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMESLOT_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_CS2101;
import static seedu.address.logic.commands.EditCommand.EditLessonDescriptor;

import org.junit.Test;

import seedu.address.testutil.EditLessonDescriptorBuilder;

public class EditPersonDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditLessonDescriptor descriptorWithSameValues = new EditLessonDescriptor(DESC_MA1101R);
        assertTrue(DESC_MA1101R.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_MA1101R.equals(DESC_MA1101R));

        // null -> returns false
        assertFalse(DESC_MA1101R.equals(null));

        // different types -> returns false
        assertFalse(DESC_MA1101R.equals(5));

        // different values -> returns false
        assertFalse(DESC_MA1101R.equals(DESC_CS2101));

        // different module code -> returns false
        EditLessonDescriptor editedAmy =
                new EditLessonDescriptorBuilder(DESC_MA1101R).withCode(VALID_CODE_CS2101).build();
        assertFalse(DESC_MA1101R.equals(editedAmy));

        // different class type -> returns false
        editedAmy = new EditLessonDescriptorBuilder(DESC_MA1101R).withCode(VALID_CODE_CS2101).build();
        assertFalse(DESC_MA1101R.equals(editedAmy));

        // different time slot -> returns false
        editedAmy = new EditLessonDescriptorBuilder(DESC_MA1101R).withTimeSlot(VALID_TIMESLOT_CS2101).build();
        assertFalse(DESC_MA1101R.equals(editedAmy));

        // different group -> returns false
        editedAmy = new EditLessonDescriptorBuilder(DESC_MA1101R).withGroup(VALID_GROUP_CS2101).build();
        assertFalse(DESC_MA1101R.equals(editedAmy));

        // different lecturer -> returns false
        editedAmy = new EditLessonDescriptorBuilder(DESC_MA1101R).withLecturers(VALID_LECTURER_CS2101).build();
        assertFalse(DESC_MA1101R.equals(editedAmy));

        // different location -> returns false
        editedAmy = new EditLessonDescriptorBuilder(DESC_MA1101R).withLocation(VALID_VENUE_CS2101).build();
        assertFalse(DESC_MA1101R.equals(editedAmy));
    }
}
```
###### \java\seedu\address\logic\parser\ColorKeywordCommandParserTest.java
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
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LESSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.lecturer.Lecturer;
import seedu.address.model.module.ClassType;
import seedu.address.model.module.Code;
import seedu.address.model.module.Group;
import seedu.address.model.module.Location;
import seedu.address.model.module.TimeSlot;

public class ParserUtilTest {
    private static final String INVALID_CODE = "ABC4444AA";
    private static final String INVALID_CLASS_TYPE = "LECTURE";
    private static final String INVALID_LOCATION = "";
    private static final String INVALID_GROUP = "A";
    private static final String INVALID_TIME_SLOT = "FRIDAY[2PM-4PM]";
    private static final String INVALID_LECTURER = "";

    private static final String VALID_CODE = "CS2103T";
    private static final String VALID_CLASS_TYPE = "LEC";
    private static final String VALID_LOCATION = "LT30";
    private static final String VALID_GROUP = "4";
    private static final String VALID_TIME_SLOT = "WED[1100-1200]";
    private static final String VALID_LECTURER_1 = "Prof Cao Liang";
    private static final String VALID_LECTURER_2 = "Prof Justin Poh";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_LESSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_LESSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseCode_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseCode(null);
    }

    @Test
    public void parseCode_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseCode(Optional.of(INVALID_CODE));
    }

    @Test
    public void parseCode_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseCode(Optional.empty()).isPresent());
    }

    @Test
    public void parseCode_validValue_returnsCode() throws Exception {
        Code expectedCode = new Code(VALID_CODE);
        Optional<Code> actualCode = ParserUtil.parseCode(Optional.of(VALID_CODE));

        assertEquals(expectedCode, actualCode.get());
    }

    @Test
    public void parseClassType_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseClassType(null);
    }

    @Test
    public void parseClassType_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseClassType(Optional.of(INVALID_CLASS_TYPE));
    }

    @Test
    public void parseClassType_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseClassType(Optional.empty()).isPresent());
    }

    @Test
    public void parseClassType_validValue_returnsClassType() throws Exception {
        ClassType expectedClassType = new ClassType(VALID_CLASS_TYPE);
        Optional<ClassType> actualClassType = ParserUtil.parseClassType(Optional.of(VALID_CLASS_TYPE));

        assertEquals(expectedClassType, actualClassType.get());
    }

    @Test
    public void parseLocation_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseLocation(null);
    }

    @Test
    public void parseLocation_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseLocation(Optional.of(INVALID_LOCATION));
    }

    @Test
    public void parseLocation_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseLocation(Optional.empty()).isPresent());
    }

    @Test
    public void parseLocation_validValue_returnsLocation() throws Exception {
        Location expectedLocation = new Location(VALID_LOCATION);
        Optional<Location> actualLocation = ParserUtil.parseLocation(Optional.of(VALID_LOCATION));

        assertEquals(expectedLocation, actualLocation.get());
    }

    @Test
    public void parseGroup_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseGroup(null);
    }

    @Test
    public void parseGroup_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseGroup(Optional.of(INVALID_GROUP));
    }

    @Test
    public void parseGroup_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseGroup(Optional.empty()).isPresent());
    }

    @Test
    public void parseGroup_validValue_returnsGroup() throws Exception {
        Group expectedGroup = new Group(VALID_GROUP);
        Optional<Group> actualGroup = ParserUtil.parseGroup(Optional.of(VALID_GROUP));

        assertEquals(expectedGroup, actualGroup.get());
    }

    @Test
    public void parseTimeSlot_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTimeSlot(null);
    }

    @Test
    public void parseTimeSlot_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTimeSlot(Optional.of(INVALID_TIME_SLOT));
    }

    @Test
    public void parseTimeSlot_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseTimeSlot(Optional.empty()).isPresent());
    }

    @Test
    public void parseTimeSlot_validValue_returnsTimeSlot() throws Exception {
        TimeSlot expectedTimeSlot = new TimeSlot(VALID_TIME_SLOT);
        Optional<TimeSlot> actualTimeSlot = ParserUtil.parseTimeSlot(Optional.of(VALID_TIME_SLOT));

        assertEquals(expectedTimeSlot, actualTimeSlot.get());
    }

    @Test
    public void parseLecturers_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseLecturer(null);
    }

    @Test
    public void parseLecturers_collectionWithInvalidLecturers_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseLecturer(Arrays.asList(VALID_LECTURER_1, INVALID_LECTURER));
    }

    @Test
    public void parseLecturers_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseLecturer(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseLecturers_collectionWithValidLecturers_returnsLecturerSet() throws Exception {
        Set<Lecturer> actualLecturerSet = ParserUtil.parseLecturer(Arrays.asList(VALID_LECTURER_1, VALID_LECTURER_2));
        Set<Lecturer> expectedLecturerSet = new HashSet<Lecturer>(
                Arrays.asList(new Lecturer(VALID_LECTURER_1), new Lecturer(VALID_LECTURER_2)));

        assertEquals(expectedLecturerSet, actualLecturerSet);
    }
}
```
###### \java\seedu\address\model\AddressBookTest.java
``` java
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
###### \java\seedu\address\model\lesson\ClassTypeTest.java
``` java
public class ClassTypeTest {

    @Test
    public void isValidClassType() {
        // invalid classtype
        assertFalse(ClassType.isValidClassType("")); // empty string
        assertFalse(ClassType.isValidClassType(" ")); // spaces only
        assertFalse(ClassType.isValidClassType("Lecture")); // spell out 'lecture'
        assertFalse(ClassType.isValidClassType("Tutorial")); // spell out 'Tutorial'


        // valid classtype
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
###### \java\seedu\address\model\lesson\CodeTest.java
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
###### \java\seedu\address\model\lesson\GroupTest.java
``` java
public class GroupTest {

    @Test
    public void isValidGroup() {
        // invalid group numbers
        assertFalse(Group.isValidGroup("")); // empty string
        assertFalse(Group.isValidGroup(" ")); // spaces only
        assertFalse(Group.isValidGroup("phone")); // non-numeric
        assertFalse(Group.isValidGroup("9011p041")); // alphabets within digits
        assertFalse(Group.isValidGroup("9312 1534")); // spaces within digits

        // valid group numbers
        assertTrue(Group.isValidGroup("9")); // exactly 1 numbers
        assertTrue(Group.isValidGroup("93121534")); //more than 1 number
        assertTrue(Group.isValidGroup("124293842033123")); // long phone numbers
    }
}
```
###### \java\seedu\address\model\lesson\LocationTest.java
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
###### \java\seedu\address\model\lesson\TimeSlotTest.java
``` java
public class TimeSlotTest {
    @Test
    public void isValidTimeSlot() {
        // invalid time slot
        assertFalse(TimeSlot.isValidTimeSLot("")); // empty string
        assertFalse(TimeSlot.isValidTimeSLot(" ")); // spaces only
        assertFalse(TimeSlot.isValidTimeSLot("FRI")); // no '['
        assertFalse(TimeSlot.isValidTimeSLot("FRI[]")); // no start time and end time
        assertFalse(TimeSlot.isValidTimeSLot("FRI[1000]")); // no end time
        assertFalse(TimeSlot.isValidTimeSLot("FRI[10001200]")); // no '-'
        assertFalse(TimeSlot.isValidTimeSLot("FRI[1200-1000]")); // start time less than end time

        // valid time slot
        assertTrue(TimeSlot.isValidTimeSLot("FRI[1000-1200]")); // Must follow this format
    }
}
```
###### \java\seedu\address\model\ModelManagerTest.java
``` java
public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredLessonList().remove(0);
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withLesson(MA1101R_L1).withLesson(MA1101R_L2).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookName("differentName");
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String keywords = MA1101R_L1.getCode().fullCodeName;
        modelManager.updateFilteredLessonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);


    }
}
```
###### \java\seedu\address\model\UniqueLecturerListTest.java
``` java
public class UniqueLecturerListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueLecturerList uniqueLecturerList = new UniqueLecturerList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueLecturerList.asObservableList().remove(0);
    }
}
```
###### \java\seedu\address\model\UniqueLessonListTest.java
``` java
public class UniqueLessonListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueLessonList uniqueLessonList = new UniqueLessonList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueLessonList.asObservableList().remove(0);
    }
}
```
###### \java\seedu\address\testutil\AddressBookBuilder.java
``` java
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
###### \java\seedu\address\testutil\EditLessonDescriptorBuilder.java
``` java
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
###### \java\seedu\address\testutil\LessonBuilder.java
``` java
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
###### \java\seedu\address\testutil\LessonUtil.java
``` java
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
###### \java\seedu\address\testutil\TypicalLessonComponents.java
``` java
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
###### \java\seedu\address\testutil\TypicalLessons.java
``` java
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
###### \java\seedu\address\ui\CommandBoxTest.java
``` java

    @Test
    public void computeMarginTest() {
        Text text = new Text("m/");

        //XSmall Font size
        Font font = new Font("Monospace regular", 12);
        text.setFont(font);
        double expectedMargin = commandBox.computeMargin(1, "m/");
        assertEquals(text.getBoundsInLocal().getWidth(), expectedMargin, 0.001);

        //Small Font size
        font = new Font("Monospace regular", 17);
        text.setFont(font);
        expectedMargin = commandBox.computeMargin(2, "m/");
        assertEquals(text.getBoundsInLocal().getWidth(), expectedMargin, 0.001);

        //Default Font size
        font = new Font("Monospace regular", 25);
        text.setFont(font);
        expectedMargin = commandBox.computeMargin(3, "m/");
        assertEquals(text.getBoundsInLocal().getWidth(), expectedMargin, 0.001);

        //Large Font size
        font = new Font("Monospace regular", 32);
        text.setFont(font);
        expectedMargin = commandBox.computeMargin(4, "m/");
        assertEquals(text.getBoundsInLocal().getWidth(), expectedMargin, 0.001);

        //XLarge Font size
        font = new Font("Monospace regular", 40);
        text.setFont(font);
        expectedMargin = commandBox.computeMargin(5, "m/");
        assertEquals(text.getBoundsInLocal().getWidth(), expectedMargin, 0.001);

    }

    @Test
    public void getTagIndexListTest() {
        String textInput = "l/Victor l/Jack";
        ArrayList<Integer> expectedList = new ArrayList<Integer>();
        expectedList.add(0); // the first index for l/ is 0
        expectedList.add(9); // the second index for l/ is 9
        assertEquals(commandBox.getTagIndexList(textInput), expectedList);
    }

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
###### \java\seedu\address\ui\LessonCardTest.java
``` java
public class LessonCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        // Lesson lessonWithNoLecturers = new LessonBuilder().withLecturers(new String[0]).build();
        // LessonListCard lessonListCard = new LessonListCard(lessonWithNoLecturers, 1);
        // uiPartRule.setUiPart(lessonListCard);
        // assertCardDisplay(lessonListCard, lessonWithNoLecturers, 1);

        // with tags
        Lesson lessonWithLecturers = new LessonBuilder().build();
        LessonListCard lessonListCard = new LessonListCard(lessonWithLecturers, 2);
        uiPartRule.setUiPart(lessonListCard);
        assertCardDisplay(lessonListCard, lessonWithLecturers, 2);

        // changes made to Lesson reflects on card
        guiRobot.interact(() -> {
            lessonWithLecturers.setCodeType(MA1101R_L1.getCode());
            lessonWithLecturers.setClassType(MA1101R_L1.getClassType());
            lessonWithLecturers.setLocation(MA1101R_L1.getLocation());
            lessonWithLecturers.setGroupType(MA1101R_L1.getGroup());
            lessonWithLecturers.setTimeSlot(MA1101R_L1.getTimeSlot());
            lessonWithLecturers.setLecturers(MA1101R_L1.getLecturers());
        });
        assertCardDisplay(lessonListCard, lessonWithLecturers, 2);
    }

    @Test
    public void equals() {
        Lesson lesson = new LessonBuilder().build();
        LessonListCard lessonListCard = new LessonListCard(lesson, 0);

        // same lesson, same index -> returns true
        LessonListCard copy = new LessonListCard(lesson, 0);
        assertTrue(lessonListCard.equals(copy));

        // same object -> returns true
        assertTrue(lessonListCard.equals(lessonListCard));

        // null -> returns false
        assertFalse(lessonListCard.equals(null));

        // different types -> returns false
        assertFalse(lessonListCard.equals(0));

        // different lesson, same index -> returns false
        Lesson differentLesson = new LessonBuilder().withCode("CS2101").build();
        assertFalse(lessonListCard.equals(new LessonListCard(differentLesson, 0)));

        // same lesson, different index -> returns false
        assertFalse(lessonListCard.equals(new LessonListCard(lesson, 1)));
    }

    /**
     * Asserts that {@code lessonListCard} displays the details of {@code expectedLesson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(LessonListCard lessonListCard, ReadOnlyLesson expectedLesson, int expectedId) {
        guiRobot.pauseForHuman();

        LessonCardHandle lessonCardHandle = new LessonCardHandle(lessonListCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", lessonCardHandle.getId());

        // verify lesson details are displayed correctly
        assertCardDisplaysLesson(expectedLesson, lessonCardHandle);
    }
}
```
###### \java\systemtests\ColorEnableSystemTest.java
``` java
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
