package seedu.address.logic.parser;

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
