package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CLASSTYPE_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.CLASSTYPE_DESC_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.CODE_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.CODE_DESC_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_DESC_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CLASSTYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GROUP_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LECTURER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIMESLOT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VENUE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LECTURER_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.LECTURER_DESC_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.TIMESLOT_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.TIMESLOT_DESC_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSTYPE_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LECTURER_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LECTURER_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMESLOT_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_MA1101R;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.lecturer.Lecturer;
import seedu.address.model.module.ClassType;
import seedu.address.model.module.Code;
import seedu.address.model.module.Group;
import seedu.address.model.module.Lesson;
import seedu.address.model.module.Location;
import seedu.address.model.module.TimeSlot;
import seedu.address.testutil.LessonBuilder;

//@@author cctdaniel
public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Lesson expectedLesson = new LessonBuilder().withCode(VALID_CODE_MA1101R).withClassType(VALID_CLASSTYPE_MA1101R)
                .withLocation(VALID_VENUE_MA1101R).withGroup(VALID_GROUP_MA1101R).withTimeSlot(VALID_TIMESLOT_MA1101R)
                .withLecturers(VALID_LECTURER_MA1101R).build();

        // multiple names - last name accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + CODE_DESC_CS2101 + CODE_DESC_MA1101R
                + CLASSTYPE_DESC_MA1101R + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R
                + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R, new AddCommand(expectedLesson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_CS2101
                + CLASSTYPE_DESC_MA1101R + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R
                + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R, new AddCommand(expectedLesson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_CS2101 + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R
                + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R, new AddCommand(expectedLesson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                +  VENUE_DESC_MA1101R + GROUP_DESC_CS2101 + GROUP_DESC_MA1101R
                + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R, new AddCommand(expectedLesson));

        // multiple addresses - last time slot accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                +  VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + TIMESLOT_DESC_CS2101
                + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R, new AddCommand(expectedLesson));

        // multiple tags - all accepted
        Lesson expectedLessonMultipleLecturer = new LessonBuilder().withCode(VALID_CODE_MA1101R)
                .withClassType(VALID_CLASSTYPE_MA1101R).withLocation(VALID_VENUE_MA1101R)
                .withGroup(VALID_GROUP_MA1101R).withTimeSlot(VALID_TIMESLOT_MA1101R)
                .withLecturers(VALID_LECTURER_MA1101R, VALID_LECTURER_CS2101).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                        +  VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R
                        + LECTURER_DESC_MA1101R + LECTURER_DESC_CS2101,
                new AddCommand(expectedLessonMultipleLecturer));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Lesson expectedLesson = new LessonBuilder().withCode(VALID_CODE_MA1101R).withClassType(VALID_CLASSTYPE_MA1101R)
                .withLocation(VALID_VENUE_MA1101R).withGroup(VALID_GROUP_MA1101R).withTimeSlot(VALID_TIMESLOT_MA1101R)
                .withLecturers(VALID_LECTURER_MA1101R).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                +  VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R
                + LECTURER_DESC_MA1101R, new AddCommand(expectedLesson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing code prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_CODE_MA1101R + CLASSTYPE_DESC_MA1101R
                +  VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R
                + LECTURER_DESC_MA1101R, expectedMessage);

        // missing class type prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + VALID_CLASSTYPE_MA1101R
                +  VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R
                + LECTURER_DESC_MA1101R, expectedMessage);

        // missing venue prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + VALID_VENUE_MA1101R + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R
                + LECTURER_DESC_MA1101R, expectedMessage);

        // missing group prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R + VALID_GROUP_MA1101R + TIMESLOT_DESC_MA1101R
                + LECTURER_DESC_MA1101R, expectedMessage);

        // missing time slot prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + VALID_TIMESLOT_MA1101R
                + LECTURER_DESC_MA1101R, expectedMessage);

        // missing lecturer prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R
                + VALID_LECTURER_MA1101R, expectedMessage);


        // all prefixes missing
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_CODE_MA1101R + VALID_CLASSTYPE_MA1101R
                + VALID_VENUE_MA1101R + VALID_GROUP_MA1101R + VALID_TIMESLOT_MA1101R
                + VALID_LECTURER_MA1101R, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid code
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_CODE_DESC + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R
                + LECTURER_DESC_MA1101R, Code.MESSAGE_CODE_CONSTRAINTS);

        // invalid class type
        assertParseFailure(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + INVALID_CLASSTYPE_DESC
                + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R
                + LECTURER_DESC_MA1101R, ClassType.MESSAGE_CLASSTYPE_CONSTRAINTS);

        // invalid venue
        assertParseFailure(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + INVALID_VENUE_DESC + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R
                + LECTURER_DESC_MA1101R, Location.MESSAGE_LOCATION_CONSTRAINTS);

        // invalid group
        assertParseFailure(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R + INVALID_GROUP_DESC + TIMESLOT_DESC_MA1101R
                + LECTURER_DESC_MA1101R, Group.MESSAGE_GROUP_CONSTRAINTS);

        // invalid time slot
        assertParseFailure(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + INVALID_TIMESLOT_DESC
                + LECTURER_DESC_MA1101R, TimeSlot.MESSAGE_TIMESLOT_CONSTRAINTS);

        // invalid lecturer
        assertParseFailure(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R
                + INVALID_LECTURER_DESC, Lecturer.MESSAGE_LECTURER_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        //The sequence of Add is {CT, V, GP, TS, C, L}
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_CODE_DESC + INVALID_CLASSTYPE_DESC
                + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + INVALID_TIMESLOT_DESC
                + LECTURER_DESC_MA1101R, ClassType.MESSAGE_CLASSTYPE_CONSTRAINTS);
    }
}
