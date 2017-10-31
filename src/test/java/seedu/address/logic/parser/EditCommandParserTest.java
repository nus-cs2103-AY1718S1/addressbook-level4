package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CLASSTYPE_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.CLASSTYPE_DESC_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.CODE_DESC_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_DESC_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CLASSTYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GROUP_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIMESLOT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VENUE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LECTURER_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.LECTURER_DESC_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.TIMESLOT_DESC_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSTYPE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSTYPE_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LECTURER_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LECTURER_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMESLOT_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_MA1101R;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LECTURER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LESSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LESSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_LESSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditLessonDescriptor;
import seedu.address.model.ListingUnit;
import seedu.address.model.lecturer.Lecturer;
import seedu.address.model.module.ClassType;
import seedu.address.model.module.Code;
import seedu.address.model.module.Group;
import seedu.address.model.module.Location;
import seedu.address.model.module.TimeSlot;
import seedu.address.testutil.EditLessonDescriptorBuilder;

//@@author junming403
public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_LECTURER;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_CODE_MA1101R, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        // negative index
        assertParseFailure(parser, "-5" + CODE_DESC_MA1101R, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + CODE_DESC_MA1101R, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        assertParseFailure(parser, "1" + INVALID_CODE_DESC,
                Code.MESSAGE_CODE_CONSTRAINTS); // invalid module code
        assertParseFailure(parser, "1" + INVALID_CLASSTYPE_DESC,
                ClassType.MESSAGE_CLASSTYPE_CONSTRAINTS); // invalid class type
        assertParseFailure(parser, "1" + INVALID_VENUE_DESC,
                Location.MESSAGE_LOCATION_CONSTRAINTS); // invalid venue
        assertParseFailure(parser, "1" + INVALID_GROUP_DESC,
                Group.MESSAGE_GROUP_CONSTRAINTS); // invalid group
        assertParseFailure(parser, "1" + INVALID_TIMESLOT_DESC,
                TimeSlot.MESSAGE_TIMESLOT_CONSTRAINTS); // invalid time slot

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_CLASSTYPE_DESC + VENUE_DESC_MA1101R,
                ClassType.MESSAGE_CLASSTYPE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + CLASSTYPE_DESC_MA1101R + INVALID_CLASSTYPE_DESC,
                ClassType.MESSAGE_CLASSTYPE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + LECTURER_DESC_MA1101R + LECTURER_DESC_CS2101 + TAG_EMPTY,
                Lecturer.MESSAGE_LECTURER_CONSTRAINTS);
        assertParseFailure(parser, "1" + LECTURER_DESC_MA1101R + TAG_EMPTY + LECTURER_DESC_CS2101,
                Lecturer.MESSAGE_LECTURER_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + LECTURER_DESC_MA1101R + LECTURER_DESC_CS2101,
                Lecturer.MESSAGE_LECTURER_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_CODE_DESC + INVALID_CLASSTYPE_DESC + INVALID_VENUE_DESC
                        + INVALID_GROUP_DESC + INVALID_TIMESLOT_DESC,
                ClassType.MESSAGE_CLASSTYPE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_LESSON;
        String userInput = targetIndex.getOneBased() + CLASSTYPE_DESC_CS2101 + LECTURER_DESC_MA1101R
                + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + CODE_DESC_MA1101R + TIMESLOT_DESC_MA1101R
                + LECTURER_DESC_CS2101;

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder().withCode(VALID_CODE_MA1101R)
                .withClassType(VALID_CLASSTYPE_CS2101).withLocation(VALID_VENUE_MA1101R)
                .withGroup(VALID_GROUP_MA1101R).withTimeSlot(VALID_TIMESLOT_MA1101R)
                .withLecturers(VALID_LECTURER_MA1101R, VALID_LECTURER_CS2101).build();
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_LESSON;
        String userInput = targetIndex.getOneBased() + CLASSTYPE_DESC_CS2101 + VENUE_DESC_MA1101R;

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder().withClassType(VALID_CLASSTYPE_CS2101)
                .withLocation(VALID_VENUE_MA1101R).build();
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        // code
        Index targetIndex = INDEX_SECOND_LESSON;
        String userInput = targetIndex.getOneBased() + CODE_DESC_MA1101R;
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder().withCode(VALID_CODE_MA1101R).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // class type
        userInput = targetIndex.getOneBased() + CLASSTYPE_DESC_MA1101R;
        descriptor = new EditLessonDescriptorBuilder().withClassType(VALID_CLASSTYPE_MA1101R).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // venue
        userInput = targetIndex.getOneBased() + VENUE_DESC_MA1101R;
        descriptor = new EditLessonDescriptorBuilder().withLocation(VALID_VENUE_MA1101R).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // group
        userInput = targetIndex.getOneBased() + GROUP_DESC_MA1101R;
        descriptor = new EditLessonDescriptorBuilder().withGroup(VALID_GROUP_MA1101R).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // time slot
        userInput = targetIndex.getOneBased() + TIMESLOT_DESC_MA1101R;
        descriptor = new EditLessonDescriptorBuilder().withTimeSlot(VALID_TIMESLOT_MA1101R).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + LECTURER_DESC_MA1101R;
        descriptor = new EditLessonDescriptorBuilder().withLecturers(VALID_LECTURER_MA1101R).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_LESSON;
        String userInput = targetIndex.getOneBased()  + CLASSTYPE_DESC_MA1101R + VENUE_DESC_MA1101R
                + GROUP_DESC_MA1101R  + CLASSTYPE_DESC_MA1101R + VENUE_DESC_MA1101R
                + GROUP_DESC_MA1101R
                + CLASSTYPE_DESC_CS2101 + VENUE_DESC_CS2101 + GROUP_DESC_CS2101 + LECTURER_DESC_MA1101R
                + LECTURER_DESC_CS2101;

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder().withClassType(VALID_CLASSTYPE_CS2101)
                .withLocation(VALID_VENUE_CS2101).withGroup(VALID_GROUP_CS2101)
                .withLecturers(VALID_LECTURER_CS2101, VALID_LECTURER_MA1101R).build();
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        // no other valid values specified
        Index targetIndex = INDEX_THIRD_LESSON;
        String userInput = targetIndex.getOneBased() + INVALID_CLASSTYPE_DESC + CLASSTYPE_DESC_CS2101;
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder()
                .withClassType(VALID_CLASSTYPE_CS2101).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + VENUE_DESC_MA1101R + INVALID_CLASSTYPE_DESC
                + GROUP_DESC_CS2101
                + CLASSTYPE_DESC_CS2101;
        descriptor = new EditLessonDescriptorBuilder().withClassType(VALID_CLASSTYPE_CS2101)
                .withGroup(VALID_GROUP_CS2101)
                .withLocation(VALID_VENUE_MA1101R).build();
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetLecturers_failure() {
        Index targetIndex = INDEX_THIRD_LESSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder().withLecturers().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);

        assertParseFailure(parser, userInput, Lecturer.MESSAGE_LECTURER_CONSTRAINTS);
    }

    @Test
    public void parse_editModule() {
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        Index targetIndex = INDEX_FIRST_LESSON;
        String userInput = targetIndex.getOneBased()  + " " + VALID_CODE_MA1101R;

        EditCommand expectedCommand = new EditCommand(targetIndex, VALID_CODE_MA1101R);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_editLocation() {
        ListingUnit.setCurrentListingUnit(ListingUnit.LOCATION);
        Index targetIndex = INDEX_FIRST_LESSON;
        String userInput = targetIndex.getOneBased()  + " " + VALID_VENUE_MA1101R;

        ListingUnit.setCurrentListingUnit(ListingUnit.LOCATION);
        EditCommand expectedCommand = new EditCommand(targetIndex, VALID_VENUE_MA1101R);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
